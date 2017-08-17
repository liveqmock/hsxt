define(['text!goodsManageTpl/spcx/tab.html',
		'goodsManageSrc/spcx/lssplb',
		'goodsManageSrc/spcx/cysplb'
		], function(tpl, lssplb, cysplb){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tpl));
			
			//零售商品列表
			$('#spcx_lssplb').click(function(e){
				comm.liActive($(this), '#spcx_edit');
				lssplb.showPage();
			}).click();
			
			//餐饮商品列表
			$('#spcx_cysplb').click(function(e){
				comm.liActive($(this), '#spcx_edit');
				cysplb.showPage();
			});
		}
	}
});