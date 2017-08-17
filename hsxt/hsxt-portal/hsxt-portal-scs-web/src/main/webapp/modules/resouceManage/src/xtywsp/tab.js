define(['text!resouceManageTpl/xtywsp/tab.html',
			'resouceManageSrc/xtywsp/cyqyzgzxsp',
			'resouceManageSrc/xtywsp/tzjfhdsp',
			'resouceManageSrc/xtywsp/cyjfhdsp' 
			],function(tab,cyqyzgzxsp, tzjfhdsp , cyjfhdsp  ){
	return {	 
		showPage : function(){			
		 
			$('.operationsInner').html(_.template(tab)) ;
			
			
			// 
			$('#xtywsp_cyqyzgzxsp').click(function(e) {				 		 
                cyqyzgzxsp.showPage();
				comm.liActive($('#xtywsp_cyqyzgzxsp'));
            }.bind(this)).click();			 
			
			// 
			$('#xtywsp_tzjfhdsp').click(function(e) {
			 
                tzjfhdsp.showPage();
				comm.liActive($('#xtywsp_tzjfhdsp'));
            }.bind(this)); 
			// 
			$('#xtywsp_cyjfhdsp').click(function(e) {
		 
                cyjfhdsp.showPage();
				comm.liActive($('#xtywsp_cyjfhdsp'));
            }.bind(this));  
			
		 
			
		} 
	}
}); 
