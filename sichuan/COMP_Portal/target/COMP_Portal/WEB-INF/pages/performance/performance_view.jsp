<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="right" class="right">
	<s:hidden id="vmId" name="vmId"></s:hidden>
	<div class="wapper">
		<table width="100%" height="95" cellpadding="0" cellspacing="2">
			<tr>
				<td
					style="border: 1px solid #aaa; background-color: #FFF; vertical-align: top;">
					<div class="rightToolbar">
						<div class="rightToolbarCrumbs">
							<table cellpadding="0" cellspacing="0" height="40">
								<tr>
									<td width="45"><img src="<%=request.getContextPath()%>/themes/performance/images/toolbarIco.png" /></td>
									<td><h2>&nbsp;虚拟机性能管理&nbsp;</h2></td>
								</tr>
							</table>
						</div>
						<ul class="rightToolButtonjhj">
							<li><a id="tab-title-1" href="#" onclick="resource();return false;" class="buttonFoucs">性能指标
							</a></li>
							<li><a id="tab-title-2" href="#" onclick="message();return false;" class="button"> 
								基本信息
							</a></li>
							<li><a id="tab-title-3" href="#" onclick="property();return false;" class="button"> 性能视图 </a></li>
						</ul>
					</div>


					<div id="task1">
						<!-- tab开始 -->
						<div class="rightDisplayJhj">
							<ul>
							</ul>
							<!-- tab结束 -->
						</div>
						<!-- 折线图+表格开始 -->
						<!-- 折线图+表格结束-->
						<!-- task1结束 -->
					</div>


					<div id="task2" style="overflow: auto; background-color: white;">
					</div> 
					<!-- 性能视图 --> <!--内容部分 main star-->
					<div id="performanceProperty" style="overflow: scroll-y; background-color: white;">
						<!--工作区 workarea start-->
						<!--选项卡 tab start-->
						<select id="time" name="time" style="float:left;" onchange="timeChange();">
							<option value="0">最近一周</option>
							<option value="1">最近一个月</option>
							<option value="2">最近三个月</option>
						</select>
						<!--报表开始-->
						<table id="performancePropertyTable"
							style="width: 98%; height: 80%; margin: 5px;">
						</table>
					</div> <!--报表结束--> <!--工作区 workarea end--> <!--内容部分 main end-->

				</td>
			</tr>
		</table>
		<div class="messages succcess" style="top: 25px">
			<div id="msgTip" class="msgSuccess"></div>
		</div>
	</div>

</div>

<style>


.report {
	display: none;
	width: 100%;
}

.reportdiv {
	margin-left: 10px;
	margin-top: 2px;
	width: 98%;
	height: 200px;
}

.reportdiv1 {width: 98%; height: 98%; margin-left: 5px;margin-top: 0px;}

.scroll-pane {
		width: 100%;
		height: 455px;
		overflow: auto;
}

.div{ overflow:visible; height:auto;}
.progressbarNum { float: left; width: 100px;}
.progressbar{ margin-left: 2px; margin-top: 4px; display: true ; width:80px; height:10px; float: left;}


.ou {border-top:1px solid #666; line-height:26px;}
.ed {background:#f2f2f2; border-top:1px solid #666; line-height:26px;}
.stripe_tb tr.alt {border-top:1px solid #666; line-height:26px;}
.stripe_tb tr.ou {background:#f2f2f2; border-top:1px solid #666; line-height:26px;}

</style>

<script type="text/javascript"
	src="../scripts/pagesjs/performance/performance_view.js"></script>