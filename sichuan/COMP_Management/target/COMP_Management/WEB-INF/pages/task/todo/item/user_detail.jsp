<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<h1>用户审批</h1>
<div class="details-con">
    <div class="detail-title">
        <a href="javascript:void(0)" id='goBack' title="返回">
            <span class="back"></span>
        </a>
      	<h3 class="apply-title">用户审批</h3>
    </div>
  <!-- form -->
    <div class="page-form">
    	<div class="contentDetail">
    		<div class="result" style="margin-top:5px;">
    			<h5 class="label">
		            <span class="label-bg">基本信息</span>
		        </h5>
		        <ul class="result-list">
	    			<li><span class="result-name">用户账号：</span><span><s:property value="userBean.userId"/> &nbsp;</span></li>
	    			<li><span class="result-name">创建人：</span><span><s:property value="userBean.createUserId"/>&nbsp;</span></li>
    			</ul>
    			<ul class="result-list">
	                <li><span class="result-name">用户姓名：</span>
	                	<span><s:property value="userBean.userName"/> &nbsp;</span>
	                </li>
	            	<li><span class="result-name">创建时间：</span><span><s:property value="userBean.createTime"/>&nbsp;</span></li>
	            </ul>
	            <ul class="result-list" style="width: 100%;">
	            	<li><span class="result-name">描述信息：</span><span><s:property value="userBean.desc"/>&nbsp;</span></li>
	            </ul>
    		</div>
    		<div class="result" style="margin-top:5px;">
				<h5 class="label">
		            <span class="label-bg">联系信息</span>
		        </h5>
		        <ul class="result-list">
	            	<li><span class="result-name">手机号码：</span>
	            		<span><s:property value="userBean.mobile"/>&nbsp;</span>
	            	</li>
	            	<li><span class="result-name">邮箱：</span>
	            		<span><s:property  value="userBean.email"/>&nbsp;</span>
	            	</li>
	            </ul>
	            <ul class="result-list">
	            	<li><span class="result-name">固定电话：</span>
	            		<span><s:property value="userBean.telphone"/>&nbsp;</span>
	            	</li>
	            	<%--<li><span class="result-name">传真号码：</span>
	            		<span><s:property  value="userBean.fax"/>&nbsp;</span>
	            	</li> --%>
	            	<li><span class="result-name">部门名称：</span>
	            		<span><s:property value="userBean.departmentName"/>&nbsp;</span>
	            	</li>
	            </ul>
	            <ul class="result-list" style="width: 100%;">
	            	<li><span class="result-name">通讯地址：</span><span><s:property value="userBean.address"/>&nbsp;</span></li>
	            </ul>
			</div>
			<div class="result" style="margin-top:5px;">
				<h5 class="label">
		            <span class="label-bg">业务组信息</span>
		        </h5>
		        <ul class="result-list">
	            	<li>
	            		<span class="result-name">绑定的业务组：</span>
		            	<div style="width: 410px;">
		            		&nbsp;<span id="app"></span>
		            	</div>
	                </li>
		        </ul>
		        <ul class="result-list">    
		            <li>
	    				<span class="result-name">状态：</span>
		            	<span id="appStatus"></span>
		            	<%-- <span id="showAppEdit" style="cursor: pointer;color: #0066cc;">(查看全部)</span> --%>
		            </li>
	            </ul>
		        <%--
		        <s:if test="userStatus==0">
		        <!-- 包含未绑定的业务组 -->
		        <ul class="result-list" style="width:80%;">
	            	<li>
	            		<span class="result-name">所属业务组：</span>
	            		<span>
		            	<s:iterator value="userBean.apps" status="apps" id="app">
		            		<s:if test="#app.appBind_status==2">
		            		  <s:property value="#app.appName"/>&nbsp;&nbsp;
		            		</s:if> 
		            	</s:iterator>&nbsp;
	            	   </span>
	               </li>
	            	<li>
	            		<span class="result-name">变更后的所属业务组：</span>
	            		<span>
		            		<s:iterator value="userBean.apps" status="apps" id="app2">
			            		<s:if test="#app2.appBind_status==1">
			            		  <s:property value="#app2.appName"/>&nbsp;&nbsp;
			            		</s:if>
			            	</s:iterator>
	            		</span>
	            	</li>
	            </ul>
	            </s:if>
	            <s:else>
	            	<ul class="result-list" style="width:80%;">
		            	<li><span class="result-name">所属业务组：</span>
			            	<span>
			            	<s:iterator value="userBean.apps" status="apps" id="app">
			            		<s:if test="#app.appBind_status==1">
			            		  <s:property value="#app.appName"/>&nbsp;&nbsp;
			            		</s:if> 
			            	</s:iterator>
	            	  	  </span>
		            	</li>
		            </ul>
	            </s:else>
	             --%>
			</div>
			<div class="result" style="margin-top:5px;">  
				<h5 class="label">
		            <span class="label-bg">审批意见</span>
		        </h5>	
	          <ul class="result-list">
	          		<li>
	          			<span class="result-name">计费用户ID:</span>
	          			<input id="chargeUserId" name="chargeUserId" onchange="setAuditPassStyle()" class="text" type="text" onblur="setAuditPassStyle()" onkeypress="setAuditPassStyle()">
	          		</li>
	          </ul>
		        <ul class="result-list">
		        	<li>
		        	<span class="result-name">审批意见：</span>
		        	</li>
		        	<div style="margin-left:150px;margin-top:-13px;"><textarea cols="31" rows="3" id="auditInfo"></textarea></div>
		        </ul>    
      	  		 
     		</div>
       </div>  
       <div class="page-button">
          <ul class="opt-bottom-btn">
              <li id="passLi" >
                  <a href="javascript:void(0);" id="auditPass" >通过</a>
              </li>
              <li>
                  <a href="javascript:void(0);" id="auditUnPass">不通过</a>
              </li>
              <li>
                  <a href="javascript:void(0);" id="cancel">取消</a>
              </li>
          </ul>
      </div>
    </div>
</div>
<input type="hidden" id="userStatus" name="userStatus" value="${userStatus}"/>
<input type="hidden" id="userId" name="userId" value="${userBean.userId}"/>
<script type="text/javascript" src="../scripts/pagesjs/task/user_detail.js"></script>
<script>
var appAry = new Array();
var approvalApps = new Array();
var index = 0;
var indexNew = 0;
var approvalFlag = false;
<s:iterator value="userBean.apps" status="apps" id="app">
<s:if test="#app.appBind_status==2">
	appAry[index] = '<s:property value="#app.appName"/>';
	
</s:if>
<s:else>
	approvalFlag = true;
	appAry[index] = '<s:property value="#app.appName"/>';
	index++;
	approvalApps[indexNew] = '<s:property value="#app.appName"/>';
	indexNew++;
</s:else>
</s:iterator>
initApps();
</script>