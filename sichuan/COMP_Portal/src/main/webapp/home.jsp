<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>首页</title>
<link rel="stylesheet" type="text/css" href="css/zg.css" />
<link rel="stylesheet" type="text/css" href="css/pageswitch.css" />
<script src="js/jquery-1.12.4.js" type="text/javascript" charset="utf-8"></script>
<script src="js/browserDetect.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
	if (BrowserDetect.version <= 8 && BrowserDetect.browser == "Explorer") {
		window.location.href = "404.html";
	}
</script>
</head>

<body>
	<header>
		<div>
			<a class="logo-link" href="home.jsp"><img width="155px"
				height="50px" class="logo" src="img/logo.png" alt="" /><span>四川政企云服务</span></a>
			<nav>
				<ul class="header-list">
					<li class="list-nav"><a href="home.jsp">首页</a></li>
					<li class="list-nav product-nav"><a href="">产品</a>
						<div class="sub-nav">
							<a href="host.jsp">云主机</a> <a href="ebs.jsp">云硬盘</a> <a href="#">云网络</a>
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
						<li class="user"><a href="userCenter/orderQuery.action" class="user-head"> <img
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
	<section class="slider">
		<div id="container" data-PageSwitch>
			<div class="sections">
				<div class="section active" id="section0">
					<div class="intro">
						<h1 class="title">云主机</h1>
						<p>云主机是在一组集群主机上虚拟出多个类似独立主机的部分，云服务器具有自助管理、数据安全保障、自动故障恢复和防网络攻击等高级功能，能够帮您简化开发部署过程，降低运维成本，构建按需扩展的应用，从而更适应快速多变的业务需求。</p>
						<a href="host.jsp">了解详情</a>
					</div>
					<img src="img/banner-01.png" />
				</div>
				<div class="section" id="section1">
					<div class="intro">
						<h1 class="title">云硬盘</h1>
						<p>云硬盘可以低成本提供大量的文件存储。并具有灵活变更扩展、安全、低成本、高可靠的特点。用户可以通过简单的鼠标点击，为业务虚拟机动态扩展存储容量，无需中断业务，减少运维成本，提升业务可靠性。</p>
						<a href="ebs.jsp">了解详情</a>
					</div>
					<img src="img/banner-02.png" />
				</div>
				<div class="section" id="section2">
					<div class="intro">
						<h1 class="title">云网络</h1>
						<p>利用云技术，向客户提供融合的、更好业务感知的网络服务及应用。</p>
						<a href="">了解详情</a>
					</div>
					<img src="img/banner-03.png" />
				</div>

			</div>
		</div>
	</section>
	<section class="product">
		<div class="container">
			<div class="product-title">
				<h2>全省领先的平台云产品</h2>
				<p>计算、存储、网络、安全、大数据、人工智能，普惠科技助您飞跃发展</p>
			</div>
			<ul>
				<li>
					<div class="product-brief">
						<div class="detail-head">
							<img src="img/brief-logo1.png" />
							<h3>云主机</h3>
						</div>
						<ul>
							<li>快速部署</li>
							<li>安全保障</li>
							<li>稳定可靠</li>
<!-- 							<li>弹性伸缩</li> -->
						</ul>
					</div>
					<div class="product-details">
						<div class="detail-head">
							<h3>云主机</h3>
							<p>自助管理、数据安全保障、自动故障恢复和防网络攻击等高级功能，能够帮您简化开发部署过程，降低运维成本，构建按需扩展的应用，从而更适应快速多变的业务需求。</p>
							<a href="console/vmApplyAction.action">购买</a>
						</div>
						<ul>
							<li>
								<h3>快速部署</h3>
								<p>数秒内便可创建云主机并投入使用，提高业务部署及上线速度。</p>
							</li>
							<li>
								<h3>安全保障</h3>
								<p>提供密钥认证、安全组防护、防火墙防护、多用户隔离等手段，确保业务安全。</p>
							</li>
							<li>
								<h3>稳定可靠</h3>
								<p>云主机可用性不低于99.95%，提供宕机迁移、数据备份和恢复等功能，确保业务稳定。</p>
							</li>
<!-- 							<li> -->
<!-- 								<h3>弹性伸缩</h3> -->
<!-- 								<p>多台云主机即开即用，灵活扩容，支持CPU、内存垂直升级，最大程度满足业务弹性需求</p> -->
<!-- 							</li> -->
						</ul>
					</div>
				</li>
				<li>
					<div class="product-brief">
						<div class="detail-head">
							<img src="img/brief-logo2.png" />
							<h3>云硬盘</h3>
						</div>
						<ul>
							<li>高可靠性</li>
							<li>灵活快速</li>
							<li>稳定使用</li>
						</ul>
					</div>
					<div class="product-details">
						<div class="detail-head">
							<h3>云硬盘</h3>
							<p>灵活变更扩展、安全、低成本、高可靠的特点。用户可以通过简单的鼠标点击，为业务云主机动态扩展存储容量，无需中断业务，减少运维成本，提升业务可靠性。</p>
							<a href="console/ebsApplyAction.action">购买</a>
						</div>
						<ul>
							<li>
								<h3>高可靠性</h3>
								<p>具有更高的数据可靠性，更高的I/O吞吐.</p>
							</li>
							<li>
								<h3>灵活快速</h3>
								<p>根据用户需求，有效预测业务负载的变化趋势，只能、快速的应对各种业务变化场景。</p>
							<li>
								<h3>稳定使用</h3>
								<p>提供实时监控工具，对发生故障的服务实例进行快速恢复和切换。</p>
							</li>
						</ul>
					</div>
				</li>
				<li>
					<div class="product-brief">
						<div class="detail-head">
							<img src="img/brief-logo3.png" />
							<h3>云网络</h3>
						</div>
						<ul>
							<li>灵活方便</li>
							<li>按需扩张</li>
							<li>高性能</li>
						</ul>
					</div>
					<div class="product-details">
						<div class="detail-head">
							<h3>云网络</h3>
							<p>利用云技术，向客户提供融合的、更好业务感知的网络服务及应用。</p>
							<a href="">购买</a>
						</div>
						<ul>
							<li>
								<h3>灵活方便</h3>
								<p>按需与云主机或弹性负载均衡器进行绑定和解绑</p>
							</li>
							<li>
								<h3>按需扩张</h3>
								<p>公网IP的带宽支持不同规格，用户可以按需进行扩容</p>
							</li>
							<li>
								<h3>高性能</h3>
								<p>每条带宽的误差不超过5%，网络可用性达99.99%</p>
							</li>
						</ul>
					</div>
				</li>
			</ul>
		</div>
	</section>
	<section class="solution">
		<div class="container">
			<div class="solution-title">
				<h2>精准定制，基于场景的行业解决方案</h2>
				<p>计算、存储、网络、安全、大数据、人工智能，普惠科技助您飞跃发展</p>
			</div>

			<ul>

				<li class="internet">
					<div>
						<img src="img/internet-icon.png" alt="" />
						<h4>教育行业解决方案</h4>
					</div>
					<div>
						<img src="img/internet-icon.png" alt="" />
						<h4>教育行业解决方案</h4>
						<a href="situation_education.jsp">查看详情</a>
					</div>
				</li>
				<li class="house">
					<div>
						<img src="img/house-icon.png" alt="" />
						<h4>政企云解决方案</h4>
					</div>
					<div>
						<img src="img/house-icon.png" alt="" />
						<h4>政企云解决方案</h4>
						<a href="situation_company.jsp">查看详情</a>
					</div>
				</li>
				<li class="business">
					<div>
						<img src="img/bussiness-icon.png" alt="" />
						<h4>医疗行业解决方案</h4>
					</div>
					<div>
						<img src="img/bussiness-icon.png" alt="" />
						<h4>医疗行业解决方案</h4>
						<a href="situation_medical.jsp">查看详情</a>
					</div>
				</li>
				<li class="finance">
					<div>
						<img src="img/finance-icon.png" alt="" />
						<h4>金融解决方案</h4>
					</div>
					<div>
						<img src="img/finance-icon.png" alt="" />
						<h4>金融解决方案</h4>
						<a href="situation_banking.jsp">查看详情</a>
					</div>
				</li>
			</ul>
		</div>
	</section>
	<section class="case">
		<img src="img/success-bg.png" alt="" />
		<div class="case-details">
			<h2>成功案例</h2>
			<ul>
				<li>四川省人民政府</li>
				<li>绵阳市教育局</li>
				<li>雅安市中医院</li>
				<li>成都胃病医院</li>
			</ul>
			<ul>
				<li><img src="img/bank-logo1.png" /><span>中国建设银行</span> <label>China
						Construction Bank</label></li>
				<li><img src="img/bank-logo2.png" />
					<div>
						<span>中移物联网</span> <label>China Mobile IOT</label>
					</div></li>
				<li></li>
				<li><img src="img/bank-logo3.png" /><span>中国邮政储蓄银行</span><label>SAVINGS
						BANK OF CHINA</label></li>
			</ul>
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
					法律声明<br />在您开始访问、浏览和使用本网站，请仔细阅读本声明的所有条款。一旦浏览、使用本网站，表示您已经同意接受本声明条款。<a
						href="">了解更多</a>
				</h3>
				<p1>中国移动通信集团四川有限公司.保留所有权利.|蜀ICP备09015892号-31</p1>
			</div>
		</div>
	</footer>
	<script src="js/pageswitch.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript">
		$("#container").PageSwitch({
			direction : "horizontal",
			loop : true,
			duration : 1000
		});

		$(".solution ul li").hover(function() {
			$(this).find("div:eq(1)").animate({
				top : '0'
			}, 400, 'linear');
			$(this).find("div:eq(0)").animate({
				top : '80%'
			}, 400, 'linear');
			$(this).find("div:eq(0)").css("opacity", 0);
			$(this).find("div:eq(1)").css("opacity", 1);
		}, function() {
			$(this).find("div:eq(0)").animate({
				top : '0'
			}, 400, 'linear');
			$(this).find("div:eq(1)").animate({
				top : '-80%'
			}, 400, 'linear');
			$(this).find("div:eq(1)").css("opacity", 0);
			$(this).find("div:eq(0)").css("opacity", 1);
		})

	$(function() {
		$('.user-info-tab').load('userInfoTab.jsp');
	});

	</script>
</body>

</html>

