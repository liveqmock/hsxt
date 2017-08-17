define(["hsec_tablePointSrc/tablePoint"
		,"hsec_shopInfoFoodDat/shopFood"
		,"hsec_shopInfoFoodSrc/shopInfoFoodUpdate/shopInfoFoodUpdate"
		,"hsec_shopInfoSrc/shopInfoUpdate/shopInfoUpdate"
		,"hsec_shopInfoFoodSrc/shopInfoFoodAdd/shopInfoFoodAdd"
		,"text!hsec_shopInfoFoodTpl/shopInfoFoodMain/tab.html"
		,"text!hsec_shopInfoFoodTpl/shopInfoFoodMain/gridMain.html"
		,"text!hsec_shopInfoFoodTpl/shopInfoFoodMain/itemInfoHead.html"
		,"text!hsec_shopInfoFoodTpl/shopInfoFoodMain/itemInfoData.html"
		,"text!hsec_shopInfoFoodTpl/shopInfoFoodMain/itemMenuFoodList.html"
		],function(tablePoint,ajaxRequest,shopInfoFoodUpdateJs,shopInfoUpdateJs,shopInfoFoodAddJs,itemTabTpl,gridMainTpl,itemInfoHead,itemInfoData,itemMenuFoodList){
	//声明查询参数
	var queryData = {"currentPageIndex":1,"eachPageSize":20};
	//营业点是否冻结
	var isFrozen = false;
	
	var info = {
		//查询method
		queryParam : function(num){
			queryData = {};
			queryData["shopName"] = $.trim($("#shopName").val());
			queryData["shopType"] = $("#shopType").attr("optionvalue");
			queryData["area"] = $("#areaId").attr("optionvalue");
			queryData["businessArea"] = $.trim($("#businessArea").val());
			return queryData;
		},
		//初始化ComboxList
		selectList : function(){
			ajaxRequest.getSelectAreaComboboxData(function(response){
				if (response.retCode == "200") {
					var opts = new Array();
					opts[0] = {name:"全部",value:""};
					$.each(response.data,function(key,value){
						opts[key+1] = {name:value.areaName,value:value.areaCode}
					});
					$("#areaId").selectList({
						options:opts,
						width:115
					});
				}
			});
			$("#shopType").selectList({
				options:[
                    {name:'全部',value:''},
					{name:'零售',value:'1'},
					{name:'餐饮',value:'2'}
				]
			});
		},
		//主入口
		bindData : function(){
			$("#busibox").html(gridMainTpl);
			info.selectList();
			
			info.loadBsGridData(info.queryParam(1));
			$("#btnSearch").unbind().on("click",function(){
				info.loadBsGridData(info.queryParam(1));
			});
		},
		//加载表格数据
		loadBsGridData : function(queryParam){
			var gridObj;
			var menuName='';
			$(function(){
				gridObj = $.fn.bsgrid.init('shopinfoGridMain',{
					url:{url:comm.UrlList["listItemInfoFoodQueryParam"],domain:"sportal"},//comm.domainList["sportal"]+comm.UrlList["listItemInfoFoodQueryParam"],
					pageSizeSelect: true,
	                pageSize: 20,
	                otherParames:queryParam,
	                stripeRows: true,  //行色彩分隔 
					displayBlankRows: false,   //显示空白行
					operate: {
						/**
						 * record:数据源
						 * rowIndex：行
						 * colIndex：列
						 * options：
						 */
						add: function(record, rowIndex, colIndex, options){
							var sTpl = "";
							var chkid = record.salerShopStore.id;
							var vshopids = record.salerShopStore.vShopId;
							var type = record.salerShopStore.type;
							var name = record.salerShopStore.name;
							var addr = record.salerShopStore.addr;
							var section = record.salerShopStore.section;
							if(section.length > 0){
								section = eval("("+section+")")[1];//设置商圈
							}
							var status = record.salerShopStore.status;
							var menuIds = record.menuIds;
							var modifiedCss = "";
							switch (colIndex){
								case 0 :
									sTpl = '<input class="mr5 myDeviler" name="itemInfoChkb" type="checkbox" chkid="'+chkid+'" vshopids="'+vshopids+'" data-type="'+type+'">'
									break;
								case 1 :
									if(type=='2'){
										sTpl = name;
									}
									break;
								case 2 :
									if(type=='1'){
										sTpl = "零售";
									}else if(type=='2'){
										sTpl = "餐饮 ";
									}
									break;
								case 3 :
									sTpl = addr;
									break;
								case 4 :
									sTpl = section;
									break;
								case 5 :
									if(status=='2'){
										sTpl = '启用 ';
									}else if(status == '6'){
										sTpl = '冻结';
									}
									break;
								case 6 :
									if(menuIds.length > 0){
										sTpl = '<span class="'+menuIds[0]+'"></span>';
									}else{
										sTpl = '';
									}
									break;
								case 7 :
									if(status=='2'){//正常才有操作，冻结无操作
										if(type=='1'){
											modifiedCss = "modefiyls";
										}else{
											modifiedCss = "modefiy";
											sTpl = '<a href="javascript:;" data-id="'+rowIndex+'" class="caozuo lh24 settingsMenu mr10">设置菜单</a>';
										}
										sTpl += '	<a data-id="'+rowIndex+'" class="'+modifiedCss+' caozuo lh24 updateCounterById  mr10">修改</a>';
										sTpl += '	<a data-id="'+rowIndex+'" class="caozuo lh24 delCounterById  mr10">删除</a>';
									}
									break;
								default :
									break;
							}
							return sTpl;
						}
					},
					complete : function(e,o){
						if(!(o.responseText===undefined)){
							//设置菜单名称
							var menuObj = eval("("+o.responseText+")").orther;
							if (!menuObj){return};
							isFrozen = menuObj.status;
							
							//判断营业点状态
							if(!isFrozen){
								//自定义分页栏按钮
								var buttonHtml = '<td style="text-align: left;width:200px;">';
						        buttonHtml += '<table id="customTable"><tr>';/*<input name="chkAll" id="chkAll" value="" type="checkbox">*/
						        buttonHtml += '<td><div id="splb_sclb" class="splb_sclb"><input name="chkAll" id="chkAll" value="" type="checkbox"><a>全选</a></div></td>';
						        //和业务确认，屏蔽此功能  modify by zhucy 2016-04-09
						        //buttonHtml += '<td><div id="batchCounterDeletData" class="splb_sclb"><a style="margin-top: 4px;display: block;" href="javascript:;">批量删除</a></div></td>';
						        buttonHtml += '<td><div id="batchSttingsDish" class="splb_sclb" style="width:80px;"><a href="javascript:;">批量设置菜单</a></div></td>';
						        buttonHtml += '</tr></table>';
						        buttonHtml += '</td>';
						        
						        /*营业点列表--多次上下翻页后，底部控件重复  限制只加一次 modify by kuangrb 2016-05-13*/
						        if($('#' + gridObj.options.pagingOutTabId + ' tr:eq(0)').find('table#customTable').length == 0 ){
						        	$('#' + gridObj.options.pagingOutTabId + ' tr:eq(0)').prepend(buttonHtml);
						        }
						        /*end*/
						        
							}else{
								$('#btnAdd').hide();
							}
							
							var obj = gridObj.getAllRecords();
							if(obj.length > 0 && menuObj ) {
								for(var i=0;i<obj.length;i++){
									var menuIds = obj[i].menuIds;
									if(menuIds != null && menuIds != ''&& menuIds.length > 0){
										/**
										 * //modifly by zhanghh 2016-01-29 
										 * desc:当选择为all时，展示为“全部”
										 */
										if("all"==menuObj[menuIds[0]]){
											menuName = "全部";
										}else{
											menuName = menuObj[menuIds[0]];
										}
									}
									$("."+menuIds[0]).html(menuName);
								}
	//							$.each(obj,function(key,value){
	//								var menuIds =value.menuIds;
	//								if(menuIds != null && menuIds != ''&& menuIds.length > 0){
	//									menuName = menuObj[menuIds[0]];
	//									$("."+menuIds[0]).html(menuName);
	//								}
	//							});
							}
						}
						$("#btnSearch").unbind().on("click",function(){
							info.loadBsGridData(info.queryParam(1));
						});
						//新增营业点
						$("#btnAdd").unbind().on("click",function(){
							shopInfoFoodAddJs.bindFootInit(info);
						});
						//餐饮修改事件
						$(".modefiy").unbind().on("click",function(){
							//获取当前行的选中下标
							var obj = gridObj.getRecord($(this).attr("data-id"));
							var shopId = obj.salerShopStore.id;
							shopInfoFoodUpdateJs.bindFootInit(info,shopId);
						});
						//零售修改事件
						$(".modefiyls").unbind().on("click",function(){
							var obj = gridObj.getRecord($(this).attr("data-id"));
							var shopId = obj.salerShopStore.id;
							shopInfoUpdateJs.init(info,shopId);
						});
						$(".delCounterById").unbind().on('click',function(){	
							var obj = gridObj.getRecord($(this).attr("data-id"));
							var id = obj.salerShopStore.id;
							var vshopId=obj.salerShopStore.vShopId;
							var param = {"id" : id,"vShopId":vshopId};
							 $( "#dlg1" ).dialog({
							 	  title : "提示信息",
							      modal: true,
								  close: function() { 
								        $(this).dialog('destroy');
								  },
							      buttons: {
							        确定: function() {
							        	//删除
					        			ajaxRequest.deleteFoodCounterById(param, function(response) {
							        		$("#dlg1").dialog( "destroy" );
											if(response.retCode=="200"){
												tablePoint.tablePoint("删除成功！",function(){
													$(obj).parent().parent().remove();
													info.loadBsGridData(info.queryParam(1));
												});
											}else{
												tablePoint.tablePoint("删除失败!请稍后重试！");
												info.loadBsGridData(info.queryParam(1));
											}
										})
							        },
							        取消: function() {
							        	$(this).dialog('destroy');
							        }
							      }
							});
						});//end delete
						
						
						$(".settingsMenu").bind("click",function(){//单个设置菜单
							var obj = gridObj.getRecord($(this).attr("data-id"));
							var id = obj.salerShopStore.id;
							var vshopId=obj.salerShopStore.vShopId;
							var param = {"shopId" : id,"virtualShopId":vshopId}
							ajaxRequest.getSalerStoreMenuRelations(param, function(response) {
								var html = _.template(itemMenuFoodList, response.data);
								var itemId = response.setMenuId;										
								$('#dlgSetFoodMenuItem').html(html);
								//var itemGridObj = $.fn.bsgrid.init('menuItemFoodList',{});
								//选中已经关联的菜单
								$("#menu_"+itemId).attr("checked","checked");
								$("#dlgSetFoodMenuItem").dialog({
									title : "设置菜单",
									width : "870",
									modal : true,
									closeIcon:true,//Modify by zhanghh 2016-01-15 添加右上角关闭图标
									close: function() { 
									     $(this).dialog('destroy');
									},
									buttons : {
										'确定' : function() {
											
											var list = $('#menuItemFoodList input:radio[name="itemMenuFood"]:checked').val();/*校验单选框是否选中 add by kuangrb 2016-05-27*/
											if(list != null){
												param["vShopId"] = vshopId;
												var menuId = $('#menuItemFoodList input[name="itemMenuFood"]:checked').attr("foodItemId");
												param["menuId"] = menuId;
												info.addSalerStoreMenuRelation(param);
												$(this).dialog("destroy");										
											}
											else{
												comm.alert({
													imgFlag : true,
													imgClass : 'tips_error',
													content : '请选择菜单名称！'	
												});
											}
											
											
											
										},
										'取消' : function(){
											$(this).dialog("destroy");
										}
									}
								   });
							   });	
						});//end settings menu
						
						
						//全选事件
						//info.bindCheckAllClick();
						$('#' + gridObj.options.pagingOutTabId + ' #chkAll').unbind().on('click',function(){
							//tablePoint.checkBoxAll('#chkAll',".myDeviler[type='checkbox']");
							var chkbox = $(".myDeviler[type='checkbox']");
							$(this).prop('checked')?chkbox.prop('checked',true):chkbox.prop('checked',false);
							$("input[type='checkbox']").not($('#chkAll')).on('click',function(){
								$('#chkAll').prop('checked',false);
							})
						});//end 全选事件
						//批量删除
						//info.bindBatchDeleteClick();
						$('#' + gridObj.options.pagingOutTabId + ' #batchCounterDeletData').unbind().on('click',function(){
							info.bindBatchDeleteClick();
						});//end 批量删除
						//批量设置菜单
						//info.bindBatchSettingsClick();
						$('#' + gridObj.options.pagingOutTabId + ' #batchSttingsDish').unbind().on('click',function(){
							info.bindBatchSettingsClick();
						});//end 批量设置菜单
					}
				});
			});
		},
		bindTableClick : function(){
			$("#btnAdd").on("click",function(){
				shopInfoFoodAddJs.bindFootInit(info);
			});
			//全选事件
			tablePoint.checkBoxAll("#chkAll",".myDeviler[type='checkbox']");
		},
		//查询事件
		bindSearchClick : function(){
			$("#btnSearch").off().on("click",function(){
				info.loadBsGridData(shop.queryParam(1));
			});
		},
		//全选
		bindCheckAllClick : function(){
			tablePoint.checkBoxAll("#chkAll",".myDeviler[type='checkbox']");
		},
		//修改事件
		bindUpdateClick : function(){
			$(".modefiy").bind("click",function(){
				var shopId = $(this).parent().attr("value");
				shopInfoFoodUpdateJs.bindFootInit(info,shopId);
			});
			$(".modefiyls").bind("click",function(){
				var shopId = $(this).parent().attr("value");
				shopInfoUpdateJs.init(info,shopId);
			});
		},
		//删除事件
		bindDeleteClick : function(){
			$(".delCounterById").unbind().on('click',function(){	
				var obj = $(this);
				var id = $(this).attr("value");
				var vshopId=$(this).attr("vshopid");
				var param = {"id" : id,"vShopId":vshopId};
					 $( "#dlg1" ).dialog({
					 	  title : "提示信息",
					      modal: true,
						  close: function() { 
						        $(this).dialog('destroy');
						  },
					      buttons: {
					        确定: function() {
					        	//删除
					        			ajaxRequest.deleteFoodCounterById(param, function(response) {
							        		$("#dlg1").dialog( "destroy" );
											if(response.retCode=="200"){
												tablePoint.tablePoint("删除成功！",function(){
													$(obj).parent().parent().remove();
													$("#btnSearch").click();
												});
											}else{
												tablePoint.tablePoint("删除失败!请稍后重试！");
												$("#btnSearch").click();
											}
										})
					        
					        },
					        取消: function() {
					        	$(this).dialog('destroy');
					        }
					      }
					  });
			});
		},
		//批量删除事件
		bindBatchDeleteClick : function(){
				 var ids ="";
				 var vshopids="";
				  $("input[name='itemInfoChkb']:checked").each(function(){
					  ids+=$(this).attr("chkid")+",";
					  vshopids=$(this).attr("vshopids");
				  })
				  var lastIndex = ids.lastIndexOf(',');
				  if (lastIndex > -1) {
					  ids = ids.substring(0, lastIndex) + ids.substring(lastIndex + 1, ids.length);
				  }
				  if($("input[name='itemInfoChkb']:checked").length<=0){
					  tablePoint.tablePoint("请先选择记录！");
				  }else{
					  var param = {"ids" : ids,"vShopId":vshopids}
						 $( "#dlg1" ).dialog({
						 	  title : "提示信息",
						      modal: true,
							  close: function() { 
							        $(this).dialog('destroy');
							  },
						      buttons: {
						        确定: function() {
						        	//批量删除
						        	tablePoint.bindJiazai("#table1", true);
						    		ajaxRequest.deleteCateringCategoryByIds(param, function(response) {
						    				tablePoint.bindJiazai("#table1", false);
						    			$("#dlg1").dialog("destroy");
										if(response.retCode=="200"){
											tablePoint.tablePoint("批量删除营业点成功！",function(){
												info.loadBsGridData(info.queryParam(1));
											});
										}else{
											tablePoint.tablePoint("批量删除失败！请稍后重试！");
											info.loadBsGridData(info.queryParam(1));
										}
									})
						        },
						        取消: function() {
						        	$(this).dialog('destroy');
						        }
						      }
						  });
					  
				  }
			//});
		},
		//设置菜单
		bindSettingsClick : function(){
			$(".settingsMenu").bind("click",function(){
				var obj = $(this);
				var id = $(this).attr("value");
				var vshopId=$(this).attr("vshopid");
				var param = {"shopId" : id,"virtualShopId":vshopId}
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
								param["vShopId"] = vshopId;
								var menuId = $('#menuItemFoodList input[name="itemMenuFood"]:checked').attr("foodItemId");
								param["menuId"] = menuId;
								if(menuId != null && menuId != ""){
									info.addSalerStoreMenuRelation(param);
								}
								$(this).dialog("destroy");
							}
						}
					   });
				   });	
			});
		},
		addSalerStoreMenuRelation : function(param){
			ajaxRequest.addSalerStoreMenuRelation(param, function(response) { //关联菜单
				if(response.retCode == "200"){
					tablePoint.tablePoint('营业点设置菜单成功！');
					info.loadBsGridData(info.queryParam(1));
				}else{
					tablePoint.tablePoint('营业点设置菜单失败，请稍后重试！');
					info.loadBsGridData(info.queryParam(1));
				}
			   });
		},
		//批量设置菜单
		bindBatchSettingsClick : function(){
			var ids ="";
			 var vshopids="";
			 var flag = false;
			  $("input[name='itemInfoChkb']:checked").each(function(){
				  ids　+=　$(this).attr("chkid")+",";
				  if($(this).attr("data-type") != "2"){
				  	 flag = true;
				  }
			  })
			  var lastIndex = ids.lastIndexOf(',');
			  if (lastIndex > -1) {
				  ids = ids.substring(0, lastIndex) + ids.substring(lastIndex + 1, ids.length);
			  }
			  var param = {};
				param["shopId"] = ids;//营业点ＩＤ
			  if($("input[name='itemInfoChkb']:checked").length<=0){
				  tablePoint.tablePoint("请先选择记录！");
			  }else{
			  	if(flag){
			  		tablePoint.tablePoint("所选择记录中含有不是餐饮的记录！");
			  	}else{
					ajaxRequest.getSalerStoreMenuRelations(null, function(response) {
					var html = _.template(itemMenuFoodList, response.data);									
					$('#dlgSetFoodMenuItem').html(html).dialog({
						title : "批量设置菜单",
						width : "870",
						modal : true,
						closeIcon:true,
						buttons : {
							'确定' : function() {
								var menuId = $('#menuItemFoodList input[name="itemMenuFood"]:checked').attr("foodItemId");
								param["menuId"] = menuId;
								info.batchAddStoreMenuRelation(param);
								$(this).dialog("destroy");
							},
							'取消' : function(){
								$(this).dialog("destroy");
							}
						}
					   });
				   });	
			  	}
			 }
		},
		batchAddStoreMenuRelation : function(param){
			ajaxRequest.batchAddStoreMenuRelation(param, function(response) { //批量关联菜单
				if(response.retCode == "200"){
					tablePoint.tablePoint(response.msg);
					info.loadBsGridData(info.queryParam(1));
				}else{
					tablePoint.tablePoint(response.msg);
					info.loadBsGridData(info.queryParam(1));
				}
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
	};
	return info;
});