define(["text!hsec_administratorTpl/tab.html"
		,"hsec_administratorSrc/administrator"]
		,function(tpl,ajax){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tpl));
			//查询
			$('#xtyhgl_czygl').click(function(e){
				ajax.bindData();
			}).click();
		}
	}
});