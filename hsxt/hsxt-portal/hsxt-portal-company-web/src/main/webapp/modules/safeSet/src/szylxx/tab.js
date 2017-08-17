define(['text!safeSetTpl/szylxx/tab.html',
		'safeSetSrc/szylxx/szylxx'
		],function(tpl, szylxx){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tpl));

			$('#xtaq_szylxx').click(function(e) {
                szylxx.showPage();
            });
			
			//获取设置预留信息三级菜单
 			var match = comm.findPermissionJsonByParentId("020506000000"); 

 			 for(var i = 0 ; i< match.length ; i++)
 			 {
 				 //设置预留信息
 				 if(match[i].permId =='020506010000')
 				 {
 					 $('#xtaq_szylxx').show();
 					 $('#xtaq_szylxx').click();
 				 }
 				 
 			 }
		} 
	}
});