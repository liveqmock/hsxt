define(function(){
	var ajaxRequest ={
		/**
		 * 查询商品信息（餐饮）
		 */
		listItemInfoFood : function(param,callback){
			comm.Request({url:'listItemInfoFood',domain:'scs'},{
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
		/**
		 * 删除商品（餐饮）
		 */
		deleteItemInfoFood : function(param,callback){
			comm.Request({url:'deleteItemInfoFood',domain:'scs'},{
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
		/**
		 * 批量删除商品（餐饮）
		 */
		deleteItemFoodByIds : function(param,callback){
			comm.Request({url:'deleteItemFoodByIds',domain:'scs'},{
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
		/** 查询类目 */
		queryCategory:function(param,callback){
			comm.Request({url:'queryCategory',domain:'scs'},{
				type:'POST',
				data:param,
				dataType:"json",
				success:function(response){
					callback(response)	
				},
				error: function(){
					//alert('请求数据出错！');
				}
			});
		},
		/** 上架 */
		itemPutOnFood : function(param,callback){
			comm.Request({url:'itemPutOnFood',domain:'scs'},{
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
		/** 下架 */
		itemPutOffFood : function(param,callback){
			comm.Request({url:'itemPutOffFood',domain:'scs'},{
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
		/** 批量上架 */
		batchItemPutOn : function(param,callback){
			comm.Request({url:'batchItemPutOn',domain:'scs'},{
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
		/** 批量下架 */
		batchItemPutOff : function(param,callback){
			comm.Request({url:'batchItemPutOff',domain:'scs'},{
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
	}
	return ajaxRequest;
});