define(["text!investAccountTpl/investDetailSearch.html"], function (tpl) {
	var detailSearch =  {
		searchTable:null,//积分明细查询表格结果
		_dataModule : null,
		show : function(dataModule){
			var self=this;
			detailSearch._dataModule = dataModule;
			
			//加载余额查询
			$("#myhs_zhgl_box").html(tpl);
			
			// 加载开始、结束日期
			$("#search_startDate, #search_endDate").datepicker({
				changeMonth : true,
				changeYear : true,
				dateFormat : "yy-mm-dd",
				maxDate : comm.getCurrDate()
			});
			
			//快捷选择日期事件
			$("#quickDate").bind("change", function () {
				var method = {
					'today' : 'getTodaySE',
					'week' : 'getWeekSE',
					'month' : 'getMonthSE'
				}[$(this).val()];
				var arr = method ? comm[method]() : ['', ''];
				$("#search_startDate").val(arr[0]);
				$("#search_endDate").val(arr[1]);
			});
			
			//立即搜索单击事件
			$("#sinvest_searchBtn").click(function () {
				var valid = comm.queryDateVaild("sinvest_form");
				if (!valid.form()) {
					return;
				}
				
				//首次加载
				self.pageQuery();
				
			});
		},
		validateData : function(){
			return $("#sinvest_form").validate({
				rules : {
					sinvest_beginDate : {
						required : true,
						date : true,
						endDate : "#sinvest_endDate"
					},
					sinvest_endDate : {
						required : true,
						date : true
					}
				},
				messages : {
					sinvest_beginDate : {
						required : comm.lang("investAccount").detailSearch.beginDateRequired,
						date : comm.lang("investAccount").detailSearch.beginDateError,
						endDate : comm.lang("investAccount").detailSearch.beginDateEnd
					},
					sinvest_endDate : {
						required : comm.lang("investAccount").detailSearch.endDateRequired,
						date : comm.lang("investAccount").detailSearch.endDateError
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
		/** 分页查询 */
		pageQuery:function(){
			var jsonData = {search_beginDate:$("#search_startDate").val(),search_endDate:$("#search_endDate").val()};
			detailSearch.searchTable=detailSearch._dataModule.pointInvestPage(jsonData,function(record, rowIndex, colIndex, options){
				//交易时间
				if(colIndex == 1){
					return comm.formatDate(record["investDate"],"yyyy-MM-dd");
				}
				
				//投资积分数
				if(colIndex==2){
					return comm.formatMoneyNumber(record["investAmount"]);
				}
				//交易后投资积分总数
				if(colIndex==3){
					return comm.formatMoneyNumber(record["accumulativeInvestCount"]); 
				}
			});
		}
	};
	return detailSearch
});
