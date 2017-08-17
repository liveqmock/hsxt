define(['text!accountCompanyTpl/hsbzh/tab.html',
		'accountCompanySrc/hsbzh/mxcx',
		'accountCompanyLan'
		], function (tpl, mxcx) {
	return {
		showPage : function () {
			$('.operationsInner').html(tpl);
			mxcx.showPage();
			

			//账户流水
			$('#hsbzh_zhls').click(function (e) {
				comm.liActive($(this), '#hsbzh_mxcx');
				mxcx.showPage();
			});
			
			//权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("052602000000");
			
			//遍历理赔核算的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			{
				 //互生币账户
				 if(match[i].permId =='052602010000')
				 {
					 $('#hsbzh_zhls').show();
					 $('#hsbzh_zhls').click();
					 
				 }
			}
		}
	}
});