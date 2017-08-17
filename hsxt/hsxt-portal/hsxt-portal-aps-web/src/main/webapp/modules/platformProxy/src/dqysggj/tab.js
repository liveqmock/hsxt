define(['text!platformProxyTpl/dqysggj/tab.html',
		'platformProxySrc/dqysggj/ptdggj',
		'platformProxySrc/dqysggj/ptdghsk',
		'platformProxySrc/dqysggj/ptdggjfh',
		'platformProxySrc/dqysggj/ptdggxkdzfw',
		'platformProxySrc/dqysggj/ptdggjck'
		], function(tab, ptdggj, ptdghsk, ptdggjfh, ptdggxkdzfw,ptdggjck){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			
			$('#dqysggj_ptdggj').click(function(){
				ptdggj.showPage();
				comm.liActive($('#dqysggj_ptdggj'));
			}.bind(this));
			
			$('#dqysggj_ptdghsk').click(function(){
				ptdghsk.showPage();
				comm.liActive($('#dqysggj_ptdghsk'));
			}.bind(this));
			
			$('#dqysggj_ptdggjfh').click(function(){
				ptdggjfh.showPage();
				comm.liActive($('#dqysggj_ptdggjfh'));
			}.bind(this));
			
			$('#dqysggj_ptdggxkdzfw').click(function(){
				ptdggxkdzfw.showPage();
				comm.liActive($('#dqysggj_ptdggxkdzfw'));
			}.bind(this));
			
			$('#dqysggj_ptdggjck').click(function(){
				ptdggjck.showPage();
				comm.liActive($('#dqysggj_ptdggjck'));
			}.bind(this));
			
			
			//权限设置后默认选择规则
            var isModulesDefault = false;	//是否已有默认选择菜单
			var match = comm.findPermissionJsonByParentId("051203000000");
			
			//遍历交易密码重置的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			 {
				 //平台代购工具
				 if(match[i].permId =='051203010000')
				 {
					 $('#dqysggj_ptdggj').show();
					 $('#dqysggj_ptdggj').click();
					 //已经设置默认值
					 isModulesDefault = true;
				 }
				 //平台代购互生卡
				 else if(match[i].permId =='051203020000')
				 {
					 $('#dqysggj_ptdghsk').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#dqysggj_ptdghsk').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				//平台代购工具复核
				 else if(match[i].permId =='051203030000')
				 {
					 $('#dqysggj_ptdggjfh').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#dqysggj_ptdggjfh').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				//平台代购个性卡定制服务
				 else if(match[i].permId =='051203040000')
				 {
					 $('#dqysggj_ptdggxkdzfw').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#dqysggj_ptdggxkdzfw').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				//平台代购工具审批查询
				 else if(match[i].permId =='051203050000')
				 {
					 $('#dqysggj_ptdggjck').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#dqysggj_ptdggjck').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
			 }
		}	
	}	
});