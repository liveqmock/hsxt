define([  'text!reporterTpl/radar/radar2/line2.html' , 'echarts',
                'echarts/chart/radar',],function(radarTpl, ec){
	return { 
		showPage : function(tabid){			
			$('#main-content > div[data-contentid="'+tabid+'"]').html(radarTpl) ;	 
			//Todo...
		 	
		 	option = {
			    title : {
			        text: '罗纳尔多 vs 舍普琴科',
			        subtext: '完全实况球员数据'
			    },
			    tooltip : {
			        trigger: 'axis'
			    },
			    legend: {
			        x : 'center',
			        data:['罗纳尔多','舍普琴科']
			    },
			    toolbox: {
			        show : true,
			        feature : {
			            mark : {show: true},
			            dataView : {show: true, readOnly: false},
			            restore : {show: true},
			            saveAsImage : {show: true}
			        }
			    },
			    calculable : true,
			    polar : [
			        {
			            indicator : [
			                {text : '进攻', max  : 100},
			                {text : '防守', max  : 100},
			                {text : '体能', max  : 100},
			                {text : '速度', max  : 100},
			                {text : '力量', max  : 100},
			                {text : '技巧', max  : 100}
			            ],
			            radius : 130
			        }
			    ],
			    series : [
			        {
			            name: '完全实况球员数据',
			            type: 'radar',
			            itemStyle: {
			                normal: {
			                    areaStyle: {
			                        type: 'default'
			                    }
			                }
			            },
			            data : [
			                {
			                    value : [97, 42, 88, 94, 90, 86],
			                    name : '舍普琴科'
			                },
			                {
			                    value : [97, 32, 74, 95, 88, 92],
			                    name : '罗纳尔多'
			                }
			            ]
			        }
			    ]
			};
			                     
        	
        	
	       $('#radar2').css({width:'800px',height:'320px'});
            var myChart = ec.init($('#radar2')[0]);
	        myChart.setOption(option) ;			
		 	 
		}
	}
}); 

 