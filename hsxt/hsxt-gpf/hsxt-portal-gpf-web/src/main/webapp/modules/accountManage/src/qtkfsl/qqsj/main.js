define(['text!accountManageTpl/qtkfsl/qqsj/main.html' ],function(mxcxTpl ){
	return {
 
		showPage : function(tabid){
			
			//请求数据：1 模板页面的加载
			$('#main-content > div[data-contentid="'+tabid+'"]').html( mxcxTpl   ) ; 
			
			/*
			var getData = {"title",'2015学生成绩',"xm":'张三',"xb":"男","km":"数学","fs":80 } ;			
			$('#main-content > div[data-contentid="'+tabid+'"]').html( _.template(mxcxTpl , getData )  ) ; 
		     */
			//Todo...		   	
			
		    
			
				
		}
	}
}); 

 