define(['text!cashtransferTpl/yhzzbl/plzz.html',
        'cashtransferDat/cashTransfer' ], function(plzzTpl,dataModule){
	return {
		plzzObj  : null,
		showPage : function(data, searchTable){
			$('#tjzz_content').html(_.template(plzzTpl));	
			
			/*表格请求数据*/
			var plzzObj = $.fn.bsgrid.init('plzzTable', {				 
				pageSizeSelect: true ,
				pageSize: 10 ,
				stripeRows: true,  //行色彩分隔 
				displayBlankRows: false ,   //显示空白行
				localData:data,
				operate : {
					detail:this.detail,
				}
			});
			/*end*/
			
			/*弹出框*/
			$( "#tjzz_content" ).dialog({
				title:"提交转账",
				modal:true,
				width:"800",
				height:"400",
				buttons:{
					"确定":function(){
						var secArray = plzzObj.getAllRecords();
						var arry = [];
						for(var i=0; i<secArray.length;i++){
							arry.push(secArray[i].transNo);
						}
						var params = {
								transNos	: escape(JSON.stringify(arry)),	//消息ID
								apprOptId	: comm.getCookie('custName'),
								apprOptName	: comm.getCookie('operName'),
								apprRemark	: ''
						};
						//删除	
						dataModule.transBatch(params,function(res){
							//刷新列表
							if(searchTable){
								searchTable.refreshPage();
							}
						});
						$( "#tjzz_content" ).dialog( "destroy" );
					},
					"取消":function(){
						 $( "#tjzz_content" ).dialog( "destroy" );
					}
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
			
			/*if(colIndex == 6){
				if(record.feeAmt){
					return comm.fmtMoney(record.feeAmt);
				}else{
					return "-";
				}
			}*/
			
		}
		
	}	
});