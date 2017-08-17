define(['text!platformProxyTpl/dhhsb/tab.html',
		'platformProxySrc/dhhsb/dhhsb'
		], function(tab, dhhsb){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			//兑换互生币
			$('#dhhsb_dhhsb').click(function(){
				dhhsb.showPage();
				comm.liActive($('#dhhsb_dhhsb'));
			}.bind(this));
			
			
			//权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("051201000000");
			
			//遍历兑换互生币的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			{
				 //兑换互生币
				 if(match[i].permId =='051201010000')
				 {
					 $('#dhhsb_dhhsb').show();
					 $('#dhhsb_dhhsb').click();
					 
				 }
			}
		}
	}	
});