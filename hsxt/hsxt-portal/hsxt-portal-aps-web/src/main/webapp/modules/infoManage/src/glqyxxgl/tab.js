define(['text!infoManageTpl/glqyxxgl/tab.html',
		'infoManageSrc/glqyxxgl/glqyxxwh',
		'infoManageLan'
		], function(tab, glqyxxwh){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			//关联企业信息维护
			$('#glqyxxwh').click(function(){
				glqyxxwh.showPage();
				comm.liActive($('#glqyxxwh'), '#ckqyxxxx, #xgqyxx');
			}.bind(this));
			
			//权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("051605000000");
			
			//遍历关联企业信息管理的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			{
				 //关联企业信息维护
				 if(match[i].permId =='051605010000')
				 {
					 $('#glqyxxwh').show();
					 $('#glqyxxwh').click();
					 
				 }
			}
			
		}
	}	
});