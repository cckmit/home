<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>自定义产品</title>
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
	<header class="header-bgcolor bottom-border">
		<div>
			<a class="logo-link" href="home.jsp"><img width="155px" height="50px"
				class="logo" src="img/logo.png" alt="" /><span>四川政企云服务</span></a>
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
								href="situation_banking.jsp">金融行业解决方案</a> <!-- <a
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

	<section class="self-config dark-section pt80">
		<div class="config-title">
			<h2>自定义配置</h2>
		</div>
		<div class="config-content">
		<h3>基础配置</h3>
		<div>
			<span class="col-name">cpu</span><span class="col-content"><a
				class="cpu hover" href="javascript:void(0);">1核</a> <a
				class="cpu hover" href="javascript:void(0);">2核</a> <a
				class="cpu hover" href="javascript:void(0);">4核</a><a
				class="cpu hover" href="javascript:void(0);">8核</a> <a
				class="cpu hover" href="javascript:void(0);">16核</a></span>
		</div>
		<div>
			<span class="col-name">内存</span><span class="col-content"><a
				class="ram hover" href="javascript:void(0);">2GB</a> <a
				class="ram hover" href="javascript:void(0);">4GB</a> <a
				class="ram hover" href="javascript:void(0);">8GB</a></span>
		</div>
		<div>
			<span class="col-name">系统盘</span> <span class="col-content"> <a
				class="disk hover" href="javascript:void(0);">5GB</a> <a
				class="disk hover" href="javascript:void(0);">10GB</a> <a
				class="disk hover" href="javascript:void(0);">15GB</a>
			</span>
		</div>
		<div>
			<span class="col-name">镜像</span>
			<div class="col-content">
				<div style="position: relative; height: 100%;">
					<select name="" id=""><option value="">Linux</option>
						<option value="">Windows</option></select><select name="" id=""
						style="left: 155px;">
						<option value="">redhat5</option>
						<option value="">redhat6</option>
						<option value="">redhat7</option>
					</select>
				</div>
			</div>
		</div>
	</div>
	<div class="config-content">
		<h3>存储</h3>
		<div>
			<span class="col-name">临时数据盘</span>

			<div class="col-content">
				<div class="slide-box">
					<div id="wrapper" class="wrapper">
						<div id="fill" class="fill"></div>
						<div id="slider" class="slider"></div>
					</div>
					<input id="tmp_disk" type="text" class="slide-val" value="0GB"
						readonly="readonly" />
					<div class="slide-intro">
						<span>0GB</span> <span>50GB</span> <span>100GB</span>
					</div>
				</div>

			</div>

		</div>
		<div style="clear: both;"></div>
	</div>
	<div class="config-content">
		<h3>购买量</h3>
		<div>
			<span class="col-name">使用时间</span>
			<div class="col-content">
				<div class="slide-box">
					<div id="timewrapper" class="wrapper">
						<div id="timefill" class="fill"></div>
						<div id="timeslider" class="slider"></div>
					</div>
					<input id="time" type="text" class="slide-val" value="0个月"
						readonly="readonly" />
					<div class="slide-intro year">
						<span>1月</span> <span>2月</span> <span>3月</span> <span>4月</span> <span>5月</span>
						<span>6月</span> <span>7月</span> <span>8月</span> <span>9月</span> <span>10月</span>
						<span>11月</span> <span>1年</span> <span>2年</span>
					</div>
				</div>

			</div>
		</div>
		<div style="clear: both;"></div>
		<div style="margin-bottom: 200px;">
			<span class="col-name">购买数量</span><span class="col-content"><input
				type="number" name="num" id="num" value="1" min="1" />台</span>
		</div>
	</div>
	</section>

	<div class="cart-bar">
		<div class="cart-content">
			<div class="cart-total">
				<span>当前配置：</span>
				<div class="cart-detail">
					<div class="cart-col">
						<div class="cart-row">
							<span class="name">CPU:</span><span class="cpu">0核</span>
						</div>
						<div class="cart-row">
							<span class="name">内存:</span><span class="ram">0GB</span>
						</div>
						<div class="cart-row">
							<span class="name">系统盘:</span><span class="disk">0GB</span>
						</div>
					</div>
					<div class="cart-col">
						<div class="cart-row">
							<span class="name">镜像:</span><span class="ios"> </span>
						</div>
						<div class="cart-row">
							<span class="name">临时数据盘:</span><span class="tmp_disk">0GB</span>
						</div>
						<div class="cart-row">
							<span class="name">使用时间:</span><span class="time">0个月</span>
						</div>
					</div>
					<div class="cart-col">
						<div class="cart-row">
							<span class="name">购买数量:</span><span class="num">1</span>
						</div>
						<div class="cart-row"></div>
						<div class="cart-row"></div>
					</div>

				</div>
			</div>

			<span class="total-price">总费用：<strong class="price">￥0.00</strong>
				<a class="apply" href="">立即申请</a>
			</h3>
		</div>
	</div>
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
	<script type="text/javascript ">
			
			$('.config-content a').click(function() {
				$(this).addClass("active");
				$(this).removeClass("hover");
				$(this).siblings().removeClass("active");
				$(this).siblings().addClass("hover");
    		if($(this).hasClass("cpu")){
				$('.cart-bar span.cpu').text($(this).text());
			}else if($(this).hasClass("ram")){
				$('.cart-bar span.ram').text($(this).text());
			}else if($(this).hasClass("disk")){
				$('.cart-bar span.disk').text($(this).text());
			}
			});

			$("#num").change(function(){
				$(".cart-bar span.num").text($(this).val());
			});
			
			var menu_width=$('#left').width();
			var slider = document.getElementById('slider');
			var fill = document.getElementById('fill');
			var wrapper_width = document.getElementById('wrapper').clientWidth;
			var slider_width = document.getElementById('slider').clientWidth;
			var wrapper_left = document.getElementById('wrapper').offsetLeft;
			var drag = 0;
			$('#slider').mousedown(function(){
						drag=1;
					});
					
		

			$('#wrapper').click(function(e){
				if(e.target !== slider) {
					if(e.offsetX > (wrapper_width - slider_width)) {
						slider.style.left = wrapper_width - slider_width + 'px';
					fill.style.width = wrapper_width - slider_width + 'px';
						$('#tmp_disk').val("100GB");
						$('.cart-bar span.tmp_disk').text("100GB");
					} else if(e.offsetX < slider_width) {
						slider.style.left = '0px';
						fill.style.width = '0px';
						$('#tmp_disk').val("0GB");
						$('.cart-bar span.tmp_disk').text("0GB");
					} else {
						slider.style.left = e.offsetX - slider_width / 2 + 'px';
						fill.style.width = e.offsetX - slider_width / 2 + 'px';
						$('#tmp_disk').val(Math.round((e.offsetX/wrapper_width)*100)+"GB");
						$('.cart-bar span.tmp_disk').text(Math.round((e.offsetX/wrapper_width)*100)+"GB");
					}
				}
			});
			
			$('#wrapper').mousemove(function(e){
				if(drag == 1) {
					if((e.pageX - wrapper_left-menu_width) > (wrapper_width - slider_width / 2)) {
						slider.style.left = wrapper_width - slider_width + 'px';
						fill.style.width = wrapper_width - slider_width + 'px';
						$('#tmp_disk').val("100GB");
						$('.cart-bar span.tmp_disk').text("100GB");
					} else if((e.pageX - wrapper_left-menu_width) < slider_width ) {
						slider.style.left = '0px';
						fill.style.width = '0px';
						$('#tmp_disk').val("0GB");
						$('.cart-bar span.tmp_disk').text("0GB");
					} else {
						slider.style.left = (e.pageX - wrapper_left-menu_width) - slider_width + 'px';
						fill.style.width = (e.pageX - wrapper_left-menu_width) - slider_width + 'px';
						$('#tmp_disk').val(Math.round(((e.pageX - wrapper_left-menu_width)/wrapper_width)*100)+"GB");
						$('.cart-bar span.tmp_disk').text(Math.round(((e.pageX - wrapper_left-menu_width)/wrapper_width)*100)+"GB");
					}
			}});
		
		
			
			var timeslider = document.getElementById('timeslider');
			var timefill = document.getElementById('timefill');
			var	timewrapper_width = document.getElementById('timewrapper').clientWidth;
			var timeslider_width =document.getElementById('timeslider').clientWidth;
			var timewrapper_left = document.getElementById('timewrapper').offsetLeft;
			var timedrag = 0;
			$('#timeslider').mousedown(function() {
				timedrag = 1;
			});

			$('#timewrapper').click(function(e){
				if(e.target !== timeslider) {
					if(e.offsetX > (timewrapper_width - timeslider_width)) {
						timeslider.style.left = timewrapper_width - timeslider_width + 'px';
						timefill.style.width = timewrapper_width - timeslider_width + 'px';
						$('#time').val("2年");
						$('.cart-bar span.time').text("2年");
					} else if(e.offsetX < timeslider_width) {
						timeslider.style.left = '0px';
						timefill.style.width = '0px';
						$('#time').val("1个月");
						$('.cart-bar span.time').text("1个月");
					} else {
						timeslider.style.left = e.offsetX - timeslider_width / 2 + 'px';
						timefill.style.width = e.offsetX - timeslider_width / 2 + 'px';
						var num=Math.round((e.offsetX/timewrapper_width)*12);
						if(num<11){
							$('#time').val(num+1+"个月");
							$('.cart-bar span.time').text(num+1+"个月");
						}else if(num==11){
							$('#time').val("1年");
							$('.cart-bar span.time').text("1年");
						}else if(num==12){
							$('#time').val("2年");
							$('.cart-bar span.time').text("2年");
						}
					}
				}
			});
			
			$('#timewrapper').mousemove(function(e){
				if(timedrag == 1) {
					if((e.pageX - timewrapper_left-menu_width) > (timewrapper_width - timeslider_width / 2)) {
						timeslider.style.left = timewrapper_width - timeslider_width + 'px';
						timefill.style.width = timewrapper_width - timeslider_width + 'px';
						$('#time').val("2年");
						$('.cart-bar span.time').text("2年");
					} else if((e.pageX - timewrapper_left-menu_width) < timeslider_width ) {
						timeslider.style.left = '0px';
						timefill.style.width = '0px';
						$('#time').val("1个月");
						$('.cart-bar span.time').text("1个月");
					} else {
						timeslider.style.left = (e.pageX - timewrapper_left-menu_width) - timeslider_width + 'px';
						timefill.style.width = (e.pageX - timewrapper_left-menu_width) - timeslider_width + 'px';
						var num=Math.round(((e.pageX - timewrapper_left-menu_width)/timewrapper_width)*12);
						if(num<11){
							$('#time').val(num+1+"个月");
							$('.cart-bar span.time').text(num+1+"个月");
						}else if(num==11){
							$('#time').val("1年");
							$('.cart-bar span.time').text("1年");
						}else if(num==12){
							$('#time').val("2年");
							$('.cart-bar span.time').text("2年");
						}	
							
					}
			}});
			
			$(document).mouseup(function() {
				drag = 0;
				timedrag = 0;
			});

			
	</script>
</body>

</html>