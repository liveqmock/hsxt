define(["hsec_tablePointSrc/tablePoint"
		,"hsec_itemMenuFoodDat/itemMenuNew"
		,"hsec_itemInfoFoodSrc/category"
		,"hsec_itemInfoFoodSrc/itemList/itemListInfoHead"
		,"text!hsec_itemMenuFoodTpl/main/index.html"
		,"text!hsec_itemMenuFoodTpl/main/dishMain.html"
		,"text!hsec_itemMenuFoodTpl/cp/dishFood.html"
		,"text!hsec_itemMenuFoodTpl/cp/setUpFood.html"
		,"text!hsec_itemMenuFoodTpl/yyd/operatingFood.html"
		,"text!hsec_itemMenuFoodTpl/yyd/setOperatingPoint.html"]
		,function(tablePoint,ajaxRequest,categoryJs,itemListInfoHeadJs,indexTpl,dishMainTpl,dishFoodTpl,setUpFoodTpl,operatingFoodTpl,setOperatingPointTpl){
	/** 菜单menuId */
	var _menuId = "";
	/** 商城virtualShopId */
	var _virtualShopId = "";
	/** 营业点vshopId */
	var _vshopId = "";
	/** 菜单名称 */
	var _menuName = "";
	/** 左边菜单点击事件 */
	//var menuValue = "1";
	var itemMenuNew = {
		//构建查询参数
		queryParam : function(num){
			var param = {"currentPageIndex":num!=null?num:1,"eachPageSize":20};
			return param;
		},
		//绑定数据到页面
		bindData : function(){
			$("#busibox").html(indexTpl);
			itemMenuNew.initBindData();
		},
		//初始化页面数据[左边菜单]
		initBindData : function(){
			var param = itemMenuNew.queryParam(1);
			ajaxRequest.listItemMenuFood(param,function(response){
				var html = _.template(dishMainTpl,response);
				$("#cddy-1").html(html);
				itemMenuNew.initAttrClick();//属性
				itemMenuNew.tabClick();//tab事件
				itemMenuNew.leftMenuClick();//左边菜单事件
				itemMenuNew.addMenuClick();//添加左边菜单
				itemMenuNew.deleteMenuClick();//删除左边菜单及关联关系
				/***********************菜品***********************/
				itemMenuNew.bindDishData(_menuId,_virtualShopId);
				/***********************营业点***********************/
				itemMenuNew.bindOperationgData(_menuId,_virtualShopId);
				$(".cookbook-ipt:eq(0)").click();
			});
		},
		//菜品数据绑定{已关联}
		bindDishData : function(menuId,virtualShopId){
			itemListInfoHeadJs.bindSelectData();//查询类目
			var param = itemMenuNew.queryParam(1);
			param.menuId = menuId;
			param.virtualShopId = virtualShopId;
			
			//根据营业点ID或者菜单ID查询菜品和商品平台分类集合[已关联]
			ajaxRequest.customClassificationCollection(param,function(response){
				response["menuId"] = menuId;
				response["menuName"] = _menuName;
				var html = _.template(dishFoodTpl,response);
				$("#cddy").html(html);
				//$("#cpname").text(_menuName);
				itemMenuNew.initAttrClick();//属性
				itemMenuNew.tabClick();//tab事件
				itemMenuNew.setUpFoodClick();//菜品设置
				itemMenuNew.bindDeleteDishesClick();//删除菜单与菜品关系事件
			});
			
		},
		//左边菜单事件
		leftMenuClick : function(){
			
			$(".cookbook-ipt").unbind("click").on("click",function(){
				_menuName = $(this).val();
				_menuId = $(this).attr("data-id");
				_virtualShopId = $(this).attr("data-virtual-shop-id");
				itemMenuNew.bindDishData(_menuId,_virtualShopId);
				itemMenuNew.bindOperationgData(_menuId,_virtualShopId);
			});
		},
		//添加左边菜单
		addMenuClick : function(){
			$(".ckbk-yes").unbind().on("click",function(){
				var menuId = $(this).parent().siblings("input").attr("data-id");
				var oldName = $(this).parent().siblings("input").attr("value");
				var name = $(this).parent().siblings("input").val();
				if(!name){
					tablePoint.tablePoint("请输入内容");
					return;
				}else{
					if(!menuId){
						var insertData = {
							"name":name,
							"remark":"",
							"status":1,
							"type":2
						};
						//调用保存方法
						ajaxRequest.saveItemMenuFood(insertData, function(response){
							if( response.retCode == 200 ){
								itemMenuNew.initBindData();
								tablePoint.tablePoint(response.msg);
							}else{
								tablePoint.tablePoint(response.msg);
							}
						});
					}else{
						//修改字段值
						var updateParam = {
							"id":menuId,
							"name":name
						};
						//调用修改方法
						ajaxRequest.updateItemMenuFood(updateParam,function(response){
							if(response.retCode == 200){
								itemMenuNew.initBindData();
								tablePoint.tablePoint("修改菜单成功！");
							}else{
								tablePoint.tablePoint(response.msg);
							}
						});
					}
				}
			});
		},
		//删除菜单及关联关系
		deleteMenuClick : function(){
			$(".cg-del").unbind().on("click",function(){
				var $this = $(this);
				var menuId = $(this).parent().siblings("input").attr("data-id");
				if(!menuId){
					$this.parents("li").remove();
				}else{
					
					tablePoint.tablePoint3('删除菜单同时删除已设置的菜品及营业点，确认要删除此菜单吗？',function(){
						ajaxRequest.deleteItemMenuFood(menuId,function(response){
							if(response.retCode == 200){
								tablePoint.tablePoint('删除菜单成功！');
								$this.parents("li").remove();
							}else{
								tablePoint.tablePoint(response.msg);
							}
						});
					})
				}
			});
		},
		//tab页事件
		tabClick : function(){
			$(".cddy").unbind().on("click",function(){
				$("#cddy").show();
				$("#yyd").hide();
				itemMenuNew.bindDishData(_menuId,_virtualShopId);//菜单
			});
			$(".yyd").unbind().on("click",function(){
				$("#yyd").show();
				$("#cddy").hide();
				itemMenuNew.bindOperationgData(_menuId,_virtualShopId);//营业点
			});
		},
		//菜品查询事件
		bindSearchDishesClick : function(){
			$(".foodserach").unbind("click").on("click",function(){
				itemMenuNew.bindItemInfoRelationData();
			});
		},
		//根据营业点ID或者菜单ID查询菜品和自定义分类集合[未关联]
		bindItemInfoRelationData : function(){
//			var param = itemMenuNew.queryParam(num);
//				param.menuId = _menuId;
//				param.virtualShopId = _virtualShopId;
//				param.name = $("#itemName").val();
//				/**
//				 * 1、设置value为pxx的项选中 $(".selector").val("pxx");
//				 * 2、设置text为pxx的项选中 $(".selector").find("option[text='pxx']").attr("selected",true);
//				 * 3、获取当前选中项的value $(".selector").val();
//				 * 4、当前选中的文本值$(".selector").find("option:selected").text();
//				 */
//				//param.categoryId = $("#itemCateName").val();
//				param.categoryName = $("#itemCateName").find("option:selected").text();
				var param = itemMenuNew.queryParam(1);
				param.name = $.trim($("#itemName").val());
				param.categoryId = $.trim($("#categoryId").val());
				param.menuId = _menuId;
				param.virtualShopId = _virtualShopId;
				//根据营业点ID或者菜单ID查询菜品和自定义分类集合{未关联}
				ajaxRequest.customClassificationNotCollection(param,function(response){
					//console.info(response);
					var html = _.template(setUpFoodTpl,response);
					$("#cddy-1").empty().html(html);
					itemMenuNew.initAttrClick();//属性事件
					itemMenuNew.checkAllClick();//全选
					itemMenuNew.bindSearchDishesClick();//菜品查询事件
					itemMenuNew.cancelSetUpFoodClick();//取消菜品设置
					itemMenuNew.saveSetUpFoodClick();//保存菜品设置
					itemMenuNew.btnComboxClick();
				});
		},
		//设置菜品
		setUpFoodClick : function(){
			$(".dycdcp").unbind().on("click",function(){
				itemMenuNew.bindItemInfoRelationData();
			});
		},
		//combox事件{调用组件}
		btnComboxClick : function(){
			$("#gArrow,#categoryName").click(function(){
				categoryJs.bindData(function(){
					var offset = $("#categoryName").offset();
					//$(".goodsCatalogue").attr("style","left:"+offset.left+"px;top:"+(offset.top+33)+"px") 调整菜品分类下拉菜单位置 del by kuangrb 2016-05-18
				});
			});
		},
		//取消菜品
		cancelSetUpFoodClick : function(){
			$(".dycdback").unbind().on("click",function(){
				itemMenuNew.bindData();
			});
		},
		//保存菜品
		saveSetUpFoodClick : function(){
			$(".foodsave").unbind().on("click",function(){
				var itemIds = "";
				$(".addC").each(function(){
					itemIds += $(this).attr("data-id") +",";
				});
				if(itemIds==""){
					tablePoint.tablePoint("请选择菜品");
				}else{
					itemIds = itemIds.substring(0,itemIds.length - 1);//去掉最后一个逗号
					//console.info(itemIds);
					var relationData = {
						"menuId":_menuId,
						"itemId":itemIds,
						"virtualShopId":_virtualShopId
					};
					//调用关联菜品方法
					ajaxRequest.saveItemMenuItemRelation(relationData,function(response){
						if(response.retCode == 200){
							$(".dycdback").click();
							tablePoint.tablePoint(response.msg);
						}else{
							tablePoint.tablePoint(response.msg);
						}
					});
				}
			});
		},
		//删除菜单与菜品关系事件[Info]
		bindDeleteDishesClick : function(){
			$("#cddy .cp_close").click(function(){
				var menuId = $(this).closest("div").attr("data-menuid");
				var itemId = $(this).closest("div").attr("data-id");
				var virtualShopId = $(this).closest("div").attr("data-varshopid");
				tablePoint.tablePoint("确定要删除吗？", function(){
					ajaxRequest.deleteItemInfoFood(menuId,virtualShopId,itemId,function(response){
						if(response.retCode == 200){
							itemMenuNew.bindData();
							tablePoint.tablePoint(response.msg);
							//$(this).parents("li").remove();
						}else{
							tablePoint.tablePoint(response.msg);
						}
					}); 
				}); 
				 
			});
		},
		/******************************************************设置营业点*********************************************************/
		//区域
		bindAreaComboboxData : function(){
			var html = "";
			ajaxRequest.getSelectAreaComboboxData(function(response){
				$.each(response.data,function(k,v){
					html +="<option value='"+v.areaCode+"'>"+v.areaName+"</option>";
				});
				$("#area").append(html);
			});
		},
		//商圈
		bindLocationComboboxData : function(){
			var html ="";
			ajaxRequest.querySelectLocationCombobox(function(response){
				$.each(response.data,function(k,v){
					html +="<option value='"+v.id+"'>"+v.locationName+"</option>";
				});
				$("#businessArea").append(html);
			});
		},
		//营业点数据加载
		bindOperationgData : function(menuId,virtualShopId){
			var param = itemMenuNew.queryParam(1);
			param.menuId = menuId;
			param.vShopId = virtualShopId;
			
			//查询菜单已关联的营业点数据  modify by zhucy 添加注释
			ajaxRequest.listMenuSalerShopStores(param,function(response){
				response["menuId"] = menuId;
				response["vShopId"] = _virtualShopId;
				var html = _.template(operatingFoodTpl,response);
				$("#yyd").html(html);
				itemMenuNew.initAttrClick();//属性
				itemMenuNew.tabClick();//tab事件
				itemMenuNew.setOperationgPointClick();//设置营业点
				itemMenuNew.bindDeleteOperationgClick();//营业点删除
			});
		},
		//查询未关联营业点集合
		queryOperationgRelationData : function(){
			var param = itemMenuNew.queryParam(1);
				param.menuId = _menuId;
				param.vShopId = _virtualShopId;
				param.name = $.trim($("#shopName").val());//营业点名称
				param.areaCode = $.trim($("#area").val());//区域
				param.section = $.trim($("#businessArea").val());//商圈
				param.type = "2";
				
				//查询菜单未关联的营业点信息
				ajaxRequest.listNotMenuSalerShopStores(param,function(response){
					response["menuId"] = _menuId;
					response["vShopId"] = _virtualShopId;
					//console.info(response);
					var html = _.template(setOperatingPointTpl,response);
					$("#cddy-1").html(html);
					itemMenuNew.initAttrClick();//属性事件
					itemMenuNew.checkAllClick();//全选
					itemMenuNew.cancelOperationgPointClick();//取消营业点设置
					itemMenuNew.saveOperationgPointClick();//保存营业点设置
					itemMenuNew.bindSearchOperatingClick();//营业点查询
					itemMenuNew.bindAreaComboboxData();
					itemMenuNew.bindLocationComboboxData();
				});
		},
		//设置营业点
		setOperationgPointClick : function(){
			$(".szyyd").unbind().on("click",function(){
				itemMenuNew.queryOperationgRelationData();//未关联
			});
		},
		//取消营业点设置
		cancelOperationgPointClick : function(){
			$(".yydback").unbind().on("click",function(){
				itemMenuNew.bindData();
			});
		},
		//保存营业点设置
		saveOperationgPointClick : function(){
			$(".yydsave").unbind().on("click",function(){
				var itemIds = "";
				$(".addC").each(function(){
					itemIds += $(this).attr("data-id") +",";
				});
				if(itemIds==""){
					tablePoint.tablePoint("请选择营业点");
				}else{
					itemIds = itemIds.substring(0,itemIds.length - 1);//去掉最后一个逗号
					var relationData = {
						"menuId":_menuId,
						"shopId":itemIds,
						"vShopId":_virtualShopId
					};
					//调用关联营业点方法
					ajaxRequest.batchAddSalerStoreMenuRelation(relationData,function(response){
						if(response.retCode == 200){
							itemMenuNew.initBindData();
							$(".yyd").click();
							//$(".yydsearch").click();
							tablePoint.tablePoint(response.msg);
						}else{
							tablePoint.tablePoint(response.msg);
						}
					});
				}
			});
		},
		//营业点删除
		bindDeleteOperationgClick : function(){
			$("#yyd .cp_close").unbind().on("click",function(){
				var menuId = $(this).closest("div").attr("data-menuid");
				//var itemId = $(this).closest("div").attr("data-id");
				var virtualShopId = $(this).closest("div").attr("data-varshopid");
				var shopId = $(this).closest("div").attr("data-shopid");
				var param = {
					"menuId":menuId,
					"shopId":shopId,
					"vShopId":virtualShopId
				};
				tablePoint.tablePoint("确定要删除吗？", function(){
					ajaxRequest.deleteSalerStoreMenuRelation(param,function(response){
						if(response.retCode == 200){
							$(".yyd").click();
							tablePoint.tablePoint(response.msg);
						}else{
							tablePoint.tablePoint(response.msg);
						}
					});
				}); 
				
			});
		},
		//营业点查询
		bindSearchOperatingClick : function(){
			$(".yydsearch").unbind().on("click",function(){
				itemMenuNew.queryOperationgRelationData();
			});
		},
		//全选与取消
		checkAllClick : function(){
			//全选与取消
			$(".allgetchk").click(function(){
				var lis = $(this).parents("h1").next().find("li");//.length;
				var ico = $(this).parents("h1").next().find(".cp_get");
				$(this).prop("checked")?ico.removeClass("none"):ico.addClass("none");
				$(this).prop("checked")?lis.addClass("addC"):lis.removeClass("addC");//添加类样式，以便获取ID
			});
		},
		//初始化属性
		initAttrClick : function(){
			$(".cookbook-list a").click(function(){
				if($(this).parents("li").siblings().find(".ckbkedit").length >1){
					//tablePoint.tablePoint("未编辑完成！");
					return false;
				}else{
				  //新增
				  if($(this).attr("id")=="addckbk"){
					  var tt = $(".cllist").clone(true).removeClass("cllist none");
					  $(this).parents("li").before(tt);
					  $(this).parents("li").siblings().find("a").removeClass("active");
					  $(this).parents("li").prev().find("a").addClass("active");	
				  }else{
					  $(this).addClass("active").parents("li").siblings().find("a").removeClass("active");
				  }
				}
			});
			//编辑
			$(".cg-edit").click(function(){
				if($(this).parents("li").siblings().find(".ckbkedit").length >1){
					tablePoint.tablePoint("未编辑完成！");
					return false;
				}else{
					$(this).parent().hide().siblings("span").show().siblings("input").addClass("ckbkedit").prop("readonly",false);
				};	
			});
//			//删除
//			$(".cg-del").click(function(){
//				$(this).parents("li").remove();	
//			});
//			//绿色“V”事件
//			$(".ckbk-yes").click(function(){
//				if($(this).parent().siblings("input").val()==''){
//					tablePoint.tablePoint("请输入内容");
//					return false;
//				}else{
//					$(this).parent().hide().siblings("span").show().siblings("input").removeClass("ckbkedit").prop("readonly",true);
//				};
//			});
			//红色“X”事件
			$(".ckbk-del").click(function(){
				//alert($(this).closest("input").attr("data-id") ); 
				//判断当前菜单ID
				if($(this).parent().siblings("input").attr("data-id")!= ""){
					$(this).parent().hide().siblings("span").show().siblings("input").removeClass("ckbkedit").prop("readonly",true);
				}else{
					$(this).parents("li").remove();
				}
			});
			/*鼠标滑动显示操作图标*/
			$(".cookbook-list a").hover(function(){
				if($(this).children(".ckbkedit-ipt").has(":hidden")){
					if($(this).children(".cookbook-ipt").prop("readonly")==false){
						$(this).children(".shopcg-list-edit").hide();
					}else{
						$(this).children(".shopcg-list-edit").show();
					}
				}else{
					$(this).children(".shopcg-list-edit").hide();	
				}
			},function(){
				$(this).children(".shopcg-list-edit").hide();	
			});
			//删除菜品鼠标跟随显示删除按钮
			$(".delcp").hover(function(){
				$(this).find("i").removeClass("none");
			},function(){
				$(this).find("i").addClass("none");
			});
//			//删除菜品事件
//			$(".cp_close").click(function(){
//				$(this).parents("li").remove();
//			});
			//添加菜品事件
			$(".addcp").click(function(){
				$(this).find("i").toggleClass("none");
				$(this).parents("li").toggleClass("addC");//如果存在（不存在）就删除（添加）一个类。
				//新增关联操作
				var m = $(this).parents("ul").find(".cp_get:visible").length;
				var n = $(this).parents("ul").find("li").length;
				if(m !== n){
					$(this).parents("ul").prev("h1").find(".allgetchk").prop("checked",false);
				}else{
					$(this).parents("ul").prev("h1").find(".allgetchk").prop("checked",true);
				}
			});
		}
	};
	
	return itemMenuNew;

});