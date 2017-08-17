define(['text!companyInfoTpl/smrz/tab.html',
		'companyInfoSrc/smrz/smrz' ,
		'companyInfoSrc/smrz/smrz_zt'],function( tab,smrz,smrz_ztts ){
	return {	 
		showPage : function(){	 
			$('.operationsInner').html(_.template(tab)) ;
			//系统登录信息
			$('#smrz_smrzxx').click(function(e) {				 
                smrz.showPage();				 
            }.bind(this)).click();
			
			$('#smrz_ztts').click(function(e) {				 
                smrz_ztts.showPage();				 
            }.bind(this));
			 
			 
		} 
	}
}); 

