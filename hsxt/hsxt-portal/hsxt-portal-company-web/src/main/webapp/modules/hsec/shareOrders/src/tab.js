define(['text!hsec_shareOrdersTpl/tab.html',
		'hsec_shareOrdersSrc/shareOrder'
		], function(tpl,ajax){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tpl));
			//菜单查询
			$('#sdgl_sd').click(function(e){
				ajax.bindData();
			}).click();
		}
	}
});