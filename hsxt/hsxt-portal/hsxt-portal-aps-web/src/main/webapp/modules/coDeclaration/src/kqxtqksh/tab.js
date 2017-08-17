define(['text!coDeclarationTpl/kqxtqksh/tab.html',
		'coDeclarationSrc/kqxtqksh/tgqykqxtyw',
		'coDeclarationSrc/kqxtqksh/cyqykqxtyw',
		'coDeclarationSrc/kqxtqksh/fwgskqxtyw'
		], function(tab, tgqykqxtyw, cyqykqxtyw, fwgskqxtyw){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			
			$('#tgqykqxtyw').click(function(){
				tgqykqxtyw.showPage();
				comm.liActive($('#tgqykqxtyw'));
				comm.setCache('coDeclaration' , 'kstqksh_tab', 1 )
				
			}.bind(this));
			
			$('#cyqykqxtyw').click(function(){
				cyqykqxtyw.showPage();
				comm.liActive($('#cyqykqxtyw'));
				comm.setCache('coDeclaration' , 'kstqksh_tab', 2 )
			}.bind(this));
			
			$('#fwgskqxtyw').click(function(){
				fwgskqxtyw.showPage();
				comm.liActive($('#fwgskqxtyw'));
				comm.setCache('coDeclaration' , 'kstqksh_tab', 3 )
			}.bind(this));
			
			//权限设置后默认选择规则
            var isModulesDefault = false;	//是否已有默认选择菜单
			var match = comm.findPermissionJsonByParentId("050108000000");
			
			//遍历审批统计查询的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			 {
				 //托管企业开启系统欠费审核
				 if(match[i].permId =='050108010000')
				 {
					 $('#tgqykqxtyw').show();
					 $('#tgqykqxtyw').click();
					 //已经设置默认值
					 isModulesDefault = true;
				 }
				 //成员企业开启系统欠费审核
				 else if(match[i].permId =='050108020000')
				 {
					 $('#cyqykqxtyw').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#cyqykqxtyw').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				 //服务公司开启系统欠费审核
				 else if(match[i].permId =='050108030000')
				 {
					 $('#fwgskqxtyw').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#fwgskqxtyw').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
			 }
		}
	}	
});