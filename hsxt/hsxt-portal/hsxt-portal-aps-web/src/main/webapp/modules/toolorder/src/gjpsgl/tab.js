define(['text!toolorderTpl/gjpsgl/tab.html',
		'toolorderSrc/gjpsgl/gjqdmbgl',
		'toolorderSrc/gjpsgl/gjpsgl',
		'toolorderSrc/gjpsgl/gjqddy',
		'toolorderLan'
		], function(tab, gjqdmbgl, gjpsgl, gjqddy){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			
			$('#gjpsgl').click(function(){
				gjpsgl.showPage();
				comm.liActive($('#gjpsgl'));
			}.bind(this));
			
			$('#gjqddy').click(function(){
				gjqddy.showPage();
				comm.liActive($('#gjqddy'));
			}.bind(this));
			
			//权限设置后默认选择规则
            var isModulesDefault = false;	//是否已有默认选择菜单
			var match = comm.findPermissionJsonByParentId("050702000000");
			
			//遍历节余款管理的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			 {
				 //工具配送管理
				 if(match[i].permId =='050702010000')
				 {
					 $('#gjpsgl').show();
					 $('#gjpsgl').click();
					 //已经设置默认值
					 isModulesDefault = true;
				 }
				 //工具配送单打印
				 else if(match[i].permId =='050702020000')
				 {
					 $('#gjqddy').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#gjqddy').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
			 }
		}
	}	
});