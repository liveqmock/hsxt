define([ 'text!reporterTpl/bar/bar3/bar3.html'   ,'echarts',
                'echarts/chart/bar',],function(lineTpl ,ec){
	return { 
		showPage : function(tabid){
			
			$('#main-content > div[data-contentid="'+tabid+'"]').html(lineTpl) ;	 
			//Todo...
		 	
		 	 option = {
				    title : {
				        text: '世界人口总量',
				        subtext: '数据来自网络'
				    },
				    tooltip : {
				        trigger: 'axis'
				    },
				    legend: {
				        data:['2011年', '2012年']
				    },
				    toolbox: {
				        show : true,
				        feature : {
				            mark : {show: true},
				            dataView : {show: true, readOnly: false},
				            magicType: {show: true, type: ['line', 'bar']},
				            restore : {show: true},
				            saveAsImage : {show: true}
				        }
				    },
				    calculable : true,
				    xAxis : [
				        {
				            type : 'value',
				            boundaryGap : [0, 0.01]
				        }
				    ],
				    yAxis : [
				        {
				            type : 'category',
				            data : ['巴西','印尼','美国','印度','中国','世界人口(万)']
				        }
				    ],
				    series : [
				        {
				            name:'2011年',
				            type:'bar',
				            data:[18203, 23489, 29034, 104970, 131744, 630230]
				        },
				        {
				            name:'2012年',
				            type:'bar',
				            data:[19325, 23438, 31000, 121594, 134141, 681807]
				        }
				    ]
				};
				                    
			                    
		 	
		 	
		 	
		 	 
        
	        $('#barReport3').css({width:'800px',height:'320px'});
            var myChart = ec.init($('#barReport3')[0]);
            
	        myChart.setOption(option) ;
		 		

			
	 
		 	
		 	 
		}
	}
}); 

 