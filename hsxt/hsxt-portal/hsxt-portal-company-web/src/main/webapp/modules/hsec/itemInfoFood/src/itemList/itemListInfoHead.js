/***
 * 餐饮JS
 */
define([ "hsec_itemInfoFoodDat/itemList"
         ,"text!hsec_itemInfoFoodTpl/itemList/itemListHead.html"
         ,"text!hsec_itemInfoFoodTpl/itemList/name.html"
         ,"hsec_tablePointSrc/tablePoint"
         ,"hsec_itemInfoFoodSrc/category"
         ], function(ajaxRequest,tpl,nameTpl,tablePoint,category) {
	
	//刷新查询方法
	/*function refresh(){
		shop.queryParam(1);
		shop.bindTable();
	}*/
    var gridObj;
	var objParam={eachPageSize:10,firstRecordIndex:1};
	var param;
	var typeState = "";//状态查询
	var virtualShopId = '';
	var shop = {
			selectList : function(){
				$("#selectStatus").selectList({
					options:[
	                    {name:'全部',value:''},
						{name:'未上架',value:'0'},
						{name:'已上架',value:'3'}
					]
				});
			},
			/*queryParam : function(currentPageIndex){
				objParam = {};
				objParam["status"]=$("#selectStatus").attr("optionvalue");
				objParam["categoryId"]=$("#categoryId").val();
			    objParam["name"]=$("#itemName").val();
				objParam["code"]=$("#itemCode").val();
				objParam["brandName"]=$("#itemBrand").val();
				if($("#shopInfo").val() != "" && $("#shopInfo").val() != null){
					var shopInfoList = new Array();
					shopInfoList.push($("#shopInfo").val());
					objParam["listShopIds"] = shopInfoList;
				}
			},*/
			bindData : function() {
				//查询商城商品分类
				$('#busibox').html(_.template(tpl));
				shop.selectList();
				//查询商品列表
				shop.bindDataClick();
				shop.bindTable(objParam);
				shop.bindClick();
				$("#gArrow,#categoryName").click(function(){
					category.bindData(function(){
						//var offset = $("#categoryName").offset();
						//$(".goodsCatalogue").attr("style","left:"+offset.left+"px;top:"+(offset.top+33)+"px")
						$(".goodsCatalogue").css({ "top":"29px" ,"left":'1px'} );
					});
					
				});
		
			},
			
			//商品类目加载
			bindSelectData : function() {
				ajaxRequest.queryCategory({}, function(response) {
					$.each(response.data, function(key, val) {
                        $("#foodItemType").append('<option value="' + val.id + '">' + val.categoryName + '</option>');
                    });
				})
			},
			bindDataClick : function(){
				$("#shopInfo").release().on("change",function(){
			    	shop.bindTable(objParam);
			    	$(this).attr("title",$(this).find("option:selected").text());
				})
				//编号输入验证
				$("#itemCode").on('focus',function(){
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
						objParam.status = $("#selectStatus").attr("optionvalue");
						objParam.categoryId = $.trim($("#categoryId").val());
					    objParam.name = $.trim($("#itemName").val());
						objParam.code = $.trim($("#itemCode").val());
						objParam.brandName =$.trim($("#itemBrand").val());
						if($.trim($("#shopInfo").val()) != "" && $.trim($("#shopInfo").val()) != null){
							var shopInfoList = new Array();
							shopInfoList.push($.trim($("#shopInfo").val()));
							objParam.listShopIds = shopInfoList;
						}
						
				       // $(function () {
				            gridObj = $.fn.bsgrid.init('cyItemInfoTable', {
				                url:{url:comm.UrlList["getItemInfo"],domain:"sportal"},//comm.domainList["sportal"]+comm.UrlList["getItemInfo"],//"/food/itemInfo/getItemInfo",
				                //pageSizeSelect: true,
				                pageSize: 10,
				                otherParames:$.param(objParam),
				                stripeRows: true,  //行色彩分隔 
								displayBlankRows: false,    //显示空白行
								rowSelectedColor : false,
								lineWrap : true,
								//isProcessLockScreen : false,
								operate : {
									add: function(record, rowIndex, colIndex, options){
										var status = record.status;
										var id = record.id;
										virtualShopId = record.virtualShopId;
										var str = "";
										switch (colIndex) {
											case 0:
												str = _.template(nameTpl, record);
												break;
											case 1:
												var taste = record.taste;
												try{
													if($.trim(taste)!="" && taste!=undefined){
														taste = eval(taste);
														$.each(taste,function(key,t){
															str += t.value+" ";
														});
													}
												}catch(e){
													if($.trim(taste)!="" && taste!=undefined){
														str+=taste;
													}
												}
												break;
											case 2:
												var price = record.priceRegion;
												if(record.ishaspv == '1'){
													price = comm.formatMoneyNumber(record.price);
												}else{
													if(price == ""){
										          		price = comm.formatMoneyNumber(record.price);
										          	}else{
										          		price = price.replace(",", " ~ ");
										          	}
												} 
									          	str = '<span class="fb red">'+price+'</span>';
												break;
											case 3:
									          	var pv = record.pvRegion;
									          //无积分显示 “-”
								          		if(record.ishaspv == '1'){
								          			pv = '-';
								          		}else{
								          			if(pv == ""){ 
								          				pv = record.pv;
									          		}else{
										          		pv = pv.replace(",", " ~ ");
										          	}
								          		}
									          	str = '<span class="fb blue">'+pv+'</span>';
												break;
											case 4:
												str = record.createTime;
												break;
											case 5:
												if(status=='0'){
													str = "未上架";
									         	} else if(status=='3') {
									         		str = "已上架";
										   		}
												break;
											case 6:
												if(responseState.retCode!=600){
													str += '<a class="updateItem" data-id="'+id+'" data-vid="'+virtualShopId+'">编辑</a>';
													str += ' | <a class="delsingleItem" data-id="'+id+'">删除</a>';
													if(status=='0'){
														str += ' | <a class="putaway" data-id="'+id+'" data-vid="'+virtualShopId+'">上架</a>';
													}else if(status=='3'){
														str += ' | <a class="soldout" data-id="'+id+'" data-vid="'+virtualShopId+'">下架</a>'
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
									if (other ){
										itemVisitUrl = other.itemVisitUrl;
									}
									
									$(".itemFooImg").each(function(){
										var dsrc = $(this).attr("data-src");
										var ishaspv = $(this).attr("data-ishaspv");//是否为无积分商品 0 否 1 是
										if(ishaspv!=1 && dsrc){
											$(this).attr("src",itemVisitUrl + dsrc);
										}else{
											$(this).attr("src","resources/img/item_df.png");
										}
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
									
								}//end complete;
				            });
				     //});//end $
	        		//自定义分页栏按钮
					var buttonHtml = '<td style="text-align: left;">';
			        buttonHtml += '<table><tr>';
			        buttonHtml += '<td><div id="" class="splb_sclb lh30"><a>全选</a><input name="" type="checkbox" value="" id="all"></td>';
			        buttonHtml += '<td><div id="batchDeletData" class="splb_sclb lh30"><a href="javascript:;">批量删除</a></div></td>';
			        buttonHtml += '<td><div id="batchPutOn" class="splb_sclb lh30"><a href="javascript:;">批量上架</a></div></td>';
			        buttonHtml += '<td><div id="batchPutOff" class="splb_sclb lh30"><a href="javascript:;">批量下架</a></div></td>';
			        buttonHtml += '</tr></table>';
			        buttonHtml += '</td>';
			        $('#' + gridObj.options.pagingOutTabId + ' tr:eq(0)').prepend(buttonHtml);
	        	})//end ajaxRequest;
			},
			bindClick : function(){
				//新增商品
				$("#addItem").unbind().on('click',function(){
					require(['hsec_itemInfoFoodSrc/tab'],function(tab){
						tab.showPage();
					})
				});
			
				//搜索商品按钮事件
				$("#searchItem").unbind().on('click',function(){
					shop.bindTable(objParam);
				});
			},
			bindTableClick : function(){
				//商品状态事件
				$("#selItemStatus").unbind().on('change',function(){
					typeState = $(this).val();
					shop.bindTable(objParam);
				});
				//全选
				tablePoint.checkBoxAll($("#all"),$(".search-table .std_checkbox input[type='checkbox']"));
				
				// 编辑
				$(".updateItem").unbind().bind("click",function(){
					var param = {"itemId":$(this).attr("data-id"),"vshopId":$(this).attr("data-vid")}
					
					var navObj = {}; 
        			require(["hsec_itemInfoFoodSrc/releaseItem"],function(src){	
    					navObj.shop = src;
    					navObj.shop.bindData(param);	 
    				})
					
				});
				//单条删除记录事件
				$(".delsingleItem").unbind().on('click',function(){	
						var obj = $(this);
						var itemId = $(this).attr("data-id");
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
													if(response.retCode=="200"){
														tablePoint.tablePoint("删除成功！",function(){
															$(obj).parent().parent().remove();
															shop.bindTable(objParam);
														});
													}else{
														tablePoint.tablePoint("删除失败!请稍后重试！");
														shop.bindTable(objParam);
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
//					  var virtualShopId = $(this).attr("data-vid");
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
					        			var param = {"virtualShopId":virtualShopId,"itemIds" : itemIds}
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
				 //单条下架事件
				$(".soldout").unbind().on("click",function(){
					var self = this;
					comm.confirm({
						content:'是否确定下架？',
						callOk : function(){
							var itemId = $(self).attr("data-id");
							var virtualShopId = $(self).attr("data-vid");
							var param = {"itemId":itemId, "virtualShopId":virtualShopId};
							ajaxRequest.itemPutOff(param,function(response){
								if(response.retCode == 200){
									tablePoint.tablePoint('设置菜品下架成功！',function(){
										shop.bindTable(objParam);
									});
								}else{
									tablePoint.tablePoint('设置菜品下架失败！');
								}
							});
						}
					});
					
					
				}); 
		    	//单条上架事件
				$(".putaway").unbind().on("click",function(){
					var itemId = $(this).attr("data-id");
					var virtualShopId = $(this).attr("data-vid");
					var param = {"itemId":itemId, "virtualShopId":virtualShopId};
					ajaxRequest.itemPutOn(param,function(response){
						if(response.retCode == 200){
							tablePoint.tablePoint('设置菜品上架成功！',function(){
								shop.bindTable(objParam);
							});
						}else{
							tablePoint.tablePoint('设置菜品上架失败！');
						}
					});
				});
				 //批量上架事件
					$("#batchPutOn").unbind().on('click',function(){
						 var itemIds ="";
						 var flag = false;
						  $("input[name='itemDataCheckBox']:checked").each(function(){
							  itemIds+=$(this).val()+",";
							  if($(this).attr("status")!='0'){
								  flag = true; 
							  }
						  })
//						  var virtualShopId = $(this).attr("data-vid");
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
						        			var param = {"virtualShopId":virtualShopId,"itemIds" : itemIds}
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
	
	/**
	 *对response进行解析 
	 */
	function foodResponseParse(response){
		if(response!=null&&response.retCode==200){
			//获得展示的图片
			var listResult=response.data.QueryResult.result;
			var itemVistiUrl=response.data.itemVisitUrl;
			if(listResult==null||listResult=='')
				return response;
			$(listResult).each(function(index,dom){
				//价格展示																
				if(dom.priceRegion!=null&&dom.priceRegion!=''){
					
					var newstr=dom.priceRegion.split(",");									
					response.data.QueryResult.result[index].price=
						comm.formatMoneyNumber(newstr[0])+" ~ "+comm.formatMoneyNumber(newstr[1]);
				}else{
					response.data.QueryResult.result[index].price=
						comm.formatMoneyNumber(dom.price);
				}
				
				//积分展示
				if(dom.pvRegion!=null&&dom.pvRegion!=''){
					var newstr=dom.pvRegion.split(",");
					response.data.QueryResult.result[index].pv=	
						comm.formatMoneyNumber(newstr[0])+" ~ "+comm.formatMoneyNumber(newstr[1]);
				}else{
					response.data.QueryResult.result[index].pv=
						comm.formatMoneyNumber(dom.pv);
				}
				
				var pics=dom.pics;
				var webShowPicUrl="";
				if(pics!=null&&pics!=''){									
					var obj = jQuery.parseJSON(pics);
					if(obj[0]!=null&&obj[0]!=''&&obj[0].web!==null&&obj[0].web!=''){
						var webPic=obj[0].web;								
						$(webPic).each(function(index2,dom2){
							if(webPic.size="110*110"){
								webShowPicUrl=itemVistiUrl+"/"+dom2.name;										
								return false;	//跳出本层循环
							}
						});	
					}				
					
				}
				response.data.QueryResult.result[index].webShowPic=webShowPicUrl;	
				
			});
		}
		return response;
	};
	
	return shop;
	
	
});
