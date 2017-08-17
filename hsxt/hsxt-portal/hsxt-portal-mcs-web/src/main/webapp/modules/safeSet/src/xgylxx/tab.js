define(['text!safeSetTpl/xgylxx/tab.html',
		'safeSetSrc/xgylxx/xgylxx' ],function( tab,xgylxx ){
	return {	 
		showPage : function(){	
			 	
			$('.operationsInner').html(_.template(tab)) ;
			//修改预留信息
			$('#xtaq_xgylxx').click(function(e) {				 
                xgylxx.showPage();
				 
            }.bind(this));
			
			//获取缓存中的预留信息
			var reserveInfo = comm.getCookie("reserveInfo");
			//权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("040704000000");
			
			//遍历修改预留信息的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			 {
				 //修改预留信息
				 if(match[i].permId =='040704010000' &&  comm.isNotEmpty(reserveInfo))
				 {
					 $('#xtaq_xgylxx').show();
					 $('#xtaq_xgylxx').click();
					 
				 }
			 }
		} 
	}
}); 

