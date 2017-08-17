define(['text!serviceMsgManageTpl/xxmb/tab.html',
		'serviceMsgManageSrc/xxmb/sjdxmb',
		'serviceMsgManageSrc/xxmb/tsxxmb',
		'serviceMsgManageSrc/xxmb/yjmb'
		], function(tab, sjdxmb, tsxxmb, yjmb){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			
			$('#sjdxmb').click(function(){
				sjdxmb.showPage();
				comm.liActive($('#sjdxmb'), '#xzmb, #xgmb');
			}.bind(this));
			
			$('#tsxxmb').click(function(){
				tsxxmb.showPage();
				comm.liActive($('#tsxxmb'), '#xzmb, #xgmb');
			}.bind(this));
			
			$('#yjmb').click(function(){
				yjmb.showPage();
				comm.liActive($('#yjmb'), '#xzmb, #xgmb');
			}.bind(this));
			
			//权限设置后默认选择规则
            var isModulesDefault = false;	//是否已有默认选择菜单
			var match = comm.findPermissionJsonByParentId("051901000000");
			
			//遍历福利资格查询的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			 {
				 //手机短信模板
				 if(match[i].permId =='051901010000')
				 {
					 $('#sjdxmb').show();
					 $('#sjdxmb').click();
					 //已经设置默认值
					 isModulesDefault = true;
				 }
				 //推送消息模板
				 else if(match[i].permId =='051901020000')
				 {
					 $('#tsxxmb').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#tsxxmb').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				//邮件模板
				 else if(match[i].permId =='051901030000')
				 {
					 $('#yjmb').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#yjmb').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				 
			 }
		}	
	}	
});
