define(["text!hsec_saleSupportTpl/tab.html"
		,"hsec_saleSupportSrc/saleSupportActivity"]
		,function(tpl,saleAjax){
	return {
		showPage : function(){
			$(".operationsInner").html(tpl);
			$('#shlb').click(function(e){
				saleAjax.bindData();
			}).click();
		}
	}
});