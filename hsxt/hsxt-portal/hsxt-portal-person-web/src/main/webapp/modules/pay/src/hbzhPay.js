define(['text!payTpl/hbzhPay.html'/*,
		'text!payTpl/selectPayMethod.html'*/
		], function(hbzhPayTpl/*, selectPayMethodTpl*/){
	return {
		showPage : function(obj){
			$('#payment_operate').html(_.template(hbzhPayTpl, obj));
			$("#huobi_pay_pwd").val('');
			
			var self = this;
			self.getRandomToken(); /// 获取随机的Token
			
			var transNo = obj.orderNumber;    // 订单流水号
			
			var jsonParam = {};
			
			// 查询货币余额
			comm.requestFun("findCurrencyBlance",jsonParam,function(response){
				
					if(response.data){
						currencyBlance = response.data.currencyBlance || "0";
						$("#hbAmount").text(comm.formatMoneyNumber(currencyBlance));
						$("#hbAmount_hb").val(currencyBlance);
					}
					
					
			},comm.lang("hsbAccount"));
			
			$('#forgetPwd_hbzh').click(function(){
				comm.setCache('safeSet','resetDelPassword','1');
				$('#side_safetySet').click();	
			});
			
			$('#comfirmPay_btn').click(function(){
				
				var hbAmount = $("#hbAmount_hb").val() || 0;
				var payAmount = obj.realMoney || 0;
				var hbAmount_f = parseFloat(hbAmount);
				var payAmount_f = parseFloat(payAmount);
				if(payAmount_f > hbAmount_f || hbAmount == 0){
					comm.warn_alert("货币余额不足");
					return;
				}
				
				var valid = self.validatePWD();
				if(!valid.form()){
					return;	
				}
				
				//密码根据随机token加密
				var randomToken = $("#hbpay_randomToken").val();	//获取随机token
				var dealPwd = $("#huobi_pay_pwd").val();
				var payPwd = comm.tradePwdEncrypt(dealPwd,randomToken);		
				//传递参数
				var jsonData = {	
					custType        :   2,
					transNo			:   transNo,
					randomToken     :   randomToken,
					tradePwd		:	payPwd
				};
				
				// 校验支付密码后去付款
				comm.requestFun("validateHbPay",jsonData,function(response){
					
					comm.alert({
						width : 800,
						height : 200,
						imgClass : 'tips_yes',
						timeClose : 3,
						content : '恭喜，您的订单支付成功！金额为：<span class="pay-text-red f16">' + obj.paymentAmount + '</span><br /><div class="mt20 tc">您的订单号为：' + obj.orderNumber + '，系统将在3秒后，自动返回订单详情页面。</div>'	
					});
					
					setTimeout(jumpUrls,3000);
					function jumpUrls(){
						
						$("#ul_myhs_right_tab li:eq(3)").find("a").trigger("click");
					}
				},comm.lang("hsbAccount"));
				
			});	
			
			
			$('#qPay_btn').click(function(){
				obj.paymentMethod = 'kjzf';
				require(['paySrc/pay'],function(pay){
					pay.showPage(obj);
				});	
			});
			
			$('#bPay_btn').click(function(){
				obj.paymentMethod = 'wyzf';
				require(['paySrc/pay'],function(pay){
					pay.showPage(obj);
				});		
			});
			
			//银行卡支付
			$('#bcPay_btn').click(function(){
				obj.paymentMethod = 'yhkzf';
				require(['paySrc/pay'],function(pay){
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
					$("#hbpay_randomToken").val(response.data);
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
					huobi_pay_pwd : {
						required : true	,
						minlength : 8
					}	
				},
				messages : {
					huobi_pay_pwd : {
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
	}	
});