<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<style>
.table-seach p{
	margin-right: -2px;
}
</style>
	<div id="message" style="display: none;">
		<span class="error"><s:actionerror /></span>
		<span class="error"><s:fielderror /></span>
		<span class="success"><s:actionmessage /></span>
	</div>
       	<h1>用户审批</h1>
   	  <div class="details-con">
   	    <div class="detail-title">
          <h3 class="apply-title">用户审批</h3>
   	    </div>
   	    <form action="userApprovalList.action" id="search_form" name="search_form"
		method="post">
        <div class="table-seach">
        	<p>
        	用户账号：<input class="text" type="text" name="queryUserInfo.userId" maxlength="20" size="14" value="<s:property value="queryUserInfo.userId" />"/>
            </p>
        	<p>
        	用户姓名：<input class="text" type="text" id="userName" name="queryUserInfo.userName" maxlength="10" size="14" value="<s:property value="queryUserInfo.userName" />"/>
            </p>
            <p>
                                  绑定的业务组：<input class="text" type="text" id="appName" name="queryUserInfo.appName" maxlength="64" size="14" value="<s:property value="queryUserInfo.appName"/>" /> 
            </p>
        	<p>
        	创建时间：
        	<input type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="true" class="Wdate wdatelong" size="14" id="beforeCreateTime" value="<s:property value="queryUserInfo.beforeCreateTime" />" maxlength="15"/>
        	<input type="hidden" value="" name="queryUserInfo.beforeCreateTime" id="beforeCreateTimeV" />
        	至
        	<input type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="true" class="Wdate wdatelong" size="14" id="afterCreateTime" value="<s:property value="queryUserInfo.afterCreateTime" />" maxlength="15"/>
            <input type="hidden" value="" name="queryUserInfo.afterCreateTime" id="afterCreateTimeV" />
            </p>
            <ul class="opt-btn fr">
                <li>
                    <a class="search" href="javascript:void(0);" id="search">查询</a>
                </li>
                <!-- <li>
                <a class="batch" href="javascript:void(0);" onclick="approvalUserMore(this);return false;">批量审批</a>
                </li> -->
            </ul>
        </div>
        </form>
        <!-- 列表 -->
        <div id="approval-will" class="table-content table-block">
             <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <col style="width:3%;" />
                <col style="width:16%;" />
                <col style="width:16%;" />
                <%--<col style="width:10%;" />
                <col style="width:13%;" />
                <col style="width:15%;" /> --%>
                <col style="width:29%" />
                <col style="width:9%" />
                <col style="width:12%;" />
                <col style="width:5%;" />
              <tr>
                <th class="nl"><input type="checkbox" name="selectAll" id="selectAll"/></th>
                <th>用户账号</th>
                <th>用户姓名</th>
                <%-- <th>电话</th>
                <th>邮箱</th>
                <th>部门</th>--%>
                <th>绑定的业务组</th>
                <th>状态</th>
                <th>创建时间</th>
                <th>操作</th>
              </tr>
              <s:if test="userBeans.size()>0" >
			      <s:iterator value="userBeans" var="user">
		              <tr>
		                <td><input type="checkbox" name="select" value="<s:property value='userId'/>"/>
		                	<input type="hidden" name="status" value="<s:property value="#user.status.getCode()"/>" />
		                </td>
		                <td><s:property value='userId'/></td>
		                <td><s:property value='userName'/></td>
		                <%--<td><s:property value='mobile'/></td>
		                <td><s:property value='email'/></td>
		                <td><s:property value='departmentName'/></td> --%>
		                <td>
		                	<div class="cut"><s:iterator value="#user.apps" var="app" status="st"><s:if test="!#st.isFirst()">,&nbsp;</s:if><s:property value="#app.appName"/></s:iterator></div>
		                	<%--
		                	<div title="<s:iterator value="#user.apps" var="app" status="st"><s:if test="!#st.isFirst()">,&nbsp;</s:if><s:property value="#app.appName"/></s:iterator>">
							 	<s:iterator value="#user.apps" var="app" status="a">
							 		<s:if test="#a.isFirst()">
							 			<s:property value="#app.appName"/>
							 		</s:if>
							 	</s:iterator>
							 	<s:if test="#user.apps.size > 1">
						 			...
						 		</s:if>
							</div>
							 --%> 
		                	<%-- <s:iterator value="#user.apps" var="app" status="st">
		                		<s:if test="#st.isFirst()">
		                			<s:property value="#app.appName"/>
		                		</s:if>
		                		<s:else>
		                			<div style="display:none"><s:property value="#app.appName"/> </div>
		                		</s:else>
		                	</s:iterator> --%>
		                </td>
		                <td>
		                	<s:if test="#user.status.getCode()==0">
		                	  	 业务组变更待审
		                	</s:if>
		                	<s:else>
		                		注册待审
		                	</s:else>
		                </td>
		                <td><s:property value='updateTime'/></td>
		                <td class="table-opt-block">
		                    <a href="javascript:void(0);" onclick="onUserDetail('<s:property value='userId'/>','<s:property value='#user.status.getCode()'/>');return false;">审批</a>
		                </td>
		              </tr>
	              </s:iterator>
              </s:if>
              <s:if test="userBeans.size()==0" >
              	<tr>
	                <td colspan="7" align="center">
	                	无符合数据
	                </td>
	            </tr>
              </s:if>
            </table>
         </div>
      <!-- 批量审批弹出层 -->
	<div class="page-form" id="approvalUserMore" style="display:none;">
	    <div class="field">
	        <div class="caption">审批意见：</div>
	        <div class="content">
	        	<textarea cols="40" rows="5" id=auditInfo></textarea>
	      </div>
	        <div class="point"></div>
	    </div>
	        <div class="point"></div>
	    </div>
	 <!-- 翻页 -->
         <div class="pageBar">
            <s:property value="pageBar" escape="false" />
         </div>
	</div>
	
    <s:form method="post" id="userDetail">
    	<input type="hidden" id="userId" value="" name="userId" />
    	<input type="hidden" id="userStatus" value="" name="userStatus"/>
    </s:form>
<script type="text/javascript" src="../scripts/pagesjs/task/user_list.js"></script>
