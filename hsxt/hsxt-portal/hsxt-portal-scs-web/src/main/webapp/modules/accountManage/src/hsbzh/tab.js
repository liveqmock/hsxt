define(['text!accountManageTpl/hsbzh/tab.html',
		   'accountManageSrc/hsbzh/zhye',
			'accountManageSrc/hsbzh/dhhsb',
			'accountManageSrc/hsbzh/hsbzhb', 
			'accountManageSrc/hsbzh/mxcx',
			],function(tab , zhye ,dhhsb , hsbzhb , mxcx){
	return {	 
		showPage : function(){			
		 
			$('.operationsInner').html(_.template(tab)) ;
			//账户余额
			$('#hsbzh_zhye').click(function(e) {				 		 
                zhye.showPage();
				this.liActive($('#hsbzh_zhye'));
            }.bind(this));			 
			
			// 
			$('#hsbzh_dhhsb').click(function(e) {			 
                dhhsb.showPage();
				this.liActive($('#hsbzh_dhhsb'));
            }.bind(this)); 
            // 
			$('#hsbzh_hsbzhb').click(function(e) {			 
                hsbzhb.showPage();
				this.liActive($('#hsbzh_hsbzhb'));
            }.bind(this)); 
	 
            
            //明细查询
            $('#hsbzh_mxcx').click(function(e) {		 			
                mxcx.showPage();
				this.liActive($('#hsbzh_mxcx'));
            }.bind(this)); 
            
            //权限设置后默认选择规则
            var isModulesDefault = false;	//是否已有默认选择菜单
			var match = comm.findPermissionJsonByParentId("030102000000");
			
			//遍历积分账户的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			 {
				 //账户余额
				 if(match[i].permId =='030102010000')
				 {
					 $('#hsbzh_zhye').show();
					 $('#hsbzh_zhye').click();
					 //已经设置默认值
					 isModulesDefault = true;
				 }
				 //兑换互生币
				 else if(match[i].permId =='030102020000')
				 {
					 $('#hsbzh_dhhsb').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#hsbzh_dhhsb').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				 //互生币转货币
				 else if(match[i].permId =='030102030000')
				 {
					 $('#hsbzh_hsbzhb').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#hsbzh_hsbzhb').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				 //明细查询
				 else if(match[i].permId =='030102040000')
				 {
					 $('#hsbzh_mxcx').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#hsbzh_mxcx').click();
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

