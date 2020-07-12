<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<!-- 业务组修改 -->
<div class="page-form" id="app_update" style="display: none;">
	<form id="app_update_form" name="app_update_form"
		action="appUpdate.action" method="post">
			<div class="field">
				<div class="caption">业务组ID：</div>
				<div class="content">
					<input id="edit_app_id" name="app.app_id" type="text"
						style="border: 1px;" size="40" readonly="readonly" />
				</div>
			</div>
		<div class="field">
			<div class="caption">资源池名称：</div>
			<div class="content">
			<select id="respool" name="app.respoolId">
<%--				<option  value="select">-请选择-</option>--%>
			</select></div>
		</div>
			<div class="field">
				<div class="caption">业务组名称：</div>
				<div class="content">
					<input id="edit_app_name" name="app.app_name" type="text"
						class="text" maxlength="64" size="40" />
				</div>
				<div class="point">(最多64位，由汉字、数字、字母、横线和下划线组成)</div>
			</div>

			<div class="field" style="margin-top: 5px;">
				<ul class="result-list editUL"
					style="width: 55%; padding-left: 54px; float: left;">
					<li><span class="result-name"
						style="width: 89px; padding-left: 15px; margin-right: 20px; text-align: right;"><font
							class="red-font"></font>联系人名称：</span>
						<div class="content">
							<div class="block_edit"
								style="width: 250px; height: 25px; margin-left: 105px; margin-top: -23px; padding-left: 5px; padding-right: 0; border-bottom: none; overflow: auto; overflow-x: hidden;">
								<ul class="edit_user" id="edit_user_add_app_contacts_ul">
									<li class="add_user" id="edit_user_add_app_contacts_button"
										style="border: 0; margin: 0; padding: 0;"></li>
								</ul>
							</div>
						</div>
					</li>
				</ul>
				<ul class="result-list editUL"
					style="width: 10%; float: left; padding-top: 5px;">
					<li>
						<div class="bind_div">
							<span id="bind_app_edit">添加</span>
						</div>
					</li>
				</ul>
			</div>

			<div class="field">
				<div class="caption">业务组描述：</div>
				<div class="content">
					<textarea id="edit_description" name="app.description"
						class="uniform" cols="37" rows="3"></textarea>
				</div>
				<div class="point">(最多256位)</div>
			</div>
	</form>
</div>

