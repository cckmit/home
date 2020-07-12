$(function() {

	$("#topo").siblings().removeClass("active");
	$("#topo").addClass("active");
	$(".status-tab a").unbind('click');
	showTopoData();
	draw();
});

var vmResultInfos = null;
var diskInfos = null;
var diskInfos4Up = null;
var resultInfoList = [];
function showTopoData() {
	$.ajax({
		url : 'showTopoListJson.action?struts.enableJSONValidation=true',
		type : 'POST',
		data : null,
		cache : false,
		async : false,
		dataType : 'json',
		success : function(data) {
//			vmResultInfos = data.vmResultInfos.length;
//			diskInfos = data.diskInfos.length;
//			diskInfos4Up = data.diskInfos4Up.length;
			resultInfoList = data; 
//			alert(resultInfoList[1].appName);
		}
	});
	
}

var nodes = null;
var edges = null;
var network = null;

var DIR = '../img/topo/';
var EDGE_LENGTH_MAIN = 150;
var EDGE_LENGTH_SUB = 50;

// Called when the Visualization API is loaded.
function draw() {
	// Create a data table with nodes.
	nodes = [];

	// Create a data table with links.
	edges = [];

	var userId = document.getElementById("userId").value;
	nodes.push({
		id : 100,
		label : userId,
		image : DIR + 'Network-Pipe-icon.png',
		shape : 'image'
	});
	
	for (var i = 0; i < resultInfoList.length; i++) {
		var ebsSum = parseInt(resultInfoList[i].ebsCount) + parseInt(resultInfoList[i].ebsUpCount);
		nodes.push({
			id : i,
			label : resultInfoList[i].appName, // 业务
			image : DIR + 'Network-Pipe-icons.png',
			shape : 'image'
		});
		edges.push({
			from : 100,
			to : i,
			length : EDGE_LENGTH_MAIN
		});
		
		nodes.push({
			id : 100000 + i,
			label : '云硬盘' + ebsSum + '块',
			image : DIR + 'Hardware-Printer-Blue-icon.png',
			shape : 'image'
		});
		edges.push({
			from : i,
			to : 100000 + i,
			length : EDGE_LENGTH_MAIN
		});
		
		nodes.push({
			id : 200000 + i,
			label : '云主机' + resultInfoList[i].vmCount + '台',
			image : DIR + 'Hardware-My-Computer-3-icon.png',
			shape : 'image'
		});
		edges.push({
			from : i,
			to : 200000 + i,
			length : EDGE_LENGTH_MAIN
		});
		

		nodes.push({
			id : 1000 + i,
			label : '未挂载' + resultInfoList[i].ebsCount + '块', 
			image : DIR + 'Network-Drive-icon.png',
			shape : 'image'
		});
		edges.push({
			from : 100000 + i,
			to : 1000 + i,
			length : EDGE_LENGTH_SUB
		});
		
		nodes.push({
			id : 2000 + i,
			label : '已挂载' + resultInfoList[i].ebsUpCount + '块', 
			image : DIR + 'Hardware-Laptop-1-icon.png',
			shape : 'image'
		});
		edges.push({
			from : 100000 + i,
			to : 2000 + i,
			length : EDGE_LENGTH_SUB
		});
		
	}
	

//	nodes.push({
//		id : 100,
//		label : '未挂载' + diskInfos + '块',
//		image : DIR + 'Network-Drive-icon.png',
//		shape : 'image'
//	});
//	edges.push({
//		from : 100000,
//		to : 100,
//		length : EDGE_LENGTH_SUB
//	});
	
//	for (var i = 0; i <= vmResultInfos.length; i++) {
//		nodes.push({
//			id : 200,
//			label : vmResultInfos + '台',
//			image : DIR + 'Hardware-My-Computer-3-icon.png',
//			shape : 'image'
//		});
//		edges.push({
//			from : 200000,
//			to : 200,
//			length : EDGE_LENGTH_SUB
//		});
//	}

//	nodes.push({
//		id : 300,
//		label : '已挂载' + diskInfos4Up + '块',
//		image : DIR + 'Network-Drive-icon.png',
//		shape : 'image'
//	});
//	edges.push({
//		from : 100000,
//		to : 300,
//		length : EDGE_LENGTH_SUB
//	});
	
	// create a network
	var container = document.getElementById('mynetwork');
	var data = {
		nodes : nodes,
		edges : edges
	};
	var options = {};
	network = new vis.Network(container, data, options);
}

