<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript"
	src="../scripts/pagesjs/report/cpuDeviceMonthlyReport.js"></script>
<script type="text/javascript">


	$(function() {
	
		var deviceTypeName = '<s:property value="monthPerformanceInfo.deviceTypeName" />';  
		/* 展示图表 */
		chartData = <s:property value="chartData"/>;
		
		var deviceType = '<s:property value="monthPerformanceInfo.deviceType" />';
		var	chartTitle = '<b>TOP20-' + deviceTypeName + 'cpu平均使用率<b>';
		var	yAxisTitle = deviceTypeName + 'CPU平均使用率(%)';
		var	unit = '%';

		
		$("#chart")[0] = new Charts.Chart({
			chart : {
				renderTo : 'chart', /*加载chart的控件id*/
				defaultSeriesType : 'column',
				backgroundColor : '#fcfcfc',
				style : {
					fontFamily : 'Microsoft YaHei', // default font
					fontSize : '12px'
				}
			},
			title : {
				text : chartTitle,
				style : {
					fontFamily : 'Microsoft YaHei', // default font
					fontSize : '16px'
				}
			},
			xAxis : {
				categories : chartData[0],
				labels : {
					rotation : -20,
					align : 'right',
					style : {
						fontFamily : 'Microsoft YaHei', // default font
						fontSize : '12px'
					}
				}
			},
			yAxis : {
				title : {
					text : yAxisTitle,
					style : {
						fontFamily : 'Microsoft YaHei', // default font
						fontSize : '12px'
					}
				},
				max : 100
			},
			tooltip : {
				formatter : function() {
					var s;
					s = '<b>' + this.x + '</b><br/>';
					s += this.series.name + ': ' + this.point.y + unit + '<br/>';
					return s;
				}
			},

			series : chartData[1]
		});

	
		/* 回填参数 */
		var resPoolId='<s:property value="monthPerformanceInfo.resPoolId"></s:property>';
		var poolPartId='<s:property value="monthPerformanceInfo.poolPartId"></s:property>';
		var orderType='<s:property value="monthPerformanceInfo.orderType"></s:property>';
		var dateType='<s:property value="monthPerformanceInfo.dateType"></s:property>';
		var startDate='<s:property value="monthPerformanceInfo.startDate"></s:property>';
		var endDate='<s:property value="monthPerformanceInfo.endDate"></s:property>';
		if(resPoolId!==''){
			$('#resPoolId').find('option[value="'+resPoolId+'"]').attr("selected","selected");
			selectResPool(resPoolId,poolPartId);
		}
		var curText = $('.charts-title tspan:first').text();
		var tmp = curText.substr(5);
		if (orderType === '0') {
			$('input[name="monthPerformanceInfo.orderType"][value=0]').prop("checked",true);
			$('.charts-title tspan:first').text("Top20" + tmp);
		} else if (orderType === '1') {
			$('input[name="monthPerformanceInfo.orderType"][value=1]').prop("checked",true);
			$('.charts-title tspan:first').text("Low20" + tmp);
		}
		/* 全天，白天，夜间 */
		if(dateType==0){
			$('input[name="monthPerformanceInfo.dateType"][value=0]').prop("checked",true);
		}else if(dateType==1){
			$('input[name="monthPerformanceInfo.dateType"][value=1]').prop("checked",true);
		}else if(dateType==2){
			$('input[name="monthPerformanceInfo.dateType"][value=2]').prop("checked",true);
		}
		$('#startDate').val(startDate);
		$('#endDate').val(endDate);
		
		
		/* ajax取得列表信息 */
        var	performanceType = 'cpu';
		var ajaxActionStr = '/monthPerformanceByAjax.action'
		                  + '?monthPerformanceInfo.deviceType=' + deviceType
		                  + '&monthPerformanceInfo.performanceType=' + performanceType
		                  +'&monthPerformanceInfo.orderType='+orderType
		                  +'&monthPerformanceInfo.dateType='+dateType
		                  +'&monthPerformanceInfo.startDate='+startDate
		                  +'&monthPerformanceInfo.endDate='+endDate;
		if(resPoolId!==''){
			ajaxActionStr=ajaxActionStr+"&monthPerformanceInfo.resPoolId="+resPoolId;
		}
		if(poolPartId!==''){
			ajaxActionStr=ajaxActionStr+"&monthPerformanceInfo.poolPartId="+poolPartId;
		}
		          
        
	    getListData(ajaxActionStr,deviceType);
		
		Initmenu(deviceType);
		
	});

</script>
</head>
<body>
	<h1>
		<s:property value="monthPerformanceInfo.deviceTypeName" />
		月度性能统计
	</h1>

	<!-- 概况 -->
	<div class="details-con">
		<!-- 标题栏-->
		<div class="BussTitle">
			<div class="BussTitleButton" style="width: 450px">
				<a id="cpuUrl" href="#" class="BussTitleButtonFocus"
					onclick="setPerformanceUrl(<s:property value="monthPerformanceInfo.deviceType" />, 'cpu')"><div
						id="cpuDiv" class="">CPU</div></a> <a id="memUrl" href="#"
					class="BussTitleButtonLoseFocus"
					onclick="setPerformanceUrl(<s:property value="monthPerformanceInfo.deviceType" />, 'mem')"><div
						id="memDiv" class="">内存</div></a>
						<s:if test="monthPerformanceInfo.deviceType!=1">
					<a id="pageUrl" href="#"
					class="BussTitleButtonLoseFocus"
					onclick="setPerformanceUrl(<s:property value="monthPerformanceInfo.deviceType" />, 'page')"><div
						id="pageSpaceDiv" class="">PageSpace</div></a>
						</s:if>
						<a id="ioUrl" href="#"
					class="BussTitleButtonLoseFocus"
					onclick="setPerformanceUrl(<s:property value="monthPerformanceInfo.deviceType" />, 'io')"><div
						id="ioDiv" class="">磁盘IO</div></a>
			</div>
		</div>

		<div class="BussHardsoft" style="height: 100px;">
			<s:form id="searchForm" action="cpuDevicePerformanceAction.action"
				method="post">
				<input name="monthPerformanceInfo.deviceType" type="hidden"
					value='<s:property value="monthPerformanceInfo.deviceType"></s:property>' />
				<div style="margin-top: 20px; float: left; width: 100%">
					资源池：
					<s:select id="resPoolId" name="monthPerformanceInfo.resPoolId"
						list="resPoolList" listKey="resPoolId" listValue="resPoolName"
						headerKey="" headerValue="-请选择 -"
						onchange="selectResPool(this.value)" />
					分区： <select id="partId" name="monthPerformanceInfo.poolPartId">
						<option value="" selected="selected">-请选择 -</option>
					</select>
				</div>
				<div style="margin: 10px 0 10px; float: left; width: 100%">
					类型：
					<s:radio list="#{'0':' Top 20','1':' Low 20'}"
						name="monthPerformanceInfo.orderType" value="0" />
					&nbsp;&nbsp; 查询日期：
					<s:textfield id="startDate" name="monthPerformanceInfo.startDate"
						onClick="WdatePicker({dateFmt:'yyyy-MM-dd'});"
						cssClass="Wdate wdatelong" size="15" readonly="true" />
					至
					<s:textfield id="endDate" name="monthPerformanceInfo.endDate"
						onClick="WdatePicker({dateFmt:'yyyy-MM-dd'});"
						cssClass="Wdate wdatelong" size="15" readonly="true" />
					&nbsp;&nbsp;
					<s:radio list="#{'0':' 全天','1':' 白天','2':' 夜间'}"
						name="monthPerformanceInfo.dateType" value="0" />
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a class="reportSearchBtn" href="#"
						onclick="searchReport();">查询</a>
				</div>
			</s:form>
			<div class="BussIllustrate"></div>
		</div>

		<div class="BussPmgTabBg" style="border-bottom: 0px">
			<div class="BussPmg" style="width: 98%;">
				<div id="chart" style="height: 100%"></div>
			</div>
		</div>
		<s:if test=""></s:if>
		<div class="BussPmgTabBg" style="height:450px;">
			<div class="table-sta">
				<table id="listTable" class="BussTable" cellpadding="0"
					cellspacing="0">
					<tr>
						<th><s:property value="monthPerformanceInfo.deviceTypeName" />名称</th>
						<th><s:property value="monthPerformanceInfo.deviceTypeName" />ID</th>
						<s:if test="monthPerformanceInfo.deviceType == 0">
							<th>主机型号</th>
						</s:if>
						<th>主机配置</th>
						<th>操作系统</th>
						<th>业务组名称</th>
						<th>业务组联系人</th>
						<th>性能平均值</th>
						<th>性能最大值</th>
					</tr>
					<tbody id="listTbody">
					</tbody>
				</table>
				<!-- <div id="pageBarDiv" class="pageBarReport"
					style="float: left; margin-left: 200px;"></div> -->
			</div>
		</div>
		<div>&nbsp;</div>
	</div>
	

</body>
</html>