define(function() {
	var ajaxRequest = {
			/**
			 * 上传功能
			 */
			upLoadFile : function(imgId,callback){
			        $.ajaxFileUpload({  
			            url:comm.domainList.sportal+"files/upload2?type=shopImg&fileType=image",  
			            secureuri:false,  
			            fileElementId:imgId,        //file的id  
			            dataType : "json",                  //返回数据类型为文本  
			            success:function(data,status){  
			            	callback(data)
			            }  
			        })  
			},
			/** 读取营业点初始化信息 */
			findFoodInit : function(callback) {
				comm.Request({url:'/salerShopFood/findFoodInit',domain:'sportal'}, {
					type : 'POST',
					dataType : "json",
					success : function(response) {
						callback(response)
					},
					error : function() {
						alert('请求数据出错！');
					}
				});
			},
			/** 保存餐饮营业点信息 */
			addFoodShop : function(param, callback) {
				comm.Request({url:'/salerShopFood/addFoodShop',domain:'sportal'}, {
					type : 'POST',
					data : param,
					dataType : "json",
					success : function(response) {
						callback(response)
					},
					error : function() {
						alert('请求数据出错！');
					}
				});
			},
			/** 修改餐饮营业点信息 */
			updateSalerShopStore : function(param, callback) {
				comm.Request({url:'/salerShopFood/updateSalerShopStore',domain:'sportal'}, {
					type : 'POST',
					data : param,
					dataType : "json",
					success : function(response) {
						callback(response)
					},
					error : function() {
						alert('请求数据出错！');
					}
				});
			},
			/** 条件查询营业点信息 */
			listItemInfoFoodQueryParam : function(param, callback){
				comm.Request({url:'/salerShopFood/listItemInfoFoodQueryParam',domain:'sportal'}, {
					type : 'POST',
					data : param,
					dataType : "json",
					success : function(response) {
						callback(response)
					},
					error : function() {
						alert('请求数据出错！');
					}
				});
			},
			/** 获取当前登录的下拉选列表 */
			getSelectAreaComboboxData : function(callback){
				comm.Request({url:'/salerShopFood/getSelectAreaComboboxData',domain:'sportal'}, {
					type : 'POST',
					dataType : "json",
					success : function(response) {
						callback(response)
					},
					error : function() {
						alert('获取当前登录的下拉选列表数据出错！');
					}
				});
			},
			/** 删除单条营业点信息 */
			deleteFoodCounterById : function(param, callback) {
				comm.Request({url:'/salerShopFood/deleteCateringCategoryById',domain:'sportal'}, {
					type : 'POST',
					data : param,
					dataType : "json",
					success : function(response) {
						callback(response)
					},
					error : function() {
						alert('请求数据出错！');
					}
				});
			},
			/** 批量删除营业点信息 */
			deleteCateringCategoryByIds : function(param, callback) {
				comm.Request({url:'/salerShopFood/deleteCateringCategoryByIds',domain:'sportal'}, {
					type : 'POST',
					data : param,
					dataType : "json",
					success : function(response) {
						callback(response)
					},
					error : function() {
						alert('请求数据出错！');
					}
				});
			},
			//获得菜单
			getSalerStoreMenuRelations : function(param, callback) {
				comm.Request({url:'/foodItemMenu/listItemMenuFood',domain:'sportal'}, {
					type : 'POST',
					data : param,
					dataType : "json",
					success : function(response) {
						callback(response)
					},
					error : function() {
						alert('请求数据出错！');
					}
				});
			},
			/** 营业点设置菜单>关联菜单 */
			addSalerStoreMenuRelation :function(param, callback) {
				comm.Request({url:'/foodItemMenu/addSalerStoreMenuRelation',domain:'sportal'}, {
					type : 'POST',
					data : param,
					dataType : "json",
					success : function(response) {
						callback(response)
					},
					error : function() {
						alert('请求数据出错！');
					}
				});
			},
			/** 读取营业点详情 */
			shopFoodInfo :function(param, callback) {
				comm.Request({url:'/salerShopFood/shopFoodInfo',domain:'sportal'}, {
					type : 'POST',
					data : param,
					dataType : "json",
					success : function(response) {
						callback(response)
					},
					error : function() {
						alert('请求数据出错！');
					}
				});
			},
			/** 批量新增营业点菜单关联关系 */
			batchAddStoreMenuRelation : function(param, callback){
				comm.Request({url:'/foodItemMenu/batchAddStoreMenuRelation',domain:'sportal'}, {
					type : 'POST',
					data : param,
					dataType : "json",
					success : function(response) {
						callback(response)
					},
					error : function() {
						alert('请求数据出错！');
					}
				});
			},
			//判断网上商城停止积分活动权限
			// 判断商城状态 2015/12/14 wangte 
			hasRightEdit: function(param,callback){
				comm.Request({url:'/item/hasRightEdit',domain:'sportal'},{
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
			}
	};
	return ajaxRequest;
});