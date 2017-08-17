define(['text!receivablesmanageTpl/dfkdd/tab.html',
		'receivablesmanageSrc/dfkdd/dfkdd'
		], function(tab, dfkdd){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			
			//待付款订单
			$('#dfkdd').click(function(){
				dfkdd.showPage();
				comm.liActive($('#dfkdd'),'#ckdd');
				$('#busibox').removeClass('none');
			}.bind(this));
			
			//权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("050202000000");
			
			//遍历待付款订单的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			{
				 //待付款订单
				 if(match[i].permId =='050202010000')
				 {
					 $('#dfkdd').show();
					 $('#dfkdd').click();
					 
				 }
			}
		}	
	}	
});