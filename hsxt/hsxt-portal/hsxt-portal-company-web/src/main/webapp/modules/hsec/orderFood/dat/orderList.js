define(function(){
	var ajaxRequest = {
			//查询订单list
			queryOrders : function(param,callback){
				// /food/order/queryOrderFoodList
				comm.Request({url:'/food/orders/orderList',domain:'sportal'},{
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
			
			//确认订单
			acceptOrder : function(param,callback){
				// /food/order/queryOrderFoodList
				comm.Request({url:'/food/orders/acceptOrder',domain:'sportal'},{
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
			
			//查询订单详情
			queryOrderDetail : function(param,callback){
				// /food/order/queryOrderFoodList
				comm.Request({url:'/food/orders/orderDetail',domain:'sportal'},{
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
			
			//打单结算
			sendOrderToBuyer : function(param,callback){
				comm.Request({url:'/food/orders/sendOrderToBuyer',domain:'sportal'},{
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
			
			//查询送货员
			listDeliver : function(param,callback){		
				comm.Request({url:'/food/orders/listDeliver',domain:'sportal'},{
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
			
			//确认送餐
			confirmDeliveryFood : function(param,callback){			
				comm.Request({url:'/food/orders/confirmDeliveryFood',domain:'sportal'},{
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
			
			//企业确认店内消费者就餐
			customerTakingMeals : function(param,callback){			
				comm.Request({url:'/food/orders/customerTakingMeals',domain:'sportal'},{
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
					
			//企业确定自提订单
			takeOwn : function(param,callback){			
				comm.Request({url:'/food/orders/takeOwn',domain:'sportal'},{
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
			
			//确认外卖订单付款，交易完成
			confirmWMorder : function(param,callback){			
				comm.Request({url:'/food/orders/confirmWMorder',domain:'sportal'},{
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
			
			//查询自定义商城分类
			getCustomCategory : function(param,callback){				
				comm.Request({url:'/food/orders/getCustomCategory',domain:'sportal'},{
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
			
			//查询关联菜单商品
			listCategoryFood : function(param,callback){			
				comm.Request({url:'/food/orders/listCategoryFood',domain:'sportal'},{
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
			
			//继续点菜
			takeOrder : function(param,callback){			
				comm.Request({url:'/food/orders/takeOrder',domain:'sportal'},{
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
			
			//删除订单详情	
			delOrderDetail : function(param,callback){			
				comm.Request({url:'/food/orders/delOrderDetail',domain:'sportal'},{
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
			
			
			//企业确认消费者取消订单
			confirmCancelOrder : function(param,callback){			
				comm.Request({url:'/food/orders/confirmCancelOrder',domain:'sportal'},{
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
			
			//企业取消订单
			confirmCancelInOrder : function(param,callback){			
				comm.Request({url:'/food/orders/confirmCancelInOrder',domain:'sportal'},{
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
			//店内消费现金结账
			confirmPayInOrder : function(param,callback){			
				comm.Request({url:'/food/orders/confirmPayInOrder',domain:'sportal'},{
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
			updateOrderDetail : function(param,callback){				
				comm.Request({url:'/food/orders/updateOrderDetail',domain:'sportal'},{
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
			
			getItemCustomCategory : function(param,callback){				
				comm.Request({url:'/food/orders/getItemCustomCategory',domain:'sportal'},{
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
			
			//查询营业点
			getSalerShopStores : function(param,callback){				
				comm.Request({url:'/food/orders/getSalerShopStores',domain:'sportal'},{
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
			/** 购买单个商品的最大数量key_餐饮 */
			getAddFoodCount : function(callback){
				comm.Request({url:'/food/orders/getAddFoodCount',domain:'sportal'},{
					type:'POST',
					dataType:"json",
					success:function(response){					
						callback(response)	
					},
					error: function(){
						alert('请求购买单个商品的最大数量出错！');
					}
				});
			},
			//企业拒接订单
			sallerRefuseOrder : function(param,callback){			
				comm.Request({url:'/food/orders/sallerRefuseOrder',domain:'sportal'},{
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