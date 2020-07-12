<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<h1>存储阵列</h1>
<div id="message" style="display: none;">
	<span id="error" class="error"><s:actionerror /></span>
	<span class="error"><s:fielderror /></span>
	<span class="success"><s:actionmessage /></span>
</div>
<div class="details-con">
	<div class="detail-title">
		<h3 class="apply-title">存储阵列列表</h3>
	</div>
        
	<form action="raidListAction.action" id="search_form" name="search_form"
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
		    <label class="fl">存储阵列ID：</label>
			<input type="text" class="text" size="25" id="queryRaidId" name="queryRaidInfo.raidId" maxlength="64"
				value="<s:property value="queryRaidInfo.raidId" />" />
		    </p>
			<p>
			<label class="fl">存储阵列名称：</label>
			<input type="text" class="text" size="25" id="queryRaidName" name="queryRaidInfo.raidName" maxlength="64"
				value="<s:property value="queryRaidInfo.raidName" />" />
		    </p>
			<p>
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
			<col style="width: 11%;" />
			<col style="width: 7%;" />
			<col style="width: 7%;" />
			<col style="width: 7%;" />
			<col style="width: 8%;" />
			<col style="width: 6%;" />
			<!-- <col style="width: 7%;" />
			<col style="width: 6%;" /> -->
			<col style="width: 11%;" />
			<col style="width: 6%;" />

			<tr>
				<th>存储阵列ID</th>
				<th>存储阵列名称</th>
				<th>类型</th>
				<th>IP地址</th>
				<th>配置容量(GB)</th>
				<th>CACHE容量(MB)</th>
				<th>设备厂商</th>
				<!-- <th>资产状态</th> 
				<th>资产SLA类型</th>-->
				<th>更新时间</th>
				<th>操作</th>
			</tr>
			<s:iterator value="raidInfoList">
				<tr class="iterator">
					<td>
					    <s:property value="raidId" />
					</td>
					<td><s:property value="raidName" /></td>
					<td><s:property value="saType" /></td>
               <%-- <td><s:property value="saIp" /></td> --%>
                    <td align="center"><div style="overflow:hidden; width:117px; height:30px;" title="<s:property value="saIp" />"><s:property value="saIp" /></div></td>
					<td><s:property value="saCapacity" /></td>
					<td><s:property value="saCapacity" /></td>
					<td><s:property value="vendorName" /></td>
					<!-- <td style="padding-left: 10px; text-align: left">
							<s:if test="assetState == \"1\""><img style="margin-bottom: -3px" alt="已使用" src="../images/pm_avliable.png">已使用</s:if>
							<s:elseif test="assetState == \"2\""><img style="margin-bottom: -3px" alt="未使用" src="../images/pm_powerup.png">未使用</s:elseif>
							<s:elseif test="assetState == \"3\""><img style="margin-bottom: -3px" alt="不可用" src="../images/pm_running.png">不可用</s:elseif>
							<s:elseif test="assetState == \"4\""><img style="margin-bottom: -3px" alt="丢失" src="../images/pm_processing.png">丢失</s:elseif>
							<s:elseif test="assetState == \"5\""><img style="margin-bottom: -3px" alt="待确认" src="../images/pm_running.png">待确认</s:elseif>
							<s:elseif test="assetState == \"6\""><img style="margin-bottom: -3px" alt="已删除" src="../images/pm_processing.png">已删除</s:elseif>
					</td>
					<td style="padding-left: 10px; text-align: left">
							<s:if test="assetSlaType == \"1\"">提供服务</s:if>
							<s:elseif test="assetSlaType == \"2\"">平台自服务</s:elseif>
					</td> -->
					<td align="center"><s:property value="updateTime" /></td>
					<td class="table-opt-block" >
						<a href="javascript:void(0);"
						onclick="raidInfoDetail('<s:property value="raidId" />');return false;">详情</a>
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
	url : 'raidListAction.action'
    });
}
function raidInfoDetail(raidId) {
	var form = document.getElementById("search_form");
	var nodeType = document.getElementById('nodeType').value;
	var nodeId = document.getElementById('nodeId').value;
	var treeNodeName = document.getElementById('treeNodeName').value;
	var pnodeId = document.getElementById('pnodeId').value;
	var pnodeName = document.getElementById('pnodeName').value;
	var curFun = document.getElementById('curFun').value;
	
	form.action = "raidInfoDetail.action?raidInfo.nodeType=" + nodeType + "&raidInfo.nodeId="
		+ nodeId + "&raidInfo.treeNodeName="
		+ treeNodeName + "&raidInfo.pnodeId="
		+ pnodeId + "&raidInfo.pnodeName="
		+ pnodeName + "&raidInfo.curFun="
		+ curFun + "&raidInfo.raidId=" + raidId;
	form.submit();
}

</script>
	