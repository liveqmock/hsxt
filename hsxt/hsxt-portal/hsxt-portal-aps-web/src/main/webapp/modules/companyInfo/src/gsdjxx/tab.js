define(['text!companyInfoTpl/gsdjxx/tab.html',
		'companyInfoSrc/gsdjxx/gsdjxx',
		'companyInfoSrc/gsdjxx/gsdjxxxg'  ],function( tab,gsdjxx,gsdjxxxg ){
	return {	 
		showPage : function(){	
			 	
			$('.operationsInner').html(_.template(tab)) ;
			//工商登记信息
			$('#qyxx_gsdjxx').click(function(e) {		
				this.showLi($('#qyxx_gsdjxx') );		 
                gsdjxx.showPage();				 
            }.bind(this));
			
			
			$('#qyxx_gsdjxxxg').click(function(e) {					 
				this.showLi($('#qyxx_gsdjxxxg') );				 
                gsdjxxxg.showPage();				 
			}.bind(this)) ;
			
			//权限设置后默认选择规则
			var match = comm.findPermissionJsonByParentId("051402000000");
			
			//遍历工商登记信息的子菜单设置默认选中
			for(var i = 0 ; i< match.length ; i++)
			{
				 //工商登记信息
				 if(match[i].permId =='051402010000')
				 {
					 $('#qyxx_gsdjxx').show();
					 $('#qyxx_gsdjxx').click();
					 
				 }
			}
			
		},
		showLi : function(liObj){
			$('#qyxx_gsdjxx > a').removeClass('active');
			liObj.css('display','').find('a').addClass('active');
			liObj.siblings('li').not('#qyxx_gsdjxx').css('display','none');
		} 
		
		
	}
}); 

