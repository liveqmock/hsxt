define(['text!accountPersonFckrTpl/hsbzh/mxcx/mxcx.html'
		], function(tpl){
	return personHsbmxcx = {
		
		showPage : function(){
			$('#busibox').html(tpl);
			//载入下拉参数
			comm.initSelect($("#scpoint_subject"),comm.lang("accountPersonFckr").businessEnum,null,null);
		 	comm.initSelect($("#quickDate"),comm.lang("accountPersonFckr").quickDateEnum,null,null);
		 	//时间控件		    
		    comm.initBeginEndTime('#search_startDate','#search_endDate');
		    //快捷选择日期事件
		    personHsbmxcx.dateChange();
		    //查询
		    $("#scpoint_searchBtn").click(function(){
			   if(!comm.queryDateVaild("scpoint_form").form()){return;}
			   personHsbmxcx.pageQuery();
		    });
		},
		/** 分页查询 */ 
		pageQuery:function(){
			var params = {};
			params.search_accType = comm.lang("accountPersonFckr").hsbAccount;
			params.search_mobile = comm.removeNull($("#mobile").val());
			params.search_perName = comm.removeNull($("#perName").val());
			params.search_businessType = comm.removeNull($("#scpoint_subject").attr("optionValue"));
			params.search_beginDate = comm.removeNull($("#search_startDate").val());
			params.search_endDate = comm.removeNull($("#search_endDate").val());
			comm.getCommBsGrid("scpoint_result_table", "query_perNoCard_hsbmxcx",params,null,personHsbmxcx.detail);
		},
		detail:function(record, rowIndex, colIndex, options){
			//渲染列:交易时间
			if(colIndex == 2){
				return comm.formatDate(record.createdDate,'yyyy-MM-dd');
			}else if(colIndex == 3){
				return personHsbmxcx.getBusiType(record.transSys,record.transType,record.businessType);
			}else if(colIndex == 4){
				return comm.lang("accountPersonFckr").businessEnum[record.businessType];
			}else if(colIndex == 5){
				return comm.formatMoneyNumber(record.amount);
			}else if(colIndex == 6){
				return comm.formatMoneyNumber(record.accBalanceNew);	
			}
		},
		dateChange : function(){
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
		},
		getBusiType : function(transSys,transType,businessType){
			var transTypeName= comm.lang("accountPersonFckr").transTypeEnum[transType];
			if(!transTypeName && transSys=='PS'){
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
				}else if(tr=='15'){
					transTypeName="线下订单预付订金退款";
				}else if(tr=='29'){
					transTypeName="预付定金撤销";
				}
			}
			return transTypeName;
		}
	};
});