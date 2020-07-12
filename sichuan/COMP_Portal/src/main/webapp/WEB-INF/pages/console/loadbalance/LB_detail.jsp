<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<div class="list">
	<div class="title">
		<h2>负载均衡详情</h2>
		<div id="message" style="display: none;">
			<span id="error" class="error"><s:actionerror /></span> <span
				class="error"><s:fielderror /></span> <span class="success"><s:actionmessage /></span>
		</div>
	</div>
	<div class="btns">
		<a class="apply" href="javascript:void(0);"
			onclick="goToLBApplyPage();"><img src="../img/ico-apply.png"
			alt="" />申请负载均衡</a>
	</div>
	<div class="details-con">
		<div class="detail-title <s:if test="LBinfo.status==0">blue</s:if>
		        			<s:if test="LBinfo.status==1">green</s:if>
		        			">
			<div class="status ">
				<span id="status"> <s:if test="LBinfo.status == 0">待创建</s:if>
					<s:elseif test="LBinfo.status == 1">运行中</s:elseif></span>
					<input type="hidden" id="LBinfostatus" value="<s:property value="LBinfo.status" />" />
			</div>
			<h2>
				<s:property value="LBinfo.lbname" />
			</h2>
			<img id="operation" class="operation" src="../img/ico-setting.png" />
			<ul id="vmopt" class="opts">
				<li><a class="del" href="javascript:delLbinfo();"><img
						src="../img/ico-opt-del.png" alt="" /></a></li>

			</ul>
		</div>
		<div class="detail-info">
			<table>
				<tbody>
					<tr>
						<th>名称：</th>
						<td style="word-break: break-all"><span id="lbname"><s:property
									value="LBinfo.lbname" /></span></td>
					</tr>
					<tr>
						<th>负载均衡ID：</th>
						<td><s:property value="LBinfo.LBid" /></td>
						<td><input id="lbid" type="hidden"
							value="<s:property value="LBinfo.LBid" />" /> <input
							id="instanceid" type="hidden"
							value="<s:property value="LBinfo.instanceid" />" /></td>
					</tr>

					<tr>
						<th>负载方式：</th>
						<td><s:if test="LBinfo.LBType==1">单臂</s:if> <s:elseif
								test="LBinfo.LBType==2">双臂</s:elseif> <s:elseif
								test="LBinfo.LBType==3">三角</s:elseif></td>
					</tr>
					<tr>
						<th>策略：</th>
						<td><input type="hidden"
							value="<s:property value="LBinfo.Strategy"/>" id="changeStrategy">
							<s:if test="LBinfo.Strategy==0">基于应用响应时间</s:if> <s:elseif
								test="LBinfo.Strategy==1">原IP地址哈希（HASH）值</s:elseif> <s:elseif
								test="LBinfo.Strategy==2">目的IP地址哈希（HASH）值</s:elseif> <s:elseif
								test="LBinfo.Strategy==3">内容字串哈希值</s:elseif> <s:elseif
								test="LBinfo.Strategy==4">Cookie名称哈希值</s:elseif> <s:elseif
								test="LBinfo.Strategy==5">URL哈希值</s:elseif> <s:elseif
								test="LBinfo.Strategy==6">服务器可用带宽</s:elseif> <s:elseif
								test="LBinfo.Strategy==7">服务器应用连接数</s:elseif> <s:elseif
								test="LBinfo.Strategy==8">服务器负荷</s:elseif> <s:elseif
								test="LBinfo.Strategy==9">轮询</s:elseif> <a
							href="javascript:goToUpdate();"><img width="25px"
								height="25px" src="../img/ico-opts-update.png" /></a></td>
					</tr>
					<tr>
						<th>协议类型：</th>
						<td><s:property value="LBinfo.protocal" /></td>
					</tr>
					<tr>
						<th>所属企业客户：</th>
						<td><s:property value="LBinfo.appname" /> <input
							type="hidden" id="lbappId"
							value="<s:property value="LBinfo.appid" />"></td>
					</tr>
					<tr>
						<th>浮动IP：</th>
						<td><s:property value="LBinfo.lbip" /></td>
						<td><input id="ipType" type="hidden" value="<s:property value="LBinfo.ipType"/>" /></td>
					</tr>
					<tr>
						<th>端口：</th>
						<td><s:property value="LBinfo.lbport" /></td>
					</tr>
					<tr>
						<th>吞吐能力：</th>
						<td><s:property value="LBinfo.Throughput" /> Kbps</td>
					</tr>
					<tr>
						<th>并发链接数：</th>
						<td><s:property value="LBinfo.KbpsConnectNum" /> 个</td>
					</tr>
					<tr>
						<th>链接速度：</th>
						<td><s:property value="LBinfo.NewConnectNum" /> 个/秒</td>
					</tr>
					<tr>
						<th>资源池：</th>
						<td><s:property value="LBinfo.respoolname" /></td>
						<td><input id="resPoolId" type="hidden"
							value="<s:property value="LBinfo.respoolId"/>" /></td>
					</tr>
					<tr>
						<th>分区：</th>
						<td><s:property value="LBinfo.respoolPartname" /></td>
						<td><input id="resPoolPartId" type="hidden"
							value="<s:property value="LBinfo.respoolPartId"/>" /></td>
						<td></td>
					</tr>
		
				</tbody>
			</table>
		</div>

		<div class="detail-tab">
			<div class="tab">
				<a class="active" href="javascript:void(0);"
					data-tab="#watch_tab_info">对象信息</a> <a class="btn"
					href="javascript:addobj();">添加对象</a>
			</div>
			<div class="detail-center">
				<div id="watch_tab_info">
					<div class="#watch_tab_info">
						<table>
							<thead>
								<th>浮动IP</th>
								<th>端口</th>
								<th>对象描述</th>
								<th>创建时间</th>
								<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<s:iterator value="objInfolist">
									<tr>
										<td><s:property value='hostip' /> <input type="hidden"
											value='<s:property value='hostip' />' id="hostip" /> <input
											type="hidden"
											value='<s:property value='objInfolist.size()' />'
											id="objInfolistsize" /> <input type="hidden"
											value='<s:property value='id' />' id="id" /></td>
										<td><s:property value='hostport' /> <input type="hidden"
											value='<s:property value='hostport' />' id="hostport" /></td>
										<td id="objdescription"
											title="<s:property value='objdescription' />"><span><s:property
													value='objdescription' /></span></td>
										<td><s:property value='create_time' /></td>
										<td><span class="span-btn"><a
												href="javaScript:delobjinfo('<s:property value='hostip' />','<s:property value='id' />','<s:property value='hostport' />');"
												style="margin-left: 30px;">删除</a></span></td>
									</tr>
								</s:iterator>
							</tbody>
						</table>
					</div>
				</div>

			</div>

		</div>
		<div class="time">
			<span class="time-begin">创建时间：<i><s:property
						value="LBinfo.createTime" /></i></span><span class="time-end">到期时间：<i><s:property
						value="LBinfo.expireTime" /></i></span>
		</div>
	</div>






	<%-- 添加对象信息  --%>
	<div class="page-form" id="modify_addobj_div" style="display: none;">

		<table>
			<tr>
				<td>对象端口：</td>
				<td><input type="text" id="objport" name="lbport"
					maxlength="10" /></td>
			</tr>

<!-- 			<tr> -->
<!-- 				<td>所属企业用户：</td> -->
<!-- 				<td id="bindDiv"><a href="javascript:bindBusiness();">绑定企业用户</a></td> -->
<!-- 			</tr> -->

<!--        <tr>
				<td>对象IP：</td>

				<td><select id="vlanSelect0" name="vlanId"
					onchange="changeVlan(this)" onclick="checkradioApplyNet()"></select></td>

				<td><select id="ipsegmentSelect0" name="ipSegmentId"
					onchange="changeIpsegment(this)"></select></td>

				<td><select id="ipSelect0" name="lbip"></select></td>
			</tr>   -->

            <tr>
                <td>&nbsp;&nbsp;&nbsp;&nbsp;对象IP：</td>               
                <td><input type="text" id="ipSelect0" name="lbip"
					maxlength="50" /></td>
            </tr>

			<tr>
				<td>对象描述：</td>
				<td><input type="text" id="objdescription" maxlength="30"></td>
			</tr>
			
			<tr>
                <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;提示：</td>
                <s:if test="LBinfo.ipType == 1">
                <td><font color="red">该负载均衡只能添加IPV6类型的对象</font></td></s:if> 
                <s:else>
                <td><font color="red">该负载均衡只能添加IPV4类型的对象</font></td></s:else>                  
            </tr>
		</table>
	</div>

	<!-- 所属业务 -->
	<div id="bindBusiness"  style="display: none;">

		<div class="toolbar">
			<span class="keyName">企业客户名称：</span><input type="text" size="30"
				id="queryBusinessName" /> <a class="search btn fr"
				href="javascript:loadBusinessList('businessListJson.action')">查询</a>
		</div>


		<table class="table" id="businessListTab">
			<thead>
				<tr>
					<th></th>
					<th>企业客户名称</th>
					<th>企业客户描述</th>
				</tr>
			</thead>
			<tbody id="businessListTbody">
			</tbody>
		</table>


		<div class="pageBar" id="businessListPageBarDiv"
			style="text-align: center"></div>
	</div>


	<!-- 修改策略信息 -->
	<div class="page-form" id="modify_updatelbinfo_div"
		style="display: none;">
		<table>
			<tr>
				<td>负载均衡策略：</td>
				<td><select name="Strategy" id="Strategy">
						<option value="0">基于应用响应时间</option>
						<option value="1">原IP地址哈希（HASH）值</option>
						<option value="2">目的IP地址哈希（HASH）值</option>
						<option value="3">内容字串哈希值</option>
						<option value="4">Cookie名称哈希值</option>
						<option value="5">URL哈希值</option>
						<option value="6">服务器可用带宽</option>
						<option value="7">服务器应用连接数</option>
						<option value="8">服务器负荷</option>
						<option value="9">轮询</option>
				</select></td>
			</tr>
		</table>
	</div>
	<script type="text/javascript"
		src="../scripts/pagesjs/console/loadbalance/LB_detail.js"></script>
	<link href="../styles/bindApp.css" rel="stylesheet" type="text/css" />
	<script>
		function s(e, a) {
			if (e && e.preventDefault)
				e.preventDefault();
			else
				window.event.returnValue = false;
			a.focus();
		}
	
		
	</script>