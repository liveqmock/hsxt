define(['text!safeSetTpl/xgjymm/tab.html',
		'safeSetSrc/xgjymm/xgjymm' ],function( tab,xgjymm ){
	return {	 
		showPage : function(){	
			 	
			$('.operationsInner').html(_.template(tab)) ;
			//修改交易密码
			$('#xtaq_xgjymm').click(function(e) {				 
                xgjymm.showPage();
				 
            }.bind(this));
			 
			//权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("030703000000");
			
			//遍历修改交易密码的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			{
				 //修改交易密码
				 if(match[i].permId =='030703010000')
				 {
					 $('#xtaq_xgjymm').show();
					 $('#xtaq_xgjymm').click();
					 
				 }
			}
		} 
	}
}); 

