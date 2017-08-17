define(['text!infoManageTpl/glqyxxgl/yhzhxx.html'], function(yhzhxxTpl){
	return {
		showPage : function(){
			$('#infobox').html(_.template(yhzhxxTpl));
			
			/*按钮事件*/
			$('#yhzhxx_back').triggerWith('#glqyxxwh');
			/*end*/
		}	
	}	
});