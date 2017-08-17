define(['text!platformDebitTpl/kkcxfh/kkcxfh.html',
		'text!platformDebitTpl/kkcxfh/kkcxfh_ck.html',
		'text!platformDebitTpl/kkcxfh/kkcxfh_fh.html'
		], function(kkcxfhTpl, kkcxfh_ckTpl, kkcxfh_fhTpl){
	return {
		showPage : function(){
			$('#busibox').html(kkcxfhTpl);	
			
			var self = this;
			
			/*下拉列表*/
			$("#quickDate").selectList({
				options:[
					{name:'近一日',value:'1'},
					{name:'近一周',value:'2'},
					{name:'近一月',value:'3'},
					{name:'近三月',value:'4'}
				]
			});
			
			/*end*/
			
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
			
			/*表格数据模拟*/
			var json = [{
							"th_1":"2016-02-12 12:12:12",
							"th_2":"05111662122",
							"th_3":"张好古",
							"th_4":"100",
							"th_5":"2016-02-12 12:12:12",
							"th_6":"长得太挫了！",
							"th_7":"待扣款"
						},
						{
							"th_1":"2016-02-12 12:12:12",
							"th_2":"05111662122",
							"th_3":"张好古",
							"th_4":"100",
							"th_5":"2016-02-12 12:12:12",
							"th_6":"长得太挫了！",
							"th_7":"待扣款"
						},
						{
							"th_1":"2016-02-12 12:12:12",
							"th_2":"05111662122",
							"th_3":"张好古",
							"th_4":"100",
							"th_5":"2016-02-12 12:12:12",
							"th_6":"长得太挫了！",
							"th_7":"待扣款"
						},
						{
							"th_1":"2016-02-12 12:12:12",
							"th_2":"05111662122",
							"th_3":"张好古",
							"th_4":"100",
							"th_5":"2016-02-12 12:12:12",
							"th_6":"长得太挫了！",
							"th_7":"待扣款"
						},
						{
							"th_1":"2016-02-12 12:12:12",
							"th_2":"05111662122",
							"th_3":"张好古",
							"th_4":"100",
							"th_5":"2016-02-12 12:12:12",
							"th_6":"长得太挫了！",
							"th_7":"待扣款"
						}];	
			
			var gridObj = $.fn.bsgrid.init('searchTable', {				 
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
							
							this.chaKan(record);
							
						}.bind(this) ) ;
						
						return link1;
					}.bind(this),
					
					edit : function(record, reowIndex, colIndex, options){
						var link1 = $('<a>复核</a>').click(function(e) {
							
							this.fuhe(record);
							
						}.bind(this) ) ;
						
						return link1;
					}.bind(this)
				}
				
			});
			
			/*end*/	
			
		},
		chaKan : function(obj){
			$('#kkcxfh_ck_dlg').html(kkcxfh_ckTpl).dialog({
				title : '查看详情',
				width : 900,
				height: 600,
				modal : true,
				closeIcon : true,
				buttons : {
					'关闭' : function(){
						$(this).dialog('destroy');	
					}
				}	
			});
			
			$('#sqrq_txt').text(obj.th_1);
			$('#hsh_txt').text(obj.th_2);
			$('#dwmc_txt').text(obj.th_3);
			$('#kkje_txt').text(obj.th_4);
			$('#kkrq_txt').text(obj.th_5);
			$('#zt_txt').text(obj.th_7);
			$('#kkyy_txt').text(obj.th_6);
			
			$('#spjl_ck_btn').click(function(){
				$('#spjl_div')[$('#spjl_div').is(':hidden') ? 'removeClass' : 'addClass']('none');
			});
			
			/*表格数据模拟*/
			var json = [{
							"th_1":"2016-02-12 12:12:12",
							"th_2":"扣款申请",
							"th_3":"大飞",
							"th_4":"这个事情可以有"
						},
						{
							"th_1":"2016-02-12 12:12:12",
							"th_2":"扣款申请复核通过",
							"th_3":"太子",
							"th_4":"看起来还可以"
						},
						{
							"th_1":"2016-02-12 12:12:12",
							"th_2":"扣款申请复核驳回",
							"th_3":"大飞",
							"th_4":"这个事情可以有"
						},
						{
							"th_1":"2016-02-12 12:12:12",
							"th_2":"扣款成功",
							"th_3":"张好古",
							"th_4":"看起来还可以"
						},
						{
							"th_1":"2016-02-12 12:12:12",
							"th_2":"撤销申请",
							"th_3":"张好古",
							"th_4":"看起来还可以"
						},
						{
							"th_1":"2016-02-12 12:12:12",
							"th_2":"撤销申请复核通过",
							"th_3":"张好古",
							"th_4":"看起来还可以"
						},
						{
							"th_1":"2016-02-12 12:12:12",
							"th_2":"撤销申请复核驳回",
							"th_3":"张好古",
							"th_4":"看起来还可以"
						}
						];	
			
			var gridObj = $.fn.bsgrid.init('spjl_talbe', {				 
				//url : comm.domainList['local']+comm.UrlList['tgqycsyw'],
				// autoLoad: false,
				pageSizeSelect: true ,
				pageSize: 10 ,
				stripeRows: true,  //行色彩分隔 
				displayBlankRows: false ,   //显示空白行
				localData:json 
				
			});
			
			/*end*/	
			
		},
		fuhe : function(obj){
			$('#kkcxfh_fh_dlg').html(kkcxfh_fhTpl).dialog({
				title : '复核',
				width : 900,
				height: 500,
				modal : true,
				closeIcon : true,
				buttons : {
					'确定' : function(){
						$(this).dialog('destroy');	
					},
					'取消' : function(){
						$(this).dialog('destroy');	
					}
				}	
			});
			
			$('#fh_sqrq_txt').text(obj.th_1);
			$('#fh_hsh_txt').text(obj.th_2);
			$('#fh_dwmc_txt').text(obj.th_3);
			$('#fh_zt_txt').text(obj.th_7);	
			$('#fh_kkyy_txt').text(obj.th_6);
			$('#fh_cxsqrq_txt').text('');
			$('#fh_cxyy_txt').text('');
		}
	}	
});
