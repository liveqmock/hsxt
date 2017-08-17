define(['text!cashtransferTpl/yhzzcx/yhzzcx.html',
		'cashtransferDat/cashTransfer',
		'cashtransferSrc/yhzzcx/recordDetail',
		'cashtransferSrc/yhzzcx/bankDetail',
		'cashtransferLan'
		], function(yhzzcxTpl, dataModoule, recordDetail,bankDetail){
	var yhzzcxFun = {
		searchTable : null,
		showPage : function(){
			
			$('#busibox').html(_.template(yhzzcxTpl));
			$("#listCountTable").hide();		
			
			// 时间控件		    
		    comm.initBeginEndTime('#search_startDate','#search_endDate');
			
			// 下拉列表事件
			comm.initSelect('#transStatus', comm.lang("cashTransfer").transStatusEnum,null,null,{name:comm.lang("common").default_select, value:''});
			
			// 银行转账导出
			$("#btnExport").click(function(){
				//查询条件验证
				if(!comm.queryDateVaild("search_form").form()){
					return;
				}
				
				//导出链接
				var hrefParam="?";
				var params = yhzzcxFun.getListQueryCriteria();
				for(var key in params){
					if(comm.isNotEmpty(key))
						hrefParam+=key+"="+params[key]+"&";
				};
				
				//登录信息
				var lp = comm.getRequestParams();
				hrefParam+="token="+lp.token+"&custId="+lp.custId+"&channelType="+lp.channelType;
				
				window.open(comm.domainList["apsWeb"]+comm.UrlList["transfer_record_export"]+hrefParam,'_blank');    
			});
			
			// 查询银行转账
			$("#qry_yhzz_btn").click(function(){
				
				//查询条件验证
				if(!comm.queryDateVaild("search_form").form()){
					return;
				}
				
				//列表查询条件
				var params = yhzzcxFun.getListQueryCriteria();
				yhzzcxFun.searchTable = comm.getCommBsGrid("searchTable","find_transferRecord_list",params,comm.lang("cashTransfer"),yhzzcxFun.detail);
				
				//转账金额统计
				var reqData = {
						startDate	: $("#search_startDate").val().trim(),	//开始时间
						endDate		: $("#search_endDate").val().trim(),	//结束时间
						hsResNo		: $("#hsResNo").val().trim(),			//互生号
						transStatus	: $("#transStatus").attr("optionvalue") //状态
				};
				dataModoule.getTransferRecordListCount(reqData,function(res){
					if(res.retCode==22000){
						var data = res.data;
						if(data){
							$("#totalNum").text(data.totalNum);
							$("#totalAmount").text(comm.fmtMoney(data.totalAmount));
							$("#applyingAmount").text(comm.fmtMoney(data.applyingAmount));
							$("#reversedAmount").text(comm.fmtMoney(data.reversedAmount));
							$("#waitBackAmount").text(comm.fmtMoney(data.waitBackAmount));
							$("#transSuccessAmount").text(comm.fmtMoney(data.transSuccessAmount));
							$("#deductFeeAmount").text(comm.fmtMoney(data.deductFeeAmount));
							$("#payingAmount").text(comm.fmtMoney(data.payingAmount));
							$("#revokedAmount").text(comm.fmtMoney(data.revokedAmount));
							$("#backSuccessAmount").text(comm.fmtMoney(data.backSuccessAmount));
							$("#transFailAmount").text(comm.fmtMoney(data.transFailAmount));
							$("#bankBackAmount").text(comm.fmtMoney(data.bankBackAmount));
							$("#listCountTable").show();
						}
					}
				});
				
			});
			
		},
		/** 列表查询条件 */
		getListQueryCriteria:function(){
			var params = {
					search_startDate	: $("#search_startDate").val().trim(),	//开始时间
					search_endDate		: $("#search_endDate").val().trim(),	//结束时间
					search_hsResNo		: $("#hsResNo").val().trim()			//互生号
			};
			
			var transStatus = $("#transStatus").attr("optionvalue");
			if(transStatus){
				params.search_transStatus = transStatus;
			}
			
			return params;
		},
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex == 0){
				var detailLink = $('<a title='+record.transNo+'>'+record.transNo+'</a>').click(function(e) {
					var postData = record;
					postData.commitTypeName = comm.lang("cashTransfer").commitType[postData.commitType];
					postData.amountDisp = comm.fmtMoney(postData.amount);
					postData.feeAmtDisp = postData.feeAmt==null?'-':comm.fmtMoney(postData.feeAmt);
					postData.realAmtDisp = comm.fmtMoney(postData.realAmt);
					postData.transStatusName = comm.lang("cashTransfer").transStatusEnum[postData.transStatus];
					postData.custNameDisp = $('<a >'+record.custName+'</a>').click(function(e) {
						//显示详细信息页面
						bankDetail.showPage(record);
					});
					
					//显示详细信息页面
					recordDetail.showPage(postData);
				});
				return detailLink;
			}
			
			if(colIndex == 2){
				var link1 =  $('<a title='+record.custName+'>'+record.custName+'</a>').click(function(e) {
					
					//显示详细信息页面
					bankDetail.showPage(record);
				});
				return   link1 ;
			}
			if(colIndex == 4){
				if(record.amount){
					return comm.fmtMoney(record.amount);
				}else{
					return "";
				}
			}
			if(colIndex == 5){
				if(record.feeAmt){
					return comm.fmtMoney(record.feeAmt);
				}else{
					return "-";
				}
			}
			if(colIndex == 7){
				return comm.lang("cashTransfer").transStatusEnum[record.transStatus];
			}
			
			if(colIndex == 8){
				/*if(record.transStatus==1||record.transStatus==2){
					return "<span title='"+record.reqTime+"'>" + record.reqTime + "</span>";
				}*/
				return "<span title='"+record.resultTime+"'>" + record.resultTime + "</span>";
			}
			
			if(colIndex == 9){
				return comm.lang("cashTransfer").commitType[record.commitType];
			}
		}
	}	
	
	return yhzzcxFun;
});