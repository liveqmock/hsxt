define(['text!coDeclarationTpl/kqxtyw/tab.html',
		'coDeclarationSrc/kqxtyw/tgqykqxtyw',
		'coDeclarationSrc/kqxtyw/cyqykqxtyw',
		'coDeclarationSrc/kqxtyw/fwgskqxtyw'
		], function(tab, tgqykqxtyw, cyqykqxtyw, fwgskqxtyw){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			
			$('#tgqykqxtyw').click(function(){
				tgqykqxtyw.showPage();
				comm.liActive($('#tgqykqxtyw'));
				//设置类型缓存
				comm.setCache('coDeclaration','kqxtyw_sblb','托管企业');
				
			}.bind(this));
			
			$('#cyqykqxtyw').click(function(){
				cyqykqxtyw.showPage();
				comm.liActive($('#cyqykqxtyw'));
				//设置类型缓存
				comm.setCache('coDeclaration','kqxtyw_sblb','成员企业');
			}.bind(this));
			
			$('#fwgskqxtyw').click(function(){
				fwgskqxtyw.showPage();
				comm.liActive($('#fwgskqxtyw'));
				//设置类型缓存
				comm.setCache('coDeclaration','kqxtyw_sblb','服务公司');
			}.bind(this));
			
			//权限设置后默认选择规则
            var isModulesDefault = false;	//是否已有默认选择菜单
			var match = comm.findPermissionJsonByParentId("050102000000");
			
			//遍历开启系统业务的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			 {
				 //托管企业开启系统业务
				 if(match[i].permId =='050102010000')
				 {
					 $('#tgqykqxtyw').show();
					 $('#tgqykqxtyw').click();
					 //已经设置默认值
					 isModulesDefault = true;
				 }
				 //成员企业开启系统业务
				 else if(match[i].permId =='050102020000')
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
				 //服务公司开启系统业务
				 else if(match[i].permId =='050102030000')
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