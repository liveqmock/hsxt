define(['text!debitTpl/lztj/tab.html',
		'debitSrc/lztj/lztj'
		], function(tab, lztj){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			
			$('#lztj').click(function(){
				lztj.showPage();
				comm.liActive($('#lztj'), '#ckxq');
			}.bind(this));
			
			//权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("050408000000");
			
			//遍历临账关联复核的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			{
				 //临账关联复核
				 if(match[i].permId =='050408010000')
				 {
					 $('#lztj').show();
					 $('#lztj').click();
					 
				 }
			}
		}
	}	
});