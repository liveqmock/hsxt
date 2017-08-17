define(['text!debitTpl/lzlr/tab.html',
		'debitSrc/lzlr/lzlr'
		], function(tab, lzlr){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			//临账录入
			$('#lzlr').click(function(){
				lzlr.showPage();
				comm.liActive($('#lzlr'));
			}.bind(this));
			
			//权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("050402000000");
			
			//遍历临账录入的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			{
				 //临账录入
				 if(match[i].permId =='050402010000')
				 {
					 $('#lzlr').show();
					 $('#lzlr').click();
					 
				 }
			}
		}
	}	
});