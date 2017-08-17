/**
 * @author zhanghh add 20150915
 * @version 1.0.0
 * @description 菜单管理ajax
 */
define(function(){
	var ajaxRequest = {
		/** 添加菜单记录 */
		saveItemMenuFood : function(param,callback){
			comm.Request({url:'/itemMenuFoodWeb/saveItemMenuFood',domain:'sportal'},{
				type:'POST',
				data:param,
				dataType:"json",
				success:function(response){
					callback(response)	
				},
				error: function(){
					alert('请求数据出错！');
				}
			});
		},
		/** 删除菜单记录 */
		deleteItemMenuFood : function(id,callback){
			comm.Request({url:'/itemMenuFoodWeb/deleteItemMenuFood',domain:'sportal'},{
				type:'POST',
				data:{"id":id},
				dataType:"json",
				success:function(response){
					callback(response)	
				},
				error: function(){
					alert('请求数据出错！');
				}
			});
		},
		/** 启用/禁用菜单 */
		updateItemMenuFoodStatus : function(param,callback){
			comm.Request({url:'/itemMenuFoodWeb/updateItemMenuFoodStatus',domain:'sportal'},{
				type:'POST',
				data:param,
				dataType:"json",
				success:function(response){
					callback(response)	
				},
				error: function(){
					alert('请求数据出错！');
				}
			});
		},
		/** 修改菜单记录 */
		updateItemMenuFood : function(param,callback){
			comm.Request({url:'/itemMenuFoodWeb/updateItemMenuFood',domain:'sportal'},{
				type:'POST',
				data:param,
				dataType:"json",
				success:function(response){
					callback(response)	
				},
				error: function(){
					alert('请求数据出错！');
				}
			});
		},
		/** 获取菜单列表 */
		listItemMenuFood : function(param,callback){
			comm.Request({url:'/itemMenuFoodWeb/listItemMenuFood',domain:'sportal'},{
				type:'POST',
				data:param,
				dataType:"json",
				success:function(response){
					callback(response)	
				},
				error: function(){
					alert('请求数据出错！');
				}
			});
		},
		/** 根据ID获取菜单记录 */
		getItemMenuFoodDetail : function(id,callback){
			comm.Request({url:'/itemMenuFoodWeb/getItemMenuFoodDetail',domain:'sportal'},{
				type:'POST',
				data:{"pid":id},
				dataType:"json",
				success:function(response){
					callback(response)	
				},
				error: function(){
					alert('请求数据出错！');
				}
			});
		},
		/** 获取菜单关联 */
		listItemMenuItemRelation : function(param,callback){
			comm.Request({url:'/itemMenuFoodWeb/listItemMenuItemRelation',domain:'sportal'},{
				type:'POST',
				data:param,
				dataType:"json",
				success:function(response){
					callback(response)	
				},
				error: function(){
					alert('请求获取菜单关联数据出错！');
				}
			});
		},
		/**
		 *  查询商品信息（未关联菜单的餐饮商品）
		 */
		listNotMenuItemInfoFood : function(param,callback){
			comm.Request({url:'/itemMenuFoodWeb/listNotMenuItemInfoFood',domain:'sportal'},{
				type:'POST',
				data:param,
				dataType:"json",
				success:function(response){
					callback(response)	
				},
				error: function(){
					alert('请求查询商品信息（未关联菜单的餐饮商品）数据出错！');
				}
			});
		},
		/** isBoolean:是否有关联菜品 */
		isBoolean : function(menuId,vshopId,callback){
			comm.Request({url:'/itemMenuFoodWeb/isBoolean',domain:'sportal'},{
				type:'POST',
				data:{"menuId":menuId,"vshopId":vshopId},
				dataType:"json",
				success:function(response){
					callback(response)	
				},
				error: function(){
					alert('请求是否有关联菜品数据出错！');
				}
			});
		},
		/** 删除菜单与菜品关系 */
		deleteItemInfoFood : function(menuId,vShopId,itemId,callback){
			comm.Request({url:'/itemMenuFoodWeb/deleteItemInfoFood',domain:'sportal'},{
				type:'POST',
				data:{"menuId":menuId,"vshopId":vShopId,"itemId":itemId},
				dataType:"json",
				success:function(response){
					callback(response)	
				},
				error: function(){
					alert('请求删除菜品记录数据出错！');
				}
			});
		},
		/** 批量删除菜单与菜品关系 */
		batchDeleteItemInfoFood : function(menuId,vShopId,itemIds,callback){
			comm.Request({url:'/itemMenuFoodWeb/batchDeleteItemInfoFood',domain:'sportal'},{
				type:'POST',
				data:{"menuId":menuId,"vshopId":vShopId,"itemIds":itemIds},
				dataType:"json",
				success:function(response){
					callback(response)	
				},
				error: function(){
					alert('请求批量删除菜品记录数据出错！');
				}
			});
		},
		/** 菜单与菜品关联关系 */
		saveItemMenuItemRelation : function(param,callback){
			comm.Request({url:'/itemMenuFoodWeb/saveItemMenuItemRelation',domain:'sportal'},{
				type:'POST',
				data:param,
				dataType:"json",
				success:function(response){
					callback(response)	
				},
				error: function(){
					alert('请求批量删除菜品记录数据出错！');
				}
			});
		},
		/**
	     * 查询商品信息（关联了菜单的餐饮商品）
	     */
		listMenuItemInfoFood : function(param,callback){
			comm.Request({url:'/itemMenuFoodWeb/listMenuItemInfoFood',domain:'sportal'},{
				type:'POST',
				data:param,
				dataType:"json",
				success:function(response){
					callback(response)	
				},
				error: function(){
					alert('查询商品信息（关联了菜单的餐饮商品）数据出错！');
				}
			});
		},
		/** 根据营业点ID或者菜单ID查询菜品和自定义分类集合{已关联} */
		customClassificationCollection : function(param,callback){
			comm.Request({url:'/itemMenuFoodWeb/customClassificationCollection',domain:'sportal'},{
				type:'POST',
				data:param,
				dataType:"json",
				success:function(response){
					callback(response)	
				},
				error: function(){
					alert('根据营业点ID或者菜单ID查询菜品和自定义分类集合数据出错！');
				}
			});
		},
		/** 根据营业点ID或者菜单ID查询未关联的菜品和自定义分类集合 {未关联} */
		customClassificationNotCollection : function(param,callback){
			comm.Request({url:'/itemMenuFoodWeb/customClassificationNotCollection',domain:'sportal'},{
				type:'POST',
				data:param,
				dataType:"json",
				success:function(response){
					callback(response)	
				},
				error: function(){
					alert('根据营业点ID或者菜单ID查询菜品和自定义分类集合数据出错！');
				}
			});
		},
		/** 查询营业点信息（关联了菜单的营业点） */
		listMenuSalerShopStores : function(param,callback){
			comm.Request({url:'/itemMenuFoodWeb/listMenuSalerShopStores',domain:'sportal'},{
				type:'POST',
				data:param,
				dataType:"json",
				success:function(response){
					callback(response)	
				},
				error: function(){
					alert('查询营业点信息（关联了菜单的营业点）数据出错！');
				}
			});
		},
		/** 查询营业点信息（未关联菜单的营业点） */
		listNotMenuSalerShopStores : function(param,callback){
			comm.Request({url:'/itemMenuFoodWeb/listNotMenuSalerShopStores',domain:'sportal'},{
				type:'POST',
				data:param,
				dataType:"json",
				success:function(response){
					callback(response)	
				},
				error: function(){
					alert('查询营业点信息（未关联菜单的营业点）数据出错！');
				}
			});
		},
		/*删除营业点菜单关联关系*/
		deleteSalerStoreMenuRelation : function(param,callback){
			comm.Request({url:'/itemMenuFoodWeb/deleteSalerStoreMenuRelation',domain:'sportal'},{
				type:'POST',
				data:param,
				dataType:"json",
				success:function(response){
					callback(response)	
				},
				error: function(){
					alert('删除营业点菜单关联关系出错！');
				}
			});
		},
		/*批量新增营业点菜单关联关系*/
		batchAddSalerStoreMenuRelation : function(param,callback){
			comm.Request({url:'/itemMenuFoodWeb/batchAddSalerStoreMenuRelation',domain:'sportal'},{
				type:'POST',
				data:param,
				dataType:"json",
				success:function(response){
					callback(response)	
				},
				error: function(){
					alert('批量新增营业点菜单关联关系出错！');
				}
			});
		},
		/** 获取当前登录区域的下拉选列表 */
		getSelectAreaComboboxData : function(callback){
			comm.Request({url:'/salerShopFood/getSelectAreaComboboxData',domain:'sportal'}, {
				type : 'POST',
				dataType : "json",
				success : function(response) {
					callback(response)
				},
				error : function() {
					//alert('获取当前登录的下拉选列表数据出错！');
				}
			});
		},
		/**根据当前登录的城市查询商圈*/
		querySelectLocationCombobox : function(callback){
			comm.Request({url:'/itemMenuFoodWeb/querySelectLocationCombobox',domain:'sportal'}, {
				type : 'POST',
				dataType : "json",
				success : function(response) {
					callback(response)
				},
				error : function() {
					//alert('根据当前登录的城市查询商圈！');
				}
			});
		}
	};
	return ajaxRequest;
});