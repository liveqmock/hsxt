define(["text!safeSetTpl/czjymm/jymmcs.html","safeSetDat/safeSet","safeSetLan"],function(tpl,safeSet){
	return {
		showPage : function(){
			var self = this;
			$('#busibox').html(_.template(tpl));
			
			//获取交易密码配置
			safeSet.querymmpz({},function(res){
				var tradingPasswordLength=res.data.tradingPasswordLength;
				//密码长度限制
				$("#sPasswordLength").html(tradingPasswordLength);
				$("#txtNewTradePwd,#txtConfirmTradePwd").attr("maxlength",tradingPasswordLength);
			});
			
			//取消重置
			$('#btn_jymmcs_qxcz').triggerWith('#xtaq_czjymm');
			
			//提交
			$('#btn_jymmcs_tj').click(function(){
				if (!self.validateData()) {
					return;
				}
				
				//密码格式验证
				if (!self.pwdFormatChk()) {
					return false;
				} 
				
				
				//获取token
				comm.getToken(function(rsp){
					var token=rsp.data;
				
					//数据验证通过
					var $authCode=$("#txtAuthCode").val();//授权码
					var $newTradePwd=comm.tradePwdEncrypt($("#txtNewTradePwd").val(),token);//新交易密码
					var $confirmTradePwd=comm.tradePwdEncrypt($("#txtConfirmTradePwd").val(),token);//确认交易密码
					var resetParam={
							"randomToken":token,
							"authCode":$authCode,
							"newTradingPassword":$newTradePwd,
							"confirmTradingPassword":$confirmTradePwd
						};
					
					safeSet.resetjymmcz(resetParam,function(res){
						comm.alert({
							content: comm.lang("safeSet").set_trad_pwd_success,
							width: 590,
							callOk: function(){
								//跳转至明细查询页面
								$("#xtaq_czjymm").trigger('click');
							}
						});
					});
				});
			
			});
		},
		validateData : function(){
			return comm.valid({
				formID : '#jymmcs_form',
				rules : {
					txtAuthCode : {
						required : true,
					},
					txtNewTradePwd : {
						required : true,
						digits:true,
						minlength : $("#txtNewTradePwd").attr("maxlength")
					},
					txtConfirmTradePwd : {
						required : true,
						digits:true,
						equalTo : "#txtNewTradePwd",
						minlength :  $("#txtNewTradePwd").attr("maxlength")
					}
				},
				messages : {
					txtAuthCode : {
						required : comm.lang("safeSet").input_reset_trading_password_authcode
					},
					txtNewTradePwd : {
						required : comm.lang("safeSet").input_new_trading_password,
						digits:comm.lang("safeSet").pwdDigits,
						minlength : comm.lang("safeSet").trading_password_length
					},
					txtConfirmTradePwd : {
						required : comm.lang("safeSet").input_confirm_trading_password,
						digits:comm.lang("safeSet").pwdDigits,
						equalTo: comm.lang("safeSet").trading_password_inconsistent,
						minlength : comm.lang("safeSet").trading_password_length
					}
				}
			});
		},
		pwdFormatChk:function(){
			/** 密码数据格式验证 */
			var $inputNewTradePwd=$("#txtNewTradePwd");
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