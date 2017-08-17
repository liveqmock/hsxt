define(function(){
	var ajaxRequest = {
		qqsj : function(callback,data){  	//回调参数
				comm.Request( 'test'  ,{	//接口别名
				//comm.Request( [{url:'test',domain:'local'}] , {	//接口别
			  	data:data || {}, 
				success:function(response){	
					//alert(111)					
					callback(response);	
				},
				error: function(){
					comm.alert('请求数据出错！');
				}					
			});	
		},
					
	};	
	return ajaxRequest;
});
