define(['text!infoManageTpl/smrzsp/tab.html',
		'infoManageSrc/smrzsp/xfzsmrzsp',
		'infoManageSrc/smrzsp/tgqysmrzsp',
		'infoManageSrc/smrzsp/cyqysmrzsp',
		'infoManageSrc/smrzsp/fwgssmrzsp',
		'infoManageSrc/smrzsp/xfzsmrzspcx',
		'infoManageSrc/smrzsp/qysmrzspcx',
		'infoManageLan'
		], function(tab, xfzsmrzsp, tgqysmrzsp, cyqysmrzsp, fwgssmrzsp, xfzsmrzspcx, qysmrzspcx){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			
			$('#xfzsmrzsp').click(function(){
				xfzsmrzsp.showPage();
				comm.liActive($('#xfzsmrzsp'));
				comm.goDetaiPage("busibox2","busibox");
			}.bind(this)).click();
			
			$('#tgqysmrzsp').click(function(){
				tgqysmrzsp.showPage();
				comm.liActive($('#tgqysmrzsp'));
				comm.goDetaiPage("busibox2","busibox");
			}.bind(this));
			
			$('#cyqysmrzsp').click(function(){
				cyqysmrzsp.showPage();
				comm.liActive($('#cyqysmrzsp'));
				comm.goDetaiPage("busibox2","busibox");
			}.bind(this));
			
			$('#fwgssmrzsp').click(function(){
				fwgssmrzsp.showPage();
				comm.liActive($('#fwgssmrzsp'));
				comm.goDetaiPage("busibox2","busibox");
			}.bind(this));
			
			$('#xfzsmrzspcx').click(function(){
				xfzsmrzspcx.showPage();
				comm.liActive($('#xfzsmrzspcx'));
				comm.goDetaiPage("busibox2","busibox");
			}.bind(this));
			
			$('#qysmrzspcx').click(function(){
				qysmrzspcx.showPage();
				comm.liActive($('#qysmrzspcx'));
				comm.goDetaiPage("busibox2","busibox");
			}.bind(this));
			
			//权限设置后默认选择规则
            var isModulesDefault = false;	//是否已有默认选择菜单
			var match = comm.findPermissionJsonByParentId("051603000000");
			
			//遍历实名认证审批的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			 {
				 //消费者实名认证审批
				 if(match[i].permId =='051603010000')
				 {
					 $('#xfzsmrzsp').show();
					 $('#xfzsmrzsp').click();
					 //已经设置默认值
					 isModulesDefault = true;
				 }
				 //托管企业实名认证审批
				 else if(match[i].permId =='051603020000')
				 {
					 $('#tgqysmrzsp').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#tgqysmrzsp').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				//成员企业实名认证审批
				 else if(match[i].permId =='051603030000')
				 {
					 $('#cyqysmrzsp').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#cyqysmrzsp').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				//服务公司实名认证审批
				 else if(match[i].permId =='051603040000')
				 {
					 $('#fwgssmrzsp').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#fwgssmrzsp').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				//消费者实名认证审批查询
				 else if(match[i].permId =='051603050000')
				 {
					 $('#xfzsmrzspcx').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#xfzsmrzspcx').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				//企业实名认证审批查询
				 else if(match[i].permId =='051603060000')
				 {
					 $('#qysmrzspcx').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#qysmrzspcx').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				 
			 }
			
		}	
	}	
});