define(['text!accountManageTpl/qtkfsl/qqsj3/main.html' ,'accountManageDat/qtkfsl/qqsj' ],function(mxcxTpl ,ajax){
	return {
 
		showPage : function(tabid){			
			//请求数据：2 请求远程接口数据
			ajax.qqsj(function(getData){				 
				$('#main-content > div[data-contentid="'+tabid+'"]').html( _.template(mxcxTpl , getData )  ) ; 
				// To Do ...
				
				
				
			}) 
			
		}
	}
}); 

 