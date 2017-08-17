define(['text!echartsTpl/line/line.html'  ,'echarts',
                'echarts/chart/bar',],function(lineTpl ,ec){
	return { 
		showPage : function(){
			$('#busibox').html(_.template(lineTpl)) ;			 
			//Todo...
		 	
		 	 
        	
        	
	        $('#emain').css({width:'800px',height:'320px'});
	        var myChart = ec.init($('#emain')[0]);
	        myChart.setOption(option) ;			
		 	$('#ModifyBt_gongshang').triggerWith('#qyxx_gsdjxxxg');		 	
		 	 
		}
	}
}); 

 