define([ 'text!reporterTpl/funnel/funnel2.html'  ,'echarts',
                'echarts/chart/funnel',],function(lineTpl ,ec){
	return { 
		showPage : function(tabid){			
			$('#main-content > div[data-contentid="'+tabid+'"]').html(lineTpl) ;		
			//Todo...
		 	option = {
			    color : [
			        'rgba(255, 69, 0, 0.5)',
			        'rgba(255, 150, 0, 0.5)',
			        'rgba(255, 200, 0, 0.5)',
			        'rgba(155, 200, 50, 0.5)',
			        'rgba(55, 200, 100, 0.5)'
			    ],
			    title : {
			        text: '漏斗图',
			        subtext: '纯属虚构'
			    },
			    tooltip : {
			        trigger: 'item',
			        formatter: "{a} <br/>{b} : {c}%"
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
			    legend: {
			        data : ['展现','点击','访问','咨询','订单']
			    },
			    calculable : true,
			    series : [
			        {
			            name:'预期',
			            type:'funnel',
			            x: '10%',
			            width: '80%',
			            itemStyle: {
			                normal: {
			                    label: {
			                        formatter: '{b}预期'
			                    },
			                    labelLine: {
			                        show : false
			                    }
			                },
			                emphasis: {
			                    label: {
			                        position:'inside',
			                        formatter: '{b}预期 : {c}%'
			                    }
			                }
			            },
			            data:[
			                {value:60, name:'访问'},
			                {value:40, name:'咨询'},
			                {value:20, name:'订单'},
			                {value:80, name:'点击'},
			                {value:100, name:'展现'}
			            ]
			        },
			        {
			            name:'实际',
			            type:'funnel',
			            x: '10%',
			            width: '80%',
			            maxSize: '80%',
			            itemStyle: {
			                normal: {
			                    borderColor: '#fff',
			                    borderWidth: 2,
			                    label: {
			                        position: 'inside',
			                        formatter: '{c}%',
			                        textStyle: {
			                            color: '#fff'
			                        }
			                    }
			                },
			                emphasis: {
			                    label: {
			                        position:'inside',
			                        formatter: '{b}实际 : {c}%'
			                    }
			                }
			            },
			            data:[
			                {value:30, name:'访问'},
			                {value:10, name:'咨询'},
			                {value:5, name:'订单'},
			                {value:50, name:'点击'},
			                {value:80, name:'展现'}
			            ]
			        }
			    ]
			};
			                    
		 	 
        	
        	
	        $('#funnel2').css({width:'800px',height:'320px'});
	        var myChart = ec.init($('#funnel2')[0]);
	        myChart.setOption(option) ;			
		 	$('#ModifyBt_gongshang').triggerWith('#qyxx_gsdjxxxg');		 	
		 	 
		}
	}
}); 

 