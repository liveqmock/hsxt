define(['text!shouhouTpl/tab.html',
		'shouhouSrc/qyddgl',
		'shouhouSrc/qypjgl',
		'shouhouSrc/ddshgl'  
		], function(tab, qyddgl, qypjgl, ddshgl){
	return {	 
		showPage : function(){	
			 	
			$('.operationsArea').html(_.template(tab)) ;
			
			$('#qyddgl').click(function(e) {		
				this.showLi($('#qyddgl') );		 
                qyddgl.showPage();				 
            }.bind(this)).click();
			
			$('#qypjgl').click(function(e) {					 
				this.showLi($('#qypjgl') );				 
                qypjgl.showPage();				 
           }.bind(this)) ; 
		   
		   $('#ddshgl').click(function(e) {					 
				this.showLi($('#ddshgl') );				 
                ddshgl.showPage();
           }.bind(this)) ; 
		},
		showLi : function(liObj){
			$('#tabNav').find('a').removeClass('active');
			liObj.css('display','').find('a').addClass('active');
			/*liObj.siblings('li').not('#qyddgl').css('display','none');*/
		} 
		
		
	}
}); 

