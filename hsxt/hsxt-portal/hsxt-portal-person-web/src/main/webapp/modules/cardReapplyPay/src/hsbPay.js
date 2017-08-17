define(['text!cardReapplyPayTpl/hsbPay.html'/*,
		'text!payTpl/selectPayMethod.html'*/
		], function(hsbPayTpl/*, selectPayMethodTpl*/){
	var mayUseHsb = "0";
	var self = {
		showPage : function(obj){
			$('#payment_operate').html(_.template(hsbPayTpl, obj));
			self.getRandomToken(); /// 获取随机的Token

			var transNo = obj.orderNumber;    // 订单流水号
			var jsonParam = {};

			// 查询互生币余额
			comm.requestFun("findHsbBlance",jsonParam,function(response){
				mayUseHsb = "0"
				if(response.data){
					mayUseHsb = response.data.circulationHsb || "0";
				}
				$("#hsbAmount").html(comm.formatMoneyNumber(mayUseHsb));
				if(comm.isNotEmpty(obj.isFrist)){
					obj.isFrist = null;
					if(Number(obj.paymentAmount) > Number(mayUseHsb)){
						//$('#qPay_btn').click();
					}
				}
			},comm.lang("hsbAccount"));

			$('#forgetPwd_hsb').click(function(){
				comm.setCache('safeSet','resetDelPassword','1');
				$(".ul-myhs-left-list li:eq(2)").trigger("click");
			});

			$('#comfirmPay_btn').click(function(){
				if(parseFloat(obj.paymentAmount) > parseFloat(mayUseHsb)){
					comm.warn_alert("互生币余额不足");
					return;
				}

				var valid = self.validatePWD();
				if(!valid.form()){
					return;
				}

				//密码根据随机token加密
				var randomToken = $("#hsbpay_randomToken").val();	//获取随机token
				var dealPwd = $("#pay_pwd").val();
				var payPwd = comm.tradePwdEncrypt(dealPwd,randomToken);
				//传递参数
				var jsonData = {
					orderNo			:   transNo,
					randomToken     :   randomToken,
					tradePwd		:	payPwd,
					payChannel      :   "200"
				};

				// 校验支付密码后去付款
				comm.requestFun("toolOrderOayment",jsonData,function(response){

					comm.alert({
						width : 800,
						height : 200,
						imgClass : 'tips_yes',
						timeClose : 3,
						content : '恭喜，您的订单支付成功！金额为：<span class="pay-text-red f16">' + comm.formatMoneyNumber(obj.paymentAmount) + '</span><br /><div class="mt20 tc">您的订单号为：' + obj.orderNumber + '，系统将在3秒后，自动返回订单详情页面。</div>'
					});

					setTimeout(jumpUrls,3000);
					function jumpUrls(){
						$(".ul-myhs-left-list li:eq(3)").trigger("click");
					}
				},comm.lang("hsbAccount"));

			});

			/* 
			$('#qPay_btn').click(function(){

				$("#pay_pwd").tooltip().tooltip("destroy");

				obj.paymentMethod = 'kjzf';
				require(['cardReapplyPaySrc/pay'],function(pay){
					pay.showPage(obj);
				});
			});*/

			$('#bPay_btn').click(function(){
				obj.paymentMethod = 'wyzf';
				require(['cardReapplyPaySrc/pay'],function(pay){
					pay.showPage(obj);
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
					$("#hsbpay_randomToken").val(response.data);
				}
				else
				{
					comm.warn_alert(comm.lang("pointAccount").randomTokenInvalid);
				}

			});
		},
		validatePWD : function(){
			return $("#pwdForm").validate({
				rules : {
					pay_pwd : {
						required : true	,
						minlength : 8
					}
				},
				messages : {
					pay_pwd : {
						required : comm.lang("hsbAccount")[30002],
						minlength : comm.lang("hsbAccount")[30012]
					}
				},
				errorPlacement : function (error, element) {
					$(element).attr("title", $(error).text()).tooltip({
						tooltipClass: "ui-tooltip-error",
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
	return self ;
});