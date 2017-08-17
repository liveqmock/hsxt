define(['text!toolorderTpl/shddgl/gjtkddyw.html'], function(gjtkddywTpl){
	return {
		showPage : function(){
			$('#busibox').html(_.template(gjtkddywTpl));	
		}	
	}	
});