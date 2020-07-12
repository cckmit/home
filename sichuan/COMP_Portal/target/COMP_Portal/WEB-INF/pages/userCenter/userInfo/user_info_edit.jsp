<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<style>
ul.edit_app li {
	border: 1px solid #009DD9;
	margin: 10px;
	border-radius: 5px;
	width: 200px;
	line-height: 30px !important;
	padding: 5px 10px;
}

#bind_app {
	display: inline-block;
	text-decoration: none;
	font-size: 14px;
	width: 100px;
	height: 30px;
	line-height: 30px;
	text-align: center;
	background-color: #009DD9;
	color: #fff;
	margin-right: 10px;
	border: 1px solid transparent;
	text-decoration: none;
}
</style>
<div class="self-config">
	<div class="config-title">
		<h2>修改用户信息</h2>
		<div id="message" style="display: none;">
			<span id="error" class="error"><s:actionerror /></span> <span
				class="error"><s:fielderror /></span> <span class="success"><s:actionmessage /></span>
		</div>
	</div>
	<s:form id="edit_form" method="post">
		<input type="hidden" name="count" value="${count}" />
		<input id="userId" type="hidden" name="user.userId"
			value="${user.userId}" />
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
				<span class="col-name">用户姓名：</span><span class="col-content"><input
					type="text" id="userName" name="user.userName"
					value="${user.userName}" maxlength="10" /></span>
			</div>
			<div>
				<span class="col-name">创建时间：</span><span class="col-content"><s:property
						value="user.createTime" /> </span>
			</div>
			<div>
				<span class="col-name">描述信息：</span><span class="col-content"><input
					type="text" class="max-text" id="desc" name="user.desc"
					value="${user.desc}" maxlength="1024" /></span>
			</div>
		</div>

		<div class="config-content">
			<h3>联系信息</h3>

			<div>
				<span class="col-name">手机号码：</span><span class="col-content"><input
					type="text" id="mobile" name="user.mobile" value="${user.mobile}"
					maxlength="14" /></span>
			</div>
			<div>
				<span class="col-name">邮箱：</span><span class="col-content"><input
					type="text" id="email" name="user.email" value="${user.email}"
					maxlength="64" /></span>
			</div>
			<div>
				<span class="col-name">固定电话：</span><span class="col-content"><input
					type="text" id="telphone" name="user.telphone"
					value="${user.telphone}" maxlength="20" /></span>
			</div>
			<div>
				<span class="col-name">部门名称：</span><span class="col-content"><input
					type="text" id="deptName" name="user.departmentName"
					value="${user.departmentName}" maxlength="50" /></span>
			</div>
			<div>
				<span class="col-name">通讯地址：</span><span class="col-content"><input
					type="text" id="address" class="max-text" name="user.address"
					value="${user.address}" maxlength="64" /></span>
			</div>
		</div>

		<div class="config-content">
			<h3>企业客户信息</h3>
			<div>
				<span class="col-name">绑定的企业客户：</span>
				<s:if test="user.userId=='admin'">
				<s:if test="count>0">
					<div class="col-content">
						<span id="app"></span></span>
					</div>
					<div>
						<span class="col-name">状态：</span><span class="col-content"><span
							id="appStatus"></span> <span id="showAppEdit"
							style="cursor: pointer; color: #0066cc;">(查看全部)</span></span>
					</div>
			</s:if>
			<s:else>
				<div class="col-content">
					<div class="bind_div">
						<a id="bind_app" class="btn" href="javascript:;">绑定</a>
					</div>

				</div>
				<div class="block_edit"
					style="margin-left: 146px; margin-top: -27px; padding-left: 5px; padding-right: 0; border-bottom: none; background-color: white; overflow: auto; overflow-x: hidden;">
					<ul class="edit_app" id="add_user_add_app_ul"
						style="list-style: none; margin: 30px 0;">
						<li class="add_app" id="add_user_add_app_button"
							style="border: 0; margin: 0; padding: 0;"></li>
					</ul>
				</div>
			</s:else>
			</s:if>
			<s:else>
				<div class="col-content">
					<span id="app"></span></span>
				</div>
			</s:else>
		</div>
		<div class="submit">
			<a class="btn fr" href="userInfo.action" id="cancel">取消</a> <a
				class="btn fr" href="javascript:void(0);" onclick="editSubmit()"
				id="edit">保存</a>
		</div>
</div>
</s:form>
</div>


<!-- 业务列表-->
<s:include value="../../../../userBindAppList.jsp" />
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
		$("#userInfo").siblings().removeClass("active");
		$("#userInfo").addClass("active");

		// 业务列表全选
		$("#selectAllApp").click(function(event) {
			checkAllApp(this);
		});

		//初始绑定业务按钮
		$('#add_user_add_app_ul li:not(#add_user_add_app_button)').remove();
		$('input[name="appIds"]').remove();
		//初始化业务数据.
		<s:iterator value="user.apps" status="apps" id="app">
		addAPP("<s:property value="#app.appId"/>",
				"<s:property value="#app.appName"/>",
				$('#add_user_add_app_button'));
		</s:iterator>
		$('#add_user_add_app_ul li').each(
				function() {
					if (typeof $(this).data("appId") === 'undefined') {
						return true;
					}
					$('<input type="hidden" name="appIds" />').appendTo(
							$('#edit_form')).val($(this).data("appId"));
					$('<input type="hidden" name="oldAppIds" />').appendTo(
							$('#edit_form')).val($(this).data("appId"));
				});
		initDelAppButton();
		// 绑定业务查询按钮事件
		$("#searchApp").click(function(event) {
			searchAppData('queryAppListAction.action', false);
		});

		// 绑定业务按钮事件
		$('#bind_app').click(function() {
			$('#searchAppName').attr("value", "");//业务列表，查询条件框
			addAppEvent($('#add_user_add_app_button'));
		});

	});

	var msg = "";
	function editSubmit() {
		var count = $("input[name='count']").val();
		var f0 = false;
		if (validateUserInfo()) {
			if (count > 0) {
				f0 = true;
			} else {
				f0 = validateApp();
			}
			if (f0) {
				$("#edit_form").attr('action', 'userUpdateSave.action');
				$("#edit_form").submit();
			}
		}
	}

	//返回列表页面
	function backList(msg, errMsg) {
		$.backListMsg({
			msg : msg,
			errMsg : errMsg,
			url : 'userInfo.action'
		});
	}
	function validateUserInfo() {
		var userName = $('#userName').attr("value");
		var mobile = $('#mobile').attr("value");
		var email = $('#email').attr("value");
		/*var question =$("#question").val();
		var answer = $("#answer").val();*/
		var rexMail = /^([a-zA-Z0-9\\._-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
		var rexNum = /^[0-9]*[1-9][0-9]*$/;
		if (null == userName || userName == "") {
			msg += "用户姓名不能为空，请重新输入！<br>";
		}
		if (null == mobile || mobile == "") {
			msg += "手机号码不能为空，请重新输入！<br>";
		} else if (!rexNum.test(mobile)) {
			msg += "手机号码只能为数字！！";
		}
		if (null == email || email == "") {
			msg += "邮箱不能为空，请重新输入！<br>";
		} else if (!rexMail.test(email)) {
			msg += "邮箱格式不正确！";
		}
		/*if(question==null ||question==""){
			msg+="密保问题不能为空,请重新输入！<br>";
		}
		if(answer==null || answer==""){
			msg+="密保答案不能为空,请重新输入!<br>";
		}*/
		if (msg != '') {
			$.compMsg({
				type : 'error',
				msg : msg
			});
			return false;
		} else {
			if (validateUser()) {
				return true;
			} else {
				return false;
			}
		}

	}

	function validateUser() {//用户邮箱、手机号是否唯一
		var userId = $('#userId').attr("value");
		var mobile = $('#mobile').attr("value");
		var email = $('#email').attr("value");
		var flag = true;
		$
				.ajax({
					type : "POST",
					async : false,
					url : 'registerValidateUserIdAction.action?struts.enableJSONValidation=true',
					data : {
						userId : userId,
						mobile : mobile,
						email : email
					},
					dataType : "JSON",
					cache : false,
					success : function(data) {
						if (data.errMsg != null && data.errMsg != "") {
							//$('#errParam').text(data.errMsg);
							$.compMsg({
								type : 'error',
								msg : data.errMsg
							});
							flag = false;
						}

					}
				});
		if (flag) {
			return true;
		} else {
			return false;
		}
	};

	//**************************绑定业务***************************
	//业务列表选中的appId数组和appName数组
	var currentPageCheckedAppIdArry = new Array();
	var currentPageCheckedAppNameArry = new Array();
	//点击业务列表全选checkbox
	function checkAllApp(obj) {

		if ($(obj).is(':checked')) {
			$("[name='selectApp']").each(function(index) {
				$(this).prop('checked', true);
				isCheckGetAppId(index);
			});
		} else {
			$("[name='selectApp']").each(function(index) {
				$(this).prop('checked', false);
				isCheckGetAppId(index);
			});
		}
	}
	//点击当前页，每个复选框时判断是否全选 框check
	function ischeckCurrentAllApp() {

		var checkedAllApp = true;
		var obj = document.getElementsByName("selectApp");
		if (obj.length == 0) {
			checkedAllApp = false;
		}
		for (var i = 0; i < obj.length; i++) {
			if (!obj[i].checked) {
				checkedAllApp = false;
				break;
			}
		}
		if (checkedAllApp) {
			$('#selectAllApp').attr("checked", true);//业务列表，当页"全选"true
		} else {
			$('#selectAllApp').attr("checked", false);//业务列表，当页"全选"false
		}
	}
	function isCheckGetAppId(cbIndex) {
		var obj = document.getElementsByName("selectApp");
		var appIdTmp = $(obj[cbIndex]).parents('tr').data('appId');
		var appNameTmp = $(obj[cbIndex]).parents('tr').data('appName');

		if (obj[cbIndex].checked) {
			currentPageCheckedAppIdArry.push(appIdTmp);//添加到数组中的页面新选中的元素
			currentPageCheckedAppNameArry.push(appNameTmp);
		} else {
			removeElement(appIdTmp);//删除数组中指定元素
		}

	}
	function emptyApp() {
		currentPageCheckedAppIdArry = [];
		currentPageCheckedAppNameArry = [];
		var obj = document.getElementsByName("selectApp");
		for (var i = 0; i < obj.length; i++) {
			if (obj[i].checked) {
				obj[i].checked = false;
			}
		}
	}
	function initAppAry() {
		$('.delApp').each(function() {
			var appId = $(this).parents('li').data('appId');
			var appName = $(this).parents('li').data('appName');
			currentPageCheckedAppIdArry.push(appId);//添加到数组中的页面新选中的元素
			currentPageCheckedAppNameArry.push(appName);

			$('[name="selectApp"]').not(":checked").each(function() {
				var appId_box = $(this).parents('tr').data('appId');
				if (appId == appId_box) {
					$(this).attr('checked', true);
				}
			});
		});
	}
	//生成业务列表样式及点击确定后添加业务
	var addAppEvent = function(addAppButton) {
		emptyApp();
		initAppAry();
		$
				.dialog({
					title : '企业客户列表',
					content : document.getElementById('app_list'),
					init : function() {
						getPageData('queryAppListAction.action', false);
					},
					button : [ {
						name : '确定',
						callback : function() {
							$('.delApp').parent('li').remove();//删除文本框中的内容，重新添加
							for ( var c in currentPageCheckedAppIdArry) {
								addAPP(currentPageCheckedAppIdArry[c],
										currentPageCheckedAppNameArry[c],
										addAppButton);
							}
							emptyApp();
							initAppAry();
							initDelAppButton();
						},
						focus : true
					} ],
					cancelVal : '关闭',
					cancel : true,
					lock : true
				});
	};
	//数组指定的元素位置
	function appIdIndexOf(val) {
		for (var i = 0; i < currentPageCheckedAppIdArry.length; i++) {
			if (currentPageCheckedAppIdArry[i] == val)
				return i;
		}
		return -1;
	}
	//数组指定的元素删除
	function removeElement(val) {
		var index = appIdIndexOf(val);
		if (index > -1) {
			currentPageCheckedAppIdArry.splice(index, 1);
			currentPageCheckedAppNameArry.splice(index, 1);
		}
	}
	//单个值放入数组中
	function addElement(appIdTmp, appNameTmp) {

		var c = 0;
		for (c; c < currentPageCheckedAppIdArry.length; c++) {
			if (currentPageCheckedAppIdArry[c] == appIdTmp) {
				break;
			}
		}
		if (c == currentPageCheckedAppIdArry.length) {
			currentPageCheckedAppIdArry.push(appIdTmp);//添加到数组中的页面新选中的元素
			currentPageCheckedAppNameArry.push(appNameTmp);
		}
	}
	//在缓存中除去本页去掉的值
	function removeElements() {
		$('[name="selectApp"]').not(":checked").each(function() {
			var tmp = this;
			var appId = $(tmp).parents('tr').data('appId');
			for ( var cr in currentPageCheckedAppIdArry) {
				if (currentPageCheckedAppIdArry[cr] == appId) {
					currentPageCheckedAppIdArry.splice(cr, 1);//删除数组中的业页面取消的元素，c为数组索引，1为删除数组中元素的个数
					currentPageCheckedAppNameArry.splice(cr, 1);
				}
			}
		});
	}
	//获取本页选中的值放在缓存中
	function addElements() {
		$('[name="selectApp"]:checked').each(function() {
			var appId = $(this).parents('tr').data('appId');
			var appName = $(this).parents('tr').data('appName');
			var c = 0;
			for (c; c < currentPageCheckedAppIdArry.length; c++) {
				if (currentPageCheckedAppIdArry[c] == appId) {
					break;
				}
			}
			if (c == currentPageCheckedAppIdArry.length) {
				currentPageCheckedAppIdArry.push(appId);//添加到数组中的页面新选中的元素
				currentPageCheckedAppNameArry.push(appName);
			}
		});
	}
	//业务列表，"分页"按钮事件
	var getPageData = function(url) {
		removeElements();
		addElements();
		searchAppData(url, true);

	};

	//业务列表，"查询"按钮执行
	var searchAppData = function(url, isChangePageEvent) {
		var queryData = {};
		if (!isChangePageEvent) {
			var searchAppName = $("#searchAppName").val();
			queryData = {
				"app.appName" : searchAppName
			};
		}
		$
				.ajax({
					type : "GET",
					url : url,
					data : queryData,
					success : function(data) {

						$('#appListTbody').empty();

						$
								.each(
										data.appList,
										function(index) {
											var existApp = false;
											for ( var c in currentPageCheckedAppIdArry) {
												if (currentPageCheckedAppIdArry[c] == this.appId) {
													existApp = true;
													break; //等效于break
												}
											}
											var appCheckBox = "";
											if (existApp) {
												appCheckBox = $('<td align="center"><input id="" type="checkbox" name="selectApp" value=""  checked="true" onclick="ischeckCurrentAllApp();isCheckGetAppId('
														+ index + ');"/></td>');
											} else {
												appCheckBox = $('<td align="center"><input id="" type="checkbox" name="selectApp" value=""  onclick="ischeckCurrentAllApp();isCheckGetAppId('
														+ index + ');"/></td>');
											}

											$('<tr>')
													.appendTo('#appListTbody')
													.data('appId', this.appId)
													.data('appName',
															this.appName)
													.append(appCheckBox)
													.append(
															'<td>'
																	+ this.appName
																	+ '</td>')
													.append(
															'<td>'
																	+ this.description
																	+ '</td>');
										});
						ischeckCurrentAllApp();//通过数组中的值判断"全选"是否需要选中
						$('#appListPageBarDiv').html(data.pageBar);
					},
					error : function(data) {
						var mes = fieldErrors(data.fieldErrors);
						if (mes != '') {
							$.compMsg({
								type : 'error',
								msg : mes
							});
						}
						if (data.resultMessage != null) {
							if (data.resultFlage == 'success') {
								backList(data.resultMessage, '');
								flage = false;
							} else if (data.resultFlage == 'failure') {
								$.compMsg({
									type : 'error',
									msg : data.resultMessage
								});
							} else if (data.resultFlage == 'error') {
								$.compMsg({
									type : 'error',
									msg : data.resultMessage
								});
							}
						}
					},
					dataType : "json"
				});
	};
	//一个一个添加或删除具体业务同在框中已有的APP比对
	var addAPP = function(appId, appName, addAppButton) {
		var exist = false;
		var appUL = addAppButton.parents('.edit_app');
		appUL.find('li').each(function() {
			if (appId == $(this).data("appId")) {
				exist = true;
				return false; //等效于break
			}
		});

		if (exist) {
			return;
		}

		var isTooLong = false;
		var appName_show = appName;
		var byteValLen = 0;
		var returnText = "";
		var maxTextLength = 10 * 2;//9*2;
		for (var i = 0; i < appName.length; i++) {
			(appName[i].match(/[^x00-xff]/ig) != null) ? (byteValLen += 2)
					: (byteValLen += 1);
			returnText += appName[i];
			if (byteValLen > maxTextLength) {
				isTooLong = true;
				appName_show = returnText.substring(0, i - 1) + '...';
				break;
			}
		}
		if (isTooLong) {
			$(
					'<li style="float: left; " title="'+appName+'">'
							+ appName_show
							+ '<span class="delApp" style="float:right;cursor:pointer;" >X</span></li>')
					.insertBefore(addAppButton).data("appId", appId).data(
							"appName", appName);
		} else {
			$(
					'<li style="float: left;">'
							+ appName_show

							+ '<span class="delApp" style="float:right;cursor:pointer; " >X</span></li>')
					.insertBefore(addAppButton).data("appId", appId).data(
							"appName", appName);
		}
		//initDelAppButton();
	};
	//检验错误信息
	function fieldErrors(errors) {
		var mes = '';
		if (!jQuery.isEmptyObject(errors)) {
			if (!jQuery.isEmptyObject(errors.appName)) {
				mes += errors.appName + '\n';
			}
		}
		return mes;
	}
	//删除--
	var initDelAppButton = function() {
		$('.delApp').click(function() {
			$(this).parent('li').remove();
		});
	};

	function validateApp() {//绑定业务
		$('input[name="appIds"]').remove();

		$('#add_user_add_app_ul li').each(
				function() {
					if (typeof $(this).data("appId") === 'undefined') {
						return true;
					}
					$('<input type="hidden" name="appIds" class="appIds" />')
							.appendTo($('#edit_form')).val(
									$(this).data("appId"));
				});

		var appIdStr = document.getElementsByName('appIds').length;
		if (appIdStr == 0) {
			//$('#errParam').text("所属业务不能为空，请重新绑定！");
			$.compMsg({
				type : 'error',
				msg : "绑定的企业客户不能为空，请重新绑定！"
			});
			return false;
		} else {
			return true;
		}
	}

	
</script>