<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="list">
	<div class="title">
		<h2>云主机批量修改</h2>
		<div id="message" style="display: none;">
			<span id="error" class="error"><s:actionerror /></span> <span
				class="error"><s:fielderror /></span> <span class="success"><s:actionmessage /></span>
		</div>
	</div>
	<div class="tool-bar">
		<div class="search-text">
			<img class="search-ico" src="../img/ico-search.png" /> <input
				class="search-text" type="text" id="batchVmName" name="batchVmName"
				value="<s:property value="batchVmName" />" style="width: auto;"
				placeholder="输入名称" />
		</div>
		<div class="search-text">
			<img class="search-ico" src="../img/ico-search.png" /> <input
				class="search-text w-auto" type="text" id="batchVmIp"
				name="batchVmIp" value="<s:property value="batchVmIp" />"
				style="width: auto;" placeholder="输入IP地址" />
		</div>

		<div class="search-text">
			<select id="batchVmFlag">
				<option value="notSelect">--修改结果--</option>
				<option value="1">修改成功</option>
				<option value="0">修改失败</option>
			</select>
		</div>

		<a class="search-btn" href="javascript:void(0);"
			onclick="queryVmBatch()">查询</a>

	</div>
	<div class="list-items batchVm-list">
		<s:if test="batchVMInfoList.size()>0">
			<ul id="resultList" class="items">

				<s:iterator value="#request.batchVMInfoList">

					<li>
							<div
								class="item-status <s:if test='vmModifyFlag.equals("1")'>green</s:if>
		        			<s:else>red</s:else>">
								<s:if test='vmModifyFlag.equals("1")'>
									修改成功
								</s:if>
								<s:else>
									修改失败
								</s:else>
							</div>
							<div class="item-content">
								<h3>
									<s:property value="vmName" />
								</h3>
								<div class="detail">
									<div class="detail-col">
										<div class="detail-row">
											<span>IP地址：</span> <span><s:property value="vmIP" /></span>
										</div>
										<div class="detail-row">
											<span>结果描述：</span> <span><s:property
													value="modifyDesc" /></span>
										</div>
									</div>
									<div class="detail-col">
										<div class="detail-row">
											<span>修改时间：</span> <span><s:property
													value="createTime" /></span>
										</div>
									</div>

								</div>
							</div>
					</li>
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
	src="../scripts/pagesjs/console/host/batch_modify_vm_list.js"></script>

