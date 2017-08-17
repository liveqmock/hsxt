define(['text!debitTpl/lzglfh/tab.html',
		'debitSrc/lzglfh/lzglfh'
		], function(tab, lzglfh){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			//临账关联复核
			$('#lzglfh').click(function(){
				lzglfh.showPage();
				comm.liActive($('#lzglfh'), '#fh');
			}.bind(this));

			//权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("050406000000");
			
			//遍历临账关联复核的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			{
				 //临账关联复核
				 if(match[i].permId =='050406010000')
				 {
					 $('#lzglfh').show();
					 $('#lzglfh').click();
					 
				 }
			}
		}	
	}	
});