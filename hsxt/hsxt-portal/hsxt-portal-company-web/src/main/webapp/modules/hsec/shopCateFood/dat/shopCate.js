define(function(){
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
	
	queryShopCategory : function(param,callback){
		comm.Request({url:'/food/itemSalerCategory/queryShopCategory',domain:'sportal'},{
			type:'POST',
			data:param,
			dataType:"json",
			success:function(response){
				callback(response);	
			},
			error: function(){
				alert('请求数据出错！');
			}
		});
	},		
	/*删除商品类目*/
	deleteSalerShopCategory : function(param,callback){
	    param = {"salerCatJson":JSON.stringify(param)};
		comm.Request({url:'/food/itemSalerCategory/deleteSalerShopCategory',domain:'sportal'},{
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
	/*新增商品类目*/
	saveSalerShopCategoryNew: function(param,callback){
		comm.Request({url:'/food/itemSalerCategory/saveSalerShopCategoryNew',domain:'sportal'},{
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
	//查询商品列表
	getRelationItem : function(param,callback){
		comm.Request({url:'/food/itemSalerCategory/getRelationItem',domain:'sportal'},{
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
	/*新增商品，商城商品分类关联*/
	addSalerShopCategory : function(param,callback){
		comm.Request({url:'/food/itemSalerCategory/addSalerCategory',domain:'sportal'},{
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
	/*删除商品，商城商品分类关联*/
	delSalerShopCategory : function(param,callback){
		comm.Request({url:'/food/itemSalerCategory/delSalerCategory',domain:'sportal'},{
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
	//查询餐饮自定义类目已经关联的商品
	cateAndItemCollection : function(param,callback){
		comm.Request({url:'/food/itemSalerCategory/cateAndItemCollection',domain:'sportal'},{
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
	//查询未关联商品的平台类目集合
	notRelationCate : function(param,callback){
		comm.Request({url:'/food/itemSalerCategory/notRelationCate',domain:'sportal'},{
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
	
	/*显示类目*/
	getAddBaseData : function(param,callback){
		comm.Request({url:'/food/itemSalerCategory/getAddBaseData',domain:'sportal'},{
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
	//查询商品列表
	queryItemList : function(param,callback){
		comm.Request({url:'/item/getItembyVshopIdAndShopCategoryId',domain:'sportal'},{
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
	sortFoodCategory : function(param,callback){
		comm.Request({url:'/food/itemSalerCategory/sortFoodCategory',domain:'sportal'},{
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
