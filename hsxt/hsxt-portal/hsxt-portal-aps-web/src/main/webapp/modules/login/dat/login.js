define(function(){
	var ajaxRequest = {
		personLogin : function(param,callback){
			alert('111111111111111');
			$.ajax({
                    type: "get",
                    async: false,
                    data: {
                         "postalcode": "10504",
                        "country": "US"
                    },
                    url: "http://www.geonames.org/postalCodeLookupJSON",
                    dataType: "jsonp",
                    jsonp: "callback",
                    success: function(json) {
						alert('222222222222');
                        var data = json.postalcodes[0];
                        for (var e in data) {
                            alert(e + "--->" + data[e])
                        }
                    },
                    error: function(XMLHttpRequest, textStatus, errorThrown) {
						alert('333333333333333');
                        alert(XMLHttpRequest.status);
                        alert(XMLHttpRequest.readyState);
                        alert(textStatus);
                    }
 
                });
				
		},loginOff : function(callback){
			comm.Request({url:'/login/loginOff',domain:'ecportal'},{
				type:'POST',
				dataType:"json",
				success:function(response){
					callback(response);	
				},
				error: function(){
					alert('请求数据出错！');
				}
				
			})	
		},
		updateLogin :function(param,callback){
			comm.Request({url:'/login/updateLogin',domain:'ecportal'},{
				type:'POST',
				dataType:"json",
				data:param,
				success:function(response){
					callback(response);	
				},
				error: function(){
					alert('请求数据出错！');
				}
				
			})	
		},
		companyLogin : function(param,callback){
			comm.Request({url:'/companyLogin/companyLogin/',domain:'sportal'},{
				type:'POST',
				dataType:"json",
				data:param,
				success:function(response){
					callback(response);	
				},
				error: function(){
					alert('请求数据出错！');
				}
				
			})	
		},
		updateConfirmProtocl : function(callback){
			comm.Request({url:'/login/updateConfirmProtocl',domain:'ecportal'},{
				type:'POST',
				dataType:"json",
				success:function(response){
					callback(response);	
				},
				error: function(){
					alert('请求数据出错！');
				}
				
			})	
		}
	};	
	return ajaxRequest;
});;urn ajaxRequest;
});