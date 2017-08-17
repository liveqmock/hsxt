define([ 'text!reporterTpl/tree/tree4/line.html'  ,'echarts',
                'echarts/chart/tree',],function(lineTpl ,ec){
	return { 
		showPage : function(tabid){			
			$('#main-content > div[data-contentid="'+tabid+'"]').html(lineTpl) ;		 
			//Todo...
		 	 
		 	 option = {
    title : {
        text: '树图混搭',
        subtext: '多个树'
    },
    tooltip : {
        trigger: 'item',
        formatter: "{b}: {c}"
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
    calculable : false,

    series : [
        {
            name:'树图',
            type:'tree',
            orient: 'vertical',  // vertical horizontal
            rootLocation: {x: '25%' ,y: 50}, // 根节点位置  {x: 100, y: 'center'}
            nodePadding: 1,
            itemStyle: {
                normal: {
                    label: {
                        show: false,
                        formatter: "{b}"
                    },
                    lineStyle: {
                        color: '#48b',
                        shadowColor: '#000',
                        shadowBlur: 3,
                        shadowOffsetX: 3,
                        shadowOffsetY: 5,
                        type: 'curve' // 'curve'|'broken'|'solid'|'dotted'|'dashed'

                    }
                },
                emphasis: {
                    label: {
                        show: true
                    }
                }
            },
            
            data: [
                {
                    name: '根节点',
                    value: 6,
                    children: [
                        {
                            name: '节点1',
                            value: 4,
                            children: [
                                {
                                    name: '叶子节点1',
                                    value: 4
                                },
                                {
                                    name: '叶子节点2',
                                    value: 4
                                },
                                {
                                    name: '叶子节点3',
                                    value: 2
                                },
                                 {
                                    name: '叶子节点4',
                                    value: 2
                                },
                                {
                                    name: '叶子节点5',
                                    value: 2
                                },
                                {
                                    name: '叶子节点6',
                                    value: 4
                                }
                            ]
                        },
                        {
                            name: '节点2',
                            value: 4,
                            children: [{
                                name: '叶子节点7',
                                value: 4
                            },
                            {
                                name: '叶子节点8',
                                value: 4
                            }]
                        },
                        {
                            name: '节点3',
                            value: 1,
                            children: [
                                {
                                    name: '叶子节点9',
                                    value: 4
                                },
                                {
                                    name: '叶子节点10',
                                    value: 4
                                },
                                {
                                    name: '叶子节点11',
                                    value: 2
                                },
                                 {
                                    name: '叶子节点12',
                                    value: 2
                                }
                            ]
                        }
                    ]
                }
            ]
        },
        {
            name:'树图',
            type:'tree',
            orient: 'horizontal',  // vertical horizontal
            rootLocation: {x: '45%', y: '60%'}, // 根节点位置  {x: 'center',y: 10}
            nodePadding: 20,
            symbol: 'circle',
            symbolSize: 40,
            itemStyle: {
                normal: {
                    label: {
                        show: true,
                        position: 'inside',
                        textStyle: {
                            color: '#cc9999',
                            fontSize: 15,
                            fontWeight:  'bolder'
                        }
                    },
                    lineStyle: {
                        color: '#000',
                        width: 1,
                        type: 'broken' // 'curve'|'broken'|'solid'|'dotted'|'dashed'
                    }
                },
                emphasis: {
                    label: {
                        show: true
                    }
                }
            },
            data: [
                {
                    name: '手机',
                    value: 6,
                    symbolSize: [90, 70],
                    symbol: 'image://http://www.iconpng.com/png/ecommerce-business/iphone.png',
                    itemStyle: {
                        normal: {
                            label: {
                                show: false
                            }
                        }
                    },
                    children: [
                        {
                            name: '小米',
                            value: 4,
                            symbol: 'image://http://pic.58pic.com/58pic/12/36/51/66d58PICMUV.jpg',
                            itemStyle: {
                                normal: {
                                    label: {
                                        show: false
                                    }
                                }
                            },
                            symbolSize: [60, 60],
                            children: [
                                {
                                    name: '小米1',
                                    symbol: 'circle',
                                    symbolSize: 20,
                                    value: 4,
                                    itemStyle: {
                                        normal: {
                                            color: '#fa6900',
                                            label: {
                                                show: true,
                                                position: 'right'
                                            },
                                            
                                        },
                                        emphasis: {
                                            label: {
                                                show: false
                                            },
                                            borderWidth: 0
                                        }
                                    }
                                },
                                {
                                    name: '小米2',
                                    value: 4,
                                    symbol: 'circle',
                                    symbolSize: 20,
                                    itemStyle: {
                                        normal: {
                                            label: {
                                                show: true,
                                                position: 'right',
                                                formatter: "{b}"
                                            },
                                            color: '#fa6900',
                                            borderWidth: 2,
                                            borderColor: '#cc66ff'

                                        },
                                        emphasis: {
                                            borderWidth: 0
                                        }
                                    }
                                },
                                {
                                    name: '小米3',
                                    value: 2,
                                    symbol: 'circle',
                                    symbolSize: 20,
                                    itemStyle: {
                                        normal: {
                                            label: {
                                                position: 'right'
                                            },
                                            color: '#fa6900',
                                            brushType: 'stroke',
                                            borderWidth: 1,
                                            borderColor: '#999966',
                                        },
                                        emphasis: {
                                            borderWidth: 0
                                        }
                                    }
                                }
                            ]
                        },
                        {
                            name: '苹果',
                            symbol: 'image://http://www.viastreaming.com/images/apple_logo2.png',
                            symbolSize: [60, 60],
                            itemStyle: {
                                normal: {
                                    label: {
                                        show: false
                                    }
                                    
                                }
                            },
                            value: 4
                        },
                        {
                            name: '华为',
                            symbol: 'image://http://market.huawei.com/hwgg/logo_cn/download/logo.jpg',
                            symbolSize: [60, 60],
                            itemStyle: {
                                normal: {
                                    label: {
                                        show: false
                                    }
                                    
                                }
                            },
                            value: 2
                        },
                        {
                            name: '联想',
                            symbol: 'image://http://www.lenovo.com.cn/HomeUpload/Home001/6d94ee9a20140714.jpg',
                            symbolSize: [100, 40],
                            itemStyle: {
                                normal: {
                                    label: {
                                        show: false
                                    }
                                    
                                }
                            },
                            value: 2
                        }
                    ]
                }
            ]
        }
    ]
};
                    
                     
		 	 
		 	 
        
	         $('#tree4').css({width:'800px',height:'320px'});
	         var myChart = ec.init($('#tree4')[0]);
	         myChart.setOption(option) ;
		 		

			
		 
		 	
		 	 
		}
	}
}); 

 