define(function(){
	var ajaxRequest = {
		jfzhmxcx : function(callback,data){  	//回调参数
				//comm.Request( 'jfzhmxcx'  ,{	//接口别名
				comm.Request( [{url:'jfzhmxcx',domain:'local'}] , {	//接口别
			  	data:data || {}, 
				success:function(response){	
					//alert(111)					
					callback(response);	
				},
				error: function(){
					comm.alert('请求数据出错！');
				}					
			});	
		}			
	};	
	return ajaxRequest;
});
