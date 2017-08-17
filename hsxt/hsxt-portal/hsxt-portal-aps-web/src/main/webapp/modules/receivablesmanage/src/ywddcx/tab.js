define(['text!receivablesmanageTpl/ywddcx/tab.html',
		'receivablesmanageSrc/ywddcx/ywddcx'
		], function(tab, ywddcx){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			//业务订单查询
			$('#ywddcx').click(function(){
				ywddcx.showPage();
				comm.liActive($('#ywddcx'));
			}.bind(this));
			//权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("050201000000");
			
			//遍历业务订单查询的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			{
				 //业务订单查询
				 if(match[i].permId =='050201010000')
				 {
					 $('#ywddcx').show();
					 $('#ywddcx').click();
					 
				 }
			}
		}	
	}	
});