define(function(){
	var ajaxRequest = {
			/**
			 * 上传功能
			 */
			upLoadFile : function(imgId,callback){
			        $.ajaxFileUpload({  
			        	url:comm.domainList.sportal+"files/upload?type=vshop&fileType=image",  
			            secureuri:false,  
			            fileElementId:imgId,        //file的id  
			            dataType:"text",                  //返回数据类型为文本  
			            success:function(data,status){  
			            	callback(data)
			            }  
			        })  
			},
			/*查询图片集合*/
			allImgList : function(param,callback){
				comm.Request({url:'/marketImg/allImgList',domain:'sportal'},{
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
			/*添加图片 */
			imgAdd : function(param,callback){
				comm.Request({url:'/marketImg/imgAdd',domain:'sportal'},{
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
			/*删除图片 */
			imgDelete : function(param,callback){
				comm.Request({url:'/marketImg/imgDelete',domain:'sportal'},{
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
			/*修改排序 */
			updateSortMy : function(param,callback){
				comm.Request({url:'/marketImg/updateSortMy',domain:'sportal'},{
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