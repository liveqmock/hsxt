define(['text!echartsTpl/chord/line.html'  ,'echarts',
                'echarts/chart/chord',],function(lineTpl ,ec){
	return { 
		showPage : function(){
			$('#busibox').html(_.template(lineTpl)) ;			 
			//Todo...
		 	option = {
			    title : {
			        text: '测试数据'
			    },
			    tooltip : {
			        trigger: 'item',
			        formatter: function (params) {
			            if (params.indicator2) { // is edge
			                return params.value.weight;
			            } else {// is node
			                return params.name
			            }
			        }
			    },
			    toolbox: {
			        show : true,
			        y:'bottom',
			        feature : {
			            restore : {show: true},
			            magicType: {show: true, type: ['force', 'chord']},
			            saveAsImage : {show: true}
			        }
			    },
			    legend: {
			        x: 'right',
			        data:['g1','g2', 'g3', 'g4']
			    },
			    series : [
			        {
			            type:'chord',
			            radius : ['55%', '75%'],
			            center : ['50%', '50%'],
			            padding : 2,
			            sort : 'descending', // can be 'none', 'ascending', 'descending'
			            sortSub : 'descending', // can be 'none', 'ascending', 'descending'
			            startAngle : 90,
			            clockWise : false,
			            showScale : true,
			            showScaleText : true,
			            itemStyle : {
			                normal : {
			                    borderWidth : 0,
			                    borderColor : '#000',
			                    chordStyle : {
			                        borderWidth : 1,
			                        borderColor : '#333'
			                    },
			                    label: {
			                        show: true,
			                        color: 'red'
			                    }
			                },
			                emphasis : {
			                    borderWidth : 0,
			                    borderColor : '#000',
			                    chordStyle : {
			                        borderWidth : 2,
			                        borderColor : 'black'
			                    }
			                }
			            },
			            data : [
			                {
			                    name : 'g1',
			                    itemStyle : {
			                        normal: {
			                            color: 'rgba(255,30,30,0.5)',
			                            lineStyle : {
			                                width: 1,
			                                color: 'green'
			                            }
			                        },
			                        emphasis: {
			                            color: 'yellow',
			                            lineStyle : {
			                                width: 2,
			                                color: 'blue'
			                            }
			                        }
			                    }
			                },
			                {name : 'g2'},
			                {name : 'g3'},
			                {name : 'g4'}
			            ],
			            matrix : [
			                [11975,  5871, 8916, 2868],
			                [ 1951, 10048, 2060, 6171],
			                [ 8010, 16145, 8090, 8045],
			                [ 1013,   990,  940, 6907]
			            ],
			            markPoint : {
			                symbol: 'star',
			                data : [
			                    {name : '打酱油的标注', value : 100, x:'5%', y:'50%', symbolSize:32},
			                    {name : '打酱油的标注', value : 100, x:'95%', y:'50%', symbolSize:32}
			                ]
			            }
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

 