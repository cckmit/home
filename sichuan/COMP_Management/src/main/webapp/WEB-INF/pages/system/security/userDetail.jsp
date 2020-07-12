<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style>
<!--
.result{
	margin-left: 0;
}
.result-list {
    width: 41%;
}
-->
</style>
<!-- 用户详细信息 -->
<div class="page-form" id="user_detail" style="display: none;">
	<div class="contentDetail">
    		<div class="result" style="margin-top:5px;">
    			<h5 class="label">
		            <span class="label-bg">基本信息</span>
		        </h5>
		        <ul class="result-list" style="padding-left: 54px;">
	    			<li>
	    				<span class="result-name">用户账号：</span>
	    				<span id="userId"></span>
	    				<span id="status"></span>
	    			</li>
	    			<li>
	    				<span class="result-name">创建人：</span>
	    				<span id="createUserId"></span>
	    			</li>
    			</ul>
    			<ul class="result-list">
	                <li><span class="result-name">用户姓名：</span>
	                	<span id="userName"></span>
	                </li>
	            	<li>
	            		<span class="result-name">创建时间：</span>
	            		<span id="createTime"></span>
	            	</li>
	            </ul>
	            <ul class="result-list" style="width: 100%;padding-left: 54px;">
	            	<li>
	    				<span class="result-name">角色：</span>
	    				<span id="role"></span>&nbsp;
		            </li>
	            </ul>
	            <ul class="result-list" style="width: 100%;padding-left: 54px;">
	            	<li><span class="result-name">描述信息：</span>
		            	<span id="desc"></span>&nbsp;
		            </li>
	            </ul>
    		</div>
    		<div class="result" style="margin-top:5px;">
				<h5 class="label">
		            <span class="label-bg">联系信息</span>
		        </h5>
		        <ul class="result-list" style="padding-left: 54px;">
	            	<li><span class="result-name">手机号码：</span>
	            		<span id="mobile"></span>
	            	</li>
	            	<li><span class="result-name">邮箱：</span>
	            		<span id="email"></span>
	            	</li>
	            </ul>
	            <ul class="result-list">
	            	<li><span class="result-name">固定电话：</span>
	            		<span id="telphone"></span>&nbsp;
	            	</li>
	            	<%--<li><span class="result-name">传真号码：</span>
	            		<input type="text" size="25" id="fax" value="2" style="border: 1px;"
							readonly="readonly" />
	            	</li> --%>
	            	<li><span class="result-name">部门名称：</span>
	            		<span id="departmentName"></span>&nbsp;
	            	</li>
	            </ul>
	            <ul class="result-list" style="width: 100%;padding-left: 54px;">
	            	<li><span class="result-name">通讯地址：</span>
	            		<span id="address"></span>&nbsp;
	            	</li>
	            </ul>
			</div>
			<div class="result" style="margin-top:5px;">
				<h5 class="label">
		            <span class="label-bg">业务组信息</span>
		        </h5>
		        <ul class="result-list" style="padding-left: 54px;">
	            	<li>
	            		<span class="result-name"  style="width: 84px;">绑定的业务组：</span>
		            	<div style="width: 300px;">
		            		<span id="app"></span>&nbsp;
		            	</div>
	                </li>
		        </ul>
		        <ul class="result-list">    
		            <li>
	    				<span class="result-name">状态：</span>
		            	<span id="appStatus"></span>
		            	<span id="showAppEdit" style="cursor: pointer;color: #0066cc;">(查看全部)</span>
		            </li>
	            </ul>
			</div>
			<div id="auditDiv" class="result" style="margin-top:5px; display: none;">  
				<h5 class="label">
		            <span class="label-bg">审批意见</span>
		        </h5>	
		        <ul class="result-list" style="padding-left: 54px;">
		        	<li><span class="result-name">审批意见：</span>
		        		<div style="width: 700px;">
		            		<span id="auditInfo"></span>&nbsp;
		            	</div>
		        	</li>
		        </ul>    
     		</div>
       </div>  
</div>
