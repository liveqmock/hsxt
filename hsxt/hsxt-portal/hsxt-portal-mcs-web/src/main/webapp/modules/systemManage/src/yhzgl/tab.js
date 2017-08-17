define(['text!systemManageTpl/yhzgl/tab.html',
		'systemManageSrc/yhzgl/yhzgl' ,
		'systemManageSrc/yhzgl/xgyhz' ,
		'systemManageSrc/yhzgl/tjwhyh',
		'systemManageLan'],function( tab,yhzgl,xgyhz,tjwhyh ){
	return {	 
		showPage : function(){	
			 	
			$('.operationsInner').html(_.template(tab)) ;
			 //用户组管理
			$('#xtyhgl_yhzgl').click(function(e) {	
				this.showLi($('#xtyhgl_yhzgl'));					 
                yhzgl.showPage();				 
            }.bind(this));
			
			$('#xtyhgl_xgyhz').click(function(e) {	
				this.showLi($('#xtyhgl_xgyhz'));					 
                xgyhz.showPage();				 
            }.bind(this));
            
            $('#xtyhgl_tjwhzy').click(function(e) {	
				this.showLi($('#xtyhgl_tjwhzy'));					 
//                tjwhyh.showPage();				 
            }.bind(this));
            
            //权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("040802000000");
			
			//遍历用户组管理的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			 {
				 //用户组管理
				 if(match[i].permId =='040802010000')
				 {
					 $('#xtyhgl_yhzgl').show();
					 $('#xtyhgl_yhzgl').click();
				 }
			 }
			 
		} ,
		showLi : function(liObj){
			$('#xtyhgl_yhzgl > a').removeClass('active');
			liObj.css('display','').find('a').addClass('active');
			liObj.siblings('li').not('#xtyhgl_yhzgl').css('display','none');
		}
		
		
		
	}
}); 

