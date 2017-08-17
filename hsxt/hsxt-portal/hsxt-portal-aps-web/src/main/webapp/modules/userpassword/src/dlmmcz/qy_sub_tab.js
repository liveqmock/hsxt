define(['text!userpasswordTpl/dlmmcz/qy_sub_tab.html',
		'userpasswordSrc/dlmmcz/xtzcxx',
		'userpasswordSrc/dlmmcz/gsdjxx',
		'userpasswordSrc/dlmmcz/lxxx',
		'userpasswordSrc/dlmmcz/yhzhxx'
		], function(tab, xtzcxx, gsdjxx, lxxx, yhzhxx){
	return {
		showPage : function(obj){
			$('#busibox').html(_.template(tab));	
		     //这个JS已经作废了，不用了
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
			
			
			 //返回列表
		 	$('#ent_back').click(function(){
		 		 $('#busibox').removeClass('none');
		 		 $('#cx_content_detail').addClass('none') ;
		 		 
		 	/*	$('#ckqyxxxx').css('display','none').find('a').removeClass('active');
				$('#qydlmmcz').find('a').addClass('active');*/
		 		 
		 	});
		}	
	}	
});