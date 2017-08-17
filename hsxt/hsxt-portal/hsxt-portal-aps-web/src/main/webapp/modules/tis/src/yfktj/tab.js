define(['text!tisTpl/yfktj/tab.html',
		'tisSrc/yfktj/yfkyjtj',
		'tisSrc/yfktj/yfkkctj'
		], function(tab, yfkyjtj, yfkkctj){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			
			$('#yfkyjtj').click(function(){
				yfkyjtj.showPage();
				comm.liActive($('#yfkyjtj'));
			}.bind(this)).click();
			
			$('#yfkkctj').click(function(){
				yfkkctj.showPage();
				comm.liActive($('#yfkkctj'));
			}.bind(this));
			
		}	
	}	
});