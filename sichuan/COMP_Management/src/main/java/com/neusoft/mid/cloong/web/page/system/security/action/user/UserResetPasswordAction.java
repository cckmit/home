/*******************************************************************************
 * @(#)UserResetPasswordAction.java 2014年1月13日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.system.security.action.user;

import com.neusoft.mid.cloong.identity.service.UserService;
import com.neusoft.mid.cloong.web.action.BaseAction;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.page.product.item.vm.info.CreateResult;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 用户解锁Action
 * @author <a href="mailto:feng.jian@neusoft.com">feng jian</a>
 * @version $Revision 1.0 $ 2014年1月13日 下午2:17:02
 */
public class UserResetPasswordAction extends BaseAction {

    /**
     * serialVersionUID : 类序列号
     */
    private static final long serialVersionUID = -7900411968576847173L;

    /**
     * 日志打印对象
     */
    private static LogService logger = LogService.getLogger(UserResetPasswordAction.class);

    // ~~~~~~~~~~~ Spring area START ~~~~~~~~~~~//
    /**
     * 用户操作服务service
     */
    private UserService userService;

    /**
     * 操作结果
     */
    private CreateResult result;

    // ^^^^^^^^^^^ Spring area END ^^^^^^^^^^^^^//

    // ~~~~~~~~~~~ request parameter area START ~~~~~~~~~~~//

    /**
     * 用户绑定的权限ID列表
     */
    private String[] userIds;

    // ^^^^^^^^^^^ request parameter area END ^^^^^^^^^^^^^//

    // ~~~~~~~~~~~ response data area START ~~~~~~~~~~~~~//

    // NOTHINE

    // ^^^^^^^^^^^ response data area END ^^^^^^^^^^^^^^^^^^//
    //

    /**
     * execute Action执行方法
     * @return 页面返回结果码
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
    public String execute() {
        try {

            result = new CreateResult();
            if (userIds == null || userIds.length == 0) {
                // 对象为空，返回界面提示
                result = new CreateResult(ConstantEnum.FAILURE.toString(), getText("user.opt.fail"));
                return ConstantEnum.FAILURE.toString();
            }

            this.userService.resetPassword(userIds);
            result = new CreateResult(ConstantEnum.SUCCESS.toString(), getText("user.resetpass.success"));
            logger.info("重置用户密码成功!");

        } catch (Exception e) {
            logger.error("", e);

            this.addActionError(getText("user.opt.error"));
            result = new CreateResult(ConstantEnum.ERROR.toString(), getText("user.opt.error"));

            return ConstantEnum.ERROR.toString();
        }
        return ConstantEnum.SUCCESS.toString();
    }

    /**
     * 设置userService字段数据
     * @param userService The userService to set.
     */
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * 获取result字段数据
     * @return Returns the result.
     */
    public CreateResult getResult() {
        return result;
    }

    /**
     * 设置result字段数据
     * @param result The result to set.
     */
    public void setResult(CreateResult result) {
        this.result = result;
    }

    /**
     * 获取userIds字段数据
     * @return Returns the userIds.
     */
    public String[] getUserIds() {
        return userIds;
    }

    /**
     * 设置userIds字段数据
     * @param userIds The userIds to set.
     */
    public void setUserIds(String[] userIds) {
        this.userIds = userIds;
    }


}
