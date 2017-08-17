define(['text!safeSetTpl/szmbwt/tab.html',
		'safeSetSrc/szmbwt/szmbwt'
		],function(tpl, szmbwt){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tpl));
			//设置密保问题
			$('#xtaq_szmbwt').click(function(e) {
                szmbwt.showPage();
            });
			
			//获取设置密保问题三级菜单
 			var match = comm.findPermissionJsonByParentId("020505000000"); 

 			 for(var i = 0 ; i< match.length ; i++)
 			 {
 				 //设置密保问题
 				 if(match[i].permId =='020505010000')
 				 {
 					 $('#xtaq_szmbwt').show();
 					 $('#xtaq_szmbwt').click();
 				 }
 				 
 			 }
		} 
	}
});