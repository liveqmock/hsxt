define(['text!reporterTpl/map/map8/line.html'  ,'echarts',
                'echarts/chart/map',],function(lineTpl ,ec){
	return { 
		showPage : function(tabid){			
			$('#main-content > div[data-contentid="'+tabid+'"]').html(lineTpl) ;	 		 
			//Todo...
		 	 
		 	  var itemStyle = {
    normal:{label:{
        show:true,
        formatter:'{b}',
        textStyle: {fontSize: 20,fontWeight : 'bold'}
    }},
    emphasis:{label:{show:true}}
};
option = {
    title : {
        text : '2012 World GDP Top 8',
        subtext : 'from baike （Billion $）',
        sublink : 'http://baike.baidu.com/link?url=MyQikkoGI08hUfVmiB0g01ZJ6Wpyu18s8e6XdA3dwcI73-i0J7Ce32LuPNqfEWBj2rUcSfIHYIVI0aL8czbS_a',
        x : 'center'
    },
    tooltip : {
        trigger: 'item',
        showDelay: 0,
        transitionDuration: 0.2,
        formatter : function (params) {
            var value = params.value + '';
            value = value.replace(/(\d{1,3})(?=(?:\d{3})+(?!\d))/g, '$1,');
            return params.seriesName + '<br/>' + value + ' Billion';
        }
    },
    toolbox: {
        show : true,
        x: 'right',
        y: 'bottom',
        feature : {
            mark : {show: true},
            dataView : {show: true, readOnly: false},
            restore : {show: true},
            saveAsImage : {show: true}
        }
    },
    dataRange: {
        orient: 'horizontal',
        x : 'center',
        y: 'center',
        min: 2000,
        max: 16000,
        splitNumber: 0,            // 分割段数，默认为5
        text:['16,000B','2,000B'],  
        calculable : true,
        itemWidth:40,
        color: ['orangered','yellow','lightskyblue']
    },
    series : [
        {
            name: 'USA',
            type: 'map',
            mapType: 'world|United States of America',
            mapLocation: {x:'5%', y:'10%',width:'22%',height:'35%'},
            itemStyle: itemStyle,
            data:[
                {name : 'United States of America', value : 15684.7}
            ]
        },
        {
            name: 'China',
            type: 'map',
            mapType: 'world|China',
            mapLocation: {x:'30%', y:'10%',width:'22%',height:'35%'},
            itemStyle: itemStyle,
            itemStyle: itemStyle,
            data:[
                {name : 'China', value : 8227}
            ]
        },
        {
            name: 'Japan',
            type: 'map',
            mapType: 'world|Japan',
            mapLocation: {x:'55%', y:'10%',width:'22%',height:'35%'},
            itemStyle: itemStyle,
            data:[
                {name : 'Japan', value : 5963.9}
            ]
        },
        {
            name: 'Germany',
            type: 'map',
            mapType: 'world|Germany',
            mapLocation: {x:'76%', y:'10%',width:'22%',height:'35%'},
            itemStyle: itemStyle,
            data:[
                {name : 'Germany', value : 3400.5}
            ]
        },
        {
            name: 'France',
            type: 'map',
            mapType: 'world|France',
            mapLocation: {x:'5%', y:'60%',width:'22%',height:'35%'},
            itemStyle: itemStyle,
            data:[
                {name : 'France', value : 2608.6}
            ]
        },
        {
            name: 'United Kingdom',
            type: 'map',
            mapType: 'world|United Kingdom',
            mapLocation: {x:'33%', y:'60%',width:'22%',height:'35%'},
            itemStyle: itemStyle,
            data:[
                {name : 'United Kingdom', value : 2440.5}
            ]
        },
        {
            name: 'Brazil',
            type: 'map',
            mapType: 'world|Brazil',
            mapLocation: {x:'55%', y:'60%',width:'22%',height:'35%'},
            itemStyle: itemStyle,
            data:[
                {name : 'Brazil', value : 2395.9}
            ]
        },
        {
            name: 'Russia',
            type: 'map',
            mapType: 'world|Russia',
            mapLocation: {x:'76%', y:'70%',width:'22%',height:'35%'},
            itemStyle: itemStyle,
            data:[
                {name : 'Russia', value : 2021.9}
            ]
        }
    ]
};
                    
				                    
		 	 
        
	        $('#map8').css({width:'800px',height:'320px'});
	         var myChart = ec.init($('#map8')[0]);
	         myChart.setOption(option) ;
			 		

			
 
		 	
		 	 
		}
	}
}); 

 