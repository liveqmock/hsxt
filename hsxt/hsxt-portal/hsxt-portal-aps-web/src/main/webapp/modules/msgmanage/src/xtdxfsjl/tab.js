define(['text!msgmanageTpl/xtdxfsjl/tab.html',
		'msgmanageSrc/xtdxfsjl/xtdxfsjl'
		], function(tab, xtdxfsjl){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			
			$('#xtdxfsjl').click(function(){
				xtdxfsjl.showPage();
				comm.liActive($('#xtdxfsjl'));
			}.bind(this)).click();
			
		}	
	}	
});