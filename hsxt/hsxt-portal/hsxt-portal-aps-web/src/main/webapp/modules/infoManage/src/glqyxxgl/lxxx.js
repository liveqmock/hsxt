define(['text!infoManageTpl/glqyxxgl/lxxx.html'], function(lxxxTpl){
	return {
		showPage : function(obj){
			$('#infobox').html(_.template(lxxxTpl, obj));
			
			/*按钮事件*/
			$('#lxxx_back').triggerWith('#glqyxxwh');
			/*end*/
		}	
	}	
});