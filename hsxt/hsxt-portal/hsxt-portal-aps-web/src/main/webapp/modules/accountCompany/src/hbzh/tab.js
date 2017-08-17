define(['text!accountCompanyTpl/hbzh/tab.html',
		'accountCompanySrc/hbzh/mxcx',
		'accountCompanyLan'
		],function(tpl, mxcx){
	return {	 
		showPage : function(){			
			$('.operationsInner').html(tpl);
			mxcx.showPage();
			
			//明细查询
			$('#hbzh_mxcx').click(function(e) {
				comm.liActive($(this));				
                mxcx.showPage();
            });
			
			//权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("052603000000");
			
			//遍历理赔核算的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			{
				 //账户流水
				 if(match[i].permId =='052603010000')
				 {
					 $('#hbzh_mxcx').show();
					 $('#hbzh_mxcx').click();
					 
				 }
			}
		}
	}
});