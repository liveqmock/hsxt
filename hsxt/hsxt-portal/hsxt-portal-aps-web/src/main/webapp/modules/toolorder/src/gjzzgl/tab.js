define(['text!toolorderTpl/gjzzgl/tab.html',
		'toolorderSrc/gjzzgl/gjddlb',
		'toolorderSrc/gjzzgl/skgjzz',
		'toolorderSrc/gjzzgl/skgjzzlb',
		'toolorderSrc/gjzzgl/hskzz',
		'toolorderSrc/gjzzgl/hskffsq',
		'toolorderLan'
		], function(tab, gjddlb, skgjzz, skgjzzlb,hskzz,hskffsq){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			
			$('#gjddlb').click(function(){
				gjddlb.showPage();
				comm.liActive($('#gjddlb'));
			}.bind(this));
			
			$('#skgjzz').click(function(){
				skgjzz.showPage();
				comm.liActive($('#skgjzz'));
			}.bind(this));
			
			$('#skgjzzlb').click(function(){
				skgjzzlb.showPage();
				comm.liActive($('#skgjzzlb'));
			}.bind(this));
			
			$('#hskzz').click(function(){
				hskzz.showPage();
				comm.liActive($('#hskzz'));
			}.bind(this));
			
			$('#hskffsq').click(function(){
				hskffsq.showPage();
				comm.liActive($('#hskffsq'));
			}.bind(this));
			
			//权限设置后默认选择规则
            var isModulesDefault = false;	//是否已有默认选择菜单
			var match = comm.findPermissionJsonByParentId("050701000000");
			//需要优化
			 $('#skgjzzlb').show();
			 if(isModulesDefault == false )
			 {
				 //默认选中
				 $('#skgjzzlb').click();
				 //已经设置默认值
				 isModulesDefault = true;
			 }
			 
				//需要优化
			 $('#hskffsq').show();
			 if(isModulesDefault == false )
			 {
				 //默认选中
				 $('#hskffsq').click();
				 //已经设置默认值
				 isModulesDefault = true;
			 }
			 
			//遍历工具制作管理的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			 {
				 //工具订单列表
				 if(match[i].permId =='050701010000')
				 {
					 $('#gjddlb').show();
					 $('#gjddlb').click();
					 //已经设置默认值
					 isModulesDefault = true;
				 }
				 //刷卡工具制作
				 else if(match[i].permId =='050701020000')
				 {
					 $('#skgjzz').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#skgjzz').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				//互生卡制作
				 else if(match[i].permId =='050701030000')
				 {
					 $('#hskzz').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#hskzz').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
			 }
			
		}	
	}	
});