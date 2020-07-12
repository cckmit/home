<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<h1>服务目录管理</h1>
<span class="apply"><a
	href="javascript:onAdd(this,<s:property value='catalogType' />);"><s:if
			test="#request.catalogType == 0">创建虚拟机目录</s:if>
		<s:elseif test="#request.catalogType == 1">创建物理机目录</s:elseif>
		<s:elseif test="#request.catalogType == 5">创建云硬盘目录</s:elseif></a></span>
<div class="details-con">
	<div class="detail-title">
		<h3 class="apply-title">
		  <s:if test="#request.catalogType == 0">
          	虚拟机目录管理
          </s:if>
          <s:if test="#request.catalogType == 1">
          	物理机目录管理
          </s:if>
		  <s:if test="#request.catalogType == 5">
          	云硬盘目录管理
          </s:if>
		</h3>
		<ul class="title-btn"></ul>
	</div>
	<div>&nbsp;</div>
	<s:form method="post" id="catalogDel">
		<input type="hidden" id="catalogId"
			value="<s:property value='catalogId' />" name="catalogId" />
		<input type="hidden" id="catalogType"
			value="<s:property value='catalogType' />" name="catalogType" />
	</s:form>
	<!-- 列表 -->
	<div id="approval-will" class="table-content table-block">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<col style="width: 15%;" />
			<col style="width: 17%;" />
			<col style="width: 12%;" />
			<col style="width: 14%;" />
			<col style="width: 20%;" />
			<tr>
				<th>服务目录名称</th>
				<th>服务目录描述</th>
				<th>创建人</th>
				<th>创建时间</th>
				<th>操作</th>
			</tr>
			<s:if test="catalogInfos.size()>0">
				<s:iterator value="#request.catalogInfos">
					<tr>
						<td><s:property value='catalogName' /></td>
						<td><s:property value='description' /></td>
						<td><s:property value='createUser' /></td>
						<td><s:property value='createTime' /></td>
						<td class="table-opt-block"><a href="javascript:void(0);"
							onclick="onCatalogModify(this,'<s:property value='catalogId'/>',<s:property value='catalogType' />);return false;">修改</a>
							<a href="javascript:void(0);"
							onclick="onCatalogDel(this,'<s:property value='catalogId'/>',<s:property value='catalogType' />);return false;">删除</a>
						</td>
					</tr>
				</s:iterator>
			</s:if>
			<s:else>
				<tr>
              		<td colspan="5" align="center">
              			暂无符合数据！
              		</td>
              	</tr>
			</s:else>
		</table>
	</div>
	<!-- 翻页 -->
	<div class="pageBar">
		<s:property value="pageBar" escape="false" />
	</div>
</div>


<!-- 弹出层添加服务目录 -->

<div class="page-form" id="catalog_create_div" style="display: none;">
	<s:form method="post" id="catalogCreate">
		<div class="section">
			<div class="field">
				<div class="caption">
					<font class="red-font">*</font>目录名称：
				</div>
				<div class="content">
					<input type="hidden" name="catalogId" id="catalogId" value="" /> <input
						type="hidden" name="catalogType" id="catalogType" value="" /> <input
						type="text" size="28" name="catalogName" id="catalogName" value=""
						maxlength="25" />
				</div>
				<div class="point"></div>
			</div>
			<div class="field">
				<div class="caption">目录描述：</div>
				<div class="content">
					<textarea cols="30" id="description" name="description"></textarea>
				</div>
				<div class="point"></div>
			</div>
		</div>
	</s:form>
</div>
<!-- 弹出层修改服务目录 -->
<div class="page-form" id="catalog_modify_div" style="display: none;">
	<s:form method="post" id="catalogModify">
		<div class="section">
			<div class="field">
				<div class="caption">
					<font class="red-font">*</font>目录名称：
				</div>
				<div class="content">
					<input type="hidden" name="catalogId" id="catalogId" value="" /> <input
						type="hidden" name="catalogType" id="catalogType" value="" /> <input
						type="text" size="28" id="catalogName" name="catalogName"
						value="<s:property value='catalogName'/>" maxlength="25" /> <input
						type="hidden" id="oldCatalogName" name="oldCatalogName"
						value="<s:property value='catalogName'/>" />
				</div>
				<div class="point"></div>
			</div>
			<div class="field">
				<div class="caption">目录描述：</div>
				<div class="content">
					<textarea cols="30" id="description" name="description"><s:property
							value='description' /></textarea>
				</div>
				<div class="point"></div>
			</div>
		</div>
	</s:form>
</div>
<script>
    	if('${errMsg}'!=''){
    		$.compMsg({type:'error',msg:'${errMsg}'}); 
        }
    	if('${msg}'!=''){
    		$.compMsg({type:'success',msg:'${msg}'}); 
        }
    </script>
<script type="text/javascript"
	src="../scripts/pagesjs/catalog/catalog_vm_list.js"></script>
