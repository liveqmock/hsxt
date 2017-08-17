define(['text!safeSetTpl/szjymm/tab.html',
		'safeSetSrc/szjymm/szjymm' ],function( tab,szjymm ){
	return {	 
		showPage : function(){	
			 	
			$('.operationsInner').html(_.template(tab)) ;
			//设置交易密码
			$('#xtan_szjymm').click(function(e) {				 
                szjymm.showPage();
				 
            }.bind(this));
			 
			//权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("030702000000");
			
			//遍历修设置交易密码的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			{
				 //设置交易密码
				 if(match[i].permId =='030702010000')
				 {
					 $('#xtan_szjymm').show();
					 $('#xtan_szjymm').click();
					 
				 }
			}
		} 
	}
}); 

