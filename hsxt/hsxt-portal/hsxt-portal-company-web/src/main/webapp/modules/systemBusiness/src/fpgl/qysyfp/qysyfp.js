define(['text!systemBusinessTpl/fpgl/qysyfp/qysyfp.html', 
        'text!systemBusinessTpl/fpgl/qysyfp/qysyfp_ck.html',
        'text!systemBusinessTpl/fpgl/qysyfp/qysyfp_sq.html',
        'systemBusinessDat/systemBusiness', 
        'systemBusinessLan'	 
		], function(qysyfpTpl,qysyfpckTpl,qysyfpsqTpl,dataModoule){
	return qysyfp={
		showPage : function(){	
			$('#busibox').html(qysyfpTpl) ;
			
			qysyfp.res_table = $("#res_table").html();
				 comm.initSelect('#search_status', comm.lang("systemBusiness").invoice_status);
					
				 //时间控件
				 comm.initBeginEndTime("#search_beginDate",'#search_endDate');
				 
				 qysyfp.queryInvoiceSum();
					
				//清除绑定绑定事件
				//$('#PC001,#PC002,#PC003,#PC006,#PC005,#PC006,#PC007,#PC008,#PC009,#PC010,#PC011,#PC012').click(function(){});
					
				//开票
				$('#PC001,#PC002,#PC003,#PC006,#PC005,#PC006,#PC007,#PC008,#PC009,#PC010,#PC011,#PC012').die().live("click", (function(e){
					var id=$(e.currentTarget).attr('id'),
					openContent=$(e.currentTarget).parent('td').siblings().eq(0).text();
					allAmount=$(e.currentTarget).parent('td').siblings().eq(1).text();
					if(allAmount==0){
						comm.error_alert("金额0,不能申请发票", 400);
					}else if(allAmount < 1000 && id == "PC005"){
						return;
					}else{
						
									dataModoule.findMainInfoDefaultBankByResNo(null,function(res){
										var obj=res.data.mainInfo;
										 obj.allAmount=allAmount;
										 obj.openContent=openContent;
										 var bankInfo=res.data.bankInfo;
										 if(!bankInfo){
												comm.error_alert("该企业无默认银行账户",400)
												return false;
										 }
										 obj.bankBranchName=bankInfo.bankName;
										 obj.bankNo=bankInfo.bankAccNo;
										 obj.officeTel = comm.removeNull(res.data.baseInfo.officeTel);
										 $('#qysyfp_dialog').html(_.template(qysyfpsqTpl,obj));
										 comm.initSelect('#fpsq_invoiceType', comm.lang("systemBusiness").invoice_type,120,0);
									});	
						 $('#qysyfp_dialog').dialog({
						 	width:800, 
							height:320,
						 	title:'企业索取发票',
						 	buttons:{
						 		'确认':function(){
						 			var params=$("#fpsq_applyInvoiceform").serializeJson();
						 			$.extend(params,{"bizType":id});
						 			$(this).dialog('destroy');
						 			$.extend(params,{"invoiceType":$("#fpsq_invoiceType").attr("optionvalue")});
						 			dataModoule.custApplyInvoice(params,function(res){
						 				comm.yes_alert("申请发票成功",400);
						 				qysyfp.queryInvoiceSum();
										if(qysyfp.searchTable){
											$("#scpoint_searchBtn").click();
										}
						 			});						 			
						 		},
						 		'取消': function(){						 			
						 			$(this).dialog('destroy');
						 		}
						 	}						 	
						 });
				}
				}));
				
				
				$("#scpoint_searchBtn").click(function(){
					if(!comm.queryDateVaild('qysyfp_queryform').form()){
						return;
					}
					var params=$("#qysyfp_queryform").serializeJson();
					
					var status=$("#search_status").attr("optionvalue");
					if(!status||status=="" ){
						status="";
					}
					$.extend(params,{"search_status":status,'search_startDate':$('#search_beginDate').val()});
					qysyfp.searchTable = dataModoule.listInvoice("fpsq_table",params,qysyfp.detail);
				});
		} ,
		queryInvoiceSum : function(){
			$("#res_table").html(qysyfp.res_table);
			dataModoule.queryInvoiceSum({invoiceMaker:0},function(res){
				var invoicePools=res.data.invoicePools;
				if(invoicePools==null || invoicePools.length==0){
//						$("#PC001remain").text("0.00");
//						$("#PC001opened").text("0.00");
//						$("#PC002remain").text("0.00");
//						$("#PC002opened").text("0.00");
//						$("#PC003remain").text("0.00");
//						$("#PC003opened").text("0.00");
//						$("#PC005remain").text("0.00");
//						$("#PC005opened").text("0.00");
//						$("#PC006remain").text("0.00");
//						$("#PC006opened").text("0.00");
					
					$('<tr><td class="p5 border_top_CCC" align="right">暂无索取发票</td></tr>').appendTo($("#remain_tr"));
					$('<tr><td class="p5 border_top_CCC" align="right">0.00</td></tr>').appendTo($("#opended_tr"));
					
					$("#remain_td").attr("rowspan", 2);
					$("#opended_td").attr("rowspan", 2);
					
				}else{
					for(var i=0;i<invoicePools.length;i++){
						var bizType = invoicePools[i].bizType;
						var bizName = comm.getNameByEnumId(bizType, comm.lang("systemBusiness").bizType);
						var remainTr='<tr>';
						remainTr+='<td class="p5 border_top_CCC">'+bizName+'</td>';
						remainTr+='<td class="p5 border_top_CCC" align="right"><span id="'+bizType+'remain">'+(comm.formatMoneyNumber2(invoicePools[i].remainAmount))+'</span></td>';
						if(bizType == 'PC005' && invoicePools[i].remainAmount < 1000){//消费积分小于1000变灰
							remainTr+='<td class="border_top_CCC" align="right"><a id="'+bizType+'" class="pl5 pr5" style="color:gray;" title="金额小于1000,不能申请发票">索要发票</a></td>';
						}else{
							remainTr+='<td class="border_top_CCC" align="right"><a id="'+bizType+'" class="pl5 pr5">索要发票</a></td>';
						}
						remainTr+='</tr>';
						$(remainTr).appendTo($("#remain_tr"));
						var opendedTr='<tr>';
						opendedTr+='<td class="p5 border_top_CCC">'+bizName+'</td>';
						opendedTr+='<td class="p5 border_top_CCC" align="right"><span id="'+bizType+'opened">'+(comm.formatMoneyNumber2(invoicePools[i].openedAmount))+'</span></td>';
						opendedTr+='</tr>';
						$(opendedTr).appendTo($("#opended_tr"));
					}
					$("#remain_td").attr("rowspan", invoicePools.length+1);
					$("#opended_td").attr("rowspan", invoicePools.length+1);
				}
			});
		},
		detail: function(record, rowIndex, colIndex, options){
			if(colIndex == 1){
				return comm.formatMoneyNumber(record.allAmount);
			}else if(colIndex == 2){
				return comm.lang("systemBusiness").bizType[record.bizType];
			}else if(colIndex == 3){
				return comm.lang("systemBusiness").post_way[record.postWay];
			}else if(colIndex == 6){
				return comm.lang("systemBusiness").invoice_status[record.status];
			}else if(colIndex == 8){
				var link2 = $('<a>查看</a>').click(function(e) {
					dataModoule.getInvoiceById({invoiceId:record.invoiceId,invoiceMaker:0},function(res){
						qysyfp.chaKan(res.data, record.status);
					});					
				});
				return link2;
			}
		},
		chaKan : function(obj, status){
			obj.status = status;
			obj.receiveWay = comm.lang("systemBusiness").receive_way[obj.receiveWay];
			obj.postWayName=comm.lang("systemBusiness").post_way[obj.postWay];
			obj.invoiceTypeName=comm.lang("systemBusiness").invoice_type[obj.invoiceType];
			/*弹出框*/
			$( "#qysyfp_xq_dialog" ).dialog({
				title:"查看发票信息",
				width:"830",
				modal:true,
				closeIcon : true,
				buttons:{ 
					"返回":function(){
						$( this ).dialog( "destroy" );
					}
				}
			});
			/*end*/	
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
		}
	};
});