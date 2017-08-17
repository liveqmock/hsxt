define(['text!accountManageTpl/jfzh/tab.html',
		'accountManageSrc/jfzh/zhye',
		'accountManageSrc/jfzh/jfzhsb',
		'accountManageSrc/jfzh/jftz',
		'accountManageSrc/jfzh/mxcx',
		'accountManageSrc/jfzh/xfjffp',
		'accountManageSrc/jfzh/jfhsfp',
		'accountManageSrc/jfzh/jfzzzfp',
		'accountManageSrc/jfzh/mxcx_xq',
		'accountManageLan'
		], function (tpl, zhye, jfzhsb, jftz, mxcx,xfjffp, jfhsfp, jfzzzfp, mxcx_xq) {
	return {
		showPage : function () {
			
			$('.operationsInner').html(tpl);
			//账户余额
			$('#jfzh_zhye').click(function (e) {
				comm.liActive($(this), '#jfzh_mxcxqx', '#busibox_xq');
				zhye.showPage();
			});

			//积分转互生币
			$('#jfzh_jfzhsb').click(function (e) {
				comm.liActive($(this), '#jfzh_mxcxqx', '#busibox_xq');
				jfzhsb.showPage();
			});

			//积分投资
			$('#jfzh_jftz').click(function (e) {
				comm.liActive($(this), '#jfzh_mxcxqx', '#busibox_xq');
				jftz.showPage();
			});

			//明细查询
			$('#jfzh_mxcx').click(function (e) {
				comm.liActive($(this), '#jfzh_mxcxqx', '#busibox_xq');
				mxcx.showPage();
			});
			
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
			
			 var isModulesDefault = false;
			 var match = comm.findPermissionJsonByParentId("020101000000"); 

			 for(var i = 0 ; i< match.length ; i++)
			 {
				 //账户余额
				 if(match[i].permId =='020101010000')
				 {
					 $('#jfzh_zhye').show();
					 $('#jfzh_zhye').click();
					 //已经设置默认值
					 isModulesDefault = true;
				 }
				 //积分转互生币
				 else if(match[i].permId =='020101020000')
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
				 else if(match[i].permId =='020101030000')
				 {
					 $('#jfzh_jftz').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#jfzh_jftz').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				 //明细查询
				 else if(match[i].permId =='020101040000')
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
				 else if(match[i].permId =='020101050000')
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
				 else if(match[i].permId =='020101060000')
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
				 else if(match[i].permId =='020101070000')
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
			 var entResType = comm.getCookie("entResType");
			 //成员企业没有积分投资
			 if(entResType == "2"){
				 $("#jfzh_jftz").hide();//隐藏 【积分投资】菜单
			 }
//			//详情 消费积分分配
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
		},
		liActive :function(liObj){		 
			$('#jfzh_xq_xhjffp').css('display','none');
			$('#jfzh_xq_jfhsfp').css('display','none');
			liObj.parent('ul').find('a').removeClass('active');
			liObj.find('a').addClass('active');	
		} 
	}
});