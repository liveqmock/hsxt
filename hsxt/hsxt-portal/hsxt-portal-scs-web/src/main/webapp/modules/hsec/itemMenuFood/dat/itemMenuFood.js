define(function(){
	var ajaxRequest ={
		//餐饮菜单管理
		listItemMenuFood:function(param,callback){
			comm.Request({url:'listItemMenuFood',domain:'scs'},{
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