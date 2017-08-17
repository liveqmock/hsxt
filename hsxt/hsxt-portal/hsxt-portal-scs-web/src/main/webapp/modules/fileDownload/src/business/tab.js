define(['text!fileDownloadTpl/business/tab.html',
		'fileDownloadSrc/business/business_cn',
		'fileDownloadSrc/business/business_hk',
		'fileDownloadSrc/business/business_tw',
		'fileDownloadSrc/business/business_mc' 
		 ],function( tab,business_cn ,business_hk ,business_tw , business_mc ){
	return {	 
		showPage : function(){	
			 	
			$('.operationsInner').html(_.template(tab)) ;
			//系统登录信息
			$('#business_cn').click(function(e) {	
				comm.liActive($('#business_cn') );			 
                business_cn.showPage();				 
            }.bind(this)).click();
			
			$('#business_hk').click(function(e) {		
				comm.liActive($('#business_hk') );			 
                business_hk.showPage();				 
            }.bind(this)) ; 
           
           $('#business_tw').click(function(e) {		
				comm.liActive($('#business_tw') );			 
                business_tw.showPage();				 
            }.bind(this)) ;  
            
           $('#business_mc').click(function(e) {		
				comm.liActive($('#business_mc') );			 
                business_mc.showPage();				 
            }.bind(this)) ;  
           
           
            
		} ,
		showLi : function(liObj){			
			//liObj.css('display','');
			//liObj.siblings('li').css('display','none');
		} 
		
		
	}
}); 
