/*******************************************************************************
 * @(#)VMPreApplyInfoAction.java 2018-4-23
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.host.vm.order;

import org.apache.struts2.ServletActionContext;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.ResourceTypeEnum;
import com.neusoft.mid.cloong.web.page.console.host.vm.info.VMPreInstanceInfo;
import com.neusoft.mid.cloong.web.page.host.vm.order.info.OsInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 虚拟机预订购
 * @author li-lei>
 * @version $Revision 1.0 $ 2018-4-20 下午1:43:21
 */
public class VMPreApplyInfoAction extends BaseAction {

    /**
     * 虚拟化版本号
     */
    private static final long serialVersionUID = -3246558237673423698L;

    /**
     * 日志记录者
     */
    private static LogService logger = LogService.getLogger(VMApplyInfoAction.class);

    /**
     * 条目ID
     */
    private String itemId;

    /**
     * 规格ID
     */
    private String standardId;
    
    private String appId;

    /**
     * OSID
     */
    private String osId;

    /**
     * 数量
     */
    private String num;

    /**
     * 时长
     */
    private String lengthTime;
    
    /**
     * 计费方式.
     */
    private String chargeType;

    /**
     * 备注
     */
    private String vmRemark;

    /**
     * 资源池ID
     */
    private String respoolId;

    /**
     * 资源池分区ID
     */
    private String respoolPartId;

    /**
     * 资源池名称
     */
    private String respoolName;

    /**
     * 资源池分区名称
     */
    private String respoolPartName;

    /**
     * CPU数量
     */
    private String cpuNum;

    /**
     * 内存容量
     */
    private String ramSize;

    /**
     * 磁盘容量
     */
    private String discSize;

    /**
     * 虚拟机名称
     */
    private String vmName;

    /**
     * 时间戳 yyyyMMddHHmmss
     */
    private static final DateTimeFormatter TIMESTAMP = DateTimeFormat.forPattern("yyyyMMddHHmmss");

    private String appId1;
    
    // IP类型
    private String ipType;
    
    /**
     * execute Action执行方法
     * @return 结果码
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
    @Override
    public String execute() {
        try {
        	logger.info("***********appId:" + appId);
        	if (null == appId) {
        		appId = appId1;
        	}
            
            logger.debug("开始创建虚拟机");
            printVmInformation();
            
            // session中获取用户ID
            String userId = ((UserBean) ServletActionContext.getRequest().getSession()
                    .getAttribute(SessionKeys.userInfo.toString())).getUserId();
            
            OsInfo tempOsInfo = (OsInfo) ibatisDAO.getSingleRecord("queryOsName", osId);
            
            VMPreInstanceInfo vmPreInstanceInfo = new VMPreInstanceInfo();
            vmPreInstanceInfo.setCaseId(generatorCaseId(ResourceTypeEnum.VM.toString()));
            vmPreInstanceInfo.setVmName(vmName);
            vmPreInstanceInfo.setAppId(appId);
            vmPreInstanceInfo.setStatus("0"); // 待订购
            vmPreInstanceInfo.setResPoolId(respoolId);
            vmPreInstanceInfo.setResPoolName(respoolName);
            vmPreInstanceInfo.setResPoolPartId(respoolPartId);
            vmPreInstanceInfo.setResPoolPartName(respoolPartName);
            vmPreInstanceInfo.setCpuNum(cpuNum);
            vmPreInstanceInfo.setRamSize(ramSize);
            vmPreInstanceInfo.setDiscSize(discSize);
            vmPreInstanceInfo.setIsoId(osId);
            vmPreInstanceInfo.setIsoName(tempOsInfo.getOsName());
            vmPreInstanceInfo.setChargeType(chargeType);
            vmPreInstanceInfo.setChargeTime(lengthTime);
            vmPreInstanceInfo.setNum(num);
            vmPreInstanceInfo.setCreateTime(TIMESTAMP.print(new DateTime()));
            vmPreInstanceInfo.setCreateUser(userId);
            vmPreInstanceInfo.setDescription(vmRemark);
            vmPreInstanceInfo.setIpType(ipType);
            
            ibatisDAO.insertData("insertPreApplyVm", vmPreInstanceInfo);
            msg = "预订购虚拟机申请发送成功！";
            return ConstantEnum.SUCCESS.toString();
        } catch (Exception e) {
        	 msg = "预订购虚拟机申请发送失败！";
            return ConstantEnum.FAILURE.toString();
        }
    }

    /**
     * 生成虚拟机实例Id 格式为：CASE-[资源类型]- [实例ID]-yyyyMMddHHmmss+四位随机数
     * @param type 资源类型
     * @return
     */
    public String generatorCaseId(String type) {
        StringBuilder sb = new StringBuilder();
        String currentTimeStamp = TIMESTAMP.print(new DateTime());
        sb.append("CASE-").append("PRE-").append(type).append("-").append("TT").append("-")
                .append(currentTimeStamp);
        return sb.toString();
    }

    private void printVmInformation() {
        if (logger.isDebugEnable()) {
			logger.debug("itemId = " + itemId);
			logger.debug("standardId = " + standardId);
            logger.debug("osId = " + osId);
            logger.debug("num = " + num);
            logger.debug("lengthTime = " + lengthTime);
            logger.debug("vmRemark = " + vmRemark);
            logger.debug("respoolId = " + respoolId);
            logger.debug("respoolPartId = " + respoolPartId);
            logger.debug("respoolName = " + respoolName);
            logger.debug("respoolPartName = " + respoolPartName);
            logger.debug("appId = " + appId);
            logger.debug("vmName = " + vmName);
        }
    }

    /**
     * 获取itemId字段数据
     * @return Returns the itemId.
     */
    public String getItemId() {
        return itemId;
    }

    /**
     * 设置itemId字段数据
     * @param itemId The itemId to set.
     */
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    /**
     * 获取standardId字段数据
     * @return Returns the standardId.
     */
    public String getStandardId() {
        return standardId;
    }

    /**
     * 设置standardId字段数据
     * @param standardId The standardId to set.
     */
    public void setStandardId(String standardId) {
        this.standardId = standardId;
    }

    /**
     * 获取osId字段数据
     * @return Returns the osId.
     */
    public String getOsId() {
        return osId;
    }

    /**
     * 设置osId字段数据
     * @param osId The osId to set.
     */
    public void setOsId(String osId) {
        this.osId = osId;
    }

    /**
     * 获取num字段数据
     * @return Returns the num.
     */
    public String getNum() {
        return num;
    }

    /**
     * 设置num字段数据
     * @param num The num to set.
     */
    public void setNum(String num) {
        this.num = num;
    }

    /**
     * 获取lengthTime字段数据
     * @return Returns the lengthTime.
     */
    public String getLengthTime() {
        return lengthTime;
    }

    /**
     * 设置lengthTime字段数据
     * @param lengthTime The lengthTime to set.
     */
    public void setLengthTime(String lengthTime) {
        this.lengthTime = lengthTime;
    }

    /**
     * 获取vmRemark字段数据
     * @return Returns the vmRemark.
     */
    public String getVmRemark() {
        return vmRemark;
    }

    /**
     * 设置vmRemark字段数据
     * @param vmRemark The vmRemark to set.
     */
    public void setVmRemark(String vmRemark) {
        this.vmRemark = vmRemark;
    }

    /**
     * 获取respoolId字段数据
     * @return Returns the respoolId.
     */
    public String getRespoolId() {
        return respoolId;
    }

    /**
     * 设置respoolId字段数据
     * @param respoolId The respoolId to set.
     */
    public void setRespoolId(String respoolId) {
        this.respoolId = respoolId;
    }

    /**
     * 获取respoolPartId字段数据
     * @return Returns the respoolPartId.
     */
    public String getRespoolPartId() {
        return respoolPartId;
    }

    /**
     * 设置respoolPartId字段数据
     * @param respoolPartId The respoolPartId to set.
     */
    public void setRespoolPartId(String respoolPartId) {
        this.respoolPartId = respoolPartId;
    }

    /**
     * 获取respoolName字段数据
     * @return Returns the respoolName.
     */
    public String getRespoolName() {
        return respoolName;
    }

    /**
     * 设置respoolName字段数据
     * @param respoolName The respoolName to set.
     */
    public void setRespoolName(String respoolName) {
        this.respoolName = respoolName;
    }

    /**
     * 获取respoolPartName字段数据
     * @return Returns the respoolPartName.
     */
    public String getRespoolPartName() {
        return respoolPartName;
    }

    /**
     * 设置respoolPartName字段数据
     * @param respoolPartName The respoolPartName to set.
     */
    public void setRespoolPartName(String respoolPartName) {
        this.respoolPartName = respoolPartName;
    }

    /**
     * 获取cpuNum字段数据
     * @return Returns the cpuNum.
     */
    public String getCpuNum() {
        return cpuNum;
    }

    /**
     * 设置cpuNum字段数据
     * @param cpuNum The cpuNum to set.
     */
    public void setCpuNum(String cpuNum) {
        this.cpuNum = cpuNum;
    }

    /**
     * 获取ramSize字段数据
     * @return Returns the ramSize.
     */
    public String getRamSize() {
        return ramSize;
    }

    /**
     * 设置ramSize字段数据
     * @param ramSize The ramSize to set.
     */
    public void setRamSize(String ramSize) {
        this.ramSize = ramSize;
    }

    /**
     * 获取discSize字段数据
     * @return Returns the discSize.
     */
    public String getDiscSize() {
        return discSize;
    }

    /**
     * 设置discSize字段数据
     * @param discSize The discSize to set.
     */
    public void setDiscSize(String discSize) {
        this.discSize = discSize;
    }

    /**
     * 获取appId字段数据
     * @return Returns the appId.
     */
    public String getAppId() {
        return appId;
    }

    /**
     * 设置appId字段数据
     * @param appId The appId to set.
     */
    public void setAppId(String appId) {
        this.appId = appId;
    }

    /**
     * 获取serialversionuid字段数据
     * @return Returns the serialversionuid.
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    /**
     * 获取timestamp字段数据
     * @return Returns the timestamp.
     */
    public static DateTimeFormatter getTimestamp() {
        return TIMESTAMP;
    }

    public String getVmName() {
        return vmName;
    }

    public void setVmName(String vmName) {
        this.vmName = vmName;
    }

	public String getAppId1() {
		return appId1;
	}

	public void setAppId1(String appId1) {
		this.appId1 = appId1;
	}

    /**
	 * @return the chargeType
	 */
	public String getChargeType() {
		return chargeType;
	}

	/**
	 * @param chargeType the chargeType to set
	 */
	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}

	/**
	 * @return the ipType
	 */
	public String getIpType() {
		return ipType;
	}

	/**
	 * @param ipType the ipType to set
	 */
	public void setIpType(String ipType) {
		this.ipType = ipType;
	}

}
