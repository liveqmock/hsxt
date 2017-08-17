define(["text!hsec_shopCouponManagerTpl/tab.html"
		,"hsec_shopCouponManagerSrc/shopCouponActivity"]
		,function(tpl,couponAjax){
	return {
		showPage:function(){
			$(".operationsInner").html(_.template(tpl));
			
			$("#coupon").unbind().on('click',function(){
				couponAjax.bindData();
			}).click();
		}
	}
});