define(['text!accountManageTpl/tzzh/tab.html',
		'accountManageSrc/tzzh/zhye',
		'accountManageSrc/tzzh/zftzmxcx',
		'accountManageSrc/tzzh/tzfhmxcx',
		'accountManageLan'
		], function (tpl, zhye, zftzmxcx, tzfhmxcx) {
	return {
		showPage : function () {
			$('.operationsInner').html(tpl);
			//账户余额
			$('#tzzh_zhye').click(function (e) {
				comm.liActive($(this), '#tzzh_tzfhxq', '#busibox_xq');
				zhye.showPage();
			});

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
			
			//获取缓存中投资账户三级菜单
			 var isModulesDefault = false;
			var match = comm.findPermissionJsonByParentId("020104000000"); 

			 for(var i = 0 ; i< match.length ; i++)
			 {
				 //积分投资总数
				 if(match[i].permId =='020104010000')
				 {
					 $('#tzzh_zhye').show();
					 $('#tzzh_zhye').click();
					 //已经设置默认值
					 isModulesDefault = true;
				 }
				 //积分投资明细查询
				 else if(match[i].permId =='020104020000')
				 {
					 $('#tzzh_zftzmxcx').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#tzzh_zftzmxcx').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				 //投资分红明细查询
				 else if(match[i].permId =='020104030000')
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