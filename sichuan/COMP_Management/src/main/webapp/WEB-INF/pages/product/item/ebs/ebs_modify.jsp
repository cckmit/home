<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
       <h1>服务条目管理</h1>
      <div class="details-con">
   	    <div class="detail-title">
            <a href="javascript:void(0)" id='goBack' title="返回">
                <span class="back"></span>
            </a>
          <h3 class="apply-title">修改云硬盘条目</h3>
   	    </div>
        <!-- form -->
		<div class="page-form">
        	<div class="section">
        	<input type="hidden" value="${itemInfo.standardId }" id="oldStandardId" name="oldStandardId">
        	<input type="hidden" value="${itemInfo.itemId }" id="itemId" name="itemId">
        	<input type="hidden" value="${itemInfo.standardId }" id="standardId" name="standardId">
        	<input type="hidden" value="${itemInfo.status.code}" id="statusCode"
				name="statusCode">
            	<div class="field">
                	<div class="caption"><font class="red-font">*</font>条目名称：</div>
                    <div class="content">
                      <input type="text" size="28" value="${itemInfo.itemName }" maxlength="25" id="itemName"/>
                    </div>
                    <div class="point"></div>
                </div>
                <div class="field">
                	<div class="caption"><font class="red-font">*</font>条目分类：</div>
                    <div class="content">
                      <s:select id="catalogId" list="#request.itemTypeInfos" listKey="catalogId" listValue="catalogName" cssClass="select-max"></s:select>
                    </div>
                    <div class="point"></div>
                </div>
            	<%-- <div class="field">
                	<div class="caption">最大资源购买量：</div>
                    <div class="content">
                      <input type="text" size="28" value="${itemInfo.maxNum }" maxlength="10" id="maxNum"/>
                    </div>
                    <div class="point"></div>
                </div>
            	<div class="field">
                	<div class="caption">最小资源购买量：</div>
                    <div class="content">
                      <input type="text" size="28" value="${itemInfo.minNum }" maxlength="10" id="minNum"/>
                    </div>
                    <div class="point"></div>
                </div>
                <div class="field">
                	<div class="caption">销售起始时间：</div>
                    <div class="content">
                      <input type="text" size="28" maxlength="14" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="true" cssClass="Wdate wdatelong" value="${itemInfo.sellStartTime }" id="sellStartTime"/>(如不填写默认当前系统时间,通过后立即生效)
                    </div>
                    <div class="point"></div>
                </div>
                <div class="field">
                	<div class="caption">销售结束时间：</div>
                    <div class="content">
                      <input type="text" size="28" maxlength="14" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="true" cssClass="Wdate wdatelong" value="${itemInfo.sellEndTime }" id="sellEndTime"/>(如不填写长期有效)
                    </div>
                    <div class="point"></div>
                </div> --%>
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
                        <ul class="opt-bottom-btn" style="padding-bottom:0px;">
                            <li  style="width:100px;">
                                <a href="javascript:void(0)" onclick="onRelateResource();return false;">关联资源规格</a>
                            </li>
                        </ul>
                    </div>
                    <div class="content"><font class="red-font">*</font>
                      （一个服务目录条目只能关联一个规格）
                    </div>
                </div>
                <div class="field">
                	<div id="approval-relate-resource" class="table-content table-block">
                         <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <col style="width:30%;" />
                            <col style="width:25%;" />
                            <col style="width:25%;" />
                            <col style="width:20%;" />
                          <tr>
                            <th>规格名称</th>
                            <th>硬盘空间(GB)</th>
                            <th>硬盘类型</th>
                            <th>操作</th>
                          </tr>
                       </table>
                    </div>
                </div>
        	</div>
        	<%--
            <div class="section">
        		<div class="field">
                	<div class="caption">
                        <ul class="opt-bottom-btn" style="padding-bottom:0px;">
                            <li  style="width:100px;">
                                <a href="javascript:void(0)" onclick="onRelateExpenses()">关联资费信息</a>
                            </li>
                        </ul>
                    </div>
                </div>
                <div class="field">
                	<div id="approval-relate-expenses" class="table-content table-block">
                         <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <col style="width:30%;" />
                            <col style="width:25%;" />
                            <col style="width:25%;" />
                            <col style="width:20%;" />
                          <tr>
                            <th class="nl">计费周期</th>
                            <th>单价(元)</th>
                            <th>业务组编码</th>
                            <th>操作</th>
                          </tr>
                       </table>
                    </div>
                </div>
        	</div>
        	 --%>
            <div class="page-button">
                <ul class="opt-bottom-btn">
	                <s:if test="itemInfo.status.code!=3">
	                    <li>
	                        <a href="javascript:void(0)" id="saveItem">保存</a>
	                    </li>
                    </s:if>
                    <li>
                        <a href="javascript:void(0)" id="saveAndSubmitItem">提交</a>
                    </li>
                    <li>
                        <a href="javascript:void(0)" id="cancel">取消</a>
                    </li>
                </ul>
            </div>
        </div>
    </div>
    <!-- form end -->
    <!-- 关联资源模板信息 -->
    <div class="page-form" id="relate_resource_div" style="display:none;">
        <div class="section">
        	<div class="table-seach">
                <p>
                <select class="select-min" id="queryRecommend">
                    <option value="0">未关联</option>
                	<option value="1">已关联</option>
                </select>
                </p>
                <p>规格名称:<input type="text" size="25" id="queryStandardName"/>
                </p>
                <p>硬盘容量(GB):<input type="text" size="8" id="queryDiscSize"/>
                </p>
                <ul class="opt-btn">
                    <li>
                        <a class="search" href="javascript:void(0)" id="search">查询</a>
                    </li>
                </ul>
            </div>
            <div class="field">
                <div class="table-content table-block" style="height:220px;overflow-y: scroll;">
                     <table width="100%" border="0" cellspacing="0" cellpadding="0" id="standardlist">
                     </table>
                </div>
            </div>
        </div>
    </div>
    <!-- 关联资费信息 -->
    <div class="page-form" id="relate_expenses_div" style="display:none;">
        <div class="section">
    	<div class="field">
        	<div class="caption">计费周期：</div>
            <div class="content">
            	<select class="select-max" id="price_type">
                	<option value="小时">小时
                    </option>
                	<option value="天">天
                    </option>
                	<option value="月">月
                    </option>
                	<option value="年">年
                    </option>
                </select>
            </div>
            <div class="point"></div>
        </div>
        <div class="field">
        	<div class="caption">单价(元)：</div>
            <div class="content">
            	<input type="text" size="28" value="" id="expenses_price"/>
            </div>
            <div class="point"></div>
        </div>
        </div>
        <div class="section" style="border:none;">
        	<div class="table-seach">
                <p>业务组编码：<input type="text" size="8"/></p>
                <p>业务组名称：<input type="text" size="8"/></p>
            	<ul class="opt-btn">
                    <li>
                        <a class="search" href="javascript:void(0)">查询</a>
                    </li>
                </ul>
            </div>
            <div class="field">
                <div class="table-content table-block">
                     <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <col style="width:5%;" />
                        <col style="width:25%;" />
                        <col style="width:30%;" />
                        <col style="width:40%;" />
                      <tr>
                      	<th class="nl">&nbsp;</th>
                        <th>业务组编码</th>
                        <th>业务组名称</th>
                        <th>业务组描述</th>
                      </tr>
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
<script type="text/javascript" src="../scripts/pagesjs/item/ebs/ebs_btn.js"></script>
<script type="text/javascript" src="../scripts/pagesjs/item/ebs/search_ebs_standard.js"></script>
<script type="text/javascript" src="../scripts/pagesjs/item/ebs/ebs_modify.js"></script>