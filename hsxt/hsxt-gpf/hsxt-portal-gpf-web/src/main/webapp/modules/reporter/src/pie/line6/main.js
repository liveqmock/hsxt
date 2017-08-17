define(['echarts',    'echarts/chart/pie',],function(ec){
	return { 
		showPage : function(tabid){
	 	 
			//Todo...
		 	 
		 	 var dataStyle = {
				    normal: {
				        label: {show:false},
				        labelLine: {show:false}
				    }
				};
				var placeHolderStyle = {
				    normal : {
				        color: 'rgba(0,0,0,0)',
				        label: {show:false},
				        labelLine: {show:false}
				    },
				    emphasis : {
				        color: 'rgba(0,0,0,0)'
				    }
				};
				option = {
				    title: {
				        text: '你幸福吗？',
				        subtext: 'From ExcelHome',
				        sublink: 'http://e.weibo.com/1341556070/AhQXtjbqh',
				        x: 'center',
				        y: 'center',
				        itemGap: 20,
				        textStyle : {
				            color : 'rgba(30,144,255,0.8)',
				            fontFamily : '微软雅黑',
				            fontSize : 35,
				            fontWeight : 'bolder'
				        }
				    },
				    tooltip : {
				        show: true,
				        formatter: "{a} <br/>{b} : {c} ({d}%)"
				    },
				    legend: {
				        orient : 'vertical',
				        x : document.getElementById('main').offsetWidth / 2,
				        y : 45,
				        itemGap:12,
				        data:['68%的人表示过的不错','29%的人表示生活压力很大','3%的人表示“我姓曾”']
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
				    series : [
				        {
				            name:'1',
				            type:'pie',
				            clockWise:false,
				            radius : [125, 150],
				            itemStyle : dataStyle,
				            data:[
				                {
				                    value:68,
				                    name:'68%的人表示过的不错'
				                },
				                {
				                    value:32,
				                    name:'invisible',
				                    itemStyle : placeHolderStyle
				                }
				            ]
				        },
				        {
				            name:'2',
				            type:'pie',
				            clockWise:false,
				            radius : [100, 125],
				            itemStyle : dataStyle,
				            data:[
				                {
				                    value:29, 
				                    name:'29%的人表示生活压力很大'
				                },
				                {
				                    value:71,
				                    name:'invisible',
				                    itemStyle : placeHolderStyle
				                }
				            ]
				        },
				        {
				            name:'3',
				            type:'pie',
				            clockWise:false,
				            radius : [75, 100],
				            itemStyle : dataStyle,
				            data:[
				                {
				                    value:3, 
				                    name:'3%的人表示“我姓曾”'
				                },
				                {
				                    value:97,
				                    name:'invisible',
				                    itemStyle : placeHolderStyle
				                }
				            ]
				        }
				    ]
				};
				                    
                    
		 	 
        
	         $('#main-content > div[data-contentid="'+tabid+'"]').css({width:'800px',height:'320px'});
            var myChart = ec.init($('#main-content > div[data-contentid="'+tabid+'"]')[0]);
	         myChart.setOption(option) ;
		 		

			
	 
		 	
		 	 
		}
	}
}); 

 