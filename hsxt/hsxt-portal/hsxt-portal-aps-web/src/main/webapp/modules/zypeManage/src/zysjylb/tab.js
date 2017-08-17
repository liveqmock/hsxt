define(['text!zypeManageTpl/zysjylb/tab.html',
		'zypeManageSrc/zysjylb/zypecx'
		], function(tab, zypecx){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));

			$('#zysjylb').click(function(){
				require(['zypeManageSrc/zysjylb/sub_tab'], function(subTab){
					subTab.showPage();	
				});
				comm.liActive($('#zysjylb'));
			}.bind(this));
			
			$('#zypecx').click(function(){
				zypecx.showPage();
				comm.liActive($('#zypecx'));
			}.bind(this));
			
			//权限设置后默认选择规则
            var isModulesDefault = false;	//是否已有默认选择菜单
			var match = comm.findPermissionJsonByParentId("051701000000");
			
			//遍历资源数据一览表的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			 {
				 //资源数据一览表
				 if(match[i].permId =='051701010000')
				 {
					 $('#zysjylb').show();
					 $('#zysjylb').click();
					 //已经设置默认值
					 isModulesDefault = true;
				 }
				 //资源配额查询
				 else if(match[i].permId =='051701020000')
				 {
					 $('#zypecx').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#zypecx').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				 
			 }
		}	
	}	
});