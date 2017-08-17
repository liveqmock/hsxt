define(['text!serviceMsgManageTpl/mbfh/sjdxmbsh.html'], function(sjdxmbshTpl){
	return {
		showPage : function(){
			$('#busibox').html(_.template(sjdxmbshTpl));	
		}	
	}	
});