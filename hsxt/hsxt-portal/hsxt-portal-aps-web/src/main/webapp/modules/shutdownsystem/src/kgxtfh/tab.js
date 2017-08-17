define(['text!shutdownsystemTpl/kgxtfh/tab.html',
		'shutdownsystemSrc/kgxtfh/kgxtfh'
		], function(tab, kgxtfh){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			//开关系统复核
			$('#kgxtfh').click(function(){
				kgxtfh.showPage();
				comm.liActive($('#kgxtfh'),'#gbqyfh, #kqqyfh');
			}.bind(this));
			
			//权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("052103000000");
			
			//遍历开关系统复核的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			{
				 //开关系统复核
				 if(match[i].permId =='052103010000')
				 {
					 $('#kgxtfh').show();
					 $('#kgxtfh').click();
					 
				 }
			}
		}	
	}	
});