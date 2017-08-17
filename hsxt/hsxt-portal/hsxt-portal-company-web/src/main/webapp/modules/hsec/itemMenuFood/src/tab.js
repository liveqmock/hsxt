define(['text!hsec_itemMenuFoodTpl/main/tab.html',
		'hsec_itemMenuFoodSrc/itemMenuNew',
		'hsec_marketstartSrc/marketstart'
		], function(tpl, ajax, vshopAjax){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tpl));
			vshopAjax.getVshopStatus();
			//菜单查询
			$('#cdgl_cdcx').click(function(e){
				ajax.bindData();
			}).click();
		}
	}
});