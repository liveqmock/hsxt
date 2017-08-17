define(["text!fckr_accountManagementTpl/mobileBind.html",
		"text!fckr_accountManagementTpl/mobileBind2.html",
		"text!fckr_accountManagementTpl/mobileBind3.html",
		"text!fckr_accountManagementTpl/mobileBind_yzfs_dlg.html",
		"pointAccountLan"
	], function (tpl, tpl2, tpl3, mobileBind_yzfs_dlgTpl) {
	var mobileBind = {
		
		show : function(dataModule){
			//加载页面
			$("#myhs_zhgl_box").html(tpl + tpl2 + tpl3);
			
			//得到互生卡号
			/*var hsCardId = $('#card_id').text();
			$("#mobileBind_resNo, #mobileBind_resNo2, #mobileBind_resNo3").val(hsCardId);*/
			
			//获取用户绑定手机号码
			dataModule.findMobileByCustId(null,function (response) {
				var moblieVal=response.data;
				if (moblieVal.isBind=="1") 
				{
					//设置显示div
					$('#mobileBind_view_div').removeClass('none');
					$('#mobileBind_new_div').addClass('none');
					
					//数据非空验证
					if (response.data ) 
					{
						//给手机号码设置掩码
						var mobile = moblieVal.mobile ;
						$("#myBindmobile").val(mobile);
						var mobile = mobile.substr(0, 3) + "****" + mobile.substr(mobile.length - 4, 4);
						$('#mobileBind_teleNo2').val(mobile);
						$("#mobileBind_teleNo_txt").show();
					}
				}
			});
			
			//获取随机验证码
			mobileBind.getRandomToken();
			
			//立即绑定手机号单击事件
			$('#mobileBind_submitBtn').click(function (e) {
				var valid = mobileBind.validateData();
				if (!valid.form()) {
					return;
				}
				var mobile = $("#mobileBind_teleNo").val();
				var checkNo = $("#mobileBind_checkCode").val();
				dataModule.mobileBind({
					mobile : mobile,
					fixMobileCheckNum : checkNo
				}, function (response) {
					comm.i_alert(response.msg);
					if (response.code == 0) {
						var newMobile = mobile.substr(0, 3) + "****" + mobile.substr(mobile.length - 4, 4);
						$('#mobileBind_teleNo2').val(newMobile);
						$('#mobileBind_view_div').removeClass('none');
						$('#mobileBind_new_div').addClass('none');
						$("#mobileBind_teleNo_txt").show();
					}
				});
			});
			
			$('#mobileBind_back_submitBtn').click(function(){
				$('#mobileBind_mod_div').addClass('none');
				$('#mobileBind_view_div').removeClass('none');	
			});
			
			//获取短信验证码按钮
			$("#mobileBind_checkBtn").click(function (e) {
				mobileBind.sendCheckCode('mobileBind_teleNo', this, dataModule);
			});
			$("#mobileBind_mod_checkBtn").click(function (e) {
				mobileBind.sendCheckCode('mobileBind_teleNo3', this, dataModule);
			});
			
			//修改绑定手机号单击事件
			$('#mobileBind_modBtn').click(function(){
				
				//加载获取手机验证码的界面
				var mobileTp = $('#yzfs_dlg').html(mobileBind_yzfs_dlgTpl);
				
				//显示绑定手机号码
				$('#yzsffs_slt').append($("<option/>", {value: '',text: '--选择--'}));
				$('#yzsffs_slt').append($("<option/>", {value: $("#myBindmobile").val(),text: '已验证手机 '+$("#mobileBind_teleNo2").val()}));
				
				//修改页面获取短信验证码按钮
				$("#dlg_hqdxyzm").click(function (e) {
					mobileBind.sendCheckCode('yzsffs_slt', this, dataModule);
				});
				//设置dialog方式显示
				mobileTp.dialog({
					title : '验证方式',
					width : '500',
					modal : true,
					closeIcon : true,
					buttons : {
						'提交' : function(){
							
							if(!mobileBind.validateYzfs().form()){
								return;	
							}
							
							var jsonParam ={
									mobile:$("#myBindmobile").val(),
									smsCode:$("#dtyzm_input").val()
							};
							var dialogObj = this;
							//获取用户绑定手机号码
							dataModule.checkSmsCode(jsonParam,function (response) {
								
								//销毁模式对话框
								$(dialogObj).dialog('destroy');
								
								//设置div显示隐藏
								$('#mobileBind_mod_div').removeClass('none');
								$('#mobileBind_view_div').addClass('none');
								
								$("#mobileBind_checkCode3,#mobileBind_loginPwd").val("");
								
								
								//立即修改手机号单击事件
								$('#mobileBind_mod_submitBtn').click(function(){
									
									//验证数据准确
									var valid = mobileBind.validateModData();
									if (!valid.form()) {
										return;
									}
									
									
									//获取界面元素
									var mobile = $("#mobileBind_teleNo3").val();		//手机号
									var smsCode = $("#mobileBind_checkCode3").val();	//短信验证码
									var loginPwd = $("#mobileBind_loginPwd").val();		//登录密码
									var randomToken = $("#mobile_randomToken").val();	//随机token
									var name = comm.getCookie('userName');
									//密码根据随机token加密
									var loginPwdNew 	= comm.encrypt(loginPwd,randomToken);		//原始登录密码
									
									//封装参数
									var jsonData ={
											userNameL		:	name,
											mobile 			:	mobile,
											smsCode 		:	smsCode,
											loginPwd 		:	loginPwdNew,
											randomToken		:	randomToken
									};
									
									
									dataModule.editBindMobile(jsonData, function (response) {
										
										comm.i_alert('修改成功！');
										
										//初始化界面
										$('#ul_myhs_right_tab li a[data-id="3"]').trigger('click');
										
									});
								});
							});
							
						}
					}	
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
					$("#mobile_randomToken").val(response.data);
				}
				else
				{
					comm.warn_alert(comm.lang("hsbAccount").randomTokenInvalid);
				}
				
			});
		},
		//发送验证码
		sendCheckCode : function(sInputId, oBtnSend, dataModule){
			var mobile = $("#" + sInputId).val();
			if ($.trim(mobile).length == 0) {
				comm.warn_alert("请输入手机号码");
				return;
			}
			var reg = /^1[3|4|5|8][0-9]\d{4,8}$/;
			if (!reg.test(mobile)) {
				comm.warn_alert("请输入正确的手机号码");
				return;
			}
			var t = 120;
			var oThis = $(oBtnSend);
			if (oThis.text() != '获取短信验证码' && oThis.text() != '重新获取短信验证码') {
				return;
			}
			var interval = setInterval(function () {
				oThis.addClass("ongsd");
				t -= 1;
				oThis.html("剩余时间&nbsp;(&nbsp" + t + "&nbsp)");
				if (t == 1) {
					clearInterval(interval);
					oThis.html("重新获取短信验证码");
					oThis.removeClass("ongsd");
					t = 120;
				}
			}, 1000);
			
			//封装参数
			var jsonParam ={
					mobile : mobile		//手机号码
			};
			
			//发送短信
			dataModule.mobileSendCode(jsonParam, function (response) {
				comm.warn_alert(comm.lang("myHsCard").sentSuccessfully);
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
						required : '请输入手机号码',
						mobileNo : '请输入正确的手机号码'
					},
					mobileBind_checkCode : {
						required : '请输入短信验证码'
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
						mobile : true
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
						required : '请输入手机号码',
						mobileNo : '请输入正确的手机号码'
					},
					mobileBind_checkCode3 : {
						required : '请输入短信验证码',
						digits : '请输入正确的短信验证码',
						maxlength : '验证码不能超过6位'
					},
					mobileBind_loginPwd : {
						required : '请输入登录密码'
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
		validateYzfs : function(){
			return $("#jbzl_yzfsForm").validate({
				rules : {
					yzsffs_slt : {
						required : true
					},
					dtyzm_input : {
						required : true	
					}
				},
				messages : {
					yzsffs_slt : {
						required : '必选'
					},
					dtyzm_input : {
						required : '必填'
					}
				},
				errorPlacement : function (error, element) {
					/*if ($(element).attr('name') == 'quickDate') {
						element = element.parent();
					}*/
					$(element).attr("title", $(error).text()).tooltip({
						tooltipClass: "ui-tooltip-error",
						destroyFlag : true,
						destroyTime : 3000,
						position : {
							my : "top-18",
							at : "left+230"
						}
					}).tooltip("open");
					$(".ui-tooltip").css("max-width", "230px");
				},
				success : function (element) {
					/*if ($(element).attr('name') == 'quickDate') {
						$(element.parent()).tooltip();
						$(element.parent()).tooltip("destroy");
					} else {*/
						$(element).tooltip();
						$(element).tooltip("destroy");
					/*}*/
				}
			});
		}
	};
	return mobileBind
});
