define(["text!hsec_roleTpl/tab.html"
		,"hsec_roleSrc/role"]
		,function(tpl,ajax){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tpl));
			//查询
			$('#xtyhgl_jsgl').click(function(e){
				ajax.bindData();
			}).click();
		}
	}
});