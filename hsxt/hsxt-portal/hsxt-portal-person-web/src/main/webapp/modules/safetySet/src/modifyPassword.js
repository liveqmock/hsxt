define(["text!safetySetTpl/modifyPassword.html"], function (tpl) {
	var modifyPassword = {
		show : function(dataModule){
			//加载页面
			$("#myhs_zhgl_box").html(tpl);
			
			//设置随机登录密码
			modifyPassword.getRandomToken();
			
			//初始化密码的长度
			dataModule.initTradePwd(null,function(data){
				//密码长度
				var loginPwdLength=data.data.loginPassLength;
				
				$("#sLoginPwdLen").html(loginPwdLength);
				$("#loginPsw_modify,#loginPsw_offirm_modify").attr("maxlength",loginPwdLength);
			});

			//确认提交按钮
			$('#loginPsw_modify_submit').click(function(){
				
				//form提交验证
				var valid = modifyPassword.validateData();
				if (!valid.form()) {
					return;
				}
				
				//密码格式验证
				if (!modifyPassword.pwdFormatChk()) {
					return false;
				} 
				
				//确认修改吗
				comm.i_confirm(comm.lang("safetySet").setPwdAffirm, function () {
					//获取界面数据
					var oriDealPsw 	= 	$("#loginOriPsw_modify").val();				//原始登录密码
					var dealPwd 	= 	$("#loginPsw_modify").val();				//新登录密码
					var dealPwdRe 	= 	$("#loginPsw_offirm_modify").val();			//确认登录新密码
					var randomToken = 	$("#loginPsw_randomToken").val();			//获取随机token
					
					//密码根据随机token加密
					var oriDealPswNew 	= comm.encrypt(oriDealPsw,randomToken);		//原始登录密码
					var dealPwdNew 		= comm.encrypt(dealPwd,randomToken);		//新登录密码
					var dealPwdReNew 	= comm.encrypt(dealPwdRe,randomToken);		//确认登录新密码
					
					//封装参数
					var jsonData ={
							 oldLoginPwd 		:	oriDealPswNew,
	 						 newLoginPwd 		:	dealPwdNew,
	 						 newLoginPwdRe 		:	dealPwdReNew,
	 						 randomToken		:	randomToken
					};
//					requestFunForHandFail
					//执行保存操作
					dataModule.modfiyLoginPwd(jsonData, function (response) {
						if (response.retCode ==22000){
							//提示保存成功
							comm.yes_alert(comm.lang("safetySet").savePwdSuccess);
						}else if(160359 == response.retCode || 160108 == response.retCode) {
							comm.error_alert("原登录密码错误，请重新输入！");
						}else if(160467 == response.retCode) {
							comm.error_alert(response.resultDesc);
						}else{
							comm.alertMessageByErrorCode(comm.lang("safetySet"), response.retCode);
						}
						//清空
						$("#loginOriPsw_modify").val("");
						$("#loginPsw_modify").val("");
						$("#loginPsw_offirm_modify").val("");
					});
				});
			});
		},
		
		/**
		 * 获取随机token数据
		 */
		getRandomToken : function (){
			//获取随机token
			comm.getToken(null,function(response){
				//非空数据验证
				if(response.data)
				{
					$("#loginPsw_randomToken").val(response.data);
				}
				else
				{
					comm.warn_alert(comm.lang("pointAccount").randomTokenInvalid);
				}
				
			});
		},
		//表单的验证规则
		validateData : function(){
			return $("#loginPwd_modify_form").validate({
				rules : {
					loginOriPsw_modify : {
						required : true,
						maxlength : $("#loginPsw_modify").attr("maxlength"),
						digits : true
					},
					loginPsw_modify : {
						required : true,
						maxlength :  $("#loginPsw_modify").attr("maxlength"),
						minlength :  $("#loginPsw_modify").attr("maxlength"),
						digits : true,
						unequalTo : "#loginOriPsw_modify"
					},
					loginPsw_offirm_modify : {
						required : true,
						equalTo : "#loginPsw_modify",
						digits : true
					}
				},
				messages : {
					loginOriPsw_modify : {
						required : comm.lang("safetySet")[30117],
						maxlength : comm.lang("safetySet").setDelPasswordMinlength,
						minlength : comm.lang("safetySet").setDelPasswordMinlength,
						digits : comm.lang("safetySet").pwdDigits
					},
					loginPsw_modify : {
						required : comm.lang("safetySet")[30118],
						maxlength : comm.lang("safetySet").setDelPasswordMinlength,
						minlength : comm.lang("safetySet").setDelPasswordMinlength,
						digits : comm.lang("safetySet").pwdDigits,
						unequalTo : comm.lang("safetySet").OriginalNewPwdUnequalTo
					},
					loginPsw_offirm_modify : {
						required : comm.lang("safetySet")[30119],
						equalTo : comm.lang("safetySet")[30121],
						digits : comm.lang("safetySet").pwdDigits
					}
				},
				errorPlacement : function (error, element) {
					$(element).attr("title", $(error).text()).tooltip({
						tooltipClass: "ui-tooltip-error",
						destroyFlag : true,
						destroyTime : 3000,
						position : {
							my : "left+180 top+5",
							at : "left top"
						}
					}).tooltip("open");
					$(".ui-tooltip").css("max-width", "230px");
				},
				success : function (element) {
					$(element).tooltip();
					$(element).tooltip("destroy");
				}
			});
		},
		//密码格式验证
		pwdFormatChk:function(){
			/** 密码数据格式验证 */
			var $txtNewPwd=$("#loginPsw_modify");
			var $newPwd=$txtNewPwd.val();
			var $pwdLen=$txtNewPwd.attr("maxlength")-1;
			
			//匹配6位顺增或顺降  
			eval("var increaseReg =/^(?:(?:0(?=1)|1(?=2)|2(?=3)|3(?=4)|4(?=5)|5(?=6)|6(?=7)|7(?=8)|8(?=9)){"+$pwdLen+"}|(?:9(?=8)|8(?=7)|7(?=6)|6(?=5)|5(?=4)|4(?=3)|3(?=2)|2(?=1)|1(?=0)){"+$pwdLen+"})\\d$/;"); 
	        if(increaseReg.test($newPwd)){
	        	comm.alert({width:500,imgClass: 'tips_i' ,content:comm.lang("safetySet").pwd_increase_error });
	        	return false;
	        }
	          
	        //匹配6位重复数字  
	        eval("var repeatedReg =/^([\\d])\\1{"+$pwdLen+"}$/;"); 
	        if(repeatedReg.test($newPwd)){
	        	comm.alert({width:500,imgClass: 'tips_i' ,content:comm.lang("safetySet").pwd_number_repeated_error });
	        	return false;
	        }
	        return true;
		}
	};
	return modifyPassword
});
