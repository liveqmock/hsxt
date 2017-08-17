define(["text!debitTpl/lztkfh/lztkfh.html",
		"text!debitTpl/lztkfh/lztkfh_fh.html",
		"text!debitTpl/lztkfh/tkfh_dialog.html",
		'debitDat/debit',
        'commDat/common',
		'debitLan'],function(lztkfhTpl, lztkfh_fhTpl, tkfh_dialogTpl,dataModoule,common){
	var lztkfhFun = {
		lztkfhBsGrid : null,
		showPage : function(){
			$('#busibox').html(_.template(lztkfhTpl));	
			//时间控件		    
		    comm.initBeginEndTime('#search_startDate','#search_endDate');
			
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
				lztkfhFun.lztkfhBsGrid = dataModoule.queryDebitTaskListPage("searchTable",params,lztkfhFun.detail,lztkfhFun.workOrderHangers,lztkfhFun.workOrderRefuse);
			});
		},
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex == 3){
				return comm.formatMoneyNumber(record.payAmount);
			}else if(colIndex == 5){
				if(!record.payUse){
					return "";
				}else{
					var payUses=record.payUse.split(',');
					var payUseString=new Array();
					for(var i=0;i<payUses.length;i++)
					{
						payUseString.push(comm.lang("debit").payUse[payUses[i]]);
					}
					return '<span title='+payUseString.join("/")+'>'+payUseString.join("/")+'</span>';
				}
			}else if(colIndex == 7){
				return comm.formatMoneyNumber(record.unlinkAmount);
			}else if(colIndex == 8){
				return comm.formatMoneyNumber(record.refundAmount);
			}else if(colIndex == 9){				
				var link1 = $('<a>复核</a>').click(function(e) {
					lztkfhFun.fuHe(record);	
				 });	
				return link1;
			}								
		},
		/** 复核 */
		fuHe : function(obj){
			$('#lztkfhTpl').addClass('none');
			$('#lztkfh_operation_tpl').removeClass('none');
			comm.liActive_add($('#tksqfh'));	
			$('#tkfhDiv').removeClass('none');

			if(!obj.payUse){
				obj.payUseString="";
			}else{
				var payUses=obj.payUse.split(',');
				var payUseString=new Array();
				for(var i=0;i<payUses.length;i++)
				{
					payUseString.push(comm.lang("debit").payUse[payUses[i]]);
				}
				obj.payUseString=payUseString.join("/");
			}
			obj.payType=comm.lang("debit").payType[obj.payType];
			obj.payAmountText = comm.formatMoneyNumber(obj.payAmount);
			$('#lztkfh_operation_tpl').html(_.template(lztkfh_fhTpl, obj));
			//初始化小图片
			if(obj.fileId){
				$("#img").attr("src", comm.getFsServerUrl(obj.fileId));
				comm.initPicPreview("#img", obj.fileId, "");
			}
			
			//退款复核确认
			$('#tkfh_btn').click(function(){
				$('#dialogBox').html(_.template(tkfh_dialogTpl,obj));	
				
				/*弹出框*/
				$( "#dialogBox" ).dialog({
					title:"复核信息",
					width:"400",
					height:'220',
					modal:true,
					buttons:{ 
						"确定":function(){
							if(!lztkfhFun.validateDataAppr()){
								return;
							}
							var params=$("#tkfh_form"). serializeJson();
							$( this ).dialog( "destroy" );
							dataModoule.approveRefundDebit(params,function(res){
								 comm.alert({imgClass: 'tips_yes' ,content: "退款复核完成",callOk:function(){
									 $('#fh_back').click();
								 }});
							});		
						},
						"取消":function(){
							 $( this ).dialog( "destroy" );
						}
					}
				});
			});
			
			//返回按钮
			$("#fh_back").click(function(){
				$('#lztkfh_operation_tpl').addClass('none');
				$('#lztkfhTpl').removeClass('none');
				$("#lztkfh").siblings().addClass('tabNone');
				$("#tkfhDiv").addClass("none");
				
				//刷新分页数据
				lztkfhFun.lztkfhBsGrid.refreshPage();
			});
		},
		/**
		 * 退款复核-表单校验
		 */
		validateDataAppr : function(){
			return comm.valid({
				formID : '#tkfh_form',
				rules : {
					debitId : {
						required : true
					},
					apprRemark : {
						required : true
					},
					status : {
						required : true
					}
				},
				messages : {
					debitId : {
						required : comm.lang("debit")[35020]
					},
					apprRemark : {
						required : comm.lang("debit")[35033]
					},
					status : {
						required :  comm.lang("debit")[35022]
					}
				}
			});
		},
		/**
		 * 工单挂起
		 */
		workOrderHangers: function(record, rowIndex, colIndex, options){
			if(colIndex == 10){
			/*	return comm.workflow_operate('挂起', '临账退款复核业务', function(){
					common.workTaskHangUp({"bizNo":record.debitId},function(rsp){
						comm.alert({imgClass: 'tips_yes' ,content:comm.lang("debit")[22000],callOk:function(){
							lztkfhFun.lztkfhBsGrid.refreshPage();
						}});
					});
				});*/
				
				return comm.workflow_operate('挂起', '临账退款复核业务', function(){
					require(["workoptSrc/gdgq"],function(gdgq){
						gdgq.guaQi(record.debitId,lztkfhFun.lztkfhBsGrid);
					});
				});
			}
		},
		/**
		 * 工单拒绝受理
		 */
		workOrderRefuse: function(record, rowIndex, colIndex, options){
			if(colIndex == 10){
				return comm.workflow_operate('拒绝受理', '临账退款复核业务', function(){
					common.workTaskRefuseAccept({"bizNo":record.debitId},function(rsp){
						comm.alert({imgClass: 'tips_yes' ,content:comm.lang("debit")[22000],callOk:function(){
							lztkfhFun.lztkfhBsGrid.refreshPage();
						}});
					});
				});
			}
		}
	} 
	
	return lztkfhFun;
	
});