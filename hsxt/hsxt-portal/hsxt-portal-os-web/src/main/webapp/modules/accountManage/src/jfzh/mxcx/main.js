define(['text!accountManageTpl/jfzh/mxcx/mxcx.html' ],function(mxcxTpl ){
	return {
 
		showPage : function(tabid){
			$('#main-content > div[data-contentid="'+tabid+'"]').html(_.template(mxcxTpl)) ;
		 
			//Todo...		   	
		
		   	
		   	//跳转到某标签（data-tabid）
		   	$('#btnNew').triggerTab(5);
		   	//可以在跳转时写处理事件
		   	$('#btnNew2').triggerTab(6,function(){
				comm.alert('可以在这里写处理逻辑') ;
			}); 
			
				
		}
	}
}); 

 