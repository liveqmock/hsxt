define(['text!eshopInfoTpl/gzsc/tab.html',
		'eshopInfoSrc/gzsc/gzsc'
		], function(tpl, gzsc){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tpl));
			
			//关注收藏
			$('#gzsc_gzsclb').click(function(e){
				comm.liActive($(this));
				gzsc.showPage();
			}).click();
		}
	}
});