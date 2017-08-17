define(["text!safeSetTpl/dlmm/dlmm.html",
        'safeSetDat/safeSet'],function(tpl,safeSet){
	var dlmmPage = {
		showPage : function(){
			
			$('#busibox').html(_.template(tpl));	
			
			/** 确认按钮事件 */
			$('#btn_xgdlmm_qr').click(function() {
				if (!dlmmPage.validateData()) {
					return false;
				} 
				
				//密码格式验证
				if (!dlmmPage.pwdFormatChk()) {
					return false;
				} 
				
				//获取token
				comm.getToken(null,function(data){
					var token=data.data;
					var $oldPassword=comm.encrypt($("#txtOldPassword").val(),token);
					var $newPassword=comm.encrypt($("#txtNewPassword").val(),token);
					var $confirmPassword=comm.encrypt($("#txtConfirmPassword").val(),token);
					
					//修改参数
					var updatedlmmParam={"randomToken":token,
							"oldPassword":$oldPassword,
							"newPassword":$newPassword,
							"confirmPassword":$confirmPassword};
					safeSet.updatePwd(updatedlmmParam,function(response){
						if (response.retCode ==22000){
							$("#txtOldPassword,#txtNewPassword,#txtConfirmPassword").val("");
							comm.alert({content: comm.lang("safeSet").update_password_success});
						}else if(160359 == response.retCode || 160108 == response.retCode ) {
							comm.error_alert("原登录密码错误，请重新输入！");
							$('#txtOldPassword').val('');
							$('#txtNewPassword').val('');
							$('#txtConfirmPassword').val('');
						}else if(160467 == response.retCode) {
							comm.error_alert(response.resultDesc);
							$('#txtOldPassword').val('');
							$('#txtNewPassword').val('');
							$('#txtConfirmPassword').val('');
						}else{
							comm.alertMessageByErrorCode(comm.lang("safeSet"), response.retCode);
								$('#txtOldPassword').val('');
							$('#txtNewPassword').val('');
							$('#txtConfirmPassword').val('');
						}
					});
				});
			});
		},		
		validateData : function() {
			return comm.valid({
				formID : '#xgdlmm_form',
				rules : {
					txtOldPassword : {
						required : true,
						digits  : true
					},
					txtNewPassword : {
						required : true,
						digits:true,
						rangelength:[6,6]
					},
					txtConfirmPassword : {
						required : true,
						digits:true,
						equalTo : "#txtNewPassword",
						rangelength:[6,6]
					}
				},
				messages : {
					txtOldPassword : {
						required : comm.lang("safeSet").input_old_password,
						digits:comm.lang("safeSet").pwdDigits
					},
					txtNewPassword : {
						required : comm.lang("safeSet").input_new_password,
						digits:comm.lang("safeSet").pwdDigits,
						rangelength : comm.lang("safeSet").password_maxlength
					},
					txtConfirmPassword : {
						required : comm.lang("safeSet").input_confirm_password,
						digits:comm.lang("safeSet").pwdDigits,
						equalTo: comm.lang("safeSet").password_inconsistent,
						rangelength : comm.lang("safeSet").password_maxlength
					}
				}
			});
		},
		pwdFormatChk:function(){
			/** 密码数据格式验证 */
			var $txtNewPwd=$("#txtNewPassword");
			var $newPwd=$txtNewPwd.val();
			var $pwdLen=$txtNewPwd.attr("maxlength")-1;
			
			//匹配6位顺增或顺降  
			eval("var increaseReg =/^(?:(?:0(?=1)|1(?=2)|2(?=3)|3(?=4)|4(?=5)|5(?=6)|6(?=7)|7(?=8)|8(?=9)){"+$pwdLen+"}|(?:9(?=8)|8(?=7)|7(?=6)|6(?=5)|5(?=4)|4(?=3)|3(?=2)|2(?=1)|1(?=0)){"+$pwdLen+"})\\d$/;"); 
	        if(increaseReg.test($newPwd)){
	        	comm.alert({width:450,imgClass: 'tips_i' ,content:comm.lang("safeSet").pwd_increase_error });
	        	return false;
	        }
	          
	        //匹配6位重复数字  
	        eval("var repeatedReg =/^([\\d])\\1{"+$pwdLen+"}$/;"); 
	        if(repeatedReg.test($newPwd)){
	        	comm.alert({width:450,imgClass: 'tips_i' ,content:comm.lang("safeSet").pwd_number_repeated_error });
	        	return false;
	        }
	        
	        return true;
		}//密码格式验证
	};
	return dlmmPage
});