package com.neusoft.mid.cloong.identity.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.neusoft.mid.cloong.common.mybatis.BatchVO;
import com.neusoft.mid.cloong.common.util.Base64;
import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.common.util.MD5;
import com.neusoft.mid.cloong.common.util.PasswordTool;
import com.neusoft.mid.cloong.identity.bean.RoleBean;
import com.neusoft.mid.cloong.identity.bean.UserAppBean;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.identity.bean.core.UserStatus;
import com.neusoft.mid.cloong.identity.bean.query.QueryUserInfo;
import com.neusoft.mid.cloong.identity.exception.UserOperationException;
import com.neusoft.mid.cloong.identity.service.base.IdentityBaseService;
import com.neusoft.mid.iamp.logger.LogService;
import com.neusoft.mid.cloong.common.util.MailService;

/**
 * 用户管理服务类
 * @author <a href="mailto:feng.jian@neusoft.com">feng jian </a>
 * @version $Revision 1.1 $ 2013-3-6 上午10:07:54
 */
public class UserService extends IdentityBaseService {

    /**
     * 生成的随机密码长度
     */
    public static final int genPassLength = 8;

    /**
     * 日志打印对象
     */
    private static LogService logger = LogService.getLogger(UserService.class);

    /**
     * 常量:email显示文字
     */
    public static final String EMAIL_CHANGE = "您的云计算运营系统帐号绑定的邮箱变更为:";

    /**
     * 常量:mobile显示文字
     */
    public static final String MOBILE_CHANGE = "您的云计算运营系统帐号绑定的手机变更为:";

    /**
     * 邮件服务
     */
    private MailService mailService;

    /**
     * 发件人地址
     */
    private String fromMailAddress;

    /**
     * queryUserList 查询用户列表
     * @param selectUser 用户查询条件
     * @return 用户列表
     * @throws SQLException SQL异常
     */
    @SuppressWarnings("unchecked")
    public List<UserBean> queryUserList(QueryUserInfo selectUser) throws SQLException {
        List<UserBean> res = this.ibatisDAO.getData("queryUserInfos", selectUser);
        return res;
    }

    /**
     * updateUser 保存用户信息
     * @param user 需要保存的用户信息
     * @throws SQLException SQL异常
     * @throws UserOperationException 用户管理操作异常
     */
    public void updateUser(UserBean user) throws SQLException, UserOperationException {

        isHaveAttrUser(user);

        UserBean oldUserInfo = this.queryUserById(user.getUserId());
        checkEmailMobileChange(user, oldUserInfo);

        List<BatchVO> voList = new ArrayList<BatchVO>();
        // 更新用户基本信息
        user.setUpdateTime(DateParse.generateDateFormatyyyyMMddHHmmss());// 设置更新时间
        BatchVO updateUserVO = new BatchVO(BatchVO.OPERATION_MOD, "updateUserInfo", user);
        voList.add(updateUserVO);

        // 删除用户所有的绑定关系
        BatchVO delUserRoleRelationVO = new BatchVO(BatchVO.OPERATION_DEL, "deleteAllUserRoleRelation", user);
        voList.add(delUserRoleRelationVO);

        // 将用户和角色的新绑定关系入库
        for (RoleBean role : user.getRoles()) {
            Map<String, String> daoParameter = new HashMap<String, String>();
            daoParameter.put("userId", user.getUserId());
            daoParameter.put("roleId", role.getRoleId());

            BatchVO vo = new BatchVO(BatchVO.OPERATION_ADD, "insertUserRoleRelation", daoParameter);
            voList.add(vo);
        }
        if(user.getApps()!=null && user.getApps().size()!=0){//绑定业务.
        	//先删除原有的绑定关系
        	UserAppBean app =new UserAppBean();
        	app.setUserId(user.getUserId());
        	BatchVO delvo = new BatchVO(BatchVO.OPERATION_DEL, "delUserApp", app);
        	voList.add(delvo);
        	for (UserAppBean userAppBean : user.getApps()) {
                BatchVO vo = new BatchVO(BatchVO.OPERATION_ADD, "insertUserApp", userAppBean);
                voList.add(vo);
            }
        }
        this.ibatisDAO.updateBatchData(voList);
    }

    /**
     * changeUserStatus 更新用户的状态信息
     * @param userIds 要更新状态的用户ID列表
     * @param userStatus 要更新的状态
     * @throws SQLException SQL异常
     * @throws UserOperationException 用户操作失败exception
     */
    public void changeUserStatus(String[] userIds, final UserStatus userStatus) throws SQLException,
            UserOperationException {

        // 校验是否为待审批用户
        isHaveStatusUser(userIds, UserStatus.EXAMINATION);
        // 校验用户
        isHaveStatusUser(userIds, userStatus);

        List<BatchVO> voList = new ArrayList<BatchVO>();
        for (final String userId : userIds) {

            // 更新用户基本信息
            UserBean user = new UserBean();
            user.setUserId(userId);
            user.setStatus(userStatus);
            user.setUpdateTime(DateParse.generateDateFormatyyyyMMddHHmmss());// 设置更新时间
            BatchVO updateUserVO = new BatchVO(BatchVO.OPERATION_MOD, "updateUserInfo", user);
            voList.add(updateUserVO);
        }

        this.ibatisDAO.updateBatchData(voList);

        for (final String userId : userIds) {
            // 更新用户基本信息
            UserBean user = this.queryUserById(userId);
            this.sendNoticeToUser(user, "[云资源运营管理平台]账号状态变更", "您的帐号已被管理员设置为" + userStatus.getDesc()
                    + "状态，如果有任何问题，请及时联系管理员。");
        }

    }

    /**
     * resetPassword 重置用户的密码
     * @param userIds 要被重置密码的用户ID
     * @throws SQLException SQL异常
     */
    public void resetPassword(String[] userIds) throws SQLException {

        final Map<String, String> userPassMap = new HashMap<String, String>();

        List<BatchVO> voList = new ArrayList<BatchVO>();
        for (final String userId : userIds) {
            // 更新用户基本信息
            UserBean user = new UserBean() {
                {
                    String pass = doWithPasswd(this);
                    this.setUserId(userId);
                    userPassMap.put(userId, pass);
                }
            };
            user.setUpdateTime(DateParse.generateDateFormatyyyyMMddHHmmss());// 设置更新时间
            BatchVO updateUserVO = new BatchVO(BatchVO.OPERATION_MOD, "updateUserInfo", user);
            voList.add(updateUserVO);
        }

        this.ibatisDAO.updateBatchData(voList);

        for (final String userId : userIds) {
            // 更新用户基本信息
            UserBean user = this.queryUserById(userId);
            this.sendNoticeToUser(user, "[云资源运营管理平台]您的密码已经被重置", "您的密码已经被管理员重置,新密码为[" + userPassMap.get(userId)
                    + "],请登录后及时修改。<br>如果有任何问题，请及时联系管理员。");
        }
    }

    /**
     * createUser 创建用户
     * @param user 需要创建的用户信息
     * @throws SQLException SQL异常
     * @throws UserOperationException 用户操作异常
     */
    public void createUser(UserBean user) throws SQLException, UserOperationException {

        isHavenUserId(user.getUserId());

        isHaveAttrUser(user);

        List<BatchVO> voList = new ArrayList<BatchVO>();

        String password = doWithPasswd(user);

        // 添加用户基本信息
        String dateTime = DateParse.generateDateFormatyyyyMMddHHmmss();
        user.setCreateTime(dateTime);// 设置创建日期
        user.setUpdateTime(dateTime);// 设置更新日期

        BatchVO createUserVO = new BatchVO(BatchVO.OPERATION_ADD, "insertUserInfo", user);
        voList.add(createUserVO);

        // 将用户和角色的新绑定关系入库
        for (RoleBean role : user.getRoles()) {
            Map<String, String> daoParameter = new HashMap<String, String>();
            daoParameter.put("userId", user.getUserId());
            daoParameter.put("roleId", role.getRoleId());

            BatchVO vo = new BatchVO(BatchVO.OPERATION_ADD, "insertUserRoleRelation", daoParameter);
            voList.add(vo);
        }
        for (UserAppBean userAppBean : user.getApps()) {
            BatchVO vo = new BatchVO(BatchVO.OPERATION_ADD, "insertUserApp", userAppBean);
            voList.add(vo);
        }
        this.ibatisDAO.updateBatchData(voList);

        sendNoticeToUser(user, "[云资源运营管理平台]账号创建成功", "您的帐号密码已经生成,为[" + password + "],请登录后及时修改。<br>如果有任何问题，请及时联系管理员。");
    }

    /**
     * queryUserById 通过ID获取用户详细信息
     * @param userId 查询的用户ID
     * @return 返回用户信息
     * @throws SQLException 数据库异常
     */
    public UserBean queryUserById(String userId) throws SQLException {
        UserBean user = (UserBean) this.ibatisDAO.getSingleRecord("queryUserInfoById", userId);
        List<RoleBean> roleList = this.ibatisDAO.getData("queryUserRoleByUserId", userId);
        user.setRoles(roleList);
        return user;
    }

    /**
     * doWithPasswd 处理新用户的密码信息
     * @param user 要生成新密码的用户
     * @return 生成的密码
     */
    private String doWithPasswd(UserBean user) {
        //String passwd = PasswordTool.getPassword(8);
        String passwd = "123456";
        String encodePass = Base64.encode(MD5.getMd5Bytes(passwd));
        user.setPassword(encodePass);
        return passwd;
    }

    /**
     * sendNoticeToUser 发送通知给用户
     * @param user 接受消息的用户
     * @param title 邮件通知时的邮件标题
     * @param message 发送的消息内容
     */
    private void sendNoticeToUser(UserBean user, String title, String message) {
        // TODO 发送短信或邮件通知
        // FIXME 为了重庆项目，屏蔽了此通知

        logger.info("向用户[" + user.getUserId() + "]发送通知,内容为:{===============\n" + message + "}\n================");

        // 发送邮件
        try {
            // mailService.sendMails(this.fromMailAddress, user.getEmail(), title, message);
        } catch (Exception e) {
            logger.error(message, e);
        }
    }

    /**
     * checkEmailMobileChange 检查编辑用户信息时,手机和邮箱是否发生变化,发生变化会向用户发送通知
     * @param user 发生变更的用户信息
     * @param oldUserInfo 发生变更前的用户信息
     */
    private void checkEmailMobileChange(UserBean user, UserBean oldUserInfo) {

        StringBuilder builder = new StringBuilder();
        boolean isChange = false;
        if (!oldUserInfo.getMobile().equals(user.getMobile())) {
            builder.append(MOBILE_CHANGE).append(user.getMobile()).append(" ");
            isChange = true;
        }

        if (!oldUserInfo.getEmail().equals(user.getEmail())) {
            builder.append(EMAIL_CHANGE).append(user.getEmail()).append(" ");
            isChange = true;
        }
        if (isChange) {
            logger.info("用户" + user.getUserId() + "的手机或EMAIL发生变化.");
            this.sendNoticeToUser(user, "[云资源运营管理平台]您的账号手机/邮箱发生变更", builder.toString() + ",如果有任何问题，请及时联系管理员。");
            this.sendNoticeToUser(oldUserInfo, "[云资源运营管理平台]您的账号手机/邮箱发生变更", builder.toString() + ",如果有任何问题，请及时联系管理员。");
        }
    }

    /**
     * isHavenUserId 验证用户ID是否存在
     * @param userId 查询的用户ID
     * @throws SQLException SQL异常
     * @throws UserOperationException 用户操作异常
     */
    private void isHavenUserId(String userId) throws SQLException, UserOperationException {
        Map<String, Object> queryHaveUserIdInfo = new HashMap<String, Object>();
        queryHaveUserIdInfo.put("userIds", new String[] { userId });

        int haveUserIdNum = this.ibatisDAO.getCount("countUserByIdsAndStatus", queryHaveUserIdInfo);
        if (haveUserIdNum > 0) {
            throw new UserOperationException("用户帐号已存在!");
        }
    }

    /**
     * verification 校验用户是否处于审批状态
     * @param userIds 用户的ID
     * @param status 用户的状态
     * @throws SQLException SQL异常
     * @throws UserOperationException 用户操作异常
     */
    private void isHaveStatusUser(String[] userIds, UserStatus status) throws SQLException, UserOperationException {
        Map<String, Object> queryObj = new HashMap<String, Object>();
        queryObj.put("userIds", userIds);
        queryObj.put("status", status);

        int examinationUserNum = this.ibatisDAO.getCount("countUserByIdsAndStatus", queryObj);
        if (examinationUserNum > 0) {
            throw new UserOperationException("对" + status.getDesc() + "状态的用户不能执行此操作!");
        }
    }

    /**
     * verification 校验用户是否处于审批状态
     * @param userInfo 用户的ID
     * @throws SQLException SQL异常
     * @throws UserOperationException 用户操作异常
     */
    private void isHaveAttrUser(UserBean userInfo) throws SQLException, UserOperationException {
        int examinationUserNum = this.ibatisDAO.getCount("countUserByMobileAndMail", userInfo);
        if (examinationUserNum > 0) {
            throw new UserOperationException("手机或邮箱已经存在!");
        }
    }

    /**
     * 设置mailService字段数据
     * @param mailService The mailService to set.
     */
    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }

    /**
     * 设置fromMailAddress字段数据
     * @param fromMailAddress The fromMailAddress to set.
     */
    public void setFromMailAddress(String fromMailAddress) {
        this.fromMailAddress = fromMailAddress;
    }

}
