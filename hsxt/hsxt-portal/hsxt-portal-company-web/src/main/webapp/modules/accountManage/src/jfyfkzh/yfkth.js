define(['text!accountManageTpl/jfyfkzh/yfkth/yfkth.html',
		'text!accountManageTpl/jfyfkzh/yfkth/yfkth_qr.html'
		], function(tpl, qrTpl){
	return yfkth = {
		showPage : function(){
			$('#busibox').html(tpl);
			
			
			
			//加载数据
			$("#acpoint_cpointTotalNum").text(1000);
			$("#acpoint_cpointConverNum").text(1000);
			$("#acb_minMoney").text(500);
			$("#acb_usableCpointNum").text(1000);
			$("#acb_currency").text("人民币");
			$("#acb_rate").text(1);
			$("#acb_usableCpointMoney").text(1000);
			
			//申请提交按钮事件
			$('#acbQrBtn').click(function(){
				if (!yfkth.validateData()) {
					return;
				}
				
				$('#qr_acb_appBackNum').text(1000);
				$('#qr_acb_rate').text(1);
				$('#qr_acb_realMoneyNum').text(1000);
				
				//隐藏申请页面
				$('#cpointBack').addClass('none');
				//显示确认页面
				$('#cpointBack_affirm').html(_.template(qrTpl)).removeClass('none');
				
				//页面返回修改
				$('#acb_return').click(function(){
					//隐藏页面
					$('#cpointBack_affirm').html('').addClass('none');
					//显示申请页面
					$('#cpointBack').removeClass('none');
				});
				//提交按钮事件
				$('#acb_appBackSubmit').click(function(){
					if (!yfkth.validatePwdData()) {
						return;
					}
					
					comm.alert({
						content: '预付款退回成功！',
						callOk: function(){
							//跳转至明细查询页面
							$("#yfzfk_mxcx").trigger('click');
						}
					});
				});
			});
		},
		validateData : function(){
			return comm.valid({
				formID : '#acb_appForm',
				rules : {
					
				},
				messages : {
					
				}
			});
		},
		validatePwdData : function(){
			return comm.valid({
				formID : '#acb_appForm_qr',
				rules : {
					
				},
				messages : {
					
				}
			});
		}
	};
});