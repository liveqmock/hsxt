define(['text!accountManageTpl/jfyfkzh/mxcx/mxcx.html',
		'text!accountManageTpl/jfyfkzh/mxcx/hsbyj.html',
		'text!accountManageTpl/jfyfkzh/mxcx/wyyj.html',
		'text!accountManageTpl/jfyfkzh/mxcx/xfjfkc.html',
		'text!accountManageTpl/jfyfkzh/mxcx/xfjfcd.html',
		'text!accountManageTpl/jfyfkzh/mxcx/yfkth.html',
		'text!accountManageTpl/jfyfkzh/mxcx/yfkzc.html',
		'text!accountManageTpl/jfyfkzh/mxcx/sbyj.html',
		'text!accountManageTpl/jfyfkzh/mxcx/yfkpzzr.html',
		'text!accountManageTpl/jfyfkzh/mxcx/xxyj.html'
		], function(tpl, hsbyjTpl, wyyjTpl, xfjfkcTpl, xfjfcdTpl, yfkthTpl, yfkzcTpl, sbyjTpl, yfkpzzrTpl, xxyjTpl){
	return mxcx = {
		//互生币预缴
		hsbyjTpl : hsbyjTpl,
		//网银预缴
		wyyjTpl : wyyjTpl,
		//消费积分扣除
		xfjfkcTpl : xfjfkcTpl,
		//消费积分撤单
		xfjfcdTpl : xfjfcdTpl,
		//预付款退回
		yfkthTpl : yfkthTpl,
		//预付款转出
		yfkzcTpl : yfkzcTpl,
		//申报预缴
		sbyjTpl : sbyjTpl,
		//预付款平账转入
		yfkpzzrTpl : yfkpzzrTpl,
		//线下预缴
		xxyjTpl : xxyjTpl,
		showPage : function(){
			$('#busibox').html(tpl);
			
			
			searchTable = null;
			
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
				$("#scpoint_beginDate").val(arr[0]).tooltip().tooltip("destroy");
				$("#scpoint_endDate").val(arr[1]).tooltip().tooltip("destroy");
			}); 
			$("#scpoint_subject").selectList({
				options:[
					{name:'全部',value:'all'},
					{name:'收入',value:'income'},
					{name:'支出',value:'disbursement'}
				]	
			}).change(function(e){
				var v = $(this).attr('optionValue');
				
			});
			/*日期控件*/
			$("#scpoint_beginDate").datepicker({dateFormat:'yy-mm-dd'});
			$("#scpoint_endDate").datepicker({dateFormat:'yy-mm-dd'});
			
			//查询单击事件
			$('#scpoint_searchBtn').click(function(){
				if (!mxcx.validateData()) {
					return;
				}
				
				searchTable = $.fn.bsgrid.init('scpoint_result_table', {
					//url: '',
					localData: [{"serialNo":"W1111102156489645664894234","transDate":"2014-08-28 10:11:53","transType":"互生币预缴","transCode":"收入","transAmount":"194.20","balance":"194.20"},{"serialNo":"W1111102156489645664894234","transDate":"2014-08-28 10:11:53","transType":"网银预缴","transCode":"收入","transAmount":"194.20","balance":"194.20"},{"serialNo":"W1111102156489645664894234","transDate":"2014-08-28 10:11:53","transType":"消费积分扣除","transCode":"支出","transAmount":"194.20","balance":"194.20"},{"serialNo":"W1111102156489645664894234","transDate":"2014-08-28 10:11:53","transType":"消费积分撤单","transCode":"支出","transAmount":"194.20","balance":"194.20"},{"serialNo":"W1111102156489645664894234","transDate":"2014-08-28 10:11:53","transType":"消费积分扣除","transCode":"支出","transAmount":"194.20","balance":"194.20"},{"serialNo":"W1111102156489645664894234","transDate":"2014-08-28 10:11:53","transType":"消费积分撤单","transCode":"支出","transAmount":"194.20","balance":"194.20"},{"serialNo":"W1111102156489645664894234","transDate":"2014-08-28 10:11:53","transType":"预付款退回","transCode":"支出","transAmount":"194.20","balance":"194.20"},{"serialNo":"W1111102156489645664894234","transDate":"2014-08-28 10:11:53","transType":"预付款转出","transCode":"支出","transAmount":"194.20","balance":"194.20"},{"serialNo":"W1111102156489645664894234","transDate":"2014-08-28 10:11:53","transType":"预付款转出","transCode":"支出","transAmount":"194.20","balance":"194.20"},{"serialNo":"W1111102156489645664894234","transDate":"2014-08-28 10:11:53","transType":"申报预缴","transCode":"收入","transAmount":"194.20","balance":"194.20"},{"serialNo":"W1111102156489645664894234","transDate":"2014-08-28 10:11:53","transType":"预付款平账转入","transCode":"收入","transAmount":"194.20","balance":"194.20"},{"serialNo":"W1111102156489645664894234","transDate":"2014-08-28 10:11:53","transType":"线下预缴","transCode":"收入","transAmount":"194.20","balance":"194.20"}],
					otherParames: {
						beginDate : $("#scpoint_beginDate").val(),
						endDate : $("#scpoint_endDate").val(),
						subject : $("#scpoint_subject").attr('optionValue')
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
								//查看详情点击事件
								mxcx.showDetail(searchTable.getRecord(rowIndex));
							});
						}
					}
				});
			});
			//这一行需要去掉，此处用于显示数据
			//$('#scpoint_searchBtn').click();
		},
		validateData : function(){
			return comm.valid({
				left:10,
				top:20,
				formID : '#scpoint_form',
				rules : {
					scpoint_beginDate:{required:true},
					scpoint_endDate:{required:true},
					scpoint_subject:{required:true}
				},
				messages : {
					scpoint_beginDate:{required:'输入日期'},
					scpoint_endDate:{required:'输入日期'},
					scpoint_subject:{required:'请选择业务类别'}
				}
			});
		},
		showDetail : function(obj){
			var serialNo = obj.serialNo,
			transType = obj.transType,
			mxcx = this,
			//根据不同code显示不同的弹出框
			oData = {
				"互生币预缴" : {
					name : "互生币预缴",
					tpl : 'hsbyjTpl',
					datMethod : ''
				},
				"网银预缴" : {
					name : "网银预缴",
					tpl : 'wyyjTpl',
					datMethod : ''
				},
				"消费积分扣除" : {
					name : "消费积分扣除",
					tpl : 'xfjfkcTpl',
					datMethod : ''
				},
				"消费积分撤单" : {
					name : "消费积分撤单",
					tpl : 'xfjfcdTpl',
					datMethod : ''
				},
				"预付款退回" : {
					name : "预付款退回",
					tpl : 'yfkthTpl',
					datMethod : ''
				},
				"预付款转出" : {
					name : "预付款转出",
					tpl : 'yfkzcTpl',
					datMethod : ''
				},
				"申报预缴" : {
					name : "申报预缴",
					tpl : 'sbyjTpl',
					datMethod : ''
				},
				"预付款平账转入" : {
					name : "预付款平账转入",
					tpl : 'yfkpzzrTpl',
					datMethod : ''
				},
				"线下预缴" : {
					name : "线下预缴",
					tpl : 'xxyjTpl',
					datMethod : ''
				}
			}[transType];
			if(oData && !$.isEmptyObject(oData)){
				//datName[oData.datMethod]({serialNo: serialNo}, function(response){
					//$('#jfyfkzh-mxcx-dialog > p').html(_.template(mxcx[oData.tpl], response));
					$('#jfyfkzh-mxcx-dialog > p').html(_.template(mxcx[oData.tpl]));
					$('#jfyfkzh-mxcx-dialog').dialog({
						title : (oData.name || '') + "详情",
						open: function (event, ui) {
							$(".ui-dialog-titlebar-close", $(this).parent()).show();
						},
						width : oData.width || 400
					});
				//});
			}
		}
	};
});