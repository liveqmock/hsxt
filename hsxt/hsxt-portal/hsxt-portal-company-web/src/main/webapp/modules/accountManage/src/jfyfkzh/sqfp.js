define(['text!accountManageTpl/jfyfkzh/sqfp/sqfp.html',
		'text!accountManageTpl/jfyfkzh/sqfp/sqfpmx.html'
		], function(tpl, mxTpl){
	return sqfp = {
		mxTpl : mxTpl,
		searchTable : null,
		showPage : function(){
			$('#busibox').html(tpl);
			
			
			//加载数据
			$("#ai_cpointPrepayTotal").text('10000.00');
			$("#ai_cpointTotal").text('5000.00');
			$("#ai_usedMoney").text('4000.00');
			$("#ai_issuedInvoice").text('1500.00');
			$("#ai_unIssuedInvoice").text('2500.00');
			//$("#ai_appleingInvoiceNum").text();
			$("#ai_backedMoney").text('1000.00');
			/*下拉列表*/
			$("#quickDate").selectList({
				options:[
					{name:'今天',value:'today'},
					{name:'最近一周',value:'week'},
					{name:'最近一月',value:'month'}
				]
			}).change(function(e){
				var method = {
					'today' : 'getTodaySE',
					'week' : 'getWeekSE',
					'month' : 'getMonthSE'
				}[$(this).attr('optionValue')];
				var arr = method ? comm[method]() : ['', ''];
				$("#ai_beginDate").val(arr[0]);
				$("#ai_endDate").val(arr[1]);
			}); 
			$("#ai_bBuyType").selectList({
				options:[
					{name:'申请中',value:'6'},
					{name:'已开发票',value:'2'},
					{name:'待签收',value:'7'},
					{name:'已签收',value:'4'}
				]	
			}).change(function(e){
				var v = $(this).attr('optionValue');
				
			});
			/*日期控件*/
			$("#ai_beginDate").datepicker({dateFormat:'yy-mm-dd'});
			$("#ai_endDate").datepicker({dateFormat:'yy-mm-dd'});
			
			//申请发票按钮事件
			$('#ai_applyBtn').click(function(){
				if (!sqfp.validateInvoiceMoney()) {
					return;
				}
				
				
			});
			
			//按钮事件
			$('#ai_searchInvoice').click(function(){
				if (!sqfp.validateData()) {
					return;
				}
				
				sqfp.searchTable = searchTable = $.fn.bsgrid.init('ai_result_table', {
					//url: '',
					localData: [{"id":"xx01","xh":1,"rq":"2014-05-16","je":"1,500.00","zt":"申请中"},{"id":"xx02","xh":2,"rq":"2014-05-16","je":"1,500.00","zt":"已开发票"},{"id":"xx03","xh":3,"rq":"2014-05-16","je":"1,500.00","zt":"待签收"},{"id":"xx04","xh":4,"rq":"2014-05-16","je":"1,500.00","zt":"已签收"}],
					otherParames: {
						beginDate : $("#ai_beginDate").val(),
						endDate : $("#ai_endDate").val(),
						subject : $("#ai_bBuyType").val()
					},
					//不显示空白行
					displayBlankRows: false,
					//不显示无页可翻的提示
					pageIncorrectTurnAlert: false,
					//隔行变色
					stripeRows: true,
					//不显示选中行背景色
					rowSelectedColor: false,
					pageSize: 10,
					operate: {
						add: function(record, rowIndex, colIndex, options){
							return $('<a title="查看详情">查看</a>').click(function(e){
								sqfp.showDetail(searchTable.getRecord(rowIndex));
							});
						},
						edit: function(record, rowIndex, colIndex, options){
							if (sqfp.searchTable.getRecordIndexValue(record, 'zt') == '待签收') {
								return $('<a class="ml10" title="签收发票">签收</a>').click(function(e){
									sqfp.checkin(searchTable.getRecord(rowIndex));
								});
							}
						}
					}
				});
			});
			//这一行需要去掉，此处用于显示数据
			$('#ai_searchInvoice').click();
		},
		validateData : function(){
			return comm.valid({
				formID : '#applyInvoice_form',
				rules : {
					
				},
				messages : {
					
				}
			});
		},
		validateInvoiceMoney : function(){
			return comm.valid({
				formID : '#ai_applyForm',
				rules : {
					
				},
				messages : {
					
				}
			});
		},
		showDetail : function(obj){
			var serialNo = obj.id,
			sqfp = this;
						
			$('#sqfpck-dialog>p').html(_.template(sqfp.mxTpl));
			
			var invoice_table = $.fn.bsgrid.init('ai_invoice_table', {
				//url: '',
				localData: [{"xh":1,"dh":"abc123124561","km":"41564646441","je":"1,000.00"},{"xh":2,"dh":"abc123124562","km":"41564646442","je":"1,000.00"},{"xh":3,"dh":"abc123124563","km":"41564646443","je":"1,000.00"},{"xh":4,"dh":"abc123124564","km":"41564646444","je":"1,000.00"},{"xh":5,"dh":"abc123124565","km":"41564646445","je":"1,000.00"}],
				otherParames: {
					serialNo : serialNo
				},
				//不显示空白行
				displayBlankRows: false,
				//不显示无页可翻的提示
				pageIncorrectTurnAlert: false,
				//隔行变色
				stripeRows: true,
				//不显示选中行背景色
				rowSelectedColor: false,
				pageSize: 5
			});
			$('#sqfpck-dialog').dialog({
				title : '申请发票详情',
				width : 800,
				open: function (event, ui) {
					$(".ui-dialog-titlebar-close", $(this).parent()).show();
				},
				modal:true,
				buttons: {
					"确定":function(){
						$(this).dialog("destroy");
					}
				}
			});
		},
		checkin : function(obj){
			var serialNo = obj.id,
			sqfp = this;
			
			$('#sqfpck-dialog>p').html(_.template(sqfp.mxTpl));
			
			var invoice_table = $.fn.bsgrid.init('ai_invoice_table', {
				//url: '',
				localData: [{"xh":1,"dh":"abc123124561","km":"41564646441","je":"1,000.00"},{"xh":2,"dh":"abc123124562","km":"41564646442","je":"1,000.00"},{"xh":3,"dh":"abc123124563","km":"41564646443","je":"1,000.00"},{"xh":4,"dh":"abc123124564","km":"41564646444","je":"1,000.00"},{"xh":5,"dh":"abc123124565","km":"41564646445","je":"1,000.00"}],
				otherParames: {
					serialNo : serialNo
				},
				//不显示空白行
				displayBlankRows: false,
				//不显示无页可翻的提示
				pageIncorrectTurnAlert: false,
				//隔行变色
				stripeRows: true,
				//不显示选中行背景色
				rowSelectedColor: false,
				pageSize: 5
			});
			
			$('#sqfpck-dialog').dialog({
				title : '发票签收',
				width : 800,
				open: function (event, ui) {
					$(".ui-dialog-titlebar-close", $(this).parent()).show();
				},
				modal:true,
				buttons: {
					"签收":function(){
						
						comm.alert({
							title:"发票签收",
							content: '发票签收成功！',
							callOk: function(){
								//刷新表格
								if(sqfp.searchTable)sqfp.searchTable.refreshPage();
							}
						});
						$(this).dialog("destroy");
					},
					"取消":function(){
						$(this).dialog("destroy");
					}
				}
			});
		}
	};
});