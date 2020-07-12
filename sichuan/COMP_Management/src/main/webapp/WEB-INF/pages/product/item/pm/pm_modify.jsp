<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<h1>服务条目管理</h1>
<div class="details-con">
	<div class="detail-title">
		<a href="javascript:void(0)" id='goBack' title="返回"> <span
			class="back"></span>
		</a>
		<h3 class="apply-title">修改物理机条目</h3>
	</div>
	<!-- form -->
	<div class="page-form">
		<div class="section">
			<input type="hidden" value="${itemInfo.standardId }"
				id="oldStandardId" name="oldStandardId"> <input
				type="hidden" value="${itemInfo.itemId }" id="itemId" name="itemId">
			<input type="hidden" value="${itemInfo.standardId }" id="standardId"
				name="standardId">
			<input type="hidden" value="${itemInfo.status.code}" id="statusCode"
				name="statusCode">
			<div class="field">
				<div class="caption">
					<font class="red-font">*</font>条目名称：
				</div>
				<div class="content">
					<input type="text" size="28" value="${itemInfo.itemName }"
						maxlength="25" id="itemName" />
				</div>
				<div class="point"></div>
			</div>
			<div class="field">
				<div class="caption">
					<font class="red-font">*</font>条目分类：
				</div>
				<div class="content">
					<s:select id="catalogId" list="#request.itemTypeInfos"
						listKey="catalogId" listValue="catalogName" cssClass="select-max"></s:select>
				</div>
				<div class="point"></div>
			</div>
			<div class="field">
				<div class="caption">条目描述：</div>
				<div class="content">
					<textarea cols="42" id="description">${itemInfo.description }</textarea>
				</div>
				<div class="point"></div>
			</div>
		</div>
		<div class="section">
			<div class="field">
				<div class="caption">
					<ul class="opt-bottom-btn" style="padding-bottom: 0px;">
						<li style="width: 100px;"><a href="javascript:void(0)"
							onclick="onRelateResource();return false;">关联资源规格</a></li>
					</ul>
				</div>
				<div class="content">
					<font class="red-font">*</font> （一个服务目录条目只能关联一个规格）
				</div>
			</div>
			<div class="field">
				<div id="approval-relate-resource" class="table-content table-block">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <col style="width:20%;" />
                            <col style="width:20%;" />
                            <col style="width:20%;" />
                            <col style="width:15%;" />
                            <col style="width:15%;" />
                            <col style="width:10%;" />
                          <tr>
                            <th>规格名称</th>
                            <th>物理机型号</th>
                            <th>CPU类型</th>
                            <th>内存容量(MB)</th>
                            <th>磁盘空间(GB)</th>
                            <th>操作</th>
                          </tr>
                    </table>
				</div>
			</div>
		</div>
		<div class="page-button">
			<ul class="opt-bottom-btn">
				<s:if test="itemInfo.status.code!=3">
					<li><a href="javascript:void(0)" id="saveItem">保存</a></li>
				</s:if>
				<li><a href="javascript:void(0)" id="saveAndSubmitItem">提交</a>
				</li>
				<li><a href="javascript:void(0)" id="cancel">取消</a></li>
			</ul>
		</div>
	</div>
</div>
<!-- form end -->
<!-- 关联资源模板信息 -->
<div class="page-form" id="relate_resource_div" style="display: none;">
	<div class="section">
		<div class="table-seach">
			<p>
				<select class="select-min" id="queryRecommend">
					<option value="0">未关联</option>
					<option value="1">已关联</option>
				</select>
			</p>
			<p><div style="float: left; padding-right: 10px;">物理机类型:</div>
                	<select id="queryServerType" class="select-max">
                	<option value=''>--请选择--</option>
                </select>
            </p>
            <p>CPU类型:<input type="text" size="8" id="queryCpuType"/>
            </p>
			<p>内存容量(MB):</p>
            <p><input type="text" size="8" id="queryRamSize"/>
            </p>
            <p>
				磁盘空间(GB):<input type="text" size="8" id="queryDiscSize" />
			</p>
			<ul class="opt-btn">
				<li><a class="search" href="javascript:void(0)" id="search">查询</a>
				</li>
			</ul>
		</div>
		<div class="field">
			<div class="table-content table-block"
				style="height: 220px; overflow-y: scroll;">
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					id="standardlist">
				</table>
			</div>
		</div>
	</div>
</div>
<!-- -->
<script type="text/javascript">
	$("#catalogId").val("${itemInfo.catalogId}");
</script>
<script type="text/javascript" src="../scripts/pagesjs/item/entry.js"></script>
<script type="text/javascript" src="../scripts/pagesjs/item/pm/pm_btn.js"></script>
<script type="text/javascript" src="../scripts/pagesjs/item/pm/search_pm_standard.js"></script>
<script type="text/javascript" src="../scripts/pagesjs/item/pm/pm_modify.js"></script>