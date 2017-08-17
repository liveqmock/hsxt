define(['text!hsec_itemInfoFoodTpl/tab.html',
        'hsec_itemInfoFoodSrc/releaseItem'],
		function(tab,releaseItem){
	return {	 
		showPage : function(){			
			$('.operationsInner').html(_.template(tab)) ;

			$("#itemFood").click(function(){
				comm.liActive($("#itemFood"));
				releaseItem.bindData();
			}.bind(this)).click();	
			
		}

	}
}); 