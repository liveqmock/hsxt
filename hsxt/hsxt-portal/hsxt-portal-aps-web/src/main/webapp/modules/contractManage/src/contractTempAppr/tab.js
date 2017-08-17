define(['text!contractManageTpl/contractTempAppr/tab.html',
		'contractManageSrc/contractTempAppr/htmbfh'
		], function(tab, htmbfh){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			//合同模版复核
			$('#htmbfh').click(function(){
				htmbfh.showPage();
				comm.liActive($('#htmbfh'));
			}.bind(this));
			
			//权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("051105000000");
			
			//遍历合同模版复核的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			{
				 //合同模版复核
				 if(match[i].permId =='051105010000')
				 {
					 $('#htmbfh').show();
					 $('#htmbfh').click();
					 
				 }
			}	
		}	
	}	
});
