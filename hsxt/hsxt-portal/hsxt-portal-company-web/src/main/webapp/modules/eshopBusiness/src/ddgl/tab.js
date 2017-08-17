define(['text!eshopBusinessTpl/ddgl/tab.html',
		'eshopBusinessSrc/ddgl/ddgl'
		], function(tpl, ddgl){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tpl));
			
			//订单管理
			ddgl.showPage();
		}
	}
});