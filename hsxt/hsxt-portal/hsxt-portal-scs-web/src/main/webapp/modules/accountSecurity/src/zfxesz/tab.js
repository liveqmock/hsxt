define(['text!accountSecurityTpl/zfxesz/tab.html',
		'accountSecuritySrc/zfxesz/zfxesz'
		],function(tab,zfxesz){
	return {	 
		showPage : function(){	
			 	
			$('.operationsInner').html(_.template(tab)) ;		
			//支付限额设置
			$('#zfxesz').click(function(e) {	
				this.showLi($('#zfxesz')) ;			 
                zfxesz.showPage() ;				 
            }.bind(this)) ;
			
			//权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("031101000000");
			
			//遍历支付限额设置的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			{
				 //支付限额设置
				 if(match[i].permId =='031101010000')
				 {
					 $('#zfxesz').show();
					 $('#zfxesz').click();
					 
				 }
			}
		} ,
		
		showLi : function(liObj){
			$('#zfxesz > a').removeClass('active');
			liObj.find('a').addClass('active');
		}
		
		
	}
}); 

