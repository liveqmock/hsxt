define(function(){
	var ajaxRequest = {
			//查询商城商品分类集合
			getSalerShopCategory : function(param,callback){
				comm.Request({url:'',domain:'sportal'},{
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
			queryItemList: function(param,callback){
				comm.Request({url:'/food/itemInfo/getItemInfo',domain:'sportal'},{
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
			//根据商品ID删除记录
			deleteById: function(param,callback){
				comm.Request({url:'/food/itemInfo/deleteItemFoodById',domain:'sportal'},{
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
			
			//单条商品发布
			publishItemId: function(param,callback){
				comm.Request({url:'',domain:'sportal'},{
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
			
			//单条商品取消审核
			cancelCheckById: function(param,callback){
				comm.Request({url:'',domain:'sportal'},{
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
			//批量菜品下架操作
			batchPutOff: function(param,callback){
				comm.Request({url:'/food/itemInfo/batchItemPutOff',domain:'sportal'},{
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
			//单个菜品下架操作  zhanghh 20151107 add
			itemPutOff: function(param,callback){
				comm.Request({url:'/food/itemInfo/itemPutOff',domain:'sportal'},{
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
			//单个菜品上架操作 zhanghh 20151107 add
			itemPutOn: function(param,callback){
				comm.Request({url:'/food/itemInfo/itemPutOn',domain:'sportal'},{
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
			//批量菜品上架操作
			batchPutOn: function(param,callback){
				comm.Request({url:'/food/itemInfo/batchItemPutOn',domain:'sportal'},{
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
			
			//商品批量发布操作
			batPublishItem: function(param,callback){
				comm.Request({url:'',domain:'sportal'},{
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
			//商品批量删除
			batchDeleteItem: function(param,callback){
				
				comm.Request({url:'/food/itemInfo/deleteItemFoodByIds',domain:'sportal'},{
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
			//判断网上商城停止积分活动权限
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
			},
			//商品类目查询
			queryCategory:function(param,callback){
				comm.Request({url:'/food/itemInfo/queryCategory',domain:'sportal'},{
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
			}

	};	
	return ajaxRequest;
});