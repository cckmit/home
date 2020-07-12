
/**
 *通过机房ID获取机柜信息列表
 */
function setCabinetList(){

	
	var machinerRoomId = $("#machinerRoomId").val();
	
	$.ajax({
	    type : "GET",
	    url : basePath+'/resViewPmGetCabinetListAction.action',
	    data:{machinerRoomId:machinerRoomId},
	    success : function(data){
			$('#cabinetId').empty();
			$('#cabinetId').append("<option value=''>-请选择-</option>");
			$.each(data.cabinetList,function(index){
				$('#cabinetId').append("<option value='" + this.cabinetId + "'>" + this.cabinetName + "</option>");
		   });
	    },
	    error: function(data){
			var mes = fieldErrors(data.fieldErrors);
			if(mes!=''){
				$.compMsg({type:'error',msg:mes});
			}
			if(data.resultMessage!=null){
				if(data.resultFlage=='success'){
					backList(data.resultMessage,'');
					flage = false;
				}else if(data.resultFlage=='failure'){
					$.compMsg({type:'error',msg:data.resultMessage}); 
				}else if(data.resultFlage=='error'){
					$.compMsg({type:'error',msg:data.resultMessage}); 
				}
			}
	    },
	    dataType:"json"
	}); 
	
	
}
//检验错误信息
//返回错误提示内容
function fieldErrors(errors){
	var mes = '';
	if(!jQuery.isEmptyObject(errors)){
		if(!jQuery.isEmptyObject(errors.roleName)){
			mes+=errors.roleName+'\n';
		}
	}
	return mes;
}