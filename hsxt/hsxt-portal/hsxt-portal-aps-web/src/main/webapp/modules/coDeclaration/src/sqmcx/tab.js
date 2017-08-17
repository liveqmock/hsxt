define(['text!coDeclarationTpl/sqmcx/tab.html',
		'coDeclarationSrc/sqmcx/sqmcx'
		], function(tab, sqmcx){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			//授权码查询
			$('#sqmcx').click(function(){
				sqmcx.showPage();
				comm.liActive($('#sqmcx'));
			}.bind(this));
			
			//权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("050104000000");
			
			//遍历授权码查询的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			{
				 //授权码查询
				 if(match[i].permId =='050104010000')
				 {
					 $('#sqmcx').show();
					 $('#sqmcx').click();
					 
				 }
			}
			
		}	
	}	
});