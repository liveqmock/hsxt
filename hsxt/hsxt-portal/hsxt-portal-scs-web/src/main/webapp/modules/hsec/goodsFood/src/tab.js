define(["text!hsec_goodsFoodTpl/tab.html"
		,"hsec_goodsFoodSrc/goodsFood"]
		,function(tpl,foodAjax){
	return {
		showPage : function(){
			$('.operationsInner').html(tpl);
			$('#cycp').click(function(e){
				foodAjax.bindData();
			}).click();
		}
	}
});