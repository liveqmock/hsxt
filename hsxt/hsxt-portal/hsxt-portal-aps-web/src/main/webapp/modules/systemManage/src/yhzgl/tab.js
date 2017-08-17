define(['text!systemManageTpl/yhzgl/tab.html',
		'systemManageSrc/yhzgl/yhzgl' ,
		'systemManageSrc/yhzgl/xgyhz' ,
		'systemManageSrc/yhzgl/tjwhyh',
		'systemManageLan'],function( tab,yhzgl,xgyhz,tjwhyh ){
	return {	 
		showPage : function(){	
			 	
			$('.operationsInner').html(_.template(tab)) ;
			 
			$('#xtyhgl_yhzgl').click(function(e) {	
				this.showLi($('#xtyhgl_yhzgl'));					 
                yhzgl.showPage();				 
            }.bind(this));
			
			$('#xzyhz').click(function(e) {	
				this.showLi($('#xzyhz'));					 
				xgyhz.showPage();				 
			}.bind(this));
			
			$('#xgyhz').click(function(e) {	
				this.showLi($('#xgyhz'));					 
				xgyhz.showPage();				 
			}.bind(this));
			
            $('#zywh').click(function(e) {	
				this.showLi($('#zywh'));					 
//                tjwhyh.showPage();				 
            }.bind(this));
            
            $('#tjyhzcy').click(function(e) {	
            	this.showLi($('#tjyhzcy'));					 
//                tjwhyh.showPage();				 
            }.bind(this));
			
          //权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("052030000000");
			
			//遍历用户组管理的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			{
				 //用户组管理
				 if(match[i].permId =='052030100000')
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

