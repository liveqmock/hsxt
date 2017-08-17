define(['text!systemManageTpl/yhzgl/tab.html',
		'systemManageSrc/yhzgl/yhzgl' ,
		'systemManageSrc/yhzgl/xgyhz' ,
		'systemManageSrc/yhzgl/tjwhyh'   ],function( tab,yhzgl,xgyhz,tjwhyh ){
	return {	 
		showPage : function(){	
			 	
			$('.operationsInner').html(_.template(tab)) ;
			 
			$('#xtyhgl_yhzgl').click(function(e) {	
				this.showLi($('#xtyhgl_yhzgl'));					 
                yhzgl.showPage();				 
            }.bind(this)).click();
			
			$('#xtyhgl_xgyhz').click(function(e) {	
				this.showLi($('#xtyhgl_xgyhz'));					 
                xgyhz.showPage();				 
            }.bind(this));
            
            $('#xtyhgl_tjwhzy').click(function(e) {	
				this.showLi($('#xtyhgl_tjwhzy'));					 
                tjwhyh.showPage();				 
            }.bind(this));
			
			
			 
			 
		} ,
		showLi : function(liObj){
			$('#xtyhgl_czygl > a').removeClass('active');
			liObj.css('display','').find('a').addClass('active');
			liObj.siblings('li').not('#xtyhgl_yhzgl').css('display','none');
		}
		
		
		
	}
}); 

