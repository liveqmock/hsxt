define(['text!echartsTpl/chord/tab.html',
		'echartsSrc/chord/line', 
		'echartsSrc/chord/line1' ,
		'echartsSrc/chord/line2' ,
		'echartsSrc/chord/line3' ,
		'echartsSrc/chord/line4'  ],function( tab,line,line1,line2,line3,line4   ){
	return {	 
		showPage : function(){	
			 	
			$('.operationsArea').html(_.template(tab)) ;
			// 
			$('#line').click(function(e) {		
				this.showLi($('#line') );		 
                line.showPage();				 
            }.bind(this)).click();
			
			$('#line1').click(function(e) {		
				this.showLi($('#line1') );		 
                line1.showPage();				 
            }.bind(this));
            
            $('#line2').click(function(e) {		
				this.showLi($('#line2') );		 
                line2.showPage();				 
            }.bind(this));
            
            $('#line3').click(function(e) {		
				this.showLi($('#line3') );		 
                line3.showPage();				 
            }.bind(this));
            
            $('#line4').click(function(e) {		
				this.showLi($('#line4') );		 
                line4.showPage();				 
            }.bind(this));
            
            
            
            
			 
		},
		showLi : function(liObj){
			//$('#qyxx_gsdjxx > a').removeClass('active');
			liObj.css('display','').find('a').addClass('active');
			liObj.siblings('li').find('a').removeClass('active') ;
		} 
		
		
	}
}); 

