define(['text!eshopInfoTpl/dkqsz/tab.html',
		'eshopInfoSrc/dkqsz/dkqgzlb'
		], function(tpl, dkqgzlb){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tpl));
			
			//抵扣券规则列表
			$('#dkqgzlb').click(function(e){
				comm.liActive($(this));
				dkqgzlb.showPage();
			}).click();
		}
	}
});