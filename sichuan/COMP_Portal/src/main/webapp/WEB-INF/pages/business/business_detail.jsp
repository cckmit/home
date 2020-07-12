<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<head>

</head>
<body>

	<!--右侧-->
	<div id="right">
		<h1>业务管理</h1>
		<div class="details-con">
			<div class="detail-title">
				<a href="businessList.action" title="返回"> <span class="back"></span>
				</a>
				<h3 class="apply-title">业务详情</h3>
			</div>
			<!-- form -->
			<div class="page-form">
				<div class="section">
					<div class="field">
						<div class="caption">业务名称：</div>
						<div class="content">
							<strong><s:property value="businessInfo.name" /></strong>
						</div>
					</div>
					<div class="field">
						<div class="caption">业务描述：</div>
						<div class="content">
							<strong><s:property value="businessInfo.description" /></strong>
						</div>
					</div>
					<div class="field">
						<div class="caption">主机资源：</div>
						<div class="content">
							<strong>
								<ul id="hostId" class="ulList">
									<s:iterator value="businessInfo.resourceList" id="res">
										<li><s:property value="name" /></li>
									</s:iterator>
								</ul>
							</strong>
						</div>
					</div>
				</div>
				<div class="page-button">
					<ul class="opt-bottom-btn">
						<li><a href="businessList.action">返回</a></li>
					</ul>
				</div>
			</div>
		</div>
		<!-- form end -->
	</div>

	<script type="text/javascript"
		src="../scripts/pagesjs/business/business_detail.js"></script>
</body>