define(['text!safeSetTpl/szylxx/tab.html',
		'safeSetSrc/szylxx/szylxx' ],function( tab,szylxx ){
	return {	 
		showPage : function(){	
			 	
			$('.operationsInner').html(_.template(tab)) ;
			//设置预留信息
			$('#xtaq_szylxx').click(function(e) {				 
                szylxx.showPage();
				 
            }.bind(this));
			 
			 //权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("030706000000");
			
			//遍历设置预留信息的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			{
				 //设置预留信息
				 if(match[i].permId =='030706010000')
				 {
					 $('#xtaq_szylxx').show();
					 $('#xtaq_szylxx').click();
					 
				 }
			}
		} 
	}
}); 

