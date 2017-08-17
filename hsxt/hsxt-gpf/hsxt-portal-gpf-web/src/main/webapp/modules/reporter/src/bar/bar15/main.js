define([   'text!reporterTpl/bar/bar15/bar15.html' ,'echarts',
                'echarts/chart/bar',  ],function(bar15Tpl ,ec){
	return { 
		showPage : function(tabid){			
			$('#main-content > div[data-contentid="'+tabid+'"]').html(bar15Tpl) ;	 	 
			//Todo...
		    var zrColor = require('zrender/tool/color') ;
			var colorList = [
			  '#ff7f50','#87cefa','#da70d6','#32cd32','#6495ed',
			  '#ff69b4','#ba55d3','#cd5c5c','#ffa500','#40e0d0'
			];
			var itemStyle = {
			    normal: {
			        color: function(params) {
			          if (params.dataIndex < 0) {
			            // for legend
			            return zrColor.lift(
			              colorList[colorList.length - 1], params.seriesIndex * 0.1
			            );
			          }
			          else {
			            // for bar
			            return zrColor.lift(
			              colorList[params.dataIndex], params.seriesIndex * 0.1
			            );
			          }
			        }
			    }
			};
			option = {
			    title: {
			        text: '2010-2013年中国城镇居民家庭人均消费构成（元）',
			        subtext: '数据来自国家统计局',
			        sublink: 'http://data.stats.gov.cn/search/keywordlist2?keyword=%E5%9F%8E%E9%95%87%E5%B1%85%E6%B0%91%E6%B6%88%E8%B4%B9'
			    },
			    tooltip: {
			        trigger: 'axis',
			        backgroundColor: 'rgba(255,255,255,0.7)',
			        axisPointer: {
			            type: 'shadow'
			        },
			        formatter: function(params) {
			            // for text color
			            var color = colorList[params[0].dataIndex];
			            var res = '<div style="color:' + color + '">';
			            res += '<strong>' + params[0].name + '消费（元）</strong>'
			            for (var i = 0, l = params.length; i < l; i++) {
			                res += '<br/>' + params[i].seriesName + ' : ' + params[i].value 
			            }
			            res += '</div>';
			            return res;
			        }
			    },
			    legend: {
			        x: 'right',
			        data:['2010','2011','2012','2013']
			    },
			    toolbox: {
			        show: true,
			        orient: 'vertical',
			        y: 'center',
			        feature: {
			            mark: {show: true},
			            dataView: {show: true, readOnly: false},
			            restore: {show: true},
			            saveAsImage: {show: true}
			        }
			    },
			    calculable: true,
			    grid: {
			        y: 80,
			        y2: 40,
			        x2: 40
			    },
			    xAxis: [
			        {
			            type: 'category',
			            data: ['食品', '衣着', '居住', '家庭设备及用品', '医疗保健', '交通和通信', '文教娱乐服务', '其他']
			        }
			    ],
			    yAxis: [
			        {
			            type: 'value'
			        }
			    ],
			    series: [
			        {
			            name: '2010',
			            type: 'bar',
			            itemStyle: itemStyle,
			            data: [4804.7,1444.3,1332.1,908,871.8,1983.7,1627.6,499.2]
			        },
			        {
			            name: '2011',
			            type: 'bar',
			            itemStyle: itemStyle,
			            data: [5506.3,1674.7,1405,1023.2,969,2149.7,1851.7,581.3]
			        },
			        {
			            name: '2012',
			            type: 'bar',
			            itemStyle: itemStyle,
			            data: [6040.9,1823.4,1484.3,1116.1,1063.7,2455.5,2033.5,657.1]
			        },
			        {
			            name: '2013',
			            type: 'bar',
			            itemStyle: itemStyle,
			            data: [6311.9,1902,1745.1,1215.1,1118.3,2736.9,2294,699.4]
			        }
			    ]
			};
			                      
						                      
							                
		 	 
        
	         $('#barReport15').css({width:'800px',height:'320px'});
             var myChart = ec.init($('#barReport15')[0]);
	         
	         myChart.setOption(option) ;
		 		

			
		 	 
		 	
		 	 
		}
	}
}); 

 