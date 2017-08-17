define(['text!companyInfoTpl/smrz/smrz_zt.html' ],function(smrz_ztTpl ){
	return {
		 
		showPage : function(){
			$('#contentWidth_3').html(_.template(smrz_ztTpl)) ;			 
			//Todo...
		 	//重新实名认证
			$('#smrz_csrz').click(function(){
					$('#smrz_zt_tpl').addClass('none');
					$('#smrz_ztts_tpl').removeClass('none');
			});
		 
			
			
			
			
			
			
				
		}
	}
}); 

 