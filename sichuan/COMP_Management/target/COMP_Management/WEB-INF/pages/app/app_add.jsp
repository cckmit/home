<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<!-- 业务组创建 -->

<div class="page-form" id="app_add_dialog" style="display: none;">
	<form id="app_add_form" name="app_add_form"   action="appAdd.action"
		method="post">
		<div>
			<div class="field">
				<div class="caption">业务组ID：</div>
				<div class="content">
					<input id="add_app_id" name="app.app_id" class="text" type="text"
						maxlength="64" size="40" />
				</div>
				<div class="point">(最多64位，由数字、字母、下划线组成)</div>
			</div>
			<div class="field">
				<div class="caption">资源池名称：</div>
				<select id="respooladd" name="app.respoolId">
					<option selected="selected" value="select">-请选择-</option>
				</select>
			</div>
			<div class="field">
				<div class="caption">业务组名称：</div>
				<div class="content">
					<input id="add_app_name" name="app.app_name" class="text"
						type="text" maxlength="64" size="40" />
				</div>
				<div class="point">(最多64位，由汉字、数字、字母、横线和下划线组成)</div>
			</div>
			
			


			<div class="field" style="margin-top: 5px;">
				<!-- <h5 class="label">
					<span class="label-bg">联系人信息</span>
				</h5> -->
				<ul class="result-list" style="width: 55%;float:left;">
					<li><span class="result-name" style="float: left;margin-right: 20px; min-height: 12px; padding: 8px 0 1px 0; text-align: right; width: 140px;"><font
							class="red-font"></font>联系人名称：</span>
						
						<div class="content">
							<div class="block_edit"
								style="width:251px;margin-left: 160px; margin-top: -23px; padding-left: 5px; padding-right: 0; border-bottom: none; overflow: auto; overflow-x: hidden;">
								<ul class="edit_user" id="add_user_add_app_contacts_ul">
									<li class="add_user" id="add_user_add_app_contacts_button"
										style="border: 0; margin: 0; padding: 0;"></li>
								</ul>
							</div>
						</div>
						
						</li>
				</ul>
				<ul class="result-list" style="width: 10%;float:left;padding:12px 0 0 15px;">
					<li>
						<div class="bind_div" >
							<span id="bind_app_add">添加</span>
						</div>
					</li>
				</ul>
			</div>
			
			
			<div class="field">
				<div class="caption">业务组描述：</div>
				<div class="content">
					<textarea id="add_description" name="app.description"
						class="uniform" cols="37" rows="3"></textarea>
				</div>
				<div class="point">(最多256位)</div>
			</div>
			
			
		</div>
	</form>
</div>