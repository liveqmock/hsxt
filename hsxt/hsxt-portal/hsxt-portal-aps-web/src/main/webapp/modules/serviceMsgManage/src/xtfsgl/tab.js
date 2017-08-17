define(['text!serviceMsgManageTpl/xtfsgl/tab.html',
		'serviceMsgManageSrc/xtfsgl/sjdx',
		'serviceMsgManageSrc/xtfsgl/tsxx',
		'serviceMsgManageSrc/xtfsgl/yj' 
		], function(tab, sjdx, tsxx, yj,cxfs){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
 
			$('#serviceMsg_sjdx').click(function(){
				 
				sjdx.showPage();
				comm.liActive($('#serviceMsg_sjdx'));
			}.bind(this));
			
			$('#serviceMsg_tsxx').click(function(){
				tsxx.showPage();
				comm.liActive($('#serviceMsg_tsxx'));
			}.bind(this));
			
			$('#serviceMsg_yj').click(function(){
				yj.showPage();
				comm.liActive($('#serviceMsg_yj'));
			}.bind(this));	
			
			//权限设置后默认选择规则
            var isModulesDefault = false;	//是否已有默认选择菜单
			var match = comm.findPermissionJsonByParentId("051903000000");
			
			//遍历福利资格查询的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			 {
				 //手机短信
				 if(match[i].permId =='051903010000')
				 {
					 $('#serviceMsg_sjdx').show();
					 $('#serviceMsg_sjdx').click();
					 //已经设置默认值
					 isModulesDefault = true;
				 }
				 //推送消息
				 else if(match[i].permId =='051903020000')
				 {
					 $('#serviceMsg_tsxx').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#serviceMsg_tsxx').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				//邮件
				 else if(match[i].permId =='051903030000')
				 {
					 $('#serviceMsg_yj').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#serviceMsg_yj').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				 
			 }
			
		}	
	}	
});
