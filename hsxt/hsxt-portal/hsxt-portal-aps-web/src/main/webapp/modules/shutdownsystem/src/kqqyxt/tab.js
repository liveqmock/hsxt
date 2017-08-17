define(['text!shutdownsystemTpl/kqqyxt/tab.html',
		'shutdownsystemSrc/kqqyxt/kqqyxt'
		], function(tab, kqqyxt){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			//开启企业系统
			$('#kqqyxt').click(function(){
				kqqyxt.showPage();
				comm.liActive($('#kqqyxt'),'#kqxt');
			}.bind(this));
			
			//权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("052102000000");
			
			//遍历开启企业系统的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			{
				 //开启企业系统
				 if(match[i].permId =='052102010000')
				 {
					 $('#kqqyxt').show();
					 $('#kqqyxt').click();
					 
				 }
			}
		}	
	}	
});