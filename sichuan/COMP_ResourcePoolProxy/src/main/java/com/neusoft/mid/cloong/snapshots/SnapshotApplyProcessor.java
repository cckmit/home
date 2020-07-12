package com.neusoft.mid.cloong.snapshots;

import java.lang.reflect.InvocationTargetException;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import com.neusoft.mid.cloong.BaseProcessor;
import com.neusoft.mid.cloong.CommonStatusCode;
import com.neusoft.mid.cloong.InterfaceResultCode;
import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseResp;
import com.neusoft.mid.cloong.rpproxy.interfaces.snapshots.RPPApplySnapshotReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.snapshots.RPPApplySnapshotResp;
import com.neusoft.mid.grains.commons.route.exception.ServiceStopException;
import com.neusoft.mid.grains.commons.route.exception.UnmatchException;
import com.neusoft.mid.iamp.api.RuntimeContext;
import com.neusoft.mid.iamp.logger.LogService;

public class SnapshotApplyProcessor extends BaseProcessor{
	 private static LogService logger = LogService.getLogger(SnapshotApplyProcessor.class);
	    /**
	     * Snaopshot申请接口
	     */
	    private SnapshotApply snapshotApply;


	    @Override
	    public String process(RuntimeContext reqCxt, RuntimeContext respCxt) {

	    	RPPApplySnapshotReq req = (RPPApplySnapshotReq) reqCxt.getAttribute(RPPApplySnapshotReq.REQ_BODY);
	    	RPPApplySnapshotResp resp = new RPPApplySnapshotResp();

	        // 校验
	        if (!validateInputPara(req)) {
	            assembleErrorResp(InterfaceResultCode.INPUT_PARR_ERROR, respCxt, resp);
	            return FAILURE;
	        }

	        SnapshotApplyReq applyReq = null;
	        try {
	            applyReq = assembleEBSReq(req);
	        } catch (ServiceStopException e) {
	            logger.error(CommonStatusCode.INTERNAL_ERROR, "向资源池发送Snapshot申请操作请求失败，资源池代理系统内部错误，缓存服务停止，获取不到资源池信息，本次操作流水号为:"
	                    + (String) reqCxt.getAttribute("SERIAL_NUM"), null);
	            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, resp);
	            return FAILURE;
	        } catch (Exception e) {
	            logger.error(
	                    CommonStatusCode.INTERNAL_ERROR,
	                    "向资源池发送Snapshot申请操作请求失败，资源池代理系统内部错误，无法通过给定的资源池ID获取到资源池信息，本次操作流水号为:"
	                            + (String) reqCxt.getAttribute("SERIAL_NUM"), null);
	            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, resp);
	            return FAILURE;
	        }
	        if (applyReq.getResourceUrl() == null || applyReq.getPassword() == null) {
	            logger.error(CommonStatusCode.INTERNAL_ERROR,
	                    "向资源池发送虚拟Snapshot申请操作请求失败，资源池代理系统内部错误，通过给定的资源池ID获取到的资源池信息为空，本次操作流水号为:" + applyReq.getSerialNum(), null);
	            assembleErrorResp(InterfaceResultCode.RESOURCE_POOL_ROXY_DATAPARA_ERROR, respCxt, resp);
	            return FAILURE;
	        }
	        RPPApplySnapshotResp snapshotApplyResp = snapshotApply.apply(applyReq);
	        assebleRes(snapshotApplyResp, respCxt);
	        if (snapshotApplyResp.getResultCode().equals(InterfaceResultCode.SUCCESS.getResultCode())) {
	            logger.info("为虚拟机" + req.getVmId() + "申请创建快照 " + snapshotApplyResp.getSnapshotIdList() + "操作成功");
	        } else {
	            logger.info("为虚拟机" + req.getVmId() + "申请创建快照操作失败,接口响应码为[" + snapshotApplyResp.getResultCode() + "]，接口响应消息为["
	                    + snapshotApplyResp.getResultMessage() + "]");
	        }
	        return SUCCESS;
	    }
	    /**
	     * 校验请求
	     * @param req
	     * @return
	     */
	    private boolean validateInputPara(RPPApplySnapshotReq req) {
	        if (StringUtils.isBlank(req.getResourcePoolId()) || (!P_STRING.matcher(req.getResourcePoolId()).matches())) {
	            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查资源池ID RES_POOL_ID", null);
	            return false;
	        }
	        if (StringUtils.isBlank(req.getResourcePoolPartId())
	                || (!P_STRING.matcher(req.getResourcePoolPartId()).matches())) {
	            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查资源池分区ID RES_POOL_PART_ID", null);
	            return false;
	        }
	        if (StringUtils.isBlank(req.getVmId())) {
	            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查Snapshot申请务vmID属性vmId", null);
	            return false;
	        }
	        if (StringUtils.isBlank(req.getSnapshot_name())) {
	            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查Snapshot申请业务name属性snapshot_name", null);
	            return false;
	        }
	      /*  if (StringUtils.isBlank(req.getSnapshot_desc())) {
	            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查Snapshot申请业务desc属性snapshot_desc", null);
	            return false;
	        }*/
	        /*if (StringUtils.isBlank(req.getQuiesce())) {
	            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查Snapshot申请业务Quiesce属性quiescee", null);
	            return false;
	        }
	        if (StringUtils.isBlank(req.getGenerate_memory())) {
	            logger.error(CommonStatusCode.INPUT_PARA_ERROR, "请求参数异常，请检查Snapshot申请业务Generate_memory属性generate_memory", null);
	            return false;
	        }*/
	        return true;
	    }

	    private SnapshotApplyReq assembleEBSReq(RPPApplySnapshotReq req) throws ServiceStopException, UnmatchException,
	            IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {

	        SnapshotApplyReq snapshotApplyReq = new SnapshotApplyReq();
	        RPPApplySnapshotReq info = (RPPApplySnapshotReq) BeanUtils.cloneBean(req);
	        snapshotApplyReq.setInfo(info);

	        // 设置默认值
	        if (info.getQuiesce() == null&&"".equals(info.getQuiesce())) {
	            info.setQuiesce("0");
	        }
	        if (info.getGenerate_memory() == null && "".equals(info.getGenerate_memory())) {
	            info.setGenerate_memory("0");
	        }
	        //测试将资源池id改为01，正式环境要改为获取到的资源池id
	        setCommAttribute(req.getResourcePoolId(), req.getResourcePoolPartId(), snapshotApplyReq);
	        //setCommAttribute("CPC-RP-01", req.getResourcePoolPartId(), vlanApplyReq);
	        
	        return snapshotApplyReq;
	    }

	    private void assebleRes(RPPBaseResp rppResp, RuntimeContext respCxt) {
	        respCxt.setAttribute(RPPBaseResp.RESP_BODY, rppResp);
	    }
		public void setSnapshotApply(SnapshotApply snapshotApply) {
			this.snapshotApply = snapshotApply;
		}



}
