define(['text!companyInfoTpl/yhzhxx/tab.html',
		'companyInfoSrc/yhzhxx/yhzhxx',
		'companyInfoSrc/yhzhxx/yhzhxx_xg',
		'companyInfoSrc/yhzhxx/yhzhxx_sc',
		'companyInfoSrc/yhzhxx/yhzhxx_xq',
		'companyInfoSrc/yhzhxx/yhzhxx_tj',
		 ],function( tab,yhzhxx ,yhzhxx_xg ,yhzhxx_sc , yhzhxx_xq, yhzhxx_tj ){
	return {	 
		showPage : function(){	
			 	
			$('.operationsInner').html(_.template(tab)) ;
			// 
			$('#qyxx_yhzhxx').click(function(e) {	
				this.showLi($('#qyxx_yhzhxx') );			 
                yhzhxx.showPage();				 
            }.bind(this)).click();
			
			$('#qyxx_yhzhxxxg').click(function(e) {		
				this.showLi($('#qyxx_yhzhxxxg') );			 
                yhzhxx_xg.showPage();				 
            }.bind(this)) ; 
           
           $('#qyxx_yhzhxxxq').click(function(e) {		
				this.showLi($('#qyxx_yhzhxxxq') );			 
                yhzhxx_xq.showPage();				 
            }.bind(this)) ;  
            
           $('#qyxx_yhzhxxsc').click(function(e) {		
				this.showLi($('#qyxx_yhzhxxsc') );			 
                yhzhxx_sc.showPage();				 
            }.bind(this)) ;  
           
           $('#qyxx_yhzhxxtj').click(function(e) {		
				this.showLi($('#qyxx_yhzhxxtj') );			 
                yhzhxx_tj.showPage();				 
            }.bind(this)) ;  
            
		} ,
		showLi : function(liObj){			
			$('#qyxx_yhzhxx > a').removeClass('active');
			liObj.css('display','').find('a').addClass('active');
			liObj.siblings('li').not('#qyxx_yhzhxx').css('display','none');
		} 
		
		
	}
}); 

