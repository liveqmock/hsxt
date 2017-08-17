define(['text!safeSetTpl/szjymm/szjymm.html','safeSetDat/safeSet','safeSetLan'],function(szjymmTpl,safeSet ){
	return {
		 
		showPage : function(){
			var self = this;
			$('#busibox').html(_.template(szjymmTpl));
			
			//获取交易密码配置
			safeSet.querymmpz({},function(res){
				var tradingPasswordLength=res.data.tradingPasswordLength;
				//密码长度限制
				$("#sPasswordRule").html(tradingPasswordLength);
				$("#txtNewTradingPassword,#txtConfirmTradingPassword").attr("maxlength",tradingPasswordLength);
			});
			
			$('#btn_szjymm_qr').click(function(){
				var $ck=$(this);
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
					
					var $newPassword=comm.tradePwdEncrypt($("#txtNewTradingPassword").val(),pwdToken);//新密码
					var $confirmPassword=comm.tradePwdEncrypt($("#txtConfirmTradingPassword").val(),pwdToken);//确认密码
					
					var addParam={"randomToken":pwdToken,"newTradingPassword":$newPassword,"confirmTradingPassword":$confirmPassword};
					safeSet.addjymm(addParam,function(res){
						comm.alert({
							content: comm.lang("safeSet").add_trading_password_success,
							callOk: function(){
								
								//重新加载菜单,隐藏设置交易密码菜单,跳转到修改交易密码菜单
								comm.setCookie("tradPwd", true);
								
								var parentId= "030700000000";
								$("li >a").each(function(){
									if($(this).attr("menuurl") == "safeSetSrc/szjymm/tab"){
									    parentId = $(this).attr("id").split("_")[0];
										return false;
									}
								});
								
								var objParam={"parentId":parentId}
								
								comm.resetMenu(objParam,function(){
									$("li >a").each(function(){
										var arr = new Array();
										var menuurl = $(this).attr("menuurl");
										if(menuurl == "safeSetSrc/xgjymm/tab"){
											var id = $(this).attr("id");
											$("#" + id).trigger('click');
										}
									});
								});
							}
						});
					});
				});
				
			});
		},
		validateData : function(){
			return comm.valid({
				formID : '#szjymm_form',
				rules : {
					txtNewTradingPassword : {
						required : true,
						digits:true,
						minlength : $("#txtNewTradingPassword").attr("maxlength")
					},
					txtConfirmTradingPassword : {
						required : true,
						digits:true,
						equalTo : "#txtNewTradingPassword",
						minlength :  $("#txtNewTradingPassword").attr("maxlength")
					}
				},
				messages : {
					txtNewTradingPassword : {
						required : comm.lang("safeSet").input_new_trading_password,
						digits:comm.lang("safeSet").pwdDigits,
						minlength : comm.lang("safeSet").trading_password_length,
					},
					txtConfirmTradingPassword : {
						required : comm.lang("safeSet").input_confirm_trading_password,
						digits:comm.lang("safeSet").pwdDigits,
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

 