<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script>

</script>

<h1>云硬盘 </h1>
  <!-- 概况 -->
  <div class="product-details">
    <%-- 云主机推荐 --%>
	<s:if test="recommends.size > 0">
      <div class="product-clear-b">
          <div>
              <h2 class="product-h2" id="ebs_recommend">云硬盘推荐</h2>
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
                                <td style="width:38%;"><span class="itemName"><s:property value="itemName"/></span></td>
                                <td style="width:10%;" nowrap="nowrap">描述:</td>
                                <td style="width:38%;" rowspan="2" class="imgTxt"><s:property value="description"/></td>
                            </tr>
                            <tr>
                                <td>存储:</td>
                                <td><s:property value="discSize"/>G</td>
                            </tr>
                        </table>
                    </div>
                    <hr class="product-line" />
                    <div class="product-clear-b">
                        <span class="product-btn-orange"><a href="../console/ebsApplyAction.action?itemId=<s:property value="itemID"/>">马上申请</a></span>
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
              <h2 class="product-h2" id="ebs_price">云硬盘总览</h2>
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
              <div id="ebs_tab_info" class="table-content product-tab-bottom">
                   <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr class="product-tab-title">
                      <td class="nl">产品类型</td>
                      <td>云硬盘类型</td>
                      <td>描述</td>
                      <td>存储</td>
                      <td>操作</td>
                    </tr>
                    <s:if test="ebsItems == null || ebsItems.size == 0">
	                    <tr>
	                      <td colspan="6">暂无云硬盘数据</td>
	                    </tr>
					</s:if>
					<s:iterator value="ebsItemMaps" id="column">
				      <s:set name="total" value="#column.value.size"/>
				      <s:iterator value="#column.value" status="s">
				       <tr>
				         <s:if test="#s.first"><td rowspan="${total}" class="nl"><s:property value="#column.key"/></td></s:if>
				         <td><s:property value="itemName"/></td>
	                      <td><s:property value="description"/></td>
	                      <td><s:property value="discSize"/>G</td>
	                      <td>
	                      	<span class="product-list-btn"><a href="javascript:ebs_apply_post('<s:property value='itemID'/>');">马上申请</a></span>
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
<form id='ebsApplyFrom' action='../console/ebsApplyAction.action' method='post'></form>
</div>
<script type="text/javascript" src="../scripts/pagesjs/portal/product/ebs.js"></script>
