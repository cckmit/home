<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<h1>路由器端口</h1>
<div id="message" style="display: none;">
	<span id="error" class="error"><s:actionerror /></span>
	<span class="error"><s:fielderror /></span>
	<span class="success"><s:actionmessage /></span>
</div>
<div class="details-con">
	<div class="detail-title">
		<h3 class="apply-title">路由器端口列表</h3>
	</div>
        
	<form action="rtifListAction.action" id="search_form" name="search_form"
		method="post">
		<s:hidden id="nodeType" name="nodeType"></s:hidden>
        <s:hidden id="nodeId" name="nodeId"></s:hidden>
        <s:hidden id="treeNodeName" name="treeNodeName"></s:hidden>
        <s:hidden id="pnodeId" name="pnodeId"></s:hidden>
        <s:hidden id="pnodeName" name="pnodeName"></s:hidden>
        <s:hidden id="appId" name="appId"></s:hidden>
        <s:hidden id="curFun" name="curFun"></s:hidden>
        <s:hidden id="routerId" name="rtInfo.routerId"></s:hidden>
		<div class="table-seach" style="margin:0 8px;">
		    <p>
		    <label class="fl">路由器端口ID：</label>
			<input type="text" class="text" size="25" id="queryRouterIfId" name="rtInfo.routerIfId" maxlength="64"
				value="<s:property value="rtInfo.routerIfId" />" />
		    </p>
			<p>
			<label class="fl">路由器端口名称：</label>
			<input type="text" class="text" size="25" id="queryRouterIfName" name="rtInfo.routerIfName" maxlength="64"
				value="<s:property value="rtInfo.routerIfName" />" />
		    </p>
			<ul class="opt-btn fr">
	            <li>
	                <a id="search" class="search" href="javascript:void(0);">查询</a>
	            </li>
	        </ul>

		</div>
	<!-- 列表 -->
	<div id="approval-will" class="table-content table-block">
		<table id="ordertable" width="100%" border="0" cellspacing="0"
			cellpadding="0">
			<col style="width: 13%;" />
			<col style="width: 13%;" />
			<col style="width: 13%;" />
			<col style="width: 10%;" />
			<col style="width: 10%;" />
			<col style="width: 10%;" />
			<col style="width: 10%;" />
			<col style="width: 10%;" />
			<tr>
				<th>端口ID</th>
				<th>端口名称</th>
				<th>所属路由器</th>
				<th>类型</th>
				<th>速率(KBPS)</th>
				<th>状态</th>
				<th>更新时间</th>
				<th>操作</th>
			</tr>
			<s:iterator value="rtInfoList">
				<tr class="iterator">
					<td><s:property value="routerIfId" /></td>
					<td><s:property value="routerIfName" /></td>
					<td><s:property value="routerName" /></td>
					<td><s:property value="ifType" /></td>
					<td><s:property value="ifSpeed" /></td>
					<td><s:property value="ifStatus" /></td>
					<td align="center"><s:property value="updateTime" /></td>
					<td class="table-opt-block" >
						<a href="javascript:void(0);"
						onclick="rtifInfoDetail('<s:property value="routerIfId" />');return false;">详情</a>
					</td>
				</tr>
			</s:iterator>
		</table>
	</div>
	</form>
	<!-- 翻页 -->
	<div class="pageBar">
		<s:property value="pageBar" escape="false" />
	</div>
</div>

<script type="text/javascript">
$(function() {

    /**
     * 搜索按钮事件
     */
    $('#search').click(function() {
	//$('#search_form').submit();
	document.getElementById("search_form").submit();
    });
    
    $("text,select").uniform();
	$.uniform.update();
});

/**
 * 实现分页空间的数据获取方法,未来考虑扩展为jquery插件..
 */
var getPageData = function(url){
    searchRoleData(url,true);
};

function rtifInfoDetail(routerIfId) {
	var form = document.getElementById("search_form");
	var nodeType = document.getElementById('nodeType').value;
	var nodeId = document.getElementById('nodeId').value;
	var treeNodeName = document.getElementById('treeNodeName').value;
	var pnodeId = document.getElementById('pnodeId').value;
	var pnodeName = document.getElementById('pnodeName').value;
	var appId = document.getElementById('appId').value;
	var curFun = document.getElementById('curFun').value;
	
	form.action = "rtifInfoDetail.action?rtInfo.nodeType=" + nodeType + "&rtInfo.nodeId="
		+ nodeId + "&rtInfo.treeNodeName="
		+ treeNodeName + "&rtInfo.pnodeId="
		+ pnodeId + "&rtInfo.pnodeName="
		+ pnodeName + "&rtInfo.appId="
		+ appId + "&rtInfo.curFun="
		+ curFun + "&routerIfId=" + routerIfId;
	form.submit();
}
</script>
<script type="text/javascript" src="../scripts/pagesjs/resourceManagement/getCabinetList.js"></script>
	