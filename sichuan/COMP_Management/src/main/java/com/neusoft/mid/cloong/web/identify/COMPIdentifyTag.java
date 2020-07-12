package com.neusoft.mid.cloong.web.identify;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang.StringUtils;

import com.neusoft.mid.cloong.identity.bean.PermissionBean;
import com.neusoft.mid.cloong.identity.bean.RoleBean;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.iamp.logger.LogService;

public class COMPIdentifyTag extends BodyTagSupport{

	/**
	 * serialVersionUID : TODO 这里请补充该字段的简述说明
	 */
	private static final long serialVersionUID = 4798227800549887669L;
	/**
     * 用于日志输出
     */
    private static LogService logger = LogService.getLogger(COMPIdentifyTag.class);

    /**
     * 标签需要的参数，权限点
     */
    private String permissionname;

    /**执行该类时，会执行该方法
     * 返回值:
     * EVAL_BODY_INCLUDE 表示会包含标签体中的内容
     * SKIP_BODY         表示不会包含标签体中的内容
     */
    public int doStartTag() throws JspException {
        // 获取到传进来的权限点
        // 判断当前用户是否有该权限点
        // 从session中获取拉拉的接口对象
        // 然后把权限点放入接口对象中
        // 根据接口对象返回的boolean值，就可以判断，该用户是否有看该权限点的权限。
        HttpSession session = this.pageContext.getSession();
        if (null == session) {
            logger.error(ValidateStatusEnum.SESSIONISNULL_EXCEPTION_CODE,
                    "session为空！");
        }
        UserBean userInfo = (UserBean) session
                .getAttribute(SessionKeys.userInfo.toString());
        if (null == userInfo) {
            logger.error(ValidateStatusEnum.AUTHENTICATERISNULL_EXCEPTION_CODE,
            "session中的userInfo为空！");
        }
        if (StringUtils.isEmpty(permissionname)) {
            logger.error(ValidateStatusEnum.AUTHENTICATERISNULL_EXCEPTION_CODE,
            "标签的permissionname为空！");
        }

        if (logger.isDebugEnable()) {
            //logger.debug("session = " + session);
            //logger.debug("authenticater = " + userInfo);
        }

        // 有权限，就显示包含的内容
        if (isAccess(userInfo)) {
            return EVAL_BODY_INCLUDE;
        }
        // 没有权限，就不显示包含的内容
        return SKIP_BODY;
    }
    
    private boolean isAccess(UserBean userInfo){
        for(RoleBean roleInfo:userInfo.getRoles()){
            for(PermissionBean perm:roleInfo.getPermList()){
                if(perm.getEnglishName().equals(permissionname)){
                    return true;
                }
            }
        }
        return false;
    }

    public String getPermissionname() {
        return permissionname;
    }

    public void setPermissionname(String permissionname) {
        this.permissionname = permissionname;
    }



}
