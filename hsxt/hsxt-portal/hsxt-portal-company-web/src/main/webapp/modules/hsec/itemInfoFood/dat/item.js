define(function(){
	var ajaxRequest = {
			/**
			 * 上传功能
			 */
			upLoadFile : function(imgId,callback){
		        $.ajaxFileUpload({  
		        	url:comm.domainList.sportal+"files/upload?type=food&fileType=image",  
		            secureuri:false,  
		            fileElementId:imgId,        //file的id  
		            dataType:"text",                  //返回数据类型为文本  
		            success:function(data,status){  
		            	callback(data)
		            }  
		        })  
			},
			//初始化发布商品相关数据
			initItemInfo: function(param,callback){
				comm.Request({url:'/food/itemInfo/initItemInfo',domain:'sportal'},{
					type:'POST',
					data:param,
					dataType:"json",
					success:function(response){
						callback(response)	
					},
					error: function(){
						//alert('请求数据出错！');
					}
				});
			},
			queryCategory:function(param,callback){
				comm.Request({url:'/food/itemInfo/queryCategory',domain:'sportal'},{
					type:'POST',
					data:param,
					dataType:"json",
					success:function(response){
						callback(response)	
					},
					error: function(){
						//alert('请求数据出错！');
					}
				});
			},
			saveItemFood:function(param,callback){
				comm.Request({url:'/food/itemInfo/saveItemFood',domain:'sportal'},{
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
			queryPropBycategoryId : function(param,callback){
				comm.Request({url:'/food/itemInfo/queryPropBycategoryId',domain:'sportal'},{
					type:'POST',
					data:param,
					dataType:"json",
					success:function(response){
						callback(response)	
					},
					error: function(){
						//alert('请求数据出错！');
					}
				});
			}

	};	
	return ajaxRequest;
});