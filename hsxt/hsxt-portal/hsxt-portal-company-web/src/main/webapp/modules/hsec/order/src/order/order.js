define([ "text!hsec_orderTpl/orderHead/orderHead.html",
         "text!hsec_orderTpl/orderData/info.html",
         "hsec_orderDat/orderList",
         "text!hsec_orderTpl/orderDialog/deliverDialog.html",
         "text!hsec_orderTpl/orderDialog/sendDialog.html",
         "text!hsec_orderTpl/orderDialog/fixDeliverFeeDialog.html",
         "text!hsec_orderTpl/orderInfo/orderInfo.html",
         "text!hsec_orderTpl/orderInfo/printOrder.html",
         "text!hsec_orderTpl/gridMain.html",
         "text!hsec_orderTpl/tab.html"
         ,"hsec_tablePointSrc/tablePoint"
         ,"hsec_orderFoodSrc/function" ],
         function(tpl,tplData,ajaxRequest,deliverTpl,sendTpl,fixFeeTpl,orderInfoTpl,printOrderTpl,gridMainTpl,tabTpl,tablePoint) {
	var queryParam = {"orderStatusStr":"","currentPageIndex":1};
	var idclear = new Array();
	var index = "";
	var https = '';
	var orderStatusStr = "";
	var takeCode = "";
	var delayTime;//订单延迟的时间
	var orderDelayCount;//订单可以延迟的次数
	if(!delayTime){
		ajaxRequest.getDelayTime(function(r){
			if(r.retCode == '200'){
				delayTime = r.data;
			}
		});
	}
	if(!orderDelayCount){
		ajaxRequest.getDelayCount(function(rs){
			if(rs.retCode=='200'){
				orderDelayCount = rs.data;
			}
		});
	}
	
	var shop = {
			//查询条件
			queryParam : function(orderStatusStr, currentPageIndex) {
			   	var orderCode=$.trim($("#orderCode").val());
		    	var nickName =$.trim($("#ConsumerName").val());		
		    	var startTime=$.trim($("#startTime").val());
		    	var endTime =$.trim($("#endTime").val());
		    	var shopIds =$.trim($("#selRelatedShop").val());
//		    	var shopIds =$("#selRelatedShop").attr("optionvalue");
		    	var takeCode =$.trim($("#takeCode").val());
				queryParam = {};
				queryParam["orderStatusStr"] = orderStatusStr;
				queryParam["orderId"] = orderCode;
				queryParam["nikeName"] = nickName;
				if(startTime != '' && startTime != null){
					queryParam["startTime"] = startTime;
				}
				if(endTime != '' && endTime != null){
					queryParam["endTime"] = endTime;
				}
				if(typeof(shopIds) == "undefined") {
					shopIds = "";
				}
				queryParam["shopIds"] = shopIds;
				var boolDel = 0;
		    	if($("#orderDel").hasClass("active")){
		    		boolDel = 1;
		    	}
		    	if(takeCode != null &&　takeCode　!= ""){
		    	queryParam["takeCode"] = takeCode;
		    	}
		    	queryParam["shopDelStatus"] = boolDel;
		    	queryParam["eachPageSize"] = 10;
		    	return queryParam;
			},
			bindData : function() {
				ajaxRequest.queryStartSalerShop(function(response) {
					if (response.retCode == "200") {
						$("#busibox").html(_.template(gridMainTpl,response));
						//loadDatePicker("#startTime","#endTime");
						/**日期控件**/
						$("#startTime").datepicker({
							changeMonth : true,
							changeYear : true,
							dateFormat : "yy-mm-dd",
							maxDate : comm.getCurrDate(),
							onClose : function(selectedDate){
								$( "#endTime" ).datepicker( "option", "minDate", selectedDate );
							}
						});
						$(" #endTime").datepicker({
							changeMonth : true,
							changeYear : true,
							dateFormat : "yy-mm-dd",
							maxDate : comm.getCurrDate(),
							onClose : function(selectedDate){
								$( "#startTime" ).datepicker( "option", "maxDate", selectedDate );
							}
						});
						/**日期控件**/
						orderStatusStr = 0;
						shop.queryData(shop.queryParam(orderStatusStr,"1"));
					}
				});	
//				$("#busibox").html(_.template(gridMainTpl));
//				shop.selectList();
//				loadDatePicker("#startTime","#endTime");
//				orderStatusStr = 0;
//				shop.queryData(shop.queryParam(orderStatusStr,"1"));
				 $("input[type='text']").each(function(){
					$(this).val("");
				})
				$("#searchData").attr("orderStatus","");
			},
		    //shopSelect selRelatedShop
			//列表数据请求后台方法
			queryData:function(queryParam){
				var data = {};
				var data2 = {};
				var gridObj = $.fn.bsgrid.init('orderGridMain',{
						url :{url:comm.UrlList["queryOrders"],domain:"sportal"},//comm.domainList["sportal"]+comm.UrlList["queryOrders"],
						otherParames:queryParam,
						stripeRows:true,
						pageSize:10,
						//不显示空白行
						displayBlankRows: false,
						// 渲染单元格数据执行方法, 不论此单元格是否为空单元格(即无数据渲染)
						additionalRenderPerColumn: function(record, rowIndex, colIndex, tdObj, trObj, options){
							data = gridObj.getRecord(rowIndex);
							data.orderDelayCount = orderDelayCount;
							//页头一行显示数据信息html;
							sTpl = '<span class="mr15"><%=obj.createTime%></span><span class="mr15">订单编号：<%=obj.odId%></span>';
							sTpl +='<span class="mr15 fb blue">';
							sTpl +='<%try{%>'
					        sTpl +='  <%if(obj.isCardCustomer == 0){%>'
					        sTpl +='  		<%if(obj.nickName!=null && obj.nickName!=""){%>'
							sTpl +='          			<%var allName = obj.nickName;%>'
							sTpl +='          			<%if(obj.buyerAccountNo!=null && obj.buyerAccountNo!=""){%>'
							sTpl +='          				<%=allName+"（"+obj.buyerAccountNo.substring(0,3)+"****"+obj.buyerAccountNo.substring(7,11)+"）"%>'
							sTpl +='          			<%}else{%>'
							sTpl +='          				<%=allName%>'
							sTpl +='          			<%}%>'
					        sTpl +='  		<%}else{%>'
					        sTpl +='  					<%if(obj.buyerAccountNo!=null && obj.buyerAccountNo!=""){%>'
					        sTpl +='  						<%=obj.buyerAccountNo.substring(0,3)+"****"+obj.buyerAccountNo.substring(7,11)%>'
					        sTpl +='  					<%}%>	'
					        sTpl +='  		<%}%>'
					        sTpl +='  <%}else{%>'
					        sTpl +='  		<%if(obj.nickName!=null && obj.nickName!=""){%>'
							sTpl +='         			<%var allName = obj.nickName;%>'
							sTpl +='          			<%if(obj.buyerAccountNo!=null && obj.buyerAccountNo!=""){%>'
							sTpl +='          				<%=allName+"（"+obj.buyerAccountNo+"）"%>'
							sTpl +='          			<%}else{%>'
							sTpl +='          				<%=allName%>'
							sTpl +='          			<%}%>'
					        sTpl +='  		<%}else{%>'
					        sTpl +='  					<%if(obj.buyerAccountNo!=null && obj.buyerAccountNo!=""){%>'
					        sTpl +='  						<%=obj.buyerAccountNo%>'
					        sTpl +='  					<%}%>'	
					        sTpl +='  		<%}%>'
					        sTpl +='   <%}%>'
					        sTpl +='  <%}catch(e){}%>'
							sTpl +='</span>';
							sTpl +='<span class="mr15" title="<%=obj.shopName%>"><%if(obj.shopName!=null&&obj.shopName!=""){%><%=obj.shopName.substring(0,20)+"..."%><%}%></span>';
							sTpl +='<%if(obj.orderStatus == 3 || obj.orderStatus == 10){%>'
							sTpl +=' <span id="time'+rowIndex+'">'
							sTpl +='			<%if(obj.orderStatus == 3){%>待收货确认：<%}%><%if(obj.orderStatus == 10){%>待确认：<%}%><span class="time_d red"></span>天<span class="time_h red"></span>时<span class="time_m red"></span>分<span class="time_s red"></span>秒'
							sTpl +='			<%if(obj.orderStatus == 3){%>'
							sTpl +='			<%if((obj.delayDeliverCount == null || obj.delayDeliverCount == "")){%>'
							sTpl +='			&emsp;&emsp;&emsp;&emsp;<a href="javascript:;" class="yanchiTime" orderDelayTime = "<%=obj.orderDelayTime2%>" data-oid="<%=obj.orderId%>">延迟收货</a>'
							sTpl +='			<%}else{ %>' 
							sTpl +='				<%if( obj.delayDeliverCount < obj.orderDelayCount){%>' 
							sTpl +='					&emsp;&emsp;&emsp;&emsp;<a href="javascript:;" class="yanchiTime" orderDelayTime = "<%=obj.orderDelayTime2%>" data-oid="<%=obj.orderId%>">延迟收货</a>'
							sTpl +='				<%}%>'
							sTpl +='			<%}%>'
							sTpl +='		<%}%>'
							sTpl +='	 </span>'
							sTpl +='	<%}%>';
							sTpl +='<% var orderStatusList = [98,99,5,4,-1]; if(obj.shopDelStatus == 0){%> '
							sTpl +='<%try {$.each(orderStatusList,function(sk,sv){%>'
							sTpl +='<%if(sv == obj.orderStatus){%>'
							sTpl +='<span style="float: right;"><a class="fir_class orderDelete" style="vertical-align:middle;" data-oid="<%=obj.orderId%>" data-uid="<%=obj.userId%>">删除订单</a></span>'
							sTpl +='<%}%>'
							sTpl +='<%}) }catch(e){}%>'
							sTpl +='<%}%>'
							sTpl +='<%if(obj.shopDelStatus == 1){%>'
							sTpl +='<%try {$.each(orderStatusList,function(sk,sv){%>'
							sTpl +='<%if(sv == obj.orderStatus){%>'
							sTpl +='<span style="float: right;"><a class="fir_class orderReturn" style="vertical-align:middle;" data-oid="<%=obj.orderId%>" data-uid="<%=obj.userId%>">订单还原</a>&nbsp;&nbsp;&nbsp;<a class="fir_class orderLongDelete" style="vertical-align:middle;" data-oid="<%=obj.orderId%>" data-uid="<%=obj.userId%>">永久删除</a></span>'
							sTpl +='<%}%>'
							sTpl +='<%}) }catch(e){}%>'
							sTpl +='<%}%>'
							trObj.find('td').attr('colspan', '9').css({'background-color':'#EBEBEB', 'color':'#101010', 'text-align': 'left'}).html(_.template(sTpl, data));
							//订单详情
							var obList = data.orderDetailList;
							len = obList.length;
							//从单价到操作html;
							/*sTpl_2 = '<td></td><td></td>'*/
							sTpl_2 ='<td><span class="red"><%=comm.formatMoneyNumber(obj.actuallyAmount)%></span><p>含运费(<span class="red"><%=comm.formatMoneyNumber(obj.postAge)%></span>)</p></td>'
							sTpl_2 += '<td><span class="blue"><%=comm.formatMoneyNumber(obj.totalPoints)%></span></td>'
							sTpl_2 += '<td><% if(obj.deliveryType==1){%>快递<%}else if(obj.deliveryType==2){%>营业点自提<%}else if(obj.deliveryType==3){%>送货上门<%}%></td>'
							sTpl_2 += '<td><% if(obj.payType==1){%>现金<%}else if(obj.payType==3){%>互生币<%}else if(obj.payType==4){%>网银<%}else{%>其他<%}%></td>'
							sTpl_2 += '<td><%if(obj.orderStatus == 3){%>发货时间:<%=obj.sendTime%><br/><%}%><%=obj.orderStatusName%><%if(obj.orderStatus == 1){%><br/><%=obj.paidTime%><%}else if(obj.orderStatus == 4){%><br/><%=obj.endTime%><%}%></td><td></td>';
							
							deliveryType = data.deliveryType;
							orderStatus = data.orderStatus;
							postAge = data.postAge;
							isPayOnDelivery = data.isPayOnDelivery;
							payType = data.payType;
							
							//修改bug 19279
							var tkCode = $.trim($("#takeCode").val());
							
							for(var i = len - 1; i >= 0; i--){
								var $tr = $('<tr></tr>');
								obList[i]["https"] = https;//动态设置服务品路径
								$tr.append(_.template(tplData, obList));//渲染订单详情信息
								if(i === 0) {
									$tr.append(_.template(sTpl_2, data));//渲染订单
									var $buttons = $('<div></div>');//操作列按键。
									//查看详情
									$buttons.append($('<p><a href="javascript:;" class="orderInfo" title="查看详情">查看详情</a></p>').click(function(e){
										shop.showDetail(gridObj.getRecord(rowIndex));
									}));
									if(deliveryType==3 && (orderStatus==3 || orderStatus==8)){
								         $buttons.append($('<p><a class="ddlb_fh printSendOrder" title="打印送货单">打印送货单</a></p>').click(function(e){
											shop.printSendOrder(gridObj.getRecord(rowIndex));
										}));
								      }
								    //判断时须转换为对象
								    if(typeof(orderStatusStr) != ''){
								    	if(orderStatus==0 && deliveryType!=2){
								    		$buttons.append($('<p><a oldPostage="'+postAge+'" class="ddlb_fh fixDeliverFee" >修改运费</a></p>').click(function(e){
								    			shop.fixDeliverFee(gridObj.getRecord(rowIndex));
								    		}));
								    	}// end 0 & 2
								    	if(orderStatus==1 || orderStatus==9){
								    		$buttons.append($('<p><a class="ddlb_fh stocking" >备货</a></p>').click(function(e){
								    			//alert("备货");
								    			shop.stocking(gridObj.getRecord(rowIndex));
								    		}));
								    	}//end 1 & 9
								    	if(orderStatus==2 || orderStatus==7){
									    	if(deliveryType == 1){
									         	$buttons.append($('<p><a class="ddlb_fh deliverItem" >发货</a></p>').click(function(e){
									         		//alert("发货");
									         		shop.deliverItem(gridObj.getRecord(rowIndex));
								    			}));
									         }
									         if(deliveryType == 3){
									         	$buttons.append($('<p><a class="ddlb_fh sendItem" >送货</a></p>').click(function(e){
									         		//alert("送货");
									         		shop.sendItem(gridObj.getRecord(rowIndex));
								    			}));
									         }
								    	}// end 2 & 7
								    	 if(orderStatus==10){
									         $buttons.append($('<p><a  class="ddlb_fh yesOrder" >确定</a></p>').click(function(e){
									         	//alert("确定");
									         	shop.yesOrder(gridObj.getRecord(rowIndex));
									         }));
									      }// end 10
									     if(orderStatus==6){
									           if(isPayOnDelivery == 1){
									           		$buttons.append($('<p><a class="ddlb_fh confirmPay" >确定已支付</a></p>').click(function(e){
														//alert("确定已支付");
									           			shop.confirmPay(gridObj.getRecord(rowIndex));
									           		}))
									           }
									           if(deliveryType == 2 && ("1,3,4").indexOf(payType) > -1 ){
									        	   if(tkCode){
									        		   	$buttons.append($('<p><a class="ddlb_fh deliveryYes" id="txtTakeCode" takeCode="'+data.takeCode+'">确认自提</a></p>').click(function(e){
															//alert("确认自提");
										           			shop.deliveryYes(gridObj.getRecord(rowIndex));
										           		}))
									        	   }
									          }
									      }//end 6
									      if(orderStatus==8){
									    	  $buttons.append($('<p><a class="ddlb_fh confirmPay" >确定已支付</a></p>').click(function(e){
									         	  //alert("确定已支付");
									          	shop.confirmPay(gridObj.getRecord(rowIndex));
									          }));
									      }//end 8;
								    }//end orderStatusStr;;
									$tr.find('td:last').append($buttons);
								}
							}
							trObj.after($tr);
						},
						complete:function(options, XMLHttpRequest, textStatus){
							data2 = eval("("+XMLHttpRequest.responseText+")");
							//add by zhanghh :2016-04-07,当没有数据展示时合并单元格，视具体情况合并
							if(data2.data.length<=0){
								$("#orderGridMain").find("tbody tr td").attr("colspan","9");
							}
							other = data2.orther;
							if(other == null){
								return;
							}
							data.getTypeTen = other.getTypeTen;
							data.getTypeThree = other.getTypeThree;
							data.httpUrl = other.httpUrl;
							https = other.httpUrl;

							
							$(".orderImg").each(function(){
								var dsrc = $(this).attr("data-src");
								$(this).attr("src",https + dsrc);
							});
							
							data.orderDelayCount2 = other.orderDelayCount;
							data.orderDelayTime2 = other.orderDelayTime;
							data.orderTime = other.orderTime;
							data.shopType = other.shopType;
							
							if(other.shopType==1){
							 	$("#shopSelect").hide();
							}
							var takeCode = $("#takeCode").val();
							if(takeCode != null && takeCode != ""){
								data.takeCode = takeCode;
								takeCode = takeCode;
							}else{
								data.takeCode = -1;
								takeCode = -1;
							}
							shop.setTimeoutUpdate();
							//start time
							if(data2.data.length > 0){
								$.each(data2.data,function(k,y){
									if(y.orderStatus == 3 || y.orderStatus == 10){
			    						var timeNum = 0;
			    						var orderTimeNum = 0;
			    						var overTime = 0;
			    						var delayTime = 0;
			    						var date;
				    						try{
				    							if(y.orderStatus == 3){
				    								if(y.delayDeliverTime != null &&　y.delayDeliverTime != ""){
						    							var date = new Date(y.delayDeliverTime.replace(/-/g,"/"));
							    						timeNum = date.getTime();
						    						}else if(y.sendTime != null && y.sendTime != ""){
							    						var date = new Date(y.sendTime.replace(/-/g,"/"));
							    						timeNum = date.getTime();
						    						}
						    						date = new Date(data.orderTime.replace(/-/g,"/"));
						    						orderTimeNum = date.getTime();
						    						overTime = data.getTypeThree;
				    							}else if(y.orderStatus == 10){
				    								if(y.cancelTime != null && y.cancelTime != ""){
							    						var date = new Date(y.cancelTime.replace(/-/g,"/"));
							    						timeNum = date.getTime();
						    						}
						    						date = new Date(data.orderTime.replace(/-/g,"/"));
						    						orderTimeNum = date.getTime();
						    						overTime = data.getTypeTen;
				    							}
				    						}catch(e){
				    							timeNum = 0;
				    						}
				    					shop.bindShowTime(timeNum+overTime+delayTime,orderTimeNum,k);
			    		    		}
		    		    		})//end $.each;
							}else{
								$("#orderGridMain").find("tbody tr td").attr("colspan","9");//合并单元格
							}
	    		    		//end start time;
							shop.bindHeadClick();
							//延迟收货
							$(".yanchiTime").unbind().on("click",function(){
								//alert("延迟收货");
								shop.yanchiTime({"yanchiTime":$(this).attr("orderDelayTime"),"orderId":$(this).attr("datd-oid")});
							});
							//删除定单
							$(".orderDelete").unbind().on("click",function(){
								shop.orderDelete({"orderId":$(this).attr("data-oid"),"userId":$(this).attr("data-uid")})
							});
							//还原订单
							$(".orderReturn").unbind().on("click",function(){
								//alert("还原订单");
								shop.orderReturn({"orderId":$(this).attr("data-oid"),"userId":$(this).attr("data-uid")});
							});
							$(".orderLongDelete").unbind().on("click",function(){
								//alert("永久删除订单");
								shop.orderLongDelete({"orderId":$(this).attr("data-oid"),"userId":$(this).attr("data-uid")});
							});
						}
					});
			},	
			bindTabClick:function(){
				//选择类型事件
			    $(".operType").unbind().on('click',function(){
			    		$("#orderDel").removeClass("active"); 
				    	 //样式变化
				    	if($(this).attr("id")!=$("a[name='operType'][class='active']").attr("id")){
							$(".operType").removeClass("active"); 
							$(this).addClass("active");
						}
				    	var type = $(this).attr("type");
				    	//状态清空
				    	$("#searchData").attr("orderStatus",type);
				    	
				    	if(type == "6"){
				    		$("#takeCodeLi").show();
				    		$("#ConsumerNameLi").hide();
				    	}else{
				    		$("#takeCodeLi").hide();
				    		$("#ConsumerNameLi").show();
				    	}
				    	
				    	//清空输入框
				    	$("input[type='text']").each(function(){
							$(this).val("");
		                 })
		                shop.queryParam(type,"1");
						shop.queryData(queryParam);
			    });
			},
			//题头事件绑定
			bindHeadClick:function(){
				
//				//编号输入验证
//				$("#orderCode").on('focus',function(){
//					$(this).on('keyup',function(){
//						var num = $.trim($(this).val());
//						var p1=/^[\d]+$/;
//						if(!p1.test(num)){
//							$(this).val("");
//						}else{
//							$(this).val(num);
//						}
//					})
//				});
					//下拉列表框，关联实体店
					$("#selRelatedShop").unbind().on("change",function(){
				    	$(this).attr("title",$(this).find("option:selected").text());
				    	//$(this).attr("title",$(this).attr("optionvalue"));
					})
				     //搜索
				    $("#searchData").unbind().on('click',function(){
				    	var orderStatusStr=$("#searchData").attr("orderStatus");
				    	shop.queryParam(orderStatusStr,"1");
				    	shop.queryData(queryParam);
				    });
				    
				    $("#orderDel").unbind().on('click',function(){
				    	 //样式变化
				    	if($(this).attr("id")!=$("a[name='operType'][class='active']").attr("id")){
							$(".operType").removeClass("active"); 
							$(this).addClass("active");
						}
				    	//状态清空
				    	$("#searchData").attr("orderStatus","");
				    	if($("#ConsumerNameLi").is(":hidden")){
				    		$("#takeCodeLi").hide();
				    		$("#ConsumerNameLi").show();
				    	}
				    	//清空输入框
				    	$("input[type='text']").each(function(){
							$(this).val("");
		                 })
		                shop.queryParam("","1");
						shop.queryData(queryParam);
				    });
			    },
			    //订单详情　add by zhanghh ＠date:2015-12-25
			    showDetail : function(obj){
		    		var orderId =obj.orderId;
		    		var userId =obj.userId;
		    		var param = "userId="+userId+"&orderId="+orderId;
		    		ajaxRequest.queryOrderInfo(param,function(response){
		    		var html = _.template(orderInfoTpl, response.data);
		    		$('#orderInfoAll').html(html);
		    		$("#orderListMain").hide();
		    		//var gridInfoObj = $.fn.bsgrid.init('gridInfo',{});
		    		$("#orderClose").on("click",function(){
			    			$('#orderInfoAll').html("");
				    		$("#orderListMain").show();
			    		})
		    		})
			    },
			    	//删除订单
			    orderDelete : function(obj){
			    		var orderId =obj.orderId;
			    		var userId =obj.userId;
			    		var param = "userId="+userId+"&orderId="+orderId;
			  		  $( "#dlg3" ).dialog({
							title:'你确定要删除订单？',
							show: true,
							modal: true,
							open : function(){
								$(this).html("<p class='table-del'>删除后，您可在订单回收站找回，或永久删除</p>");
							},
							close: function() { 
						        $(this).dialog('destroy');
							},
							buttons: {
								"确定": function() {
									 	$(this).dialog('destroy');
										ajaxRequest.deleteOrdersBySaler(param,function(response){
							    			if(response.retCode == 200){
							    				 tablePoint.tablePoint("删除成功！",function(){
							    					  //$($this).parents(".orderTr").remove();
							    				 	  $("#searchData").click();
							    				   });
							    			}else{
							    				 tablePoint.tablePoint("删除失败！");
							    			}
							    		})
								},
								"取消": function() {
								    $( this ).dialog( "destroy" );
								}
							}
						});
			    	},
			    	//还原订单
			    	orderReturn:function(obj){
			    		var $this = $(this);
			    		var orderId =obj.orderId;
			    		var userId =obj.userId;
			    		var param = "userId="+userId+"&orderId="+orderId;
			    		ajaxRequest.backOrdersBySaler(param,function(response){
			    			if(response.retCode == 200){
			    				 tablePoint.tablePoint("订单还原成功,可在全部里面查找！",function(){
			    					  //$($this).parents(".orderTr").remove();
			    				 	  $("#searchData").click();
			    				   });
			    			}else{
			    				 tablePoint.tablePoint("订单还原失败！");
			    			}
			    		})
			    	},
			    	//永久删除订单 add by zhanghh @date:2015-12-10
			  		orderLongDelete:function(obj){
			  			var $this = $(this);
			    		var orderId =obj.orderId;
			    		var userId =obj.userId;
			    		var param = {"userId":userId,"orderId":orderId};
			    		$( "#dlg3" ).dialog({
							title:'你确定永久删除订单？',
							show: true,
							modal: true,
							open : function(){
								$(this).html("<p class='table-del'>删除后将无法再查看订单信息,请谨慎!</p>");
							},
			    			buttons:{
			    				"确定":function(){
			    					$( this ).dialog( "destroy" );
			    					ajaxRequest.deletePermOrdersBySaler($.param(param),function(response){
						    			if(response.retCode==200){
						    				tablePoint.tablePoint("永久删除订单成功！",function(){
					    					  //$($this).parents(".orderTr").remove();
					    					  $("#searchData").click();
					    				   });
						    			}else{
						    				tablePoint.tablePoint("永久删除订单失败！");
						    			}
						    		});
			    				},
			    				"取消":function(){
			    					//$( "#dlg3" ).dialog("destroy");
			    					$( this ).dialog( "destroy" );
			    				}
			    			}
			    		});
			  		},
			    	//延迟收货
			    	yanchiTime:function(obj){
			    		var $this = $(this);
			    		var yanchiTime = obj.yanchiTime;//$(this).attr("orderDelayTime");
			    		//var orderId =obj.orderId;
			    		var orderId = $(".yanchiTime").attr('data-oid'); //David 2016-01-13
			    		var param = "orderId="+orderId;
			    
			  		  $( "#dlg3" ).dialog({
							title:'延迟收货',
							show: true,
							modal: true,
							open : function(){
								$(this).html("<p class='table-del'>订单收货时间将延长"+delayTime+"小时,确定延时?</p>");//"+yanchiTime+"小时
							},
							buttons: {
								"确定": function() {
									 	$(this).dialog('destroy');
										ajaxRequest.delayDeliver(param,function(response){
							    			if(response.retCode == 200){
							    				 tablePoint.tablePoint("操作成功！",function(){
							    					 //shop.queryData(queryParam);
							    				 	$("#searchData").click();
							    				   });
							    			}else{
							    				 tablePoint.tablePoint("操作失败！");
							    			}
							    		})
								},
								"取消": function() {
								    $( this ).dialog( "destroy" );
								}
							}
						});
			    	},
			    	
			    	//确定已经支付
			    	confirmPay:function(obj){
			    		  var orderId =obj.orderId;
			    		  var userId =obj.userId;
			    		  var param = {"userId":userId,"orderId":orderId};
			    		  
			    		  $( "#dlg3" ).dialog({
								title:'确定支付',
								show: true,
								modal: true,
								open : function(){
									$(this).html("<p class='table-del'>确定已经支付?</p>");
								},
								close: function() { 
							        $(this).dialog('destroy');
								},
								buttons: {
									"确定": function() {
										 	$(this).dialog('destroy');
										  ajaxRequest.confirmPayBySaler(param,function(response){
							    			   if(response.retCode=="200"){
							    				   tablePoint.tablePoint("操作成功！",function(){
							    					   shop.queryData(queryParam);
							    				   });
							    			   }else if(response.retCode=="782"){
							    				   tablePoint.tablePoint("订单支付处理中！",function(){
							    					   shop.queryData(queryParam);
							    				   });
							    			   }else if(response.retCode=="781"){
							    				   tablePoint.tablePoint("订单冲正处理中！",function(){
							    					   shop.queryData(queryParam);
							    				   });
							    			   }else if(response.retCode=="791"){
							    				   tablePoint.tablePoint("订单已支付！",function(){
							    					   shop.queryData(queryParam);
							    				   });
							    			   }else if(response.retCode=="590"){
							    				   tablePoint.tablePoint("积分预付款余额不足！",function(){
							    					   shop.queryData(queryParam);
							    				   });
							    			   }else if(response.retCode=="589"){
							    				   tablePoint.tablePoint("企业流通账款账户余额不能为负数！",function(){
							    					   shop.queryData(queryParam);
							    				   });
							    			   }else{
							    				   tablePoint.tablePoint("操作失败！");
							    			   }
							    		  })
									},
									"取消": function() {
									    $( this ).dialog( "destroy" );
									}
								}
							});
			    	},
			    	//取消订单
			    	cancelPay:function(obj){
			    		  var param = {};
			    		  param["orderId"] =obj.orderId;
			    		  param["userId"] =obj.userId;
			    		  $( "#dlg3" ).dialog({
								title:'取消订单',
								show: true,
								modal: true,
								open : function(){
									$(this).html("<p class='table-del'>确定取消订单?</p>");
								},
								close: function() { 
							        $(this).dialog('destroy');
								},
								buttons: {
									"确定": function() {
										 	$(this).dialog('destroy');
										 	  ajaxRequest.cancelOrderBySaler(param,function(response){
								    			   if(response.retCode=="200"){
								    				   tablePoint.tablePoint("操作成功！",function(){
								    					   shop.queryData(queryParam);
								    				   });
								    			   }else{
								    				   tablePoint.tablePoint("操作失败！");
								    			   }
								    		  })
									},
									"取消": function() {
									    $( this ).dialog( "destroy" );
									}
								}
							});
			    	},
			     	//确定备货
			    	stocking:function(obj){
			    		  var param = {};
			    		  param["orderId"] =obj.orderId;
			    		  param["userId"] =obj.userId;
			    		  $( "#dlg3" ).dialog({
								title:'确定备货',
								show: true,
								modal: true,
								open : function(){
									$(this).html("<p class='table-del'>确定备货?</p>");
								},
								buttons: {
									"确定": function() {
										 	  $(this).dialog('destroy');
										 	  ajaxRequest.salerOrderStocking(param,function(response){
								    			   if(response.retCode=="200"){
								    				   tablePoint.tablePoint("操作成功！",function(){
								    					   shop.queryData(queryParam);
								    				   });
								    			   }else{
								    				   tablePoint.tablePoint("操作失败！");
								    			   }
								    		  })
									},
									"取消": function() {
									    $( this ).dialog( "destroy" );
									}
								}
							});
			    	},
			     	//确定取消订单
			    	yesOrder:function(obj){
			    		  var param = {};
			    		  param["orderId"] =obj.orderId;
			    		  param["userId"] =obj.userId;
			    		  $( "#dlg3" ).dialog({
								title:'确定取消订单',
								show: true,
								modal: true,
								closeIcon:true,//Modify by zhanghh 2016-01-19 添加右上角关闭图标
								open : function(){
									$(this).html("<p class='table-del'>确定取消订单?</p>");
								},
								buttons: {
									"确定": function() {
										 	  $(this).dialog('destroy');
										 	  ajaxRequest.confirmCancelBySaler(param,function(response){
								    			   if(response.retCode=="200"){
								    				   tablePoint.tablePoint("操作成功！",function(){
								    					   shop.queryData(queryParam);
								    				   });
								    			   }else{
								    				   tablePoint.tablePoint("操作失败！");
								    			   }
								    		  })
									}
								}
							});
			    	},
			    	//确定用户自提
			     	deliveryYes:function(obj){
			     	    var orderId =obj.orderId;
		    		    var userId =obj.userId;
		    		    var shopId =obj.shopId;
			     		var takeCode = $("#txtTakeCode").attr("takeCode");
			     		var param = {};
			     		param["orderId"] = orderId;
			     		param["userId"] = userId;
			     		param["shopId"] = shopId;
			     		param["takeCode"] = takeCode;
			    		  $( "#dlg4" ).dialog({
								title:'自提确认',
								show: true,
								modal: true,
								open : function(){
									$(this).html("<p class='table-del'>确认该订单已自提!</p>");
								},
								buttons: {
									"确定": function(){
										ajaxRequest.takeToOfflineStore(param,function(response){
											if(response.retCode=="200"){
												tablePoint.tablePoint("确认成功!",function(){
													$("#dlg4").dialog('destroy');
													shop.queryData(queryParam);
												});
											}else{
												tablePoint.tablePoint("确认失败!");
											}
										})
									},
									"取消": function(){
										 $(this).dialog('destroy');
									}
								}
							});
			    	},
			     	
			    	//发货弹出窗口
			    	 deliverItem:function(obj){
			    		  var orderId =obj.orderId;
			    		  var userId =obj.userId;
			    		  var param = {"orderId":orderId,"userId":userId};
						ajaxRequest.getLogisticList(param,function(response){
	        				var html = _.template(deliverTpl, response.data);
	        				$('#dlg2').html(html);
	        				$("#dlg2").dialog({
								title : "物流公司选择",
								width : "600",
								height: "300",
								modal : true,
								close: function() { 
								        $(this).dialog('destroy');
								},
								buttons : {
									'确定' : function() {
										    var logisticId = $("#logisticalSelect option:selected").val();
										    var logisticName=$("#logisticalSelect option:selected").text();
										    var logisticCode=$.trim($("#logisticCode").val());
										    var logisticNode=$("#logisticNode").val();
										   if(""== logisticId){
											   tablePoint.tablePoint("请先选择物流公司！")
											   return false;
										   }else if(logisticCode.length > 19 || logisticCode.length < 1){
											   tablePoint.tablePoint("请填写正确的物流单号！")
											   return false;
										   }else if(logisticNode.length > 50){
											   tablePoint.tablePoint("备注长度不能超过50个字符！")
											   return false;
										   }else{
												shop.confirmLogisticData(orderId,userId,logisticId,logisticName,logisticCode,logisticNode);
												 $(this).dialog("destroy"); 
										   }
									},
									'关闭' : function() {
										$(this).dialog("destroy");
									}
								}
							});
			        	});
					},
		    	  //送货弹出窗口
		    	  sendItem:function(obj){
		    		    var orderId =obj.orderId;
		    		    var userId =obj.userId;
		    		    var shopId =obj.shopId;
		    		    var param = {}
		    		    //实体店Id
		    		    param["shopId"]=shopId;
		    		    ajaxRequest.querySalerShopDeliver(param,function(response){
			    		    	var html = _.template(sendTpl, response);
								$('#dlg2').html(html);
								//绑定选择送货人事件
			    		    	$("#deliverManSel").change(function(){
			    		    		$("#deliverContact").val($("#deliverManSel option:selected").attr("phone"));
			    		    	});
								$("#dlg2").dialog({
									title : "送货人选择",//display: block; width: 546px; min-height: 50px; max-height: none; height: 227px;
									width : 600,
									height:300,
									modal : true,
									close: function() { 
								        $(this).dialog('destroy');
									},
									buttons : {
										'确定' : function() {
											 var deliveryId=$("#deliverManSel option:selected").val();
										     var deliverName=$("#deliverManSel option:selected").text();
										     var deliverContact=$("#deliverContact").val();
										     var deliverNode=$("#deliverNode").val();
										     
										     if(""== deliveryId){
												   tablePoint.tablePoint("请先选择送货人！")
												   return false;
											   }else if(deliverNode.length > 50){
												   tablePoint.tablePoint("备注长度不能超过50个字符！")
												   return false;
											   }else{
												   shop.confirmDeliverManData(orderId, userId, deliveryId, deliverName, deliverContact, deliverNode)
												   $(this).dialog("destroy");
											   }
										},
										'关闭' : function() {
											$(this).dialog("destroy");
										}
									}
								});
		    		    })
					},
		    	//修改运费弹出窗口
		    	  fixDeliverFee:function(obj){
		    		  	var re = /^\d+$/;
		    		    var orderId =obj.orderId;
		    		    var userId =obj.userId;
		    		   // var oldPostage=$(this).attr("oldPostage")
		    		    var oldPostage = obj.postAge; // David 2016-1-13
			    		    	var html = _.template(fixFeeTpl, null);
								$('#dlg2').html(html);
								//设置旧运费
								$("#OldPostage").val(comm.formatMoneyNumber(oldPostage));
								$("#dlg2").dialog({
									title : "修改运费",
									width : "600",
									modal : true,
									close: function() { 
								        $(this).dialog('destroy');
									},
									buttons : {
										'确定' : function() {
											var postage = $("#dlg2 .newPostage").val();
											if(!re.test(postage) || postage > 999999 || postage < 0){
												tablePoint.tablePoint("请输入整数，范围0~999999");
												return;
											}
											var param={"userId":userId,"orderId":orderId,"postage":postage};
											ajaxRequest.changePostageBySaler(param,function(response){
												if(response.retCode=="200"){
													tablePoint.tablePoint("修改成功！",function(){
														shop.queryData(queryParam);
													});
												}else{
													tablePoint.tablePoint("修改失败！");
												}
											})
											//情况页面的值
											$(this).dialog("destroy");
											
										},
										'关闭' : function() {
											$(this).dialog("destroy");
										}
									}
								});
					},
		    	  
		    	  	//打印送货单
		    	    printSendOrder:function(obj){
		    	    	   var orderId =obj.orderId;
			    		   var userId =obj.userId;
		    	    	   var param = "userId="+userId+"&orderId="+orderId;
						   ajaxRequest.queryOrderInfo(param,function(response){
							   if(response.retCode == 200){
					    			//填充打印单信息
					    		   var printHtml = _.template(printOrderTpl, response.data);	
					    		   //打印指定的内容    		    	    	 
				    	    	   $('#wrap').parent().append("<div id='wrapPrint'></div>")
				    	    	   $('#wrapPrint').html(printHtml);
				    	    	   $('#wrap').addClass("noprint");		    	 
				    	    		//设置网页打印的页眉页脚为空		    	    		
				    	    		try{
				    	    			var hkey_root,hkey_path,hkey_key;
				    	    			hkey_root="HKEY_CURRENT_USER";
				    	    			hkey_path="SoftwareMicrosoftInternet ExplorerPageSetup";
				    	    			var RegWsh = new ActiveXObject("WScript.Shell");
				    	    			hkey_key="header" ;
				    	    			RegWsh.RegWrite(hkey_root+hkey_path+hkey_key,"");
				    	    			hkey_key="footer";
				    	    			RegWsh.RegWrite(hkey_root+hkey_path+hkey_key,"");
				    	    		}catch(e){
				    	    			var a='';
				    	    		}		    	    		
				    	    	   window.print(); 
				    	    	   $('#wrap').removeClass("noprint");
				    	    	   $("#wrapPrint").remove();
							   }
						   });
		    	   },
			    //发货调用后台方法
			    confirmLogisticData:function(orderId,userId,logisticId,logisticName,logisticCode,logisticNode){
				    var param={};
				    param["orderId"]=orderId;
				    param["userId"]=userId;
				    param["logisticId"]=logisticId;
				    param["logisticName"]=logisticName;
				    param["logisticCode"]=logisticCode;
				    param["logisticNode"]=logisticNode;
					   ajaxRequest.confirmDeliveryBySaler($.param(param),function(response){
						   if(response.retCode == 200){
							   shop.queryData(queryParam);
						   }else{
							   tablePoint.tablePoint("发货失败!请稍后重试!");
						   }
					   })
			   },
			    //送货调用后台方法
			    confirmDeliverManData:function(orderId,userId,deliveryId,deliverName,deliverContact,deliverNode){
				    var param={};
				    param["orderId"]=orderId;
				    param["userId"]=userId;
				    param["deliveryId"]=deliveryId;
				    param["deliverName"]=deliverName;
				    param["deliverContact"]=deliverContact;
				    param["deliverNode"]=deliverNode;
					   ajaxRequest.confirmDeliveryBySaler($.param(param),function(response){
						   if(response.retCode == 200){
								$( "#dlg3" ).dialog({
				   	  				title : "提示信息",
				   	  				show: true,
				   	  				modal: true,
				   	  				open : function(){
				   	  					$(this).html("<p class='table-del'>打印该送货单?</p>");
			   	  				},
			   	  				buttons: {
			   	  					"确定": function() {
										   //打印送货单
						    	    	   var param = "userId="+userId+"&orderId="+orderId;			    
										   ajaxRequest.queryOrderInfo(param,function(response){
											   $( "#dlg3" ).dialog( "destroy" );
											   if(response.retCode == 200){
								    			  //填充打印单信息
									    		   var printHtml = _.template(printOrderTpl, response.data);				    		 	    	 
								    	    	   $('#wrap').parent().append("<div id='wrapPrint'></div>")
								    	    	   $('#wrapPrint').html(printHtml);
								    	    	   $('#wrap').addClass("noprint");	 
								    	    	   // PageSetup_Null();							    	    	
								    	    	   window.print(); 
								    	    	   $('#wrap').removeClass("noprint");
								    	    	   $("#wrapPrint").remove();	
											   }
							    	    	   shop.queryData(queryParam);
										   });															  
			   	  					},
			   	  					"取消": function() {
			   	  						shop.queryData(queryParam);
			   	  					    $( this ).dialog( "destroy" );
			  	  					}
			   	  				}
			   	  			});
						   }else{
							   tablePoint.tablePoint("送货失败!请稍后重试!");
						   }
					   })
			   },
			//对话框绑定事件
			    bindDialog:function(){
			    	//查看详情
			    	$(".orderInfo").unbind().on('click',function(){
			    		var orderId =$(this).parents(".orderTr").attr("orderId");
			    		var userId =$(this).parents(".orderTr").attr("userId");
			    		var param = "userId="+userId+"&orderId="+orderId;
			    		ajaxRequest.queryOrderInfo(param,function(response){
			    		var html = _.template(orderInfoTpl, response.data);
			    		$('#orderInfoAll').html(html);
			    		$("#orderListMain").hide();
			    		$("#orderClose").on("click",function(){
			    			$('#orderInfoAll').html("");
				    		$("#orderListMain").show();
			    		})
			    	})
			    	})
			    	
			    		//删除订单
			    	$(".orderDelete").unbind().on('click',function(){
			    		var $this = $(this);
			    		var orderId =$(this).parents(".orderTr").attr("orderId");
			    		var userId =$(this).parents(".orderTr").attr("userId");
			    		var param = "userId="+userId+"&orderId="+orderId;
			  		  $( "#dlg3" ).dialog({
							title:'你确定要删除订单？',
							show: true,
							modal: true,
							open : function(){
								//$(this).html("<p class='table-del'>确定删除订单?(注：订单进入回收站.)</p>");
								$(this).html("<p class='table-del'>删除后，您可在订单回收站找回，或永久删除</p>");
							},
							close: function() { 
						        $(this).dialog('destroy');
							},
							buttons: {
								"确定": function() {
									 	$(this).dialog('destroy');
										ajaxRequest.deleteOrdersBySaler(param,function(response){
							    			if(response.retCode == 200){
							    				 tablePoint.tablePoint("删除成功！",function(){
							    					  $($this).parents(".orderTr").remove();
							    				   });
							    			}else{
							    				 tablePoint.tablePoint("删除失败！");
							    			}
							    		})
								},
								"取消": function() {
								    $( this ).dialog( "destroy" );
								}
							}
						});
			    	})
			    	//还原订单
			    	$(".orderReturn").unbind().on('click',function(){
			    		var $this = $(this);
			    		var orderId =$(this).parents(".orderTr").attr("orderId");
			    		var userId =$(this).parents(".orderTr").attr("userId");
			    		var param = "userId="+userId+"&orderId="+orderId;
			    		ajaxRequest.backOrdersBySaler(param,function(response){
			    			if(response.retCode == 200){
			    				 tablePoint.tablePoint("订单还原成功,可在全部里面查找！",function(){
			    					  $($this).parents(".orderTr").remove();
			    				   });
			    			}else{
			    				 tablePoint.tablePoint("订单还原失败！");
			    			}
			    		})
			    	})
			    	//永久删除订单 add by zhanghh @date:2015-12-10
			  		$(".orderLongDelete").off(".orderLongDelete").on("click",function(){
			  			var $this = $(this);
			    		var orderId =$(this).parents(".orderTr").attr("orderId");
			    		var userId =$(this).parents(".orderTr").attr("userId");
			    		var param = {"userId":userId,"orderId":orderId};
			    		$( "#dlg3" ).dialog({
							title:'你确定永久删除订单？',
							show: true,
							modal: true,
							open : function(){
								$(this).html("<p class='table-del'>删除后将无法再查看订单信息,请谨慎!</p>");
							},
			    			buttons:{
			    				"确定":function(){
			    					$( this ).dialog( "destroy" );
			    					ajaxRequest.deletePermOrdersBySaler($.param(param),function(response){
						    			if(response.retCode==200){
						    				tablePoint.tablePoint("永久删除订单成功！",function(){
					    					  $($this).parents(".orderTr").remove();
					    				   });
						    			}else{
						    				tablePoint.tablePoint("永久删除订单失败！");
						    			}
						    		});
			    				},
			    				"取消":function(){
			    					//$( "#dlg3" ).dialog("destroy");
			    					$( this ).dialog( "destroy" );
			    				}
			    			}
			    		});
			  		});
			    	//延迟收货
			    	$(".yanchiTime").unbind().on('click',function(){
			    		var $this = $(this);
			    		var yanchiTime = $(this).attr("orderDelayTime");
			    		//var orderId =$(this).parents(".orderTr").attr("orderId");
			    		var orderId = $(".yanchiTime").attr('data-oid'); //David 2016-1-13
			    		var param = "orderId="+orderId;
			  		  $( "#dlg3" ).dialog({
							title:'延迟收货',
							show: true,
							modal: true,
							open : function(){
								$(this).html("<p class='table-del'>订单收货时间将延长"+yanchiTime+"小时,确定延时?</p>");
							},
							buttons: {
								"确定": function() {
									 	$(this).dialog('destroy');
										ajaxRequest.delayDeliver(param,function(response){
							    			if(response.retCode == 200){
							    				 tablePoint.tablePoint("操作成功！",function(){
							    					 shop.queryData(queryParam);
							    				   });
							    			}else{
							    				 tablePoint.tablePoint("操作失败！");
							    			}
							    		})
								},
								"取消": function() {
								    $( this ).dialog( "destroy" );
								}
							}
						});
			    	})
			    	
			    	//确定已经支付
			    	$(".confirmPay").unbind().on('click',function(){
			    		  var orderId =$(this).parents(".orderTr").attr("orderId");
			    		  var userId =$(this).parents(".orderTr").attr("userId");
			    		  var param = {"userId":userId,"orderId":orderId};
			    		  
			    		  $( "#dlg3" ).dialog({
								title:'确定支付',
								show: true,
								modal: true,
								open : function(){
									$(this).html("<p class='table-del'>确定已经支付?</p>");
								},
								close: function() { 
							        $(this).dialog('destroy');
								},
								buttons: {
									"确定": function() {
										 	$(this).dialog('destroy');
										  ajaxRequest.confirmPayBySaler(param,function(response){
							    			   if(response.retCode=="200"){
							    				   tablePoint.tablePoint("操作成功！",function(){
							    					   shop.queryData(queryParam);
							    				   });
							    			   }else if(response.retCode=="782"){
							    				   tablePoint.tablePoint("订单支付处理中！",function(){
							    					   shop.queryData(queryParam);
							    				   });
							    			   }else if(response.retCode=="781"){
							    				   tablePoint.tablePoint("订单冲正处理中！",function(){
							    					   shop.queryData(queryParam);
							    				   });
							    			   }else if(response.retCode=="791"){
							    				   tablePoint.tablePoint("订单已支付！",function(){
							    					   shop.queryData(queryParam);
							    				   });
							    			   }else if(response.retCode=="590"){
							    				   tablePoint.tablePoint("积分预付款余额不足！",function(){
							    					   shop.queryData(queryParam);
							    				   });
							    			   }else if(response.retCode=="589"){
							    				   tablePoint.tablePoint("企业流通账款账户余额不能为负数！",function(){
							    					   shop.queryData(queryParam);
							    				   });
							    			   }else{
							    				   tablePoint.tablePoint("操作失败！");
							    			   }
							    		  })
									},
									"取消": function() {
									    $( this ).dialog( "destroy" );
									}
								}
							});
			    	});
			    	//取消订单
			    	$(".cancelPay").unbind().on('click',function(){
			    		  var param = {};
			    		  param["orderId"] =$(this).parents(".orderTr").attr("orderId");
			    		  param["userId"] =$(this).parents(".orderTr").attr("userId");
			    		  $( "#dlg3" ).dialog({
								title:'取消订单',
								show: true,
								modal: true,
								open : function(){
									$(this).html("<p class='table-del'>确定取消订单?</p>");
								},
								close: function() { 
							        $(this).dialog('destroy');
								},
								buttons: {
									"确定": function() {
										 	$(this).dialog('destroy');
										 	  ajaxRequest.cancelOrderBySaler(param,function(response){
								    			   if(response.retCode=="200"){
								    				   tablePoint.tablePoint("操作成功！",function(){
								    					   shop.queryData(queryParam);
								    				   });
								    			   }else{
								    				   tablePoint.tablePoint("操作失败！");
								    			   }
								    		  })
									},
									"取消": function() {
									    $( this ).dialog( "destroy" );
									}
								}
							});
			    	});
			    	
			     	//确定备货
			    	$(".stocking").unbind().on('click',function(){
			    		  var param = {};
			    		  param["orderId"] =$(this).parents(".orderTr").attr("orderId");
			    		  param["userId"] =$(this).parents(".orderTr").attr("userId");
			    		  $( "#dlg3" ).dialog({
								title:'确定备货',
								show: true,
								modal: true,
								open : function(){
									$(this).html("<p class='table-del'>确定备货?</p>");
								},
								buttons: {
									"确定": function() {
										 	  $(this).dialog('destroy');
										 	  ajaxRequest.salerOrderStocking(param,function(response){
								    			   if(response.retCode=="200"){
								    				   tablePoint.tablePoint("操作成功！",function(){
								    					   shop.queryData(queryParam);
								    				   });
								    			   }else{
								    				   tablePoint.tablePoint("操作失败！");
								    			   }
								    		  })
									},
									"取消": function() {
									    $( this ).dialog( "destroy" );
									}
								}
							});
			    	});
			     	//确定取消订单
			    	$(".yesOrder").unbind().on('click',function(){
			    		  var param = {};
			    		  param["orderId"] =$(this).parents(".orderTr").attr("orderId");
			    		  param["userId"] =$(this).parents(".orderTr").attr("userId");
			    		  $( "#dlg3" ).dialog({
								title:'确定取消订单',
								show: true,
								modal: true,
								
								open : function(){
									$(this).html("<p class='table-del'>确定取消订单?</p>");
								},
								buttons: {
									"确定": function() {
										 	  $(this).dialog('destroy');
										 	  ajaxRequest.confirmCancelBySaler(param,function(response){
								    			   if(response.retCode=="200"){
								    				   tablePoint.tablePoint("操作成功！",function(){
								    					   shop.queryData(queryParam);
								    				   });
								    			   }else{
								    				   tablePoint.tablePoint("操作失败！");
								    			   }
								    		  })
									}
								}
							});
			    	});
			    	
			    	//确定用户自提
			     	$(".deliveryYes").unbind().on('click',function(){
			     	    var orderId =$(this).parents(".orderTr").attr("orderId");
		    		    var userId =$(this).parents(".orderTr").attr("userId");
		    		    var shopId =$(this).parents(".orderTr").attr("shopId");
			     		var takeCode = $(this).attr("takeCode");
			     		var param = {};
			     		param["orderId"] = orderId;
			     		param["userId"] = userId;
			     		param["shopId"] = shopId;
			     		param["takeCode"] = takeCode;
			    		  $( "#dlg4" ).dialog({
								title:'自提确认',
								show: true,
								modal: true,
								open : function(){
									$(this).html("<p class='table-del'>确认该订单已自提?</p>");
								},
								buttons: {
									"确定": function(){
										ajaxRequest.takeToOfflineStore(param,function(response){
											if(response.retCode=="200"){
												tablePoint.tablePoint("确认成功!",function(){
													$("#dlg4").dialog('destroy');
													shop.queryData(queryParam);
												});
											}else{
												tablePoint.tablePoint("确认失败!");
											}
										})
									},
									"取消": function(){
										 $(this).dialog('destroy');
									}
								}
							});
			    	});
			     	
			    	//发货弹出窗口
			    	  $(".deliverItem").unbind().on('click',function(){
			    		  var orderId =$(this).parents(".orderTr").attr("orderId");
			    		  var userId =$(this).parents(".orderTr").attr("userId");
			    		  var param = {"orderId":orderId,"userId":userId};
						ajaxRequest.getLogisticList(param,function(response){
	        				var html = _.template(deliverTpl, response.data);
	        				$('#dlg2').html(html);
	        				$("#dlg2").dialog({
								title : "快递公司选择",
								width : "600",
                                                                height:"300",
								modal : true,
								close: function() { 
								        $(this).dialog('destroy');
								},
								buttons : {
									'确定' : function() {
										    var logisticId = $("#logisticalSelect option:selected").val();
										    var logisticName=$("#logisticalSelect option:selected").text();
										    var logisticCode=$.trim($("#logisticCode").val());
										    var logisticNode=$("#logisticNode").val();
										   if(""== logisticId){
											   tablePoint.tablePoint("请先选择快递公司！")
											   return false;
										   }else if(logisticCode.length > 19 || logisticCode.length < 1){
											   tablePoint.tablePoint("快递单号不能为空,长度小于19位！")
											   return false;
										   }else if(logisticNode.length > 50){
											   tablePoint.tablePoint("备注长度不能超过50个字符！")
											   return false;
										   }else{
												shop.confirmLogisticData(orderId,userId,logisticId,logisticName,logisticCode,logisticNode);
												 $(this).dialog("destroy"); 
										   }
									},
									'关闭' : function() {
										$(this).dialog("destroy");
									}
								}
							});
			        	});
					});
		    	  //送货弹出窗口
		    	  $(".sendItem").unbind().on('click',function(){
		    		    var orderId =$(this).parents(".orderTr").attr("orderId");
		    		    var userId =$(this).parents(".orderTr").attr("userId");
		    		    var shopId =$(this).parents(".orderTr").attr("shopId");
		    		    var param = {}
		    		    //实体店Id
		    		    param["shopId"]=shopId;
		    		    ajaxRequest.querySalerShopDeliver(param,function(response){
			    		    	var html = _.template(sendTpl, response);
								$('#dlg2').html(html);
								//绑定选择送货人事件
			    		    	$("#deliverManSel").change(function(){
			    		    		$("#deliverContact").val($("#deliverManSel option:selected").attr("phone"));
			    		    	});
								$("#dlg2").dialog({
									title : "送货人选择",//display: block; width: 546px; min-height: 50px; max-height: none; height: 227px;
									width : 550,
									height:300,
									modal : true,
									close: function() { 
								        $(this).dialog('destroy');
									},
									buttons : {
										'确定' : function() {
											 var deliveryId=$("#deliverManSel option:selected").val();
										     var deliverName=$("#deliverManSel option:selected").text();
										     var deliverContact=$("#deliverContact").val();
										     var deliverNode=$("#deliverNode").val();
										     
										     if(""== deliveryId){
												   tablePoint.tablePoint("请先选择送货人！")
												   return false;
											   }else if(deliverNode.length > 50){
												   tablePoint.tablePoint("备注长度不能超过50个字符！")
												   return false;
											   }else{
												   shop.confirmDeliverManData(orderId, userId, deliveryId, deliverName, deliverContact, deliverNode)
												   $(this).dialog("destroy");
											   }
										},
										'关闭' : function() {
											$(this).dialog("destroy");
										}
									}
								});
		    		    })
					});
		    	  
		    	//修改运费弹出窗口
		    	  $(".fixDeliverFee").unbind().on('click',function(){
		    		  	var re = /^\d+$/;
		    		    var orderId =$(this).parents(".orderTr").attr("orderId");
		    		    var userId =$(this).parents(".orderTr").attr("userId");
		    		    var oldPostage=$(this).attr("oldPostage")
			    		    	var html = _.template(fixFeeTpl, null);
								$('#dlg2').html(html);
								//设置旧运费
								$("#OldPostage").val(comm.formatMoneyNumber(oldPostage));
								$("#dlg2").dialog({
									title : "修改运费",
									width : "600",
									modal : true,
									close: function() { 
								        $(this).dialog('destroy');
									},
									buttons : {
										'确定' : function() {
											var postage = $("#dlg2 .newPostage").val();
											if(!re.test(postage) || postage > 999999 || postage < 0){
												tablePoint.tablePoint("请输入数字,范围0~999999");
												return;
											}
											var param={"userId":userId,"orderId":orderId,"postage":postage};
											ajaxRequest.changePostageBySaler(param,function(response){
												if(response.retCode=="200"){
													tablePoint.tablePoint("修改成功！",function(){
														shop.queryData(queryParam);
													});
												}else{
													tablePoint.tablePoint("修改失败！");
												}
											})
											//情况页面的值
											$(this).dialog("destroy");
											
										},
										'关闭' : function() {
											$(this).dialog("destroy");
										}
									}
								});
					});
		    	  
		    	  	//打印送货单
		    	    $(".printSendOrder").unbind().on('click',function(){
		    	    	   var orderId =$(this).parents(".orderTr").attr("orderId");
			    		   var userId =$(this).parents(".orderTr").attr("userId");
		    	    	   var param = "userId="+userId+"&orderId="+orderId;
		    			 
						   ajaxRequest.queryOrderInfo(param,function(response){
							   if(response.retCode == 200){
					    			//填充打印单信息
					    		   var printHtml = _.template(printOrderTpl, response.data);	
					    		   //打印指定的内容    		    	    	 
				    	    	   $('#wrap').parent().append("<div id='wrapPrint'></div>")
				    	    	   $('#wrapPrint').html(printHtml);
				    	    	   $('#wrap').addClass("noprint");		    	 
				    	    		//设置网页打印的页眉页脚为空		    	    		
				    	    		try{
				    	    			var hkey_root,hkey_path,hkey_key;
				    	    			hkey_root="HKEY_CURRENT_USER";
				    	    			hkey_path="SoftwareMicrosoftInternet ExplorerPageSetup";
				    	    			var RegWsh = new ActiveXObject("WScript.Shell");
				    	    			hkey_key="header" ;
				    	    			RegWsh.RegWrite(hkey_root+hkey_path+hkey_key,"");
				    	    			hkey_key="footer";
				    	    			RegWsh.RegWrite(hkey_root+hkey_path+hkey_key,"");
				    	    		}catch(e){
				    	    			var a='';
				    	    		}		    	    		
				    	    	   window.print(); 
				    	    	   $('#wrap').removeClass("noprint");
				    	    	   $("#wrapPrint").remove();
							   }
						   });
		    	   });	   
			    },
			    bindShowTime:function(time,timeStart,k){
			        function showTime(time,timeStart,k)  
			        {
			        	if(!$("#orderGridMain")){//#tableTimeClock
			        		 $.each(idclear,function(k,v){
									clearTimeout(v);
							  })
						      idclear.length = 0;
			        	      return false;
			        	}
			        	var timeStart = timeStart + 1000; //设定当前时间
				  		var time_end = time; //设定目标时间
				  		// 计算时间差 
				  		var time_distance = time_end - timeStart; 
				  		// 天
				  		var int_day = Math.floor(time_distance/86400000) 
				  		time_distance -= int_day * 86400000; 
				  		// 时
				  		var int_hour = Math.floor(time_distance/3600000) 
				  		time_distance -= int_hour * 3600000; 
				  		// 分
				  		var int_minute = Math.floor(time_distance/60000) 
				  		time_distance -= int_minute * 60000; 
				  		// 秒 
				  		var int_second = Math.floor(time_distance/1000) 
				  		if(int_day > -1){
				  			// 时分秒为单数时、前面加零 
					  		if(int_day < 10){ 
					  			int_day = "0" + int_day; 
					  		} 
					  		if(int_hour < 10){ 
					  			int_hour = "0" + int_hour; 
					  		} 
					  		if(int_minute < 10){ 
					  			int_minute = "0" + int_minute; 
					  		} 
					  		if(int_second < 10){
					  			int_second = "0" + int_second; 
					  		} 
				  		}else{
				  			int_day = "--";
				  			int_hour = "--";
				  			int_minute = "--";
				  			int_second = "--";
				  		}
				  		// 显示时间 
				  		$("#time"+k+" .time_d").html(int_day); 
				  		$("#time"+k+" .time_h").html(int_hour); 
				  		$("#time"+k+" .time_m").html(int_minute); 
				  		$("#time"+k+" .time_s").html(int_second);
				  		idclear.push(setTimeoutMy(showTime,1000,time,timeStart,k));
				  		 if(idclear.length >= 40){
							 $.each(idclear,function(k,v){
								  if(k < 20){
									  clearTimeout(v);
								  }
						      })
						      for(var k = 0;k < 20;k++){
						    	  //idclear.remove(0);
						      		idclear.splice(k,1);
						      }
				  		 }
			        }  
			        showTime(time,timeStart,k);
			    },
			    setTimeoutUpdate : function(){
				      $.each(idclear,function(k,v){
							clearTimeout(v);
					  })
				      idclear.length = 0;
			    	　    var __sto = setTimeout;  
			    	　　window.setTimeoutMy = function(callback,timeout,param)  
			    	　　{  
			    	　　var args = Array.prototype.slice.call(arguments,2);  
			    	　　var _cb = function()  
			    	　　{  
			    	　　callback.apply(null,args);  
			    	　　}  
			    	　　   var id = __sto(_cb,timeout);
			    	   if(id != null){
			    		   return id;
			    	   }
			    	　　} 
			    }
	    }
		return shop;
});
