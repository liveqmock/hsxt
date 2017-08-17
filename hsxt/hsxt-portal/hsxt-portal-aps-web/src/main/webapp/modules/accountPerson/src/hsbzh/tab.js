define(['text!accountPersonTpl/hsbzh/tab.html',
		'accountPersonSrc/hsbzh/mxcx',
		'accountPersonLan'
		], function (tpl, mxcx) {
	return {
		showPage : function () {
			$('.operationsInner').html(tpl);
			mxcx.showPage();
			
			//明细查询
			$('#hsbzh_mxcx').click(function (e) {
				comm.liActive($(this));
				mxcx.showPage();
			});
			
			//权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("052702000000");
			
			//遍历互生币账户的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			{
				 //账户流水
				 if(match[i].permId =='052702010000')
				 {
					 $('#hsbzh_mxcx').show();
					 $('#hsbzh_mxcx').click();
					 
				 }
			}
			
		}
	}
});