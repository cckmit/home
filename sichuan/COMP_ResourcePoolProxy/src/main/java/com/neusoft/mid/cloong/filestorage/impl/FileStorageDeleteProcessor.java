package com.neusoft.mid.cloong.filestorage.impl;

import com.CMCCCloud.www.schemas.CMCC.SOAP.common.v1.Authorization;
import com.CMCCCloud.www.schemas.CMCC.SOAP.common.v1.holders.ResponseHolder;
import com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.interfaces.FSServicePortProxy;
import com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.DeleteFileStorageReq;
import com.CMCCCloud.www.wsdl.CMCC.SOAP.fs.v1.local.DeleteFileStorageResp;
import com.neusoft.mid.cloong.BaseProcessor;
import com.neusoft.mid.cloong.CommonStatusCode;
import com.neusoft.mid.cloong.InterfaceResultCode;
import com.neusoft.mid.cloong.rpproxy.interfaces.filestorage.RPPFileStorageCreateResponse;
import com.neusoft.mid.cloong.rpproxy.interfaces.filestorage.RPPFileStorageDeleteReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.filestorage.RPPFileStorageDeleteResponse;
import com.neusoft.mid.iamp.api.RuntimeContext;
import com.neusoft.mid.iamp.logger.LogService;

public class FileStorageDeleteProcessor extends BaseProcessor {

	private static LogService logger = LogService.getLogger(FileStorageDeleteProcessor.class);
	
	@Override
	public String process(RuntimeContext reqCxt, RuntimeContext resp) {
		logger.info("删除分布式文件开始");		
		RPPFileStorageDeleteReq fsDeleteReq = (RPPFileStorageDeleteReq) reqCxt.getAttribute(RPPFileStorageDeleteReq.REQ_BODY);
		RPPFileStorageDeleteResponse rppFileStorageDeleteResponse = new RPPFileStorageDeleteResponse();

		if (null == fsDeleteReq.getFileStorageID() || "".equals(fsDeleteReq.getFileStorageID())) {
			assembleErrorResp(InterfaceResultCode.INPUT_PARR_ERROR, resp, rppFileStorageDeleteResponse);
			return FAILURE;
		}
		logger.info("需要删除的分布式文件id：="+fsDeleteReq.getFileStorageID());
		FSServicePortProxy fsServicePortProxy = new FSServicePortProxy();

		Authorization simpleRequestHeader = new Authorization();
		simpleRequestHeader.setTimestamp(this.getTimeStampgen().generateTimeStamp());
		simpleRequestHeader.setTransactionID(this.getTransactonGen()
				.generateTransactionId(fsDeleteReq.getResourcePoolId(), this.getTimeStampgen().generateTimeStamp()));
		simpleRequestHeader.setZoneID(fsDeleteReq.getResourcePoolPartId());
		ResponseHolder simpleResponseHeader = new ResponseHolder();
		try {
			DeleteFileStorageReq parameters = new DeleteFileStorageReq(fsDeleteReq.getFileStorageID());
			DeleteFileStorageResp deleteFileStorage = fsServicePortProxy.deleteFileStorage(parameters , simpleRequestHeader, simpleResponseHeader);
			String faultString = deleteFileStorage.getFaultString();
			rppFileStorageDeleteResponse.setFaultString(faultString);
			assebleRes(rppFileStorageDeleteResponse, resp);
		} catch (Exception e3) {
			logger.error(CommonStatusCode.INTERNAL_ERROR, "资源池代理系统内部错误，无法通过给定的资源池ID获取到资源池信息", e3);
			assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, resp, rppFileStorageDeleteResponse);
			return FAILURE;
		}
		logger.info("删除分布式文件结束");
		return SUCCESS;
	}

	private void assebleRes(RPPFileStorageDeleteResponse vmResp, RuntimeContext resp) {
		resp.setAttribute(RPPFileStorageCreateResponse.RESP_BODY, vmResp);
	}
}
