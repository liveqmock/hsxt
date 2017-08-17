define(['text!accountPersonTpl/hsbzh/mxcx/mxcx.html',
		'accountPersonDat/accountPerson',
		'accountPersonLan'
		], function(tpl,accountPerson){
	return cardPersonHsbzhMxcx={
		showPage : function(){
			$('#busibox').html(tpl);
			//载入下拉参数
			comm.initSelect($("#scpoint_subject"),comm.lang("accountPerson").businessEnum,null,null);
		 	comm.initSelect($("#quickDate"),comm.lang("accountPerson").quickDateEnum,null,null);
		 	comm.initSelect($("#optHsbType"),comm.lang("accountPerson").hsbAccTypeEnum,null,"20110");
		 	
		 	//时间控件		    
		    comm.initBeginEndTime('#search_startDate','#search_endDate');
			   
			//快捷选择日期事件
			$("#quickDate").bind("change", function () {
				var method = {
					'today' : 'getTodaySE',
					'week' : 'getWeekSE',
					'month' : 'getMonthSE'
				}[$(this).attr("optionvalue")];
				var arr = method ? comm[method]() : ['', ''];
				$("#search_startDate").val(arr[0]);
				$("#search_endDate").val(arr[1]);
			});
		
			   //查询
			   $("#scpoint_searchBtn").click(function(){
				   if(!comm.queryDateVaild("scpoint_form").form()){
						return;
					}
				   cardPersonHsbzhMxcx.pageQuery();
			   });
		
		},
		/** 分页查询 */ 
		pageQuery:function(){
			var params = {
					        "search_businessType":comm.removeNull($("#scpoint_subject").attr("optionValue")),
							"search_accType":comm.removeNull($("#optHsbType").attr("optionValue")),
							"search_beginDate":comm.removeNull($("#search_startDate").val()),
							"search_endDate":comm.removeNull($("#search_endDate").val()),
							"search_hsResNo":comm.removeNull($("#hsResNo").val()),
							"search_enterpriseName" : comm.removeNull($("#enterpriseName").val()),
							};
			accountPerson.getAccoutGrid("scpoint_result_table",params,cardPersonHsbzhMxcx.detail);
		},
		detail:function(record, rowIndex, colIndex, options){
			//渲染列:交易时间
			if(colIndex == 2){
				return comm.formatDate(record.createdDate,'yyyy-MM-dd');
			}
			if(colIndex == 3){
				return cardPersonHsbzhMxcx.getBusiType(record.transSys,record.transType,record.businessType);
			}else if(colIndex == 4){
				return comm.lang("accountPerson").businessEnum[record.businessType];
			}else if(colIndex == 5){
				return comm.formatMoneyNumber(record.amount);
			}else if(colIndex == 6){
				return comm.formatMoneyNumber(record.accBalanceNew);	
			}
		},
		getBusiType : function(transSys,transType,businessType){
			var transTypeName="";
			if(transSys=='PS'){
				var tr=transType.charAt(1)+transType.charAt(3);
				if(tr=='10'){
					transTypeName="网上订单支付";
				}else if(tr=='11'){
					transTypeName="网上订单支付撤单";
				}else if(tr=='12'){
					transTypeName="网上订单支付退货";
				}else if(tr=='13'){
					transTypeName="网上订单支付";
				}else if(tr=='20'){
					transTypeName="消费积分";
				}else if(tr=='21'){
					transTypeName="消费积分撤单";
				}else if(tr=='22'){
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
			}else{
				transTypeName= comm.lang("accountPerson").transTypeEnum[transType];
			}
			return transTypeName;
		}
	};
});