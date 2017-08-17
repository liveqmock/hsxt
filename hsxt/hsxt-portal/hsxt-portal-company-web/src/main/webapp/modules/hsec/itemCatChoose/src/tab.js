define(['text!hsec_itemCatChooseTpl/tab.html',
        'hsec_itemCatChooseSrc/item/item'],
		function(tab,releaseItem){
	return {	 
		showPage : function(){			
			$('.operationsInner').html(_.template(tab)) ;

			$("#item").click(function(){
				comm.liActive($("#item"));
				releaseItem.bindData();
			}.bind(this)).click();	
			
		}

	}
}); 