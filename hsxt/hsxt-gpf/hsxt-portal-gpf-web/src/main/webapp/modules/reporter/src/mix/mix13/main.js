define([ 'text!reporterTpl/mix/mix13/mix13.html'  ,'echarts',
                 'echarts/chart/bar','echarts/chart/line'  ],function(lineTpl ,ec){
	return { 
		showPage : function(tabid){			
			$('#main-content > div[data-contentid="'+tabid+'"]').html(lineTpl) ;		 
			//Todo...
		 	 
			                    
                    
        
	        $('#mix13').css({width:'800px',height:'320px'});
	        var myChart = ec.init($('#13')[0]);
	        myChart.setOption(option) ;
			 		

			
 
		 	
		 	 
		}
	}
}); 

 