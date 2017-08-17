define(['text!shutdownsystemTpl/kgxtshcx/tab.html',
		'shutdownsystemSrc/kgxtshcx/kgxtshcx'
		], function(tab, kgxtshcx){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			//开关系统审核查询
			$('#kgxtshcx').click(function(){
				kgxtshcx.showPage();
				comm.liActive($('#kgxtshcx') );
			}.bind(this)).click();	
			
			//权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("052104000000");
			
			//遍历开关系统审核查询的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			{
				 //开关系统审核查询
				 if(match[i].permId =='052104010000')
				 {
					 $('#kgxtshcx').show();
					 $('#kgxtshcx').click();
					 
				 }
			}
		}	
	}	
});