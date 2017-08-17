define([ 'text!reporterTpl/bar/bar12/bar12.html'  ,'echarts',
                'echarts/chart/bar',  ],function(bar12Tpl ,ec){
	return { 
		showPage : function(tabid){			
			$('#main-content > div[data-contentid="'+tabid+'"]').html(bar12Tpl) ;	 	 
			//Todo...
		    option = {
				    title : {
				        text: 'ECharts2 vs ECharts1',
				        subtext: 'Chrome下测试数据'
				    },
				    tooltip : {
				        trigger: 'axis'
				    },
				    legend: {
				        data:[
				            'ECharts1 - 2k数据','ECharts1 - 2w数据','ECharts1 - 20w数据','',
				            'ECharts2 - 2k数据','ECharts2 - 2w数据','ECharts2 - 20w数据'
				        ]
				    },
				    toolbox: {
				        show : true,
				        feature : {
				            mark : {show: true},
				            dataView : {show: true, readOnly: false},
				            magicType : {show: true, type: ['line', 'bar']},
				            restore : {show: true},
				            saveAsImage : {show: true}
				        }
				    },
				    calculable : true,
				    grid: {y: 70, y2:30, x2:20},
				    xAxis : [
				        {
				            type : 'category',
				            data : ['Line','Bar','Scatter','K','Map']
				        },
				        {
				            type : 'category',
				            axisLine: {show:false},
				            axisTick: {show:false},
				            axisLabel: {show:false},
				            splitArea: {show:false},
				            splitLine: {show:false},
				            data : ['Line','Bar','Scatter','K','Map']
				        }
				    ],
				    yAxis : [
				        {
				            type : 'value',
				            axisLabel:{formatter:'{value} ms'}
				        }
				    ],
				    series : [
				        {
				            name:'ECharts2 - 2k数据',
				            type:'bar',
				            itemStyle: {normal: {color:'rgba(193,35,43,1)', label:{show:true}}},
				            data:[40,155,95,75, 0]
				        },
				        {
				            name:'ECharts2 - 2w数据',
				            type:'bar',
				            itemStyle: {normal: {color:'rgba(181,195,52,1)', label:{show:true,textStyle:{color:'#27727B'}}}},
				            data:[100,200,105,100,156]
				        },
				        {
				            name:'ECharts2 - 20w数据',
				            type:'bar',
				            itemStyle: {normal: {color:'rgba(252,206,16,1)', label:{show:true,textStyle:{color:'#E87C25'}}}},
				            data:[906,911,908,778,0]
				        },
				        {
				            name:'ECharts1 - 2k数据',
				            type:'bar',
				            xAxisIndex:1,
				            itemStyle: {normal: {color:'rgba(193,35,43,0.5)', label:{show:true,formatter:function(p){return p.value > 0 ? (p.value +'\n'):'';}}}},
				            data:[96,224,164,124,0]
				        },
				        {
				            name:'ECharts1 - 2w数据',
				            type:'bar',
				            xAxisIndex:1,
				            itemStyle: {normal: {color:'rgba(181,195,52,0.5)', label:{show:true}}},
				            data:[491,2035,389,955,347]
				        },
				        {
				            name:'ECharts1 - 20w数据',
				            type:'bar',
				            xAxisIndex:1,
				            itemStyle: {normal: {color:'rgba(252,206,16,0.5)', label:{show:true,formatter:function(p){return p.value > 0 ? (p.value +'+'):'';}}}},
				            data:[3000,3000,2817,3000,0]
				        }
				    ]
				};
				                    
                    
			                      
				                
		 	 
        
	         $('#barReport12').css({width:'800px',height:'320px'});
             var myChart = ec.init($('#barReport12')[0]);
	         
	         myChart.setOption(option) ;
		 		
			 
 
		 	
		 	 
		}
	}
}); 

 