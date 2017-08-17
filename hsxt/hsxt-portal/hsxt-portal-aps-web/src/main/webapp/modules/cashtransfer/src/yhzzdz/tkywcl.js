define(['text!cashtransferTpl/yhzzdz/tkywcl.html',
		'cashtransferDat/cashTransfer',
		"cashtransferLan"], function(tkywclTpl, dataModoule){
	var tkywclFun = {
		searchTable : null,
		showPage : function(){
			$('#busibox').html(_.template(tkywclTpl));	

			/*表格请求数据*/
			var params = {
					search_transStatus	: 6  //转账失败
			};
			
			tkywclFun.searchTable = comm.getCommBsGrid("searchTable","find_transferRecord_list",params,comm.lang("cashTransfer"),tkywclFun.detail);
			
			/*按钮事件*/
			$('#zzsbcl_btn').click(function(){
				var obj = tkywclFun.searchTable.getCheckedRowsRecords();
				if(obj.length < 1){
					return;
				}
				//跳转至对账
				require(["cashtransferSrc/yhzzdz/tkcl"],function(tkcl){
					tkcl.showPage(obj,tkywclFun.searchTable);
				});
			});
			
		},
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex == 6){
				if(record.amount){
					return comm.fmtMoney(record.amount);
				}else{
					return "";
				}
			}
			if(colIndex == 7){
				if(record.feeAmt){
					return comm.fmtMoney(record.feeAmt);
				}else{
					return "";
				}
			}
		}
	}	
	return tkywclFun;
});