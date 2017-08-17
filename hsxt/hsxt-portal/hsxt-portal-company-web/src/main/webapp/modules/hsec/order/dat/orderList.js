define(function(){
	var ajaxRequest = {
			//查询订单
			queryOrders : function(param,callback){
			comm.Request({url:'/order/queryOrders',domain:'sportal'},{
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
		//备货中
		salerOrderStocking : function(param,callback){
		comm.Request({url:'/order/salerOrderStocking',domain:'sportal'},{
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
	//买家取消订单，商家确认同意
	confirmCancelBySaler : function(param,callback){
	comm.Request({url:'/order/confirmCancelBySaler',domain:'sportal'},{
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
		//查询物流列表
		getLogisticList : function(param,callback){
			comm.Request({url:'/order/getLogisticList',domain:'sportal'},{
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
		//发货，送货
		confirmDeliveryBySaler : function(param,callback){
			comm.Request({url:'/order/confirmDeliveryBySaler',domain:'sportal'},{
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
		//查询送货人员
		querySalerShopDeliver : function(param,callback){
			comm.Request({url:'/order/querySalerShopDeliver',domain:'sportal'},{
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
		//修改运费
		changePostageBySaler : function(param,callback){
			comm.Request({url:'/order/changePostageBySaler',domain:'sportal'},{
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
		//商家对后付款，现金交易订单支付确定操作
		confirmPayBySaler : function(param,callback){
			comm.Request({url:'/order/confirmPayBySaler',domain:'sportal'},{
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
		//取消订单
		cancelOrderBySaler : function(param,callback){
			comm.Request({url:'/order/cancelOrderBySaler',domain:'sportal'},{
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
		//查询订单信息和对应的订单详情列表
		queryOrderInfo : function(param,callback){
			comm.Request({url:'/order/queryOrderInfo',domain:'sportal'},{
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
		/** 查询营业点集合 */
		queryStartSalerShop : function(callback) {
			comm.Request({url:'/salerShop/queryStartSalerShop',domain:'sportal'},{
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
		/** 自提码确定 */
		takeToOfflineStore : function(param, callback) {
			comm.Request({url:'/order/takeToOfflineStore',domain:'sportal'},{
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
		//企业删除订单［回收站可查询］
		deleteOrdersBySaler : function(param, callback) {
			comm.Request({url:'/order/deleteOrdersBySaler',domain:'sportal'},{
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
		//回收订单
		backOrdersBySaler : function(param, callback) {
			comm.Request({url:'/order/backOrdersBySaler',domain:'sportal'},{
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
		//订单延迟
		delayDeliver : function(param, callback) {
			comm.Request({url:'/order/delayDeliver',domain:'sportal'},{
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
		//企业永久删除订单
		deletePermOrdersBySaler : function(param, callback){
			comm.Request({url:'/order/deletePermOrdersBySaler',domain:'sportal'},{
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
		//获取延迟收货时间
		getDelayTime : function(callback){
			comm.Request({url:'/order/getDelayTime',domain:'sportal'},{
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
		//获取延迟收货的次数
		getDelayCount : function(callback){
			comm.Request({url:'/order/getDelayCount',domain:'sportal'},{
				type : 'POST',
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