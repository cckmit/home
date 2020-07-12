$(function() {
    
	//菜单显示当前
	$(".left-menu li:contains('IP段')").addClass("selected").next().show();
	queryStatusLoading();
	vmNameChange();
	$("select[id!='select']").uniform();//初始化下拉菜单样式
});
function queryStatusLoading(){
	var str = "";
	var flage = false;
	$("#resultList li").each(function(i){
		   var diskStatus = $(this).attr('status');
		   if(diskStatus==1||diskStatus==3){
			   if(!flage){
				   flage = true;
				   str+="[";
			   }else{
			   	   str+=",";
			   }
			   var diskId = $(this).attr('name');
			   str+="{id:'"+diskId+"',status:'"+diskStatus+"'}";
		   }
	  });
	  if(flage){
		  str+="]";
		   var da_val = {queryStr:str};
		   $.ajax({
	            type:"POST",
	            url:'ebsQueryListStateAction.action',
	            data:da_val,
	            dataType:"JSON",
	            cache: false,
	            success:function(data){
		            var ls = data.resultStatusInfos;
					if(ls==null||ls=='undefined'){
						window.location.href='exceptionIntercepor.action';
					}
	            	for(var i=0;i<ls.length;i++){
	            		var ebsId = ls[i].id.replace(/\+/ig,'\\+');
		            	var html;
		            	var statusCode = ls[i].status;
		            	var effectiveTime = ls[i].effectiveTime;
		            	var overTime = ls[i].overTime;
		            	$("#s"+ebsId).attr('status',statusCode);
		            	if(statusCode=='1'){
			            	html = '<span class="status s-gray">创建中</span>';
			            }else if(statusCode=='2'){
			            	html = '<span class="status s-blue">已创建</span>';
			            }else if(statusCode=='3'){
			            	html = '<span class="status s-gray">挂载中</span>';
			            }else if(statusCode=='4'){
			            	html = '<span class="status s-green">已挂载</span>';
			            }else if(statusCode=='5'){
			            	html = '<span class="status s-blue">未挂载</span>';
			            }else if(statusCode=='6'){
			            	html = '<span class="status s-orange">创建失败</span>';
			            }else if(statusCode=='7'){
			            	html = '<span class="status s-orange">挂载失败</span>';
			            }else if(statusCode=='8'){
			            	html = '<span class="status s-orange">发送失败</span>';
			            }else if(statusCode=='9'){
			            	html = '<span class="status s-orange">状态异常</span>';
			            }
		            	$("#status"+ebsId).html(html);
		            	$("#createTime"+ebsId).text(effectiveTime);
		            	$("#expireTime"+ebsId).text(overTime);
		            }
	     		    window.setTimeout('queryStatusLoading()',10000);
	            },
	            error:function(){
		        }
		   });
	  }
}
function goToPage(diskId){
	if(diskId==''){
		
	}else{
		$("#diskId").val(diskId);
		$("#gotoForm").attr('action','diskDetail.action');
		$("#gotoForm").submit();
	}

}
/**
 * 云硬盘名称字体大小
 */
function vmNameChange(){
    var maxTextLength = 24;//最大字体长度
    $("li h2").each(function(i){
        if ($(this).height() > 24) {
            var text = $(this).text();
			//判断是否大于长度截取字符
            if (text.length > maxTextLength) {
                var byteValLen = 0;
                var returnText = "";
                for (var i = 0; i < text.length; i++) {
                    (text[i].match(/[^x00-xff]/ig) != null) ? (byteValLen += 2) : (byteValLen += 1);
                    returnText += text[i]
                    if (byteValLen > maxTextLength) {
                        break;
                    }
                }
                $(this).text(returnText + "...");
            }
			$(this).css("font-size", "12px");
		        }
	})
}
