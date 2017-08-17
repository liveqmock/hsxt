define(['text!shutdownsystemTpl/gbqyxt/tab.html',
		'shutdownsystemSrc/gbqyxt/gbqyxt'
		], function(tab, gbqyxt){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			//关闭企业系统
			$('#gbqyxt').click(function(){
				gbqyxt.showPage();
				comm.liActive($('#gbqyxt'),'#gbxt');
			}.bind(this));
			
			//权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("052101000000");
			
			//遍历关闭企业系统的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			{
				 //关闭企业系统
				 if(match[i].permId =='052101010000')
				 {
					 $('#gbqyxt').show();
					 $('#gbqyxt').click();
					 
				 }
			}
		}
	}	
});