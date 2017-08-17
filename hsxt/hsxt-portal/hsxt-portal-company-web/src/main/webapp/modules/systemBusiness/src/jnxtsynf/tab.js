define(['text!systemBusinessTpl/jnxtsynf/tab.html',
		'systemBusinessSrc/jnxtsynf/jnxtsynf',
		'systemBusinessLan'
		], function(tpl, jnxtsynf){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tpl));
			
			//缴纳系统使用年费
			$('#xtywbl_jnxtsynf').click(function(e){
				comm.liActive($(this));
				jnxtsynf.showPage();
			});
			
			//获取缓存中工具管理三级菜单
			var match = comm.findPermissionJsonByParentId("020202000000"); 

			 for(var i = 0 ; i< match.length ; i++)
			 {
				 //缴纳系统使用年费
				 if(match[i].permId =='020202020000')
				 {
					 $('#xtywbl_jnxtsynf').show();
					 $('#xtywbl_jnxtsynf').click();
				 }
				
			 }
		}
	}
});