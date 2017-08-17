define(['text!platformProxyTpl/qynfjn/tab.html',
		'platformProxySrc/qynfjn/qynfjn'
		], function(tab, qynfjn){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			//企业年费缴纳
			$('#qynfjn_qynfjn').click(function(){
				qynfjn.showPage();
				comm.liActive($('#qynfjn_qynfjn'));
			}.bind(this));
			
			//权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("051201000000");
			
			//遍历企业年费缴纳的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			{
				 //企业年费缴纳
				 if(match[i].permId =='051201010000')
				 {
					 $('#qynfjn_qynfjn').show();
					 $('#qynfjn_qynfjn').click();
					 
				 }
			}
		}
	}	
});