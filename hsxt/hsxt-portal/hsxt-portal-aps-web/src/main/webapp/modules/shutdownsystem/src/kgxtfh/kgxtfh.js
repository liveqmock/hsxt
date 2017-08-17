define(["text!shutdownsystemTpl/kgxtfh/kgxtfh.html",
"text!shutdownsystemTpl/kgxtfh/fh.html",
'shutdownsystemDat/shutdownsystem',
'commDat/common',
'shutdownsystemLan'],function(kgxtfhTpl,fhTpl,shutdownsystem,common){
	var self= {
		kgxtfhGrid:null,
		showPage : function(){
			$('#busibox').html(_.template(kgxtfhTpl));	
			/*下拉列表*/
			comm.initSelect('#company_type_1', comm.lang("shutdownsystem").custType);
			comm.initSelect('#fh_type', comm.lang("shutdownsystem").applyType,null,null,{name:comm.lang("shutdownsystem").select_review_type, value:""});
			$("#qry_btn_2").click(function(){
				var params={
						search_companyResNo:$("#companyResNo").val(),
						search_companyName:$("#companyName").val(),
						search_linkman:$("#linkman").val(),
						search_custType:$("#company_type_1").attr("optionvalue"),
						search_applyType:$("#fh_type").attr("optionvalue")
				};
				self.kgxtfhGrid=shutdownsystem.queryCloseOpenEnt4Appr("searchTable111",params,self.detail,self.del);
			});
			//$("#qry_btn_2").click();
		},
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex==5){
				return comm.lang('shutdownsystem').custType[record.custType];
			}else if(colIndex==6){
				return comm.lang('shutdownsystem').applyType[record.applyType];
			}else if(colIndex==8){
				var link1 = $('<a>复核</a>').click(function(e) {
					shutdownsystem.getDetail({applyId:record.applyId},function(res){
						self.fuHe(res.data);
					});					
				});
				return  link1 ;
			}else if(colIndex==9){
				/*var link1 = $('<a>挂起</a>').click(function(e) {
					comm.i_confirm(comm.lang('shutdownsystem').suspendApproval,function(){
						shutdownsystem.updateTask({bizNo:record.applyId,taskStatus:4,exeCustId:comm.getCookie('custId')},function(res){
							if(res.retCode==22000){
								$("#qry_btn_2").click();
								comm.warn_alert(comm.lang('shutdownsystem').suspendSucess);
							}
						});	
					});
				});
				return  link1 ;*/
				
				return comm.workflow_operate('挂起', '开关系统复核业务', function(){
					require(["workoptSrc/gdgq"],function(gdgq){
						gdgq.guaQi(record.applyId,self.kgxtfhGrid);
					});
				});
				
			}
			
		},
		del : function(record, rowIndex, colIndex, options){
			if(colIndex==9){
				/*var link1 = $('<a>拒绝受理</a>').click(function(e) {
					comm.i_confirm(comm.lang('shutdownsystem').refuseApproval,function(){
						shutdownsystem.updateTask({bizNo:record.applyId,taskStatus:1,exeCustId:comm.getCookie('custId')},function(res){
							if(res.retCode==22000){
								$("#qry_btn_2").click();
								comm.warn_alert(comm.lang('shutdownsystem').refuseSucess);
							}
						});
					});
				});
				return  link1 ;*/
				
				return comm.workflow_operate('拒绝受理', '开关系统复核业务', function(){
					common.workTaskRefuseAccept({"bizNo":record.applyId},function(rsp){
						comm.alert({imgClass: 'tips_yes' ,content:'操作成功',callOk:function(){
							self.kgxtfhGrid.refreshPage();
						}});
					});
				});
			}
		},
		
		fuHe : function(obj){
			obj.custTypeName=comm.lang('shutdownsystem').custType[obj.custType];
			if(obj.applyType == '1'){
				comm.liActive_add($('#gbqyfh'));	
			}
			else if(obj.applyType == '2'){
				comm.liActive_add($('#kqqyfh'));	
			}
			
			$('#busibox').html(_.template(fhTpl,obj));	
			
			$('#cancel_kgxtfh').triggerWith('#kgxtfh');
			
			$("#kgxtfh_submit").click(function(){
				if (!self.validateData()) {
					return;
				}
				var params = $('#kgxtfh_form').serializeJson();
				var isPass = $.trim($('input[name="isPass"]:checked').val());
				if(comm.isEmpty(isPass)){
					comm.warn_alert('请勾选复核结果');
					return;
				}
				shutdownsystem.approve(params,function(res){
					
					//复核类型
					var applyTypePrompt=comm.lang('shutdownsystem').applyType[obj.applyType]+comm.lang('shutdownsystem').apprResult[isPass];
					
					comm.yes_alert(applyTypePrompt,400);
					$('#cancel_kgxtfh').click();
				});
			});
		},
		validateData : function(){
			return comm.valid({
				formID : '#kgxtfh_form',
				rules : {
					apprRemark:{
						required : true,
						rangelength:[1,300]
					}
				},
				messages : {
					apprRemark:{
						required : comm.lang("shutdownsystem").apprRemarkReired,
						rangelength:comm.lang("shutdownsystem").remarkLength
					}
				}
			});
		}
	}
	return self;
});