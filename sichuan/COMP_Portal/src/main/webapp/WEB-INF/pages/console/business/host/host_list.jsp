<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="list">
	<div class="title">
		<h2>云主机列表</h2>
		<div id="message" style="display: none;">
			<span id="error" class="error"><s:actionerror /></span> <span
				class="error"><s:fielderror /></span> <span class="success"><s:actionmessage /></span>
		</div>
		<s:form id="gotoForm">
			<input type="hidden" value="" id="vmId" name="vmId">
			<s:hidden id="nodeType" name="nodeType"></s:hidden>
			<s:hidden id="nodeId" name="nodeId"></s:hidden>
			<s:hidden id="treeNodeName" name="treeNodeName"></s:hidden>
			<s:hidden id="pnodeId" name="pnodeId"></s:hidden>
			<s:hidden id="pnodeName" name="pnodeName"></s:hidden>
			<s:hidden id="appId" name="appId"></s:hidden>
			<s:hidden id="curFun" name="curFun"></s:hidden>
			<s:hidden id="businessName" name="businessName"></s:hidden>
		</s:form>
		<input type="hidden" value="<s:property value="type" />" id="type"
			name="type">
		<s:url id="addUrl" action="vmApply">
			<s:param name="nodeType" value="nodeType" />
			<s:param name="nodeId" value="nodeId" />
			<s:param name="treeNodeName" value="treeNodeName" />
			<s:param name="pnodeId" value="pnodeId" />
			<s:param name="pnodeName" value="pnodeName" />
			<s:param name="appId" value="appId" />
			<s:param name="curFun" value="curFun" />
			<s:param name="businessName" value="businessName" />
			<s:param name="queryBusinessId" value="queryBusinessId" />
		</s:url>
	</div>
	<s:if test="#session.userInfo.userId=='admin'">
		<div class="btns">
			<a class="apply" href="${addUrl}"><img
				src="../img/ico-apply.png" alt="" />申请云主机</a> 
		</div>
	</s:if>
	<div class="tool-bar">
		<div class="search-text">
			<img class="search-ico" src="../img/ico-search.png" /> <input
				class="search-text w-auto" type="text" size="23" id="privateIp"
				name="privateIp" maxlength="15"
				value="<s:property value="privateIp" />" style="width: auto;"
				placeholder="输入IP地址" />
		</div>
		<div class="search-text">
			<img class="search-ico" src="../img/ico-search.png" /> <input
				class="search-text" type="text" size="25" id="isoName"
				name="isoName" maxlength="50" value="<s:property value="isoName" />"
				style="width: auto;" placeholder="输入操作系统" />
		</div>
		<div class="search-text">
			<img class="search-ico" src="../img/ico-search.png" /> <input
				class="search-text" type="text" size="25" id="vmName" name="vmName"
				maxlength="25" value="<s:property value="vmName" />"
				style="width: auto;" placeholder="输入名称" />
		</div>
		<a class="search-btn" href="javascript:;" onclick="queryVm('','<s:property value="queryBusinessId" />')">查询</a>
		<!-- <a
			id="bind_vm" class="batch-btn" href="javascript:void(0);"><img
			src="../img/batch-ico.png" alt="" />批量修改</a> -->
	</div>
	<div class="list-items">
		<s:if test="vmResultInfos.size()>0">
			<ul id="resultList" class="items">
				<s:iterator value="#request.vmResultInfos">

					<li status="<s:property value='status'/>"
						id="s<s:property value='vmId'/>" name="<s:property value='vmId'/>"><a
						href="javascript:void(0);"
						onclick="goToVMPage('<s:property value='caseId'/>','<s:property value='queryBusinessId'/>');return false;">
							<input type="hidden" id="iso<s:property value='vmId'/>"
							value="<s:property value='isoName'/>" />
							<div id="status<s:property value="vmId"/>"
								class="item-status blue">
								<s:if test="vmId == null || vmId == ''">待创建</s:if>
								<s:else>加载中</s:else>
							</div>
							<div class="item-content">
								<h3>
									<s:if test="vmName.trim()!=''">
										<s:property value="vmName" />
									</s:if>
									<s:else>&nbsp;</s:else>
								</h3>
								<div class="detail">
									<div class="detail-col">
										<s:property value="isoName" />
									</div>
									<div class="detail-col">
										<div class="detail-row">
											<span>内网IP：</span> <span><s:property
													value="privateIpStr" /></span>
										</div>
										<div class="detail-row">
											<span>所属业务：</span> <span><s:property value="appName" /></span>
										</div>
									</div>
									<div class="detail-col">
										<div class="detail-row">
											<span>创建时间：</span> <span
												id="effectiveTime<s:property value='vmId'/>"><s:property
													value="effectiveTime" /></span>
										</div>
										<div class="detail-row">
											<span>到期时间：</span> <span
												id="overTime<s:property value='vmId'/>"><s:property
													value="overTime" /></span>
										</div>
									</div>

								</div>
							</div>
					</a></li>


				</s:iterator>
			</ul>
		</s:if>
		<!-- 翻页 -->
		<div class="pageBar">
			<s:property value="pageBar" escape="false" />
		</div>
	</div>
</div>


<%-- 

<ul id="listNull" class="resource-list">
	<li><a href="javascript:void(0);"> 暂无符合条件数据 </a></li>
</ul> --%>



<div id="vm_list" style="display: none; width: 1000px;">
	<div class="toolbar">
		<span class="keyName">虚拟机名称：</span><input name="appName" type="text"
			size="30" id="searchAppName" maxlength="30" value="" /> <a
			class="search btn fr" href="javascript:void(0);" id="searchApp">查询</a>
	</div>


	<table class="table" id="businessListTab">
		<thead>
			<tr>
				<th><input type="checkbox" name="selectAllApp"
					id="selectAllApp" /></th>
				<th>云主机名称</th>
				<th>创建时间</th>
			</tr>
		</thead>
		<tbody id="appListTbody">
		</tbody>
	</table>
	<div class="pageBar" id="appListPageBarDiv"></div>

</div>



	<%-- 	 <div style="margin-left: 15%; margin-top: 7px;">
		<ul class="result-list" style="width: 100%; list-style: none;">
			<li><span class="result-name" style="float: left;">云主机：</span>
				<div class="content">
					<div class="block_edit"
						style="margin-left: 70px; min-height: 40px; width: 620px; padding: 2px 0 2px 5px; border-bottom: none; overflow: auto; overflow-x: hidden;">
						<ul class="edit_app" id="add_user_add_app_ul">
							<!-- 	<li class="add_app" id="add_user_add_app_button">绑定业务</li> -->
							<li class="add_app" id="add_user_add_app_button"
								style="border: 0; margin: 0; padding: 0;"></li>
						</ul>
					</div>
				</div></li>
		</ul>
	</div>  --%>
</div>

<s:if test="vmResultInfos.size()<=0">
	<script type="text/javascript">
		$("#listNull li").show();
	</script>
</s:if>
<script type="text/javascript"
	src="../scripts/pagesjs/console/business/host/host_list.js"></script>
<script type="text/javascript" language="JavaScript" charset="UTF-8">
      document.onkeydown=function(event){
            var e = event || window.event || arguments.callee.caller.arguments[0];
            
             if(e && e.keyCode==13){ // enter 键
            	 queryPm();
            }
        }; 
</script>