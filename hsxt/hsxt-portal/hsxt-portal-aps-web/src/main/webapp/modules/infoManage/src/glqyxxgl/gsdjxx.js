define(['text!infoManageTpl/glqyxxgl/gsdjxx.html'], function(gsdjxxTpl){
	return {
		showPage : function(obj){
			$('#infobox').html(_.template(gsdjxxTpl, obj));
			
			/*按钮事件*/
			$('#gsdjxx_back').triggerWith('#glqyxxwh');
			/*end*/
		}	
	}	
});