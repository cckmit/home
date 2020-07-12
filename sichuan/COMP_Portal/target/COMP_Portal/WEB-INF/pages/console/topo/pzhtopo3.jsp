<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<link
	href="../scripts/lib/vis/vis.min.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="../scripts/lib/vis/vis.min.js"></script>
<div class="list">
	<%-- <div class="title">
		<h2>拓扑图</h2>
		<s:hidden id="userId" name="userId"></s:hidden>
	</div> --%>
	<div class="tool-bar" style="position: absolute;">
		<div id="respoolshow" class="search-text">
			<select id="respool" name="respoolId"></select>
			</div>
		<div id="adminshow"class="search-text" style="top: 50px;">
			<select id="app" onchange="topomain()">
				<%-- <option value="notSelect">--业务名称--</option>
				<s:iterator value="app">
					<option value="<s:property value="appid" />"><s:property
							value="appname" /></option>
				</s:iterator> --%>
			</select>
		</div>
	</div>
	<div id="mynetwork" class="one"style="height: 750px; width: 65%;"></div>
</div>

<div class="page-form" id="detail" style="display: none;">
		<div>
			<div class="field">
				<div class="caption"style= "width : 200px; font-weight: bold;">云主机名称：</div>
				<div class="content"style= "width : 150px;">
					<input type="text" size="40" id="vmname" value=""
						style="border: 1px;" readonly="readonly" />
				</div>
				
				<div class="caption"style= "width : 200px; font-weight: bold;">当前状态：</div>
				<div class="content"style= "width : 150px;">
					<input type="text" size="40" id="statustext" value=""
						style="border: 1px;" readonly="readonly" />
				</div>
			</div>
			<div class="field">
				<div class="caption"style= "width : 200px; font-weight: bold;">空闲CPU百分比：</div>
				<div class="content"style= "width : 150px;">
					<input type="text" size="40" id="cpuidle" value=""
						style="border: 1px;" readonly="readonly" />
				</div>
				<div class="caption"style= "width : 200px; font-weight: bold;">CPU速度：</div>
				<div class="content"style= "width : 150px;">
					<input type="text" size="40" id="cpuspeed" value=""
						style="border: 1px;" readonly="readonly" />
				</div>
			</div>
			<div class="field">
				<div class="caption"style= "width : 200px; font-weight: bold;">系统磁盘写入速率：</div>
				<div class="content"style= "width : 150px;">
					<input type="text" size="40" id="diskwrite" value=""
						style="border: 1px;" readonly="readonly" />
				</div>
				<div class="caption"style= "width : 200px; font-weight: bold;">系统磁盘读取速率：</div>
				<div class="content"style= "width : 150px;">
					<input type="text" size="40" id="diskread" value=""
						style="border: 1px;" readonly="readonly" />
				</div>
			</div>
			<div class="field">
				<div class="caption"style= "width : 200px; font-weight: bold;">网络入口带宽速度：</div>
				<div class="content"style= "width : 150px;">
					<input type="text" size="40" id="bytesin" value=""
						style="border: 1px;" readonly="readonly" />
				</div>
				<div class="caption"style= "width : 200px; font-weight: bold;">网络出口带宽速度：</div>
				<div class="content"style= "width : 150px;">
					<input type="text" size="40" id="bytesout" value=""
						style="border: 1px;" readonly="readonly" />
				</div>
			</div>
			<div class="field">
				<div class="caption"style= "width : 200px; font-weight: bold;">内存利用率：</div>
				<div class="content"style= "width : 150px;">
					<input type="text" size="40" id="mempercent" value=""
						style="border: 1px;" readonly="readonly" />
				</div>
				
				
			</div>
</div>	
<div class="page-form" id="detail2" style="display: none;">
<div class="field">
<div class="caption"style= "width : 100px; font-weight: bold;">云主机名称：</div>
				<div class="content" style= "width : 100px;">
					<input type="text" size="40" id="vmname2" value=""
						style="border: 1px;" readonly="readonly" />
				</div>
<div class="caption"style= "width : 100px;font-weight: bold;">当前状态：</div>
				<div class="content"style= "width : 100px;">
					<input type="text" size="40" id="statustext2" value=""
						style="border: 1px;" readonly="readonly" />
				</div>
</div>
</div>
<script type="text/javascript"
	src="../scripts/pagesjs/console/topo/pzhtopo3.js"></script>
