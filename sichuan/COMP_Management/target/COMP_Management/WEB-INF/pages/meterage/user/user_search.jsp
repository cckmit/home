<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="styles/base.css" type="text/css" />
<link rel="stylesheet" type="text/css" href="styles/formStyle.css" />
<link rel="stylesheet" type="text/css" href="styles/meterageStyle.css" />
<script type="text/javascript" src="scripts/lib/jquery-1.8.3.js"></script>
<script type="text/javascript" src="scripts/lib/meterage-tab.js"></script>
<script type="text/javascript" src="scripts/lib/jquery.mooko.tabs.js"></script>
<script type="text/javascript" src="scripts/lib/left-menu.js"></script>
<script type="text/javascript"
	src="scripts/lib/dialog/jquery.artDialog.js?skin=default"></script>
<script type="text/javascript" src="scripts/lib/jquery.uniform.js"></script>
<script type="text/javascript" src="scripts/common.js"></script>
<script language="JavaScript" type="">
function closeWindow()
	{
	window.opener.document.getElementById('userid').value=getCheckVal();
	window.close();
	}
 	/**
     * 用户全选
     */
$(function() {
	$("select,:radio").uniform();
});

/**
 * 点击用户列表全选checkbox后触发的逻辑
 * @param obj
 */
function checkAll(obj) {
    if ($(obj).is(':checked')) {
	$("input[name='select']").each(function() {
	    $(this).attr('checked', true);
	});
    } else {
	$("input[name='select']").each(function() {
	    $(this).attr('checked', false);
	});
    }
    $("select,:checkbox").uniform();
    $.uniform.update();
}
/**
 * 获取选中的用户列表checkbox
 */
var getCheckVal = function() {
    var res = new Array();
    $("input[name='select']:checked").each(function() {
	res.push($(this).val());
    });
    return res;
};
</script>
</head>
<body>
	<!-- 列表 -->
	<div id="approval-will" class="table-content table-block">
		<table id="ordertable" width="100%" border="0" cellspacing="0"
			cellpadding="0">
			<col style="width:5%;" />
			<col style="width: 15%;" />
			<col style="width: 20%;" />
			<col style="width: 20%;" />
			<col style="width: 20%;" />
			<col style="width: 20%;" />
			<tr>
				<th class="nl"></th>
				<th>帐号</th>
				<th>姓名</th>
				<th>手机</th>
				<th>邮箱</th>
				<th>部门</th>
			</tr>
			<s:iterator value="userList">
				<tr class="iterator">
					<td align="center"><input type="radio" name="select"
						value="<s:property value='userId'/>" /></td>
					<td><s:property value="userId" /></td>
					<td><s:property value="userName" /></td>
					<td><s:property value="mobile" /></td>
					<td><s:property value="email" /></td>
					<td align="center"><s:property value="departmentName" /></td>
				</tr>
			</s:iterator> 
		</table>
	</div>
	<div class="pageBar">
		<s:property value="pageBar" escape="false" />
	</div>
	<div class="table-content table-block">
	<table id="ordertable" width="100%" border="0" cellspacing="0"
			cellpadding="0">
			<col style="width: 30%;" />
			<col style="width: 30%;" />
			<col style="width: 20%;" />
			<col style="width: 20%;" />
			<tr>
				<th class="nl"></th>
				<th></th>
				<th></th>
				<th></th>
				<th></th>
			</tr>
				<tr class="iterator" height="30">
					<td align="center"></td>
					<td></td>
					<td></td>
					<td class='table-opt-block'><a href="javascript:closeWindow();">确 定</a><a href="javascript:window.close();">取 消</a></td>
				</tr>
		</table>
	</div>
</body>
</html>	