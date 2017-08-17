define(['text!debitTpl/jykgl/ykzbdk.html',
	'text!debitTpl/jykgl/zbdk_dialog.html',
        'debitDat/debit',
        'debitSrc/debitComm',
		'debitLan'],function(ykzbdkTpl, zbdk_dialogTpl,dataModoule,debitComm){
	var ykzbdkFun = {
		ykBsGrid:null,	//余款分页函数
		showPage : function(){
			$('#busibox').html(_.template(ykzbdkTpl));	
			
			//时间控件		    
		    comm.initBeginEndTime('#search_startDate','#search_endDate');

		    //余款分页查询
			$("#queryykzbdk").click(function(){
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
				ykzbdkFun.ykBsGrid = dataModoule.listDebit("searchTable",params,ykzbdkFun.detail,null,null);
			});
		},
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex == 2){
				return comm.formatMoneyNumber(record.payAmount);
			}else if(colIndex == 4){
				//付款用途
				var cUse=debitComm.collectUse(record.payUse);
				return '<span title='+cUse+'>'+cUse+'</span>' ;
			}else if(colIndex == 6){
				return comm.formatMoneyNumber(record.linkAmount);
			}else if(colIndex == 7){
				return comm.formatMoneyNumber(record.unlinkAmount);
			}else if(colIndex == 8){
				var link1 = $('<a>转不动款</a>').click(function(e) {
					ykzbdkFun.zbdk(record);					
				}) ;			
				return link1;				
			}			
		},
		/**
		 * 转不动款
		 */
		zbdk : function(obj){
			//付款用途
			obj.payUserString = debitComm.collectUse(obj.payUse);
			$('#dialogBox').html(_.template(zbdk_dialogTpl, obj));
			
			/*弹出框*/
			$( "#dialogBox" ).dialog({
				title:"转不动款内容",
				width:"630",
				height:'430',
				modal:true,
				buttons:{ 
					"确定":function(){
						var dself=this;
						
						//表单验证
						if(!ykzbdkFun.validateDataTrans()){return;}	
						
						//双签验证
						comm.verifyDoublePwd($("#txtReviewUser").val(),$("#txtReviewPwd").val(),$("#verificationMode").attr("optionvalue"),function(rsp){
							//保存双签客户号
							$("#dblOptCustId").val(rsp.data);
							
							//转不动款
							var params=$("#zbdk_form").serializeJson();
							dataModoule.turnNotMove(params,function(res){
								$(dself).dialog( "destroy" );
								 comm.yes_alert("转不动款完成",400);
								$("#queryykzbdk").click();		
							});		
						});
					},
					"取消":function(){
						 $(this).dialog( "destroy" );
					}
				}
			});
			/*end*/	
			
			//双签验证方式
			comm.initSelect("#verificationMode", comm.lang("common").verificationMode, 180).change(function(e){
				var val = $(this).attr('optionValue');
				$("#txtReviewUser,#txtReviewPwd").val("");
				switch(val){
					case '1':
						$('#fhy_userName, #fhy_password').removeClass('none');
						$('#verificationMode_prompt').addClass('none');
						break;	
						
					case '2':
						$('#fhy_userName, #fhy_password').addClass('none');
						$('#verificationMode_prompt').removeClass('none');
						break;
						
					case '3':
						$('#fhy_userName, #fhy_password').addClass('none');
						$('#verificationMode_prompt').removeClass('none');
						break;
					case '':
						$('#fhy_userName, #fhy_password').addClass('none');
						$('#verificationMode_prompt').addClass('none');
						break;
				}
			});
		},
		/**
		 * 转不动款-表单校验
		 */
		validateDataTrans : function(){
			return comm.valid({
				formID : '#zbdk_form',
				rules : {
					debitId : {
						required : true
					},
					balanceRecord : {
						required : true
					},
					txtReviewUser : {
						required : function(){
							var $verificationMode = $("#verificationMode").attr("optionvalue");	//双签验证类型
							if($verificationMode!="1"){return false;}	//非用户密码验证时，不验证帐号
							return true;
						}
					},
					txtReviewPwd : {
						required : function(){
							var $verificationMode = $("#verificationMode").attr("optionvalue");	//双签验证类型
							if($verificationMode!="1"){return false;}	//非用户密码验证时，不验证密码
							return true;
						}
					},
					debitStatus : {
						required : true
					},
					verificationMode:{
						required : true
					}
				},
				messages : {
					debitId : {
						required : comm.lang("debit")[35020]
					},
					balanceRecord : {
						required : comm.lang("debit")[35032]
					},
					txtReviewUser : {
						required :  comm.lang("debit")[35034]
					},
					txtReviewPwd : {
						required :  comm.lang("debit")[35035]
					},
					debitStatus : {
						required :  comm.lang("debit")[35022]
					},
					verificationMode : {
						required : comm.lang("common").select_verificationMode_prompt
					}
				}
			});
		}
	}
	
	return ykzbdkFun;
});