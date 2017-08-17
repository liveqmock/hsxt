define([ "text!hsec_shopInfoTpl/shopInfoList/shopInfoListHead.html"
         ,"text!hsec_shopInfoTpl/shopInfoList/shopInfoList.html"
         ,"text!hsec_shopInfoTpl/baiduMap/baiduMap.html"
         ,"hsec_shopInfoSrc/shopInfo/shopInfoHead"
         ,"hsec_shopInfoSrc/shopInfoAdd/shopInfoAdd"
         ,"hsec_shopInfoSrc/shopInfoUpdate/shopInfoUpdate"
         ,"hsec_shopInfoDat/shopInfo"
         ,"hsec_tablePointSrc/tablePoint"
         ,"text!hsec_shopInfoTpl/shopInfoList/itemMenuFoodList.html"], function(shopInfoListHead,shopInfoList,baiduMap,shopInfoHead,shopInfoAdd,shopInfoUpdate,ajaxRequest,tablePoint,itemMenuFoodList) {
			var param = {};
	var shop = {
			queryParam : function(num){
				param = {};
				var shopInfoNames = $("#shopInfoNames").val();
				if(shopInfoNames != null && shopInfoNames !=""){
					param["name"] = $.trim(shopInfoNames);
				}
				if($("#shopInfoSection").val() != "" && $("#shopInfoSection").val() != null){
					param["section"] = "[\""+$("#shopInfoSection").val()+"\",\""+$("#shopInfoSection").find("option:selected").text()+"\"]";
				}
				if($("#status").val()!=null && $("#status").val()!=""){
					param["status"] = $("#status").val();
				}
				param["currentPageIndex"] = num;
			},
			init : function(){
				if(param != null || param != ""){
					param = {};
				}
			},
			bindData : function(){
				ajaxRequest.listMapSalerShop(null, function(response) {
					var html = _.template(shopInfoListHead, response.data);
					$('.operationsArea').html(html);
					shop.queryParam(1);
					shop.bindDataClick();
					shop.bindTable();
					shop.init();
				})	
			},
			bindDataClick : function(){
				//搜索
				$("#search").unbind().on("click",function(){
					shop.queryParam(1);
					shop.bindTable();
				});
				//添加营业点
				$("#addShopInfo").unbind().on("click",function(){
					shopInfoAdd.init(shop);
				})
			},
			bindTable : function(){
					tablePoint.bindJiazai("#typeHide", true);
				ajaxRequest.listSalerShop(param, function(response) {
					tablePoint.bindJiazai("#typeHide", false);
					var html = _.template(shopInfoList, response.data);
					$("#shopInfoTable").html(html);
					$("#searchTable").DataTable({
						"scrollY":"345px",//垂直限制高度，需根据页面当前情况进行设置
						"scrollCollapse": true,//垂直限制高度
						"bFilter" : false, 
						"bPaginate": false,
						"bLengthChange" : false, 
						"bSort":false,
						"sDom" : '<""l>t<"F"fp>',
						"oLanguage" : {//改变语言
							"sZeroRecords" : "没有找到符合条件的数据"
						}
					});
					shop.bindTableClick();
				})
			},
			getSalerStoreMenuRelations : function(){ //设置菜单
				param["virtualShopId"] = "2510162148197376";
				var shopId = $("#searchTable").find("tbody").find("tr").attr("shopid");
				param["shopId"] = shopId;
				ajaxRequest.getSalerStoreMenuRelations(param, function(response) {
					var html = _.template(itemMenuFoodList, response.data);
					var itemId = response.setMenuId;										
					$('#dlgSetFoodMenuItem').html(html);
					//选中已经关联的菜单
					//$("#menu_"+"2518308520477696").attr("checked","checked");
					$("#menu_"+itemId).attr("checked","checked");
					$("#dlgSetFoodMenuItem").dialog({
						title : "设置菜单",
						width : "600",
						modal : true,						
						close: function() { 
						        $(this).dialog('destroy');
						},
						buttons : {
							'确定' : function() {
								param["vShopId"] = "2510162148197376";
								var menuId = $('#menuItemFoodList input[name="itemMenuFood"]:checked').attr("foodItemId");
								param["menuId"] = menuId;
								shop.addSalerStoreMenuRelation(param);
								$(this).dialog("destroy");
							}
						}
					   });
				   });				
			},
			addSalerStoreMenuRelation : function(param){
				ajaxRequest.addSalerStoreMenuRelation(param, function(response) { //关联菜单
					if(response.responseStatus == "200"){
						alert("更新成功！");
						shop.bindData();
					}
				   });
			},
			bindTableClick : function(){
				//分页
			    $(".pageOrder").unbind().on('click',function(){
			    	var num = $(this).attr("page");
			    	if(num == '' || num == null){
			    		return false;
			    	}
			    	shop.queryParam(num);
			    	shop.bindTable();
			    });
			    //分页输入
		    	   $('#pageInput').unbind().on('keypress',function(event){
		            if(event.keyCode == "13")    
		            {
		            	var value = $(this).val();
		            	var totalPage = Number($(this).attr("totalPage"));
			            	if(value > totalPage  || value <= 0 || isNaN(value)){
			            		tablePoint.tablePoint("输入范围错误!请再次输入!");
			            		return false;
			            	}else{
			            		shop.queryParam(value);
					    		shop.bindTable();
			            	}
		            }
		        });
				
				//显示营业点详情
				$("#searchTable").on("click",".detail",function(){
					var shopId = $(this).parents("tr").attr("shopId");
					shopInfoHead.bindData(shopId,shop);
				})
				
				//显示营业点地图
				$("#searchTable").on("click",".lookMap",function(){
					$('#dlg2').html(baiduMap);
					var landmark = $(this).attr("landmark");
					var obj = $(this);
					require(["hsec_shopInfoSrc/baiduMap/map"],function(shopMap){
					      var shopName=$(obj).parents("tr").children(0).children(0).html();
					      var contactWay=$(obj).parents("tr").children().eq(4).html();
					      var xy = landmark.split(",");
					      if(xy.length < 2 || xy[0] == null || xy[0] == "" || xy[1] == null || xy[1] == ""){
					    	  tablePoint.tablePoint("营业点未选择地标!");
					    	  return;
					      }
					      var longitude = xy[1];
					      var dimension = xy[0];
	  				$("#dlg2").dialog({
							title : "地图定位",
							width : "628",
							modal : true,
							open : function(){
								shopMap.initMap(shopName,contactWay,longitude,dimension);
							},
							close: function() { 
							        $(this).dialog('destroy');
							},
							buttons : {
								'关闭' : function() {
									$(this).dialog("destroy");
								}
							}
						   });
					   });
				})
				
				
					//营业点提交删除
				$("#searchTable").on("click",".deleteShopInfo",function(){
					var shopId = $(this).parents("tr").attr("shopId");
					 $( "#dlg1" ).dialog({
					      modal: true,
					      title:"删除营业点",
						  close: function() { 
						        $(this).dialog('destroy');
						  },
					      buttons: {
					        确定: function() {
					        	//删除
								if(shopId == null || shopId == "" || shopId == "undefined"){
									return false;
								}
								var param={"id":shopId,"status":4};
								ajaxRequest.updateSalerShopStatus(param,function(response){
									if(response.retCode==200){
										tablePoint.tablePoint("删除成功!",function(){
												shop.bindTable();
										});
									}else if(response.retCode==602){
										tablePoint.tablePoint("营业点含有未完结订单,不能删除!");
									}else if(response.retCode==603){
										tablePoint.tablePoint("营业点含有未完结售后单,不能删除!");
									}else if(response.retCode==604){
										$("#dlg2").html("<p class='table-del'>确定删除最后一个启用中营业点？</p>");
										 $( "#dlg2" ).dialog({
										      modal: true,
										      title:"删除最后一个营业点",
											  close: function() { 
											        $(this).dialog('destroy');
											  },
										      buttons: {
										        确定: function() {
													var param={"id":shopId};
													ajaxRequest.deleteSalerShop(param,function(response){
														if(response.retCode==200){
															tablePoint.tablePoint("删除成功!",function(){
																	shop.bindTable();
															});
														}else{
															tablePoint.tablePoint("删除失败!");
														}
													})
										            $( this ).dialog( "destroy" );
										        },
										        "取消":function(){
										        	$( this ).dialog( "destroy" );
										        }
										      }
										  });
									}else{
										tablePoint.tablePoint("删除失败!");
									}
								})
					            $( this ).dialog( "destroy" );
					        },
					        "取消": function(){
					        	$( this ).dialog( "destroy" );
					        }
					      }
					  });
				})
				
				//营业点修改
				$("#searchTable").on("click",".updateShopInfo",function(){
					var shopId = $(this).parents("tr").attr("shopId");
					shopInfoUpdate.init(shop,shopId);
				})
				
				//设置菜单
				$("#searchTable").on("click",".setFoodMenu",function(){				
					shop.getSalerStoreMenuRelations();
				})
			}
	} 
	return shop;
})
