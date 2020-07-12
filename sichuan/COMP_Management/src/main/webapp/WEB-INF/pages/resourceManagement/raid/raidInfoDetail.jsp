<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
       <h1>存储阵列</h1>
      <div class="details-con">
   	    <div class="detail-title">
            <a href="javascript:void(0)" id='goBack' title="返回">
                <span class="back"></span>
            </a>
          <h3 class="apply-title">存储阵列详情</h3>
          <ul class="title-btn">
	           <li class="select">
	           <s:url id="raidInfoDetailUrl" action="raidInfoDetail">
					<s:param name="raidInfo.raidId">${raidInfo.raidId}</s:param>
					<s:param name="nodeType">${nodeType}</s:param>
					<s:param name="nodeId">${nodeId}</s:param>
					<s:param name="treeNodeName">${treeNodeName}</s:param>
					<s:param name="pnodeId">${pnodeId}</s:param>
					<s:param name="pnodeName">${pnodeName}</s:param>
					<s:param name="appId">${appId}</s:param>
					<s:param name="curFun">${curFun}</s:param>
				</s:url>
				<a href="${raidInfoDetailUrl}">配置属性</a>
	           </li>
	           <!-- <li>
		           <s:url id="realTimeUrl" action="realTimeReport">
					<s:param name="deviceId">${raidInfo.raidId}</s:param>
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
		            	<li><span class="result-name">所属资源池：</span><span><s:property value="raidInfo.resPoolName" />&nbsp;</span></li>
		            	<li><span class="result-name">所属设备ID：</span><span><s:property value="raidInfo.relatedEqpId" />&nbsp;</span></li>
		            </ul>
		        </div>
		        <div class="result" style="width: 130%">
		        	<h5 class="label">
		            	<span class="label-bg">基本信息</span>
		            </h5>
		            <ul class="result-list">
		            	<li><span class="result-name">存储阵列ID：</span><span><s:property value="raidInfo.raidId" />&nbsp;</span></li>
		            	<li><span class="result-name">设备序列号：</span><span><s:property value="raidInfo.eqpSerialnum" />&nbsp;</span></li>
		                <li><span class="result-name">类型：</span><span><s:property value="raidInfo.saType" />&nbsp;</span></li>
		                <li><span class="result-name">设备厂商：</span><span><s:property value="raidInfo.vendorName" />&nbsp;</span></li>
		                <li><span class="result-name">微码版本：</span><span><s:property value="raidInfo.saMicrocodeVer" />&nbsp;</span></li>
		            </ul>
		            <ul class="result-list">
		            	<li><span class="result-name">存储阵列名称：</span><span><s:property value="raidInfo.raidName" />&nbsp;</span></li>
		            	<li><span class="result-name">资产状态：</span>
		            	    <span>
						        <s:if test="raidInfo.assetState == \"1\"">已使用</s:if>
								<s:elseif test="raidInfo.assetState == \"2\"">未使用</s:elseif>
								<s:elseif test="raidInfo.assetState == \"3\"">不可用</s:elseif>
								<s:elseif test="raidInfo.assetState == \"4\"">丢失</s:elseif>
								<s:elseif test="raidInfo.assetState == \"5\"">待确认</s:elseif>
								<s:elseif test="raidInfo.assetState == \"6\"">已删除</s:elseif>
						        &nbsp;
		            	    </span>
		            	</li>
		            	<li><span class="result-name">资产SLA类型：</span>
		            	    <span>
		            	    	<s:if test="raidInfo.assetSlaType == \"1\"">提供服务</s:if>
							    <s:elseif test="raidInfo.assetSlaType == \"2\"">平台自服务</s:elseif>
						        &nbsp;
		            	    </span>
		            	</li>
		            	<li><span class="result-name">IP地址：</span><span><s:property value="raidInfo.saIp" />&nbsp;</span></li>
		                <li><span class="result-name">更新时间：</span><span><s:property value="raidInfo.updateTime" />&nbsp;</span></li>
		            </ul>
		        </div>
		        <div class="result" style="width: 130%">
		        	<h5 class="label">
		            	<span class="label-bg">配置属性</span>
		            </h5>
		            <ul class="result-list">
		            	<li><span class="result-name">配置容量(GB)：</span><span><s:property value="raidInfo.saCapacity" />&nbsp;</span></li>
		                <li><span class="result-name">磁盘规格：</span><span><s:property value="raidInfo.diskSpecification" />&nbsp;</span></li>
		            </ul>
		            <ul class="result-list">
		                <li><span class="result-name">CACHE容量(MB)：</span><span><s:property value="raidInfo.cacheCapacity" />&nbsp;</span></li>
		            	<!-- <li><span class="result-name">磁盘标识：</span><span><s:property value="raidInfo.diskIds" />&nbsp;</span></li> -->
		            </ul>
		            <ul class="result-list">
		            	<!-- <li><span class="result-name">磁盘适配卡标识：</span><span><s:property value="raidInfo.diskAdaptorId" />&nbsp;</span></li> 
		                <li><span class="result-name">主机通道卡标识：</span><span><s:property value="raidInfo.hbaIds" />&nbsp;</span></li>-->
		            </ul>
		            <ul class="result-list">
		                <!-- <li><span class="result-name">磁盘适配卡类型：</span><span><s:property value="raidInfo.diskAdaptorType" />&nbsp;</span></li> -->
		                <li><span class="result-name">主机通道卡类型：</span><span><s:property value="raidInfo.hbaTypes" />&nbsp;</span></li>
		            </ul>
		            <ul class="result-list">
		                <!-- <li><span class="result-name">主机通道卡数目：</span><span><s:property value="raidInfo.hbaNum" />&nbsp;</span></li> -->
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
	
	form.action = "raidListAction.action?raidInfo.nodeType=" + nodeType + "&raidInfo.nodeId="
		+ nodeId + "&raidInfo.treeNodeName="
		+ treeNodeName + "&raidInfo.pnodeId="
		+ pnodeId + "&raidInfo.pnodeName="
		+ pnodeName + "&raidInfo.appId="
		+ appId + "&raidInfo.curFun=" + curFun;
	form.submit();
}
</script>