define(['text!accountManageTpl/sub/jfzh.html' ],function(jfzhTpl ){
	return {
 
		showPage : function(tabid){
			$('#main-content > div[data-contentid="'+tabid+'"]').html(jfzhTpl) ;
		    
		 
		 
 		   	
			
				
		}
	}
}); 

 