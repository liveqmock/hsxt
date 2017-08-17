define(['text!tisTpl/hbzhtj/tab.html',
		'tisSrc/hbzhtj/hbzhsrhztj',
		'tisSrc/hbzhtj/hbzhzchztj',
		'tisSrc/hbzhtj/fwgslwfmxb'
		], function(tab, hbzhsrhztj, hbzhzchztj, fwgslwfmxb){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			
			$('#hbzhsrhztj').click(function(){
				hbzhsrhztj.showPage();
				comm.liActive($('#hbzhsrhztj'));
			}.bind(this)).click();
			
			$('#hbzhzchztj').click(function(){
				hbzhzchztj.showPage();
				comm.liActive($('#hbzhzchztj'));
			}.bind(this));
			
			$('#fwgslwfmxb').click(function(){
				fwgslwfmxb.showPage();
				comm.liActive($('#fwgslwfmxb'));
			}.bind(this));
			
		}	
	}	
});