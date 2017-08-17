define(function(){
	var ajaxRequest = {
		//查询评价信息
		serviceOpenList : function(param, callback) {
			comm.Request({url:'serviceOpenList',domain:'scs'}, {
				type : 'POST',
				data : param,
				dataType : "json",
				success : function(response){
					callback(response)	
				},
				error: function(){
					alert('获得服务公司服务刚开通列表失败！');
				}
			});
		}
	}
	return ajaxRequest;
})