define(['text!hsec_shopCateFoodTpl/tab.html',
		'hsec_shopCateFoodSrc/shopCateNew'
		], function(tpl,ajax){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tpl));
			ajax.init(1);
		}
	}
});