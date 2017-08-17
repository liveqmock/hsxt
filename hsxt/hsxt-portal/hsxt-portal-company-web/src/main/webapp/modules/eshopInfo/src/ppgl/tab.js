define(['text!eshopInfoTpl/ppgl/tab.html',
		'eshopInfoSrc/ppgl/sppp'
		], function(tpl, sppp){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tpl));
			
			//商品品牌
			$('#ppgl_sppp').click(function(e){
				comm.liActive($(this));
				sppp.showPage();
			}).click();
		}
	}
});