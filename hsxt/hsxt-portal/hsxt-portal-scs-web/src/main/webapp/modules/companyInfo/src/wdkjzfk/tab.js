define(['text!companyInfoTpl/wdkjzfk/tab.html',
		'companyInfoSrc/wdkjzfk/wdkjzfk'
		], function (tpl, wdkjzfk) {
	return {
		showPage : function () {
			$('.operationsInner').html(tpl);
			
			//我的快捷支付卡
			$('#wdkjzfk').click(function (e) {
				comm.liActive($(this), "#tjkjzfk");	
				wdkjzfk.showPage();
			});
			
			 //权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("030505000000");
			
			//遍历我的快捷支付卡查询的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			{
				 //我的快捷支付卡
				 if(match[i].permId =='030505010000')
				 {
					 $('#wdkjzfk').show();
					 $('#wdkjzfk').click();
					 
				 }
			}
		}
	}
});