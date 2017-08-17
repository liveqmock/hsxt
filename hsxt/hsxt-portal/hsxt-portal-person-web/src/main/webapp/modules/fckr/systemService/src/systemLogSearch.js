define(["text!fckr_systemServiceTpl/systemLogSearch.html",
        'fckr_systemServiceLan'], function (tpl) {
	var systemLogSearch = {
		_dataModule : null,
		show : function(dataModule){
			_dataModule = dataModule;
			this.initForm();
		},
		/**
		 * 初始化界面
		 */
		initForm : function(){
			var self = this;
			$("#myhs_zhgl_box").html(tpl);
			$("#search_startDate").datepicker({dateFormat:'yy-mm-dd'});
			$("#search_endDate").datepicker({dateFormat:'yy-mm-dd'});
			$("#search_startDate").val(comm.getCurrDate());
			$("#search_endDate").val(comm.getCurrDate());
			//快捷选择日期事件
			$("#quickDate").bind("change", function () {
				var method = {
					'today' : 'getTodaySE',
					'week' : 'getWeekSE',
					'month' : 'getMonthSE',
					'3month' : 'get3MonthSE',
					'6month' : 'get6MonthSE',
					'1year' : 'get1YearsSE'
				}[$(this).val()];
				var arr = method ? comm[method]() : ['', ''];
				$("#search_startDate").val(arr[0]);
				$("#search_endDate").val(arr[1]);
			});
			$('#queryBtn').click(function(){
				self.initData();
			});
			//self.initData();
		},
		/**
		 * 初始化数据
		 */
		initData : function(){
			if(!this.validateData().form()){
				return;
			}
			var jsonParam = {
					search_startDate:$("#search_startDate").val(),
					search_endDate:$("#search_endDate").val(),
					search_methodName:$("#search_methodName").val(),
				};
			_dataModule.findSystemLogList(jsonParam, this.detail);
		},
		/**
		 * 数据校验
		 */
		validateData : function(){
			return $("#systemLogSearch_form").validate({
				rules : {
					search_startDate : {
						required : true,
						date : true,
						endDate : "#search_endDate"
					},
					search_endDate : {
						required : true,
						date : true
					}
				},
				messages : {
					search_startDate : {
						required : comm.lang("systemService").systemLogSearch.beginDateRequired,
						date : comm.lang("systemService").systemLogSearch.beginDateError,
						endDate : comm.lang("systemService").systemLogSearch.beginDateEnd
					},
					search_endDate : {
						required : comm.lang("systemService").systemLogSearch.endDateRequired,
						date : comm.lang("systemService").systemLogSearch.endDateError
					}
				},
				errorPlacement : function (error, element) {
					if ($(element).attr('name') == 'quickDate') {
						element = element.parent();
					}
					$(element).attr("title", $(error).text()).tooltip({
						tooltipClass: "ui-tooltip-error",
						destroyFlag : true,
						destroyTime : 3000,
						position : {
							my : "left+2 top+30",
							at : "left top"
						}
					}).tooltip("open");
					$(".ui-tooltip").css("max-width", "230px");
				},
				success : function (element) {
					if ($(element).attr('name') == 'quickDate') {
						$(element.parent()).tooltip();
						$(element.parent()).tooltip("destroy");
					} else {
						$(element).tooltip();
						$(element).tooltip("destroy");
					}
				}
			});
		},
		/**
		 * 处理业务结果
		 */
		detail : function(record, rowIndex, colIndex, options){
			var methodName = $("#search_methodName").val();
			if(colIndex == 2){
				return comm.getNameByEnumId(methodName, comm.lang("systemService").bsType);
			}else if(colIndex == 4){
				//渲染列:详情
				
				if((methodName == "findCardapplyList" || methodName == "findCardlossList") && comm.isNotEmpty(record.remark)){
					
					var remark = record.remark || "";
					return $('<a class="ec-set-icon ec-set-icon-see black Thick_btn fln" data-reason="' + remark + '" title="查看备注"></a>').click(function(e){
						//查看备注原因
						$("#dlg1_reason").html($(e.currentTarget).attr("data-reason"));
						$("#dlg1").dialog({
							title : "备注",
							modal : true,
							buttons : {
								"确定" : function () {
									$(this).dialog("close");
								}
							}
						});
					});
				}else{
					return '';
				}
				
			}
			if(methodName == "findTruenameregList"){//实名注册
				return comm.getNameByEnumId(record['bsResult'], comm.lang("systemService").regStatus);
			}else if(methodName == "findTruenameaugList"){//实名认证
				return comm.getNameByEnumId(record['bsResult'], comm.lang("systemService").tureNameStatus);
			}else if(methodName == "findImptinfochangeList"){//重要信息变更
				return comm.getNameByEnumId(record['bsResult'], comm.lang("systemService").chgStatus);
			}else if(methodName == "findCardapplyList"){//互生卡补办
				return comm.getNameByEnumId(record['bsResult'], comm.lang("systemService").orderStatus);
			}
		}
	};
	return systemLogSearch
});