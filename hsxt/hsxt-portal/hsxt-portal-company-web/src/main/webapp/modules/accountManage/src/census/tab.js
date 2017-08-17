define(['text!accountManageTpl/census/tab.html',
		'accountManageSrc/census/yfzfkctj',
		'accountManageSrc/census/yfzfkctjPOS',
		'accountManageSrc/census/yfzfkctjCZY',
		'accountManageSrc/census/tjscxs',
		'accountManageSrc/census/tjxxxs',
		'accountManageSrc/census/tjjfjeth',
		'accountManageLan'
		], function (tpl, yfzfkctj, yfzfkctjPOS, yfzfkctjCZY, tjscxs, tjxxxs, tjjfjeth) {
	return {
		showPage : function () {
			$('.operationsInner').html(tpl);
			
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
			
			//商城销售统计
			$('#census_tjscxs').click(function (e) {
				comm.liActive($(this));
				tjscxs.showPage();
			});
			
			//线下销售统计
			$('#census_tjxxxs').click(function (e) {
				comm.liActive($(this));
				tjxxxs.showPage();
			});
			
			//积分金额退回统计
			$('#census_tjjfjeth').click(function (e) {
				comm.liActive($(this));
				tjjfjeth.showPage();
			});
			//获取缓存中积分账户
			var isModulesDefault = false;
			var match = comm.findPermissionJsonByParentId("020106000000"); 

			 for(var i = 0 ; i< match.length ; i++)
			 {
				//消费积分扣除统计
				  if(match[i].permId =='020106010000')
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
				 else if(match[i].permId =='020106020000')
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
				 else if(match[i].permId =='020106030000')
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
				//商城销售统计
				 else if(match[i].permId =='020106040000')
				 {
					 $('#census_tjxxxs').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#census_tjxxxs').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				//线下销售统计
				 else if(match[i].permId =='020106050000')
				 {
					 $('#census_tjxxxs').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#census_tjxxxs').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 } 
				  
				  
			 }
		}
	}
});