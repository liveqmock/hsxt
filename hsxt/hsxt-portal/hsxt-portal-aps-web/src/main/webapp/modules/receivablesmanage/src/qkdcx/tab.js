define(['text!receivablesmanageTpl/qkdcx/tab.html',
		'receivablesmanageSrc/qkdcx/xtsynf',
		'receivablesmanageSrc/qkdcx/xtzyf'
		], function(tab, xtsynf, xtzyf){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			//系统使用年费
			$('#xtsynf').click(function(){
				xtsynf.showPage();
				comm.liActive($('#xtsynf'),'#ckdd,#ckdd2');
				$('#busibox').removeClass('none');
				$('#xtsynf_ckTpl').addClass('none');
				$('#xtzyf_ckTpl').addClass('none');
			}.bind(this));
			//系统销售费
			$('#xtzyf').click(function(){
				xtzyf.showPage();
				comm.liActive($('#xtzyf'),'#ckdd,#ckdd2');
				$('#busibox').removeClass('none');
				$('#xtsynf_ckTpl').addClass('none');
				$('#xtzyf_ckTpl').addClass('none');
			}.bind(this));
			
			//权限设置后默认选择规则
            var isModulesDefault = false;	//是否已有默认选择菜单
			var match = comm.findPermissionJsonByParentId("050205000000");
			
			//遍历欠款单查询的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			 {
				 //系统使用年费
				 if(match[i].permId =='050205010000')
				 {
					 $('#xtsynf').show();
					 $('#xtsynf').click();
					 //已经设置默认值
					 isModulesDefault = true;
				 }
				 //系统销售费
				 else if(match[i].permId =='050205020000')
				 {
					 $('#xtzyf').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#xtzyf').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
			 }
		}	
	}	
});