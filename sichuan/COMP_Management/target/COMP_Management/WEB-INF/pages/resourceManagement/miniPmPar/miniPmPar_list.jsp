<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<h1>小型机分区</h1>
<div id="message" style="display: none;">
	<span id="error" class="error"><s:actionerror /></span>
	<span class="error"><s:fielderror /></span>
	<span class="success"><s:actionmessage /></span>
</div>
<div class="details-con">
	<div class="detail-title">
		<h3 class="apply-title">小型机分区列表</h3>
	</div>
        
	<form action="miniPmParListAction.action" id="search_form" name="search_form"
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
			<label class="fl">小型机分区ID：</label>
			<input type="text" class="text" size="25" id="queryminiPmParId" name="queryMiniPmParInfo.miniPmParId" maxlength="64"
				value="<s:property value="queryMiniPmParInfo.miniPmParId" />" />
			</p>
			<p>
			<label class="fl">小型机分区名称：</label>
			<input type="text" class="text" size="25" id="queryminiPmParName" name="queryMiniPmParInfo.miniPmParName" maxlength="64"
				value="<s:property value="queryMiniPmParInfo.miniPmParName" />" />
			</p>
			<p>
			<label class="fl">所属小型机名称：</label>
			<input type="text" class="text" size="25" id="queryminiPmName" name="queryMiniPmParInfo.miniPmName" maxlength="64"
				value="<s:property value="queryMiniPmParInfo.miniPmName" />" />
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
			<col style="width: 18%;" />
			<col style="width: 13%;" />
			<col style="width: 13%;" />
			<col style="width: 7%;" />
			<col style="width: 7%;" />
			<col style="width: 7%;" />
			<col style="width: 7%;" />
			<col style="width: 7%;" />
			<col style="width: 12%;" />
			<col style="width: 5%;" />

			<tr>
				<th>小型机分区ID</th>
				<th>小型机分区名称</th>
				<th>所属小型机名称</th>
				<th>IP地址</th>
				<th>CPU数量(个)</th>
				<th>内存(GB)</th>
				<th>磁盘空间(GB)</th>
				<th>状态</th>
				<th>更新时间</th>
				<th>操作</th>
			</tr>
			<s:iterator value="miniPmParInfoList">
				<tr class="iterator">
					<td><s:property value="miniPmParId" /></td>
					<td><s:property value="miniPmParName" /></td>
					<td><s:property value="miniPmName" /></td>
					<td><s:property value="privateIp" /></td>
					<td><s:property value="cpuNum" /></td>
					<td align="center"><s:property value="memorySize" /></td>
					<td align="center"><s:property value="diskSize" /></td>
					<td style="padding-left: 10px; text-align: left">
						<s:if test="curStatus == \"0\""><img style="margin-bottom: -3px" alt="不可用" src="../images/pm_avliable.png">不可用</s:if>
							<s:elseif test="curStatus == \"1\""><img style="margin-bottom: -3px" alt="就绪" src="../images/pm_powerup.png">就绪</s:elseif>
							<s:elseif test="curStatus == \"2\""><img style="margin-bottom: -3px" alt="运行" src="../images/pm_running.png">运行</s:elseif>
							<s:elseif test="curStatus == \"3\""><img style="margin-bottom: -3px" alt="安装中" src="../images/pm_processing.png">安装中</s:elseif>
					</td>
					<td align="center"><s:property value="updateTime" /></td>
					<td align="center" class="table-opt-block">
						<a href="javascript:void(0);"
						onclick="miniPmParInfoDetail('<s:property value="miniPmParId" />');return false;">详情</a>
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
	url : 'miniPmParListAction.action'
    });
}
function miniPmParInfoDetail(miniPmParId) {
	var form = document.getElementById("search_form");
	var nodeType = document.getElementById('nodeType').value;
	var nodeId = document.getElementById('nodeId').value;
	var treeNodeName = document.getElementById('treeNodeName').value;
	var pnodeId = document.getElementById('pnodeId').value;
	var pnodeName = document.getElementById('pnodeName').value;
	var appId = document.getElementById('appId').value;
	var curFun = document.getElementById('curFun').value;
	
	form.action = "miniPmParInfoDetail.action?miniPmParInfo.nodeType=" + nodeType + "&miniPmParInfo.nodeId="
		+ nodeId + "&miniPmParInfo.treeNodeName="
		+ treeNodeName + "&miniPmParInfo.pnodeId="
		+ pnodeId + "&miniPmParInfo.pnodeName="
		+ pnodeName + "&miniPmParInfo.appId="
		+ appId + "&miniPmParInfo.curFun="
		+ curFun + "&miniPmParInfo.miniPmParId=" + miniPmParId;
	form.submit();
}
</script>
	