<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
	<div id="message" style="display: none;">
		<span class="error"><s:actionerror /></span>
		<span class="error"><s:fielderror /></span>
		<span class="success"><s:actionmessage /></span>
	</div>
       	<h1>角色管理</h1>
        <span class="apply"><a href="javascript:onAdd();">创建角色</a></span>
      <div class="details-con">
   	    <div class="detail-title">
          <h3 class="apply-title">角色列表</h3>
        </div>
        <form action="roleSearch.action" id="search_form" name="search_form"
		method="post">
        <div class="table-seach">
        	<p>
        	<label class="fl">角色名称：</label> <input name="queryRoleInfo.roleName" class="text" type="text" size="25" id="searchName" maxlength="30" value="<s:property value="queryRoleInfo.roleName" />"/>
            </p>
            <p><label class="fl">角色状态：</label> 
            <select id="searchStatus" name="queryRoleInfo.queryStatus" class="select-min">
            		<option value=""
						<s:if test="queryRoleInfo.queryStatus == \"\"">selected="selected"</s:if>>-请选择-</option>
						<option value="0"
						<s:if test="queryRoleInfo.queryStatus == \"0\"">selected="selected"</s:if>>无效</option>
					<option value="1"
						<s:if test="queryRoleInfo.queryStatus == \"1\"">selected="selected"</s:if>>有效</option>
			</select></p>
            <ul class="opt-btn fr">
                <li>
                    <a class="search" href="javascript:void(0);"  id="search">查询</a>
                </li>
            </ul>
        </div>
        </form>
        <!-- 列表 -->
        <div id="approval-resource" class="table-content table-block">
             <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <col style="width:20%;" />
                <col style="width:40%;" />
                <col style="width:10%;" />
                <col style="width:30%;" />
              <tr>
                <th class="nl">角色名称</th>
                <th>描述</th>
                <th>状态</th>
                <th>操作</th>
              </tr>
              <s:if test="roleInfos==null or roleInfos.size()==0">
              	<tr>
              		<td colspan="9" align="center">
              			暂无符合数据！
              		</td>
              	</tr>
              </s:if>
              <s:if test="roleInfos!=null and roleInfos.size()>0" >
			      <s:iterator value="#request.roleInfos">
		              <tr>
		                <td><s:property value='roleName'/></td>
		                <td title="<s:property value='description'/>"><s:property value='description'/></td>
		                <td><s:property value='status.desc'/></td>
		                <td class="table-opt-block">
		                    <a href="javascript:void(0)" onclick="onModify(this,'<s:property value='roleId'/>','<s:property value='status.code'/>');return false;">修改</a>
							<a href="javascript:void(0);" onclick="onRoleAuth('<s:property value='roleId'/>');return false;">授权</a>
							<a href="javascript:void(0);" onclick="onDel(this,'<s:property value='roleId'/>');return false;">删除</a>
		                </td>
		              </tr>
	              </s:iterator>
              </s:if>
            </table>
        </div>
        <!-- 翻页 -->
		<div class="pageBar">
			<s:property value="pageBar" escape="false" />
		</div>
</div>
      <!-- 弹出层 添加系统配置 -->
       <div class="page-form" id="ztree_div" style="display:none;">
		<ul id="authTree" class="ztree"></ul>
	</div>
	<div class="page-form" id="role_div" style="display:none;">
		<div class="section">
			<div class="field">
	            <div class="caption"><font class="red-font">*</font>角色名称：</div>
	            <div class="content">
	            	<input type="text" size="28" id="roleName" value="" maxlength="25"/>
	            </div>
	            <div class="point"></div>
	        </div>
	        <div class="field">
	            <div class="caption"><font class="red-font">*</font>角色状态：</div>
	            <div class="content">
	            	<select id="status" class="select-max">
						<option selected="selected" value="1">有效</option>
						<option value="0">无效</option>
					</select>
	            </div>
	            <div class="point"></div>
	        </div>
	        <div class="field">
	            <div class="caption">描述：</div>
	            <div class="content">
	            	<textarea cols="50" id="description"></textarea>
	            </div>
	            <div class="point"></div>
	        </div>
	    </div>
	</div>
<script type="text/javascript" src="../scripts/pagesjs/system/security/role_list.js"></script>
