define(['text!companyInfoTpl/yhzhxx/yhzhxx_sc.html' ],function(yhzhxxscTpl ){
	return {
		 
		showPage : function(){
			$('#contentWidth_4').html(_.template(yhzhxxscTpl)) ;			 
			//Todo...
 
			
			//返回
			$('#btn_back').triggerWith('#qyxx_yhzhxx');
			
			
				
		}
	}
}); 

 