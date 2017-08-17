define(['text!scoremanageTpl/flzgcx/tab.html',
		'scoremanageSrc/flzgcx/hsywsh',
		'scoremanageSrc/flzgcx/mfylbtjh'
		], function(tab, hsywsh, mfylbtjh){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			
			//互生意外伤害
			$('#hsywsh').click(function(){
				hsywsh.showPage();
				comm.liActive($('#hsywsh'));
			}.bind(this));
			
			//互生医疗补贴计划
			$('#mfylbtjh').click(function(){
				mfylbtjh.showPage();
				comm.liActive($('#mfylbtjh'));
			}.bind(this));
			
			//权限设置后默认选择规则
            var isModulesDefault = false;	//是否已有默认选择菜单
			var match = comm.findPermissionJsonByParentId("050806000000");
			
			//遍历福利资格查询的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			 {
				 //互生意外伤害
				 if(match[i].permId =='050806010000')
				 {
					 $('#hsywsh').show();
					 $('#hsywsh').click();
					 //已经设置默认值
					 isModulesDefault = true;
				 }
				 //互生医疗补贴计划
				 else if(match[i].permId =='050806020000')
				 {
					 $('#mfylbtjh').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#mfylbtjh').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				 
			 }
		}	
	}	
});