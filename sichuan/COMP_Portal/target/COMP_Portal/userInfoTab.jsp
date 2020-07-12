<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<style type="text/css">
.user-info-tab {
	position: absolute;
	width: 250px;
	height: 150px;
	background-color: #2C3E50;
	right: 0;
	top: 80px;
	color: #fff;
	opacity: .8;
	border-left: 1px solid #F2F2F2;
	border-right: 1px solid #F2F2F2;
	border-bottom: 1px solid #F2F2F2;
}

.user-info-tab div.user-info {
	width: 100%;
	height: 100px;
	float: left;
	position: relative;
	padding: 40px 5px 15px 80px;
	box-sizing: border-box;
	background-color: #051931;
	text-align: left;
}

.user-info-tab div.user-info img {
	position: absolute;
	border-radius: 50%;
	height: 60px;
	width: 60px;
	top: 50%;
	left: 15px;
	margin-top: -30px;
}

.user-info-tab div.user-info p {
	text-align: left;
	font-size: 16px;
	line-height: 24px;
}

.user-info-tab a {
	display: inline-block;
	width: 250px;
	height: 50px;
	line-height: 50px;
	text-align: center;
	text-decoration: none;
	float: left;
	box-sizing: box-border;
	font-size: 14px;
}

.right-border {
	border-right: 1px solid #BFBFBF;
	margin-left: -1px;
}
</style>

<div class="user-info">
	<img src="${pageContext.request.contextPath}/img/head.png" />
	<p>
		用户名：<span><s:property value='#session.userInfo.userName' /></span>
	</p>
</div>
<!-- <a href="logout.action" class="right-border">修改密码</a> -->
<a href="logout.action" class="">退出</a>

<script type="text/javascript">
	var userTabIn = false;
	$("header ul.login a.user-head").click(function() {
		$('.user-info-tab').slideDown('slow');
	});

	$("header ul.login a.user-head").mouseleave(function() {
		setTimeout(function(){
			if(!userTabIn){
				$('.user-info-tab').slideUp('slow');
			}
		},1000);
	});
	$("div.user-info-tab").mouseleave(function() {
		$('.user-info-tab').slideUp('slow');
	});
	$("div.user-info-tab").mouseenter(function() {
		userTabIn=true;
	});

	
</script>