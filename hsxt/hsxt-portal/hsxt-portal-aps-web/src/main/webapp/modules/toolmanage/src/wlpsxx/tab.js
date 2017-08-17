define(['text!toolmanageTpl/wlpsxx/tab.html',
		'toolmanageSrc/wlpsxx/psfsxx'
		], function(tab, psfsxx){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			//配送方式信息
			$('#psfsxx').click(function(){
				psfsxx.showPage();
				comm.liActive($('#psfsxx'));
			}.bind(this));
			//权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("051802000000");
			
			//遍历配送方式信息的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			{
				 //配送方式信息
				 if(match[i].permId =='051802010000')
				 {
					 $('#psfsxx').show();
					 $('#psfsxx').click();
					 
				 }
			}
		}
	}	
});