define(['text!callCenterTpl/hjzxbb/tab.html',
		'callCenterSrc/hjzxbb/hjzxbb'
		], function(tab, hjzxbb){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			
			$('#hjzxbb').click(function(){
				hjzxbb.showPage();
				comm.liActive($('#hjzxbb'));
			}.bind(this)).click();

			
		}
	}	
});