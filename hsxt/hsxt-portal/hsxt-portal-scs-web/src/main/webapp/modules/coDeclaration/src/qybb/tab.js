define(['text!coDeclarationTpl/qybb/tab.html',
			'coDeclarationSrc/qybb/qybb',
			'coDeclarationSrc/qybb/qybbcx',
			'coDeclarationSrc/qybb/yybbcx' 
			],function(tab,qybb, qybbcx , yybbcx  ){
	return {
		//企业报备初始化
		showPage : function(){			
		 
			$('.operationsInner').html(_.template(tab)) ;
			$('#isSaveFj').val('');
			
			// 
			$('#qybb_qybb').click(function(e) {
				$('#isSaveFj').val('');
                qybb.showPage();
				comm.liActive($('#qybb_qybb'));
            }.bind(this));			 
			
			// 
			$('#qybb_qybbcx').click(function(e) {
				$('#isSaveFj').val('');
                qybbcx.showPage();
				comm.liActive($('#qybb_qybbcx'));
            }.bind(this)); 
			// 
			$('#qybb_yybbcx').click(function(e) {
				$('#isSaveFj').val('');
                yybbcx.showPage();
				comm.liActive($('#qybb_yybbcx'));
            }.bind(this));  
			
			//权限设置后默认选择规则
            var isModulesDefault = false;	//是否已有默认选择菜单
			var match = comm.findPermissionJsonByParentId("030206000000");
			
			//遍历企业报备的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			 {
				 //企业报备
				 if(match[i].permId =='030206010000')
				 {
					 $('#qybb_qybb').show();
					 $('#qybb_qybb').click();
					 //已经设置默认值
					 isModulesDefault = true;
				 }
				 //企业报备查询
				 else if(match[i].permId =='030206020000')
				 {
					 $('#qybb_qybbcx').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#qybb_qybbcx').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				 //异议报备查询
				 else if(match[i].permId =='030206030000')
				 {
					 $('#qybb_yybbcx').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#qybb_yybbcx').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
			 }
			
		} 
	}
}); 
