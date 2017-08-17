define(['text!eshopInfoTpl/jbxx/tab.html',
		'eshopInfoSrc/jbxx/jbxx'
		], function(tpl, jbxx){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tpl));
			
			$('#wsscxx_jbxx').click(function(e){
				//基本信息
				jbxx.showPage();
			}).click();
		}
	}
});