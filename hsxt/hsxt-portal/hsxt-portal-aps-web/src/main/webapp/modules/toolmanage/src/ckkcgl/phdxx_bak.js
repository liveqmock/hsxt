define(['text!toolmanageTpl/ckkcgl/phdxx.html',
		'text!toolmanageTpl/ckkcgl/phdxx_tj_dialog.html',
		'text!toolmanageTpl/ckkcgl/phdxx_sz_dialog.html',
		'text!toolmanageTpl/ckkcgl/phdxx_xg_dialog.html',
		'text!toolmanageTpl/ckkcgl/phdxx_gl_dialog.html',
		'text!toolmanageTpl/ckkcgl/phdxx_ck_dialog.html'
		], function(phdxxTpl, phdxx_tj_dialogTpl, phdxx_sz_dialogTpl, phdxx_xg_dialogTpl, phdxx_gl_dialogTpl, phdxx_ck_dialogTpl){
	return {
		showPage : function(){
			$('#busibox').html(_.template(phdxxTpl));
			
			/*表格数据模拟*/
			var json = [{
							"th_1":"华南仓沿江路配货点",
							"th_2":"华南仓1",
							"th_3":"xxx",
							"th_4":"广东/福建/海南",
							"th_5":"张三",
							"th_6":"广东省广州市xxxxx路xxx号"
						}];	
	
			var gridObj;
			
			gridObj = $.fn.bsgrid.init('searchTable', {				 
				//url : comm.domainList['local']+comm.UrlList['tgqycsyw'],
				// autoLoad: false,
				pageSizeSelect: true ,
				pageSize: 10 ,
				stripeRows: true,  //行色彩分隔 
				displayBlankRows: false ,   //显示空白行
				localData:json ,
				operate : {	
					detail : function(record, rowIndex, colIndex, options){
						var link1 = $('<a>查看</a>').click(function(e) {
							var obj = gridObj.getRecord(rowIndex);
							this.chaKan(obj);
						}.bind(this) ) ;
						
						return link1;
					}.bind(this),
					
					edit : function(record, rowIndex, colIndex, options){
						var link2 = $('<a>修改</a>').click(function(e) {
							var obj = gridObj.getRecord(rowIndex);
							this.xiuGai(obj);
						}.bind(this) ) ;
						
						return link2;
					}.bind(this),
					
					del : function(record, rowIndex, colIndex, options){
						var link3 = $('<a>关联配货员</a>').click(function(e) {
							var obj = gridObj.getRecord(rowIndex);
							this.glphy(obj);
						}.bind(this) ) ;
						
						return link3;
					}.bind(this)
				}
				
			});
			
			/*end*/	
			
			$('#tjphd_btn').click(function(){
				$('#dialogBox_tj').html(_.template(phdxx_tj_dialogTpl));
				
				/*弹出框*/
				$( "#dialogBox_tj" ).dialog({
					title:"添加配货点",
					width:"1000",
					modal:true,
					buttons:{ 
						"确定":function(){
							$( this ).dialog( "destroy" );
						},
						"取消":function(){
							 $( this ).dialog( "destroy" );
						}
					}
				});
				/*end*/	
				
				/*下拉列表*/
				$("#warehouseName_tj").selectList({
					width:140,
					options:[
						{name:'xxx',value:'1'},
						{name:'xxx',value:'2'}
					]
				});
				
				$("#toolCategory_tj").selectList({
					options:[
						{name:'POS机',value:'1'},
						{name:'刷卡器',value:'2'},
						{name:'互生卡',value:'3'},
						{name:'...',value:'4'}
					]
				});
				
				$("#area_tj").selectList({
					options:[
						{name:'全国',value:'1'},
						{name:'北京',value:'2'},
						{name:'上海',value:'3'}
					]
				});
				
				$("#area_2_tj").selectList({
					width:140,
					options:[
						{name:'中国-广东-深圳',value:'1'},
						{name:'中国-上海-上海',value:'2'},
						{name:'中国-北京-北京',value:'3'}
					]
				});
				
				$("#operator_tj").selectList({
					options:[
						{name:'0001（张三）',value:'1'},
						{name:'0002（李四）',value:'2'},
						{name:'0003（王五）',value:'3'},
						{name:'0004（赵六）',value:'4'}
					]
				});
				
				/*end*/
				
				/*表格数据模拟*/
				var json_toolCategory_tj = [{
								"th_1":"POS机"
							},
							{
								"th_1":"POS机"
							},
							{
								"th_1":"POS机"
							},
							{
								"th_1":"POS机"
							},
							{
								"th_1":"POS机"
							},
							{
								"th_1":"POS机"
							}];	
							
				// 在渲染表格数据后执行的方法
				/*$.fn.bsgrid.defaults.additionalAfterRenderGrid = function (parseSuccess, gridData, options) {
					
				};*/
				
				gridObj = $.fn.bsgrid.init('searchTable_toolCategory_tj', {				 
					//url : comm.domainList['local']+comm.UrlList['tgqycsyw'],
					// autoLoad: false,
					pageAll: true,
					/*pageSizeSelect: true ,
					pageSize: 10 ,*/
					stripeRows: true,  //行色彩分隔 
					displayBlankRows: false ,   //显示空白行
					localData:json_toolCategory_tj ,
					operate : {	
						detail : function(record, rowIndex, colIndex, options){
							var link1 = $('<a>删除</a>').click(function(e) {
								//var obj = gridObj.getRecord(rowIndex);
								
								
							}.bind(this) ) ;
							
							return link1;
						}.bind(this)
					}
					
				});
				
				comm.scrollTable('searchTable_toolCategory_tj');			
				
				var json_area_tj = [{
								"th_1":"北京"
							},
							{
								"th_1":"上海"
							},
							{
								"th_1":"深圳"
							},
							{
								"th_1":"广州"
							}];	
				
				gridObj = $.fn.bsgrid.init('searchTable_area_tj', {				 
					//url : comm.domainList['local']+comm.UrlList['tgqycsyw'],
					// autoLoad: false,
					pageAll: true,
					/*pageSizeSelect: true ,
					pageSize: 10 ,*/
					stripeRows: true,  //行色彩分隔 
					displayBlankRows: false ,   //显示空白行
					localData:json_area_tj ,
					operate : {	
						detail : function(record, rowIndex, colIndex, options){
							var link1 = $('<a>删除</a>').click(function(e) {
								//var obj = gridObj.getRecord(rowIndex);
								
								
							}.bind(this) ) ;
							
							return link1;
						}.bind(this)
					}
					
				});
				
				comm.scrollTable('searchTable_area_tj');	
				
				var json_operator_tj = [{
								"th_1":"0001",
								"th_2":"张三"
							},
							{
								"th_1":"0002",
								"th_2":"李四"
							},
							{
								"th_1":"0003",
								"th_2":"王五"
							},
							{
								"th_1":"0004",
								"th_2":"赵六"
							}];	
				
				gridObj = $.fn.bsgrid.init('searchTable_operator_tj', {				 
					//url : comm.domainList['local']+comm.UrlList['tgqycsyw'],
					// autoLoad: false,
					pageSizeSelect: true ,
					pageSize: 10 ,
					stripeRows: true,  //行色彩分隔 
					displayBlankRows: false ,   //显示空白行
					localData:json_operator_tj ,
					operate : {	
						detail : function(record, rowIndex, colIndex, options){
							var link1 = $('<a>删除</a>').click(function(e) {
								//var obj = gridObj.getRecord(rowIndex);
								
								
							}.bind(this) ) ;
							
							return link1;
						}.bind(this)
					}
					
				});

				$('#searchTable_toolCategory_tj_pt, #searchTable_area_tj_pt, #searchTable_operator_tj_pt').addClass('td_nobody_r_b');
				
				/*end*/	
				
			});
			
			$('#zdqssjsz_btn').click(function(){
				$('#dialogBox_sz').html(_.template(phdxx_sz_dialogTpl));
				
				/*弹出框*/
				$( "#dialogBox_sz" ).dialog({
					title:"自动签收时间设置",
					width:"600",
					modal:true,
					buttons:{ 
						"确定":function(){
							$( this ).dialog( "destroy" );
						},
						"取消":function(){
							 $( this ).dialog( "destroy" );
						}
					}
				});
				/*end*/	
				
				/*日期控件*/
				$( "#autoTime" ).focus();
				$( "#autoTime" ).datepicker({dateFormat:'yy-mm-dd'});
				/*end*/
			
			});
				
		},
		chaKan : function(obj){
			$('#dialogBox_ck').html(_.template(phdxx_ck_dialogTpl, obj));	
			
			/*弹出框*/
			$( "#dialogBox_ck" ).dialog({
				title:"配货点详情",
				width:"800",
				modal:true,
				buttons:{ 
					"确定":function(){
						$( this ).dialog( "destroy" );
					}
				}
			});
			/*end*/	
				
		},
		xiuGai : function(obj){
			$('#dialogBox_xg').html(_.template(phdxx_xg_dialogTpl, obj));
				
				/*弹出框*/
				$( "#dialogBox_xg" ).dialog({
					title:"修改配货点",
					width:"1000",
					modal:true,
					buttons:{ 
						"确定":function(){
							$( this ).dialog( "destroy" );
						},
						"取消":function(){
							 $( this ).dialog( "destroy" );
						}
					}
				});
				/*end*/	
				
				/*下拉列表*/
				$("#warehouseName_xg").selectList({
					width:140,
					options:[
						{name:'xxx',value:'1', selected: true},
						{name:'xxx',value:'2'}
					]
				});
				
				$("#toolCategory_xg").selectList({
					options:[
						{name:'POS机',value:'1'},
						{name:'刷卡器',value:'2'},
						{name:'互生卡',value:'3'},
						{name:'...',value:'4'}
					]
				});
				
				$("#area_xg").selectList({
					options:[
						{name:'全国',value:'1'},
						{name:'北京',value:'2'},
						{name:'上海',value:'3'}
					]
				});
				
				$("#area_2_xg").selectList({
					width:140,
					options:[
						{name:'中国-广东-深圳',value:'1', selected: true},
						{name:'中国-上海-上海',value:'2'},
						{name:'中国-北京-北京',value:'3'}
					]
				});
				
				$("#operator_xg").selectList({
					options:[
						{name:'0001（张三）',value:'1'},
						{name:'0002（李四）',value:'2'},
						{name:'0003（王五）',value:'3'},
						{name:'0004（赵六）',value:'4'}
					]
				});
				
				/*end*/
				
				/*表格数据模拟*/
				var json_toolCategory_xg = [{
								"th_1":"POS机"
							},
							{
								"th_1":"POS机"
							},
							{
								"th_1":"POS机"
							},
							{
								"th_1":"POS机"
							},
							{
								"th_1":"POS机"
							},
							{
								"th_1":"POS机"
							}];	
							
				// 在渲染表格数据后执行的方法
				/*$.fn.bsgrid.defaults.additionalAfterRenderGrid = function (parseSuccess, gridData, options) {
					
				};*/
				
				gridObj = $.fn.bsgrid.init('searchTable_toolCategory_xg', {				 
					//url : comm.domainList['local']+comm.UrlList['tgqycsyw'],
					// autoLoad: false,
					pageAll: true,
					/*pageSizeSelect: true ,
					pageSize: 10 ,*/
					stripeRows: true,  //行色彩分隔 
					displayBlankRows: false ,   //显示空白行
					localData:json_toolCategory_xg ,
					operate : {	
						detail : function(record, rowIndex, colIndex, options){
							var link1 = $('<a>删除</a>').click(function(e) {
								//var obj = gridObj.getRecord(rowIndex);
								
								
							}.bind(this) ) ;
							
							return link1;
						}.bind(this)
					}
					
				});
				
				comm.scrollTable('searchTable_toolCategory_xg');			
				
				var json_area_xg = [{
								"th_1":"北京"
							},
							{
								"th_1":"上海"
							},
							{
								"th_1":"深圳"
							},
							{
								"th_1":"广州"
							}];	
				
				gridObj = $.fn.bsgrid.init('searchTable_area_xg', {				 
					//url : comm.domainList['local']+comm.UrlList['tgqycsyw'],
					// autoLoad: false,
					pageAll: true,
					/*pageSizeSelect: true ,
					pageSize: 10 ,*/
					stripeRows: true,  //行色彩分隔 
					displayBlankRows: false ,   //显示空白行
					localData:json_area_xg ,
					operate : {	
						detail : function(record, rowIndex, colIndex, options){
							var link1 = $('<a>删除</a>').click(function(e) {
								//var obj = gridObj.getRecord(rowIndex);
								
								
							}.bind(this) ) ;
							
							return link1;
						}.bind(this)
					}
					
				});
				
				comm.scrollTable('searchTable_area_xg');	
				
				var json_operator_xg = [{
								"th_1":"0001",
								"th_2":"张三"
							},
							{
								"th_1":"0002",
								"th_2":"李四"
							},
							{
								"th_1":"0003",
								"th_2":"王五"
							},
							{
								"th_1":"0004",
								"th_2":"赵六"
							}];	
				
				gridObj = $.fn.bsgrid.init('searchTable_operator_xg', {				 

					//url : comm.domainList['local']+comm.UrlList['tgqycsyw'],
					// autoLoad: false,
					pageSizeSelect: true ,
					pageSize: 10 ,
					stripeRows: true,  //行色彩分隔 
					displayBlankRows: false ,   //显示空白行
					localData:json_operator_xg ,
					operate : {	
						detail : function(record, rowIndex, colIndex, options){
							var link1 = $('<a>删除</a>').click(function(e) {
								//var obj = gridObj.getRecord(rowIndex);
								
								
							}.bind(this) ) ;
							
							return link1;
						}.bind(this)
					}
					
				});

				$('#searchTable_toolCategory_xg_pt, #searchTable_area_xg_pt, #searchTable_operator_xg_pt').addClass('td_nobody_r_b');
				
				/*end*/		
		},	
		glphy : function(obj){
			$('#dialogBox_gl').html(_.template(phdxx_gl_dialogTpl, obj));	
			
			/*弹出框*/
			$( "#dialogBox_gl" ).dialog({
				title:"关联配货员",
				width:"600",
				modal:true,
				buttons:{ 
					"确定":function(){
						$( this ).dialog( "destroy" );
					},
					"取消":function(){
						$( this ).dialog( "destroy" );
					}
				}
			});
			/*end*/	
			
			/*下拉列表*/
			$("#operator").selectList({
				optionHeight: 90,
				options:[
					{name:'0001（张三）',value:'1'},
					{name:'0002（李四）',value:'2'},
					{name:'0003（王五）',value:'3'},
					{name:'0004（赵六）',value:'4'}
				]
			});
			/*end*/	
			
			var json_operator_gl = [{
								"th_1":"0001",
								"th_2":"张三"
							},
							{
								"th_1":"0002",
								"th_2":"李四"
							},
							{
								"th_1":"0003",
								"th_2":"王五"
							},
							{
								"th_1":"0004",
								"th_2":"赵六"
							}];	
				
				gridObj = $.fn.bsgrid.init('searchTable_operator_gl', {				 

					//url : comm.domainList['local']+comm.UrlList['tgqycsyw'],
					// autoLoad: false,
					pageAll: true,
					/*pageSizeSelect: true ,
					pageSize: 10 ,*/
					stripeRows: true,  //行色彩分隔 
					displayBlankRows: false ,   //显示空白行
					localData:json_operator_gl ,
					operate : {	
						detail : function(record, rowIndex, colIndex, options){
							var link1 = $('<a>移除配货员</a>').click(function(e) {
								//var obj = gridObj.getRecord(rowIndex);
								
								
							}.bind(this) ) ;
							
							return link1;
						}.bind(this)
					}
					
				});
				
				$('#searchTable_operator_gl_pt').addClass('td_nobody_r_b');
				
				comm.scrollTable('searchTable_operator_gl');
				
		}
	}	
});
