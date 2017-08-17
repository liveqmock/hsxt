define(['text!companyInfoTpl/zyxxbg/ck.html' ],function( ckTpl ){
	return {
		 
		showPage : function(){
			$('#contentWidth_3').html(_.template(ckTpl)) ;			 
			//Todo...
		 	$('#btn_fh').triggerWith('#zyxxbg_jlcx'); 
	
			
				
		}
	}
}); 

 