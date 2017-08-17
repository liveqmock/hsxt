define(['text!reporterTpl/force/force3/line.html'  ,'echarts',
                'echarts/chart/force',],function(lineTpl ,ec){
	return { 
		showPage : function(tabid){
			$('#main-content > div[data-contentid="'+tabid+'"]').html(lineTpl) ; 		 
			//Todo...
		 	
					 	
			var nodes = [];
			var links = [];
			var constMaxDepth = 2;
			var constMaxChildren = 7;
			var constMinChildren = 4;
			var constMaxRadius = 10;
			var constMinRadius = 2;
			
			function rangeRandom(min, max) {
			    return Math.random() * (max - min) + min;
			}
			
			function createRandomNode(depth, parentNode) {
			    var node = {
			        name : 'NODE_' + nodes.length,
			        value : rangeRandom(constMinRadius, constMaxRadius),
			        // Custom properties
			        id : nodes.length,
			        depth : depth,
			        category : depth === constMaxDepth ? 0 : 1,
			        __parentNode__ : parentNode ? parentNode.id : -1,
			        __children__ : [],
			        __collapsed__ : false
			    }
			    nodes.push(node);
			
			    return node;
			}
			
			function forceMockThreeData() {
			    var depth = 0;
			    var rootNode = createRandomNode(0);
			    rootNode.name = 'ROOT';
			    rootNode.category = 2;
			
			    function mock(parentNode, depth) {
			        var nChildren = Math.round(rangeRandom(constMinChildren, constMaxChildren));
			        
			        for (var i = 0; i < nChildren; i++) {
			            var childNode = createRandomNode(depth, parentNode);
			            links.push({
			                source : parentNode.id,
			                target : childNode.id,
			                weight : 1
			            });
			            parentNode.__children__.push(childNode.id);
			            if (depth < constMaxDepth) {
			                mock(childNode, depth + 1);
			            }
			        }
			    }
			
			    mock(rootNode, 0);
			}
			
			forceMockThreeData();
			
			option = {
			    title : {
			        text: 'Force',
			        subtext: 'Node collapse example',
			        x:'right',
			        y:'bottom'
			    },
			    tooltip : {
			        trigger: 'item',
			        formatter: '{a} : {b}'
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
			        data:['叶子节点','非叶子节点', '根节点']
			    },
			    series : [
			        {
			            type:'force',
			            name : "Force tree",
			            ribbonType: false,
			            categories : [
			                {
			                    name: '叶子节点'
			                },
			                {
			                    name: '非叶子节点'
			                },
			                {
			                    name: '根节点'
			                }
			            ],
			            itemStyle: {
			                normal: {
			                    label: {
			                        show: false
			                    },
			                    nodeStyle : {
			                        brushType : 'both',
			                        strokeColor : 'rgba(255,215,0,0.6)',
			                        lineWidth : 1
			                    }
			                }
			            },
			            minRadius : constMinRadius,
			            maxRadius : constMaxRadius,
			            coolDown: 0.995,
			            nodes : nodes,
			            links : links
			        }
			    ]
			};
			
			function isAscendant(node1, node2) {
			    var parent = nodes[node2.__parentNode__];
			    while(parent) {
			        if (parent.id === node1.id) {
			            return true;
			        }
			        parent = nodes[parent.__parentNode__];
			    }
			    return false;
			}
			
			function addChildrenToChart(node) {
			    for (var i = 0; i < node.__children__.length; i++) {
			        var childNode = nodes[node.__children__[i]];
			        childNode.ignore = false;
			
			        addChildrenToChart(childNode);
			    }
			}
			
			var ecConfig = require('echarts/config');
			function focus(param) {
			    var data = param.data;
			    if (
			        data.source !== undefined
			        && data.target !== undefined
			    ) {
			    } else { // 点击的是点
			        var targetNode = nodes[data.id];
			    
			        if (!targetNode.__collapsed__) {
			            option.series[0].nodes = nodes.map(function (node) {
			                node.ignore = isAscendant(data, node);
			                return node;
			            });
			        } else {
			            addChildrenToChart(targetNode);
			        }
			
			        targetNode.__collapsed__ = ! targetNode.__collapsed__;
			        myChart.setOption(option, true);
			    }
			}
			//myChart.on(ecConfig.EVENT.CLICK, focus);
			
			 
        
	        $('#forceReport3').css({width:'800px',height:'400px'});
	        var myChart = ec.init($('#forceReport3')[0]);
	        myChart.setOption(option) ;
		 		

		 
		 	
		 	 
		}
	}
}); 

 