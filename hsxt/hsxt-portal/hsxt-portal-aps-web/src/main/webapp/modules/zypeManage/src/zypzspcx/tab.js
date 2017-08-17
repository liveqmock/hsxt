define(['text!zypeManageTpl/zypzspcx/tab.html',
		'zypeManageSrc/zypzspcx/ejzypzspcx',
		'zypeManageSrc/zypzspcx/sjzypzspcx',
		], function(tab, ejzypzspcx, sjzypzspcx){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			
			$('#ejzypzspcx').click(function(){
				ejzypzspcx.showPage();
				comm.liActive($('#ejzypzspcx'));
			}.bind(this));
			
			$('#sjzypzspcx').click(function(){
				sjzypzspcx.showPage();
				comm.liActive($('#sjzypzspcx'));
			}.bind(this));
			
			//权限设置后默认选择规则
            var isModulesDefault = false;	//是否已有默认选择菜单
			var match = comm.findPermissionJsonByParentId("051706000000");
			
			//遍历资源数据一览表的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			 {
				 //二级资源配置审批查询
				 if(match[i].permId =='051706010000')
				 {
					 $('#ejzypzspcx').show();
					 $('#ejzypzspcx').click();
					 //已经设置默认值
					 isModulesDefault = true;
				 }
				 //三级资源配置审批查询
				 else if(match[i].permId =='051706020000')
				 {
					 $('#sjzypzspcx').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#sjzypzspcx').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				 
			 }	
		}	
	}	
});
