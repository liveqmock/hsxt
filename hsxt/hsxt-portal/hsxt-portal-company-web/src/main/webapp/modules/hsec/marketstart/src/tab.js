define(['text!hsec_marketstartTpl/tab.html',
        'hsec_marketstartSrc/marketstart'],
		function(tab,marketstart){
	return {	 
		showPage : function(){			
			$('.operationsInner').html(_.template(tab)) ;

			$("#marketinfo").click(function(){
				comm.liActive($("#marketinfo"));
				marketstart.bindData();
			}.bind(this)).click();	
			
		}

	}
}); 