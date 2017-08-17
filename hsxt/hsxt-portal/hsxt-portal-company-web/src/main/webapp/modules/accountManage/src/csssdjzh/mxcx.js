define([
		'text!accountManageTpl/csssdjzh/mxcx/mxcx.html',
		'text!accountManageTpl/csssdjzh/mxcx/mxcx_jfhsfpns.html',
		'text!accountManageTpl/csssdjzh/mxcx/mxcx_jfzzzfpns.html',
		'text!accountManageTpl/csssdjzh/mxcx/mxcx_xhjfns.html',
		"accountManageDat/csssdjzh/csssdjzh",
		'accountManageDat/accountManage',
	    "accountManageLan"
		], function(tpl, mxcx_jfhsfpnsTpl,mxcx_jfzzzfpnsTpl,mxcx_xhjfnsTpl,csssdjzh,accountManage){
	return mxcx = {
		showPage : function(){
			$('#busibox').html(tpl);
			
			//载入下拉参数  
		 	comm.initSelect($("#quickDate"),comm.lang("accountManage").quickDateEnum,null);
		 	comm.initSelect($("#optTerm"),comm.lang("accountManage").taxRateTypeEnum,null,null);
		 	
		 	//时间控件
			comm.initBeginEndTime("#search_beginDate",'#search_endDate');
			   
			//快捷选择日期事件
			$("#quickDate").bind("change", function () {
				var method = {
					'today' : 'getTodaySE',
					'week' : 'getWeekSE',
					'month' : 'getMonthSE'
				}[$(this).attr("optionvalue")];
				var arr = method ? comm[method]() : ['', ''];
				$("#search_beginDate").val(arr[0]);
				$("#search_endDate").val(arr[1]);
			});
			
		   //查询
		   $("#btnQuery").click(function(){
			   if(!comm.queryDateVaild('city_tax_form').form()){
					return;
				}
			   mxcx.pageQuery();
		   });
		},
		/** 分页查询 */ 
		pageQuery:function(){
			var param = {
					      "search_accType":comm.navNull($("#optTerm").attr("optionvalue")),
					      "search_isTaxAccount":true,
					      "search_beginDate":comm.navNull($("#search_beginDate").val()),
					      "search_endDate":comm.navNull($("#search_endDate").val())
					     };
			csssdjzh.cityTaxDetailedPage("scpoint_result_table",param,function(record, rowIndex, colIndex, options){
				//渲染列:交易时间
				if(colIndex == 1){
					return comm.formatDate(record.createdDate,'yyyy-MM-dd');
				}
				//交易类型
				if(colIndex == 2){
					return comm.lang("accountManage").transTypeEnum[record["transType"]];
				}
				//业务类型
				if(colIndex == 3){
					return comm.lang("accountManage").taxRateTypeEnum[record["accType"]];
				} 
				//发生金额
				if(colIndex==4){
					return comm.formatMoneyNumber(record["amount"]);	
				}

				//查看详情点击事件
				return $('<a title="查看详情">查看</a>').click(function(e){
					mxcx.showDetail(record);
				});
			});
		},
		showDetail: function(record){
			switch (record.transType) {
			case 'U26000'://积分再增值分配
				mxcx.commDetiledShow(mxcx_jfzzzfpnsTpl,record,true);
				break;
			case 'U16000'://积分互生分配
				mxcx.commDetiledShow(mxcx_jfhsfpnsTpl,record,true);
				break;
			case 'G21000'://消费积分分配
				mxcx.commDetiledShow(mxcx_xhjfnsTpl,record,true);
				break;
			default:
				break;
			}
		},
		//公共详情显示
		commDetiledShow :function(html,record,isQuery,width){
			width = comm.isNotEmpty(width)?width:480;
			var transNo=comm.navNull(record.sourceTransNo,record.transNo);
			var transDate=comm.formatDate(record.createdDate,'yyyy-MM-dd hh:mm:ss');
			var transType=comm.navNull(record.transType);
			var businessTypeName=comm.navNull(comm.lang("accountManage").businessTypeEnum[record.businessType]);
			var amount=comm.formatMoneyNumber(record.amount);
			var transTypeName=comm.navNull(comm.lang("accountManage").transTypeEnum[record.transType]);
			var exchangeRate;
			var currencyNameCn;
			cacheUtil.findCacheLocalInfo(function(res){
				exchangeRate=comm.formatTransRate(res.exchangeRate);
				currencyNameCn=res.currencyNameCn;
			});
			if(comm.isNotEmpty(html)){
				if(isQuery){
					var param = {
							transNo:record.relTransNo,
							transType:record.transType
					};
					if(record.transSys=='PS'||param.transNo=='gjx'){
						param.transNo=record.transNo;
					}
					accountManage.getAccOptDetailed(param,function(resp){
						if(resp){
							var obj = resp.data.data;
							var data = null;
							if(comm.isNotEmpty(obj)){
								        data=obj;
										data.transNo=comm.navNull(record.transNo),
										data.transDate=transDate,
										data.transType=transTypeName,
										data.businessType=businessTypeName,
										data.channle=comm.navNull(comm.lang("common").accessChannel[obj.channel]),
										data.remark=comm.navNull(record.remark),
										data.amount=amount,
										data.currencyNameCn=currencyNameCn,
										data.exchangeRate=exchangeRate
								$('#csssdjzh-dialog > p').html(_.template(html,data));
								$('#csssdjzh-dialog').dialog({width:width,title:transTypeName+"详情",closeIcon:true}); 
							}else{
								comm.yes_alert(comm.lang("accountManage").dataNotExist);
							}
						}
					});
				}else{
					var data = {
					        transNo:transNo,
							transDate:transDate,
							transTypeName:transTypeName,
							businessTypeName:businessTypeName,
					        amount:amount,
					        currencyNameCn:currencyNameCn,
					        exchangeRate:exchangeRate,
					        remark:comm.navNull(record.remark)
					};
					$('#csssdjzh-dialog > p').html(_.template(html,data));
					$('#csssdjzh-dialog').dialog({width:width,title:transTypeName+"详情",closeIcon:true}); 
				}
			}
		}
	};
});