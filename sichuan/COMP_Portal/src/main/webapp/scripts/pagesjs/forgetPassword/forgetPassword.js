$(function(){

	//设置页面展示高度 start
	var winHeight = $(window).height();
	var headerHeight = $("#header").height();
	var footHeight =$("#footer").height();
	$("#main").height(winHeight-headerHeight-footHeight-20);
	$("#login").height($("#main").height()-40);
	$("#detail_tab").height($("#login").height()-10);
	$(window).resize(function() {	
		$("#main").height(winHeight-headerHeight-footHeight-20);
		$("#login").height($("#main").height()-40);
		$("#detail_tab").height($("#login").height()-10);
	});
	//高度结束  end
	
	//显示验证码
	var hcode = document.getElementById('hcode');
	if(hcode!=null){
		hcode.src = "createVerifyImageServlet";
	}
	
	//展示
	$("#loginPwLi").css("color","#FF8C69");

});

function show() {//重新载入验证码
	var timenow = new Date().getTime();
	var hcode = document.getElementById('hcode');
    hcode.src = "createVerifyImageServlet?datetime=" + timenow;
    
    var hcode1 = document.getElementById('hcode1');
    hcode1.src = "createVerifyImageServlet?datetime=" + timenow;
}

//验证账户是否存在.
function validateUserId(){
	var flag = true;
	var userId = $("#userId").val();
	if(userId==""){
		/*$('#errParam').text("用户账号不能为空!");*/
		$.compMsg({
			type : 'error',
			msg : "账户不能为空，请重新输入！"
		});
	}else{
		$.ajax({
			type:"POST",
			async:false,
			url:"findPWValidateUserIdaction.action?struts.enableJSONValidation=true",
			data:{userId:userId},
			dataType:"JSON",
			success:function(data){
				if(data.errMsg != null && data.errMsg!= ""){
					$('#errParam').text(data.errMsg);
					/*$.compMsg({
						type : 'error',
						msg : data.errMsg
					});*/
					flag=false;
				}else{
					$("#errParam").text("");
					flag=true;
				}
			}
		});
	}
	return flag;
}

//验证验证码
function validateVerify(num){
	var flag = true;
	var verify;
	if(num == 1){
		verify = $("#verify").val();
	}else{
		verify = $("#verify2").val();
	}
	if(verify=="" || verify==null){
		if(num == 1){
			$('#errParam').text("验证码不能为空!");
			$.compMsg({
				type : 'error',
				msg : "验证码不能为空！"
			});
		}else{
			$("#errPassword").text("验证码不能为空!");
			$.compMsg({
				type : 'error',
				msg : "验证码不能为空！"
			});
		}
		flag =false;
	}else{
		$.ajax({
			type:"POST",
			async:false,
			url:"findPWValidateVerifyaction.action?struts.enableJSONValidation=true",
			data:{verify:verify},
			dateType:"JSON",
			success:function(data){
				if(data.errMsg != null && data.errMsg!= ""){
					if(num == 1){
						$('#errParam').text(data.errMsg);
						$.compMsg({
							type : 'error',
							msg : data.errMsg
						});
						show();
					}else{
						$("#errPassword").text(data.errMsg);
						$.compMsg({
							type : 'error',
							msg : data.errMsg
						});
					}
					flag=false;
				}else{
					$("#errParam").text("");
					$("#errPassword").text("");
					flag=true;
				}
			}
		});
	}
	return flag;
}
//验证下一步.
function nextStep(){
	var userId = $("#userId").val();
	var verify = $("#verify").val();
	if(userId=="" || userId==null){
		$("#errParam").text("账户不能为空!");
		/*return false;*/
		$.compMsg({
			type : 'error',
			msg : "账户不能为空！"
		});
		return false;
	}else if(verify=="" || verify==null){
		$("#errParam").text("验证码不能为空!");
		/*return false;*/
		$.compMsg({
			type : 'error',
			msg : "验证码不能为空！"
		});
		$('#verify').focus().select();
		return false;
	}
	if(validateVerify(1)&&validateUserId() ){
		$("#validateUserIdDiv").css("display","none");
		$("#validateQuestionDiv").css("display","block");
		$("#questionLi").css("color","#FF8C69");
		$("#loginPwLi").css("color","#555");
		//查询注册时使用的问题
		$.ajax({
			type:"POST",
			url:"findPWQueryUserQuestionAction.action?struts.enableJSONValidation=true",
			dataType:"JSON",
			data:{userId:userId},
			async:false,
			success:function(data){
				$("#questionSpan").text(data.userInfo.security_question);
				$("#correctAnswer").val(data.userInfo.security_answer);
			}
		});
	}
	show();
}

//密码重置按钮事件
function findPassword(){
	var correctAnswer = $("#correctAnswer").val();
	var answer = $("#answer").val();
	if(answer=="" || answer==null){
		$("#errQuestion").text("密保答案不能为空!");
		return false;
	}else if(correctAnswer!=answer){
		$("#errQuestion").text("密保答案错误，请重新输入!");
		return false;
	}
	$("#validateQuestionDiv").css("display","none");
	$("#resetPWDiv").css("display","block");
	$("#resetPwLi").css("color","#FF8C69");
	$("#questionLi").css("color","#555");
	$("#loginPwLi").css("color","#555");
	
	//显示验证码
	var hcode1 = document.getElementById('hcode1');
	var timenow = new Date().getTime();
	if(hcode1 !=null){
		hcode1.src = "createVerifyImageServlet?dateTime="+timenow;
	}
}

//验证用户输入的密码是否一致
function validatePassword(){
	var flag = false;
	var pw = $("#userPassword").val();
	var confirmPw=$("#confirmUserPassword").val();
	if(pw!="" && confirmPw!=""){
		if(pw!=confirmPw){
			$("#errPassword").text("两次输入密码不一致，请重新输入！");
			flag = false;
		}else{
			$("#errPassword").text("");
			flag = true;
		}
	}
	return flag;
}
//提交按钮
function submitform(){
	var pw = $("#userPassword").val();
	var confirmPw=$("#confirmUserPassword").val();
	if(pw=="" ||pw==null){
		$('#errPassword').text("新密码不能为空！");
		return false;
	}else if(confirmPw=="" ||confirmPw==null){
		$('#errPassword').text("确认密码不能为空！");
		return false;
	} 
	if(validatePassword() && validateVerify(2)){
		$.ajax({
			type:"POST",
			async:false,
			url:"resetUserPasswordAction.action",
			dataType:"JSON",
			data:{userId:$("#userId").val(),password:pw},
			success:function(data){
				if(data.errMsg != null && data.errMsg!= ""){
					$("#errEdit").text(data.errMsg);
				}
				$("#resetPWDiv").css("display","none");
				$("#editSuccessDiv").css("display","block");
				$("#resetPwLi").css("color","#555");
				$("#questionLi").css("color","#555");
				$("#loginPwLi").css("color","#555");
				var secs=3;
				for(var i=secs;i>=0;i--) { 
				 window.setTimeout('doUpdate(' + i + ')', (secs-i) * 1000); 
				} 
			}
		});
	}
}
function doUpdate(num) { 
	document.getElementById('skipDiv').innerHTML = '密码已重置,'+num+'秒后后自动跳转到登录页!' ; 
	if(num == 0) {
		login();
	} 
} 
//立即登录.	
function login(){
	window.location.href="login.jsp";
}
