define(['text!hsec_provisioningFoodTpl/tab.html',
		'hsec_provisioningFoodSrc/provisioning'
		], function(tpl,ajax){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tpl));
			//查询
			$('#wsscgl_fwkt').click(function(e){
				ajax.bindData();
			}).click();
		}
	}
});