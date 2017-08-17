define(['text!hsec_shippingListTpl/tab.html',
		'hsec_shippingListSrc/shippingList/shippingList'
		], function(tpl,ajax){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tpl));
			//查询
			$('#wsscgl_yfsz').click(function(e){
				ajax.bindData();
			}).click();
		}
	}
});