define(['text!accountManageTpl/jfzh/tab.html',
		'accountManageSrc/jfzh/jfzhye',
		/*'accountManageSrc/jfzh/jfzhb',*/
		'accountManageSrc/jfzh/xfjffp',
		'accountManageSrc/jfzh/mxcx',
		'accountManageSrc/jfzh/mxcx_xq','accountManageLan'],function(tab,jfzhye/*,jfzhb*/,xfjffp,mxcx,mxcx_xq){
	return {	 
		showPage : function(){			
			$('.operationsInner').html(_.template(tab)) ;
			//账户余额
			$('#jfzh_ye').click(function(e) {
				$('#jfzh_xq').css('display','none');					 
                jfzhye.showPage();
				this.liActive($('#jfzh_ye'));
				$('#busibox').removeClass('none');
				$('#jfzh_detail').addClass('none');
            }.bind(this));
			 
			
			//积分转货币
			/*$('#jfzh_zhb').click(function(e) {
				$('#jfzh_xq').css('display','none');	
                jfzhb.showPage();
				this.liActive($('#jfzh_zhb'));
            }.bind(this)); */
			//明细查询
			$('#jfzh_cx').click(function(e) {
				$('#jfzh_xq').css('display','none');	
                mxcx.showPage();
				this.liActive($('#jfzh_cx'));
				$('#busibox').removeClass('none');
				$('#jfzh_detail').addClass('none');
				
            }.bind(this));  
			
			//消费积分分配
			$('#census_xfjffp').click(function(e) {				 		 
                xfjffp.showPage();
				this.liActive($('#census_xfjffp'));
            }.bind(this));	 
			
			//权限设置后默认选择规则
            var isModulesDefault = false;	//是否已有默认选择菜单
			var match = comm.findPermissionJsonByParentId("040101000000");
			
			//遍历积分账户的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			 {
				 //账户余额
				 if(match[i].permId =='040101010000')
				 {
					 $('#jfzh_ye').show();
					 $('#jfzh_ye').click();
					 //已经设置默认值
					 isModulesDefault = true;
				 }
				 //明细查询
				 else if(match[i].permId =='040101020000')
				 {
					 $('#jfzh_cx').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#jfzh_cx').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				//消费积分分配
				 else if(match[i].permId =='040101030000')
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
			 }
			
		},
		liActive :function(liObj){		 
			liObj.parent('ul').find('a').removeClass('active');
			liObj.find('a').addClass('active');	
		}
	}
}); 

