define(function(){
	
	var ajaxRequest = {
		//获取待审核,审核通过,审核不通过,全部实体店列表
		getSalerShopList : function(param,callback) {
			comm.Request({url:'listSalerShop',domain:'scs'},{
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
		//申请
		updateSalerShop : function(param,callback) {
			comm.Request({url:'updateSalerShop',domain:'scs'},{
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
		
		//查看实体店详情
		getSalerShopDetail : function(param,callback) {
			comm.Request({url:'findSalerShop',domain:'scs'},{
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
		
		//审核实体店
		pendingSalerShop : function(param,callback) {
			comm.Request({url:'pendingSalershop',domain:'scs'},{
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
		
		//删除营业点
		delSalerShop : function(param,callback) {
			comm.Request({url:'delSalerShop',domain:'scs'},{
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
		
		//查看审核记录详情
		getNewlySalerShopCheckHistory : function(param,callback) {
			comm.Request({url:'getNewlySalerShopCheckHistory',domain:'scs'},{
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
		},
		//获取某级行政区域
		getFirstAreaList:function(param,callback){
			comm.Request({url:'getRootArea',domain:'scs'},{
				type:'POST',
				data:param,
				dataType:'json',
				success:function(response){ callback(response)},
				error:function(){alert('请求数据出错！')}
			});
		}
		
	}
	return ajaxRequest;
});