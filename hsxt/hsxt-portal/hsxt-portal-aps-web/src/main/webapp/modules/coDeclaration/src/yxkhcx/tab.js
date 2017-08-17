define(['text!coDeclarationTpl/yxkhcx/tab.html',
		'coDeclarationSrc/yxkhcx/yxkhcx'
		], function(tab, yxkhcx){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			//意向客户查询
			$('#yxkhcx').click(function(){
				yxkhcx.showPage();
				comm.liActive($('#yxkhcx'));
			}.bind(this));
			
			//权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("050106000000");
			
			//遍历意向客户查询的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			{
				 //意向客户查询
				 if(match[i].permId =='050106010000')
				 {
					 $('#yxkhcx').show();
					 $('#yxkhcx').click();
					 
				 }
			}
			
		}	
	}	
});