define(['text!invoiceTpl/qysltzsqsh/qysltzspfh.html',
		'text!invoiceTpl/qysltzsqsh/qysltzspfh_fh.html',
		'text!invoiceTpl/qysltzsqsh/qysltzspfh_fh_dialog.html',
		'invoiceDat/invoice',
        'commDat/common',
		'invoiceLan'],function(qysltzspfhTpl, qysltzspfh_fhTpl, qysltzspfh_fh_dialogTpl,dataModoule,common){
	var qysltzspfhFun = {
		sltzfhBsGrid:null,
		showPage : function(){
			$('#busibox').html(_.template(qysltzspfhTpl));	
			
			//分页查询
			$("#slfh_query").click(function(){
				var params=$("#slfh_form").serializeJson();
				qysltzspfhFun.sltzfhBsGrid = dataModoule.approveTaxrateChangeList("searchTable",params,qysltzspfhFun.detail,qysltzspfhFun.workOrderHangers,qysltzspfhFun.workOrderRefuse);
			});
		},
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex==4){
				//return comm.percentage(record.applyTaxrate);
				return (parseFloat(record.applyTaxrate)*100).toFixed(2)+"%";
			}else if(colIndex==6){
				return comm.lang("invoice").status[record.status];
			}else if(colIndex==7 && record.status == '1'){
				var link1 = $('<a>复核</a>').click(function(e) {
				    dataModoule.queryTaxrateChange({applyId :record.applyId},function(res){
				    	var obj=res.data;
						comm.groupOperInfo(obj.createdBy,function(res){
							obj.createdBy=obj.resNo+"_"+res;
							qysltzspfhFun.fuHe(obj);
						});
					});	
			    });
				return link1;
			}
		},
		/**
		 * 工单挂起
		 */
		workOrderHangers: function(record, rowIndex, colIndex, options){
			if(colIndex == 8){
			/*	return comm.workflow_operate('挂起', '企业税率调整复核业务', function(){
					common.workTaskHangUp({"bizNo":record.applyId},function(rsp){
						comm.alert({imgClass: 'tips_yes' ,content:comm.lang("invoice")[22000],callOk:function(){
							qysltzspfhFun.sltzfhBsGrid.refreshPage();
						}});
					});
				});*/
			   
			   return comm.workflow_operate('挂起', '企业税率调整复核业务', function(){
					require(["workoptSrc/gdgq"],function(gdgq){
						gdgq.guaQi(record.applyId,qysltzspfhFun.sltzfhBsGrid);
					});
				});
			}
		},
		/**
		 * 工单拒绝受理
		 */
		workOrderRefuse: function(record, rowIndex, colIndex, options){
			if(colIndex == 8){
				return comm.workflow_operate('拒绝受理', '企业税率调整复核业务', function(){
					common.workTaskRefuseAccept({"bizNo":record.applyId},function(rsp){
						comm.alert({imgClass: 'tips_yes' ,content:comm.lang("invoice")[22000],callOk:function(){
							qysltzspfhFun.sltzfhBsGrid.refreshPage();
						}});
					});
				});
			}
		},		
		/**
		 * 复核
		 */
	   fuHe : function(obj){
			comm.liActive_add($('#fh'));
			$('#qysltzspfhTpl').addClass("none");
			$('#qysltzspfhTpl_operate').removeClass("none");
			
			var date=new Date();
			var nextMonthDay=new Date(date.getFullYear(),date.getMonth()+1,1);
			    nextMonthDay =comm.formatDate(nextMonthDay, 'yyyy-MM-dd');
			obj.nextMonthDay=nextMonthDay;
			obj.currTaxrate=(parseFloat(obj.currTaxrate)*100).toFixed(2)+"%";
			obj.applyTaxrate=(parseFloat(obj.applyTaxrate)*100).toFixed(2)+"%";
			$('#qysltzspfhTpl_operate').html(_.template(qysltzspfh_fhTpl, obj));
			
			if(obj.proveMaterialFile){
				$("#proveFile").attr("href", comm.getFsServerUrl(obj.proveMaterialFile));
			}
			$('#fhxxDiv').removeClass('none');
			
			//返回按钮
			$("#back_qysltzspfh").click(function(){
				comm.liActive_add($('#qysltzspfh'));
				
				$('#fh').addClass("tabNone");
				$('#fhxxDiv').addClass("none");
				$('#qysltzspfhTpl').removeClass("none");
				$('#qysltzspfhTpl_operate').html("").addClass("none");
			});
			
			//确认复核
			$('#fh_btn').click(function(){
				$('#dialogBox').html(_.template(qysltzspfh_fh_dialogTpl,obj));	
				
				/*弹出框*/
				$("#dialogBox").dialog({
					title:"复核信息",
					width:"400",
					height:'250',
					modal:true,
					buttons:{ 
						"确定":function(){
							if(!qysltzspfhFun.validateData()){
								return;
							}
							var params = $("#confirm_review_form").serializeJson();
							$( this ).dialog( "destroy" );
							dataModoule.apprTaxrateChange(params,function(res){
								comm.yes_alert("复核税率调整完成",400);
								qysltzspfhFun.sltzfhBsGrid.refreshPage();
							});		
						},
						"取消":function(){
							 $( this ).dialog( "destroy" );
						}
					}
				});
			});
		},
		/**
		 * 表单校验
		 */
		validateData : function(){
			return comm.valid({
				formID : '#confirm_review_form',
				rules : {
					status : {
						required : true
					},
					applyReason : {
						required : true
					},
					applyId : {
						required : true
					},
					customId : {
						required : true
					},
				},
				messages : {
					status : {
						required : comm.lang("invoice")[35101]
					},
					applyReason : {
						required : comm.lang("invoice")[35102]
					},
					applyId : {
						required :  comm.lang("invoice")[35100]
					},
					customId : {
						required :  comm.lang("invoice")[35104]
					}
				}
			});
		}
	}
	return qysltzspfhFun;
});