<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>注册</title>
<link rel="stylesheet" type="text/css" href="css/zg.css" />
<script src="js/jquery-1.12.4.js" type="text/javascript" charset="utf-8"></script>
<script src="js/browserDetect.js" type="text/javascript" charset="utf-8"></script>
<script src="scripts/common.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript"
	src="scripts/lib/dialog/jquery.artDialog.js?skin=default"></script>

<script type="text/javascript">
	if (BrowserDetect.version <= 8 && BrowserDetect.browser == "Explorer") {
		window.location.href = "404.html";
	}
</script>
</head>

<body>
	<header class=" header-bgcolor bottom-border">
		<div>
			<a class="logo-link " href="home.jsp"><img width="155px "
				height="50px " class="logo " src="img/logo.png " alt=" " /><span>四川政企云服务</span></a>
			<nav>

				<ul class="login ">
					<li class="login-btn "><a href="login.jsp">登录</a></li>
				</ul>
			</nav>
		</div>
	</header>
	<main>
	<section class="reg-form dark-section pt80 ">
		<div class="reg-title ">
			<h2>注册用户</h2>
		</div>
		<s:form id="formSubmit" name="formSubmit" method="post"
			action="register.action">
			<div class="content ">
				<h3>基本信息</h3>
				<div>
					<div class="fl"><span style="color:red">*</span>用户账号：
						<input id="userId" name="user.userId" type="text"
							placeholder="用户账号" autocomplete="off" />
					</div>
					<div class="fr"><span style="color:red">*</span>用户姓名：
						<input id="userName" name="user.userName" type="text"
							placeholder="用户姓名" autocomplete="off" />
					</div>
					<div class="fl"><span style="color:red">*</span>用户密码：
						<input autocomplete="off" id="password" name="user.password" type="password" placeholder="用户密码" />
					</div>
					<div class="fr">确认密码：
						<input autocomplete="off" id="confirmPassword" name="user.confirmPassword" type="password" placeholder="确认密码" autocomplete="off" />
					</div>
					<div class="full">&nbsp;描述信息：
						<input id="desc" name="user.desc" type="text" placeholder="描述信息" autocomplete="off" />
					</div>
					<div style="clear: both; padding: 0;"></div>
				</div>
			</div>
			<div class="content ">
				<h3>联系信息</h3>
				<div>
					<div class="fl"><span style="color:red">*</span>手机号码：
						<input id="mobile" name="user.mobile" type="text"
							placeholder="手机号码" autocomplete="off"/>
					</div>
					<div class="fr">固定电话：
						<input id="telphone" name="user.telphone" type="text"
							placeholder="固定电话" autocomplete="off"/>
					</div>
					<div class="fl">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color:red">*</span>邮箱：
						<input id="email" name="user.email" type="text" placeholder="邮箱" autocomplete="off"/>
					</div>
					<div class="fr">部门名称：
						<input id="departmentName" name="user.departmentName" type="text"
							placeholder="部门名称" class="fr" autocomplete="off"/>
					</div>
					<div class="full">&nbsp;通讯地址：
						<input id="address" name="user.address" type="text"
							placeholder="通讯地址" autocomplete="off"/>
					</div>
				</div>
				<div style="clear: both; padding: 0;"></div>
			</div>
			<div class="content">
				<h3>密保问题</h3>
				<div>
					<div class="fl"><span style="color:red">*</span>密码问题：
						<input id="security_question" name="user.security_question"
							type="text" placeholder="密码问题  ：例如您的生日" autocomplete="off"/>
					</div>
					<div class="fr"><span style="color:red">*</span>密码答案：
						<input id="security_answer" name="user.security_answer"
							type="text" placeholder="密码答案" autocomplete="off"/>
					</div>
				</div>
				<div style="clear: both; padding: 0;"></div>
			</div>
			<div class="agree-content">
				<input id='read' type="checkbox" class="agree-btn" value="" /><span>我已经阅读并同意《自服务门户服务条款》</span>
				<a href="javascript:;" onclick="showAgreement()">阅读全文</a>
			</div>
			<a id="btnRegister" class="reg-submit" href="javascript:void(0);">提
				交</a>
		</s:form>
	</section>

	</main>
	<footer>
		<div class="container ">
			<div class="link ">
				<div class="call-center ">
					<img src="img/center.png " alt=" " />
					<h3>
						售前咨询电话<br />4001099199
					</h3>
					<h3 class="worktime ">工作时间</h3>
					<p>工作日 8:30～17：30</p>
				</div>
				<div class="linkdoc ">
					<dl>
						<dt>
							<img src="img/help.png " />新手指南
						</dt>
						<dd>
							<ul>
								<li><a href=" ">注册/登录</a></li>
								<li><a href=" ">常见问题</a></li>
								<li><a href=" ">了解产品</a></li>
							</ul>
						</dd>
					</dl>
					<dl>
						<dt>
							<img src="img/about.png " />用户相关
						</dt>
						<dd>
							<ul>
								<li><a href=" ">控制台</a></li>
								<li><a href=" ">订单管理</a></li>
								<li><a href=" ">ICP备案</a></li>
							</ul>
						</dd>
					</dl>
					<dl>
						<dt>
							<img src="img/partner.png " />合作伙伴
						</dt>
						<dd>
							<ul>
								<li><a href=" ">合作伙伴登录</a></li>
								<li><a href=" ">申请加入</a></li>
								<li><a href=" ">合作流程</a></li>
								<li><a href=" ">合作条件</a></li>
							</ul>
						</dd>
					</dl>
					<dl>
						<dt>
							<img src="img/support.png " />支持与帮助
						</dt>
						<dd>
							<ul>
								<li><a href=" ">平台使用手册</a></li>
								<li><a href=" ">人工在线帮助</a></li>
								<li><a href=" ">常见问题</a></li>
								<li><a href=" ">通知公告</a></li>
							</ul>
						</dd>
					</dl>
				</div>

			</div>
			<div class="warning ">
				<h3>
					法律声明<br />再您开始访问，浏览和使用本网站，请仔细阅读本声明的所有条款。一旦浏览，使用本网站表示您已经同意接受本声明条款。<a
						href=" ">了解更多</a>
				</h3>
				<p1>中国移动通信集团四川有限公司.保留所有权利.|蜀ICP备09015892号-31</p1>
			</div>
		</div>
	</footer>

	<div id="serviceRule"
		style="display: none; width: auto; height: auto; line-height: 20px;">
		<table>
			<tr>
				<td align="center"><span
					style="font-size: 14px; font-weight: bold; text-align: center"><s:property
							value="getText('regedit.developer.protocol')" escape="false" /></span></td>
			</tr>
			<tr>
				<td style="font-size: 12px;">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;本服务条款是四川政企云网站的经营者中国移动通信集团四川有限公司，与用户共同缔结的对双方具有约束力的有效契约。<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;为更好地为您提供服务，中国移动将遵循“合法、正当、必要”原则，按照法律法规的规定和您的同意收集您的个人信息，主要包括：<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;一、您提供的信息：您注册成为我们的用户或使用我们的服务时，向我们提供的相关个人信息，包括您的姓名（必填）、手机号码（必填）、邮箱（必填）、固定电话、部门名称和通信地址。<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;二、在使用我们提供的服务过程中，除了您的注册信息，不会收集其它用户信息。<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;三、您的个人信息，我们将通过技术手段对数据进行去标识化处理，请您了解并同意，在此情况下我们有权使用已经去标识化的信息；并在符合相关法律法规的前提下，我们有权对包括您的个人信息在内的用户数据库进行整体化分析和利用。<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;四、我们可能将收集到的您的个人信息用于以下目的：<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1、在我们提供服务时，用于身份验证、客户服务、安全防范、诈骗监测、存档和备份用途，确保我们向您提供的产品和服务的安全性。<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2、通过信息数据分析，帮助我们设计新服务，改善我们现有服务。<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3、使我们更加了解您如何使用我们的服务，从而针对性地满足您的个性化需求。<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5、其他您授权同意提供个人信息的情况。<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;五、当我们要将信息用于本政策未载明的其它用途时，会事先征求您的同意。<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;六、您充分知晓，以下情形中我们使用个人信息无需征得您的授权同意：<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1、与国家安全、国防安全有关的。<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2、与公共安全、公共卫生、重大公共利益有关的。<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3、与犯罪侦查、起诉、审判和判决执行等有关的。<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4、出于维护个人信息主体或其他个人的生命、财产等重大合法权益但又很难得到本人同意的。<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5、所收集的个人信息是个人信息主体自行向社会公众公开的。<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;6、从合法公开披露的信息中收集的您的个人信息的，如合法的新闻报道、政府信息公开等渠道。<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;7、根据您的要求签订合同所必需的。<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;8、用于维护所提供的产品与/或服务的安全稳定运行所必需的，例如发现、处置产品与/或服务的故障。<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;9、为合法的新闻报道所必需的。<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;10、学术研究机构基于公共利益开展统计或学术研究所必要，且对外提供学术研究或描述的结果时，对结果中所包含的个人信息进行去标识化处理的。<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;11、法律法规规定的其他情形。<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;为切实加强移动云接入客户的安全使用和规范管理，促进互联网信息服务活动健康有序发展，维护国家安全和社会稳定，保障社会公众利益和公民合法权益，保障其它客户的合法权益， 中国移动通信集团公司及中国移动通信有限公司（下文统称中国移动）根据《中华人民共和国计算机信息系统安全保护条例》、 《中华人民共和国计算机信息网络国际联网管理暂行规定》、《计算机信息网络国际联网安全保护管理办法》和其他相关法律、行政法规的规定， 用户申请、使用云平台服务时需承诺如下内容：<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;一、遵守国家有关法律法规，提供的信息符合国家法律、法规与工业和信息化部等政府部门的各项管理规定以及中国移动《IDC信息安全管理规范》的相关规定，并严格执行上述信息安全管理规定。<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;二、不得利用中国移动互联网（CMNET）从事危害国家安全、泄露国家机密等违法犯罪活动，不得利用中国移动互联网（CMNET）制作、查阅、复制和传播违反宪法和法律、妨碍社会治安、破坏国家统一、 破坏民族团结、色情、暴力等的信息，不得利用中国移动互联网（CMNET）发布任何含有下列内容之一的信息：<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1、危害国家安全、泄露国家秘密，侵犯国家的、社会的、集体的荣誉、利益和公民的合法权益，从事违法犯罪活动。     <br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2、煽动抗拒、破坏宪法和法律、行政法规实施的。      <br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3、煽动颠覆国家政权，推翻社会主义制度的。 <br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4、煽动分裂国家、破坏国家统一的。<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5、煽动民族仇恨、民族歧视，破坏民族团结的。 <br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;6、捏造或者歪曲事实，散布谣言，扰乱社会秩序的。     <br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;7、破坏国家宗教政策，宣扬邪教、封建迷信、淫秽、色情、赌博、暴力、凶杀、恐怖，教唆犯罪的。<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;8、公然侮辱他人或者捏造事实诽谤他人的。<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;9、损害国家机关信誉的。<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;10、其他违反宪法和法律、行政法规的。<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;11、侵犯他人知识产权，传播虚假信息的。<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;12、违背社会主义精神文明建设要求、违背中华民族优良文化传统与习惯、以及其它违背社会公德的各类低俗信息。<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;13、工业和信息化部、公安部、工商总局、国家互联网信息办公室等国家职能机构、部门禁止发布的各类信息。<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;三、不得利用中国移动互联网（CMNET）从事以下破坏计算机系统及互联网安全的行为：<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1、网络攻击行为<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1）利用自己或他人的机器设备，未经他人允许，通过非法手段取得他人机器设备的控制权；<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2）非授权访问、窃取、篡改、滥用他人机器设备上的信息，对他人机器设备功能进行删除、修改或者增加；<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3）向其他机器设备发送大量信息包，干扰其他机器设备的正常运行甚至无法工作；或引起网络流量大幅度增加，造成网络拥塞，而损害他人利益的行为     <br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4）资源被利用进行网络攻击的行为或由于机器设备被计算机病毒侵染而造成攻击等一切攻击行为。<br>
					
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2、淫秽色情信息危害行为<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1）在网站或业务平台等相关系统发布、传播淫秽色情和低俗信息的行为。     <br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2）网站或业务平台引入、分发其他的网站、应用等内容，由于管理、手段缺失，而导致引入、分发内容包含色情、低俗等不良信息的行为。<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3、计算机病毒危害行为<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1）有意通过互联网络传播计算机病毒。<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2）因感染计算机病毒进而影响网络和其它客户正常使用的行为。<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4、发送或协助发送垃圾邮件行为<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1）故意向未主动请求的客户发送或中转电子邮件广告刊物或其他资料。<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2）故意发送没有明确的退信方法、发信人、回信地址等的邮件。<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3）邮件服务器存在安全漏洞或开放OPENRELAY功能导致被利用转发垃圾邮件。<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4）冒名发送恶意邮件（违反国家法律法规的）。 <br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5）其它任何协助垃圾邮件发送行为、可能会导致影响网络正常运营及其它客户正常使用的邮件等。<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5、手机恶意软件危害行为<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1）服务器作为手机应用软件下载源所提供的下载列表中含有违规恶意软件，或者直接作为手机恶意软件的下载源，通过互联网传播手机恶意软件。     <br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2）作为手机恶意软件控制服务器，对中毒用户手机进行远程控制，进行下发远程指令、收集用户隐私、不知情定制业务造成扣费等恶意行为。<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;中国移动通信集团四川有限公司作为云服务经营者，同时负有监管责任，具有以下管理权力：<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;一、中国移动发现上述违法犯罪活动和有害信息，将立即采取措施制止并及时向有关主管部门报告。对于其它破坏中国移动网络信息安全或违反国家相关法律法规规定的行为，本服务条款同样适用。<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;二、中国移动有权在中国移动接入的业务网站、即时通信工具、网盘、视频等进行全面检查、逐一过滤，彻底清理涉暴恐音视频等存量有害信息，关闭传播恐怖宣传视频的注册账户，并建立清理暴恐音视频工作台账。<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;三、中国移动采取必要手段加强网站/平台内容监测、审核、拦截，一经发现涉暴恐音视频等有害信息，立即清理，并及时上报国家相关部门。<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;四、中国移动作为电信运营商，有按要求配合国家有关部门处理互联网网络与信息安全工作的义务，包括但不限于提供客户基础资料、采取技术措施处理等。<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;五、“依据《非经营性互联网备案管理办法》第二十三条规定，如备案信息不真实，将关闭网站并注销备案。入驻客户承诺并确认：提交的所有备案信息真实有效， 当备案信息发生变化时（包含在二级域名的使用过程中，对应的一级域名发生变更或取消接入等）请及时与中国移动备案部门联系并提交更新信息， 如因未及时更新而导致备案信息不准确，我公司有权依法对接入网站进行关闭处理。<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;六、如有其它影响网络安全和信息安全的突发事件，中国移动有权采取紧急措施，以保证网络安全。<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;本服务条款之效力、解释、变更、执行与争议解决均适用中华人民共和国法律。因本服务条款产生之争议，均应依照中华人民共和国法律予以处理。<br>
				</td>
			</tr>
		</table>
	</div>

	<div class="page-form" id="securityQuestionDIV" style="display: none;">
		<div class="field">注册申请已提交，请等待审批！</div>
	</div>
	<!--[if lte IE 9]>
		<script src="js/jquery.placeholder.js" type="text/javascript" charset="utf-8"></script>
		<script>
				$(function() {
				$('input').placeholder();
			});
		</script>
		<![endif]-->
	<script type="text/javascript ">

function showAgreement(){
$.dialog({
		title : '自服务门户服务条款',
		content : document.getElementById('serviceRule'),
		ok : function() {
			
			
			return true;
		},
		cancel : false,
		lock : true
	});

}



			$('.required input').keydown(function() {
				$(this).parent().removeClass('star');
			});
			$('.content input[type=text]').blur(function() {
				if($(this).val().trim() == "") {
					$(this).val("");
					if($(this).parent().hasClass('required')) {
						$(this).parent().addClass('star');
					}
				}
			});

		//点击确认申请
$('#btnRegister').click(function(){
var isRead=$('#read').prop('checked');
if(!isRead){
$.compMsg({
					type : 'warn',
					msg : "请阅读并同意《自服务门户服务条款》！"
				});
return ;
}
var f9 = validateSecurity();
	var f8 = validateRead();
	var f6 = validateEmail();
	var f5 = validateMobile();
	var f4 = validateConfirmPassword();
	var f3 = validatePassword();
	var f2 = validateUserName();
	var f1 = validateUserId();
	if (f1 && f2 && f3 && f4 && f5 && f6  && f8 && f9) {
		var f10 = validateRegisterUserId();
		if(f10){
			var appIdStr = "";
		    $('input[name="appIds"]').each(function() {
		    	appIdStr += $(this).val()+",";
		    });
			var userObj = {
				userId : $('#userId').val(),
				userName : $('#userName').val(),
				password : $('#password').val(),
				mobile : $('#mobile').val(),
				email : $('#email').val(),
				address : $('#address').val(),
				telphone : $('#telphone').val(),
				fax : "",//$('#fax').val(),
				desc : $('#desc').val(),
				departmentName : $('#departmentName').val(),
				security_question : $('#security_question').val(),
				security_answer : $('#security_answer').val()
			};
			var da_val = {
				userObj : JSON.stringify(userObj)
			};
			$.ajax( {
				type : "POST",
				url : 'register.action',
				data : da_val,
				dataType : "JSON",
				cache : false,
				success : function(data) {
					if(data.resultPath=="error"){
						$.compMsg( {
							type : 'error',
							msg : "注册申请提交失败！"
						});
						return;
					}
					$.dialog({
						title: "申请结果",
						content: document.getElementById('securityQuestionDIV'),
						init:function(){ },
						button:[{
							id:'notpass',
				            name: '确定',
				            callback: function () {},
							focus: true
						},{
							id:'notpass',
				            name: '返回首页',
				            callback: function () {
				            	window.location.href = 'home.jsp';
							}
						}],
						cancelVal: "确定",
						cancel:true,
						lock: true
					});
				}
			});
		}else {
			return false;
		}
	} else {
		return false;
	}

});

function validateRead() {//服务条款
	var checked = $('#read').is(':checked');
	if(checked){
		return true;
	};
	//$('#errParam').text("请阅读自服务门户服务条款！");
	$.compMsg( {
		type : 'error',
		msg : "请阅读自服务门户服务条款！"
	});
	return false;
	
}

function validateUserId() {//用户账号
	var userId = $('#userId').val();
	var rexName = /[0-9a-zA-Z_]/;
	if(null == userId || userId==""){
		//$('#errParam').text("用户账号不能为空，请重新输入！");
		$.compMsg( {
			type : 'error',
			msg : "用户账号不能为空，请重新输入！"
		});
		return false;
	} else if(!rexName.test(userId)){
		//$('#errParam').text("用户账号只能由数字字母下划线组成！");
		$.compMsg( {
			type : 'error',
			msg : "用户账号只能由数字字母下划线组成，请重新输入！"
		});
		return false;
	} else {
		return true;
	}
}
function validateUserName() {//用户姓名
	var userName = $('#userName').val();
	if (null == userName || userName=="") {
		//$('#errParam').text("用户姓名不能为空，请重新输入！");
		$.compMsg( {
			type : 'error',
			msg : "用户姓名不能为空，请重新输入！"
		});
		return false;
	} else {
		return true;
	}
}
function validatePassword() {//密码
	var password = $('#password').val();
	if (null == password || password=="") {
		//$('#errParam').text("密码不能为空，请重新输入！");
		$.compMsg( {
			
			type : 'error',
			msg : "密码不能为空，请重新输入！"
		});
		return false;
	} else if(checkPass(password)<3) {
			$.compMsg({
				type : 'error',
				msg : '密码至少8位，包含大小写字母、数字、特殊符号中的三种,请正确输入!'
			});
			return;
	} else {
		return true;
	}
}
function validateConfirmPassword() {//确认密码
	var password = $('#password').val();
	var confirmPassword = $('#confirmPassword').val();
	if (null == confirmPassword || confirmPassword=="") {
		//$('#errParam').text("确认密码不能为空，请重新输入！");
		$.compMsg( {
			type : 'error',
			msg : "确认密码不能为空，请重新输入！"
		});
		return false;
	} else {
		if (password != confirmPassword) {
			//$('#errParam').text("两次输入密码不一致，请重新输入！");
			$.compMsg( {
				type : 'error',
				msg : "两次输入密码不一致，请重新输入！"
			});
			return false;
		} else {
			return true;
		}
	}
}
function checkPass(s){
		if(s.length < 8){
            return 0;
  		}
		var ls = 0;
		if(s.match(/([a-z])+/)){
		    ls++;
		}
		if(s.match(/([0-9])+/)){
		    ls++; 
		}
		if(s.match(/([A-Z])+/)){
		    ls++;
		}
		if(s.match(/[^a-zA-Z0-9]+/)){
		    ls++;
		}
		return ls;
	}
function validateMobile() {//手机号码
	var mobile = $('#mobile').val();
	var rexNum = /^[0-9]*[1-9][0-9]*$/;
	if (null == mobile || mobile=="") {
		//$('#errParam').text("手机号码不能为空，请重新输入！");
		$.compMsg( {
			type : 'error',
			msg : "手机号码不能为空，请重新输入！"
		});
		return false;
	} else if(!rexNum.test(mobile)){
		//$('#errParam').text("手机号码只能为数字！");
		$.compMsg( {
			type : 'error',
			msg : "手机号码只能为数字，请重新输入！"
		});
		return false;
	} else if($.trim(mobile).length != 11){
		//$('#errParam').text("手机号码长度错误！");
		$.compMsg( {
			type : 'error',
			msg : "手机号码长度错误，请重新输入！"
		});
		return false;
	} else {
		return true;
	}
}
function validateEmail() {//邮箱
	var email = $('#email').val();
	var rexMail = /^([a-zA-Z0-9\\._-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
	if (null == email || email=="") {
		//$('#errParam').text("邮箱不能为空，请重新输入！");
		$.compMsg( {
			type : 'error',
			msg : "邮箱不能为空，请重新输入！"
		});
	} else if(!rexMail.test(email)){
		//$('#errParam').text("邮箱格式不正确！");
		$.compMsg( {
			type : 'error',
			msg : "邮箱格式不正确，请重新输入！"
		});
	} else {
		return true;
	}
}
function validateSecurity() {//密保问题和答案
	var question = $('#security_question').val();
	var answer = $('#security_answer').val();

	if(null == question || question=="" || question=="例如：您的生日是？"){
		//$('#errParam').text("密保问题不能为空，请重新输入！");
		$.compMsg( {
			type : 'error',
			msg : "密保问题不能为空，请重新输入！"
		});
		return false;
	}
	if(null == answer || answer==""){
		//$('#errParam').text("答案不能为空，请重新输入！");
		$.compMsg( {
			type : 'error',
			msg : "密保答案不能为空，请重新输入！"
		});
		return false;
	} 
	return true;
}
function validateRegisterUserId(){//用户账号是否唯一
	var userId = $('#userId').val();
	var mobile = $('#mobile').val();
	var email = $('#email').val();
	var flag = true;
	$.ajax({
		type : "POST",
		async:false,
		url : 'registerValidateUserIdAction.action?struts.enableJSONValidation=true',
		data : { 
			userId : userId, 
			mobile : mobile,
			email : email,
			isCheckId : "true"
		},
		dataType : "JSON",
		cache : false,
		success : function(data) {
			if(data.errMsg != null && data.errMsg!= ""){
				//$('#errParam').text(data.errMsg);
				$.compMsg( {
					type : 'error',
					msg : data.errMsg
				});
				flag = false;
			}
			
		}
    });
	if(flag){
		return true;
	}else{
		return false;
	}
};


</script>

</body>

</html>