<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
       <h1>路由器</h1>
      <div class="details-con">
   	    <div class="detail-title">
            <a href="javascript:void(0)" id='goBack' title="返回">
                <span class="back"></span>
            </a>
          <h3 class="apply-title">路由器详情</h3>
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
	        <s:hidden id="routerId" name="rtInfo.routerId"></s:hidden>
   	    </s:form>
   	   
		<div class="page-form">    
		    <div class="contentDetail">
		    	<div class="result" style="width: 130%">
		        	<h5 class="label">
		            	<span class="label-bg">位置信息</span>
		            </h5>
		            <ul class="result-list">
		            	<li><span class="result-name">所属资源池：</span><span><s:property value="rtInfo.resPoolName" />&nbsp;</span></li>
		            	<li><span class="result-name">所属路由器：</span><span><s:property value="rtInfo.routerName" />&nbsp;</span></li>
		            </ul>
		        </div>
		        <div class="result" style="width: 130%">
		        	<h5 class="label">
		            	<span class="label-bg">基本信息</span>
		            </h5>
		            <ul class="result-list">
		            	<li><span class="result-name">端口ID：</span><span><s:property value="rtInfo.routerIfId" />&nbsp;</span></li>
		                <li><span class="result-name">类型：</span><span><s:property value="rtInfo.ifType" />&nbsp;</span></li>
		                <li><span class="result-name">本端配置IP地址：</span><span><s:property value="rtInfo.ifSetLocalIp" />&nbsp;</span></li>
		                <li><span class="result-name">连接设备IP地址：</span><span><s:property value="rtInfo.ifConnectEqpIp" />&nbsp;</span></li>
		                <li><span class="result-name">对端端口标识：</span><span><s:property value="rtInfo.destIfId" />&nbsp;</span></li>
		                <li><span class="result-name">描述：</span><span><s:property value="rtInfo.ifDescription" />&nbsp;</span></li>
		            </ul>
		            <ul class="result-list">
		            	<li><span class="result-name">端口名称：</span><span><s:property value="rtInfo.routerIfName" />&nbsp;</span></li>
		            	<li><span class="result-name">状态：</span><span><s:property value="rtInfo.ifStatus" />&nbsp;</span></li>
		            	<li><span class="result-name">IP网络掩码：</span><span><s:property value="rtInfo.ipSubNetmask" />&nbsp;</span></li>
		                <li><span class="result-name">物理地址：</span><span><s:property value="rtInfo.ifPhyAddress" />&nbsp;</span></li>
		                <li><span class="result-name">速率(KBPS)：</span><span><s:property value="rtInfo.ifSpeed" />&nbsp;</span></li>
		                <li><span class="result-name">更新时间：</span><span><s:property value="rtInfo.updateTime" />&nbsp;</span></li>
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
	
	form.action = "rtifListAction.action?rtInfo.nodeType=" + nodeType + "&rtInfo.nodeId="
		+ nodeId + "&rtInfo.treeNodeName="
		+ treeNodeName + "&rtInfo.pnodeId="
		+ pnodeId + "&rtInfo.pnodeName="
		+ pnodeName + "&rtInfo.appId="
		+ appId + "&rtInfo.curFun=" 
		+ curFun;
	form.submit();
}
</script>