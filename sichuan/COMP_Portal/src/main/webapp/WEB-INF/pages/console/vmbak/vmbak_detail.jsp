<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:hidden id="vmBakId" name="vmBakInfo.vmBakId" />

<!--右侧-->
<s:form id="vmBakDetailForm" name="vmBakDetailForm" action="vmBakQueryListAction.action" method="post">
    <s:hidden id="nodeType" name="nodeType"></s:hidden>
    <s:hidden id="nodeId" name="nodeId"></s:hidden>
    <s:hidden id="treeNodeName" name="treeNodeName"></s:hidden>
    <s:hidden id="pnodeId" name="pnodeId"></s:hidden>
    <s:hidden id="pnodeName" name="pnodeName"></s:hidden>
    <s:hidden id="appId" name="appId"></s:hidden>
    <s:hidden id="curFun" name="curFun"></s:hidden>
</s:form>
<div id="right">
    <h1>虚拟机备份控制台</h1>
    <div id="message" style="display: none;">
		<span id="error" class="error"><s:actionerror /></span>
		<span class="error"><s:fielderror /></span>
		<span class="success"><s:actionmessage /></span>
	</div>
       	
    <span class="region" ></span>
    <div class="details-con">
    	<div class="detail-title">
            <a href="#" onclick="goToList()" title="返回">
                <span class="back"></span>
            </a>
            <h3 class="apply-title">虚拟机备份任务详情</h3>
            <ul class="vh-btn-r">

            </ul>
        </div>
      	<div class="detail-info apply-info">
            <div class="apply-info">
                
            </div>
            <div id="bindDiv" class="apply-info">
            	<span class="apply-span-name" style="width: 150px;">执行备份的虚拟机：</span>
				<s:property value="vmBakInfo.vmName" />
			</div>

			<div class="apply-info">
                <span class="apply-span-name" style="width: 150px;">备份任务名称：</span>
                <s:property value="vmBakInfo.vmBakName" />
            </div>
            
            <div class="apply-info">
                <span class="apply-span-name" style="width: 150px;">备份周期：</span>
                <s:if test="vmBakInfo.backupCyc==1">每天</s:if>
		        <s:elseif test="vmBakInfo.backupCyc==7">每周</s:elseif>
            </div>
            
            <div class="apply-info">
                <span class="apply-span-name" style="width: 150px;">备份任务保留时间：</span>
                <s:property value="vmBakInfo.backStoreTime" />天
            </div>
            
            <div class="apply-info">
                <span class="apply-span-name" style="width: 150px;">预计开始时间：</span>
                <s:property value="vmBakInfo.backupStartTime" />
            </div>
            
            <div class="apply-info">
                <span class="apply-span-name" style="width: 150px;">状态：</span>
                <!--<s:property value="vmBakInfo.bakupStatus.desc" />-->
                <span id="bakupStatus">
                <s:if test="vmBakInfo.status.code==11">待创建</s:if>
			    <s:else>加载中</s:else>
			    </span>
            </div>
            
            <s:if test="vmBakInfo.restoreVmBakInternalId != null && vmBakInfo.restoreVmBakInternalId != ''">
            <div class="apply-info">
                <span class="apply-span-name" style="width: 150px;">恢复信息：</span>
                <span id="restoreStatus">加载中</span>
            </div>
            </s:if>
            
            <div class="vm-apply-detail-l">
                <span class="apply-span-name" style="width: 150px;">备注：</span>
                <s:property value="vmBakInfo.description" />
            </div>
		</div>

		<s:if test="vmBakInfo.vmBakId != null && vmBakInfo.vmBakId != ''">
		<hr class="apply-line" />
		<s:form id="searchForm">
        <div class="table-seach">
		    <p>
		        <label class="fl">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;备份时间：</label>
		        <s:textfield id="startTime" name="startTime" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" cssClass="Wdate wdatelong text" size="23" readonly="true"/>
			    至
				<s:textfield id="endTime" name="endTime" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" cssClass="Wdate wdatelong text" size="23" readonly="true"/>
		    </p>
		    <ul class="opt-btn fr">
	            <li><a id="search" class="search" href="#" onclick="searchVmBak();">查询</a></li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	        </ul>
		</div>
        </s:form>
        
		<div id="backup_tab_info" class="table-content table-block" style="height:380px; overflow:hidden">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<col style="width: 35%;" />
				<col style="width: 25%;" />
				<col style="width: 25%;" />
				<col style="width: 15%;" />
				<tr>
					<th class="nl">备份ID</th>
					<th>容量（MB）</th>
					<th>创建时间</th>
					<th>操作</th>
				</tr>
				<tbody id="listTbody">
				</tbody>
			</table>
			<div id="pageBarDiv" class="pageBar" style="float:left; margin-left:200px;"></div>
		</div>
		</s:if>

    </div>
</div>

<script type="text/javascript" src="../scripts/pagesjs/console/vmbak/vmbak_detail.js"></script>