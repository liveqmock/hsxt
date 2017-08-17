define(['text!accountCompanyTpl/hsbzh/mxcx/mxcx.html',
		"accountCompanyDat/accountCompany",
		"accountCompanyLan"
		], function(tpl,accountCompany){
	return companyHsbzhMxcx={
		showPage : function(){
			$('#busibox').html(tpl);
			//载入下拉参数
			comm.initSelect($("#scpoint_subject"),comm.lang("accountCompany").businessEnum,null,null);
		 	comm.initSelect($("#quickDate"),comm.lang("accountCompany").quickDateEnum,null,null);
		 	comm.initSelect($("#optHsbType"),comm.lang("accountCompany").hsbAccTypeEnum,null,"20110");
		    
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
				 companyHsbzhMxcx.pageQuery();
			 });
		},
		/** 分页查询 */ 
		pageQuery:function(){
			var params = {
					       "search_businessType":comm.removeNull($("#scpoint_subject").attr('optionValue')),
							"search_accType":comm.removeNull($("#optHsbType").attr("optionValue")),
							"search_beginDate":comm.removeNull($("#search_startDate").val()),
							"search_endDate":comm.removeNull($("#search_endDate").val()),
							"search_hsResNo" : comm.removeNull($("#hsResNo").val()),
							"search_enterpriseName" : comm.removeNull($("#enterpriseName").val())
							};
			 accountCompany.getAccoutGrid("scpoint_result_table",params,companyHsbzhMxcx.detail);
		},
		detail:function(record, rowIndex, colIndex, options){
			var transTypeName=companyHsbzhMxcx.getBusiType(record.transSys,record.transType);
			//渲染列:交易时间
			if(colIndex == 2){
				return comm.formatDate(record.createdDate,'yyyy-MM-dd');
			}
			if(colIndex == 3){
				return transTypeName;
			}else if(colIndex == 4){
				return comm.lang("accountCompany").businessEnum[record.businessType];
			}else if(colIndex == 5){
				return comm.formatMoneyNumber(record.amount);
			}else if(colIndex == 6){
				return comm.formatMoneyNumber(record.accBalanceNew);	
			}
		},
		getBusiType : function(transSys,transType,businessType){
			var transTypeName= comm.lang("accountCompany").transTypeEnum[transType];
			if(!transTypeName && transSys=='PS'){
				var tr=transType.charAt(1)+transType.charAt(3);
				var second=transType.charAt(2);
				if(tr=='10'){
					transTypeName="积分金额扣除";
				}else if(tr=='11'){
					transTypeName="网上订单撤单";
				}else if(tr=='12'){
					transTypeName="网上订单退货";
				}else if(tr=='20'){
					transTypeName="积分金额扣除";
				}else if(tr=='21'){
					transTypeName="消费积分撤单";
				}else if(tr=='22'){
					transTypeName="消费积分退货";
				}else if(tr=='13'){
					transTypeName="积分金额扣除";
				}else if(tr=='14'){
					transTypeName="积分金额扣除";
				}
			}
			return transTypeName;
	    }
	};
});