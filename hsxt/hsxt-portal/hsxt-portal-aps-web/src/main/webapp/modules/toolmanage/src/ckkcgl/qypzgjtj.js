define(['text!toolmanageTpl/ckkcgl/qypzgjtj.html',
		'text!toolmanageTpl/ckkcgl/qypzgjtj_posj_dialog.html',
		'text!toolmanageTpl/ckkcgl/qypzgjtj_jfskq_dialog.html',
		'text!toolmanageTpl/ckkcgl/qypzgjtj_xfskq_dialog.html',
		'text!toolmanageTpl/ckkcgl/qypzgjtj_hsk_dialog.html',
		'text!toolmanageTpl/ckkcgl/qypzgjtj_pad_dialog.html'
		], function(qypzgjtjTpl, qypzgjtj_posj_dialogTpl, qypzgjtj_jfskq_dialogTpl, 
			qypzgjtj_xfskq_dialogTpl, qypzgjtj_hsk_dialogTpl,qypzgjtj_pad_dialogTpl){
	return {
		showPage : function(){
			$('#infobox').html(_.template(qypzgjtjTpl));
				
			/*下拉列表*/
			$("#unitType").selectList({
				options:[
					{name:'托管企业',value:'1'},
					{name:'成员企业',value:'2'},
					{name:'消费者',value:'3'}
				]
			});
			/*end*/
			
			/*表格数据模拟*/
			var json = [{
							"th_1":"05001000000",
							"th_2":"归一科技",
							"th_3":"托管企业",
							"th_4":"100",
							"th_5":"50",
							"th_6":"50",
							"th_7":"10000" ,
							"th_8":"100"
						},
						{
							"th_1":"05001000000",
							"th_2":"美宜佳",
							"th_3":"成员企业",
							"th_4":"100",
							"th_5":"50",
							"th_6":"50",
							"th_7":"10000",
							"th_8":"100"
						},
						{
							"th_1":"05001000000",
							"th_2":"xxxxx",
							"th_3":"消费者",
							"th_4":"0",
							"th_5":"0",
							"th_6":"0",
							"th_7":"2",
							"th_8":"100"
						}];	
	
			var gridObj;
			
			gridObj = $.fn.bsgrid.init('searchTable', {				 
				pageSizeSelect: true ,
				pageSize: 10 ,
				stripeRows: true,  //行色彩分隔 
				displayBlankRows: false ,   //显示空白行
				localData:json,
				operate : {	
					detail : function(record, rowIndex, colIndex, options){
						var link1;
						if(colIndex == 3){
							link1 = $('<a>' + gridObj.getColumnValue(rowIndex, 'th_4') + '</a>').click(function(e) {
								if(gridObj.getColumnValue(rowIndex, 'th_4') != 0){
									var obj = gridObj.getRecord(rowIndex);
									this.chaKan_1(obj);
								}
							}.bind(this) ) ; 
						} else if(colIndex == 4){
							 link1  = $('<a>' + gridObj.getColumnValue(rowIndex, 'th_5') + '</a>').click(function(e) {
								if(gridObj.getColumnValue(rowIndex, 'th_5') != 0){
									var obj = gridObj.getRecord(rowIndex);
									this.chaKan_2(obj);
								}
							}.bind(this) ) ; 
						} else if(colIndex == 5){
							link1 = $('<a>' + gridObj.getColumnValue(rowIndex, 'th_6') + '</a>').click(function(e) {
								if(gridObj.getColumnValue(rowIndex, 'th_6') != 0){
									var obj = gridObj.getRecord(rowIndex);
									this.chaKan_3(obj);
								}
							}.bind(this) ) ; 
						} else if(colIndex == 6){
							link1 = $('<a>' + gridObj.getColumnValue(rowIndex, 'th_7') + '</a>').click(function(e) {
								if(gridObj.getColumnValue(rowIndex, 'th_7') != 0){
									var obj = gridObj.getRecord(rowIndex);
									this.chaKan_4(obj);
								}
							}.bind(this) ) ; 
						} else if(colIndex == 7){
							link1 = $('<a>' + gridObj.getColumnValue(rowIndex, 'th_8') + '</a>').click(function(e) {
								if(gridObj.getColumnValue(rowIndex, 'th_8') != 0){
									var obj = gridObj.getRecord(rowIndex);
									this.chaKan_8(obj);
								}
							}.bind(this) ) ; 
						}
						
						return link1;
					}.bind(this) 
				 
					 
					 
					
				}	
						
			});
			
			/*end*/	
		},
		chaKan_1 : function(obj){
			$('#dialogBox_posj').html(_.template(qypzgjtj_posj_dialogTpl, obj));
			
			/*弹出框*/
			$( "#dialogBox_posj" ).dialog({
				title:"POS机详情",
				width:"850",
				modal:true,
				buttons:{ 
					"确定":function(){
						$( this ).dialog( "destroy" );
					}
				}
			});
			/*end*/	
			
			/*表格数据模拟*/
			var json_posj = [{
							"th_1":"A1409191102",
							"th_2":"09191101",
							"th_3":"xxx",
							"th_4":"申报申购",
							"th_5":"2015-08-16 19:25:00"
						},
						{
							"th_1":"A1409191102",
							"th_2":"09191101",
							"th_3":"xxx",
							"th_4":"新增申购",
							"th_5":"2015-09-16 09:35:00"
						}];	
			
			gridObj = $.fn.bsgrid.init('searchTable_posj', {				 
				//url : comm.domainList['local']+comm.UrlList['tgqycsyw'],
				// autoLoad: false,
				pageSizeSelect: true ,
				pageSize: 10 ,
				stripeRows: true,  //行色彩分隔 
				displayBlankRows: false ,   //显示空白行
				localData:json_posj 
			});
			
			$('#searchTable_posj_pt').addClass('td_nobody_r_b');
			/*end*/	
				
		},
		chaKan_8 : function(obj){
			$('#dialogBox_posj').html(_.template(qypzgjtj_pad_dialogTpl, obj));
			
			/*弹出框*/
			$( "#dialogBox_posj" ).dialog({
				title:"互生平板详情",
				width:"850",
				modal:true,
				buttons:{ 
					"确定":function(){
						$( this ).dialog( "destroy" );
					}
				}
			});
			/*end*/	
			
			/*表格数据模拟*/
			var json_posj = [{
							"th_1":"A1409191102",
							"th_2":"09191101",
							"th_3":"xxx",
							"th_4":"申报申购",
							"th_5":"2015-08-16 19:25:00"
						},
						{
							"th_1":"A1409191102",
							"th_2":"09191101",
							"th_3":"xxx",
							"th_4":"新增申购",
							"th_5":"2015-09-16 09:35:00"
						}];	
			
			gridObj = $.fn.bsgrid.init('searchTable_posj', {				 
				//url : comm.domainList['local']+comm.UrlList['tgqycsyw'],
				// autoLoad: false,
				pageSizeSelect: true ,
				pageSize: 10 ,
				stripeRows: true,  //行色彩分隔 
				displayBlankRows: false ,   //显示空白行
				localData:json_posj 
			});
			
			$('#searchTable_posj_pt').addClass('td_nobody_r_b');
			/*end*/	
				
		},
		chaKan_2 : function(obj){
			$('#dialogBox_jfskq').html(_.template(qypzgjtj_jfskq_dialogTpl, obj));
			
			/*弹出框*/
			$( "#dialogBox_jfskq" ).dialog({
				title:"积分刷卡器详情",
				width:"850",
				modal:true,
				buttons:{ 
					"确定":function(){
						$( this ).dialog( "destroy" );
					}
				}
			});
			/*end*/	
			
			/*表格数据模拟*/
			var json_jfskq = [{
							"th_1":"A1409191102",
							"th_2":"09191101",
							"th_3":"xxx",
							"th_4":"申报申购",
							"th_5":"2015-08-16 19:25:00"
						},
						{
							"th_1":"A1409191102",
							"th_2":"09191101",
							"th_3":"xxx",
							"th_4":"新增申购",
							"th_5":"2015-09-16 09:35:00"
						}];	
			
			gridObj = $.fn.bsgrid.init('searchTable_jfskq', {				 
				//url : comm.domainList['local']+comm.UrlList['tgqycsyw'],
				// autoLoad: false,
				pageSizeSelect: true ,
				pageSize: 10 ,
				stripeRows: true,  //行色彩分隔 
				displayBlankRows: false ,   //显示空白行
				localData:json_jfskq 
			});
			
			$('#searchTable_jfskq_pt').addClass('td_nobody_r_b');
			/*end*/		
		},	
		chaKan_3 : function(obj){
			$('#dialogBox_xfskq').html(_.template(qypzgjtj_xfskq_dialogTpl, obj));
			
			/*弹出框*/
			$( "#dialogBox_xfskq" ).dialog({
				title:"消费刷卡器详情",
				width:"850",
				modal:true,
				buttons:{ 
					"确定":function(){
						$( this ).dialog( "destroy" );
					}
				}
			});
			/*end*/		
			
			/*表格数据模拟*/
			var json_xfskq = [{
							"th_1":"A1409191102",
							"th_2":"09191101",
							"th_3":"xxx",
							"th_4":"申报申购",
							"th_5":"2015-08-16 19:25:00"
						},
						{
							"th_1":"A1409191102",
							"th_2":"09191101",
							"th_3":"xxx",
							"th_4":"新增申购",
							"th_5":"2015-09-16 09:35:00"
						}];	
			
			gridObj = $.fn.bsgrid.init('searchTable_xfskq', {				 
				//url : comm.domainList['local']+comm.UrlList['tgqycsyw'],
				// autoLoad: false,
				pageSizeSelect: true ,
				pageSize: 10 ,
				stripeRows: true,  //行色彩分隔 
				displayBlankRows: false ,   //显示空白行
				localData:json_xfskq 
			});
			
			$('#searchTable_xfskq_pt').addClass('td_nobody_r_b');
			/*end*/	
		},	
		chaKan_4 : function(obj){
			$('#dialogBox_hsk').html(_.template(qypzgjtj_hsk_dialogTpl, obj));
			
			/*弹出框*/
			$( "#dialogBox_hsk" ).dialog({
				title:"互生卡详情",
				width:"850",
				modal:true,
				buttons:{ 
					"确定":function(){
						$( this ).dialog( "destroy" );
					}
				}
			});
			/*end*/	
			
			/*表格数据模拟*/
			var json_hsk_sb = [{
							"th_1":"01001010001 ~ 01001010999"
						},
						{
							"th_1":"01001010001 ~ 01001010999"
						},
						{
							"th_1":"01001010001 ~ 01001010999"
						},
						{
							"th_1":"01001010001 ~ 01001010999"
						},
						{
							"th_1":"01001010001 ~ 01001010999"
						}];	
			
			gridObj = $.fn.bsgrid.init('searchTable_hsk_sb', {				 
				//url : comm.domainList['local']+comm.UrlList['tgqycsyw'],
				// autoLoad: false,
				pageAll: true,
				/*pageSizeSelect: true ,
				pageSize: 10 ,*/
				stripeRows: true,  //行色彩分隔 
				displayBlankRows: false ,   //显示空白行
				localData:json_hsk_sb 
			});
			
			var json_hsk_xz = [{
							"th_1":"01001010001 ~ 01001010999"
						},
						{
							"th_1":"01001010001 ~ 01001010999"
						},
						{
							"th_1":"01001010001 ~ 01001010999"
						},
						{
							"th_1":"01001010001 ~ 01001010999"
						},
						{
							"th_1":"01001010001 ~ 01001010999"
						}];	
			
			gridObj = $.fn.bsgrid.init('searchTable_hsk_xz', {				 
				//url : comm.domainList['local']+comm.UrlList['tgqycsyw'],
				// autoLoad: false,
				pageAll: true,
				/*pageSizeSelect: true ,
				pageSize: 10 ,*/
				stripeRows: true,  //行色彩分隔 
				displayBlankRows: false ,   //显示空白行
				localData:json_hsk_xz 
			});
			
			$('#searchTable_hsk_sb_pt, #searchTable_hsk_xz_pt').addClass('td_nobody_r_b');
			comm.scrollTable('searchTable_hsk_sb');
			comm.scrollTable('searchTable_hsk_xz');
			
			/*end*/		
		}	
	}
});