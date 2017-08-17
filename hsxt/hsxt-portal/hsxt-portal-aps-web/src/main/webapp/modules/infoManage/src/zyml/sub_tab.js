define(['text!infoManageTpl/zyml/sub_tab.html',
		'infoManageSrc/zyml/xtdjxx',
		'infoManageSrc/zyml/gsdjxx',
		'infoManageSrc/zyml/lxxx',
		'infoManageSrc/zyml/yhzhxx',
		'infoManageSrc/zyml/ywczxk',
		'infoManageSrc/zyml/xhzxxxx_kjk'
		],function(tab, xtdjxx, gsdjxx, lxxx, yhzhxx, ywczxk, xhzxxxx_kjk){
	return {	 
		showPage : function(num, record, custID){
			
			$('#busibox2').html(_.template(tab));
			comm.goDetaiPage("busibox","busibox2");
			/*系统登记信息*/
			$('#xtdjxx').click(function(e) {
                xtdjxx.showPage(num, record, custID);
				comm.liActive($('#xtdjxx'));
            }.bind(this)).click();
			
			/*工商登记信息*/
			$('#gsdjxx').click(function(e) {
                gsdjxx.showPage(num, custID);
				comm.liActive($('#gsdjxx'));
            }.bind(this)); 
			
			/*联系信息*/
			$('#lxxx').click(function(e) {
                lxxx.showPage(num, custID);
				comm.liActive($('#lxxx'));
            }.bind(this)); 
			
			/*银行账户信息*/
			$('#yhzhxx').click(function(e) {
                yhzhxx.showPage(num, custID);
				comm.liActive($('#yhzhxx'));
            }.bind(this));
			
			$('#zyml_kjk').click(function(e) {
                xhzxxxx_kjk.showPage(num, custID);
				comm.liActive($('#zyml_kjk'));
            }.bind(this)); 
			
			/*业务操作许可*/
			$('#ywczxk').click(function(e) {
                ywczxk.showPage(num, custID);
				comm.liActive($('#ywczxk'));
            }.bind(this));
			
		}

	}
}); 

