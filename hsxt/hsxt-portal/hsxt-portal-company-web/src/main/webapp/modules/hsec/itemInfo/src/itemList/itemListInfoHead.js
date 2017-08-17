/***
 * 零售JS
 */
define([ "hsec_itemInfoDat/itemList"
         ,"text!hsec_itemInfoTpl/itemList/itemListHead.html"
         ,"text!hsec_itemInfoTpl/itemList/name.html"
         ,"hsec_tablePointSrc/tablePoint"
         ], function(ajaxRequest,tpl,nameTpl,tablePoint) {
	
	//刷新查询方法
	function refresh(){
		shop.queryParam()
		shop.bindTable(objParam);
	}
	var objParam={firstRecordIndex:1,eachPageSize:10};
	var param;
	var typeState = "";//状态查询
	var shop = {
			selectList : function(){
				var shopCounterOptionHtml="";
				ajaxRequest.getStartSalerShop({},function(response) {
					//console.info(response);
					if (response.retCode == "200") {
						var opts = new Array();
						opts[0] = {name:"全部",value:""};
						$.each(response.data,function(key,d){
							opts[key+1] = {name:d.addr,value:d.id}
						});
						$("#shopInfo").selectList({
							options:opts,
							width:115,
							optionWidth:200,
							optionHeight:220
						})
						//add by zhucy 2016-03-30 营业点添加 title提示
						.next().find('li:gt(0)').each(function(){
							var _this = $(this);
							_this.attr('title',_this.text());
						});
						
						//add by liaoc, 20160422, 需求变更，添加日期筛选
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
					}
				})
			},
			queryParam : function(){
				objParam["id"]=$.trim($("#itemId").val());
				
				objParam["status"]=$("#selItemStatus  option:selected").val();
				
			    objParam["name"]=$.trim($("#itemName").val());
				objParam["code"]=$.trim($("#itemCode").val());
				objParam["brandName"]=$.trim($("#itemBrand").val());
				if($("#shopInfo").attr("optionvalue") != "" && $("#shopInfo").attr("optionvalue") != null){
					var shopInfoList = new Array();
					shopInfoList.push($("#shopInfo").attr("optionvalue"));
					objParam["listShopIds"] = shopInfoList;
				}else{
					objParam["listShopIds"] = "";
				}
				if($("#rexiao").is(":checked")  != false){
					objParam["hotSell"] = $("#rexiao").val();
				}else{
					objParam["hotSell"] = "";
				}
				//add by liaoc, 20160422, 添加上架时间的查询条件
				objParam["putonTimeStartStr"] = $.trim($("#startTime").val());
				objParam["putonTimeEndStr"] = $.trim($("#endTime").val());
				objParam["firstRecordIndex"] = 1;
				objParam["eachPageSize"] = 10;
			},
			bindData : function() {
				ajaxRequest.getStartSalerShop(null, function(response) {
					//查询商城商品分类
					var html = _.template(tpl, response.data);
					$('#busibox').html(html);
					//查询商品列表
					shop.bindDataClick();
					shop.queryParam();
					shop.bindTable(objParam);
					shop.bindClick();
					shop.selectList();
				})
			},
			bindDataClick : function(){
				$("#shopInfo").off().on("change",function(){
			    	shop.bindTable(shop.queryParam());
			    	$(this).attr("optionvalue", $(this).siblings().find("li:selected").attr("data-value"));//$(this).find("option:selected").text()
				})
				
				//热销搜索
				$("#rexiao").off().on('click',function(){
					shop.queryParam()
			    	shop.bindTable(objParam);
				});
				//编号输入验证
				$("#itemId").on('focus',function(){
					$(this).on('keyup',function(){
						var num = $(this).val();
						var p1=/^[\d]+$/;
						if(!p1.test(num)){
							$(this).val("");
						}
					})
				});
			},
			bindTable :function(objParam){
				ajaxRequest.hasRightEdit(null, function(responseState) {
					tablePoint.bindJiazai("#typeHide", true);
					var gridObj;
			        $(function () {
			            gridObj = $.fn.bsgrid.init('reTailItemInifoTable', {
			                url:{url:comm.UrlList["queryItemList"],domain:"sportal"},//comm.domainList["sportal"]+comm.UrlList["queryItemList"],//"/item/queryItemList",
			                pageSizeSelect: true,
			                pageSize: 10,
			                otherParames:objParam,
			                stripeRows: true,  //行色彩分隔 
							displayBlankRows: false,    //显示空白行
							rowSelectedColor : false,
							lineWrap : true,
							isProcessLockScreen : false,
							operate : {
								add: function(record, rowIndex, colIndex, options){
									var id = record.id;
									var virtualShopId = record.virtualShopId;
									var status = record.status;
									
									var str = "";
									switch (colIndex) {
										case 0:
											var html = _.template(nameTpl, record);
											str = html;
											break;
										case 2:
											str = '<span class="fb red">'+record.lowPrice.toFixed(2)+' ~ '+record.highPrice.toFixed(2)+'</span>';
											break;
										case 3:
											str = '<span class="fb blue">'+record.lowAuction.toFixed(2)+' ~ '+record.highAuction.toFixed(2)+'</span>';
											break;
										case 4:
											if(record.putonTime){
												str = record.putonTime.replace(' ','<br>');
											}
											break;	
										case 5:
											 $.each(record.lstRelShopName,function(k,v){
											 	str += "<p>"+v+"</p>";
									         });
											break;
										case 6:
											str = record.lowAuction.toFixed(2);
											break;	
										case 7:
											if(responseState.retCode!=600){
										        str += '<div class="caozuo lh24"><a class="updateItem" itemId="'+id+'">编辑</a></div>';
										        str += '<div  value="'+id+'" status="'+status+'" class="caozuo lh24 delsingleItem"><a>删除</a></div>';
										        if(status!='-5'){
										        	if(status=='0'||status=='-1'){
										        		str += '<div  value="'+id+'" status="'+status+'" class="caozuo lh24 publishItem"><a>发布</a></div>';
										        	}else if(status=='1') {
										        		str += '<div  value="'+id+'" status="'+status+'" class="caozuo lh24 cancleItem"><a>取消审核</a></div>';
										        	}else if(status=='3') {
										        		str += '<div value="'+id+'" status="'+status+'" class="caozuo lh24 putOffItem"><a>缺货</a></div>';
										        	}else{
										        		str += '<div value="'+id+'" status="'+status+'" class="caozuo lh24 putOnItem"><a>上架</a></div>';
										        	}
										        }
											}
											break;	
										default:
											break;
									}
									return str;
								}
							},
							complete : function(e,o){
								tablePoint.bindJiazai("#typeHide", false);
								var other = eval("("+o.responseText+")").orther,itemVisitUrl='';
								if (other){
									itemVisitUrl = other.itemVisitUrl;
								}
								//点名称查看详情
								$("#table1 .itemName").each(function(){
									var dh = $(this).attr("data-href");
									$(this).attr("href",itemVisitUrl+dh);
								});
								shop.bindTableClick();
								//全选事件
								$('#' + gridObj.options.pagingOutTabId + ' #all').unbind().on('click',function(){
									var chkbox = $(".myDeviler[type='checkbox']");
									$(this).prop('checked')?chkbox.prop('checked',true):chkbox.prop('checked',false);
									$("input[type='checkbox']").not($('#all')).on('click',function(){
										$('#all').prop('checked',false);
									})
								});//end 全选事件
								//设置分页的宽度  
								$('#' + gridObj.options.pagingOutTabId +' tr:eq(0) >td:eq(0)  '  ).css("width",460+"px");
								$('#' + gridObj.options.pagingOutTabId +' tr:eq(0) >td:eq(1) table'  ).css("width",550+"px");
							}
			            });
			        });
		            //自定义分页栏按钮
					var buttonHtml = '<td style="text-align: left;">';
			        buttonHtml += '<table><tr>';
			        buttonHtml += '<td><div id="" class="splb_sclb lh30"><a>全选</a><input name="" type="checkbox" value="" id="all"></div></td>';
			        buttonHtml += '<td><div id="batchDeletData" class="splb_sclb  lh30"><a href="javascript:;">批量删除</a></div></td>';
			        buttonHtml += '<td><div id="batchPublish" class="splb_sclb lh30"><a href="javascript:;">批量发布</a></div></td>';
			        buttonHtml += '<td><div id="batchPutOn" class="splb_sclb lh30"><a href="javascript:;">批量上架</a></div></td>';
			        buttonHtml += '<td><div id="batchPutOff" class="splb_sclb lh30"><a href="javascript:;">批量下架</a></div></td>';
			        buttonHtml += '</tr></table>';
			        buttonHtml += '</td>';
			        
			        $('#' + gridObj.options.pagingOutTabId + ' tr:eq(0)').prepend(buttonHtml);
	        	})
			},
			bindClick : function(){
					//新增商品
					$("#addItem").unbind().on('click',function(){
						var navObj = {};
						require(['hsec_itemCatChooseSrc/tab'],function(tab){
							tab.showPage();
						})
					});
				
					//搜索商品按钮事件
					$("#searchItem").unbind().on('click',function(){
						refresh();
					});
				
			},
			bindTableClick : function(){
				//商品状态事件
				$("#selItemStatus").unbind().on('change',function(){
					typeState = $(this).val();
					refresh();
				});
				
				//全选
				tablePoint.checkBoxAll($("#all"),$(".search-table .std_checkbox input[type='checkbox']"));
				
					//查看商品审核详情
					$(".itemStatus").unbind().on('click',function(){
						var itemId = $(this).parents("tr").attr("itemId");
						var statusInfo = $(this).parents("td").find(".statusInfo");
						if($(statusInfo).is(":hidden")){
							ajaxRequest.getItemChecks("itemId="+itemId, function(response) {
								if(response.retCode=="200"){
									$(statusInfo).show(500);
									if("" != response.data[0] && null != response.data[0]){
										$(statusInfo).val(response.data[0].remark);
									}
								}else{
									tablePoint.tablePoint("查询失败,请稍后重试!");
								}
							})
						}else{
							$(statusInfo).hide(500);
						}
					}); 
		    	   
					//单条删除记录事件
				$(".delsingleItem").unbind().on('click',function(){	
						var obj = $(this);
						var itemId = $(this).attr("value");
						var param = {"itemId" : itemId}
							 $( "#dlg1" ).dialog({
							 	  title:"提示信息",
							      modal: true,
								  close: function() { 
								        $(this).dialog('destroy');
								  },
							      buttons: {
							        确定: function() {
							        	//删除
							        	shop.bindItemState(function(retCode){
							        		if(retCode){
							        			return false;
							        		}else{
							        			ajaxRequest.deleteById(param, function(response) {
									        		$("#dlg1").dialog( "destroy" );
													if(response.retCode==200){
														tablePoint.tablePoint("删除成功！",function(){
															$(obj).parents("tr").remove();
														});
													}else{
														tablePoint.tablePoint("删除失败!请稍后重试！");
													}
												})
							        		}
							        	});
							        
							        },
							        取消: function() {
							        	$(this).dialog('destroy');
							        }
							      }
							  });
							
					});  
		    	   
				 //批量下架事件
				 $("#batchPutOff").unbind().on('click',function(){	
					 var itemIds ="";
					 var flag = false;
					  $("input[name='itemDataCheckBox']:checked").each(function(){
						  itemIds+=$(this).val()+",";
						  if($(this).attr("status")!="3"){
							  flag = true; 
						  }
					  })
					  
					  if($("input[name='itemDataCheckBox']:checked").length<=0){
						  tablePoint.tablePoint("请先选择记录！");
					  }else{
						  if(flag){
							  tablePoint.tablePoint("所选择记录中含有不能批量下架的记录！")
						  } else{
								shop.bindItemState(function(retCode){
					        		if(retCode){
					        			return false;
					        		}else{
							  
					        			var param = {"itemIds" : itemIds}
					        				tablePoint.bindJiazai("#table1", true);
										ajaxRequest.batchPutOff(param, function(response) {
											tablePoint.bindJiazai("#table1", false);
											if(response.retCode=="200"){
												tablePoint.tablePoint("批量缺货下架成功！",function(){
													shop.bindTable(objParam);
												});
											}else{
												tablePoint.tablePoint("批量缺货下架失败！请稍后重试！");
											}
										})
					        		}
								})
					     }
					  }
				});
		    	   
				 //批量上架事件
					$("#batchPutOn").unbind().on('click',function(){
						 var itemIds ="";
						 var flag = false;
						  $("input[name='itemDataCheckBox']:checked").each(function(){
							  itemIds+=$(this).val()+",";
							  if($(this).attr("status")!='-2'){
								  flag = true; 
							  }
						  })
						  
						  if($("input[name='itemDataCheckBox']:checked").length<=0){
							  tablePoint.tablePoint("请先选择记录！");
						  }else{
							  if(flag){
								  tablePoint.tablePoint("所选择记录中含有不能批量上架的记录！")
							  } else{ 
									shop.bindItemState(function(retCode){
						        		if(retCode){
						        			return false;
						        		}else{
						        			var param = {"itemIds" : itemIds}
						        				tablePoint.bindJiazai("#table1", true);
											ajaxRequest.batchPutOn(param, function(response) {
												tablePoint.bindJiazai("#table1", false);
												if(response.retCode=="200"){
													tablePoint.tablePoint("批量上架成功！",function(){
														shop.bindTable(objParam);
													});
												}else{
													tablePoint.tablePoint("批量上架失败！请稍后重试！");
												}
											})
						        		}
									})
						     }
						  }
					});
					
					 //批量发布事件
					$("#batchPublish").unbind().on('click',function(){	
						 var itemIds ="";
						 var flag = false;
						  $("input[name='itemDataCheckBox']:checked").each(function(){
							  itemIds+=$(this).val()+",";
							  if($(this).attr("status")!="0"&&$(this).attr("status")!="-1"){
								  flag = true; 
							  }
						  })
						  
						  if($("input[name='itemDataCheckBox']:checked").length<=0){
							  tablePoint.tablePoint("请先选择记录！");
						  }else{
							  if(flag){
								  tablePoint.tablePoint("所选择记录中含有不能批量发布的记录！")
							  } else{ 
									shop.bindItemState(function(retCode){
						        		if(retCode){
						        			return false;
						        		}else{
						        			var param = {"itemIds" : itemIds}
						        				tablePoint.bindJiazai("#table1", true);
											ajaxRequest.batPublishItem(param, function(response) {
												tablePoint.bindJiazai("#table1", false);
												if(response.retCode=="200"){
													tablePoint.tablePoint("批量发布商品成功！",function(){
														shop.bindTable(objParam);
													});
												}else if(response.retCode=="601"){
													tablePoint.tablePoint("所选择记录中含有未关联营业点商品，不能发布商品！");
												}else{
													tablePoint.tablePoint("批量发布商品失败！请稍后重试！");
												}
											})
						        		}
									})
						     }
						  }
					});
				 
					 //批量删除事件
					$("#batchDeletData").unbind().on('click',function(){	
						 var itemIds ="";
						  $("input[name='itemDataCheckBox']:checked").each(function(){
							  itemIds+=$(this).val()+",";
						  })
						  if($("input[name='itemDataCheckBox']:checked").length<=0){
							  tablePoint.tablePoint("请先选择记录！");
						  }else{
							  var param = {"itemIds" : itemIds}
							  
								 $( "#dlg1" ).dialog({
								      modal: true,
									  close: function() { 
									        $(this).dialog('destroy');
									  },
								      buttons: {
								        确定: function() {
								        	shop.bindItemState(function(retCode){
								        		if(retCode){
								        			return false;
								        		}else{
								        	//删除
								        			tablePoint.bindJiazai("#table1", true);
								    		ajaxRequest.batchDeleteItem(param, function(response) {
								    				tablePoint.bindJiazai("#table1", false);
								    			$("#dlg1").dialog("destroy");
												if(response.retCode=="200"){
													tablePoint.tablePoint("批量删除商品成功！",function(){
														shop.bindTable(objParam);
													});
												}else{
													tablePoint.tablePoint("批量删除失败！请稍后重试！");
												}
											})
								        }
								        	})
								        },
								        取消: function() {
								        	$(this).dialog('destroy');
								        }
								      }
								  });
							  
						  }
					});
				
					 //单条记录发布商品
					$(".publishItem").unbind().on('click',function(){	
						var itemId = $(this).attr("value");
						shop.bindItemState(function(retCode){
			        		if(retCode){
			        			return false;
			        		}else{
							var param = {"itemId" : itemId}
									ajaxRequest.publishItemId(param, function(response) {
										if(response.retCode=="200"){
											tablePoint.tablePoint("发布商品成功！",function(){
												shop.bindTable(objParam);
											});
										}else if(response.retCode=="601"){
											tablePoint.tablePoint("商品未关联营业点，不能发布商品！");
										}else{
											tablePoint.tablePoint("发布商品失败！请稍后重试！");
										}
									})
			        		}
						})
					});
						
						 //单条记录取消审核
					$(".cancleItem").unbind().on('click',function(){	
						var itemId = $(this).attr("value");
						shop.bindItemState(function(retCode){
			        		if(retCode){
			        			return false;
			        		}else{
							var param = {"itemId" : itemId }
									ajaxRequest.cancelCheckById(param, function(response) {
										if(response.retCode=="200"){
											tablePoint.tablePoint("取消审核成功！",function(){
												shop.bindTable(objParam);
											});
										}else{
											tablePoint.tablePoint("取消审核失败！请稍后重试！");
										}
									})
			        		}
						})
					});
						
						 //单条记录缺货
					 $(".putOffItem").unbind().on('click',function(){	
						 var itemIds = $(this).attr("value")+",";
								 $( "#dlg0" ).dialog({
								 	  title:'提示信息',
								      modal: true,
								      open : function(){
								    	  $(this).find("p").html("确定商品缺货吗?");
								      },
									  close: function() { 
									        $(this).dialog('destroy');
									  },
								      buttons: {
								        确定: function() {
								        	shop.bindItemState(function(retCode){
								        		if(retCode){
								        			return false;
								        		}else{
												var param = {"itemIds" : itemIds }
														ajaxRequest.batchPutOff(param, function(response) {
															$("#dlg0").dialog('destroy');
															if(response.retCode=="200"){
																tablePoint.tablePoint("缺货下架成功！",function(){
																	shop.bindTable(objParam);
																});
															}else{
																tablePoint.tablePoint("缺货下架失败！请稍后重试！");
															}
														})
								        		}
											})
								        },
								        取消: function() {
								        	$(this).dialog('destroy');
								        }
								   }
							});
					});
						
						 //单条记录上架
					 $(".putOnItem").unbind().on('click',function(){	
						var itemIds = $(this).attr("value")+",";
						shop.bindItemState(function(retCode){
			        		if(retCode){
			        			return false;
			        		}else{
			        			var param = {"itemIds" : itemIds }
									ajaxRequest.batchPutOn(param, function(response) {
										if(response.retCode=="200"){
											tablePoint.tablePoint("上架成功！",function(){
												shop.bindTable(objParam);
											});
										}else{
											tablePoint.tablePoint("上架失败！请稍后重试！");
										}
									})
			        		}
						})
					});
				//编辑事件	 
				$(".updateItem").unbind().on('click',function(){	
					var itemId = $(this).attr("itemId");
					shop.bindItemState(function(retCode){
		        		if(retCode){
		        			return false;
		        		}else{
		        			//itemUpdate.bindUpdate(itemId);
		        			var navObj = {}; 
		        			require(["hsec_itemCatChooseSrc/item/item"],function(src){	
		    					navObj.shop = src;
		    					navObj.shop.bindUpdate(itemId);	 
		    				})
		        		}
					})
				});	
			},
			bindItemState : function(callback){
				ajaxRequest.hasRightEdit(null, function(response) {
					if(response.retCode == 600){
						tablePoint.tablePoint("无权进行此操作!请刷新页面!");
						callback(true);
					}else{
						callback(false);
					}
				})
			}
	   }
		return shop;
});
