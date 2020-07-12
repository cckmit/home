<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<h1>物理机控制台</h1>
<div id="message" style="display: none;">
	<span id="error" class="error"><s:actionerror /></span> <span
		class="error"><s:fielderror /></span> <span class="success"><s:actionmessage /></span>
</div>
<!-- 状态列表
<ul class="status-tab">
	<li class="selected"><a href="#">全部<span></span></a></li>
	<li><a href="#" rel="s-green">运行中<span></span></a></li>
	<li><a href="#" rel="s-gray">已停止<span></span></a></li>
	<li><a href="#" rel="s-blue">创建中<span></span></a></li>
</ul>
 -->
<s:form id="gotoForm">
	<input type="hidden" value="" id="pmId" name="pmId"/>
	<input type="hidden" value="" id="caseId" name="caseId"/>
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
<s:url id="addUrl" action="pmApply">
	<s:param name="nodeType" value="nodeType" />
	<s:param name="nodeId" value="nodeId"/>
    <s:param name="treeNodeName" value="treeNodeName"/>
    <s:param name="pnodeId" value="pnodeId"/>
    <s:param name="pnodeName" value="pnodeName"/>
    <s:param name="appId" value="appId"/>
    <s:param name="curFun" value="curFun"/>
	<s:param name="queryBusinessId"><s:property value="#parameters.queryBusinessId" /></s:param>
</s:url>
<%-- <a href="pmApplyAction.action?appId=<s:property value="appId"/>&appName=<s:property value="appName"/>">申请物理机</a> --%>
<a href="${addUrl}">申请物理机</a>
<input type="hidden" value="<s:property value="appId"/>" id="param-appId" name="appId"/>
<input type="hidden" value="<s:property value="appName"/>" id="param-appName" name="caseId"/>
</span>
<!-- 查询条件 -->
<div class="table-seach" style="float: left">
	<p>
		IP：<input class="text" type="text" size="23" id="privateIp"
			name="privateIp" maxlength="15"
			value="<s:property value="privateIp" />" />
	</p>
	<p>
		操作系统：<input class="text" type="text" size="25" id="isoName"
			name="isoName" maxlength="50"
			value="<s:property value="isoName" />" />
	</p>
	<p>
		名称：<input class="text" type="text" size="25" id="pmName"
			name="pmName" maxlength="25" value="<s:property value="pmName" />" />
	</p>
	<ul class="opt-btn fr">
		<li><a class="search" href="#" onclick="queryPm('','<s:property value="queryBusinessId" />')">查询</a></li>
	</ul>
</div>
<!-- 主机列表 -->
<ul class="resource-list" id="resultList">
	<s:if test="pmResultInfos.size()>0">
		<s:iterator value="#request.pmResultInfos">
			<li status="<s:property value='status'/>"
				id="s<s:property value='pmId'/>" name="<s:property value='pmId'/>">
				<a href="javascript:void(0);"
				onclick="goToPMPage('<s:property value='caseId'/>');return false;">
					<span class="ico-m ico-pm"></span>
					<div>
						<h2>
							<s:if test="pmName.trim()!=''">
								<s:property value="pmName" />
							</s:if>
							<s:else>&nbsp;</s:else>
						</h2>
					</div>
					<div>内网IP：<s:property value="ipStr" /></div>
					<div>
						创建时间：<SPAN id="effectiveTime<s:property value='pmId'/>"><s:property
								value="effectiveTime" /></SPAN>
					</div>
					<!-- 
					<div id="status<s:property value="pmId"/>">
						<span
							class="status
		        			<s:if test="status.code==0">s-blue</s:if>
		        			<s:if test="status.code==1">s-blue</s:if>
		        			<s:if test="status.code==2">s-green</s:if>
		        			<s:if test="status.code==3">s-brown</s:if>
		        			<s:if test="status.code==4">s-gray</s:if>
		        			<s:if test="status.code==5">s-orange</s:if>
		        			<s:if test="status.code==6">s-red</s:if>
		        			<s:if test="status.code==7">s-waterblue</s:if>
		        			<s:if test="status.code==8">s-wathet</s:if>
		        			<s:if test="status.code==9">s-deongaree</s:if>
		        			<s:if test="status.code==10">s-orange</s:if>
		        			<s:if test="status.code==11">s-orange</s:if>
		        			">
							<%-- 样式判断结束 --%> <s:property value="status.desc" />
						</span>
					</div>
					 -->
					 <div>
						<span id="status<s:property value="pmId"/>" class="status s-blue">
							<s:if test="pmId == null || pmId == ''">待创建</s:if>
				            <s:else>加载中</s:else>
						</span>
						<s:property value="isoName" />
					</div>
					<div>
						所属业务：
						<s:property value="appName" />
					</div>
					<div>
						到期时间：<SPAN id="overTime<s:property value='pmId'/>"><s:property
								value="overTime" /></SPAN>
					</div>
			</a>
			</li>
		</s:iterator>
	</s:if>
</ul>
<!-- 列表无数据 -->
<ul id="listNull" class="resource-list">
	<li><a href="javascript:void(0);"> 暂无符合条件数据 </a></li>
</ul>

<!-- 翻页 -->
<div class="pageBar">
	<s:property value="pageBar" escape="false" />
</div>

<s:if test="pmResultInfos.size()<=0">
	<script type="text/javascript">
            $("#listNull li").show();
			</script>
</s:if>

<script type="text/javascript"
	src="../scripts/pagesjs/console/business/host/pm_list.js"></script>
<script type="text/javascript" language=JavaScript charset="UTF-8">
      document.onkeydown=function(event){
            var e = event || window.event || arguments.callee.caller.arguments[0];
            
             if(e && e.keyCode==13){ // enter 键
            	 queryPm();
            }
        }; 
</script>