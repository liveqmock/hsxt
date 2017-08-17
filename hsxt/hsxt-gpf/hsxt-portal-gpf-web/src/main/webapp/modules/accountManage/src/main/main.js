define(['text!accountManageTpl/main/main.html' ],function(mainTpl ){
	return { 
		showPage : function(tabid){
			$('#main-content > div[data-contentid="'+tabid+'"]').html(_.template(mainTpl)) ;
		    
		    
		     
		 	
			 
			
		   	
		 
 		   	
			
				
		}
	}
}); 

 