define(['text!safeSetTpl/dlmm/tab.html',
		'safeSetSrc/dlmm/dlmm'
		], function(tab, dlmm){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			//修改登录密码
			$('#xgdlmm').click(function(){
				dlmm.showPage();
				comm.liActive($('#xgdlmm'));
			}.bind(this));
			
			//权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("051501000000");
			
			//遍历登录密码的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			{
				 //修改登录密码
				 if(match[i].permId =='051501010000')
				 {
					 $('#xgdlmm').show();
					 $('#xgdlmm').click();
					 
				 }
			}
		}	
	}	
});