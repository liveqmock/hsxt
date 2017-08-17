define(["text!receivablesmanageTpl/ywddcx/ywddcx.html","receivablesmanageDat/receivablesmanage","receivablesmanageLan"],function(ywddcxTpl,receivablesmanage){
	var ywddcxFun = {
		showPage : function(){
			
			$('#busibox').html(_.template(ywddcxTpl));	

			// 日期控件
			comm.initBeginEndTime('#search_startDate','#search_endDate');
			
			// 订单类型 
			comm.initSelect("#transaction_type",comm.lang("receivablesmanage").OrderType);
			
			// 订单状态
			comm.initSelect("#payment_state",comm.lang("receivablesmanage").selectPayStatus,null,"0,1,2,3",{name:comm.lang("common").default_select, value:"0,1,2,3"});
			
			// 支付方式
			comm.initSelect("#payment_method",comm.lang("receivablesmanage").PayChannel);
			
			
			/** 查询事件 */
			$("#btnQuery").click(function(){
				if(!comm.queryDateVaild("search_form").form()){
					return;
				}
				ywddcxFun.pageQuery();
			});
		},
		/** 分页查询 */
		pageQuery:function(){
			//查询参数
			var queryParam={
						"search_hsResNo":$("#txtHsResNo").val(),"search_startDate":$("#search_startDate").val(),
						"search_endDate":$("#search_endDate").val(),"search_orderType":$("#transaction_type").attr("optionvalue"),
						"search_payChannel":$("#payment_method").attr("optionvalue"),"search_unPayStatus":$("#payment_state").attr("optionvalue"),
						"search_custNameCondition":$("#txtCustName").val()
						};
			var gridObj= receivablesmanage.businessOrderPage("searchTable",queryParam,function(record, rowIndex, colIndex, options){
				
				//企业名称
				if(colIndex==2){
					var result = comm.removeNull(record["custName"]);
					return "<span title='"+result+"'>"+result+"</span>";
                }
				//订单类型
				if(colIndex==5){
                    return  comm.lang("receivablesmanage").OrderType[record["orderType"]];
                }
				
				//订单金额，根据订单支付方式判断
				if(colIndex==6){
                    if(record.payChannel==200){
                    	return comm.formatMoneyNumber(record.orderHsbAmount);
                    }else {
                    	return comm.formatMoneyNumber(record.orderCashAmount);
                    }
                }
				
				//折合应付本币(人民币)
				if(colIndex==7){
					if(comm.isEmpty(record.exchangeRate)&&record.currencyCode=='CNY'){
						record.exchangeRate= 1 ;
					}
                    if(record.payChannel==200){
                    	//return comm.formatMoneyNumber(record.orderHsbAmount*parseFloat(record.exchangeRate));
                    	return comm.formatMoneyNumber(record.orderHsbAmount);
                    }else {
                    	//return comm.formatMoneyNumber(record.orderCashAmount*parseFloat(record.exchangeRate));
                    	return comm.formatMoneyNumber(record.orderCashAmount);
                    }
                }
				
				//订单支付方式
				if(colIndex==8){
                    return comm.lang("receivablesmanage").PayChannel[record["payChannel"]];
                }
				
				//订单状态
				if(colIndex==10){
                    return comm.lang("receivablesmanage").businessPayStatus[record["payStatus"]];
                }
				
			});
		}
	}
	return ywddcxFun;
});