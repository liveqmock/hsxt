define(['text!accountManageTpl/tzzh/tab.html',
		   'accountManageSrc/tzzh/zhye',
			'accountManageSrc/tzzh/jftzmxcx',
			'accountManageSrc/tzzh/tzfhmxcx' ,
			'accountManageSrc/tzzh/tzfhmxcx_xq',
			"accountManageLan"
			],function(tab , zhye , jftzmxcx , tzfhmxcx ,tzfhmxcx_xq){
	return {
		//投资账户初始化方法
		showPage : function(){			
		 
			$('.operationsInner').html(_.template(tab)) ;
			//账户余额
			$('#tzzh_zhye').click(function(e) {				 		 
                zhye.showPage();
				this.liActive($('#tzzh_zhye'));
            }.bind(this));			 
			
			// 
			$('#tzzh_jftzmxcx').click(function(e) {			 
                jftzmxcx.showPage();
				this.liActive($('#tzzh_jftzmxcx'));
            }.bind(this)); 
            // 
			$('#tzzh_tzfhmxcx').click(function(e) {			 
                tzfhmxcx.showPage();
				this.liActive($('#tzzh_tzfhmxcx'));
            }.bind(this)); 
	 		
	 	 
    		$('#tzzh_tzfhxq').click(function(e) {		
    			
//                tzfhmxcx_xq.showPage();
				this.liActive($('#tzzh_tzfhxq'));
				$('#tzzh_tzfhxq').css('display','');	 
            }.bind(this)); 
            
    		//权限设置后默认选择规则
            var isModulesDefault = false;	//是否已有默认选择菜单
			var match = comm.findPermissionJsonByParentId("030104000000");
			
			//遍历投资账户的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			 {
				 //积分投资总数
				 if(match[i].permId =='030104010000')
				 {
					 $('#tzzh_zhye').show();
					 $('#tzzh_zhye').click();
					 //已经设置默认值
					 isModulesDefault = true;
				 }
				 //积分投资明细查询
				 else if(match[i].permId =='030104020000')
				 {
					 $('#tzzh_jftzmxcx').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#tzzh_jftzmxcx').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				 //投资分红明细查询
				 else if(match[i].permId =='030104030000')
				 {
					 $('#tzzh_tzfhmxcx').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#tzzh_tzfhmxcx').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
			 }
		},
		liActive :function(liObj){		
			$('#tzzh_tzfhxq').css('display','none');	  
			liObj.parent('ul').find('a').removeClass('active');
			liObj.find('a').addClass('active');	
		}
	}
}); 