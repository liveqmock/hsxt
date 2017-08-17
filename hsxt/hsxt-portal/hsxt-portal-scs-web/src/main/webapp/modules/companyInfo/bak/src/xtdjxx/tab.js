define(['text!companyInfoTpl/xtdjxx/tab.html',
		'companyInfoSrc/xtdjxx/xtdjxx' ],function( tab,xtdjxx ){
	return {	 
		showPage : function(){	
			 	
			$('.operationsInner').html(_.template(tab)) ;
			//系统登录信息
			$('#qyxx_xtdjxx').click(function(e) {				 
                xtdjxx.showPage();				 
            }.bind(this)).click();
			
			
			
			 
			 
		} 
	}
}); 

