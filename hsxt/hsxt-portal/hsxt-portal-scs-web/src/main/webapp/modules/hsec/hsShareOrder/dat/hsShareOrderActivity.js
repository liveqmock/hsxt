define(function(){
	var ajaxRequest = {
		//获取所有晒单
		listShareOrderByParam : function(param,callback){
			comm.Request({url:'listShareOrderByParam',domain:'scs'}, {
				type : 'POST',
				dataType : "json",
				data:param,
				success : function(response){
					callback(response);
				},
				error: function(){
					alert('获取所有晒单出错！');
				}
			});
		},
		//获取所有晒单
		deleteShardeOrderById : function(id,callback){
			comm.Request({url:'deleteShardeOrderById',domain:'scs'}, {
				type : 'POST',
				dataType : "json",
				data:{"id":id},
				success : function(response){
					callback(response);
				},
				error: function(){
					alert('删除晒单出错！');
				}
			});
		}
	};
	return ajaxRequest;
});