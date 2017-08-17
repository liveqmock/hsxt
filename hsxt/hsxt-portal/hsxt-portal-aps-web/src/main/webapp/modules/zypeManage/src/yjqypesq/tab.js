define(['text!zypeManageTpl/yjqypesq/tab.html',
		'zypeManageSrc/yjqypesq/yjqypesq',
		'zypeManageSrc/yjqypesq/yjqypesqcx'
		], function(tab, yjqypesq, yjqypesqcx){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));	
			$('#yjqypesq').click(function(){
				yjqypesq.showPage();
				comm.liActive($('#yjqypesq'));
			}.bind(this));
			
			$('#yjqypesqcx').click(function(){
				yjqypesqcx.showPage();
				comm.liActive($('#yjqypesqcx'));
			}.bind(this));
			
			//权限设置后默认选择规则
            var isModulesDefault = false;	//是否已有默认选择菜单
			var match = comm.findPermissionJsonByParentId("051702000000");
			
			//遍历资源数据一览表的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			 {
				 //一级区域配额申请
				 if(match[i].permId =='051702010000')
				 {
					 $('#yjqypesq').show();
					 $('#yjqypesq').click();
					 //已经设置默认值
					 isModulesDefault = true;
				 }
				 //一级区域配额申请查询
				 else if(match[i].permId =='051702020000')
				 {
					 $('#yjqypesqcx').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#yjqypesqcx').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				 
			 }
		}	
	}	
});