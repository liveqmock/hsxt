define(["text!pointAccountTpl/pointToCash.html", "text!pointAccountTpl/pointToCash_affirm.html"], function (tpl, tplAffirm) {
	var pointToCash = {
		show : function(dataModule){
			//加载页面模板
			$("#myhs_zhgl_box").html(tpl + tplAffirm);
			
			//获取积分转现初始化数据
			dataModule.getAssetValuePoint(null,function (response){
				//操作成功
				$("#ptc_pointAccUsable").text(comm.formatMoneyNumber(response.data.usablePointNum || '0'));				//可用积分数
			});
			
			
			//提交申请点击事件
			$('#ptcQrBtn').click(function(){
				//转出积分数数据验证
				var valid = pointToCash.validateData();
				
				//数据格式验证
				if (!valid.form()) 
				{
					return;
				}
				
				//传递参数
				var jsonData = {
					custId : $.cookie('cookie_custId'),
					pointNo : $.cookie('cookie_pointNo'),
					inputNum : $("#ptc_toCashPointNum").val(), 
					token :$.cookie('cookie_token')
				};
				//显示遮罩层
				comm.showLoading();
				//获取确认页面数据
				dataModule.pointToCashConfirmInfo(jsonData
				, function (response) { 
					if (response.code == 0) {
						//数据
						$("#qr_ptc_toCashPointNum").text(response.data.amount);
						$("#qr_ptc_rate").text(response.data.exchangeRate);
						//当地结算货币
						$("#qr_ptc_currency").text(response.data.targetCurrencyCode);
						$("#qr_ptc_realMoneyNum").text(response.data.targetAmount);
					} else {
						comm.error_alert(response.data.msg);
					}
					comm.closeLoading();
				});
				//隐藏申请页面
				$('#pointToCash').addClass('none');
				//显示确认页面
				$('#pointToCash_affirm').removeClass('none');
			});
			//积分转现返回修改页面
			$("#ptc_return").click(function () {
				$('input').tooltip().tooltip('destroy');
				$('#qr_ptc_dealPwd').val("");
				//显示申请页面
				$('#pointToCash').removeClass('none');
				//隐藏确认页面
				$('#pointToCash_affirm').addClass('none');
			});
			//确认提交点击事件
			$('#ptc_appComit').click(function () {
				//交易密码数据验证
				var valid = pointToCash.validateDealPwd();
				if (!valid.form()) {
					return;
				}
				//交易密码是否初始化
				dataModule.dealPswQuery(function (data) {
					
					//交易密码未设置
					if (data.code == '1') 
					{
						comm.warn_alert(comm.lang("pointAccount").pointToCash.setTransactionPassword);
						return;
					} 
					else 
					{
						comm.i_confirm(comm.lang("pointAccount").pointToCash.confirmIntegralMoney, function () {
							comm.showLoading();
							
							//传递参数
							var jsonData = {
								custId 		: 	$.cookie('cookie_custId'),					//客户号
								pointNo 	: 	$.cookie('cookie_pointNo'),					//互生卡号
								token 		:	$.cookie('cookie_token'),					//token
								tradePwd 	: 	$.trim($("#qr_ptc_dealPwd").val()),			//交易密码
								amount 	    : 	$.trim($("#qr_pth_toHsbPointNum").text()),	//转出积分数
								account   	:  	$.trim($("#qr_ptc_account").text()),		//账户
								currency   	:  	$.trim($("#qr_ptc_rate").text()),		//结算币种
								exchangeRate		:	$.trim($("#qr_ptc_rate").text()),			//结算利率
								targetAmount		:	$.trim($("#qr_ptc_realMoneyNum").text()),		//转入的互生币
								
							};
							//积分转现提交
							dataModule.pointCashForm(jsonData, function (response) {
								if (response.code == 0) {
									$("#ptc_dialog").dialog({
										dialogClass : "no-dialog-close",
										title : "积分转货币账户",
										width : "260",
										modal : true
									});
									//清空数据
									$("#qr_ptc_toCashPointNum").text("");
									$("#qr_ptc_dealPwd").val("");
								} else if (response.code == 501008) {
									comm.error_alert("交易密码验证错误！");
								} else if (response.code == 1) {
									comm.error_alert("交易密码验证错误！");
								} else if (response.code == 2) {
									comm.error_alert("输入金额错误！");
								} else {
									comm.error_alert(response.msg);
								}
								comm.closeLoading();
							});
						});
					}
				});
			});
			//点击dialog窗的确定按钮
			$('#ptc_cg_sure').click(function(){
				$("#ptc_dialog").dialog("destroy");
				$('#ul_myhs_right_tab li a[data-id="2"]').trigger('click');
			});
		},
		
		/**
		 * 积分数量规则验证
		 */
		validateData : function () {
			//转出积分数数据验证
			return $("#ptc_appform").validate({
				rules : {
					ptc_toCashPointNum : {
						required : true,
						digits : true,
						maxlength : 10,
						greater : "#ptc_pointAccUsable",
						less : "#ptc_minPointNum"
					}
				},
				messages : {
					ptc_toCashPointNum : {
						required : comm.lang("pointAccount")[30001],
						digits : comm.lang("pointAccount")[30153],
						maxlength : comm.lang("pointAccount").pointToCashMaxlength,
						greater : comm.lang("pointAccount")[30005],
						less : comm.lang("pointAccount")[30006]
					}
				},
				errorPlacement : function (error, element) {
					$(element).attr("title", $(error).text()).tooltip({
						tooltipClass: "ui-tooltip-error",
						destroyFlag : true,
						destroyTime : 3000,
						position : {
							my : "left+160 top+5",
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

		/**
		 * 交易密码验证
		 */
		validateDealPwd : function() {
			return $("#qr_ptc_appform").validate({
				rules : {
					qr_ptc_dealPwd : {
						required : true,
						maxlength : 8,
						minlength : 8
					}
				},
				messages : {
					qr_ptc_dealPwd : {
						required : comm.lang("pointAccount")[30002],
						maxlength : comm.lang("pointAccount").dealPwdLength,
						minlength : comm.lang("pointAccount").dealPwdLength
					}
				},
				errorPlacement : function (error, element) {
					$(element).attr("title", $(error).text()).tooltip({
						tooltipClass: "ui-tooltip-error",
						position : {
							my : "left+200 top+5",
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
	return pointToCash;
});
