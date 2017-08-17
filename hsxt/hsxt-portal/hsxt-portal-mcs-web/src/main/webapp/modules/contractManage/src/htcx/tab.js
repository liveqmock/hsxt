define(['text!contractManageTpl/htcx/tab.html',
		'contractManageSrc/htcx/htcx'
		], function(tab, htcx){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			
			//合同查询
			$('#htcx').click(function(){
				htcx.showPage();
				comm.liActive($('#htcx'));
			}.bind(this));
			
			//权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("040401000000");
			
			//遍历合同查询的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			 {
				 //合同查询
				 if(match[i].permId =='040401010000')
				 {
					 $('#htcx').show();
					 $('#htcx').click();
					 
				 }
			 }
			
		}	
	}	
});