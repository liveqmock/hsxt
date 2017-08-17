define(['text!systemManageTpl/czygl/tab.html',
		'systemManageSrc/czygl/czygl',
		'systemManageSrc/czygl/xgczy',
		'systemManageSrc/czygl/xzczy', 
		'systemManageLan'
		],function(tpl, czygl,xgczy,xzczy){
	return {	 
		showPage : function(){
//			$('.operationsInner').html(_.template(tpl));
//			
//			$('#xtyhgl_czygl').click(function(e) {
//				comm.liActive($(this), '#xtyhgl_xzczy, #xtyhgl_xgczy, #xtyhgl_hskjb, #xtyhgl_hskbd', '#busibox2');
//                czygl.showPage();
//            }).click();
			
			$('.operationsInner').html(_.template(tpl)) ;		
			
			$('#xtyhgl_czygl').click(function(e) {	
				czygl.showPage() ;				 
				this.showLi($('#xtyhgl_czygl')) ;			 
            }.bind(this));
			
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
            
           //获取缓存中操作员管理三级菜单
 			var match = comm.findPermissionJsonByParentId("020601000000"); 

 			 for(var i = 0 ; i< match.length ; i++)
 			 {
 				 //操作员管理
 				 if(match[i].permId =='020601010000')
 				 {
 					 $('#xtyhgl_czygl').show();
 					 $('#xtyhgl_czygl').click();
 				 }
 				 
 			 }
            
		},
		
		showLi : function(liObj){
			$('#xtyhgl_czygl > a').removeClass('active');
			liObj.css('display','').find('a').addClass('active');
			liObj.siblings('li').not('#xtyhgl_czygl').css('display','none');
		}
	}
});