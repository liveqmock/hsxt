define(['text!goodsManageTpl/tjzh/tab.html',
		'goodsManageSrc/tjzh/tjzhlb'
		], function(tpl, tjzhlb){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tpl));
			
			//推荐组合列表
			$('#tjzh_tjzhlb').click(function(e){
				comm.liActive($(this), '#tjzh_xzxgzh');
				tjzhlb.showPage();
			}).click();
		}
	}
});