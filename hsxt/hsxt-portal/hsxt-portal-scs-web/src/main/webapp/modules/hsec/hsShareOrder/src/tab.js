define(["text!hsec_hsShareOrderTpl/tab.html"
		,"hsec_hsShareOrderSrc/hsShareOrderActivity"]
		,function(tpl,hsoaAjax){
	return {
		showPage:function(){
			$(".operationsInner").html(_.template(tpl));
			
			$("#hsorder").click(function(){
				hsoaAjax.bindData();
			}).click();
		}
	};	
});