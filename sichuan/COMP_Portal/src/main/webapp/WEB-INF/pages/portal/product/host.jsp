<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<h1>云主机</h1>
    <%-- 云主机推荐 --%>
    <div>
	<s:if test="recommends.size > 0||pmRecommends.size>0">
      <div class="product-clear-b">
          <div>
              <h2 class="product-h2" id="recommend">云主机推荐</h2>
          </div>
          <hr class="product-w-line" />
          <div class="product-services">
              <%-- 推荐主机 --%>
              <s:iterator value="recommends" status="st">
              <div class="product-two-col">
              	<div class="two-col-margin">
                  	<span class="product-services-title"><a><s:property value="itemName"/></a></span>
                    <div class="two-col-tab">
                        <table>
                            <tr>
                                <td style="width:14%;" nowrap="nowrap">处理器:</td>
                                <td style="width:31%;"><s:property value="cpuNum"/> CPU</td>
                                <td style="width:10%;">系统:</td>
                                <td style="width:45%;" rowspan="3" class="imgTxt"></td>
                            </tr>
                            <tr>
                                <td>内存:</td>
                                <td><s:property value="ramSize"/> M</td>
                            </tr>
                            <tr>
                                <td>存储:</td>
                                <td><s:property value="discSize"/> G</td>
                            </tr>
                        </table>
                    </div>
                    <hr class="product-line" />
                    <div class="product-clear-b">
                        <span class="product-btn-orange"><a href="../console/vmApplyAction.action?itemId=<s:property value="itemID"/>">马上申请</a></span>
                    </div>
                    <div class="product-clear-b"></div>
                </div>
            </div>
            </s:iterator>
            <s:iterator value="pmRecommends" status="st">
              <div class="product-two-col">
              	<div class="two-col-margin">
                  	<span class="product-services-title"><a><s:property value="itemName"/></a></span>
                    <div class="two-col-tab">
                        <table>
                            <tr>
                                <td style="width:14%;">处理器:</td>
                                <td style="width:31%;"><s:property value="cpuNum"/> CPU</td>
                                <td style="width:10%;">系统:</td>
                                <td rowspan="3" class="imgTxt"></td>
                            </tr>
                            <tr>
                                <td>内存:</td>
                                <td><s:property value="ramSize"/> M</td>
                            </tr>
                            <tr>
                                <td>存储:</td>
                                <td><s:property value="discSize"/> G</td>
                            </tr>
                        </table>
                    </div>
                    <hr class="product-line" />
                    <div class="product-clear-b">
                        <span class="product-btn-orange"><a href="../console/pmApplyAction.action?itemId=<s:property value="itemID"/>">马上申请</a></span>
                    </div>
                    <div class="product-clear-b"></div>
                </div>
            </div>
            </s:iterator>
            <div style="clear:both;"></div>
          </div>
      </div>
	</s:if>
	<%-- 价格总览 --%>
      <div id="price_tab" class="product-clear-b">
          <div>
              <h2 class="product-h2" id="price">云主机总览</h2>
              <div class="price_tab">
           		<ul>
               		<li><a href="#vm_tab_info">云主机</a></li>
               		<li><a href="#pm_tab_info">物理机</a></li>
           		</ul>
              </div>
          </div>
          <hr class="product-w-line" />
          <div>
          	<%-- 虚拟机价目表 --%>
              <div id="vm_tab_info" class="table-content product-tab-bottom">
                   <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr class="product-tab-title">
                      <td class="nl" rowspan="2">产品类型</td>
                      <td rowspan="2">机器类型</td>
                      <!-- <td>操作系统</td> -->
                      <td rowspan="2">操作</td>
                    </tr>
                    <tr class="product-tab-title">
                    	<!--  <td style="width:100px;">
                      		<select class="select-max" id="os"></select>
                    	</td>
                    	-->
                    </tr>
                    <s:if test="hostItemMaps == null || hostItemMaps.size == 0">
	                    <tr>
	                      <td colspan="8">暂无主机数据</td>
	                    </tr>
					</s:if>
					<s:iterator value="hostItemMaps" id="column">
				      <s:set name="total" value="#column.value.size"/>
				      <s:iterator value="#column.value" status="s">
				       <tr>
				         <s:if test="#s.first"><td rowspan="${total}" class="nl"><s:property value="#column.key"/></td></s:if>
				         <td><s:property value="itemName"/></td>
	                     <!--  <td><span class="os">LINUX|RHEL6.2|64位</span></td>  -->
	                      <td>
	                      	<span class="product-list-btn"><a href="javascript:vm_apply_post('<s:property value='itemID'/>','vm');">马上申请</a></span>
	                      </td>
	                    </tr>
				      </s:iterator>
					</s:iterator>
                  </table>
              </div>
              <%-- 物理机价目表 --%>
              <div id="pm_tab_info" class="table-content product-tab-bottom">
                   <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr class="product-tab-title">
                      <td class="nl">产品类型</td>
                      <td>机器类型</td>
                      <td>操作</td>
                    </tr>
                    <s:if test="pmHostItemMaps == null || pmHostItemMaps.size == 0">
	                    <tr>
	                      <td colspan="8">暂无主机数据</td>
	                    </tr>
					</s:if>
					<s:iterator value="pmHostItemMaps" id="pmColumn">
				      <s:set name="pmTotal" value="#pmColumn.value.size"/>
				      <s:iterator value="#pmColumn.value" status="s">
				       <tr>
				         <s:if test="#s.first"><td rowspan="${pmTotal}" class="nl"><s:property value="#pmColumn.key"/></td></s:if>
				         <td><s:property value="itemName"/></td>
	                      <td>
	                      	<span class="product-list-btn"><a href="javascript:vm_apply_post('<s:property value='itemID'/>','pm');">马上申请</a></span>
	                      </td>
	                    </tr>
				      </s:iterator>
					</s:iterator>
                  </table>
              </div>
          </div>
       </div>
  </div>
<div style="display:none;">
<form id='vmApplyFrom' action='../console/vmApplyAction.action' method='post'></form>
</div>
<script type="text/javascript" src="../scripts/pagesjs/portal/product/host.js"></script>
