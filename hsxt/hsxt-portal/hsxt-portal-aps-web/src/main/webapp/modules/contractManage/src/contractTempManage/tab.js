define(['text!contractManageTpl/contractTempManage/tab.html',
		'contractManageSrc/contractTempManage/htmbwh'
		], function(tab, htmbwh){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			//合同模版维护
			$('#htmbwh').click(function(){
				htmbwh.showPage();
				comm.liActive($('#htmbwh'));
			}.bind(this));
			
			//权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("051104000000");
			
			//遍历合同模版维护的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			{
				 //合同模版维护
				 if(match[i].permId =='051104010000')
				 {
					 $('#htmbwh').show();
					 $('#htmbwh').click();
					 
				 }
			}
				
		}	
	}	
});
