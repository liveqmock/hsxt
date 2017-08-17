define(['text!infoManageTpl/zyxxbgsp/tab.html',
		'infoManageSrc/zyxxbgsp/xfzzyxxbgsp',
		'infoManageSrc/zyxxbgsp/tgqyzyxxbgsp',
		'infoManageSrc/zyxxbgsp/cyqyzyxxbgsp',
		'infoManageSrc/zyxxbgsp/fwgszyxxbgsp',
		'infoManageSrc/zyxxbgsp/xfzzyxxbgspcx',
		'infoManageSrc/zyxxbgsp/qyzyxxbgspcx',
		'infoManageLan'
		], function(tab, xfzzyxxbgsp, tgqyzyxxbgsp, cyqyzyxxbgsp, fwgszyxxbgsp,xfzzyxxbgspcx, qyzyxxbgspcx){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			
			$('#xfzzyxxbgsp').click(function(){
				xfzzyxxbgsp.showPage();
				comm.liActive($('#xfzzyxxbgsp'));
				comm.goDetaiPage("busibox2","busibox");
			}.bind(this));
			
			$('#tgqyzyxxbgsp').click(function(){
				tgqyzyxxbgsp.showPage();
				comm.liActive($('#tgqyzyxxbgsp'));
				comm.goDetaiPage("busibox2","busibox");
			}.bind(this));
			
			$('#cyqyzyxxbgsp').click(function(){
				cyqyzyxxbgsp.showPage();
				comm.liActive($('#cyqyzyxxbgsp'));
				comm.goDetaiPage("busibox2","busibox");
			}.bind(this));
			
			$('#fwgszyxxbgsp').click(function(){
				fwgszyxxbgsp.showPage();
				comm.liActive($('#fwgszyxxbgsp'));
				comm.goDetaiPage("busibox2","busibox");
			}.bind(this));
			
			$('#xfzzyxxbgspcx').click(function(){
				xfzzyxxbgspcx.showPage();
				comm.liActive($('#xfzzyxxbgspcx'));
				comm.goDetaiPage("busibox2","busibox");
			}.bind(this));
			
			$('#qyzyxxbgspcx').click(function(){
				qyzyxxbgspcx.showPage();
				comm.liActive($('#qyzyxxbgspcx'));
				comm.goDetaiPage("busibox2","busibox");
			}.bind(this));
			
			//权限设置后默认选择规则
            var isModulesDefault = false;	//是否已有默认选择菜单
			var match = comm.findPermissionJsonByParentId("051604000000");
			
			//遍历福利资格查询的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			 {
				 //消费者重要信息变更审批
				 if(match[i].permId =='051604010000')
				 {
					 $('#xfzzyxxbgsp').show();
					 $('#xfzzyxxbgsp').click();
					 //已经设置默认值
					 isModulesDefault = true;
				 }
				 //托管企业重要信息变更审批
				 else if(match[i].permId =='051604020000')
				 {
					 $('#tgqyzyxxbgsp').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#tgqyzyxxbgsp').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				//成员企业重要信息变更审批
				 else if(match[i].permId =='051604030000')
				 {
					 $('#cyqyzyxxbgsp').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#cyqyzyxxbgsp').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				//服务公司重要信息变更审批
				 else if(match[i].permId =='051604040000')
				 {
					 $('#fwgszyxxbgsp').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#fwgszyxxbgsp').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				//消费者重要信息变更审批查询
				 else if(match[i].permId =='051604050000')
				 {
					 $('#xfzzyxxbgspcx').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#xfzzyxxbgspcx').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				//企业重要信息变更审批查询
				 else if(match[i].permId =='051604060000')
				 {
					 $('#qyzyxxbgspcx').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#qyzyxxbgspcx').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				 
			 }
		}	
	}	
});