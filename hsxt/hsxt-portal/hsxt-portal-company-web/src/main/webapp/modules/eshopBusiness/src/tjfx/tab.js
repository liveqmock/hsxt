define(['text!eshopBusinessTpl/tjfx/tab.html',
		'eshopBusinessSrc/tjfx/ddxstj',
		'eshopBusinessSrc/tjfx/yjsddtj',
		'eshopBusinessSrc/tjfx/tktj'
		], function(tpl, ddxstj, yjsddtj, tktj){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tpl));
			
			//订单销售统计
			$('#tjfx_ddxstj').click(function(e){
				comm.liActive($(this));
				ddxstj.showPage();
			}).click();
			
			//已结算订单统计
			$('#tjfx_yjsddtj').click(function(e){
				comm.liActive($(this));
				yjsddtj.showPage();
			});
			
			//退款统计
			$('#tjfx_tktj').click(function(e){
				comm.liActive($(this));
				tktj.showPage();
			});
		}
	}
});