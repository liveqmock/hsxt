define(['text!safeSetTpl/xgylxx/tab.html',
		'safeSetSrc/xgylxx/xgylxx'
		],function(tpl, xgylxx){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tpl));

			$('#xtaq_xgylxx').click(function(e) {
                xgylxx.showPage();
            });
			
			//获取设置密保问题三级菜单
 			var match = comm.findPermissionJsonByParentId("020507000000"); 

 			 for(var i = 0 ; i< match.length ; i++)
 			 {
 				 //设置密保问题
 				 if(match[i].permId =='020507010000')
 				 {
 					 $('#xtaq_xgylxx').show();
 					 $('#xtaq_xgylxx').click();
 				 }
 				 
 			 }
		} 
	}
});