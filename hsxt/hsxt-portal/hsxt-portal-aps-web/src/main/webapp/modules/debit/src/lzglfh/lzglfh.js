define(['text!debitTpl/lzglfh/lzglfh.html',
		'text!debitTpl/lzglfh/lzglfh_fh.html',
		'text!debitTpl/lzglfh/qrfh_dialog.html',
		'debitDat/debit',
        'commDat/common',
        'debitSrc/debitComm',
		'debitLan'],function(lzglfhTpl, lzglfh_fhTpl, lzglfh_qrfh_dialogTpl,dataModoule,common,debitComm){
	var lzglfhFun={
		lzglfhBsGrid:null,
		reqRemark:null,
		showPage : function(){
			$('#busibox').html(_.template(lzglfhTpl));	
			
			//初始化控件值		    
		    comm.initBeginEndTime('#search_startDate','#search_endDate');
            comm.initSelect('#orderType', comm.lang("debit").orderType);
			
            //分页查询
			$("#lzglfh_query").click(function(){
				if(!comm.queryDateVaild("search_form").form()){
					return;
				}
				var params={
						search_startDate:$("#search_startDate").val(),
						search_endDate:$("#search_endDate").val(),
						search_orderType:$("#orderType").attr("optionvalue"),
						search_hsResNo:$("#hsResNo").val(),
						search_companyName:$("#companyName").val()
				};
				lzglfhFun.lzglfhBsGrid = dataModoule.noApproveList("lzglfh_searchTable",params,lzglfhFun.grid,lzglfhFun.workOrderHangers,lzglfhFun.workOrderRefuse);
			});				
		},
		grid : function(record, rowIndex, colIndex, options){
			if(colIndex == 4){
				return comm.lang("debit").orderType[record.orderType];
			}else if(colIndex == 5){
				return comm.formatMoneyNumber(record.orderOriginalAmount-record.orderDerateAmount);
			}else if(colIndex == 6){
				return comm.formatMoneyNumber(record.orderCashAmount);
			}else if(colIndex == 8){
				var link1 = $('<a>复核</a>').click(function(e) {
					lzglfhFun.fuHe(record);	
				});
				return link1;
			}			
		},
		/**
		 * 复核
		 */
		fuHe : function(obj){
			$('#lzglfhTpl').addClass("none");
			$('#lzglfh_operation_tpl').removeClass("none");
			$('#lzglfh_operation_tpl').html(_.template(lzglfh_fhTpl));
			comm.liActive_add($('#fh'));
			
			//返回
			$('#fh_back').click(function(){
				$('#lzglfhTpl').removeClass("none");
				$('#lzglfh_operation_tpl').addClass("none");
				$("#lzlrfh_operation_tpl").html("");
				
				comm.liActive_add($('#lzglfh'));	
				$("#fh").addClass('tabNone');
			});
			
			//订单信息
			var data = {
				hsResNo	:obj.hsResNo,
				custName:obj.custName,
				orderOperator:obj.orderOperator,
				orderNo:obj.orderNo,
				orderTypeName:comm.lang("debit").orderType[obj.orderType],
				orderStatusName:comm.lang("debit").orderStatus[obj.orderStatus],
				orderAmount:comm.formatMoneyNumber(obj.orderOriginalAmount-obj.orderDerateAmount),
				orderCashAmount:comm.formatMoneyNumber(obj.orderCashAmount),
				orderTime:obj.orderTime
			};

			var gridObj = $.fn.bsgrid.init('searchTable_1', {				 
				pageSizeSelect: true ,
				pageSize: 10 ,
				stripeRows: true,  //行色彩分隔 
				displayBlankRows: false ,   //显示空白行
				localData:[data]
			});
			
             //显示待关联的临账信息
			$("#totalLinkAmount").text(comm.formatMoneyNumber(obj.orderCashAmount));
			 gridObj2=dataModoule.findDebitLinkByOrderNo("searchTable_2",{search_orderNo:obj.orderNo},lzglfhFun.detail);	
			
			//确认关联复核			
			$('#qrfh_btn').click(function(){
				var debitIds=new Array();
				var useEntCustNames=new Array();
				var linkAmount=new Array();
				var checkVals=gridObj2.getAllRecords();
				 $.each(checkVals, function(i,val){      
				    debitIds.push(val.debitId);
				    linkAmount.push(val.linkAmount);
				    useEntCustNames.push(val.useEntCustName);
				  });
				 obj.useEntCustNames=useEntCustNames;
				 obj.debitIds=debitIds.join(',');
				 obj.linkAmount=linkAmount;
				 obj.reqRemark=lzglfhFun.reqRemark;
				$('#dialogBox').html(_.template(lzglfh_qrfh_dialogTpl,obj));	
				
				/*弹出框*/
				$("#dialogBox").dialog({
					title:"复核确认",
					width:"630",
					height:'425',
					modal:true,
					buttons:{ 
						"确定":function(){	
							var diaSelf=this;
							if(!lzglfhFun.validateDataAppr()){
								return;
							}
							
							var params=$("#lzglfh_qrfh_form").serializeJson();
							dataModoule.approveDebitLink(params,function(res){
								comm.alert({imgClass: 'tips_yes' ,content:"临账关联审核完成",callOk:function(){
									$(diaSelf).dialog("destroy");
									$('#fh_back').click();
									lzglfhFun.lzglfhBsGrid.refreshPage();
									
								}});
							});		
						},
						"取消":function(){
							 $( this ).dialog( "destroy" );
						}
					}
				});
			});
		},
		detail : function(record, rowIndex, colIndex, options){
			lzglfhFun.reqRemark=record.auditRecord;
			if(colIndex == 2){
				return comm.formatMoneyNumber(record.payAmount);
			}else if(colIndex == 4){
				//付款用途
				var use = debitComm.collectUse(record.payUse);
				return '<span title='+use.join("/")+'>'+use.join("/")+'</span>' ;
				
			}else if(colIndex == 6){
				return comm.formatMoneyNumber(record.unlinkAmount);
			}else if(colIndex == 7){
				return comm.formatMoneyNumber(record.linkAmount);
			}
		},
		/**
		 * 表单校验
		 */
		validateDataAppr : function(){
			return comm.valid({
				formID : '#lzglfh_qrfh_form',
				rules : {
					debitIds : {
						required : true
					},
					apprRemark : {
						required : true
					},
					orderNo : {
						required : true
					},
					cashAmount : {
						required : true
					},
					linkAmount : {
						required : true
					}
				},
				messages : {
					debitIds : {
						required : comm.lang("debit")[35020]
					},
					apprRemark : {
						required : comm.lang("debit")[35033]
					},
					orderNo : {
						required :  comm.lang("debit")[35024]
					},
					cashAmount : {
						required :  comm.lang("debit")[35026]
					},
					linkAmount : {
						required :  comm.lang("debit")[35031]
					}
				}
			});
		},
		/**
		 * 工单挂起
		 */
		workOrderHangers: function(record, rowIndex, colIndex, options){
			if(colIndex == 9){
				return comm.workflow_operate('挂起', '临账关联复核业务', function(){
					require(["workoptSrc/gdgq"],function(gdgq){
						gdgq.guaQi(record.orderNo,lzglfhFun.lzglfhBsGrid);
					});
				});
			}
		},
		/**
		 * 工单拒绝受理
		 */
		workOrderRefuse: function(record, rowIndex, colIndex, options){
			if(colIndex == 9){
				return comm.workflow_operate('拒绝受理', '临账关联复核业务', function(){
					common.workTaskRefuseAccept({"bizNo":record.orderNo},function(rsp){
						comm.alert({imgClass: 'tips_yes' ,content:comm.lang("debit")[22000],callOk:function(){
							lzglfhFun.lzglfhBsGrid.refreshPage();
						}});
					});
				});
			}
		}
	} 
	return lzglfhFun;
});