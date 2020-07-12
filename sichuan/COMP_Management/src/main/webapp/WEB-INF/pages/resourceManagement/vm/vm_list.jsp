<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<h1>虚拟机</h1>
<div id="message" style="display: none;">
	<span id="error" class="error"><s:actionerror /></span>
	<span class="error"><s:fielderror /></span>
	<span class="success"><s:actionmessage /></span>
</div>
<div class="details-con">
	<div class="detail-title">
		<h3 class="apply-title">虚拟机列表</h3>
	</div>     
	<form action="resViewVmListAction.action" id="search_form" name="search_form"
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
			<label class="fl">虚拟机ID：</label>
			<input type="text" class="text" size="25" id="queryvmId" name="queryVmInfo.vmId" maxlength="64"
				value="<s:property value="queryVmInfo.vmId" />" />
			</p>
			<p>
			<label class="fl">虚拟机名称：</label>
			<input type="text" class="text" size="25" id="queryvmName" name="queryVmInfo.vmName" maxlength="64"
				value="<s:property value="queryVmInfo.vmName" />" />
			</p>
			<%--  <p>
			<label class="fl">状态：</label>
			<s:select id="" cssClass="select-min" headerKey = "" headerValue = "-请选择-" name="" 
				list="" listKey="" listValue="" onchange=""/> 
			</p>    --%>
			 <p>
			<label class="fl">状态：</label>
			<select class="select-min" id="querycurStatus"
					name="queryVmInfo.querycurStatus">
					<option value=""
						<s:if test="queryVmInfo.querycurStatus == \"\"">selected="selected"</s:if>>-请选择-</option>
					 <option value="0"
						<s:if test="queryVmInfo.querycurStatus == \"0\"">selected="selected"</s:if>>不可用</option> 
					<option value="1"
						<s:if test="queryVmInfo.querycurStatus == \"1\"">selected="selected"</s:if>>已加电</option>
					<option value="2"
						<s:if test="queryVmInfo.querycurStatus == \"2\"">selected="selected"</s:if>>运行</option>
					<option value="3"
						<s:if test="queryVmInfo.querycurStatus == \"3\"">selected="selected"</s:if>>关机</option>
					<option value="4"
						<s:if test="queryVmInfo.querycurStatus == \"4\"">selected="selected"</s:if>>休眠</option>
					<option value="5"
						<s:if test="queryVmInfo.querycurStatus == \"5\"">selected="selected"</s:if>>处理中</option>
			</select>
			</p> 
			
			<%--  <p>
			<label class="fl">状态：</label>
			<s:select id="curStatus" cssClass="select-min" headerKey = "" headerValue = "-请选择-" name="queryPmInfos.curStatus" 
				list="vmInfoList" listKey="curStatus" listValue="curStatus" onchange="queryVmInfos.querycurStatus;"/> 
			</p>   --%>
			
			
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
			<col style="width: 18%;min-width: 250px;" />
			<col style="width: 19%;" />
			<col style="width: 14%;" />
			<col style="width: 8%;" />
			<col style="width: 8%;" />
			<col style="width: 8%;" />
			<col style="width: 6%;" />
			<col style="width: 14%;" />
			<col style="width: 7%;" />

			<tr>
				<th>虚拟机ID</th>
				<th>虚拟机名称</th>
				<th>IP地址</th>
				<th>CPU数量(个)</th>
				<th>内存(GB)</th>
				<th>磁盘空间(GB)</th>
				<th>状态</th>
				<th>更新时间</th>
				<th>操作</th>
			</tr>
			<s:iterator value="vmInfoList">
				<tr class="iterator">
					<td><s:property value="vmId" /></td>
					<td><s:property value="vmName" /></td>
<%-- 					<td><s:property value="privateIp" /></td> --%>
                    <td align="center"><div style="overflow:hidden; width:140px; height:30px;" title="<s:property value="privateIp" />"><s:property value="privateIp" /></div></td>
					<td><s:property value="cpuNum" /></td>
					<td align="center"><s:property value="memorySize" /></td>
					<td align="center"><s:property value="diskSize" /></td>
					<td style="padding-left: 10px; text-align: left">
						<s:if test="curStatus == \"0\""><img style="margin-bottom: -3px" alt="不可用" src="../images/pm_avliable.png">不可用</s:if>
							<s:elseif test="curStatus == \"1\""><img style="margin-bottom: -3px" alt="已加电" src="../images/pm_powerup.png">已加电</s:elseif>
							<s:elseif test="curStatus == \"2\""><img style="margin-bottom: -3px" alt="运行" src="../images/pm_running.png">运行</s:elseif>
							<s:elseif test="curStatus == \"3\""><img style="margin-bottom: -3px" alt="关机" src="../images/pm_shutdown.png">关机</s:elseif>
							<s:elseif test="curStatus == \"4\""><img style="margin-bottom: -3px" alt="休眠" src="../images/pm_pause.png">休眠</s:elseif>
							<s:elseif test="curStatus == \"5\""><img style="margin-bottom: -3px" alt="处理中" src="../images/pm_processing.png">处理中</s:elseif>
					</td>
					<td align="center"><s:property value="updateTime" /></td>
					<td align="center" class="table-opt-block">
						<a href="javascript:void(0);"
						onclick="vmInfoDetail('<s:property value="vmId" />');return false;">详情</a>
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
	url : 'resViewVmListAction.action'
    });
}
function vmInfoDetail(vmId) {
	var form = document.getElementById("search_form");
	var nodeType = document.getElementById('nodeType').value;
	var nodeId = document.getElementById('nodeId').value;
	var treeNodeName = document.getElementById('treeNodeName').value;
	var pnodeId = document.getElementById('pnodeId').value;
	var pnodeName = document.getElementById('pnodeName').value;
	var appId = document.getElementById('appId').value;
	var curFun = document.getElementById('curFun').value;
	
	form.action = "resViewVmInfoDetail.action?vmInfo.nodeType=" + nodeType + "&vmInfo.nodeId="
		+ nodeId + "&vmInfo.treeNodeName="
		+ treeNodeName + "&vmInfo.pnodeId="
		+ pnodeId + "&vmInfo.pnodeName="
		+ pnodeName + "&vmInfo.appId="
		+ appId + "&vmInfo.curFun="
		+ curFun + "&vmInfo.vmId=" + vmId;
	form.submit();
}
</script>
	