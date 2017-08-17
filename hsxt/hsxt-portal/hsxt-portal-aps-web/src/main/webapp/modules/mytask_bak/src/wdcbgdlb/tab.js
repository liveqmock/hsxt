define(['text!mytaskTpl/wdcbgdlb/tab.html',
		'mytaskSrc/wdcbgdlb/wdcbgdlb'
		], function(tab, wdcbgdlb){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			
			$('#wdcbgdlb').click(function(){
				wdcbgdlb.showPage();
				comm.liActive($('#wdcbgdlb'),'#cknr');
			}.bind(this)).click();
			
		}	
	}	
});