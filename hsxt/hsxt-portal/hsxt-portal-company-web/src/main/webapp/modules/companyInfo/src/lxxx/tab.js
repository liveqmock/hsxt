define(['text!companyInfoTpl/lxxx/tab.html',
		'companyInfoSrc/lxxx/lxxx',
		'companyInfoSrc/lxxx/lxxxxg',
		'companyInfoLan'
		], function(tab, lxxx, lxxxxg){
	return {
		showPage : function(){	
		 	
			$('.operationsInner').html(_.template(tab)) ;
			// 
			$('#lxxx').click(function(e) {	
					this.showLi($('#lxxx')); 
	            lxxx.showPage();				 
	        }.bind(this));
			
			$('#lxxx_xg').click(function(e) {		
			 	this.showLi($('#lxxx_xg')); 
	            lxxxxg.showPage();				 
	        }.bind(this)) ;
			
			//获取缓存联系信息三级菜单
			var match = comm.findPermissionJsonByParentId("020403000000"); 
	
			 for(var i = 0 ; i< match.length ; i++)
			 {
				 //联系信息
				 if(match[i].permId =='020403010000')
				 {
					 $('#lxxx').show();
					 $('#lxxx').click();
				 }
				
			 }
			 
		} ,
		showLi : function(liObj){
			$('#lxxx > a').removeClass('active');
			liObj.css('display','').find('a').addClass('active');
			liObj.siblings('li').not('#lxxx').css('display','none');
		} 
	}
});