define(['text!businessHandlingTpl/nhjn/pay/hsbPay.html',
        'businessHandlingDat/businessHandling',
        'accountManageDat/hsbzh/hsbzh',
		], function(hsbPayTpl,systemBusiness,hsbzhAjax){
	return {
		showPage : function(obj){
			$('#payment_operate').html(_.template(hsbPayTpl, obj));			
			var self = this;
			if(comm.isNotEmpty(obj.isFrist)){
				hsbzhAjax.findHsbBlance(null,function(resp){
					var mayUseHsb = comm.isNotEmpty(resp.data.circulationHsb)?(resp.data.circulationHsb):0;
					obj.isFrist = null;
					if(Number(obj.payHsbAmount) > Number(mayUseHsb)){
						$('#qPay_btn').trigger('click');
					}
				});
			}
			$('#forgetPwd_hsb').click(function(){
				var parentId= "030700000000";
				
				var objParam={"parentId":parentId}
				
				comm.resetMenu(objParam,function(){
					$("li >a").each(function(){
						var arr = new Array();
						var menuurl = $(this).attr("menuurl");
						if(menuurl == "safeSetSrc/czjymm/tab"){
							var id = $(this).attr("id");
							$("#" + id).trigger('click');
						}
					});
				});
			});
			
			$('#comfirmPay_btn').click(function(){
				if(!self.validatePWD()){
					return;	
				}
				
				//获取token  加密交易密码
				comm.getToken(function(resp){
					
					var tradePwd = comm.tradePwdEncrypt($('#pay_pwd').val(),resp.data);
					
					/*******缴纳年费 start***********/
					var param = comm.getRequestParams();
					var postData = {
							"order.custId" : param.entCustId,
							"order.orderNo" : obj.orderNo,
							"order.payChannel" : 200,
							"order.orderOperator" : param.custName,
							tradePwd : tradePwd,
							randomToken:resp.data
					};
					
					systemBusiness.payAnnualFee(postData,function(res){
						//支付成功  隐藏支付按钮
						$("#comfirmPay_btn").hide();
						//网银url
						var url = res.data;
						comm.alert({
							width : 650,
							height : 200,
							imgFlag : true,
							imgClass : 'tips_yes',
							timeClose : 3,
							content : '恭喜，您的订单支付成功！金额为：<span class="pay-text-red f16">' + obj.paymentAmount + '</span><br /><div class="mt20 tc">您的订单号为：'+obj.orderNo+'，系统将在3秒后，自动返回订单详情页面。</div>'	
						});
					});
					/*********** 缴纳年费end ********/
					
				});
				
				
			});	
			
			
			$('#qPay_btn').click(function(){
				$("#pay_pwd").tooltip().tooltip("destroy");
				obj.paymentMethod = 'quickPay';
				require(['businessHandlingSrc/nhjn/pay/pay'],function(pay){
					pay.showPage(obj);
				});	
			});
			
			$('#bPay_btn').click(function(){
				$("#pay_pwd").tooltip().tooltip("destroy");
				obj.paymentMethod = 'eBankPay';
				require(['businessHandlingSrc/nhjn/pay/pay'],function(pay){
					pay.showPage(obj);
				});		
			});
			
			//银行卡支付
			$('#bcPay_btn').click(function(){
				obj.paymentMethod = 'yhkzf';
				require(['businessHandlingSrc/nhjn/pay/pay'],function(pay){
					pay.showPage(obj);
				});		
			});
			
		},
		validatePWD : function(){
			return comm.valid({
				formID : '#pwdForm',
				rules : {
					pay_pwd : {
						required : true	,
						minlength:8
					}	
				},
				messages : {
					pay_pwd : {
						required : comm.lang("businessHandling").inputTradePwd,
						minlength:comm.lang("businessHandling").dealPwdLength
					}
				}
			});	
		}	
	}	
});