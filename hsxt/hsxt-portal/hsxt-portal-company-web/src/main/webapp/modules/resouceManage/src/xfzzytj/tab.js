define(['text!resouceManageTpl/xfzzytj/tab.html',
		'resouceManageSrc/xfzzytj/xfzzytj'
		],function(tpl, xfzzytj){
	return {
		showPage : function(){
			$('.operationsInner').html(tpl);
			//消费者资源统计
			$('#xtzygl_xfzzytj').click(function(e) {
                xfzzytj.showPage();
            });
			//获取缓存消费者资源统计三级菜单
			var match = comm.findPermissionJsonByParentId("020301000000"); 

			 for(var i = 0 ; i< match.length ; i++)
			 {
				 //消费者资源统计
				 if(match[i].permId =='020301010000')
				 {
					 $('#xtzygl_xfzzytj').show();
					//默认选中
					 $('#xtzygl_xfzzytj').click();
				 }
				
			 }
		}
	}
});