package com.neusoft.mid.cloong.vault.service;

import com.neusoft.mid.cloong.vault.beans.outside.base.VaultBaseResponse;
import com.neusoft.mid.cloong.vault.beans.outside.cancelRequest.CancelRequestRequest;
import com.neusoft.mid.cloong.vault.beans.outside.cancelRequest.CancelRequestResponse;
import com.neusoft.mid.cloong.vault.beans.outside.createAppRequest.CreateAppRequestRequest;
import com.neusoft.mid.cloong.vault.beans.outside.createAppRequest.CreateAppRequestResponse;
import com.neusoft.mid.cloong.vault.beans.outside.queryAppOperJKStatus.QueryAppOperJKStatusRequest;
import com.neusoft.mid.cloong.vault.beans.outside.queryAppOperJKStatus.QueryAppOperJKStatusResponse;
import com.neusoft.mid.cloong.vault.beans.outside.queryJKStatusByID.QueryJKStatusByIDRequest;
import com.neusoft.mid.cloong.vault.beans.outside.queryJKStatusByID.QueryJKStatusByIDResponse;
import com.neusoft.mid.cloong.vault.beans.outside.reSendJKPass.ReSendJKPassRequest;
import com.neusoft.mid.cloong.vault.beans.outside.reSendJKPass.ReSendJKPassResponse;
import com.neusoft.mid.cloong.vault.beans.outside.remoteAuth.RemoteAuthRequest;
import com.neusoft.mid.cloong.vault.beans.outside.remoteAuth.RemoteAuthResponse;
import com.neusoft.mid.cloong.vault.config.WsdlClientConfig;
import com.neusoft.mid.cloong.vault.utils.JaxbUtil;
import com.neusoft.mid.cloong.vault.utils.OtherUtil;
import com.neusoft.mid.cloong.vault.wsdl.services.BussinessSupportService;
import com.neusoft.mid.cloong.vault.wsdl.services.BussinessSupportServicePortType;
import com.neusoft.mid.cloong.web.action.BaseAction;
import com.neusoft.mid.iamp.logger.LogService;

import java.util.Date;

public class VaultService extends BaseAction {

    /**
     * 日志
     */
    private static LogService log = LogService.getLogger(VaultService.class);
    /**
     * 序列号
     */
    //private static final long serialVersionUID = 4196853216651241774L;
    private static final long serialVersionUID = 1L;

    //QueryAppOperJKStatusRequest request;Date time;

    /*@Override
    public String execute() {
        //queryAppOperJKStatus(request,time);
        return ConstantEnum.SUCCESS.toString();
    }*/
    public VaultBaseResponse queryAppOperJKStatus(QueryAppOperJKStatusRequest request,Date time){
        String requestXml = OtherUtil.getRequestXml(request, QueryAppOperJKStatusRequest.class);
        log.info("QueryAppOperJKStatusRequest:" + requestXml);
        BussinessSupportServicePortType servicePortType = this.getService();
        String responseXml = servicePortType.queryAppOperJKStatus(requestXml);
        log.info("QueryAppOperJKStatusResponse:" + responseXml);
        JaxbUtil reponseBinder = new JaxbUtil(QueryAppOperJKStatusResponse.class);
        QueryAppOperJKStatusResponse queryParamResponse = reponseBinder.fromXml(responseXml);
        log.info("QueryAppOperJKStatusResponse+fromXml:" + queryParamResponse);
        log.info("RequestID:" + queryParamResponse.getRequestID());
        /*if (StringUtils.isEmpty(queryParamResponse.getRequestID())) {
            OtherUtil.startTimeMap.put(request.getServiceUserName(), OtherUtil.getTime(time));
        } else {
            VaultRecordBean bean = new VaultRecordBean();
            bean.setRequestID(queryParamResponse.getRequestID());
            log.info("Approver:" + queryParamResponse.getApprover());
            log.info("AuthMode:" +  queryParamResponse.getAuthMode());

            *//*UpdateWrapper<VaultRecordBean> wrapper = new UpdateWrapper<VaultRecordBean>();
            wrapper.setEntity(bean);
            VaultRecordBean vaultRecordBean = vaultRecordMapper.selectOne(wrapper);*//*
            VaultRecordBean vaultRecordBean = null;
            if (vaultRecordBean != null) {
                List<AuthMode> authMode = new ArrayList<AuthMode>();
                List<String> approver = new ArrayList<String>();
                try {
                    authMode.add(AuthMode.value(vaultRecordBean.getAuthMode()));
                } catch (Exception e) {
                    log.info("AuthMode ADD Exception:" + e);
                }
                approver.addAll(Arrays.asList(vaultRecordBean.getSelectedApprover().split(",")));
                queryParamResponse.setAuthMode(authMode);
                queryParamResponse.setApprover(approver);
            }

        }*/
        return queryParamResponse;
    }

    public VaultBaseResponse queryJKStatusByID(QueryJKStatusByIDRequest request,Date time){

        String requestXml = OtherUtil.getRequestXml(request,QueryJKStatusByIDRequest.class);
        BussinessSupportServicePortType servicePortType = this.getService();
        log.info("queryJKStatusByIDRequest:" + requestXml);
        String responseXml = servicePortType.queryJKStatusByID(requestXml);
        log.info("queryJKStatusByIDResponse:" + responseXml);
        JaxbUtil reponseBinder = new JaxbUtil(QueryJKStatusByIDResponse.class);
        QueryJKStatusByIDResponse queryParamResponse = reponseBinder.fromXml(responseXml);
        log.info("RequestID:" + queryParamResponse.getRequestID());
        return queryParamResponse;

    }

    public VaultBaseResponse createAppRequest(CreateAppRequestRequest request,Date time){

        String requestXml = OtherUtil.getRequestXml(request,CreateAppRequestRequest.class);
        BussinessSupportServicePortType servicePortType = this.getService();
        log.info("CreateAppRequestRequest:" + requestXml);
        String responseXml = servicePortType.createAppRequest(requestXml);
        log.info("CreateAppRequestResponse:" + responseXml);
        JaxbUtil reponseBinder = new JaxbUtil(CreateAppRequestResponse.class);
        CreateAppRequestResponse queryParamResponse = reponseBinder.fromXml(responseXml);
        log.info("RequestID:" + queryParamResponse.getRequestID());
        return queryParamResponse;

    }


    public VaultBaseResponse remoteAuth(RemoteAuthRequest request,Date time){

        String requestXml = OtherUtil.getRequestXml(request,RemoteAuthRequest.class);
        BussinessSupportServicePortType servicePortType = this.getService();
        log.info("RemoteAuthRequest:" + requestXml);
        String responseXml = servicePortType.remoteAuth(requestXml);
        log.info("RemoteAuthResponse:" + responseXml);
        JaxbUtil reponseBinder = new JaxbUtil(RemoteAuthResponse.class);
        RemoteAuthResponse queryParamResponse = reponseBinder.fromXml(responseXml);
        log.info("ResultCode:" + queryParamResponse.getResultCode());
        return queryParamResponse;

    }


    public VaultBaseResponse reSendJKPass(ReSendJKPassRequest request,Date time){

        String requestXml = OtherUtil.getRequestXml(request,ReSendJKPassRequest.class);
        BussinessSupportServicePortType servicePortType = this.getService();
        log.info("ReSendJKPassRequest:" + requestXml);
        String responseXml = servicePortType.reSendJKPass(requestXml);
        log.info("ReSendJKPassResponse:" + responseXml);
        JaxbUtil reponseBinder = new JaxbUtil(ReSendJKPassResponse.class);
        ReSendJKPassResponse queryParamResponse = reponseBinder.fromXml(responseXml);
        log.info("RequestID:" + queryParamResponse.getRequestID());
        return queryParamResponse;

    }


    public VaultBaseResponse cancelRequest(CancelRequestRequest request,Date time){

        String requestXml = OtherUtil.getRequestXml(request,CancelRequestRequest.class);
        BussinessSupportServicePortType servicePortType = this.getService();
        log.info("CancelRequestRequest:" + requestXml);
        String responseXml = servicePortType.cancelRequest(requestXml);
        log.info("CancelRequestResponse:" + responseXml);
        JaxbUtil reponseBinder = new JaxbUtil(CancelRequestResponse.class);
        CancelRequestResponse queryParamResponse = reponseBinder.fromXml(responseXml);
        log.info("ResultCode:" + queryParamResponse.getResultCode());
        return queryParamResponse;

    }

    public BussinessSupportServicePortType getService(){
        BussinessSupportService bussinessSupportService = WsdlClientConfig.getInstance().getBussinessSupportService();
        BussinessSupportServicePortType bussinessSupportServicePortType = null;
        try {
            log.info("正在获取wsdl客户端！");
            bussinessSupportServicePortType = bussinessSupportService.getBussinessSupportServiceHttpPort();
        } catch (Exception e) {
            log.info("获取wsdl客户端失败" ,e);
        }
        return bussinessSupportServicePortType;
    }
}
