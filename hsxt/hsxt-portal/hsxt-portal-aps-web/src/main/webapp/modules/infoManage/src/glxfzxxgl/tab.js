define(['text!infoManageTpl/glxfzxxgl/tab.html',
		'infoManageSrc/glxfzxxgl/glxfzxxwh',
		'infoManageLan'
		], function(tab, glxfzxxwh){
	return {
		showPage : function(obj){
			$('.operationsInner').html(_.template(tab));
			//关联消费者信息维护
			$('#glxfzxxwh').click(function(){
				glxfzxxwh.showPage(obj);
				comm.liActive($('#glxfzxxwh'), '#xgxfzxx');
			}.bind(this));
			
			//权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("051606000000");
			
			//遍历关联消费者信息管理的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			{
				 //关联消费者信息维护
				 if(match[i].permId =='051606010000')
				 {
					 $('#glxfzxxwh').show();
					 $('#glxfzxxwh').click();
					 
				 }
			}
		}	
	}	
});