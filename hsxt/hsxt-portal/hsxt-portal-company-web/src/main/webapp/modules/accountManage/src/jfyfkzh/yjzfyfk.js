define(['text!accountManageTpl/jfyfkzh/yjzfyfk/yjzfyfk.html',
		'text!accountManageTpl/jfyfkzh/yjzfyfk/yjzfyfk_hsb.html',
		'text!accountManageTpl/jfyfkzh/yjzfyfk/yjzfyfk_wy.html'
		], function(tpl, hsbTpl, wyTpl){
	return yjzfyfk = {
		showPage : function(){
			$('#busibox').html(tpl + hsbTpl + wyTpl);
			//加载数据
			$("#acpoint_appMinNum").val(1000);
			$("#acpoint_currency").text('人民币');
			$("#acpoint_rate").text('1');
			
			//转现积分数文本框-按下键盘事件
			$("#acpoint_appBuyCpointNum").bind("keyup", function () {
				var v = $(this).val(),
				r = $("#acpoint_rate").text();
				if (!isNaN(v) && !isNaN(r)) {
					var cpoint = parseFloat(v),
					rate = parseFloat(r);
					$("#acpoint_convertAppNum").text(cpoint * rate);
					$("#acpoint_realMoney").text(cpoint);
					if (!v) {
						$("#acpoint_convertAppNum, #acpoint_realMoney").text("");
					}
				}
			});
			//申请提交按钮事件
			$('#acpointQrBtn').click(function(){
				if (!yjzfyfk.validateData()) {
					return;
				}
				var radioVal = $("input[name=acpoint_checkbox]:checked").val();
				var acpoint_rate = parseFloat($("#acpoint_rate").text());
				var jsonData = {
					acpoint_appBuyCpointNum : $("#acpoint_appBuyCpointNum").val(),
					acpoint_currency : $("#acpoint_currency").text(),
					acpoint_rate : acpoint_rate,
					acpoint_converPointNum : $("#acpoint_appBuyCpointNum").val() / acpoint_rate,
					radioVal : radioVal
				};
				if (radioVal == "acpoint_cashAcctValue") {
					//互生币支付
					//隐藏申请页面
					$('#cpointPrepay').addClass('none');
					//数据
					$("#qr_acpoint_cash_appBuyCpointNum").text(jsonData.acpoint_appBuyCpointNum);
					$("#qr_acpoint_cash_currency").text(jsonData.acpoint_currency);
					$("#qr_acpoint_cash_rate").text(jsonData.acpoint_rate);
					$("#qr_acpoint_cash_converPointNum").text(jsonData.acpoint_converPointNum);
					$("#qr_acpoint_cash_converHsbNum").text(jsonData.acpoint_converPointNum);
					$("#acpoint_cash_radioVal").val(jsonData.radioVal);
					//显示确认页面
					$('#cpointPrepay_cashpay').removeClass('none');
				
				} else if (radioVal == "acpoint_ebankValue") {
					//网银支付
					//隐藏申请页面
					$('#cpointPrepay').addClass('none');
					//数据
					$("#qr_acpoint_ebank_appBuyCpointNum").text(jsonData.acpoint_appBuyCpointNum);
					$("#qr_acpoint_ebank_currency").text(jsonData.acpoint_currency);
					$("#acpoint_ebank_radioVal").val(jsonData.radioVal);
					//显示确认页面
					$('#cpointPrepay_ebankpay').removeClass('none');
				}
			});
			//互生币支付页面返回修改
			$('#acpoint_cash_return').click(function(){
				//隐藏互生币支付页面
				$('#cpointPrepay_cashpay').addClass('none');
				//显示申请页面
				$('#cpointPrepay').removeClass('none');
			});
			//互生币支付页确认
			$('#acpoint_cash_paymentSubmit').click(function(){
				if (!yjzfyfk.validatePwdData()) {
					return;
				}
				
				comm.confirm({
					title: "预缴积分预付款",
					content: "确认要申请购买吗？",
					imgFlag: 1,
					callOk: function () {
						
						comm.alert({
							content: '预缴积分预付款成功！',
							callOk: function(){
								//跳转至明细查询页面
								$("#yfzfk_mxcx").trigger('click');
							}
						});
						//清空数据
						$("#qr_acpoint_cash_appBuyCpointNum").text("");
						$("#acpoint_dealPwd").val("");
					}
				});
			});
			//网银支付页面返回修改
			$('#acpoint_ebank_return').click(function(){
				//隐藏互生币支付页面
				$('#cpointPrepay_ebankpay').addClass('none');
				//显示申请页面
				$('#cpointPrepay').removeClass('none');
			});
			//网银支付页确认
			$('#acpoint_ebank_paymentSubmit').click(function(){
				comm.confirm({
					title: "预缴积分预付款",
					content: "确认要申请购买吗？",
					imgFlag: 1,
					callOk: function () {
						var radioVal = $("#acpoint_ebank_radioVal").val(),
						appBuyCpointNum = $("#qr_acpoint_ebank_appBuyCpointNum").text(),
						dealPwd = null;
						//window.open(comm.domainList['local']+comm.UrlList["buyCpointOperate"]+"?appBuyCpointNum="+appBuyCpointNum+"&dealPwd="+dealPwd+"&radioVal="+radioVal);
						$('#yjzfyfk-dialog > p').html('<div class="tc fb f14 pt9">预缴积分预付款订单已提交银行，请确认支付结果。</div>');
						$('#yjzfyfk-dialog').dialog({
							title:"预缴积分预付款",
							width: 500,
							buttons: {
								"支付成功": function(){
									//跳转至明细查询页面
									$("#yfzfk_mxcx").trigger('click');
									$(this).dialog("destroy");
								},
								"支付失败": function(){
									//跳转至明细查询页面
									$("#yfzfk_mxcx").trigger('click');
									$(this).dialog("destroy");
								}
							}
						  });
						//清空数据
						$("#qr_acpoint_ebank_appBuyCpointNum").text("");
					}
				});
			});
		},
		validateData : function(){
			return comm.valid({
				formID : '#acpoint_appForm',
				rules : {
					acpoint_appBuyCpointNum : {
						required : true,
						digits : true,
					 	maxlength : 10,
					 	less : '#acpoint_appMinNum'
					}
				},
				messages : {
					acpoint_appBuyCpointNum : {
						required : comm.lang("accountManage").yjzfyfk_yfk,
						digits : comm.lang("accountManage").digits,
						maxlength : comm.lang("accountManage").maxlength,
						less : comm.lang("accountManage").yjzfyfk_less
					}
				}
			});
		},
		validatePwdData : function(){
			return comm.valid({
				formID : '#acpoint_appForm_qr',
				rules : {
					acpoint_dealPwd : {
						required : true
					}
				},
				messages : {
					acpoint_dealPwd : {
						required : comm.lang("accountManage").yjzfyfk_pwd
					}
				}
			});
		}
	};
});