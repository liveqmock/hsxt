define(['text!hsec_deliveryManTpl/tab.html',
		'hsec_deliveryManSrc/deliveryMan'
		], function(tpl,ajax){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tpl));
			//菜单查询
			$('#wsscgl_shy').click(function(e){
				ajax.bindData();
			}).click();
		}
	}
});