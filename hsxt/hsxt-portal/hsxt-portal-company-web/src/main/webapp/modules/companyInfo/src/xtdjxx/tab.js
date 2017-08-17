define(['text!companyInfoTpl/xtdjxx/tab.html',
		'companyInfoSrc/xtdjxx/xtdjxx',
		'companyInfoLan'
		], function(tpl, xtdjxx){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tpl));
			
			/*系统登记信息*/	
			$('#xtdjxx').click(function(e){
				xtdjxx.showPage();
				comm.liActive($(this));
			}.bind(this));
			
			//获取缓存系统登记信息三级菜单
			var match = comm.findPermissionJsonByParentId("020401000000"); 

			 for(var i = 0 ; i< match.length ; i++)
			 {
				 //系统登记信息
				 if(match[i].permId =='020401010000')
				 {
					 $('#xtdjxx').show();
					 $('#xtdjxx').click();
				 }
				
			 }
		}
	}
});