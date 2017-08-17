define(["text!hsec_serviceOpenTpl/tab.html"
		,"hsec_serviceOpenSrc/serviceOpenActivity"],function(tpl,serviceOpenAjax){
	return {
		showPage : function(){
			$(".operationsInner").html(tpl);
			$('#provi').click(function(e){
				serviceOpenAjax.bindData();
			}).click();
		}
	}
});