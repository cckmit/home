<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

       <h1>小型机分区</h1>
      <div class="details-con">
   	      <div class="detail-title">
              <a href="javascript:void(0)" id='goBack' title="返回">
                  <span class="back"></span>
              </a>
              <h3 class="apply-title">小型机分区详情</h3>
              <ul class="title-btn">
	           <li class="select">
	           <s:url id="miniPmParInfoDetailUrl" action="miniPmParInfoDetail">
					<s:param name="miniPmParInfo.miniPmParId">${miniPmParInfo.miniPmParId}</s:param>
					<s:param name="nodeType">${nodeType}</s:param>
					<s:param name="nodeId">${nodeId}</s:param>
					<s:param name="treeNodeName">${treeNodeName}</s:param>
					<s:param name="pnodeId">${pnodeId}</s:param>
					<s:param name="pnodeName">${pnodeName}</s:param>
					<s:param name="appId">${appId}</s:param>
					<s:param name="curFun">${curFun}</s:param>
				</s:url>
				<a href="${miniPmParInfoDetailUrl}">配置属性</a>
	           </li>
	           <li>
		           <s:url id="realTimeUrl" action="realTimeReport">
					<s:param name="deviceId">${miniPmParInfo.miniPmParId}</s:param>
					<s:param name="staType">CPU</s:param>
					<s:param name="deviceType">MINIPMPAR</s:param>
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
		            	<li><span class="result-name">所属资源池：</span><span><s:property value="miniPmParInfo.resPoolName" />&nbsp;</span></li>
		                <li><span class="result-name">所属小型机：</span><span><s:property value="miniPmParInfo.miniPmName" />&nbsp;</span></li>
		            </ul>
		            <ul class="result-list">
		            	<li><span class="result-name">所属分区：</span><span><s:property value="miniPmParInfo.resPoolPartName" />&nbsp;</span></li>
		            </ul>
		        </div>
		        <div class="result">
		        	<h5 class="label">
		            	<span class="label-bg">基本信息</span>
		            </h5>
		            <ul class="result-list">
		            	<li><span class="result-name">小型机分区ID：</span><span><s:property value="miniPmParInfo.miniPmParId" />&nbsp;</span></li>
		                <li><span class="result-name">所属业务组：</span><span><s:property value="miniPmParInfo.appName" />&nbsp;</span></li>
		                <li><span class="result-name">操作系统：</span><span><s:property value="miniPmParInfo.vmOs" />&nbsp;</span></li>
		                <li><span class="result-name">投入生产运行时间：</span><span><s:property value="miniPmParInfo.runTime" />&nbsp;</span></li>
		                <li><span class="result-name">更新时间：</span><span><s:property value="miniPmParInfo.updateTime" />&nbsp;</span></li>
		            </ul>
		            <ul class="result-list">
		            	<li><span class="result-name">小型机分区名称：</span><span><s:property value="miniPmParInfo.miniPmParName" />&nbsp;</span></li>
		            	<li><span class="result-name">状态：</span>
		            	    <span>
						        <s:if test="miniPmParInfo.curStatus == \"0\""><img style="margin-bottom: -3px" alt="不可用" src="../images/pm_avliable.png">不可用</s:if>
								<s:elseif test="miniPmParInfo.curStatus == \"1\""><img style="margin-bottom: -3px" alt="就绪" src="../images/pm_powerup.png">就绪</s:elseif>
								<s:elseif test="miniPmParInfo.curStatus == \"2\""><img style="margin-bottom: -3px" alt="运行" src="../images/pm_running.png">运行</s:elseif>
								<s:elseif test="miniPmParInfo.curStatus == \"3\""><img style="margin-bottom: -3px" alt="安装中" src="../images/pm_processing.png">安装中</s:elseif>
						        &nbsp;
		            	    </span>
		            	</li>
		            	
		            	<li><span class="result-name">操作系统盘类型：</span><span><s:property value="miniPmParInfo.osType" />&nbsp;</span></li>
		            	<li><span class="result-name">IP地址：</span><span><s:property value="miniPmParInfo.privateIp" />&nbsp;</span></li>
		            </ul>
		        </div>
		        <div class="result">
		        	<h5 class="label">
		            	<span class="label-bg">配置属性</span>
		            </h5>
		            <ul class="result-list">
		            	<li><span class="result-name">cpu数量(个)：</span><span><s:property value="miniPmParInfo.cpuNum" />&nbsp;</span></li>
		                <li><span class="result-name">内存(GB)：</span><span><s:property value="miniPmParInfo.memorySize" />&nbsp;</span></li>
		            </ul>
		            <ul class="result-list">
		            	<li><span class="result-name">cpu主频(GHz)：</span><span><s:property value="miniPmParInfo.cpuFrequency" />&nbsp;</span></li>
		            	<li><span class="result-name">磁盘空间(GB)：</span><span><s:property value="miniPmParInfo.diskSize" />&nbsp;</span></li>
		            </ul>
		        </div> 
		    </div>	
		</div>
		<div style="padding: 15px;clear: both">
           
		</div>
    </div>
    <div class="page-button" >
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
	
	form.action = "miniPmParListAction.action?miniPmParInfo.nodeType=" + nodeType + "&miniPmParInfo.nodeId="
		+ nodeId + "&miniPmParInfo.treeNodeName="
		+ treeNodeName + "&miniPmParInfo.pnodeId="
		+ pnodeId + "&miniPmParInfo.pnodeName="
		+ pnodeName + "&miniPmParInfo.appId="
		+ appId + "&miniPmParInfo.curFun=" + curFun;
	form.submit();
}
</script>