define(['text!companyInfoTpl/lxxx/lxxxxg.html' ],function(lxxxxgTpl ){
	return {
		 
		showPage : function(){
			$('#busibox').html(_.template(lxxxxgTpl)) ;			 
		 	
			 $('#backBt_qiyelianxi').click(function(){
			 		$('#qyxx_lxxx').click();
			 });
				
		}
	}
}); 

 