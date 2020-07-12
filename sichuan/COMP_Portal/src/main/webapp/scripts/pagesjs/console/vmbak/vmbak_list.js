$(function() {
	//菜单显示当前
	$(".left-menu li:contains('虚拟机备份')").addClass("selected");
	// 自定义form样式
    $("select,:text").uniform();
    $.uniform.update();

    loadVmBakStatus();
});

//查询虚拟机备份任务状态
function loadVmBakStatus(){
	var str = "";
	var flage = false;
	$("#backupList tr").each(function(i){
		   var status = $(this).attr('status');
		   var vmBakId = $(this).attr('id');
		   if(status != null && status != 'undefined' && status != "11"){ // 去掉标题行 和 状态是“待创建”的（待创建的接口中查询不到状态）。
			   if(!flage){
				   flage = true;
				   str+="[";
			   }else{
			   	   str+=",";
			   }
			   str+="{id:'" + vmBakId + "',status:'" + status + "'}";
		   }
	  });
	  if(flage){
		  str+="]";
		   var da_val = {queryStr:str};
		   $.ajax({
	            type:"POST",
	            url:'vmBakQueryListStateAction.action',
	            data:da_val,
	            dataType:"JSON",
	            cache: false,
	            success:function(data){
		            var ls = data.resultStatusInfos;
					if(ls==null||ls=='undefined'){
						window.location.href='exceptionIntercepor.action';
					}
	            	for(var i=0;i<ls.length;i++){
	            		var vmBakId = ls[i].id.replace(/\+/ig,'\\+');
		            	var statusCode = ls[i].status;
		            	var statusDesc = ls[i].statusText;

		            	// tr
		            	$("#"+vmBakId).attr('status',statusCode);

		            	// 备份状态td
		            	$("#status"+vmBakId).html(statusDesc);
		            	
		            	// 操作td
		            	//var caseId = $("#"+vmBakId).attr('caseId');
		            	//var html = '<a href="#" onclick="vmBakDetail(\''+caseId+'\');">详情</a>';
		            	//if(statusCode==2 || statusCode==5 || statusCode==7|| statusCode==9){
		            	//	html += '<a href="#" onclick="delVmBak(\''+vmBakId+'\');">删除</a>';
		            	//}
		            	//$("#opt"+vmBakId).html(html);
		            }
	     		    window.setTimeout('loadVmBakStatus()',10000);
	            },
	            error:function(){
		        }
		   });
	  }
}


//虚拟机备份任务查询
function searchVmBakList(){
	var form = document.getElementById("vmBakForm");
    form.action = "vmBakQueryListAction.action";
    form.submit();
}

// 虚拟机备份任务详情
function vmBakDetail(caseId){
	document.getElementById("caseId").value = caseId;
    var form = document.getElementById("vmBakForm");
    form.action = "vmBakDetailAction.action";
    form.submit();
}

//虚拟机备份任务创建
function vmBakCreate(){
	var form = document.getElementById("vmBakForm");
    form.action = "vmBakApplyAction.action";
    form.submit();
}

//删除
function delVmBak(vmBakId) {
	$.dialog( {
		title : '删除',
		content : '请您确认是否删除该虚拟机备份任务？',
		ok : function() {
			$.dialog( {
				title : false,
				clouse : false,
				width : 200,
				height : 40,
				lock : true,
				content : '请稍后，正在删除中...',
				init: function(){
						var tmpDialog = this;
						var da_val = {vmBakId: vmBakId};
						$.ajax({type: "POST",
								url: 'vmBakDelJsonAction.action',
								dataType: "JSON",
								data: da_val,
								cache: false,
								success: function(data){
									$.backListMsg({msg:data.mes,errMsg:'',url:'vmBakQueryListAction.action'});
								},
								error: function(){
									$.compMsg({type:'error',msg:'删除失败!'});
									tmpDialog.close();
								}
						});//删除结束
					}
			});
			return true;
		},
		cancelVal : '关闭',
		cancel : true,
		lock : true
	});
}