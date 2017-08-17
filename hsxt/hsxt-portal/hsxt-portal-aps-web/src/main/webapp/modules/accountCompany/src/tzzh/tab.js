define(['text!accountCompanyTpl/tzzh/tab.html',
		'accountCompanySrc/tzzh/zftzmxcx',
		'accountCompanySrc/tzzh/tzfhmxcx',
		'accountCompanyLan'
		], function (tpl, zftzmxcx, tzfhmxcx) {
	return {
		showPage : function () {
			$('.operationsInner').html(tpl);
			zftzmxcx.showPage();
			
			//积分投资明细查询
			$('#tzzh_zftzmxcx').click(function (e) {
				comm.liActive($(this), '#tzzh_tzfhxq', '#busibox_xq');
				zftzmxcx.showPage();
			});

			//投资分红明细查询
			$('#tzzh_tzfhmxcx').click(function (e) {
				comm.liActive($(this), '#tzzh_tzfhxq', '#busibox_xq');
				tzfhmxcx.showPage();
			});
			
			//权限设置后默认选择规则
            var isModulesDefault = false;	//是否已有默认选择菜单
			var match = comm.findPermissionJsonByParentId("052604000000");
			
			//遍历投资账户的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			 {
				 //积分投资流水
				 if(match[i].permId =='052604010000')
				 {
					 $('#tzzh_zftzmxcx').show();
					 $('#tzzh_zftzmxcx').click();
					 //已经设置默认值
					 isModulesDefault = true;
				 }
				 //投资分红流水
				 else if(match[i].permId =='052604020000')
				 {
					 $('#tzzh_tzfhmxcx').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#tzzh_tzfhmxcx').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				 
			 }
		}
	}
});