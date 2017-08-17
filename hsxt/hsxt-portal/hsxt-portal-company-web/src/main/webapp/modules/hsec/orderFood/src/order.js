define([   
         "text!hsec_orderFoodTpl/search.html",
         "text!hsec_orderFoodTpl/orderHeader.html",   
         "text!hsec_orderFoodTpl/orderList.html",  
         "text!hsec_orderFoodTpl/orderDetail.html",     
         "hsec_orderFoodDat/orderList",
         "text!hsec_orderFoodTpl/addOrderFood/selectFoods.html", 
         "text!hsec_orderFoodTpl/addOrderFood/orderInfos.html", 
         "text!hsec_orderFoodTpl/addOrderFood/foodsList.html", 
         "text!hsec_orderFoodTpl/sendDialog.html",
         "text!hsec_orderFoodTpl/payOrderDialog.html",    
         "text!hsec_orderFoodTpl/orderDetail/inOrder.html",
         "text!hsec_orderFoodTpl/orderDetail/sendOrder.html",
         "text!hsec_orderFoodTpl/orderDetail/takeOrder.html",
         "text!hsec_orderFoodTpl/modifyOrder.html",   
         "text!hsec_orderFoodTpl/shopCounterOption.html",
         "hsec_tablePointSrc/tablePoint",
         "hsec_orderFoodSrc/parseResponse",
         "hsec_orderFoodSrc/function" 
         ], function(searchTpl,orderHeader,orderList,orderDetail,ajaxRequest,
        		 dianCaiSelectFoodsTpl,dianCaiOrderInfosTpl,dianCaifoodsListTpl,
        		 sendTpl,payOrderDialog,
        		 inOrder,sendOrder,takeOrder,modifyOrder,shopCounterOption,
        		 tablePoint) {
		var orderStatusStr="1,-3,8,2,6,7,10,11,99,-1,4,9";  //查询的全部的订单状态
		//var repastForm='1';		//就餐形式 1:店内就餐，2 送餐
		var index=0;		//orderStatusStr:orderStatusStr,
		var queryParam = {firstRecordIndex:1};//
		//单个菜品可选数量
		var maxAddCount;
		if(!maxAddCount){
			ajaxRequest.getAddFoodCount(function(rp){
				if(rp.retCode == 200){
					maxAddCount = rp.data;
				}
			});	
		}
		var shop = {
			getsortIndex : function(){
				var active = $("#orderFoodTabList a[class=active]");
				var index  = active.attr("sortindex");
				orderStatusStr = active.attr("orderstatusstr");
				return index;
			},
			selectList : function(){
				$("#repastForm").selectList({
					options:[
	                    {name:'全部',value:''},
						{name:'店内消费',value:'1'},
						{name:'外卖',value:'2'},
						{name:'门店自提',value:'3'}
					]
				});
				
				$("#orderStatus").selectList({
					options:[
	                    {name:'全部',value:''},
						{name:'未确认',value:'1,-3,8'},
						{name:'待送餐/待自提/待就餐',value:'2,9'},
						{name:'送餐中',value:'11'},
						{name:'就餐中',value:'6'},
						{name:'待结账',value:'7'},
						{name:'交易完成',value:'4'},
						{name:'取消未确认',value:'10'},
						{name:'已取消',value:'99,-1'}
					],
					optionWidth:135,
					optionHeight:150
				});
				//加载送货员
				ajaxRequest.listDeliver({},function(reps){
					if(reps.retCode == 200){
						var opts = new Array();
						opts[0] = {name:"全部",value:""};
						$.each(reps.data,function(key,d){
						    var idAndshopIdStr = d.id +','+d.shopId;
							opts[key+1] = {name:d.name,value:idAndshopIdStr}
						});
						$("#sendPeopleId").selectList({
							options:opts,
							width:115,
							optionWidth:150,
							optionHeight:200
						});
					}
				});				
//				$("#shopId").selectList({
//					options:[],
//					width:115
//				});
				var shopCounterOptionHtml="";
				ajaxRequest.getSalerShopStores({},function(response) {
					if (response.retCode == "200") {
						var opts = new Array();
						opts[0] = {name:"全部",value:""};

						$.each(response.data,function(key,d){
							opts[key+1] = {name:d.salerShopStore.name,value:d.salerShopStore.id}
						});
						$("#shopId").selectList({
							options:opts,
							width:115,
							optionWidth:150,
							optionHeight:200
						})
						//add by zhucy 2016-03-30 营业点添加 title提示
						.next().find('li:gt(0)').each(function(){
							var _this = $(this);
							_this.attr('title',_this.text());
						});
					}
					
				})
			},
			bindData : function() {
				var index = shop.getsortIndex();
				$('#busibox').html(_.template(searchTpl,{"queryIndex":index}));
				// 初始化下拉框数据
				shop.selectList();
				//loadDatePicker("#beginTime","#endTime");
				/**日期控件**/
				$("#beginTime").datepicker({
					changeMonth : true,
					changeYear : true,
					dateFormat : "yy-mm-dd",
					maxDate : comm.getCurrDate(),
					onClose : function(selectedDate){
						$( "#endTime" ).datepicker( "option", "minDate", selectedDate );
					}
				});
				$("#endTime").datepicker({
					changeMonth : true,
					changeYear : true,
					dateFormat : "yy-mm-dd",
					maxDate : comm.getCurrDate(),
					onClose : function(selectedDate){
						$( "#beginTime" ).datepicker( "option", "maxDate", selectedDate );
					}
				});
				/**日期控件**/
				shop.loadData(queryParam);
			},
			//查询
			bindClickQuery:function(){
				var index = shop.getsortIndex();

				//编号输入验证
				$("#orderId,#buyerAccountNo").on('focus',function(){
					$(this).on('keyup',function(){
						var num = $(this).val();
						var p1=/^[\d]+$/;
						if(!p1.test(num)){
							$(this).val("");
						}
					})
				});
				//已送餐订单 营业点 与 送货员 二级联动
				$("#shopId").on('change',function(){
					if(index == 4){
						var shopId = $(this).attr("optionvalue");
							$("#sendPeopleId").next().find('li').each(function(){
								var _this = $(this);
								var datas = _this.attr("data-value").split(",");
								if(shopId != "" && datas[1] != shopId  ){
									_this.hide();
								}else{
									_this.show();
								}
								if(datas == ""){
									_this.show();
									_this.click();
								}
							});
					}
				});
				//搜索查询
				$(".btn-search").unbind().on('click',function(){	
					shop.loadData(queryParam);
				});	
			},
			//加载list
			loadData : function (queryParam){
				//获得页面的查询参数
				var rf = $("#repastForm").attr("optionvalue");

				if(rf){
					queryParam.repastForm = rf;
				}else{
					queryParam.repastForm = "";
				}
				
				queryParam.orderId=$(".searchBar").find("input[name='orderId']").val();
				queryParam.startTime=$("#beginTime").val();
				queryParam.endTime=$("#endTime").val();
				queryParam.shopId=$("#shopId").attr("optionvalue");
				queryParam.buyerAccountNo=$(".searchBar").find("input[name='buyerAccountNo']").val();

				//送餐员
				var sendPeopleStr = $("#sendPeopleId").attr("optionvalue");
				var sendPeopleId = "";
				if(sendPeopleStr){
					var sendPeopleArray =  sendPeopleStr.split(',');
					sendPeopleId = sendPeopleArray[0];
				}
				queryParam.deliveryId = sendPeopleId;
				
				var os=$("#orderStatus").attr("optionvalue");
				if(comm.isNotEmpty(os)){
					queryParam.orderStatusStr=os;
				}else{
					queryParam.orderStatusStr=orderStatusStr;
				}
				queryParam.eachPageSize=10;
				var gridObj;
		        $(function () {
		            gridObj = $.fn.bsgrid.init('foodOrderTable', {
		                url:{url:comm.UrlList['orderFoodList'],domain:"sportal"},  
		                // autoLoad: false,
		                //pageSizeSelect: true,
		                pageSize: 10,
		                otherParames:queryParam,
		                stripeRows: true,  //行色彩分隔 
						displayBlankRows: false,    //显示空白行
						lineWrap : true,
						operate: {
							add: function(record, rowIndex, colIndex, options){
								var str = "";
								var repastForm = record.repastForm;
								var orderStatus = record.orderStatus;
								switch (colIndex) {
									case 0:
										var queryIndex  = shop.getsortIndex();
										if(queryIndex=='1' || queryIndex=='5'){
							                str = "<input type='checkbox' name='batcheSelect' orderId='"+record.orderId+"' userId='"+record.userId+"' style='width: 14px;height: 14px;' class='mr5'>";
							            }
										str += record.orderId;
										break;
									case 1:
										if(repastForm=='1'){
					                  		str = "店内消费";
				                	  	} else if(repastForm=='2') {
				                	  		str = "外卖";
						   				} else if(repastForm=='3') {
						   					str = "门店自提";
						   				}  
										break;
									case 3:
										str = '<span class="fb red">'+comm.formatMoneyRound(record.amountTotal)+'</span>';
										if(repastForm == '1'){
											var tl = record.amountTotal + (record.amountOther || 0);
											str = '<span class="fb red">'+comm.formatMoneyRound(tl)+'</span>';
											str += '<p>(已付定金:<span class="red">'+comm.formatMoneyNumber(record.moneyEarnest)+'</span>)</p>';
											
											if(record.amountCoupon){
												var amountCoupon;
												try{
						            				var ci = eval("("+record.couponInfo+")");
						            				if(ci.num!=null){
						            					amountCoupon = ci.num * ci.faceValue;
						            				}else{
						            					amountCoupon = record.amountCoupon;
						            				}
						            			}catch(e){
						            				amountCoupon = record.amountCoupon;
						            			}
						            			str += '<p>(折扣金额:<span class="red">'+comm.formatMoneyNumber(amountCoupon)+'</span>)</p>';
											}
											
											str += '<p>(服务费:<span class="red">'+comm.formatMoneyNumber(record.amountOther)+'</span>)</p>';
											
											
										}else if(repastForm == '2'){
											var l = record.amountLogistic || 0;
											str = '<span class="fb red">'+comm.formatMoneyRound(record.amountTotal + l)+'</span>';
											str += '<p>(含配送费:<span class="red">'+comm.formatMoneyNumber(l)+'</span>)</p>';
										}
										break;
									case 4:
										str = '<span class="fb blue">'+comm.formatMoneyNumber(record.pointsTotal)+'</span>';
										break;
									case 6:
										var planTime = comm.formatDate(record.planTime,'yyyy-MM-dd hh:mm');
										var deliverTime = comm.formatDate(record.deliverTime,'yyyy-MM-dd hh:mm')
										if(repastForm=='2'){
					                		if(orderStatus=='4'){
					                			str = "<p>时间："+deliverTime+"</p>";	
						                	}else{
						                	    str = "<p>时间："+planTime+"</p>";	
						                	}                		
					                	    str += "<p>地址："+record.receiverAddr+"</p>";
					                	}else if(repastForm=='1'){
					                		str = "<p>人数："+record.personCount+" </p><p>时间："+planTime+"</p>";
					                	}else if(repastForm=='3'){
					                		str = "<p>时间："+planTime+"</p>";
					                	}  
										break;
									case 7:
										
										// 店内就餐
					                	if(repastForm=='1'){	                	
					                		if(orderStatus=='1'||orderStatus=='-3'){
									 			str = "未确认";
									   		} else if(orderStatus=='2' || orderStatus=='9') {
									   			str = "待就餐";
									   		} else if(orderStatus=='6') {
									   			str = "就餐中";
									   		} else if(orderStatus=='7') {
									   			str = "待结账";
									   		} else if(orderStatus=='4') {
									   			str = "交易完成";
									   		} else if(orderStatus=='10') {
									   			str = "取消未确认";					
											} else if(orderStatus=='99'||orderStatus=='-1'){
												str = "已取消";
									   		} 					   		       	   		       
					                	}
					                	
					                	// 外卖货到付款，没有在线支付(不显示 -4 0 1 3的状态)
					                	if(repastForm=='2'){  		                	
						                	if(orderStatus=='8'){
						                		str = "未确认";
									   		} else if(orderStatus=='2' ) {
									   			str = "待送餐";
									   		} else if(orderStatus=='11') {
									   			str = "送餐中";			   				   		
									   		} else if(orderStatus=='4') {
									   			str = "交易完成";
									   		} else if(orderStatus=='10') {
									   			str = "取消未确认";
											} else if(orderStatus=='99'||orderStatus=='-1'){
												str = "已取消";						
									   		}  
					                	 } 
					                
					                	 // 自提：只有有货到付款		
					                	 if(repastForm=='3'){	            															   		  		
						                	if(orderStatus=='8'){
						                		str = "未确认";
									   		} else if(orderStatus=='2' ) {
									   			str = "待自提";				   	
									   		} else if(orderStatus=='4') {
									   			str = "交易完成";				   	
									   		} else if(orderStatus=='10') {
									   			str = "取消未确认";											   		
								   		    } else if(orderStatus=='99'||orderStatus=='-1'){
								   		    	str = "已取消";								
									   		}     
					                	 } 	
										break;
									case 8:
										var orderOprate = "";
					                	//店内就餐 
					                	if(repastForm=='1'){
						                	if(orderStatus=='1'||orderStatus=='-3'){
						                		orderOprate = "确认接单";
									   		} else if(orderStatus=='2' || orderStatus=='9') {
									   			orderOprate = "开台";
									   		} else if(orderStatus=='6'|| orderStatus=='7') {
									   			orderOprate = "修改";					   	
									   		} else if(orderStatus=='10') {
									   			orderOprate = "确认取消订单";											   	
									   		}  				   		       
					                	}
					                	
					                	//外卖
					                	if(repastForm=='2'){	                	
						                	if(orderStatus=='8') {
						                		orderOprate = "确认接单";
									   		} else if(orderStatus=='2') {
									   			orderOprate = "送餐";
									   		} else if(orderStatus=='11') {
									   			orderOprate = "现金收款 ";
									   		} else if(orderStatus=='10') {
									   			orderOprate = "确认取消订单";					  	
									   		}  	       
					                	} 
					                	
					                	// 自提		
					                	if(repastForm=='3'){
					                		 if(orderStatus=='-4') {
					                			 orderOprate = "未支付";
					                		} else if(orderStatus=='0') {
					                			orderOprate = "付款中";	
					                		} else if(orderStatus=='8') {
					                			orderOprate = "确认接单";
									   		} else if(orderStatus=='2') {
									   			orderOprate = "现金收款";
									   		 } else if(orderStatus=='10') {
									   			orderOprate = "确认取消订单";					  	
									   		}  	       
					                	} 
					                	if(repastForm=='2'){
					                		if(orderStatus=='1'||orderStatus=='8'){
					                			str += "<a data-id="+rowIndex+" class='orderReject mr10'>拒接</a>";
					                		}
					                	}
					                	str += "<a data-id="+rowIndex+" class='orderOprate mr10'>"+orderOprate+"</a>";
										if(repastForm=='1'){
						                	if(orderStatus=='2'||orderStatus=='9'){
						                		str = "<a data-id="+rowIndex+" class='orderOprate mr10'>开台</a>";
									 			str += " <a data-id="+rowIndex+" class='confirmCancelInOrder mr10'>取消预订</a>";	   	
									   		}			   		       
										}
										str += " <a data-id="+rowIndex+" class='xqdd mr10'>详情</a>";
										break;
									default:
										break;
								}
								return str;
							}
						},
						complete : function(e,o){
							var other = null;
							if (o && o.responseText){
								other = eval("("+o.responseText+")");
							}
							//var other = eval("("+o.responseText+")");
							var shopType = 0;
							if(other != null){
								shopType = other.shopType;
							}
							if(shopType == 1){
								$("#shopType").hide();
							}	
							shop.bindClickQuery();
							//订单详情加载
							$(".xqdd").click(function(){
								var obj = gridObj.getRecord($(this).attr("data-id"));
								var query={};
									query.orderId=obj.orderId;
									query.userId=obj.userId;
									query.repastForm=obj.repastForm;
								shop.queryOrderDetailNew(query);	
							});
							//商家取消订单
							$(".confirmCancelInOrder").click(function(){
								var obj = gridObj.getRecord($(this).attr("data-id"));
								var orderId=obj.orderId;
								var userId=obj.userId;					
								var cancelReason=$("#dlg3 input[name='cancelReason']").val("");
								var refundType=$("#dlg3 input[name='refundType']:checked").attr("checked",true);
								var moneyEarnest=obj.moneyEarnest;
								if(comm.isEmpty(moneyEarnest)||moneyEarnest=='0'){
									//预付金为空 则没有预付金
									comm.i_confirm("该订单没有预约金，确认取消订单?", function() {
										var param={orderId:orderId,userId:userId,cancelReason:'',refundType:'2',moneyEarnestRefund:'0'};				
										ajaxRequest.confirmCancelInOrder(param, function(response) {
					  			    		if (response.retCode == "200") {			  			    		
												tablePoint.tablePoint("取消预定成功！");								
												shop.loadData(queryParam);		
											}else{
												tablePoint.tablePoint("取消预定失败,请重试！");
											}
					  			    	});
								    });	
									
								}else{
									$("#dlg3 .moneyEarnest").text(comm.formatMoneyNumber(moneyEarnest));					
									  $( "#dlg3" ).dialog( {
										  show: true,
										  modal: true,
										  title:"取消预约",
										  width:"400",
										  buttons: {
											确定: function() {
												var cancelReason=$("#dlg3 input[name='cancelReason']").val();
												var refundType=$("#dlg3 input[name='refundType']:checked").val();										
												if(comm.isEmpty(refundType)){
													tablePoint.tablePoint("请选择退款类型！");	
													return false;
												}
												if(refundType=='2') moneyEarnest=0;
												var param={orderId:orderId,userId:userId,cancelReason:cancelReason,refundType:refundType,moneyEarnestRefund:moneyEarnest};				
												ajaxRequest.confirmCancelInOrder(param, function(response) {
							  			    		if (response.retCode == "200") {			  			    		
														tablePoint.tablePoint("取消预定成功！");
														$("#dlg3").dialog( "close" );
														shop.loadData(queryParam);		
													}else{
														tablePoint.tablePoint("取消预定失败,请重试！");
													}
							  			    	});
												
											},
											取消: function() {$("#dlg3").dialog( "close" );}		
											}
										  });  	
								}
																	
							});
							//订单操作
							$(".orderOprate").click(function(){
								var obj = gridObj.getRecord($(this).attr("data-id"));
								var orderId=obj.orderId;
								var userId=obj.userId;	
								var orderStatus=obj.orderStatus;
								var repastForm=obj.repastForm;
								var shopId =obj.shopId;
								var pCount=obj.personCount;
								
								var text=$(this).text();	
								//确认接单
								if((repastForm=='1'&&(orderStatus=='1'||orderStatus=='-3'))
										||(repastForm=='2'&&orderStatus=='8')
											||(repastForm=='3'&&orderStatus=='8')){
									comm.i_confirm(text+"?", function() {
										var param={orderIds:orderId,userIds:userId};
					  			    	ajaxRequest.acceptOrder(param, function(response) {
					  			    		if (response.retCode == "200") {			  			    		
												tablePoint.tablePoint("操作成功！");	
												shop.loadData(queryParam);		
											}else{
												tablePoint.tablePoint("操作失败!");
											}
					  			    	});
								    });		
								}
								
								//修改
								if(repastForm=='1'&&(orderStatus=='6'||orderStatus=='7')){										
									var query={};
									query.orderId=orderId;
									query.userId=userId;
									query.repastForm=repastForm;										
									shop.editOrder(query);																		
								}
														
								
								//企业确认店内消费者就餐						
								if(repastForm=='1'&&(orderStatus=='2'||orderStatus=='9')){
									var personCount=$(this).parent().attr("personCount");
									var tableNo=$("#dlg12 input[name='tableNo']").val("");
									var tableNumber=$("#dlg12 input[name='tableNumber']").val(personCount);
									$('input[name="tableNumber"]').val(pCount);
									$( "#dlg12" ).dialog( {
										  show: true,
										  modal: true,
										  title:"开台确认",
										  width:"400",
										  buttons: {
											确定: function() {										
													var tableNumber=$("#dlg12 input[name='tableNumber']").val();
													var tableNo=$("#dlg12 input[name='tableNo']").val();
											var c = /^[0-9]*[0-9][0-9]*$/;//验证整数    || Number(tableNumber) > 50 不限制最大值  modifly by zhanghh 20160520 :Bug:24793
											if(comm.isEmpty(tableNumber)||comm.isEmpty(tableNo) || !c.test(tableNumber) || Number(tableNumber) < 1){
												tablePoint.tablePoint("桌号和开台人数不能为空.");	//,1~50人范围
												return false;
											}
													var param={orderId:orderId,userId:userId,tableNumber:tableNumber,tableNo:tableNo};				
													ajaxRequest.customerTakingMeals(param, function(response) {
								  			    		if (response.retCode == "200") {			  			    		
															tablePoint.tablePoint("开台成功！");
															$("#dlg12").dialog( "close" );
															shop.loadData(queryParam);		
														}else{
															tablePoint.tablePoint("开台失败");
															console.info(response.msg);
														}
								  			    	});
												},
											取消: function() {
												$( this ).dialog( "close" );
												}		
											}
										  });  						
								}
								
								//企业确认自提订单						
								if(repastForm=='3'&&orderStatus=='2'){
									comm.i_confirm(text+"?", function() {
										var param={orderId:orderId,userId:userId};
					  			    	ajaxRequest.takeOwn(param, function(response) {
					  			    		if (response.retCode == "200") {			  			    		
												tablePoint.tablePoint("操作成功！");	
												shop.loadData(queryParam);		
					  			    		}else if(response.retCode=="590"){
												 tablePoint.tablePoint("现金结账失败,企业预付积分不足");
											}else{
											   tablePoint.tablePoint("操作失败!");
											}
					  			    	});
								    });		
								}
												
								//送餐配送
								if(repastForm=='2'&&orderStatus=='2'){														
						    		    
						    		    var param = {}
						    		    //实体店Id
						    		    param["shopId"]=shopId;
						    		    param["userId"]=userId;
						    		    param["orderId"]=orderId;			    		    
						    		    ajaxRequest.listDeliver(param,function(response){
						    		    	var html = _.template(sendTpl, response);
											$('#dlg2').html(html);
											//绑定选择送货人事件
//						    		    	$("#deliverManSel").change(function(){
//						    		    		$("#deliverContact").val($("#deliverManSel option:selected").attr("phone"));
//						    		    	});
											$("#dlg2").dialog({
												title : "送货员选择",
												width : "450",
												modal : true,
												close: function() { 
											        $(this).dialog('destroy');
												},
												buttons : {
													'确定' : function() {
														 var deliveryId=$("#deliverManSel option:selected").val();
													     var deliverName=$("#deliverManSel option:selected").text();
											     var deliverNode= $.trim($("#deliverNode").val());
											     var deliverContact=$("#deliverManSel option:selected").attr("phone");												     
											     if(comm.isEmpty(deliveryId) || deliveryId == "请选择"){
													   tablePoint.tablePoint("请先选择送货员！");
													   return false;
												   }else if(deliverNode.length > 200){
													   tablePoint.tablePoint("备注内容过长,请不要超过200长度,现在长度"+deliverNode.length+"！");
													   return false;
												   }else{
															    var sendParam = {}
												    		    //实体店Id
												    		    //sendParam["shopId"]=shopId;
												    		    sendParam["userId"]=userId;
												    		    sendParam["orderId"]=orderId;	
															    sendParam["deliveryId"]=deliveryId;
												    		    sendParam["deliverName"]=deliverName;
												    		    sendParam["deliverContact"]=deliverContact;	
												    		    sendParam["deliverNode"]=deliverNode;
															    ajaxRequest.confirmDeliveryFood(sendParam,function(response){
																	if (response.retCode == "200") {			  			    		
																		tablePoint.tablePoint("送餐成功！");
																		$("#dlg2").dialog("destroy");
																		shop.loadData(queryParam);		
																	}else{
																		tablePoint.tablePoint("操作失败,请刷新页面重试!");
																	}
															   });											   													 
														   }
													},
													'关闭' : function() {
														$(this).dialog("destroy");
													}
												}
											});
					    		    });				    		    				    		    							
								}
								
								//企业确认外卖付款，交易完成						
								if(repastForm=='2'&&orderStatus=='11'){
									comm.i_confirm(text+"?", function() {
										var param={orderId:orderId,userId:userId};
					  			    	ajaxRequest.confirmWMorder(param, function(response) {
					  			    if (response.retCode == "200") {			  			    		
										tablePoint.tablePoint("操作成功！");	
										shop.loadData(queryParam);		
									}else if(response.retCode=="590"){
										   tablePoint.tablePoint("现金结账失败,企业预付积分不足");
									}else{
										   tablePoint.tablePoint("操作失败!");
										}
					  			   });
								  });		
								}
														
								//企业确认消费者取消订单						
								if(orderStatus=='10'){
									comm.i_confirm(text+"?", function() {
										var param={orderId:orderId,userId:userId};
					  			    	ajaxRequest.confirmCancelOrder(param, function(response) {
					  			    		if (response.retCode == "200") {			  			    		
												tablePoint.tablePoint("操作成功！");	
												shop.loadData(queryParam);		
											}else{
												tablePoint.tablePoint("操作失败!");
											}
					  			    	});
								    });		
								}
							});	
							//拒接
							$(".orderReject").click(function(){
								var obj = gridObj.getRecord($(this).attr("data-id"));
								var orderId=obj.orderId;
								var userId=obj.userId;					
								//拒接订单
									comm.i_confirm("拒接此订单码?", function() {
										var param={orderId:orderId,userId:userId};				
										ajaxRequest.sallerRefuseOrder(param, function(response) {
					  			    		if (response.retCode == "200") {			  			    		
												tablePoint.tablePoint("拒接订单成功！");								
												shop.loadData(queryParam);		
											}else{
												tablePoint.tablePoint("拒接订单失败,请重试！");
											}
					  			    	});
								    });	
							});
							 //批量送餐
						    $(".batchSend").unbind().click(function(){			   
					    		var allOrders=$("input[name='batcheSelect']:checked");
					    		var orderIds="";
					    		var userIds="";
								//过滤选择
					    		var flag=true;
								$.each(allOrders,function(key,order){
									//判断是否是外卖订单
									if($(order).parents("tr").attr("repastForm")!=2){
										tablePoint.tablePoint("订单"+$(order).parents("tr").attr("orderId")+"为非外卖订单，请重新选择");
										flag= false;
										return flag ;
									}
									orderIds+=$(order).parents("tr").attr("orderId")+",";
									userIds+=$(order).parents("tr").attr("userId")+",";		
									
								});
								if(flag){
									if(comm.isEmpty(orderIds)||comm.isEmpty(userIds)){
										tablePoint.tablePoint("请选择订单!");
										return false;
									}
									var param = {shopId:""}		    		 
					    		    ajaxRequest.listDeliver(param,function(response){
					    		    	var html = _.template(sendTpl, response);
										$('#dlg2').html(html);								   
					    		    });	
								}					   
						    });
						    
						    $(".batchCancel").unbind().click(function(){
						    	tablePoint.tablePoint("暂不支持批量操作！");
						    });
						}
		            });
				});	
		        
		        /** add by zhucy 2016-03-30 分页栏操作 begin  未确认订单  **/
		        var index = shop.getsortIndex();
		        if(index == 1){
		        	//自定义分页栏按钮
					var buttonHtml = '<td style="text-align: left;">';
			        buttonHtml += '<table id="cy_order_page"><tr>';
			        buttonHtml += '<td><div class="splb_sclb"><input name="chkAll" id="chkAll" value="" type="checkbox"><a>全选</a></div></td>';
			        buttonHtml += '<td><div id="batchConfirm" class="splb_sclb" style="width:80px;"><a style="display: block;" href="javascript:;">批量确认</a></div></td>';
			        buttonHtml += '</tr></table>';
			        buttonHtml += '</td>';
			        $('#' + gridObj.options.pagingOutTabId + ' tr:eq(0)').prepend(buttonHtml);
			        
			        $('#cy_order_page').find('#chkAll').off('click').on('click',function(){
			        	var ck = $(this).prop('checked');
			        	$("input[name='batcheSelect']").each(function(){
			        		$(this).prop('checked',ck);
			        	});
			        }).end().find('#batchConfirm').off('click').on('click',function(){//批量确认
			        	var s = $("input[name='batcheSelect']:checked");
			        	if(s.length > 0){
			        		var a = [],b = [];
			        		$.each(s,function(){
			        			var _this = $(this);
			        			a.push(_this.attr('orderId'));
			        			b.push(_this.attr('userId'));
			        		});
			        		a = a.join(',');
			        		b = b.join(',');
				        	comm.i_confirm("确定批量接单?", function() {
			  			    	ajaxRequest.acceptOrder({orderIds:a,userIds:b}, function(r) {
			  			    		if (r.retCode == "200") {			  			    		
										tablePoint.tablePoint("操作成功！");	
										shop.loadData(queryParam);
									}else{
										tablePoint.tablePoint("操作失败!");
									}
			  			    	});
						    });
			        	}else{
			        		tablePoint.tablePoint("请选择订单!");
							return false;
			        	}
			        });
		        }
		        /** add by zhucy 2016-03-30 分页栏操作 end 未确认订单   **/
			},
			//查询订单详情
			queryOrderDetailNew : function (query){
				//创建对象						
				ajaxRequest.queryOrderDetail(query,function(response) {
				   if(query.repastForm=='1'){	
						var html = _.template(inOrder, parseLineLog(query.repastForm,response));
					}
					if(query.repastForm=='2'){
						var html = _.template(sendOrder, parseLineLog(query.repastForm,response));
					}
					if(query.repastForm=='3'){
						var html = _.template(takeOrder, parseLineLog(query.repastForm,response));
					}					
					$(".searchbox").addClass("none").hide();
					$("#orderDetail").html(html).removeClass("none").show();
					
					$(".btn-xq").click(function(){
						$(".searchbox").removeClass("none").show();
						$("#orderDetail").addClass("none").hide();	
					});
				});		
			},
			//修改订单
			editOrder : function (query){
				ajaxRequest.queryOrderDetail(query,function(orderDetailResponse) {																
					var html = _.template(modifyOrder, parseLineLog(query.repastForm,orderDetailResponse));
					var maxPrintCount=orderDetailResponse.data.paramSendOrderCount;	//最大打单次数					
					$("#orderEdit").html(html);
					$("#orderEdit").removeClass("none").show();
					//$("#orderEdit").html(html).removeClass("none").show();
					$(".searchbox").addClass("none");									
					var editResult=orderDetailResponse.data.QueryResult;
					//加载已经打单的结账信息
					if(editResult.orderStatus=='7'){						
						//折扣信息设置单选框选中
						$("input[name='discount'][value='"+editResult.discountType+"']").attr("checked","true");
						var actualPayMoney=myParseFloat(editResult.amountTotal)+myParseFloat(editResult.amountOther);
												
						if(editResult.discountType=='0'){
							
						}else if(editResult.discountType=='1'){
							//折扣券
							var couponInfoJson=eval("("+editResult.couponInfo+")");     						
							var amountCoupon= $("input[name='amountCoupon']").val(couponInfoJson.num);
							var couponInfo= $("input[name='couponInfo']").val(couponInfoJson.faceValue);
							actualPayMoney=myParseFloat(actualPayMoney)-myParseFloat(couponInfoJson.num)*myParseFloat(couponInfoJson.faceValue);
							$(".discountType1").removeClass("none").show();	
						}else if(editResult.discountType=='2'){
							//折扣率
							 $("input[name='discountRate']").val( (editResult.discountRate*100).toFixed(0) );							 							
							 var discountRateMoney=parseFloat(editResult.amountCoupon);
							 $("#zehouPrice").html(myToFixed(editResult.amountTotal - myToFixed(editResult.amountCoupon)));
							 $("input[name='discountRateMoney']").val(myToFixed(editResult.amountCoupon));							
							 actualPayMoney=actualPayMoney-myParseFloat(discountRateMoney);		
							 $(".discountType2").removeClass("none").show();	
						}						
						//定金支付信息
						$("input[name='checkOutType'][value='"+editResult.checkOutType+"']").attr("checked","true");	
						if(editResult.checkOutType=='2'){
							$(".reBackMoneyEarnest").text(editResult.moneyEarnest!=null?comm.formatMoneyNumber(editResult.moneyEarnest.toFixed(2)):0);								
							$(".checkOutType").removeClass("none").show();	
						}else{
							actualPayMoney=actualPayMoney-myParseFloat(editResult.moneyEarnest);	
							$(".checkOutType").addClass("none").hide();
						}
						$(".actualPay,.actualPayText").text(myToFixed(actualPayMoney));	
						if(editResult.checkOutType=='1'&&myParseFloat(actualPayMoney)<0){	
							$(".checkOutType").removeClass("none").show();
							$(".reBackMoneyEarnest").text((-myToFixed(actualPayMoney.toFixed(2)))+"元");								
							$(".actualPayText").text("0");	
						}
					}else{//加载已经打单的结账信息else 
						var showActualMoney=editResult.amountTotal-editResult.moneyEarnest;
						//判断金额是否小于0
						if((editResult.checkOutType==null||editResult.checkOutType=='1')&&myParseFloat(showActualMoney)<0){	
							$(".checkOutType").removeClass("none").show();
							$(".reBackMoneyEarnest").text((-showActualMoney.toFixed(2))+"元");								
							$(".actualPayText").text("0");	
						}
					}
					
					//改页面显示金额			
					
					//js动态计算实付金额 js联动
					//单选框选择
					$("input[name='discount']").click(function(){
						var clickValue=$(this).val();	
						$.each($("input[name='discount']"),function(key,option){
							if($(option).val()!=clickValue){
								$(option).removeAttr("checked");
							}								
						});		
									
						var discount=$("input[name='discount']:checked").val()
						$(".discountType").addClass("none").hide();
						//折扣方式
						if(typeof(discount) == "undefined"){
						
						}else{
							var actualPay=$(".actualPayText").text();
							if(actualPay=='0'){
								tablePoint.tablePoint("该订单付款金额为0,不能再使用折扣！");								
								$("input[name='discount'][value='"+clickValue+"']").click()
			            		return false;
							}
							
							if(discount=='1'){								
								$(".discountType1").removeClass("none").show();	
							}else if(discount=='2'){							
								$(".discountType2").removeClass("none").show();	
							}
						}
						countActualMoney();
					});
					
					$("input[name='checkOutType']").click(function(){	
						
						var checkOutType=$("input[name='checkOutType']:checked").val();
						//定金退还
						if(checkOutType=='2'){
							$("input[name='checkOutType']").eq(0).attr('checked',false);//选中
							$("input[name='checkOutType']").eq(1).attr('checked',true);//选中
							$(".checkOutType").removeClass("none").show();						
							$(".reBackMoneyEarnest").text(comm.formatMoneyNumber(editResult.moneyEarnest.toFixed(2)));
						}else{
							$("input[name='checkOutType']").eq(0).attr('checked',true);//选中
							$("input[name='checkOutType']").eq(1).attr('checked',false);//选中
							$(".checkOutType").addClass("none").hide();
						}
						countActualMoney();
					});
					/*function isPirce(s){
					    s = trim(s);
					    var p =/^[1-9](\d+(\.\d{1,2})?)?$/; 
					    var p1=/^[0-9](\.\d{1,2})?$/;
					    return p.test(s) || p1.test(s);
					}*/
					//input 输入
					$("input[name='amountOther']").blur(function(){
						//$("input[name='discountRate']").val("");
						//$("input[name='discountRateMoney']").val("");
						
						if( checkInputNumber($("input[name='amountOther']").val()) ){
							$("input[name='amountOther']").val(myToFixed(($("input[name='amountOther']").val())));//服务费金额保留小数两位并四舍五入。
							countActualMoney();
						}else{						
							tablePoint.tablePoint("金额必须为数字且大于0!",function(){
								$("input[name='amountOther']").val('');
								countActualMoney();
							});
							
						}
													
					});
					$("input[name='amountCoupon']").blur(function(){
						var amountCoupon=$("input[name='amountCoupon']").val();		
						if( checkInputNumber(amountCoupon)&&checkInputInt(amountCoupon) ){							
							countActualMoney();
						}else{						
							tablePoint.tablePoint("折扣券数量必须为整数!",function(){
								$("input[name='amountCoupon']").val('');
							});
							
						}
								
					});
					
					//input 输入
					$("input[name='discountRate']").blur(function(){
						var discountRate= $("input[name='discountRate']").val();
						if(comm.isEmpty(discountRate)) return false;
						//整数和范围校验
						if(checkInputNumber(discountRate)&&
								checkInputInt(discountRate)&&
								(50<=myParseFloat(discountRate)&&myParseFloat(discountRate)<=100) ){
							var amountTotal=$("input[name='amountTotal']").val();	//订单总金额	
							//var amountOther=$("input[name='amountOther']").val();
							var totalMoneyConsume=myParseFloat(amountTotal);												
							var actualPayMoney=myToFixed(totalMoneyConsume*(myParseFloat(discountRate)/100));
							$("#zehouPrice").html(actualPayMoney);
							$("input[name='discountRateMoney']").val(myToFixed(totalMoneyConsume-actualPayMoney));	
							countActualMoney();
						}else{
							tablePoint.tablePoint("折扣率必须为整数且在50%~100%之间!",function(){
								$("input[name='discountRate']").val('');
							});					
						}
						
					});	
					
					$("input[name='discountRateMoney']").blur(function(){						
						var amountTotal=$("input[name='amountTotal']").val();	//订单总金额	
						//var amountOther=$("input[name='amountOther']").val();
						var totalMoneyConsume=myParseFloat(amountTotal);
						var discountRateMoney= $("input[name='discountRateMoney']").val();	
						if(comm.isEmpty(discountRateMoney)) return false;
						if(checkInputNumber(discountRateMoney)){
							var actualPayMoney=totalMoneyConsume-myParseFloat(discountRateMoney);
							var rate=Math.round((actualPayMoney/totalMoneyConsume)*100);
							$("input[name='discountRate']").val(rate);	
							//$("input[name='discountRate']").blur();
							countActualMoney();
						}else{
							tablePoint.tablePoint("折扣金额必须为数字且大于0!",function(){
								 $("input[name='discountRateMoney']").val('');		
							});
							
						}
						
					});
					//计算实付金额 (金额，折扣校验)
					function countActualMoney(){
						var amountTotal=myParseFloat($("input[name='amountTotal']").val());	//订单总金额
						var moneyEarnest=myParseFloat($("input[name='moneyEarnest']").val());	//已付金额
						var discount=$("input[name='discount']:checked").val();
						var checkOutType=$("input[name='checkOutType']:checked").val();
						var amountOther=myParseFloat($("input[name='amountOther']").val());
						var amountCoupon=myParseFloat($("input[name='amountCoupon']").val());
						var couponInfo= myParseFloat($("input[name='couponInfo']").val());	//折扣券的面值 现在页面统一是10元
						var discountRate= myParseFloat($("input[name='discountRate']").val());
						var discountRateMoney= myParseFloat($("input[name='discountRateMoney']").val());						
						var actualPayMoney=0.00;
						//消费总金额
						var totalMoneyConsume=amountTotal+amountOther;
						$(".totalMoneyConsume").text( myToFixed(totalMoneyConsume));
						//折扣方式
						if(typeof(discount) == "undefined"){
							actualPayMoney=totalMoneyConsume;//-myParseFloat(moneyEarnest);
						}else if(discount=='1'){
							//互生消费抵扣券
							var couponPercent=0.5;//折扣券使用金额百分比
							if(amountCoupon*couponInfo>totalMoneyConsume*0.5){
								tablePoint.tablePoint("折扣券使用不能超过订单金额的50%!");
								$(".actualPay,.actualPayText").text("");
								return false;
							}
							actualPayMoney=totalMoneyConsume-amountCoupon*couponInfo;
						}else if(discount=='2'){
							//店内折扣
							actualPayMoney=totalMoneyConsume-discountRateMoney;						
						}
						
						if(checkOutType=='1'){
							//定金支付
							actualPayMoney=actualPayMoney-moneyEarnest;
						}
//						if(typeof(discount)!= "undefined"&&actualPayMoney<0){				
//							tablePoint.tablePoint("折扣后该订单付款金额小于0,请修改折扣率或抵扣劵数量！");
//							$(".actualPay,.actualPayText").text("");
//							//$("input[name='discount'][value='"+discount+"']").click()
//		            		return false;
//						}
						//实际支付费用
						actualPayMoney=myToFixed(actualPayMoney);
						$(".actualPay").text(actualPayMoney);	//update by zhanghh						
						//定金退还 判断金额是否小于0
						if(checkOutType=='1'&&myParseFloat(actualPayMoney)<=0){	
							$(".checkOutType").removeClass("none").show();
							$(".reBackMoneyEarnest").text((-(myToFixed(actualPayMoney)))+'元');	//update by zhanghh							
							$(".actualPayText").text("0");	
						}else{//改页面显示金额
							if(checkOutType=='1'){
								$(".reBackMoneyEarnest").text(0);
							}
							$(".actualPayText").text(actualPayMoney); //update by zhanghh		
						}
						
					}				
					//打预结单
					$(".orderSendToUser").click(function(){					
						var sendOrderCount=$("input[name='sendOrderCount']").val();	//打单次数
						if(comm.isEmpty(sendOrderCount))sendOrderCount=0;
						if(parseInt(sendOrderCount)>=maxPrintCount){
							tablePoint.tablePoint("该订单已经超过打单的最大的次数!");
		            		return false;
						}
						comm.i_confirm("确认打预结单?", function() {																	
							var orderId=query.orderId;
							var userId=query.userId;																			
							var moneyEarnest=$("input[name='moneyEarnest']").val();	//已付金额
							var discount=$("input[name='discount']:checked").val();
							var checkOutType=$("input[name='checkOutType']:checked").val();
							var amountOther=$("input[name='amountOther']").val();
							var amountOtherMsg=$("input[name='amountOtherMsg']").val();
							var amountCoupon= $("input[name='amountCoupon']").val();
							var couponInfo= $("input[name='couponInfo']").val();	//折扣券的面值 现在页面统一是10元
							var discountRate= $("input[name='discountRate']").val();
							var discountRateMoney= $("input[name='discountRateMoney']").val();
							var amountActually=$(".actualPay").text();
							if(comm.isEmpty(amountActually)){
								tablePoint.tablePoint("付款金额错误，请从新填写订单折扣信息!");
			            		return false;
							}
							if(typeof(discount) == "undefined"){
								discount='0';																						
							}else{
								if($("input[name='discount']:checked").attr("value") == 1){
									if(amountCoupon == null || amountCoupon == "" || Number(amountCoupon) < 0){
										tablePoint.tablePoint("抵扣劵使用数量不能为空!");
					            		return false;
									}	
								}
							}
							if(discount=='2'){
								if(comm.isEmpty(discountRate)||comm.isEmpty(discountRateMoney)){
									tablePoint.tablePoint("请输入折扣率");
									return false;
								}								
								if(!(50<=myParseFloat(discountRate)&&myParseFloat(discountRate)<=100)){
									tablePoint.tablePoint("打单失败，折扣率在必须在50%~100%之间");
									return false;
								}
								amountCoupon=discountRateMoney;
							}														
							var payParam={};							
							payParam.userId=userId;
							payParam.orderId=orderId;
							payParam.discountType=discount;
							payParam.discountRate=discountRate/100;
							payParam.discountRateMoney=discountRateMoney;
							payParam.amountCoupon=amountCoupon;
							payParam.couponInfo=couponInfo;
							payParam.amountOther=amountOther;
							payParam.amountOtherMsg=amountOtherMsg;
							payParam.checkOutType=checkOutType;
							payParam.amountActually=amountActually;
							
							if(checkOutType=='2'){
								payParam.moneyEarnestRefund=moneyEarnest;
							}else{
								//判断支付金额是否小于0，
								if(amountActually<=0){								
									payParam.moneyEarnestRefund=-amountActually;		
									payParam.amountActually='0';
								}else{
									payParam.moneyEarnestRefund='0';
								}							
							}																											
							tablePoint.bindJiazai($(".operationsInner"),true);	
							ajaxRequest.sendOrderToBuyer(payParam,function(response) {
								tablePoint.bindJiazai($(".operationsInner"),false);
								if(response.retCode=="200"){
									var cunrentPrintCount=parseInt(sendOrderCount)+1;
									
									if(cunrentPrintCount==1){
										tablePoint.tablePoint("第"+cunrentPrintCount+"次打单成功，还能打印"+(maxPrintCount-cunrentPrintCount)+"次");
									}else{
										if(cunrentPrintCount==maxPrintCount){
											tablePoint.tablePoint("第"+cunrentPrintCount+"次打单成功，剩余可打单次数0次",function(){},'重复打印预结单');
										}else{
											tablePoint.tablePoint("第"+cunrentPrintCount+"次打单成功，还能打印"+(maxPrintCount-cunrentPrintCount)+"次",function(){},'重复打印预结单');
										}
									}
									shop.editOrder(query);	
								}else if(response.retCode=="201"){
									tablePoint.tablePoint("打预结单错误");
								}
							});
					    });
					});
					
					//店内消费现金结账
					$(".confirmPayInOrder").unbind().on('click',function(){
						var orderStatus=$(this).parent().attr("orderStatus");						
						if(orderStatus=='7'){
							var orderId=query.orderId;
							var userId=query.userId;																			
							var moneyEarnest=$("input[name='moneyEarnest']").val();	//已付金额
							var discount=$("input[name='discount']:checked").val();
							var checkOutType=$("input[name='checkOutType']:checked").val();
							var amountOther=$("input[name='amountOther']").val();
							var amountOtherMsg=$("input[name='amountOtherMsg']").val();
							var amountCoupon= $("input[name='amountCoupon']").val();
							var couponInfo= $("input[name='couponInfo']").val();	//折扣券的面值 现在页面统一是10元
							var discountRate= $("input[name='discountRate']").val();
							var discountRateMoney= $("input[name='discountRateMoney']").val();
							var amountActually=$(".actualPay").text();
							if(comm.isEmpty(amountActually)){
								tablePoint.tablePoint("付款金额错误，请从新填写订单折扣信息!");
			            		return false;
							}
							
							if(typeof(discount) == "undefined"){
								discount='0';																						
							}else{
								if($("input[name='discount']:checked").attr("value") == 1){
									if(amountCoupon == null || amountCoupon == "" || Number(amountCoupon) < 0){
										tablePoint.tablePoint("抵扣劵使用数量不能为空!");
					            		return false;
									}	
								}
							
							}
							if(discount=='2'){
								if(comm.isEmpty(discountRate)||comm.isEmpty(discountRateMoney)){
									tablePoint.tablePoint("请输入折扣率");
									return false;
								}								
								if(!(50<=myParseFloat(discountRate)&&myParseFloat(discountRate)<=100)){
									tablePoint.tablePoint("结账失败，折扣率在必须在50%~100%之间");
									return false;
								}
								amountCoupon=discountRateMoney;
							}														
							var payParam={};							
							payParam.userId=userId;
							payParam.orderId=orderId;
							payParam.discountType=discount;
							payParam.discountRate=discountRate/100;
							payParam.discountRateMoney=discountRateMoney;
							payParam.amountCoupon=amountCoupon;
							payParam.couponInfo=couponInfo;
							payParam.amountOther=amountOther;
							payParam.amountOtherMsg=amountOtherMsg;
							payParam.checkOutType=checkOutType;
							payParam.amountActually=amountActually;											
							if(checkOutType=='2'){
								//定金退还
								payParam.moneyEarnestRefund=moneyEarnest;
							}else{
								//定金支付，判断支付金额是否小于0，
								if(amountActually<=0){								
									payParam.moneyEarnestRefund=-amountActually;		
									payParam.amountActually='0';
								}else{
									payParam.moneyEarnestRefund='0';
								}							
							}

							tablePoint.bindJiazai($(".operationsInner"),true);
							ajaxRequest.confirmPayInOrder(payParam,function(response) {
								tablePoint.bindJiazai($(".operationsInner"),false);
								if(response.retCode=="200"){
									tablePoint.tablePoint("结账成功",function(){
										$(".editClose").click();
									});
									//shop.editOrder(query);										
								}else if(response.retCode=="590"){
									tablePoint.tablePoint("现金结账失败,企业预付积分不足");
								}else if(response.retCode=="554"){
									tablePoint.tablePoint("预付定金不足支付，确定以现金支付",function(){
										$("input[name='checkOutType']").eq(0).attr('checked',false);//选中
										$("input[name='checkOutType']").eq(1).attr('checked',true);//选中
										$(".checkOutType").removeClass('none').css('display',"");//去除样式	
										countActualMoney();//调用结算
										$(".orderSendToUser").trigger('click');
									});//暂不支持现金尾款支付
								}else{
									tablePoint.tablePoint("结账失败");
								}
							});	
						}else{
							tablePoint.tablePoint("只有已打单的订单才能结账!");
						}	
					});
						
					//验证 no-sub,add_sub点击事件
					function checkNoAddSubClick(clickObj){
						//是否是删除菜品验证
						var statu=$(clickObj).parent().attr("orderDetailStatu");
						if(statu!='0') return false;		
						//打单次数验证
						var sendOrderCount=$("input[name='sendOrderCount']").val();	
						if(comm.isEmpty(sendOrderCount))sendOrderCount=0;
						if(parseInt(sendOrderCount)>=maxPrintCount){	
							tablePoint.tablePoint("该订单已超过最大打单次数,不能修改菜品！");
		            		return false;
						}
						return true;
					}
					//订单详情金额、积分和数量动态改变---开始
					$(".no-sub,.add_sub").click(function(){					
						if(checkNoAddSubClick(this)){
							var orderDetailId=$(this).parent().attr("orderDetailId");
							var quantity=$(this).parent().find("input[name='quantity']").val();
							var changeMoney=parseFloat($(this).parent().attr("price"));
							var changePoint=parseFloat($(this).parent().attr("point"));	
							if(parseInt(quantity) <= parseInt(maxAddCount)){
								if($(this).hasClass("no-sub")){
									if(parseInt(quantity)<=1){
										tablePoint.tablePoint("数量不能为0！");
									}else{
										updateDetail(orderDetailId,parseInt(quantity)-1,changeMoney,changePoint,$(this).parent());
									}	
								}else{
									if(parseInt(quantity) < parseInt(maxAddCount)){
										updateDetail(orderDetailId,parseInt(quantity)+1,changeMoney,changePoint,$(this).parent());
									}else{
										updateDetail(orderDetailId,parseInt(maxAddCount),changeMoney,changePoint,$(this).parent());
									}
								}
							}else{
								updateDetail(orderDetailId,parseInt(maxAddCount),changeMoney,changePoint,$(this).parent());
							}
						}
					  });
					
					$("input[name='quantity']").blur(function(){
						var quantity=$(this).parent().find("input[name='quantity']").val();
						if(parseInt(quantity) <= parseInt(maxAddCount)){
							var beforeQuantity=$(this).attr("beforeQuantity");
							var orderDetailId=$(this).parent().attr("orderDetailId");				
							var changeMoney=parseFloat($(this).parent().attr("price"))*(parseInt(quantity)-parseInt(beforeQuantity));
							var changePoint=parseFloat($(this).parent().attr("point")*(parseInt(quantity)-parseInt(beforeQuantity)));
							updateDetail(orderDetailId,quantity,changeMoney,changePoint,$(this).parent())
						}else{
							var beforeQuantity=$(this).attr("beforeQuantity");
							var orderDetailId=$(this).parent().attr("orderDetailId");				
							var changeMoney=parseFloat($(this).parent().attr("price"))*(parseInt(quantity)-parseInt(beforeQuantity));
							var changePoint=parseFloat($(this).parent().attr("point")*(parseInt(quantity)-parseInt(beforeQuantity)));
							updateDetail(orderDetailId,maxAddCount,changeMoney,changePoint,$(this).parent())
						}
					})
				
					function updateDetail(orderDetailId,quantity,changeMoney,changePoint,tr){
						var orderId=query.orderId;
						var userId=query.userId;
						var updateParam={};
						updateParam.userId=userId;
						updateParam.orderId=orderId;
						updateParam.orderDetailId=orderDetailId;
						updateParam.quantity=quantity;
						tablePoint.bindJiazai($(".operationsInner"),true);	
						ajaxRequest.updateOrderDetail(updateParam,function(response) {
							tablePoint.bindJiazai($(".operationsInner"),false);	
							if(response.retCode=="200"){
								$(tr).find("input[name='quantity']").val(quantity);
								$(tr).find("input[name='quantity']").attr("beforeQuantity",quantity);
								shop.editOrder(query);	//加载修改页面					
							}else if(response.retCode=="779"){
								tablePoint.tablePoint("订单已结账不能修改订单");
							}else{
								tablePoint.tablePoint("改单失败！请刷新页面");
							}
						});
					}
					//订单详情金额、积分和数量动态改变---结束
					
					//删除订单详情
					$(".deleteOrderDetail").click(function(){
						var delParam={};	
						delParam.orderDetailId=$(this).attr("orderDetailId");
						if($(".deleteOrderDetail").length<=1){
							tablePoint.tablePoint("删除失败，订单菜品数量不能为0");
							return false;
						}
						//打单次数验证
						var sendOrderCount=$("input[name='sendOrderCount']").val();	
						if(comm.isEmpty(sendOrderCount))sendOrderCount=0;
						if(parseInt(sendOrderCount)>=maxPrintCount){	
							tablePoint.tablePoint("该订单已超过最大打单次数,不能删除菜品！");
		            		return false;
						}

						comm.i_confirm("确认删除该菜品?", function() {															
							delParam.orderId=query.orderId;
							delParam.userId=query.userId;
							ajaxRequest.delOrderDetail(delParam,function(response) {
								if(response.retCode=='200'){									
									tablePoint.tablePoint("删除成功");		
									shop.editOrder(query);	
								}else if(response.retCode=="779"){
									tablePoint.tablePoint("订单已结账不能修改订单");
								}else{
									tablePoint.tablePoint("删除失败请重试");
								}
							})
						})	
					});					
					//click事件 
					function addDiancaiClickEvent(){
						//选择菜品数量修改 开始
						$("#foodList_selectFoods .no-sub").unbind().click(function(){							
							var quantity=$(this).parent().find(".selectQuantity").text();
							if(parseInt(quantity)>0){
								$(this).parent().find(".selectQuantity").text(parseInt(quantity)-1);	
							}
						});							
						$("#foodList_selectFoods .add_sub").unbind().click(function(){
							var quantity=$(this).parent().find(".selectQuantity").text();
							if(parseInt(quantity) < parseInt(maxAddCount)){
								$(this).parent().find(".selectQuantity").text(parseInt(quantity)+1);	
							}else{
								$(this).parent().find(".selectQuantity").text(parseInt(maxAddCount));
							}
						});
					
						$("#takeOrder_orderInfos .no-sub").unbind().click(function(){								
							var quantity=$(this).parent().find(".number-show").val();
							if(parseInt(quantity)<=1){
								tablePoint.tablePoint("购买数量不能为0！");
							}else{
								$(this).parent().find(".number-show").val(parseInt(quantity)-1);
							}									
						});							
						$("#takeOrder_orderInfos .add_sub").unbind().click(function(){
							var quantity=$(this).parent().find(".number-show").val();
							if(parseInt(quantity) < parseInt(maxAddCount)){
								$(this).parent().find(".number-show").val(parseInt(quantity)+1);
							}else{
								$(this).parent().find(".number-show").val(parseInt(maxAddCount));
							}
						});
						
						//增减多规格事件
						$(".addMultiStandard").click(function(e){
							stopPropagation(e);
							$(".dggsect").hide();
							$(".addMultiStandard").show();
							var $cout = $(this).prev();
							var ct = parseInt($cout.text()); 
							if($(this).siblings().is(":hidden")){
								if($(this).next().hasClass("dggsect")){
									$(this).hide().next().show();	
								}else{
									$(this).siblings().show();
								}	
							}else if($cout.is(":visible")){
								if($cout.text()>=parseInt(maxAddCount)){
									return false;
								}else{
									$cout.text(ct +=1);
								}
							}
						});
						
						//多规格窗口操作
						$(".sbmt_dggsect").unbind().click(function(){
							//添加菜品
							//获得新增加的菜品
							var $this=$(this);
							var addFoods="";
							var parentDiv=$(this).parent().parent();
							var addFoodsStandard=$(parentDiv).find(".btn-guige .active").attr("standardindex");	//商品规格的json串下标 -1表示没有规格
							var buyNumber=$(parentDiv).find(".food-cout").text();
							var addFoods=$(parentDiv).attr("itemFoodId")+","+buyNumber+";";	//id，数量													
							if('0'==buyNumber){
								tablePoint.tablePoint("购买数量不能为0");
								return false;
							}
							/*if(parseInt(parseInt)> parseInt(maxAddCount)){
								tablePoint.tablePoint("数量不能大于单个菜品可选最大数量"+maxAddCount);
								return false;
							}*/
							var addParam={addFoods:addFoods,addFoodsStandard:addFoodsStandard,updateFoods:''};
							addParam.orderId=query.orderId;
							addParam.userId=query.userId;
							ajaxRequest.takeOrder(addParam,function(response) {
								if(response.retCode=='200'){
									tablePoint.tablePoint("加菜成功");	
									$($this).parents(".dggsect").hide().prev().show();																
									//刷新订单
									ajaxRequest.queryOrderDetail(query,function(response) {
										var html2 = _.template(dianCaiOrderInfosTpl,response);
										$("#takeOrder_orderInfos").html(html2);//订单信息
										
										addDiancaiClickEvent();
										$("#takeOrder_orderInfos .btn-jc").unbind().click(function(){									
											confirmDianCai();										
										});									
									})
									//返回到修改页面
								}else if(response.retCode=="779"){
									tablePoint.tablePoint("订单已结账不能修改订单");
								}else{
									tablePoint.tablePoint("继续点菜失败，请刷新页面重试");
									
								}
							});																							
						});
						$(".close_dggsect").click(function(){		
							$(this).parents(".dggsect").hide().prev().show();	
						});
						$(".add2").click(function(){
							var $cout = $(this).prev();
							var ct = parseInt($cout.text()); 
							if($cout.text()>=parseInt(maxAddCount)){
								return false;
							}else{
								$cout.text(ct +=1);
							}
						});
						$(".del2").click(function(){
							var $cout = $(this).next();
							var ct = parseInt($cout.text()); 
							if($cout.text()<=0){
								return false;
							}else{
								$cout.text(ct -=1);	
							}
						});
						
						//规格选择
						$(".btn-guige").on("click","input",function(){
							$(this).addClass("active").siblings().removeClass("active");
							//下面的价格和积分改变
							var parentDiv=$(this).parent().parent().parent();
							var standardIndex=$(this).attr("standardIndex");
							$(parentDiv).find(".standardPriceAndPv").attr("style","display:none");
							$(parentDiv).find(".standardPriceAndPv[standardIndex='"+standardIndex+"']").removeAttr("style");
						});
						
						//阻止冒泡
						function stopPropagation(e) {
						if (e.stopPropagation) 
							e.stopPropagation();
						else 
							e.cancelBubble = true;
						}
						//选择菜品数量修改 结束
					}
					//结束
					
					//确认点菜开始
					function confirmDianCai(){
						//获得新增加的菜品
						var addFoods="";
						var addFoodsStandard="";	//商品规格的json串下标 -1表示没有规格
						var allFoods=$("#foodList_selectFoods .selectQuantity");
						//过滤选择的菜品
						$.each(allFoods,function(key,selectFoods){
							//判断是否是选择的菜品
							if(parseInt($(selectFoods).text())>0&&parseInt($(selectFoods).text())<=parseInt(maxAddCount)){
								// id,数量									
								addFoods+=$(selectFoods).attr("itemFoodId")+","+$(selectFoods).text()+";";
								addFoodsStandard+=$(selectFoods).attr("standardIndex")+";"
							}
						});
						
						//获得修改数量的菜品
						var updateFoods="";					
						var orderInfos=$("#takeOrder_orderInfos .number-show");
						$.each(orderInfos,function(key,orderDetail){									
							var beforeQuantity=$(orderDetail).attr("beforeChangeQuantity");
							var changeQuantity=$(orderDetail).val();
							//判断是否修改数量
							if(parseInt(beforeQuantity)!=parseInt(changeQuantity) && parseInt(changeQuantity)<=parseInt(maxAddCount)){
								updateFoods+=$(orderDetail).attr("orderDetailId")+","+changeQuantity+";";
							}else{
								if(parseInt(changeQuantity) > parseInt(maxAddCount)){
									tablePoint.tablePoint("数量不能大于单个菜品可选最大数量"+maxAddCount);
									changeQuantity = maxAddCount;
								}
							}							
						});
					
						if(comm.isEmpty(addFoods)&&comm.isEmpty(updateFoods)){
							//tablePoint.tablePoint("请选择要点的菜品");
							$("#detailAddFood").addClass("none");		
							shop.editOrder(query);
							return false;
						}
						var addParam={addFoods:addFoods,addFoodsStandard:addFoodsStandard,updateFoods:updateFoods};
						addParam.orderId=query.orderId;
						addParam.userId=query.userId;
						ajaxRequest.takeOrder(addParam,function(response) {
							if(response.retCode=='200'){
								tablePoint.tablePoint("加菜成功");	
								//新增菜品清空为0
								$.each(allFoods,function(key,selectFoods){									
									$(selectFoods).text("0")																									
								});
								$("#detailAddFood").addClass("none");		
								shop.editOrder(query);
							}else if(response.retCode=="779"){
								tablePoint.tablePoint("订单已结账不能修改订单");
								$.each(allFoods,function(key,selectFoods){									
									$(selectFoods).text("0")																									
								});
								$("#detailAddFood").addClass("none");		
								shop.editOrder(query);
							}else{
								tablePoint.tablePoint("继续点菜失败，请刷新页面重试");
								
							}
						});		
					}
					//确认点菜 结束
					
					//加菜
					$(".diancai").unbind().click(function(){
						var sendOrderCount=$("input[name='sendOrderCount']").val();	//打单次数
						if(comm.isEmpty(sendOrderCount))sendOrderCount=0;
						if(parseInt(sendOrderCount)>=maxPrintCount){
							tablePoint.tablePoint("该订单已经超过打单的最大的次数,不能继续加菜!");
		            		return false;
						}
						query["shopId"] = $(this).attr("shopId");
						ajaxRequest.getItemCustomCategory(query,function(response) {
							var html1 = _.template(dianCaiSelectFoodsTpl,response);	//搜索								
							var html2 = _.template(dianCaiOrderInfosTpl,orderDetailResponse);
							var html3 = _.template(dianCaifoodsListTpl,response);	//菜品list
							$("#orderEdit").addClass("none").hide();
							$("#takeOrder_selectFoods").html(html1);//搜索
							$("#foodList_selectFoods").html(html3);//菜品list
							$("#takeOrder_orderInfos").html(html2);//订单信息
							$("#detailAddFood").removeClass("none").show();				
							
							//重设置已选菜品的宽度 
							if ($('#service').hasClass('none')){
								$('#takeOrder_selectFoods').css('width','69%');
							} else {
								$('#takeOrder_selectFoods').css('width','62%');
							} 	
										
							addDiancaiClickEvent();
							//搜索联动 开始
							$("#takeOrder_selectFoods .btn-search1").unbind().click(function(){
								$("#takeOrder_selectFoods .btn-search1").removeClass("btn-active");
								$(this).addClass("btn-active");
								var customCategoryId=$(this).attr("itemCustomCategoryId");
								query.customCategoryId=customCategoryId;
								query.searchName=$("#takeOrder_selectFoods input[name='searchName']").val();
								ajaxRequest.getItemCustomCategory(query,function(response){
									var html3 = _.template(dianCaifoodsListTpl,response);	
									$("#foodList_selectFoods").html(html3);
									addDiancaiClickEvent();
								});
								
							});				
							$("#takeOrder_selectFoods .btn-search2").unbind().click(function(){
								var customCategoryId=$("#takeOrder_selectFoods .btn-active").attr("itemCustomCategoryId");
								query.customCategoryId=customCategoryId;
								query.searchName=$("#takeOrder_selectFoods input[name='searchName']").val();
								ajaxRequest.getItemCustomCategory(query,function(response){
									var html3 = _.template(dianCaifoodsListTpl,response);	
									$("#foodList_selectFoods").html(html3);
									addDiancaiClickEvent();
								});
								
							});
							//搜索联动结束
							
							//确认点菜传参约定1 已经有的订单详情 2新增的菜品  数量的变换
							$("#takeOrder_orderInfos .btn-jc").unbind().click(function(){							
								confirmDianCai();								
							});							
						});					
					});
					
					$(".editClose").click(function(){
						shop.loadData(queryParam);
						$(".searchbox").removeClass("none").show();
						$("#orderDetail,#orderEdit").addClass("none").hide();	
					});
				});		
			}		
		}			
		return shop;
});