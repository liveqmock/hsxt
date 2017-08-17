define(['text!messageCenterTpl/wfxx/tab.html',
		'messageCenterSrc/wfxx/wfxx',
		'messageCenterSrc/wfxx/ckxq',
		'messageCenterLan'
		],
		function( tab,wfxx,ckxq ){
	return {	 
		showPage : function(){	
			 	
			$('.operationsInner').html(_.template(tab)) ;
			//未发消息
			$('#xxzx_wfxx').click(function(e) {	
				this.showLi($('#xxzx_wfxx'));					 
                wfxx.showPage();				 
            }.bind(this)).click();
			
			$('#xxzx_ckxq').click(function(e) {		
				this.showLi($('#xxzx_ckxq'));				 
                ckxq.showPage();				 
            }.bind(this)) ;
			 
		} ,
		showLi : function(liObj){
			$('#xxzx_wfxx > a').removeClass('active');
			liObj.css('display','').find('a').addClass('active');
			liObj.siblings('li').not('#xxzx_wfxx').css('display','none');
		}
		
		
		
	}
}); 