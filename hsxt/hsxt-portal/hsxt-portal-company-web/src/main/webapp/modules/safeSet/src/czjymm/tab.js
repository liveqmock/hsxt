define(['text!safeSetTpl/czjymm/tab.html',
		'safeSetSrc/czjymm/czjymm'
		],function(tpl, czjymm){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tpl));
			//重置交易密码
			$('#xtaq_czjymm').click(function(e) {
                czjymm.showPage();
            });
			
			//获取重置交易密码三级菜单
 			var match = comm.findPermissionJsonByParentId("020504000000"); 

 			 for(var i = 0 ; i< match.length ; i++)
 			 {
 				 //重置交易密码
 				 if(match[i].permId =='020504010000')
 				 {
 					 $('#xtaq_czjymm').show();
 					 $('#xtaq_czjymm').click();
 				 }
 				 
 			 }
		} 
	}
});