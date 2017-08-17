define([ 'echarts',   'echarts/chart/scatter',],function(ec){
	return { 
		showPage : function(tabid){
 		 
			//Todo...
		 	
		 	option = {
				    tooltip : {
				        trigger: 'axis',
				        axisPointer:{
				            show: true,
				            type : 'cross',
				            lineStyle: {
				                type : 'dashed',
				                width : 1
				            }
				        }
				    },
				    legend: {
				        data:['scatter1','scatter2']
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
				    xAxis : [
				        {
				            type : 'value'
				        }
				    ],
				    yAxis : [
				        {
				            type : 'value'
				        }
				    ],
				    series : [
				        {
				            name:'scatter1',
				            type:'scatter',
				            symbol: 'emptyCircle', //'circle', 'rectangle', 'triangle', 'diamond', 'emptyCircle', 'emptyRectangle', 'emptyTriangle', 'emptyDiamond'
				            symbolSize: function (value){
				                if (value[0] < 2) {
				                    return 2;
				                }
				                else if (value[0] < 8) {
				                    return Math.round(value[2] * 3);
				                }
				                else {
				                    return 20;
				                }
				            },
				            itemStyle: {
				                normal: {
				                    color: 'lightblue',
				                    borderWidth: 4,
				                    label : {show: true}
				                },
				                emphasis: {
				                    color: 'lightgreen',
				                }
				            },
				            data: (function () {
				                var d = [];
				                var len = 20;
				                while (len--) {
				                    d.push([
				                        (Math.random()*10).toFixed(2) - 0,
				                        (Math.random()*10).toFixed(2) - 0,
				                        (Math.random()*10).toFixed(2) - 0
				                    ]);
				                }
				                return d;
				            })(),
				            markPoint : {
				                data : [
				                    {type : 'max', name: 'y最大值'},
				                    {type : 'min', name: 'y最小值'},
				                    {type : 'max', name: 'x最大值', valueIndex : 0, symbol:'arrow',itemStyle:{normal:{borderColor:'red'}}},
				                    {type : 'min', name: 'x最小值', valueIndex : 0, symbol:'arrow',itemStyle:{normal:{borderColor:'red'}}}
				                ]
				            },
				            markLine : {
				                data : [
				                    {type : 'average', name: 'y平均值'},
				                    {type : 'average', name: 'x平均值', valueIndex : 0, itemStyle:{normal:{borderColor:'red'}}}
				                ]
				            }
				        },
				        {
				            name:'scatter2',
				            type:'scatter',
				            symbol: 'image://../asset/ico/favicon.png',     // 系列级个性化拐点图形
				            symbolSize: function (value){
				                return Math.round(value[2] * 3);
				            },
				            itemStyle: {
				                emphasis : {
				                    label : {show: true}
				                }
				            },
				            data: (function () {
				                var d = [];
				                var len = 20;
				                while (len--) {
				                    d.push([
				                        (Math.random()*10).toFixed(2) - 0,
				                        (Math.random()*10).toFixed(2) - 0,
				                        (Math.random()*10).toFixed(2) - 0
				                    ]);
				                }
				                d.push({
				                    value : [5,5,1000],
				                    itemStyle: {
				                        normal: {
				                            borderWidth: 8,
				                            color: 'orange'
				                        },
				                        emphasis: {
				                            borderWidth: 10,
				                            color: '#ff4500'
				                        }
				                    },
				                    symbol: 'emptyTriangle',
				                    symbolRotate:90,
				                    symbolSize:30
				                })
				                return d;
				            })(),
				            markPoint : {
				                symbol: 'emptyCircle',
				                itemStyle:{
				                    normal:{label:{position:'top'}}
				                },
				                data : [
				                    {name : '有个东西', value : 1000, xAxis: 5, yAxis: 5, symbolSize:80}
				                ]
				            }
				        }
				    ]
				};
				                     
                    
        
	         $('#main-content > div[data-contentid="'+tabid+'"]').css({width:'800px',height:'320px'});
            var myChart = ec.init($('#main-content > div[data-contentid="'+tabid+'"]')[0]);
	        myChart.setOption(option) ;
			 		

			
		 	$('#ModifyBt_gongshang').triggerWith('#qyxx_gsdjxxxg');
		 	
		 	 
		}
	}
}); 

 