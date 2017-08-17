define(['text!systemBusinessTpl/xtywddcx/tab.html',
		'systemBusinessSrc/xtywddcx/xtywddcx',
		'systemBusinessLan'
		], function(tpl, xtywddcx){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tpl));
			
			//系统业务订单查询
			$('#xtywddcx').click(function(e){
				comm.liActive($(this));
				xtywddcx.showPage();
			});
			
			//获取缓存系统业务订单查询三级菜单
			var match = comm.findPermissionJsonByParentId("020207000000"); 

			 for(var i = 0 ; i< match.length ; i++)
			 {
				 //网上登记积分
				 if(match[i].permId =='020207010000')
				 {
					 $('#xtywddcx').show();
					//默认选中
					 $('#xtywddcx').click();
				 }
			 }
		}
	}
});