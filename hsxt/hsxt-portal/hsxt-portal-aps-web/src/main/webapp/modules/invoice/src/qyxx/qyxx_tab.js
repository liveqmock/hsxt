define(['text!invoiceTpl/qyxx/qyxx_tab.html',
		'invoiceSrc/qyxx/xtdjxx',
		'invoiceSrc/qyxx/gsdjxx',
		'invoiceSrc/qyxx/lxxx',
		'invoiceSrc/qyxx/yhzhxx'
		],function(tab, xtdjxx, gsdjxx, lxxx, yhzhxx){
	return {	 
		showPage : function(custId){		
			$('#ckqyxxTpl').html(_.template(tab)) ;
			$("#customId").val(custId);
			/*系统登记信息*/
			$('#xtdjxx').click(function(e) {
                xtdjxx.showPage();
				comm.liActive($('#xtdjxx'));
            }.bind(this)).click();
			
			/*工商登记信息*/
			$('#gsdjxx').click(function(e) {
                gsdjxx.showPage();
				comm.liActive($('#gsdjxx'));
            }.bind(this)); 
			
			/*联系信息*/
			$('#lxxx').click(function(e) {
                lxxx.showPage();
				comm.liActive($('#lxxx'));
            }.bind(this)); 
			
			/*银行账户信息*/
			$('#yhzhxx').click(function(e) {
                yhzhxx.showPage();
				comm.liActive($('#yhzhxx'));
            }.bind(this)); 

		}

	}
}); 

