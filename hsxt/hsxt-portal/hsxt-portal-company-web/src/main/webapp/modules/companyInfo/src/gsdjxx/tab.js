define(['text!companyInfoTpl/gsdjxx/tab.html',
		'companyInfoSrc/gsdjxx/gsdjxx',
		'companyInfoSrc/gsdjxx/gsdjxxxg'
		], function(tpl, gsdjxx, gsdjxxxg){
	return {
		showPage : function(){	
		 	
			$('.operationsInner').html(_.template(tpl)) ;
			// 
			$('#gsdjxx').click(function(e) {		
				this.showLi($('#gsdjxx') );		 
                gsdjxx.showPage();				 
            }.bind(this));
			
			
			$('#gsdjxx_xg').click(function(e) {					 
				this.showLi($('#gsdjxx_xg') );				 
                gsdjxxxg.showPage();				 
           }.bind(this)) ; 
			
			//获取缓存工商登记信息三级菜单
			var match = comm.findPermissionJsonByParentId("020402000000"); 

			 for(var i = 0 ; i< match.length ; i++)
			 {
				 //工商登记信息
				 if(match[i].permId =='020402010000')
				 {
					 $('#gsdjxx').show();
					 $('#gsdjxx').click();
				 }
				
			 }
		},
		showLi : function(liObj){
			$('#gsdjxx > a').removeClass('active');
			liObj.css('display','').find('a').addClass('active');
			liObj.siblings('li').not('#gsdjxx').css('display','none');
		} 
	}	
});