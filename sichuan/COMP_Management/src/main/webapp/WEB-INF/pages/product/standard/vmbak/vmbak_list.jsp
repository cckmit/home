<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
       	<h1>资源规格管理</h1>
       	<div id="message" style="display: none;"><span id="error"
	class="error"><s:actionerror /></span> <span class="error"><s:fielderror /></span>
<span class="success"><s:actionmessage /></span></div>
        <span class="apply"><a href="#" onclick="onVmAdd();">创建虚拟机备份规格</a></span>
      <div class="details-con">
   	    <div class="detail-title">
          <h3 class="apply-title">虚拟机备份规格管理</h3>
        </div>
        <form name="vmbakForm" id="vmbakForm" action="VMBAKStandardListQuery.action" method="post">
        <div class="table-seach">
        	<p>
        	规格名称：<input id="vmStandard" name="standardName" class="text" type="text" size="28" value="<s:property value="standardName" />"/>
            </p>
           <%--  <p>
            	状态：
            	</p>
            	<p>
            <select class="select-min" name="">
            	<option>全部</option>
                <option>同步中</option>
                <option>全部同步成功</option>
                <option>部分同步成功</option>
            </select>
            </p> --%>
            <p>
        	创建时间：<input id="startTime" name="startTime" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" readonly="true" cssClass="Wdate wdatelong" type="text" size="20" value="<s:property value="startTime" />"/>至<input id="endTime" name="endTime" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" readonly="true" cssClass="Wdate wdatelong" value="<s:property value="endTime" />" type="text" size="20"/>
            </p>
            <ul class="opt-btn fr">
                <li><a class="search" href="#" onclick="query()">查询</a></li>
                <%/*<li><a class="import" href="#">导入</a></li>
                <li><a class="export" href="#">导出</a></li>*/ %>
            </ul>
        </div>
        </form>
        <!-- 列表 -->
         <!-- 虚拟机 -->
         <div id="approval-vm" class="table-content table-block">
             <table id="standardTable" width="100%" border="0" cellspacing="0" cellpadding="0">
                <col style="width:15%;" />
                <col style="width:27%;" />
                <col style="width:15%;" />
                <col style="width:17%;" />
                <col style="width:10%;display: none;" />
                <col style="width:26%;" />
              <tr>
                <th class="nl">规格名称</th>
                <th>规格ID</th>
                <th>创建人</th>
                <th>创建时间</th>
                <th style="display: none;">状态</th>
                <th>操作</th>
              </tr>
              <s:iterator value="vmbakStandardInfos">
              	<tr class="iterator"><td><s:property value="standardName"/></td>
                <td id="standardId"><s:property value="standardId"/></td>
                <td><s:property value="createUser"/></td>
                <td><s:property value="createTime"/></td>
                <td style="display: none;">部分同步成功</td>
                <td class="table-opt-block">
                	<a href="#" onclick="onVmDetail('<s:property value="standardId"/>');return false;">详情</a>
                    <a href="#" onclick="onVmModify('<s:property value="standardId"/>');return false;">修改</a>
                    <a href="javascript:void(0);" onclick="onSynchro(this,'<s:property value="standardId"/>');return false;">同步</a>
                    <a href="#" onclick="onDel('<s:property value="standardId"/>');return false;">删除</a>
                </td>
              	</tr>
              </s:iterator>
            </table>
         </div>
         <!-- 翻页 -->
         <div class="pageBar">
            <s:property value="pageBar" escape="false" />
         </div>
      </div>
<!-- 虚拟机弹出层 -->
<div class="page-form" id="vm_div" style="display:none;">
    <div class="section">
        <div id="vm_div_id" class="field">
            <div class="caption">虚拟机备份规格ID：</div>
            <div class="content" id="vm_standard_id">
                CIDC-T-SRV-00000001
            </div>
            <div class="point"></div>
         </div>
        <div class="field">
            <div class="caption">虚拟机备份规格名称：</div>
            <div class="content">
                <input type="text" size="28" id="vm_standard_name" value="" maxlength="50"/>
            </div>
            <div class="point"></div>
         </div>
        <div class="field">
            <div class="caption">空间大小(GB)：</div>
            <div class="content">
                <input type="text" size="28" id="vm_size" value="40" maxlength="10"/>
            </div>
            <div class="point"></div>
        </div>
        <div class="field">
            <div class="caption">虚拟机备份规格描述：</div>
            <div class="content">
                <textarea cols="42" id="vm_remark"></textarea>
            </div>
            <div class="point"></div>
        </div>
    </div>
</div>
<!-- -->
<!-- 同步资源 -->
<div class="page-form" id="synchro_div" style="display:none;">
    <div class="section">
        <div id="vm_div_id" class="field">
            <div class="caption">规格ID：</div>
            <div class="content" id="syn_standardId">
                CIDC-T-SRV-00000001
            </div>
            <div class="point"></div>
         </div>
        <div class="field">
            <div class="caption">规格名称：</div>
            <div class="content" id="syn_standardName">
                经济A型虚拟机模板
            </div>
            <div class="point"></div>
         </div>
    </div>
    <div class="section">
    	<div class="field">
            <div class="caption">资源池：</div>
            <div class="content" id="synchro_table">
            	
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="../scripts/pagesjs/standard/vmbak/vmbak_list.js"></script>