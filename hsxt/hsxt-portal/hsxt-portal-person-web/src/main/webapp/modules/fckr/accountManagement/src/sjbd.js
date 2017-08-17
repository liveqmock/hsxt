define(["text!fckr_accountManagementTpl/sjbd.html",
		"text!fckr_accountManagementTpl/sjbd_yzfs_dlg.html"
		], function (tpl, sjbd_yzfs_dlg) {
	var sjbd = {
		show : function(dataModule){
			//积分明细查询表格结果
			var searchTable = null;
			//加载页面
			$("#myhs_zhgl_box").html(tpl);
			
			$('#mobileBind_modBtn').click(function(){
				$('#yzfs_dlg').html(sjbd_yzfs_dlg).dialog({
					title : '验证方式',
					width : '500',
					modal : true,
					closeIcon : true,
					buttons : {
						'提交' : function(){
							
							if(!sjbd.validateYzfs().form()){
								return;	
							}
							$(this).dialog('destroy');
							sjbd.showTpl($('#sjbd_step2'));
							//修改页面获取短信验证码按钮
							$("#mobileBind_mod_checkBtn").click(function (e) {
								sjbd.sendCheckCode('mobileBind_teleNo3', this);
							});
							
							//立即修改手机号单击事件
							$('#mobileBind_mod_submitBtn').click(function(){
								var valid = sjbd.validateModData();
								if (!valid.form()) {
									return;
								}
								var mobile = $("#mobileBind_teleNo").val();
								var checkNo = $("#mobileBind_checkCode").val();
								
								
							});
							
							$('#mobileBind_back_submitBtn').click(function(){
								sjbd.showTpl($('#sjbd_step1'));		
							});
						}
					}	
				});	
			});
		},
		showTpl : function(tplObj){
			$('#sjbd_step1, #sjbd_step2').addClass('none');
			tplObj.removeClass('none');	
		},
		//发送验证码
		sendCheckCode : function(sInputId, oBtnSend){
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
			dataModule.mobileSendCode({
				mobile : mobile
			}, function (response) {
				if(response.code > 0){
					comm.warn_alert(response.msg);
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
						required : comm.lang("accountManagement").cardInfoBind.mobileBind.mobile,
						mobile : comm.lang("accountManagement").cardInfoBind.mobileBind.mobileErr
					},
					mobileBind_checkCode3 : {
						required : comm.lang("accountManagement").cardInfoBind.mobileBind.checkCode,
						digits : comm.lang("accountManagement").cardInfoBind.mobileBind.digits,
						maxlength : comm.lang("accountManagement").cardInfoBind.mobileBind.maxlength
					},
					mobileBind_loginPwd : {
						required : comm.lang("accountManagement").cardInfoBind.mobileBind.pwd
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
	return sjbd
});