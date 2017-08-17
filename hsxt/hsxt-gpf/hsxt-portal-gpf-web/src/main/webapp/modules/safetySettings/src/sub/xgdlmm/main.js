define(['text!safetySettingsTpl/sub/xgdlmm/xgdlmm.html','safetySettingsDat/xgdlmm/xgdlmm','safetySettingsLan' ],function(xgdlmmTpl,loginPasswordAjax ){
	jQuery.validator.addMethod("notEqualTo", function(value, element, elementId) {
		if(!elementId)return false;
		var comp = $(elementId).val();
		return this.optional(element) || comp != value;
	}, '新密码不能与旧密码相同');
	var loginPassword = {
		showPage: function (tabid) {
			$('#main-content > div[data-contentid="' + tabid + '"]').html(xgdlmmTpl);

			$('#xgdlmm_qrBtn').click(function () {
				if (loginPassword.validate()) {
					var params = $('#xgdlmmForm').serializeJson();
					var loginInfo = comm.getLoginInfo();
					if(loginInfo) {
						params.operatorId = loginInfo.operatorId;
						params.updatedBy = loginInfo.loginUser;
					}
					loginPasswordAjax.editLoginPassword(function (result) {
						if(result) {
							comm.alert({
								imgFlag: true,
								imgClass: 'tips_yes',
								content: '登录密码设置成功！'
							});
						}
					},params);
				}
			});


		},
		validate: function () {
			return comm.valid({
				formID: '#xgdlmmForm',
				top: -5,
				left: 280,
				rules: {
					oldPassword: {
						required: true,
						digits: true,
						passwordLogin: true
					},
					loginPwd: {
						required: true,
						digits: true,
						passwordLogin: true,
						passwordRule:true,
						notEqualTo:'#oldPassword'
					},
					loginPwd2: {
						required: true,
						digits: true,
						passwordLogin: true,
						equalTo: '#loginPwd'
					}
				},
				messages: {
					oldPassword: {
						required: '旧密码必填',
						digits: '输入数字'
					},
					loginPwd: {
						required: '新密码必填',
						digits: '输入数字'
					},
					loginPwd2: {
						required: '确认新密码必填',
						digits: '输入数字',
						equalTo: '确认新密码与新密码必须保持一致'
					}
				}
			});
		}
	};
	return loginPassword;
}); 
