define(['text!accountManageTpl/hbzh/tab.html',
		'accountManageSrc/hbzh/zhye',
		'accountManageSrc/hbzh/hbzyh',
		'accountManageSrc/hbzh/mxcx'
		],function(tpl, zhye, hbzyh, mxcx){
	return {	 
		showPage : function(){			
			$('.operationsInner').html(tpl);
			//账户余额
			$('#hbzh_zhye').click(function(e) {
				comm.liActive($(this));
				zhye.showPage();
            });
			
			//货币转银行
			$('#hbzh_hbzyh').click(function(e) {	
				comm.liActive($(this));		
                hbzyh.showPage();
            });
			
			//明细查询
			$('#hbzh_mxcx').click(function(e) {
				comm.liActive($(this));				
                mxcx.showPage();
            });
			
			//获取缓存中货币账户三级菜单
			var isModulesDefault = false;
			var match = comm.findPermissionJsonByParentId("020103000000"); 
			
			 for(var i = 0 ; i< match.length ; i++)
			 {
				 //账户余额
				 if(match[i].permId =='020103010000')
				 {
					 $('#hbzh_zhye').show();
					 //默认选中
					 $('#hbzh_zhye').click();
					 //已经设置默认值
					 isModulesDefault = true;
				 }
				 //货币转银行
				 else if(match[i].permId =='020103020000')
				 {
					 $('#hbzh_hbzyh').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#hbzh_hbzyh').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				 //明细查询
				 else if(match[i].permId =='020103030000')
				 {
					 $('#hbzh_mxcx').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#hbzh_mxcx').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				 
			 }
		}
	}
});