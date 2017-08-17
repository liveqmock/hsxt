define(['text!echartsTpl/line/line.html'  ,'echarts',
                'echarts/chart/bar',],function(lineTpl ,ec){
	return { 
		showPage : function(){
			$('#busibox').html(_.template(lineTpl)) ;			 
			//Todo...
		 	 
		 	 option = {
				    title : {
				        text : '时间坐标折线图',
				        subtext : 'dataZoom支持'
				    },
				    tooltip : {
				        trigger: 'item',
				        formatter : function (params) {
				            var date = new Date(params.value[0]);
				            data = date.getFullYear() + '-'
				                   + (date.getMonth() + 1) + '-'
				                   + date.getDate() + ' '
				                   + date.getHours() + ':'
				                   + date.getMinutes();
				            return data + '<br/>'
				                   + params.value[1] + ', ' 
				                   + params.value[2];
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
				        start : 70
				    },
				    legend : {
				        data : ['series1']
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
				    series : [
				        {
				            name: 'series1',
				            type: 'line',
				            showAllSymbol: true,
				            symbolSize: function (value){
				                return Math.round(value[2]/10) + 2;
				            },
				            data: (function () {
				                var d = [];
				                var len = 0;
				                var now = new Date();
				                var value;
				                while (len++ < 200) {
				                    d.push([
				                        new Date(2014, 9, 1, 0, len * 10000),
				                        (Math.random()*30).toFixed(2) - 0,
				                        (Math.random()*100).toFixed(2) - 0
				                    ]);
				                }
				                return d;
				            })()
				        }
				    ]
				};
				                    
		 	 
        
	        $('#emain').css({width:'800px',height:'320px'});
	         var myChart = ec.init($('#emain')[0]);
	         myChart.setOption(option) ;
			 		

			
		 	$('#ModifyBt_gongshang').triggerWith('#qyxx_gsdjxxxg');
		 	
		 	 
		}
	}
}); 

 