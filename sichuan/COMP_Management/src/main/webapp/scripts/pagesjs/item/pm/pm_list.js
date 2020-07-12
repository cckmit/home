$(function(){
    //菜单显示当前，开发时删除
    $(".left-menu li:contains('服务条目管理')").addClass("selected").next().show();
    $(".left-menu dl a:contains('物理机')").parent().addClass("selected");
    $("#head-menu li a:contains('产品管理')").parent().addClass("head-btn-sel");
    //自定义form样式
    $(":text,:checkbox,select").uniform();
    $.uniform.update();
    $("#apply_host").click(function (){
        window.location.href = 'pmItemCreateInfo.action';
    });
	$("#search").click(function(){
		queryItems();
	});
	var itemCode = $("#itemCode").val();
	if('5'==itemCode||'7'==itemCode){
		done($("#doneId"));
  	}
	$("#beginCreateTime").val(_fomart14($("#beginCreateTime").val()));
	$("#endCreateTime").val(_fomart14($("#endCreateTime").val()));
});

//条目修改跳转
function onItemModify(obj, itemId){
    if (itemId != '') {
        $("#itemId").val(itemId);
        $("#itemModify").attr('action', 'pmItemInfo.action');
        $("#itemModify").submit();
    }
}

//条目详情
function onItemDetail(itemId){
    if (itemId != '') {
        $("#itemId").val(itemId);
        $("#itemModify").attr('action', 'pmItemDetail.action');
        $("#itemModify").submit();
    }
}
//willTables = '<col style="width:5%;" />';
var willTables = '<col style="width:18%;" />';
    willTables += '<col style="width:18%;" />';
    willTables += '<col style="width:18%;" />';
    willTables += '<col style="width:18%;" />';
    willTables += '<col style="width:28%;" />';
  	willTables += '<tr>';
  	//willTables += '<th class="nl" style="width:5%;"><input type="checkbox"></th>';
    willTables += '<th class="nl" style="width:18%;">条目名称</th>';
    willTables += '<th style="width:18%;">条目状态</th>';
    willTables += '<th style="width:18%;">创建人</th>';
    willTables += '<th style="width:18%;">创建时间</th>';
    willTables += '<th style="width:28%;">操作</th>';
  	willTables += '</tr>';
//doneTables = '<col style="width:5%;" />';
var doneTables = '<col style="width:18%;" />';
    doneTables += '<col style="width:18%;" />';
    doneTables += '<col style="width:18%;" />';
    doneTables += '<col style="width:18%;" />';
    doneTables += '<col style="width:28%;" />';
	doneTables += '<tr>';
    //doneTables += '<th class="nl" style="width:5%;"><input type="checkbox"></th>';
    doneTables += '<th class="nl" style="width:18%;">条目名称</th>';
    doneTables += '<th style="width:18%;">条目状态</th>';
    doneTables += '<th style="width:18%;">创建人</th>';
    doneTables += '<th style="width:18%;">创建时间</th>';
    doneTables += '<th style="width:28%;">操作</th>';
	doneTables += '</tr>';

function getPageData(url) {
	queryItems(url);
}

//查询
function queryItems(url){
	var itemType = '0';
    var itemName = $("#itemName").val();
    var status = $("#status").val();
    var beginCreateTime = _dateToStr($("#beginCreateTime").val());
    var endCreateTime = _dateToStr($("#endCreateTime").val());
    var stype = $(".select a").html();
    var da_val;
	if (url == '' || url == undefined) {
		url = 'pmItemQuery.action';
		if (stype == '未发布') {
			da_val = {
				'itemName': itemName,
				'status': status,
				'beginCreateTime': beginCreateTime,
				'endCreateTime': endCreateTime,
				'publish': '0'
			};
		}
		else {
			if (status == '2') {
				da_val = {
					'itemName': itemName,
					'status': '7',
					'beginCreateTime': beginCreateTime,
					'endCreateTime': endCreateTime,
					'publish': '1'
				};
			}
			else {
				da_val = {
					'itemName': itemName,
					'recommend': status,
					'beginCreateTime': beginCreateTime,
					'endCreateTime': endCreateTime,
					'publish': '1'
				};
			}
		}
	}
    $.ajax({
        url: url,
        type: 'post',
        data: da_val,
        cache: false,
        dataType: 'json',
        success: function(data){
			if(data!=null){
				var willHtml = willTables;
				var doneHtml = doneTables;
				var list = data.list;
				var pageBar = data.page;
				for(var i=0;i< list.length;i++){
					var entity = list[i];
					//alert(entity.status.getCode());
					var statusCode = '';
					var statusDesc = '';
					if(entity.status=='CREATING'){
						statusCode = '0';
						statusDesc = '已保存'
					}else if(entity.status=='PENDING'){
						statusCode = '1';
						statusDesc = '条目待审'
					}else if(entity.status=='PENDPASS'){
						statusCode = '2';
						statusDesc = '审批通过'
					}else if(entity.status=='UN_PENDPASS'){
						statusCode = '3';
						statusDesc = '审批不通过'
					}else if(entity.status=='PUBLISH'){
						statusCode = '4';
						statusDesc = '发布待审'
					}else if(entity.status=='PUBLISHPASS'){
						statusCode = '5';
						statusDesc = '已发布'
					}else if(entity.status=='UN_PUBLISHPASS'){
						statusCode = '6';
						statusDesc = '发布不通过'
					}else if(entity.status=='PULLED'){
						statusCode = '7';
						statusDesc = '下架'
					}else if(entity.status=='DELETED'){
						statusCode = '8';
						statusDesc = '删除'
					}
					var recommendCode='';
					if(entity.recommend=='UN_RECOMMENDATION'){
						recommendCode = '0';
					}else if(entity.recommend=='RECOMMENDATION'){
						recommendCode = '1';
					}
					
					if(statusCode!='5' && statusCode!='7'){
						willHtml+='<tr>';
		                //willHtml+='<td><input type="checkbox" name="select" ></td>';
		                willHtml+='<td>'+entity.itemName+'</td>';
		                willHtml+='<td>'+statusDesc+'</td>';
		                willHtml+='<td>'+entity.createUser+'</td>';
		                willHtml+='<td>'+_fomart14(entity.createTime)+'</td>';
		                willHtml+='<td class="table-opt-block">';
	                	willHtml+='<a href="javascript:void(0)" onclick="onItemDetail(\''+entity.itemId+'\');return false;">详情</a>';
						if (statusCode == '0' || statusCode == '3') {
							willHtml+='<a href="javascript:void(0);"  onclick="onItemModify(this,\''+entity.itemId+'\');return false;">修改</a>';
						}
						if (statusCode == '0') {
	                    	willHtml+='<a href="javascript:void(0)" onclick="onItemSubmit(this,\''+entity.itemId+'\');return false;">提交</a>';
						}
						if (statusCode == '2') {
	                    	willHtml+='<a href="javascript:void(0);" onclick="onItemSend(this,\''+entity.itemId+'\');return false;">发布</a>';
						}
						if (statusCode == '0') {
							willHtml+='<a href="javascript:void(0);" onclick="onItemDel(this,\''+entity.itemId+'\');return false;">删除</a>';
						}
		                willHtml+='</td>';
		              	willHtml+='</tr>';
					}else if(statusCode=='5' ||statusCode=='7'){
						doneHtml+='<tr>';
		                //doneHtml+='<td><input type="checkbox" name="select" ></td>';
		                doneHtml+='<td>'+entity.itemName+'</td>';
		                doneHtml+='<td>'+statusDesc+'</td>';
		                doneHtml+='<td>'+entity.createUser+'</td>';
		                doneHtml+='<td>'+_fomart14(entity.createTime)+'</td>';
		                doneHtml+='<td class="table-opt-block">';
	                	doneHtml+='	<a href="javascript:void(0)" onclick="onItemDetail(\''+entity.itemId+'\');return false;">详情</a>';
						if(statusCode!='7'){
	                    	doneHtml+='<a href="javascript:void(0);" onclick="onItemDown(this,\''+entity.itemId+'\');return false;">下架</a>';
						}
						if (recommendCode== '0'&& statusCode!='7') {
							doneHtml+='<a href="javascript:void(0);" onclick="onItemCommend(this,\''+entity.itemId+'\');return false;">推荐</a>';
						}
						if (recommendCode=='1'&& statusCode!='7') {
	                    	doneHtml+='<a href="javascript:void(0);" onclick="onItemCommend(this,\''+entity.itemId+'\');return false;">取消推荐</a>';
						}
		                doneHtml+='</td>';
		              	doneHtml+='</tr>';
					}
				}
				$("#approval-will table").empty();
				$("#approval-will table").append(willHtml);
				$("#approval-done table").empty();
				$("#approval-done table").append(doneHtml);
				$(".pageBar").html(pageBar);
				$(":radio,:text,:checkbox").uniform();
				$("select[id!='status']").uniform();
   				$.uniform.update();
			}
        }
    });
}
//操作后更新界面信息
function initData(){
	queryItems();
}
