define(['text!contractManageTpl/contractSeal/tab.html',
		'contractManageSrc/contractSeal/query',
		'contractManageLan'
], function(tab, query){
return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			//合同盖章
			$('#sealContract').click(function(){
				query.showPage();
				comm.liActive($('#sealContract'));
			}.bind(this));
			
			//权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("051102000000");
			
			//遍历合同盖章的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			{
				 //合同盖章
				 if(match[i].permId =='051102010000')
				 {
					 $('#sealContract').show();
					 $('#sealContract').click();
					 
				 }
			}
		}	
	};	
});
