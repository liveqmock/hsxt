define(["text!commTpl/HuDong/orderTpl/orderMini.html",
        "text!commTpl/HuDong/orderTpl/orderInfo.html",
        "text!commTpl/HuDong/orderTpl/orderInfoLeft.html",
        "text!commTpl/HuDong/orderTpl/orderInfoRight.html",
        "text!commTpl/HuDong/orderTpl/orderInfoMiddle.html",
        "commSrc/frame/HuDong/publicTool"
        ], function(orderMiniTpl,orderInfoTpl,orderInfoLeftTpl,orderInfoRightTpl,orderInfoMiddleTpl,publicToolJs) {
	var orderXmpp = {
			init : function(){
				orderXmpp.orderClick();
				var orderList = store.get("orderList");
				var marketInfoMain = store.get("marketInfoMain");
				var listParam = new Array();
				if(orderList != null && orderList != "" && orderList != undefined){
					$.each(orderList,function(k,v){
						if(v.fromMarketId == marketInfoMain.marketId){
							listParam.push(v);
						}
					})
						if(listParam.length > 0){	
							var orderParam = {};
							orderParam["orderList"] = listParam;
							orderParam["DatetimeNew"] = publicToolJs.DatetimeNew;
							orderParam["orderType"] = orderXmpp.orderType;
							var html = _.template(orderMiniTpl,orderParam);
							$("#ddmsg").find("ul").html(html);
						}
				}
			},
			newOrder : function(paramOrder){
				var contentList = new Array();
				var list = new Array();
				list.push(paramOrder);
				var param = {};
				param["codeId"] = paramOrder.orderInfo.sub_msg_code;
				param["orderList"] = list;
				contentList.push(param);
				var orderParam = {};
				orderParam["orderList"] = contentList;
				orderParam["DatetimeNew"] = publicToolJs.DatetimeNew;
				orderParam["orderType"] = orderXmpp.orderType;
				var html = _.template(orderMiniTpl,orderParam);
				$.each($("#ddmsg ul li"),function(k,v){
					if($(v).attr("data-id") == paramOrder.orderInfo.sub_msg_code){
						$(v).remove();
						return false;
					}
				})
				$("#ddmsg").find("ul").append(html);
			},
			 orderClick : function(){
				 	//点击显示事件
					$("#ddmsg").on("click","ul>li",function(){
						publicToolJs.removeCount(this);
						var param = {};
						var orInfo = store.get("orderList");
						var marketInfoMain = store.get("marketInfoMain");
						var orderThis = new Array();
						var thisCodeId = $(this).attr("data-id");
							$.each(orInfo,function(k,v){
								if(v.codeId == thisCodeId && v.fromMarketId == marketInfoMain.marketId){
									orderThis.push(v);
									$.each(v.orderList,function(k2,v2){
										if(v2.newStr == 1){
											v2.newStr = -1;
											return;
										}
									})
									return;
								}
							})
							store.set("orderList",orInfo);
							param["thisId"] = $(this).attr("orderTime-id");
							param["orderList"] = orderThis;
							param["codeId"] = thisCodeId;
							param["NowtimeNew"] = publicToolJs.NowtimeNew;
							param["orderType"] = orderXmpp.orderType;
						var html = _.template(orderInfoTpl,param);
						$("#huDongMain").html(html);
						orderXmpp.orderRightClick();
						orderXmpp.orderLeftClick();
						orderXmpp.orderTopClick();
						orderXmpp.fenye();
					})
					
					
			 },
			 orderTopClick : function(){
				 $("#huDongMain").on("click",".gbck",function(){
					 $("#huDongMain").attr("style","position: absolute;top: 30%;left: 25%;z-index: 99");
					$("#huDongMain").html("");
				}) 
			 },
			 orderRightClick : function(){
				 $("#rightHtml").on("click",".hd_hslt_list>li",function(){
					 $(this).find("b").removeClass("unread");
					 var orderTimeId = $(this).attr("orderTime-id");
					 var bool = 0;
					 $.each($("#hd_leftScr ul li"),function(k,v){
						 if($(v).attr("orderTime-id") == orderTimeId){
							 bool = 1;
							 return;
						 }
					 })
					 if(bool == 1){
						 return;
					 }
					 $("#hd_leftScr ul li").removeClass("hd_left_cur");
					 var orderList = store.get("orderList");
					 var marketInfoMain = store.get("marketInfoMain");
					 var order2List = null;
					 var coId = $("#hd_msg_content").attr("code-id");
					 var orderParam = {};
					 	orderParam["orderTimeId"] = orderTimeId;
					 	$.each(orderList,function(k,v){
					 		if(v.codeId == coId && v.fromMarketId == marketInfoMain.marketId){
					 			order2List = v;
					 			return;
					 		}
					 	})
					 	orderParam["codeId"] = coId;
					 	orderParam["orderType"] = orderXmpp.orderType;
						orderParam["orderList"] = order2List;
						orderParam["NowtimeNew"] = publicToolJs.NowtimeNew;
						var html = _.template(orderInfoMiddleTpl,orderParam);
						$(".con_warp").html(html);
						var html = _.template(orderInfoLeftTpl,orderParam);
						$("#hd_leftScr ul").append(html);
				 
				 
				 })
			 },
			 orderLeftClick : function(){
					 $("#hd_leftScr").on("click","ul>li>i",function(){
						 if($(this).parents("ul").find("li").length < 2){
							 $("#huDongMain").html("");
						 }else{
							 if($(this).parents("li").hasClass("hd_left_cur")){
								 var nodesiblings = $(this).parents("li").siblings()[0];
								 $(nodesiblings).addClass("hd_left_cur");
								 $(nodesiblings).find("p").trigger("click");
							 }
							 $(this).parents("li").remove();
						 } 
						 orderXmpp.orderInfoColorLeftRight();
					 })
					$("#hd_leftScr").on("click","ul>li>p",function(){
						 $("#hd_leftScr ul li").removeClass("hd_left_cur");
						 $(this).parent().addClass("hd_left_cur");
						 var orderTimeId = $(this).parent().attr("orderTime-id");
						 var orderList = store.get("orderList");
						 var marketInfoMain = store.get("marketInfoMain");
						 var coId = $("#hd_msg_content").attr("code-id");
						 var orderParam = {};
						 	orderParam["orderTimeId"] = orderTimeId;
						 	$.each(orderList,function(k,v){
						 		if(v.codeId == coId && v.fromMarketId == marketInfoMain.marketId){
						 			orderList = v;
						 			return;
						 		}
						 	})
						 	orderParam["codeId"] = coId;
						 	orderParam["orderType"] = orderXmpp.orderType;
							orderParam["orderList"] = orderList;
							orderParam["NowtimeNew"] = publicToolJs.NowtimeNew;
						 var html = _.template(orderInfoMiddleTpl,orderParam);
						 $(".con_warp").html(html);
					 })
			 },
			 fenye : function(){
				 $("#rightHtml").on("change",".fenye",function(){
					 var orInfo = store.get("orderList");
					 var marketInfoMain = store.get("marketInfoMain");
					 var $this = $(this).val();
					 var thisCodeId = $("#hd_msg_content").attr("code-id");
					 var orderThis = new Array();
					 $.each(orInfo,function(k,v){
						 if(v.codeId == thisCodeId && v.fromMarketId == marketInfoMain.marketId){
							 var orderList = v.orderList;
							 var orders = new Array();
							 for(var i = orderList.length - 1;i>=0;i--){
								 orders.push(orderList[i]);
							 }
						 $.each(orders,function(k2,v2){
							 if(($this - 1)*5 <= k2 && k2 < $this*5){
								 orderThis.push(v2);
							 }
						 })
						 return;
					 }
				 })
				 var param = {};
					 param["orderList"] = orderThis;
					 param["NowtimeNew"] = publicToolJs.NowtimeNew;
					 var html = _.template(orderInfoRightTpl,param);
					 $(".hd_hslt_list").html(html);
					orderXmpp.orderInfoColorLeftRight();
			  })
			 },
			 orderInfoColorLeftRight : function(){
				 $.each($("#rightHtml .hd_hslt_list li"),function(k,v){
					 var bool = 0;
					$.each($("#hd_leftScr ul li"),function(k2,v2){
						if($(v2).attr("orderTime-id") == $(v).attr("orderTime-id")){
							$(v).find("b").removeClass("unread");
							bool = 1;
							return;
						}
					}) 
					if(bool != 1){
							$(v).find("b").addClass("unread");
					}
				 }) 
			 },
			 orderType : function(orderType){
					var orderName;
					switch(orderType)
					{
					case "20201":
						orderName = "发货提醒";
					      break;
					case "20202":
						orderName = "售后退款";
					  	  break;
					case "20203":
						orderName = "售后申诉";
					  	  break;
					case "20204":
						orderName = "违规举报";
						  break;
					case "20205":
						orderName = "取消订单退款";
						  break;
					case "20206":
						orderName = "交易成功";
						  break;
					case "20207":
						orderName = "待商家确认订单";
						  break;
					case "20208":
						orderName = "订单关闭";
						  break;
					case "20209":
						orderName = "订单支付成功";
						  break;
					case "20210":
						orderName = "取消订单";
						  break;
					case "20211":
						orderName = "退换货申请";
						  break;
					case "20212":
						orderName = "待收货确认";
						  break;
					case "20214":
						orderName = "待送货";
						  break;
					case "20215":
						orderName = "投诉";
						  break;
					case "20216":
						orderName = "投诉企业";
						  break;
					case "20217":
						orderName = "举报";
						  break;
					case "20218":
						orderName = "申请取消订单";
						  break;
					}
					return orderName;
				 }
	}
	return orderXmpp;
})