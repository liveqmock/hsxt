define(['text!platformDebitTpl/kkcxfh/tab.html',
		'platformDebitSrc/kkcxfh/kkcxfh'
		], function(tab, kkcxfh){
	return {
		showPage : function(){
			$('.operationsInner').html(tab);
			
			$('#platformDebit_kkcxfh').click(function(){
				kkcxfh.showPage();
				comm.liActive($('#platformDebit_kkcxfh'));
			}.bind(this)).click();
				
		}	
	}	
});
