define([ 'text!reporterTpl/mix/mix12/mix12.html'  ,'echarts',
               'echarts/chart/bar','echarts/chart/line','echarts/chart/pie'  ],function(lineTpl ,ec){
	return { 
		showPage : function(tabid){			
			$('#main-content > div[data-contentid="'+tabid+'"]').html(lineTpl) ;		 	 
			//Todo...
		    
				                    
             // original
var data = [30, 20, 54, 21, 90, 30, 10];

var gap = 0;
var total = 0;
var maxIndex;
var dataArray = (function(){
    var max = Math.max.apply(Math, data);
    var min = Math.min.apply(Math, data);
    gap = Math.round((max - min));
    var nd = [{value:data[0] + gap,symbol:'none'}];
    for (var i = 0, l = data.length; i < l; i++) {
        if (data[i] == max) {
            maxIndex = i;
        }
        total += data[i];
        nd.push(data[i] + gap);
    }
    nd.push({value:data[data.length - 1] + gap,symbol:'none'});
    return nd;
})();

option = {
    backgroundColor:'#fff',
    title : {
        text: '某楼盘销售情况',
        subtext: '纯属虚构 折线饼图交互混搭实例',
        x: 'center'
    },
    legend: {
        data:['销量', '占比'],
        x: 'left',
        orient: 'vertical',
        selectedMode: false
    },
    tooltip : {
        trigger: 'item',
        formatter: function(params){
            if (params.seriesName == '占比') {
                return '总量 : ' + total + '<br/>'
                       + params.name + ' : ' + params.value + '<br/>'
                       + '占比 : ' +  params.percent + '%';
            }
            else if (params.name != '占位'){
                update(params);
                return params.seriesName + '<br/>'
                       + params.name + ' : ' + params.value;
            }
        },
        axisPointer: {
            type: 'none'
        }
    },
    toolbox: {
        show : true,
        feature : {
            saveAsImage : {show: true}
        }
    },
    grid:{
        backgroundColor:'#ccc',
        borderWidth:0
    },
    xAxis : [
        {
            type : 'category',
            boundaryGap : false,
            show : false,
            data : ['占位','周一','周二','周三','周四','周五','周六','周日','占位']
        }
    ],
    yAxis : [
        {
            type : 'value',
            boundaryGap:[0,0.5],
            show : false
        }
    ],
    animation: false,
    series : [
        {
            name:'销量',
            type:'line',
            symbol: 'emptyCircle',
            symbolSize: 6,
            showAllSymbol:true,
            smooth:true,
            itemStyle: {normal: {areaStyle: {type: 'default'}}},
            data: dataArray
        },
        {
            name:'遮罩',
            type:'pie',
            clickable:false,
            tooltip: {show:false},
            radius : [100, 180],
            itemStyle: {
                normal: {color: '#fff',label:{show:false},labelLine:{show:false}},
                emphasis: {color:'rgba(0,0,0,0)'}
            },
            data:[
              {value:100, name:'直接访问'}
            ]
        },
        {
            name:'占比',
            type:'pie',
            clickable: false,
            clockWise: true,
            radius : [110, 125],
            data:[
              {
                  itemStyle: {normal: {
                      label:{
                          position:'inside',
                          formatter: '\n{b} : {c}\n\n( {d}% )',
                          textStyle: {
                              fontSize: 15,
                              baseline: 'top',
                              color: '#1e90ff'
                          }
                      },
                      labelLine:{show:false}
                  }}
              },
              {
                  name:'其他',
                  tooltip: {show:false},
                  itemStyle: {normal: {color: '#fff',label:{show:false},labelLine:{show:false}}}
              }
            ]
        }
    ]
};
function changePieSeries(params) {
    var curData = params.value - gap;
    option.series[2].startAngle = -90 + (curData / total * 360) / 2;
    option.series[2].data[0].name = params.name;
    option.series[2].data[0].value = curData;
    option.series[2].data[1].value = total - curData;
    
    for (var i = 1, l = option.series[0].data.length - 1; i < l; i++) {
        if (option.series[0].data[i].symbol) {
            option.series[0].data[i].symbol = 'emptyCircle';
            option.series[0].data[i].symbolSize = 6;
        }
    }
    option.series[0].data[params.dataIndex] = {
        name : params.name,
        value : params.value,
        symbol: 'emptyDiamond',
        symbolSize: 10
    }
}
function update(params){
    changePieSeries(params);
    option.animation = true;
    myChart.setOption(option);
}
changePieSeries({
    name : option.xAxis[0].data[maxIndex + 1],
    value : option.series[0].data[maxIndex + 1],
    dataIndex: maxIndex + 1
});

                           
			                      
				                
		 	 
        
	         $('#mix12').css({width:'800px',height:'320px'});
	         var myChart = ec.init($('#mix12')[0]);
	         myChart.setOption(option) ;
		 		

			
 
		 	
		 	 
		}
	}
}); 

 