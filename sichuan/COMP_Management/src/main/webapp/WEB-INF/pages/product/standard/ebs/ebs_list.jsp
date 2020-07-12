<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
       	<h1>资源规格管理</h1>
       	<div id="message" style="display: none;"><span id="error"
	class="error"><s:actionerror /></span> <span class="error"><s:fielderror /></span>
<span class="success"><s:actionmessage /></span></div>
        <span class="apply"><a href="#" onclick="onVmAdd();">创建云硬盘规格</a></span>
      <div class="details-con">
   	    <div class="detail-title">
          <h3 class="apply-title">云硬盘规格管理</h3>
        </div>
        <form name="vmForm" id="vmForm" action="EBSStandardListQuery.action" method="post">
        <div class="table-seach">
        	<p>
        	规格名称：<input id="standardName" name="standardName" class="text" type="text" size="28" value="<s:property value="standardName" />"/>
            </p>
            <%/*<p>
            <select class="select-min">
            	<option>全部</option>
                <option>同步中</option>
                <option>全部同步成功</option>
                <option>部分同步成功</option>
            </select>
            </p>*/ %>
            <p>
        	创建时间：<input id="startTime" name="startTime" type="text" size="23" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" readonly="true" class="Wdate wdatelong" value="<s:property value="startTime" />"/>
        	至<input id="endTime" name="endTime" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" readonly="true" class="Wdate wdatelong" value="<s:property value="endTime" />" type="text" size="23"/>
            </p>
            <ul class="opt-btn fr">
                <li><a class="search" href="#" onclick="query()">查询</a></li>
                <%/*<li><a class="import" href="#">导入</a></li>
                <li><a class="export" href="#">导出</a></li>*/ %>
            </ul>
        </div>
        </form>
        <!-- 列表 -->
         <!-- 机 -->
         <div id="approval-vm" class="table-content table-block">
             <table id="standardTable" width="100%" border="0" cellspacing="0" cellpadding="0">
                <col style="width:15%;" />
                <col style="width:24%;" />
                <col style="width:15%;" />
                <col style="width:18%;" />
                <col style="width:10%;display: none;" />
                <col style="width:28%;" />
              <tr>
                <th class="nl">规格名称</th>
                <th>规格ID</th>
                <th>创建人</th>
                <th>创建时间</th>
                <th style="display: none;">状态</th>
                <th>操作</th>
              </tr>
              <s:if test="ebsStandardInfos==null or ebsStandardInfos.size()==0">
              	<tr>
              		<td colspan="6" align="center">
              			暂无符合数据！
              		</td>
              	</tr>
              </s:if>
              <s:else>
	              <s:iterator value="ebsStandardInfos">
	              	<tr class="iterator"><td><s:property value="standardName"/></td>
	                <td id="standardId"><s:property value="standardId"/></td>
	                <td><s:property value="createUser"/></td>
	                <td><s:property value="createTime"/></td>
	                <td style="display: none;">部分同步成功</td>
	                <td class="table-opt-block">
	                	<a href="#" onclick="onVmDetail('<s:property value="standardId"/>');return false;">详情</a>
	                    <%-- <a href="#" onclick="onVmModify('<s:property value="standardId"/>');return false;">修改</a> --%>
	                    <a href="javascript:void(0);" onclick="onSynchro(this,'<s:property value="standardId"/>');return false;">同步</a>
	                    <a href="#" onclick="onDel('<s:property value="standardId"/>');return false;">删除</a>
	                </td>
	              	</tr>
	              </s:iterator>
              </s:else>
            </table>
         </div>
         <!-- 翻页 -->
         <div class="pageBar">
            <s:property value="pageBar" escape="false" />
         </div>
      </div>
<!-- 机弹出层 -->
<div class="page-form" id="vm_div" style="display:none;">
    <div class="section">
        <div id="vm_div_id" class="field">
            <div class="caption">硬盘规格ID：</div>
            <div class="content" id="vm_standard_id">
                CIDC-T-SRV-00000001
            </div>
            <div class="point"></div>
         </div>
        <div class="field">
            <div class="caption">硬盘规格名称：</div>
            <div class="content">
                <input type="text" size="28" id="vm_standard_name" value="经济A型机模板"/>
            </div>
            <div class="point"></div>
         </div>
        <div class="field">
            <div class="caption">硬盘容量(GB)：</div>
            <div class="content">
                <input type="text" size="28" id="vm_size" value="40"/>
            </div>
            <div class="point"></div>
        </div>
        <div class="field">
            <div class="caption">硬盘类型：</div>
            <div class="content" id="type_select">
                <input type="radio" class="radio" id="resourceTypeVm" name="resourceType" value="1" checked/>云硬盘&nbsp;&nbsp;&nbsp;&nbsp;
                <%-- <input type="radio" class="radio" id="resourceTypePm" name="resourceType" value="2" />物理硬盘--%>
            </div>
            <div class="content" id="type_input" style="display: none;">
                <input type="text" size="28" id="resourceType_input" disabled="disabled"/>
            </div>
            <div class="point"></div>
        </div>
        <div class="field">
            <div class="caption">硬盘规格描述：</div>
            <div class="content">
                <textarea cols="42" id="vm_remark"></textarea>
            </div>
            <div class="point"></div>
        </div>
    </div>
    <%--<div class="section" id="synchro_option">
    	<div class="field">
            <div class="caption">资源池：</div>
            <div class="content">
            	<table>
                     <tr>
                        <td>
                        <div class="authBlock zoone">
                          <dt>
                          	<span class="up"></span>
                          	<label><input type="checkbox" name="host" />辽宁资源池</label><label>服务正常</label><label>资源池01</label>
                          </dt>
                          <dd>
                          	<li>
                                <label title="分区01"><input type="checkbox" name="host"/>分区01</label>
                                <label>服务正常</label>
                                <label title="01分区辽宁资源池">01分区...</label>
                            </li>
                          	<li>
                                <label title="分区02"><input type="checkbox" name="host"/>分区02</label>
                                <label>服务正常</label>
                                <label title="01分区辽宁资源池">02分区...</label>
                            </li>
                          	<li>
                                <label title="分区03"><input type="checkbox" name="host"/>分区03</label>
                                <label>服务正常</label>
                                <label title="01分区辽宁资源池">03分区...</label>
                            </li>
                          </dd>
                      </div>
                      </td>
                    </tr>
                    <tr>
                        <td>
                        <div class="authBlock zoone">
                          <dt>
                          	<span class="up"></span>
                          	<label><input type="checkbox" name="host" />广东资源池</label><label>服务正常</label><label>资源池01</label>
                          </dt>
                          <dd>
                          	<li>
                                <label title="分区01"><input type="checkbox" name="host"/>分区01</label>
                                <label>服务正常</label>
                                <label title="01分区广东资源池">01分区...</label>
                            </li>
                          	<li>
                                <label title="分区02"><input type="checkbox" name="host"/>分区02</label>
                                <label>服务正常</label>
                                <label title="01分区广东资源池">02分区...</label>
                            </li>
                          	<li>
                                <label title="分区03"><input type="checkbox" name="host"/>分区03</label>
                                <label>服务正常</label>
                                <label title="01分区广东资源池">03分区...</label>
                            </li>
                          </dd>
                      </div>
                      </td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
    <div class="section" id="synchro_detail_option">
    	<div class="field">
            <div class="caption">资源池：</div>
            <div class="content">
            	<table>
                     <tr>
                        <td>
                        <div class="authBlock zoone">
                          <dt>
                          	<span class="up"></span>
                          	<label>辽宁资源池</label><label>服务正常</label><label>资源池01</label>
                          </dt>
                          <dd>
                          	<li>
                                <label title="分区01">分区01</label>
                                <label>服务正常</label>
                                <label title="01分区辽宁资源池">01分区...</label>
                                <label>可用</label>
                            </li>
                          	<li>
                                <label title="分区02">分区02</label>
                                <label>服务正常</label>
                                <label title="01分区辽宁资源池">02分区...</label>
                                <label>可用</label>
                            </li>
                          	<li>
                                <label title="分区03">分区03</label>
                                <label>服务正常</label>
                                <label title="01分区辽宁资源池">03分区...</label>
                                <label>可用</label>
                            </li>
                          </dd>
                      </div>
                      </td>
                    </tr>
                    <tr>
                        <td>
                        <div class="authBlock zoone">
                          <dt>
                          	<span class="up"></span>
                          	<label>广东资源池</label><label>服务正常</label><label>资源池01</label>
                          </dt>
                          <dd>
                          	<li>
                                <label title="分区01">分区01</label>
                                <label>服务正常</label>
                                <label title="01分区广东资源池">01分区...</label>
                                <label>不可用</label>
                            </li>
                          	<li>
                                <label title="分区02">分区02</label>
                                <label>服务正常</label>
                                <label title="01分区广东资源池">02分区...</label>
                                <label>可用</label>
                            </li>
                          	<li>
                                <label title="分区03">分区03</label>
                                <label>服务正常</label>
                                <label title="01分区广东资源池">03分区...</label>
                                <label class="red-font">同步失败(传输失败)</label>
                            </li>
                          </dd>
                      </div>
                      </td>
                    </tr>
                </table>
            </div>
        </div>
    </div>--%>
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
                经济A型机模板
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
<script type="text/javascript" src="../scripts/pagesjs/standard/ebs/ebs_list.js"></script>