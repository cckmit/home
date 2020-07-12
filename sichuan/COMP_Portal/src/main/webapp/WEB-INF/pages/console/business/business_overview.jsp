<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="right" class="right">



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



<%-- 

	<div class="details-con">

		<s:iterator value="businessList">
			<div class="ov-block">
				
				<div class="detail-title">
					<h3>
						<s:property value='name' />
					</h3>
				</div>
				<div id="hostContent_<s:property value='businessId'/>">
					<img style="margin: 100px;" src="../images/loading.gif" />
					<ul class="list resourceList"></ul>
				</div>
			</div>
		</s:iterator>

		<div style="clear: both;"></div>
	</div>
</div> --%>
<script type="text/javascript"
	src="../scripts/pagesjs/console/business/business_overview.js"></script>