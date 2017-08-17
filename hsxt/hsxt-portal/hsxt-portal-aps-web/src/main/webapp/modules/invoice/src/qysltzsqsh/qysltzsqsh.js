define(['text!invoiceTpl/qysltzsqsh/qysltzsqsh.html',
		'text!invoiceTpl/qysltzsqsh/qysltzsqsh_sp.html',
		'text!invoiceTpl/qysltzsqsh/qysltzsqsh_sp_dialog.html',
		'invoiceDat/invoice',
		'commDat/common',
		'invoiceLan'],function(qysltzsqshTpl, qysltzsqsh_spTpl, qysltzsqsh_sp_dialogTpl,dataModoule,common){
	var qysltzsqshPage = {
		gridObj : null,
		showPage : function(){
			$('#busibox').html(_.template(qysltzsqshTpl));	
			$("#slsh_query").click(function(){
				var params=$("#slsh_form").serializeJson();
				qysltzsqshPage.gridObj = dataModoule.approveTaxrateChangeList("searchTable",params,qysltzsqshPage.detail,qysltzsqshPage.query);
			});
		},
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex==4){
				//return comm.percentage(record.applyTaxrate);
				return (parseFloat(record.applyTaxrate)*100).toFixed(2)+"%";
			}else if(colIndex==6){
				return comm.lang("invoice").status[record.status];
			}else if(colIndex==7 && record.status == '0'){
				var link1 = $('<a>审批</a>').click(function(e) {
					dataModoule.queryTaxrateChange({applyId :record.applyId},function(res){
						var obj=res.data;
						comm.groupOperInfo(obj.createdBy,function(res){
							obj.createdBy=obj.resNo+"_"+res;
							qysltzsqshPage.shenPi(obj);
						});
					});
			   });
               return link1;
			}
			else if(colIndex == 8)
			{
	        	return comm.workflow_operate('拒绝受理', '企业税率调整申请审核业务', function(){
					common.workTaskRefuseAccept({"bizNo":record.applyId},function(rsp){
						comm.alert({imgClass: 'tips_yes' ,content:comm.lang("invoice")[22000],callOk:function(){
							qysltzsqshPage.gridObj.refreshPage();
						}});
					});
				});
			}
		},
		query : function(record, rowIndex, colIndex, options){
			if(colIndex == 8){
			   return comm.workflow_operate('挂起', '企业税率调整申请审核业务', function(){
					require(["workoptSrc/gdgq"],function(gdgq){
						gdgq.guaQi(record.applyId,qysltzsqshPage.gridObj);
					});
				});
			}
		},
		shenPi : function(obj){
			$("#qysltzsqshTpl").hide();
			$('#qysltzsqsh_operation_tpl').show();
			
			comm.liActive_add($('#sp'));
			obj.currTaxrate=(parseFloat(obj.currTaxrate)*100).toFixed(2)+"%";
			obj.applyTaxrate=(parseFloat(obj.applyTaxrate)*100).toFixed(2)+"%";
			$('#qysltzsqsh_operation_tpl').html(_.template(qysltzsqsh_spTpl, obj));
			if(obj.proveMaterialFile){
				$("#proveFile").attr("href", comm.getFsServerUrl(obj.proveMaterialFile));
			}
			$('#spxxDiv').removeClass('none');
			$('#sp_btn').click(function(){
				$('#dialogBox').html(_.template(qysltzsqsh_sp_dialogTpl,obj));	
				
				/*弹出框*/
				$( "#dialogBox" ).dialog({
					title:"审批信息",
					width:"400",
					height:'220',
					modal:true,
					buttons:{ 
						"确定":function(){
							if(!qysltzsqshPage.validateData()){
								return;
							}
							var params = $("#sqsp_form"). serializeJson();
							$( this ).dialog( "destroy" );
							dataModoule.apprTaxrateChange(params,function(res){
								comm.yes_alert("初审税率调整完成",400);
								//$('#back_qysltzsqsh').click();
							});		
						},
						"取消":function(){
							 $( this ).dialog( "destroy" );
						}
					}
				});
				/*end*/	
			});
			
			//返回
			$("#back_qysltzsqsh").click(function(){
				//显示列表元素
				$("#qysltzsqshTpl").show();
				//清空查看元素并隐藏
				$('#qysltzsqsh_operation_tpl').html("").hide();
				//选中查询元素
				comm.liActive_add($('#qysltzsqsh'));
				//隐藏
				$("#sp").addClass("tabNone");
				$("#spxxDiv").addClass("none");
				
				//刷新列表
				qysltzsqshPage.gridObj.refreshPage();
			});
			//$('#back_qysltzsqsh').triggerWith('#qysltzsqsh');	
		},
		/**
		 * 表单校验
		 */
		validateData : function(){
			return comm.valid({
				formID : '#sqsp_form',
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
		
	};
	return qysltzsqshPage
});