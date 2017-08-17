define(["text!fckr_cashAccountTpl/detailSearch.html",
        "fckr_cashAccountDat/cashAccount",], function (tpl,dataModule) {
	var detailSearch =  {
			show : function(dataModule){
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
				//加载业务类型
				comm.initSelectOption($("#scash_subject"),comm.lang("cashAccount").businessTypeEnum);
				//立即搜索单击事件
				$("#scash_searchBtn").click(function () {
					var valid = comm.queryDateVaild("detailSearch_form");
					if (!valid.form()) {
						return;
					}
					//分页查询
					detailSearch.pageQuery();
				});
			},
			/** 分页查询 */ 
			pageQuery:function(){
				var jsonData = {
						"search_businessType":comm.navNull($("#scash_subject").val()),
						"search_accType":"30110",
						"search_beginDate":comm.navNull($("#search_startDate").val()),
						"search_endDate":comm.navNull($("#search_endDate").val()),
						};
				dataModule.detailedPage(jsonData,function(record, rowIndex, colIndex, options){
					//渲染列:交易时间
					if(colIndex == 0){
						return comm.formatDate(record.createdDate,'yyyy-MM-dd');
					}
					
					//渲染列:交易类型
					if(colIndex == 1){
						 return detailSearch.getBusiType(record.transSys,record.transType,record.businessType);
					}
					//渲染列:业务类型
					if(colIndex == 2){
						return comm.lang("cashAccount").businessTypeEnum[record["businessType"]];
					} 
					//渲染列：发生金额
					if(colIndex==3){
						return comm.formatMoneyNumber(record["amount"]);	
					}
					//渲染列：交易后金额  
					if(colIndex==4){
						if(record.accBalanceNew==null){
							return "--";
						}else{
							return comm.formatMoneyNumber(record.accBalanceNew);	
						}
					}
				});
			},
			getBusiType : function(transSys,transType,businessType){
					var transTypeName= comm.lang("cashAccount").transTypeEnum[transType];
					if(!transTypeName && transSys=='PS'){
						var tr=transType.charAt(1)+transType.charAt(3);
						if(tr=='10'){
							transTypeName="网上订单支付";
						}else if(tr=='13'){
							transTypeName="网上订单支付";
						}else if(tr=='11'){
							transTypeName="网上订单支付撤单";
						}else if(tr=='12'){
							transTypeName="网上订单支付退货";
						}else if(tr=='20'){
							transTypeName="消费积分";
						}else if(tr=='21'){
							transTypeName="消费积分撤单";
						}else if(tr=='22'||tr=='26'){
							transTypeName="消费积分退货";
						}else if(tr=='27'){
							transTypeName="互生币预付定金";
						}else if(tr=='28' && businessType=='1'){
							transTypeName="预付定金撤销";
						}else if(tr=='28' && businessType=='2'){
							transTypeName="消费积分";
						}else if(tr=='15'){
							transTypeName="网上订单预付订金退款";
						}else if(tr=='25'){
							transTypeName="线下订单预付订金退款";
						}else if(tr=='29'){
							transTypeName="预付定金撤销";
						}
					}
					return transTypeName;
			}
		};
		return detailSearch
	});
