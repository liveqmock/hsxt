define(['text!safeSetTpl/xgylxx/tab.html',
		'safeSetSrc/xgylxx/xgylxx' ],function( tab,xgylxx ){
	return {	 
		showPage : function(){	
			 	
			$('.operationsInner').html(_.template(tab)) ;
			//修改预留信息
			$('#xtaq_xgylxx').click(function(e) {				 
                xgylxx.showPage();
				 
            }.bind(this));
			
			 //权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("030707000000");
			
			//遍历修改预留信息的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			{
				 //修改预留信息
				 if(match[i].permId =='030707010000')
				 {
					 $('#xtaq_xgylxx').show();
					 $('#xtaq_xgylxx').click();
					 
				 }
			}
			 
			 
		} 
	}
}); 

