<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<div id="message" style="display: none;">
	<span id="error" class="error"><s:actionerror /></span>
	<span class="error"><s:fielderror /></span>
	<span class="success"><s:actionmessage /></span>
</div>

<s:form id="vmBakForm">
<s:hidden id="nodeType" name="nodeType"></s:hidden>
<s:hidden id="nodeId" name="nodeId"></s:hidden>
<s:hidden id="treeNodeName" name="treeNodeName"></s:hidden>
<s:hidden id="pnodeId" name="pnodeId"></s:hidden>
<s:hidden id="pnodeName" name="pnodeName"></s:hidden>
<s:hidden id="appId" name="appId"></s:hidden>
<s:hidden id="curFun" name="curFun"></s:hidden>
        
<input type="hidden" id="caseId" name="caseId">

   
<div id="right">
   	<h1>虚拟机备份控制台</h1>
    <span class="apply"><a href="#" onclick="vmBakCreate()">申请备份任务</a></span>

    <div class="details-con">
    	<div class="detail-title">
            <h3 class="apply-title">虚拟机备份任务列表</h3>
        </div>
        
        <div class="table-seach">
		    <p>
		        <label class="fl" style="width:80px">虚拟机名称：</label>
		        <s:textfield id="vmName" name="vmName" cssClass="text" size="20"/>
		    </p>
		    <!-- 
		    <p>
		        <label class="fl" style="width:50px">状态：</label>
		        <s:select id="status" name="status" class="select-min"
			    list="statusMap" listKey="key" listValue="value" 
			    headerKey="" headerValue="-- 全部 --"/>
		    </p>
		     -->
		    <p>
		        <label class="fl" style="width:60px">创建时间：</label>
		        <s:textfield id="startTime" name="startTime" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" cssClass="Wdate wdatelong" size="23" readonly="true"/>
			    至
				<s:textfield id="endTime" name="endTime" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" cssClass="Wdate wdatelong" size="23" readonly="true"/>
		    </p>
		    <ul class="opt-btn fr">
	            <li><a id="search" class="search" href="#" onclick="searchVmBakList();">查询</a></li>
	        </ul>
		</div>

        <!--虚拟机备份任务列表开始-->
        <div class="table-content table-block">
            <table id="backupList" width="100%" border="0" cellspacing="0" cellpadding="0">
                <col style="width:30%;" />
                <col style="width:30%;" />
                <col style="width:10%;" />
                <col style="width:20%;" />
                <col style="width:10%;" />
                <tr class="cs-ak-tb-title">
                    <th class="nl">备份任务名称</th>
                    <th>虚拟机名称</th>
                    <th>状态</th>
                    <th>创建时间</th>
                    <th>操作</th>
                </tr>
                <s:if test="vmBakResultInfos.size()>0" >
		        <s:iterator value="#request.vmBakResultInfos">
		        <tr id='<s:property value="vmBakId"/>' caseId='<s:property value="caseId"/>' status='<s:property value="status.code"/>'>
		            <td><s:property value="vmBakName"/></td>
		            <td><s:property value="vmName"/></td>
		            <td id="status<s:property value="vmBakId"/>">
			            <s:if test="status.code==11">待创建</s:if>
			            <s:else>加载中</s:else>
		            </td>
		            <td><s:property value="createTime"/></td>
		            <td id="opt<s:property value="vmBakId"/>" class="table-opt-block" >
		              	<a href="#" onclick="vmBakDetail('<s:property value="caseId"/>')">详情</a>
		            </td>
		        </tr>
		   		</s:iterator>
                </s:if>
                <s:else>
     	        <tr>
                   <td colspan="6">暂无符合条件数据</td>
                </tr>
                </s:else>
            </table>
        </div>
        <!--虚拟机备份任务列表结束-->
        
	    <!-- 翻页 -->
		<div class="pageBar">
		    <s:property value="pageBar" escape="false" />
        </div>
	</div>
</div>
</s:form>

<script type="text/javascript" src="../scripts/pagesjs/console/vmbak/vmbak_list.js"></script>