<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>


<div class="self-config">
	<div class="config-title">
		<h2>用户信息</h2>
		<div id="message" style="display: none;">
			<span id="error" class="error"><s:actionerror /></span> <span
				class="error"><s:fielderror /></span> <span class="success"><s:actionmessage /></span>
		</div>
	</div>
	<div class="btns">
		<a class="apply" href="javascript:;" onclick="edit()" id="edit"><img
			src="../img/ico-apply.png" alt="" />修改用户信息</a>
	</div>
	<div style="clear:both;"></div>
	<s:form id="details_form" method="post">
		<div class="config-content">
			<h3>基本信息</h3>
			<div>
				<span class="col-name">用户账号：</span>
				<div class="col-content">
					<s:property value="user.userId" />
				</div>
			</div>
			<div>
				<span class="col-name">创建人：</span><span class="col-content"><s:property
						value="user.createUserId" /></span>
			</div>
			<div>
				<span class="col-name">用户姓名：</span><span class="col-content"><s:property
						value="user.userName" /> </span>
			</div>
			<div>
				<span class="col-name">创建时间：</span><span class="col-content"><s:property
						value="user.createTime" /> </span>
			</div>
			<div>
				<span class="col-name">描述信息：</span><span class="col-content"><s:property
						value="user.desc" /> </span>
			</div>
		</div>

		<div class="config-content">
			<h3>联系信息</h3>

			<div>
				<span class="col-name">手机号码：</span><span class="col-content"><s:property
						value="user.mobile" /></span>
			</div>
			<div>
				<span class="col-name">邮箱：</span><span class="col-content"><s:property
						value="user.email" /></span>
			</div>
			<div>
				<span class="col-name">固定电话：</span><span class="col-content"><s:property
						value="user.telphone" /></span>
			</div>
			<div>
				<span class="col-name">部门名称：</span><span class="col-content"><s:property
						value="user.departmentName" /></span>
			</div>
			<div>
				<span class="col-name">通讯地址：</span><span class="col-content"><s:property
						value="user.address" /></span>
			</div>
		</div>

		<div class="config-content">
			<h3>企业客户信息</h3>

			<div>
				<span class="col-name">绑定的企业客户：</span>
				<div class="col-content">
					<span id="app"></span></span>
				</div>
				<div>
					<span class="col-name">状态：</span><span class="col-content"><span
						id="appStatus"></span> <span id="showAppEdit"
						style="cursor: pointer; color: #0066cc;">(查看全部)</span></span>
				</div>

			</div>
		
	</s:form>
</div>


<script>
	var appAry = new Array();
	var approvalApps = new Array();
	var index = 0;
	var indexNew = 0;
	var approvalFlag = false;
	<s:iterator value="user.apps" status="apps" id="app">
	<s:if test="#app.appBind_status==2">
	appAry[index] = '<s:property value="#app.appName"/>';
	index++;
	</s:if>
	<s:else>
	approvalFlag = true;
	approvalApps[indexNew] = '<s:property value="#app.appName"/>';
	indexNew++;
	</s:else>
	</s:iterator>
	initApps();
	function initApps() {
		$("#app").html(appAry.join(", "));
		if (approvalFlag) {
			$('#appStatus').removeClass();
			$('#appStatus').addClass("status_yellow");
			$('#appStatus').text("企业客户变更待审");
			$('#showAppEdit').show();
			$('#showAppEdit').attr("title",
					"企业客户变更已提交审核, 变更后企业客户将更新为 : " + approvalApps.join(", "));
		} else {
			$('#appStatus').removeClass();
			$('#appStatus').text("正常");
			$('#showAppEdit').hide();
		}
	}

	$(function() {
		//菜单显示当前，开发时删除
		$("#userInfo").siblings().removeClass("active");
		$("#userInfo").addClass("active");
	});

	function edit() {
		$("#details_form").attr('action', 'userUpdate.action');
		$("#details_form").submit();
	}

	function closeAccount() {
		$("#details_form").attr('action', 'closeAccount.action');
		$("#details_form").submit();
	}

	
</script>