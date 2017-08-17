define(["text!hsec_virtualShopTpl/tab.html"
		,"hsec_virtualShopSrc/virtualShopActivity"]
		,function(tpl,ajax){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tpl));
			ajax.bindData();
		}
	}
});