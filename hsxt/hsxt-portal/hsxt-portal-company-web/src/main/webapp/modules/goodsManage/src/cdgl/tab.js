define(['text!goodsManageTpl/cdgl/tab.html',
		'goodsManageSrc/cdgl/cdcx'
		], function(tpl, cdcx){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tpl));
			
			//菜单查询
			$('#cdgl_cdcx').click(function(e){
				comm.liActive($(this), '#cdgl_glcp');
				$('#cdgl_glcp a').text('关联菜品');
				cdcx.showPage();
			}).click();
		}
	}
});