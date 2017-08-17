define(function(){
	ajaxRequest = {
		/**
		 * 初始化服务
		 */
		provisioningFood : function(param,callback){
			comm.Request({url:'/provisioningFoodWeb/provisioningFood',domain:'sportal'},{
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
		 * 开通服务
		 */
		openService : function(param,callback){
			comm.Request({url:'/provisioningFoodWeb/openService',domain:'sportal'},{
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
		 * 关闭服务
		 */
		closedService : function(param,callback){
			comm.Request({url:'/provisioningFoodWeb/closedService',domain:'sportal'},{
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