define(['text!echartsTpl/chord/line.html'  ,'echarts',
                'echarts/chart/chord','echarts/chart/line' ],function(lineTpl ,ec){
	return { 
		showPage : function(){
			$('#busibox').html(_.template(lineTpl)) ;			 
			//Todo...
		 	option = {
				    title : {
				        text: '测试数据',
				        subtext: 'From d3.js',
				        x:'right',
				        y:'bottom'
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
				        feature : {
				            restore : {show: true},
				            magicType: {show: true, type: ['force', 'chord']},
				            saveAsImage : {show: true}
				        }
				    },
				    legend: {
				        x: 'left',
				        data:['group1','group2', 'group3', 'group4']
				    },
				    series : [
				        {
				            type:'chord',
				            sort : 'ascending',
				            sortSub : 'descending',
				            showScale : true,
				            showScaleText : true,
				            data : [
				                {name : 'group1'},
				                {name : 'group2'},
				                {name : 'group3'},
				                {name : 'group4'}
				            ],
				            itemStyle : {
				                normal : {
				                    label : {
				                        show : false
				                    }
				                }
				            },
				            matrix : [
				                [11975,  5871, 8916, 2868],
				                [ 1951, 10048, 2060, 6171],
				                [ 8010, 16145, 8090, 8045],
				                [ 1013,   990,  940, 6907]
				            ]
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

 