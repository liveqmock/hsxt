define(['text!toolmanageTpl/ckkcgl/tab.html',
		'toolmanageSrc/ckkcgl/ckxx',
		'toolmanageSrc/ckkcgl/gjrk',
		'toolmanageSrc/ckkcgl/kctj',
		'toolmanageSrc/ckkcgl/kcyj',
		'toolmanageSrc/ckkcgl/gjcx',
		'toolmanageSrc/ckkcgl/gjdj'
		], function(tab, ckxx, gjrk, kctj, kcyj, gjcx, gjdj, crklscx/*, gjtj*/){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			
			$('#ckxx').click(function(){
				ckxx.showPage();
				comm.liActive($('#ckxx'));
			}.bind(this));
			
		
			
			$('#gjrk').click(function(){
				gjrk.showPage();
				comm.liActive($('#gjrk'));
			}.bind(this));
			
			$('#kctj').click(function(){
				kctj.showPage();
				comm.liActive($('#kctj'));
			}.bind(this));
			
			$('#kcyj').click(function(){
				kcyj.showPage();
				comm.liActive($('#kcyj'));
			}.bind(this));
			
			$('#gjcx').click(function(){
				gjcx.showPage();
				comm.liActive($('#gjcx'));
			}.bind(this));
			
			$('#gjdj').click(function(){
				gjdj.showPage();
				comm.liActive($('#gjdj'));
			}.bind(this));
			
			$('#crklscx').click(function(){
				require(['toolmanageSrc/ckkcgl/ckkcgl_sub_tab'], function(subTab){
					subTab.showPage();
				});
				comm.liActive($('#crklscx'));
			}.bind(this));
			
			$('#gjtj').click(function(){
				require(['toolmanageSrc/ckkcgl/gjtj_sub_tab'], function(subTab){
					subTab.showPage();
				});
				comm.liActive($('#gjtj'));
			}.bind(this));
			
			//权限设置后默认选择规则
            var isModulesDefault = false;	//是否已有默认选择菜单
			var match = comm.findPermissionJsonByParentId("051803000000");
			
			//遍历福利资格查询的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			 {
				 //仓库信息
				 if(match[i].permId =='051803010000')
				 {
					 $('#ckxx').show();
					 $('#ckxx').click();
					 //已经设置默认值
					 isModulesDefault = true;
				 }
				 //工具入库
				 else if(match[i].permId =='051803020000')
				 {
					 $('#gjrk').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#gjrk').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				//库存统计
				 else if(match[i].permId =='051803030000')
				 {
					 $('#kctj').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#kctj').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				//库存预警
				 else if(match[i].permId =='051803040000')
				 {
					 $('#kcyj').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#kcyj').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				//工具查询
				 else if(match[i].permId =='051803050000')
				 {
					 $('#gjcx').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#gjcx').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				//工具登记
				 else if(match[i].permId =='051803060000')
				 {
					 $('#gjdj').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#gjdj').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				//出入库流水查询
				 else if(match[i].permId =='051803070000')
				 {
					 $('#crklscx').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#crklscx').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				//工具统计
				 else if(match[i].permId =='051803080000')
				 {
					 $('#gjtj').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#gjtj').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				 
			 }
		}	
	}	
});