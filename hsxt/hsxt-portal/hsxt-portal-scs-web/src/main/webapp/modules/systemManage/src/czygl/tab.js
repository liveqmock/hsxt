define(['text!systemManageTpl/czygl/tab.html',
		'systemManageSrc/czygl/czygl',
		'systemManageSrc/czygl/xgczy',
		'systemManageSrc/czygl/xzczy',  
		'systemManageSrc/czygl/hskbd',
		'systemManageSrc/czygl/hskjb',
		'systemManageLan'],function( tab,czygl,xgczy,xzczy ,hskbd,hskjb){
	return {	 
		showPage : function(){	
			 	
			$('.operationsInner').html(_.template(tab)) ;		
			//操作员管理
			$('#xtyhgl_czygl').click(function(e) {	
				this.showLi($('#xtyhgl_czygl')) ;			 
                czygl.showPage() ;				 
            }.bind(this)) ;
			
			$('#xtyhgl_xgczy').click(function(e) {	
				this.showLi($('#xtyhgl_xgczy'));				 
//                xgczy.showPage();				 
            }.bind(this)) ; 
            
            $('#xtyhgl_xzczy').click(function(e) {	
				this.showLi($('#xtyhgl_xzczy'));				 
                xzczy.showPage();				 
            }.bind(this)) ; 
            
             $('#xtyhgl_hskbd').click(function(e) {	
				this.showLi($('#xtyhgl_hskbd'));				 
//                hskbd.showPage();				 
            }.bind(this)) ; 
            
             $('#xtyhgl_hskjb').click(function(e) {	
				this.showLi($('#xtyhgl_hskjb'));				 
//                hskjb.showPage();				 
            }.bind(this)) ; 
            
           //权限设置后默认选择规则
 			var match = comm.findPermissionJsonByParentId("030801000000");
 			
 			//遍历操作员管理的子菜单设置默认选中
 			for(var i = 0 ; i< match.length ; i++)
 			{
 				 //操作员管理
 				 if(match[i].permId =='030801010000')
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

