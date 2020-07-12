<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!-- 弹出层 添加系统配置 -->
<div class="page-form" id="resource_div" style="display:none;">
    <div class="section">
        <div class="field">
            <div class="caption"><font class="red-font">*</font>授权条件：</div>
            <div class="content">
                <input type="text" size="28" id="resource_code" value="" maxlength="20"/>
            </div>
            <div class="point"></div>
        </div>
        <div class="field">
            <div class="caption"><font class="red-font">*</font>授权模式：</div>
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
            <div class="caption"><font class="red-font">*</font>审批人：</div>
            <div class="content">
                <input type="password" size="28" id="resource_pwd" value="" maxlength="50"/>
            </div>
            <div class="point"></div>
        </div>
        <div class="field">
            <div class="caption">申请原因：</div>
            <div class="content">
                <textarea cols="30" id="resource_remark"></textarea>
            </div>
            <div class="point"></div>
        </div>
    </div>
</div>
<%--
<script type="text/javascript" src="../scripts/pagesjs/system/resource_paramenter_list.js"></script>--%>
