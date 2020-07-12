<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<h1>负载均衡控制台</h1>
<div id="message" style="display: none;">
	<span id="error" class="error"><s:actionerror /></span> <span
		class="error"><s:fielderror /></span> <span class="success"><s:actionmessage /></span>
</div>
</ul>
<%-- <s:form id="gotoForm"> --%>
<!-- 	<input type="hidden" value="" id="lbid" -->
<!-- 		name="LBid"> -->
<%-- </s:form> --%>
<s:form id="gotoForm">
	<input type="hidden" value="" id="lbid" name="LBid">
	<input type="hidden" value="<s:property value='msg' />" id="msg" />
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
<s:url id="addUrl" action="lbApply">
	<s:param name="nodeType" value="nodeType" />
	<s:param name="nodeId" value="nodeId"/>
    <s:param name="treeNodeName" value="treeNodeName"/>
    <s:param name="pnodeId" value="pnodeId"/>
    <s:param name="pnodeName" value="pnodeName"/>
    <s:param name="appId" value="appId"/>
    <s:param name="curFun" value="curFun"/>
	<s:param name="queryBusinessId"><s:property value="#parameters.queryBusinessId" /></s:param>
</s:url>
<a href="${addUrl}">申请负载均衡</a>
</span>
<%-- <input type="hidden" value="<s:property value='msg' />" id="msg" /> --%>
<%-- <span class="apply" ><a href="LBapplyAction.action"></a></span> --%>
<!-- 查询条件 -->
<div class="table-seach" style="float: left;margin:15px 10px;">
	<p>
		IP：<input class="text" type="text" size="23" id="lbip"
			name="privateIp" maxlength="15"
			value="<s:property value="lbip" />" />
	</p>
<!-- 	<p> -->
<!-- 		操作系统：<input class="text" type="text" size="25" id="isoName" -->
<%-- 			name="isoName" maxlength="50" value="<s:property value="isoName" />" /> --%>
<!-- 	</p> -->
	<p>
		名称：<input class="text" type="text" size="25" id="lbname"
			name="vmName" maxlength="25" value="<s:property value="lbname" />" />
	</p>
    <ul class="opt-btn fr">
		<li><a class="search" href="#" onclick="queryLbBatch()">查询</a></li>
	</ul>
</div>
	<s:form id="selectlb">
	<s:hidden id="nodeType" name="nodeType"></s:hidden>
    <s:hidden id="nodeId" name="nodeId"></s:hidden>
    <s:hidden id="treeNodeName" name="treeNodeName"></s:hidden>
    <s:hidden id="pnodeId" name="pnodeId"></s:hidden>
    <s:hidden id="pnodeName" name="pnodeName"></s:hidden>
    <s:hidden id="appId" name="appId"></s:hidden>
    <s:hidden id="curFun" name="curFun"></s:hidden>
    </s:form>
<!-- 主机列表 -->
<ul class="resource-list" id="resultList">
	<s:if test="lbList.size()>0">
		<s:iterator value="lbList">
			<li status="<s:property value='status'/>"
				id="<s:property value='LBid'/>" >
				<a href="javascript:void(0);"
				onclick="goToPage('<s:property value='LBid'/>');return false;">
					 <span
					class="ico-m ico-pm"></span>
					<div>
						<h2>
							<s:if test="lbname.trim()!=''">
								<s:property value="lbname" />
							</s:if>
							<s:else>&nbsp;</s:else>
						</h2>
					</div>
					<div
						style="width: 230px; overflow: hidden; white-space: nowrap; text-overflow: ellipsis"
						title="<s:property value='lbip'/>">
						浮动IP：
						<s:property value="lbip" />
					</div>
					<div>
						创建时间：<SPAN id="effectiveTime<s:property value='vmId'/>"><s:property
								value="createtime" /></SPAN>
					</div>
					<div>
						<span id="status<s:property value="vmId"/>" class="status s-blue">
							<s:if test="status==0">待创建</s:if>
				            <s:elseif test="status==1">运行中</s:elseif>
						</span>
					</div>
					<div>
						所属业务：
						<s:property value="appname" />
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
<s:if test="lbList.size()<=0">
	<script type="text/javascript">
		$("#listNull li").show();
	</script>
</s:if>
<script type="text/javascript"
	src="../scripts/pagesjs/console/business/loadbalance/LB_list.js"></script>
<link href="../styles/bindApp.css" rel="stylesheet" type="text/css" />
<style>
.result-list li{
	color: #000;
	margin-bottom: 10px;
}
</style>
<script type="text/javascript" language="JavaScript" charset="UTF-8">
      document.onkeydown=function(event){
            var e = event || window.event || arguments.callee.caller.arguments[0];
            
             if(e && e.keyCode==13){ // enter 键
            	 queryPm();
            }
        }; 
</script>