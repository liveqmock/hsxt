define(function(){
	var ajaxRequest = {
			/*查询退换货记录*/
			getRefundByUserId : function(param,callback){
			comm.Request({url:'/refund/getRefundByUserId',domain:'sportal'},{
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
		/*售后状态变更*/
		salerStatus : function(param,callback){
			comm.Request({url:'/refund/salerStatus',domain:'sportal'},{
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
		//查看订单详情
		queryOrderInfo : function(param,callback){
			comm.Request({url:'/refund/queryOrderInfo',domain:'sportal'},{
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
		//查看售后详情
		getRefundDetail : function(param,callback){
			comm.Request({url:'/refund/getRefundDetail',domain:'sportal'},{
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
		/*查询物流公司*/
		listSysLogistic : function(param,callback){
			comm.Request({url:'/salerShop/listSysLogistic',domain:'sportal'},{
				type:'POST',
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