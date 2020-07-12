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
<div class="page-form" id="user_edit_dialog" style="display: none;">
	<form action="userOperat.action" name="user_edit_form" id="user_edit_form" method="post">
		<div class="contentDetail">
    		<div class="result" style="margin-top:5px;">
    			<h5 class="label">
		            <span class="label-bg">基本信息</span>
		        </h5>
		        <ul class="result-list" style="padding-left: 54px;">
	    			<li>
	    				<span class="result-name">用户账号：</span>
	    				<span id="edit_userId"></span>
	    				<span id="edit_status"></span>
	    			</li>
    			</ul>
    			<ul class="result-list">
	                <li><span class="result-name"><font class="red-font">*</font>用户姓名：</span>
	                	<input type="text" size="25" name="user.userName" id="edit_userName" value="userName"
							class="text" maxlength="10"/>
	                </li>
	            </ul>
	            <ul class="result-list" style="width: 80%;padding-left: 54px;">
	            	<li>
	    				<span class="result-name">角色：</span>
		            	<div class="content">
							<div class="block_edit" style="margin-left: 91px;margin-top:-23px; padding-left: 5px; padding-right: 0; border-bottom: none; overflow:auto;overflow-x: hidden;">
								<ul class="edit_role" id="edit_user_add_role_ul">
									<%--<li class="add_role" id="add_user_add_role_button">添加</li> --%>
									<li class="add_role" id="edit_user_add_role_button" style="border:0;margin: 0;padding: 0;"></li>
								</ul>
							</div>
						</div>
		            </li>
	            </ul>
	            <ul class="result-list" style="width: 10%;">
	            	<li>
						<div class="bind_div" style="margin-top: -13px;">
							<span id="bind_role_edit">添加</span>
						</div>
		            </li>
	            </ul>
	            <ul class="result-list" style="width: 100%;padding-top: 10px;padding-left: 54px;">
	            	<li><span class="result-name">描述信息：</span><input type="text" size="25" name="user.desc" id="edit_desc" value="userName"
							class="text" maxlength="1024" style="width: 446px;"/></li>
	            </ul>
    		</div>
    		<div class="result" style="margin-top:5px;">
				<h5 class="label">
		            <span class="label-bg">联系信息</span>
		        </h5>
		        <ul class="result-list" style="padding-left: 54px;">
	            	<li><span class="result-name"><font class="red-font">*</font>手机号码：</span>
	            		<input type="text" size="25" name="user.mobile" id="edit_mobile" value="mobile"
							class="text" maxlength="14"/>
	            	</li>
	            	<li><span class="result-name"><font class="red-font">*</font>邮箱：</span>
	            		<input type="text" size="25" name="user.email" id="edit_email" value="2" class="text" maxlength="64"/>
	            	</li>
	            </ul>
	            <ul class="result-list">
	            	<li><span class="result-name">固定电话：</span>
	            		<input type="text" size="25" name="user.telphone" id="edit_telphone" value="email"
							class="text" maxlength="20"/>
	            	</li>
	            	<%-- <li><span class="result-name">传真号码：</span>
	            		<input type="text" size="25" name="user.fax" id="edit_fax" value="2" class="text" maxlength="20"/>
	            	</li>--%>
	            	<li><span class="result-name">部门名称：</span>
	            		<input type="text" size="25" name="user.departmentName" id="edit_departmentName" value="2"
							class="text" maxlength="50"/>
	            	</li>
	            </ul>
	            <ul class="result-list" style="width: 100%;padding-left: 54px;">
	            	<li><span class="result-name">通讯地址：</span>
	            		<input type="text" size="25" name="user.address" id="edit_address" value="2" class="text" maxlength="64" style="width: 446px;"/>
	            	</li>
	            </ul>
			</div>
			<div class="result" style="margin-top:5px;">
				<h5 class="label">
		            <span class="label-bg">业务组信息</span>
		        </h5>
		        
		        <ul class="result-list noeditUL" style="padding-left: 54px;">
	            	<li  >
	            		<span class="result-name"   >绑定的业务组：</span>
		            	<div style="width: 300px;">
		            		&nbsp;<span id="app_edit"></span>
		            	</div>
	                </li>
		        </ul>
		        <ul class="result-list noeditUL">    
		            <li>
	    				<span class="result-name">状态：</span>
		            	<span id="appStatus_edit"></span>
		            	<span id="showAppEdit_edit" style="cursor: pointer;color: #0066cc;">(查看全部)</span>
		            </li>
	            </ul>
	            
		        <ul class="result-list editUL" style="width: 80%;padding-left: 54px;">
		       		 <li><span class="result-name"   style="width: 90px;"><font class="red-font">*</font>绑定的业务组：</span>
		            	<div class="content">
							<div class="block_edit" style="margin-left: 91px;margin-top:-23px; padding-left: 5px; padding-right: 0; border-bottom: none; overflow:auto;overflow-x: hidden;">
								<ul class="edit_app" id="edit_user_add_app_ul">
									<%--<li class="add_app" id="edit_user_add_app_button">绑定业务组</li> --%>
									<li class="add_app" id="edit_user_add_app_button" style="border:0;margin: 0;padding: 0;"></li>
								</ul>
							</div>
						</div>
		             </li>
		             <!-- 
		             <li>
	            		<span class="result-name" style="float:none; margin-left: 72px; color: red;">该用户提交的业务组变更还未审批，变更后的所属业务组如下：</span><br/>
	            	</li>
	            	<li id="approvalDiv" style="display:none;margin-left: 70px;">
	            		<span class="result-name">所属业务组：</span>
	            		<div style="width: 700px;">
	            			&nbsp;<span id="approvalApp"></span>
	            		</div>
	            	</li>
	            	 -->
	            </ul>
	            <ul class="result-list editUL" style="width: 10%;">
		       		 <li>
		            	<div class="bind_div">
							<span id="bind_app_edit">绑定</span>
						</div>
		             </li>
	            </ul>
			</div>
       </div> 
	</form>
</div>

