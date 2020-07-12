<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="list">
	<div class="title">
		<h2>负载均衡列表</h2>
		<div id="message" style="display: none;">
			<span id="error" class="error"><s:actionerror /></span> <span
				class="error"><s:fielderror /></span> <span class="success"><s:actionmessage /></span>
		</div>
		<s:form id="gotoForm">
			<input type="hidden" value="<s:property value='msg' />" id="msg" />
			<input type="hidden" value="" id="lbid" name="LBid">
		</s:form>
	</div>
	<s:if test="#session.userInfo.userId=='admin'">
		<div class="btns">
			<a class="apply" href="LBapplyAction.action"><img
				src="../img/ico-apply.png" alt="" />申请负载均衡</a>
		</div>
	</s:if>
	<div class="tool-bar">
		<div class="search-text">
			<img class="search-ico" src="../img/ico-search.png" /> <input
				class="search-text w-auto" type="text" id="lbip" name="privateIp"
				maxlength="50" value="<s:property value="lbip" />"
				style="width: auto;" placeholder="输入IP地址" />
		</div>
		<div class="search-text">
			<img class="search-ico" src="../img/ico-search.png" /> <input
				class="search-text" type="text" id="lbname" name="vmName"
				maxlength="25" value="<s:property value="lbname" />"
				style="width: auto;" placeholder="输入名称" />
		</div>
		<a class="search-btn" href="javascript:void(0);"
			onclick="queryLbBatch()">查询</a>

	</div>
	<div class="list-items lb-list">
		<s:if test="LBinfoList.size()>0">
			<ul id="resultList" class="items">

				<s:iterator value="#request.LBinfoList">

					<li status="<s:property value='status'/>"
						id="s<s:property value='LBid'/>"><a
						href="javascript:void(0);"
						onclick="goToPage('<s:property value='LBid'/>');return false;">
							<div
								class="item-status <s:if test="status==0">blue</s:if>
		        			<s:if test="status==1">green</s:if>">
								<s:if test="status==0">待创建</s:if>
								<s:if test="status==1">运行中</s:if>
							</div>
							<div class="item-content">
								<h3>
									<s:if test="lbname.trim()!=''">
										<s:property value="lbname" />
									</s:if>
									<s:else>&nbsp;</s:else>
								</h3>
								<div class="detail">
									<div class="detail-col">
										<div class="detail-row">
											<span>浮动IP：</span> <span title="<s:property value='lbip'/>"><s:property
													value="lbip" /></span>
										</div>
										<div class="detail-row">
											<span>所属企业客户：</span> <span><s:property value="appname" /></span>
										</div>
									</div>
									<div class="detail-col">
										<div class="detail-row">
											<span>创建时间：</span> 
											<s:if test="status==0"><span></span></s:if>
											<s:else>
											<span
												id="effectiveTime<s:property value='vmId'/>"><s:property
													value="createtime" /></span>
											</s:else>
										</div>
										<div class="detail-row">
											<%-- <span>到期时间：</span> <span
												id="expireTime<s:property value='diskId'/>"><s:property
													value="expireTime" /></span> --%>
										</div>
									</div>

								</div>
							</div>
					</a></li>
				</s:iterator>
			</ul>
			<!-- 翻页 -->
			<div class="pageBar">
				<s:property value="pageBar" escape="false" />
			</div> 
		</s:if>
	</div>
</div>




<script type="text/javascript"
	src="../scripts/pagesjs/console/loadbalance/LB_list.js"></script>
<link href="../styles/bindApp.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" language="JavaScript" charset="UTF-8">
      document.onkeydown=function(event){
            var e = event || window.event || arguments.callee.caller.arguments[0];
            
             if(e && e.keyCode==13){ // enter 键
            	 queryPm();
            }
        }; 
</script>













