define(['text!accountManageTpl/csssdjzh/tab.html',
		'accountManageSrc/csssdjzh/zhye',
		'accountManageSrc/csssdjzh/mxcx',
		'accountManageSrc/csssdjzh/sltzsq',
		'accountManageSrc/csssdjzh/sltzsqcx'
		],function(tpl, zhye, mxcx, sltzsq, sltzsqcx){
	return {
		showPage : function(){
			$('.operationsInner').html(tpl);
			//账户余额
			$('#csssdjzh_zhye').click(function(e) {
				comm.liActive($(this));
                zhye.showPage();
            });
			
			//明细查询
			$('#csssdjzh_mxcx').click(function(e) {
				comm.liActive($(this));
                mxcx.showPage();
            });
			
			//税率调整申请
			$('#csssdjzh_sltzsq').click(function(e) {
				comm.liActive($(this));
                sltzsq.showPage();
            });
			
			//税率调整申请查询
			$('#csssdjzh_sltzsqcx').click(function(e) {
				comm.liActive($(this));
                sltzsqcx.showPage();
            });
			
			//获取缓存中投资账户三级菜单
			var match = comm.findPermissionJsonByParentId("020105000000"); 
			var isModulesDefault = false;
			 for(var i = 0 ; i< match.length ; i++)
			 {
				 //账户余额
				 if(match[i].permId =='020105010000')
				 {
					 $('#csssdjzh_zhye').show();
					 //默认选中
					 $('#csssdjzh_zhye').click();
					 //已经设置默认值
					 isModulesDefault = true;
					 
				 }
				 //明细查询
				 else if(match[i].permId =='020105020000')
				 {
					 $('#csssdjzh_mxcx').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#csssdjzh_mxcx').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				 //税率调整申请
				 else if(match[i].permId =='020105030000')
				 {
					 $('#csssdjzh_sltzsq').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#csssdjzh_mxcx').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				 //税率调整申请查询
				 else if(match[i].permId =='020105040000')
				 {
					 $('#csssdjzh_sltzsqcx').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#csssdjzh_mxcx').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
			 }
		}
	}
});