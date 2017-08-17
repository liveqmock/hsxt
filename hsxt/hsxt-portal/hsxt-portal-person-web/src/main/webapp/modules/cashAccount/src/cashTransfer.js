define(["text!cashAccountTpl/cashTransfer.html", "text!cashAccountTpl/cashTransfer_affirm.html","myHsCardDat/myHsCard"], function (tpl, tplAffirm,myHsCard) {
	var restrictData = null;
	var cashTransfer = {
			
		accountAmount:null,		//货币金额
		transferBankCfg:null,	//转银行配置
			
		show : function(dataModule){
			//加载页面模板
			$("#myhs_zhgl_box").html(tpl + tplAffirm);
			
			var bankList = null;
			
			//货币账户-货币转银行，查询绑定银行信息---------------
			dataModule.initCurrencyTransferBank(null, function (response) {
				restrictData = response.data;
				//设置货币余额
				$("#ac_cashAccTotal").text(comm.formatMoneyNumber(response.data.pointBlance)||'0.00');
				$("#cashAccTotal").val(response.data.pointBlance||'0.00');
				$("#hb_ac_minCashNum").text(response.data.currencyMin||'0.00');
				$("#ac_minCashNum").val(response.data.currencyMin||'0.00');
				
				//保存账户信息
				cashTransfer.accountAmount=response.data.pointBlance;	
				cashTransfer.transferBankCfg=response.data;
				
				// 清空银行列表
				$("#ac_bankCardList").empty();
				
			
//				if(!response.data.bankList){
//					comm.alert({content:'请先绑定银行卡'});	
//					$("#acQrBtn").hide();
//					return;
//				}
				
				
				
				//保留查询到自己的银行卡信息
				bankList = response.data.bankList;
				
				if(comm.isNotEmpty(bankList)){
					$.each(response.data.bankList, function (i, o) {
						var moren = ""; // 是否默认
						var yz = "";
						var defaultSelect = '';
						if (o.isDefaultAccount == '1'){
							moren = "[默认]";
							defaultSelect = 'selected="selected"';
						}
						if (o.isValidAccount == '1') {
							yz = "[已验证]";
						} else {
							yz = "[未验证]";
						}
						
						$("#ac_bankCardList").append('<option value="' + o.accId + '" '+defaultSelect+' isMask="'+response.data.isMask+'" currencyCode="'+o.currencyCode+'" bankNo="'+o.bankCode+'" bankAccNo="'+ o.bankAccNo +'" bankAccName="'+o.bankAccName+'" bankName="'+o.bankName+'" cityNo="'+o.cityNo+'" cityName="'+o.cityName+'" provinceNo="'+o.provinceNo+'">' + yz + "&nbsp;&nbsp;" + o.bankName + cashTransfer.maskBankAccNo(o.bankAccNo) + "&nbsp;&nbsp;" + moren + '</option>');
					});
				}
				
				//获取是否在重要信息变更
				myHsCard.initPerChange(null, function(res){
					$('#isRealNameAuth').val(comm.removeNull(res.data.authStatus));
					if(res.data.perChangeInfo){
						$('#isSubmitImportInfo').val(comm.removeNull(res.data.perChangeInfo.status));
					}
				});
			});
			
			
			//提交申请点击事件
			$('#acQrBtn').click(function(){
				var restrictValue = restrictData.restrictValue;
				if("1" == restrictValue){
					var restrictRemark = restrictData.restrictRemark;
					var msg = "货币转银行业务暂时不能提交！<br />原因：" + restrictRemark;
					comm.warn_alert(msg);
					return;
				}
				var isRealNameAuth = $('#isRealNameAuth').val();
				if(comm.isEmpty(isRealNameAuth)|| isRealNameAuth == 1){
					comm.warn_alert(comm.lang("cashAccount")[30160]);
					return;
				}
				var isSubmitImportInfo = $('#isSubmitImportInfo').val();
				if(comm.isNotEmpty(isSubmitImportInfo)){
					if(isSubmitImportInfo == 0 || isSubmitImportInfo == 1 ){
						comm.warn_alert(comm.lang("cashAccount")[30159]);
						return;
					}
				}
				
				//转出积分数数据验证
				var valid = cashTransfer.validateData();
				if (!valid.form()) {
					return;
				}
				
				/**	验证今日限额超标	*/
				//转出金额
				var $transferMoney = parseFloat(comm.trim($("#ac_appTransferMoneyNum").val()));	
				//今日已发生金额
				var dayTransferAmount = parseFloat((comm.isNotEmpty(cashTransfer.transferBankCfg.dayTransferAmount)? cashTransfer.transferBankCfg.dayTransferAmount : 0));
				//单日转出最大金额
				var dayTransferMaxAmount = comm.isNotEmpty(cashTransfer.transferBankCfg.dayTransferMaxAmount) ? parseFloat(cashTransfer.transferBankCfg.dayTransferMaxAmount) : 0;	
				
				//今日发生金额+转出金额>今日消费最大金额
				if(dayTransferMaxAmount!=0 && dayTransferMaxAmount<($transferMoney+dayTransferAmount)){
					comm.warn_alert(comm.strFormat(comm.lang("cashAccount").day_transfer_max_amount_error,[dayTransferAmount,$transferMoney,dayTransferMaxAmount]),600);
					return false;
				}
				
				//银行卡绑定提示
				if (null == $("#ac_bankCardList").val() || $("#ac_bankCardList").val() == "") {
					comm.warn_alert(comm.lang("cashAccount").pleaseBindBankCard,800);
					return;
				}
				cityNo = $("#ac_bankCardList option:selected").attr("cityNo") =='' ?'230123':$("#ac_bankCardList option:selected").attr("cityNo");
				
				var jsonParam = {
						bankNo 	:	$("#ac_bankCardList option:selected").attr("bankNo"),		//银行账号
						bankCityNo	:	cityNo,														//所属地区
						amount		:	$.trim($("#ac_appTransferMoneyNum").val())					//转账金额
				};
				
				
				dataModule.getBankTransFee(jsonParam, function (response) {
					
					var selectedOption =  $("#ac_bankCardList option:selected");		//选择银行卡No
					
					
					// 开户地区-城市
					cacheUtil.syncGetRegionByCode(null, selectedOption.attr("provinceNo") , selectedOption.attr("cityNo"), "  ", function(resdata){
						$("#qr_ac_bankAddr").text(resdata);
					});
					
					// 银行账号
					$("#qr_ac_bankAccount").text(cashTransfer.maskBankAccNo(selectedOption.attr("bankAccNo")));
					//转账金额
					$("#qr_ac_appTransferMoneyNum").text($.trim($("#ac_appTransferMoneyNum").val()));
					
					var bankAccName = $.trim(selectedOption.attr("bankAccName")); //账户名称
					var isMask = $.trim(selectedOption.attr("isMask")); //是否添加掩码
					
					if(isMask != "false"){
						bankAccName = cashTransfer.maskName(bankAccName) ;
					}
					//账户名称
					$("#qr_ac_bankAccountName").text(bankAccName);
					// 开户银行
					$("#qr_ac_bankName").text(selectedOption.attr("bankName"));
					// 结算币种
					$("#qr_ac_currency").text(selectedOption.attr("currencyCode"));
					// 银行扣除手续费
					$("#qr_ac_poundage").text(comm.formatMoneyNumber(parseFloat(response.data)));
					// 实际到账金额//lyh20160215修改，实际到账金额=申请金额，不需要从申请金额中减去手续费
					//$("#qr_ac_realMoneyNum").text(comm.formatMoneyNumber((parseFloat($("#ac_appTransferMoneyNum").val())-parseFloat(response.data))));
					$("#qr_ac_realMoneyNum").text(comm.formatMoneyNumber((parseFloat($("#ac_appTransferMoneyNum").val()))));
				});
				
				//div显示控制
				$('#cashTransfer').addClass('none');
				$('#cashTransfer_affirm').removeClass('none');
			});
			
			//返回修改页面
			$("#ac_return").click(function () {
				$('input').tooltip().tooltip('destroy');
				$('#qr_ac_dealPwd').val("");
				//显示申请页面
				$('#cashTransfer').removeClass('none');
				//隐藏确认页面
				$('#cashTransfer_affirm').addClass('none');
			});
			
			//确认提交点击事件
			$('#ac_appTransferSubmit').click(function () {
				//交易密码数据验证
				var valid = cashTransfer.validateDealPwd();
				if (!valid.form()) {
					return;
				}
				
				//获取随机token
				cashTransfer.getRandomToken();
				
				comm.i_confirm(comm.lang("cashAccount").confirmCurrencyBank, function () {
					
						comm.showLoading();
						
						//密码根据随机token加密
						var randomToken = $("#ac_randomToken").val();		//获取随机token
						var dealPwd = $.trim($("#qr_ac_dealPwd").val());	//获取交易密码
						var word = comm.tradePwdEncrypt(dealPwd,randomToken);		//加密
						
						//lyh修改
						var ac_appTransferMoneyNum =$.trim($("#ac_appTransferMoneyNum").val());
						if(ac_appTransferMoneyNum!=""){
							if(ac_appTransferMoneyNum.indexOf(".")!=-1){
								var sxindex=ac_appTransferMoneyNum.indexOf(".");
								var laststr=ac_appTransferMoneyNum.split(".");
									ac_appTransferMoneyNum=ac_appTransferMoneyNum.substring(0,sxindex);
								$("#ac_appTransferMoneyNum").val(ac_appTransferMoneyNum);
							}
						}
						
						
						var jsonParam = {
								accId 		: 	$("#ac_bankCardList option:selected").val(),	//选择的银行卡信息
								amount  	: 	ac_appTransferMoneyNum,			//货币金额
								tradePwd	:	word,											//交易密码
								randomToken :   randomToken										//随机token
						};
						//提交货币转银行
						dataModule.currencyTransferBank(jsonParam, function (response) {
							$("#ac_dialog").dialog({
								dialogClass : "no-dialog-close",
								title : comm.lang("cashAccount").moneyTransferBanks,
								width : "340",
								modal : true
							});
							
								//清空数据
//								$("#qr_ac_appTransferMoneyNum").text("");
//								$("#qr_ac_realMoneyNum").text("");
//								$("#qr_ac_poundage").text("");
								$("#qr_ac_dealPwd").val("");
							});
							comm.closeLoading();
				});
			});
			//点击dialog窗的确定按钮
			$('#ac_cg_sure').click(function(){
				$("#ac_dialog").dialog("destroy");
				$('#ul_myhs_right_tab li a[data-id="2"]').trigger('click');
			});
		},
		maskBankAccNo : function(bankAccNo){
			try{
				return bankAccNo.substr(0, 4) + " **** **** " + bankAccNo.substr(bankAccNo.length - 4, 4);
			}catch(e){
				return bankAccNo;
			}
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
					$("#ac_randomToken").val(response.data);
				}
				else
				{
					comm.warn_alert(comm.lang("cashAccount").randomTokenInvalid);
				}
				
			});
		},
		maskName : function(custName){
			try{
				//如果是英文名实名注册  只保留首位，其余遮挡
				if(/^[A-Za-z]+$/.test(custName)){
	 				return comm.plusXing(custName,1,0,true);
	 			}else{
	 				//其他中文实名注册
		 			return comm.plusXing(custName,1,0,true);
	 			}
			}catch(ex){
				return custName;
			}
		},
		validateData : function () {
			return $("#ac_appform").validate({
				rules : {
					ac_appTransferMoneyNum : {
						required : true,
						maxlength : 10,
						greater : "#cashAccTotal",
						less : "#ac_minCashNum",
						digits2 : true,
						max:function(){
							//获取转出最大金额
							var maxAmount = cashTransfer.transferBankCfg.transferMaxAmount;
							
							//当未设置转出最大金额时默认给个最大值
							if(comm.isEmpty(maxAmount) || parseFloat(maxAmount)<=0){
								return 9999999999;
							}
							
							return maxAmount;
						}
					}
				},
				messages : {
					ac_appTransferMoneyNum : {
						required : comm.lang("cashAccount")[30014],
						maxlength : comm.lang("cashAccount").currencyBankMaxlength,
						greater : comm.lang("cashAccount")[30113],
						less : comm.lang("cashAccount")[30114],
						digits2 : comm.lang("cashAccount")[30016],
						max : comm.lang("cashAccount").transfer_max_amount
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
			return $("#ac_appForm_qr").validate({
				rules : {
					qr_ac_dealPwd : {
						required : true,
						maxlength : 8,
						minlength : 8
					}
				},
				messages : {
					qr_ac_dealPwd : {
						required : comm.lang("cashAccount")[30002],
						maxlength : comm.lang("cashAccount").dealPwdLength,
						minlength : comm.lang("cashAccount").dealPwdLength
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
	return cashTransfer;
});
