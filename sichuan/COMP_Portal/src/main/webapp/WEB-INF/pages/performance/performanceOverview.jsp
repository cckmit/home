<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="right" class="right">

	<s:hidden id="vmId" name="vmId"></s:hidden>

	<div class="overview-details">

		<s:iterator value="businessList">

			<div class="ov-app-block">
				<input type="hidden" class="business_data"
					value="<s:property value='businessId'/>" />
				<h3>
					<s:property value='name' />
				</h3>
				<div id="count_<s:property value='businessId'/>" class="ov-app-content">

				</div>
				<ul id="detail_<s:property value='businessId'/>" class="ov-app-details">
				</ul>
			</div>
		</s:iterator>

	</div>
</div>

<style>
.overview-details .ov-app-block {
	width: 1000px;
	height: 300px;
	background-color: #fff;
	margin: 0 auto 50px;
	position: relative;
	box-sizing: border-box;
}

.overview-details .ov-app-block .ov-app-content {
	float: left;
	width: 340px;
	height: 250px;
	box-sizing: border-box;
	border-right: 4px solid #F2F2F2;
	border-top: 2px solid #F2F2F2;
}
</style>

<script type="text/javascript"
	src="../scripts/pagesjs/performance/performanceOverview.js"></script>