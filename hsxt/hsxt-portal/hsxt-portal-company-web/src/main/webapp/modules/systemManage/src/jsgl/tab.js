define(['text!systemManageTpl/jsgl/tab.html',
		'systemManageSrc/jsgl/jsgl',
		'systemManageSrc/jsgl/xzxgjs',
		'systemManageLan'
		 ],function(tpl, jsgl,xzxgjs){
	return {
		showPage : function(){
			
			$('.operationsInner').html(_.template(tpl));
			
			//角色管理
            
            $('#xtyhgl_jsgl').click(function(e) {	
            	jsgl.showPage() ;				 
				this.showLi($('#xtyhgl_jsgl')) ;			 
            }.bind(this));
			
			$('#xtyhgl_xzxgjs').click(function(e) {	
				xzxgjs.showPage();				 
				this.showLi($('#xtyhgl_xzxgjs'));				 
            }.bind(this)) ; 
			
			//获取缓存中角色管理三级菜单
 			var match = comm.findPermissionJsonByParentId("020602000000"); 

 			 for(var i = 0 ; i< match.length ; i++)
 			 {
 				 //角色管理
 				 if(match[i].permId =='020602010000')
 				 {
 					 $('#xtyhgl_jsgl').show();
 					 $('#xtyhgl_jsgl').click();
 				 }
 				 
 			 }
			
//            //角色管理
//            $('#xtyhgl_jsgl').click(function(e) {
//            	comm.liActive($(this), '#xtyhgl_xzxgjs');
//            	jsgl.showPage();
//            }).click();
//            
//            $('#xtyhgl_xzxgjs').click(function(e) {		
//            	comm.liActive($(this), '#xtyhgl_jsgl');
//            	xzxgjs.showPage();				 
//            }.bind(this));
		},
		showLi : function(liObj){
			$('#xtyhgl_czygl > a').removeClass('active');
			liObj.css('display','').find('a').addClass('active');
			liObj.siblings('li').not('#xtyhgl_jsgl').css('display','none');
		}
	}
});