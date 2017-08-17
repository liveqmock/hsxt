define(['text!infoManageTpl/qyzyspyw/tab.html',
		'infoManageSrc/qyzyspyw/cyqyzgzxsp',
		'infoManageSrc/qyzyspyw/cyqyzgzxspcx',
		'infoManageSrc/qyzyspyw/tzjfhdsp',
		'infoManageSrc/qyzyspyw/tzjfhdspcx',
		'infoManageSrc/qyzyspyw/cyjfhdsp',
		'infoManageSrc/qyzyspyw/cyjfhdspcx',
		'infoManageLan'
		], function(tab, cyqyzgzxsp, cyqyzgzxspcx, tzjfhdsp, tzjfhdspcx, cyjfhdsp, cyjfhdspcx){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			
			$('#cyqyzgzxsp').click(function(){
				cyqyzgzxsp.showPage();
				comm.liActive($('#cyqyzgzxsp'));
			}.bind(this));
			
			$('#cyqyzgzxspcx').click(function(){
				cyqyzgzxspcx.showPage();
				comm.liActive($('#cyqyzgzxspcx'));
			}.bind(this));
			
			$('#tzjfhdsp').click(function(){
				tzjfhdsp.showPage();
				comm.liActive($('#tzjfhdsp'));
			}.bind(this));
			
			$('#tzjfhdspcx').click(function(){
				tzjfhdspcx.showPage();
				comm.liActive($('#tzjfhdspcx'));
			}.bind(this));
			
			$('#cyjfhdsp').click(function(){
				cyjfhdsp.showPage();
				comm.liActive($('#cyjfhdsp'));
			}.bind(this));
			
			$('#cyjfhdspcx').click(function(){
				cyjfhdspcx.showPage();
				comm.liActive($('#cyjfhdspcx'));
			}.bind(this));
			
			//权限设置后默认选择规则
            var isModulesDefault = false;	//是否已有默认选择菜单
			var match = comm.findPermissionJsonByParentId("051602000000");
			
			//遍历福利资格查询的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			 {
				 //成员企业资格注销审批
				 if(match[i].permId =='051602010000')
				 {
					 $('#cyqyzgzxsp').show();
					 $('#cyqyzgzxsp').click();
					 //已经设置默认值
					 isModulesDefault = true;
				 }
				 //成员企业资格注销审批查询
				 else if(match[i].permId =='051602020000')
				 {
					 $('#cyqyzgzxspcx').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#cyqyzgzxspcx').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				//停止积分活动审批
				 else if(match[i].permId =='051602030000')
				 {
					 $('#tzjfhdsp').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#tzjfhdsp').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				//停止积分活动审批查询
				 else if(match[i].permId =='051602040000')
				 {
					 $('#tzjfhdspcx').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#tzjfhdspcx').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				//参与积分活动审批
				 else if(match[i].permId =='051602050000')
				 {
					 $('#cyjfhdsp').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#cyjfhdsp').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				//参与积分活动审批查询
				 else if(match[i].permId =='051602060000')
				 {
					 $('#cyjfhdspcx').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#cyjfhdspcx').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				 
			 }
		}
	}	
});