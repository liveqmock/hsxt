define(['text!contractManageTpl/contractQuery/tab.html',
		'contractManageSrc/contractQuery/query',
		'contractManageLan'
		], function(tab, query){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			//合同查询
			$('#queryContract').click(function(){
				query.showPage();
				comm.liActive($('#queryContract'));
			}.bind(this));
			
			//权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("051101000000");
			
			//遍历合同查询的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			{
				 //合同查询
				 if(match[i].permId =='051101010000')
				 {
					 $('#queryContract').show();
					 $('#queryContract').click();
					 
				 }
			}
		}	
	};	
});
