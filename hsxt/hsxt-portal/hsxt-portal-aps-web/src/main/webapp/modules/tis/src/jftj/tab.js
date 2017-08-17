define(['text!tisTpl/jftj/tab.html',
		'tisSrc/jftj/jffphztj',
		'tisSrc/jftj/jfzzfphztj',
		'tisSrc/jftj/jfsyhztj'
		], function(tab, jffphztj, jfzzfphztj, jfsyhztj){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			
			$('#jffphztj').click(function(){
				jffphztj.showPage();
				comm.liActive($('#jffphztj'));
			}.bind(this)).click();
			
			$('#jfzzfphztj').click(function(){
				jfzzfphztj.showPage();
				comm.liActive($('#jfzzfphztj'));
			}.bind(this));
			
			$('#jfsyhztj').click(function(){
				jfsyhztj.showPage();
				comm.liActive($('#jfsyhztj'));
			}.bind(this));
			
		}	
	}	
});