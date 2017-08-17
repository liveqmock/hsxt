define(['text!systemBusinessTpl/ddhsb/tab.html',
		'systemBusinessSrc/ddhsb/ddhsb',
		'systemBusinessLan'
		], function(tpl, ddhsb){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tpl));
			
			//代兑互生币
			$('#ddhsb').click(function(e){
				comm.liActive($(this));
				ddhsb.showPage();
			});
			
			//获取缓存中代兑互生币三级菜单
			var match = comm.findPermissionJsonByParentId("020208000000"); 

			 for(var i = 0 ; i< match.length ; i++)
			 {
				 //代兑互生币
				 if(match[i].permId =='020208010000')
				 {
					 $('#ddhsb').show();
					 $('#ddhsb').click();
				 }
			 }
		}
	}
});