<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
       <h1>交换机</h1>
      <div class="details-con">
   	    <div class="detail-title">
            <a href="javascript:void(0)" id='goBack' title="返回">
                <span class="back"></span>
            </a>
          <h3 class="apply-title">交换机详情</h3>
          <ul class="title-btn">
	           <li class="select">
	           <s:url id="swInfoDetailUrl" action="swInfoDetail">
					<s:param name="swInfo.switchId">${swInfo.switchId}</s:param>
					<s:param name="nodeType">${nodeType}</s:param>
					<s:param name="nodeId">${nodeId}</s:param>
					<s:param name="treeNodeName">${treeNodeName}</s:param>
					<s:param name="pnodeId">${pnodeId}</s:param>
					<s:param name="pnodeName">${pnodeName}</s:param>
					<s:param name="appId">${appId}</s:param>
					<s:param name="curFun">${curFun}</s:param>
				</s:url>
				<a href="${swInfoDetailUrl}">配置属性</a>
	           </li>
	           <!-- <li>
		           <s:url id="realTimeUrl" action="realTimeReport">
					<s:param name="deviceId">${swInfo.switchId}</s:param>
					<s:param name="staType">CPU</s:param>
					<s:param name="deviceType">MINIPM</s:param>
					<s:param name="nodeType">${nodeType}</s:param>
					<s:param name="nodeId">${nodeId}</s:param>
					<s:param name="treeNodeName">${treeNodeName}</s:param>
					<s:param name="pnodeId">${pnodeId}</s:param>
					<s:param name="pnodeName">${pnodeName}</s:param>
					<s:param name="appId">${appId}</s:param>
					<s:param name="curFun">${curFun}</s:param>
				</s:url>
				<a href="${realTimeUrl}">性能指标</a>
	           </li> -->
	         </ul>
   	    </div>
		<s:form method="post" id="formId">
   	    	<input type="hidden" id="msg" name="msg" value="" />
   	    	<input type="hidden" id="errMsg" name="errMsg" value="" />
   	    	<s:hidden id="nodeType" name="nodeType"></s:hidden>
	        <s:hidden id="nodeId" name="nodeId"></s:hidden>
	        <s:hidden id="treeNodeName" name="treeNodeName"></s:hidden>
	        <s:hidden id="pnodeId" name="pnodeId"></s:hidden>
	        <s:hidden id="pnodeName" name="pnodeName"></s:hidden>
	        <s:hidden id="appId" name="appId"></s:hidden>
	        <s:hidden id="curFun" name="curFun"></s:hidden>
   	    </s:form>
   	   
		<div class="page-form">    
		    <div class="contentDetail">
		    	<div class="result" style="width: 130%">
		        	<h5 class="label">
		            	<span class="label-bg">位置信息</span>
		            </h5>
		            <ul class="result-list">
		            	<li><span class="result-name">所属资源池：</span><span><s:property value="swInfo.resPoolName" />&nbsp;</span></li>
		            </ul>
		        </div>
		        <div class="result" style="width: 130%">
		        	<h5 class="label">
		            	<span class="label-bg">基本信息</span>
		            </h5>
		            <ul class="result-list">
		            	<li><span class="result-name">交换机ID：</span><span><s:property value="swInfo.switchId" />&nbsp;</span></li>
		            	<li><span class="result-name">设备序列号：</span><span><s:property value="swInfo.switchSerialnum" />&nbsp;</span></li>
		                <li><span class="result-name">交换机型号：</span><span><s:property value="swInfo.switchType" />&nbsp;</span></li>
		                <li><span class="result-name">软件版本：</span><span><s:property value="swInfo.swVersion" />&nbsp;</span></li>
		                <li><span class="result-name">资产状态：</span>
		            	    <span>
						        <s:if test="swInfo.assetState == \"1\"">已使用</s:if>
								<s:elseif test="swInfo.assetState == \"2\"">未使用</s:elseif>
								<s:elseif test="swInfo.assetState == \"3\"">不可用</s:elseif>
								<s:elseif test="swInfo.assetState == \"4\"">丢失</s:elseif>
								<s:elseif test="swInfo.assetState == \"5\"">待确认</s:elseif>
								<s:elseif test="swInfo.assetState == \"6\"">已删除</s:elseif>
						        &nbsp;
		            	    </span>
		            	</li>
		            	<li><span class="result-name">资产SLA类型：</span>
		            	    <span>
		            	    	<s:if test="swInfo.assetSlaType == \"1\"">提供服务</s:if>
							    <s:elseif test="swInfo.assetSlaType == \"2\"">平台自服务</s:elseif>
						        &nbsp;
		            	    </span>
		            	</li>
		            </ul>
		            <ul class="result-list">
		            	<li><span class="result-name">交换机名称：</span><span><s:property value="swInfo.switchName" />&nbsp;</span></li>
		            	<li><span class="result-name">状态：</span>
		            	    <span>
		            	    	<s:if test="swInfo.curStatus == \"0\""><img style="margin-bottom: -3px" alt="运行" src="../images/pm_running.png" >运行</s:if>
								<s:elseif test="swInfo.curStatus == \"1\""><img style="margin-bottom: -3px" alt="闲置" src="../images/pm_pause.png">闲置</s:elseif>
								<s:elseif test="swInfo.curStatus == \"2\""><img style="margin-bottom: -3px" alt="报废" src="../images/pm_avliable.png">报废</s:elseif>
								<s:elseif test="swInfo.curStatus == \"3\""><img style="margin-bottom: -3px" alt="损坏" src="../images/pm_shutdown.png">损坏</s:elseif>
						        &nbsp;
		            	    </span>
		            	</li>
		            	<li><span class="result-name">设备厂商：</span><span><s:property value="swInfo.vendorName" />&nbsp;</span></li>
		            	<li><span class="result-name">IP地址：</span><span><s:property value="swInfo.switchIp" />&nbsp;</span></li>
		            	<li><span class="result-name">资产来源分类：</span>
		            	    <span>
						        <s:if test="swInfo.assetOriginType == \"1\"">自产</s:if>
								<s:elseif test="swInfo.assetOriginType == \"2\"">外购</s:elseif>
								<s:elseif test="swInfo.assetOriginType == \"3\"">借入</s:elseif>
								<s:elseif test="swInfo.assetOriginType == \"4\"">订单</s:elseif>
								<s:elseif test="swInfo.assetOriginType == \"5\"">第三方监控源</s:elseif>
								<s:elseif test="swInfo.assetOriginType == \"6\"">可自义</s:elseif>
						        &nbsp;
		            	    </span>
		            	</li>
		                <li><span class="result-name">更新时间：</span><span><s:property value="swInfo.updateTime" />&nbsp;</span></li>
		            </ul>
		        </div>
		    </div>	
		</div>
		<div style="padding: 15px;clear: both">
           
		</div>
    </div>
    <div class="page-button">
            <ul class="opt-bottom-btn">
                <li>
                    <a href="javascript:void(0)" id="cancel">取消</a>
                </li>
            </ul>
		</div>

<script type="text/javascript">
$("#goBack").click(function(){
		backList();
	});
	
$("#cancel").click(function(){
	backList();
});
function backList(){
	var form = document.getElementById("formId");
	var nodeType = document.getElementById('nodeType').value;
	var nodeId = document.getElementById('nodeId').value;
	var treeNodeName = document.getElementById('treeNodeName').value;
	var pnodeId = document.getElementById('pnodeId').value;
	var pnodeName = document.getElementById('pnodeName').value;
	var appId = document.getElementById('appId').value;
	var curFun = document.getElementById('curFun').value;
	
	form.action = "swListAction.action?swInfo.nodeType=" + nodeType + "&swInfo.nodeId="
		+ nodeId + "&swInfo.treeNodeName="
		+ treeNodeName + "&swInfo.pnodeId="
		+ pnodeId + "&swInfo.pnodeName="
		+ pnodeName + "&swInfo.appId="
		+ appId + "&swInfo.curFun=" + curFun;
	form.submit();
}
</script>