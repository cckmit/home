<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<h1>交换机端口</h1>
<div id="message" style="display: none;">
	<span id="error" class="error"><s:actionerror /></span>
	<span class="error"><s:fielderror /></span>
	<span class="success"><s:actionmessage /></span>
</div>
<div class="details-con">
	<div class="detail-title">
		<h3 class="apply-title">交换机端口列表</h3>
	</div>
        
	<form action="swIfListAction.action" id="search_form" name="search_form"
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
		    <label class="fl">交换机端口ID：</label>
			<input type="text" class="text" size="25" id="querySwitchIfId" name="querySwIfInfo.switchIfId" maxlength="64"
				value="<s:property value="querySwIfInfo.switchIfId" />" />
		    </p>
			<p>
			<label class="fl">交换机端口名称：</label>
			<input type="text" class="text" size="25" id="querySwitchIfName" name="querySwIfInfo.switchIfName" maxlength="64"
				value="<s:property value="querySwIfInfo.switchIfName" />" />
		    </p>
		    <p>
			<label class="fl">所属交换机名称：</label>
			<input type="text" class="text" size="25" id="querySwitchName" name="querySwIfInfo.switchName" maxlength="64"
				value="<s:property value="querySwIfInfo.switchName" />" />
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
			<col style="width: 7%;" />
			<col style="width: 7%;" />
			<col style="width: 7%;" />
			<col style="width: 6%;" />
			<col style="width: 6%;" />
			<col style="width: 11%;" />
			<col style="width: 12%;" />

			<tr>
				<th>端口ID</th>
				<th>端口名称</th>
				<th>交换机名称</th>
				<th>端口类型</th>
				<th>端口速度(kbps)</th>
				<th>状态</th>
				<th>更新时间</th>
				<th>操作</th>
			</tr>
			<s:iterator value="swIfInfoList">
				<tr class="iterator">
					<td>
					    <s:property value="switchIfId" />
					</td>
					<td><s:property value="switchIfName" /></td>
					<td><s:property value="switchName" /></td>
					<td><s:property value="ifType" /></td>
					<td><s:property value="ifSpeed" /></td>
					<td><s:property value="ifStatus" /></td>
					<td align="center"><s:property value="updateTime" /></td>
					<td class="table-opt-block" >
						<a href="javascript:void(0);"
						onclick="swIfInfoDetail('<s:property value="switchIfId" />');return false;">详情</a>
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
	url : 'swIfListAction.action'
    });
}
function swIfInfoDetail(switchIfId) {
	var form = document.getElementById("search_form");
	var nodeType = document.getElementById('nodeType').value;
	var nodeId = document.getElementById('nodeId').value;
	var treeNodeName = document.getElementById('treeNodeName').value;
	var pnodeId = document.getElementById('pnodeId').value;
	var pnodeName = document.getElementById('pnodeName').value;
	var appId = document.getElementById('appId').value;
	var curFun = document.getElementById('curFun').value;
	
	form.action = "swIfInfoDetail.action?swIfInfo.nodeType=" + nodeType + "&swIfInfo.nodeId="
		+ nodeId + "&swIfInfo.treeNodeName="
		+ treeNodeName + "&swIfInfo.pnodeId="
		+ pnodeId + "&swIfInfo.pnodeName="
		+ pnodeName + "&swIfInfo.appId="
		+ appId + "&swIfInfo.curFun="
		+ curFun + "&swIfInfo.switchIfId=" + switchIfId;
	form.submit();
}
</script>
	