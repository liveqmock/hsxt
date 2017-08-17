define(['text!companyInfoTpl/zyxxbg/tab.html',
		'companyInfoSrc/zyxxbg/zyxxbgsq',
		'companyInfoSrc/zyxxbg/zyxxbgcx',
		'companyInfoSrc/zyxxbg/ck'  ],function( tab,zyxxbgsq,zyxxbgcx,ck ){
	return {	 
		showPage : function(){	 
			$('.operationsInner').html(_.template(tab)) ;
			 
			$('#zyxxbg_bgsq').click(function(e) {				 
                zyxxbgsq.showPage();
                comm.liActive($('#zyxxbg_bgsq'));	
                $('#zyxxbg_ck').css('display','none');	
                $('#contentWidth_3_detail').hide();
                $('#contentWidth_3_ck').css('display','none');
            }.bind(this));
			
			$('#zyxxbg_jlcx').click(function(e) {				 
                zyxxbgcx.showPage();	
                comm.liActive($('#zyxxbg_jlcx'));	
                $('#zyxxbg_ck').css('display','none');	
                $('#contentWidth_3_ck').css('display','none');
            }.bind(this)); 
            
            $('#zyxxbg_ck').click(function(e) {				 
                ck.showPage();	
                comm.liActive($('#zyxxbg_ck'));	
                $('#zyxxbg_ck').css('display','');	
            }.bind(this)); 
            
            //获取缓存重要信息变更三级菜单
            var isModulesDefault = false;
			var match = comm.findPermissionJsonByParentId("020407000000"); 
	
			 for(var i = 0 ; i< match.length ; i++)
			 {
				 //重要信息变更申请
				 if(match[i].permId =='020407010000')
				 {
					 $('#zyxxbg_bgsq').show();
					//默认选中
					 $('#zyxxbg_bgsq').click();
					 //已经设置默认值
					 isModulesDefault = true;
				 }
				 //重要信息变更记录查询
				 else if(match[i].permId =='020407020000')
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

