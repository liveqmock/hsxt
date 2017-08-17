define(["text!safetySetTpl/modifyDealPassword.html"], function (tpl) {
	var modifyDealPassword = {
		show : function(dataModule){
			//加载页面
			$("#myhs_zhgl_box").html(tpl);
			
			//初始化交易密码长度
			dataModule.initTradePwd(null,function(data){
				var delPwdLength=data.data.tradingPasswordLength; //交易密码长度
				$("#sDelPwdLength").html(delPwdLength);
				$("#dealPwd_modfiy,#dealPwd_modfiy_affirm").attr("maxlength",delPwdLength);
			});
			
			//确认提交按钮
			$('#deal_modify_submit').click(function(){
				
				var valid = modifyDealPassword.validateData();
				if (!valid.form()) {
					return;
				}
				//密码格式验证
				if (!modifyDealPassword.pwdFormatChk()) {
					return false;
				} 
				// 确认修改吗？
				comm.i_confirm(comm.lang("safetySet").setDelPasswordAffirm, function () {   
					//获取随机token
					comm.getToken(null,function(response){
						var toekn=response.data;
						//获取界面数据
						var oriDealPsw 	= 	$("#oriDealPsw_modify").val();			//原始交易密码
						var dealPwd 	= 	$("#dealPwd_modfiy").val();				//交易密码
						var dealPwdRe 	= 	$("#dealPwd_modfiy_affirm").val();		//确认交易密码
						
						//密码根据随机token加密
						var oriDealPswNew 	= comm.tradePwdEncrypt(oriDealPsw,toekn);		//原始交易密码加密
						var dealPwdNew 		= comm.tradePwdEncrypt(dealPwd,toekn);		//交易密码加密
						var dealPwdReNew 	= comm.tradePwdEncrypt(dealPwdRe,toekn);		//确认交易密码加密
						
						//封装传递json参数
						var jsonData ={
								oldTradePwd 	: 	oriDealPswNew,
								newTradePwd 	: 	dealPwdNew,
								newTradePwdRe	: 	dealPwdReNew,
								randomToken		:	toekn
						};
						
						//执行修改交易密码操作
						dataModule.modfiyDealPwd(jsonData, function (response) {
								comm.yes_alert(comm.lang("safetySet").modifyDealPassSuccess);
								$("#oriDealPsw_modify, #dealPwd_modfiy, #dealPwd_modfiy_affirm").val("");
						});
					});
				});
			});
		},
		validateData : function(){
			return $("#dealPwd_modify_form").validate({
				rules : {
					oriDealPsw_modify : {
						required : true,
						minlength : $("#dealPwd_modfiy").attr("maxlength"),
						maxlength : $("#dealPwd_modfiy").attr("maxlength"),
						digits : true
					},
					dealPwd_modfiy : {
						required : true,
						minlength : $("#dealPwd_modfiy").attr("maxlength"),
						digits : true,
						unequalTo : "#oriDealPsw_modify"
						
					},
					dealPwd_modfiy_affirm : {
						required : true,
						equalTo : "#dealPwd_modfiy",
						digits : true
					}
				},
				messages : {
					oriDealPsw_modify : {
						required : comm.lang("safetySet")[30117],
						minlength : comm.lang("safetySet").setDelPasswordMinlength,
						maxlength : comm.lang("safetySet").setDelPasswordMinlength,
						digits : comm.lang("safetySet")[30168]
					},
					dealPwd_modfiy : {
						required : comm.lang("safetySet")[30118],
						minlength : comm.lang("safetySet").setDelPasswordMinlength,
						digits : comm.lang("safetySet")[30168],
						unequalTo : comm.lang("safetySet").trad_pwd_identical_error
					},
					dealPwd_modfiy_affirm : {
						required : comm.lang("safetySet")[30120],
						equalTo : comm.lang("safetySet")[30122],
						digits : comm.lang("safetySet")[30168]
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
			var $inputNewTradePwd=$("#dealPwd_modfiy");
			var $tradePwdLen=$inputNewTradePwd.attr("maxlength")-1;//密码长度
			var $newTradePwd=$inputNewTradePwd.val();	//密码内容
			
			//匹配6位顺增或顺降  
			eval("var increaseReg =/^(?:(?:0(?=1)|1(?=2)|2(?=3)|3(?=4)|4(?=5)|5(?=6)|6(?=7)|7(?=8)|8(?=9)){"+$tradePwdLen+"}|(?:9(?=8)|8(?=7)|7(?=6)|6(?=5)|5(?=4)|4(?=3)|3(?=2)|2(?=1)|1(?=0)){"+$tradePwdLen+"})\\d$/;"); 
			if(increaseReg.test($newTradePwd)){
	        	comm.alert({width:500,imgClass: 'tips_i' ,content:comm.lang("safetySet").trad_pwd_increase_error });
	        	return false;
	        }
	          
	        //匹配6位重复数字  
	        eval("var repeatedReg =/^([\\d])\\1{"+$tradePwdLen+"}$/;"); 
	        if(repeatedReg.test($newTradePwd)){
	        	comm.alert({width:500,imgClass: 'tips_i' ,content:comm.lang("safetySet").trad_pwd_number_repeated_error });
	        	return false;
	        }
	        return true;
		}
	};
	return modifyDealPassword
});
