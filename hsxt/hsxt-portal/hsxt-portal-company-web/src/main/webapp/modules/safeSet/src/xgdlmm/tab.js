define(['text!safeSetTpl/xgdlmm/tab.html',
		'safeSetSrc/xgdlmm/xgdlmm'
		],function(tpl, xgdlmm){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tpl));
			
			//修改登录密码
			$('#xtaq_xgdlmm').click(function(e) {
                xgdlmm.showPage();
            });
			
			//获取缓修改登录密码三级菜单
 			var match = comm.findPermissionJsonByParentId("020501000000"); 

 			 for(var i = 0 ; i< match.length ; i++)
 			 {
 				 //修改登录密码
 				 if(match[i].permId =='020501010000')
 				 {
 					 $('#xtaq_xgdlmm').show();
 					 $('#xtaq_xgdlmm').click();
 				 }
 				 
 			 }
		} 
	}
});