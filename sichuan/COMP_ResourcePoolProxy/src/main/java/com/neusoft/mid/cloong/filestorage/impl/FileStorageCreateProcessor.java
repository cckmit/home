package com.neusoft.mid.cloong.filestorage.impl;

import com.CMCCCloud.www.schemas.CMCC.SOAP.common.v1.Authorization;
import com.CMCCCloud.www.schemas.CMCC.SOAP.common.v1.holders.ResponseHolder;
import com.CMCCCloud.www.schemas.CMCC.SOAP.fs.v1.FileStorageParamArray;
import com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.interfaces.FSServicePortProxy;
import com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.CreatFileStorageReq;
import com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.CreatFileStorageResp;
import com.neusoft.mid.cloong.BaseProcessor;
import com.neusoft.mid.cloong.CommonStatusCode;
import com.neusoft.mid.cloong.InterfaceResultCode;
import com.neusoft.mid.cloong.rpproxy.interfaces.filestorage.RPPFileStorageCreateReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.filestorage.RPPFileStorageCreateResponse;
import com.neusoft.mid.iamp.api.RuntimeContext;
import com.neusoft.mid.iamp.logger.LogService;

public class FileStorageCreateProcessor extends BaseProcessor {

	private static LogService logger = LogService.getLogger(FileStorageCreateProcessor.class);
	
	@Override
	public String process(RuntimeContext reqCxt, RuntimeContext resp) {

		logger.info("创建分布式文件开始");		
		RPPFileStorageCreateReq fsCreateReq = (RPPFileStorageCreateReq) reqCxt.getAttribute(RPPFileStorageCreateReq.REQ_BODY);
		RPPFileStorageCreateResponse rppFileStorageCreateResponse = new RPPFileStorageCreateResponse();

		if (!validateInputPara(fsCreateReq)) {
			assembleErrorResp(InterfaceResultCode.INPUT_PARR_ERROR, resp, rppFileStorageCreateResponse);
			return FAILURE;
		}

		FSServicePortProxy fsServicePortProxy = new FSServicePortProxy();
		CreatFileStorageReq parameters = new CreatFileStorageReq();
		String appID = fsCreateReq.getAppID();
		int quotaSize = fsCreateReq.getQuotaSize();
		int sharetype = fsCreateReq.getSharetype();
		String appName = fsCreateReq.getAppName();
		String fileStorageName = fsCreateReq.getFileStorageName();
		String password = fsCreateReq.getPassword();
		logger.info("appID:="+appID+",quotaSize:="+quotaSize+",sharetype:="+sharetype+",appName:="+appName+",fileStorageName:="+fileStorageName);
		
		FileStorageParamArray fileStorageParamArray = new FileStorageParamArray(quotaSize, sharetype);
		parameters.setAppID(appID);
		parameters.setAppName(appName);
		parameters.setFileAdminUser("root");
		parameters.setFileStorageName(fileStorageName);
		parameters.setFileStorageParamArray(fileStorageParamArray);
		parameters.setFileStorageTemplateId(null);
		parameters.setParamFlag("1");
		parameters.setPassword("!QAZ2wsx");

		Authorization simpleRequestHeader = new Authorization();
		simpleRequestHeader.setTimestamp(this.getTimeStampgen().generateTimeStamp());
		simpleRequestHeader.setTransactionID(this.getTransactonGen()
				.generateTransactionId(fsCreateReq.getResourcePoolId(), this.getTimeStampgen().generateTimeStamp()));
		simpleRequestHeader.setZoneID(fsCreateReq.getResourcePoolPartId());
		ResponseHolder simpleResponseHeader = new ResponseHolder();
		try {
			CreatFileStorageResp creatFileStorage = fsServicePortProxy.creatFileStorage(parameters, simpleRequestHeader,simpleResponseHeader);
			String fileStorageID = creatFileStorage.getFileStorageID();
			String fsUrl = creatFileStorage.getFSUrl();
			String faultString = creatFileStorage.getFaultString();
			rppFileStorageCreateResponse.setFaultString(faultString);
			rppFileStorageCreateResponse.setFileStorageID(fileStorageID);
			rppFileStorageCreateResponse.setFSUrl(fsUrl);
			assebleRes(rppFileStorageCreateResponse, resp);
		} catch (Exception e3) {
			logger.error(CommonStatusCode.INTERNAL_ERROR, "资源池代理系统内部错误，无法通过给定的资源池ID获取到资源池信息", e3);
			assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, resp, rppFileStorageCreateResponse);
			return FAILURE;
		}
		logger.info("创建分布式文件结束");
		return SUCCESS;
	}

	private void assebleRes(RPPFileStorageCreateResponse vmResp, RuntimeContext resp) {
		resp.setAttribute(RPPFileStorageCreateResponse.RESP_BODY, vmResp);
	}

	private boolean validateInputPara(RPPFileStorageCreateReq req) {

		if (req.getAppID() == null) {
			logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查传参标识符属性AppID", null);
			return false;
		}
		if (req.getAppName() == null) {
			logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查流量协议类型属性AppName", null);
			return false;
		}
		if (req.getFileStorageName() == null) {
			logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查负载均衡入口IP地址属性FileStorageName", null);
			return false;
		}
		return true;
	}
}
