define(['text!infoManageTpl/glqyxxgl/xtzcxx.html'], function(xtzcxxTpl){
	return {
		showPage : function(obj){
			$('#infobox').html(_.template(xtzcxxTpl, obj));
			
			/*按钮事件*/
			$('#xtzcxx_back').triggerWith('#glqyxxwh');
			/*end*/
		}	
	}	
});