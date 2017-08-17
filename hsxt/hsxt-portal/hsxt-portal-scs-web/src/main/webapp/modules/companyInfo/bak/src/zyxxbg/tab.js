define(['text!companyInfoTpl/zyxxbg/tab.html',
		'companyInfoSrc/zyxxbg/zyxxbgsq',
		'companyInfoSrc/zyxxbg/zyxxbgcx',
		'companyInfoSrc/zyxxbg/tpwjsc',		
		'companyInfoSrc/zyxxbg/ck'  ],function( tab,zyxxbgsq,zyxxbgcx,tpwjsc,ck ){
	return {	 
		showPage : function(){	 
			$('.operationsInner').html(_.template(tab)) ;
			 
			$('#zyxxbg_bgsq').click(function(e) {				 
                zyxxbgsq.showPage();
                comm.liActive($('#zyxxbg_bgsq'));	
                $('#zyxxbg_ck').css('display','none');	
                $('#zyxxbg_tpwjsc').css('display','none');	
                		 
            }.bind(this)).click();
			
			$('#zyxxbg_jlcx').click(function(e) {				 
                zyxxbgcx.showPage();	
                comm.liActive($('#zyxxbg_jlcx'));	
                $('#zyxxbg_ck').css('display','none');	
                $('#zyxxbg_tpwjsc').css('display','none');					 
            }.bind(this)); 
            
            $('#zyxxbg_tpwjsc').click(function(e) {				 
                tpwjsc.showPage();	
                comm.liActive($('#zyxxbg_tpwjsc'));	
                $('#zyxxbg_ck').css('display','none');	
                $('#zyxxbg_tpwjsc').css('display','');					 
            }.bind(this)); 
            
            $('#zyxxbg_ck').click(function(e) {				 
                ck.showPage();	
                comm.liActive($('#zyxxbg_ck'));	
                $('#zyxxbg_ck').css('display','');	
                $('#zyxxbg_tpwjsc').css('display','none');					 
            }.bind(this)); 
            
			 
		} 
	}
}); 

