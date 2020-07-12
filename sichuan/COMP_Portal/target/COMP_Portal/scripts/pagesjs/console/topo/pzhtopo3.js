
/*三期portal*/

$(function() {

	$("#pzhtopo3").siblings().removeClass("active");
	$("#pzhtopo3").addClass("active");
	$(".status-tab a").unbind('click');
	queryResPool("", "", "");
	//showTopoData();
	showApplist();
	//changeapp2();
	draw();
	$("#respool").change(function() {
		showApplist();
		// varRespoolId = $(this).children('option:selected').val();
		// varRespoolPartId = "";
		//onloadResPool(varRespoolId, varRespoolPartId);
		// queryResPool(varRespoolId, varRespoolPartId);
	});

	// $("#respoolpart").change(function() {
	// 	varRespoolId = $("#respool").children('option:selected').val();
	// 	varRespoolPartId = $(this).children('option:selected').val();
	// 	//onloadResPool(varRespoolId, varRespoolPartId);
	// 	queryResPool(varRespoolId, varRespoolPartId);
	// });
	
});
var status = 0;
var status1 = 0;
var nodes = new vis.DataSet([
    
]);     
var edges =new vis.DataSet([
    
	]);          
var network = null; 
var DIR = '../img/topo/';  
var EDGE_LENGTH_MAIN = 150;
var EDGE_LENGTH_SUB = 50;
var resultList = [];

var detail =false;
var resultInfoList = [];
function topomain(){
	var appname = $("#app").find("option:selected").text();
	changeapp(appname);
}

function showdetail() {
	alert('123');
}
/* 查资源池 */
//此方法还有些多余功能，用于以后资源池分区拓展
function queryResPool(resPoolId, resPartId) {
	var data = {
		itemId : "",
		respoolId : resPoolId,
		respoolPartId : resPartId
	};
	$.ajax({
		url : 'vmOnloadResPoolAction.action?struts.enableJSONValidation=true',
		type : 'POST',
		data : data,
		cache : false,
		async : false,
		dataType : 'json',
		success : function(data) {
			$("#respool").empty();
			for (var i = 0; i < data.respools.length; i++) {
				if (i == 0) {
					$("#respoolName").val(data.respools[i].respoolName);
				}
				if (resPoolId != data.respools[i].respoolId) {
					$("#respool").append(
						'<option value="' + data.respools[i].respoolId
						+ '">' + data.respools[i].respoolName
						+ '</option>');
				} else {
					$("#respool").append(
						'<option selected="selected" value="'
						+ data.respools[i].respoolId + '">'
						+ data.respools[i].respoolName
						+ '</option>');
					$("#respoolName").val(data.respools[i].respoolName);
				}
			}

			$("#respoolpart").empty();
			for (var i = 0; i < data.respoolParts.length; i++) {
				if (i == 0) {
					$("#respoolPartName").val(
						data.respoolParts[i].respoolPartName);
				}
				if (resPartId == data.respoolParts[i].respoolPartId) {
					$("#respoolpart").append(
						'<option selected="selected" value="'
						+ data.respoolParts[i].respoolPartId + '">'
						+ data.respoolParts[i].respoolPartName
						+ '</option>');
					$("#respoolPartName").val(
						data.respoolParts[i].respoolPartName);

				} else {
					$("#respoolpart").append(
						'<option value="'
						+ data.respoolParts[i].respoolPartId + '">'
						+ data.respoolParts[i].respoolPartName
						+ '</option>');
				}

			}
			if (typeof (resPartId) == "undefined" || resPartId == "") {
				resPartId = data.respoolParts[0].respoolPartId;
			}
		}
	});
}
//根据网段查询主机
function showTopoData(a) {
	/*if(a.indexOf("::") != -1){	
		var subnet = a.split(":");
		var subnetstart = subnet[0]+":"+subnet[1]+":"+subnet[2]+":"+subnet[3]+":"+subnet[4]+":"+subnet[5]+":%";
	}else{
		var subnet = a.split(".");
		var subnetstart = subnet[0]+"."+subnet[1]+"."+subnet[2]+".%";
		var subnetend = subnet[0]+"."+subnet[1]+"."+subnet[2]+".255";		
	}*/
	var data= a;
	$.ajax({
		url : 'showPzhtopoListJson.action?struts.enableJSONValidation=true',
		type : 'POST',
		data : data,
		cache : false,
		async : false,
		dataType : 'json',
		success : function(data) {
			resultInfoList = data; 
		}
	});
	status1=1;
}


/*展示admin用户的业务菜单*/
function showApplist() {
	 // $("#respool").find("option:selected").text();
	var resPoolId =$("#respool").children('option:selected').val();
	var data1 = {
		resPoolId : resPoolId
	};
	url = 'showApplist.action';
	$.ajax( {
				type : "POST",
				url : url,
				data : data1,
				dataType : "JSON",
				cache : false,
				success : function(data) {
					applist=data;
					if(applist[0]&&applist[0].userId=="admin"){
						$('#app').empty();
						$('#app').append('<option value='+applist[0].appId+' select="selected">'+applist[0].appName+'</option>')
						for(var i =1;i<applist.length;i++){						
							$('#app').append('<option value='+applist[i].appId+'>'+applist[i].appName+'</option>');
						}					
						var appname = $("#app").find("option:selected").text();
						changeapp(appname);
					}else if(applist[0]&&applist[0].userId=="quanxian"){
						document.getElementById("respoolshow").style.display="none";
						document.getElementById("adminshow").style.display="none";
						appname="pzh攀枝花";//就利用appname，不建新参数了。取这名是为了防止巧合重复
						changeapp2(appname);
					}else{
						document.getElementById("respoolshow").style.display="none";
						document.getElementById("adminshow").style.display="none";
						appname="";
						changeapp2(appname);
					}
				}
			});
	
}
//展示admin用户网段拓扑
function changeapp(appname) {
	var appid = "";
	if ($('#app').val() != "notSelect") {
		appid = $('#app').val();
	}	
	var da_val;	
	da_val = {
			appid : appid
	};
	url = 'showPzhtopobyapp.action';
	
	$.ajax( {
				type : "POST",
				url : url,
				data : da_val,
				dataType : "JSON",
				cache : false,
				success : function(data) {
					resultList = data;
					status=1;
					
					draw(appname);
					
				}
			
			});
	status1=0;
}
//展示非admin用户网段拓扑
function changeapp2(appname) {	
	url = 'showPzhtopobyapp2.action';
	$.ajax( {
				type : "POST",
				url : url,
				data : null,
				dataType : "JSON",
				cache : false,
				success : function(data) {
					resultList = data;
					status=1;
					draw(appname);
					
				}
			
			});
}


/*var network = new vis.Network(container, data, options);

network.on("onmouseover", function (params) {
    params.event = "[original event]";
    document.getElementById('eventSpan').innerHTML = '<h2>Click event:</h2>' + JSON.stringify(params, null, 4);
    console.log('click event, getNodeAt returns: ' + this.getNodeAt(params.pointer.DOM));
	alert('123');
});
*/



// Called when the Visualization API is loaded.
function draw(appname) {
	// Create a data table with nodes.
	nodes = [];
	
	// Create a data table with links.
	edges = [];
	
		
//	根节点
	nodes.push({
		id : 1,
		label : '互联网',
		image : DIR+'hulianwang.png',
		shape : 'image',
		level:1
	});
	if(appname=="pzh攀枝花"){		
		nodes.push({
			id : 2,
			label : '专线接入',
			image : DIR+'panzhihua.png',
			shape : 'image',
			level:1
		});
	}else{
		nodes.push({
			id : 2,
			label : '专线接入',
			image : DIR+'kehuwangluo.png',
			shape : 'image',
			level:1
		});
	}
//第二层攀枝花交换机节点
	nodes.push({
		id : 21,
		label : '专线接入交换机',
		image : DIR + 'jiaohuanji.png',
		shape : 'image',
		level:2	
	});
	
	nodes.push({
		id : 22,
		label : '专线接入交换机',
		image : DIR + 'jiaohuanji.png',
		shape : 'image',
		level:2
	});
	
//	交换机到攀枝花布线
	edges.push({
		from : 2,
		to : 21,
		length: 35
	});
	
	edges.push({
		from : 2,
		to : 22,
		length: 35
	});
	edges.push({
		from : 21,
		to : 22,
		length: 10
	});

//第三层防火墙
	nodes.push({
		id : 31,
		label : '互联网出口防火墙',
		image : DIR + 'fanghuoqiang.png',
		shape : 'image',
		level:3
	});	
	nodes.push({
		id : 32,
		label : '互联网出口防火墙',
		image : DIR + 'fanghuoqiang.png',
		shape : 'image',
		level:3	
	});
	nodes.push({
		id : 33,
		label : '专线接入防火墙',
		image : DIR + 'fanghuoqiang.png',
		shape : 'image',
		level:3	
		
	});	
	nodes.push({
		id : 34,
		label : '专线接入防火墙',
		image : DIR + 'fanghuoqiang.png',
		shape : 'image',
		level:3	
	});
	
	edges.push({
		from : 1,
		to : 31
	});
	edges.push({
		from : 1,
		to : 32
	});
	edges.push({
		from : 31,
		to : 32,
		length: 15
	});
	edges.push({
		from : 21,
		to : 33,
		length: 20
	});	
	
	edges.push({
		from : 22,
		to : 34,
		length: 20
	});
	edges.push({
		from : 33,
		to : 34,
		length: 15
	});
	
//第四层交换机
	nodes.push({
		id : 41,
		label : '核心交换机',
		image : DIR + 'jiaohuanji.png',
		shape : 'image',
		level:4	
	});
	
	nodes.push({
		id : 42,
		label : '核心交换机',
		image : DIR + 'jiaohuanji.png',
		shape : 'image',
		level:4
	});
	edges.push({
		from : 31,
		to : 41,
		length: 150
	});
	edges.push({
		from : 33,
		to : 41,
		length: 150
	});
	edges.push({
		from : 32,
		to : 42,
		length: 150
	});
	edges.push({
		from : 34,
		to : 42,
		length: 150
	});
	edges.push({
		from : 41,
		to : 42
	});
	
//第五层交换机
	nodes.push({
		id : 51,
		label : '汇聚交换机',
		image : DIR + 'jiaohuanji.png',
		shape : 'image',
		level:5
	});
	
	nodes.push({
		id : 52,
		label : '汇聚交换机',
		image : DIR + 'jiaohuanji.png',
		shape : 'image',
		level:5
	});
	edges.push({
		from : 41,
		to : 51,
		length: 20
	});
	edges.push({
		from : 42,
		to : 52,
		length: 20
	});
	edges.push({
		from : 51,
		to : 52,
		length: 20
	});
//第六层网段
	if(status==1){		
		for (var i = 0; i < resultList.length; i++) {
			/*if(a.indexOf(":") != -1){	
				var subnet = a.split(":");
				for (var k = 0; k < resultList.length; k++){					
					if(subnet[k].equals("")){
						subnet[k]="0000"
					}
					var subnetstart = subnet[0]+":"+subnet[1]+":"+subnet[2]+":"+subnet[3]+":"+subnet[4]+":"+subnet[5]+":%";
				}
			}else{	*/					
				var a = {
						subnetstart : resultList[i].subnetstart,
						subnetend : resultList[i].subnetend
				}
			/*}*/
			var c = (i+1)*100;
			nodes.push({
				id : c,
				label : resultList[i].ipsubnet, // 网段
				image : DIR + 'fuwuqi.png',
				shape : 'image',
				level:6,
				
			});
			edges.push({
				from : 51,
				to : c
				
			});
			edges.push({
				from : 52,
				to : c
				
			});
			if(status1==0){
				
				showTopoData(a);
			}
			
			
			var m = 0;
			var n = 0;
			var x = 0;
			var y = 0;
			
			for (var j = 0; j < resultInfoList.length; j++) {					
					m = m + 1;
					if(m >= 10){
						 x = x + 1;
						 m = 0;
					}
					nodes.push({
						id : c+j+1,
						label : resultInfoList[j].vmname+'\n'+resultInfoList[j].ip, // 业务
						image : DIR + 'Hardware-My-Computer-3-icon.png',
						shape : 'image',
						level:7+x,
						title:'IP地址'+ resultInfoList[j].ip,
					});
					edges.push({
						from : c,
						to : c+j+1,
						length: 150
						
					});
				
			}
			
			
		}
	}	
	
	// create a network
	
	var container = document.getElementById('mynetwork');
	var data = {
		nodes : nodes,
		edges : edges
	};
	var options = { 
			interaction: {
				dragNodes:true
			},
			edges:{
				width : 2/*,
				length : 10*/

				
			},
			layout:{
				randomSeed:1,
				hierarchical:{
					
					levelSeparation: 110,
					direction: 'UD',
					sortMethod: 'directed'
				   }
			},
		};
	var network = new vis.Network(container, data, options);
	var data = new vis.DataSet(container, data, options);
	network.on("showPopup", function(params) {  //每个点的操作时间，官网有详细案例可查询
		var str = params.toString();
		str=str.substring(str.length-2,str.length);
		str=parseInt(str);
		var vmId = resultInfoList[str-1].vmId;		
		var resPoolId = resultInfoList[str-1].resPoolId;
		var resPoolPartId = resultInfoList[str-1].resPoolPartId;
		var data={
				vmId:vmId,
				resPoolId:resPoolId,
				resPoolPartId:resPoolPartId
		};
		$.ajax({
			url : 'showtopostatus.action',
			type : 'POST',
			data : data,
			cache : false,
			async : false,
			dataType : 'json',
			success : function(data) {
				statusinfo = data; 
			}
		});
		resultInfoList[str-1].statuscode=statusinfo.statuscode;
		resultInfoList[str-1].statustext=statusinfo.statustext;
		
		if(resultInfoList[str-1].statuscode!=2){
			$('#vmname2').val(resultInfoList[str-1].vmname);
			$('#statustext2').val(resultInfoList[str-1].statustext);
			$.dialog({
				id:"detail",
				width: "30%",
			    fixed: true,
				flat: true,
				title: "云主机性能详情",
				left: "30%",
				position : "center",
				content: document.getElementById('detail2'),
				
			});	
			
		}else{		
		
		var diskwrite = 0;
		var diskread = 0;
		var bytesin =0;
		var bytesout =0;
		if (resultInfoList[str-1].diskwrite!=null&&resultInfoList[str-1].diskwrite!=""){			
			diskwrite =resultInfoList[str-1].diskwrite.split("=")[resultInfoList[str-1].diskwrite.split("=").length-1];
		}
		if (resultInfoList[str-1].diskread!=null&&resultInfoList[str-1].diskread!=""){
			diskread =resultInfoList[str-1].diskread.split("=")[resultInfoList[str-1].diskread.split("=").length-1];
		}
		if (resultInfoList[str-1].bytesin!=null&&resultInfoList[str-1].bytesin!=""){
			bytesin =(resultInfoList[str-1].bytesin.split("=")[resultInfoList[str-1].bytesin.split("=").length-1]).split(".")[0];
		}
		if (resultInfoList[str-1].bytesout!=null&&resultInfoList[str-1].bytesout!=""){
			bytesout =(resultInfoList[str-1].bytesout.split("=")[resultInfoList[str-1].bytesout.split("=").length-1]).split(".")[0];
		}
		var mempercent = parseFloat(resultInfoList[str-1].mempercent);
		
		$('#vmname').val(resultInfoList[str-1].vmname);
		$('#ip').val(resultInfoList[str-1].ip);
		$('#statustext').val(resultInfoList[str-1].statustext);
		$('#cpuidle').val(resultInfoList[str-1].cpuidle+' %');
		$('#cpuspeed').val(resultInfoList[str-1].cpuspeed+' MHz');
		$('#diskwrite').val(diskwrite+' bytes/sec');
		$('#diskread').val(diskread+' bytes/sec');
		$('#bytesin').val(bytesin+' bytes/sec');
		$('#bytesout').val(bytesout+' bytes/sec');
		$('#mempercent').val(mempercent+' %');
		
		$.dialog({
			id:"detail",
			width: "50%",
			left: "50%",
			overlay: true,
			shadow: true,
			flat: true,
			draggable: true,
			title: "云主机性能详情",
			content: document.getElementById('detail'),
			
		});	
		
		var appname = $("#app").find("option:selected").text();
		if(resultInfoList[str-1].click!="1"){/*
		draw(appname);
		resultInfoList[str-1].click="1";*/
		}
		}
	});
	network.on("hidePopup", function () {
		top.art.dialog({id:"detail"}).close();
    });
}
	
	






