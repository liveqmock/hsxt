define(['text!systemManageTpl/czygl/tab.html',
		'systemManageSrc/czygl/czygl',
		'systemManageSrc/czygl/xgczy',
		'systemManageSrc/czygl/xzczy' ,
		'systemManageLan'],function( tab,czygl,xgczy,xzczy ){
	return {	 
		showPage : function(){	
			 	
			$('.operationsInner').html(_.template(tab)) ;		
			
			$('#xtyhgl_czygl').click(function(e) {	
				this.showLi($('#xtyhgl_czygl'));			 
                czygl.showPage();				 
            }.bind(this));
			
			$('#xtyhgl_xgczy').click(function(e) {	
				this.showLi($('#xtyhgl_xgczy'));				 
//                xgczy.showPage();				 
            }.bind(this)) ; 
            
            $('#xtyhgl_xzczy').click(function(e) {	
				this.showLi($('#xtyhgl_xzczy'));				 
                xzczy.showPage();				 
            }.bind(this)) ; 
            
          //权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("052010000000");
			
			//遍历操作员管理的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			{
				 //操作员管理
				 if(match[i].permId =='052010100000')
				 {
					 $('#xtyhgl_czygl').show();
					 $('#xtyhgl_czygl').click();
					 
				 }
			}
			 
		} ,
		
		showLi : function(liObj){
			$('#xtyhgl_czygl > a').removeClass('active');
			liObj.css('display','').find('a').addClass('active');
			liObj.siblings('li').not('#xtyhgl_czygl').css('display','none');
		}
		
		
	}
}); 

