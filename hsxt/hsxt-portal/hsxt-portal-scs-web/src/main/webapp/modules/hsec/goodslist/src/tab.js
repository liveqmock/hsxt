define(["text!hsec_goodslistTpl/goodMain.html"
		,"hsec_goodslistSrc/goodslistsrc"
		,"hsec_goodslistSrc/goodFood"]
		,function(tpl,ajaxList,ajaxFood){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tpl));
			//零售商品
			$("#retailGoods").click(function(){
				comm.liActive($("#retailGoods"));
				ajaxList.initData();
			}.bind(this)).click();
			//餐饮商品
			$("#beverageGoods").click(function(){
				comm.liActive($("#beverageGoods"));
				ajaxFood.bindGoodFoodData();
			}.bind(this));
		}
	}
});