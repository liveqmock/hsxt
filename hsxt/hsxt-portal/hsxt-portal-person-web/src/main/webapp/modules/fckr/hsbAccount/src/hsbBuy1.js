define(["text!fckr_hsbAccountTpl/hsbBuy.html", 
        "text!fckr_hsbAccountTpl/hsbBuy_cashpay_bak.html", 
        "text!fckr_hsbAccountTpl/hsbBuy_ebankpay_bak.html"], function (tpl, tplCashpay, tplEbankpay) {
	var hsbBuy = {
		show : function(dataModule){
			//加载页面模板
			$("#myhs_zhgl_box").html(tpl + tplCashpay + tplEbankpay);
			
			
			//测试cookie读取操作
			$.cookie('cookie_custId','0500108181620151111');
			$.cookie('cookie_pointNo','88888888');
			$.cookie('cookie_token','cookie_token');
			$.cookie('cookie_custName','custName');
			
			//读取
			var custId = $.cookie('cookie_custId'); 	// 读取 cookie 客户号
			var pointNo =$.cookie('cookie_pointNo'); 	// 读取 cookie pointNo
			var token =$.cookie('cookie_token'); 	    // 读取 cookie pointNo//测试cookie读取操作
			
			//参数构造
			var jsonParam ={
					custId 		: 	$.cookie('cookie_custId'),					//客户号
					pointNo 	: 	$.cookie('cookie_pointNo'),					//互生卡号
					token 		:	$.cookie('cookie_token'),
				
			}
			//初始化页面
			dataModule.initTransferHsb(jsonParam,function(response){
				//数据非空验证
				if(response)
				{
					//加载数据
					$("#hb_txt_currencyCode").text(response.data.currencyCode);			//当前平台币种
					$("#hb_txt_exchangeRate").text(response.data.exchangeRate);			//当前平台币种
					$("#hb_txt_verifiedMin").text(response.data.verifiedMin); 			// 实名认证兑换互生最小值
					$("#hb_hid_verifiedMin").val(response.data.verifiedMin); 			// 实名认证兑换互生最小值
					$("#hb_txt_verifiedMax").text(response.data.verifiedMax); 			// 实名认证兑换互生最大值
					$("#hb_txt_hid_verifiedMax").val(response.data.verifiedMax); 		// 实名认证兑换互生最大值
					$("#hb_txt_notVerifiedMin").text(response.data.notVerifiedMin); 	// 未实名认证兑换互生最小值
					$("#hb_hid_notVerifiedMin").val(response.data.notVerifiedMin); 		// 未实名认证兑换互生最小值
					$("#hb_txt_notVerifiedMax").text(response.data.notVerifiedMax); 	// 未实名认证兑换互生最大值
					$("#hb_hid_notVerifiedMax").val(response.data.notVerifiedMax); 		// 未实名认证兑换互生最大值
					$("#hb_txt_restrictionsHsb").text(response.data.restrictionsHsb); 	// 每日最多兑换互生币数量
					$("#hb_txt_toDayRestrictionsHsb").text(response.data.toDayRestrictionsHsb); // 今天还可兑换的互生币数量
					$("#hb_hid_isRegistration").val(response.data.isRegistration); // 是否实名注册//1：未实名注册、2：已实名注册（有名字和身份证）、3:已实名认证
				}
				
			});

			//兑换互生币数量文本框-按下键盘事
			$("#hb_appBuyNum").keyup(function () {
				
				var hsbNumber =  $(this).val();							//获取互生币数量
				var exchangeRate = $("#hb_txt_exchangeRate").text();	//获取货币兑换比率
				
				//数字验证
				if (!isNaN(hsbNumber) && !isNaN(exchangeRate)) {

					
					var ratefee = $("#htc_hid_currencyFee").val();	//货币转换费

					var num = parseInt(hsbNumber) * parseInt(exchangeRate);	//计算需要花多少钱
					
					$("#hb_convertAppMoney").text(num.toFixed(2));
					$("#hb_realMoney").text(num.toFixed(2));
					
					 
				}else {
					$("#hb_convertAppMoney").text("");
					$("#hb_realMoney").text("");
				}
			});
			
			//提交申请点击事件
			$('#hb_QrBtn').click(function(){   //点击提交事件   我获得是选择货币支付还是网银支付
				//转出积分数数据验证
				var valid = hsbBuy.validateData();
				if (!valid.form()) {
					return;
				}
				
				var radioVal = $("input[name=hb_checkbox]:checked").val();
				var hb_rate = comm.getCache("personWeb", "cache").rate_hsb_cash;
				var hb_realMoney = $("#hb_realMoney").text();
				var hb_convertAppMoney = $("#hb_convertAppMoney").text();
				var jsonData = {
					hb_appBuyNum : $("#hb_appBuyNum").val(),
					hb_currency : $("#hb_currency").text(),
					hb_rate : hb_rate,
					hb_convertAppMoney : hb_convertAppMoney,
					hb_realMoney : hb_realMoney,
					radioVal : radioVal
				};
				//隐藏申请页面
				$('#hsbBuy').addClass('none');
				if (radioVal == "1") {
					//现金账户支付
					$("#qr_hb_cash_buyNum").text(jsonData.hb_appBuyNum);
					$("#qr_hb_cash_convertAppMoney").text(jsonData.hb_convertAppMoney);
					$("#qr_hb_cash_rate").text(jsonData.hb_rate);
					$("#qr_hb_cash_currency").text(jsonData.hb_currency);
					$("#qr_hb_cash_realhsbNum").text(jsonData.hb_appBuyNum / jsonData.hb_rate);  //
					$("#qr_hb_cash_realMoney").text(jsonData.hb_realMoney);
					$("#hb_cash_radioVal").val(jsonData.radioVal);
					//显示确认页面
					$('#hsbBuy_cashpay').removeClass('none');
					
				} else if (radioVal == "2") {
					//网银支付
					$("#qr_hb_ebank_buyNum").text(jsonData.hb_appBuyNum);
					$("#qr_hb_ebank_convertAppMoney").text(jsonData.hb_convertAppMoney);
					
					$("#qr_hb_ebank_rate").text(jsonData.hb_rate);             // 网银支付页面货币转换比率
					
					$("#qr_hb_ebank_currency").text(jsonData.hb_currency);
					$("#qr_hb_ebank_realMoney").text(jsonData.hb_realMoney);
					$("#qr_hb_ebank_realhsbNum").text(jsonData.hb_appBuyNum / jsonData.hb_rate);
					$("#hb_ebank_radioVal").val(jsonData.radioVal);
					//显示确认页面
					$('#hsbBuy_ebankpay').removeClass('none');
				}
			});

			//兑换互生币现金支付返回修改页面
			$("#hb_cash_return").click(function () {
				$('input').tooltip().tooltip('destroy');
				$('#hb_dealPwd').val("");
				$('#hsbBuy').removeClass('none');
				$('#hsbBuy_cashpay').addClass('none');
			});
			//兑换互生币网银支付返回修改页面
			$("#hb_ebank_return").click(function () {
				$('#hsbBuy').removeClass('none');
				$('#hsbBuy_ebankpay').addClass('none');
			});

			//货币购买互生币提交点击事件
			$('#hb_cash_paymentSubmit').click(function(){
				//转出积分数数据验证
				var valid = hsbBuy.validateDealPwd();
				if (!valid.form()) {
					return;
				}
				
				comm.i_confirm(comm.lang("hsbAccount").affireBuy, function () {   //
					
					var radioVal = $.trim($("#hb_cash_radioVal").val());
					var appBuyNum = $.trim($("#qr_hb_cash_buyNum").text());
					var dealPwd = $.trim($("#hb_dealPwd").val());
				
					var jsonParam ={
							custId 		: 	$.cookie('cookie_custId'),					//客户号
							pointNo 	: 	$.cookie('cookie_pointNo'),					//互生卡号
							token 		:	$.cookie('cookie_token'),					//安全令牌
							tradePwd    :   $("#hb_dealPwd").val(),						//交易密码
							custName    :   $.cookie('cookie_custName'),				//客户名
							quantity    :   $("#hb_appBuyNum").val(),                   //输入互生币数量
							randomToken :  'randomToken',								//随机token（防止CSRF攻击）
							orderOriginalAmount  :   $("#hb_realMoney").text(),         //应支付金额 
							orderType   :   $('#hsbzh_ghsb_zffs input[name="hb_checkbox"]:checked ').val()  //选择哪种支付方式
					};
					//互生币转现提交
					dataModule.transferHsb(jsonData, function (response) { 
						if (response.code == 0) {
							$("#hb_cash_dialog").dialog({
								dialogClass : "no-dialog-close",
								title : comm.lang("hsbAccount").hsbBuy.convertHsb,      //兑换互生币
								width : "340",
								modal : true
							});
							//清空数据
							$("#qr_hb_cash_buyNum").text("");
							$("#hb_dealPwd").val("");
						} else if (response.code == 501008) {
							//comm.error_alert("交易密码验证错误！");
							comm.error_alert(comm.lang("hsbAccount").hsbBuy.passwordCheckFail);
						} else if (response.code == 1) {
							comm.error_alert(comm.lang("hsbAccount").hsbBuy.passwordCheckFails);
						} else if (response.code == 2) {
							comm.error_alert(comm.lang("hsbAccount").hsbBuy.importMoneyError);
						} else {
							comm.error_alert(response.msg);
						}
						comm.closeLoading();
					});
				});
					
			});
			//点击dialog窗的确定按钮
			$("#hb_cash_cg_sure").click(function () {
				$("#hb_cash_dialog").dialog("destroy");
				$('#ul_myhs_right_tab li a[data-id="3"]').trigger('click');
			});

			//网银支付提交点击事件
			
			$('#hb_ebank_paymentSubmit').click(function(){
				//=================================================
				$.cookie('cookie_custId','cookie_custId');
				$.cookie('cookie_pointNo','cookie_pointNo');
				//读取
				var custId = $.cookie('cookie_custId'); 	// 读取 cookie 客户号
				var pointNo =$.cookie('cookie_pointNo'); 
				var jsonData ={
						custId 		: 	$.cookie('cookie_custId'),					//客户号
						pointNo 	: 	$.cookie('cookie_pointNo'),					//互生卡号
						token 		:	$.cookie('cookie_token'),
						tradePwd    :   $("#hb_dealPwd").val(),
						hsbNum      :   $("#hb_appBuyNum").val(),                   //输入互生币数量
						cashCost    :   $("#hb_convertAppMoney").text() ,           //折合货币金额
						cash        :   $("#hb_realMoney").text(),                  //应支付金额 
						orderType   :   $('#hsbzh_ghsb_zffs input[name="hb_checkbox"]:checked ').val()  //选择哪种支付方式
				};
				
				dataModule.buyHsbConfirm(
				
				    jsonData, function (response) {
				 
					if (response.code == 0) {
						comm.i_confirm(comm.lang("hsbAccount").hsbBuy.affirmBuy, function () {  // 确认要购买吗
							var radioVal = $("#hb_ebank_radioVal").val();
							var appBuyNum = $("#qr_hb_ebank_buyNum").text();
							var dealPwd = null;
							var param = "appBuyNum=" + appBuyNum + "&dealPwd=" + dealPwd + "&radioVal=" + radioVal;
							//window.open(comm.domainList['local'] + comm.UrlList["buyHsbOperate"] + "?" + param);
							//弹框
							$("#hb_ebank_dialog").dialog({
								dialogClass : "no-dialog-close",
								title :comm.lang("hsbAccount").hsbBuy.convertHsbSubmit ,   //兑换互生币订单提交
								width : "550",
								modal : true
							});
							//清空数据
							$("#qr_hb_ebank_buyNum").text("");
						});
					}
				
				});
			});
			// =====   这段代码要不要放到  response 里到时再看。  ================-
			
			//点击网银购买互生币dialog窗的支付成功/失败按钮    
			$("#hb_ebank_cg_sure, #hb_ebank_cg_lose").click(function () {
				$("#hb_ebank_dialog").dialog("destroy");
				$('#ul_myhs_right_tab li a[data-id="3"]').trigger('click');
			});
		},
		
		validateData : function () {
			//转出积分数数据验证
			return $("#hb_appForm").validate({
				rules : {
					hb_appBuyNum : {
						required : true,
						digits : true,
						min  : 100,
						maxlength : 10
					}
				},
				messages : {
					hb_appBuyNum : {
						required : comm.lang('hsbAccount').hsbBuy.required,
						digits : comm.lang("hsbAccount").hsbBuy.digits,
						min    : comm.lang("hsbAccount").hsbBuy.min ,
						maxlength : comm.lang('hsbAccount').hsbBuy.maxlength
					}
				},
				errorPlacement : function (error, element) {
					$(element).attr("title", $(error).text()).tooltip({
						tooltipClass: "ui-tooltip-error",
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
		validateDealPwd : function() {
			return $("#hb_appForm_qr").validate({
				rules : {
					hb_dealPwd : {
						required : true,
						maxlength : 8,
						minlength : 8
					}
				},
				messages : {
					hb_dealPwd : {
						required :  comm.lang("hsbAccount")[30002],
						maxlength : comm.lang("hsbAccount")[30012],
						minlength : comm.lang("hsbAccount")[30012]
					}
				},
				errorPlacement : function (error, element) {
					$(element).attr("title", $(error).text()).tooltip({
						tooltipClass: "ui-tooltip-error",
						destroyFlag : true,
						destroyTime : 3000,
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
	return hsbBuy;
});
