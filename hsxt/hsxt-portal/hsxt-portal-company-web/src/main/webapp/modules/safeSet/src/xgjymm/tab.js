define(['text!safeSetTpl/xgjymm/tab.html',
		'safeSetSrc/xgjymm/xgjymm'
		],function(tpl, xgjymm){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tpl));

			$('#xtaq_xgjymm').click(function(e) {
                xgjymm.showPage();
            });
			
			//获取修改交易密码三级菜单
 			var match = comm.findPermissionJsonByParentId("020503000000"); 

 			 for(var i = 0 ; i< match.length ; i++)
 			 {
 				 //修改交易密码
 				 if(match[i].permId =='020503010000')
 				 {
 					 $('#xtaq_xgjymm').show();
 					 $('#xtaq_xgjymm').click();
 				 }
 				 
 			 }
		} 
	}
});