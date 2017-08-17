define(["text!hsec_orderTpl/orderMain.html"
		,"hsec_orderSrc/ordersrc"
		,"hsec_orderSrc/orderFood"]
		,function(tpl,ajaxList,ajaxFood){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tpl));
			//餐饮订单
			$("#beverageOrders").click(function(){
				comm.liActive($("#beverageOrders"));
				ajaxFood.bindOrderFoodData();
			}.bind(this)).click();
			//零售订单
			$("#retailOrders").click(function(){
				comm.liActive($("#retailOrders"));
				ajaxList.initData();
			}.bind(this));
		}
	}
});