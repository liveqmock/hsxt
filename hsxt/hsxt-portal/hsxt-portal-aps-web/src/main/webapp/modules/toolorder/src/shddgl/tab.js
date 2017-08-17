define(['text!toolorderTpl/shddgl/tab.html',
        'toolorderSrc/shddgl/gjshddlb',
		'toolorderSrc/shddgl/shddsh',
		'toolorderSrc/shddgl/hsjfgjcl',
		'toolorderSrc/shddgl/shgjzz',
		'toolorderSrc/shddgl/shgjps',
		'toolorderSrc/shddgl/shgjpsqddy',
		'toolorderSrc/shddgl/gjtkddyw'
		], function(tab, gjshddlb ,shddsh, hsjfgjcl, shgjzz, shgjps, shgjpsqddy, gjtkddyw){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			
			$('#shddsh').click(function(){
				shddsh.showPage();
				comm.liActive($('#shddsh'));
			}.bind(this));
			
			$('#hsjfgjcl').click(function(){
				hsjfgjcl.showPage();
				comm.liActive($('#hsjfgjcl'));
			}.bind(this)).click();
			
			$('#shgjzz').click(function(){
				shgjzz.showPage();
				comm.liActive($('#shgjzz'));
			}.bind(this));
			
			$('#shgjps').click(function(){
				shgjps.showPage();
				comm.liActive($('#shgjps'));
			}.bind(this));
			
			$('#shgjpsqddy').click(function(){
				shgjpsqddy.showPage();
				comm.liActive($('#shgjpsqddy'));
			}.bind(this));
			
			$('#gjtkddyw').click(function(){
				gjtkddyw.showPage();
				comm.liActive($('#gjtkddyw'));
			}.bind(this));
			
			$('#gjshddlb').click(function(){
				gjshddlb.showPage();
				comm.liActive($('#gjshddlb'));
			}.bind(this));

			//权限设置后默认选择规则
            var isModulesDefault = false;	//是否已有默认选择菜单
			var match = comm.findPermissionJsonByParentId("050703000000");
			
			//遍历节余款管理的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			 {
				 
				//售后工具处理
				if(match[i].permId =='050703010000')
				 {
					 $('#hsjfgjcl').show();
					 $('#hsjfgjcl').click();
					 //已经设置默认值
					 isModulesDefault = true;
				 }
				 //售后订单审核
				 else if(match[i].permId =='050703020000')
				 {
					 $('#shddsh').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#shddsh').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				//售后工具制作
				 else if(match[i].permId =='050703030000')
				 {
					 $('#shgjzz').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#shgjzz').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				//售后工具配送
				 else if(match[i].permId =='050703040000')
				 {
					 $('#shgjps').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#shgjps').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				//售后工具配送清单打印
				 else if(match[i].permId =='050703050000')
				 {
					 $('#shgjpsqddy').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#shgjpsqddy').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				//售后订单列表查询
				 else if(match[i].permId =='050703060000')
				{
					 $('#gjshddlb').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#gjshddlb').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				}
			 }			
		}	
	}	
});