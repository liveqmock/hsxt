define(['text!accountManageTpl/jfzh/tab.html',
			'accountManageSrc/jfzh/zhye',
			'accountManageSrc/jfzh/jfzhb',
			'accountManageSrc/jfzh/jfzhsb',
			'accountManageSrc/jfzh/jftz',
			'accountManageSrc/jfzh/mxcx',
			'accountManageSrc/jfzh/xfjffp',
			'accountManageSrc/jfzh/jfhsfp',
			'accountManageSrc/jfzh/jfzzzfp',
			'accountManageSrc/jfzh/mxcx_xq',
			],function(tab,zhye, jfzhb , jfzhsb ,jftz , mxcx,xfjffp, jfhsfp, jfzzzfp,mxcx_xq){
	return {	 
		showPage : function(){			
		 
			$('.operationsInner').html(_.template(tab)) ;
			//账户余额
			$('#jfzh_zhye').click(function(e) {				 		 
                zhye.showPage();
				this.liActive($('#jfzh_zhye'));
            }.bind(this));			 
			
			//积分转货币
			$('#jfzh_jfzhb').click(function(e) {
			 
                jfzhb.showPage();
				this.liActive($('#jfzh_jfzhb'));
            }.bind(this)); 
			//积分转互生币
			$('#jfzh_jfzhsb').click(function(e) {
		 
                jfzhsb.showPage();
				this.liActive($('#jfzh_jfzhsb'));
            }.bind(this));  
			
			//积分投资
			$('#jfzh_jfztz').click(function(e) {		 			
                jftz.showPage();
				this.liActive($('#jfzh_jfztz'));
            }.bind(this)); 
            
            //明细查询
            $('#jfzh_mxcx').click(function(e) {		 			
                mxcx.showPage();
				this.liActive($('#jfzh_mxcx'));
            }.bind(this)); 
          //消费积分分配
			$('#census_xfjffp').click(function(e) {				 		 
                xfjffp.showPage();
				this.liActive($('#census_xfjffp'));
            }.bind(this));		 
			
			//积分互生分配
			$('#census_jfhsfp').click(function(e) {			 
                jfhsfp.showPage();
				this.liActive($('#census_jfhsfp'));
            }.bind(this)); 
            
			//积分再增值分配
			$('#census_jfzzzfp').click(function(e) {			 
                jfzzzfp.showPage();
				this.liActive($('#census_jfzzzfp'));
            }.bind(this));             
			
//            //详情 消费积分分配
//            $('#jfzh_xq_xhjffp').click(function(e) {		 			
//                mxcx_xq.showPage('jfzh_xq_xhjffp');
//				this.liActive($('#jfzh_xq_xhjffp'));
//				$('#jfzh_xq_xhjffp').css('display','');
//            }.bind(this)); 
            
            //互生积分分配
            $('#jfzh_xq_jfhsfp').click(function(e) {		 			
                mxcx_xq.showPage('jfzh_xq_jfhsfp');
				this.liActive($('#jfzh_xq_jfhsfp'));
				$('#jfzh_xq_jfhsfp').css('display','');
            }.bind(this));
            
            //权限设置后默认选择规则
            var isModulesDefault = false;	//是否已有默认选择菜单
			var match = comm.findPermissionJsonByParentId("030101000000");
			
			//遍历积分账户的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			 {
				 //账户余额
				 if(match[i].permId =='030101010000')
				 {
					 $('#jfzh_zhye').show();
					 $('#jfzh_zhye').click();
					 //已经设置默认值
					 isModulesDefault = true;
				 }
				 //积分转互生币
				 else if(match[i].permId =='030101020000')
				 {
					 $('#jfzh_jfzhsb').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#jfzh_jfzhsb').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				 //积分投资
				 else if(match[i].permId =='030101030000')
				 {
					 $('#jfzh_jfztz').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#jfzh_jfztz').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				 //明细查询
				 else if(match[i].permId =='030101040000')
				 {
					 $('#jfzh_mxcx').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#jfzh_mxcx').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				//消费积分分配
				 else if(match[i].permId =='030101050000')
				 {
					
					 $('#census_xfjffp').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#census_xfjffp').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				//积分互生分配
				 else if(match[i].permId =='030101060000')
				 {
					 $('#census_jfhsfp').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#census_jfhsfp').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				//积分再增值分配
				 else if(match[i].permId =='030101070000')
				 {
					 $('#census_jfzzzfp').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#census_jfzzzfp').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
			 }
		},
		liActive :function(liObj){		 
			$('#jfzh_xq_xhjffp').css('display','none');
			$('#jfzh_xq_jfhsfp').css('display','none');
			liObj.parent('ul').find('a').removeClass('active');
			liObj.find('a').addClass('active');	
		} 
	}
}); 

