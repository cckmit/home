<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>云主机推荐</title>
<link rel="stylesheet" type="text/css" href="css/zg.css" />
<script src="js/jquery-1.12.4.js" type="text/javascript" charset="utf-8"></script>
<script src="js/browserDetect.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
	if (BrowserDetect.version <= 8 && BrowserDetect.browser == "Explorer") {
		window.location.href = "404.html";
	}
	$(function() {
		$('.user-info-tab').load('userInfoTab.jsp');
		
		$.ajax({
			url:'vmRecommendStandards.action',
			type:'post',
			data:{},
			success:function(data){
				var standards= data.standards;
				if(standards.length>3){
					standards.length=3;
				}
				var liHtml="";
				for(var i=0;i<standards.length;i++){
					var cpuNum=standards[i].cpuNum;
					var ramSize=parseInt(standards[i].ramSize);
					var discSize=parseInt(standards[i].discSize);
					var desc=standards[i].description;
					var monthPrice=standards[i].monthPrice;
					liHtml+="<li>";
					liHtml+="<h1>推荐产品"+(i+1)+"</h1>";
					liHtml+="<ul>";
					liHtml+="<li><label>处理器：</label></label><span>"+cpuNum+"核</span></li>";
					liHtml+="<li><label>内存：</label></label><span>"+ramSize+"GB</span></li>";
					liHtml+="<li><label>系统盘：</label></label><span>"+discSize+"GB</span></li>";
					liHtml+="<li><label>描述：</label></label><span>"+desc+"</span></li>";
					liHtml+="</ul>";
					liHtml+="<h2>";
					liHtml+="<strong>￥"+monthPrice+"</strong>元/月起";
					liHtml+="</h2>";
					liHtml+="<a href='console/vmApplyAction.action?cpuNum="+cpuNum+"&ramSize="+ramSize+"'>立即购买</a>";
				}
				
				$("ul.product-items").append(liHtml);
				
			},error:function(){
				window.location.href="home.jsp";
			}
		});

	
		$.ajax({
			url:'ebsRecommendStandards.action',
			type:'post',
			data:{},
			success:function(ret){
				var ebsStandards= ret.standards;
				var ebsHtml="";
				for(var i=0;i<ebsStandards.length;i++){
					var a=1000000000000000;
					var discSize=parseInt(ebsStandards[i].discSize);
					var monthPrice=parseFloat(ebsStandards[i].monthPrice);
					var price=a*monthPrice*discSize/a;
					ebsHtml+="<li><i></i>";
					ebsHtml+="<span class='name'>推荐产品"+(i+1)+"</span>";
					ebsHtml+='<p class="desc">';
					ebsHtml+='<span class="col-name">磁盘大小：</span><span class="col-text">'+discSize+'GB</span>';
					ebsHtml+='</p><p>';
					ebsHtml+='<span class="price">￥'+price+'</span><span class="col-text">元/每月起</span>';
					ebsHtml+='</p>';
					ebsHtml+='<a href="console/ebsApplyAction.action?discSize='+discSize+'">立即购买</a></li>';
				}
				
				$("ul.ebs-products").append(ebsHtml);
			},error:function(){
				window.location.href="home.jsp";
			}
		});
		
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
	<section class="dark-title">
		<h1>云主机推荐</h1>
		<div class="triangle"></div>
	</section>
	<section>
		<div class="product-container">
			<ul class="product-items">
				<%-- <li>
					<h1>推荐产品1</h1>
					<ul>
						<li><label>处理器：</label></label><span>6G</span></li>
						<li><label>内存：</label></label><span>6G</span></li>
						<li><label>系统盘：</label></label><span>578G</span></li>
						<li><label>处理器：</label></label><span>REDHAT_6.5</span></li>
					</ul>
					<h2>
						<strong>$45</strong>元/月起
					</h2> <a href="">立即购买</a>
				</li>
				 --%>
			</ul>
			<p>
				注：如果以上没有符合您要求的套餐，请点击查看 <a href="hostMore.action" class="more">更多套餐</a>
			</p>
		</div>

		<div id="ebs-recommend" class="product-list">
			<h2>其他产品</h2>
			<div>
				<h3>云硬盘</h3>
				<ul class="ebs-products">
					<%-- <li><i></i><span class="name">推荐产品1</span>
						<p class="desc">
							<span class="col-name">磁盘大小：</span><span class="col-text">256G</span>
						</p>
						<p>
							<span class="price">$45</span><span class="col-text">元/每月起</span>
						</p> <a href="">立即购买</a></li> --%>
				</ul>
			</div>

			<div class="none">
				<h3>云网络</h3>
				<ul class="network-products">
					<li><i></i><span class="name">推荐产品1</span>
						<p class="desc">
							<span class="col-name">磁盘大小：</span><span class="col-text">256G</span>
						</p>
						<p>
							<span class="price">$45</span><span class="col-text">元/每月起</span>
						</p> <a href="">立即购买</a></li>
					<li><i></i><span class="name">推荐产品1</span>
						<p class="desc">
							<span class="col-name">磁盘大小：</span><span class="col-text">256G</span>
						</p>
						<p>
							<span class="price">$45</span><span class="col-text">元/每月起</span>
						</p> <a href="">立即购买</a></li>
					<li><i></i><span class="name">推荐产品1</span>
						<p class="desc">
							<span class="col-name">磁盘大小：</span><span class="col-text">256G</span>
						</p>
						<p>
							<span class="price">$45</span><span class="col-text">元/每月起</span>
						</p> <a href="">立即购买</a></li>
					<li><i></i><span class="name">推荐产品1</span>
						<p class="desc">
							<span class="col-name">磁盘大小：</span><span class="col-text">256G</span>
						</p>
						<p>
							<span class="price">$45</span><span class="col-text">元/每月起</span>
						</p> <a href="">立即购买</a></li>
					<li><i></i><span class="name">推荐产品1</span>
						<p class="desc">
							<span class="col-name">磁盘大小：</span><span class="col-text">256G</span>
						</p>
						<p>
							<span class="price">$45</span><span class="col-text">元/每月起</span>
						</p> <a href="">立即购买</a></li>
				</ul>
			</div>
		</div>
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
</body>

</html>