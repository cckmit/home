<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<h1>路由器</h1>
<div id="message" style="display: none;">
	<span id="error" class="error"><s:actionerror /></span>
	<span class="error"><s:fielderror /></span>
	<span class="success"><s:actionmessage /></span>
</div>
<div class="details-con">
	<div class="detail-title">
		<h3 class="apply-title">路由器列表</h3>
	</div>
        
	<form action="rtListAction.action" id="search_form" name="search_form"
		method="post">
		<s:hidden id="nodeType" name="nodeType"></s:hidden>
        <s:hidden id="nodeId" name="nodeId"></s:hidden>
        <s:hidden id="treeNodeName" name="treeNodeName"></s:hidden>
        <s:hidden id="pnodeId" name="pnodeId"></s:hidden>
        <s:hidden id="pnodeName" name="pnodeName"></s:hidden>
        <s:hidden id="appId" name="appId"></s:hidden>
        <s:hidden id="curFun" name="curFun"></s:hidden>
		<div class="table-seach" style="margin:0 8px;">
		    <p>
		    <label class="fl">路由器ID：</label>
			<input type="text" class="text" size="25" id="queryRouterId" name="queryRtInfo.routerId" maxlength="64"
				value="<s:property value="queryRtInfo.routerId" />" />
		    </p>
			<p>
			<label class="fl">路由器名称：</label>
			<input type="text" class="text" size="25" id="queryRouterName" name="queryRtInfo.routerName" maxlength="64"
				value="<s:property value="queryRtInfo.routerName" />" />
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
			<col style="width: 17%;" />
			<col style="width: 17%;" />
			<col style="width: 17%;" />
			<col style="width: 10%;" />
			<col style="width: 14%;" />
			<col style="width: 14%;" />

			<tr>
				<th>路由器ID</th>
				<th>路由器名称</th>
				<th>型号</th>
				<th>状态</th>
				<th>更新时间</th>
				<th>操作</th>
			</tr>
			<s:iterator value="rtInfoList">
				<tr class="iterator">
					<td><s:property value="routerId" /></td>
					<td><s:property value="routerName" /></td>
					<td><s:property value="routerType" /></td>
					<td style="padding-left: 10px; text-align: left">
						<s:if test="curStatus == \"0\""><img style="margin-bottom: -3px" alt="正常运行" src="../images/pm_running.png">正常运行</s:if>
						<s:elseif test="curStatus == \"1\""><img style="margin-bottom: -3px" alt="闲置" src="../images/pm_powerup.png">闲置</s:elseif>
						<s:elseif test="curStatus == \"2\""><img style="margin-bottom: -3px" alt="报废" src="../images/pm_avliable.png">报废</s:elseif>
						<s:elseif test="curStatus == \"3\""><img style="margin-bottom: -3px" alt="坏" src="../images/pm_avliable.png">坏</s:elseif>
					</td>
					<td align="center"><s:property value="updateTime" /></td>
					<td class="table-opt-block" >
						<a href="javascript:void(0);"
						onclick="rtInfoDetail('<s:property value="routerId" />');return false;">详情</a>
						<a href="javascript:void(0);"
						onclick="rtifInfos('<s:property value="routerId" />');return false;">查看端口</a>
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

// 返回列表页面
function backList(msg, errMsg) {
    $.backListMsg({
	msg : msg,
	errMsg : errMsg,
	url : 'rtListAction.action'
    });
}
function rtInfoDetail(routerId) {
	var form = document.getElementById("search_form");
	var nodeType = document.getElementById('nodeType').value;
	var nodeId = document.getElementById('nodeId').value;
	var treeNodeName = document.getElementById('treeNodeName').value;
	var pnodeId = document.getElementById('pnodeId').value;
	var pnodeName = document.getElementById('pnodeName').value;
	var appId = document.getElementById('appId').value;
	var curFun = document.getElementById('curFun').value;
	
	form.action = "rtInfoDetail.action?rtInfo.nodeType=" + nodeType + "&rtInfo.nodeId="
		+ nodeId + "&rtInfo.treeNodeName="
		+ treeNodeName + "&rtInfo.pnodeId="
		+ pnodeId + "&rtInfo.pnodeName="
		+ pnodeName + "&rtInfo.appId="
		+ appId + "&rtInfo.curFun="
		+ curFun + "&rtInfo.routerId=" + routerId;
	form.submit();
}

function rtifInfos(routerId){
	var form = document.getElementById("search_form");
	var nodeType = document.getElementById('nodeType').value;
	var nodeId = document.getElementById('nodeId').value;
	var treeNodeName = document.getElementById('treeNodeName').value;
	var pnodeId = document.getElementById('pnodeId').value;
	var pnodeName = document.getElementById('pnodeName').value;
	var appId = document.getElementById('appId').value;
	var curFun = document.getElementById('curFun').value;
	
	form.action = "rtifListAction.action?rtInfo.nodeType=" + nodeType 
			+ "&rtInfo.nodeId=" + nodeId 
			+ "&rtInfo.treeNodeName=" + treeNodeName 
			+ "&rtInfo.pnodeId=" + pnodeId 
			+ "&rtInfo.pnodeName=" + pnodeName 
			+ "&rtInfo.appId=" + appId 
			+ "&rtInfo.curFun=" + curFun 
			+ "&rtInfo.routerId=" + routerId;
	form.submit();
}
</script>
<script type="text/javascript" src="../scripts/pagesjs/resourceManagement/getCabinetList.js"></script>
	