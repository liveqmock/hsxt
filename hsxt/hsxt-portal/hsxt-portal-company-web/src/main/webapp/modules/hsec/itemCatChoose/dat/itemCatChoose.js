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
			/*显示类目*/
			getNextByPid : function(param,callback){
			comm.Request({url:'/item/getNextByPid',domain:'sportal'},{
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
		},	/*读取品牌*/
		initItemPinpaiInfo : function(param,callback){
			comm.Request({url:'/itemInit/initItemPinpaiInfo',domain:'sportal'},{
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
		/** 查询商品库数 */
		getItemTemplateCount : function(param, callback) {
			comm.Request({url:'/itemTemplate/getItemTemplateCount',domain:'sportal'},{
				type : 'POST',
				data : param,
				dataType : "json",
				success : function(response) {
					callback(response)
				},
				error : function() {
					alert('请求数据出错！');
				}
			});
		},
		/** 查询关键字 */
		searchCategoryByStrName : function(param, callback) {
			comm.Request({url:'/item/searchCategoryByStrName',domain:'sportal'},{
				type : 'POST',
				data : param,
				dataType : "json",
				success : function(response) {
					callback(response)
				},
				error : function() {
					alert('请求数据出错！');
				}
			});
		}
	};	
	return ajaxRequest;
});