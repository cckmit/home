package com.neusoft.mid.cloong.resourcePool.webservice.publicCloud.configuration;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;

import javax.xml.ws.Holder;

import com.idc.configuration.bean.ReportConfigurationInfoByDataReqType;
import com.idc.configuration.bean.ReportConfigurationInfoByDataReqType.ConfigurationInfoList;
import com.idc.configuration.bean.ReportConfigurationInfoByDataReqType.ConfigurationInfoList.ConfigurationInfo;
import com.idc.configuration.bean.ReportConfigurationInfoByDataReqType.ConfigurationInfoList.ConfigurationInfo.CIAttributeInfoList.CIAttributeInfo;
import com.idc.configuration.bean.ReportConfigurationInfoByFileReqType;
import com.idc.configuration.bean.ReportConfigurationInfoByFileRespType;
import com.idc.configuration.bean.Authorization;
import com.idc.configuration.bean.ReportConfigurationInfoByDataRespType;
import com.idc.configuration.bean.Response;
import com.idc.configuration.wsdl.Idc;
import com.neusoft.mid.cloong.ReqBodyThreadLcoal;
import com.neusoft.mid.cloong.RespBodyThreadLcoal;
import com.neusoft.mid.cloong.common.mybatis.IbatisDAO;
import com.neusoft.mid.cloong.info.ReqBodyInfo;
import com.neusoft.mid.cloong.info.RespBodyInfo;
import com.neusoft.mid.iamp.logger.LogService;
import com.neusoft.mid.cloong.resourcePool.webservice.publicCloud.configuration.ResultCode;

public class IdcImpl implements Idc
{

	/**
	 * 日志记录
	 */
	private static LogService logger = LogService.getLogger(IdcImpl.class);

	private IbatisDAO dao;

	private final SimpleDateFormat dbFormat = new SimpleDateFormat("yyyyMMddHHmmss");

	private final SimpleDateFormat remoteFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private ReqBodyInfo info;

	@Override
	public ReportConfigurationInfoByFileRespType reportConfigurationInfoByFile(
			ReportConfigurationInfoByFileReqType reportConfigurationInfoByFileReqInput , Authorization authorization , Holder<Response> response)
	{
		System.out.println("文件上报");
		return null;
	}

	@SuppressWarnings("deprecation")
	@Override
	public ReportConfigurationInfoByDataRespType reportConfigurationInfoByData(
			ReportConfigurationInfoByDataReqType reportConfigurationInfoByDataReqInput , Authorization authorization , Holder<Response> response)
	{

		logger.info("进入数据上报资源实例接口");

		/**
		 * 返回的报文头
		 */
		RespBodyInfo respInfo = new RespBodyInfo();

		info = ReqBodyThreadLcoal.get();

		/* 接口执行结果编码 */
		String resultCode = "";

		/* 错误字符串 */
		String faultString = "";

		ArrayList<String> noParentItem = new ArrayList<String>();

		ConfigurationInfoList confList = reportConfigurationInfoByDataReqInput.getConfigurationInfoList();

		ArrayList<ConfigurationInfo> list = null;

		if(null != confList)
			list = (ArrayList<ConfigurationInfo>)confList.getConfigurationInfo();

		if(null != list)
		{

			/* 按最后更新时间排序 */
			Collections.sort(list , new Comparator<ConfigurationInfo>()
			{

				@Override
				public int compare(ConfigurationInfo o1 , ConfigurationInfo o2)
				{
					Date date1 = new Date();
					Date date2 = new Date();
					try
					{
						date1 = remoteFormat.parse(o1.getLastUpateTime());

						date2 = remoteFormat.parse(o2.getLastUpateTime());
					}
					catch(ParseException e)
					{
						logger.error(ResultCode.ERROR_BODY_DATEFORMATERROR.getDesc() , e);
						e.printStackTrace();
					}

					if(date1.before(date2))
					{
						return -1;
					}
					else
					{
						return 1;
					}
				}

			});
			for (ConfigurationInfo i : list)
			{
				/* X86物理机 */
				if("CIDC-PhysicalMachine".equalsIgnoreCase(i.getCiId()))
				{
					/* 物理机主键校验 */
					if(null == i.getInstanceId() || "".equals(i.getInstanceId()))
					{
						resultCode = ResultCode.ERROR_BODY_PM_EQPID_NONE.getCode();
						faultString += ResultCode.ERROR_BODY_PM_EQPID_NONE.getDesc();
						break;
					}

					/* 校验操作类型 如果是删除，要先校验实例是否存在 */
					if(i.getStatus() == 2)
					{
						Integer count = new Integer(0);
						try
						{
							count = (Integer)dao.getSingleRecord("validatePmInstance" , i.getInstanceId());
						}
						catch(SQLException e)
						{
							resultCode = ResultCode.ERROR_BODY_DATABASE_EXCEPTION.getCode();
							logger.error(ResultCode.ERROR_BODY_DATABASE_EXCEPTION.getDesc() , e);
							break;
						}

						if(count == 0)
						{
							resultCode = ResultCode.ERROR_BODY_PM_INSTANCE_NOTEXIST.getCode();
							logger.info(ResultCode.ERROR_BODY_PM_INSTANCE_NOTEXIST.getDesc() + i.getInstanceId());
							break;
						}
					}

					HashMap<String , String> para = new HashMap<String , String>();
					for (CIAttributeInfo it : i.getCiAttributeInfoList().getCiAttributeInfo())
					{
						para.put(it.getCiAttributeID() , it.getCiAttributeValue());
					}

					/* 实例ID赋值 */
					para.put("instance_id" , i.getInstanceId());

					/* 资源池ID & 资源池分区ID */
					if(null != info.getResourcePoolId() && !"".equals(info.getResourcePoolId()))
						para.put("rc_id" , info.getResourcePoolId());
					if(null != info.getResourcePoolPartId() && !"".equals(info.getResourcePoolPartId()))
						para.put("zone_id" , info.getResourcePoolPartId());

					/* app_id若为空或空串，则不入库 */
					if(null == para.get("app_id") || "".equals(para.get("app_id")))
						para.remove("app_id");

					/* 时间类型转换 */
					String runtime = para.get("run_time");
					try
					{
						if(null != runtime && !"".equals(runtime))
						{
							runtime = runtime.trim();
							runtime = dbFormat.format(remoteFormat.parse(runtime));
						}
					}
					catch(ParseException e1)
					{
						logger.error(ResultCode.ERROR_BODY_DATEFORMATERROR.getDesc() , e1);
						e1.printStackTrace();
						resultCode = ResultCode.ERROR_BODY_DATEFORMATERROR.getCode();
						break;
					}

					para.put("run_time" , runtime);

					if(i.getStatus() != 2)
					{

						try
						{
							dao.insertData("insertTheSinglePmRecordByData" , para);
						}
						catch(SQLException e)
						{
							resultCode = ResultCode.ERROR_BODY_DATABASE_EXCEPTION.getCode();
							logger.error("增量上报接口物理机" + para.get("instance_id") + "入库失败" , e);
							e.printStackTrace();
							break;
						}
					}
					else
					{
						try
						{
							dao.deleteData("deletePm" , para.get("instance_id"));
						}
						catch(SQLException e)
						{
							resultCode = ResultCode.ERROR_BODY_DATABASE_EXCEPTION.getCode();
							logger.error("删除物理机" + para.get("instance_id") + "入库失败" , e);
							e.printStackTrace();
							break;
						}
					}

				}
				/* 虚拟机 */
				else if("CIDC-VirtualMachine".equalsIgnoreCase(i.getCiId()))
				{
					/* 虚拟机主键校验 */
					if(null == i.getInstanceId() || "".equals(i.getInstanceId()))
					{
						resultCode = ResultCode.ERROR_BODY_VM_INSTANCEID_NONE.getCode();
						faultString += ResultCode.ERROR_BODY_VM_INSTANCEID_NONE.getDesc();
						break;
					}

					/* 校验操作类型 */
					if(i.getStatus() == 2)
					{
						Integer count = new Integer(0);
						try
						{
							count = (Integer)dao.getSingleRecord("validateVmInstance" , i.getInstanceId());
						}
						catch(SQLException e)
						{
							resultCode = ResultCode.ERROR_BODY_DATABASE_EXCEPTION.getCode();
							logger.error("验证实例异常" , e);
							break;
						}

						if(count == 0)
						{
							resultCode = ResultCode.ERROR_BODY_VM_INSTANCE_NOTEXIST.getCode();
							logger.info(ResultCode.ERROR_BODY_VM_INSTANCE_NOTEXIST.getDesc() + i.getInstanceId());
							break;
						}
					}

					HashMap<String , String> para = new HashMap<String , String>();
					for (CIAttributeInfo it : i.getCiAttributeInfoList().getCiAttributeInfo())
					{
						para.put(it.getCiAttributeID() , it.getCiAttributeValue());
					}

					para.put("instance_id" , i.getInstanceId());

					/* 资源池ID & 资源池分区ID */
					if(null != info.getResourcePoolId() && !"".equals(info.getResourcePoolId()))
						para.put("rc_id" , info.getResourcePoolId());
					if(null != info.getResourcePoolPartId() && !"".equals(info.getResourcePoolPartId()))
						para.put("zone_id" , info.getResourcePoolPartId());

					/* 时间类型转换 */
					String runtime = para.get("run_time");

					try
					{
						if(null != runtime && !"".equals(runtime))
						{
							runtime = runtime.trim();
							runtime = dbFormat.format(remoteFormat.parse(runtime));
						}
					}
					catch(ParseException e1)
					{
						logger.error(ResultCode.ERROR_BODY_DATEFORMATERROR.getDesc() , e1);
						e1.printStackTrace();
						resultCode = ResultCode.ERROR_BODY_DATEFORMATERROR.getCode();
						break;
					}

					para.put("run_time" , runtime);

					if(i.getStatus() != 2)
					{
						try
						{
							/* 查询该虚拟机是由哪个系统创建的 (运营 or BOMC) */
							Integer count = (Integer)dao.getSingleRecord("getVmOwnership" , para.get("instance_id"));

							if(null != count && !count.equals(new Integer(0)))
							{
								para.put("CREAT_FLAG" , "0");
							}
							else
							{
								para.put("CREAT_FLAG" , "1");
							}

							dao.insertData("insertTheSingleVmRecordByData" , para);

							/* 校验虚拟机的宿主物理机是否存在 */
							Integer parentCount = (Integer)dao.getSingleRecord("validatePmInstance" , para.get("parent_id"));

							if(null == parentCount || parentCount.equals(new Integer(0)))
							{
								noParentItem.add(para.get("eqp_id"));
							}
						}
						catch(SQLException e)
						{
							resultCode = ResultCode.ERROR_BODY_DATABASE_EXCEPTION.getCode();
							logger.error("增量上报接口虚拟机" + para.get("instance_id") + "入库失败" , e);
							e.printStackTrace();
							break;
						}
					}
					else
					{
						try
						{
							dao.deleteData("deleteVm" , para.get("instance_id"));
						}
						catch(SQLException e)
						{
							resultCode = ResultCode.ERROR_BODY_DATABASE_EXCEPTION.getCode();
							logger.error("删除虚拟机" + para.get("instance_id") + "入库失败" , e);
							e.printStackTrace();
							break;
						}
					}

				}
				/* 块存储 */
				else if("CIDC-EBlockStorage".equalsIgnoreCase(i.getCiId()))
				{
					/* 块存储主键校验 */
					if(null == i.getInstanceId() || "".equals(i.getInstanceId()))
					{
						resultCode = ResultCode.ERROR_BODY_EBS_INSTANCE_NONE.getCode();
						faultString += ResultCode.ERROR_BODY_EBS_INSTANCE_NONE.getDesc();
						break;
					}

					/* 校验操作类型 */
					if(i.getStatus() == 2)
					{
						Integer count = new Integer(0);
						try
						{
							count = (Integer)dao.getSingleRecord("validateEbsInstance" , i.getInstanceId());
						}
						catch(SQLException e)
						{
							ResultCode.ERROR_BODY_DATABASE_EXCEPTION.getCode();
							logger.error("验证实例异常" , e);
							break;
						}

						if(count == 0)
						{
							resultCode = ResultCode.ERROR_BODY_EBS_INSTANCE_NOTEXIST.getCode();
							logger.info("没有卷实例" + i.getInstanceId());
							break;
						}
					}

					HashMap<String , String> para = new HashMap<String , String>();
					for (CIAttributeInfo it : i.getCiAttributeInfoList().getCiAttributeInfo())
					{
						para.put(it.getCiAttributeID() , it.getCiAttributeValue());
					}

					para.put("instance_id" , i.getInstanceId());

					/* 资源池ID */
					if(null != info.getResourcePoolId() && !"".equals(info.getResourcePoolId()))
						para.put("rc_id" , info.getResourcePoolId());

					if(i.getStatus() != 2)
					{
						try
						{
							/* 查询该卷是由哪个系统创建的 (运营 or BOMC) */
							Integer count = (Integer)dao.getSingleRecord("getEbsOwnership" , para.get("instance_id"));

							if(null != count && !count.equals(new Integer(0)))
							{
								para.put("CREAT_FLAG" , "0");
							}
							else
							{
								para.put("CREAT_FLAG" , "1");
							}

							/* 判断所挂载的设备类型 */

							/**
							 * 0：小型机 1：小型机分区 2：物理机 3：虚拟机 （摘自数据库设计报告）
							 */

							String parentType = "";
							/* 判断挂载到物理机上 */
							Integer pmParent = (Integer)dao.getSingleRecord("validatePmInstance" , para.get("parent_id"));

							if(null == pmParent || pmParent.equals(new Integer(0)))
							{
								/* 判断挂载到虚拟机上 */
								Integer vmParent = (Integer)dao.getSingleRecord("validateVmInstance" , para.get("parent_id"));

								/* 如果没有查到指定的挂载设备，加入到返回队列中 */
								if(null == vmParent || vmParent.equals(new Integer(0)))
								{
									noParentItem.add(para.get("eqp_id"));
								}
								/* 挂载到了虚拟机上 */
								else
								{
									parentType = "3";
								}
							}
							/* 挂载到了物理机上 */
							else
							{
								parentType = "2";
							}

							para.put("parent_type" , parentType);

							dao.insertData("insertTheSingleEbsRecordByData" , para);
						}
						catch(SQLException e)
						{
							resultCode = ResultCode.ERROR_BODY_DATABASE_EXCEPTION.getCode();
							logger.error("增量上报接口块存储" + para.get("instance_id") + "入库失败" , e);
							e.printStackTrace();
							break;
						}
					}
					else
					{
						try
						{
							dao.deleteData("deleteEbs" , para.get("instance_id"));
						}
						catch(SQLException e)
						{
							resultCode = ResultCode.ERROR_BODY_DATABASE_EXCEPTION.getCode();
							logger.error("删除卷" + para.get("instance_id") + "入库失败" , e);
							e.printStackTrace();
							break;
						}
					}

				}
				else
				{
					logger.info("接收配置项ID为" + i.getCiId() + "的配置项，不属于业务处理范围。");
				}
			}

		}
		else
		{
			logger.info("配置项属性值列表长度为0");
		}

		logger.info("完成数据上报资源实例接口的调用");

		ReportConfigurationInfoByDataRespType r = new ReportConfigurationInfoByDataRespType();

		if("".equals(resultCode))
		{
			resultCode = "00000000";
			faultString += ResultCode.obtainItemStatusEunm(resultCode).getDesc();
		}

		r.setFaultString(faultString);

		if(!noParentItem.isEmpty())
		{
			logger.info("本次接口调用，没有\"父节点\"的设备ID(资源设备编码)有： ");
		}

		for (String i : noParentItem)
		{
			logger.info("{" + i + "}");
			faultString += "{" + i + "}";
		}

		respInfo.setResourcePoolId(info.getResourcePoolId());
		respInfo.setTransactionID(info.getTransactionID());
		respInfo.setResultCode(resultCode);
		RespBodyThreadLcoal.set(respInfo);

		return r;
	}

	/**
	 * @return the dao
	 */
	public IbatisDAO getDao()
	{
		return dao;
	}

	/**
	 * @param dao
	 *            the dao to set
	 */
	public void setDao(IbatisDAO dao)
	{
		this.dao = dao;
	}

}
