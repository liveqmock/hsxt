define(['text!coDeclarationTpl/csyw/sbxx.html'], function(sbxxTpl){
	return{
		showPage : function(num){
			$('#infobox').html(_.template(sbxxTpl,{num:num}));	
			
			/*按钮事件*/
			$('#tgqy_sbxx_cancel').triggerWith('#tgqycsyw');
			
			$('#cyqy_sbxx_cancel').triggerWith('#cyqycsyw');
			
			$('#fwgs_sbxx_cancel').triggerWith('#fwgscsyw');
			
			/*end*/
		}	
	}
});