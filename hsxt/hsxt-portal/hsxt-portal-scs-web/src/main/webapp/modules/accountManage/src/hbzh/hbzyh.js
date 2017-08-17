define(['text!accountManageTpl/hbzh/hbzyh.html','text!accountManageTpl/hbzh/hbzyh_jymm.html' ,'accountManageDat/hbzh/hbzh','accountManageLan'],function(jfzhbTpl,hbzyh_jymmTpl ,dataModule){
	return hbzyh = {
		restrictData : null, 	//业务限制数据
		accountAmount:null,		//货币金额
		transferBankCfg:null,	//转银行配置
		
		showPage : function(ac_appTransferMoneyNum, accId){
			$('#busibox').html(_.template(jfzhbTpl));
			
			//处理返回修改时金额丢失
			if(ac_appTransferMoneyNum){
				$("#ac_appTransferMoneyNum").val(ac_appTransferMoneyNum);
			}
			//Todo...
		 	$('#bankNo').selectList({width:270,optionWidth:270});
			$('#bankNo1').selectList({width:270,optionWidth:270});
			
			//货币账户-货币转银行，查询绑定银行信息---------------
			dataModule.initCurrencyTransferBank(null, function (response) {
				//设置货币余额
				$("#ac_cashAccTotal").text(comm.formatMoneyNumber(response.data.pointBlance)||'0.00');
				$("#cashAccTotal").val(response.data.pointBlance||'0.00');
				$("#ac_minCashNum").val(response.data.currencyMin||'0.00');
				$("#hb_ac_minCashNum").text(response.data.currencyMin||'0.00');
				
				hbzyh.accountAmount=response.data.pointBlance;
				hbzyh.transferBankCfg=response.data;
				hbzyh.restrictData = response.data.restrict;						//获取业务限制数据
				
				// 清空银行列表
				$("#ac_bankCardList").empty();
				
				if(!response.data.bankList){
					comm.alert({content:'请先绑定银行卡'});	
					$("#btn_hbzyh_post").hide();
					return;
				}
				
				//保留查询到自己的银行卡信息
				bankList = response.data.bankList;
				
				$.each(response.data.bankList, function (i, o) {
					var moren = ""; // 是否默认
					var yz = "";
					var defaultSelect = '';
					//处理返回修改时银行丢失
					if(accId){
						if(o.accId == accId){
							defaultSelect = 'selected="selected"';
						}
					}else{
						if (comm.navNull(o.isDefaultAccount) == '1'){
							moren = "[默认]";
							defaultSelect = 'selected="selected"'
						}
					}
					if (comm.navNull(o.isValidAccount) == '1') {
						yz = "[已验证]";
					} else {
						yz = "[未验证]";
					}
					$("#ac_bankCardList").append('<option value="' + o.accId + '" '+defaultSelect+' currencyCode="'+response.data.currencyCode+'" bankNo="'+o.bankCode+'" bankAccNo="'+ o.bankAccNo +'" bankAccName="'+o.bankAccName+'" bankName="'+o.bankName+'" cityNo="'+o.cityNo+'" cityName="'+o.cityName+'" provinceNo="'+o.provinceNo+'">' + yz + "&nbsp;&nbsp;" + o.bankName + o.bankAccNo + "&nbsp;&nbsp;" + moren + '</option>');
					defaultSelect = '';
				});
				
				
			});
			
			//切换到输入交易密码
			$('#btn_hbzyh_post').click(function(e) {
				try{
					//当前企业货币转银行是否可以判断
					var restrictValue = hbzyh.restrictData.restrictValue;
					if("1" == restrictValue){
						var restrictRemark = hbzyh.restrictData.restrictRemark;
						var msg = "货币转银行业务暂时不能提交！原因：" + restrictRemark;
						comm.warn_alert(msg);
						return;
					}
				}catch(ex){}
				
				//转出货币数量数据验证
				var valid = hbzyh.validateData();
				if (!valid.form()) {
					return;
				}
				
				/**	验证今日限额超标	*/
				//转出金额
				var $transferMoney = parseFloat(comm.trim($("#ac_appTransferMoneyNum").val()));	
				//今日已发生金额
				var dayTransferAmount = comm.isNotEmpty(hbzyh.transferBankCfg.dayTransferAmount)? parseFloat(hbzyh.transferBankCfg.dayTransferAmount) : 0;
				//单日转出最大金额
				var dayTransferMaxAmount = comm.isNotEmpty(hbzyh.transferBankCfg.dayTransferMaxAmount) ? parseFloat(hbzyh.transferBankCfg.dayTransferMaxAmount) : 0;	
				
				//今日发生金额+转出金额>今日消费最大金额
				if(dayTransferMaxAmount!=0 && dayTransferMaxAmount<($transferMoney+dayTransferAmount)){
					comm.warn_alert(comm.strFormat(comm.lang("accountManage").day_transfer_max_amount_error,[dayTransferAmount,$transferMoney,dayTransferMaxAmount]),600);
					return false;
				}
				
				//银行卡绑定提示
				if (null == $("#ac_bankCardList").val() || $("#ac_bankCardList").val() == "") {
					comm.warn_alert(comm.lang("cashAccount").pleaseBindBankCard);
					return;
				}
				
				cityNo = $("#ac_bankCardList option:selected").attr("cityNo") =='' ?'230123':$("#ac_bankCardList option:selected").attr("cityNo");
				
				var jsonParam = {
						bankNo 	:	$("#ac_bankCardList option:selected").attr("bankNo"),			//银行代码
						bankCityNo	:	cityNo,														//所属地区
						amount		:	$.trim($("#ac_appTransferMoneyNum").val())					//转账金额
				};
				var ac_bankCardList = $("#ac_bankCardList option:selected");		//选择银行卡No
				var ac_appTransferMoneyNum = $.trim($("#ac_appTransferMoneyNum").val());
                $('#busibox').html(_.template(hbzyh_jymmTpl)) ;
				
				dataModule.getBankTransFee(jsonParam, function (response) {
					
					var selectedOption =  ac_bankCardList;		//选择银行卡No
					
					// 开户地区-城市
					cacheUtil.syncGetRegionByCode(null, selectedOption.attr("provinceNo") , selectedOption.attr("cityNo"), "  ", function(resdata){
						$("#qr_ac_bankAddr").text(resdata);
					});
					//$("#qr_ac_bankAddr").text(selectedOption.attr("cityNo")+" "+selectedOption.attr("provinceNo"));
					// 银行账号
					$("#qr_ac_bankAccount").text(selectedOption.attr("bankAccNo"));
					//转账金额
					$("#qr_ac_appTransferMoneyNum").text(ac_appTransferMoneyNum);
					//账户名称
					$("#qr_ac_bankAccountName").text(selectedOption.attr("bankAccName"));
					// 开户银行
					$("#qr_ac_bankName").text(selectedOption.attr("bankName"));
					// 结算币种
					$("#qr_ac_currency").text(selectedOption.attr("currencyCode"));
					// 银行扣除手续费
					$("#qr_ac_poundage").text(comm.formatMoneyNumber(response.data));
					// 实际到账金额
					$("#qr_ac_realMoneyNum").text(comm.formatMoneyNumber(ac_appTransferMoneyNum));
					
					//转出的货币金额
					$("#transferMoneyNum").val(ac_appTransferMoneyNum);
					//选择的银行卡id
					$("#accId").val(selectedOption.val());
					
					//获取随机token
					hbzyh.getRandomToken();
					
				});
				
				//修改
				$('#btn_hbzyh_edit').click(function(e) {
					var accId = $("#accId").val();
                    hbzyh.showPage(ac_appTransferMoneyNum, accId);
                });
				//确认
				$('#btn_hbzyh_ok_post').click(function(e) {
                    //Todo..
					//交易密码数据验证
					var valid = hbzyh.validateDealPwd();
					if (!valid.form()) {
						return;
					}
					//获取随机token
					hbzyh.getRandomToken();
					
					comm.i_confirm(comm.lang("accountManage").affireHbToYh,function() {
						//密码根据随机token加密
						var randomToken = $("#ac_randomToken").val();			//获取随机token
						var dealPwd = $.trim($("#qr_ac_dealPwd").val());		//获取交易密码
						var word = comm.tradePwdEncrypt(dealPwd,randomToken);		//加密
						
						var jsonParam = {
									accId 		: 	$("#accId").val(),	//选择的银行卡信息
									amount  	: 	$("#transferMoneyNum").val(),					//货币金额
									tradePwd	:	word,											//交易密码
									randomToken :   randomToken										//随机token
							};
							
						//提交货币转银行
						dataModule.currencyTransferBank(jsonParam, function (response) {
							comm.alert({content:comm.lang("accountManage").moneyTransferBanks});	
							//刷新界面
							hbzyh.showPage();
						});
					});
                });	
            });
		},
		/**
		 * 获取随机token数据
		 */
		getRandomToken : function (){
			//获取随机token
			comm.getToken(function(response){
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
		/**
		 * 银行账号掩码处理
		 */
		maskBankAccNo : function(bankAccNo){
			try{
				return bankAccNo.substr(0, 4) + " **** **** " + bankAccNo.substr(bankAccNo.length - 4, 4);
			}catch(e){
				return bankAccNo;
			}
		},
		/**
		 * 用户名称掩码处理
		 */
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
						digits : true,
						maxlength : 10,
						greater : "#cashAccTotal",
						less : "#ac_minCashNum",
						max:function(){
							//获取转出最大金额
							var maxAmount = hbzyh.transferBankCfg.transferMaxAmount;
							
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
						required : comm.lang("accountManage")[30014],
						digits : comm.lang("accountManage")[30016],
						maxlength : comm.lang("accountManage").currencyBankMaxlength,
						greater : comm.lang("accountManage")[30113],
						less : comm.lang("accountManage")[30114],
						max : comm.lang("accountManage").transfer_max_amount
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
			return $("#ac_appform_jymm").validate({
				rules : {
					qr_ac_dealPwd : {
						required : true,
						maxlength : 8,
						minlength : 8
					}
				},
				messages : {
					qr_ac_dealPwd : {
						required : comm.lang("accountManage")[30002],
						maxlength : comm.lang("accountManage").dealPwdLength,
						minlength : comm.lang("accountManage").dealPwdLength
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
	return hbzyh;
}); 

 