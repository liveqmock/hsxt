define(['text!messageCenterTpl/fsxx/tab.html',
		'messageCenterSrc/fsxx/fsxx','messageCenterSrc/fsxx/xxcg'  ],function( tab,fsxx ,xxcg){
	return {	 
		showPage : function(){	
			 	
			$('.operationsInner').html(_.template(tab)) ;
			//发送消息
			
			$('#xxzx_fsxx').click(function(e) {				 
                fsxx.showPage();
                this.showLi($('#xxzx_fsxx'));			 
            }.bind(this)).click();
			
			//消息草稿
			$('#xxzx_xxcg').click(function(e) {				 
                xxcg.showPage();	
                this.showLi($('#xxzx_xxcg'));				 
            }.bind(this)) ;
			//权限设置后默认选择规则
            var isModulesDefault = false;	//是否已有默认选择菜单
			var match = comm.findPermissionJsonByParentId("031001000000");
			
			//遍历发送消息的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			 {
				 //发送消息
				 if(match[i].permId =='031001010000')
				 {
					 $('#xxzx_fsxx').show();
					 $('#xxzx_fsxx').click();
					 //已经设置默认值
					 isModulesDefault = true;
				 }
				 //消息草稿
				 else if(match[i].permId =='031001020000')
				 {
					 $('#xxzx_xxcg').show();
					 if(isModulesDefault == false )
					 {
						 //默认选中
						 $('#xxzx_xxcg').click();
						 //已经设置默认值
						 isModulesDefault = true;
					 }
				 }
			 }
			 
		} ,
		showLi : function(liObj){
			liObj.siblings().find('a').removeClass('active');
			liObj.find('a').addClass('active');
			liObj.siblings('li').not('#xxzx_fsxx, #xxzx_xxcg').css('display','none');
		}
	}
}); 

