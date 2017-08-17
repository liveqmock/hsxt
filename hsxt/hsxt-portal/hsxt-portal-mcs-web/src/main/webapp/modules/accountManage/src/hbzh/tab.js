define(['text!accountManageTpl/hbzh/tab.html',
		'accountManageSrc/hbzh/hbzhye',
		/*'accountManageSrc/hbzh/hbzyh',*/
		'accountManageSrc/hbzh/hbmx'],function(tab,hbzhye/*,hbzyh*/,hbmx){
	return {	 
		showPage : function(){			
			$('.operationsInner').html(_.template(tab)) ;
			//账户余额
			$('#hbzh_ye').click(function(e) {				 
                hbzhye.showPage();
				this.liActive($('#hbzh_ye'));
            }.bind(this)); 
			//积分转货币
			/*$('#hbzh_zyh').click(function(e) {			
                hbzyh.showPage();
				this.liActive($('#hbzh_zyh'));
            }.bind(this));*/ 
			//明细查询
			$('#hbzh_mx').click(function(e) {				
                hbmx.showPage();
				this.liActive($('#hbzh_mx'));
            }.bind(this));
			
			//权限设置后默认选择规则
            var isModulesDefault = false;	//是否已有默认选择菜单
			var match = comm.findPermissionJsonByParentId("040102000000");
			
			//遍历货币账户的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			 {
				 //账户余额
				 if(match[i].permId =='040102010000')
				 {
					 $('#hbzh_ye').show();
					 $('#hbzh_ye').click();
					 //已经设置默认值
					 isModulesDefault = true;
				 }
				 //明细查询
				 else if(match[i].permId =='040102020000')
				 {
					 $('#hbzh_mx').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#hbzh_mx').click();
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

