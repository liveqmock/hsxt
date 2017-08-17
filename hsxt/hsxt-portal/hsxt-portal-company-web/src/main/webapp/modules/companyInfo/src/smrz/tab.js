define(['text!companyInfoTpl/smrz/tab.html',
		'companyInfoSrc/smrz/smrz'
		], function(tpl, smrz){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tpl));
			
			//实名认证信息
			$('#smrzxx').click(function(e){
				comm.liActive($(this));
				smrz.showPage();
			});
			

			 //获取缓存实名认证信息三级菜单
			var match = comm.findPermissionJsonByParentId("020406000000"); 
	
			 for(var i = 0 ; i< match.length ; i++)
			 {
				 //实名认证信息
				 if(match[i].permId =='020406010000')
				 {
					 $('#smrzxx').show();
					 $('#smrzxx').click();
				 }
				
			 }
		}
	}
});