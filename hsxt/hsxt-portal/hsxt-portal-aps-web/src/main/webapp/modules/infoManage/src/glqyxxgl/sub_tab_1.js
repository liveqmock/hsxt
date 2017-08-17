define(['text!infoManageTpl/glqyxxgl/sub_tab_1.html',
		'infoManageSrc/glqyxxgl/xtzcxx',
		'infoManageSrc/glqyxxgl/gsdjxx',
		'infoManageSrc/glqyxxgl/lxxx',
		'infoManageSrc/glqyxxgl/yhzhxx'
		], function(tab, xtzcxx, gsdjxx, lxxx, yhzhxx){
	return {
		showPage : function(obj){
			$('#busibox').html(_.template(tab));	
			
			/*系统注册信息*/
			$('#xtzcxx').click(function(e) {
                xtzcxx.showPage(obj);
				comm.liActive($('#xtzcxx'));
            }.bind(this)).click(); 
			
			/*工商登记信息*/
			$('#gsdjxx').click(function(e) {
                gsdjxx.showPage(obj);
				comm.liActive($('#gsdjxx'));
            }.bind(this));
			
			/*联系信息*/
			$('#lxxx').click(function(e) {
                lxxx.showPage(obj);
				comm.liActive($('#lxxx'));
            }.bind(this)); 
			
			/*银行账户信息*/
			$('#yhzhxx').click(function(e) {
                yhzhxx.showPage(obj);
				comm.liActive($('#yhzhxx'));
            }.bind(this)); 
		}	
	}	
});