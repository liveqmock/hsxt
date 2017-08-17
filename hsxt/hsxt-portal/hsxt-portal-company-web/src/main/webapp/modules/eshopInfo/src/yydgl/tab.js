define(['text!eshopInfoTpl/yydgl/tab.html',
		'eshopInfoSrc/yydgl/yydlb'
		], function(tpl, yydlb){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tpl));
			
			//营业点
			$('#yydgl_yydlb').click(function(e){
				comm.liActive($(this), '#yydgl_xzxgyyd', '#busibox_xzxg');
				yydlb.showPage();
			}).click();
		}
	}
});