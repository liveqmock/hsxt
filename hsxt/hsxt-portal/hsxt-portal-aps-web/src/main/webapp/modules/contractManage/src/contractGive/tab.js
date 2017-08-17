define(['text!contractManageTpl/contractGive/tab.html',
		'contractManageSrc/contractGive/htff'
		], function(tab, htff){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			comm.setCache("contractManage", "htff_searchTable_params", null);
			//合同发放
			$('#htff').click(function(){
				htff.showPage();
				comm.liActive($('#htff'));
				htff.searchTable();
			}.bind(this));
			
			//权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("051103000000");
			
			//遍历合同发放的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			{
				 //合同发放
				 if(match[i].permId =='051103010000')
				 {
					 $('#htff').show();
					 $('#htff').click();
					 
				 }
			}
		}	
	}	
});
