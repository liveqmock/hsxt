define(['text!accountManageTpl/hsbzh/tab.html',
		'accountManageSrc/hsbzh/zhye',
		'accountManageSrc/hsbzh/dhhsb',
		'accountManageSrc/hsbzh/hsbzhb',
		'accountManageSrc/hsbzh/mxcx',
		'accountManageSrc/hsbzh/zfxesz',
		'accountManageSrc/hsbzh/yfzfkctj',
		'accountManageSrc/hsbzh/yfzfkctjPOS',
		'accountManageSrc/hsbzh/yfzfkctjCZY',
		'accountManageLan'
		], function (tpl, zhye, dhhsb, hsbzhb, mxcx, zfxesz, yfzfkctj, yfzfkctjPOS, yfzfkctjCZY) {
	return {
		showPage : function () {
			$('.operationsInner').html(tpl);
			//账户余额
			$('#hsbzh_zhye').click(function (e) {
				comm.liActive($(this));
				zhye.showPage();
			});

			//兑换互生币
			$('#hsbzh_dhhsb').click(function (e) {
				comm.liActive($(this));
				dhhsb.showPage();
			});

			//互生币转货币
			$('#hsbzh_hsbzhb').click(function (e) {
				comm.liActive($(this));
				hsbzhb.showPage();
			});

			//明细查询
			$('#hsbzh_mxcx').click(function (e) {
				comm.liActive($(this));
				mxcx.showPage();
			});
			
			//支付限额设置
			$('#hsbzh_zfxesz').click(function (e) {
				comm.liActive($(this), '#hsbzh_mxcx_2');
				zfxesz.showPage();
			});
			
			//消费积分扣除统计
			$('#hsbzh_tj').click(function (e) {
				comm.liActive($(this));
				yfzfkctj.showPage();
			}).click();
			
			//消费积分扣除统计-刷卡器/POS机
			$('#hsbzh_tjpos').click(function (e) {
				comm.liActive($(this));
				yfzfkctjPOS.showPage();
			});
			
			//消费积分扣除统计-操作员
			$('#hsbzh_tjczy').click(function (e) {
				comm.liActive($(this));
				yfzfkctjCZY.showPage();
			});
			
			
			//获取缓存中积分账户
			var isModulesDefault = false;
			var match = comm.findPermissionJsonByParentId("020102000000"); 

			 for(var i = 0 ; i< match.length ; i++)
			 {
				 //账户余额
				 if(match[i].permId =='020102010000')
				 {
					 $('#hsbzh_zhye').show();
					//默认选中
					 $('#hsbzh_zhye').click();
					 //已经设置默认值
					 isModulesDefault = true;
				 }
				 //兑换互生币
				 else if(match[i].permId =='020102020000')
				 {
					 $('#hsbzh_dhhsb').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#hsbzh_dhhsb').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				 //互生币转货币
				 else if(match[i].permId =='020102030000')
				 {
					 $('#hsbzh_hsbzhb').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#hsbzh_hsbzhb').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				 //明细查询
				 else if(match[i].permId =='020102040000')
				 {
					 $('#hsbzh_mxcx').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#hsbzh_mxcx').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				 //支付限额设置
//				 else if(match[i].permId =='020102050000')
//				 {
//					 $('#hsbzh_zfxesz').show();
//					 if(isModulesDefault == false )
//					 {
//						 //默认选中
//						 $('#hsbzh_zfxesz').click();
//						 //已经设置默认值
//						 isModulesDefault = true;
//					 }
//				 }
				//消费积分扣除统计
				 else if(match[i].permId =='020102050000')
				 {
					 $('#hsbzh_tj').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#hsbzh_tj').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				//消费积分扣除统计-刷卡器/POS机
				 else if(match[i].permId =='020102060000')
				 {
					 $('#hsbzh_tjpos').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#hsbzh_tjpos').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				//消费积分扣除统计-操作员
				 else if(match[i].permId =='020102070000')
				 {
					 $('#hsbzh_tjczy').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#hsbzh_tjczy').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
			 }
			
		}
	}
});