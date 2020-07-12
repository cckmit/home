<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
      <h1>订单审批</h1>
      <div class="details-con">
   	    <div class="detail-title">
            <a href="javascript:void(0)" id='goBack' title="返回">
                <span class="back"></span>
            </a>
            <h3 class="apply-title">虚拟硬盘订单审批</h3>
   	    </div>
        <!-- form -->
		<!-- 虚拟硬盘订单详细信息 -->
		<div class="page-form" id="ebs_order_detail">
		<div>
		<div id="vm_div_id" class="field">
		<div class="caption">订单号：</div>
		<div class="content"><input type="text" size="28" id="ebsOrderId"
			value="${orderInfo.orderId}" style="border: 1px;" readonly="readonly" />
		</div>
		<div class="point"></div>
		<div class="caption">状态：</div>
		<div class="content"><input type="text" size="28" id="status"
			value="${orderInfo.status}" style="border: 1px;" readonly="readonly" /></div>
		<div class="point"></div>
		</div>
		<div class="field">
		<div class="caption">资源类型：</div>
		<div class="content"><input type="text" size="28"
			id="stardardType" value="${orderInfo.standardType}" style="border: 1px;"
			readonly="readonly" /></div>
		<div class="point"></div>
		<!--<div class="caption">总价格：</div>
		<div class="content"><input type="text" size="28" id="ebsAllPrice"
			value="${orderInfo.allPrice}" style="border: 1px;" readonly="readonly" /></div>
		<div class="point"></div>
		--></div>
		<div class="field">
		<div class="caption">时长：</div>
		<div class="content"><input type="text" size="28" id="ebsLengthTime"
			value="${orderInfo.lengthTime}" style="border: 1px;" readonly="readonly" /></div>
		<div class="point"></div>
		<div class="caption">时长单位：</div>
		<div class="content"><input type="text" size="28" id="lengthUnit"
			value="${orderInfo.lengthUnit}" style="border: 1px;" readonly="readonly" /></div>
		<div class="point"></div>
		</div>
		<div class="field">
		<div class="caption">硬盘空间(G)：</div>
		<div class="content"><input type="text" size="28" id="ebsDisk"
			value="${stardardInfo.discSize}" style="border: 1px;" readonly="readonly" /></div>
		<div class="point"></div>
		<div class="caption">创建人：</div>
		<div class="content"><input type="text" size="28" id="ebsCreater"
			value="${orderInfo.createUser}" style="border: 1px;" readonly="readonly" /></div>
		<div class="point"></div>
		</div>
		<div class="field">
		<div class="caption">创建时间：</div>
		<div class="content"><input type="text" size="28" id="ebsCreateTime"
			value="${orderInfo.createTime}" style="border: 1px;" readonly="readonly" /></div>
		<div class="point"></div>
		<div class="caption">生效时间：</div>
		<div class="content"><input type="text" size="28"
			id="ebsEffectiveTime" value="${orderInfo.effectiveTime}" style="border: 1px;" readonly="readonly" />
		</div>
		<div class="point"></div>
		</div>
		<div class="field">
		<div class="caption">到期时间：</div>
		<div class="content"><input type="text" size="28" id="ebsExpireTime"
			value="${orderInfo.expireTime}" style="border: 1px;" readonly="readonly" /></div>
		<div class="point"></div>
		</div>
		
		<div class="section">  
		</div>
		<div class="field">        
			<div class="caption">审批意见：</div>
		    <div class="content"><textarea cols="40" rows="3" id="ebsAuditInfo"></textarea></div>
		    <div class="point"></div>
		</div>
		
		</div>

            <div class="page-button">
                <ul class="opt-bottom-btn">
                    <li>
                    	<a href="javascript:void(0);" onclick="auditEbsPass();return false;">通过</a>
                    </li>
                    <li>
                        <a href="javascript:void(0);" onclick="auditEbsNoPass();return false;">不通过</a>
                    </li>
                    <li>
                        <a href="ordersAuditList.action" id="cancel">取消</a>
                    </li>
                </ul>
            </div>
            
        </div>
    </div>
    <!-- form end -->
<script type="text/javascript"
	src="../scripts/pagesjs/task/orders_detail.js"></script>
