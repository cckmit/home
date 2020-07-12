<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<h1>性能统计</h1>
<div id="message" style="display: none;">
	<span id="error" class="error"><s:actionerror /></span>
	<span class="error"><s:fielderror /></span>
	<span class="success"><s:actionmessage /></span>
</div>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="../scripts/pagesjs/resourceManagement/getPerformancelist.js"></script>
<script type="text/javascript">

var x_axisData;//x轴值
var y_axisData;//y轴值
var appId;//业务id
var device_typeNmaeForChart;//设备类型

var appId = '<s:property value="appId" />';
var nodeId = '<s:property value="nodeId" />';
var device_type = '<s:property value="device_type" />';
var treeNodeName = '<s:property value="treeNodeName" />';

var actionStr = '/memUsedPerListAction.action';//业务下性能列表传参
var performanceTbodyId = "memPerTbody";//性能列表tbody id 

//加载图表数据
$(function() {
	x_axisData = <s:property value="x_axisData"/>;
	y_axisData = <s:property value="y_axisData"/>;
	
	device_typeNmaeForChart = <s:property value="device_typeNmaeForChart"/>;

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
        title: {
            text: '<b>Top10<b>'
        },
        xAxis: {
            categories: x_axisData,
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
				text : '内存使用率(%)',
				style:{ "color": "#878787"}
			},
			max : 100
		},
		tooltip: {
            formatter: function() {
                return "<b>"+device_typeNmaeForChart+'名称</b>' +': '+this.x +'<br/>'+
                    '内存使用率' +': '+ this.y  +" %";
            }
        },
        plotOptions : {
			column : {
				pointPadding : 0.2,
				borderWidth : 0
			}
		},
        series: [{
            name: device_typeNmaeForChart +'名称',
            data: y_axisData,
            color:'#7cb5ec'
        }],
		legend : {
			enabled:false,
			margin : 20
		}
    });
	//加载表格数据
	actionStr = "/memUsedPerListAction.action?device_type="+device_type+"&appId="+appId+"&nodeId="+nodeId+"&treeNodeName="+treeNodeName;
	getPerformanceData(actionStr,performanceTbodyId);

});
</script>
</head>
<body>
<s:hidden id="device_type" name="device_type"></s:hidden>
<s:hidden id="nodeId" name="nodeId"></s:hidden>
<s:hidden id="appId" name="appId"></s:hidden>
<s:hidden id="treeNodeName" name="treeNodeName"></s:hidden>
<div style="position: relative;">
	<div style="position: absolute;width: 100%;">
    <h1><s:property value="treeNodeName" />统计</h1>
    <ul class="opt-btn fr" style="margin-top: 23px;">
        <li>
        	 <s:url id="exportAppReportUrl" action="exportAppReportAction"> 
        	 	<s:param name="appId">${appId}</s:param>
        	 	<s:param name="treeNodeName">${treeNodeName}</s:param>
        	 </s:url>
			 <a class="exportReport" href="${exportAppReportUrl}">导出</a>
        </li>
    </ul>
    <!-- 设备数 -->
     <div class="BusStatiEquip">
         <ul>
             <li>
                 <div class="BusStatiEquipList"><img src="../images/PhysicalMachine.png" width="38" height="44" />
                     <p><s:property value="deviceNum.pm_num" /></p>
                 </div>
                 <div class="BusStatiEquipTitl">物理机（台）</div>
             </li>
             <li>
                 <div class="BusStatiEquipList"><img src="../images/VirtualMachine.png" width="38" height="44" />
                 <p><s:property value="deviceNum.vm_num" /></p></div><div class="BusStatiEquipTitl">虚拟机（台）</div>
             </li>
             <li>
                 <div class="BusStatiEquipList"><img src="../images/hardWork.png" width="38" height="44" />
                 <p><s:property value="deviceNum.ebs_num" /></p></div><div class="BusStatiEquipTitl">虚拟硬盘（块）</div>
             </li>
             <!-- <li>
                 <div class="BusStatiEquipList"><img src="../images/MiniPm.png" width="38" height="44" />
                     <p><s:property value="deviceNum.miniPm_num" /></p>
                 </div>
                 <div class="BusStatiEquipTitl">小型机（台）</div>
             </li>
             <li>
                 <div class="BusStatiEquipList"><img src="../images/MiniPmPar.png" width="38" height="44" />
                     <p><s:property value="deviceNum.miniPmPar_num" /></p>
                 </div>
                 <div class="BusStatiEquipTitl">小型机分区（台）</div>
             </li> -->
         </ul>
     </div>
     </div>
     <div style="padding-top: 210px;">
     <div class="details-con" style="height: 510px;"> 
         <div class="BussTitle">
	         <div class="BussTitlefocus1">性能统计</div> 
	         <div class="BussTitleButton">
		         <a id="pmUrl" href="#" onclick="setDeviceTypeUrl('2')"><div id="pmDiv" class="">物理机</div></a>
				 <a id="vmUrl" href="#" onclick="setDeviceTypeUrl('3')"><div id="vmDiv" class="">虚拟机</div></a>
				 <!-- <a id="minipmUrl" href="#" onclick="setDeviceTypeUrl('0')"><div id="minipmDiv" class="">小型机</div></a>
				 <a id="minipmparUrl" href="#" onclick="setDeviceTypeUrl('1')"><div id="minipmparDiv" class="">小型机分区</div></a> -->
	         </div>
         </div>
         <div class="BussHardsoft" >
         	<ul>
            	<li id="cpuLi">
            	    <a id="cpuUrl"  href="#" onclick="setPerformanceUrl('cpu')">CPU</a>
            	</li>
                <li id="memLi" class="BussHardsoftFourc">
                    <a id="memUrl"  href="#" onclick="setPerformanceUrl('mem')">内存</a>
                </li>
                <li id="diskLi">
                    <a id="diskUrl" href="#" onclick="setPerformanceUrl('disk')">磁盘</a>
                </li>
            </ul>
         </div>
         <!--  
         <div class="BussUse" >
         	<div class="BussUseTitl"><b>内存使用率统计</b></div>
         </div>
         -->
         <div class="BussPmgTabBg">
            <div  class="BussPmg"><div id="chart" style="height:100%"></div></div>
            <div style="margin-left: 67%;">
	            <table  class="BussTable" cellpadding="0" cellspacing="0"  >
	                  <col style="width: 50%;" />
			          <col style="width: 50%;" />
			          <tr>
						<th><s:property value="device_typeNameForList" />名称</th>
						<th>内存使用率</th>
					  </tr>
					  <tbody id="memPerTbody">
				      </tbody>
				</table>
				<div id="pageBarDiv" class="pageBarReport" ></div>
		     </div>
        </div>
    </div>
    </div>
    </div>
    <div>&nbsp;</br>&nbsp;</div>
</body>