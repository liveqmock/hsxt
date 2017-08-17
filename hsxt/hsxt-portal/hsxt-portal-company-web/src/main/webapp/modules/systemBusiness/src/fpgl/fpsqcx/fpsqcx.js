define(['text!systemBusinessTpl/fpgl/fpsqcx/fpsqcx.html' ,
        'text!systemBusinessTpl/fpgl/fpsqcx/fpsqcx_ck.html',
        'text!systemBusinessTpl/fpgl/fpsqcx/fpsqcx_ps.html',
        'systemBusinessSrc/fpgl/fpsq/fpsq',
        'systemBusinessDat/systemBusiness', 
        'systemBusinessLan'	 
		], function(fpsqcxTpl,fpsqcxckTpl,fpsqcxpsTpl,fpsq,dataModoule){
	return fpsqcx={
		allAmount : null,
		showPage : function(){
			$('#busibox').html(fpsqcxTpl) ;			
				//开发票
				$('#fpgl_qykfp').click(function(){
						if(fpsqcx.allAmount == null){
							fpsqcx.allAmount = 0;
						}
						if(parseFloat(fpsqcx.allAmount) <=0 ){
							comm.warn_alert(comm.lang("systemBusiness").mustGtZero);
							return false;
						}
						//检查平台是否有默认银行账户
						cacheUtil.findCacheLocalInfo(function(response){
							dataModoule.findDefaultBankByResNo({companyResNo:response.platResNo}, function(res){
								if(res.data){
									comm.liActive_add($('#fpgl_fpsq'));
									fpsq.showPage(comm.formatMoneyNumber2(fpsqcx.allAmount));
								}else{
									comm.error_alert("地区平台尚未设置默认银行账户，暂无法开票", 400);
								}
							});
						});
				});
				/*日期控件*/
				comm.initBeginEndTime("#search_beginDate",'#search_endDate');
				comm.initSelect('#search_status', comm.lang("systemBusiness").delivery_status);

				dataModoule.queryInvoiceSum({invoiceMaker:1},function(res){
					var data=res.data;
					$("#openedAllAmount").text(comm.formatMoneyNumber2(data.openedAllAmount));
					$("#remainAllAmount").text(comm.formatMoneyNumber2(data.remainAllAmount));
					fpsqcx.allAmount=data.remainAllAmount;
				});
				
				$("#scpoint_searchBtn").click(function(){
					if(!comm.queryDateVaild('fpsq_form').form()){
						return;
					}
					var params=$("#fpsq_form").serializeJson();
					var status=$("#search_status").attr("optionvalue");
					if(!status||status==""){
						status="";
					}
					$.extend(params,{"search_status":status,'search_startDate':$('#search_beginDate').val()});
					dataModoule.listInvoice("fpsq_table",params,fpsqcx.detail,fpsqcx.edit);
				});
			},
			detail: function(record, rowIndex, colIndex, options){
				if(colIndex == 1){
					return comm.formatMoneyNumber(record.allAmount);
				}else if(colIndex == 2){
					return comm.lang("systemBusiness").invoice_type[record.invoiceType];
				}else if(colIndex == 4){
					return comm.lang("systemBusiness").post_way[record.postWay];
				}else if(colIndex == 7){
					return comm.lang("systemBusiness").invoice_status[record.status];
				}else if(colIndex == 9){
					var link2 = $('<a>查看</a>').click(function(e) {
						    dataModoule.getInvoiceById({invoiceId:record.invoiceId,invoiceMaker:1},function(res){
						    	fpsqcx.chaKan(res.data, record.status);	
						    });
					    });
					return link2;
				}
			},
			edit:function(record, rowIndex, colIndex, options){
				if(colIndex == 9 && record.status=='2'){
					var link2 = $('<a>配送</a>').click(function(e) {
						dataModoule.getInvoiceById({invoiceId:record.invoiceId,invoiceMaker:1},function(res){
							fpsqcx.peiSong(res.data);
						});					
					});
					return link2;
				}
			},
			chaKan : function(obj, status){
				obj.status = status;
				obj.receiveWay = comm.lang("systemBusiness").receive_way[obj.receiveWay];
				obj.allAmount = comm.formatMoneyNumber(obj.allAmount);
				obj.postWayName=comm.lang("systemBusiness").post_way[obj.postWay];
				obj.invoiceTypeName=comm.lang("systemBusiness").invoice_type[obj.invoiceType];
				/*弹出框*/
				$( "#fpsqcx_xq_dialog" ).dialog({
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
				$('#fpsqcx_xq_dialog').html(_.template(fpsqcxckTpl, obj));
				
				var json_ck = obj.invoiceLists;
				if(json_ck!=null && json_ck.length!=0){
					var gridObj_ck = $.fn.bsgrid.init('fpsqcx_xq_table', {				 
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
			},
			peiSong : function(obj){
				obj.invoiceTypeName=comm.lang("systemBusiness").invoice_type[obj.invoiceType];
				obj.allAmount=comm.formatMoneyNumber(obj.allAmount);
				/*弹出框*/
				$( "#fpsqcx_ps_dialog" ).dialog({
					title:"配送详情",
					width:"800",
					modal:true,
					closeIcon : true
				});
				/*end*/	
				$('#fpsqcx_ps_dialog').html(_.template(fpsqcxpsTpl, obj));
				$('#xgps_submit').click(function(){
					if(!fpsqcx.psValid().form()){
						return;
					}
					var params = $("#xgps_form"). serializeJson();
					dataModoule.changePostWay(params,function(res){
						comm.yes_alert("配送完成",400);
						$( "#fpsqcx_ps_dialog" ).dialog( "destroy" );
						$("#fpsq_table_pt_refreshPage").click();
					});		
				});
				
				/*取消*/	
				$('#xgps_back_dqptkfpywcx').click(function(){
					$( "#fpsqcx_ps_dialog" ).dialog( "destroy" );
				});
					
		       
				
				var json_ck = obj.invoiceLists;
				if(json_ck!=null && json_ck.length!=0){
					var gridObj_ck = $.fn.bsgrid.init('searchTable_xgps', {				 
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
				$("#fpsqcx_xq_table").css('min-width','774px');
			},
			chaKandetail : function(record, rowIndex, colIndex, options){
				if(colIndex == 3){
					return comm.formatMoneyNumber(record.invoiceAmount);
				}
			},
			psValid : function(){
				return $("#xgps_form").validate({
					 rules : {
						postWay : {
							required : true
						},
						expressName: {
							required : true
						},
						trackingNo: {
							required : true
						}
					},
					messages : {
						postWay : {
							required : comm.lang("systemBusiness").selectPostWay
						},
						expressName: {
							required : comm.lang("systemBusiness").enterExpressName
						},
						trackingNo: {
							required : comm.lang("systemBusiness").entertrackingNo
						}
					},
					errorPlacement : function (error, element) {
						$(element).attr("title", $(error).text()).tooltip({
							tooltipClass: "ui-tooltip-error",
							destroyFlag : true,
							destroyTime : 3000,
							position : {
								my : "left+2 top+30",
								at : "left top"
							}
						}).tooltip("open");
						$(".ui-tooltip").css("max-width", "230px");
					},
					success : function (element) {
						$(element).tooltip();
						$(element).tooltip("destroy");
					}
				});
			}
	};
});