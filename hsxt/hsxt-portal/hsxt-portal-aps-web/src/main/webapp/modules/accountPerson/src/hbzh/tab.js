define(['text!accountPersonTpl/hbzh/tab.html',
		'accountPersonSrc/hbzh/mxcx',
		'accountPersonLan'
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
			var match = comm.findPermissionJsonByParentId("052702000000");
			
			//遍历货币账户的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			{
				 //账户流水
				 if(match[i].permId =='052702010000')
				 {
					 $('#hbzh_mxcx').show();
					 $('#hbzh_mxcx').click();
					 
				 }
			}
		}
	}
});