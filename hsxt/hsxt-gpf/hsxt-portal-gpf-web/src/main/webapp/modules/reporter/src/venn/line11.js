define(['text!echartsTpl/line/line.html'  ,'echarts',
                'echarts/chart/bar',],function(lineTpl ,ec){
	return { 
		showPage : function(){
			$('#busibox').html(_.template(lineTpl)) ;			 
			//Todo...
		 	 
		 	 option = {
				   title: {
				       text: "对数轴示例",
				       x: "center"
				   },
				   tooltip: {
				       trigger: "item",
				       formatter: "{a} <br/>{b} : {c}"
				   },
				   legend: {
				       x: 'left',
				       data: ["2的指数"]
				   },
				   xAxis: [
				       {
				           type: "category",
				           name: "x",
				           splitLine: {show: false},
				           data: ["一", "二", "三", "四", "五", "六", "七", "八", "九"]
				       }
				   ],
				   yAxis: [
				       {
				           type: "log",
				           logLabelBase: 2,
				           name: "y",
				           axisLabel: {
				                margin: '25',
				                textStyle: {
				                    align: 'left'
				                }
				           }
				       }
				   ],
				    toolbox: {
				       show: true,
				       feature: {
				           mark: {
				               show: true
				           },
				           dataView: {
				               show: true,
				               readOnly: true
				           },
				           restore: {
				               show: true
				           },
				           saveAsImage: {
				               show: true
				           }
				       }
				   },
				   calculable: true,
				   series: [
				       {
				           name: "2的指数",
				           type: "line",
				           data: [-2, -4, -8, -16, -32, -64, -128]
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

 