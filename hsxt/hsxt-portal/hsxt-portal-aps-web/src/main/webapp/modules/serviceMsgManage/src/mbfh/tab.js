define(['text!serviceMsgManageTpl/mbfh/tab.html',
		'serviceMsgManageSrc/mbfh/sjdxmbfh',
		'serviceMsgManageSrc/mbfh/tsxxmbfh',
		'serviceMsgManageSrc/mbfh/yjmbfh'
		], function(tab, sjdxmbfh, tsxxmbfh, yjmbfh){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			
			$('#mbfh_sjdxmbfh').click(function(){
				sjdxmbfh.showPage();
				comm.liActive($('#mbfh_sjdxmbfh'));
			}.bind(this));
			
			$('#mbfh_tsxxmbfh').click(function(){
				tsxxmbfh.showPage();
				comm.liActive($('#mbfh_tsxxmbfh'));
			}.bind(this));
			
			$('#mbfh_yjmbfh').click(function(){
				yjmbfh.showPage();
				comm.liActive($('#mbfh_yjmbfh'));
			}.bind(this));
			
			//权限设置后默认选择规则
            var isModulesDefault = false;	//是否已有默认选择菜单
			var match = comm.findPermissionJsonByParentId("051902000000");
			
			//遍历福利资格查询的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			 {
				 //手机短信模板复核
				 if(match[i].permId =='051902010000')
				 {
					 $('#mbfh_sjdxmbfh').show();
					 $('#mbfh_sjdxmbfh').click();
					 //已经设置默认值
					 isModulesDefault = true;
				 }
				 //推送消息模板复核
				 else if(match[i].permId =='051902020000')
				 {
					 $('#mbfh_tsxxmbfh').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#mbfh_tsxxmbfh').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				//邮件模板复核
				 else if(match[i].permId =='051902030000')
				 {
					 $('#mbfh_yjmbfh').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#mbfh_yjmbfh').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				 
			 }
		}	
	}	
});
