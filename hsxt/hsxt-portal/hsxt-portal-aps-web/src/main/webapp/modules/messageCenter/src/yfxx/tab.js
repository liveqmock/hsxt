define(['text!messageCenterTpl/yfxx/tab.html',
		'messageCenterSrc/yfxx/yfxx'
		], function(tab, yfxx){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			
			$('#yfxx').click(function(){
				yfxx.showPage();
				comm.liActive($('#yfxx'),'#ck');
			}.bind(this));
			
			//权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("052202000000");
			
			//遍历已发消息的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			{
				 //已发消息
				 if(match[i].permId =='052202010000')
				 {
					 $('#yfxx').show();
					 $('#yfxx').click();
					 
				 }
			}
		}	
	}	
});