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
			
			/*保存商品信息*/
			addItem : function(param,callback){
			comm.Request({url:'/item/addItem',domain:'sportal'},{
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
		/*修改商品信息*/
		modifyItem : function(param,callback){
			comm.Request({url:'/item/modifyItem',domain:'sportal'},{
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
		initItemInfo : function(param,callback){
			comm.Request({url:'/itemInit/initItemInfo',domain:'sportal'},{
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
		initItemProp : function(param,callback){
			comm.Request({url:'/itemInit/initItemProp',domain:'sportal'},{
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
		/*添加商品时初始化方法 */
		initItemInfoNew : function(param,callback){
			comm.Request({url:'/itemInit/initItemInfoNew',domain:'sportal'},{
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
		listSysLogistic : function(param,callback){
			comm.Request({url:'/salerShop/listSysLogistic',domain:'sportal'},{
				type:'POST',
				dataType:"json",
				success:function(response){
					callback(response)	
				},
				error: function(){
					alert('请求数据出错！');
				}
			});
		},
		/*保存运费模板信息*/
		saveSalerStorage : function(param,callback){
			comm.Request({url:'/eShop/saveSalerStorage',domain:'sportal'},{
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
		/*查看商品时初始化方法*/
		getDetailById : function(param,callback){
			comm.Request({url:'/itemInit/getDetailById',domain:'sportal'},{
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
		//判断网上商城停止积分活动权限
		hasRightEdit: function(param,callback){
			comm.Request({url:'/item/hasRightEdit',domain:'sportal'},{
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