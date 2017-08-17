define(['text!safeSetTpl/szmbwt/tab.html',
		'safeSetSrc/szmbwt/szmbwt' ],function( tab,szmbwt ){
	return {	 
		showPage : function(){	
			 	
			$('.operationsInner').html(_.template(tab)) ;
			//设置密保问题
			$('#xtaq_szmbwt').click(function(e) {				 
                szmbwt.showPage();
				 
            }.bind(this));

			 //权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("030705000000");
			
			//遍历设置密保问题的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			{
				 //设置密保问题
				 if(match[i].permId =='030705010000')
				 {
					 $('#xtaq_szmbwt').show();
					 $('#xtaq_szmbwt').click();
					 
				 }
			}
			 
		} 
	}
}); 

