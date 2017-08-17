define(['text!businessHandlingTpl/nhjn/tab.html',
		   'businessHandlingSrc/nhjn/nhjn' ,
		   'businessHandlingSrc/nhjn/hsbzf' ,
		   'businessHandlingSrc/nhjn/wyzf' ,
		   'text!businessHandlingTpl/nhjn/dialog_info.html',
		   'businessHandlingLan'
			],function( tab , nhjn,hsbzf,wyzf ,hsbzf_fk,dialog ){
	return {	 
		showPage : function(){			
		 
			$('.operationsInner').html(_.template(tab)) ;
			 //缴纳系统使用年费
			$('#nhjn_nhjn').click(function(e) {				 		 
				//this.liActive($('#nhjn_nhjn'));
				comm.liActive($(this));
                nhjn.showPage();
            });			 
			
			// 
//			$('#nhjn_hsbzf').click(function(e) {				 		 
//                hsbzf.showPage();
//				//this.liActive($('#nhjn_nhjn'));
//            }.bind(this));			
//            
//            // 
//			$('#nhjn_wyzf').click(function(e) {				 		 
//                wyzf.showPage();
//				//this.liActive($('#nhjn_nhjn'));
//            }.bind(this)) ;			 
			//权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("030601000000");
			
			//遍历年费缴纳的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			{
				 //缴纳系统使用年费
				 if(match[i].permId =='030601010000')
				 {
					 $('#nhjn_nhjn').show();
					 $('#nhjn_nhjn').click();
					 
				 }
			}
			
		},
		liActive :function(liObj){		 
			liObj.parent('ul').find('a').removeClass('active');
			liObj.find('a').addClass('active');	
		}
	}
}); 