define(['text!companyInfoTpl/xtdjxx/tab.html',
		'companyInfoSrc/xtdjxx/xtdjxx' ],function( tab,xtdjxx ){
	return {	 
		showPage : function(){	
			 	
			$('.operationsInner').html(_.template(tab)) ;
			//系统登录信息
			$('#qyxx_xtdjxx').click(function(e) {				 
                xtdjxx.showPage();				 
            }.bind(this));
			
			
			//权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("040601000000");
			
			//遍历系统登记信息的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			 {
				 //系统登记信息
				 if(match[i].permId =='040601010000')
				 {
					 $('#qyxx_xtdjxx').show();
					 $('#qyxx_xtdjxx').click();
					 
				 }
			 }
			 
			 
		} 
	}
}); 

