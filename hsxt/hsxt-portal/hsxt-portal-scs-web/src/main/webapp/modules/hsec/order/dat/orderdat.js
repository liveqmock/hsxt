define(function(){
	var ajaxRequest ={
			//查询订单列表
			getOrderList:function(param,callback){
				comm.Request({url:'orderList',domain:'scs'},{
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
			//订单详情
			getOrderDetail:function(param,callback){
				comm.Request({url:'orderDetail',domain:'scs'},{
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
			getUrl : function(callback) {
				comm.Request({url:'getUrl',domain:'scs'}, {
					type : 'POST',
					dataType : "json",
					success : function(response){
						callback(response);
					},
					error: function(){
						alert('查询图片服务器路径出错！');
					}
				});
			}/*,
			getLogisticByRefid : function(param,callback){
				comm.Request({url:'getLogisticByRefid',domain:'scs'},{
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
			}*/
			,
			/************************餐饮订单开始******************************/
			/**
			 * 订单列表
			 */
			queryOrderFoods : function(param,callback){
				comm.Request({url:'queryOrderFoods',domain:'scs'},{
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
			 * 订单详情
			 */
			queryOrderFoodsDetail : function(param,callback){
				comm.Request({url:'queryOrderFoodsDetail',domain:'scs'},{
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
})