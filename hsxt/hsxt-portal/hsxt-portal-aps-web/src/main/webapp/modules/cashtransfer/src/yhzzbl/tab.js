define(['text!cashtransferTpl/yhzzbl/tab.html',
		'cashtransferSrc/yhzzbl/yhzzbl'
		], function(tab, yhzzbl){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			//银行转账办理
			$('#yhzzbl').click(function(){
				yhzzbl.showPage();
				comm.liActive($('#yhzzbl'));
			}.bind(this));
			
			//权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("050302000000");
			
			//遍历银行转账办理的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			{
				 //银行转账办理
				 if(match[i].permId =='050302010000')
				 {
					 $('#yhzzbl').show();
					 $('#yhzzbl').click();
					 
				 }
			}
		}
	}	
});