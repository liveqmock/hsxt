define(['text!goodsManageTpl/spfl/tab.html',
		'goodsManageSrc/spfl/spfl'
		], function(tpl, spfl){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tpl));
			
			//商品分类
			$('#spfl_spfl').click(function(e){
				comm.liActive($(this), '#tjzh_xzxgzh');
				spfl.showPage();
			}).click();
		}
	}
});