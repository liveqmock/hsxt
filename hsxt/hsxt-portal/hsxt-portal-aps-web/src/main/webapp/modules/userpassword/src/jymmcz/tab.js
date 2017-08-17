define(['text!userpasswordTpl/jymmcz/tab.html',
		'userpasswordSrc/jymmcz/xfzjymmcz',
		'userpasswordSrc/jymmcz/qyjymmczsh',
		'userpasswordSrc/jymmcz/qyjymmczshlist',
		'userpasswordSrc/dlmmcz/xtzcxx',
		'userpasswordSrc/dlmmcz/gsdjxx',
		'userpasswordSrc/dlmmcz/lxxx',
		'userpasswordSrc/dlmmcz/yhzhxx'
		], function(tab, xfzjymmcz, qyjymmczsh,qyjymmczshlist,xtzcxx, gsdjxx, lxxx, yhzhxx){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			
			$('#xfzjymmcz').click(function(){
				xfzjymmcz.showPage();
				comm.liActive($('#xfzjymmcz'), '#ckqyxxxx');
				$('#busibox').removeClass('none');
				$('#ent_detail').addClass('none');
			}.bind(this));
			
			$('#qyjymmczsh').click(function(){
				qyjymmczsh.showPage();
				comm.liActive($('#qyjymmczsh'), '#ckqyxxxx');
				$('#busibox').removeClass('none');
				$('#ent_detail').addClass('none');
			}.bind(this));
			
			
			$('#qyjymmczshlist').click(function(){
				qyjymmczshlist.showPage();
				comm.liActive($('#qyjymmczshlist'), '#ckqyxxxx');
				$('#busibox').removeClass('none');
				$('#ent_detail').addClass('none');
			}.bind(this));
			
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
				$('#qyjymmczsh').find('a').addClass('active');
		 	});
		 	
		 	//权限设置后默认选择规则
            var isModulesDefault = false;	//是否已有默认选择菜单
			var match = comm.findPermissionJsonByParentId("051002000000");
			
			//遍历交易密码重置的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			 {
				 //消费者交易密码重置
				 if(match[i].permId =='051002010000')
				 {
					 $('#xfzjymmcz').show();
					 $('#xfzjymmcz').click();
					 //已经设置默认值
					 isModulesDefault = true;
				 }
				 //企业交易密码重置审核
				 else if(match[i].permId =='051002020000')
				 {
					 $('#qyjymmczsh').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#qyjymmczsh').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				//企业交易密码重置审核
				 else if(match[i].permId =='051002030000')
				 {
					 $('#qyjymmczshlist').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#qyjymmczshlist').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				 
			 }
		}
	}	
});