<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<h1>资源规格管理</h1>
<div id="message" style="display: none;">
	<span id="error" class="error"><s:actionerror /></span> <span
		class="error"><s:fielderror /></span> <span class="success"><s:actionmessage /></span>
</div>
<span class="apply"><a href="#" onclick="onVmAdd();">创建虚拟机规格</a></span>
<div class="details-con">
	<div class="detail-title">
		<h3 class="apply-title">虚拟机规格管理</h3>
	</div>
	<form name="vmForm" id="vmForm" action="VMStandardListQuery.action"
		method="post">
		<div class="table-seach">
			<p>
				规格名称：<input id="standardName" name="standardName" class="text"
					type="text" size="28" value="<s:property value="standardName" />" />
			</p>
			<p>
				创建时间：<input id="startTime" name="startTime" type="text" size="23"
					onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"
					readonly="true" class="Wdate wdatelong"
					value="<s:property value="startTime" />" />至<input id="endTime"
					name="endTime"
					onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"
					readonly="true" class="Wdate wdatelong"
					value="<s:property value="endTime" />" type="text" size="23" />
			</p>
			<ul class="opt-btn fr">
				<li><a class="search" href="#" onclick="query()">查询</a></li>
			</ul>
		</div>
	</form>
	<!-- 列表 -->
	<!-- 虚拟机 -->
	<div id="approval-vm" class="table-content table-block">
		<table id="standardTable" width="100%" border="0" cellspacing="0"
			cellpadding="0">
			<col style="width: 15%;" />
			<col style="width: 24%;" />
			<col style="width: 15%;" />
			<col style="width: 18%;" />
			<col style="width: 10%; display: none;" />
			<col style="width: 28%;" />
			<tr>
				<th class="nl">规格名称</th>
				<th>规格ID</th>
				<th>创建人</th>
				<th>创建时间</th>
				<th style="display: none;">状态</th>
				<th>操作</th>
			</tr>
			<s:if test="vmStandardInfos==null or vmStandardInfos.size()==0">
              	<tr>
              		<td colspan="6" align="center">
              			暂无符合数据！
              		</td>
              	</tr>
            </s:if>
            <s:else>
				<s:iterator value="vmStandardInfos">
					<tr class="iterator">
						<td><s:property value="standardName" /></td>
						<td id="standardId"><s:property value="standardId" /></td>
						<td><s:property value="createUser" /></td>
						<td><s:property value="createTime" /></td>
						<td style="display: none;">部分同步成功</td>
						<td class="table-opt-block"><a href="#"
							onclick="onVmDetail('<s:property value="standardId"/>');">详情</a>
							<a href="javascript:void(0);"
							onclick="onSynchro(this,'<s:property value="standardId"/>');return false;">同步</a>
							<a href="#" onclick="onDel('<s:property value="standardId"/>');">删除</a>
						</td>
					</tr>
				</s:iterator>
			</s:else>
		</table>
	</div>
	<!-- 翻页 -->
	<div class="pageBar">
		<s:property value="pageBar" escape="false" />
	</div>
</div>
<!-- 虚拟机弹出层 -->
<div class="page-form" id="vm_div" style="display: none;">
	<div class="section">
		<div id="vm_div_id" class="field">
			<div class="caption">虚拟机规格ID：</div>
			<div class="content" id="vm_standard_id">CIDC-T-SRV-00000001</div>
			<div class="point"></div>
		</div>
		<div class="field">
			<div class="caption">虚拟机规格名称：</div>
			<div class="content">
				<input type="text" size="28" id="vm_standard_name" value="经济A型虚拟机模板" />
			</div>
			<div class="point"></div>
		</div>
		<div class="field">
			<div class="caption">CPU单元数量(个)：</div>
			<div class="content">
				<input type="text" size="28" id="vm_cpuNum" value="2" />
			</div>
			<div class="point"></div>
		</div>
		<div class="field">
			<div class="caption">内存容量(MB)：</div>
			<div class="content">
				<select class="select-max" id="vm_ram">
					<option selected value="">--请选择--</option>
					<option value="1024">1024</option>
					<option value="2048">2048</option>
					<option value="4096">4096</option>
					<option value="8192">8192</option>
					<option value="12288">12288</option>
					<option value="16384">16384</option>
					<option value="32768">32768</option>
					<option value="49152">49152</option>
					<option value="65536">65536</option>
					<option value="131072">131072</option>
					<option value="262144">262144</option>
					<option value="393216">393216</option>
				</select>
			</div>
			<div class="point"></div>
		</div>
		<div class="field">
			<div class="caption">磁盘空间(GB)：</div>
			<div class="content">
				<input type="text" size="28" id="vm_size" value="40" />
			</div>
			<div class="point"></div>
		</div>
		<div class="field">
			<div class="caption">虚拟机规格描述：</div>
			<div class="content">
				<textarea cols="42" id="vm_remark"></textarea>
			</div>
			<div class="point"></div>
		</div>
	</div>
</div>
<!-- -->
<!-- 同步资源 -->
<div class="page-form" id="synchro_div" style="display: none;">
	<div class="section">
		<div id="vm_div_id" class="field">
			<div class="caption">规格ID：</div>
			<div class="content" id="syn_standardId">CIDC-T-SRV-00000001</div>
			<div class="point"></div>
		</div>
		<div class="field">
			<div class="caption">规格名称：</div>
			<div class="content" id="syn_standardName">经济A型虚拟机模板</div>
			<div class="point"></div>
		</div>
	</div>
	<div class="section">
		<div class="field">
			<div class="caption">资源池：</div>
			<div class="content" id="synchro_table"></div>
		</div>
	</div>

</div>
<script type="text/javascript"
	src="../scripts/pagesjs/standard/vm/virtual_list.js"></script>