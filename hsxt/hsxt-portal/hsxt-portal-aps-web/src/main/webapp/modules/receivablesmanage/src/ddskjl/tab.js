define(['text!receivablesmanageTpl/ddskjl/tab.html',
		'receivablesmanageSrc/ddskjl/ddskjl'
		], function(tab, ddskjl){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			//订单收款记录
			$('#ddskjl').click(function(){
				ddskjl.showPage();
				comm.liActive($('#ddskjl'),'#cknr');
				$('#busibox').removeClass('none');
			}.bind(this));
			
			//权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("050203000000");
			
			//遍历订单收款记录的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			{
				 //订单收款记录
				 if(match[i].permId =='050203010000')
				 {
					 $('#ddskjl').show();
					 $('#ddskjl').click();
					 
				 }
			}
		}	
	}	
});