<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
	<div id="message" style="display: none;">
		<span class="error"><s:actionerror /></span>
		<span class="error"><s:fielderror /></span>
		<span class="success"><s:actionmessage /></span>
	</div>
       	<h1>系统配置</h1>
        <span class="apply"><a href="javascript:onAdd();">创建资源池参数</a></span>
      <div class="details-con">
   	    <div class="detail-title">
          <h3 class="apply-title">资源池参数配置</h3>
        </div>
        <div class="table-seach">
        </div>
        <!-- 列表 -->
        <div id="approval-resource" class="table-content table-block">
             <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <col style="width:11%;" />
                <col style="width:12%;" />
                <col style="width:36%;" />
                <col style="width:7%;" />
                <col style="width:11%;" />
                <col style="width:20%;" />
              <tr>
                <th class="nl">资源池编码</th>
                <th>资源池名称</th>
                <th style="display:none">所属区域</th>
                <th>URL</th>
                <th style="display:none">用户名</th>
                <th style="display:none">密码</th>
                <th>状态</th>
                <th>描述</th>
                <th>操作</th>
              </tr>
              <s:if test="resourceInfos==null or resourceInfos.size()==0">
              	<tr>
              		<td colspan="9" align="center">
              			暂无符合数据！
              		</td>
              	</tr>
              </s:if>
              <s:if test="resourceInfos!=null and resourceInfos.size()>0" >
			      <s:iterator value="#request.resourceInfos">
		              <tr>
		                <td><s:property value='resPoolId'/></td>
		                <td><s:property value='resPoolName'/></td>
		                <td style="display:none"><s:property value='resPoolZone'/></td>
		                
		                <td title="<s:property value='resPoolUrl'/>"><s:property value='resPoolUrl'/></td>
		                <td style="display:none"><s:property value='userCode'/></td>
		                <td style="display:none"><s:property value='userPwd'/></td>
		                <td><s:property value='status.desc'/></td>
		                <td title="<s:property value='description'/>"><s:property value='description'/></td>
		                <td class="table-opt-block">
		                	<s:if test="status.code!=3">
		                		<a href="javascript:void(0);" id="buttonModify" onclick="onModify(this);return false;">修改</a>
		                	</s:if>
		                	<s:if test="status.code==1">
		                		<a href="javascript:void(0);" id="buttonPause" onclick="onPause(this,'<s:property value='resPoolId'/>');return false;">暂停</a>
		                	</s:if>
		                	<s:if test="status.code==2">
		                		<a href="javascript:void(0);" id="buttonPause" onclick="onPause(this,'<s:property value='resPoolId'/>');return false;">恢复</a>
		                	</s:if>
		                	<s:if test="status.code!=3">
		                		<a href="javascript:void(0);" id="buttonStop" onclick="onStop(this,'<s:property value='resPoolId'/>');return false;">终止</a>
		                	</s:if>
		               		<a href="javascript:void(0);" onclick="onDel(this,'<s:property value='resPoolId'/>');return false;">删除</a>
		                </td>
		              </tr>
	              </s:iterator>
              </s:if>
            </table>
        </div>
         <!-- 翻页 -->
         <div class="pageBar">
         </div>
      </div>
      <!-- 弹出层 添加系统配置 -->
	<div class="page-form" id="resource_div" style="display:none;">
		<div class="section">
			<div class="field">
	            <div class="caption"><font class="red-font">*</font>资源池编码：</div>
	            <div class="content">
	            	<input type="text" size="28" id="resource_code" value="" maxlength="20"/>
	            </div>
	            <div class="point"></div>
	        </div>
	        <div class="field">
	            <div class="caption"><font class="red-font">*</font>资源池名称：</div>
	            <div class="content">
	                <input type="text" size="28" id="resource_name" value="" maxlength="20"/>
	            </div>
	            <div class="point"></div>
	        </div>
	        <%-- 
	        <div class="field">
	            <div class="caption">所属区域：</div>
	            <div class="content">
	                <select id="resource_zone" class="select-max">
	              	<option value="河北">河北省</option>
	                  <option value="辽宁">辽宁省</option>
	                  <option value="山西">山西省</option>
	                  <option value="江苏">江苏省</option>
	                  <option value="广东">广东省</option>
	              </select>
	            </div>
	            <div class="point"></div>
	        </div>
	        --%>
	        <div class="field">
	            <div class="caption"><font class="red-font">*</font>URL：</div>
	            <div class="content">
	                <input type="text" size="28" id="resource_url" value="" maxlength="100"/>
	            </div>
	            <div class="point"></div>
	        </div>
	        <%--
	        <div class="field">
	            <div class="caption"><font class="red-font">*</font>用户名：</div>
	            <div class="content">
	                <input type="text" size="28" id="resource_user" value="" maxlength="25"/>
	            </div>
	            <div class="point"></div>
	        </div> --%>
	        <div class="field">
	            <div class="caption"><font class="red-font">*</font>密码：</div>
	            <div class="content">
	                <input type="password" size="28" id="resource_pwd" value="" maxlength="50"/>
	            </div>
	            <div class="point"></div>
	        </div>
	        <div class="field">
	            <div class="caption">描述：</div>
	            <div class="content">
	            	<textarea cols="30" id="resource_remark"></textarea>
	            </div>
	            <div class="point"></div>
	        </div>
	    </div>
	</div>
<script type="text/javascript" src="../scripts/pagesjs/system/resource_paramenter_list.js"></script>