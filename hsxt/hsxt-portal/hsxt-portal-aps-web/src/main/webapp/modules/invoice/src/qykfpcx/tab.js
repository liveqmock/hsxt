define(['text!invoiceTpl/qykfpcx/tab.html',
		'invoiceSrc/qykfpcx/qykfpcx'
		], function(tab, qykfpcx){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			
			$('#qykfpcx').click(function(){
				qykfpcx.showPage();
				comm.liActive($('#qykfpcx'), '#ckqyxx');
			}.bind(this)).click();
			
		}
	}	
});