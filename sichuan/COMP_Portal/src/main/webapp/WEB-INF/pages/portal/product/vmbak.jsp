<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<h1>虚拟机备份 </h1>
  <!-- 概况 -->
  <div class="product-details">
    <%-- 云主机推荐 --%>
	<s:if test="recommends.size > 0">
      <div class="product-clear-b">
          <div>
              <h2 class="product-h2" id="vmbak_recommend">虚拟机备份推荐</h2>
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
                                <td style="width:14%;">类型:</td>
                                <td style="width:48%;"><span class="itemName"><s:property value="itemName"/></span></td>
                                <td style="width:10%;">描述:</td>
                                <td rowspan="3" class="imgTxt"><s:property value="description"/></td>
                            </tr>
                            <tr>
                                <td>存储:</td>
                                <td><s:property value="discSize"/>G</td>
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <td>&nbsp;</td>
                            </tr>
                        </table>
                    </div>
                    <hr class="product-line" />
                    <div class="product-clear-b">
                        <span class="product-btn-orange"><a href="../console/vmbakApplyAction.action?itemId=<s:property value="itemID"/>">马上订购</a></span>
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
              <h2 class="product-h2" id="vmbak_price">虚拟机备份总览</h2>
              <div class="price_tab">
           		<%--
           		<ul>
               		<li><a href="#vm_tab_info">云硬盘</a></li>
           		</ul>
           		 --%>
              </div>
          </div>
          <hr class="product-w-line" />
          <div>
          	<%-- 虚拟机价目表 --%>
              <div id="vmbak_tab_info" class="table-content product-tab-bottom">
                   <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr class="product-tab-title">
                      <td class="nl">产品类型</td>
                      <td>描述</td>
                      <td>存储</td>
                      <td>操作</td>
                    </tr>
                    <s:if test="vmbakItems == null || vmbakItems.size == 0">
	                    <tr>
	                      <td colspan="6">暂无云硬盘数据</td>
	                    </tr>
					</s:if>
              		<s:iterator value="vmbakItems" status="st">
	                    <tr>
	                      <td class="nl"><s:property value="itemName"/></td>
	                      <td><s:property value="description"/></td>
	                      <td><s:property value="discSize"/>G</td>
	                      <td>
	                      	<span class="product-list-btn"><a href="javascript:vmbak_apply_post('<s:property value='itemID'/>');">马上订购</a></span>
	                      </td>
	                    </tr>
		            </s:iterator>
                  </table>
              </div>
          </div>
       </div>
  </div>
<div style="display:none;">
<form id='vmbakApplyFrom' action='../console/vmbakApplyAction.action' method='post'></form>
</div>
<script type="text/javascript" src="../scripts/pagesjs/portal/product/vmbak.js"></script>
