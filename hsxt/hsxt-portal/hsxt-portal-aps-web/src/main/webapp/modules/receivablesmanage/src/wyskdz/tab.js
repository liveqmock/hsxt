define(['text!receivablesmanageTpl/wyskdz/tab.html',
		'receivablesmanageSrc/wyskdz/sjzfwyskdz',
		'receivablesmanageSrc/wyskdz/webzfwyskdz'
		], function(tab, sjzfwyskdz, webzfwyskdz){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			//手机支付网银收款对账
			$('#sjzfwyskdz').click(function(){
				sjzfwyskdz.showPage();
				comm.liActive($('#sjzfwyskdz'),'#cknr');
				$('#busibox').removeClass('none');
				$('#sjzfwyskdz_ckTpl').addClass('none');
				$('#webzfwyskdz_ckTpl').addClass('none');
			}.bind(this));
			//web支付网银收款对账
			$('#webzfwyskdz').click(function(){
				webzfwyskdz.showPage();
				comm.liActive($('#webzfwyskdz'),'#cknr');
				$('#busibox').removeClass('none');
				$('#sjzfwyskdz_ckTpl').addClass('none');
				$('#webzfwyskdz_ckTpl').addClass('none');
			}.bind(this));
			
			//权限设置后默认选择规则
            var isModulesDefault = false;	//是否已有默认选择菜单
			var match = comm.findPermissionJsonByParentId("050204000000");
			
			//遍历网银收款对账的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			 {
				 //手机支付网银收款对账
				 if(match[i].permId =='050204010000')
				 {
					 $('#sjzfwyskdz').show();
					 $('#sjzfwyskdz').click();
					 //已经设置默认值
					 isModulesDefault = true;
				 }
				 //web支付网银收款对账
				 else if(match[i].permId =='050204020000')
				 {
					 $('#webzfwyskdz').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#webzfwyskdz').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
			 }
		}	
	}	
});