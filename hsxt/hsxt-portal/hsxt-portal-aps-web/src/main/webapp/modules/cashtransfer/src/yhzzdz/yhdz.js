define(['text!cashtransferTpl/yhzzdz/yhdz.html',
		'cashtransferDat/cashTransfer'], function(yhdzTpl, dataModoule){
	return {
		yhdzObj : null,
		selfObj : null,
		showPage : function(obj){
			$('#dz_content').html(_.template(yhdzTpl));	
			/*弹出框*/
			$( "#dz_content" ).dialog({
				title:"对账结果",
				modal:true,
				width:"1000",
				height:"400",
				closeIcon : true,
				buttons:{ 
					"确定":function(){
						$( "#dz_content" ).dialog( "destroy" );
					}
				}
		
			  });
			
			selfObj = this;
			/*表格请求数据*/
			var arry = [];
			for(var i=0; i<obj.length;i++){
				arry.push(obj[i].transNo);
			}
			var reqData = {
				transNos	: escape(JSON.stringify(arry))
			};
			
			dataModoule.transCheckUpAccount(reqData,function(rsp){
				var successRecords = rsp.data;
				for(var key in successRecords){
					
					if (successRecords.length >= key) {
						successRecords[key].reconciliationStatus = 0; // 对账失败
					}
					
					/** 添加对账失败的记录 */
					if (successRecords.length >0) {
						for(var r=0; r<obj.length; r++){
							if(obj[r].orderNo==successRecords[key].orderNo){
								obj.splice(r,1)
								break;
							 }
						}
					}
					/** 添加对账失败的记录 */
				}
				$("#yhdzNum").text(successRecords.length); //退款总数
				var newRecord=$.merge(successRecords,obj);
				yhdzObj = comm.getEasyBsGrid("yhdzTable", newRecord,selfObj.detail);
			});
			//yhdzObj = comm.getCommBsGrid("yhdzTable","trans_checkUp_Account",reqData,comm.lang("cashTransfer"),selfObj.detail);
		},
		detail : function(record, rowIndex, colIndex, options){
			
			if(colIndex == 5){
				if(record.amount){
					return comm.fmtMoney(record.amount);
				}else{
					return "";
				}
			}
			if(colIndex == 6){
				if(record.feeAmt){
					return comm.fmtMoney(record.feeAmt);
				}else{
					return "";
				}
			}
			if(colIndex == 7){
				if(record.realAmt){
					return comm.fmtMoney(record.realAmt);
				}else{
					return "";
				}
			}
			if(colIndex == 9){
				return comm.lang("cashTransfer").commitType[record.commitType];
			}
			if(colIndex == 10){
				if(record["reconciliationStatus"]==0){
					return comm.lang("cashTransfer").reconciliationStatus[0];
				}else{
					return comm.lang("cashTransfer").reconciliationStatus[1];
				}
			}
		}
	}	
});