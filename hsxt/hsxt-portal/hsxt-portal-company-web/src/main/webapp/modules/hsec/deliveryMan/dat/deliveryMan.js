define(function(){
	var ajaxRequest = {
			/**
			 * 上传功能
			 */
			upLoadFile : function(imgId,callback){
			        $.ajaxFileUpload({  
			        	url:comm.domainList.sportal+"files/upload?type=product&fileType=image",  
			            secureuri:false,  
			            fileElementId:imgId,        //file的id  
			            dataType:"text",                  //返回数据类型为文本  
			            success:function(data,status){  
			            	callback(data)
			            }  
			        })  
			},
			listSalerShopDeliver : function(param,callback){
			comm.Request({url:'/deliverMan/listSalerShopDeliver',domain:'sportal'},{
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
		//关联实体店
		getDeliverShops : function(param,callback){
			comm.Request({url:'/deliverMan/getDeliverShops',domain:'sportal'},{
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
		 * 根据送货人ID获取送货人信息
		 */
		findSalerShopDeliver : function(param,callback){
			comm.Request({url:'/deliverMan/findSalerShopDeliver',domain:'sportal'},{
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
		 * 修改送货人
		 */
		updateSalerShopDeliver : function(param,callback){
			comm.Request({url:'/deliverMan/updateSalerShopDeliver',domain:'sportal'},{
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
		 *  新增送货人
		 */
		saveSalerShopDeliver : function(param,callback){
			comm.Request({url:'/deliverMan/saveSalerShopDeliver',domain:'sportal'},{
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
		 *  批量删除送货人
		 */
		batchDeleteDeliver : function(param,callback){
			comm.Request({url:'/deliverMan/batchDeleteDeliver',domain:'sportal'},{
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