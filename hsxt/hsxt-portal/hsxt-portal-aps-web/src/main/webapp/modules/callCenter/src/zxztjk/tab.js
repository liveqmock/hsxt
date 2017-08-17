define(['text!callCenterTpl/zxztjk/tab.html',
		'callCenterSrc/zxztjk/zxztjk',
		'callCenterSrc/zxztjk/zxlbsz'/*,
		'callCenterSrc/zxgl/gzzsz',
		'callCenterSrc/zxgl/rcap'*/
		], function(tab, zxztjk, zxlbsz/*, gzzsz, rcap*/){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			
			$('#zxztjk').click(function(){
				zxztjk.showPage();
				comm.liActive($('#zxztjk'));
			}.bind(this)).click();
			
			$('#zxlbsz').click(function(){
				zxlbsz.showPage();
				comm.liActive($('#zxlbsz'));
			}.bind(this));
			
			/*$('#gzzsz').click(function(){
				gzzsz.showPage();
				comm.liActive($('#gzzsz'));
			}.bind(this));
			
			$('#rcap').click(function(){
				rcap.showPage();
				comm.liActive($('#rcap'));
			}.bind(this));*/
			
		}	
	}	
});