define(["text!hsec_itemMenuFoodTpl/tab.html"
		,"hsec_itemMenuFoodSrc/itemMenuFood"]
		,function(tpl,itemMenuFoodAjax){
	return {
		showPage : function(){
			$('.operationsInner').html(tpl);
			//餐饮订单
			$("#cycd").click(function(){
				comm.liActive($("#cycd"));
				itemMenuFoodAjax.bindData();
			}.bind(this)).click();
		}
	}
});