define(['text!accountManageTpl/hsbzh/hsbzhb.html',
		'accountManageDat/hsbzh/hsbzh',
		'accountManageLan'],function( hsbzhbTpl,dataModule ){
	return hsbToCash ={	 	
		restrictData : null, 	//业务限制数据
		showPage : function(){
			$('#busibox').html(_.template(hsbzhbTpl)) ;
		 	
			
			// 初始化页面
			dataModule.initHsbTransferCurrency(null, function(response) {
				 $("#htc_hsbAccTotal").text(comm.formatMoneyNumber(response.data.circulationHsb) || '0'); 		// 流通币余额
				 $("#htc_hid_hsbAccTotal").val(response.data.circulationHsb || '0'); 	// 流通币余额
				 $("#htc_hid_minHsbNum").val(response.data.hsbMin);				//转出最小互生币数整倍数
				 $("#htc_hid_currencyFee").val(response.data.currencyFee);		//货币转费用
				 $("#htc_hid_exchangeRate").val(response.data.exchangeRate);	//转换比率
				 $("#htc_currencyCode").val(response.data.currencyCode);		//当前平台币种
				 $("#htc_txt_minHsbNum,#htc_minHsbNum").text(response.data.hsbMin);	 			//转出最小互生币数
				 $("#htc_currencyFee,#htc_txt_currencyFee").text((response.data.currencyFee * 100));	//货币转换费
				 $("#htc_toCashHsbNum").val("");
				 hsbToCash.restrictData = response.data.restrict;						//获取业务限制数据
			});
			
			// 转现互生币数文本框-按下键盘事件
			$("#htc_toCashHsbNum").bind("keyup blur change", function () {
				
				//计算转货币费用
				hsbToCash.transferCurrency();
			});	
		 	//提交表单
		 	$('#hsbzhb_tj').click(function(){
		 		try{
		 			//互生币转货币业务暂时不能受理 当出错的时候继续执行
		 			var restrictValue = hsbToCash.restrictData.restrictValue;
					if("1" == restrictValue){
						var restrictRemark = hsbToCash.restrictData.restrictRemark;
						var msg = "互生币转货币业务暂时不能受理！原因：" + restrictRemark;
						comm.warn_alert(msg);
						return;
					}
		 		}catch(ex){
		 		}
		 		
				
		 		//重新计算转货币费用
		 		hsbToCash.transferCurrency();
		 		
		 		 // 转出积分数数据验证
				var valid = hsbToCash.validateData();
				if (!valid.form()) {
					return;
				}
				// 加载数据
				$("#qr_htc_toCashHsbNum").text(comm.formatMoneyNumber($("#htc_toCashHsbNum").val())); // 转出数量
				$("#htc_fee_cost").text($("#hb_cost").text());//货币转换费
				$("#htc_fee_cost_rate").text(($("#htc_hid_currencyFee").val()* 100)); // 货币转换比
				$("#qr_htc_realToCashNum").text(comm.formatMoneyNumber($("#htc_realToCashNum").text()));//实际转入数量
				$("#bizhong").text($("#htc_currencyCode").val()); // 结算币种
				$("#qr_htc_rate").text($("#htc_hid_exchangeRate").val());//转换比率
				var num = $("#htc_realToCashNum").text() / $("#htc_hid_currencyFee").val();
				$("#qr_htc_toCashNum").text(comm.formatMoneyNumber($("#htc_realToCashNum").text()));//转入货币金额
				//设置显示div
		 		$('#div_hsbzhb_tj').addClass('none')  ;
		 		$('#div_hsbzhb_qr').removeClass('none')  ;		 		 
		 	}); 	
		 	
		 	//修改
		 	$('#hsbzhb_xg').click(function(){
		 		$('#div_hsbzhb_tj').removeClass('none')  ;
		 		$('#div_hsbzhb_qr').addClass('none')  ;		 	
		 	}); 	
		 	
		 	//确认
		 	$('#hsbzhb_qr').click(function(){
		 		// 交易密码数据验证
				var valid = hsbToCash.validateDealPwd();
				if (!valid.form()) {
					return;
				}
				//获取随机token
				hsbToCash.getRandomToken();
		 		// 确认要申请互生币转货币吗？
				comm.i_confirm(comm.lang("accountManage").affireHsbToCash,function() {
					
						//密码根据随机token加密
						var randomToken = $("#qr_htc_randomToken").val();	//获取随机token
						var dealPwd = $.trim($("#htc_dealPwd").val());		//获取交易密码
						var word = comm.tradePwdEncrypt(dealPwd,randomToken);		//加密
						var fromHsbAmt = $.trim($("#qr_htc_toCashHsbNum").text());
						fromHsbAmt = fromHsbAmt.replace(',','').replace('.00','');
						//传递参数
						var jsonData = {	
							randomToken     :   randomToken,								//随机token
							tradePwd		:	word,										//交易密码
							fromHsbAmt		: 	fromHsbAmt	//转出的互生币数量
						};
						
						//执行互生币转货币方法	
						dataModule.hsbTransferCurrency(jsonData,function(response) {
							comm.alert({content:'互生币转货币成功！'});
							hsbToCash.showPage();
						});
					}
				);
		 		
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
					$("#qr_htc_randomToken").val(response.data);
				}
				else
				{
					comm.warn_alert(comm.lang("hsbAccount").randomTokenInvalid);
				}
				
			});
		},
		//表单验证
		validateData : function() {
			var minHsbNum =$("#htc_hid_minHsbNum").val();
			// 转出积分数数据验证
			return $("#htc_appform").validate({
								rules : {
									htc_toCashHsbNum : {
										required : true,
										greater : "#htc_hid_hsbAccTotal",
										digits2 : true,
										maxlength : 10,
										less :"#htc_hid_minHsbNum"
									}
									
								},
								messages : {
									htc_toCashHsbNum : {
										greater : comm.lang("accountManage")[30010],
										required : comm.lang("accountManage")[30003],
										digits2 : comm.lang("accountManage")[30011],
										maxlength : comm.lang("accountManage")[30012],
										less : comm.lang("accountManage")[30013]
									}
								},
								errorPlacement : function(error,element) {
									$(element).attr("title",$(error).text()).tooltip(
													{
														tooltipClass : "ui-tooltip-error",
														position : {
															my : "left+160 top+5",
															at : "left top"
														}
													}).tooltip("open");
									$(".ui-tooltip").css("max-width","230px");
								},
								success : function(element) {
									$(element).tooltip();
									$(element).tooltip("destroy");
								}
							});
		},
		
		//交易密码规则验证
		validateDealPwd : function() {
			return $("#htc_qr_appform").validate({
				rules : {
					
					htc_dealPwd : {
						required : true,
						maxlength : 8,
						minlength : 8
					}
				},
				messages : {
					htc_dealPwd : {
						required : comm.lang("accountManage")[30002],
						maxlength : comm.lang("accountManage").dealPwdLength,
						minlength : comm.lang("accountManage").dealPwdLength
					}
				},
				errorPlacement : function(error,element) {
					$(element).attr("title",$(error).text()).tooltip(
									{
										tooltipClass : "ui-tooltip-error",
										position : {
											my : "left+160 top+5",
											at : "left top"
										}
									}).tooltip("open");
					$(".ui-tooltip").css("max-width","230px");
				},
				success : function(element) {
					$(element).tooltip();
					$(element).tooltip("destroy");
				}
			});
		},
		/**
		 * 转货币计算
		 */
		transferCurrency :function(){
			var $cashHsbNum = $("#htc_toCashHsbNum").val();
			if ($cashHsbNum && !isNaN($cashHsbNum)) {
				
				var $ratefee = $("#htc_hid_currencyFee").val();	//货币转换费

				var num = $cashHsbNum * $ratefee;
				
				var fee = num.toFixed(2);
				$("#hb_cost").text(num.toFixed(2));
				
				$("#htc_realToCashNum").text($cashHsbNum-fee);
			}
		}
	};
	return hsbToCash;
}); 
