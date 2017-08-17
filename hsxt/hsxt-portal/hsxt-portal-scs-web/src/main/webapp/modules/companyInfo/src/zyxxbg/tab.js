define(['text!companyInfoTpl/zyxxbg/tab.html',
		'companyInfoSrc/zyxxbg/zyxxbgsq',
		'companyInfoSrc/zyxxbg/zyxxbgcx',
		'companyInfoSrc/zyxxbg/ck'  ],function( tab,zyxxbgsq,zyxxbgcx,ck ){
	return {	 
		showPage : function(){	 
			$('.operationsInner').html(_.template(tab)) ;
			//重要信息变更申请
			$('#zyxxbg_bgsq').click(function(e) {				 
                zyxxbgsq.showPage();
                comm.liActive($('#zyxxbg_bgsq'));	
                $('#zyxxbg_ck').css('display','none');	
                $('#contentWidth_3_detail').hide();
                		 
            }.bind(this));
			
			$('#zyxxbg_jlcx').click(function(e) {				 
                zyxxbgcx.showPage();	
                comm.liActive($('#zyxxbg_jlcx'));	
                $('#zyxxbg_ck').css('display','none');	
                $('#contentWidth_3_detail').hide();
            }.bind(this)); 
            
            $('#zyxxbg_ck').click(function(e) {				 
                ck.showPage();	
                comm.liActive($('#zyxxbg_ck'));	
                $('#zyxxbg_ck').css('display','');	
            }.bind(this)); 
            
          //权限设置后默认选择规则
            var isModulesDefault = false;	//是否已有默认选择菜单
			var match = comm.findPermissionJsonByParentId("030507000000");
			
			//遍历重要信息变更的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			 {
				 //重要信息变更申请
				 if(match[i].permId =='030507010000')
				 {
					 $('#zyxxbg_bgsq').show();
					 $('#zyxxbg_bgsq').click();
					 //已经设置默认值
					 isModulesDefault = true;
				 }
				 //重要信息变更记录查询
				 else if(match[i].permId =='030507020000')
				 {
					 $('#zyxxbg_jlcx').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#zyxxbg_jlcx').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
			 }
		} 
	}
}); 

