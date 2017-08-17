define(['text!callCenterTpl/dhgdcx/tab.html',
		'callCenterSrc/dhgdcx/dhgdcx'
		], function(tab, dhgdcx){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			
			$('#dhgdcx').click(function(){
				dhgdcx.showPage();
				comm.liActive($('#dhgdcx'));
			}.bind(this)).click();

			
		}
	}	
});