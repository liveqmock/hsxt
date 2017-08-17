define(["text!fckr_accountManagementTpl/mailBind.html",
		"text!fckr_accountManagementTpl/mailBind2.html",
		"text!fckr_accountManagementTpl/mailBind3.html"
	], function (tpl, tpl2, tpl3) {
	var mailBind = {
			newEmail:null, //最新邮箱
			show : function(dataModule){
				//加载页面
				$("#myhs_zhgl_box").html(tpl + tpl2 + tpl3);
				
				//获取用户信息判断是否绑定邮箱
				dataModule.findEamilByCustId({},function (response) {
					var data=response.data;
					var email=data.email; 
					var isAuthEmail = data.isAuthEmail;
					mailBind.newEmail=email;
					if (email != '') 
					{
						//设置显示div
						$('#mailBind_view_div').removeClass('none');
						$('#mailBind_new_div').addClass('none');
						
						//数据非空验证
						email = email.substr(0, 2) + "****" + email.substr(email.indexOf("@"), email.length);
						$('#mailBind_mailInfo').val(email);
						
						if("0" == isAuthEmail){
							$('#mailState').html('未认证 <a class="ml10 blue" id="reSending">重新发送验证邮件</a>');	
							$('#reSending').click(function(){
								//封装传递的json参数
								var jsonParam={
										email 			: data.email,	//邮箱地址
										confirmEmail	: data.email,	//确认邮箱地址
										mobile 			:comm.getCookie('userName')	//手机号
								};
								//执行保定操作
								dataModule.addBindEmail(jsonParam, function (response) {
									//comm.i_alert("验证邮箱已经发送"+ email.substr(0, 2) + "****" + email.substr(email.indexOf("@"), email.length)+"，请及时登录邮箱进行验证，完成绑定。");
									comm.yes_alert(comm.lang("myHsCard").addBindEmail);
									$("#ul_myhs_right_tab li:eq(3)").find("a").trigger("click"); 
								});
							});
						}
						else{
							$('#mailState').html('已认证');
						}
					}
				});
				
				//立即绑定邮箱单击事件
				$('#mailBind_submitBtn').click(function (e) {
					$('#mailBind_mail3').val('');
					var valid = mailBind.validateData();
					if (!valid.form()) {
						return;
					}
					
					//封装传递的json参数
					var jsonParam={
							email 			: $("#mailBind_mail").val(),	//邮箱地址
							confirmEmail	: $("#mailBind_mail2").val(),	//确认邮箱地址
							mobile 			:comm.getCookie('userName')	//手机号
					};
					//执行保定操作
					dataModule.addBindEmail(jsonParam, function (response) {
						mailBind.newEmail=jsonParam.email;
						//设置显示div
						$('#mailBind_view_div').removeClass('none');
						$('#mailBind_new_div').addClass('none');
						
						//设置掩码
						var emailInfo = jsonParam.email.substr(0, 2) + "****" + jsonParam.email.substr(jsonParam.email.indexOf("@"), jsonParam.email.length);
						
						//显示绑定邮箱地址
						$('#mailBind_mailInfo').val(emailInfo);
						
						$('#reSending').click(function(){
							//执行保定操作
							dataModule.addBindEmail(jsonParam, function (response) {
								comm.yes_alert("验证邮件已发送<br>"+ emailInfo+"，<br>请及时登录邮箱进行验证，完成绑定。");
								
							});
						});
						
						//提示绑定成功
						//comm.i_alert(addEmailSuccessfully);
						comm.yes_alert("验证邮件已发送 <br>"+ emailInfo +"，<br>请及时登录邮箱进行验证，完成绑定。");
					//	$("#ul_myhs_right_tab li:eq(3)").find("a").trigger("click"); 
						
					});
				});
				

				
				
				//修改绑定邮箱单击事件
				$('#mailBind_modBtn').click(function(){
					$('#mailBind_mod_div').removeClass('none');
					$('#mailBind_view_div').addClass('none');
					$('#mailBind_mail3_confirm').val('');
					$('#mailBind_loginPwd').val('');
					$('#mailBind_mail3').val('');
				});
				
				$('#mailBind_back_submitBtn').click(function(){
					$('#mailBind_mod_div').addClass('none');	
					$('#mailBind_view_div').removeClass('none');
				});
				
				
				
				//立即修改邮箱单击事件
				$('#mailBind_mod_submitBtn').click(function(){
					var valid = mailBind.validateModData();
					if (!valid.form()) {
						return;
					}
					comm.getToken(null,function(rsp){
						var token=rsp.data;
						
						var email = $("#mailBind_mail3").val();
						var confirmEmail = $("#mailBind_mail3_confirm").val();
						var loginPwd = $("#mailBind_loginPwd").val();
						var jsonParam={
								email : email,
								confirmEmail:confirmEmail,
								loginPwd : comm.encrypt(loginPwd,token),
								randomToken:token
							};
						
						//发送邮件
						dataModule.mailModify(jsonParam,function (response) {
							if (response.retCode ==22000){
								$('#mailBind_view_div').removeClass('none');
								$('#mailBind_mod_div').addClass('none');
								//提示保存成功
								mailBind.newEmail=email;
								comm.i_alert("验证邮件已发送 <br>"+ email.substr(0, 2) + "****" + email.substr(email.indexOf("@"), email.length)+"，<br>请及时登录邮箱进行验证，完成绑定。");
								$("#ul_myhs_right_tab a[class='a_active']").click();
							}else if(160359 == response.retCode || 160108 == response.retCode || 160467 == response.retCode) {
								comm.error_alert(response.resultDesc);
								$('#mailBind_loginPwd').val('');
							}else{
								comm.alertMessageByErrorCode(comm.lang("myHsCard"), response.retCode);
								$('#mailBind_loginPwd').val('');
							}
						});
					});
					
				});
			},
			validateData : function(){
				return $("#mailBind_form").validate({
					rules : {
						mailBind_mail : {
							required : true,
							email2 : true
						},
						mailBind_mail2 : {
							required : true,
							email2 : true,
							equalTo : "#mailBind_mail"
						}
					},
					messages : {
						mailBind_mail : {
							required : comm.lang("myHsCard").cardInfoBind.mailBind.required,
							email2 : comm.lang("myHsCard").cardInfoBind.mailBind.email
						},
						mailBind_mail2 : {
							required : comm.lang("myHsCard").cardInfoBind.mailBind.required1,
							email2 : comm.lang("myHsCard").cardInfoBind.mailBind.email,
							equalTo : comm.lang("myHsCard").cardInfoBind.mailBind.equalTo
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
				return $("#mailBind_mod_form").validate({
					rules : {
						mailBind_mail3 : {
							required : true,
							email2 : true
						},
						mailBind_mail3_confirm : {
							required : true,
							email2 : true,
							equalTo : "#mailBind_mail3"
						},
						mailBind_loginPwd : {
							required : true
						}
					},
					messages : {
						mailBind_mail3 : {
							required : comm.lang("myHsCard").cardInfoBind.mailBind.required,
							email2 : comm.lang("myHsCard").cardInfoBind.mailBind.email
						},
						mailBind_mail3_confirm : {
							required : comm.lang("myHsCard").cardInfoBind.mailBind.required1,
							email2 : comm.lang("myHsCard").cardInfoBind.mailBind.email,
							equalTo : comm.lang("myHsCard").cardInfoBind.mailBind.equalTo
						},
						mailBind_loginPwd : {
							required : comm.lang("myHsCard").cardInfoBind.mailBind.required2
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
		return mailBind
	});