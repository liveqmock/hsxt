define(['text!infoManageTpl/zyml/tab.html',
		'infoManageSrc/zyml/tgqyzyyl',
		'infoManageSrc/zyml/cyqyzyyl',
		'infoManageSrc/zyml/fwgszyyl',
		'infoManageSrc/zyml/xfzzyyl',
		'infoManageLan'
		], function(tab, tgqyzyyl, cyqyzyyl, fwgszyyl, xfzzyyl){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			
			$('#tgqyzyyl').click(function(){
				tgqyzyyl.showPage();
				comm.liActive($('#tgqyzyyl'),'#ckqyxxxx, #ck');
				comm.goDetaiPage("busibox2","busibox");
			}.bind(this));
			
			$('#cyqyzyyl').click(function(){
				cyqyzyyl.showPage();
				comm.liActive($('#cyqyzyyl'),'#ckqyxxxx, #ck');
				comm.goDetaiPage("busibox2","busibox");
			}.bind(this));
			
			$('#fwgszyyl').click(function(){
				fwgszyyl.showPage();
				comm.liActive($('#fwgszyyl'),'#ckqyxxxx, #ck');
				comm.goDetaiPage("busibox2","busibox");
			}.bind(this));
			
			$('#xfzzyyl').click(function(){
				xfzzyyl.showPage();
				comm.liActive($('#xfzzyyl'),'#ckqyxxxx, #ck');
				comm.goDetaiPage("busibox2","busibox");
			}.bind(this));
			
			//权限设置后默认选择规则
            var isModulesDefault = false;	//是否已有默认选择菜单
			var match = comm.findPermissionJsonByParentId("051601000000");
			
			//遍历资源名录的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			 {
				 //托管企业资源一览
				 if(match[i].permId =='051601010000')
				 {
					 $('#tgqyzyyl').show();
					 $('#tgqyzyyl').click();
					 //已经设置默认值
					 isModulesDefault = true;
				 }
				 //成员企业资源一览
				 else if(match[i].permId =='051601020000')
				 {
					 $('#cyqyzyyl').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#cyqyzyyl').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				//服务公司资源一览
				 else if(match[i].permId =='051601030000')
				 {
					 $('#fwgszyyl').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#fwgszyyl').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				//消费者资源一览
				 else if(match[i].permId =='051601040000')
				 {
					 $('#xfzzyyl').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#xfzzyyl').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				 
			 }
		}
	}	
});