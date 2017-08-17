define(['text!debitTpl/jykgl/jykzlz.html'
		], function(jykzlzTpl){
	return {
		showPage : function(){
			$('#busibox').html(_.template(jykzlzTpl));	
		}	
	}	
});