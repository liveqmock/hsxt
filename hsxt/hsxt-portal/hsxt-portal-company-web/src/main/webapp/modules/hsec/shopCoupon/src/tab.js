define(['text!hsec_shopCouponTpl/tab.html',
		'hsec_shopCouponSrc/shopCoupon'
		], function(tpl,ajax){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tpl));
			//查询
			$('#wsscgl_dkqsz').click(function(e){
				ajax.bindData();
			}).click();
		}
	}
});