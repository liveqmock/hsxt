define(['text!reporterTpl/map/map2/line.html'  ,'echarts',
                'echarts/chart/map',],function(lineTpl ,ec){
	return { 
		showPage : function(tabid){			
			$('#main-content > div[data-contentid="'+tabid+'"]').html(lineTpl) ;	 		 
			//Todo...
		 	
		 	 option = {
    tooltip : {
        trigger: 'item',
        formatter: '{b}'
    },
    series : [
        {
            name: '中国',
            type: 'map',
            mapType: 'china',
            selectedMode : 'multiple',
            itemStyle:{
                normal:{label:{show:true}},
                emphasis:{label:{show:true}}
            },
            data:[
                {name:'广东',selected:true}
            ]
        }
    ]
};
var ecConfig = require('echarts/config');
/*
myChart.on(ecConfig.EVENT.MAP_SELECTED, function (param){
    var selected = param.selected;
    var str = '当前选择： ';
    for (var p in selected) {
        if (selected[p]) {
            str += p + ' ';
        }
    }
    document.getElementById('wrong-message').innerHTML = str;
})
                    */
        	
        	
	        $('#map2').css({width:'800px',height:'320px'});
	        var myChart = ec.init($('#map2')[0]);
	        myChart.setOption(option) ;			
		   	
		 	 
		}
	}
}); 

 