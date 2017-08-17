define(['text!goodsManageTpl/fbsp/tab.html',
		'goodsManageSrc/fbsp/fbsp'
		], function(tpl, fbsp){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tpl));
			
			//发布商品
			$('#fbsp_fbsp').click(function(e){
				comm.liActive($(this));
				fbsp.showPage();
			}).click();
		}
	}
});