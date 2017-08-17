define(['text!companyInfoTpl/lxxx/tab.html',
		'companyInfoSrc/lxxx/lxxx','companyInfoSrc/lxxx/lxxxxg'  ],function( tab,lxxx,lxxxxg ){
	return {	 
		showPage : function(){	
			 	
			$('.operationsInner').html(_.template(tab)) ;
			// 联系信息
			$('#qyxx_lxxx').click(function(e) {	
 				this.showLi($('#qyxx_lxxx')); 
                lxxx.showPage();				 
            }.bind(this));
			
			$('#qyxx_lxxxxg').click(function(e) {		
 		 		this.showLi($('#qyxx_lxxxxg')); 
                lxxxxg.showPage();				 
            }.bind(this)) ;
			
			//权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("051403000000");
			
			//遍历联系信息的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			{
				 //联系信息
				 if(match[i].permId =='051403010000')
				 {
					 $('#qyxx_lxxx').show();
					 $('#qyxx_lxxx').click();
					 
				 }
			}
			 
			 
		} ,
		showLi : function(liObj){
			$('#qyxx_lxxx > a').removeClass('active');
			liObj.css('display','').find('a').addClass('active');
			liObj.siblings('li').not('#qyxx_lxxx').css('display','none');
		} 
	}
}); 

