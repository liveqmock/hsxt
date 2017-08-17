define(['text!companyInfoTpl/gsdjxx/tab.html',
		'companyInfoSrc/gsdjxx/gsdjxx',
		'companyInfoSrc/gsdjxx/gsdjxxxg'  ],function( tab,gsdjxx,gsdjxxxg ){
	return {	 
		showPage : function(){	
			 	
			$('.operationsInner').html(_.template(tab)) ;
			// 
			$('#qyxx_gsdjxx').click(function(e) {		
				this.showLi($('#qyxx_gsdjxx') );		 
                gsdjxx.showPage();				 
            }.bind(this)).click();
			
			
			$('#qyxx_gsdjxxxg').click(function(e) {					 
				this.showLi($('#qyxx_gsdjxxxg') );				 
                gsdjxxxg.showPage();				 
           }.bind(this)) ; 
		},
		showLi : function(liObj){
			$('#qyxx_gsdjxx > a').removeClass('active');
			liObj.css('display','').find('a').addClass('active');
			liObj.siblings('li').not('#qyxx_gsdjxx').css('display','none');
		} 
		
		
	}
}); 

