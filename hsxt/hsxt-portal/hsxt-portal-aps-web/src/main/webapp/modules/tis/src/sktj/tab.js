define(['text!tisTpl/sktj/tab.html',
		'tisSrc/sktj/qysktj',
		'tisSrc/sktj/wsyhzfskmx'
		], function(tab, qysktj, wsyhzfskmx){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			
			$('#qysktj').click(function(){
				qysktj.showPage();
				comm.liActive($('#qysktj'));
			}.bind(this)).click();
			
			$('#wsyhzfskmx').click(function(){
				wsyhzfskmx.showPage();
				comm.liActive($('#wsyhzfskmx'));
			}.bind(this));
			
		}
	}	
});