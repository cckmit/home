<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
       <h1>小型机</h1>
      <div class="details-con">
   	    <div class="detail-title">
            <a href="javascript:void(0)" id='goBack' title="返回">
                <span class="back"></span>
            </a>
          <h3 class="apply-title">小型机详情</h3>
          <ul class="title-btn">
	           <li class="select">
	           <s:url id="miniPmInfoDetailUrl" action="miniPmInfoDetail">
					<s:param name="miniPmInfo.miniPmId">${miniPmInfo.miniPmId}</s:param>
					<s:param name="nodeType">${nodeType}</s:param>
					<s:param name="nodeId">${nodeId}</s:param>
					<s:param name="treeNodeName">${treeNodeName}</s:param>
					<s:param name="pnodeId">${pnodeId}</s:param>
					<s:param name="pnodeName">${pnodeName}</s:param>
					<s:param name="appId">${appId}</s:param>
					<s:param name="curFun">${curFun}</s:param>
				</s:url>
				<a href="${miniPmInfoDetailUrl}">配置属性</a>
	           </li>
	           <li>
		           <s:url id="realTimeUrl" action="realTimeReport">
					<s:param name="deviceId">${miniPmInfo.miniPmId}</s:param>
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
	           </li>
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
		    	<div class="result">
		        	<h5 class="label">
		            	<span class="label-bg">位置信息</span>
		            </h5>
		            <ul class="result-list">
		            	<li><span class="result-name">所属资源池：</span><span><s:property value="miniPmInfo.resPoolName" />&nbsp;</span></li>
		                <li><span class="result-name">机房名称：</span><span><s:property value="miniPmInfo.machinerRoomName" />&nbsp;</span></li>
		            </ul>
		            <ul class="result-list">
		            	<li><span class="result-name">所属分区：</span><span><s:property value="miniPmInfo.resPoolPartName" />&nbsp;</span></li>
		            	<li><span class="result-name">机柜名称：</span><span><s:property value="miniPmInfo.cabinetName" />&nbsp;</span></li>
		            </ul>
		        </div>
		        <div class="result">
		        	<h5 class="label">
		            	<span class="label-bg">基本信息</span>
		            </h5>
		            <ul class="result-list">
		            	<li><span class="result-name">小型机ID：</span><span><s:property value="miniPmInfo.miniPmId" />&nbsp;</span></li>
		                <li><span class="result-name">所属业务组：</span><span><s:property value="miniPmInfo.appName" />&nbsp;</span></li>
		                <li><span class="result-name">操作系统：</span><span><s:property value="miniPmInfo.osType" />&nbsp;</span></li>
		                <li><span class="result-name">设备厂商：</span><span><s:property value="miniPmInfo.vendorName" />&nbsp;</span></li>
		                <li><span class="result-name">投入生产运行时间：</span><span><s:property value="miniPmInfo.runTime" />&nbsp;</span></li>
		            </ul>
		            <ul class="result-list">
		            	<li><span class="result-name">小型机名称：</span><span><s:property value="miniPmInfo.miniPmName" />&nbsp;</span></li>
		            	<li><span class="result-name">状态：</span>
		            	    <span>
						        <s:if test="miniPmInfo.curStatus == \"0\""><img style="margin-bottom: -3px" alt="不可用" src="../images/pm_avliable.png">不可用</s:if>
								<s:elseif test="miniPmInfo.curStatus == \"1\""><img style="margin-bottom: -3px" alt="就绪" src="../images/pm_powerup.png">就绪</s:elseif>
								<s:elseif test="miniPmInfo.curStatus == \"2\""><img style="margin-bottom: -3px" alt="运行" src="../images/pm_running.png">运行</s:elseif>
								<s:elseif test="miniPmInfo.curStatus == \"3\""><img style="margin-bottom: -3px" alt="安装中" src="../images/pm_processing.png">安装中</s:elseif>
						        &nbsp;
		            	    </span>
		            	</li>
		            	<li><span class="result-name">IP地址：</span><span><s:property value="miniPmInfo.ip" />&nbsp;</span></li>
		            	<li><span class="result-name">主机型号：</span><span><s:property value="miniPmInfo.serverType" />&nbsp;</span></li>
		                <li><span class="result-name">更新时间：</span><span><s:property value="miniPmInfo.updateTime" />&nbsp;</span></li>
		            </ul>
		        </div>
		        <div class="result">
		        	<h5 class="label">
		            	<span class="label-bg">配置属性</span>
		            </h5>
		            <ul class="result-list">
		            	<li><span class="result-name">cpu数量(个)：</span><span><s:property value="miniPmInfo.cpuNum" />&nbsp;</span></li>
		            	<li><span class="result-name">cpu类型：</span><span><s:property value="miniPmInfo.cpuType" />&nbsp;</span></li>
		                <li><span class="result-name">内存(GB)：</span><span><s:property value="miniPmInfo.memorySize" />&nbsp;</span></li>
		            </ul>
		            <ul class="result-list">
		            	<li><span class="result-name">cpu主频(GHz)：</span><span><s:property value="miniPmInfo.cpuFrequency" />&nbsp;</span></li>
		            	<li><span class="result-name">磁盘空间(GB)：</span><span><s:property value="miniPmInfo.diskSize" />&nbsp;</span></li>
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
	
	form.action = "miniPmListAction.action?miniPmInfo.nodeType=" + nodeType + "&miniPmInfo.nodeId="
		+ nodeId + "&miniPmInfo.treeNodeName="
		+ treeNodeName + "&miniPmInfo.pnodeId="
		+ pnodeId + "&miniPmInfo.pnodeName="
		+ pnodeName + "&miniPmInfo.appId="
		+ appId + "&miniPmInfo.curFun=" + curFun;
	form.submit();
}
</script>