define(['text!toolorderTpl/ptdggj/tab.html',
		'toolorderSrc/ptdggj/ptdggj',
		'toolorderSrc/ptdggj/ptdggjfh',
		'toolorderLan'
		], function(tab, ptdggj, ptdggjfh){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			
			$('#ptdggj').click(function(){
				ptdggj.showPage();
				comm.liActive($('#ptdggj'));
			}.bind(this)).click();
			
			$('#ptdggjfh').click(function(){
				ptdggjfh.showPage();
				comm.liActive($('#ptdggjfh'));
			}.bind(this));
			
		}	
	}	
});