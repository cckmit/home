<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!--右侧-->
<div id="right">
    <h1>虚拟机备份控制台</h1>
    <div id="message" style="display: none;">
		<span id="error" class="error"><s:actionerror /></span>
		<span class="error"><s:fielderror /></span>
		<span class="success"><s:actionmessage /></span>
	</div>
       	
    <s:form id="vmBakApplyForm" name="vmBakApplyForm" method="post">
        <s:hidden id="nodeType" name="nodeType"></s:hidden>
        <s:hidden id="nodeId" name="nodeId"></s:hidden>
        <s:hidden id="treeNodeName" name="treeNodeName"></s:hidden>
        <s:hidden id="pnodeId" name="pnodeId"></s:hidden>
        <s:hidden id="pnodeName" name="pnodeName"></s:hidden>
        <s:hidden id="appId" name="appId"></s:hidden>
        <s:hidden id="curFun" name="curFun"></s:hidden>
        
        <span class="region" ></span>
        <div class="details-con">
        	<div class="detail-title">
                <a href="#" onclick="goToList()" title="返回">
                    <span class="back"></span>
                </a>
                <h3 class="apply-title">申请备份任务</h3>
                <ul class="vh-btn-r">

                </ul>
            </div>
          	<div class="detail-info apply-info">
                <div class="apply-info">
                    
                </div>
                <div id="bindDiv" class="apply-info">
                	<span class="apply-span-name" style="width: 140px;">执行备份的虚拟机：</span>
                	<input type="hidden" id="vmId" name="vmId" value="<s:property value="vmId"/>" />
                	<s:if test="vmId != null && vmId != ''">
                		<s:property value="vmName"/>
                	</s:if>
                	<s:else>
                		<a href="javascript:bindVm();">绑定虚拟机</a>
                	</s:else>
				</div>

				<div class="apply-info">
                    <span class="apply-span-name" style="width: 140px;">备份任务名称：</span>
                    <input type="text" class="text" size="32" id="vmBakName" name="vmBakName" value="" maxlength="50"/>
                </div>
                
                <div class="apply-info">
                    <span class="apply-span-name" style="width: 140px;">备份周期：</span>
                    <input type="radio" class="radio" id="backupCycDay" name="backupCyc" value="1"/><label for="backupCycDay">每天</label>&nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="radio" class="radio" id="backupCycWeek" name="backupCyc" value="7" checked/><label for="backupCycWeek">每周</label>
                </div>
                
                <div class="apply-info">
                    <span class="apply-span-name" style="width: 140px;">备份保留时间：</span>
                    <input type="text" class="text" size="32" id="backStoreTime" name="backStoreTime" value="" maxlength="10"/>天
                </div>
                
                <div class="apply-info">
                    <span class="apply-span-name" style="width: 140px;">预计开始时间：</span>
                    <input type="text" size="32" id="backupStartTime" name="backupStartTime" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:00:00'});" readonly="true" class="Wdate wdatelong" />
                </div>

                <div class="vm-apply-detail-l">
                    <span class="apply-span-name" style="width: 140px;">备注：</span>
                    <input  type="text" class="text" size="50"  id="description" name="description" value=""  maxlength="200"/>
                </div>
                <div class="apply-btn-commit">
                    <span>
                        <a href="#" onclick="creatVmBak()">提交</a>
                    </span>
                </div>
			</div>
			<div class="detail-time"></div>
        </div>
    </s:form>
</div>

<div id="bindVm" class="float-div" style="display: none;width:700px;">
	<div class="float-toolsbar">
		<span class="keyName">虚拟机名称：</span> <input type="text" size="30" id="queryVmName"/>
		<ul class="opt-btn fr">
			<li><a class="search" href="javascript:loadVmList('vmBakSearchVMListJsonAction.action')" >查询</a></li>
		</ul>
	</div>
	<div class="float-div-center">
		<div class="table-content">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" id="vmListTab">
				<thead>
					<tr>
						<col style="width: 10%;" />
						<col style="width: 40%;" />
						<col style="width: 50%;" />
					</tr>
					<tr>
						<th class="nl"></th>
						<th>云主机ID</th>
						<th>云主机名称</th>
					</tr>
				</thead>
				<tbody id="vmListTbody"></tbody>
			</table>
		</div>
	</div>
	<div class="pageBar" id="vmListPageBarDiv" style="text-align:center"></div>
</div>

<script type="text/javascript" src="../scripts/pagesjs/console/vmbak/vmbak_apply.js"></script>