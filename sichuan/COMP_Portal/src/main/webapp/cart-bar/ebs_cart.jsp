<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="cart-content">
	<div class="cart-total">
		<span>当前配置：</span>
		<div class="cart-detail">
			<div class="cart-col">
				<div class="cart-row">
					<span class="name">所属企业客户:</span><span id="cart-app"></span>
				</div>
				<!-- 		<div class="cart-row">
					<span class="name">硬盘类型:</span><span></span>
				</div> -->

				<div class="cart-row">
					<span class="name">硬盘名称:</span><span id="cart-name"></span>
				</div>
				<div class="cart-row">
					<span class="name">云硬盘容量:</span><span id="cart-discSize"> </span>
				</div>

			</div>
			<div class="cart-col">
				<div class="cart-row">
					<span class="name">使用时间:</span><span class="time">1小时</span>
				</div>
				<div class="cart-row">
					<span class="name">块个数:</span><span class="num">1</span>
				</div>
				<div class="cart-row"></div>
			</div>
		</div>
	</div>

	<span class="total-price "><span class="none"> 总费用：<strong
			class="price">￥0.00</strong></span> <a class="apply"
		href="javascript:submitform();">立即申请</a>
</div>
<script>
	$(function() {
		$("#cart-discSize").text($("#discSize").val());
		var userId = document.getElementById("userId").value;
		if (userId != "admin"){
			$("#cart-app").text($("#cartAppName").text());
		}
		
	});
</script>


