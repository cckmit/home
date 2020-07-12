package com.neusoft.mid.cloong.web.page.console.vlan3Phase;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.console.business.ResourceManagementBaseAction;
import com.neusoft.mid.cloong.web.page.console.vlan3Phase.VLANQueryList3PhaseAction;
import com.neusoft.mid.cloong.web.page.console.vlan3Phase.info.Cancelable;
import com.neusoft.mid.cloong.web.page.console.vlan3Phase.info.VlanInfo;
import com.neusoft.mid.cloong.web.page.console.vlan3Phase.info.VlanIpSegmentRel;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 资源视图vlan 34期查询list
 */
public class VLANQueryList3PhaseAction extends ResourceManagementBaseAction {
	


    private static final long serialVersionUID = 5201828322749702209L;

    private final LogService logger = LogService.getLogger(VLANQueryList3PhaseAction.class);

    /**
     * 用于存放查询结果
     */
    private List<VlanInfo> vlanList;

    /**
     * vlan范围号
     */
    private String vlanId;

    /**
     * vlan名称
     */
    private String vlanName;
    
    private String errMsg;

    private String msg;
    
    @SuppressWarnings("unchecked")
    public String execute() {

        logger.info("进入查询vlan列表Action");

        if (null != errMsg && !"".equals(errMsg)) {
            this.addActionError(errMsg);
        }
        // session中获取用户ID
        UserBean user = ((UserBean) ServletActionContext.getRequest().getSession()
                .getAttribute(SessionKeys.userInfo.toString()));
        String userId = user.getUserId();

        VlanInfo vlan = new VlanInfo();

        vlan.setVlanId(vlanId);
        vlan.setVlanName(vlanName);
        vlan.setCreateUser(userId);
        vlan.setBusinessList(user.getAppIdList());
        List<Integer> tmp = new ArrayList<Integer>();
        tmp.add(0);
        tmp.add(2);
        vlan.setCanceled(tmp);

        try {
            vlanList = getPage("getVlanCount3Phase", "getVlanList3Phase", vlan);
            format();
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    MessageFormat.format(getText("vlan3Phase.queryList.fail"), userId), e);
            this.addActionError(MessageFormat.format(getText("vlan3Phase.queryList.fail"), userId));
            return ConstantEnum.ERROR.toString();
        }

        logger.info("查询vlan列表成功");

        return ConstantEnum.SUCCESS.toString();
    }

    @SuppressWarnings("unchecked")
    public String query4Overview() {

        VlanInfo vlan = new VlanInfo();

        List<Integer> tmp = new ArrayList<Integer>();
        tmp.add(0);
        tmp.add(2);
        vlan.setCanceled(tmp);
        vlan.setBusinessList(getCurrentUser().getAppIdList());

        try {

            vlanList = ibatisDAO.getData("getVlanList3Phase", vlan);
            format();
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    MessageFormat.format(getText("vlan3Phase.queryList.fail"), getCurrentUserId()), e);
            this.addActionError(MessageFormat.format(getText("vlan3Phase.queryList.fail"),
                    getCurrentUserId()));
            return ConstantEnum.ERROR.toString();
        }

        return ConstantEnum.SUCCESS.toString();
    }

    /**
     * format 格式转换,判断vlan能否被释放（只有待创建的VLAN_SDN不能释放）
     * @throws Exception
     */
    private void format() throws Exception {
        for (VlanInfo it : vlanList) {
            String createTime = it.getCreateTime() == null ? "" : it.getCreateTime();
            it.setCreateTime(DateParse.parse(createTime));
            String cancelTime = it.getCancelTime() == null ? "" : it.getCancelTime();
            it.setCancelTime(DateParse.parse(cancelTime));

            /* 是否待创建 */
            if ("2".equals(it.getStatus())) {
                it.setCancelable(Cancelable.toBeCreated);
                continue;
            }
            /* 绑定关系状态是否为待审 */
//            VlanIpSegmentRel tmp = new VlanIpSegmentRel();
//            tmp.setVlanId(it.getVlanId());
//            tmp.setStatus("2");
//            VlanIpSegmentRel rel = (VlanIpSegmentRel) ibatisDAO.getSingleRecord("getVlanIpBindAss3Phase",
//                    tmp);
//
//            if (null != rel) {
//                it.setCancelable(Cancelable.bindTakingEffect);
//                continue;
//            }

            /* 查询是否有网卡使用了这个Vlan */
//            Integer count = (Integer) ibatisDAO.getSingleRecord("getVmBindingVlan3Phase", it.getVlanId());
//            if (!count.equals(0)) it.setCancelable(Cancelable.inUse);
        }
    }

    /**
     * 获取vlanList字段数据
     * @return Returns the vlanList.
     */
    public List<VlanInfo> getVlanList() {
        return vlanList;
    }

    /**
     * 设置vlanList字段数据
     * @param vlanList The vlanList to set.
     */
    public void setVlanList(List<VlanInfo> vlanList) {
        this.vlanList = vlanList;
    }

    /**
     * 获取vlanId字段数据
     * @return Returns the vlanId.
     */
    public String getVlanId() {
        return vlanId;
    }

    /**
     * 设置vlanId字段数据
     * @param vlanId The vlanId to set.
     */
    public void setVlanId(String vlanId) {
        this.vlanId = vlanId;
    }

    /**
     * 获取vlanName字段数据
     * @return Returns the vlanName.
     */
    public String getVlanName() {
        return vlanName;
    }

    /**
     * 设置vlanName字段数据
     * @param vlanName The vlanName to set.
     */
    public void setVlanName(String vlanName) {
        this.vlanName = vlanName;
    }

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

}
