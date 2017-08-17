define(['text!accountManageTpl/csssdjzh/tab.html',
		'accountManageSrc/csssdjzh/zhye',
		'accountManageSrc/csssdjzh/mxcx',
		'accountManageSrc/csssdjzh/sltzsq',
		'accountManageSrc/csssdjzh/sltzsqcx',],function(tabTpl,zhye,mxcx,sltzsq,sltzsqcx){
	return {
		//城市税收对接账户汇总初始化方法
		showPage : function(){			
			$('.operationsInner').html(_.template(tabTpl)) ;
			//账户余额
			$('#zhtz_ye').click(function(e) {				 
                zhye.showPage();
				this.liActive($('#zhtz_ye'));
            }.bind(this));
			 
			$('#zhtz_cx').click(function(e) {
                mxcx.showPage();
				this.liActive($('#zhtz_cx'));
            }.bind(this)); 
			 
			$('#zhtz_tz').click(function(e) {
                sltzsq.showPage();
				this.liActive($('#zhtz_tz'));
            }.bind(this)); 
			
			$('#zhtz_tzcx').click(function(e) {
                sltzsqcx.showPage();
				this.liActive($('#zhtz_tzcx'));
            }.bind(this)); 
			
			//权限设置后默认选择规则
            var isModulesDefault = false;	//是否已有默认选择菜单
			var match = comm.findPermissionJsonByParentId("030105000000");
			
			//遍历城市税收对接账户汇总的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			 {
				 //账户余额
				 if(match[i].permId =='030105010000')
				 {
					 $('#zhtz_ye').show();
					 $('#zhtz_ye').click();
					 //已经设置默认值
					 isModulesDefault = true;
				 }
				 //明细查询
				 else if(match[i].permId =='030105020000')
				 {
					 $('#zhtz_cx').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#zhtz_cx').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				 //税率调整申请
				 else if(match[i].permId =='030105030000')
				 {
					 $('#zhtz_tz').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#zhtz_tz').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				 //税率调整申请查询
				 else if(match[i].permId =='030105040000')
				 {
					 $('#zhtz_tzcx').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#zhtz_tzcx').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
			 }
		},
		liActive :function(liObj){		 
			liObj.parent('ul').find('a').removeClass('active');
			liObj.find('a').addClass('active');	
		}
	}
}); 

