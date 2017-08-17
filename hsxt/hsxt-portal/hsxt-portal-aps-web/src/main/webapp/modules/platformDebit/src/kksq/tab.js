define(['text!platformDebitTpl/kksq/tab.html',
		'platformDebitSrc/kksq/kksq'
		], function(tab, kksq){
	return {
		showPage : function(){
			$('.operationsInner').html(tab);
			
			$('#platformDebit_kksq').click(function(){
				kksq.showPage();
				comm.liActive($('#platformDebit_kksq'));
			}.bind(this)).click();
				
		}	
	}	
});
