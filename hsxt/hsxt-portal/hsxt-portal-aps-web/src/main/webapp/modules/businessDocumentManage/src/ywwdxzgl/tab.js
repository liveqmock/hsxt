define(['text!businessDocumentManageTpl/ywwdxzgl/tab.html',
		'businessDocumentManageSrc/ywwdxzgl/blywsqs',
		'businessDocumentManageSrc/ywwdxzgl/cyywwd'
		], function(tab, blywsqs, cyywwd){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			
			$('#blywsqs').click(function(){
				blywsqs.showPage();
				comm.liActive($('#blywsqs'));
			}.bind(this));
			
			$('#cyywwd').click(function(){
				cyywwd.showPage();
				comm.liActive($('#cyywwd'));
			}.bind(this));
			
			//权限设置后默认选择规则
            var isModulesDefault = false;	//是否已有默认选择菜单
			var match = comm.findPermissionJsonByParentId("052402000000");
			
			//遍历福利资格查询的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			 {
				 //办理业务申请书
				 if(match[i].permId =='052402010000')
				 {
					 $('#blywsqs').show();
					 $('#blywsqs').click();
					 //已经设置默认值
					 isModulesDefault = true;
				 }
				 //常用业务文档
				 else if(match[i].permId =='052402020000')
				 {
					 $('#cyywwd').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#cyywwd').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				 
			 }
				
		}	
	}	
});
