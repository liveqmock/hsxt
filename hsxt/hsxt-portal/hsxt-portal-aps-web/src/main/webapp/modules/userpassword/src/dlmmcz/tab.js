define(['text!userpasswordTpl/dlmmcz/tab.html',
		'userpasswordSrc/dlmmcz/xfzdlmmcz',
		'userpasswordSrc/dlmmcz/qydlmmcz',
		'userpasswordSrc/dlmmcz/xtzcxx',
		'userpasswordSrc/dlmmcz/gsdjxx',
		'userpasswordSrc/dlmmcz/lxxx',
		'userpasswordSrc/dlmmcz/yhzhxx'
		], function(tab, xfzdlmmcz, qydlmmcz,xtzcxx, gsdjxx, lxxx, yhzhxx){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			
			$('#xfzdlmmcz').click(function(){
				xfzdlmmcz.showPage();
				comm.liActive($('#xfzdlmmcz'), '#ckqyxxxx');
				$('#busibox').removeClass('none');
				$('#ent_detail').addClass('none');
			}.bind(this));
			
			$('#qydlmmcz').click(function(){
				qydlmmcz.showPage();
				comm.liActive($('#qydlmmcz'), '#ckqyxxxx');
				$('#busibox').removeClass('none');
				$('#ent_detail').addClass('none');
			}.bind(this));
			
			//权限设置后默认选择规则
            var isModulesDefault = false;	//是否已有默认选择菜单
			var match = comm.findPermissionJsonByParentId("051001000000");
			
			//遍历第三方业务证书管理的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			 {
				 //消费者登录密码重置
				 if(match[i].permId =='051001010000')
				 {
					 $('#xfzdlmmcz').show();
					 $('#xfzdlmmcz').click();
					 //已经设置默认值
					 isModulesDefault = true;
				 }
				 //企业登录密码重置
				 else if(match[i].permId =='051001020000')
				 {
					 $('#qydlmmcz').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#qydlmmcz').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				 
			 }
			
			
			//
			/*系统注册信息*/
			$('#xtzcxx').click(function(e) {
                xtzcxx.showPage();
				comm.liActive($('#xtzcxx'));
            }.bind(this)); 
			
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
			
			
			 //返回列表
		 	$('#ent_back').click(function(){
		 		 $('#busibox').removeClass('none');
		 		 $('#ent_detail').addClass('none') ;
		 		$('#ckqyxxxx').addClass("tabNone").find('a').removeClass('active');
				$('#qydlmmcz').find('a').addClass('active');
		 	});
			//
			
			
		}
	}	
});