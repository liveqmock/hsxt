define(['text!zypeManageTpl/sjqypepz/tab.html',
		'zypeManageSrc/sjqypepz/sjqypepzsq',
		'zypeManageSrc/sjqypepz/sjqypepzcx'
		], function(tab, sjqypepzsq, sjqypepzcx){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			
			$('#sjqypepzsq').click(function(){
				sjqypepzsq.showPage();
				comm.liActive($('#sjqypepzsq'));
			}.bind(this));
			
			$('#sjqypepzcx').click(function(){
				sjqypepzcx.showPage();
				comm.liActive($('#sjqypepzcx'));
			}.bind(this));

			//权限设置后默认选择规则
            var isModulesDefault = false;	//是否已有默认选择菜单
			var match = comm.findPermissionJsonByParentId("051704000000");
			
			//遍历资源数据一览表的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			 {
				 //三级区域配额配置申请
				 if(match[i].permId =='051704010000')
				 {
					 $('#sjqypepzsq').show();
					 $('#sjqypepzsq').click();
					 //已经设置默认值
					 isModulesDefault = true;
				 }
				 //三级区域配额配置查询
				 else if(match[i].permId =='051704020000')
				 {
					 $('#sjqypepzcx').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#sjqypepzcx').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				 
			 }
		}	
	}	
});
