<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
       <h1>服务条目管理</h1>
      <div class="details-con">
   	    <div class="detail-title">
            <a href="javascript:void(0)" id='goBack' title="返回">
                <span class="back"></span>
            </a>
          <h3 class="apply-title">虚拟机备份条目详情</h3>
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
            	<div class="field">
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
                      <input type="text" size="28" maxlength="14" value="${itemInfo.sellStartTime }" id="sellStartTime"/>(如不填写默认当前系统时间,通过后立即生效)
                    </div>
                    <div class="point"></div>
                </div>
                <div class="field">
                	<div class="caption">销售结束时间：</div>
                    <div class="content">
                      <input type="text" size="28" maxlength="14" value="${itemInfo.sellEndTime }" id="sellEndTime"/>(如不填写长期有效)
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
                    <div class="content">
                      <strong id="auditTime"></strong>
                    </div>
                    <div class="point"></div>
                </div>
                <div class="field">
                	<div class="caption">条目审批人：</div>
                    <div class="content">
                      <strong id="auditUser"></strong>
                    </div>
                    <div class="point"></div>
                </div>
                <div class="field">
                	<div class="caption">条目审批意见：</div>
                    <div class="content">
                      <strong id="auditInfo"></strong>
                    </div>
                    <div class="point"></div>
                </div>
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
            </div>
            <div class="section">
        		<div class="field">
                    <div class="caption">
                    	资源模板
                    </div>
                </div>
                <div class="field">
                	<div id="approval-relate-resource" class="table-content table-block">
                         <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <col style="width:50%;" />
                            <col style="width:50%;" />
                          <tr>
                            <th>规格名称</th>
                            <th>空间大小(GB)</th>
                          </tr>
                       </table>
                    </div>
                </div>
        	</div>
        	<%--
            <div class="section">
        		<div class="field">
                	<div class="caption">
                    	资费信息
                    </div>
                </div>
                <div class="field">
                	<div id="approval-relate-expenses" class="table-content table-block">
                         <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <col style="width:30%;" />
                            <col style="width:25%;" />
                            <col style="width:25%;" />
                          <tr>
                            <th class="nl">计费周期</th>
                            <th>单价(元)</th>
                            <th>业务组编码</th>
                          </tr>
                       </table>
                    </div>
                </div>
        	</div>
        	 --%>
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
<script type="text/javascript" src="../scripts/pagesjs/item/vmbak/vmbak_btn.js"></script>
<script type="text/javascript" src="../scripts/pagesjs/item/vmbak/search_vmbak_standard.js"></script>
<script type="text/javascript" src="../scripts/pagesjs/item/vmbak/vmbak_detail.js"></script>