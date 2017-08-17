define(['text!businessHandlingTpl/jnsh/tab.html',
		   'businessHandlingSrc/jnsh/jnsh',
		   'businessHandlingLan'
			],function( tab , jnsh ){
	return {	 
		showPage : function(){			
		 
			$('.operationsInner').html(_.template(tab)) ;
			//缴纳流水
			$('#jnsh_jnsh').click(function(e) {				 		 
                jnsh.showPage();
				//this.liActive($('#jnsh_jnsh'));
            }.bind(this));			 
			 
			//权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("030602000000");
			
			//遍历缴纳流水的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			{
				 //缴纳流水
				 if(match[i].permId =='030602010000')
				 {
					 $('#jnsh_jnsh').show();
					 $('#jnsh_jnsh').click();
					 
				 }
			}
			
		},
		liActive :function(liObj){		 
			liObj.parent('ul').find('a').removeClass('active');
			liObj.find('a').addClass('active');	
		}
	}
}); 