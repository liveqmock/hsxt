define(['text!safeSetTpl/czjymm/tab.html',
		'safeSetSrc/czjymm/czjymm',
		'safeSetSrc/czjymm/czjymm2',
		'safeSetSrc/czjymm/czjymm3' ],function( tab,czjymm,czjymm2,czjymm3 ){
	return {	 
		showPage : function(){	
			 	
			$('.operationsInner').html(_.template(tab)) ;
			//重置交易密码
			$('#xtaq_czjymm').click(function(e) {		
				this.showLi($('#xtaq_czjymm'));		 
                czjymm.showPage();				 
            }.bind(this));
            
            $('#xtaq_czjymm2').click(function(e) {		
            	this.showLi($('#xtaq_czjymm2'));				 
                czjymm2.showPage();				 
            }.bind(this)) ;
            
            $('#xtaq_czjymm3').click(function(e) {		
            	this.showLi($('#xtaq_czjymm3'));				 
                czjymm3.showPage();				 
            }.bind(this)) ;
            
          //权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("030704000000");
			
			//遍历重置交易密码的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			{
				 //重置交易密码
				 if(match[i].permId =='030704010000')
				 {
					 $('#xtaq_czjymm').show();
					 $('#xtaq_czjymm').click();
					 
				 }
			}
             
		} ,
		
		showLi : function(liObj){
			$('#xtaq_czjymm > a').removeClass('active');
			liObj.css('display','').find('a').addClass('active');
			liObj.siblings('li').not('#xtaq_czjymm').css('display','none');
		}
		
		
		
		
	}
}); 

