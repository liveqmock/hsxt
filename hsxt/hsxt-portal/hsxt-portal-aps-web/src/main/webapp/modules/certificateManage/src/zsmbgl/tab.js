define(['text!certificateManageTpl/zsmbgl/tab.html',
		'certificateManageSrc/zsmbgl/zsmbwh',
		'certificateManageSrc/zsmbgl/zsmbfh'
		], function(tab, zsmbwh, zsmbfh){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			//证书模版维护
			$('#zsmbwh').click(function(){
				zsmbwh.showPage();
				comm.liActive($('#zsmbwh'), '#xgmb, #fh, #xzmb');
				$('#fhtjDiv').addClass('none');
			}.bind(this));
			//证书模版复核
			$('#zsmbfh').click(function(){
				zsmbfh.showPage();
				comm.liActive($('#zsmbfh'), '#xgmb, #fh, #xzmb');
				$('#fhtjDiv').addClass('none');
			}.bind(this));
			//权限设置后默认选择规则
            var isModulesDefault = false;	//是否已有默认选择菜单
			var match = comm.findPermissionJsonByParentId("050903000000");
			
			//遍历第三方业务证书管理的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			 {
				 //证书模版维护
				 if(match[i].permId =='050903010000')
				 {
					 $('#zsmbwh').show();
					 $('#zsmbwh').click();
					 //已经设置默认值
					 isModulesDefault = true;
				 }
				 //证书模版复核
				 else if(match[i].permId =='050903020000')
				 {
					 $('#zsmbfh').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#zsmbfh').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				 
			 }
		}	
	}	
});