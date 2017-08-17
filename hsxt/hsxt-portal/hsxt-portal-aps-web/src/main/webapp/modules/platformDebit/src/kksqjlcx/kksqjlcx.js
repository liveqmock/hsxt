define(['text!platformDebitTpl/kksqjlcx/kksqjlcx.html',
		'text!platformDebitTpl/kksqjlcx/kksqjlcx_ck.html',
        'platformDebitDat/platformDebit',
        'platformDebitLan'
		], function(kksqjlcxTpl, kksqjlcx_ckTpl,platformDebit){
	var kksqjlcxFun = {
		showPage : function(){
			$('#busibox').html(kksqjlcxTpl);
			
			//分页查询
			$("#bjglqry_btn").click(function(){
				if(!comm.queryDateVaild("search_form").form()){
					return;
				}
				
				kksqjlcxFun.pageQuery();
			});
			
			//初始化文本值
			comm.initSelect("#txtQueryState",comm.lang("platformDebit").deductionStatusEnum);
			comm.initSelect("#quickDate",comm.lang("platformDebit").quickDateEnum);
			comm.initBeginEndTime("#search_startDate","#search_endDate");
			
			//快捷选择日期事件
			$("#quickDate").bind("change", function () {
				var method = {
					'today' : 'getTodaySE',
					'week' : 'getWeekSE',
					'month' : 'getMonthSE',
					'month3' : 'get3MonthSE'
				}[$(this).attr("optionvalue")];
				var arr = method ? comm[method]() : ['', ''];
				$("#search_startDate").val(arr[0]);
				$("#search_endDate").val(arr[1]);
			});
		},
		/** 查看 */
		chaKan : function(obj){
			$('#kksqjlcx_ck_dlg').html(kksqjlcx_ckTpl).dialog({
				title : '查看详情',
				width : 700,
				height: 400,
				modal : true,
				closeIcon : true,
				buttons : {
					'关闭' : function(){
						$(this).dialog('destroy');	
					}
				}	
			});
			
			$('#sqrq_txt').text(obj.applyDate);
			$('#hsh_txt').text(obj.entResNo);
			$('#dwmc_txt').text(obj.entCustName!=null ? obj.entCustName : "");
			$('#kkje_txt').text(comm.formatMoneyNumber(obj.amount));
			$('#kkrq_txt').text(comm.navNull(obj.apprDate));
			$('#zt_txt').text(comm.lang("platformDebit").deductionStatusEnum[obj.status]);
			$('#kkyy_txt').html(obj.applyRemark);
			
			if(obj.status!=0){
				$("#divApprInfo,#tabApprInfo").show();
				$("#spjg_txt").text(comm.lang("platformDebit").apprStatusEnum[obj.status]);
				$('#sprq_txt').text(obj.apprDate);
				$('#spczy_txt').text(obj.apprName);
				$('#spbz_txt').text(comm.navNull(obj.apprRemark));
			}else{
				$("#divApprInfo,#tabApprInfo").hide();
			}
			
		},
		/** 分页查询 */
		pageQuery:function(){
			
			//查询参数
			var queryParam = {
				"search_queryStartDate" : $("#search_startDate").val(),
				"search_queryEndDate" : $("#search_endDate").val(),
				"search_queryResNo" : $("#txtQueryResNo").val(),
				"search_queryStatus" : $("#txtQueryState").attr("optionvalue")
			};
			
			platformDebit.hsbDeductionPage("searchTable", queryParam, function(record, rowIndex, colIndex, options){
				//状态
				if(colIndex==3){
					return comm.formatMoneyNumber(record["amount"]);
				}
				if(colIndex==5){
					var text = $("<p>"+record["applyRemark"]+"</p>").text();
					return "<span title="+text+">"+text+"</span>";
				}
				//状态
				if(colIndex==6){
					return comm.lang("platformDebit").deductionStatusEnum[record["status"]];
				}
				
				if(colIndex==7){
					var link1 = $('<a>查看</a>').click(function(e) {
						kksqjlcxFun.chaKan(record);
					});
					return link1;
				}
			});
		}
	}	
	return kksqjlcxFun;
});
