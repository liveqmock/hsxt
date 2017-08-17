reqConfig.setLocalPath("mail");
reqConfig.setLocalPath("common");

require.config(reqConfig);

define(['GY',  'commLan'], function(GY) {
	var params = location.href.split('param=');
	comm.Request({domain:'apsWeb',url:'checkMailCode'},{
		type:'POST',
		data: {param: params[1] },
		dataType:'json',
		success:function(response){
			if(response.retCode=='22003'){
				comm.alert(response.resultDesc);
			}else{
				if('160136' == response.retCode){
					comm.alert('邮件已过期');
				}else{
					comm.alert(response.data);
				}
			}
			
		},
		error: function(e){
			comm.alert(e);
		}
		
	})
	
	 
		
 
});
