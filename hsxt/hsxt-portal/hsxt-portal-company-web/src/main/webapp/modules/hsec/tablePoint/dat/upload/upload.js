define(function(){
	var ajaxRequest = {
		upload : function(imgId,callback){
			$.ajaxFileUpload({  
				url:comm.domainList.sportal+"files/upload2?type=webhead&fileType=image",  
		        secureuri:false,  
		        fileElementId:imgId,        //file的id
		        dataType:"json",                  //返回数据类型为文本  
		        success:function(data,status){  
		        	callback(data)
		        }  
			});
		}
	};	
	return ajaxRequest;
});

