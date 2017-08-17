define(['text!cashtransferTpl/yhzzdz/tkcl.html',
		'cashtransferDat/cashTransfer'], function(tkclTpl, dataModoule){
	var tkclFun = {
		showPage : function(obj,searchTable){
			$('#tkcl_content').html(_.template(tkclTpl));	

			/*表格请求数据*/
			var arry = [];
			for(var i=0; i<obj.length;i++){
				arry.push(obj[i].transNo);
			}
			
			var reqData = {
				transNos	: escape(JSON.stringify(arry))
			};
			dataModoule.trans_failBack_list(reqData,function(res){
				if(res && res.data){
					$("#tkclNum").append(res.data.length);
					comm.getEasyBsGrid("tkclTable",res.data,tkclFun.detail);
					
					/*弹出框*/
					$( "#tkcl_content" ).dialog({
						title:"转账失败处理结果",
						modal:true,
						width:"800",
						height:"400",
						buttons:{
							"确定":function(){
								//刷新列表
								if(searchTable){ searchTable.refreshPage();}
								$( "#tkcl_content" ).dialog( "destroy" );
							}
						}
					});
				}
				
			});
			/*end*/
			
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
		}
	}	
	return tkclFun;
});