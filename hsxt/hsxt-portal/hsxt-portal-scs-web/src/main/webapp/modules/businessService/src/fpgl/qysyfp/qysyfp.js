define(['text!businessServiceTpl/fpgl/qysyfp/qysyfp.html',
        'text!businessServiceTpl/fpgl/qysyfp/qysyfp_ck.html',
        'text!businessServiceTpl/fpgl/qysyfp/qysyfp_sq.html', 
        'businessServiceDat/businessService',
   	    'businessServiceLan'
		], function(qysyfpTpl,qysyfpckTpl,qysyfpsqTpl,dataModoule){
	var qysqfpFun = {
		showPage : function(){
			$('#busibox').html(qysyfpTpl) ;
			
			//初始化下拉框，时间选择
			comm.initSelect('#search_status', comm.lang("businessService").invoice_status);
			comm.initBeginEndTime("#search_startDate", "#search_endDate");
					
			qysqfpFun.queryInvoiceSum();
			
			//申请发票
			$('#PC001,#PC002,#PC003,#PC006,#PC005').click(function(e){
				var id=$(e.currentTarget).attr('id'),
				openContent=$(e.currentTarget).parent('td').siblings().eq(0).text();
				allAmount=$(e.currentTarget).parent('td').siblings().eq(1).text();
				
				if(allAmount==0){
					comm.error_alert("金额为0,不能申请发票",400)
				}else{
			      dataModoule.findMainInfoDefaultBankByResNo(null,function(res){
			      var obj=res.data.mainInfo;
			      obj.openContent=openContent;
				  obj.allAmount=allAmount;
				  
			      var bankInfo=res.data.bankInfo;
				  if(!bankInfo){
					  comm.error_alert("请设置你的默认银行账户",400);
					  return false;
				  }
				  obj.bankBranchName=bankInfo.bankName;
				  obj.bankNo=bankInfo.bankAccNo;
				  obj.officeTel = comm.removeNull(res.data.baseInfo.officeTel);
				  
					$('#qysyfp_dialog').html(_.template(qysyfpsqTpl,obj));
					comm.initSelect('#fpsq_invoiceType', comm.lang("businessService").invoice_type,120,0);
					$('#qysyfp_dialog').dialog({
					 	width:750, 
						height:320,
					 	title:'企业索取发票',
					 	buttons:{
					 		'确认':function(){
								var invoiceType = comm.removeNull($("#fpsq_invoiceType").attr("optionvalue"));
								if(invoiceType == ""){
									comm.error_alert("请选择发票类型",400);
									return;
								}
					 			var params=$("#fpsq_applyInvoiceform").serializeJson();
					 			$.extend(params,{"bizType":id});
					 			$.extend(params,{"invoiceType":$("#fpsq_invoiceType").attr("optionvalue")});
					 			$(this).dialog('destroy');
					 			dataModoule.custApplyInvoice(params,function(res){
					 				comm.yes_alert("申请发票成功",400);
					 				qysqfpFun.queryInvoiceSum();
									if(qysqfpFun.searchTable){
										$("#scpoint_searchBtn").click();
									}
					 			});						 			
					 		},
					 		'取消': function(){						 			
					 			$(this).dialog('destroy');
					 		}
					 	}						 	
					});
		          });	
		       }
			});
				
				
			$("#scpoint_searchBtn").click(function(){
				var valid = comm.queryDateVaild("search_form");
				if(!valid.form()){
					return false;
				}
				var params=$("#search_form").serializeJson();
				var status=$("#search_status").attr("optionvalue");
				if(!status||status==""){
					status="";
				}
				$.extend(params,{"search_status":status});
				qysqfpFun.searchTable = dataModoule.listInvoice("fpsq_table",params,qysqfpFun.detail);
			});
		} ,
		queryInvoiceSum : function(){
			//获取发票金额
			dataModoule.queryInvoiceSum({invoiceMaker:0},function(res){
				var invoicePools=res.data.invoicePools;
				
				if(invoicePools==null || invoicePools.length==0){
					$("#PC001remain").text("0.00");
					$("#PC001opened").text("0.00");
					$("#PC002remain").text("0.00");
					$("#PC002opened").text("0.00");
					$("#PC003remain").text("0.00");
					$("#PC003opened").text("0.00");
					$("#PC005remain").text("0.00");
					$("#PC005opened").text("0.00");
					$("#PC006remain").text("0.00");
					$("#PC006opened").text("0.00");
				}else{
					for(var i=0;i<invoicePools.length;i++){
						if(invoicePools[i].bizType=='PC001'){
							$("#PC001remain").text(comm.formatMoneyNumber2(invoicePools[i].remainAmount));
							$("#PC001opened").text(comm.formatMoneyNumber2(invoicePools[i].openedAmount));
						}
						else if(invoicePools[i].bizType=='PC002'){
							$("#PC002remain").text(comm.formatMoneyNumber2(invoicePools[i].remainAmount));
							$("#PC002opened").text(comm.formatMoneyNumber2(invoicePools[i].openedAmount));
						}
						else if(invoicePools[i].bizType=='PC003'){
							$("#PC003remain").text(comm.formatMoneyNumber2(invoicePools[i].remainAmount));
							$("#PC003opened").text(comm.formatMoneyNumber2(invoicePools[i].openedAmount));
						}
						else if(invoicePools[i].bizType=='PC005'){
							$("#PC005remain").text(comm.formatMoneyNumber2(invoicePools[i].remainAmount));
							$("#PC005opened").text(comm.formatMoneyNumber2(invoicePools[i].openedAmount));
						}
						else if(invoicePools[i].bizType=='PC006'){
							$("#PC006remain").text(comm.formatMoneyNumber2(invoicePools[i].remainAmount));
							$("#PC006opened").text(comm.formatMoneyNumber2(invoicePools[i].openedAmount));
						}
					}
				}
			});
		},
		detail: function(record, rowIndex, colIndex, options){
			if(colIndex == 1){
				return comm.formatMoneyNumber(record.allAmount);
			}else if(colIndex == 2){
				return comm.lang("businessService").bizType[record.bizType];
			}else if(colIndex == 3){
				return comm.lang("businessService").post_way[record.postWay];
			}else if(colIndex == 6){
				return comm.lang("businessService").invoice_status[record.status];
			}else if(colIndex == 8){
				var link2 = $('<a>查看</a>').click(function(e) {
					dataModoule.getInvoiceById({invoiceId:record.invoiceId,invoiceMaker:0},function(res){
						qysqfpFun.chaKan(res.data, record.status);
					});					
				});
				return link2;
			}
		},
		chaKan : function(obj, status){
			obj.status=status;
			obj.receiveWay=comm.lang("businessService").receive_way[obj.receiveWay];
			obj.postWayName=comm.lang("businessService").post_way[obj.postWay];
			obj.invoiceTypeName=comm.lang("businessService").invoice_type[obj.invoiceType];
			/*弹出框*/
			$( "#qysyfp_xq_dialog" ).dialog({
				title:"查看发票信息",
				width:"800",
				modal:true,
				closeIcon : true,
				buttons:{ 
					"返回":function(){
						$( this ).dialog( "destroy" );
					}
				}
			});
			/*end*/	
			obj.allAmount=comm.formatMoneyNumber(obj.allAmount);
			$('#qysyfp_xq_dialog').html(_.template(qysyfpckTpl, obj));
			
			var json_ck = obj.invoiceLists;
			if(json_ck!=null && json_ck.length!=0){
				var gridObj_ck = $.fn.bsgrid.init('fpsq_xq_table', {				 
					pageSizeSelect: true ,
					pageSize: 10 ,
					stripeRows: true,  //行色彩分隔 
					displayBlankRows: false ,   //显示空白行
					localData:json_ck,
					operate : {
						detail:function(record, rowIndex, colIndex, options){
							return comm.formatMoneyNumber(record.invoiceAmount);
						}
					}					
				});
			}
			$("#fpsq_xq_table").css('min-width','774px');
		}
	}
	return qysqfpFun;
});