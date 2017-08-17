define(['text!businessServiceTpl/xtywsp/tab.html',
			'businessServiceSrc/xtywsp/cyqyzgzxsp',
			'businessServiceSrc/xtywsp/tzjfhdsp',
			'businessServiceSrc/xtywsp/cyjfhdsp',
			'businessServiceLan'
			],function(tab,cyqyzgzxsp, tzjfhdsp , cyjfhdsp  ){
	return {	 
		showPage : function(){			
		 
			$('.operationsInner').html(_.template(tab)) ;
			
			
			// 
			$('#xtywsp_cyqyzgzxsp').click(function(e) {				 		 
                cyqyzgzxsp.showPage();
				comm.liActive($('#xtywsp_cyqyzgzxsp'));
            }.bind(this));			 
			
			// 
			$('#xtywsp_tzjfhdsp').click(function(e) {
			 
                tzjfhdsp.showPage();
				comm.liActive($('#xtywsp_tzjfhdsp'));
            }.bind(this)); 
			// 
			$('#xtywsp_cyjfhdsp').click(function(e) {
		 
                cyjfhdsp.showPage();
				comm.liActive($('#xtywsp_cyjfhdsp'));
            }.bind(this));  
			
		 
			//权限设置后默认选择规则
            var isModulesDefault = false;	//是否已有默认选择菜单
			var match = comm.findPermissionJsonByParentId("030301000000");
			
			//遍历系统业务审批的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			 {
				 //成员企业资格注销审批
				 if(match[i].permId =='030301010000')
				 {
					 $('#xtywsp_cyqyzgzxsp').show();
					 $('#xtywsp_cyqyzgzxsp').click();
					 //已经设置默认值
					 isModulesDefault = true;
				 }
				 //停止积分活动审批
				 else if(match[i].permId =='030301020000')
				 {
					 $('#xtywsp_tzjfhdsp').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#xtywsp_tzjfhdsp').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				 //参与积分活动审批
				 else if(match[i].permId =='030301030000')
				 {
					 $('#xtywsp_cyjfhdsp').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#xtywsp_cyjfhdsp').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
			 }
		} 
	}
}); 
