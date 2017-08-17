define(['text!msgmanageTpl/xtdxmb/tab.html',
		'msgmanageSrc/xtdxmb/xtdxmb'
		], function(tab, xtdxmb){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			
			$('#xtdxmb').click(function(){
				xtdxmb.showPage();
				comm.liActive($('#xtdxmb'), '#xzmb, #xgmb, #tymb, #qymb');
			}.bind(this)).click();
		}	
	}	
});