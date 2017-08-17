define(['text!platformDebitTpl/kkcx/tab.html',
		'platformDebitSrc/kkcx/kkcx'
		], function(tab, kkcx){
	return {
		showPage : function(){
			$('.operationsInner').html(tab);
			
			$('#platformDebit_kkcx').click(function(){
				kkcx.showPage();
				comm.liActive($('#platformDebit_kkcx'));
			}.bind(this)).click();
				
		}	
	}	
});
