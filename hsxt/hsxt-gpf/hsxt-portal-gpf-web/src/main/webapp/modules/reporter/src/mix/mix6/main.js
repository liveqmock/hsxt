define([ 'text!reporterTpl/mix/mix6/mix6.html'  ,'echarts',
               'echarts/chart/bar','echarts/chart/line' ],function(lineTpl ,ec){
	return { 
		showPage : function(tabid){			
			$('#main-content > div[data-contentid="'+tabid+'"]').html(lineTpl) ;		 	 
			//Todo...
		 	 
			option = {
    title :{
        text : '销售数据',
        subtext : '纯属虚构'
    },
    tooltip : {
        trigger: 'axis',
        formatter: function (params){
            return params[0].name + ' : '
                   + (params[2].value - params[1].value > 0 ? '+' : '-') 
                   + params[0].value + '<br/>'
                   + params[2].seriesName + ' : ' + params[2].value + '<br/>'
                   + params[3].seriesName + ' : ' + params[3].value + '<br/>'
        }
    },
    toolbox: {
        show : true,
        feature : {
            mark : {show: true},
            dataView : {show: true, readOnly: false},
            magicType : {show: true, type: ['line', 'bar']},
            restore : {show: true},
            saveAsImage : {show: true}
        }
    },
    legend: {
        data:['本周', '上周'],
        selectedMode:false
    },
    xAxis : [
        {
            type : 'category',
            data : ['周一','周二','周三','周四','周五','周六','周日']
        }
    ],
    yAxis : [
        {
            type : 'value',
            min : 200,
            max : 450
        }
    ],
    series : [
        {
            name:'本周',
            type:'line',
            data:[400, 374, 251, 300, 420, 400, 440]
        },
        {
            name:'上周',
            type:'line',
            symbol:'none',
            itemStyle:{
                normal:{
                  lineStyle: {
                    width:1,
                    type:'dashed'
                  }
                }
            },
            data:[320, 332, 301, 334, 360, 330, 350]
        },
        {
            name:'上周2',
            type:'bar',
            stack: '1',
            barWidth: 6,
            itemStyle:{
                normal:{
                    color:'rgba(0,0,0,0)'
                },
                emphasis:{
                    color:'rgba(0,0,0,0)'
                }
            },
            data:[320, 332, 251, 300, 360, 330, 350]
        },
        {
            name:'变化',
            type:'bar',
            stack: '1',
            data:[
              80, 42, 
              {value : 50, itemStyle:{ normal:{color:'red'}}},
              {value : 34, itemStyle:{ normal:{color:'red'}}}, 
              60, 70, 90
            ]
        }
    ]
};
                    	                     
		 	    
		 	 
        
	         $('#mix6').css({width:'800px',height:'320px'});
	         var myChart = ec.init($('#mix6')[0]);
	         myChart.setOption(option) ;
		 		

			 
		 	
		 	 
		}
	}
}); 

 