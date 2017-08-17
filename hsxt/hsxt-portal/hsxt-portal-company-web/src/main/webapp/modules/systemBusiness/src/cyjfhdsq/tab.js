define(['text!systemBusinessTpl/cyjfhdsq/tab.html',
		'systemBusinessSrc/cyjfhdsq/cyjfhdsq',
		'systemBusinessLan'
		], function(tpl, cyjfhdsq){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tpl));
			
			//参与积分活动
			$('#cyjfhdsq').click(function(e){
				comm.liActive($(this));
				cyjfhdsq.showPage();
			});
			
			//获取缓存中参与积分活动申请三级菜单
			var match = comm.findPermissionJsonByParentId("020206000000"); 

			 for(var i = 0 ; i< match.length ; i++)
			 {
				 //网上登记积分
				 if(match[i].permId =='020206010000')
				 {
					 $('#cyjfhdsq').show();
					 $('#cyjfhdsq').click();
				 }
			 }
		}
	}
});