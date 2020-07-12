package com.neusoft.mid.cloong.snapshots;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import com.neusoft.mid.cloong.BaseProcessor;
import com.neusoft.mid.cloong.CommonStatusCode;
import com.neusoft.mid.cloong.InterfaceResultCode;
import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.snapshots.RPPRecoverySnapshotReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.snapshots.RPPRecoverySnapshotResp;
import com.neusoft.mid.grains.commons.route.exception.ServiceStopException;
import com.neusoft.mid.grains.commons.route.exception.UnmatchException;
import com.neusoft.mid.iamp.api.RuntimeContext;
import com.neusoft.mid.iamp.logger.LogService;

public class SnapshotRecoveryProcessor extends BaseProcessor{
	 private static LogService logger = LogService.getLogger(SnapshotApplyProcessor.class);
	    /**
	     * Snaopshot恢复接口
	     */
	    private SnapshotRecovery snapshotRecovery;


	    @Override
	    public String process(RuntimeContext reqCxt, RuntimeContext respCxt) {

	    	RPPRecoverySnapshotReq req = (RPPRecoverySnapshotReq) reqCxt.getAttribute(RPPRecoverySnapshotReq.REQ_BODY);
	    	RPPRecoverySnapshotResp resp = new RPPRecoverySnapshotResp();

	        // 校验
	        if (!validateInputPara(req)) {
	            assembleErrorResp(InterfaceResultCode.INPUT_PARR_ERROR, respCxt, resp);
	            return FAILURE;
	        }

	        SnapshotRecoveryReq recoveryReq = null;
	        try {
	        	recoveryReq = assembleEBSReq(req);
	        } catch (ServiceStopException e) {
	            logger.error(CommonStatusCode.INTERNAL_ERROR, "向资源池发送Snapshot恢复操作请求失败，资源池代理系统内部错误，缓存服务停止，获取不到资源池信息，本次操作流水号为:"
	                    + (String) reqCxt.getAttribute("SERIAL_NUM"), null);
	            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, resp);
	            return FAILURE;
	        } catch (Exception e) {
	            logger.error(
	                    CommonStatusCode.INTERNAL_ERROR,
	                    "向资源池发送Snapshot申请恢复操作请求失败，资源池代理系统内部错误，无法通过给定的资源池ID获取到资源池信息，本次操作流水号为:"
	                            + (String) reqCxt.getAttribute("SERIAL_NUM"), null);
	            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, resp);
	            return FAILURE;
	        }
	        if (recoveryReq.getResourceUrl() == null || recoveryReq.getPassword() == null) {
	            logger.error(CommonStatusCode.INTERNAL_ERROR,
	                    "向资源池发送虚拟Snapshot申请恢复操作请求失败，资源池代理系统内部错误，通过给定的资源池ID获取到的资源池信息为空，本次操作流水号为:" + recoveryReq.getSerialNum(), null);
	            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, resp);
	            return FAILURE;
	        }
	        RPPRecoverySnapshotResp snapshotRecoveryResp = snapshotRecovery.recovery(recoveryReq);
	        assebleRes(snapshotRecoveryResp, respCxt);
	        if (snapshotRecoveryResp.getResultCode().equals(InterfaceResultCode.SUCCESS.getResultCode())) {
	            logger.info("为虚拟机" + req.getVmId() + "申请恢复快照 操作成功");
	        } else {
	            logger.info("为虚拟机" + req.getVmId() + "申请恢复快照操作失败,接口响应码为[" + snapshotRecoveryResp.getResultCode() + "]，接口响应消息为["
	                    + snapshotRecoveryResp.getResultMessage() + "]");
	        }
	        return SUCCESS;
	    }
	    /**
	     * 校验请求
	     * @param req
	     * @return
	     */
	    private boolean validateInputPara(RPPRecoverySnapshotReq req) {
	        if (StringUtils.isBlank(req.getResourcePoolId()) || (!P_STRING.matcher(req.getResourcePoolId()).matches())) {
	            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查资源池ID RES_POOL_ID", null);
	            return false;
	        }
	        if (StringUtils.isBlank(req.getVmId())) {
	            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查Snapshot申请务vmID属性vmId", null);
	            return false;
	        }
	        if (StringUtils.isBlank(req.getSnapshot_id())) {
	            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查Snapshot申请业务id属性snapshot_id", null);
	            return false;
	        }
	        return true;
	    }

	    private SnapshotRecoveryReq assembleEBSReq(RPPRecoverySnapshotReq req) throws ServiceStopException, UnmatchException,
	            IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {

	        SnapshotRecoveryReq snapshotRecoveryReq = new SnapshotRecoveryReq();
	        RPPRecoverySnapshotReq info = (RPPRecoverySnapshotReq) BeanUtils.cloneBean(req);
	        snapshotRecoveryReq.setInfo(info);
	        //测试将资源池id改为01，正式环境要改为获取到的资源池id
	        setCommAttribute(req.getResourcePoolId(), req.getResourcePoolPartId(), snapshotRecoveryReq);
	        //setCommAttribute("CPC-RP-01", req.getResourcePoolPartId(), vlanApplyReq);
	        
	        return snapshotRecoveryReq;
	    }

	    private void assebleRes(RPPBaseResp rppResp, RuntimeContext respCxt) {
	        respCxt.setAttribute(RPPBaseResp.RESP_BODY, rppResp);
	    }
		public void setSnapshotRecovery(SnapshotRecovery snapshotRecovery) {
			this.snapshotRecovery = snapshotRecovery;
		}
}
