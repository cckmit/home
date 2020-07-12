<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<div class="overview-details">
	<div class="ov-block">
		<h3>云主机概况</h3>
		<a href="vmQueryListAction.action" class="more">查看更多</a>
		<div class="ov-content">
			<div class="ov-total">
				<span class="fl">共</span> <i id="vmCount" class="ov-num"></i> <span
					class="fr">个</span>
			</div>
			<s:if test="#session.userInfo.userId=='admin'">
				<a class="apply" href="vmApplyAction.action">申请云主机</a>
			</s:if>
		</div>
		<ul id="hostList" class="ov-details">
			<!-- <li class="run shadow"><a href="">
					<div class="status-block">运行中</div>
					<div class="status-title">test</div>
			</a></li> -->
		</ul>
	</div>
	<div class="ov-block">
		<h3>云硬盘概况</h3>
		<a href="ebsQueryListAction.action" class="more">查看更多</a>
		<div class="ov-content">
			<div class="ov-total">
				<span class="fl">共</span> <i id="ebsCount" class="ov-num"></i> <span
					class="fr">个</span>
			</div>
			<s:if test="#session.userInfo.userId=='admin'">
				<a class="apply" href="ebsApplyAction.action">申请云硬盘</a>
			</s:if>
		</div>
		<ul id="ebsList" class="ov-details">
		</ul>
	</div>
	<div class="ov-block">
		<h3>负载均衡概况</h3>
		<a href="loadBalanceAction.action" class="more">查看更多</a>
		<div class="ov-content">
			<div class="ov-total">
				<span class="fl">共</span> <i id="LBcount" class="ov-num"></i> <span
					class="fr">个</span>
			</div>
			<s:if test="#session.userInfo.userId=='admin'">
				<a class="apply" href="LBapplyAction.action">申请负载均衡</a>
			</s:if>
		</div>
		<ul id="LBlist" class="ov-details">

		</ul>
	</div>

	<div class="ov-block none">
		<h3>物理机概况</h3>
		<a href="pmQueryListAction.action" class="more">查看更多</a>
		<div class="ov-content">
			<div class="ov-total">
				<span class="fl">共</span> <i id="pmCount" class="ov-num"></i> <span
					class="fr">个</span>
			</div>
			<a class="apply" href="pmApplyAction.action">申请物理机</a>
		</div>
		<ul id="pmList" class="ov-details">

		</ul>
	</div>
	
	<div class="ov-block">
		<h3>云主机批量修改概况</h3>
		<a href="vmBatchQueryListAction.action" class="more">查看更多</a>
		<div class="ov-content">
			<div class="ov-total">
				<span class="fl">共</span> <i id="vmBatchModifyCount" class="ov-num"></i>
				<span class="fr">个</span>
			</div>
		</div>
		<ul id="vmBatchModifyList" class="ov-details">

		</ul>
	</div>

<s:if test="#session.userInfo.userId=='admin'">
	<div class="ov-block">
		<h3>VLAN概况</h3>
		<a href="vlanQueryListAction.action" class="more">查看更多</a>
		<div class="ov-content">
			<div class="ov-total">
				<span class="fl">共</span> <i id="vlanCount" class="ov-num"></i> <span
					class="fr">个</span>
			</div>
			<a class="apply" href="createVlanAction.action">申请VLAN</a>
		</div>
		<ul id="vlanList" class="ov-details">

		</ul>
	</div>

	<div class="ov-block">
		<h3>IP段概况</h3>
		<a href="ipSegmentQueryListAction.action" class="more">查看更多</a>
		<div class="ov-content">
			<div class="ov-total">
				<span class="fl">共</span> <i id="ipSegmentCount" class="ov-num"></i>
				<span class="fr">个</span>
			</div>
			<a class="apply" href="createIpSegmentAction.action">申请IP段</a>
		</div>
		<ul id="ipSegmentList" class="ov-details">

		</ul>
	</div>

</s:if>

</div>

<script type="text/javascript"
	src="../scripts/pagesjs/console/resources/business_overview.js"></script>



<%-- 

<div id="right">
	<h1>概况</h1>
	<div class="details-con">
		<div class="ov-block">
			<div class="detail-title">
				<h3>云主机</h3>
				<span class="apply"> <a class="apply_arrow"
					href="vmApplyAction.action">申请云主机</a>
				</span>
			</div>
			<div id="hostContent">
				<img style="margin: 100px;" src="../images/loading.gif" />
			</div>
		</div>
		<div class="ov-block">
			<div class="detail-title">
				<h3>物理机</h3>
				<span class="apply"> <a class="apply_arrow"
					href="pmApplyAction.action">申请物理机</a>
				</span>
			</div>
			<div id="pmContent">
				<img style="margin: 100px;" src="../images/loading.gif" />
			</div>
		</div>
		<div class="ov-block">
			<div class="detail-title">
				<h3>云硬盘</h3>
				<span class="apply"> <a id="apply_host"
					href="ebsApplyAction.action">申请云硬盘</a>
				</span>
			</div>
			<div id="ebsContent">
				<img style="margin: 100px;" src="../images/loading.gif" />
			</div>
		</div>
		<div class="ov-block">
			<div class="detail-title">
				<h3>Vlan</h3>
				<span class="apply"> <a class="apply_host"
					href="createVlanAction.action">申请Vlan</a>
				</span>
			</div>
			<div id="vlanContent">
				<img style="margin: 100px;" src="../images/loading.gif" />
			</div>
		</div>
		<div class="ov-block">
			<div class="detail-title">
				<h3>IP段</h3>
				<span class="apply"> <a class="apply_host"
					href="createIpSegmentAction.action">申请IP段</a>
				</span>
			</div>
			<div id="ipSegmentContent">
				<img style="margin: 100px;" src="../images/loading.gif" />
			</div>
		</div>
		<div class="ov-block">
			<div class="detail-title">
				<h3>云主机批量审批结果</h3>
			</div>
			<div id="vmBatchModifyContent">
				<img style="margin: 100px;" src="../images/loading.gif" />
			</div>
		</div>
		<div class="ov-block">
			<div class="detail-title">
				<h3>负载均衡</h3>
				<span class="apply"> <a class="apply_host"
					href="LBapplyAction.action">申请负载均衡</a>
				</span>
			</div>
			<div id="loadbalance">
				<img style="margin: 100px;" src="../images/loading.gif" />
			</div>
		</div>
		<div style="clear: both;"></div>
	</div>
</div> 
<script type="text/javascript"
	src="../scripts/pagesjs/console/resources/business_overview.js"></script>--%>