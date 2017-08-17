define(['text!accountPersonTpl/tzzh/tab.html',
		'accountPersonSrc/tzzh/zftzmxcx',
		'accountPersonSrc/tzzh/tzfhmxcx',
		'accountPersonLan'
		], function (tpl, zftzmxcx, tzfhmxcx) {
	return {
		showPage : function () {
			$('.operationsInner').html(tpl);
			zftzmxcx.showPage();

			//积分投资明细查询
			$('#tzzh_zftzmxcx').click(function (e) {
				comm.liActive($(this), '#busibox_xq');
				zftzmxcx.showPage();
			});

			//投资分红明细查询
			$('#tzzh_tzfhmxcx').click(function (e) {
				comm.liActive($(this), '#tzzh_tzfhxq', '#busibox_xq');
				tzfhmxcx.showPage();
			});
			
			//权限设置后默认选择规则
            var isModulesDefault = false;	//是否已有默认选择菜单
			var match = comm.findPermissionJsonByParentId("052704000000");
			
			//遍历福利资格查询的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			 {
				 //积分投资流水
				 if(match[i].permId =='052704010000')
				 {
					 $('#tzzh_zftzmxcx').show();
					 $('#tzzh_zftzmxcx').click();
					 //已经设置默认值
					 isModulesDefault = true;
				 }
				 //投资分红流水
				 else if(match[i].permId =='052704020000')
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