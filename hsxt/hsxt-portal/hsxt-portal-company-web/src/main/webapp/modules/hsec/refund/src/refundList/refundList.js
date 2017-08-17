/**
 * 售后服务js脚本 
 * modifly by zhanghh 2016-02-03 
 * desc:修改了弹出框的关闭小图标，调整相关关闭的事件
 */
define([ "text!hsec_refundTpl/refundList/tab.html"
         ,"text!hsec_refundTpl/refundList/refundConditions/refundConditions.html"
         ,"hsec_refundDat/refund"
         ,"text!hsec_refundTpl/refundList/refundDialog/dialogRefundHH.html"
         ,"text!hsec_refundTpl/refundList/refundDialog/dialogRefundInfo.html"
         ,"hsec_tablePointSrc/tablePoint"
         ,"text!hsec_refundTpl/refundList/gridMain.html"], function(tpl,tpl2,ajaxRequest,tpl4,tpl5,tablePoint,gridMainTpl) {
	var queryParam = {"firstRecordIndex":1};
	var shop = {
		        _flag:false,
				//查询条件
				queryData : function(status, orderId, nickName, currentPageIndex) {
					queryParam = {};
					queryParam["refStatus"] = status;//$("#status").attr("optionvalue");
					queryParam["orderId"] = orderId;
					queryParam["nickName"] = nickName;
					queryParam["firstRecordIndex"] = currentPageIndex;
					queryParam["eachPageSize"] = 10;
					return queryParam;
				},
				bindData : function() {
					// 读取集合
						$("#busibox").html(_.template(gridMainTpl));
						var shopCounterOptionHtml="";
						/*状态下拉列表*/
						$("#status").selectList({
							options:[
								{name:'全部',value:''},
								{name:'未处理',value:'0'},
								{name:'不同意',value:'1'},
								{name:'上门取货',value:'2'},
								{name:'待买家发货',value:'3'},
								{name:'收货确认',value:'4'},
								{name:'退款中',value:'5'},
								{name:'退/换货结束',value:'6'},
								{name:'退款失败',value:'7'},
								{name:'待发货/换货',value:'8'},
								{name:'待确认收货/换货',value:'9'},
								{name:'待送货上门/换货',value:'10'}
							]	
						});
						shop.pageLabelClick();
						shop.loadBsGridData(shop.queryData("0","","",1));
						$("#selectStatus").hide();
			},
			loadBsGridData : function(queryParam){
				var gridObj = $.fn.bsgrid.init('refundGridMain',{
					url : {url:comm.UrlList["getRefundByUserId"],domain:"sportal"},//comm.domainList["sportal"]+comm.UrlList["getRefundByUserId"],
					otherParames:$.param(queryParam),
					stripeRows:true,
					pageSize:10,
					displayBlankRows:false,
					operate : {
						add : function(record, rowIndex, colIndex, options){
							var data = gridObj.getRecord(rowIndex);
							if(colIndex == 0){
								return rowIndex+1;
							}
							if(colIndex == 1){
								return data.ordId;
							}
							if(colIndex == 2){
								return data.shopName;
							}
							if(colIndex == 3){
								return data.nickName!=null && data.nickName!="" ? data.nickName:data.buyerAccountNo;
							}
							if(colIndex == 4){
								sTpl = "";
								if(data.refundType=="1"){
                                    sTpl = '退款退货';
			                     }else if(data.refundType=="2"){
			                        sTpl = '仅退款';
			                     }else{
			                        sTpl = '换货';
			                     }
								return sTpl;
							}
							if(colIndex == 5){
								return '<span class="fb red">'+comm.formatMoneyNumber(data.price)+'</span>';
							}
							if(colIndex == 6){
								return '<span class="fb blue">'+comm.formatMoneyNumber(data.points)+'</span>';
							}
							if(colIndex == 7){
								return data.createTime;
							}
							if(colIndex == 8){
								return '<span class="refundStatusRow">'+data.statusName+'</span>';
							}
							if(colIndex == 9){
								sTpl = "";
								sTpl = ' <a class="checkRefundInfo mr10" oId="'+data.ordId+'" rId="'+data.refundId+'" uId="'+data.userId+'">查看详情</a>';
								if(data.status==0 || data.status==4 || data.status==8){
								sTpl += ' <a class="afterDeal mr10" oId="'+data.ordId+'" rId="'+data.refundId+'" uId="'+data.userId+'">处理申请</a>';
								}
								return sTpl;
							}
						}
					},
					//Ajax请求完成后执行方法, 参数: options, XMLHttpRequest, textStatus
					complete:function(op, xhr, ts){
						shop.bindClick();
					}
				});
			},
			bindClick : function() { 
				//绑定查看详情对话框
				  $(".checkRefundInfo").unbind().on('click',function(){
					    var oId = $(this).attr("oId");
					    var rId = $(this).attr("rId");
					    var uId = $(this).attr("uId");
					    var param = "refId="+rId+"&userId="+uId+"&orderId="+oId;
					    ajaxRequest.getRefundDetail(param, function(response){
						var html = _.template(tpl5, response);
						$('#refundInfoAll').html(html);	
						//var gridInfoObj = $.fn.bsgrid.init('searchTable',{});
						$('#shlb').hide();	
						 
						$(".tr_img").hover(
								function(){$(this).find('.shfw_arrow_left, .shfw_arrow_right').stop().fadeIn(100)},
								function(){$(this).find('.shfw_arrow_left, .shfw_arrow_right').stop().fadeOut(100)}
								)
							 
								function viewImg(){
									var n=1;
									
								$(".shfw_arrow_right").on('click',function(){
									
									var imgWarp=$(this).siblings(".td_img").children(".inner_img"),
									imgNum=imgWarp.children("img").size(),
									visibleNum=parseInt($(this).siblings(".td_img").width()/73);
										imgWarp.width(imgNum*77+'px');
										n=n<=imgNum-visibleNum?n:n-1;
										imgWarp.animate({left:-77*n+"px"},100);
										n+=1;
										})	
									$(".shfw_arrow_left").on('click',function(){
										var imgWarp=$(this).siblings(".td_img").children(".inner_img")
										n=n==1?2:n;
										imgWarp.animate({left:-77*(n-2)+"px"},100);
										n-=1;
								
										})
									}
									viewImg();

								  shop.status();
								  
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

									/*图片点击放大结束*/
									$("#returnRefund").on("click",function(){
										$('#refundInfoAll').html("");	
										$('#shlb').show();	
									})
					   });  
					});
				  
				//处理申请
				  $(".afterDeal").unbind().on('click',function(){
				  	
				  	 comm.getRandomToken(function(r){
				  	 	if (r && r.retCode == '22000') {
				  	 	     $('#ref_vdt_token').val(r.data);
				  	 	}
				  	 });
				  	
					 var oId = $(this).attr("oId");
					 var rId = $(this).attr("rId");
					 var uId = $(this).attr("uId");
					 var param = "refId="+rId+"&userId="+uId+"&orderId="+oId;
					 ajaxRequest.getRefundDetail(param, function(response){
						 ajaxRequest.listSysLogistic(null,function(response2){
							 if(null != response2.data){
								 response["logistic"] = response2.data;
							 }else{
								 response["logistic"] = "";
							 }
						 var html = _.template(tpl4, response);
						$('#dlg2').html(html);
						shop.status($('#dlg2'));
						$("#dlg2").dialog({
							title : "处理申请",
							width : "600",
							modal : true,
							closeIcon :true,
							open:function(){
								$(".ui-dialog-titlebar-close").click(function(){
									$("#dlg2").dialog("destroy");
								});
							}
							/*,
							close: function() { 
						        $(this).dialog('destroy');
						   }*/
						});
					});
				});
				
				
				
			});
			},
			status : function(obj){
				function checkPhone(str){
					var boolCheck = false;
				    var re = /^0\d{2,3}-?\d{7,8}$/;
					    if(re.test(str)){
					    	boolCheck = true;
					    }
				    re = /^1\d{10}$/;
				        if (re.test(str)) {
				        	boolCheck = true;
				        }
				        return boolCheck;
				}
			    $(".status").unbind().on('click',function(){
			    	var $this = $(this);
	    	    	var param = {};
	    	    	param["refId"] = $.trim($("#refId").val());
	    	    	param["userId"] = $.trim($("#userId").val());
	    	    	param["orderId"] =$.trim($("#orderIds").val());
	    	    	param["refundType"] = $.trim($("#refundType").attr("refundType"));
	    	    	param["status"] = $.trim($(this).attr("status"));
	    	    	param["updateStatus"] = $.trim($(this).attr("updateStatus"));
	    	    	
	    	    	if($(this).attr("status")=="8"&&($("#logisticName").val() == null || $.trim($("#logisticName").val()).length == 0)){
	    	    		tablePoint.tablePoint("请选择快递公司!");
	    	    		return false;
	    	    	}else{
	    	    		param["logisticId"] = $("#logisticName").val()!=null?$("#logisticName").val():'';
	    	    		param["logisticName"] = $("#logisticName").find("option:selected").text();
	    	    	}
	    	    	if($(this).attr("status")=="8"&&($("#logId").val().length > 19 || $.trim($("#logId").val()).length == 0)){
	    	    		tablePoint.tablePoint("快递单号不能为空,字符长度不超过19个字符,请重新填写!");
	    	    		return false;
	    	    	}else{
	    	    		param["logId"] = $("#logId").val()!=null?$("#logId").val():'';
	    	    	}
	    	    	
	    	    	if($(this).attr("status")=="8" && $("#salerNote").val().length > 100){
	    	    		tablePoint.tablePoint("备注字符长度填写超过100个字符,请重新填写!");
	    	    		return false;
	    	    	}else{
	    	    		param["salerNote"] = $("#salerNote").val()!=null?$("#salerNote").val():'';
	    	    	}
	    	    	
                   if($(this).attr("updateStatus")== 0 &&($("#refundType").attr("refundType")=='1'||$("#refundType").attr("refundType")=='3')){
                	   if($("#salerAddress").val().length > 150 || $.trim($("#salerAddress").val()).length == 0){
	   	    	    		tablePoint.tablePoint("请重新填写!收货地址字符长度填写范围1~150个字符。");
	   	    	    		return false;
   	    	    		}
                	   if($("#salerContact").val().length > 20 || $.trim($("#salerContact").val()).length == 0 || checkPhone($.trim($("#salerContact").val())) == false){
	   	    	    		tablePoint.tablePoint("请重新填写!联系方式可以为手机/座机,例如:13800000000,010-88888888.");
	   	    	    		return false;
  	    	    		}
                	   if($("#salerContactor").val().length > 30 || $.trim($("#salerContactor").val()).length == 0){
	   	    	    		tablePoint.tablePoint("请重新填写!联系人字符长度填写范围1~30个字符。");
	   	    	    		return false;
  	    	    		}
                	   var re = /^[1-9][0-9]{5}$/;
                	   if($("#salerPostCode").val().length > 10 || $.trim($("#salerPostCode").val()).length == 0 || re.test($.trim($("#salerPostCode").val())) == false){
	   	    	    		tablePoint.tablePoint("请填写正确的邮政编码。例：518000。");
	   	    	    		return false;
  	    	    		}
                	   
                	    param["salerAddress"]=$("#salerAddress").val()!=null?$("#salerAddress").val():'';
                        param["salerContact"]=$("#salerContact").val()!=null?$("#salerContact").val():'';
                        param["salerContactor"]=$("#salerContactor").val()!=null?$("#salerContactor").val():'';
                        param["salerPostCode"]=$("#salerPostCode").val()!=null?$("#salerPostCode").val():'';
	    	    	}
                    
	    	    	var paramSubmit = {};
	    	    	if($(this).attr("updateStatus") == 1){
	    	    		$("#dlg4").dialog({
							title : "售后拒绝",
							width : "400",
							modal : true,
//							closeIcon:true,
							open : function(){
								$("#noRefund").val("");
//								$(".ui-button-icon-only").click(function(){
//									$("#dlg4").dialog("destroy");
//								});
							},
							buttons:{ 
								"确定" : function(){ 
										if($("#noRefund").val().length > 140){
											tablePoint.tablePoint("拒绝理由长度不能超过140个字符!请修改后确认!");
											return false;
										}
										param["noRefund"] = $("#noRefund").val();
										paramSubmit["refund"] = JSON.stringify(param);
										//2016-04-01  bug19319
										if (  $('#dlg2').hasClass('ui-dialog-content')  ){
											$(obj).dialog('destroy');
										}; 
										tablePoint.bindJiazai("#dlg2",true);
										ajaxRequest.salerStatus($.param(paramSubmit), function(response){
											tablePoint.bindJiazai("#dlg2",false);
						    	    		if(response.retCode == 200){
						    	    			tablePoint.tablePoint("操作成功!",function(){ 
						    	    				if (  $('#dlg2').hasClass('ui-dialog-content')  ){
														$(obj).dialog('destroy');
													}; 
													if (  $('#dlg4').hasClass('ui-dialog-content')  ){
														$('#dlg4').dialog('destroy');
													}; 
						    	    				$('#refundInfoAll').html("");	
													$('#shlb').show();
													$("#queryRefundList").click();
						    	    			});
						    	    		}else if(response.retCode == 590){
						    	    			tablePoint.tablePoint("消费者积分余额不足，操作失败！");
						    	    		}else if(response.retCode == 589){
						    	    			tablePoint.tablePoint("退款失败，企业互生币余额不足!");
						    	    		}else{
						    	    			tablePoint.tablePoint("操作失败!",function(){
						    	    				if (  $('#dlg2').hasClass('ui-dialog-content')  ){
														$(obj).dialog('destroy');
													}; 
						    	    			});
						    	    		}
						    	    	});
						    	    	if (  $('#dlg4').hasClass('ui-dialog-content')  ){
											$('#dlg4').dialog('destroy');
										}; 
								},
								"取消" : function(){
									$(this).dialog('destroy');
									if (  $('#dlg2').hasClass('ui-dialog-content')  ){
											$(obj).dialog('destroy');
									}; 
								}
						   }
						});
	    	    	}else{
	    	    		if(!shop._flag){
	    	    			shop._flag = true;
	    	    		
	    	    			var _token = $('#ref_vdt_token').val();
	    	    			if(!_token){
	    	    				comm.getRandomToken(function(r){
	    	    					shop._flag = false;
							  	 	if (r && r.retCode == '22000') {
							  	 	     $('#ref_vdt_token').val(r.data);
							  	 	}
							  	 });
	    	    			    return;
	    	    			}
	    	    			
	    	    		    $this.addClass("btn-search ml10").removeClass("status");//按钮置灰
	    	    		    param['ref_token'] = _token;
		    	    		paramSubmit["refund"] = JSON.stringify(param);
		    	    		tablePoint.bindJiazai("#dlg2",true);//添加遮罩层 add by zhanghh:2016-06-01
		    	    		
		    	    		ajaxRequest.salerStatus($.param(paramSubmit), function(response){
		    	    			shop._flag = false;
			    	    		tablePoint.bindJiazai("#dlg2",false);//添加遮罩层
			    	    		if(response.retCode == 200){
			    	    			tablePoint.tablePoint("操作成功!",function(){
			    	    				$(obj).dialog("destroy");
			    	    				$('#refundInfoAll').html("");	
										$('#shlb').show();
										$("#queryRefundList").click();
										$this.removeClass("btn-search ml10").addClass("status");
			    	    			});
			    	    		}else if(response.retCode == 590){
			    	    			$this.removeClass("btn-search ml10").addClass("status");
			    	    			tablePoint.tablePoint("消费者积分余额不足，操作失败！");
			    	    		}else if(response.retCode == 589){
			    	    			$this.removeClass("btn-search ml10").addClass("status");
			    	    			tablePoint.tablePoint("退款失败，企业互生币余额不足!");
			    	    		}else if(response.retCode == 220){
			    	    			$this.removeClass("btn-search ml10").addClass("status");
			    	    			tablePoint.tablePoint("请不要重复提交");
			    	    		}else if(response.retCode == 208){
			    	    		   tablePoint.tablePoint("请不要重复提交!",function(){
			    	    		   	    comm.getRandomToken(function(r){
									  	 	if (r && r.retCode == '22000') {
									  	 	     $('#ref_vdt_token').val(r.data);
									  	 	}
									  	 });
			    	    				$(obj).dialog('destroy');
			    	    				$this.removeClass("btn-search ml10").addClass("status");
			    	    			});
			    	    		}else{
			    	    			tablePoint.tablePoint("操作失败!",function(){
			    	    				$(obj).dialog('destroy');
			    	    			});
			    	    			$this.removeClass("btn-search ml10").addClass("status");
			    	    		}
			    	    	});
			    	    	
	    	    		}
		    	    	
		    	    	
		    	    	
		    	    	
		    	    	
	    	    	}
			    });
			},
			//绑定页头标签事件
			pageLabelClick : function() {
				   //售后状态事件
				  $(".refundTypes").unbind().on('click',function(){
						 //样式变化 
					    $("li[name='pageLabel']").each(function(){
					    	$(this).removeClass("cur"); 
						 })
						 	$(this).addClass("cur");
					    
						 $("#orderId").val("");
                         $("#nickName").val("");
						 //设置需要查询的状态
                         var type = $(this).attr("litype");
                        shop.loadBsGridData(shop.queryData(type,"","",1));
					    $("#queryRefundList").attr("values",type);
					    if(type == ""){
						    $("#selectStatus").show();
						    $("#status option:first").attr('selected','selected');
							  //状态下来框选择
							  $("#status").on('change',function(){
								  shop.loadBsGridData(shop.queryData($(this).attr("optionvalue"),$("#orderId").val(),$("#nickName").val(),1));
							  });
					    }else{
					    	$("#selectStatus").hide();
					    }
					});
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
				  //查询事件
				  $("#queryRefundList").unbind().on('click',function(){
					  var type;
					  if($("#selectStatus").is(":hidden")){
						  type = $("#queryRefundList").attr("values");     
						}else{
						  type = $("#status").attr("optionvalue");    
						}
					 shop.loadBsGridData(shop.queryData(type,$.trim($("#orderId").val()),$.trim($("#nickName").val()),1)); 
				  });	
			}			
		}
	return shop;
});

