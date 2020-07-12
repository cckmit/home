<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<h1>云硬盘</h1>
<div id="message" style="display: none;">
	<span id="error" class="error"><s:actionerror /></span>
	<span class="error"><s:fielderror /></span>
	<span class="success"><s:actionmessage /></span>
</div>
<div class="details-con">
	<div class="detail-title">
		<h3 class="apply-title">云硬盘列表</h3>
	</div>
        
	<form action="resViewEbsListAction.action" id="search_form" name="search_form"
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
			<label class="fl">云硬盘ID：</label>
			<input type="text" class="text" size="25" id="queryEbsId" name="queryEbsInfo.ebsId" maxlength="64"
				value="<s:property value="queryEbsInfo.ebsId" />" />
			</p>
			<p>
			<label class="fl">云硬盘名称：</label>
			<input type="text" class="text" size="25" id="queryEbsName" name="queryEbsInfo.ebsName" maxlength="64"
				value="<s:property value="queryEbsInfo.ebsName" />" />
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
			<col style="width: 19%;min-width: 250px;" />
			<col style="width: 18%;" />
			<col style="width: 12%;" />
			<col style="width: 8%;" />
			<col style="width: 20%;" />
			<col style="width: 16%;" />
			<col style="width: 10%;" />

			<tr>
				<th>云硬盘ID</th>
				<th>云硬盘名称</th>
				<th>磁盘空间(GB)</th>
				<th>状态</th>
				<th>挂载虚拟机名称</th>
				<th>更新时间</th>
				<th>操作</th>
			</tr>
			<s:iterator value="ebsInfoList">
				<tr class="iterator">
					<td><s:property value="ebsId" /></td>
					<td><s:property value="ebsName" /></td>
					<td><s:property value="diskSize" /></td>
					<td style="padding-left: 10px; text-align: left">
						<s:if test="curStatus == \"1\""><img style="margin-bottom: -3px" alt="已创建" src="../images/ebs_nomount.png">已创建</s:if>
						<s:elseif test="curStatus == \"2\""><img style="margin-bottom: -3px" alt="已挂载" src="../images/ebs_mountd.png">已挂载</s:elseif>
						<s:elseif test="curStatus == \"3\""><img style="margin-bottom: -3px" alt="创建失败" src="../images/ebs_fail.png">创建失败</s:elseif>
						<s:elseif test="curStatus == \"4\""><img style="margin-bottom: -3px" alt="挂载失败" src="../images/ebs_nomount.png">挂载失败</s:elseif>
						<s:elseif test="curStatus == \"5\""><img style="margin-bottom: -3px" alt="创建中" src="../images/ebs_processing.png">创建中</s:elseif>
					</td>
					<td align="center"><s:property value="vmName" /></td>
					<td align="center"><s:property value="updateTime" /></td>
					<td align="center" class="table-opt-block">
						<a href="javascript:void(0);"
						onclick="ebsInfoDetail('<s:property value="ebsId" />');return false;">详情</a>
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

<script type="text/javascript"
	src="../scripts/pagesjs/resourceManagement/vm/vm_list.js"></script>
	
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
	url : 'resViewEbsListAction.action'
    });
}
function ebsInfoDetail(ebsId) {
	var form = document.getElementById("search_form");
	var nodeType = document.getElementById('nodeType').value;
	var nodeId = document.getElementById('nodeId').value;
	var treeNodeName = document.getElementById('treeNodeName').value;
	var pnodeId = document.getElementById('pnodeId').value;
	var pnodeName = document.getElementById('pnodeName').value;
	var appId = document.getElementById('appId').value;
	var curFun = document.getElementById('curFun').value;
	
	form.action = "resViewEbsInfoDetail.action?ebsInfo.nodeType=" + nodeType + "&ebsInfo.nodeId="
		+ nodeId + "&ebsInfo.treeNodeName="
		+ treeNodeName + "&ebsInfo.pnodeId="
		+ pnodeId + "&ebsInfo.pnodeName="
		+ pnodeName + "&ebsInfo.appId="
		+ appId + "&ebsInfo.curFun="
		+ curFun + "&ebsInfo.ebsId=" + ebsId;
	form.submit();
}
</script>
	