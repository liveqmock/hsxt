define(function(){
	var ajaxRequest = {
			//查询商城商品分类集合
			getSalerShopCategory : function(param,callback){
				comm.Request({url:'/item/getSalerShopCategory',domain:'sportal'},{
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
			//查询启动中的实体店
			getStartSalerShop : function(param,callback){
				comm.Request({url:'/item/getStartSalerShop',domain:'sportal'},{
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
				comm.Request({url:'/item/queryItemList',domain:'sportal'},{
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
				comm.Request({url:'/item/deleteById',domain:'sportal'},{
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
				comm.Request({url:'/item/publishItemId',domain:'sportal'},{
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
				comm.Request({url:'/item/cancelCheckById',domain:'sportal'},{
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
			//批量商品缺货操作
			batchPutOff: function(param,callback){
				comm.Request({url:'/item/batchPutOff',domain:'sportal'},{
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
			//商品批量上架操作
			batchPutOn: function(param,callback){
				comm.Request({url:'/item/batchPutOn',domain:'sportal'},{
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
				comm.Request({url:'/item/batPublishItem',domain:'sportal'},{
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
				
				comm.Request({url:'/item/batchDeleteItem',domain:'sportal'},{
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
			//查询商品审核原因
			getItemChecks: function(param,callback){
				comm.Request({url:'/item/getItemChecks',domain:'sportal'},{
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