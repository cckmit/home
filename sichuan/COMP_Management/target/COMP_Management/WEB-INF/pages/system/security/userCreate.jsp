<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style>
<!--
.result{
	margin-left: 0;
}
.result-list .result-name{
	width: 89px;
}
.result-list {
    width: 41%;
}
-->
</style>
<!-- 创建用户表单 -->
<div class="page-form" id="user_create_dialog" style="display: none;">
	<form action="userCreate.action" name="user_create_form"
		id="user_create_form" method="post">
		<div class="contentDetail">
			<div class="result" style="margin-top:5px;">
    			<h5 class="label">
		            <span class="label-bg">基本信息</span>
		        </h5>
		        <ul class="result-list" style="padding-left: 54px;">
	    			<li>
	    				<span class="result-name"><font class="red-font">*</font>用户账号：</span>
	    				<input type="text" size="25" name="user.userId" id="add_userId"
						value="" class="text" maxlength="20"/>
	    			</li>
	    			<%--<li>
	    				<span class="result-name">立刻启用：</span>
		            	<input type="checkbox" name="userStatus" id="add_userStatus"
						value="0" class="text" checked="checked" />
		            </li> --%>
    			</ul>
    			<ul class="result-list">
	                <li><span class="result-name"><font class="red-font">*</font>用户姓名：</span>
	                	<input type="text" size="25" name="user.userName" id="add_userName"
						value="" class="text" maxlength="10"/>
	                </li>
	            </ul>
	            <ul class="result-list" style="width: 80%;padding-left: 54px;">
	            	<li>
	    				<span class="result-name">角色：</span>
		            	<div class="content">
							<div class="block_edit" style="margin-left: 91px;margin-top:-23px; padding-left: 5px; padding-right: 0; border-bottom: none; overflow:auto;overflow-x: hidden;">
								<ul class="edit_role" id="add_user_add_role_ul">
									<%--<li class="add_role" id="add_user_add_role_button">添加</li> --%>
									<li class="add_role" id="add_user_add_role_button" style="border:0;margin: 0;padding: 0;"></li>
								</ul>
							</div>
						</div>
		            </li>
	            </ul>
	            <ul class="result-list" style="width: 10%;">
	            	<li>
						<div class="bind_div">
							<span id="bind_role_add">添加</span>
						</div>
		            </li>
	            </ul>
	            <ul class="result-list" style="width: 100%;padding-top: 10px;padding-left: 54px;">
	            	<li><span class="result-name">描述信息：</span><input type="text" size="25" name="user.desc" id="add_desc" value=""
							class="text" maxlength="1024" style="width: 446px;"/></li>
	            </ul>
    		</div>
			<div class="result" style="margin-top:5px;">
				<h5 class="label">
		            <span class="label-bg">联系信息</span>
		        </h5>
		        <ul class="result-list" style="padding-left: 54px;">
	            	<li><span class="result-name"><font class="red-font">*</font>手机号码：</span>
	            		<input type="text" size="25" name="user.mobile" id="add_mobile"
						value="" class="text" maxlength="14"/>
	            	</li>
	            	<li><span class="result-name"><font class="red-font">*</font>邮箱：</span>
	            		<input type="text" size="25" name="user.email" id="add_email"
						value="" class="text" maxlength="64"/>
	            	</li>
	            </ul>
	            <ul class="result-list">
	            	<li><span class="result-name">固定电话：</span>
	            		<input type="text" size="25" name="user.telphone" id="add_telphone"
						value="" class="text" maxlength="20"/>
	            	</li>
	            	<%--<li><span class="result-name">传真号码：</span>
	            		<input type="text" size="25" name="user.fax" id="add_fax" value=""
						class="text" maxlength="20"/>
	            	</li> --%>
	            	<li><span class="result-name">部门名称：</span>
	            		<input type="text" size="25" name="user.departmentName"
						id="add_departmentName" value="" class="text" maxlength="50"/>
	            	</li>
	            </ul>
	            <ul class="result-list" style="width: 100%;padding-left: 54px;">
	            	<li><span class="result-name">通讯地址：</span>
	            		<input type="text" size="25" name="user.address" id="add_address" value="" class="text" maxlength="64" style="width: 446px;"/>
	            	</li>
	            </ul>
			</div>
			<div class="result" style="margin-top:5px;">
				<h5 class="label">
		            <span class="label-bg">业务组信息</span>
		        </h5>
		        <ul class="result-list" style="width: 80%;padding-left: 54px;">
		       		 <li><span class="result-name"   style="width: 90px;"><font class="red-font">*</font>绑定的业务组：</span>
		            	<div class="content">
							<div class="block_edit" style="margin-left: 91px;margin-top:-23px; padding-left: 5px; padding-right: 0; border-bottom: none; overflow:auto;overflow-x: hidden;">
								<ul class="edit_app" id="add_user_add_app_ul">
									<%--<li class="add_app" id="add_user_add_app_button">绑定业务组</li> --%>
									<li class="add_app" id="add_user_add_app_button" style="border:0;margin: 0;padding: 0;"></li>
								</ul>
							</div>
						</div>
		             </li>
	            </ul>
	            <ul class="result-list" style="width: 10%;">
	            	<li>
						<div class="bind_div" >
							<span id="bind_app_add">绑定</span>
						</div>
		            </li>
	            </ul>
	   
			</div>
		</div>
	</form>
</div>

