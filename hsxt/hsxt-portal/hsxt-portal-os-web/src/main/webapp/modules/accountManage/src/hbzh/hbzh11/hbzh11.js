define(['text!accountManageTpl/hbzh/hbzh11/hbzh11.html' ],function(hbzh11Tpl ){
	return {
 
		showPage : function(tabid){
			$('#main-content > div[data-contentid="'+tabid+'"]').html(_.template(hbzh11Tpl)) ;
		    
		    $('#btnNew').triggerTab(5);
		   	
		   	$('#btnNew2').triggerTab(6,function(){
				comm.alert('可以在这里写处理逻辑')
			}); 
 		   	 
		 	
			
			
				
		}
	}
}); 

 