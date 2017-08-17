define(['text!businessServiceTpl/fpgl/fpsqcx/fpsqcx.html', 
        'text!businessServiceTpl/fpgl/fpsqcx/fpsqcx_ck.html', 
        'text!businessServiceTpl/fpgl/fpsqcx/fpsqcx_ps.html', 
        'businessServiceSrc/fpgl/fpsq/fpsq',
        'businessServiceDat/businessService',
   	    'businessServiceLan'
		], function(fpsqcxTpl,fpsqcxckTpl,fpsqcxpsTpl,fpsq,dataModoule){
	var fpsqcxFun = {
		allAmount : null,
		showPage : function(){
			$('#busibox').html(fpsqcxTpl) ;	
			
			//开票跳转
			$('#fpgl_qykfp').click(function(){
				if(fpsqcxFun.allAmount==null){
					fpsqcxFun.allAmount==0;
				}
				
				//开票金额不能小于0
				if(parseFloat(fpsqcxFun.allAmount)<=0){
					comm.i_alert("开发票金额需大于0");
					return false;
				}
				
				//检查平台是否有默认银行账户
				cacheUtil.findCacheSystemInfo(function(response){
					dataModoule.findDefaultBankByResNo({companyResNo:response.platResNo}, function(res){
						if(res.data){
							comm.liActive_add($('#fpgl_fpsq'));
							fpsq.showPage(fpsqcxFun.allAmount);
						}else{
							comm.error_alert("地区平台尚未设置默认银行账户，暂无法开票", 400);
						}
					});
				});
			});
			
			//时间区间查询
			comm.initBeginEndTime("#search_startDate", "#search_endDate");
			
			comm.initSelect('#search_status', comm.lang("businessService").delivery_status);
			dataModoule.queryInvoiceSum({invoiceMaker:1},function(res){
				var data=res.data;
				$("#openedAllAmount").text(comm.formatMoneyNumber2(data.openedAllAmount));
				$("#remainAllAmount").text(comm.formatMoneyNumber2(data.remainAllAmount));
				fpsqcxFun.allAmount=data.remainAllAmount;
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
				dataModoule.listInvoice("fpsq_table",params,fpsqcxFun.detail,fpsqcxFun.edit);
			});
			//$("#scpoint_searchBtn").click();
	},
	detail: function(record, rowIndex, colIndex, options){
				if(colIndex == 1){
					return comm.formatMoneyNumber(record.allAmount);
				}else if(colIndex == 2){
					var invoiceType = comm.lang("businessService").invoice_type[record.invoiceType];
					return invoiceType?invoiceType:'';
				}else if(colIndex == 4){
					return comm.lang("businessService").post_way[record.postWay];
				}else if(colIndex == 7){
					return comm.lang("businessService").invoice_status[record.status];
				}else if(colIndex == 9){
					var link2 = $('<a>查看</a>').click(function(e) {
						dataModoule.getInvoiceById({invoiceId:record.invoiceId,invoiceMaker:1},function(res){
							fpsqcxFun.chaKan(res.data, record.status);	
						});					
					});
					return link2;
				}
	},
	edit:function(record, rowIndex, colIndex, options){
		if(colIndex == 9 && record.status=='2'){
			var link2 = $('<a>配送</a>').click(function(e) {
				dataModoule.getInvoiceById({invoiceId:record.invoiceId,invoiceMaker:1},function(res){
					fpsqcxFun.peiSong(res.data);
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
		obj.allAmount=comm.formatMoneyNumber(obj.allAmount);
		/*弹出框*/
		$( "#fpsqcx_xq_dialog" ).dialog({
			title:"企业开发票查询",
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
			if(json_ck!=null && json_ck.length!=0){
				comm.getEasyBsGrid('fpsqcx_xq_table',json_ck,fpsqcxFun.chaKandetail);
			}

		}	
		$("#fpsqcx_xq_table").css('min-width','774px');
	},
	peiSong : function(obj){
		obj.invoiceTypeName=comm.lang("businessService").invoice_type[obj.invoiceType];
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
			if(!fpsqcxFun.psValid().form()){
				return;
			}
			var params = $("#xgps_form").serializeJson();
			dataModoule.changePostWay(params,function(res){
				comm.yes_alert("配送完成",400);
				$( "#fpsqcx_ps_dialog" ).dialog( "destroy" );
				$("#fpsq_table_pt_refreshPage").click();
			});		
		});
		
		/*返回*/	
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
					required : comm.lang("businessService").selectPostWay
				},
				expressName: {
					required : comm.lang("businessService").enterExpressName
				},
				trackingNo: {
					required : comm.lang("businessService").entertrackingNo
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
	}
	return fpsqcxFun;
});