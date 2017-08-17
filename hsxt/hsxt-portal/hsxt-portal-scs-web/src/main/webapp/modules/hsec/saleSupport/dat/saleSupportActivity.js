define(function(){
	var ajaxRequest = {
		//查询售后信息
		findSaleSupports : function(param, callback) {
			comm.Request({url:'findSaleSupports',domain:'scs'}, {
				type : 'POST',
				data : param,
				dataType : "json",
				success : function(response){
					callback(response)	
				},
				error: function(){
					alert('查询售后信息出错！');
				}
			});
		},
		//处理投诉
		serviceProcComplain : function(param, callback){
			comm.Request({url:'serviceProcComplain',domain:'scs'}, {
				type : 'POST',
				data : param,
				dataType : "json",
				success : function(response){
					callback(response)	
				},
				error: function(){
					alert('服务公司处理投诉异常！');
				}
			});
		},
		
		//根据售后ID查询物流信息  2015-10-15 xiongzx add
		getLogisticByRefid : function(param, callback) {
			comm.Request({url:'/saleSupport/getLogisticByRefid',domain:'scs'}, {
				type : 'POST',
				data : param,
				dataType : "json",
				success : function(response){
					callback(response)	
				},
				error: function(){
					alert('根据售后ID查询物流信息出错！');
				}
			});
		},
		
		//根据类型查询数据字典
		getDictionarysByType : function(callback){
			comm.Request({url:'/saleSupport/getDictionarysByType',domain:'scs'}, {
				type : 'POST',
				data : null,
				dataType : "json",
				success : function(response){
					callback(response)	
				},
				error: function(){
					alert('根据类型查询数据字典异常！');
				}
			});
		}
	}
	return ajaxRequest;
})