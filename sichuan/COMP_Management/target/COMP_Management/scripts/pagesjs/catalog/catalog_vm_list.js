$(function(){
    //菜单显示当前，开发时删除
    $(".left-menu li:contains('服务目录管理')").addClass("selected").next().show();
    $("#head-menu li a:contains('产品管理')").parent().addClass("head-btn-sel");
	var catalogType = $("#catalogType").val();
	if(catalogType==0){
    	$(".left-menu dl a:contains('虚拟机 ')").parent().addClass("selected");
	}
	if(catalogType==1){
    	$(".left-menu dl a:contains('物理机')").parent().addClass("selected");
	}
	if(catalogType==4){
    	$(".left-menu dl a:contains('虚拟机备份')").parent().addClass("selected");
	}
	if(catalogType==5){
    	$(".left-menu dl a:contains('云硬盘')").parent().addClass("selected");
	}
    //自定义form样式
    $(":text,:checkbox,select").uniform();
    $.uniform.update();
});
//保存
function onAdd(obj, catalogType){
	$.dialog({
		title: '创建服务目录',
		content: document.getElementById('catalog_create_div'),
		init:function (){
			$("#catalogType").val(catalogType);
			$("#catalogName").val("");
			$("#description").val("");
			//更新下拉列表显示状态
			$.uniform.update();
		},		
			
		button:[
	        {
	            name: '创建',
	            callback: function () {
					this.button({
						name: '创建',
						disabled: true
	            	});
					tempCatalogId = $("#catalogId").val();
					tempCatalogType = $("#catalogType").val();
					tempCatalogName = $("#catalogName").val();
					tempDescription = $("#description").val();	
					
					if(tempCatalogType == '' || tempCatalogType.length>2){
						$.compMsg({type:'error',msg:'服务目录类型格式有误，请重新输入！'});
						this.button({
							name: '创建',
							disabled: false
		            	});
						return false;
					}else if(tempCatalogName == '' || tempCatalogName.length>25){
						$.compMsg({type:'error',msg:'服务目录名称格式有误，请重新输入！'}); 
						this.button({
							name: '创建',
							disabled: false
		            	});
						return false;
					}else if(tempDescription.length>100){
						$.compMsg({type:'error',msg:'服务目录描述格式有误，请重新输入！'});
						this.button({
							name: '创建',
							disabled: false
		            	});
						return false;
					}else {
				        $("#catalogCreate").attr('action', 'createCatalog.action');
				        $("#catalogCreate").submit();
				    }
					return true;
	            },
				focus: true
	        }],
	        
		cancelVal: '取消',
		cancel:true,
		lock: true
	});

}
////修改跳转
function onCatalogModify(obj, catalogId, catalogType){
	$.dialog({
		title: '修改服务目录',
		content: document.getElementById('catalog_modify_div'),
		init:function(){
			$("#catalogId").val(catalogId);
			$("#catalogType").val(catalogType);
			$("#catalogName").val($(obj).parents("tr").find("td").eq(0).text());
			$("#oldCatalogName").val($(obj).parents("tr").find("td").eq(0).text());
			$("#description").val($(obj).parents("tr").find("td").eq(1).text());
			},
			button:[
	        {
	            name: '修改',
	            callback: function () {
					this.button({
						name: '修改',
						disabled: true
	            	});

					tempCatalogId = $("#catalogId").val();
					tempCatalogType = $("#catalogType").val();
					tempCatalogName = $("#catalogName").val();
					tempDescription = $("#description").val();	
					if(tempCatalogId == '' ||tempCatalogId.length>25){
						$.compMsg({type:'error',msg:'服务目录ID格式有误，请重新输入！'}); 
					}else if(tempCatalogType == '' || tempCatalogType.length>2){
						$.compMsg({type:'error',msg:'服务目录类型格式有误，请重新输入！'}); 
					}else if(tempCatalogName == '' || tempCatalogName.length>25){
						$.compMsg({type:'error',msg:'服务目录名称格式有误，请重新输入！'}); 
					}else if(tempDescription.length>100){
						$.compMsg({type:'error',msg:'服务目录描述格式有误，请重新输入！'}); 
					}else {
				        $("#catalogModify").attr('action', 'modifyCatalog.action');
				        $("#catalogModify").submit();
				    }
					return true;
	            },
				focus: true
	        }],
		
		cancelVal: '取消',
		cancel:true,
		lock: true
	});
}
//删除
function onCatalogDel(obj,catalogId,catalogType){
	$.dialog( {
		title : '删除服务目录',
		content : '请您确认是否删除服务目录？删除后将无法恢复！',
		ok : function() {
					if(catalogId == '' ||catalogId.length>25){
						$.compMsg({type:'error',msg:'服务目录ID格式有误，请重新输入！'}); 
					}else if(catalogType.length>2){
						$.compMsg({type:'error',msg:'服务目录类型格式有误，请重新输入！'}); 
					}else  {
						$("#catalogType").val(catalogType);
				        $("#catalogId").val(catalogId);
				        $("#catalogDel").attr('action', 'delCatalog.action');
				        $("#catalogDel").submit();
				    }
			return true;
		},
		cancelVal : '关闭',
		cancel : true,
		lock : true
	});
}

