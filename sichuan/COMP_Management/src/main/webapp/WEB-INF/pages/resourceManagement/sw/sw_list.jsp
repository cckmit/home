<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<h1>交换机</h1>
<div id="message" style="display: none;">
	<span id="error" class="error"><s:actionerror /></span>
	<span class="error"><s:fielderror /></span>
	<span class="success"><s:actionmessage /></span>
</div>
<div class="details-con">
	<div class="detail-title">
		<h3 class="apply-title">交换机列表</h3>
	</div>
        
	<form action="swListAction.action" id="search_form" name="search_form"
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
		    <label class="fl">交换机ID：</label>
			<input type="text" class="text" size="25" id="querySwitchId" name="querySwInfo.switchId" maxlength="64"
				value="<s:property value="querySwInfo.switchId" />" />
		    </p>
			<p>
			<label class="fl">交换机名称：</label>
			<input type="text" class="text" size="25" id="querySwitchName" name="querySwInfo.switchName" maxlength="64"
				value="<s:property value="querySwInfo.switchName" />" />
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
             <col style="width: 14%;" />
             <col style="width: 8%;" />
             <col style="width: 7%;" />
             <col style="width: 7%;" />
             <col style="width: 4%;" />
             <col style="width: 11%;" />
             <col style="width: 9%;" />

			<tr>
				<th>交换机ID</th>
				<th>交换机名称</th>
				<th>交换机型号</th>
				<th>设备厂商</th>
				<th>IP地址</th>
				<th>状态</th>
				<th>更新时间</th>
				<th>操作</th>
			</tr>
			<s:iterator value="swInfoList">
				<tr class="iterator">
					<td>
					    <s:property value="switchId" />
					</td>
					<td><s:property value="switchName" /></td>
					<td><s:property value="switchType" /></td>
					<td><s:property value="vendorName" /></td>
               <%-- <td><s:property value="switchIp" /></td> --%>
					<td align="center"><div style="overflow:hidden; width:125px; height:30px;" title="<s:property value="switchIp"/>"><s:property value="switchIp"/></div></td>
					<td style="padding-left: 10px; text-align: left">
						<s:if test="curStatus == \"0\""><img style="margin-bottom: -3px" alt="运行" src="../images/pm_running.png" >运行</s:if>
						<s:elseif test="curStatus == \"1\""><img style="margin-bottom: -3px" alt="闲置" src="../images/pm_pause.png">闲置</s:elseif>
						<s:elseif test="curStatus == \"2\""><img style="margin-bottom: -3px" alt="报废" src="../images/pm_avliable.png">报废</s:elseif>
						<s:elseif test="curStatus == \"3\""><img style="margin-bottom: -3px" alt="损坏" src="../images/pm_shutdown.png">损坏</s:elseif>
					</td>
					<td align="center"><s:property value="updateTime" /></td>
					<td class="table-opt-block" >
						<a href="javascript:void(0);"
						onclick="swInfoDetail('<s:property value="switchId" />');return false;">详情</a>
						<a href="javascript:void(0);"
						onclick="swIfInfos('<s:property value="switchId" />','<s:property value="switchName" />');return false;">查看端口</a>
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
	url : 'swListAction.action'
    });
}
function swInfoDetail(switchId) {
	var form = document.getElementById("search_form");
	var nodeType = document.getElementById('nodeType').value;
	var nodeId = document.getElementById('nodeId').value;
	var treeNodeName = document.getElementById('treeNodeName').value;
	var pnodeId = document.getElementById('pnodeId').value;
	var pnodeName = document.getElementById('pnodeName').value;
	var appId = document.getElementById('appId').value;
	var curFun = document.getElementById('curFun').value;
	
	form.action = "swInfoDetail.action?swInfo.nodeType=" + nodeType + "&swInfo.nodeId="
		+ nodeId + "&swInfo.treeNodeName="
		+ treeNodeName + "&swInfo.pnodeId="
		+ pnodeId + "&swInfo.pnodeName="
		+ pnodeName + "&swInfo.appId="
		+ appId + "&swInfo.curFun="
		+ curFun + "&swInfo.switchId=" + switchId;
	form.submit();
}

function swIfInfos(switchId,switchName){
	var form = document.getElementById("search_form");
	var nodeType = document.getElementById('nodeType').value;
	var nodeId = document.getElementById('nodeId').value;
	var treeNodeName = document.getElementById('treeNodeName').value;
	var pnodeId = document.getElementById('pnodeId').value;
	var pnodeName = document.getElementById('pnodeName').value;
	var appId = document.getElementById('appId').value;
	var curFun = document.getElementById('curFun').value;
	
	form.action = "swIfListAction.action?querySwIfInfo.nodeType=" + nodeType 
			+ "&querySwIfInfo.nodeId=" + nodeId 
			+ "&querySwIfInfo.treeNodeName=" + treeNodeName 
			+ "&querySwIfInfo.pnodeId=" + pnodeId 
			+ "&querySwIfInfo.pnodeName=" + pnodeName 
			+ "&querySwIfInfo.appId=" + appId 
			+ "&querySwIfInfo.curFun=" + curFun 
			+ "&querySwIfInfo.switchId=" + switchId
			+ "&querySwIfInfo.switchName=" + switchName;
	form.submit();
}
</script>
	