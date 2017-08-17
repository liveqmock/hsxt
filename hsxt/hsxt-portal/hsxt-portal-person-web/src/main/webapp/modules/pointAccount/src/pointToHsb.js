define(["text!pointAccountTpl/pointToHsb.html", "text!pointAccountTpl/pointToHsb_affirm.html"], function (tpl, tplAffirm) {
	var restrictData = null;
	var pointToHsb = {
		show : function(dataModule){
			// 加载页面模板
			$("#myhs_zhgl_box").html(tpl + tplAffirm);
			
			// 获取积分转现初始化数据
			dataModule.initIntegralTransferHsb(null,function (response) {
				restrictData = response.data;
					// 非空验证
					if (response) 
					{
						// 设置可用积分数
						$("#pth_pointAccUsable").text(comm.formatMoneyNumber(response.data.pointBlance) || '0');	
						$("#pth_maxpointAccUsable").val(response.data.pointBlance || '0');	
						// 设置转出最小积分数量
						$("#integrationMin").text(response.data.integrationMin || '0');
						//设置Hidden 最小积分数量
						$("#pth_minPointNum").val(response.data.integrationMin || '0');
						// 设置币种
						$("#pth_currencyCode").val(response.data.exchangeRate|| '0');
						
					}
				}
			);
			
			$('#pth_toHsbPointNum').keyup(function(){
				var pth_toHsbPointNum=$('#pth_toHsbPointNum').val();
				pointToHsb.CtoH(pth_toHsbPointNum);
			});
			
			
			
			// 提交申请点击事件
			$('#pthQrBtn').click(function(){
				var restrictValue = restrictData.restrictValue;
				if("1" == restrictValue){
					var restrictRemark = restrictData.restrictRemark;
					var msg = "积分转互生币业务暂时不能受理！<br />原因：" + restrictRemark;
					comm.warn_alert(msg);
					return;
				}
				// 转出积分数数据验证
				var valid = pointToHsb.validateData();
				if (!valid.form()) {
					return;
				}
				var toHsbPointNum =$("#pth_toHsbPointNum").val();
				$("#qr_pth_currency").text($("#pth_currencyCode").val());	// 币种
				$("#qr_pth_toHsbPointNum").text(comm.formatMoneyNumber(toHsbPointNum || '0'));	// 转出积分数数量
				
				// 转入的数量
				$("#qr_pth_realHsbNum").text(comm.formatMoneyNumber(toHsbPointNum || '0'));
				//清空默认交易密码
				$("#qr_pth_dealPwd").val("");
				// 隐藏申请页面
				$('#pointToHsb').addClass('none');
				// 显示确认页面
				$('#pointToHsb_affirm').removeClass('none');
			});
			
			// 积分转互生币返回修改页面
			$("#pth_return").click(function () {
				$('input').tooltip().tooltip('destroy');
				$('#qr_pth_dealPwd').val("");
				// 显示申请页面
				$('#pointToHsb').removeClass('none');
				// 隐藏确认页面
				$('#pointToHsb_affirm').addClass('none');
			});
			
			// 确认提交点击事件
			$('#pth_appComit').click(function () {
				// 交易密码数据验证
				var valid = pointToHsb.validateDealPwd();
				if (!valid.form()) {
					return;
				}
				
				// 获取随机token
				pointToHsb.getRandomToken();
				
				comm.i_confirm(comm.lang("pointAccount").confirmIntegralHSCurrency, function () {
					// 密码根据随机token加密
					var randomToken = $("#qr_pth_randomToken").val();	// 获取随机token
					var dealPwd = $.trim($("#qr_pth_dealPwd").val());	// 获取交易密码
					var word = comm.tradePwdEncrypt(dealPwd,randomToken);		// 加密
					
					// 传递参数
					var jsonData = {
						tradePwd 	: 	word,											// 交易密码
						amount		: 	$("#pth_toHsbPointNum").val(),		// 转出积分数
						randomToken :   randomToken,									// 随机token（防止CSRF攻击）
					};
					
					// 积分转互生币提交
					dataModule.integralTransferHsb(jsonData, function (response) {
						$("#pth_dialog").dialog({
							title : comm.lang("pointAccount").integralHSBSuccessfully,
							dialogClass : "no-dialog-close",
							width : "260",
							modal : true
						});
						// 清空数据
					//	$("#qr_pth_toHsbPointNum").text("");
						$("#qr_pth_dealPwd").val("");
						
					});
				});
				
			});
			// 点击dialog窗的确定按钮
			$('#pth_cg_sure').click(function(){
				$("#pth_dialog").dialog("destroy");
				$('#ul_myhs_right_tab li a[data-id="2"]').trigger('click');
			});
		},
		/**
		 * 获取随机token数据
		 */
		getRandomToken : function (){
			// 获取随机token
			comm.getToken(null,function(response){
				// 非空数据验证
				if(response.data)
				{
					$("#qr_pth_randomToken").val(response.data);
				}
				else
				{
					comm.warn_alert(comm.lang("myHsCard").randomTokenInvalid);
				}
				
			});
		},

		/**
		 * 积分数量规则验证
		 */
		validateData : function () {
			
			// 转出积分数数据验证
			return $("#pth_appform").validate({
				rules : {
					pth_toHsbPointNum : {
						required : true,
						digits2 : true,
						maxlength : 10,
						greater : "#pth_maxpointAccUsable",
						less : "#pth_minPointNum"
							
					}
				},
				messages : {
					pth_toHsbPointNum : {
						required : comm.lang("pointAccount")[30001],
						digits2 : comm.lang("pointAccount")[30154],
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
		 * 全角转半角
		 */
		CtoH:function(value){
					var str=value;
					var result="";
					for (var i = 0; i < str.length; i++)
					{
								if (str.charCodeAt(i)==12288)
								{
								result+= String.fromCharCode(str.charCodeAt(i)-12256);
								continue;
								}
								if (str.charCodeAt(i)>65280 && str.charCodeAt(i)<65375)
								    result+= String.fromCharCode(str.charCodeAt(i)-65248);
								else 
									result+= String.fromCharCode(str.charCodeAt(i));
					} 
					value=result;
					$('#pth_toHsbPointNum').val(value);
				}, 
			
			

		/**
		 * 交易密码验证
		 */
		validateDealPwd : function() {
			return $("#qr_pth_appform").validate({
				rules : {
					qr_pth_dealPwd : {
						required : true,
						maxlength : 8,
						minlength : 8
					}
				},
				messages : {
					qr_pth_dealPwd : {
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
	return pointToHsb;
});
