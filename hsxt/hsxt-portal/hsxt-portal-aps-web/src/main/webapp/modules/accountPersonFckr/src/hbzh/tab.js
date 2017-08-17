define(['text!accountPersonFckrTpl/hbzh/tab.html',
		'accountPersonFckrSrc/hbzh/mxcx',
		'accountPersonFckrLan'
		],function(tpl, mxcx){
	return {	 
		showPage : function(){			
			$('.operationsInner').html(tpl);
			mxcx.showPage();
			
			//账户余额
			$('#hbzh_zhye').click(function(e) {
				comm.liActive($(this));
				zhye.showPage();
            }).click();
			
			//货币转银行
			$('#hbzh_hbzyh').click(function(e) {	
				comm.liActive($(this));		
                hbzyh.showPage();
            });
			
			//明细查询
			$('#hbzh_mxcx').click(function(e) {
				comm.liActive($(this));				
                mxcx.showPage();
            });
		}
	}
});