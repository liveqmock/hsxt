define(['text!platformDebitTpl/kkfh/tab.html',
		'platformDebitSrc/kkfh/kkfh'
		], function(tab, kkfh){
	return {
		showPage : function(){
			$('.operationsInner').html(tab);
			
			$('#platformDebit_kkfh').click(function(){
				kkfh.showPage();
				comm.liActive($('#platformDebit_kkfh'));
			}.bind(this)).click();
				
		}	
	}	
});
