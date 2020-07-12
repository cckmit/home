package com.neusoft.mid.cloong.web.page.console.business;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.console.vlan3Phase.info.Cancelable;
import com.neusoft.mid.cloong.web.page.console.vlan3Phase.info.VlanInfo;
import com.neusoft.mid.cloong.web.page.console.vlan3Phase.info.VlanIpSegmentRel;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 业务视图查询vlan列表
 */
public class QueryVlanList3PhaseAction extends ResourceManagementBaseAction {

    private static final long serialVersionUID = -3256721028749119063L;

    private static LogService logger = LogService.getLogger(QueryVlanList3PhaseAction.class);

    /**
     * vlan号
     */
    private String vlanId;

    /**
     * vlan名称
     */
    private String vlanName;

    /**
     * 用于传递业务视图的业务ID参数
     */
    private String bussinessId;

    /**
     * 业务视图,页面上传来的业务id
     */
    private String queryBusinessId;

    /**
     * 业务名称
     */
    private String businessName;

    private List<VlanInfo> vlanList;

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

        /* 只查询一个业务 */
        List<String> apps = new ArrayList<String>();

        apps.add(queryBusinessId);

        vlan.setBusinessList(apps);

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

    /**
     * format 时间类型转换
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
//
//            //  查询是否有网卡使用了这个Vlan 
//            Integer count = (Integer) ibatisDAO.getSingleRecord("getVmBindingVlan3Phase", it.getVlanId());
//            if (!count.equals(0)) it.setCancelable(Cancelable.inUse);
        }
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

    /**
     * 获取bussinessId字段数据
     * @return Returns the bussinessId.
     */
    public String getBussinessId() {
        return bussinessId;
    }

    /**
     * 设置bussinessId字段数据
     * @param bussinessId The bussinessId to set.
     */
    public void setBussinessId(String bussinessId) {
        this.bussinessId = bussinessId;
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
     * 获取queryBusinessId字段数据
     * @return Returns the queryBusinessId.
     */
    public String getQueryBusinessId() {
        return queryBusinessId;
    }

    /**
     * 设置queryBusinessId字段数据
     * @param queryBusinessId The queryBusinessId to set.
     */
    public void setQueryBusinessId(String queryBusinessId) {
        this.queryBusinessId = queryBusinessId;
    }

    /**
     * 获取businessName字段数据
     * @return Returns the businessName.
     */
    public String getBusinessName() {
        return businessName;
    }

    /**
     * 设置businessName字段数据
     * @param businessName The businessName to set.
     */
    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }


}
