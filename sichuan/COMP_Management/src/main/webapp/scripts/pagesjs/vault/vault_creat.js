

$(function() {


});
function diaclose() {
    location.reload();
}
/**
 *  创建授权申请
 *
 */
function vaultCreat() {
	$.ajax({
		type : "POST",
		url : 'queryAppOperJKStatus.action',
		data : {},
		dataType : "JSON",
		cache : false,
		success : function(data) {
            var requestID =data.queryParamResponse.requestID;//"";//
            $("#requestID").val(requestID);
			//授权模式select
            if(data.queryParamResponse.authMode=="remoteAuth"){
                var modelist =["远程授权"];
            }
            //根据requestid判断是否需要申请
            if(requestID==""){
                $("#auth_mode").empty();
                for (var i = 0; i < modelist.length; i++) {
                    if ("" != modelist[i]) {
                        $("#auth_mode").append(
                            '<option value="' + modelist[i]
                            + '">' + modelist[i]
                            + '</option>');
                    }
                }
                if(modelist[0]!=""&&modelist[0]!=null){
                    $("#uniform-auth_mode").children("span").eq(0).empty();
                    $("#uniform-auth_mode").children("span").eq(0).append(modelist[0]);
                }else{
                    $("#uniform-auth_mode").children("span").eq(0).empty();
                    $("#uniform-auth_mode").children("span").eq(0).append("--请选择--");
                }

                //审批人select
                var userlist =data.queryParamResponse.approver;//["admin"];//
                $("#approver").empty();
                for (var i = 0; i < userlist.length; i++) {
                    if ("" != userlist[i]) {
                        userlist[i]=userlist[i].split('G|T')[1];
                        $("#approver").append(
                            '<option value="' + userlist[i]
                            + '">' + userlist[i]
                            + '</option>');
                    }
                }
                if(userlist[0]!=""&&userlist[0]!=null){
                    $("#uniform-approver").children("span").eq(0).empty();
                    $("#uniform-approver").children("span").eq(0).append(userlist[0]);
                }else{
                    $("#uniform-approver").children("span").eq(0).empty();
                    $("#uniform-approver").children("span").eq(0).append("--请选择--");
                }
                vaultCreat_dialog();
            }else{
                vaultCreatById(requestID);
            }

		}
	});

}
function vaultCreat_dialog() {
    $.dialog({
        id:"detail",
        width: "60%",
        left: "50%",
        overlay: true,
        modal: false,//设置背景灰的
        shadow: true,
        draggable:false,
        flat: true,
        title: "创建授权申请",
        position: "top",
        content: document.getElementById('spanCreate'),
        button:[{
            id:'pass',
            name: '提交申请',
            callback: function () {
                var auth_mode= $("#auth_mode").val();
                var approver= $("#approver").val();
                var requestID= $("#requestID").val();
                var application_reason= $("#application_reason").val();
                if ($("#auth_num").get(0).checked) {
                    var auth_timemode= "1";
                    var auth_time_val="";
                }else{
                    var auth_timemode= "";
                    var auth_time_val= ($("#auth_time_val").val());
                }
                var da_val = {requestID:requestID,auth_mode:auth_mode,approver:approver,auth_timemode:auth_timemode,auth_time_val:auth_time_val,application_reason:application_reason};
                $.ajax( {
                    type : "POST",
                    url : 'createAppRequest.action',
                    data : da_val,
                    dataType : "JSON",
                    cache : false,
                    async :false,
					success : function(data) {
                        var requestID =data.createqueryParamResponse.requestID;//"23";//
                        var auth_status ="";
                        if(data.createqueryParamResponse.applyStatus=="unApprove"){
                            auth_status ="待审批";
                        }else if(data.createqueryParamResponse.applyStatus=="approved"){
                            auth_status ="已审批";
                        }else if(data.createqueryParamResponse.applyStatus=="refused"){
                            auth_status ="已拒绝";
                        }else if(data.createqueryParamResponse.applyStatus=="valid"){
                            auth_status ="已生效";
                        }else if(data.createqueryParamResponse.applyStatus=="invalid"){
                            auth_status ="已过期";
                        }
                        var timeout =data.createqueryParamResponse.authEndTime;//"2019-12-18 10:16:21";//
                        vaultCreatRemote(requestID,auth_status,timeout);
                    }
                });

            },
            focus: true
        },{
            id:'console',
            name: '取消',
            callback: function () {
                diaclose();
            }
        }],
    });
    $('a.aui_close').hide();
    $('.aui_dialog').css("position","fixed");
    $('.aui_dialog').css("left","20%");
    $('.aui_dialog').css("bottom","20%");
    $('.aui_dialog').css("width","60%");
    $('.aui_dialog').css("height","60%");
    $('.aui_dialog').css("background","#fcfcfc");
    $('.aui_dialog').css("z-index","10");
}
/**
 *  已申请requestId的查询
 *
 */
function vaultCreatById(requestID){
    $.ajax({
        type : "POST",
        url : 'queryJKStatusByID.action',
        data : requestID,
        dataType : "JSON",
        cache : false,
        success : function(data) {
            var auth_status ="";
            if(data.queryParamResponseByID.applyStatus=="unApprove"){
                auth_status ="待审批";
            }else if(data.queryParamResponseByID.applyStatus=="approved"){
                auth_status ="已审批";
            }else if(data.queryParamResponseByID.applyStatus=="refused"){
                auth_status ="已拒绝";
            }else if(data.queryParamResponseByID.applyStatus=="valid"){
                auth_status ="已生效";
            }else if(data.queryParamResponseByID.applyStatus=="invalid"){
                auth_status ="已过期";
            }
            var timeout =data.queryParamResponseByID.authEndTime;//"2019-12-18 10:16:21";//
            if(auth_status !="approved"&&auth_status !="valid"){
                vaultCreatRemote(requestID,auth_status,timeout);
            }
        }
    });
}

/**
 *  远程认证
 *
 */
function vaultCreatRemote(requestID,auth_status,timeout){
    ($("#auth_status")).text(auth_status);
    ($("#timeout")).text(timeout);
    //重发60秒
    setTime(60);
    $.dialog({
        id:"detail3",
        height:"50%",
        width: "60%",
        left: "50%",
        overlay: true,
        modal: true,//设置背景灰的
        shadow: true,
        flat: true,
        draggable:false,
        title: "远程认证",
        position: "top",
        content: document.getElementById('remoteAuth'),
        button:[{
            id:'pass',
            name: '认证',
            callback: function () {
                var da_val = {requestID:requestID};
                $.ajax( {
                	type : "POST",
                	url : 'remoteAuth.action',
                	data : da_val,
                	dataType : "JSON",
                	cache : false,
                    success : function(data) {
                        if(data.remotequeryParamResponse.authResult=="false"){
                            $.compMsg( {
                                type : 'error',
                                msg : data.message
                            });
                            return;
                        }
                        // backList("远程认证成功!","");
                    }
                });
            },
            focus: true
        },{
            id:'console',
            name: '取消',
            callback: function () {
                diaclose()
            }
        }],
    });
    $('a.aui_close').hide();
    $('.aui_dialog').css("position","fixed");
    $('.aui_dialog').css("left","20%");
    $('.aui_dialog').css("bottom","20%");
    $('.aui_dialog').css("width","60%");
    $('.aui_dialog').css("height","60%");
    $('.aui_dialog').css("background","#fcfcfc");
    $('.aui_dialog').css("z-index","10");
}
/**
 *  密码重发
 *
 */
function reSendJKPass(){
    var requestID= $("#requestID").val();
    $.ajax({
        type : "POST",
        url : 'reSendJKPass.action',
        data : requestID,
        dataType : "JSON",
        cache : false,
        success : function(data) {
            setTime(60);
        }
    });
}
/**
 *  状态刷新
 *
 */
function applyStatusRefresh(){
    var requestID= $("#requestID").val();
    $.ajax({
        type : "POST",
        url : 'queryJKStatusByID.action',
        data : requestID,
        dataType : "JSON",
        cache : false,
        success : function(data) {
            var auth_status ="";
            if(data.queryParamResponseByID.applyStatus=="unApprove"){
                auth_status ="待审批";
            }else if(data.queryParamResponseByID.applyStatus=="approved"){
                auth_status ="已审批";
            }else if(data.queryParamResponseByID.applyStatus=="refused"){
                auth_status ="已拒绝";
            }else if(data.queryParamResponseByID.applyStatus=="valid"){
                auth_status ="已生效";
            }else if(data.queryParamResponseByID.applyStatus=="invalid"){
                auth_status ="已过期";
            }
            ($("#auth_status")).text(auth_status);
        }
    });
}
// //计算剩余时间
// function getTime(timeout) {
//     var curTime = new Date();
//     var date = curTime.getDate();//得到日期
//     var hour = curTime.getHours();//得到小时
//     var minu = curTime.getMinutes();//得到分钟ti
//     var sec = curTime.getSeconds();//得到秒
//     //下面是结束时间
//     var str1 = timeout.split(' ');
//     var day = str1[1].split('-');
//     day = day[2];//结束日期
//     var str = str1[1].split(':');//之后的c为时 str[1]为分 str[2]为秒
//
//     if(day-date==0){
//         var endHour = str[0]-hour;
//         if(str[1]-minu>=0){
//             var endHour = str[0]-hour;
//         }
//     }else{
//         var endHour = str[0]-hour+24;
//
//     }
//
//     return endtime;
// }
//60s倒计时实现逻辑

function setTime(countdown) {
    var now = new Date();
    var year = now.getFullYear(); //得到年份
    var month = now.getMonth();//得到月份
    var date = now.getDate();//得到日期
    var hour = now.getHours();//得到小时
    var minu = now.getMinutes();//得到分钟
    var sec = now.getSeconds();//得到秒
    if(minu==59){
        minu = 0;
        if(hour==23){
            hour=0;
            data++
        }else{
            hour++
        }
    }else{
        minu++
    }
    if(month<10){
        month = "0" + month;
    }
    if(date<10){
        date = "0" + date;
    }
    if(hour <10){
        hour = "0" + hour;
    }
    if(minu <10){
        minu = "0" + minu;
    }
    if(sec <10){
        sec = "0" + sec ;
    }

    clearInterval(this.tool);
    setTimeout(() =>{
        if (countdown == 0) {
        ($("#reSendJKPass")).show();
        ($("#reSendJKPassText")).hide();
        clearInterval(tool)
    }else {
        ($("#reSendJKPass")).hide();
        ($("#reSendJKPassText")).show();
        ($("#reSendJKPassText")).text("("+countdown+")秒后可重新发送短信");
        countdown --;
    }
}, 0)
    var tool = setInterval(() =>{
        if (countdown == 0) {
        ($("#reSendJKPass")).show();
        ($("#reSendJKPassText")).hide();
        clearInterval(tool)
    }else {
        ($("#reSendJKPass")).hide();
        ($("#reSendJKPassText")).show();
        ($("#reSendJKPassText")).text("("+countdown+")秒后可重新发送短信");
        countdown--;
    }
}, 1000)
    this.tool = tool;

}