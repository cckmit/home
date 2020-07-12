<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
	<div id="message" style="display: none;">
		<span class="error"><s:actionerror /></span>
		<span class="error"><s:fielderror /></span>
		<span class="success"><s:actionmessage /></span>
	</div>
       	<h1>系统配置</h1>
        <span class="apply"><a href="javascript:onAdd();">创建资源池分区参数</a></span>
      <div class="details-con">
   	    <div class="detail-title">
          <h3 class="apply-title">资源池分区管理</h3>
        </div>
        <div class="table-seach">
        </div>
        <!-- 列表 -->
        <div id="approval-resource" class="table-content table-block">
             <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <col style="width:10%;" />
                <col style="width:15%;" />
                <col style="width:15%;" />
                <col style="width:19%;" />
                <col style="width:10%;" />
                <col style="width:10%;" />
                <col style="width:10%;" />
                <col style="width:15%;" />
              <tr>
                <th class="nl">资源池编码</th>
                <th>资源池名称</th>
                <th>分区名称</th>
                <th>描述</th>
                <th>每虚机VCPU上限</th>
                <th>每虚机内存上限</th>
                <th>每虚机磁盘上限</th>
                <th>操作</th>
              </tr>
              <s:if test="resourcePartInfos==null or resourcePartInfos.size()==0">
              	<tr>
              		<td colspan="5" align="center">
              			暂无符合数据！
              		</td>
              	</tr>
              </s:if>
              <s:if test="resourcePartInfos!=null and resourcePartInfos.size()>0" >
			      <s:iterator value="#request.resourcePartInfos">
		              <tr>
		                <td><s:property value='resPoolId'/></td>
		                <td><s:property value='resPoolName'/></td>
		                <td><s:property value='resPoolPartName'/></td>
		                <td><s:property value='description'/></td>
		                <td><s:property value='cpuNumTotal'/></td>
		                <td><s:property value='ramSizeTotal'/></td>
		                <td><s:property value='discSizeTotal'/></td>
		                <td class="table-opt-block">
		                	<a href="javascript:void(0)" onclick="onModify(this,'<s:property value='resPoolPartId'/>');return false;">修改</a>
							<a href="javascript:void(0);" onclick="onDel(this,'<s:property value='resPoolId'/>','<s:property value='resPoolPartId'/>');return false;">删除</a>
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
	            <div class="caption">资源池名称：</div>
	            <div class="content">
	            	<select id="resource_name" class="select-max">
	                	<option value="">--请选择--
	                    </option>
	                	<option value="CIDC-RP-01">辽宁资源池
	                    </option>
	                    <option value="CIDC-RP-02">广东资源池
	                    </option>
	                </select>
	            </div>
	            <div class="point"></div>
	        </div>
	        <div class="field">
	            <div class="caption">分区编码：</div>
	            <div class="content">
	                <input type="text" size="28" id="resource_code" value="" maxlength="20"/>
	                <input type="hidden" size="28" id="resource_old_code" value=""/>
	            </div>
	            <div class="point"></div>
	        </div>
	        <div class="field">
	            <div class="caption">分区名称：</div>
	            <div class="content">
	                <input type="text" size="28" id="resource_list" value="" maxlength="20"/>
	            </div>
	            <div class="point"></div>
	        </div>
	        <div class="field">
	            <div class="caption">每虚机VCPU上限(个)：</div>
	            <div class="content">
	                <input type="text" size="28" id="cpuNumTotal" value="" maxlength="10"/>
	            </div>
	            <div class="point"></div>
	        </div>
	        <div class="field">
	            <div class="caption">每虚机内存上限(MB)：</div>
	            <div class="content">
	                <input type="text" size="28" id="ramSizeTotal" value="" maxlength="10"/>
	            </div>
	            <div class="point"></div>
	        </div>
	        <div class="field">
	            <div class="caption">每虚机磁盘上限(GB)：</div>
	            <div class="content">
	                <input type="text" size="28" id="discSizeTotal" value="" maxlength="10"/>
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
<script type="text/javascript" src="../scripts/pagesjs/system/resource_zone_list.js"></script>
