<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<div class="self-config">
	<div class="config-title">
		<h2>修改密码</h2>
		<div id="message" style="display: none;">
			<span id="error" class="error"><s:actionerror /></span> <span
				class="error"><s:fielderror /></span> <span class="success"><s:actionmessage /></span>
		</div>
	</div>
	<s:form id='modifyPassword_form' method="post">
		<div class="config-content">
			<h3>密码信息</h3>

			<div>
				<span class="col-name">旧密码：</span><span class="col-content"><input
					type="password" id="oldPassword" name="user.oldPassword" maxLength="50"
					value="" autocomplete="off" />
					<s:hidden name="user.userId" /></span>
			</div>
			<div>
				<span class="col-name">新密码：</span><span class="col-content">
					<input type="password" id="password" name="user.password" maxLength="50" autocomplete="off"/>(密码至少8位，包含大小写字母、数字、特殊符号中的三种)</span> 
			</div>
			<div>
				<span class="col-name">确认密码：</span><span class="col-content">
					<input type="password" id="confirmPassword" name="user.comfirmPassword" maxLength="50" autocomplete="off"/>(密码至少8位，包含大小写字母、数字、特殊符号中的三种)</span>
			</div>
			<div class="submit">
				 <a class="btn fr" href="javascript:void(0);" onclick="editSubmit()"
					id="edit">保存</a>
				
			</div>
		</div>
	</s:form>
</div>
<script type="text/javascript" src="../scripts/lib/md5.js"></script>
<script>
	$(function() {
		//菜单显示当前，开发时删除
		$("#updatePwd").siblings().removeClass("active");
		$("#updatePwd").addClass("active");
	});

	function editSubmit() {
		var oldPassword = hex_md5($("#oldPassword").val());
		var password = $("#password").val();
		var confirmPassword = $("#confirmPassword").val();
		if (oldPassword == '') {
			$.compMsg({
				type : 'error',
				msg : '请输入旧密码!'
			});
			return;
		} else if (password == '') {
			$.compMsg({
				type : 'error',
				msg : '新密码不能为空,请正确输入!'
			});
			return;
		} else if(checkPass(password)<3) {
			$.compMsg({
				type : 'error',
				msg : '密码至少8位，包含大小写字母、数字、特殊符号中的三种,请正确输入!'
			});
			return;
		} else if (confirmPassword == '') {
			$.compMsg({
				type : 'error',
				msg : '确认密码不能为空,请正确输入!'
			});
			return;
		} else if (confirmPassword != password) {
			$.compMsg({
				type : 'error',
				msg : '新密码与确认密码不一致,请重新正确输入!'
			});
			return;
		} else {
			$("#modifyPassword_form").attr('action',
					'modifyPasswordUpdate.action');
			$("#oldPassword").val(oldPassword);
			$("#modifyPassword_form").submit();
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
	
</script>