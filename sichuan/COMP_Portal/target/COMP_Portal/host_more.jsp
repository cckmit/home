<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>云主机</title>
<link rel="stylesheet" type="text/css" href="css/zg.css" />
<script src="js/jquery-1.12.4.js" type="text/javascript" charset="utf-8"></script>
<script src="js/browserDetect.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
	if (BrowserDetect.version <= 8 && BrowserDetect.browser == "Explorer") {
		window.location.href = "404.html";
	}
	$(function() {
		$('.user-info-tab').load('userInfoTab.jsp');
	});
</script>
</head>

<body>
	<header class="">
		<div>
			<a class="logo-link" href="home.jsp"><img width="155px"
				height="50px" class="logo" src="img/logo.png" alt="" /><span>四川政企云服务</span></a>
			<nav>
				<ul class="header-list">
					<li class="list-nav"><a href="home.jsp">首页</a></li>
					<li class="list-nav product-nav"><a href="">产品</a>
						<div class="sub-nav">
							<a href="host.jsp">云主机</a> <a href="ebs.jsp">云硬盘</a> <a href="">云网络</a>
						</div></li>
					<li class="list-nav solution-nav"><a href="">应用场景</a>
						<div class="sub-nav">
							<a href="situation_education.jsp">教育行业解决方案</a> <a
								href="situation_company.jsp">政企云解决方案</a> <a
								href="situation_medical.jsp">医疗行业解决方案</a> <a
								href="situation_banking.jsp">金融行业解决方案</a>
							<!-- <a
								href="situation_factory.jsp">工业行业解决方案</a> -->
						</div></li>
				</ul>

				<ul class="login">
					<a href="console/resourcesOverviewAction.action" class="mini-logo">
						<img height='25px' width='25px' src="img/ico-console.png" />
						<p>控制台</p>
					</a>
					<s:if test="#session.userInfo!=null">
						<li class="user"><a href="#" class="user-head"> <img
								style="width: 50px; height: 50px;" src="img/head.png" />
						</a></li>
						<div class="user-info-tab none"></div>
					</s:if>
					<s:else>
						<li class="login-btn"><a href="login.jsp">登录</a></li>
					</s:else>
				</ul>
			</nav>
		</div>
	</header>
	<main>
	<section class="title bg-black white-color"
		style="border-bottom: 1px solid #fff;">

		<img style="bottom: 0; left: 0;" src="img/banner-05.png" width="50%" />
		<div class="title-detail title-right" style="width: 400px;">
			<div>
				<img src="img/server-icon02.png" width="65px" class="title-icon" />
				<h1 class="title">更多云主机套餐</h1>
			</div>
			<p>为您提供更多、更便捷的云主机产品选择</p>
		</div>
	</section>
	<section class="bg-black white-color" style="padding: 110px 0 30px;">
		<s:iterator value="standards">
			<s:form action="console/vmApplyAction.action" method="post">
				<div class="product-more">
					<div class="product-more-title">

						<h3>产品名称</h3>
						<ul>
							<li>快速</li>
							<li>便捷</li>
							<li>稳定持久</li>
							<li>安全可靠</li>
						</ul>
					</div>
					<div class="product-more-detail">
						<div class="detail-content">
							<div class="content1">
								<img src="img/cpu.png" />
								<div class="content-info">
									<div>
										<h3 class="cpu">
											<s:property value="cpuNum" />
											核
										</h3>
										<span>CPU</span>
									</div>

									<p>
										<s:property value="hourPrice" />
										元/小时或
										<s:property value="monthPrice" />
										元/月
									</p>
								</div>
							</div>
							<div class="content2">
								<img src="img/ram.png" />
								<div class="content-info">
									<div>
										<h3 class="ram">
											<s:property value="ramSize" />
											GB
										</h3>
										<span>内存</span>
									</div>

									<p>
										<s:property value="hourPrice" />
										元/小时或
										<s:property value="monthPrice" />
										元/月
									</p>
								</div>
							</div>
							<div class="content3">
								<img src="img/os.png" />
								<div class="content-info">
									<div>
										<h3 class="os">Linux</h3>
										<span>系统</span>
									</div>

									<p>免费赠送</p>
								</div>
							</div>
							<div class="content4">
								<img src="img/ebs.png" />

								<div class="content-info">
									<div>
										<h3 class="ebs">20GB</h3>
										<span>系统盘</span>
									</div>

									<p>免费赠送</p>
								</div>
							</div>

						</div>
						<div class="detail-order">
							<div class="detail-order-opt">
								<span class="num">购买数量<select name="num"
									onchange="changeNum(this);">
										<option value="1">1</option>
										<option value="2">2</option>
										<option value="3">3</option>
										<option value="4">4</option>
										<option value="5">5</option>
								</select>个
								</span> <span class="chargeType">时间 <select name="chargeType"
									onchange="timeType(this);">
										<option value="month">月</option>
										<option value="hour">小时</option>
								</select> <input type="text" name="lengthTime" value="1"
									onblur="changeTime(this);" /> <span class="time-unit">个月</span></span>
								<input type="hidden" name="monthPrice"
									value="<s:property value='monthPrice' />" /> <input
									type="hidden" name="hourPrice"
									value="<s:property value='hourPrice' />" /> <input
									type="hidden" name="cpuNum"
									value="<s:property value='cpuNum' />" /> <input type="hidden"
									name="ramSize" value="<s:property value='ramSize' />" />
							</div>
							<div class="detail-order-apply">
								<h3>总计：</h3>
								<strong class="price">￥<s:property value="monthPrice" /></strong>
								<a href="javascript:;" onclick="submit(this);">立即购买</a>
							</div>
						</div>
					</div>
				</div>
			</s:form>
			<s:form action="console/vmApplyAction.action" method="post">
				<div class="product-more">
					<div class="product-more-title">

						<h3>产品名称</h3>
						<ul>
							<li>快速</li>
							<li>便捷</li>
							<li>稳定持久</li>
							<li>安全可靠</li>
						</ul>
					</div>
					<div class="product-more-detail">
						<div class="detail-content">
							<div class="content1">
								<img src="img/cpu.png" />
								<div class="content-info">
									<div>
										<h3 class="cpu">
											<s:property value="cpuNum" />
											核
										</h3>
										<span>CPU</span>
									</div>

									<p>
										<s:property value="hourPrice" />
										元/小时或
										<s:property value="monthPrice" />
										元/月
									</p>
								</div>
							</div>
							<div class="content2">
								<img src="img/ram.png" />
								<div class="content-info">
									<div>
										<h3 class="ram">
											<s:property value="ramSize" />
											GB
										</h3>
										<span>内存</span>
									</div>

									<p>
										<s:property value="hourPrice" />
										元/小时或
										<s:property value="monthPrice" />
										元/月
									</p>
								</div>
							</div>
							<div class="content3">
								<img src="img/os.png" />
								<div class="content-info">
									<div>
										<h3 class="os">windows</h3>
										<span>系统</span>
									</div>

									<p>免费赠送</p>
								</div>
							</div>
							<div class="content4">
								<img src="img/ebs.png" />

								<div class="content-info">
									<div>
										<h3 class="ebs">40GB</h3>
										<span>系统盘</span>
									</div>

									<p>免费赠送</p>
								</div>
							</div>

						</div>
						<div class="detail-order">
							<div class="detail-order-opt">
								<span class="num">购买数量<select name="num"
									onchange="changeNum(this);">
										<option value="1">1</option>
										<option value="2">2</option>
										<option value="3">3</option>
										<option value="4">4</option>
										<option value="5">5</option>
								</select>个
								</span> <span class="chargeType">时间 <select name="chargeType"
									onchange="timeType(this);">
										<option value="month">月</option>
										<option value="hour">小时</option>
								</select> <input type="text" name="lengthTime" value="1" onblur="changeTime(this);"/>
									<span class="time-unit">个月</span></span><input type="hidden"
									name="monthPrice" value="<s:property value='monthPrice' />" />
								<input type="hidden" name="hourPrice"
									value="<s:property value='hourPrice' />" /> <input
									type="hidden" name="cpuNum"
									value="<s:property value='cpuNum' />" /> <input type="hidden"
									name="ramSize" value="<s:property value='ramSize' />" />
							</div>
							<div class="detail-order-apply">
								<h3>总计：</h3>
								<strong class="price">￥<s:property value="monthPrice" /></strong>
								<a href="javascript:;" onclick="submit(this);">立即购买</a>
							</div>
						</div>
					</div>
				</div>
			</s:form>
		</s:iterator>
	</section>
	</main>
	<footer>
		<div class="container">
			<div class="link">
				<div class="call-center">
					<img src="img/center.png" alt="" />
					<h3>
						售前咨询电话<br />4001099199
					</h3>
					<h3 class="worktime">工作时间</h3>
					<p>工作日 8:30～17：30</p>
				</div>
				<div class="linkdoc">
					<dl>
						<dt>
							<img src="img/help.png" />新手指南
						</dt>
						<dd>
							<ul>
								<li><a href="">注册/登录</a></li>
								<li><a href="">常见问题</a></li>
								<li><a href="">了解产品</a></li>
							</ul>
						</dd>
					</dl>
					<dl>
						<dt>
							<img src="img/about.png" />用户相关
						</dt>
						<dd>
							<ul>
								<li><a href="">控制台</a></li>
								<li><a href="">订单管理</a></li>
								<li><a href="">ICP备案</a></li>
							</ul>
						</dd>
					</dl>
					<dl>
						<dt>
							<img src="img/partner.png" />合作伙伴
						</dt>
						<dd>
							<ul>
								<li><a href="">合作伙伴登录</a></li>
								<li><a href="">申请加入</a></li>
								<li><a href="">合作流程</a></li>
								<li><a href="">合作条件</a></li>
							</ul>
						</dd>
					</dl>
					<dl>
						<dt>
							<img src="img/support.png" />支持与帮助
						</dt>
						<dd>
							<ul>
								<li><a href="">平台使用手册</a></li>
								<li><a href="">人工在线帮助</a></li>
								<li><a href="">常见问题</a></li>
								<li><a href="">通知公告</a></li>
							</ul>
						</dd>
					</dl>
				</div>

			</div>
			<div class="warning">
				<h3>
					法律声明<br />再您开始访问，浏览和使用本网站，请仔细阅读本声明的所有条款。一旦浏览，使用本网站表示您已经同意接受本声明条款。<a
						href="">了解更多</a>
				</h3>
				<p1>中国移动通信集团四川有限公司.保留所有权利.|蜀ICP备09015892号-31</p1>
			</div>
		</div>
	</footer>

	<script type="text/javascript">
		function changeNum(obj) {
			var num = $(obj).find("option:selected").val();

			var chargeType = $(obj).parent().siblings("span.chargeType").find(
					'select[name="chargeType"]').find("option:selected").val();
			var monthPrice = $(obj).parent().siblings(
					'input[name="monthPrice"]').val();
			var hourPrice = $(obj).parent().siblings('input[name="hourPrice"]')
					.val();
			var lengthTime = $(obj).parent().siblings("span.chargeType").find(
					'input[name="lengthTime"]').val();
			var totalPrice = charging(monthPrice, hourPrice, num, chargeType,
					lengthTime);
			$(obj).parent().parent().siblings('div.detail-order-apply').find(
					'.price').text("￥" + totalPrice);
		}

		function timeType(obj) {
			var chargeType = $(obj).find("option:selected").val();
			if (chargeType == 'month') {
				$(obj).siblings('span.time-unit').text("个月");

			} else if (chargeType == 'hour') {
				$(obj).siblings('span.time-unit').text("个小时");
			}
			var num = $(obj).parent().siblings("span.num").find(
					'select[name="num"]').find("option:selected").val();
			var monthPrice = $(obj).parent().siblings(
					'input[name="monthPrice"]').val();
			var hourPrice = $(obj).parent().siblings('input[name="hourPrice"]')
					.val();
			var lengthTime = $(obj).siblings('input[name="lengthTime"]').val();
			var totalPrice = charging(monthPrice, hourPrice, num, chargeType,
					lengthTime);
			$(obj).parent().parent().siblings('div.detail-order-apply').find(
					'.price').text("￥" + totalPrice);
		}

		function changeTime(obj) {
			var chargeType = $(obj).siblings('select[name="chargeType"]').find(
					"option:selected").val();
			var num = $(obj).parent().siblings("span.num").find(
					'select[name="num"]').find("option:selected").val();
			var monthPrice = $(obj).parent().siblings(
					'input[name="monthPrice"]').val();
			var hourPrice = $(obj).parent().siblings('input[name="hourPrice"]')
					.val();
			var lengthTime = $(obj).val();
			var totalPrice = charging(monthPrice, hourPrice, num, chargeType,
					lengthTime);
			$(obj).parent().parent().siblings('div.detail-order-apply').find(
					'.price').text("￥" + totalPrice);
		}

		function charging(monthPrice, hourPrice, num, chargeType, lengthTime) {
			var a = 10000000000;
			var price = "";
			if (chargeType == "hour") {
				price = a * hourPrice * lengthTime * num / a;
			} else if (chargeType == "month") {
				price = a * monthPrice * lengthTime * num / a;
			}
			return price;

		}

		function submit(obj) {
			$(obj).parents('form').submit();
		}
	</script>
</body>

</html>