define(['text!zypeManageTpl/zypzsp/tab.html',
		'zypeManageSrc/zypzsp/ejzypzsp',
		'zypeManageSrc/zypzsp/sjzypzsp',
		], function(tab, ejzypzsp, sjzypzsp){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			
			$('#ejzypzsp').click(function(){
				ejzypzsp.showPage();
				comm.liActive($('#ejzypzsp'));
			}.bind(this));
			
			$('#sjzypzsp').click(function(){
				sjzypzsp.showPage();
				comm.liActive($('#sjzypzsp'));
			}.bind(this));
			
			//权限设置后默认选择规则
            var isModulesDefault = false;	//是否已有默认选择菜单
			var match = comm.findPermissionJsonByParentId("051705000000");
			
			//遍历资源数据一览表的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			 {
				 //二级资源配置审批
				 if(match[i].permId =='051705010000')
				 {
					 $('#ejzypzsp').show();
					 $('#ejzypzsp').click();
					 //已经设置默认值
					 isModulesDefault = true;
				 }
				 //三级资源配置审批
				 else if(match[i].permId =='051705020000')
				 {
					 $('#sjzypzsp').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#sjzypzsp').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				 
			 }	
		}	
	}	
});
