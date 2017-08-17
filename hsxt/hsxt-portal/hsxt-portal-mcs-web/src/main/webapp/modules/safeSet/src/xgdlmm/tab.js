define(['text!safeSetTpl/xgdlmm/tab.html',
		'safeSetSrc/xgdlmm/xgdlmm' ],function( tab,xgdlmm ){
	return {	 
		showPage : function(){	
		 
			$('.operationsInner').html(_.template(tab)) ;
			//修改登录密码	 
			$('#xtaq_xgdlmm').click(function(e) {				 
                xgdlmm.showPage();				 
            }.bind(this));
			 
			//权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("040701000000");
			
			//遍历修改登录密码的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			 {
				 //修改登录密码
				 if(match[i].permId =='040701010000')
				 {
					 $('#xtaq_xgdlmm').show();
					 $('#xtaq_xgdlmm').click();
					 
				 }
			 }
		} 
	}
}); 

