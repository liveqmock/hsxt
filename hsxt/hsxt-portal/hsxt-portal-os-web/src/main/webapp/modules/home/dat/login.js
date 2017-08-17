define(function() {
	return {
		getToken : function(callback, data) { // 回调参数
			comm.Request( 
					{url : 'getToken',domain : 'osWeb'} , 
					{ data : data,
				      success : function(response) {
					callback(response);
				      },
				      error : function() {
					  comm.alert('请求数据出错！');
				    }
			}

			);
		},
		
		login : function(callback, data) { // 回调参数
			comm.Request( {
				url : 'login',
				domain : 'osWeb'
			} , { // 接口别
				data : data,
				dataType:"json",
				type:'POST',
				success : function(response) {
					callback(response);
				},
				error : function() {
					comm.alert('请求数据出错！');
				}
			}

			);
		} ,
		
		getValidCodeUrl : function(){
			return comm.domainList.osWeb + comm.UrlList.getCheckCode;
			
		},
		
		isLogin:function(params, callback){
			return comm.requestFun("isLogin", params, callback, comm.lang("login"));
			//return comm.getCommBsGrid(gridId,"addCheckBalance",params,comm.lang("checkBalance"),detail,del, add, edit);
		}

	};
});