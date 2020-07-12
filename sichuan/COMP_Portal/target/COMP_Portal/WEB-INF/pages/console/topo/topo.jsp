<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<link
	href="../scripts/lib/vis/vis.min.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="../scripts/lib/vis/vis.min.js"></script>
<div class="list">
	<div class="title">
		<h2>拓扑图</h2>
		<s:hidden id="userId" name="userId"></s:hidden>
	</div>
	<div id="mynetwork" style="height: 600px; width: 100%;"></div>
</div>
<script type="text/javascript"
	src="../scripts/pagesjs/console/topo/topo.js"></script>
