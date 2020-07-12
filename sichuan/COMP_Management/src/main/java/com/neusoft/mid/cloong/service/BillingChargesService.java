 /**
  * @author <a href="mailto:zhangyunfeng@neusoft.com"> zhangyunfeng </a>
  * @version 1.0.0  2016年10月19日   下午4:42:41
  */
package com.neusoft.mid.cloong.service;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import com.neusoft.mid.cloong.common.mybatis.IbatisDAO;
import com.neusoft.mid.cloong.identity.bean.BillingRecodInfo;
import com.neusoft.mid.iamp.logger.LogService;

 /**
 * @author <a href="mailto:zhangyunfeng@neusoft.com"> zhangyunfeng </a>
 * @version 1.0.0  2016年10月19日   下午4:42:41
 */
public class BillingChargesService {
	
	/**
	 * 日志.
	 */
	private static final LogService logger = LogService.getLogger(BillingChargesService.class);
	
	/**
	 * 换行符.
	 */
	private static final String LN = "\n";
	/**
	 * String 线程池.
	 */
	private ThreadPoolTaskExecutor billingTaskExecutor;

	/**
	 * dao.
	 */
	private IbatisDAO ibatisDAO;
	/**
	 * 主机名称.
	 */
	private String hostUser;
	
	/**
	 * 主机登录密码.
	 */
	private String hostPwd;
	
	/**
	 * 登录主机Ip.
	 */
	private String hostIp;
	
	/**
	 * 登录端口号.
	 */
	private String port;
	/**
	 * 话单路径.
	 */
	private String path;
	
	/**
	 * 话单备份路径.
	 */
	private String bakPath;
	/**
	 * 虚拟机话单文件名称.
	 */
	private String vmFileName;
	/**
	 * 云硬盘话单文件名称.
	 */
	private String ebsFileName;
	
	/**
	 * 符号左.
	 */
	private String symbolLeft;
	/**
	 * 符号中.
	 */
	private String symbolMid;
	/**
	 * 符号右.
	 */
	private String symbolRight;
	
	
	private List<String> ids = new ArrayList<String>();
	
	@SuppressWarnings("unchecked")
	public  void synchBillingToSFTP() throws SQLException{
		logger.info("********** 上传话单开始.**************");
		List<BillingRecodInfo> billingList =(List<BillingRecodInfo>) ibatisDAO.getData("queryAllBilling", null);
		
		List<BillingRecodInfo> vmBillingRecords = new ArrayList<BillingRecodInfo>();
		List<BillingRecodInfo> ebsBillingRecords = new ArrayList<BillingRecodInfo>();
		for(int i=0;i<billingList.size();i++){
				BillingRecodInfo billing = billingList.get(i);
			  if("0".equals(billing.getResourceType())){
			 	 vmBillingRecords.add(billing);
			  }else if("5".equals(billing.getResourceType())){
				 ebsBillingRecords.add(billing);
			 }
			  ids.add(billing.getId());
		}
		//分布处理话单信息.
		if(!vmBillingRecords.isEmpty()){//虚拟机
			billingTaskExecutor.execute(new BillingRecordThread(vmBillingRecords,"0"));
		}
		if(!ebsBillingRecords.isEmpty()){
			billingTaskExecutor.execute(new BillingRecordThread(ebsBillingRecords,"5"));
		}
		logger.info("********** 上传话单结束.**************");
	}
	
	private class BillingRecordThread implements Runnable {
		/**
		 *计费信息.
		 */
		private List<BillingRecodInfo>   billingRecords = new ArrayList<BillingRecodInfo>();
		/**
		 * 类型.
		 */
	    private String type ;
	    
	    private String fileName;
		private BillingRecordThread(List<BillingRecodInfo> billingRecords,String type){
				this.billingRecords = billingRecords;
				this.type = type;
		}
		@Override
		public void run() {
				fileName = getFileName();
				try {
					process();
					//更新数据库状态.
					if(!ids.isEmpty()){
						ibatisDAO.updateData("batchUpdate", ids);
					}
				} catch (Exception e) {
					logger.info("STFP Exception******", e);
				}
		}
		/**
		 * @throws Exception 
		 * 
		 */
		public void process() throws Exception  {
			   //生成新的话费数据.
				String	billingStr = formatBilling(billingRecords);
				List<String> newStringList = stringToList(billingStr);
				
				ChannelSftp sftp = SftpUtil.connect(hostUser, hostPwd, hostIp, port);
				
				sftp.cd(path);
				//已存在的话费数据.
				List<String> oldStringList = getExistBillingRecord(sftp);
				if(!oldStringList.isEmpty()){
					//比较话单数据,在原有话单数据中添加新数据.
					String allBillingStr = getAllBillingRecord(newStringList,oldStringList);
					billingStr = allBillingStr;
				}
				sftp.put(SftpUtil.string2InputStream(billingStr), fileName);
				//备份数据.
				sftp.cd(bakPath);
				sftp.put(SftpUtil.string2InputStream(billingStr), fileName);
				//关闭sftp流.
				SftpUtil.disconnect();
		}
		
		/**
		 * @param sftp
		 * @return
		 */
		public List<String> getExistBillingRecord(ChannelSftp sftp) {
			List<String> strList = new ArrayList<String>();
			InputStream  is = null;
			try {
				is = sftp.get(fileName);
				strList = stringToList(IOUtils.toString(is));
			} catch (SftpException e) {
				//不存在文件
				return strList;
			} catch (IOException e) {
				logger.info("********IO Exception", e);
			}
			return strList;
		}
		
		public String getAllBillingRecord(List<String> nStringList,List<String> oStringList){
			List<String> allDataList = new ArrayList<String>();
			allDataList.addAll(oStringList);
			for(String s:nStringList){
				 if(!oStringList.contains(s)){
					 	allDataList.add(s);
				 }
			}
			return listToString(allDataList);
		}
		public String listToString(List<String> list){
			String str = list.toString().replace(", ", LN);
			return str.substring(1, str.length()-1);
		}
		/**
		 * 生成文件话单文件名称.
		 */
		public String getFileName(){
				String name = "";
				if(type.equals("0")){
					name = MessageFormat.format(vmFileName, SftpUtil.getTimeStamp());
				}else if(type.equals("5")){
					name = MessageFormat.format(ebsFileName, SftpUtil.getTimeStamp());
				}
				return name;
		}
		
		public List<String> stringToList(String strs){
			List<String> sList = new ArrayList<String>();
			String[] array = strs.split(LN);
			for(int i=0;i<array.length;i++){
				sList.add(array[i]);
			}
			return sList;
	}
		
	}
	
	/**
	 * 格式化话单.虚拟机:{计费用户ID}|{云主机ID}|{虚拟CPU核数}|{内存大小（G）}|{计费方式（h/y）}|{订单时长}|{费用}
	 * 云硬盘:{计费用户ID}|{云硬盘ID}|{云硬盘大小（G）}|{计费方式（h/y）}|{订单时长}|{费用}
	 * @param vmBillingsRecords
	 * @return
	 */
	public String formatBilling(List<BillingRecodInfo> billingsRecords){
			StringBuffer sb = new StringBuffer();
			for(int i=0;i<billingsRecords.size();i++){
					BillingRecodInfo record = billingsRecords.get(i);
					sb.append(symbolLeft);
					sb.append(record.getChargesUserId()).append(symbolMid);
					sb.append(record.getResourceId()).append(symbolMid);
					if("0".equals(record.getResourceType())){
						sb.append(record.getCpu()).append(symbolMid);
						sb.append(record.getMemory()).append(symbolMid);
					}else if("5".equals(record.getResourceType())){
						sb.append(record.getDiskSize()).append(symbolMid);
					}
					sb.append(record.getLengthUnit()).append(symbolMid);
					sb.append(record.getLengthTime()).append(symbolMid);
					sb.append(record.getPrice()).append(symbolRight);
					if(i<billingsRecords.size()-1){
						sb.append(LN);
					}
			}
		return sb.toString();
	}
	
	

	
	
	/**
	 * @return the ibatisDAO
	 */
	@JSON(serialize=false)
	public IbatisDAO getIbatisDAO() {
		return ibatisDAO;
	}

	/**
	 * @param ibatisDAO the ibatisDAO to set
	 */
	public void setIbatisDAO(IbatisDAO ibatisDAO) {
		this.ibatisDAO = ibatisDAO;
	}

	/**
	 * @return the billingTaskExecutor
	 */
	public ThreadPoolTaskExecutor getBillingTaskExecutor() {
		return billingTaskExecutor;
	}

	/**
	 * @param billingTaskExecutor the billingTaskExecutor to set
	 */
	public void setBillingTaskExecutor(ThreadPoolTaskExecutor billingTaskExecutor) {
		this.billingTaskExecutor = billingTaskExecutor;
	}

	/**
	 * @return the hostUser
	 */
	public String getHostUser() {
		return hostUser;
	}

	/**
	 * @param hostUser the hostUser to set
	 */
	public void setHostUser(String hostUser) {
		this.hostUser = hostUser;
	}

	/**
	 * @return the hostPwd
	 */
	public String getHostPwd() {
		return hostPwd;
	}

	/**
	 * @param hostPwd the hostPwd to set
	 */
	public void setHostPwd(String hostPwd) {
		this.hostPwd = hostPwd;
	}

	/**
	 * @return the hostIp
	 */
	public String getHostIp() {
		return hostIp;
	}

	/**
	 * @param hostIp the hostIp to set
	 */
	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
	}

	/**
	 * @return the port
	 */
	public String getPort() {
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(String port) {
		this.port = port;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return the bakPath
	 */
	public String getBakPath() {
		return bakPath;
	}

	/**
	 * @param bakPath the bakPath to set
	 */
	public void setBakPath(String bakPath) {
		this.bakPath = bakPath;
	}

	/**
	 * @return the vmFileName
	 */
	public String getVmFileName() {
		return vmFileName;
	}

	/**
	 * @param vmFileName the vmFileName to set
	 */
	public void setVmFileName(String vmFileName) {
		this.vmFileName = vmFileName;
	}

	/**
	 * @return the ebsFileName
	 */
	public String getEbsFileName() {
		return ebsFileName;
	}

	/**
	 * @param ebsFileName the ebsFileName to set
	 */
	public void setEbsFileName(String ebsFileName) {
		this.ebsFileName = ebsFileName;
	}

	/**
	 * @return the symbolLeft
	 */
	public String getSymbolLeft() {
		return symbolLeft;
	}

	/**
	 * @param symbolLeft the symbolLeft to set
	 */
	public void setSymbolLeft(String symbolLeft) {
		this.symbolLeft = symbolLeft;
	}

	/**
	 * @return the symbolMid
	 */
	public String getSymbolMid() {
		return symbolMid;
	}

	/**
	 * @param symbolMid the symbolMid to set
	 */
	public void setSymbolMid(String symbolMid) {
		this.symbolMid = symbolMid;
	}

	/**
	 * @return the symbolRight
	 */
	public String getSymbolRight() {
		return symbolRight;
	}

	/**
	 * @param symbolRight the symbolRight to set
	 */
	public void setSymbolRight(String symbolRight) {
		this.symbolRight = symbolRight;
	}
	
	
}
