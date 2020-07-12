package com.neusoft.mid.cloong.snapshots;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import com.neusoft.mid.cloong.BaseProcessor;
import com.neusoft.mid.cloong.CommonStatusCode;
import com.neusoft.mid.cloong.InterfaceResultCode;
import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.snapshots.RPPDeleteSnapshotReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.snapshots.RPPDeleteSnapshotResp;
import com.neusoft.mid.grains.commons.route.exception.ServiceStopException;
import com.neusoft.mid.grains.commons.route.exception.UnmatchException;
import com.neusoft.mid.iamp.api.RuntimeContext;
import com.neusoft.mid.iamp.logger.LogService;

public class SnapshotDeleteProcessor extends BaseProcessor{
	 private static LogService logger = LogService.getLogger(SnapshotApplyProcessor.class);
	    /**
	     * Snaopshot删除接口
	     */
	    private SnapshotDelete snapshotDelete;


	    @Override
	    public String process(RuntimeContext reqCxt, RuntimeContext respCxt) {

	    	RPPDeleteSnapshotReq req = (RPPDeleteSnapshotReq) reqCxt.getAttribute(RPPDeleteSnapshotReq.REQ_BODY);
	    	RPPDeleteSnapshotResp resp = new RPPDeleteSnapshotResp();

	        // 校验
	        if (!validateInputPara(req)) {
	            assembleErrorResp(InterfaceResultCode.INPUT_PARR_ERROR, respCxt, resp);
	            return FAILURE;
	        }

	        SnapshotDeleteReq deleteReq = null;
	        try {
	        	deleteReq = assembleEBSReq(req);
	        } catch (ServiceStopException e) {
	            logger.error(CommonStatusCode.INTERNAL_ERROR, "向资源池发送Snapshot删除操作请求失败，资源池代理系统内部错误，缓存服务停止，获取不到资源池信息，本次操作流水号为:"
	                    + (String) reqCxt.getAttribute("SERIAL_NUM"), null);
	            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, resp);
	            return FAILURE;
	        } catch (Exception e) {
	            logger.error(
	                    CommonStatusCode.INTERNAL_ERROR,
	                    "向资源池发送Snapshot删除操作请求失败，资源池代理系统内部错误，无法通过给定的资源池ID获取到资源池信息，本次操作流水号为:"
	                            + (String) reqCxt.getAttribute("SERIAL_NUM"), null);
	            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, resp);
	            return FAILURE;
	        }
	        if (deleteReq.getResourceUrl() == null || deleteReq.getPassword() == null) {
	            logger.error(CommonStatusCode.INTERNAL_ERROR,
	                    "向资源池发送虚拟Snapshot删除操作请求失败，资源池代理系统内部错误，通过给定的资源池ID获取到的资源池信息为空，本次操作流水号为:" + deleteReq.getSerialNum(), null);
	            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, resp);
	            return FAILURE;
	        }
	        RPPDeleteSnapshotResp snapshotDeleteResp = snapshotDelete.delete(deleteReq);
	        assebleRes(snapshotDeleteResp, respCxt);
	        if (snapshotDeleteResp.getResultCode().equals(InterfaceResultCode.SUCCESS.getResultCode())) {
	            logger.info("为虚拟机" + req.getVmId() + "申请删除快照 操作成功" );
	        } else {
	            logger.info("为虚拟机" + req.getVmId() + "申请删除快照操作失败,接口响应码为[" + snapshotDeleteResp.getResultCode() + "]，接口响应消息为["
	                    + snapshotDeleteResp.getResultMessage() + "]");
	        }
	        return SUCCESS;
	    }
	    /**
	     * 校验请求
	     * @param req
	     * @return
	     */
	    private boolean validateInputPara(RPPDeleteSnapshotReq req) {
	        if (StringUtils.isBlank(req.getResourcePoolId()) || (!P_STRING.matcher(req.getResourcePoolId()).matches())) {
	            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查资源池ID RES_POOL_ID", null);
	            return false;
	        }
	        if (StringUtils.isBlank(req.getVmId())) {
	            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查Snapshot申请务vmID属性vmId", null);
	            return false;
	        }
	        if (StringUtils.isBlank(req.getSnapshot_id())) {
	            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查Snapshot申请业务snapshot_id属性snapshot_id", null);
	            return false;
	        }
	        return true;
	    }

	    private SnapshotDeleteReq assembleEBSReq(RPPDeleteSnapshotReq req) throws ServiceStopException, UnmatchException,
	            IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {

	        SnapshotDeleteReq snapshotDeleteReq = new SnapshotDeleteReq();
	        RPPDeleteSnapshotReq info = (RPPDeleteSnapshotReq) BeanUtils.cloneBean(req);
	        snapshotDeleteReq.setInfo(info);

	        //测试将资源池id改为01，正式环境要改为获取到的资源池id
	        setCommAttribute(req.getResourcePoolId(), req.getResourcePoolPartId(), snapshotDeleteReq);
	        return snapshotDeleteReq;
	    }

	    private void assebleRes(RPPBaseResp rppResp, RuntimeContext respCxt) {
	        respCxt.setAttribute(RPPBaseResp.RESP_BODY, rppResp);
	    }
		public void setSnapshotDelete(SnapshotDelete snapshotDelete) {
			this.snapshotDelete = snapshotDelete;
		}
}

