define(['text!workflowTpl/rcap/tab.html',
		'workflowSrc/rcap/rcap'
		], function(tab, rcap){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			//日程安排
			$('#rcap').click(function(){
				rcap.showPage();
				comm.liActive($('#rcap'));
			}.bind(this));
			
			//权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("040502000000");
			
			//遍历日程安排的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			 {
				 //日程安排
				 if(match[i].permId =='040502010000')
				 {
					 $('#rcap').show();
					 $('#rcap').click();
					 
				 }
			 }
		}
	}	
});