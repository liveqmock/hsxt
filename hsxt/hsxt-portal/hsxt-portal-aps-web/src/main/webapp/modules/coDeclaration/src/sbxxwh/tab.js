define(['text!coDeclarationTpl/sbxxwh/tab.html',
		'coDeclarationSrc/sbxxwh/glqysbzzdwh'
		], function(tab, glqysbzzdwh){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			//关联企业申报增值点维护
			$('#glqysbzzdwh').click(function(){
				glqysbzzdwh.showPage();
				comm.liActive($('#glqysbzzdwh'),'#xgzzd');
			}.bind(this)).click();
			
			//权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("050107000000");
			
			//遍历申报信息维护的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			{
				 //关联企业申报增值点维护
				 if(match[i].permId =='050107010000')
				 {
					 $('#glqysbzzdwh').show();
					 $('#glqysbzzdwh').click();
					 
				 }
			}
		}	
	}	
});