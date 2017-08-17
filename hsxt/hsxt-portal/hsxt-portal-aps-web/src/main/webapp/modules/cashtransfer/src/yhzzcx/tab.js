define(['text!cashtransferTpl/yhzzcx/tab.html',
		'cashtransferSrc/yhzzcx/yhzzcx'
		], function(tab, yhzzcx){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			//银行转账查询
			$('#yhzzcx').click(function(){
				yhzzcx.showPage();
				comm.liActive($('#yhzzcx'),'#yhzzcx_ck1, #yhzzcx_ck2');
			}.bind(this));
			
			//权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("050301000000");
			
			//遍历银行转账查询的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			{
				 //银行转账查询
				 if(match[i].permId =='050301010000')
				 {
					 $('#yhzzcx').show();
					 $('#yhzzcx').click();
					 
				 }
			}
		}	
	}	
});