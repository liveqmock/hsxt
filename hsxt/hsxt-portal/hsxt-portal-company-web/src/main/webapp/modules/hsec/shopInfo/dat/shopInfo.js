define(function() {
	var ajaxRequest = {
			/**
			 * 上传功能
			 */
			upLoadFile : function(imgId,callback){
			        $.ajaxFileUpload({  
			            url:comm.domainList.sportal+"files/upload?type=product&fileType=image",  
			            secureuri:false,  
			            fileElementId:imgId,        //file的id  
			            dataType:"text",                  //返回数据类型为文本  
			            success:function(data,status){  
			            	callback(data)
			            }  
			        })  
			},
		/** 保存实体店信息 */
		saveSalerShop : function(param, callback) {
			comm.Request({url:'/salerShop/saveSalerShop',domain:'sportal'}, {
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
		/** 查询实体店集合 */
		listSalerShop : function(param, callback) {

			comm.Request({url:'/salerShop/listSalerShop',domain:'sportal'},{
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
		/** 查询实体店对象 */
		findSalerShop : function(param, callback) {
			comm.Request({url:'/salerShop/findSalerShop',domain:'sportal'},{
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
		/** 所属商圈查询 */
		listMapSalerShop : function(param, callback) {
			comm.Request({url:'/salerShop/listMapSalerShop',domain:'sportal'},{
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
		/** 修改实体店 */
		updateSalerShop : function(param, callback) {
			comm.Request({url:'/salerShop/updateSalerShop',domain:'sportal'}, {
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
		/** 获取全部首页分类 */
		getAllMainPageCategory : function(param, callback) {
			comm.Request({url:'/salerShop/getAllMainPageCategory',domain:'sportal'}, {
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
		/** 更新实体店状态 */
		updateSalerShopStatus : function(param, callback) {
			comm.Request({url:'/salerShop/updateSalerShopStatus',domain:'sportal'}, {
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
		/** 删除最后一个启用中的实体店 */
		deleteSalerShop : function(param, callback) {
			comm.Request({url:'/salerShop/deleteSalerShop',domain:'sportal'}, {
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
		/** 营业点设置菜单>菜单列表查询 */
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
		}
	};
	return ajaxRequest;
});