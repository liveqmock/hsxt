define(['text!eshopInfoTpl/shy/tab.html',
		'eshopInfoSrc/shy/shy'
		], function(tpl, shy){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tpl));
			
			//送货员
			$('#shy_shy').click(function(e){
				comm.liActive($(this));
				shy.showPage();
			}).click();
		}
	}
});