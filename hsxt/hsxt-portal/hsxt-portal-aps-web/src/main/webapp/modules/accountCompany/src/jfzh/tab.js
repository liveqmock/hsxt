define(['text!accountCompanyTpl/jfzh/tab.html',
		'accountCompanySrc/jfzh/mxcx',
		'accountCompanyLan'
		], function (tpl, mxcx) {
	return {
		showPage : function () {
			$('.operationsInner').html(tpl);
			mxcx.showPage();
			
			//流水查询
			$('#jfzh_mxcx').click(function (e) {
				comm.liActive($(this), '#jfzh_mxcxqx', '#busibox_xq');
				mxcx.showPage();
			});
			
			//权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("052601000000");
			
			//遍历积分账户的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			{
				 //积分账户
				 if(match[i].permId =='052601010000')
				 {
					 $('#jfzh_mxcx').show();
					 $('#jfzh_mxcx').click();
					 
				 }
			}
		}
	}
});