define(['text!serviceMsgManageTpl/mbfh/yjmbsh.html'], function(yjmbshTpl){
	return {
		showPage : function(){
			$('#busibox').html(_.template(yjmbshTpl));	
		}	
	}	
});