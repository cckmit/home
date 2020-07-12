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
	var treeNodeName = $treeNode.attr("name");
	var pnodeId = $treeNode.attr("pId");
	var pnodeName = $treeNode.attr("pName");
	var curFun = $treeNode.attr("curFun");

	var appId;
	if (nodeType == "top") {
		
	} else if (nodeType == "res") {
	} else if (nodeType == "app") {
		appId = nodeId.split("-")[1];
		url +="?appIdFromTree=" + appId;
	} else if (nodeType == "app_res") {
		appId = nodeId.split("-")[1];
		url+="?type=0&queryBusinessId="+appId+"&businessName="+pnodeName;
	}

	$("#nodeType").val(nodeType);
	$("#nodeId").val(nodeId);
	$("#treeNodeName").val(encodeURIComponent(treeNodeName));
	$("#pnodeId").val(pnodeId);
	$("#pnodeName").val(encodeURIComponent(pnodeName));
	$("#appId").val(appId);
	$("#curFun").val(curFun);
    //window.location=basePath+"/"+url;
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
    setting.async.url = url + "?pnodeId=" + document.getElementById("pnodeId").value;
	 $.fn.zTree.init($("#appView"), setting);
	/*$(".ztree").each(function(index, jdom){
        setting.async.url = url;
        if ($(jdom).attr("curFun") == "zyst") {
    	    $.fn.zTree.init($("#resView"), setting);
        } else {
    	    $.fn.zTree.init($("#appView"), setting);
        }
    });*/
});

$(function() {
    $("#head-menu li a:contains('资源管理')").parent().addClass("head-btn-sel");
});