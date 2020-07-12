package com.neusoft.mid.cloong.vault.controller;

import com.neusoft.mid.cloong.identity.bean.RoleBean;
import com.neusoft.mid.cloong.identity.bean.UserAppBean;
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.identity.service.RoleSearchService;
import com.neusoft.mid.cloong.vault.beans.outside.createAppRequest.CreateAppRequestResponse;
import com.neusoft.mid.cloong.vault.beans.outside.enums.AuthMode;
import com.neusoft.mid.cloong.vault.beans.outside.localAuth.LocalAuthRequest;
import com.neusoft.mid.cloong.vault.beans.outside.localAuth.LocalAuthResponse;
import com.neusoft.mid.cloong.vault.beans.outside.queryAppOperJKStatus.QueryAppOperJKStatusRequest;
import com.neusoft.mid.cloong.vault.beans.outside.queryAppOperJKStatus.QueryAppOperJKStatusResponse;
import com.neusoft.mid.cloong.vault.beans.outside.queryJKStatusByID.QueryJKStatusByIDResponse;
import com.neusoft.mid.cloong.vault.beans.outside.reSendJKPass.ReSendJKPassResponse;
import com.neusoft.mid.cloong.vault.beans.outside.remoteAuth.RemoteAuthResponse;
import com.neusoft.mid.cloong.vault.config.WsdlClientConfig;
import com.neusoft.mid.cloong.vault.service.VaultService;
import com.neusoft.mid.cloong.vault.utils.JaxbUtil;
import com.neusoft.mid.cloong.vault.utils.OtherUtil;
import com.neusoft.mid.cloong.vault.wsdl.services.BussinessSupportService;
import com.neusoft.mid.cloong.vault.wsdl.services.BussinessSupportServicePortType;
import com.neusoft.mid.cloong.web.action.BaseAction;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.iamp.logger.LogService;
import com.neusoft.mid.cloong.vault.beans.outside.base.VaultBaseResponse;
import com.neusoft.mid.cloong.vault.beans.outside.createAppRequest.CreateAppRequestRequest;
import com.neusoft.mid.cloong.vault.beans.outside.queryJKStatusByID.QueryJKStatusByIDRequest;
import com.neusoft.mid.cloong.vault.beans.outside.reSendJKPass.ReSendJKPassRequest;
import com.neusoft.mid.cloong.vault.beans.outside.remoteAuth.RemoteAuthRequest;

import com.ultrapower.casp.common.datatran.data.user.UserInfo;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.annotations.JSON;
//import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VaultAction extends BaseAction {

    /**
     * 日志
     */
    private static LogService log = LogService.getLogger(VaultAction.class);
    /**
     * 序列号
     */
    //private static final long serialVersionUID = 4196853216651241774L;
    private static final long serialVersionUID = 1L;
    /**
     * 返回路径，用于在界面判断是否系统错误
     */
    private String resultPath = ConstantEnum.SUCCESS.toString();

    /**
     * 成功的接口响应码
     */
    private static final String SUCCEESS_CODE = "00000000";

    /**
     * 申请资源是否成功 默认成功
     */
    private String isSuccess = SUCCEESS_CODE;

    /**
     * 是否审批通过 默认通过
     */
    private static final String auditPass = "1";

    /**
     * 失败消息
     */
    private String message = "查询金库状态失败!";
    //QueryAppOperJKStatusRequest request;Date time;

    private static final String RET_FAILURE = "FAILURE";

    private static final String RET_SUCCESS = "SUCCESS";

    /**
     * 前台传入参数(创建)
     */
    private String auth_mode ;
    private String approver ;
    private String auth_timemode ;
    private String auth_time_val ;
    private String application_reason ;

    private String  requestID;
    /**
     * 前台传入参数(本地)
     */
    private String appli_account ;
    private String appli_pwd ;
    /**
     * 前台传入参数(远程)
     */
    private String authorization_code ;

    private VaultService vaultService;

    private RoleSearchService roleSearchService;

    private WsdlClientConfig wsdlClientConfig;

    private BussinessSupportService bussinessSupportService;

    private QueryAppOperJKStatusResponse queryParamResponse = new QueryAppOperJKStatusResponse();

    private QueryJKStatusByIDResponse queryParamResponseByID = new QueryJKStatusByIDResponse();

    private CreateAppRequestResponse createqueryParamResponse = new CreateAppRequestResponse();

    private RemoteAuthResponse remotequeryParamResponse = new RemoteAuthResponse();

    private LocalAuthResponse localqueryParamResponse = new LocalAuthResponse();

    private ReSendJKPassResponse reSendqueryParamResponse = new ReSendJKPassResponse();



    /*@Override
                public String execute() {
                    //queryAppOperJKStatus(request,time);
                    return ConstantEnum.SUCCESS.toString();
                }*/
    //"应用资源敏感操作金库状态查询接口
    public String queryAppOperJKStatus() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        log.info("request:" + request);
        log.info("response:" + response);

        String hostIPp = request.getHeader("Host");
        String[] newStr = hostIPp.split(":");
        String hostIP = newStr[0];

        log.info("hostIP:" + hostIP);

        UserInfo userInfo = getFourAUser();
        if (userInfo==null){
            log.info("未获取到4A用户信息！！！");
            return ConstantEnum.ERROR.toString();
        }else {
            String userId = userInfo.getAccountID();
            log.info("UserInfo+AccountID(userId):" + userInfo.getAccountID());
            log.info("TempKey:" + userInfo.getTempKey());
            log.info("UserInfo+DescIp:" + userInfo.getDescIp());
            UserBean user = queryUserInfo(userId);
            log.info("UserBean+UserName:" + user.getUserName());

            QueryAppOperJKStatusRequest requestV = new QueryAppOperJKStatusRequest();
            requestV.setServiceUserName("ty4ajk");
            requestV.setServicePwd("ty4ajk");

            requestV.setRootTicket(userInfo.getTempKey());
            //ResNum为properties里面的appCode
            requestV.setResNum("ZQYGL");
            requestV.setAppAccount(userInfo.getAccountID());
            requestV.setFunctionCode("230001");
            requestV.setOperationCode("1001");
            requestV.setUserIP(hostIP);

            String requestXml = OtherUtil.getRequestXml(requestV, QueryAppOperJKStatusRequest.class);
            log.info("QueryAppOperJKStatusRequest:" + requestXml);
            BussinessSupportServicePortType servicePortType = getService();
            String responseXml = servicePortType.queryAppOperJKStatus(requestXml);
            log.info("QueryAppOperJKStatusResponse:" + responseXml);
            JaxbUtil reponseBinder = new JaxbUtil(QueryAppOperJKStatusResponse.class);
            queryParamResponse = reponseBinder.fromXml(responseXml);
            log.info("QueryAppOperJKStatusResponse+fromXml:" + queryParamResponse);
            log.info("RequestID(Response):" + queryParamResponse.getRequestID());

            log.info("ResultCode:" + queryParamResponse.getResultCode());
            //queryParamResponse.getResultCode();
            //return queryParamResponse;
            //return vaultService.queryAppOperJKStatus(requestV,new Date());
            return ConstantEnum.SUCCESS.toString();
        }
    }


    //根据金库申请id查询金库状态接口
    public String queryJKStatusByID() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        log.info("request:" + request);
        log.info("response:" + response);

        String hostIPp = request.getHeader("Host");
        String[] newStr = hostIPp.split(":");
        String hostIP = newStr[0];
        log.info("hostIP:" + hostIP);

        QueryJKStatusByIDRequest requestV = new QueryJKStatusByIDRequest();
        requestV.setServiceUserName("ty4ajk");
        requestV.setServicePwd("ty4ajk");
        //UserInfo userInfo = getFourAUser();
        //String userId = userInfo.getAccountID();
        log.info("RequestID+requestID:" + requestID);
        requestV.setRequestID(requestID);
        requestV.setUserIP(hostIP);

        String requestXml = OtherUtil.getRequestXml(requestV,QueryJKStatusByIDRequest.class);
        BussinessSupportServicePortType servicePortType = getService();
        log.info("queryJKStatusByIDRequest:" + requestXml);
        String responseXml = servicePortType.queryJKStatusByID(requestXml);
        log.info("queryJKStatusByIDResponse:" + responseXml);
        JaxbUtil reponseBinder = new JaxbUtil(QueryJKStatusByIDResponse.class);
        queryParamResponseByID = reponseBinder.fromXml(responseXml);
        log.info("RequestID(Response):" + queryParamResponseByID.getRequestID());

        log.info("ResultCode:" + queryParamResponseByID.getResultCode());
        //return queryParamResponse;
        //return vaultService.queryJKStatusByID(requestV,new Date());
        return ConstantEnum.SUCCESS.toString();
    }


    //应用资源敏感操作金库申请创建接口
    public String createAppRequest() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();

        CreateAppRequestRequest requestV = new CreateAppRequestRequest();

        //bussinessSupportServiceInit();

        //UserInfo userInfo = getFourAUser();
        UserInfo userInfo = getFourAUser();

        String hostIPp = request.getHeader("Host");
        String[] newStr = hostIPp.split(":");
        String hostIP = newStr[0];
        log.info("hostIP:" + hostIP);

        requestV.setServiceUserName("ty4ajk");
        requestV.setServicePwd("ty4ajk");
        requestV.setRootTicket(userInfo.getTempKey());
        requestV.setAppAccount(userInfo.getAccountID());
        //properties里面的appCode
        requestV.setResNum("ZQYGL");
        requestV.setFunctionCode("230001");
        requestV.setOperationCode("1001");

        //List<AuthMode> authMode = new ArrayList<AuthMode>();
        List<String> authMode = new ArrayList<String>();
        log.info("auth_mode:" + auth_mode);
        authMode.add(auth_mode);

        requestV.setAuthMode(authMode);
        log.info("application_reason:" + application_reason);
        requestV.setApplyReason(application_reason);
        log.info("auth_timemode:" + auth_timemode);
        requestV.setUserTimes(auth_timemode);
        log.info("auth_time_val:" + auth_time_val);
        requestV.setDuration(auth_time_val);

        requestV.setUserIP(hostIP);

        List<String> approver_list = new ArrayList<String>();
        log.info("approver:" + approver);
        approver_list.add(approver);
        requestV.setSelectedApprover(approver_list);

        //以下两个参数为空即可
        requestV.setWordOrderID("");
        requestV.setWorkOrderType("");

        String requestXml = OtherUtil.getRequestXml(requestV,CreateAppRequestRequest.class);
        BussinessSupportServicePortType servicePortType = getService();
        log.info("CreateAppRequestRequest:" + requestXml);
        String responseXml = servicePortType.createAppRequest(requestXml);
        log.info("CreateAppRequestResponse:" + responseXml);
        JaxbUtil reponseBinder = new JaxbUtil(CreateAppRequestResponse.class);
        createqueryParamResponse = reponseBinder.fromXml(responseXml);
        log.info("RequestID(Response):" + createqueryParamResponse.getRequestID());

        log.info("ResultCode:" + createqueryParamResponse.getResultCode());
        //return queryParamResponse;
        //return vaultService.createAppRequest(requestV,new Date());
        return ConstantEnum.SUCCESS.toString();
    }


    //远程授权认证接口
    public String remoteAuth() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();

        String hostIPp = request.getHeader("Host");
        String[] newStr = hostIPp.split(":");
        String hostIP = newStr[0];
        log.info("hostIP:" + hostIP);

        RemoteAuthRequest requestV = new RemoteAuthRequest();

        requestV.setServiceUserName("ty4ajk");
        requestV.setServicePwd("ty4ajk");
        log.info("RequestID+requestID:" + requestID);
        requestV.setRequestID(requestID);
        requestV.setPassCode(authorization_code);
        requestV.setUserIP(hostIP);

        String requestXml = OtherUtil.getRequestXml(request,RemoteAuthRequest.class);
        BussinessSupportServicePortType servicePortType = getService();
        log.info("RemoteAuthRequest:" + requestXml);
        String responseXml = servicePortType.remoteAuth(requestXml);
        log.info("RemoteAuthResponse:" + responseXml);
        JaxbUtil reponseBinder = new JaxbUtil(RemoteAuthResponse.class);
        remotequeryParamResponse = reponseBinder.fromXml(responseXml);

        log.info("ResultCode:" + remotequeryParamResponse.getResultCode());
        //return queryParamResponse;
        //return vaultService.remoteAuth(requestV,new Date());
        return ConstantEnum.SUCCESS.toString();
    }

    //本地授权认证接口
    public String localAuth() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();

        String hostIPp = request.getHeader("Host");
        String[] newStr = hostIPp.split(":");
        String hostIP = newStr[0];
        log.info("hostIP:" + hostIP);

        LocalAuthRequest requestV = new LocalAuthRequest();

        requestV.setServiceUserName("ty4ajk");
        requestV.setServicePwd("ty4ajk");
        log.info("RequestID+requestID:" + requestID);
        requestV.setRequestID(requestID);

        log.info("appli_account:" + appli_account);
        requestV.setUserID(appli_account);
        log.info("appli_pwd:" + appli_pwd);
        requestV.setPassCode(appli_pwd);

        requestV.setUserIP(hostIP);

        String requestXml = OtherUtil.getRequestXml(request,RemoteAuthRequest.class);
        BussinessSupportServicePortType servicePortType = getService();
        log.info("LocalAuthResponse:" + requestXml);
        String responseXml = servicePortType.remoteAuth(requestXml);
        log.info("LocalAuthResponse:" + responseXml);
        JaxbUtil reponseBinder = new JaxbUtil(LocalAuthResponse.class);
        localqueryParamResponse = reponseBinder.fromXml(responseXml);

        log.info("ResultCode:" + localqueryParamResponse.getResultCode());
        //return queryParamResponse;
        //return vaultService.remoteAuth(requestV,new Date());
        return ConstantEnum.SUCCESS.toString();
    }


    //金库口令重发接口
    public String reSendJKPass() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();

        String hostIPp = request.getHeader("Host");
        String[] newStr = hostIPp.split(":");
        String hostIP = newStr[0];
        log.info("hostIP:" + hostIP);

        ReSendJKPassRequest requestV = new ReSendJKPassRequest();

        UserInfo userInfo = getFourAUser();

        requestV.setServiceUserName("ty4ajk");
        requestV.setServicePwd("ty4ajk");
        log.info("RequestID+requestID:" + requestID);
        requestV.setRequestID(requestID);
        requestV.setUserIP(hostIP);

        String requestXml = OtherUtil.getRequestXml(request,ReSendJKPassRequest.class);
        BussinessSupportServicePortType servicePortType = getService();
        log.info("ReSendJKPassRequest:" + requestXml);
        String responseXml = servicePortType.reSendJKPass(requestXml);
        log.info("ReSendJKPassResponse:" + responseXml);
        JaxbUtil reponseBinder = new JaxbUtil(ReSendJKPassResponse.class);
        reSendqueryParamResponse = reponseBinder.fromXml(responseXml);
        log.info("RequestID(Response):" + reSendqueryParamResponse.getRequestID());

        log.info("ResultCode:" + reSendqueryParamResponse.getResultCode());
        //return queryParamResponse;
        //return vaultService.reSendJKPass(requestV,new Date());
        return ConstantEnum.SUCCESS.toString();
    }



    /*public VaultBaseResponse cancelRequest() throws Exception {
        CancelRequestRequest requestV = new CancelRequestRequest();
        return vaultService.cancelRequest(requestV,new Date());
    }*/

    /*private UserInfo getFourAUser(HttpServletRequest request,HttpServletResponse response){

        LoginUtil.getInstance().init("/comm_conf/other/casp_client_config.properties", "/comm_conf/other/casp_client_config.xml");
        log.info("LoginUtil.getInstance().isEnable()" + LoginUtil.getInstance().isEnable());
        UserInfo userInfo = new UserInfo();

        if (LoginUtil.getInstance().isEnable()) {
            if (LoginUtil.getInstance().checkTicket(request)) {
                String strTic = LoginUtil.getInstance().getTicket(request);
                log.info("strTic:" + strTic);
                TransferTicket ticket = LoginUtil.getInstance().analysTicket(strTic);
                if (ticket != null && ticket.getRetCode() != null && ResultCode.RESULT_OK.equals(ticket.getRetCode())) {
                    userInfo = LoginUtil.getInstance().qryUserByTicket(ticket);
                }
            }
        }
        return userInfo;
    }*/
    @JSON(serialize=false)
    public BussinessSupportServicePortType getService() {
        //BussinessSupportService bussinessSupportService = WsdlClientConfig.getInstance().getBussinessSupportService();
        //BussinessSupportService bussinessSupportService = wsdlClientConfig.getBussinessSupportService();
         /*if (bussinessSupportService == null){
             try {
                 bussinessSupportService = connect();
             }catch (Exception e)
             {
                 log.info("connect---ex",e);
             }
         }*/
        BussinessSupportService bussinessSupportService = new BussinessSupportService();
        log.info("bussinessSupportService:" + bussinessSupportService);
        BussinessSupportServicePortType bussinessSupportServicePortType = null;
        try {
            log.info("正在获取wsdl客户端！");
            bussinessSupportServicePortType = bussinessSupportService.getBussinessSupportServiceHttpPort();
        } catch (Exception e) {
            log.info("获取wsdl客户端失败" ,e);
        }
        return bussinessSupportServicePortType;
    }
    @JSON(serialize=false)
    private UserInfo getFourAUser(){

        UserInfo userInfo = (UserInfo) ServletActionContext.getContext().getSession().get(
                SessionKeys.fourAUserInfo);
        if(userInfo==null){
            log.info("SessionKeys中没有4A用户信息！！！");
        }
        log.info("userInfo+++AccountID" +userInfo.getAccountID());
        return userInfo;
    }


    private UserBean queryUserInfo(String userId)throws SQLException {
        log.info("*****queryUserInfo的用户ID="+userId);
        UserBean userInfo = null;
        if (!StringUtils.isEmpty(userId)) {
            userInfo = (UserBean) this.ibatisDAO.getSingleRecord("getSingleUser", userId);
            if (userInfo!=null){
                List<RoleBean> roleList = this.roleSearchService.searchRolePermRelation(userId);
                userInfo.setRoles(roleList);
                List<UserAppBean> app = (List<UserAppBean>) ibatisDAO.getData("queryBingAppsbyUserId", userId);
                List<String> appList = new ArrayList<String>();
                if (app.size() > 0) {
                    for (UserAppBean appInfo : app) {
                        appList.add(appInfo.getAppId());
                    }
                }
                userInfo.setAppIdList(appList);
            }
        }
        return userInfo;
    }

    public void bussinessSupportServiceInit() {

        try {
            connect();
        } catch (Exception ex) {
            log.error("bussinessSupportServiceInit---error",ex);
            ExecutorService executorService = Executors.newFixedThreadPool(1);
            executorService.execute(this.reconnectTask());
        }
    }

    private BussinessSupportService connect() throws Exception {
        URL url = null;

        Properties prop = new Properties();
        InputStream in = new VaultAction().getClass().getResourceAsStream("/comm_conf/other/Properties.properties");
        //F:\workspace_idea522\cloong\COMP_Parent_SiChuan_34Phase\COMP_Portal\src\main\resources\comm_conf\other\Properties.properties
        try {
            prop.load(in);
        } catch (IOException e) {
            log.info("InputStream 解析失败" + e);
            e.printStackTrace();
        }
        //String httpHostlhx = prop.getProperty("httpHostlhx");
        String wsdlUrl = prop.getProperty("wsdl.url");
        //String wsdlUrl = ResourceBundle.getBundle("application-config", Locale.getDefault()).getString("wsdl.url");
        log.info("-------wsdl连接开始 -----" + wsdlUrl);
        url = new URL(wsdlUrl);
        try {
            bussinessSupportService = new BussinessSupportService(url);
        }catch (Exception e){
            log.info("bussinessSupportService---ex",e);
        }

        log.info("-------wsdl连接成功 -----");
        return bussinessSupportService;
    }

    public Runnable reconnectTask() {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(15000);
                        log.info("-------wsdl连接异常,重连开始 -----");
                        connect();
                        log.info("-------wsdl重连成功 -----");
                        break;
                    } catch (Exception e) {
                        log.info("-------wsdl重连异常,稍后继续连接 -----");
                    }
                }

            }
        };
        return task;
    }
    @JSON(serialize=false)
    public VaultService getVaultService() {
        return vaultService;
    }

    public void setVaultService(VaultService vaultService) {
        this.vaultService = vaultService;
    }
    @JSON(serialize=false)
    public RoleSearchService getRoleSearchService() {
        return roleSearchService;
    }

    public void setRoleSearchService(RoleSearchService roleSearchService) {
        this.roleSearchService = roleSearchService;
    }
    @JSON(serialize=false)
    public WsdlClientConfig getWsdlClientConfig() {
        return wsdlClientConfig;
    }

    public void setWsdlClientConfig(WsdlClientConfig wsdlClientConfig) {
        this.wsdlClientConfig = wsdlClientConfig;
    }

    /*public BussinessSupportService getBussinessSupportService() {
        return bussinessSupportService;
    }

    public void setBussinessSupportService(BussinessSupportService bussinessSupportService) {
        this.bussinessSupportService = bussinessSupportService;
    }*/

    public String getAuth_mode() {
        return auth_mode;
    }

    public void setAuth_mode(String auth_mode) {
        this.auth_mode = auth_mode;
    }

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }

    public String getAuth_timemode() {
        return auth_timemode;
    }

    public void setAuth_timemode(String auth_timemode) {
        this.auth_timemode = auth_timemode;
    }

    public String getAuth_time_val() {
        return auth_time_val;
    }

    public void setAuth_time_val(String auth_time_val) {
        this.auth_time_val = auth_time_val;
    }

    public String getApplication_reason() {
        return application_reason;
    }

    public void setApplication_reason(String application_reason) {
        this.application_reason = application_reason;
    }

    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public String getAppli_account() {
        return appli_account;
    }

    public void setAppli_account(String appli_account) {
        this.appli_account = appli_account;
    }

    public String getAppli_pwd() {
        return appli_pwd;
    }

    public void setAppli_pwd(String appli_pwd) {
        this.appli_pwd = appli_pwd;
    }

    public String getAuthorization_code() {
        return authorization_code;
    }

    public void setAuthorization_code(String authorization_code) {
        this.authorization_code = authorization_code;
    }

    public QueryAppOperJKStatusResponse getQueryParamResponse() {
        return queryParamResponse;
    }

    public void setQueryParamResponse(QueryAppOperJKStatusResponse queryParamResponse) {
        this.queryParamResponse = queryParamResponse;
    }

    public QueryJKStatusByIDResponse getQueryParamResponseByID() {
        return queryParamResponseByID;
    }

    public void setQueryParamResponseByID(QueryJKStatusByIDResponse queryParamResponseByID) {
        this.queryParamResponseByID = queryParamResponseByID;
    }

    public CreateAppRequestResponse getCreatequeryParamResponse() {
        return createqueryParamResponse;
    }

    public void setCreatequeryParamResponse(CreateAppRequestResponse createqueryParamResponse) {
        this.createqueryParamResponse = createqueryParamResponse;
    }

    public RemoteAuthResponse getRemotequeryParamResponse() {
        return remotequeryParamResponse;
    }

    public void setRemotequeryParamResponse(RemoteAuthResponse remotequeryParamResponse) {
        this.remotequeryParamResponse = remotequeryParamResponse;
    }

    public LocalAuthResponse getLocalqueryParamResponse() {
        return localqueryParamResponse;
    }

    public void setLocalqueryParamResponse(LocalAuthResponse localqueryParamResponse) {
        this.localqueryParamResponse = localqueryParamResponse;
    }

    public ReSendJKPassResponse getReSendqueryParamResponse() {
        return reSendqueryParamResponse;
    }

    public void setReSendqueryParamResponse(ReSendJKPassResponse reSendqueryParamResponse) {
        this.reSendqueryParamResponse = reSendqueryParamResponse;
    }
}
