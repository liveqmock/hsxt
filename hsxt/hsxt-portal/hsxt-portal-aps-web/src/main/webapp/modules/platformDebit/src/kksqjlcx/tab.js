define(['text!platformDebitTpl/kksqjlcx/tab.html',
		'platformDebitSrc/kksqjlcx/kksqjlcx'
		], function(tab, kksqjlcx){
	return {
		showPage : function(){
			$('.operationsInner').html(tab);
			
			$('#platformDebit_kksqjlcx').click(function(){
				kksqjlcx.showPage();
				comm.liActive($('#platformDebit_kksqjlcx'));
			}.bind(this)).click();

				
		}	
	}	
});
