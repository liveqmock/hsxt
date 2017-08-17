define(['text!mytaskTpl/wddbgdlb/tab.html',
		'mytaskSrc/wddbgdlb/wddbgdlb'
		], function(tab,wddbgdlb){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			
			$('#wddbgdlb').click(function(){
				wddbgdlb.showPage();
				comm.liActive($('#wddbgdlb'),'#cknr');
			}.bind(this)).click();
			
		}	
	}	
});