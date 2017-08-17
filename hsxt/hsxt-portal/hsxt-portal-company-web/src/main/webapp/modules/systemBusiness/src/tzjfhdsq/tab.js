define(['text!systemBusinessTpl/tzjfhdsq/tab.html',
		'systemBusinessSrc/tzjfhdsq/tzjfhdsq',
		'systemBusinessLan'
		], function(tpl, tzjfhdsq){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tpl));
			
			//停止积分活动
			$('#tzjfhdsq').click(function(e){
				comm.liActive($(this));
				tzjfhdsq.showPage();
			});
			
			//获取缓存中停止积分活动申请三级菜单
			var match = comm.findPermissionJsonByParentId("020205000000"); 

			 for(var i = 0 ; i< match.length ; i++)
			 {
				 //网上登记积分
				 if(match[i].permId =='020205010000')
				 {
					 $('#tzjfhdsq').show();
					 $('#tzjfhdsq').click();
				 }
			 }
		}
	}
});