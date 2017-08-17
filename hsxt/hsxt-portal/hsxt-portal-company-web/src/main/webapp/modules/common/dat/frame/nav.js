define(function(){
	var ajaxRequest = {
			getCompanyResNoAndCustID:function(callback){
					comm.Request('getCompanyResNoAndCustID',{
					type:'POST',	
					success:function(response){
						callback(response);	
					},
					error: function(){
						alert('请求数据出错！');
					}
					
				})	
			}
		}
		
	return ajaxRequest;
});