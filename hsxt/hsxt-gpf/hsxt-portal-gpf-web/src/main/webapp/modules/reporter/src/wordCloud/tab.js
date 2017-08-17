define(['text!echartsTpl/line/tab.html',
		'echartsSrc/line/line', 
		'echartsSrc/line/line1' ,
		'echartsSrc/line/line2' ,
		'echartsSrc/line/line3' ,
		'echartsSrc/line/line4' ,
		'echartsSrc/line/line5' ,
		'echartsSrc/line/line6' ,
		'echartsSrc/line/line7' ,
		'echartsSrc/line/line8' ,
		'echartsSrc/line/line9' ,
		'echartsSrc/line/line10' ,
		'echartsSrc/line/line11'  ],function( tab,line,line1,line2,line3,line4,line5,line6,line7,line8,line9,line10,line11   ){
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
            
            $('#line5').click(function(e) {		
				this.showLi($('#line5') );		 
                line5.showPage();				 
            }.bind(this));
            
            $('#line6').click(function(e) {		
				this.showLi($('#line6') );		 
                line6.showPage();				 
            }.bind(this));
            
            $('#line7').click(function(e) {		
				this.showLi($('#line7') );		 
                line7.showPage();				 
            }.bind(this));
            
            $('#line8').click(function(e) {		
				this.showLi($('#line8') );		 
                line8.showPage();				 
            }.bind(this));
            
            $('#line9').click(function(e) {		
				this.showLi($('#line9') );		 
                line9.showPage();				 
            }.bind(this));
            
            $('#line10').click(function(e) {		
				this.showLi($('#line10') );		 
                line10.showPage();				 
            }.bind(this));
            
            $('#line11').click(function(e) {		
				this.showLi($('#line11') );		 
                line11.showPage();				 
            }.bind(this));
            
            
			 
		},
		showLi : function(liObj){
			//$('#qyxx_gsdjxx > a').removeClass('active');
			liObj.css('display','').find('a').addClass('active');
			liObj.siblings('li').find('a').removeClass('active') ;
		} 
		
		
	}
}); 

