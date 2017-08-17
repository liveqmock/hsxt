define(["text!hsec_salerShopTpl/tab.html"
		,"hsec_salerShopSrc/salerShop"]
		,function(tpl,ajax){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tpl));
			$('#fwgs_yydxx').click(function(e){
				ajax.bindData();
			}).click();
		}
	}
});