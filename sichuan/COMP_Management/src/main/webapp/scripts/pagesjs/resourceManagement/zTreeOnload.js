var obj = window.location;
var contextPath = obj.pathname.split("/")[1] + "/" + obj.pathname.split("/")[2]; 
var basePath = obj.protocol + "//"+obj.host + "/" + contextPath; 
var url = basePath + '/creatTreeActionJson.action';

var setting = {	
	data: {
		key: {
			title:"t"
		},
		simpleData: {
			enable: true
		}
	},
	async: {
		enable: true,
		url : url
	},
	callback: {
		beforeClick: beforeClick,
		beforeExpand: beforeExpand,
		onNodeCreated: zTreeOnNodeCreated
	}
};

function beforeClick(treeId, treeNode) {
	var url = treeNode.skipUrl;
	var $treeNode = $(treeNode);
	var nodeType = $treeNode.attr("nodeType");
	var nodeId = $treeNode.attr("id");
	//var treeNodeName = encodeURIComponent($treeNode.attr("name"));
	var treeNodeName = $treeNode.attr("name");
	var pnodeId = $treeNode.attr("pId");
	var pnodeName = $treeNode.attr("pName");
	var curFun = $treeNode.attr("curFun");

	var appId;
	if (nodeType == "top") {
		if (nodeId == "zyst") { //资源视图-所有资源
		} else { //业务视图-所有业务
			url += "?deviceOverInfo.deviceType=2&deviceOverInfo.performanceType=cpu";
		}
	} else if (nodeType == "res") {
	} else if (nodeType == "app") {
		appId = nodeId.split("-")[1];
		url +="?device_type=2";//业务 下->物理机性能统计传参
	} else if (nodeType == "app_res") {
		appId = nodeId.split("-")[1];
	}

	$("#nodeType").val(nodeType);
	$("#nodeId").val(nodeId);
	$("#treeNodeName").val(treeNodeName);
	$("#pnodeId").val(pnodeId);
	$("#pnodeName").val(encodeURIComponent(pnodeName));
	$("#appId").val(appId);
	$("#curFun").val(curFun);

	document.getElementById("submitForm").action = url;
	document.getElementById("submitForm").submit();
}

function beforeExpand(treeId, treeNode) {
	return true;
}

function zTreeOnNodeCreated(event, treeId, treeNode) {
    var treeObj = $.fn.zTree.getZTreeObj(treeId);
	var $treeNode = $(treeNode);

	var selectedNodeId = document.getElementById("nodeId").value; //页面选中的菜单节点
    if (selectedNodeId != "") {
    	if (selectedNodeId == $treeNode.attr("id")) {
    		treeObj.selectNode(treeNode);
    	}
	} else {
		if ($treeNode.attr("nodeType") == "top") {
			treeObj.selectNode(treeNode);
		}
	}
}

$(document).ready(function(){
	$(".ztree").each(function(index, jdom){
        setting.async.url = url + "?curFun=" + $(jdom).attr("curFun") + "&pnodeId=" + document.getElementById("pnodeId").value;
        if ($(jdom).attr("curFun") == "zyst") {
    	    $.fn.zTree.init($("#resView"), setting);
        } else {
    	    $.fn.zTree.init($("#appView"), setting);
        }
    });
});

$(function() {
    $("#head-menu li a:contains('资源管理')").parent().addClass("head-btn-sel");
});