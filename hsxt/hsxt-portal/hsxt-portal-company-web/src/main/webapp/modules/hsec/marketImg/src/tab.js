define(['text!hsec_marketImgTpl/tab.html',
		'hsec_marketImgSrc/marketImg'
		], function(tpl,ajax){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tpl));
			//查询
			$('#wsscgl_sctp').click(function(e){
				ajax.bindData();
			}).click();
		}
	}
});