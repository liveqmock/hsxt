define([
"text!mytaskTpl/wddbrwlb/wddbrwlb.html",
"text!mytaskTpl/wddbrwlb/wddbrwlb_chakang.html",

],function(wddbrwlbTpl,wddbrwlb_chakangTpl){
 
	var wddbrwlb = {
		show:function(){
			//加载中间内容 
			$(".operationsArea").html(wddbrwlbTpl).append(wddbrwlb_chakangTpl);	
			
			var gridObj;
			gridObj = $.fn.bsgrid.init('wddbrwlbTable', {
				//url: 'http://bsgrid.coding.io/examples/grid/data/json.jsp',
				url : comm.domainList['local']+comm.UrlList['wddbrwlb'],
				// autoLoad: false,
				pageSizeSelect: true ,
				pageSize: 5 ,
				stripeRows: true,  //行色彩分隔
				//pagingLittleToolbar: true,  //精减分页
				pageAll: false ,    //在一页显示所有行
				displayBlankRows: false ,   //显示空白行
				//行选择或取消事件
				/*
				event: { 
					selectRowEvent: function (record, rowIndex, trObj, options) {
						alert('Select Row ' + rowIndex + ' Event, ID=' + gridObj.getRecordIndexValue(record, 'ID'));
					},
					unselectRowEvent: function (record, rowIndex, trObj, options) {
						alert('UnSelect Row ' + rowIndex + ' Event, ID=' + gridObj.getRecordIndexValue(record, 'ID'));
					}
				} ,
				*/
				extend: {
					settings: {
						supportGridEdit: true, // default false, if support extend grid edit
						//supportGridEditTriggerEvent: 'rowClick' // default 'rowClick', values: ''(means no need Trigger), 'rowClick', 'rowDoubleClick', 'cellClick', 'cellDoubleClick'
					}
				},
				
				operate : {	
					detail : function(record, rowIndex, colIndex, options){
						var link1 =  $('<a id="'+ gridObj.getRecordIndexValue(record, 'ID') +'" >查看</a>').click(function(e) {
							//$('#dialog').dialog({autoOpen: true }) ;
							alert(gridObj.getRecordIndexValue(record, 'ID'));
						});
						return   link1 ;
					}/*,
					
					add : function(record, rowIndex, colIndex, options){			 
						var link1 =  $('<a id="'+ gridObj.getRecordIndexValue(record, 'ID') +'" >加</a>').click(function(e) {
							//$('#dialog').dialog({autoOpen: true }) ;
							comm.alert(gridObj.getRecordIndexValue(record, 'ID'));
						});
						return link1 ;
					} ,
					del : function(record, rowIndex, colIndex, options){
						var link2 =  $('<a index="'+ gridObj.getRecordIndexValue(record, 'ID') +'" >删</a>').click(function(e) {							 
							var confirmObj = {	
								content : '您真的要删除'+gridObj.getColumnValue(rowIndex , 'CHAR')+'吗？',															
								callOk :function(){
									
									//gridObj.deleteRow( gridObj.getSelectedRowIndex() );
								}
							 }
							 comm.confirm(confirmObj); 
							
						}); 						
						return   link2  ;
					} ,
					edit: function(record, rowIndex, colIndex, options){
						var link2 =  $('<a index="'+ gridObj.getRecordIndexValue(record, 'ID') +'" >改</a>').click(function(e) {							 
							//$('#dialog').dialog({autoOpen: true }) ;
							comm.alert(gridObj.getRecordIndexValue(record, 'ID'));
						}); 						
						return   link2  ;
					}*/
					
				},
				renderImg :  function(record, rowIndex, colIndex, options){	
					var idInt = parseInt($.trim(gridObj.getRecordIndexValue(record, 'ID')));
        			return '<img src="http://bsgrid.coding.io/examples/images/' + ((idInt % 3) == 0 ? 3 : (idInt % 3)) + '.jpg" width="64px" />';
				}	
				
			}); 
			
			//下拉列表事件
			$("#quickDate").selectList({
				options:[
					{name:'今天',value:'100'},
					{name:'本周',value:'101'},
					{name:'本月',value:'102'}
				]
			}); 
			$("#businessType").selectList({
				options:[
					{name:'xxx',value:'001'},
					{name:'xxx',value:'002'},
					{name:'xxx',value:'003'}
				]	
			});
			$("#businessDescription").placeholder();
			
			$("#timeRange_1").datepicker({dateFormat:'yy-mm-dd'});
			$("#timeRange_2").datepicker({dateFormat:'yy-mm-dd'});
			
			//end
			
			//按钮事件
			$(".detailsView_btn").click(function(){
				$("#wddbrwlb").addClass("none");
				
				$("#wddbrwlb_chakang").removeClass("none");	
			});
			
			$(".back_wddbrwlb").click(function(){
				$("#wddbrwlb_chakang").addClass("none");
				
				$("#wddbrwlb").removeClass("none");	
			});
			
				
		},
		hide:function(){
			//清空中间内容） 
			$(".operationsArea").empty();	
		}
		
	};
	
		
	return wddbrwlb;
	 

});