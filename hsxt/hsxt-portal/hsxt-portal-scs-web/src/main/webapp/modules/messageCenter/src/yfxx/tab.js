define(['text!messageCenterTpl/yfxx/tab.html',
		'messageCenterSrc/yfxx/yfxx' ,
		'messageCenterSrc/yfxx/xxxq' ,
		'messageCenterLan' 
		],
		function( tab,yfxx,xxxq ){
	return {	 
		showPage : function(){	
			 	
			$('.operationsInner').html(_.template(tab)) ;
			//已发消息
			$('#xxzx_yfxx').click(function(e) {	
				this.showLi($('#xxzx_yfxx'));					 
                yfxx.showPage();				 
            }.bind(this));
			
			$('#xxzx_xxxq').click(function(e) {		
				this.showLi($('#xxzx_xxxq'));				 
                xxxq.showPage();				 
            }.bind(this)) ;

			//权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("031002000000");
			
			//遍历已发消息的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			{
				 //已发消息
				 if(match[i].permId =='031002010000')
				 {
					 $('#xxzx_yfxx').show();
					 $('#xxzx_yfxx').click();
					 
				 }
			}
		} ,
		showLi : function(liObj){
			$('#xxzx_yfxx > a').removeClass('active');
			liObj.css('display','').find('a').addClass('active');
			liObj.siblings('li').not('#xxzx_yfxx').css('display','none');
		}
		
		
		
	}
}); 

