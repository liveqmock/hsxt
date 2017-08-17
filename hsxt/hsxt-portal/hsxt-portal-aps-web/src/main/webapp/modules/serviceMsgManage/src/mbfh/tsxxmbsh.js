define(['text!serviceMsgManageTpl/mbfh/tsxxmbsh.html'], function(tsxxmbshTpl){
	return {
		showPage : function(){
			$('#busibox').html(_.template(tsxxmbshTpl));	
		}	
	}	
});