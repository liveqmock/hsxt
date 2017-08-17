define(['text!messageCenterTpl/fsxx/tab.html',
		'messageCenterSrc/fsxx/fsxx',
		'messageCenterSrc/fsxx/xxcg'
		], function(tab, fsxx, xxcg){
	return {
		showPage : function(){
			$('.operationsInner').html(_.template(tab));
			
			$('#fsxx').click(function(){
				fsxx.showPage();
				comm.liActive($('#fsxx'), '#xg, #yl');
			}.bind(this));	
			
			$('#xxcg').click(function(){
				xxcg.showPage();
				comm.liActive($('#xxcg'), '#xg, #yl');
			}.bind(this));
			
			//权限设置后默认选择规则
            var isModulesDefault = false;	//是否已有默认选择菜单
			var match = comm.findPermissionJsonByParentId("052201000000");
			
			//遍历发送消息的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			 {
				 //发送消息
				 if(match[i].permId =='052201010000')
				 {
					 $('#fsxx').show();
					 $('#fsxx').click();
					 //已经设置默认值
					 isModulesDefault = true;
				 }
				 //消息草稿
				 else if(match[i].permId =='052201020000')
				 {
					 $('#xxcg').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#xxcg').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
				 
			 }
		}	
	}	
});