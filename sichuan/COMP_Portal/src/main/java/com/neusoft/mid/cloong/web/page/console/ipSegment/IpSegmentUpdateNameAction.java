package com.neusoft.mid.cloong.web.page.console.ipSegment;

import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.page.console.ipSegment.info.IpSegmentInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * IP段更改名称ajax
 * @author <a href="mailto:li-zr@neusoft.com">Zongrui Li</a>
 * @version $Revision 1.0 $ 2015年3月13日 下午4:31:07
 */
public class IpSegmentUpdateNameAction extends BaseAction {

    private static final long serialVersionUID = 8540169046702905750L;

    private final LogService logger = LogService.getLogger(IpSegmentUpdateNameAction.class);

    private String ipSegmentId;

    private String ipSegmentDesc;

    private String resourcePoolId;

    private String resourcePoolPartId;

    @SuppressWarnings("deprecation")
    public String execute() {

        logger.info("IP段更名Action开始");

        IpSegmentInfo info = new IpSegmentInfo();

        info.setIpSegmentId(ipSegmentId);

        try {
            info = (IpSegmentInfo) ibatisDAO.getSingleRecord("getIpSegmentById", info);
        } catch (Exception e) {
            logger.error("IP段更名查询数据库异常", e);
            return ConstantEnum.ERROR.toString();
        }

        if (null == info) {
            msg = "IP段已不存在";
            return ConstantEnum.FAILURE.toString();
        } else if (ipSegmentDesc.trim().equals(info.getIpSegmentDesc())) {
            msg = "更改成功";
            return ConstantEnum.SUCCESS.toString();
        } else {
            info.setIpSegmentDesc(ipSegmentDesc.trim());
            try {
                ibatisDAO.updateData("updateIpSegmentDesc", info);
                msg = "更改成功";
            } catch (Exception e) {
                logger.error("IP段更名查询数据库异常", e);
                return ConstantEnum.ERROR.toString();
            }
        }

        logger.info("IP段更名Action完成");

        return ConstantEnum.SUCCESS.toString();
    }

    /**
     * 获取ipSegmentDesc字段数据
     * @return Returns the ipSegmentDesc.
     */
    public String getIpSegmentDesc() {
        return ipSegmentDesc;
    }

    /**
     * 设置ipSegmentDesc字段数据
     * @param ipSegmentDesc The ipSegmentDesc to set.
     */
    public void setIpSegmentDesc(String ipSegmentDesc) {
        this.ipSegmentDesc = ipSegmentDesc;
    }

    /**
     * 获取resourcePoolId字段数据
     * @return Returns the resourcePoolId.
     */
    public String getResourcePoolId() {
        return resourcePoolId;
    }

    /**
     * 设置resourcePoolId字段数据
     * @param resourcePoolId The resourcePoolId to set.
     */
    public void setResourcePoolId(String resourcePoolId) {
        this.resourcePoolId = resourcePoolId;
    }

    /**
     * 获取resourcePoolPartId字段数据
     * @return Returns the resourcePoolPartId.
     */
    public String getResourcePoolPartId() {
        return resourcePoolPartId;
    }

    /**
     * 设置resourcePoolPartId字段数据
     * @param resourcePoolPartId The resourcePoolPartId to set.
     */
    public void setResourcePoolPartId(String resourcePoolPartId) {
        this.resourcePoolPartId = resourcePoolPartId;
    }

    /**
     * 获取ipSegmentId字段数据
     * @return Returns the ipSegmentId.
     */
    public String getIpSegmentId() {
        return ipSegmentId;
    }

    /**
     * 设置ipSegmentId字段数据
     * @param ipSegmentId The ipSegmentId to set.
     */
    public void setIpSegmentId(String ipSegmentId) {
        this.ipSegmentId = ipSegmentId;
    }
}
