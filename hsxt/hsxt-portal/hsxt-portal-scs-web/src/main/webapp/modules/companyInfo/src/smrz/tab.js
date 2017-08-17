define(['text!companyInfoTpl/smrz/tab.html',
		'companyInfoSrc/smrz/smrz'],function(tab, smrz){
	return {	 
		showPage : function(){	 
			$('.operationsInner').html(_.template(tab)) ;
			//实名认证
			$('#smrz_smrzxx').click(function(e) {				 
                smrz.showPage();				 
            }.bind(this));
			
			 //权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("030506000000");
			
			//遍历实名认证查询的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			{
				 //实名认证
				 if(match[i].permId =='030506010000')
				 {
					 $('#smrz_smrzxx').show();
					 $('#smrz_smrzxx').click();
					 
				 }
			}
		} 
	}
}); 

