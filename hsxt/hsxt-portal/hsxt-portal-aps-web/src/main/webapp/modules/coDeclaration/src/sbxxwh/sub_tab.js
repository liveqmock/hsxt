define(['text!coDeclarationTpl/sbxxwh/sub_tab.html',
		'coDeclarationSrc/sbxxwh/xtzcxx',
		'coDeclarationSrc/sbxxwh/gsdjxx',
		'coDeclarationSrc/sbxxwh/lxxx',
		'coDeclarationSrc/sbxxwh/yhzhxx'
		],function(tab, xtzcxx, gsdjxx, lxxx, yhzhxx){
	return {	 
		showPage : function(applyId, custType){		
			$('#busibox').html(_.template(tab)) ;
			
			$("#applyId").val(applyId);
			$("#custType").val(custType);

			/*系统注册信息*/
			$('#xtzcxx').click(function(e) {
                xtzcxx.showPage();
				comm.liActive($('#xtzcxx'));
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

