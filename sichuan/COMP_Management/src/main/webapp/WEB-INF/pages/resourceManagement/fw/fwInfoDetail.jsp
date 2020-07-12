<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
       <h1>防火墙</h1>
      <div class="details-con">
   	    <div class="detail-title">
            <a href="javascript:void(0)" id='goBack' title="返回">
                <span class="back"></span>
            </a>
          <h3 class="apply-title">防火墙详情</h3>
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
		            	<li><span class="result-name">所属资源池：</span><span><s:property value="fwInfo.resPoolName" />&nbsp;</span></li>
		            </ul>
		        </div>
		        <div class="result" style="width: 130%">
		        	<h5 class="label">
		            	<span class="label-bg">基本信息</span>
		            </h5>
		            <ul class="result-list">
		            	<li><span class="result-name">防火墙ID：</span><span><s:property value="fwInfo.firewallId" />&nbsp;</span></li>
		            </ul>
		            <ul class="result-list">
		            	<li><span class="result-name">防火墙名称：</span><span><s:property value="fwInfo.firewallName" />&nbsp;</span></li>
		            </ul>
		            <ul class="result-list" style="width: 100%">
		            	<li><span class="result-name">所属业务系统：</span><span><s:property value="fwInfo.appNames" />&nbsp;</span></li>
		            </ul>
		            <ul class="result-list">
		                <li><span class="result-name">型号：</span><span><s:property value="fwInfo.fwType" />&nbsp;</span></li>
		                <li><span class="result-name">设备软件版本：</span><span><s:property value="fwInfo.swVersion" />&nbsp;</span></li>
		                <li><span class="result-name">网元设备厂家：</span><span><s:property value="fwInfo.vendorName" />&nbsp;</span></li>
		                <li><span class="result-name">资产来源分类：</span>
		                	<span>
		            	    	<s:if test="fwInfo.assetOriginType == \"1\"">自产</s:if>
								<s:elseif test="fwInfo.assetOriginType == \"2\"">外购</s:elseif>
								<s:elseif test="fwInfo.assetOriginType == \"3\"">借入</s:elseif>
								<s:elseif test="fwInfo.assetOriginType == \"4\"">订单</s:elseif>
								<s:elseif test="fwInfo.assetOriginType == \"5\"">第三方监控源</s:elseif>
								<s:elseif test="fwInfo.assetOriginType == \"6\"">可自义</s:elseif>
						        &nbsp;
		            	    </span>
		                </li>
		                <li><span class="result-name">资产SLA类型：</span>
		                	<span>
		            	    	<s:if test="fwInfo.assetSlaType == \"1\"">提供服务</s:if>
								<s:elseif test="fwInfo.assetSlaType == \"2\"">平台自服务</s:elseif>
						        &nbsp;
		            	    </span>
		                </li>
		            </ul>
		            <ul class="result-list">
		            	<li><span class="result-name">状态：</span>
		            	    <span>
		            	    	<s:if test="fwInfo.curStatus == \"0\""><img style="margin-bottom: -3px" alt="正常运行" src="../images/pm_running.png">正常运行</s:if>
								<s:elseif test="fwInfo.curStatus == \"1\""><img style="margin-bottom: -3px" alt="闲置" src="../images/pm_powerup.png">闲置</s:elseif>
								<s:elseif test="fwInfo.curStatus == \"2\""><img style="margin-bottom: -3px" alt="报废" src="../images/pm_avliable.png">报废</s:elseif>
								<s:elseif test="fwInfo.curStatus == \"3\""><img style="margin-bottom: -3px" alt="坏" src="../images/pm_avliable.png">坏</s:elseif>
						        &nbsp;
		            	    </span>
		            	</li>
		            	<li><span class="result-name">设备序列号：</span><span><s:property value="fwInfo.fwSerialnum" />&nbsp;</span></li>
		                <li><span class="result-name">网元IP地址：</span><span><s:property value="fwInfo.fwIp" />&nbsp;</span></li>
		                <li><span class="result-name">资产状态：</span>
		                	<span>
		            	    	<s:if test="fwInfo.assetState == \"1\"">已使用</s:if>
								<s:elseif test="fwInfo.assetState == \"2\"">未使用</s:elseif>
								<s:elseif test="fwInfo.assetState == \"3\"">不可用</s:elseif>
								<s:elseif test="fwInfo.assetState == \"4\"">丢失</s:elseif>
								<s:elseif test="fwInfo.assetState == \"5\"">待确认</s:elseif>
								<s:elseif test="fwInfo.assetState == \"6\"">已删除</s:elseif>
						        &nbsp;
		            	    </span>
		                </li>
		                <li><span class="result-name">更新时间：</span><span><s:property value="fwInfo.updateTime" />&nbsp;</span></li>
		            </ul>
		        </div>
		        <div class="result" style="width: 130%">
		        	<h5 class="label">
		            	<span class="label-bg">配置属性</span>
		            </h5>
		            <ul class="result-list">
		            	<li><span class="result-name">端口数量：</span><span><s:property value="fwInfo.portNum" />&nbsp;</span></li>
		                <li><span class="result-name">防火墙策略：</span><span><s:property value="fwInfo.firewallPolicy" />&nbsp;</span></li>
		                <!-- <li><span class="result-name">吞吐能力(KBPS)：</span><span><s:property value="fwInfo.throughput" />&nbsp;</span></li> -->
		            </ul>
		            <ul class="result-list">
		            	<li><span class="result-name">并发链接数(个)：</span><span><s:property value="fwInfo.connectNum" />&nbsp;</span></li>
		                <!-- <li><span class="result-name">新建链接数(个/秒)：</span><span><s:property value="fwInfo.newConnectNum" />&nbsp;</span></li> -->
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
	
	form.action = "fwListAction.action?fwInfo.nodeType=" + nodeType + "&fwInfo.nodeId="
		+ nodeId + "&fwInfo.treeNodeName="
		+ treeNodeName + "&fwInfo.pnodeId="
		+ pnodeId + "&fwInfo.pnodeName="
		+ pnodeName + "&fwInfo.appId="
		+ appId + "&fwInfo.curFun=" + curFun;
	form.submit();
}
</script>