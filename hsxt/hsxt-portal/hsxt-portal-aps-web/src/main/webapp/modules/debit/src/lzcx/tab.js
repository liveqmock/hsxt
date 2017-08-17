define(['text!debitTpl/lzcx/tab.html',
		'debitSrc/lzcx/lzcx'
		], function(tab, lzcx){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			//临账查询
			$('#lzcx').click(function(){
				lzcx.showPage();
				comm.liActive($('#lzcx'), '#ckxq, #lzxg, #lztksq');
				$('#fhxxDiv, #tkDiv').addClass('none');
			}.bind(this));
			
			//权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("050401000000");
			
			//遍历临账查询的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			{
				 //临账查询
				 if(match[i].permId =='050401010000')
				 {
					 $('#lzcx').show();
					 $('#lzcx').click();
					 
				 }
			}
		}
	}	
});