/*******************************************************************************
 * @(#)DiskDetailAction.java 2013-3-20
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.ip;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.neusoft.mid.cloong.common.mybatis.BatchVO;
import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.portmap.CreatePortMap;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.DeletePortMapRequest;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.PortMapRequest;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.PortMapResponse;
import com.neusoft.mid.cloong.publicIp.core.PublicPortCreateFail;
import com.neusoft.mid.cloong.publicIp.core.PublicPortDelFail;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.console.ip.info.PortConfigInfo;
import com.neusoft.mid.cloong.web.page.console.ip.info.PublicIpInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 显示公网ip详细信息
 * @author <a href="mailto:miq@neusoft.com">mi quan </a>
 * @version $Revision 1.1 $ 2013-3-20 下午05:16:45
 */
public class IpDetailAction extends BaseAction {

    private static final long serialVersionUID = 2501512361728019512L;

    private static LogService logger = LogService.getLogger(IpDetailAction.class);

    private String ipAddress;

    private PublicIpInfo ipInfo = new PublicIpInfo();
    
    private PortConfigInfo portConfig=new PortConfigInfo();
    
    private List<PortConfigInfo> portConfigList= new ArrayList<PortConfigInfo>();
    
    private CreatePortMap createportmap;
    
    /**
     * 资源池ID
     */
    private String resourcePoolId;

    /**
     * 资源池分区ID
     */
    private String resourcePoolPartId;

    /**
     * 业务id
     */
    private String appId;
    
    /**
     * 时间戳 yyyyMMddHHmmss
     */
    private static final DateTimeFormatter TIMESTAMP = DateTimeFormat.forPattern("yyyyMMddHHmmss");

    /**
     * 实例编号
     */
    private String caseId;

    public String execute() {
        try {
            // 查询公有ip详情       	
            ipInfo = (PublicIpInfo) ibatisDAO.getSingleRecord("getPublicIpDetail", caseId);
            ipInfo.setCreateTime(DateParse.parse(ipInfo.getCreateTime()));
            ipInfo.setUpdateTime(DateParse.parse(ipInfo.getUpdateTime()));
            ipAddress = ipInfo.getPublicIp();
            portConfigList=ibatisDAO.getData("getPortConfigByPublicIp", ipAddress);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ConstantEnum.SUCCESS.toString();
    }
    
    /**
     * 添加端口配置
     * @return
     */
    public String addConfig(){
        
        logger.info(portConfig.toString());
        // session中获取用户ID
        String userId = ((UserBean) ServletActionContext.getRequest().getSession()
                .getAttribute(SessionKeys.userInfo.toString())).getUserId();
        String time = DateParse.generateDateFormatyyyyMMddHHmmss();
        portConfig.setCreateUser(userId);
        portConfig.setCreateTime(time);
     // 拼凑订单信息
        List<BatchVO> updateBatchVO = new ArrayList<BatchVO>();
        try {
        	
        	PortMapRequest ptmreq = getPortMapRequest(portConfig);        	
        	PortMapResponse cpm = createportmap.createPortMap(ptmreq);
        	if ("0".equals(cpm.getResultCode())) {
				String mapId = cpm.getResultMessage();
				logger.info("addConfig PortMapId:="+mapId);
				portConfig.setPortConfigId(mapId);
			} else {
				logger.info("addConfig error:="+cpm.getResultMessage());
				PublicPortCreateFail portCreateFail=new PublicPortCreateFail();
				portCreateFail.setCreateTime(TIMESTAMP.print(new DateTime()));
				portCreateFail.setFailCause(cpm.getResultMessage());
				portCreateFail.setFailCode(cpm.getResultCode());
				portCreateFail.setPortConfigId(portConfig.getPortConfigId());
				portCreateFail.setPublicIp(portConfig.getPublicIp());
				portCreateFail.setResPoolId(resourcePoolId);
				portCreateFail.setResPoolPartId(resourcePoolPartId);
				portCreateFail.setSerialNum(ptmreq.getSerialNum());
				portCreateFail.setAppId(appId);
				ibatisDAO.insertData("insertPortCreateFail", portCreateFail);
				
				throw new Exception(cpm.getResultMessage());
			}     	
            updateBatchVO.add(new BatchVO("ADD", "addPortConfig", portConfig) );
            updateBatchVO.add(new BatchVO("MOD", "updateHostId", portConfig) );
            ibatisDAO.updateBatchData(updateBatchVO);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
			e.printStackTrace();
		}
        
        return ConstantEnum.SUCCESS.toString();
    }
    
    private PortMapRequest getPortMapRequest(PortConfigInfo portConfig2) {
    	PortMapRequest req = new PortMapRequest();
    	req.setMappingMode(portConfig.getMappingMode());
    	req.setProtocol(portConfig.getProtocol());
    	req.setPublicIp(portConfig.getPublicIp());
    	req.setPublicPort(portConfig.getPublicPort());
    	req.setVmId(portConfig.getVmId());
    	req.setVmPort(portConfig.getVmPort());
    	req.setVmPrivateIp(portConfig.getVmPrivateIp());
    	return req;
			
	}

	/**
     * 删除端口映射配置
     * @return
     */
   public String delConfig(){
        logger.info(portConfig.toString());
        List<BatchVO> updateBatchVO = new ArrayList<BatchVO>();
        try {
        	DeletePortMapRequest req = new DeletePortMapRequest();
        	PortConfigInfo configInfo = (PortConfigInfo) ibatisDAO.getSingleRecord("getPortMapConfig", portConfig);
        	logger.info("configInfo.getPortConfigId() :" +configInfo.getPortConfigId());
        	req.setPortMapId(configInfo.getPortConfigId());
			PortMapResponse dpm = createportmap.deletePortMap(req);
			if ("0".equals(dpm.getResultCode())) {
				String result = dpm.getResultMessage();
				logger.info("delConfig ResultMessage:="+result);
			} else {
				logger.info("delConfig error:="+dpm.getResultMessage());
				
				PublicPortDelFail portDelFail=new PublicPortDelFail();
				
				portDelFail.setCreateTime(TIMESTAMP.print(new DateTime()));
				portDelFail.setFailCause(dpm.getResultMessage());
				portDelFail.setFailCode(dpm.getResultCode());
				portDelFail.setPortConfigId(portConfig.getPortConfigId());
				portDelFail.setPublicIp(portConfig.getPublicIp());
				portDelFail.setResPoolId(resourcePoolId);
				portDelFail.setResPoolPartId(resourcePoolPartId);
				portDelFail.setSerialNum(req.getSerialNum());
				portDelFail.setAppId(appId);
				
				ibatisDAO.insertData("insertPortDelFail", portDelFail);
				throw new Exception(dpm.getResultMessage());
			}
        	
            updateBatchVO.add(new BatchVO("DEL", "delPortConfig", portConfig) );
            updateBatchVO.add(new BatchVO("MOD", "clearHostId", portConfig) );
            ibatisDAO.updateBatchData(updateBatchVO);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
			e.printStackTrace();
		}
        
        return ConstantEnum.SUCCESS.toString();
    }

   
    /**
     * 获取caseId字段数据
     * @return Returns the caseId.
     */
    public String getCaseId() {
        return caseId;
    }

    /**
     * 设置caseId字段数据
     * @param caseId The caseId to set.
     */
    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public PublicIpInfo getIpInfo() {
        return ipInfo;
    }

    public void setIpInfo(PublicIpInfo ipInfo) {
        this.ipInfo = ipInfo;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public PortConfigInfo getPortConfig() {
        return portConfig;
    }

    public void setPortConfig(PortConfigInfo portConfig) {
        this.portConfig = portConfig;
    }

    public List<PortConfigInfo> getPortConfigList() {
        return portConfigList;
    }

    public void setPortConfigList(List<PortConfigInfo> portConfigList) {
        this.portConfigList = portConfigList;
    }

	public CreatePortMap getCreateportmap() {
		return createportmap;
	}

	public void setCreateportmap(CreatePortMap createportmap) {
		this.createportmap = createportmap;
	}

    public String getResourcePoolId() {
        return resourcePoolId;
    }

    public void setResourcePoolId(String resourcePoolId) {
        this.resourcePoolId = resourcePoolId;
    }

    public String getResourcePoolPartId() {
        return resourcePoolPartId;
    }

    public void setResourcePoolPartId(String resourcePoolPartId) {
        this.resourcePoolPartId = resourcePoolPartId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
    
 }
