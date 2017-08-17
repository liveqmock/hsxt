define(["text!hsec_orderTpl/retail/orderHead.html"
		,"text!hsec_orderTpl/retail/orderList.html"
		,"text!hsec_orderTpl/retail/orderInfo.html"
		,"hsec_orderSrc/orderFood"
		,"hsec_orderDat/orderdat"
		,"hsec_tablePointSrc/function"]
		,function(tpl,tpl1,tpl2,orderFoodJs,ajaxRequest){
	
	//var tabSelect = "";
	var currentPageInPage = 1;
	var pageSize = 10;
	var param = {"orderStatusStr":"","firstRecordIndex":currentPageInPage,"eachPageSize":pageSize};
	var order = {
			initData : function(){
				$('#busibox').html(tpl);
				//loadDatePicker("#startTime","#endTime");
				/**日期控件**/
				$("#startTime").datepicker({
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
						$("#startTime").datepicker("option", "maxDate", selectedDate);
					}
				});
				/**日期控件**/
				$("#orderTab").selectList({
					options:[{name:'全部',value:''},
							 {name:'未付款',value:'0'},
							 {name:'待备货',value:'1,9'},
							 {name:'待发货',value:'2'},
							 {name:'待提货',value:'6'},
							 {name:'待送货',value:'7'},
							 {name:'待收货',value:'3,8'},
							 {name:'取消待确认',value:'10'}]
				});
				order.bindData(param);
				order.searchOrder();
			},
			//点击订单管理绑定数据
			bindData:function(param){
				gridObj = $.fn.bsgrid.init('orderList',{
					url:{url:comm.UrlList['orderList'],domain:'scs'},
					pageSizeSelect:true,
					pageSize:pageSize,
					otherParames:$.param(param),
					displayBlankRows:false,
					stripeRows:true,
					operate:{ 
						add : function(record, rowIndex, colIndex, options){
							if(colIndex==0){return _.template(tpl1,record)}
							if(colIndex==1){
							sTpl = '<span class="red">'+record.actuallyAmount.toFixed(2)+'</span>';
							sTpl += '<p>含运费(<span class="red">'+record.postAge.toFixed(2)+'</span>)</p>'
							return sTpl;
							}
							if(colIndex==2){
							return '<span class="blue">'+record.totalPoints.toFixed(2)+'</span>'
							}
							if(colIndex==3){
								/*if (record.isRefund==1) {
									return '<span>已申请售后</span>'
								}else{*/
									if(record.orderStatus==0){
						            	return '<span>未付款</span>'
						            }else if(record.orderStatus==1){
						            	return '<span>待备货</span>'//已付款/
						           	}else if(record.orderStatus==2){
						           		return '<span>待发货</span>'//商家备货中/
						           	}else if(record.orderStatus==3){
						           		return '<span>待确认收货</span>'
						           	}else if(record.orderStatus==4){
						           		return '<span>交易成功</span>'
						           	}else if(record.orderStatus==5){
						           		return '<span>交易关闭</span>'
						           	}else if(record.orderStatus==6){
						           		return '<span>待提货</span>'//已备货待提/
						           	}else if(record.orderStatus==7){
						           		return '<span>待送货</span>'//商家备货中/
						           	}else if(record.orderStatus==8){
						           		return '<span>待收货</span>'
						           	}else if(record.orderStatus==9){
						           		return '<span>待备货</span>'
						           	}else if(record.orderStatus==10){
						           		return '<span>买家取消待确认</span>'//已申请取消/
						           	}else if(record.orderStatus==97){
						           		return '<span>已删除</span>'
						           	}else if(record.orderStatus==98){
						           		return '<span>商家取消订单</span>'
						           	}else if(record.orderStatus==99){
						           		return '<span>买家已取消</span>'
						           	}else if(record.orderStatus==-1){
						           		return '<span>系统过期取消</span>'
						           	}else{
						           		return '<span>失效</span>'
						           	}
								//}
							}
							if(colIndex==4){
							return '<a href="javascript:void(0)" class="ddlb_ckxq_btn" orderId="'+record.odId+'" userId="'+record.userId+'" data-id="'+colIndex+'" >查看详情</a>'
							}
						}
					},
					complete:function(o,e){
						var other = eval("("+o.responseText+")").orther;
						if(other!=null){
							var https = other.url;
							$(".imgSrc").each(function(){
								var src = $(this).attr("data-src");
								$(this).attr("src",https+src);
							});
						}
						//order.bindClick();
						order.orderInfo();
					}
				});
			},
			bindClick:function(){
				/*var params; 
				$('#orderTab li').click(function(e){
					var dataId = $(e.currentTarget).attr('data-id');
					tabSelect = dataId;
					$('#orderTab li a').removeClass('active');
					$(this).find('a').addClass('active');
					
					if (dataId=='1'){										
						//未支付
					    params = {"orderStatusStr":0,"dataId": dataId,"firstRecordIndex":1,"pageSize":20};
					} else if (dataId=='2'){
						//[待商家发货] zhanghh
						params = {"orderStatusStr":1,"dataId": dataId,"firstRecordIndex":1,"pageSize":20};
					} else if (dataId=='3'){
						//已完成
						params = {"orderStatusStr":4,"dataId": dataId,"firstRecordIndex":1,"pageSize":20};
					}else if (dataId=='4'){
						//已取消
						params = {"orderStatusStr":"99,98,97,-1","dataId": dataId,"firstRecordIndex":1,"pageSize":20};
					}else if (dataId=='5'){
						//退款退货订单
						params = {"isRefund":1,"dataId": dataId,"firstRecordIndex":1,"pageSize":20};
					}else if (dataId=='6'){
						//全部
						params = {"dataId": dataId,"firstRecordIndex":1,"pageSize":20};
					}
					$(".orderList tr:gt(0)").remove();
					order.bindData(params);
				})*/
			},
			//根据条件查询订单列表
			searchOrder:function(){
				//编号输入验证
				$("#orderId,#companyNo,#resourceNo").on('focus',function(){
					$(this).on('keyup',function(){
						var num = $(this).val();
						var p1=/^[\d]+$/;
						if(!p1.test(num)){
							$(this).val("");
						}
					})
				});
				$("#orderList_search").unbind().on('click',function(){
					var orderId=$("input[name=orderId]").val(); 
					var serverNo=null;//$("input[name=serverNo]").val(); 
					var companyNo=$("input[name=companyNo]").val(); 
					var virShopName=null;$("input[name=virShopName]").val(); 
					var startTime=$("input[name=startTime]").val(); 
					var endTime=$("input[name=endTime]").val(); 
					var resourceNo=$("input[name=resourceNo]").val(); 
					var shopName=null;//$("input[name=shopName]").val(); 
					var orderStatusStr = $("#orderTab").attr("optionvalue");
					//var dataId;
					/*$(".subTabList").children().each(function(i){
						var a_class=$(this).children().attr("class");
						if(a_class=="active"){
							dataId=i+1;
						}
					});*/
					var params={};
					params.orderId = orderId;
					params.serverNo = serverNo;
					params.virShopName = virShopName;
					params.companyNo = companyNo;
					params.startTime = startTime;
					params.endTime = endTime;
					params.resourceNo = resourceNo;
					params.shopName = shopName;
					params.orderStatusStr = orderStatusStr;
					params.eachPageSize = pageSize;
					
					/*if (dataId=='1'){										
						//未支付
					    params = {"orderId":orderId,"serverNo":serverNo,"virShopName":virShopName,"companyNo":companyNo,
					    		"startTime":startTime,"endTime":endTime,"resourceNo":resourceNo,"shopName":shopName,
					    		"orderStatusStr":0,"dataId": dataId,"firstRecordIndex":currentPageInPage,"pageSize":20};
					} else if (dataId=='2'){
						//已支付
						params = {"orderId":orderId,"serverNo":serverNo,"virShopName":virShopName,"companyNo":companyNo,
					    		"startTime":startTime,"endTime":endTime,"resourceNo":resourceNo,"shopName":shopName,
					    		"orderStatusStr":1,"dataId": dataId,"firstRecordIndex":currentPageInPage,"pageSize":20};
					} else if (dataId=='3'){
						//已完成
						params = {"orderId":orderId,"serverNo":serverNo,"virShopName":virShopName,"companyNo":companyNo,
					    		"startTime":startTime,"endTime":endTime,"resourceNo":resourceNo,"shopName":shopName,
					    		"orderStatusStr":4,"dataId": dataId,"firstRecordIndex":currentPageInPage,"pageSize":20};
					}else if (dataId=='4'){
						//已取消
						params = {"orderId":orderId,"serverNo":serverNo,"virShopName":virShopName,"companyNo":companyNo,
					    		"startTime":startTime,"endTime":endTime,"resourceNo":resourceNo,"shopName":shopName,
					    		"orderStatusStr":"99,98,97,-1","dataId": dataId,"firstRecordIndex":currentPageInPage,"pageSize":20};
					}else if (dataId=='5'){
						//退款退货订单
						params = {"orderId":orderId,"serverNo":serverNo,"virShopName":virShopName,"companyNo":companyNo,
					    		"startTime":startTime,"endTime":endTime,"resourceNo":resourceNo,"shopName":shopName,
					    		"isRefund":1,"dataId": dataId,"firstRecordIndex":currentPageInPage,"pageSize":20};
					}else if (dataId=='6'){
						//全部
						params = {"orderId":orderId,"serverNo":serverNo,"virShopName":virShopName,"companyNo":companyNo,
					    		"startTime":startTime,"endTime":endTime,"resourceNo":resourceNo,"shopName":shopName,"dataId": dataId,
					    		"firstRecordIndex":currentPageInPage,"pageSize":20};
					}
					tabSelect = dataId;*/
					order.bindData(params);
				});
			},
//			update by zhanghh 20150410
			//订单详情
			orderInfo:function(){
				$("#orderList").unbind().on("click", ".ddlb_ckxq_btn", function() {
					//var paramObj = gridObj.getRecord($(this).attr("data-id"));
					var orderId=$(this).attr("orderId");
					var userId=$(this).attr("userId");
					var param={"orderId":orderId,"userId":userId};
						ajaxRequest.getOrderDetail(param,function(response){
							//response["tabSelect"] = tabSelect;
							if(response.retCode==200){
								var html = _.template(tpl2, response);
								$("#showDialog").dialog({title:"订单详情",width:1000,
									open:function(){
										$(this).html(html);
										$(".ui-dialog").css("top",100);
									},
									buttons:{
										"关闭":function(){
											$(this).dialog("destroy");
										}
									}
								});
							}else if(response.retCode==201){
								alert("对不起，请求失败");
							}else if(response.retCode==212){
								alert("登录已过期，请重新登录，谢谢");
							}
						});
					});
			}
	}
	return order;

});