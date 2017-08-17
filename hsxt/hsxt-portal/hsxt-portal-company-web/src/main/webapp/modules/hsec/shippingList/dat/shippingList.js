define(function(){
	var ajaxRequest = {
		/*查询运费模板信息*/
		listShipping : function(param,callback){
			comm.Request({url:'/eShop/listSalerStorage',domain:'sportal'},{
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
		},
		/*删除运费模板信息*/
		deleteShipping : function(param,callback){
			comm.Request({url:'/eShop/deleteSalerStorage',domain:'sportal'},{
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
		/*验证模板名称*/
		checkSalerStorage : function(param,callback){
			comm.Request({url:'/eShop/checkSalerStorage',domain:'sportal'},{
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
		/*保存运费模板信息*/
		saveSalerStorage : function(param,callback){
			comm.Request({url:'/eShop/saveSalerStorage',domain:'sportal'},{
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
		/*修改运费模板信息*/
		updateSalerStorage : function(param,callback){
			comm.Request({url:'/eShop/updateSalerStorage',domain:'sportal'},{
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