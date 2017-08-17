define(['text!toolorderTpl/gxkdzfw/tab.html',
		'toolorderSrc/gxkdzfw/gxkdzfw',
		'toolorderLan'
		], function(tab, gxkdzfw){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			//个性卡定制服务
			$('#gxkdzfw').click(function(){
				gxkdzfw.showPage();
				comm.liActive($('#gxkdzfw'));
			}.bind(this));
			
			$('#hsjfgjcl').click(function(){
				hsjfgjcl.showPage();
				comm.liActive($('#hsjfgjcl'));
			}.bind(this));
			
			//权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("050704000000");
			
			//遍历个性卡定制服务的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			{
				 //个性卡定制服务
				 if(match[i].permId =='050704010000')
				 {
					 $('#gxkdzfw').show();
					 $('#gxkdzfw').click();
					 
				 }
			}
			
		}	
	}	
});