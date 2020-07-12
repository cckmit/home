<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="cart-content">
	<div class="cart-total">
		<span>当前配置：</span>
		<div class="cart-detail">
			<div class="cart-col">
				<div class="cart-row">
					<span class="name">名称：</span><span id="cart-name"> </span>
				</div>
				<div class="cart-row">
					<span class="name">负载均衡方式：</span><span id="cart-lbtype"> </span>
				</div>

				<div class="cart-row">
					<span class="name">负载均衡策略：</span><span id="cart-strategy"> </span>
				</div>
				<div class="cart-row">
					<span class="name">所属企业客户：</span><span id="cart-app"> </span>
				</div>
			</div>
			<div class="cart-col">


				<div class="cart-row">
					<span class="name">VLAN：</span><span id="cart-vlan"> </span>
				</div>
				<div class="cart-row">
					<span class="name">IP段：</span><span id="cart-ips"></span>
				</div>
				<div class="cart-row">
					<span class="name">IP：</span><span id="cart-ip"> </span>
				</div>
					<div class="cart-row">
					<span class="name">负载均衡端口：</span><span id="cart-port"> </span>
				</div>
			</div>

			<div class="cart-col">
				<div class="cart-row">
					<span class="name">流量协议类型：</span><span id="cart-protocal"> </span>
				</div>
				<div class="cart-row">
					<span class="name">吞吐能力（Kbps）：</span><span id="cart-throughput"> </span>
				</div>

				<div class="cart-row">
					<span class="name">并发链接数（个）：</span><span id="cart-connectNum"> </span>
				</div>
				<div class="cart-row">
					<span class="name">链接速度（个／秒）：</span><span id="cart-newConnectNum"> </span>
				</div>
			</div>
		</div>
	</div>

	<span class="total-price"> <strong class="price">&nbsp;</strong>
		<a class="apply" href="javascript:submitform()">立即申请</a></span>
</div>
<<script type="text/javascript">
$(function(){
	var lbType = $("#lbType").find("option:eq(0)").text();
	$("#cart-lbtype").text(lbType);

	var strategy = $("#strategy").find("option:eq(0)").text();
	$("#cart-strategy").text(strategy);

	var protocal = $("#protocal").find("option:eq(0)").text();
	$("#cart-protocal").text(protocal);
	var userId = document.getElementById("userId").value;
	if (userId != "admin"){
		$("#cart-app").text($("#cartAppName").text());
	}
});
</script>

