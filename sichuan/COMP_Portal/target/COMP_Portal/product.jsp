<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="utf-8" />
		<title>产品</title>
		<link rel="stylesheet" type="text/css" href="styles/index.css" />
	</head>

	<body>
		<div class="product-banner">
			<div class="header">
				<img id="chinaMobile" src="images/chinamobile.png" />
				<a href="login.jsp"><img id="login" src="images/login.png" /></a>
				<ul class="header-nav">
					<li class="header-nav nav-item">
						<a href="home.jsp">首页</a>
					</li>
					<li class="header-nav nav-item active">
						<a class="active" href="product.jsp">产品介绍</a>
					</li>
					<li class="header-nav nav-item">
						<a href="situation.jsp">应用场景</a>
					</li>
					<li class="header-nav nav-item">
						<a href="console/resourcesOverviewAction.action">控制台</a>
					</li>
				</ul>
			</div>
		</div>
		<div class="product-container">
			<div class="content">
				<h2>云主机</h2>
				<p>云主机是在一组集群主机上虚拟出多个类似独立主机的部分，云服务器具有自助管理、数据安全保障、自动故障恢复和防网络攻击等高级功能，能够帮您简化开发部署过程，降低运维成本，构建按需扩展的应用，从而更适应快速多变的业务需求。</p>
				<a href="console/vmApplyAction.action" class="btn btn-primary">立即购买</a>
				<h4 style="margin-top:23px;"><img class="dot" src="images/product_14.png"/>快速部署</h4>
				<p>数秒内便可创建云主机并投入使用，提高业务部署及上线速度。</p>

				<h4><img class="dot" src="images/product_14.png"/>安全保障</h4>
				<p>提供密钥认证、安全组防护、防火墙防护、多用户隔离等手段，确保业务安全。</p>

				<h4><img class="dot" src="images/product_14.png"/>稳定可靠</h4>
				<p>云主机可用性不低于99.95%，提供宕机迁移、数据备份和恢复等功能，确保业务稳定。</p>

<!-- 				<h4><img class="dot" src="images/product_14.png"/>弹性伸缩</h4> -->
<!-- 				<p>多台云主机即开即用，灵活扩容，支持CPU、内存垂直升级，最大程度满足业务弹性需求。</p> -->

			</div>
			<div class="content-img">
				<img src="images/product_03.png" />
			</div>
		</div>
		<div class="product-container">
			<div class="content-img">
				<img src="images/product_07.png" />
			</div>
			<div class="content">
				<h2>云硬盘</h2>
				<p>云硬盘可以低成本提供大量的文件存储。并具有弹性变更扩展、安全、低成本、高可靠的特点。用户可以通过简单的鼠标点击，为业务云主机动态扩展存储容量，无需中断业务，减少运维成本，提升业务可靠性。</p>
				<a href="console/ebsApplyAction.action" class="btn btn-primary">立即购买</a>
				<h4 style="margin-top:23px;"><img class="dot" src="images/product_14.png"/>按需使用</h4>
				<p>根据用户的业务需求和策略，自动调整其弹性计算资源的管理服务。能够在业务增长时自动增加云主机实例，并在业务下降时自动减少实例。</p>

				<h4><img class="dot" src="images/product_14.png"/>灵活快速</h4>
				<p>根据用户需求，有效预测业务负载的变化趋势，只能、快速的应对各种业务变化场景。</p>

				<h4><img class="dot" src="images/product_14.png"/>稳定可靠</h4>
				<p>提供实时监控工具，对发生故障的服务实例进行快速恢复和切换。</p>

			</div>
		</div>
		<div class="product-container">
			<div class="content">
				<h2>云网络</h2>
				<p>云主机是在一组集群主机上虚拟出多个类似独立主机的部分，云服务器具有自助管理、数据安全保障、自动故障恢复和防网络攻击等高级功能，能够帮您简化开发部署过程，降低运维成本，构建按需扩展的应用，从而更适应快速多变的业务需求。</p>
				<a href="console/createVlanAction.action" class="btn btn-primary">立即购买</a>
				<h4 style="margin-top:23px;"><img class="dot" src="images/product_14.png"/>灵活方便</h4>
				<p>弹性公网IP可以按需与云主机或弹性负载均衡器进行绑定和解绑。</p>

				<h4><img class="dot" src="images/product_14.png"/>按需扩展</h4>
				<p>公网IP的带宽支持不同规格，用户可以按需进行扩容。</p>

				<h4><img class="dot" src="images/product_14.png"/>高性能</h4>
				<p>每条带宽的误差不超过5%，网络可用性达99.99%。</p>

			</div>
			<div class="content-img">
				<img src="images/product_11.png" />
			</div>
		</div>
		<footer>
			<p>版权所有 &copy中国移动通信集团四川有限公司</p>
		</footer>
	</body>

</html>