define(['text!workflowTpl/gzzsz/tab.html',
		'workflowSrc/gzzsz/gzzsz'
		], function(tab, gzzsz){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			
			$('#gzzsz').click(function(){
				gzzsz.showPage();
				comm.liActive($('#gzzsz'));
			}.bind(this)).click();
		}
	}	
});