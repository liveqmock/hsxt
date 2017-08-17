define(['text!eshopInfoTpl/yfsz/tab.html',
		'eshopInfoSrc/yfsz/yflb'
		], function(tpl, yflb){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tpl));
			
			//运费列表
			$('#yfsz_yflb').click(function(e){
				comm.liActive($(this));
				yflb.showPage();
			}).click();
		}
	}
});