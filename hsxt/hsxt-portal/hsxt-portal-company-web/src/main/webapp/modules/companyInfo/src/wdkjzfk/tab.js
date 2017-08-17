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
			
			 //获取缓存我的快捷支付卡三级菜单
			var match = comm.findPermissionJsonByParentId("020405000000"); 
	
			 for(var i = 0 ; i< match.length ; i++)
			 {
				 //我的快捷支付卡
				 if(match[i].permId =='020405010000')
				 {
					 $('#wdkjzfk').show();
					 $('#wdkjzfk').click();
				 }
				
			 }
		}
	}
});