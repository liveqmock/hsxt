define(['text!reporterTpl/funnel/funnel1.html'  ,'echarts',
                'echarts/chart/funnel','echarts/chart/line' ],function(lineTpl ,ec){
	return { 
		showPage : function(tabid){			
			$('#main-content > div[data-contentid="'+tabid+'"]').html(lineTpl) ;		 
			//Todo...
		 	option = {
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
				            name:'漏斗图',
				            type:'funnel',
				            width: '40%',
				            data:[
				                {value:60, name:'访问'},
				                {value:40, name:'咨询'},
				                {value:20, name:'订单'},
				                {value:80, name:'点击'},
				                {value:100, name:'展现'}
				            ]
				        },
				        {
				            name:'金字塔',
				            type:'funnel',
				            x : '50%',
				            sort : 'ascending',
				            itemStyle: {
				                normal: {
				                    // color: 各异,
				                    label: {
				                        position: 'left'
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
				        }
				    ]
				};
				                     
        
        
        
        
        
         $('#funnel1').css({width:'800px',height:'320px'});
         var myChart = ec.init($('#funnel1')[0]);
         myChart.setOption(option) ;
		 		

			
		 	$('#ModifyBt_gongshang').triggerWith('#qyxx_gsdjxxxg');
		 	
		 	 
		}
	}
}); 

 