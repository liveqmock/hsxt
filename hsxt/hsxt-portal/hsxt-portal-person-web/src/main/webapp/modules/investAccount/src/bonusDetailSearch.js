define(["text!investAccountTpl/bonusDetailSearch.html",
        "investAccountDat/investAccount"], function (tpl,dataModule) {
	var tzfhmxFun = {
		show : function(){
			//加载余额查询
			$("#myhs_zhgl_box").html(tpl);
			//加载年份
			tzfhmxFun.loadSelectYear($("#sshare_beginDate"),true);
			tzfhmxFun.loadSelectYear($("#sshare_endDate"),true);
			
			/** 年份下拉改变结束年份 */
			$("#sshare_beginDate").change(function(){
				 var getBeginYear=$(this).val(); //开始年份
			 	 if(getBeginYear!=""){
			 		 //开始年份大于结束年份时，重新加载结束下拉年份
			 		// if(getBeginYear<getEndYear){
			 		tzfhmxFun.loadSelectYear($("#sshare_endDate"), false, getBeginYear);
			 		// }
			 	 }else {
			 		//填充年份
			 		$("#sshare_endDate").html("");
			 		$("#sshare_endDate").append("<option value=\"\">--请选择--</option>");
			 	 }
			});
			
			//立即搜索单击事件
			$("#sshare_searchBtn").click(function () {
				var valid = tzfhmxFun.validateData();
				if (!valid.form()) {
					return;
				}
				//分页显示
				tzfhmxFun.pageQuery();
			});
			//tzfhmxFun.pageQuery();
		},
		
		/** 分页查询 */
		pageQuery:function(){
			var params = {search_beginDate :$("#sshare_beginDate").val(),search_endDate: $("#sshare_endDate").val()};
			dataModule.pointDividendPage('sshare_result_table',params,function(record, rowIndex, colIndex, options){
						return $('<a class="ec-set-icon ec-set-icon-see black Thick_btn fln" title="查看详情"></a>').click(function (e) {
							//查看详情点击事件
							tzfhmxFun.showDetail(record);
						});
			});
		},
		//查看详细
		showDetail : function(obj) {
			//详情统计信息
			dataModule.dividendDetailPageTotal({year: obj.dividendYear},function(res){
				var data=res.data;
				//加载头部数据
				$("#dividendInvestTotal_dividends").text(comm.formatMoneyNumber(data.dividendInvestTotal));
				$("#dividendCycle_dividends").text(data.dividendYear+"-01-01 ~ "+data.dividendYear+"-12-31");
				$("#currDividendsTotal_dividends").text(comm.formatMoneyNumber(data.totalDividend));
				$("#dividendRadio_dividends").text(data.yearDividendRate * 100 + "%");
			});
			//详情列表信息
			dataModule.dividendDetailPage('shareDividendsDetails',{search_year: obj.dividendYear}, tzfhmxFun.detail);
			//弹出窗体
			$("#dialogShareDividendsDetails").dialog({
				title : "投资分红明细-详情",
				width : "800",
				height : "510",
				modal : true,
				closeIcon : true
			});
		},
		detail:	function(record, rowIndex, colIndex, options){
			if(colIndex == 0){
				return comm.formatDate(record.investDate);
			}
			var data=record;
		},
		/** 加载下拉年份 */
		loadSelectYear:function(selectId,isStart,startYear){
			var count;
			var currentYear=new Date().getFullYear(); //获取当前年份
		
			//循环开始年份
			if(isStart){
				count=2010;
			}else {
				count=startYear;
			}
			
			//填充年份
			selectId.html("");
			selectId.append("<option value=\"\">--请选择--</option>");
			for(var i=count;i<=currentYear;i++){
				selectId.append("<option value=\""+i+"\">"+i+"</option>");
			}
		},
		validateData : function(){
			return $("#shareDetailSearch_form").validate({
				rules : {
					sshare_beginDate : {
						required : true
					},
					sshare_endDate : {
						required : true
					}
				},
				messages : {
					sshare_beginDate : {
						required : comm.lang("investAccount").beginYear,
					},
					sshare_endDate : {
						required : comm.lang("investAccount").endYear,
					}
				},
				errorPlacement : function (error, element) {
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
		}
	}
	return tzfhmxFun;
});