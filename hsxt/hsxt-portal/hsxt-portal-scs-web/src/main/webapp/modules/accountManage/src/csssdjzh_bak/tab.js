define(['text!accountManageTpl/csssdjzh/tab.html',
		   'accountManageSrc/csssdjzh/zhye',
			'accountManageSrc/csssdjzh/sltzsq',
			'accountManageSrc/csssdjzh/sltzsqcx', 
			'accountManageSrc/csssdjzh/mxcx',
			],function(tab , zhye ,sltzsq , sltzsqcx , mxcx){
	return {	 
		showPage : function(){			
		 
			$('.operationsInner').html(_.template(tab)) ;
			//账户余额
			$('#csssdjzh_zhye').click(function(e) {				 		 
                zhye.showPage();
				this.liActive($('#csssdjzh_zhye'));
            }.bind(this)).click();			 
			
			// 
			$('#csssdjzh_sltzsq').click(function(e) {			 
                sltzsq.showPage();
				this.liActive($('#csssdjzh_sltzsq'));
            }.bind(this)); 
            // 
			$('#csssdjzh_sltzsqcx').click(function(e) {			 
                sltzsqcx.showPage();
				this.liActive($('#csssdjzh_sltzsqcx'));
            }.bind(this)); 
	 
            
            //明细查询
            $('#csssdjzh_mxcx').click(function(e) {		 			
                mxcx.showPage();
				this.liActive($('#csssdjzh_mxcx'));
            }.bind(this)); 
            
			
		},
		liActive :function(liObj){		 
			liObj.parent('ul').find('a').removeClass('active');
			liObj.find('a').addClass('active');	
		}
	}
}); 