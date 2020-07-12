function selectPool(pool) {
	var url = "../resourceManagement_resView/allResourceAction.action";
	if (pool == "yyzc") {
		url += "?resPoolId=CIDC-RP-01";
		$("#yyzc").attr("href", url);
	} else if(pool == "wg"){
		url += "?resPoolId=CIDC-RP-02";
		$("#wg").attr("href", url);
	}
	url += "?resPoolId="+pool;
	$("#"+pool+"A").attr("href", url);
}

$(function() {
	var resPoolId = document.getElementById("resPoolId").value;
	if(resPoolId == "CIDC-RP-01"){
		$("#yyzcDiv").addClass("BussTitlefocus1");
		$("#wgDiv").addClass("BussTitle2");
	}
	if(resPoolId == "CIDC-RP-02"){
		$("#yyzcDiv").addClass("BussTitle1");
		$("#wgDiv").addClass("BussTitlefocus2");
	}
		$(".poolDiv").each(function(index){
        if($(this).attr('id') == resPoolId+"Div"){
        	if(index == 0){
        		$(".poolDiv").addClass("BussTitle2");
        		$("#"+resPoolId+"Div").removeClass("BussTitle2").addClass("BussTitlefocus1");
        	}else{
        		$(".poolDiv").addClass("BussTitle2");
        		$(".poolDiv").eq(0).removeClass("BussTitle2").addClass("BussTitle1");
        		$("#"+resPoolId+"Div").removeClass("BussTitle2").addClass("BussTitlefocus2");
        	}
        }
    });
});