define(['text!yufuTpl/jfyfkzhgl/yjjl.html',
		'text!yufuTpl/jfyfkzhgl/yjjl_ck_dialog.html'
		], function(yjjlTpl, yjjl_ck_dialogTpl){
	return {
		showPage : function(){
			$('#busibox').html(_.template(yjjlTpl));
			
			/*日期控件*/
			$("#timeRange_start").datepicker({
				dateFormat : 'yy-mm-dd',
				onSelect : function(dateTxt, inst){
					var d = dateTxt.replace('-', '/');
					$("#timeRange_end").datepicker('option', 'minDate', new Date(d));	
				}
			});
			$("#timeRange_end").datepicker({
				dateFormat : 'yy-mm-dd',
				onSelect : function(dateTxt, inst){
					var d = dateTxt.replace('-', '/');
					$("#timeRange_start").datepicker('option', 'maxDate', new Date(d));	
				}
			});
			/*end*/		
			
			/*下拉列表*/
			$("#statues").selectList({
				options:[
					{name:'全部',value:'1'},
					{name:'正常',value:'2'},
					{name:'无效',value:'3'}
				]
			});
			/*end*/
			
			/*表格数据模拟*/
			var json = [{
							"th_1":"05003240000",
							"th_2":"苏宁电器",
							"th_3":"张三",
							"th_4":"13888888888",
							"th_5":"2001",
							"th_6":"2015-08-16 19:25:00",
							"th_7":"2016-08-16 19:25:00",
							"th_8":"无效"
						},
						{
							"th_1":"05003240001",
							"th_2":"国美电器",
							"th_3":"李四",
							"th_4":"13899999999",
							"th_5":"2001",
							"th_6":"2015-08-16 19:25:00",
							"th_7":"2016-08-16 19:25:00",
							"th_8":"无效"
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
					}.bind(this)
				}
				
			});
			
			/*end*/		
			
		},
		chaKan : function(obj){
			$('#dialogBox').html(_.template(yjjl_ck_dialogTpl, obj));
			
			/*弹出框*/
			$( "#dialogBox" ).dialog({
				title:"预警管理详情",
				width:"800",
				modal:true,
				buttons:{ 
					"立即发送":function(){
						$( this ).dialog( "destroy" );
						comm.alert({
							imgClass : 'tips_yes',
							content : '预警信息已经成功发送至指定的手机！'
						});
					},
					"取消":function(){
						 $( this ).dialog( "destroy" );
					}
				}
			});
			/*end*/	
				
		}	
	}	
});