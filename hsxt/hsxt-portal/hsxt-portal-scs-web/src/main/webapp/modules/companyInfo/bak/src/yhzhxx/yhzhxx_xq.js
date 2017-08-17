define(['text!companyInfoTpl/yhzhxx/yhzhxx_xq.html' ],function(yhzhxxxqTpl ){
	return {
		 
		showPage : function(){
			$('#contentWidth_4').html(_.template(yhzhxxxqTpl)) ;			 
			//Todo...
 	
			
			//$('#modifyBt_yinghang').triggerWith('#qyxx_yhzhxxxg');
			
			$('#btn_back').triggerWith('#qyxx_yhzhxx');
				
		}
	}
}); 

 