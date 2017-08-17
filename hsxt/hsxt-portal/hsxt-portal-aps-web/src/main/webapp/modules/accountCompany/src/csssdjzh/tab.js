define(['text!accountCompanyTpl/csssdjzh/tab.html',
		'accountCompanySrc/csssdjzh/mxcx',
		],function(tpl, mxcx){
	return {
		showPage : function(){
			$('.operationsInner').html(tpl);
			 mxcx.showPage();
			
			//明细查询
			$('#csssdjzh_mxcx').click(function(e) {
				comm.liActive($(this));
                mxcx.showPage();
            });
			
			//权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("052605000000");
			
			//遍历城市税收对接账户的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			{
				 //账户流水
				 if(match[i].permId =='052605010000')
				 {
					 $('#csssdjzh_mxcx').show();
					 $('#csssdjzh_mxcx').click();
					 
				 }
			}
		}
	}
});