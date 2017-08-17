define(['text!safeSetTpl/xgjymm/xgjymm.html','safeSetDat/safeSet','safeSetLan'],function(xgjymmTpl,safeSet){
	return {
		showPage : function(){
			var self = this;
			$('#busibox').html(_.template(xgjymmTpl));
			
			//获取交易密码配置
			safeSet.querymmpz({},function(res){
				var tradingPasswordLength=res.data.tradingPasswordLength;
				//密码长度限制
				$("#sPasswordRule").html(tradingPasswordLength);
				$("#txtOldTradingPassword,#txtNewTradingPassword,#txtConfirmTradingPassword").attr("maxlength",tradingPasswordLength);
			});
			
			/** 确认按钮事件 */
			$('#btn_xgjymm_qr').click(function(){
				if (!self.validateData()) {
					return false;
				}
				
				//密码格式验证
				if (!self.pwdFormatChk()) {
					return false;
				} 
				
				//获取token
				comm.getToken(function(rsp){
					var pwdToken=rsp.data;
					//数据验证通过
					var $oldPassword=comm.tradePwdEncrypt($("#txtOldTradingPassword").val(),pwdToken);//旧交易密码
					var $newPassword=comm.tradePwdEncrypt($("#txtNewTradingPassword").val(),pwdToken);//新交易密码
					var $confirmPassword=comm.tradePwdEncrypt($("#txtConfirmTradingPassword").val(),pwdToken);//确认交易密码
					var updateParam={
							"randomToken":pwdToken,
							"oldTradingPassword":$oldPassword,
							"newTradingPassword":$newPassword,
							"confirmTradingPassword":$confirmPassword
						};
					
					safeSet.updatejymm(updateParam,function(res){
						$("#txtOldTradingPassword,#txtNewTradingPassword,#txtConfirmTradingPassword").val("");
						comm.alert({imgClass: 'tips_i' ,content:comm.lang("safeSet").update_trading_password_success });
					});
				});
				
			});
				
		},
		validateData : function(){
			// 重复密码校验
			jQuery.validator.addMethod('samePwd', function(value, element) {
				var oldPwd = $('#txtOldTradingPassword').val();
				return this.optional(element) || (value != oldPwd);
			}, '新密码与旧密码重复');
			
			return comm.valid({
				formID : '#xgjymm_form',
				rules : {
					txtOldTradingPassword : {
						required : true,
					},
					txtNewTradingPassword : {
						required : true,
						digits:true,
						minlength : $("#txtNewTradingPassword").attr("maxlength")
					},
					txtConfirmTradingPassword : {
						required : true,
						digits:true,
						samePwd :true,
						equalTo : "#txtNewTradingPassword",
						minlength :  $("#txtNewTradingPassword").attr("maxlength")
					}
				},
				messages : {
					txtOldTradingPassword : {
						required : comm.lang("safeSet").input_old_trading_password
					},
					txtNewTradingPassword : {
						required : comm.lang("safeSet").input_new_trading_password,
						digits:comm.lang("safeSet").pwdDigits,
						minlength : comm.lang("safeSet").trading_password_length,
					},
					txtConfirmTradingPassword : {
						required : comm.lang("safeSet").input_confirm_trading_password,
						digits:comm.lang("safeSet").pwdDigits,
						samePwd:comm.lang("safeSet").samePassword,
						equalTo: comm.lang("safeSet").trading_password_inconsistent,
						minlength : comm.lang("safeSet").trading_password_length,
					}
				}
			});
		},
		pwdFormatChk:function(){
			/** 密码数据格式验证 */
			var $inputNewTradePwd=$("#txtNewTradingPassword");
			var $tradePwdLen=$inputNewTradePwd.attr("maxlength")-1;//密码长度
			var $newTradePwd=$inputNewTradePwd.val();	//密码内容
			
			//匹配6位顺增或顺降  
			eval("var increaseReg =/^(?:(?:0(?=1)|1(?=2)|2(?=3)|3(?=4)|4(?=5)|5(?=6)|6(?=7)|7(?=8)|8(?=9)){"+$tradePwdLen+"}|(?:9(?=8)|8(?=7)|7(?=6)|6(?=5)|5(?=4)|4(?=3)|3(?=2)|2(?=1)|1(?=0)){"+$tradePwdLen+"})\\d$/;"); 
			if(increaseReg.test($newTradePwd)){
	        	comm.alert({width:500,imgClass: 'tips_i' ,content:comm.lang("safeSet").trad_pwd_increase_error });
	        	return false;
	        }
	          
	        //匹配6位重复数字  
	        eval("var repeatedReg =/^([\\d])\\1{"+$tradePwdLen+"}$/;"); 
	        if(repeatedReg.test($newTradePwd)){
	        	comm.alert({width:500,imgClass: 'tips_i' ,content:comm.lang("safeSet").trad_pwd_number_repeated_error });
	        	return false;
	        }
	        
	        return true;
		}//密码格式验证
	}
}); 

 