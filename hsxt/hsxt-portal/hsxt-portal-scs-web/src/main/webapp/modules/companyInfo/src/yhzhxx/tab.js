define(['text!companyInfoTpl/yhzhxx/tab.html',
		'companyInfoSrc/yhzhxx/yhzhxx',
		'companyInfoSrc/yhzhxx/yhzhxx_xq',
		'companyInfoSrc/yhzhxx/yhzhxx_tj',
		 ],function( tab,yhzhxx , yhzhxx_xq, yhzhxx_tj ){
	return {	 
		showPage : function(){	
			 	
			$('.operationsInner').html(_.template(tab)) ;
			//银行账户信息
			$('#qyxx_yhzhxx').click(function(e) {	
				this.showLi($('#qyxx_yhzhxx') );			 
                yhzhxx.showPage();				 
            }.bind(this));
			
           $('#qyxx_yhzhxxxq').click(function(e) {		
				this.showLi($('#qyxx_yhzhxxxq') );			 
                yhzhxx_xq.showPage();				 
            }.bind(this)) ;  
           
           $('#qyxx_yhzhxxtj').click(function(e) {		
				this.showLi($('#qyxx_yhzhxxtj') );			 
                yhzhxx_tj.showPage();				 
            }.bind(this)) ;  
           
         //权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("030504000000");
			
			//遍历银行账户信息查询的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			{
				 //银行账户信息
				 if(match[i].permId =='030504010000')
				 {
					 $('#qyxx_yhzhxx').show();
					 $('#qyxx_yhzhxx').click();
					 
				 }
			}
            
		} ,
		showLi : function(liObj){			
			$('#qyxx_yhzhxx > a').removeClass('active');
			liObj.css('display','').find('a').addClass('active');
			liObj.siblings('li').not('#qyxx_yhzhxx').css('display','none');
		} 
		
		
	}
}); 

