define(['text!systemManageTpl/jsgl/tab.html',
		'systemManageSrc/jsgl/jsgl' ,
		'systemManageSrc/jsgl/xzxgjs' ,
		'systemManageLan'],function( tab,jsgl ,xzxgjs ){
	return {	 
		showPage : function(){	
			 	
			$('.operationsInner').html(_.template(tab)) ;
			//角色管理
			$('#xtyhgl_jsgl').click(function(e) {		
				this.showLi($('#xtyhgl_jsgl'));						 
                jsgl.showPage();				 
            }.bind(this));
			
			
			$('#xtyhgl_xzxgjs').click(function(e) {		
				this.showLi($('#xtyhgl_xzxgjs'));						 
                xzxgjs.showPage();				 
            }.bind(this));
			
			//权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("040803000000");
			
			//遍历角色管理的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			 {
				 //角色管理
				 if(match[i].permId =='040803010000')
				 {
					 $('#xtyhgl_jsgl').show();
					 $('#xtyhgl_jsgl').click();
				 }
			 } 
			 
		} ,		
		showLi : function(liObj){
			$('#xtyhgl_jsgl > a').removeClass('active');
			liObj.css('display','').find('a').addClass('active');
			liObj.siblings('li').not('#xtyhgl_jsgl').css('display','none');
		}
		
		
		
	}
}); 

