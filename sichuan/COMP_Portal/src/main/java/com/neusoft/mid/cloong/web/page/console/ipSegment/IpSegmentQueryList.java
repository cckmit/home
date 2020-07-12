package com.neusoft.mid.cloong.web.page.console.ipSegment;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.PageAction;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.console.ipSegment.info.IpSegmentInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * ip段查询列表action
 * @author <a href="mailto:li-zr@neusoft.com">Zongrui Li</a>
 * @version $Revision 1.0 $ 2015年3月6日 上午11:14:51
 */
public class IpSegmentQueryList extends PageAction {

    private static final long serialVersionUID = 6544181993662433820L;

    private static LogService logger = LogService.getLogger(IpSegmentQueryList.class);

    /**
     * 向页面传的IP段list
     */
    private List<IpSegmentInfo> ipSegmentList;

    /**
     * 业务ID,TODO:打算和业务视图用一个action
     */
    private String queryBusinessId;

    @SuppressWarnings("unchecked")
    public String execute() {

        logger.info("进入查询ip段列表Action");

        if (null != errMsg && !"".equals(errMsg)) {
            this.addActionError(errMsg);
        }
        // session中获取用户ID
        UserBean user = ((UserBean) ServletActionContext.getRequest().getSession()
                .getAttribute(SessionKeys.userInfo.toString()));
        String userId = user.getUserId();

        IpSegmentInfo ip = new IpSegmentInfo();

        ip.setBusinessList(user.getAppIdList());
        List<Integer> tmp = new ArrayList<Integer>();
        tmp.add(0);
        tmp.add(2);
        ip.setReleased(tmp);

        try {
            ipSegmentList = getPage("getIpSegmentCount", "getIpSegmentList", ip);

            for (IpSegmentInfo info : ipSegmentList) {
                info.setCreateTime(DateParse.parse(info.getCreateTime()));
            }

        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    MessageFormat.format(getText("ipSegment.queryList.fail"), userId), e);
            this.addActionError(MessageFormat.format(getText("ipSegment.queryList.fail"), userId));
            return ConstantEnum.ERROR.toString();
        }

        logger.info("ip段列表查询成功");

        return ConstantEnum.SUCCESS.toString();
    }

    @SuppressWarnings("unchecked")
    public String query4Overview() {

        IpSegmentInfo ip = new IpSegmentInfo();

        List<Integer> tmp = new ArrayList<Integer>();
        tmp.add(0);
        tmp.add(2);
        ip.setReleased(tmp);
        ip.setBusinessList(getCurrentUser().getAppIdList());

        try {
            ipSegmentList = ibatisDAO.getData("getIpSegmentList", ip);

            for (IpSegmentInfo info : ipSegmentList)
                info.setCreateTime(DateParse.parse(info.getCreateTime()));

        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    MessageFormat.format(getText("ipSegment.queryList.fail"), getCurrentUserId()),
                    e);
            this.addActionError(MessageFormat.format(getText("ipSegment.queryList.fail"),
                    getCurrentUserId()));
            return ConstantEnum.ERROR.toString();
        }

        return ConstantEnum.SUCCESS.toString();
    }

    /**
     * 获取ipSegmentList字段数据
     * @return Returns the ipSegmentList.
     */
    public List<IpSegmentInfo> getIpSegmentList() {
        return ipSegmentList;
    }

    /**
     * 设置ipSegmentList字段数据
     * @param ipSegmentList The ipSegmentList to set.
     */
    public void setIpSegmentList(List<IpSegmentInfo> ipSegmentList) {
        this.ipSegmentList = ipSegmentList;
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
}
