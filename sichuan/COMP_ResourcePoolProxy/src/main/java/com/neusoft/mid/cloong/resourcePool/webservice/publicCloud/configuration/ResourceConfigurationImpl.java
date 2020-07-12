package com.neusoft.mid.cloong.resourcePool.webservice.publicCloud.configuration;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.ws.Holder;

import com.idc.configuration.bean.Authorization;
import com.idc.configuration.bean.ReportConfigurationInfoByDataReqType;
import com.idc.configuration.bean.ReportConfigurationInfoByDataReqType.ConfigurationInfoList;
import com.idc.configuration.bean.ReportConfigurationInfoByDataReqType.ConfigurationInfoList.ConfigurationInfo;
import com.idc.configuration.bean.ReportConfigurationInfoByDataReqType.ConfigurationInfoList.ConfigurationInfo.CIAttributeInfoList.CIAttributeInfo;
import com.idc.configuration.bean.ReportConfigurationInfoByDataRespType;
import com.idc.configuration.bean.ReportConfigurationInfoByFileReqType;
import com.idc.configuration.bean.ReportConfigurationInfoByFileRespType;
import com.idc.configuration.bean.Response;
import com.idc.configuration.wsdl.Idc;
import com.neusoft.mid.cloong.ReqBodyThreadLcoal;
import com.neusoft.mid.cloong.RespBodyThreadLcoal;
import com.neusoft.mid.cloong.common.mybatis.IbatisDAO;
import com.neusoft.mid.cloong.info.ReqBodyInfo;
import com.neusoft.mid.cloong.info.RespBodyInfo;
import com.neusoft.mid.iamp.logger.LogService;

public class ResourceConfigurationImpl implements Idc {

    /**
     * 日志记录
     */
    private static LogService logger = LogService.getLogger(ResourceConfigurationImpl.class);

    private IbatisDAO dao;

    private final SimpleDateFormat dbFormat = new SimpleDateFormat("yyyyMMddHHmmss");

    private final SimpleDateFormat remoteFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private ReqBodyInfo info;

    private Map<String, String> resourceTypeMap;

    public static final String RESOURCE_KEY_VM = "VM";

    public static final String RESOURCE_KEY_PM = "PM";

    public static final String RESOURCE_KEY_MC = "MC";

    public static final String RESOURCE_KEY_MCPART = "MCPART";

    public static final String RESOURCE_KEY_BS = "BS";

    public static final String RESOURCE_KEY_RAID = "RAID";

    public static final String RESOURCE_KEY_SW = "SW";

    public static final String RESOURCE_KEY_SWIF = "SWIF";
    
    public static final String RESOURCE_KEY_RT = "RT";

    public static final String RESOURCE_KEY_RTIF = "RTIF";

    public static final String RESOURCE_KEY_FW = "FW";

    @Override
    public ReportConfigurationInfoByFileRespType reportConfigurationInfoByFile(
            ReportConfigurationInfoByFileReqType reportConfigurationInfoByFileReqInput,
            Authorization authorization, Holder<Response> response) {
        System.out.println("文件上报");
        return null;
    }

    @Override
    public ReportConfigurationInfoByDataRespType reportConfigurationInfoByData(
            ReportConfigurationInfoByDataReqType reportConfigurationInfoByDataReqInput,
            Authorization authorization, Holder<Response> response) {

        logger.info("进入数据上报资源实例接口");

        info = ReqBodyThreadLcoal.get();

        // 程序处理结果
        ResultCode resultCode = ResultCode.SUCCESS;
        // 定义没有父节点的配置型列表变量
        ArrayList<String> noParentItem = new ArrayList<String>();

        ConfigurationInfoList confList = reportConfigurationInfoByDataReqInput
                .getConfigurationInfoList();
        List<ConfigurationInfo> reportConfigurationList = new ArrayList<ConfigurationInfo>();
        if (confList != null && confList.getConfigurationInfo() != null) {
            reportConfigurationList = confList.getConfigurationInfo();
        } else {
            logger.info("配置项属性值列表长度为0");
        }

        /* 按最后更新时间排序 */
        sortConfigurationList(reportConfigurationList);

        // TODO 第三迭代需要将下面循环内代码拆分为几个不同的类
        for (ConfigurationInfo reportConfiguration : reportConfigurationList) {
            /* X86物理机 */
            if (resourceTypeMap.get(RESOURCE_KEY_PM)
                    .equalsIgnoreCase(reportConfiguration.getCiId())) {
                resultCode = processPM(reportConfiguration, noParentItem);
                if (!ResultCode.SUCCESS.equals(resultCode)) {
                    break;
                }
            }/* 虚拟机 */
            else if (resourceTypeMap.get(RESOURCE_KEY_VM).equalsIgnoreCase(
                    reportConfiguration.getCiId())) {
                resultCode = processVM(reportConfiguration, noParentItem);
                if (!ResultCode.SUCCESS.equals(resultCode)) {
                    break;
                }
            }/* 块存储 */
            else if (resourceTypeMap.get(RESOURCE_KEY_BS).equalsIgnoreCase(
                    reportConfiguration.getCiId())) {
                resultCode = processBS(reportConfiguration, noParentItem);
                if (!ResultCode.SUCCESS.equals(resultCode)) {
                    break;
                }
            }/* 小型机 */
            else if (resourceTypeMap.get(RESOURCE_KEY_MC).equalsIgnoreCase(
                    reportConfiguration.getCiId())) {
                resultCode = processMC(reportConfiguration, noParentItem);
                if (!ResultCode.SUCCESS.equals(resultCode)) {
                    break;
                }
            }/* 小型机分区 */
            else if (resourceTypeMap.get(RESOURCE_KEY_MCPART).equalsIgnoreCase(
                    reportConfiguration.getCiId())) {
                resultCode = processMCPart(reportConfiguration, noParentItem);
                if (!ResultCode.SUCCESS.equals(resultCode)) {
                    break;
                }
            } /* 存储阵列 */
            else if (resourceTypeMap.get(RESOURCE_KEY_RAID).equalsIgnoreCase(
                    reportConfiguration.getCiId())) {
                resultCode = processRaid(reportConfiguration, noParentItem);
                if (!ResultCode.SUCCESS.equals(resultCode)) {
                    break;
                }
            } /* 交换机 */
            else if (resourceTypeMap.get(RESOURCE_KEY_SW).equalsIgnoreCase(
                    reportConfiguration.getCiId())) {
                resultCode = processSW(reportConfiguration, noParentItem);
                if (!ResultCode.SUCCESS.equals(resultCode)) {
                    break;
                }
            } /* 交换机端口 */
            else if (resourceTypeMap.get(RESOURCE_KEY_SWIF).equalsIgnoreCase(
                    reportConfiguration.getCiId())) {
                resultCode = processSWIF(reportConfiguration, noParentItem);
                if (!ResultCode.SUCCESS.equals(resultCode)) {
                    break;
                }
            }/* 路由器 */
            else if (resourceTypeMap.get(RESOURCE_KEY_RT).equalsIgnoreCase(
                    reportConfiguration.getCiId())) {
                resultCode = processRT(reportConfiguration, noParentItem);
                if (!ResultCode.SUCCESS.equals(resultCode)) {
                    break;
                }
            } /* 路由器端口 */
            else if (resourceTypeMap.get(RESOURCE_KEY_RTIF).equalsIgnoreCase(
                    reportConfiguration.getCiId())) {
                resultCode = processRTIF(reportConfiguration, noParentItem);
                if (!ResultCode.SUCCESS.equals(resultCode)) {
                    break;
                }
            }/* 防火墙 */
            else if (resourceTypeMap.get(RESOURCE_KEY_FW).equalsIgnoreCase(
                    reportConfiguration.getCiId())) {
                resultCode = processFW(reportConfiguration, noParentItem);
                if (!ResultCode.SUCCESS.equals(resultCode)) {
                    break;
                }
            } else {
                logger.info("接收配置项ID为" + reportConfiguration.getCiId() + "的配置项，不属于业务处理范围。");
            }
        }

        logger.info("完成数据上报资源实例接口的调用");

        String faultString = resultCode.getDesc();
        if (!noParentItem.isEmpty()) {
            logger.info("本次接口调用，没有\"父节点\"的设备ID(资源设备编码)有： ");
            faultString += "  本次接口调用，没有\"父节点\"的设备ID(资源设备编码)有：";
        }
        for (String i : noParentItem) {
            logger.info("{" + i + "}");
            faultString += "{" + i + "}";
        }

        /* 返回的报文头 */
        RespBodyInfo respInfo = new RespBodyInfo();
        respInfo.setResourcePoolId(info.getResourcePoolId());
        respInfo.setTransactionID(info.getTransactionID());
        respInfo.setResultCode(resultCode.getCode());
        RespBodyThreadLcoal.set(respInfo);

        ReportConfigurationInfoByDataRespType r = new ReportConfigurationInfoByDataRespType();
        r.setFaultString(faultString);

        return r;
    }

    /**
     * 按照时间重新排序配置项信息
     * @author fengj<br>
     *         2015年2月15日 上午8:56:07
     * @param reportConfigurationList 要排序的配置项信息
     */
    private void sortConfigurationList(List<ConfigurationInfo> reportConfigurationList) {
        Collections.sort(reportConfigurationList, new Comparator<ConfigurationInfo>() {

            @Override
            public int compare(ConfigurationInfo o1, ConfigurationInfo o2) {
                Date date1 = new Date();
                Date date2 = new Date();
                try {
                    date1 = remoteFormat.parse(o1.getLastUpateTime());

                    date2 = remoteFormat.parse(o2.getLastUpateTime());
                } catch (ParseException e) {
                    logger.error(ResultCode.ERROR_BODY_DATEFORMATERROR.getDesc(), e);
                    e.printStackTrace();
                }

                if (date1.before(date2)) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });
    }

    /**
     * 处理小型机分区的配置项信息
     * @author fengj<br>
     *         2015年2月15日 上午8:31:08
     * @param configurationInfo 要处理的小型机分区配置项信息
     * @param resultCode 处理结果
     * @param noParentItem 没有父节点的配置项
     * @return 处理是否成功<br>
     *         true——成功 false——失败
     */
    private ResultCode processMCPart(ConfigurationInfo configurationInfo,
            ArrayList<String> noParentItem) {
        /* 虚拟机主键校验 */
        if (null == configurationInfo.getInstanceId()
                || "".equals(configurationInfo.getInstanceId())) {
            return ResultCode.ERROR_BODY_MCPAR_INSTANCE_NONE;
        }

        /* 校验操作类型 */
        if (configurationInfo.getStatus() == 2) {
            Integer count = new Integer(0);
            try {
                count = (Integer) dao.getSingleRecord("validateMiniPmPartInstance",
                        configurationInfo.getInstanceId());
            } catch (SQLException e) {
                logger.error("验证实例异常", e);
                return ResultCode.ERROR_BODY_DATABASE_EXCEPTION;
            }

            if (count == 0) {
                logger.info(ResultCode.ERROR_BODY_MCPART_INSTANCE_NOTEXIST.getDesc()
                        + configurationInfo.getInstanceId());
                return ResultCode.ERROR_BODY_MCPART_INSTANCE_NOTEXIST;
            }
        }

        HashMap<String, String> para = new HashMap<String, String>();
        for (CIAttributeInfo it : configurationInfo.getCiAttributeInfoList().getCiAttributeInfo()) {
            para.put(it.getCiAttributeID(), it.getCiAttributeValue());
        }

        para.put("instance_id", configurationInfo.getInstanceId());

        /* 资源池ID & 资源池分区ID */
        if (null != info.getResourcePoolId() && !"".equals(info.getResourcePoolId())) {
            para.put("rc_id", info.getResourcePoolId());
        }

        if (null != info.getResourcePoolPartId() && !"".equals(info.getResourcePoolPartId())) {
            para.put("zone_id", info.getResourcePoolPartId());
        }

        /* app_id若为空或空串，则不入库 */
        if (null == para.get("app_id") || "".equals(para.get("app_id"))) {
            para.remove("app_id");
        }

        ResultCode resultCode = transformRuntimeFormat(para);
        if (!ResultCode.SUCCESS.equals(resultCode)) {
            return resultCode;
        }

        if (configurationInfo.getStatus() != 2) {
            try {

                dao.insertData("insertTheSingleMiniPmPartRecordByData", para);

                /* 校验小型机分区的宿主小型机机是否存在 */
                Integer parentCount = (Integer) dao.getSingleRecord("validateMiniPmInstance",
                        para.get("parent_id"));

                if (null == parentCount || parentCount.equals(new Integer(0))) {
                    noParentItem.add(para.get("eqp_id"));
                }
            } catch (SQLException e) {
                logger.error("增量上报接口小型机分区" + para.get("instance_id") + "入库失败", e);
                e.printStackTrace();
                return ResultCode.ERROR_BODY_DATABASE_EXCEPTION;
            }
        } else {
            try {
                dao.deleteData("deleteMiniPmPart", para.get("instance_id"));
            } catch (SQLException e) {
                logger.error("删除小型机分区" + para.get("instance_id") + "入库失败", e);
                e.printStackTrace();
                return ResultCode.ERROR_BODY_DATABASE_EXCEPTION;
            }
        }

        return ResultCode.SUCCESS;
    }

    /**
     * 处理小型机的配置项信息
     * @author fengj<br>
     *         2015年2月15日 上午8:31:08
     * @param configurationInfo 要处理的小型机配置项信息
     * @param resultCode 处理结果
     * @param noParentItem 没有父节点的配置项
     * @return 处理结果
     */
    private ResultCode processMC(ConfigurationInfo configurationInfo, ArrayList<String> noParentItem) {

        /* 虚拟机主键校验是否为空 */
        if (null == configurationInfo.getInstanceId()
                || "".equals(configurationInfo.getInstanceId())) {
            return ResultCode.ERROR_BODY_MC_INSTANCE_NONE;
        }

        /* 更新配置项操作校验 */
        if (configurationInfo.getStatus() == 2) {
            Integer count = new Integer(0);
            try {
                count = (Integer) dao.getSingleRecord("validateMiniPmInstance",
                        configurationInfo.getInstanceId());
            } catch (SQLException e) {
                logger.error("验证实例异常", e);
                return ResultCode.ERROR_BODY_DATABASE_EXCEPTION;
            }

            if (count == 0) {
                logger.info(ResultCode.ERROR_BODY_MC_INSTANCE_NOTEXIST.getDesc()
                        + configurationInfo.getInstanceId());
                return ResultCode.ERROR_BODY_MC_INSTANCE_NOTEXIST;
            }
        }

        HashMap<String, String> para = new HashMap<String, String>();
        for (CIAttributeInfo it : configurationInfo.getCiAttributeInfoList().getCiAttributeInfo()) {
            para.put(it.getCiAttributeID(), it.getCiAttributeValue());
        }

        para.put("instance_id", configurationInfo.getInstanceId());

        /* 资源池ID & 资源池分区ID */
        if (null != info.getResourcePoolId() && !"".equals(info.getResourcePoolId())) {
            para.put("rc_id", info.getResourcePoolId());
        }

        if (null != info.getResourcePoolPartId() && !"".equals(info.getResourcePoolPartId())) {
            para.put("zone_id", info.getResourcePoolPartId());
        }

        /* app_id若为空或空串，则不入库 */
        if (null == para.get("app_id") || "".equals(para.get("app_id"))) {
            para.remove("app_id");
        }

        ResultCode resultCode = transformRuntimeFormat(para);
        if (!ResultCode.SUCCESS.equals(resultCode)) {
            return resultCode;
        }

        /* 如果不是删除状态 */
        if (configurationInfo.getStatus() != 2) {
            try {

                dao.insertData("insertTheSingleMiniPmRecordByData", para);

            } catch (SQLException e) {
                logger.error("增量上报接口小型机" + para.get("instance_id") + "入库失败", e);
                e.printStackTrace();
                return ResultCode.ERROR_BODY_DATABASE_EXCEPTION;
            }
        } else {/* 是删除状态 */
            try {
                dao.deleteData("deleteMiniPm", para.get("instance_id"));
            } catch (SQLException e) {
                logger.error("删除小型机" + para.get("instance_id") + "入库失败", e);
                e.printStackTrace();
                return ResultCode.ERROR_BODY_DATABASE_EXCEPTION;
            }
        }

        return ResultCode.SUCCESS;
    }

    /**
     * 转换配置项中的运行时间
     * @author fengj<br>
     *         2015年2月15日 上午9:11:06
     * @param para 配置项参数
     */
    private ResultCode transformRuntimeFormat(HashMap<String, String> para) {
        /* 时间类型转换 */
        String runtime = para.get("run_time");
        try {
            if (null != runtime && !"".equals(runtime)) {
                runtime = runtime.trim();
                runtime = dbFormat.format(remoteFormat.parse(runtime));
            }
        } catch (ParseException e1) {
            logger.error(ResultCode.ERROR_BODY_DATEFORMATERROR.getDesc(), e1);
            e1.printStackTrace();
            return ResultCode.ERROR_BODY_DATEFORMATERROR;
        }
        para.put("run_time", runtime);
        return ResultCode.SUCCESS;
    }

    /**
     * 处理物理机的配置项信息
     * @author fengj<br>
     *         2015年2月15日 上午8:31:08
     * @param configurationInfo 要处理的物理机配置项信息
     * @param resultCode 处理结果
     * @param noParentItem 没有父节点的配置项
     * @return 处理是否成功<br>
     *         true——成功 false——失败
     */
    private ResultCode processBS(ConfigurationInfo configurationInfo, ArrayList<String> noParentItem) {
        /* 块存储主键校验 */
        if (null == configurationInfo.getInstanceId()
                || "".equals(configurationInfo.getInstanceId())) {
            return ResultCode.ERROR_BODY_EBS_INSTANCE_NONE;
        }

        /* 校验操作类型 */
        if (configurationInfo.getStatus() == 2) {
            Integer count = new Integer(0);
            try {
                count = (Integer) dao.getSingleRecord("validateEbsInstance",
                        configurationInfo.getInstanceId());
            } catch (SQLException e) {
                logger.error("验证实例异常", e);
                return ResultCode.ERROR_BODY_DATABASE_EXCEPTION;
            }

            if (count == 0) {
                logger.info("没有卷实例" + configurationInfo.getInstanceId());
                return ResultCode.ERROR_BODY_EBS_INSTANCE_NOTEXIST;
            }
        }

        HashMap<String, String> para = new HashMap<String, String>();
        for (CIAttributeInfo it : configurationInfo.getCiAttributeInfoList().getCiAttributeInfo()) {
            para.put(it.getCiAttributeID(), it.getCiAttributeValue());
        }

        para.put("instance_id", configurationInfo.getInstanceId());

        /* 资源池ID */
        if (null != info.getResourcePoolId() && !"".equals(info.getResourcePoolId())) {
            para.put("rc_id", info.getResourcePoolId());
        }

        if (configurationInfo.getStatus() != 2) {
            try {
                /* 查询该卷是由哪个系统创建的 (运营 or BOMC) */
                Integer count = (Integer) dao.getSingleRecord("getEbsOwnership",
                        para.get("instance_id"));

                if (null != count && !count.equals(new Integer(0))) {
                    para.put("CREAT_FLAG", "0");
                } else {
                    para.put("CREAT_FLAG", "1");
                }

                /* 判断所挂载的设备类型 */

                /**
                 * 0：小型机 1：小型机分区 2：物理机 3：虚拟机 （摘自数据库设计报告）
                 */

                String parentType = "";
                /* 判断挂载到物理机上 */
                Integer pmParent = (Integer) dao.getSingleRecord("validatePmInstance",
                        para.get("parent_id"));

                if (null == pmParent || pmParent.equals(new Integer(0))) {
                    /* 判断挂载到虚拟机上 */
                    Integer vmParent = (Integer) dao.getSingleRecord("validateVmInstance",
                            para.get("parent_id"));

                    /* 如果没有查到指定的挂载设备，加入到返回队列中 */
                    if (null == vmParent || vmParent.equals(new Integer(0))) {
                        noParentItem.add(para.get("eqp_id"));
                    }
                    /* 挂载到了虚拟机上 */
                    else {
                        parentType = "3";
                    }
                }
                /* 挂载到了物理机上 */
                else {
                    parentType = "2";
                }

                para.put("parent_type", parentType);

                dao.insertData("insertTheSingleEbsRecordByData", para);
            } catch (SQLException e) {
                logger.error("增量上报接口块存储" + para.get("instance_id") + "入库失败", e);
                e.printStackTrace();
                return ResultCode.ERROR_BODY_DATABASE_EXCEPTION;
            }
        } else {
            try {
                dao.deleteData("deleteEbs", para.get("instance_id"));
            } catch (SQLException e) {
                logger.error("删除卷" + para.get("instance_id") + "入库失败", e);
                e.printStackTrace();
                return ResultCode.ERROR_BODY_DATABASE_EXCEPTION;
            }
        }

        return ResultCode.SUCCESS;

    }

    /**
     * 处理虚拟机的配置项信息
     * @author fengj<br>
     *         2015年2月15日 上午8:31:08
     * @param configurationInfo 要处理的虚拟机配置项信息
     * @param resultCode 处理结果
     * @param noParentItem 没有父节点的配置项
     * @return 处理是否成功<br>
     *         true——成功 false——失败
     */
    private ResultCode processVM(ConfigurationInfo configurationInfo, ArrayList<String> noParentItem) {
        /* 虚拟机主键校验 */
        if (null == configurationInfo.getInstanceId()
                || "".equals(configurationInfo.getInstanceId())) {
            return ResultCode.ERROR_BODY_VM_INSTANCEID_NONE;
        }

        /* 校验操作类型 */
        if (configurationInfo.getStatus() == 2) {
            Integer count = new Integer(0);
            try {
                count = (Integer) dao.getSingleRecord("validateVmInstance",
                        configurationInfo.getInstanceId());
            } catch (SQLException e) {
                logger.error("验证实例异常", e);
                return ResultCode.ERROR_BODY_DATABASE_EXCEPTION;
            }

            if (count == 0) {
                logger.info(ResultCode.ERROR_BODY_VM_INSTANCE_NOTEXIST.getDesc()
                        + configurationInfo.getInstanceId());
                return ResultCode.ERROR_BODY_VM_INSTANCE_NOTEXIST;
            }
        }

        HashMap<String, String> para = new HashMap<String, String>();
        for (CIAttributeInfo it : configurationInfo.getCiAttributeInfoList().getCiAttributeInfo()) {
            para.put(it.getCiAttributeID(), it.getCiAttributeValue());
        }

        para.put("instance_id", configurationInfo.getInstanceId());

        /* 资源池ID & 资源池分区ID */
        if (null != info.getResourcePoolId() && !"".equals(info.getResourcePoolId())) {
            para.put("rc_id", info.getResourcePoolId());
        }

        if (null != info.getResourcePoolPartId() && !"".equals(info.getResourcePoolPartId())) {
            para.put("zone_id", info.getResourcePoolPartId());
        }

        ResultCode resultCode = transformRuntimeFormat(para);
        if (!ResultCode.SUCCESS.equals(resultCode)) {
            return resultCode;
        }

        if (configurationInfo.getStatus() != 2) {
            try {
                /* 查询该虚拟机是由哪个系统创建的 (运营 or BOMC) */
                Integer count = (Integer) dao.getSingleRecord("getVmOwnership",
                        para.get("instance_id"));

                if (null != count && !count.equals(new Integer(0))) {
                    para.put("CREAT_FLAG", "0");
                } else {
                    para.put("CREAT_FLAG", "1");
                }

                dao.insertData("insertTheSingleVmRecordByData", para);

                /* 校验虚拟机的宿主物理机是否存在 */
                Integer parentCount = (Integer) dao.getSingleRecord("validatePmEqp",
                        para.get("parent_id"));

                if (null == parentCount || parentCount.equals(new Integer(0))) {
                    noParentItem.add(para.get("eqp_id"));
                }
            } catch (SQLException e) {
                logger.error("增量上报接口虚拟机" + para.get("instance_id") + "入库失败", e);
                e.printStackTrace();
                return ResultCode.ERROR_BODY_DATABASE_EXCEPTION;
            }
        } else {
            try {
                dao.deleteData("deleteVm", para.get("instance_id"));
            } catch (SQLException e) {
                logger.error("删除虚拟机" + para.get("instance_id") + "入库失败", e);
                e.printStackTrace();
                return ResultCode.ERROR_BODY_DATABASE_EXCEPTION;
            }
        }

        return ResultCode.SUCCESS;
    }

    /**
     * 处理块存储的配置项信息
     * @author fengj<br>
     *         2015年2月15日 上午8:31:08
     * @param configurationInfo 要处理的块存储配置项信息
     * @param resultCode 处理结果
     * @param noParentItem 没有父节点的配置项
     * @return 处理是否成功
     */
    private ResultCode processPM(ConfigurationInfo configurationInfo, ArrayList<String> noParentItem) {

        HashMap<String, String> para = new HashMap<String, String>();
        for (CIAttributeInfo it : configurationInfo.getCiAttributeInfoList().getCiAttributeInfo()) {
            para.put(it.getCiAttributeID(), it.getCiAttributeValue());
        }

        String eqpId = para.get("eqp_id");

        /* 物理机主键校验 */
        if (null == eqpId || "".equals(eqpId)) {
            return ResultCode.ERROR_BODY_PM_EQPID_NONE;
        }

        /* 校验操作类型 如果是删除，要先校验设备是否存在 */
        if (configurationInfo.getStatus() == 2) {
            Integer count = new Integer(0);
            try {
                count = (Integer) dao.getSingleRecord("validatePmEqp", eqpId);
            } catch (SQLException e) {
                logger.error(ResultCode.ERROR_BODY_DATABASE_EXCEPTION.getDesc(), e);
                return ResultCode.ERROR_BODY_DATABASE_EXCEPTION;
            }

            if (count == 0) {
                logger.info(ResultCode.ERROR_BODY_PM_INSTANCE_NOTEXIST.getDesc()
                        + configurationInfo.getInstanceId());
                return ResultCode.ERROR_BODY_PM_INSTANCE_NOTEXIST;
            }
        }

        /* 实例ID赋值 */
        para.put("instance_id", configurationInfo.getInstanceId());

        /* 资源池ID & 资源池分区ID */
        if (null != info.getResourcePoolId() && !"".equals(info.getResourcePoolId())) {
            para.put("rc_id", info.getResourcePoolId());
        }

        if (null != info.getResourcePoolPartId() && !"".equals(info.getResourcePoolPartId())) {
            para.put("zone_id", info.getResourcePoolPartId());
        }

        /* app_id若为空或空串，则不入库 */
        if (null == para.get("app_id") || "".equals(para.get("app_id"))) {
            para.remove("app_id");
        }

        ResultCode resultCode = transformRuntimeFormat(para);
        if (!ResultCode.SUCCESS.equals(resultCode)) {
            return resultCode;
        }

        if (configurationInfo.getStatus() != 2) {

            try {
                dao.insertData("insertTheSinglePmRecordByData", para);
            } catch (SQLException e) {
                logger.error("增量上报接口物理机" + eqpId + "入库失败", e);
                e.printStackTrace();
                return ResultCode.ERROR_BODY_DATABASE_EXCEPTION;
            }
        } else {
            try {
                dao.deleteData("deletePm", eqpId);
            } catch (SQLException e) {
                logger.error("删除物理机" + eqpId + "入库失败", e);
                e.printStackTrace();
                return ResultCode.ERROR_BODY_DATABASE_EXCEPTION;
            }
        }

        return ResultCode.SUCCESS;
    }
    
    /**
     * 处理存储阵列的配置项信息
     * @author x_wang<br>
     *         2015年11月5日 下午5:58:08
     * @param configurationInfo 要处理的存储阵列配置项信息
     * @param resultCode 处理结果
     * @param noParentItem 没有父节点的配置项
     * @return 处理是否成功<br>
     *         true——成功 false——失败
     */
    private ResultCode processRaid(ConfigurationInfo configurationInfo, ArrayList<String> noParentItem) {
        /* 存储阵列主键校验 */
        if (null == configurationInfo.getInstanceId()
                || "".equals(configurationInfo.getInstanceId())) {
            return ResultCode.ERROR_BODY_RAID_INSTANCEID_NONE;
        }

        /* 校验操作类型 如果是删除，要先校验设备是否存在 */
        if (configurationInfo.getStatus() == 2) {
            Integer count = new Integer(0);
            try {
                count = (Integer) dao.getSingleRecord("validateRaidInstance",
                        configurationInfo.getInstanceId());
            } catch (SQLException e) {
                logger.error("验证实例异常", e);
                return ResultCode.ERROR_BODY_DATABASE_EXCEPTION;
            }

            if (count == 0) {
                logger.info(ResultCode.ERROR_BODY_RAID_INSTANCE_NOTEXIST.getDesc()
                        + configurationInfo.getInstanceId());
                return ResultCode.ERROR_BODY_RAID_INSTANCE_NOTEXIST;
            }
        }

        HashMap<String, String> para = new HashMap<String, String>();
        for (CIAttributeInfo it : configurationInfo.getCiAttributeInfoList().getCiAttributeInfo()) {
            para.put(it.getCiAttributeID(), it.getCiAttributeValue());
        }

        para.put("instance_id", configurationInfo.getInstanceId());

        /* 资源池ID */
        if (null != info.getResourcePoolId() && !"".equals(info.getResourcePoolId())) {
            para.put("rc_id", info.getResourcePoolId());
        }

        ResultCode resultCode = transformRuntimeFormat(para);
        if (!ResultCode.SUCCESS.equals(resultCode)) {
            return resultCode;
        }

        if (configurationInfo.getStatus() != 2) {
            try {
                dao.insertData("insertTheSingleRaidRecordByData", para);
            } catch (SQLException e) {
                logger.error("增量上报接口存储阵列" + para.get("instance_id") + "入库失败", e);
                e.printStackTrace();
                return ResultCode.ERROR_BODY_DATABASE_EXCEPTION;
            }
        } else {
            try {
                dao.deleteData("deleteRaid", para.get("instance_id"));
            } catch (SQLException e) {
                logger.error("删除存储阵列" + para.get("instance_id") + "入库失败", e);
                e.printStackTrace();
                return ResultCode.ERROR_BODY_DATABASE_EXCEPTION;
            }
        }

        return ResultCode.SUCCESS;
    }

    /**
     * 处理交换机的配置项信息
     * @author x_wang<br>
     *         2015年11月5日 下午6:58:08
     * @param configurationInfo 要处理的交换机配置项信息
     * @param resultCode 处理结果
     * @param noParentItem 没有父节点的配置项
     * @return 处理是否成功<br>
     *         true——成功 false——失败
     */
    private ResultCode processSW(ConfigurationInfo configurationInfo, ArrayList<String> noParentItem) {
        /* 交换机主键校验 */
        if (null == configurationInfo.getInstanceId()
                || "".equals(configurationInfo.getInstanceId())) {
            return ResultCode.ERROR_BODY_SW_INSTANCEID_NONE;
        }

        /* 校验操作类型 如果是删除，要先校验设备是否存在 */
        if (configurationInfo.getStatus() == 2) {
            Integer count = new Integer(0);
            try {
                count = (Integer) dao.getSingleRecord("validateSwInstance",
                        configurationInfo.getInstanceId());
            } catch (SQLException e) {
                logger.error("验证实例异常", e);
                return ResultCode.ERROR_BODY_DATABASE_EXCEPTION;
            }

            if (count == 0) {
                logger.info(ResultCode.ERROR_BODY_RAID_INSTANCE_NOTEXIST.getDesc()
                        + configurationInfo.getInstanceId());
                return ResultCode.ERROR_BODY_SW_INSTANCE_NOTEXIST;
            }
        }

        HashMap<String, String> para = new HashMap<String, String>();
        for (CIAttributeInfo it : configurationInfo.getCiAttributeInfoList().getCiAttributeInfo()) {
            para.put(it.getCiAttributeID(), it.getCiAttributeValue());
        }

        para.put("instance_id", configurationInfo.getInstanceId());

        /* 资源池ID */
        if (null != info.getResourcePoolId() && !"".equals(info.getResourcePoolId())) {
            para.put("rc_id", info.getResourcePoolId());
        }

        ResultCode resultCode = transformRuntimeFormat(para);
        if (!ResultCode.SUCCESS.equals(resultCode)) {
            return resultCode;
        }

        if (configurationInfo.getStatus() != 2) {
            try {
                dao.insertData("insertTheSingleSwRecordByData", para);
            } catch (SQLException e) {
                logger.error("增量上报接口交换机" + para.get("instance_id") + "入库失败", e);
                e.printStackTrace();
                return ResultCode.ERROR_BODY_DATABASE_EXCEPTION;
            }
        } else {
            try {
                dao.deleteData("deleteSw", para.get("instance_id"));
            } catch (SQLException e) {
                logger.error("删除交换机" + para.get("instance_id") + "入库失败", e);
                e.printStackTrace();
                return ResultCode.ERROR_BODY_DATABASE_EXCEPTION;
            }
        }

        return ResultCode.SUCCESS;
    }
    
    /**
     * 处理交换机端口的配置项信息
     * @author x_wang<br>
     *         2015年11月5日 下午6:58:08
     * @param configurationInfo 要处理的交换机端口配置项信息
     * @param resultCode 处理结果
     * @param noParentItem 没有父节点的配置项
     * @return 处理是否成功<br>
     *         true——成功 false——失败
     */
    private ResultCode processSWIF(ConfigurationInfo configurationInfo, ArrayList<String> noParentItem) {
        /* 交换机端口主键校验 */
        if (null == configurationInfo.getInstanceId()
                || "".equals(configurationInfo.getInstanceId())) {
            return ResultCode.ERROR_BODY_SWIF_INSTANCEID_NONE;
        }

        /* 校验操作类型 如果是删除，要先校验设备是否存在 */
        if (configurationInfo.getStatus() == 2) {
            Integer count = new Integer(0);
            try {
                count = (Integer) dao.getSingleRecord("validateSwIfInstance",
                        configurationInfo.getInstanceId());
            } catch (SQLException e) {
                logger.error("验证实例异常", e);
                return ResultCode.ERROR_BODY_DATABASE_EXCEPTION;
            }

            if (count == 0) {
                logger.info(ResultCode.ERROR_BODY_RAID_INSTANCE_NOTEXIST.getDesc()
                        + configurationInfo.getInstanceId());
                return ResultCode.ERROR_BODY_SWIF_INSTANCE_NOTEXIST;
            }
        }

        HashMap<String, String> para = new HashMap<String, String>();
        for (CIAttributeInfo it : configurationInfo.getCiAttributeInfoList().getCiAttributeInfo()) {
            para.put(it.getCiAttributeID(), it.getCiAttributeValue());
        }

        para.put("instance_id", configurationInfo.getInstanceId());

        /* 资源池ID */
        if (null != info.getResourcePoolId() && !"".equals(info.getResourcePoolId())) {
            para.put("rc_id", info.getResourcePoolId());
        }

        ResultCode resultCode = transformRuntimeFormat(para);
        if (!ResultCode.SUCCESS.equals(resultCode)) {
            return resultCode;
        }

        if (configurationInfo.getStatus() != 2) {
            try {
                dao.insertData("insertTheSingleSwIfRecordByData", para);
                
                /* 校验交换机端口的宿主交换机是否存在 */
                Integer parentCount = (Integer) dao.getSingleRecord("validateSwInstance",
                        para.get("parent_id"));

                if (null == parentCount || parentCount.equals(new Integer(0))) {
                    noParentItem.add(para.get("eqp_id"));
                }
                
            } catch (SQLException e) {
                logger.error("增量上报接口交换机端口" + para.get("instance_id") + "入库失败", e);
                e.printStackTrace();
                return ResultCode.ERROR_BODY_DATABASE_EXCEPTION;
            }
        } else {
            try {
                dao.deleteData("deleteSwIf", para.get("instance_id"));
            } catch (SQLException e) {
                logger.error("删除交换机端口" + para.get("instance_id") + "入库失败", e);
                e.printStackTrace();
                return ResultCode.ERROR_BODY_DATABASE_EXCEPTION;
            }
        }

        return ResultCode.SUCCESS;
    }

    
    /**
     * 处理路由器的配置项信息
     * @author niul<br>
     *         2015年11月6日 上午9:58:08
     * @param configurationInfo 要处理的路由器配置项信息
     * @param resultCode 处理结果
     * @param noParentItem 没有父节点的配置项
     * @return 处理是否成功<br>
     *         true——成功 false——失败
     */
    private ResultCode processRT(ConfigurationInfo configurationInfo, ArrayList<String> noParentItem) {
        /* 路由器主键校验 */
        if (null == configurationInfo.getInstanceId()
                || "".equals(configurationInfo.getInstanceId())) {
            return ResultCode.ERROR_BODY_RT_INSTANCEID_NONE;
        }

        /* 校验操作类型 如果是删除，要先校验设备是否存在 */
        if (configurationInfo.getStatus() == 2) {
            Integer count = new Integer(0);
            try {
                count = (Integer) dao.getSingleRecord("validateRtInstance",
                        configurationInfo.getInstanceId());
            } catch (SQLException e) {
                logger.error("验证实例异常", e);
                return ResultCode.ERROR_BODY_DATABASE_EXCEPTION;
            }

            if (count == 0) {
                logger.info(ResultCode.ERROR_BODY_RT_INSTANCE_NOTEXIST.getDesc()
                        + configurationInfo.getInstanceId());
                return ResultCode.ERROR_BODY_RT_INSTANCE_NOTEXIST;
            }
        }

        HashMap<String, String> para = new HashMap<String, String>();
        for (CIAttributeInfo it : configurationInfo.getCiAttributeInfoList().getCiAttributeInfo()) {
            para.put(it.getCiAttributeID(), it.getCiAttributeValue());
        }

        para.put("instance_id", configurationInfo.getInstanceId());

        /* 资源池ID */
        if (null != info.getResourcePoolId() && !"".equals(info.getResourcePoolId())) {
            para.put("rc_id", info.getResourcePoolId());
        }

        ResultCode resultCode = transformRuntimeFormat(para);
        if (!ResultCode.SUCCESS.equals(resultCode)) {
            return resultCode;
        }

        if (configurationInfo.getStatus() != 2) {
            try {
                dao.insertData("insertTheSingleRtRecordByData", para);
            } catch (SQLException e) {
                logger.error("增量上报接口路由器" + para.get("instance_id") + "入库失败", e);
                e.printStackTrace();
                return ResultCode.ERROR_BODY_DATABASE_EXCEPTION;
            }
        } else {
            try {
                dao.deleteData("deleteRt", para.get("instance_id"));
            } catch (SQLException e) {
                logger.error("删除路由器" + para.get("instance_id") + "入库失败", e);
                e.printStackTrace();
                return ResultCode.ERROR_BODY_DATABASE_EXCEPTION;
            }
        }

        return ResultCode.SUCCESS;
    }
    
    /**
     * 处理路由器端口的配置项信息
     * @author niul<br>
     *         2015年11月6日 上午9:58:08
     * @param configurationInfo 要处理的路由器端口配置项信息
     * @param resultCode 处理结果
     * @param noParentItem 没有父节点的配置项
     * @return 处理是否成功<br>
     *         true——成功 false——失败
     */
    private ResultCode processRTIF(ConfigurationInfo configurationInfo, ArrayList<String> noParentItem) {
        /* 路由器端口主键校验 */
        if (null == configurationInfo.getInstanceId()
                || "".equals(configurationInfo.getInstanceId())) {
            return ResultCode.ERROR_BODY_RTIF_INSTANCEID_NONE;
        }

        /* 校验操作类型 如果是删除，要先校验设备是否存在 */
        if (configurationInfo.getStatus() == 2) {
            Integer count = new Integer(0);
            try {
                count = (Integer) dao.getSingleRecord("validateRtIfInstance",
                        configurationInfo.getInstanceId());
            } catch (SQLException e) {
                logger.error("验证实例异常", e);
                return ResultCode.ERROR_BODY_DATABASE_EXCEPTION;
            }

            if (count == 0) {
                logger.info(ResultCode.ERROR_BODY_RTIF_INSTANCE_NOTEXIST.getDesc()
                        + configurationInfo.getInstanceId());
                return ResultCode.ERROR_BODY_RTIF_INSTANCE_NOTEXIST;
            }
        }

        HashMap<String, String> para = new HashMap<String, String>();
        for (CIAttributeInfo it : configurationInfo.getCiAttributeInfoList().getCiAttributeInfo()) {
            para.put(it.getCiAttributeID(), it.getCiAttributeValue());
        }

        para.put("instance_id", configurationInfo.getInstanceId());

        /* 资源池ID */
        if (null != info.getResourcePoolId() && !"".equals(info.getResourcePoolId())) {
            para.put("rc_id", info.getResourcePoolId());
        }

        ResultCode resultCode = transformRuntimeFormat(para);
        if (!ResultCode.SUCCESS.equals(resultCode)) {
            return resultCode;
        }

        if (configurationInfo.getStatus() != 2) {
            try {
                dao.insertData("insertTheSingleRtIfRecordByData", para);
                
                /* 校验路由器端口的宿主交换机是否存在 */
                Integer parentCount = (Integer) dao.getSingleRecord("validateRtInstance",
                        para.get("parent_id"));

                if (null == parentCount || parentCount.equals(new Integer(0))) {
                    noParentItem.add(para.get("eqp_id"));
                }
                
            } catch (SQLException e) {
                logger.error("增量上报接口路由器端口" + para.get("instance_id") + "入库失败", e);
                e.printStackTrace();
                return ResultCode.ERROR_BODY_DATABASE_EXCEPTION;
            }
        } else {
            try {
                dao.deleteData("deleteRtIf", para.get("instance_id"));
            } catch (SQLException e) {
                logger.error("删除路由器端口" + para.get("instance_id") + "入库失败", e);
                e.printStackTrace();
                return ResultCode.ERROR_BODY_DATABASE_EXCEPTION;
            }
        }

        return ResultCode.SUCCESS;
    }
    
    /**
     * 处理防火墙的配置项信息
     * @author niul<br>
     *         2015年11月6日 上午9:58:08
     * @param configurationInfo 要处理的防火墙配置项信息
     * @param resultCode 处理结果
     * @param noParentItem 没有父节点的配置项
     * @return 处理是否成功<br>
     *         true——成功 false——失败
     */
    private ResultCode processFW(ConfigurationInfo configurationInfo, ArrayList<String> noParentItem) {
        /* 防火墙主键校验 */
        if (null == configurationInfo.getInstanceId()
                || "".equals(configurationInfo.getInstanceId())) {
            return ResultCode.ERROR_BODY_FW_INSTANCEID_NONE;
        }

        /* 校验操作类型 如果是删除，要先校验设备是否存在 */
        if (configurationInfo.getStatus() == 2) {
            Integer count = new Integer(0);
            try {
                count = (Integer) dao.getSingleRecord("validateFwInstance",
                        configurationInfo.getInstanceId());
            } catch (SQLException e) {
                logger.error("验证实例异常", e);
                return ResultCode.ERROR_BODY_DATABASE_EXCEPTION;
            }

            if (count == 0) {
                logger.info(ResultCode.ERROR_BODY_FW_INSTANCE_NOTEXIST.getDesc()
                        + configurationInfo.getInstanceId());
                return ResultCode.ERROR_BODY_FW_INSTANCE_NOTEXIST;
            }
        }

        HashMap<String, String> para = new HashMap<String, String>();
        for (CIAttributeInfo it : configurationInfo.getCiAttributeInfoList().getCiAttributeInfo()) {
            para.put(it.getCiAttributeID(), it.getCiAttributeValue());
        }

        para.put("instance_id", configurationInfo.getInstanceId());

        /* 资源池ID */
        if (null != info.getResourcePoolId() && !"".equals(info.getResourcePoolId())) {
            para.put("rc_id", info.getResourcePoolId());
        }

        ResultCode resultCode = transformRuntimeFormat(para);
        if (!ResultCode.SUCCESS.equals(resultCode)) {
            return resultCode;
        }

        if (configurationInfo.getStatus() != 2) {
            try {
                dao.insertData("insertTheSingleFwRecordByData", para);
            } catch (SQLException e) {
                logger.error("增量上报接口防火墙" + para.get("instance_id") + "入库失败", e);
                e.printStackTrace();
                return ResultCode.ERROR_BODY_DATABASE_EXCEPTION;
            }
        } else {
            try {
                dao.deleteData("deleteFwd", para.get("instance_id"));
            } catch (SQLException e) {
                logger.error("删除防火墙" + para.get("instance_id") + "入库失败", e);
                e.printStackTrace();
                return ResultCode.ERROR_BODY_DATABASE_EXCEPTION;
            }
        }

        return ResultCode.SUCCESS;
    }

    /**
     * @return the dao
     */
    public IbatisDAO getDao() {
        return dao;
    }

    /**
     * @param dao the dao to set
     */
    public void setDao(IbatisDAO dao) {
        this.dao = dao;
    }

    /**
     * 设置resourceTypeMap字段数据
     * @param resourceTypeMap The resourceTypeMap to set.
     */
    public void setResourceTypeMap(Map<String, String> resourceTypeMap) {
        this.resourceTypeMap = resourceTypeMap;
    }

}
