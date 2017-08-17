define(['text!hsec_shopInfoFoodTpl/shopInfoFoodMain/tab.html',
		'hsec_shopInfoFoodSrc/shopInfoFoodMain/shopInfoFoodMain'
		], function(tpl,ajax){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tpl));
			//营业点查询
			$('#wsscgl_yydlb').click(function(e){
				ajax.bindData();
			}.bind(this)).click();
		}
	}
});