define(['text!businessServiceTpl/jyywsh/tab.html',
			'businessServiceSrc/jyywsh/stdsh',
			'businessServiceSrc/jyywsh/wsscsh',
			'businessServiceSrc/jyywsh/spsh' 
			],function(tab,stdsh, wsscsh , spsh  ){
	return {	 
		showPage : function(){			
		 
			$('.operationsInner').html(_.template(tab)) ; 
			
			// 
			$('#jyywsh_stdsh').click(function(e) {				 		 
                stdsh.showPage();
				comm.liActive($('#jyywsh_stdsh'));
            }.bind(this)).click();			 
			
			// 
			$('#jyywsh_wsscsh').click(function(e) {
			 
                wsscsh.showPage();
				comm.liActive($('#jyywsh_wsscsh'));
            }.bind(this)); 
			// 
			$('#jyywsh_spsh').click(function(e) {
		 
                spsh.showPage();
				comm.liActive($('#jyywsh_spsh'));
            }.bind(this));  
			
		 
			
		} 
	}
}); 
