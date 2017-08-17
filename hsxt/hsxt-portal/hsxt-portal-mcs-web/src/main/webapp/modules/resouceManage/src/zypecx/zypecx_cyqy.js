define(['text!resouceManageTpl/zypecx/zypecx_cyqy.html',
		'text!resouceManageTpl/zypecx/cyqy_dialog.html'
		],function(zypecxTpl, cyqy_dialogTpl){
	return {
		 
		showPage : function(){
			$('#busibox').html(_.template(zypecxTpl)) ;			 
			//Todo...
		 	$('#zArea111').selectList();
			$('#zCity111').selectList();
			
			
			
			var testData =[
			{chengshi:'上海',renkou:123411,peier:111,yishiyong:10, zongshu:44 ,weifeipei:44,nifeipei:0 },
			{chengshi:'上海',renkou:123411,peier:111,yishiyong:10, zongshu:44 ,weifeipei:44,nifeipei:0 },
			{chengshi:'上海',renkou:123411,peier:111,yishiyong:10, zongshu:44 ,weifeipei:44,nifeipei:0 },
			{chengshi:'上海',renkou:123411,peier:111,yishiyong:10, zongshu:44 ,weifeipei:44,nifeipei:0 } ];
			
			var gridObj , self =this;
			 
			gridObj = $.fn.bsgrid.init('detailTable', {				 
				//url : comm.domainList['local']+ comm.UrlList["test1"] , 
				pageSize: 5 ,
				stripeRows: true,  //行色彩分 
				displayBlankRows: false ,   //显示空白行
			  	localData: testData,
				operate : {	
					detail : function(record, rowIndex, colIndex, options){					 
						var link1 =  null;
						if(colIndex == 2){
							link1 = $('<a>' + gridObj.getColumnValue(rowIndex, 'peier') + '</a>').click(function(e) {					 
								if(gridObj.getColumnValue(rowIndex, 'peier') != 0){
									self.chaKan();	
								}
							});	
						}
						else if(colIndex == 3){
							link1 = $('<a>' + gridObj.getColumnValue(rowIndex, 'yishiyong') + '</a>').click(function(e) {					 
								if(gridObj.getColumnValue(rowIndex, 'yishiyong') != 0){
									self.chaKan();	
								}
							});		
						}
						else if(colIndex == 4){
							link1 = $('<a>' + gridObj.getColumnValue(rowIndex, 'zongshu') + '</a>').click(function(e) {					 
								if(gridObj.getColumnValue(rowIndex, 'zongshu') != 0){
									self.chaKan();	
								}
							});		
						}
						else if(colIndex == 5){
							link1 = $('<a>' + gridObj.getColumnValue(rowIndex, 'weifeipei') + '</a>').click(function(e) {					 
								if(gridObj.getColumnValue(rowIndex, 'weifeipei') != 0){
									self.chaKan();	
								}
							});		
						}
						else if(colIndex == 6){
							link1 = $('<a>' + gridObj.getColumnValue(rowIndex, 'nifeipei') + '</a>').click(function(e) {					 
								if(gridObj.getColumnValue(rowIndex, 'nifeipei') != 0){
									self.chaKan();	
								}
							});		
						}
						return   link1 ;
					}  
				} 			
			});	
		},
		chaKan : function(){
			$('#cyqy_dialog').html(cyqy_dialogTpl);	
			
			/*表格数据模拟*/
			var json_cyqy_dialog = [{
							"th_1":"05116000000",
							"th_2":"浙江服务002",
							"th_3":"2015-09-09 09:25:00"
							},
							{
							"th_1":"05116000000",
							"th_2":"浙江服务002",
							"th_3":"2015-09-09 09:25:00"
							},
							{
							"th_1":"05116000000",
							"th_2":"浙江服务002",
							"th_3":"2015-09-09 09:25:00"
							},
							{
							"th_1":"05116000000",
							"th_2":"浙江服务002",
							"th_3":"2015-09-09 09:25:00"
							},
							{
							"th_1":"05116000000",
							"th_2":"浙江服务002",
							"th_3":"2015-09-09 09:25:00"
							},
							{
							"th_1":"05116000000",
							"th_2":"浙江服务002",
							"th_3":"2015-09-09 09:25:00"
							},
							{
							"th_1":"05116000000",
							"th_2":"浙江服务002",
							"th_3":"2015-09-09 09:25:00"
							},
							{
							"th_1":"05116000000",
							"th_2":"浙江服务002",
							"th_3":"2015-09-09 09:25:00"
							},
							{
							"th_1":"05116000000",
							"th_2":"浙江服务002",
							"th_3":"2015-09-09 09:25:00"
							},
							{
							"th_1":"05116000000",
							"th_2":"浙江服务002",
							"th_3":"2015-09-09 09:25:00"
							},
							{
							"th_1":"05116000000",
							"th_2":"浙江服务002",
							"th_3":"2015-09-09 09:25:00"
							},
							{
							"th_1":"05116000000",
							"th_2":"浙江服务002",
							"th_3":"2015-09-09 09:25:00"
							},
							{
							"th_1":"05116000000",
							"th_2":"浙江服务002",
							"th_3":"2015-09-09 09:25:00"
							},
							{
							"th_1":"05116000000",
							"th_2":"浙江服务002",
							"th_3":"2015-09-09 09:25:00"
							},
							{
							"th_1":"05116000000",
							"th_2":"浙江服务002",
							"th_3":"2015-09-09 09:25:00"
							},
							{
							"th_1":"05116000000",
							"th_2":"浙江服务002",
							"th_3":"2015-09-09 09:25:00"
							}];	
	
			var gridObj;
			 
			gridObj = $.fn.bsgrid.init('searchTable_cyqy_dialog', {				 
				//url : comm.domainList['local']+comm.UrlList['tgqycsyw'],
				// autoLoad: false,
				pageSizeSelect: true ,
				pageSize: 10 ,
				stripeRows: true,  //行色彩分隔 
				displayBlankRows: false ,   //显示空白行
				localData:json_cyqy_dialog
				
			});
			
			/*弹出框*/
			$( "#cyqy_dialog" ).dialog({
				title:"城市资源状态详情",
				width:"820",
				modal:true,
				buttons:{ 
					"确定":function(){
						$( this ).dialog( "destroy" );
					}
				}
			});
			/*end*/	
		}
	}
}); 

 