define(['text!eshopBusinessTpl/ddglcy/tab.html',
		'eshopBusinessSrc/ddglcy/cyddgl'
		], function(tpl, cyddgl){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tpl));
			
			//餐饮订单管理
			$('#ddgl_cyddgl').click(function(e){
				comm.liActive($(this), '#ddgl_cyddgl_xq', '#busibox_xq');
				cyddgl.showPage();
			}).click();
		}
	}
});