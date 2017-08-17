define(['text!cashtransferTpl/yhzzbl/plcd.html',
        'cashtransferDat/cashTransfer' ], function(plcdTpl,dataModule){
	return {
		plcdObj	 : null,
		showPage : function(data, searchTable){
			$('#cd_content').html(_.template(plcdTpl));	
						
			/*表格请求数据*/
			plcdObj = $.fn.bsgrid.init('plcdTable', {				 
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
			$( "#cd_content" ).dialog({
				title:"批量撤单",
				modal:true,
				width:"800",
				height:"400",
				buttons:{ 
					"确定":function(){
						var secArray = plcdObj.getRows();
						var val = {};
						for(var i=0; i<secArray.length;i++){
							var key = secArray[i].cells[1].title;
							var text = secArray[i].cells[6].childNodes[1].value;
							val[key] = text;
						}
						var params = {
								transNos	: encodeURIComponent(JSON.stringify(val)),	//消息ID
								apprOptId	: comm.getCookie('custName'),
								apprOptName	: comm.getCookie('operName'),
								apprRemark	: ''
						};
						//删除	
						dataModule.transRevoke(params,function(res){
							//刷新列表
							if(searchTable){
								searchTable.refreshPage();
							}
						});
						$( "#cd_content" ).dialog("destroy");
					},
					"取消":function(){
						 $( "#cd_content" ).dialog("destroy");
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
			
			if(colIndex == 6){
				return "<textarea class=\"h50 w98pc rsn pt5 pb5\" maxlength=300 placeholder=\"输入备注信息，不超过300字\"></textarea>";
			}
			
		}
	}	
});