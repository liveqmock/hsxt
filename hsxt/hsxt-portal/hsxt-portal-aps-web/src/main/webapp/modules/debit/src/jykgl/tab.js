define(['text!debitTpl/jykgl/tab.html',
		'debitSrc/jykgl/ykzbdk',
		'debitSrc/jykgl/bdkjlcx'
		], function(tab, ykzbdk, bdkjlcx){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			//余款转不动款
			$('#ykzbdk').click(function(){
				ykzbdk.showPage();
				comm.liActive($('#ykzbdk'), '#ck');
			}.bind(this));
			
			//不动款记录查询
			$('#bdkjlcx').click(function(){
				bdkjlcx.showPage();
				comm.liActive($('#bdkjlcx'), '#ck');
			}.bind(this));
			
			//权限设置后默认选择规则
            var isModulesDefault = false;	//是否已有默认选择菜单
			var match = comm.findPermissionJsonByParentId("050409000000");
			
			//遍历节余款管理的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			 {
				 //余款转不动款
				 if(match[i].permId =='050409010000')
				 {
					 $('#ykzbdk').show();
					 $('#ykzbdk').click();
					 //已经设置默认值
					 isModulesDefault = true;
				 }
				 //不动款记录查询
				 else if(match[i].permId =='050409020000')
				 {
					 $('#bdkjlcx').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#bdkjlcx').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
			 }
		}	
	}	
});