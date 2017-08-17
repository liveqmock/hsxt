define(['text!safeSetTpl/szjymm/tab.html',
		'safeSetSrc/szjymm/szjymm'
		],function(tpl, szjymm){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tpl));
			//设置交易密码
			$('#xtan_szjymm').click(function(e) {
                szjymm.showPage();
            });
			
			//获取设置交易密码三级菜单
 			var match = comm.findPermissionJsonByParentId("020502000000"); 

 			 for(var i = 0 ; i< match.length ; i++)
 			 {
 				 //设置交易密码
 				 if(match[i].permId =='020502010000')
 				 {
 					 $('#xtan_szjymm').show();
 					 $('#xtan_szjymm').click();
 				 }
 				 
 			 }
		} 
	}
});