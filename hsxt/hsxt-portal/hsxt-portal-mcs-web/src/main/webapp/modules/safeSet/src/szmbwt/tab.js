define(['text!safeSetTpl/szmbwt/tab.html',
		'safeSetSrc/szmbwt/szmbwt' ],function( tab,szmbwt ){
	return {	 
		showPage : function(){	
			 	
			$('.operationsInner').html(_.template(tab)) ;
			//设置密保问题
			$('#xtaq_szmbwt').click(function(e) {				 
                szmbwt.showPage();
				 
            }.bind(this));
			
			//获取缓存中操作员账号
			var custName = comm.getCookie("custName");
			
			//权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("040702000000");
			
			//遍历设置密保问题的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			 {
				 //设置密保问题 并且超级管理员才有修改的权限
				 if(match[i].permId =='040702010000' && custName == "0000")
				 {
					 $('#xtaq_szmbwt').show();
					 $('#xtaq_szmbwt').click();
					 
				 }
			 }
		} 
	}
}); 

