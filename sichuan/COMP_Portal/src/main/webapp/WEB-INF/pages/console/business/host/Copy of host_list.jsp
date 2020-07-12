<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="java.util.Map"%>

<h1>虚拟机控制台</h1>
<div id="message" style="display: none;">
	<span id="error" class="error"><s:actionerror /></span> <span
		class="error"><s:fielderror /></span> <span class="success"><s:actionmessage /></span>
</div>
<!-- 状态列表 
<ul class="status-tab">
	<li class="selected"><a href="javascript:void(0);"
		onclick="will(this,'<s:property value="queryBusinessId" />');return false;" value=''>全部</a></li>
	<li><a href="javascript:void(0);"
		onclick="will(this,'<s:property value="queryBusinessId" />');return false;" value='0,1,5,7'>创建中</a></li>
	<li><a href="javascript:void(0);"
		onclick="will(this,'<s:property value="queryBusinessId" />');return false;" value='2'>运行中</a></li>
	<li><a href="javascript:void(0);"
		onclick="will(this,'<s:property value="queryBusinessId" />');return false;" value='4'>已停止</a></li>
	<%--<li class="selected"><a href="#">全部<span></span></a></li>
	<li><a href="#" rel="s-green">运行中<span></span></a></li>
	<li><a href="#" rel="s-gray">已停止<span></span></a></li>
	<li><a href="#" rel="s-blue">创建中<span></span></a></li>
	<li><a href="#" rel="s-orange">到期<span></span></a></li> 
    <li><a href="#" rel="s-red">异常<span></span></a></li> --%>
</ul>
-->
<s:form id="gotoForm">
	<input type="hidden" value="" id="vmId" name="vmId">
	<s:hidden id="nodeType" name="nodeType"></s:hidden>
    <s:hidden id="nodeId" name="nodeId"></s:hidden>
    <s:hidden id="treeNodeName" name="treeNodeName"></s:hidden>
    <s:hidden id="pnodeId" name="pnodeId"></s:hidden>
    <s:hidden id="pnodeName" name="pnodeName"></s:hidden>
    <s:hidden id="appId" name="appId"></s:hidden>
    <s:hidden id="curFun" name="curFun"></s:hidden>
</s:form>
<input type="hidden" value="<s:property value="type" />" id="type"
	name="type">
<span class="apply"> 
<s:url id="addUrl" action="vmApply">
	<s:param name="nodeType" value="nodeType" />
	<s:param name="nodeId" value="nodeId"/>
    <s:param name="treeNodeName" value="treeNodeName"/>
    <s:param name="pnodeId" value="pnodeId"/>
    <s:param name="pnodeName" value="pnodeName"/>
    <s:param name="appId" value="appId"/>
    <s:param name="curFun" value="curFun"/>
	<s:param name="queryBusinessId"><s:property value="#parameters.queryBusinessId" /></s:param>
</s:url>
<a href="${addUrl}">申请虚拟机</a>
</span>

<!-- 查询条件 -->
<div class="table-seach" style="float: left">
	<p>
		IP：<input class="text" type="text" size="23" id="privateIp"
			name="privateIp" maxlength="15"
			value="<s:property value="privateIp" />" />
	</p>
	<p>
		操作系统：<input class="text" type="text" size="23" id="isoName"
			name="isoName" maxlength="50" value="<s:property value="isoName" />" />
	</p>
	<p>
		名称：<input class="text" type="text" size="25" id="vmName"
			name="vmName" maxlength="25" value="<s:property value="vmName" />" />
	</p>
	<ul class="opt-btn fr">
		<li><a class="search" href="#" onclick="queryVm('','<s:property value="queryBusinessId" />')">查询</a></li>
	</ul>
</div>
<!-- 主机列表 -->
<ul class="resource-list" id="resultList">
	<s:iterator value="#request.vmResultInfos">
		<li status="<s:property value='status'/>"
			id="s<s:property value='vmId'/>" vtype='0'
			name="<s:property value='vmId'/>"><a href="javascript:void(0);"
			onclick="goToVMPage('<s:property value='caseId'/>');return false;">
				<input type="hidden" id="iso<s:property value='vmId'/>"
				value="<s:property value='isoName'/>" /> <span class="ico-m ico-pm"></span>
				<div>
					<h2>
						<s:if test="vmName.trim()!=''">
							<s:property value="vmName" />
						</s:if>
						<s:else>&nbsp;</s:else>
					</h2>
				</div>
				<div
					style="width: 230px; overflow: hidden; white-space: nowrap; text-overflow: ellipsis"
					title="<s:property value='privateIpStr'/>">
					内网IP：
					<s:property value="privateIpStr" />
				</div>
				<div>
					创建时间：<SPAN id="effectiveTime<s:property value='vmId'/>"><s:property
							value="effectiveTime" /></SPAN>
				</div>
				<%-- 虚拟机列表直接从接口查询 不再从数据库查询状态 edit by 王欣
				<div id="status<s:property value="vmId"/>">
					<span
							class="status
		        			<s:if test="status.code==0">s-blue</s:if>
		        			<s:if test="status.code==1">s-blue</s:if>
		        			<s:if test="status.code==2">s-green</s:if>
		        			<s:if test="status.code==3">s-brown</s:if>
		        			<s:if test="status.code==4">s-gray</s:if>
		        			<s:if test="status.code==6">s-purple</s:if>
		        			<s:if test="status.code==9">s-orange</s:if>
		        			<s:if test="status.code==14">s-orange</s:if>
		        			<s:if test="status.code==15">s-orange</s:if>
		        			<s:if test="status.code==16">s-blue</s:if>
		        			">
							<s:property value="status.desc" />
						</span>
					<s:property value="isoName" />
				</div>
				--%>
				<div>
					<span id="status<s:property value="vmId"/>" class="status s-blue">
						<s:if test="vmId == null || vmId == ''">待创建</s:if>
				        <s:else>加载中</s:else>
					</span>
					<s:property value="isoName" />
				</div>
				<div>
					所属业务：
					<s:property value="appName" />
				</div>
				<div>
					到期时间：<SPAN id="overTime<s:property value='vmId'/>"><s:property
							value="overTime" /></SPAN>
				</div>
		</a></li>
	</s:iterator>
</ul>

<!-- 列表无数据 -->
<ul id="listNull" class="resource-list">
	<li><a href="javascript:void(0);"> 暂无符合条件数据 </a></li>
</ul>

<!-- 翻页 -->
<div class="pageBar">
	<s:property value="pageBar" escape="false" />
</div>

<s:if test="vmResultInfos.size()<=0">
	<script type="text/javascript">
		$("#listNull li").show();
	</script>
</s:if>

<script type="text/javascript"
	src="../scripts/pagesjs/console/business/host/host_list.js"></script>
<script type="text/javascript" language=JavaScript charset="UTF-8">
      document.onkeydown=function(event){
            var e = event || window.event || arguments.callee.caller.arguments[0];
            
             if(e && e.keyCode==13){ // enter 键
            	 queryPm();
            }
        }; 
</script>