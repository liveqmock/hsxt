define(['text!systemBusinessTpl/cyqyzgwh/tab.html',
		'systemBusinessSrc/cyqyzgwh/cyqyzgwh',
		'systemBusinessLan'
		], function(tpl, cyqyzgwh){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tpl));
			
			//成员企业资格维护
			$('#cyqyzgwh').click(function(e){
				comm.liActive($(this), '#cyqyzx');
				cyqyzgwh.showPage();
			});
			
			//获取缓存中成员企业资格维护三级菜单
			var match = comm.findPermissionJsonByParentId("020204000000"); 

			 for(var i = 0 ; i< match.length ; i++)
			 {
				 //网上登记积分
				 if(match[i].permId =='020204010000')
				 {
					 $('#cyqyzgwh').show();
					 $('#cyqyzgwh').click();
				 }
			 }
			//成员企业注销
//			$("#cyqyzx").click(function(e){
//				comm.liActive($('#cyqyzgwh'), '#cyqyzx');
//				cyqyzgwh.showPage();
//			});
		}
	}
});