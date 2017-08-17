define(['text!zypeManageTpl/ejqypepz/tab.html',
		'zypeManageSrc/ejqypepz/ejqypepzsq',
		'zypeManageSrc/ejqypepz/ejqypepzcx'
		], function(tab, ejqypepzsq, ejqypepzcx){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			
			$('#ejqypepzsq').click(function(){
				ejqypepzsq.showPage();
				comm.liActive($('#ejqypepzsq'));
			}.bind(this));
			
			$('#ejqypepzcx').click(function(){
				ejqypepzcx.showPage();
				comm.liActive($('#ejqypepzcx'));
			}.bind(this));
			
			//权限设置后默认选择规则
            var isModulesDefault = false;	//是否已有默认选择菜单
			var match = comm.findPermissionJsonByParentId("051703000000");
			
			//遍历资源数据一览表的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			 {
				 //二级区域配额配置申请
				 if(match[i].permId =='051703010000')
				 {
					 $('#ejqypepzsq').show();
					 $('#ejqypepzsq').click();
					 //已经设置默认值
					 isModulesDefault = true;
				 }
				 //二级区域配额配置查询
				 else if(match[i].permId =='051703020000')
				 {
					 $('#ejqypepzcx').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#ejqypepzcx').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				 
			 }
				
		}	
	}	
});
