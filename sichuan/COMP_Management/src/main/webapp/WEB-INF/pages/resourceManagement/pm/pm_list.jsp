<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<h1>物理机</h1>
<div id="message" style="display: none;">
	<span id="error" class="error"><s:actionerror /></span>
	<span class="error"><s:fielderror /></span>
	<span class="success"><s:actionmessage /></span>
</div>
<div class="details-con">
	<div class="detail-title">
		<h3 class="apply-title">物理机列表</h3>
	</div>
        
	<form action="resViewPmListAction.action" id="search_form" name="search_form"
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
			<label class="fl">物理机ID：</label>
			<input type="text" class="text" size="25" id="querypmId" name="queryPmInfo.pmId" maxlength="64"
				value="<s:property value="queryPmInfo.pmId" />" /></p>
			<p>
			<label class="fl">物理机名称：</label>
			<input type="text" class="text" size="25" id="querypmName" name="queryPmInfo.pmName" maxlength="64"
				value="<s:property value="queryPmInfo.pmName" />" /></p>
			  <p>
			<label class="fl">分配状态：</label>
			<select class="select-min" id="querypmState"
					name="queryPmInfo.querypmState">
					<option value=""
						<s:if test="queryPmInfo.querypmState == \"\"">selected="selected"</s:if>>-请选择-</option>
					<option value="0"
						<s:if test="queryPmInfo.querypmState == \"0\"">selected="selected"</s:if>>未分配</option>
					<option value="1"
						<s:if test="queryPmInfo.querypmState == \"1\"">selected="selected"</s:if>>已分配</option>
					<!-- <option value="2"
						<s:if test="queryPmInfo.querypmState == \"2\"">selected="selected"</s:if>>不可用</option> -->
						</select>
			</p>  
			  <p>
			<label class="fl">运行状态：</label>
			<select class="select-min" id="querycurStatus"
					name="queryPmInfo.querycurStatus">
					<option value=""
						<s:if test="queryPmInfo.querycurStatus == \"\"">selected="selected"</s:if>>-请选择-</option>
					<option value="0"
						<s:if test="queryPmInfo.querycurStatus == \"0\"">selected="selected"</s:if>>不可用</option>
					<option value="1"
						<s:if test="queryPmInfo.querycurStatus == \"1\"">selected="selected"</s:if>>已加电</option>
					<option value="2"
						<s:if test="queryPmInfo.querycurStatus == \"2\"">selected="selected"</s:if>>运行</option>
					<option value="3"
						<s:if test="queryPmInfo.querycurStatus == \"3\"">selected="selected"</s:if>>关机</option>
					<option value="4"
						<s:if test="queryPmInfo.querycurStatus == \"4\"">selected="selected"</s:if>>休眠</option>
					<option value="5"
						<s:if test="queryPmInfo.querycurStatus == \"5\"">selected="selected"</s:if>>处理中</option>
				</select> 
			</p>	
				 
				
				
			<%--  <p>
			<label class="fl">所属机房：</label>
			<s:select id="machinerRoomId" cssClass="select-min" headerKey = "" headerValue = "-请选择-" name="queryPmInfo.machinerRoomId" 
				list="machinerRoomList" listKey="machinerRoomId" listValue="machinerRoomName" onchange="setCabinetList();"/> 
			</p>  --%>
			<%-- <p>
			<label class="fl">所属机柜：</label>
			<s:select id="cabinetId" cssClass="select-min" headerKey = "" headerValue = "-请选择-" name="queryPmInfo.cabinetId"
				list="cabinetList"  listKey="cabinetId" listValue="cabinetName" /> 
			</p> --%>
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
			<col style="width: 10%;" />
			<!-- <col style="width: 8%;" />
			<col style="width: 8%;" /> -->
			<col style="width: 11%;" />
			<col style="width: 6%;" />
			<col style="width: 6%;" />
			<col style="width: 6%;" />
			<col style="width: 6%;" />
			<col style="width: 6%;" />
			<col style="width: 11%;" />
			<col style="width: 4%;" />

			<tr>
				<th>物理机ID</th>
				<th>物理机名称</th>
				<!-- <th>所属机房</th>
				<th>所属机柜</th> -->
				<th>IP地址</th>
				<th>CPU数量(个)</th>
				<th>内存(GB)</th>
				<th>磁盘空间(GB)</th>
				<th>分配状态</th>
				<th>运行状态</th>
				<th>更新时间</th>
				<th>操作</th>
			</tr>
			<s:iterator value="pmInfoList">
				<tr class="iterator">
					<td>
					    <s:property value="pmId" />
					</td>
					<td><s:property value="pmName" /></td>
					<!-- <td><s:property value="machinerRoomName" /></td>
					<td><s:property value="cabinetName" /></td> -->
                    <%--<td><s:property value="ip" /></td> --%>
					<td align="center"><div style="overflow:hidden; width:130px; height:30px;" title="<s:property value="ip" />"><s:property value="ip" /></div></td>
					<td align="center"><s:property value="cpuNum" /></td>
					<td align="center"><s:property value="memorySize" /></td>
					<td><s:property value="diskSize" /></td>
					
					<td><s:if test="pmState == \"0\"">未分配</s:if>
						<s:elseif test="pmState == \"1\"">已分配</s:elseif>
						<s:else>不可用</s:else></td>
					<td style="padding-left: 10px; text-align: left">
							<s:if test="curStatus == \"0\""><img style="margin-bottom: -3px" alt="不可用" src="../images/pm_avliable.png">不可用</s:if>
							<s:elseif test="curStatus == \"1\""><img style="margin-bottom: -3px" alt="已加电" src="../images/pm_powerup.png">已加电</s:elseif>
							<s:elseif test="curStatus == \"2\""><img style="margin-bottom: -3px" alt="运行" src="../images/pm_running.png">运行</s:elseif>
							<s:elseif test="curStatus == \"3\""><img style="margin-bottom: -3px" alt="关机" src="../images/pm_shutdown.png">关机</s:elseif>
							<s:elseif test="curStatus == \"4\""><img style="margin-bottom: -3px" alt="休眠" src="../images/pm_pause.png">休眠</s:elseif>
							<s:elseif test="curStatus == \"5\""><img style="margin-bottom: -3px" alt="处理中" src="../images/pm_processing.png">处理中</s:elseif>
					</td>
					
					<td align="center"><s:property value="updateTime" /></td>
					<td class="table-opt-block" >
						<a href="javascript:void(0);"
						onclick="pmInfoDetail('<s:property value="pmId" />');return false;">详情</a>
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
	url : 'resViewPmListAction.action'
    });
}
function pmInfoDetail(pmId) {
	var form = document.getElementById("search_form");
	var nodeType = document.getElementById('nodeType').value;
	var nodeId = document.getElementById('nodeId').value;
	var treeNodeName = document.getElementById('treeNodeName').value;
	var pnodeId = document.getElementById('pnodeId').value;
	var pnodeName = document.getElementById('pnodeName').value;
	var appId = document.getElementById('appId').value;
	var curFun = document.getElementById('curFun').value;
	
	form.action = "resViewPmInfoDetail.action?pmInfo.nodeType=" + nodeType + "&pmInfo.nodeId="
		+ nodeId + "&pmInfo.treeNodeName="
		+ treeNodeName + "&pmInfo.pnodeId="
		+ pnodeId + "&pmInfo.pnodeName="
		+ pnodeName + "&pmInfo.appId="
		+ appId + "&pmInfo.curFun="
		+ curFun + "&pmInfo.pmId=" + pmId;
	form.submit();
}
</script>

<script type="text/javascript" src="../scripts/pagesjs/resourceManagement/getCabinetList.js"></script>
	