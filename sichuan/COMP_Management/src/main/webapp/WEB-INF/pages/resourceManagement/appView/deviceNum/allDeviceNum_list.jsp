<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<head>
<script type="text/javascript">
$(function() {
    $("select").uniform();
	$.uniform.update();
});
</script>
</head>
<body>
    <h1>所有业务组统计</h1>
    <!-- 概况 -->
    <div class="details-con">
        <!-- 标题栏-->
		<div class="BussTitle">
			<a href="../resourceManagement_appView/cpuExceededAction.action?deviceOverInfo.deviceType=2&deviceOverInfo.performanceType=cpu"><div id="performanceDiv" class="BussTitle1">性能统计</div></a>
			<a href="../resourceManagement_appView/appViewAllDeviceNumAction.action"><div id="deviceNumDiv" class="BussTitlefocus2">设备数统计</div></a>
		</div>

        <div id="approval-allDeviceNum" class="table-content table-block" style="margin-top: 10px;">
	        <table id="allDeviceNumTable" width="100%" border="0" cellspacing="0" cellpadding="0">
	            <col style="width:25%;" />
	            <col style="width:15%;" />
	            <col style="width:15%;" />
	            <col style="width:15%;" />
	            <!--<col style="width:15%;" />
	            <col style="width:15%;" />-->
	            <tr>
	                <th class="nl">业务组名称</th>
	                <th>物理机（台）</th>
	                <th>虚拟机（台）</th>
	                <th>虚拟硬盘（块）</th>
	                <!-- <th>小型机（台）</th>
	                <th>小型机分区（台）</th> -->
	            </tr>
	            <s:iterator value="allDeviceNumList">
	     	        <tr class="iterator">
	                    <td title="<s:property value='app_name'/>">
	                        <s:property value="app_name"/>
	                    </td>
	                    <td><s:property value="pm_num"/></td>
	                    <td><s:property value="vm_num"/></td>
	                    <td class="table-opt-block"><s:property value="ebs_num"/></td>
	                    <!-- <td><s:property value="miniPm_num"/></td>
	                    <td class="table-opt-block"><s:property value="miniPmPar_num"/></td> -->
	     	        </tr>
	            </s:iterator>
	        </table>
	    </div>
		<div class="pageBar" style="text-align:center">
		   <s:property value="pageBar" escape="false" />
		</div>
	</div>
</body>



	    