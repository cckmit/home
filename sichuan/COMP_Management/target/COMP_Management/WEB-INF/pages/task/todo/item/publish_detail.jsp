<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
      <h1>发布审批</h1>
      <div class="details-con">
   	    <div class="detail-title">
            <a href="javascript:void(0)" id='goBack' title="返回">
                <span class="back"></span>
            </a>
            <h3 class="apply-title">发布审批</h3>
   	    </div>
        <!-- form -->
		<div class="page-form">
        	<div class="section" id="readOnly">
        	<input type="hidden" value="${itemInfo.itemId }" id="itemId" name="itemId">
        	<input type="hidden" value="${itemInfo.standardId }" id="standardId" name="standardId">
        	<input type="hidden" value="${itemInfo.itemType.code }" id="itemType" name="itemType" />
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
                    <div class="caption">
                    	资源规格
                    </div>
                </div>
                <div class="field">
                	<div id="approval-relate-resource" class="table-content table-block">
                         <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <col style="width:26%;" />
                            <col style="width:26%;" />
                            <col style="width:26%;" />
                            <col style="width:11%;" />
                            <col style="width:11%;" />
                          <tr>
                          	<th class="nl">规格名称</th>
                          	<th>物理机型号</th>
                            <th>CPU类型</th>
                            <th>内存容量(MB)</th>
                            <th>磁盘空间(GB)</th>
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
                            <th>业务编码</th>
                          </tr>
                       </table>
                    </div>
                </div>
        	</div>
        	 --%>
            <div class="field">        
      			<div class="caption">审批意见：</div>
      	  		<div class="content"><textarea cols="40" rows="3" id="auditInfo"></textarea></div>
      	 		<div class="point"></div>
     		</div>
            <div class="page-button">
                <ul class="opt-bottom-btn">
                    <li>
                        <a href="javascript:void(0);" id="publishPass">通过</a>
                    </li>
                    <li>
                        <a href="javascript:void(0);" id="publishUnPass">不通过</a>
                    </li>
                    <li>
                        <a href="javascript:void(0);" id="cancel">取消</a>
                    </li>
                </ul>
            </div>
            
        </div>
    </div>
    <!-- form end -->
    <script type="text/javascript">
    	$("#catalogId").val('${itemInfo.catalogId}');
    </script>
<script type="text/javascript" src="../scripts/pagesjs/task/publish_detail.js"></script>
