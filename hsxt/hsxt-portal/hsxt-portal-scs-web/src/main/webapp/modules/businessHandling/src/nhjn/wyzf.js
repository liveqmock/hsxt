define(['text!businessHandlingTpl/nhjn/wyzf.html'],function( wyzfTpl ){
	return {	 	
		showPage : function(){
			$('#contentWidth_2').html(_.template(wyzfTpl)) ;
			//Todo...
		 	$('#wyzf_xg').triggerWith('#nhjn_nhjn'); 
			
			
			
		  
 			
			$('#wyzf_fk').click(function(){
					comm.alert({width:500,content:'缴纳系统使用年费订单已提交银行，请确认支付结果。'})
			});	
		}
	}
}); 

 