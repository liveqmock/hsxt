define(['text!companyInfoTpl/lxxx/tab.html',
		'companyInfoSrc/lxxx/lxxx','companyInfoSrc/lxxx/lxxxxg'  ],function( tab,lxxx,lxxxxg ){
	return {	 
		showPage : function(){	
			 	
			$('.operationsInner').html(_.template(tab)) ;
			
			// 
			$('#qyxx_lxxx').click(function(e) {	
 				this.showLi($('#qyxx_lxxx')); 
                lxxx.showPage();				 
            }.bind(this)).click();
			
			$('#qyxx_lxxxxg').click(function(e) {		
 		 		this.showLi($('#qyxx_lxxxxg')); 
                lxxxxg.showPage();				 
            }.bind(this)) ;
			
			 
			 
		} ,
		showLi : function(liObj){
			$('#qyxx_lxxx > a').removeClass('active');
			liObj.css('display','').find('a').addClass('active');
			liObj.siblings('li').not('#qyxx_lxxx').css('display','none');
		} 
	}
}); 

