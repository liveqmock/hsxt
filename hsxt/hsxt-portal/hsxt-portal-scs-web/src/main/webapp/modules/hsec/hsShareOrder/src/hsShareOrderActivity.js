define(["hsec_hsShareOrderDat/hsShareOrderActivity"
        ,"text!hsec_hsShareOrderTpl/shareOrderSearch.html"
        ,"text!hsec_hsShareOrderTpl/imgInfo.html"
        ,"text!hsec_hsShareOrderTpl/showList.html"
        ,"hsec_tablePointSrc/tablePoint"
        ,"hsec_tablePointSrc/function"]
        ,function(ajaxRequest,searchTpl,infoTpl,showTpl,tablePoint){
	var selectParam={};
	var hsShareOrder ={
			queryParam : function(currentPageIndex){
				selectParam={};
				selectParam["orderId"]=$("#orderId").val();
				selectParam["itemName"]=$("#itemName").val();
				if(''!=$("#beginPrice").val()&& null!=$("#beginPrice").val()){
					selectParam["beginPrice"]=$("#beginPrice").val();
				}
				if(''!=$("#endPrice").val()&& null!=$("#endPrice").val()){
					selectParam["endPrice"]=$("#endPrice").val();
				}
				selectParam["shopId"]=$("#shopId").val()!=''?$("#shopId").val():'';
				selectParam["userName"]=$("#userName").val();
				selectParam["cnt"]=$("#cnt").val();
				var beginTime = $("#beginTime").val();
				var endTime = $("#endTime").val();
				if(beginTime != '' && beginTime != null){
					selectParam["beginTime"]=new Date(beginTime);
				}
				if(endTime != '' && endTime != null){
					var d = new Date(endTime);
					d.setDate(d.getDate()+1); 
					selectParam["endTime"] = d;
				}
				selectParam["currentPageIndex"] = currentPageIndex!=null?currentPageIndex:1;
			},
		bindData : function(){
				$("#busibox").html(searchTpl);
				//loadDatePicker("#beginTime","#endTime");
				/**日期控件**/
				$("#beginTime").datepicker({
					changeMonth : true,
					changeYear : true,
					dateFormat : "yy-mm-dd",
					maxDate : comm.getCurrDate(),
					onClose : function(selectedDate){
						$("#endTime").datepicker("option", "minDate", selectedDate);
					}
				});
				$("#endTime").datepicker({
					changeMonth : true,
					changeYear : true,
					dateFormat : "yy-mm-dd",
					maxDate : comm.getCurrDate(),
					onClose : function(selectedDate){
						$("#beginTime").datepicker("option", "maxDate", selectedDate);
					}
				});
				/**日期控件**/
				hsShareOrder.bindTable(selectParam);
		},
		bindTable : function(selectParam){
			
			selectParam.eachPageSize = 10;
			
			gridObj = $.fn.bsgrid.init('tableShare',{
				url:{url:comm.UrlList['listShareOrderByParam'],domain:'scs'},
				otherParames:$.param(selectParam),
				pageSize:10,
				pageSizeSelect:true,
				displayBlankRows:false,
				lineWrap:true,
				stripeRows:true,
				rowSelectedColor:true,
				operate:{
					add : function (record, rowIndex, colIndex, options){
						if(colIndex==0){
							return _.template(infoTpl,record);
						}
						if(colIndex==3){
							return _.template(showTpl,record);
						}
						if(colIndex==4){
							if(record.delStatus == 0){
					           return '<button name="btnDelete" value="'+record.id+'" type="button" class="btn-search ml10 new_add">删除</button>'
					        }else{
					           return '<button type="button" class="btn-search ml10 red"><b>已删除</b></button>'
					        }
						}
					}
				},
				complete:function(o,e,c){
					var other = eval("("+o.responseText+")").orther;
					var https = other.httpUrl;
					$(".imgSrc").each(function(){
						var src = $(this).attr("data-src");
						$(this).attr("src",https+src);
					})
					
					hsShareOrder.bindHeadClick();
					hsShareOrder.btnDeleteClick();
					hsShareOrder.bindClick();
				}
			});
		},
		bindHeadClick : function(){
			//编号输入验证
			$("#orderId").on('focus',function(){
				$(this).on('keyup',function(){
					var num = $(this).val();
					var p1=/^[\d]+$/;
					if(!p1.test(num)){
						$(this).val("");
					}
				})
			});
			$("#btnSearch").click(function(){
				hsShareOrder.queryParam(1);
				if(parseFloat($("#beginPrice").val()) > parseFloat($("#endPrice").val())){
					tablePoint.tablePoint("区间起始价格不能大于结束价格");
					return;
				}else{
					hsShareOrder.bindTable(selectParam);
				}
			})
		},
		//删除晒单
		btnDeleteClick : function(){
			$("button[name='btnDelete']").click(function(){
				var id = $(this).attr("value");
				$("#prop").dialog({
					title:"提示信息",
					modal : true,
					show : true,
					open : function(){
						$("#prop").html();
						$(".ui-dialog-titlebar-close").click(function(){
							$(this).dialog("destroy");
						});
					},
					buttons : {
						"确定" : function(){
							ajaxRequest.deleteShardeOrderById(id,function(response){
								if(response.retCode == 200){
									tablePoint.tablePoint("删除晒单成功！");
									$("#btnSearch").click();
								}else{
									tablePoint.tablePoint("删除晒单失败！");
									$("#btnSearch").click();
								}
							});
							$(this).dialog("destroy");
						},
						"取消" : function(){
							$(this).dialog("destroy");
						}
					}
				});
			});
		},
		//add by zhanghh 2016-03-10 点击查看大图
		bindClick : function(){
			$(".tr_img").hover(
						function(){$(this).find('.arrow_left, .arrow_right').stop().fadeIn(100)},
						function(){$(this).find('.arrow_left, .arrow_right').stop().fadeOut(100)}
						)
						function viewImg(){
							var n=1;
							
						$(".arrow_right").on('click',function(){
							
							var imgWarp=$(this).siblings(".td_img").children(".inner_img"),
							imgNum=imgWarp.children("img").size(),
							visibleNum=parseInt($(this).siblings(".td_img").width()/73);
								imgWarp.width(imgNum*77+'px');
								n=n<=imgNum-visibleNum?n:n-1;
								imgWarp.animate({left:-77*n+"px"},100);
								n+=1;
								})	
							$(".arrow_left").on('click',function(){
								var imgWarp=$(this).siblings(".td_img").children(".inner_img")
								n=n==1?2:n;
								imgWarp.animate({left:-77*(n-2)+"px"},100);
								n-=1;
						
								})
							}
							viewImg();

						  
							//商品切换
							var goodNum=$(".goods_detail dl").length;
							$(".goods_detail dl").hide().eq(0).show();
							if(goodNum==0){$(".page").hide();}
							else{
								var pHtml=""
								for(var i=1;i<=goodNum;i++){
									pHtml+="<i>"+i+"</i>";
									}
								$(".page").html(pHtml);
								};
							$(".page i").on('click',function(){
								var pIndex=$(this).index();
								$(".goods_detail dl").hide().eq(pIndex).show();
								});   
							
							/*图片点击放大*/
							$('.inner_img img').on('click',function(){
								var arr = new Array();
								$.each($(this).parent().find("img"),function(k,v){
									arr.push($(v).attr("src"));
								})
								tablePoint.imgBig(arr,this);
							});
		}
	}
	return hsShareOrder;
});