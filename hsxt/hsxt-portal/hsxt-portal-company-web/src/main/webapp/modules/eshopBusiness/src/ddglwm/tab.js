define(['text!eshopBusinessTpl/ddglwm/tab.html',
		'eshopBusinessSrc/ddglwm/wmddgl'
		], function(tpl, wmddgl){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tpl));
			
			//外卖订单管理
			$('#ddgl_wmddgl').click(function(e){
				comm.liActive($(this), '#ddgl_wmddgl_xq', '#busibox_xq');
				wmddgl.showPage();
			}).click();
		}
	}
});