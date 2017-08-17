define(['text!homeTpl/home/home.html' ],function(homeTpl ){
	return { 
		showPage : function(){		
		 
			//显示或加入标签及内容
			if ($('#main-tab > li[data-tabid="1"]').length ){
				$('#main-tab > li[data-tabid="1"]').removeClass('none') ;
				$('#main-content > div[data-contentid="1"]').removeClass('none') ;
				//隐藏其它
				$('#main-tab > li[data-tabid="1"]').siblings().remove();
				$('#main-content > div[data-contentid="1"]').siblings().remove();
			} else {				
				$('#main-tab').html('<li data-tabid="1">统计分析系统平台</li>') ;
				$('#main-content').html(homeTpl) ; 
			}
			
			$('#aaaaa').initTab(true);
			
		 
		}
	}
}); 

 