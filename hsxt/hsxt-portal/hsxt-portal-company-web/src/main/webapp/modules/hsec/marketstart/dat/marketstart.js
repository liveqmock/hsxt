define(function(){
	var ajaxRequest = {
		marketstart : function(param,callback){
			//需判断企业是否实名认证，如未实名认证跳转到互生实名认证页面
			comm.Request({url:'/eShop/saveSalerVirtualShop',domain:'sportal'},{
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
		},
		
		/**
		 * 上传功能
		 */
		upLoadFile : function(imgId,fileType,callback){
	        $.ajaxFileUpload({  
	        	url:comm.domainList.sportal+"files/upload?type="+fileType+"&fileType=image",  
	            secureuri:false,  
	            fileElementId:imgId,        //file的id  
	            dataType:"text",                  //返回数据类型为文本  
	            success:function(data,status){  
	            	callback(data)
	            }  
	        })  
	},
		initMarketInfo : function(param,callback){
			//获取企业信息
			comm.Request({url:'/eShop/initMarketInfo',domain:'sportal'},{
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
		},
		//获取企业门户协议 zhanghh 20150828
		enterpricesServiceContent : function(id,callback){
			comm.Request({url:'/eShop/enterpricesServiceContent',domain:'sportal'},{
				type:'POST',
				data:{"id":id},
				dataType:"json",
				success:function(response){
					callback(response)	
				},
				error: function(){
					alert('获取企业门户协议数据出错！');
				}
			});
		}
	};	
	return ajaxRequest;
});