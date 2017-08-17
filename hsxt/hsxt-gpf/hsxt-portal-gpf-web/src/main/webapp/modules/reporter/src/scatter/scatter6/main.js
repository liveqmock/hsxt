define([ 'echarts', 'echarts/chart/scatter',],function( ec){
	return { 
		showPage : function(tabid){
	 	 
			//Todo... 
		 	option = {
			    title : {
			        text : '时间坐标散点图',
			        subtext : 'dataZoom支持'
			    },
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
			    toolbox: {
			        show : true,
			        feature : {
			            mark : {show: true},
			            dataView : {show: true, readOnly: false},
			            restore : {show: true},
			            saveAsImage : {show: true}
			        }
			    },
			    dataZoom: {
			        show: true,
			        start : 30,
			        end : 70
			    },
			    legend : {
			        data : ['series1']
			    },
			    dataRange: {
			        min: 0,
			        max: 100,
			        orient: 'horizontal',
			        y: 30,
			        x: 'center',
			        //text:['高','低'],           // 文本，默认为数值文本
			        color:['lightgreen','orange'],
			        splitNumber: 5
			    },
			    grid: {
			        y2: 80
			    },
			    xAxis : [
			        {
			            type : 'time',
			            splitNumber:10
			        }
			    ],
			    yAxis : [
			        {
			            type : 'value'
			        }
			    ],
			    animation: false,
			    series : [
			        {
			            name:'series1',
			            type:'scatter',
			            tooltip : {
			                trigger: 'axis',
			                formatter : function (params) {
			                    var date = new Date(params.value[0]);
			                    return params.seriesName 
			                           + ' （'
			                           + date.getFullYear() + '-'
			                           + (date.getMonth() + 1) + '-'
			                           + date.getDate() + ' '
			                           + date.getHours() + ':'
			                           + date.getMinutes()
			                           +  '）<br/>'
			                           + params.value[1] + ', ' 
			                           + params.value[2];
			                },
			                axisPointer:{
			                    type : 'cross',
			                    lineStyle: {
			                        type : 'dashed',
			                        width : 1
			                    }
			                }
			            },
			            symbolSize: function (value){
			                return Math.round(value[2]/10);
			            },
			            data: (function () {
			                var d = [];
			                var len = 0;
			                var now = new Date();
			                var value;
			                while (len++ < 1500) {
			                    d.push([
			                        new Date(2014, 9, 1, 0, Math.round(Math.random()*10000)),
			                        (Math.random()*30).toFixed(2) - 0,
			                        (Math.random()*100).toFixed(2) - 0
			                    ]);
			                }
			                return d;
			            })()
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

 