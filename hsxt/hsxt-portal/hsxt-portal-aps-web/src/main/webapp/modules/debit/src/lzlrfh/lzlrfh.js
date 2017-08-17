define(["text!debitTpl/lzlrfh/lzlrfh.html",
		"text!debitTpl/lzlrfh/lzlrfh_ck.html",
		"text!debitTpl/lzlrfh/fh_dialog.html",
		'debitDat/debit',
        'commDat/common',
		'debitLan'],function(lzlrfhTpl, lzlrfh_ckTpl, lrfh_dialogTpl,dataModoule,common){
	var lzlrfhFun = {
		lzlrfhBsGrid : null,
		showPage : function(){
			$('#busibox').html(_.template(lzlrfhTpl));
			//时间控件		    
		    comm.initBeginEndTime('#search_startDate','#search_endDate');
			/*end*/	
						
			$("#btn-search").click(function(){
				if(!comm.queryDateVaild("search_form").form()){
					return;
				}
				var params={
						search_payStartDate:$("#search_startDate").val(),
						search_payEndDate:$("#search_endDate").val(),
						search_payEntCustName:$("#payEntCustName").val(),
						search_useEntCustName:$("#useEntCustName").val(),
						search_debitStatus:$("#debitStatus").val()
				};
				
				//临帐录入复核查询
				lzlrfhFun.lzlrfhBsGrid = dataModoule.queryDebitTaskListPage("searchTable",params,lzlrfhFun.detail,lzlrfhFun.workOrderHangers,lzlrfhFun.workOrderRefuse);
			});
		},
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex == 3){
				return comm.formatMoneyNumber(record.payAmount);
			}else if(colIndex == 5){
				if(!record.payUse){
					return "";
				}else{
					var payUse=record.payUse;
					var payUses=payUse.split(',');
					var payUseString=new Array();
					for(var i=0;i<payUses.length;i++)
					{
						payUseString.push(comm.lang("debit").payUse[payUses[i]]);
					}
					return '<span title='+payUseString.join("/")+'>'+payUseString.join("/")+'</span>';
				}
			}else if(colIndex == 7){				
				return comm.lang("debit").debitStatus[record.debitStatus];
			}else if(colIndex == 8){
				var link2 = $('<a>复核</a>').click(function(e) {
					lzlrfhFun.chaKan(record);	
				});	
			   return link2;
				
			}
		},
		/**
		 * 工单挂起
		 */
		workOrderHangers: function(record, rowIndex, colIndex, options){
			if(colIndex == 9){
			   return comm.workflow_operate('挂起', '临账录入复核业务', function(){
					require(["workoptSrc/gdgq"],function(gdgq){
						gdgq.guaQi(record.debitId,lzlrfhFun.lzlrfhBsGrid);
					});
				});
			}
		},
		/**
		 * 工单拒绝受理
		 */
		workOrderRefuse: function(record, rowIndex, colIndex, options){
			if(colIndex == 9){
				return comm.workflow_operate('拒绝受理', '临账录入复核业务', function(){
					common.workTaskRefuseAccept({"bizNo":record.debitId},function(rsp){
						comm.alert({imgClass: 'tips_yes' ,content:comm.lang("debit")[22000],callOk:function(){
							lzlrfhFun.lzlrfhBsGrid.refreshPage();
						}});
					});
				});
			}
		},
		chaKan : function(obj){
			var debitInfo =  $.extend(obj,{});
			$('#lzlrfhTpl').addClass("none");
			$('#lzlrfh_operation_tpl').removeClass("none");
			comm.liActive_add($('#ckxq'));	
			
			if(!debitInfo.payUse){
				debitInfo.payUseString="";
			}else{
				var payUses=debitInfo.payUse.split(',');
				var payUseString=new Array();
				for(var i=0;i<payUses.length;i++)
				{
					payUseString.push(comm.lang("debit").payUse[payUses[i]]);
				}
				debitInfo.payUseString = payUseString.join("/");
			}
			debitInfo.payType=comm.lang("debit").payType[debitInfo.payType];
			debitInfo.payAmountText = comm.formatMoneyNumber(debitInfo.payAmount);
			
			//加载数据
			$('#lzlrfh_operation_tpl').html(_.template(lzlrfh_ckTpl, debitInfo));
			
			if(debitInfo.fileId){
				$("#img").attr("src", comm.getFsServerUrl(debitInfo.fileId));
				comm.initPicPreview("#img", debitInfo.fileId, "");
			}
			
			if(debitInfo.debitStatus == '0'){
				$('#fhDiv').removeClass('none');
				
				//确认复核
				$('#fuhe_btn').click(function(){
					
					$('#dialogBox').html(_.template(lrfh_dialogTpl,debitInfo));	
					
					/*弹出框*/
					$( "#dialogBox" ).dialog({
						title:"复核信息",
						width:"400",
						height:"220",
						modal:true,
						buttons:{ 
							"确定":function(){
								var diaSelf=this;
								if(!lzlrfhFun.validateDataAppr()){
									return;
								}
								var params=$("#lrfh_form").serializeJson();
								
								dataModoule.approveDebit(params,function(res){
									comm.alert({imgClass: 'tips_yes' ,content:"复核完成",callOk:function(){
										$(diaSelf).dialog( "destroy" );
										$('#ck_back').click();
										lzlrfhFun.lzlrfhBsGrid.refreshPage();
									}});
								});										
							},
							"取消":function(){
								 $( this ).dialog( "destroy" );
							}
						}
					});
					/*end*/					
				});									
			}
			
			//返回
			$("#ck_back").on("click",function(){
				$('#lzlrfhTpl').removeClass("none");
				$('#lzlrfh_operation_tpl').addClass("none");
				$("#lzlrfh_operation_tpl").html("");
				
				comm.liActive_add($('#lzlrfh'));	
				$("#lzlrfh").siblings().addClass('tabNone');
				$("#fhDiv").addClass('none');
			});
		},
		/**
		 * 录入复核-表单校验
		 */
		validateDataAppr : function(){
			return comm.valid({
				formID : '#lrfh_form',
				rules : {
					debitId : {
						required : true
					},
					auditRecord : {
						required : true,
						rangelength : [1, 300]
					},
					debitStatus : {
						required : true
					}
				},
				messages : {
					debitId : {
						required : comm.lang("debit")[35020]
					},
					auditRecord : {
						required : comm.lang("debit")[35033],
						rangelength : comm.lang("debit").auditRecordRangelength
					},
					debitStatus : {
						required :  comm.lang("debit")[35022]
					}
				}
			});
		}
	}
	
	return lzlrfhFun;
});