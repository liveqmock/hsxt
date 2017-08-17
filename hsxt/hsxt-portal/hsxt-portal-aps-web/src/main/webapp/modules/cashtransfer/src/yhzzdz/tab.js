define(['text!cashtransferTpl/yhzzdz/tab.html',
		'cashtransferSrc/yhzzdz/yhzzdz',
		'cashtransferSrc/yhzzdz/tkywcl'
		], function(tab, yhzzdz, tkywcl){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			//银行转账对账
			$('#yhzzdz').click(function(){
				yhzzdz.showPage();
				comm.liActive($('#yhzzdz'));
			}.bind(this));
			//退款业务处理
			$('#tkywcl').click(function(){
				tkywcl.showPage();
				comm.liActive($('#tkywcl'));
			}.bind(this));
			
			
			//权限设置后默认选择规则
            var isModulesDefault = false;	//是否已有默认选择菜单
			var match = comm.findPermissionJsonByParentId("050303000000");
			
			//遍历银行转账对账的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			 {
				 //银行转账对账
				 if(match[i].permId =='050303010000')
				 {
					 $('#yhzzdz').show();
					 $('#yhzzdz').click();
					 //已经设置默认值
					 isModulesDefault = true;
				 }
				 //退款业务处理
				 else if(match[i].permId =='050303020000')
				 {
					 $('#tkywcl').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#tkywcl').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
			 }
		}
	}	
});