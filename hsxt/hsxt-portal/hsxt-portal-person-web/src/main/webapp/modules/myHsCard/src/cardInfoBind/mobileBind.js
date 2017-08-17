define(["text!myHsCardTpl/cardInfoBind/mobileBind.html",
		"text!myHsCardTpl/cardInfoBind/mobileBind2.html",
		"text!myHsCardTpl/cardInfoBind/mobileBind3.html"
	], function (tpl, tpl2, tpl3) {
	var mobileBind = {
	moblieVal :null,
		show : function(dataModule){
			//加载页面
			$("#kxxbd_content").html(tpl + tpl2 + tpl3);
			
			//设置互生号到界面显示字段中
			$("#mobileBind_resNo, #mobileBind_resNo2, #mobileBind_resNo3").val($.cookie("resNo"));
			//$("#mobileBind_resNo").val($.cookie("resNo"));
			//获取随机token
			mobileBind.getRandomToken();
			
			//获取用户绑定手机号码
			dataModule.findMobileByCustId(null,function (response) {
				var moblieVal=response.data;
				if (null != moblieVal.mobile && "" != moblieVal.mobile ) 
				{
					//设置显示div
					$('#mobileBind_view_div').removeClass('none');
					$('#mobileBind_new_div').addClass('none');
					
					//数据非空验证
					if (response.data ) 
					{
						//给手机号码设置掩码
						var mobile = moblieVal.mobile ;
						var mobile = mobile.substr(0, 3) + "****" + mobile.substr(mobile.length - 4, 4);
						$('#mobileBind_teleNo2').val(mobile);
					}
				}
			});
			

			//立即绑定手机号单击事件
			$('#mobileBind_submitBtn').click(function (e) {
				var valid = mobileBind.validateData();
				if (!valid.form()) {
					return;
				}
				
				//封装手机绑定参数
				var jsonParam ={
						mobile 	: $("#mobileBind_teleNo").val(),	//手机号码
						smsCode : $("#mobileBind_checkCode").val()  //验证码
				};
				
				//调用绑定手机号码处理方法
				dataModule.addBindMobile(jsonParam, function (response) {
					comm.setCookieForRootPath("isAuthMobile", 0);  
					//提示绑定成功
					comm.i_alert(comm.lang("myHsCard").bindMobileSuccessfully);
					
					//截取手机号码前台显示
					var newMobile = jsonParam.mobile.substr(0, 3) + "****" + jsonParam.mobile.substr(jsonParam.mobile.length - 4, 4);
					
					//控制div显示
					$('#mobileBind_teleNo2').val(newMobile);
					$('#mobileBind_view_div').removeClass('none');
					$('#mobileBind_new_div').addClass('none');
					
					
				});
			});
			
			//获取短信验证码按钮
			$("#mobileBind_checkBtn").click(function (e) {
				mobileBind.sendCheckCode(dataModule,'mobileBind_teleNo', this);
			});
			
			//修改绑定手机号单击事件
			$('#mobileBind_modBtn').click(function(){
				$('#mobileBind_mod_div').removeClass('none');
				$('#mobileBind_view_div').addClass('none');
				$('#mobileBind_checkCode3').val('');
				$('#mobileBind_loginPwd').val('');
			});
			
			//立即修改手机号单击事件
			$('#mobileBind_mod_submitBtn').click(function(){
				
				//修改数据前验证数据是否正确
				var valid = mobileBind.validateModData();
				if (!valid.form()) {
					return false;
				}
	
				//密码根据随机token加密
				var randomToken = $("#mobileBind_randomToken3").val();		//获取随机token
				var loginPwd = $.trim($("#mobileBind_loginPwd").val());		//获取交易密码
				var word = comm.encrypt(loginPwd,randomToken);				//加密
				
				//封装传递参数
				var jsonParam = {
						mobile 	: $("#mobileBind_teleNo3").val(),		//手机号码
						smsCode : $("#mobileBind_checkCode3").val(),  	//验证码
						loginPwd: word,									//密码
						randomToken:randomToken							//随机报文
				};
				
				//执行修改操作
				dataModule.editBindMobile(jsonParam , function (response) {
					comm.setCookieForRootPath("isAuthMobile", 0);  
					//提示相关信息
					comm.i_alert(comm.lang("myHsCard").editMobileSuccessfully);
					
					//设置div
					$('#mobileBind_view_div').removeClass('none');
					$('#mobileBind_mod_div').addClass('none');
					
					//截取手机号码前台显示
					var newMobile = jsonParam.mobile.substr(0, 3) + "****" + jsonParam.mobile.substr(jsonParam.mobile.length - 4, 4);
					$('#mobileBind_teleNo2').val(newMobile);
					
				});
				
			});
			//修改页面获取短信验证码按钮
			$("#mobileBind_mod_checkBtn").click(function (e) {
				mobileBind.sendCheckCode(dataModule,'mobileBind_teleNo3', this);
			});
		},
		//发送验证码
		sendCheckCode : function(dataModule,sInputId, oBtnSend){
			
			var mobile = $("#" + sInputId).val();
			if ($.trim(mobile).length == 0) {
				comm.warn_alert(comm.lang("myHsCard")[30150]);
				return;
			}
			var reg = /^1[3|4|5|7|8][0-9]\d{8}$/;
			if (!reg.test(mobile)) {
				comm.warn_alert(comm.lang("myHsCard").mobileErr);
				return;
			}
			var t = 120;
			var oThis = $(oBtnSend);
			
			if (oThis.text() != comm.lang("myHsCard").getSMSCode && oThis.text() != comm.lang("myHsCard").reacquireSMSCode) {
				return;
			}
			
			//封装参数
			var jsonParam ={
					mobile : mobile		//手机号码
			};
			
			//发送短信
			dataModule.mobileSendCode(jsonParam, function (response) {
				//定时器设置 每次发送短信读秒120
				var interval = setInterval(function () {
					oThis.addClass("ongsd");
					t -= 1;
					oThis.html(comm.lang("myHsCard").timeLeft+"&nbsp;(&nbsp" + t + "&nbsp)");
					if (t == 1) {
						clearInterval(interval);
						oThis.html(comm.lang("myHsCard").reacquireSMSCode);
						oThis.removeClass("ongsd");
						t = 120;
					}
				}, 1000);
				
				comm.warn_alert(comm.lang("myHsCard").sentSuccessfully);
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
					$("#mobileBind_randomToken, #mobileBind_randomToken2, #mobileBind_randomToken3").val(response.data);
				}
				else
				{
					comm.warn_alert(comm.lang("myHsCard").randomTokenInvalid);
				}
				
			});
		},
		validateData : function(){
			return $("#mobileBind_form").validate({
				rules : {
					mobileBind_teleNo : {
						required : true,
						mobileNo : true
					},
					mobileBind_checkCode : {
						required : true
					}
				},
				messages : {
					mobileBind_teleNo : {
						required : comm.lang("myHsCard")[30150],
						mobileNo : comm.lang("myHsCard")[30151]
					},
					mobileBind_checkCode : {
						required : comm.lang("myHsCard")[30152]
					}
				},
				errorPlacement : function (error, element) {
					$(element).attr("title", $(error).text()).tooltip({
						tooltipClass: "ui-tooltip-error",
						destroyFlag : true,
						destroyTime : 2000,
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
		validateModData : function(){
			return $("#mobileBind_mod_form").validate({
				rules : {
					mobileBind_teleNo3 : {
						required : true,
						mobileNo : true
					},
					mobileBind_checkCode3 : {
						required : true,
						digits : true,
						maxlength : 6
					},
					mobileBind_loginPwd : {
						required : true
					}
				},
				messages : {
					mobileBind_teleNo3 : {
						required : comm.lang("myHsCard").cardInfoBind.mobileBind.mobile,
						mobileNo : comm.lang("myHsCard").cardInfoBind.mobileBind.mobileErr
					},
					mobileBind_checkCode3 : {
						required : comm.lang("myHsCard").cardInfoBind.mobileBind.checkCode,
						digits : comm.lang("myHsCard").cardInfoBind.mobileBind.digits,
						maxlength : comm.lang("myHsCard").cardInfoBind.mobileBind.maxlength
					},
					mobileBind_loginPwd : {
						required : comm.lang("myHsCard").cardInfoBind.mobileBind.pwd
					}
				},
				errorPlacement : function (error, element) {
					$(element).attr("title", $(error).text()).tooltip({
						tooltipClass: "ui-tooltip-error",
						destroyFlag : true,
						destroyTime : 2000,
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
		}
	};
	return mobileBind
});
