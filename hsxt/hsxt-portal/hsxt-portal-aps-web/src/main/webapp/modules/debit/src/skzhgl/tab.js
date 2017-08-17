define(['text!debitTpl/skzhgl/tab.html',
		'debitSrc/skzhgl/skzhxxgl',
		'debitSrc/skzhgl/skzhmcgl',
		'debitLan'
		], function(tab, skzhxxgl, skzhmcgl){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			
			$('#skzhxxgl').click(function(){
				skzhxxgl.showPage();
				comm.liActive($('#skzhxxgl'), '#xgskzh, #tjskzh, #xgskzhmc, #tjskzhmc');
				$('#tjskzhDiv').addClass('none');
			}.bind(this));
			
			$('#skzhmcgl').click(function(){
				skzhmcgl.showPage();
				comm.liActive($('#skzhmcgl'), '#xgskzh, #tjskzh, #xgskzhmc, #tjskzhmc');
			}.bind(this));
			
			//权限设置后默认选择规则
            var isModulesDefault = false;	//是否已有默认选择菜单
			var match = comm.findPermissionJsonByParentId("050407000000");
			
			//遍历收款账户管理的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			 {
				 //收款账号信息管理
				 if(match[i].permId =='050407010000')
				 {
					 $('#skzhxxgl').show();
					 $('#skzhxxgl').click();
					 //已经设置默认值
					 isModulesDefault = true;
				 }
				 //收款账户管理
				 else if(match[i].permId =='050407020000')
				 {
					 $('#skzhmcgl').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#skzhmcgl').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
			 }
		}
	}	
});