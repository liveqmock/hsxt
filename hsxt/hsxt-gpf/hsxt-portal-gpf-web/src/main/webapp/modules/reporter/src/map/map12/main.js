define(['text!reporterTpl/map/map12/line.html'  ,'echarts',
                'echarts/chart/map',],function(lineTpl ,ec){
	return { 
		showPage : function(tabid){			
			$('#main-content > div[data-contentid="'+tabid+'"]').html(lineTpl) ;	 		 
			//Todo...
		 	 option = {
    series : [
        {
            name: 'Map',
            type: 'map',
            mapLocation: {
                x : 'left',
                y : 'top',
                height : 500
            },
            selectedMode: 'multiple',
            itemStyle: {
                normal: {
                    borderWidth:2,
                    borderColor:'lightgreen',
                    color: 'orange',
                    label: {
                        show: false
                    }
                },
                emphasis: {                 // 也是选中样式
                    borderWidth:2,
                    borderColor:'#fff',
                    color: '#32cd32',
                    label: {
                        show: true,
                        textStyle: {
                            color: '#fff'
                        }
                    }
                }
            },
            data:[
                 {
                     name: '广东',
                     value: Math.round(Math.random()*1000),
                     itemStyle: {
                        normal: {
                            color: '#32cd32',
                            label: {
                                show: true,
                                textStyle: {
                                    color: '#fff',
                                    fontSize: 15
                                }
                            }
                        },
                        emphasis: {                 // 也是选中样式
                            borderWidth:5,
                            borderColor:'yellow',
                            color: '#cd5c5c',
                            label: {
                                show: false,
                                textStyle: {
                                    color: 'blue'
                                }
                            }
                        }
                    }
                }
            ],
            markPoint : {
                itemStyle : {
                    normal:{
                        color:'skyblue'
                    }
                },
                data : [
                    {name : '天津', value : 350},
                    {name : '上海', value : 103},
                    {
                        name : 'echarts',
                       // symbol: 'image://../asset/img/echarts-logo.png',
                        symbol:'library/plugins/echarts/doc/asset/img/echarts-logo.png',
                        symbolSize: 21,
                        x: 300,
                        y: 100
                    }
                ]
            },
            geoCoord: {
                '上海': [121.4648,31.2891],
                '天津': [117.4219,39.4189]
            }
        }
    ]
};
                    
                    
        
	        $('#map12').css({width:'800px',height:'400px'});
	        var myChart = ec.init($('#map12')[0]);
	        myChart.setOption(option) ;
			 		

			
 
		 	
		 	 
		}
	}
}); 

 