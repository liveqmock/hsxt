define(function(){
	/*保存修改数据*/
	var ajaxRequest = {
			/**
			 * 获取网上商城首页
			 */
			getVirtualShopPage : function(param,callback){
				comm.Request({url:'/eShop/getVirtualShopPage',domain:'sportal'},{
					type:'POST',
					data:param,
					dataType:"JSON",
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
		marketmodifyUpdate : function(param,callback){
			comm.Request({url:'/eShop/updateSalerVirtualShop',domain:'sportal'},{
				type:'POST',
				data:param,
				dataType:"JSON",
				success:function(response){
					
					callback(response)	
				},
				error: function(){
					alert('请求数据出错！');
				}
			});
		},
		/*读取商城数据*/
		marketmodifyShow : function(param,callback){			
		comm.Request({url:'/eShop/findSalerVirtualShop',domain:'sportal'},{
			type:'POST',
			data:param,
			dataType:"JSON",
			success:function(response){	
				callback(response)	
			},
			error: function(){
				alert('请求数据出错！');
			}
		});
	},
	/*读取商城评分*/
	marketmodifyComment : function(param,callback){	
	comm.Request({url:'/commentRatec/getShopCommentStatisticsByShopID',domain:'sportal'},{
		type:'POST',
		data:param,
		dataType:"JSON",
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