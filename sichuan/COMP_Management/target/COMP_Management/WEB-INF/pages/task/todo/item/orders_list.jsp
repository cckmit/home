<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
	<div id="message" style="display: none;">
		<span class="error"><s:actionerror /></span>
		<span class="error"><s:fielderror /></span>
		<span class="success"><s:actionmessage /></span>
	</div>
       	<h1>订单审批</h1>
   	  <div class="details-con">
   	    <div class="detail-title">
          <h3 class="apply-title">订单审批</h3>
   	    </div>
        <div class="table-seach">
		<p><label class="fl">资源类型：</label> <select id="select"
			class="select-max" onchange="queryOrder()">
			<option selected="selected" value="select">-请选择-</option>
			<option value="0">虚拟机</option>
			<option value="1">物理机</option>
			<%-- <option value="4">虚拟机备份任务</option>--%>
			<option value="5">云硬盘</option>
			<%-- <option value="10">虚拟机快照</option>
			<option value="11">虚拟机克隆</option>--%>
			<option value="12">IP段</option>
			<option value="13">VLAN</option>
			<option value="14">负载均衡</option>
			<option value="15">分布式文件存储</option>
			<option value="16">虚拟防火墙</option>
			<option value="17">VLAN 3期SDN</option>
		</select></p>
		<p><label class="fl">资源池名称：</label> <select id="pool"
			class="select-max" onchange="queryOrder()">
			<option selected="selected" value="select">-请选择-</option>
			<s:iterator value="pools">
				<option value="<s:property value="resPoolId" />"><s:property value="resPoolName" /></option>
			</s:iterator>
		</select></p>
		<p> 业务组名称：<input id="appName" type="text" /></p>
		<ul class="opt-btn fr">
			 <li>
		         <a class="search" href="#" onclick="queryOrder()">查询</a>
		    </li>
		    <li><a class="search" onclick="queryDiv();" href="javascript:void(0);">高级查询</a></li>
		    <li>
		    	<a class="batch" href="javascript:void(0);" onclick="approvalMore(this);return false;">批量审批</a>
		    </li>
		</ul>
		</div>
       	<!-- 列表 -->
		<div id="approval-will" class="table-content table-block">
		<table id="ordertable" width="100%" border="0" cellspacing="0" cellpadding="0">
			<col style="width: 2%;" />
			<col style="width: 20%;" />
			<col style="width: 2%;" />
			<col style="width: 19%;" />
			<col style="width: 8%;" />
			<col style="width: 5%;" />
			<col style="width: 8%;" />
			<col style="width: 8%;" />
			<col style="width: 7%;" />
			<col style="width: 11%;" />
			<col style="width: 5%;" />
			<tr>
				<th class="nl"><input type="checkbox" name="selectSubAll" id="selectSubAll"/></th>
				<th>订单号</th>
				<th></th>
				<th>子订单号</th>
				<th>资源类型</th>
				<!--<th>总价</th>-->
				<th>状态</th>
				<th>资源池名称</th>
				<th>业务组名称</th>
				<th>变更人</th>
				<th>变更时间</th>
				<th>操作</th>
			</tr>
			<s:iterator value="orderInfoList">
				<tr class="iterator">
					<td><input type="checkbox" name="selectSub"  value="<s:property value='parentId'/>"/></td>
					<td><s:property value="parentId" /></td>
					<td><s:if test="status==0 || status==7 || status==8 "><input type="checkbox" name="select" value="<s:property value='orderId'/>" vtype="<s:property value='parentId'/>"/></s:if></td>
					<td><s:property value="orderId" /></td>
					<td><s:if test="caseType==0">虚拟机</s:if> <s:elseif
						test="caseType==1">物理机</s:elseif> <s:elseif
						test="caseType==2">小型机</s:elseif> <s:elseif
						test="caseType==3">小型机分区</s:elseif> <s:elseif
						test="caseType==4">虚拟机备份任务</s:elseif> <s:elseif
						test="caseType==5">云硬盘</s:elseif> <s:elseif
						test="caseType==6">云储存</s:elseif> <s:elseif
						test="caseType==7">公网IP</s:elseif> <s:elseif
						test="caseType==8">带宽</s:elseif> <s:elseif
						test="caseType==9">安全组</s:elseif> <s:elseif
						test="caseType==10">虚拟机快照</s:elseif><s:elseif
						test="caseType==11">虚拟机克隆</s:elseif><s:elseif
						test="caseType==12">IP段</s:elseif><s:elseif
						test="caseType==13">VLAN</s:elseif><s:elseif
						test="caseType==14">负载均衡</s:elseif><s:elseif
						test="caseType==15">分布式文件存储</s:elseif><s:elseif 
						test="caseType==16">虚拟防火墙</s:elseif><s:elseif
						test="caseType==17">VLAN 3期SDN</s:elseif></td>
					<!--<td><s:property value="allPrice" /></td>-->
					<td><s:if test="status==0">待审批</s:if> <s:elseif test="status==2">未通过</s:elseif>
					<s:elseif test="status==4">已取消</s:elseif> <s:elseif test="status==5">已过期</s:elseif>
					<s:elseif test="status==6">已回收</s:elseif><s:elseif test="status==7">修改待审</s:elseif>
					<s:elseif test="status==8">删除待审</s:elseif><s:else>已通过</s:else></td>
					<td class="cut pool"><s:property value="resPoolName" /></td>
					<td class="cut app"><s:property value="appName" /></td>
					<td><s:property value="updateUser" /></td>
					<td><s:property value="updateTime" /></td>
					<td class="table-opt-block">
						<s:if test="status==0 || status==7 || status==8"><a href="#" onclick="orderAudit2('<s:property value="caseType" />','<s:property value="orderId" />','<s:property value="status" />')">审批</a>
						</s:if>
					</td>
				</tr>
			</s:iterator>
		</table>
		</div>
         <!-- 翻页 -->
         <div class="pageBar">
            <s:property value="pageBar" escape="false" />
         </div>
	</div>
      <!-- 批量审批弹出层 -->
	<div class="page-form" id="approvalMore" style="display:none;">
	    <div class="field">
	        <div class="caption">审批意见：</div>
	        <div class="content">
	        	<textarea cols="40" rows="5" id="auditInfo"></textarea>
	      </div>
	        <div class="point"></div>
	    </div>
        <div class="point"></div>
    </div>
    <s:form method="post" id="orderAduitDetail" action="orderDetail.action">
    	<input type="hidden" id="caseTypeTodo" value="" name="caseType" />
    	<input type="hidden" id="orderIdTodo" value="" name="orderId" />
    </s:form>

<!-- 高级查询 -->
<div class="page-form" id="queryDiv" style="display: none;width:450px;">
	<div class="caption" style="width: auto;padding-left: 10px;">&nbsp;&nbsp;&nbsp;变更人：</div>
	<div class="content"><input type="text" size="46" id="updateUser" value="" /></div>
	<br/><br/>
	<div class="caption" style="width: auto;padding-left: 10px;"> 变更时间：</div>
	<div class="content"><input id="startTime" type="text" size="23" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" readonly="true" class="Wdate wdatelong" /></div>
	<div class="caption" style="width: auto;padding-left: 10px;">至</div>
	<div class="content"><input id="endTime" type="text" size="23" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="true" class="Wdate wdatelong" /></div>
	<br/><br/>
	<div class="caption" style="width: auto;padding-left: 10px;">&nbsp;&nbsp;&nbsp;订单号：</div>
	<div class="content"><input type="text" size="46" id="queryParentId" value="" /></div>
	<br/><br/>
	<div class="caption" style="width: auto;padding-left: 10px;">子订单号：</div>
	<div class="content"><input type="text" size="46" id="queryOrderId" value="" /></div>
</div>
<script type="text/javascript"
	src="../scripts/pagesjs/task/orders_detail.js"></script>

