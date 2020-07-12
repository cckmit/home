<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
       <h1>交换机端口</h1>
      <div class="details-con">
   	    <div class="detail-title">
            <a href="javascript:void(0)" id='goBack' title="返回">
                <span class="back"></span>
            </a>
          <h3 class="apply-title">交换机端口详情</h3>
          <ul class="title-btn">
	           <li class="select">
	           <s:url id="swIfInfoDetailUrl" action="swIfInfoDetail">
					<s:param name="swIfInfo.switchIfId">${swIfInfo.switchIfId}</s:param>
					<s:param name="nodeType">${nodeType}</s:param>
					<s:param name="nodeId">${nodeId}</s:param>
					<s:param name="treeNodeName">${treeNodeName}</s:param>
					<s:param name="pnodeId">${pnodeId}</s:param>
					<s:param name="pnodeName">${pnodeName}</s:param>
					<s:param name="appId">${appId}</s:param>
					<s:param name="curFun">${curFun}</s:param>
				</s:url>
				<a href="${swIfInfoDetailUrl}">配置属性</a>
	           </li>
	           <!-- <li>
		           <s:url id="realTimeUrl" action="realTimeReport">
					<s:param name="deviceId">${swIfInfo.switchId}</s:param>
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
		            	<li><span class="result-name">所属资源池：</span><span><s:property value="swIfInfo.resPoolName" />&nbsp;</span></li>
		            	<li><span class="result-name">所属交换机：</span><span><s:property value="swIfInfo.switchName" />&nbsp;</span></li>
		            </ul>
		        </div>
		        <div class="result" style="width: 130%">
		        	<h5 class="label">
		            	<span class="label-bg">基本信息</span>
		            </h5>
		            <ul class="result-list">
		            	<li><span class="result-name">交换机端口ID：</span><span><s:property value="swIfInfo.switchIfId" />&nbsp;</span></li>
		                <li><span class="result-name">端口型号：</span><span><s:property value="swIfInfo.ifType" />&nbsp;</span></li>
		                <li><span class="result-name">配置MAC地址：</span><span><s:property value="swIfInfo.ifSetMacAddr" />&nbsp;</span></li>
		                <li><span class="result-name">设备IP地址：</span><span><s:property value="swIfInfo.ifConnectEqpIp" />&nbsp;</span></li>
		                <li><span class="result-name">端口速率(kbps)：</span><span><s:property value="swIfInfo.ifSpeed" />&nbsp;</span></li>
		                <li><span class="result-name">描述：</span><span><s:property value="swIfInfo.ifDescription" />&nbsp;</span></li>
		            </ul>
		            <ul class="result-list">
		            	<li><span class="result-name">交换机端口名称：</span><span><s:property value="swIfInfo.switchIfName" />&nbsp;</span></li>
		            	<li><span class="result-name">端口状态：</span><span><s:property value="swIfInfo.ifStatus" />&nbsp;</span></li>
		            	<li><span class="result-name">实际MAC地址：</span><span><s:property value="swIfInfo.ifRealMacAddr" />&nbsp;</span></li>
		                <li><span class="result-name">VLAN：</span><span><s:property value="swIfInfo.vlanId" />&nbsp;</span></li>
		            	<li><span class="result-name">对端端口标识：</span><span><s:property value="swIfInfo.destIfId" />&nbsp;</span></li>
		                <li><span class="result-name">更新时间：</span><span><s:property value="swIfInfo.updateTime" />&nbsp;</span></li>
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
	var switchId = '<s:property value="swIfInfo.switchId" />';
	var switchName = '<s:property value="swIfInfo.switchName" />';
	
	form.action = "swIfListAction.action?swIfInfo.nodeType=" + nodeType + "&swIfInfo.nodeId="
		+ nodeId + "&swIfInfo.treeNodeName="
		+ treeNodeName + "&swIfInfo.pnodeId="
		+ pnodeId + "&swIfInfo.pnodeName="
		+ pnodeName + "&swIfInfo.appId="
		+ appId + "&swIfInfo.curFun=" + curFun
		+ "&querySwIfInfo.switchId=" + switchId
		+ "&querySwIfInfo.switchName=" + switchName;
	form.submit();
}
</script>