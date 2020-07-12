<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<h1>所有资源</h1>
<div id="message" style="display: none;">
	<span id="error" class="error"><s:actionerror /></span> <span
		class="error"><s:fielderror /></span> <span class="success"><s:actionmessage /></span>
</div>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="../scripts/lib/echarts/echarts.js"></script>
<script type="text/javascript"
	src="../scripts/pagesjs/resourceManagement/allResource.js"></script>
<style>
.BussTitle1{ width:200px;}
.BussTitlefocus1{ width:200px; }
.BussTitle2{width:240px;}
.BussTitlefocus2{ width:240px; }
</style>
<script type="text/javascript">
$(document).ready(function() { 
	require.config({
		paths : {
			echarts : './../scripts/lib/echarts'
		}
	});

	require([ 'echarts', 'echarts/theme/macarons', 'echarts/chart/gauge' ],
			function(ec, theme) {
				//VCPU
				//var vcpu_resUsed = <s:property value="vcpu.resUsed"/>;
				//var vcpu_resTotal = <s:property value="vcpu.resTotal"/>;

				//内存使用情况
				//var memory_resUsed = <s:property value="memory.resUsed"/>;
				//var memory_resTotal = <s:property value="memory.resTotal"/>;

				//磁盘使用情况
				//var disk_resUsed = <s:property value="disk.resUsed"/>;
				//var disk_resTotal = <s:property value="disk.resTotal"/>;
				
				//var pm_resUsed = <s:property value="pm.resUsed"/>;
				//var pm_resTotal = <s:property value="pm.resTotal"/>;
				
				var i = 1;
				<s:iterator value="partsList">
				
				//VCPU
				var vcpu_resUsed = <s:property value="vcpuResUsed"/>;
				var vcpu_resTotal = <s:property value="vcpuResTotal"/>;

				//内存使用情况
				var memory_resUsed = <s:property value="memoryResUsed"/>;
				var memory_resTotal = <s:property value="memoryResTotal"/>;

				//磁盘使用情况
				var disk_resUsed = <s:property value="diskResUsed"/>;
				var disk_resTotal = <s:property value="diskResTotal"/>;
				

				//PM使用情况
				//var pm_resUsed = <s:property value="pmResUsed"/>;
				//var pm_resTotal = <s:property value="pmResTotal"/>;
				
				var poolchartName1 = "poolchart" + i;
				i++;
				var poolchartName2 = "poolchart" + i;
				i++;
				var poolchartName3 = "poolchart" + i;
				i++;
				var oDiv = document.createElement('div');
				oDiv.className = 'resouseCapacity';
			    oDiv.innerHTML = "<div class='resouseCapacityTitel'><s:property value='poolPartName'/>容量</div><div style='width: 100%; margin: 10px;'><div id='"+poolchartName1+"'style='width: 33%; float:left; height: 270px;  text-align: center;'></div><div id='"+poolchartName2+"'style='width: 33%; float:left; height: 270px;  text-align: center;'></div><div id='"+poolchartName3+"'style='width: 33%; float:left; height: 270px;  text-align: center;'></div></div>";
			    $("#partsId").after(oDiv);

				var poolchart1 = ec.init(document.getElementById(poolchartName1),
					theme);
				poolchart1.setOption({
				tooltip : {
					formatter : "{b} <br/>{a} : {c}"
				},
				series : [
						{
							name : '已使用(个)',//指标名称
							type : 'gauge',//图表类型
							radius: [0, '99%'],
							min : 0,//仪表盘上左侧最小值
							max : vcpu_resTotal*2,//仪表盘上右侧最大值
							splitNumber : 8,// 分割段数
							detail : {
								formatter : '{value}个',
								textStyle: {
							        color: 'auto',
							        fontSize : 25
							    }
							},// 仪表盘文本格式
							data : [ {
								value : vcpu_resUsed,
								name : 'VCPU'
							} ],//仪表盘显示值
							axisLine : { // 坐标轴线
								show : true, // 默认显示，属性show控制显示与否
//								lineStyle : { // 属性lineStyle控制线条样式
//									color : [ [ 0.7, '#5AB1EF' ],
//											[ 1, '#D87A80' ] ]
//								//超过70%显示红色
//								}
							}
						}]
				});
				var poolchart2 = ec.init(document.getElementById(poolchartName2),
						theme);
					poolchart2.setOption({
					tooltip : {
						formatter : "{b} <br/>{a} : {c}"
					},
					series : [
							{
								name : '已使用(GB)',//指标名称
								type : 'gauge',//图表类型
								radius: [0, '99%'],
								min : 0,//仪表盘上左侧最小值
								max : memory_resTotal,//仪表盘上右侧最大值
								splitNumber : 8,// 分割段数
								detail : {
									formatter : '{value}GB',
									textStyle: {
								        color: 'auto',
								        fontSize : 25
								    }
								},// 仪表盘文本格式
								data : [ {
									value : memory_resUsed,
									name : '内存'
								} ],//仪表盘显示值
								axisLine : { // 坐标轴线
									show : true, // 默认显示，属性show控制显示与否
//									lineStyle : { // 属性lineStyle控制线条样式
//										color : [ [ 0.7, '#5AB1EF' ],
//												[ 1, '#D87A80' ] ]
//									//超过70%显示红色
//									}
								}
							}]
					});
					var poolchart3 = ec.init(document.getElementById(poolchartName3),
							theme);
						poolchart3.setOption({
						tooltip : {
							formatter : "{b} <br/>{a} : {c}"
						},
						series : [
								{
									name : '已使用(GB)',//指标名称
									type : 'gauge',//图表类型
									radius: [0, '99%'],
									min : 0,//仪表盘上左侧最小值
									max : disk_resTotal,//仪表盘上右侧最大值
									splitNumber : 8,// 分割段数
									detail : {
										formatter : '{value}GB',
										textStyle: {
									        color: 'auto',
									        fontSize : 25
									    }
									},// 仪表盘文本格式
									data : [ {
										value : disk_resUsed,
										name : '存储'
									} ],//仪表盘显示值
									axisLine : { // 坐标轴线
										show : true, // 默认显示，属性show控制显示与否
//										lineStyle : { // 属性lineStyle控制线条样式
//											color : [ [ 0.7, '#5AB1EF' ],
//													[ 1, '#D87A80' ] ]
//										//超过70%显示红色
//										}
									}
								} ]
						});
						//  查询资源池物理机使用情况 江西业支不需要展示
						//var pmchart = ec.init(document.getElementById('pmchart'),
						//theme);
						//pmchart
						//.setOption({
						//	tooltip : {
						//		formatter : "{b} <br/>{a} : {c}"
						//	},
						//	series : [ {
						//		name : '已使用(台)',//指标名称
						//		type : 'gauge',//图表类型
						//		radius: [0, '100%'],
						//		//center : [ '45%', '45%' ],
						//		min : 0,//仪表盘上左侧最小值
						//		max : pm_resTotal,//仪表盘上右侧最大值
						//		detail : {
						//			formatter : '{value}台',
						//			textStyle: {
						//		        color: 'auto',
						//		        fontSize : 25
						//		    }
						//		},// 仪表盘文本格式
						//		data : [ {
						//			value : pm_resUsed,
						//			name : '物理机'
						//		} ],//仪表盘显示值
						//		axisLine : { // 坐标轴线
						//			show : true, // 默认显示，属性show控制显示与否
						//			lineStyle : { // 属性lineStyle控制线条样式
						//				color : [ [ 0.7, '#5AB1EF' ],
						//						[ 1, '#D87A80' ] ]
						//			//超过70%显示红色
						//			}
						//		}
						//	} ]
						//});

				
			    </s:iterator>
				
				
			});
	
}); 
</script>
</head>
<body>
<s:hidden id="resPoolId" name="resPoolId"></s:hidden>
	<h1>所有资源统计</h1>
	<ul class="opt-btn fr" style="margin-top: 23px;">
        <li>
        	 <s:url id="exportResReportUrl" action="exportResReportAction"> </s:url>
			 <a class="exportReport" href="${exportResReportUrl}">导出统计数据</a>
        </li>
        <li>
        	<s:url id="exportAllResUrl" action="exportAllResAction"> </s:url>
			<a class="exportReport" href="${exportAllResUrl}">导出所有资源详情</a>
        </li>
    </ul> 
	<!-- 概况 -->
	<div class="details-con">
		<!-- 标题栏-->
		<div class="BussTitle">
		<s:iterator value="pools">
			<a id="<s:property value='resPoolId'/>A" href="#" onclick="selectPool('<s:property value='resPoolId'/>')">
				<div id="<s:property value='resPoolId'/>Div" class="poolDiv">
					<s:property value='resPoolName'/>
				</div>
			</a>
		</s:iterator>
		</div>
		<div class="resouseEquip">
			<ul>
				<li>
					<div class="BusStatiEquipList">
						<img src="../images/PhysicalMachine.png" width="38" height="44" />
						<p>
							<s:property value="deviceNum.pmCount" />
						</p>
					</div>
					<div class="BusStatiEquipTitl">物理机（台）</div>
				</li>
				<li>
					<div class="BusStatiEquipList">
						<img src="../images/VirtualMachine.png" width="38" height="44" />
						<p>
							<s:property value="deviceNum.vmCount" />
						</p>
					</div>
					<div class="BusStatiEquipTitl">虚拟机（台）</div>
				</li>
				<li>
					<div class="BusStatiEquipList">
						<img src="../images/hardWork.png" width="38" height="44" />
						<p>
							<s:property value="deviceNum.ebsCount" />
						</p>
					</div>
					<div class="BusStatiEquipTitl">云硬盘（块）</div>
				</li>
				<!-- <li>
					<div class="BusStatiEquipList">
						<img src="../images/MiniPm.png" width="38" height="44" />
						<p>
							<s:property value="deviceNum.miniPmCount" />
						</p>
					</div>
					<div class="BusStatiEquipTitl">小型机（台）</div>
				</li>
				<li>
					<div class="BusStatiEquipList">
						<img src="../images/MiniPmPar.png" width="38" height="44" />
						<p>
							<s:property value="deviceNum.miniPmParCount" />
						</p>
					</div>
					<div class="BusStatiEquipTitl">小型机分区（台）</div>
				</li> -->
				<li>
					<div class="BusStatiEquipList">
						<img src="../images/raid.png" width="38" height="44" />
						<p>
							<s:property value="deviceNum.raidCount" />
						</p>
					</div>
					<div class="BusStatiEquipTitl">存储阵列（个）</div>
				</li>
				<li>
					<div class="BusStatiEquipList">
						<img src="../images/sw.png" width="38" height="44" />
						<p>
							<s:property value="deviceNum.swCount" />
						</p>
					</div>
					<div class="BusStatiEquipTitl">交换机（台）</div>
				</li>
				<li>
					<div class="BusStatiEquipList">
						<img src="../images/rt.png" width="38" height="44" />
						<p>
							<s:property value="deviceNum.rtCount" />
						</p>
					</div>
					<div class="BusStatiEquipTitl">路由器（台）</div>
				</li>
				<li>
					<div class="BusStatiEquipList">
						<img src="../images/fw.png" width="38" height="44" />
						<p>
							<s:property value="deviceNum.fwCount" />
						</p>
					</div>
					<div class="BusStatiEquipTitl">防火墙（个）</div>
				</li>
			</ul>
		</div>
		<div id="partsId" class="ResourceTabBg" style="width: 97%">
			<!-- <div class="resouseCapacity">
				<div class="resouseCapacityTitel">虚拟机容量</div>
				<div style="width: 100%; margin: 10px;">
					<div id="poolchart1"
						style="width: 33%; float:left; height: 270px;  text-align: center;"></div>
					<div id="poolchart2"
						style="width: 33%; float:left; height: 270px;  text-align: center;"></div>
					<div id="poolchart3"
						style="width: 33%; float:left; height: 270px;  text-align: center;"></div>
				</div> 
			</div>-->
			<%-- 
			<div class="resouseTabEquit">
				<div class="resouseCapacityTitel">入池设备容量</div>
				<div style="margin: 10px;">
					<div id="pmchart" style="width: 100%; float:left; height: 270px; text-align: center;"></div>
				</div>
			</div>
			--%>
		</div>
		<div style="clear:both;"></div>
	</div>
</body>