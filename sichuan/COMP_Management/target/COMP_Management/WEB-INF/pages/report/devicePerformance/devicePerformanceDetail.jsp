<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style>
<!--
.result{
	margin-left: 0;
}
.result-list {
    width: 41%;
}
-->
</style>
<!-- 用户详细信息 -->
<div class="page-form" id="devicePerformance_detail" style="display: none;">
<div class="contentDetail">
    <div class="result" style="margin-top:5px;width:1300px;">
		<div class="BussPmgTabBg" style="height: 600px; width:1200px;">
			<div class="PerformanceChart" style="height: 270px;background:#fcfcfc;width:1100px;">
				<div id="chartCpu" style="height: 240px;margin-top: 30px;margin-left: 1px;width:1100px;"></div>
			</div>
			<div class="PerformanceChart" style="height: 270px;background:#fcfcfc;width:1100px;">
				<div id="chartMem" style="height: 240px;margin-top: 30px;margin-left: 1px;width:1100px;"></div>
			</div>
		</div>
	</div>
</div>
</div>
