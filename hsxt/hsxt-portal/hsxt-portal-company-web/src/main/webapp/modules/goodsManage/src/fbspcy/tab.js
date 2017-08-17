define(['text!goodsManageTpl/fbspcy/tab.html',
		'goodsManageSrc/fbspcy/fbsp'
		], function(tpl, fbsp){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tpl));
			
			//发布商品
			$('#fbspcy_fbsp').click(function(e){
				comm.liActive($(this));
				fbsp.showPage();
			}).click();
		}
	}
});