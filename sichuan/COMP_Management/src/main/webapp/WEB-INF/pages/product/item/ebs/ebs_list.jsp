<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="message" style="display: none;">
	<span class="error"><s:actionerror /></span> <span class="error"><s:fielderror /></span>
	<span class="success"><s:actionmessage /></span>
</div>
<input type="hidden" value="<s:property value='itemCode'/>"
	id="itemCode">
<h1>服务条目管理</h1>
<span class="apply"><a id="apply_host" href="javascript:void(0)">创建云硬盘条目</a></span>
<div class="details-con">
	<div class="detail-title">
		<h3 class="apply-title">云硬盘条目管理</h3>
		<ul class="title-btn">
			<li class="select"><a href="javascript:void(0);"
				onclick="will(this);return false;">未发布</a></li>
			<li><a href="javascript:void(0);"
				onclick="done(this);return false;" id="doneId">已发布</a></li>
		</ul>
	</div>
	<s:form method="post" id="itemModify">
		<input type="hidden" id="itemId" value="" name="itemId" />
	</s:form>
	<div class="table-seach">
		<p>
			条目名称：<input class="text" type="text" size="28" id="itemName"
				maxlength="25" />
		</p>
		<p>
			<label class="fl">条目状态：</label> <select class="select-min"
				id="status">
				<option value="">-请选择-</option>
				<option value="0">已保存</option>
				<option value="1">条目待审</option>
				<option value="2">审批通过</option>
				<option value="3">审批不通过</option>
				<option value="4">发布待审</option>
				<option value="6">发布不通过</option>
			</select>
		</p>
		<p>
			创建时间：<input type="text" id="beginCreateTime"
				onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"
				readonly="true" class="Wdate wdatelong" size="23" maxlength="14" />
			至<input id="endCreateTime"
				onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"
				readonly="true" class="Wdate wdatelong" maxlength="14" type="text"
				size="23" />
		</p>
		<ul class="opt-btn fr">
			<li><a class="search" href="javascript:void(0);" id="search">查询</a>
			</li>
		</ul>
	</div>
	<!-- 列表 -->
	<div id="approval-will" class="table-content table-block">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<!-- <col style="width:5%;" /> -->
			<col style="width: 18%;" />
			<col style="width: 18%;" />
			<col style="width: 18%;" />
			<col style="width: 18%;" />
			<col style="width: 28%;" />
			<tr>
				<!-- <th class="nl"><input type="checkbox"></th> -->
				<th class="nl">条目名称</th>
				<th>条目状态</th>
				<th>创建人</th>
				<th>创建时间</th>
				<th>操作</th>
			</tr>
			<s:if test="itemInfoList.size()>0">
				<s:iterator value="#request.itemInfoList">
					<s:if test="status.code!=5 and status.code!=7">
						<tr>
							<!-- <td><input type="checkbox" name="select" ></td> -->
							<td><s:property value='itemName' /></td>
							<td><s:property value='status.desc' /></td>
							<td><s:property value='createUser' /></td>
							<td><script>document.write(_fomart14("<s:property value='createTime'/>"));</script></td>
							<td class="table-opt-block"><a href="javascript:void(0)"
								onclick="onItemDetail('<s:property value='itemId'/>');return false;">详情</a>
								<s:if
									test="status.code==0 or status.code==3">
									<a href="javascript:void(0);"
										onclick="onItemModify(this,'<s:property value='itemId'/>');return false;">修改</a>
								</s:if> <s:if test="status.code==0">
									<a href="javascript:void(0);"
										onclick="onItemSubmit(this,'<s:property value='itemId'/>');return false;">提交</a>
								</s:if> <s:if test="status.code==2">
									<a href="javascript:void(0);"
										onclick="onItemSend(this,'<s:property value='itemId'/>');return false;">发布</a>
								</s:if> <s:if test="status.code==0">
									<a href="javascript:void(0);"
										onclick="onItemDel(this,'<s:property value='itemId'/>');return false;">删除</a>
								</s:if>
							</td>
						</tr>
					</s:if>
				</s:iterator>
			</s:if>
			<s:else>
				<tr>
					<td colspan="5" align="center">暂无符合数据！</td>
				</tr>
			</s:else>
		</table>
	</div>
	<div id="approval-done" class="table-content table-block"
		style="display: none;">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<col style="width: 5%;" />
			<col style="width: 18%;" />
			<col style="width: 15%;" />
			<col style="width: 15%;" />
			<col style="width: 16%;" />
			<col style="width: 32%;" />
			<tr>
				<!-- <th class="nl"><input type="checkbox"></th> -->
				<th>条目名称</th>
				<th>条目状态</th>
				<th>创建人</th>
				<th>创建时间</th>
				<th>操作</th>
			</tr>
			<s:if test="itemInfoList.size()>0">
				<s:iterator value="#request.itemInfoList">
					<s:if test="status.code==5 or status.code==7">
						<tr>
							<!-- <td><input type="checkbox" name="select" ></td> -->
							<td><s:property value='itemName' /></td>
							<td><s:property value='status.desc' /></td>
							<td><s:property value='createUser' /></td>
							<td><s:property value='createTime' /></td>
							<td class="table-opt-block"><a href="javascript:void(0)"
								onclick="onItemDetail('<s:property value='itemId'/>');">详情</a> <s:if
									test="status.code!=7">
									<a href="javascript:void(0);"
										onclick="onItemDown(this,'<s:property value='itemId'/>')">下架</a>
								</s:if> <s:if test="recommend.code==0 and status.code!=7">
									<a href="javascript:void(0);"
										onclick="onItemCommend(this,'<s:property value='itemId'/>')">推荐</a>
								</s:if> <s:if test="recommend.code==1 and status.code!=7">
									<a href="javascript:void(0);"
										onclick="onItemCommend(this,'<s:property value='itemId'/>')">取消推荐</a>
								</s:if></td>
						</tr>
					</s:if>
				</s:iterator>
			</s:if>
		</table>
	</div>
	<!-- 翻页 -->
	<div class="pageBar">
		<s:property value="pageBar" escape="false" />
	</div>
</div>
<s:if test="itemInfoList==null">
	<script type="text/javascript">
    		$.compMsg({type:'error',msg:'数据库异常！'});
    	</script>
</s:if>
<script type="text/javascript" src="../scripts/pagesjs/item/entry.js"></script>
<script type="text/javascript"
	src="../scripts/pagesjs/item/ebs/ebs_list.js"></script>