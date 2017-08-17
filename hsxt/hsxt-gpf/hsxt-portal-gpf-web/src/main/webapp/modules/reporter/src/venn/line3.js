define(['text!echartsTpl/line/line.html'  ,'echarts',
                'echarts/chart/bar',],function(lineTpl ,ec){
	return { 
		showPage : function(){
			$('#busibox').html(_.template(lineTpl)) ;			 
			//Todo...
		 	
		 	option = {
			    title : {
			        text: '某楼盘销售情况',
			        subtext: '纯属虚构'
			    },
			    tooltip : {
			        trigger: 'axis'
			    },
			    legend: {
			        data:['意向','预购','成交']
			    },
			    toolbox: {
			        show : true,
			        feature : {
			            mark : {show: true},
			            dataView : {show: true, readOnly: false},
			            magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
			            restore : {show: true},
			            saveAsImage : {show: true}
			        }
			    },
			    calculable : true,
			    xAxis : [
			        {
			            type : 'category',
			            boundaryGap : false,
			            data : ['周一','周二','周三','周四','周五','周六','周日']
			        }
			    ],
			    yAxis : [
			        {
			            type : 'value'
			        }
			    ],
			    series : [
			        {
			            name:'成交',
			            type:'line',
			            smooth:true,
			            itemStyle: {normal: {areaStyle: {type: 'default'}}},
			            data:[10, 12, 21, 54, 260, 830, 710]
			        },
			        {
			            name:'预购',
			            type:'line',
			            smooth:true,
			            itemStyle: {normal: {areaStyle: {type: 'default'}}},
			            data:[30, 182, 434, 791, 390, 30, 10]
			        },
			        {
			            name:'意向',
			            type:'line',
			            smooth:true,
			            itemStyle: {normal: {areaStyle: {type: 'default'}}},
			            data:[1320, 1132, 601, 234, 120, 90, 20]
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

 