<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
       <h1>服务条目管理</h1>
      <div class="details-con">
   	    <div class="detail-title">
            <a href="javascript:void(0)" id='goBack' title="返回">
                <span class="back"></span>
            </a>
          <h3 class="apply-title">虚拟机条目详情</h3>
   	    </div>
   	    <s:form method="post" id="formId">
   	    	<input type="hidden" id="msg" name="msg" value="" />
   	    	<input type="hidden" id="errMsg" name="errMsg" value="" />
   	    	<input type="hidden" id="itemCode" name="itemCode" value="${itemInfo.status.code }" />
   	    </s:form>
        <!-- form -->
		<div class="page-form">
        	<div class="section">
        	<input type="hidden" value="${itemInfo.standardId }" id="oldStandardId" name="oldStandardId">
        	<input type="hidden" value="${itemInfo.itemId }" id="itemId" name="itemId">
        	<input type="hidden" value="${itemInfo.standardId }" id="standardId" name="standardId">
            	<div class="field">
                	<div class="caption">条目名称：</div>
                    <div class="content">
                      <input type="text" size="28" value="${itemInfo.itemName }" maxlength="25" id="itemName"/>
                    </div>
                    <div class="point"></div>
                </div>
                <div class="field">
                	<div class="caption">条目分类：</div>
                    <div class="content">
                      <s:select id="catalogId" list="#request.itemTypeInfos" listKey="catalogId" listValue="catalogName" cssClass="select-max"></s:select>
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
                	<div class="caption">条目审批时间：</div>
                    <div class="content" style="width: 200px;">
                      <span id="auditTime"></span>
                    </div>
                    <div class="caption">发布审批时间：</div>
                    <div class="content">
                      <span id="releaseTime"></span>
                    </div>
                    <div class="point"></div>
                </div>
                <div class="field">
                	<div class="caption">条目审批人：</div>
                    <div class="content" style="width: 200px;">
                      <span id="auditUser"></span>
                    </div>
                    <div class="caption">发布审批人：</div>
                    <div class="content">
                      <span id="releaseUser"></span>
                    </div>
                    <div class="point"></div>
                </div>
                <div class="field">
                	<div class="caption">条目审批意见：</div>
                    <div class="content" style="width: 200px;">
                      <span id="auditInfo"></span>
                    </div>
                    <div class="caption">发布审批意见：</div>
                    <div class="content">
                      <span id="releaseInfo"></span>
                    </div>
                    <div class="point"></div>
                </div>
                <%-- 
                <div class="field">
                	<div class="caption">发布审批时间：</div>
                    <div class="content">
                      <strong id="releaseTime"></strong>
                    </div>
                    <div class="point"></div>
                </div>
                <div class="field">
                	<div class="caption">发布审批人：</div>
                    <div class="content">
                      <strong id="releaseUser"></strong>
                    </div>
                    <div class="point"></div>
                </div>
                <div class="field">
                	<div class="caption">发布审批意见：</div>
                    <div class="content">
                      <strong id="releaseInfo"></strong>
                    </div>
                    <div class="point"></div>
                </div>
                --%>
            </div>
            <div class="section">
        		<div class="field">
                    <div class="caption">
                    	资源规格
                    </div>
                </div>
                <div class="field">
                	<div id="approval-relate-resource" class="table-content table-block">
                         <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <col style="width:20%;" />
                            <col style="width:20%;" />
                            <col style="width:20%;" />
                            <col style="width:30%;" />
                          <tr>
                            <th class="nl">规格名称</th>
                            <th>CPU数量(个)</th>
                            <th>内存容量(MB)</th>
                            <th>磁盘空间(GB)</th>
                          </tr>
                       </table>
                    </div>
                </div>
        	</div>
            <div class="page-button">
                <ul class="opt-bottom-btn">
                    <li>
                        <a href="javascript:void(0)" id="cancel">取消</a>
                    </li>
                </ul>
            </div>
        </div>
    </div>
    <!-- form end -->
    <script type="text/javascript">
    	$("#catalogId").val("${itemInfo.catalogId}");
    </script>
<script type="text/javascript" src="../scripts/pagesjs/item/entry.js"></script>
<script type="text/javascript" src="../scripts/pagesjs/item/vm_btn.js"></script>
<script type="text/javascript" src="../scripts/pagesjs/item/search_standard.js"></script>
<script type="text/javascript" src="../scripts/pagesjs/item/vm_detail.js"></script>