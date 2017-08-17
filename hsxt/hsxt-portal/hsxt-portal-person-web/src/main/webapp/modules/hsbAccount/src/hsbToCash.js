define(
		[ "text!hsbAccountTpl/hsbToCash.html",
				"text!hsbAccountTpl/hsbToCash_affirm.html" ],
		function(tpl, tplAffirm) {
			var i = null;
			var toFee = null;
			var shumu = null;
			var sjzc = null;
			var restrictData = null;
			var hsbToCash = {

				show : function(dataModule) {
					// 加载页面模板
					$("#myhs_zhgl_box").html(tpl + tplAffirm);
					
					
					
					// 初始化页面
					dataModule.initHsbTransferCurrency(null, function(response) {
						restrictData = response.data;
						 $("#htc_hsbAccTotal").text(comm.formatMoneyNumber(response.data.circulationHsb)); 		// 流通币余额
						 $("#htc_hid_hsbAccTotal").val(response.data.circulationHsb || 0); 	// 流通币余额
						 $("#htc_hid_minHsbNum").val(response.data.hsbMin || '0');				//转出最小互生币数整倍数
						 $("#htc_hid_currencyFee").val(response.data.currencyFee);		//货币转费用
						 $("#htc_hid_exchangeRate").val(response.data.exchangeRate);	//转换比率
						 $("#htc_currencyCode").val(response.data.currencyCode);		//当前平台币种
						 $("#htc_currencyCodeName").val(response.data.currencyNameCn);		//当前平台币种
						 $("#htc_txt_minHsbNum,#htc_minHsbNum").text(response.data.hsbMin || '0');	 			//转出最小互生币数
						 $("#htc_currencyFee,#htc_txt_currencyFee").text((response.data.currencyFee * 100));	//货币转换费
					});

					// 转现互生币数文本框-按下键盘事件
					$("#htc_toCashHsbNum").keyup(function() {
						hsbToCash.calculateRatefee();
					});

					// 提交申请点击事件
					$('#htcQrBtn').click(function() {
						var restrictValue = restrictData.restrictValue;
						if("1" == restrictValue){
							var restrictRemark = restrictData.restrictRemark;
							var msg = "互生币转货币业务暂时不能受理！<br />原因：" + restrictRemark;
							comm.warn_alert(msg);
							return;
						}
						// 转出积分数数据验证
						var valid = hsbToCash.validateData();
						if (!valid.form()) {
							return;
						}
						
						hsbToCash.calculateRatefee();
						
						// 加载数据
						$("#qr_htc_toCashHsbNum").text(comm.formatMoneyNumber($("#htc_toCashHsbNum").val())); // 转出数量
						$('#cashAmount').val($("#htc_toCashHsbNum").val());
						$("#htc_fee_cost").text($("#hb_cost").text());//货币转换费
						$("#htc_fee_cost_rate").text($("#htc_txt_currencyFee").text()); // 货币转换比费用
						$("#qr_htc_realToCashNum").text($("#htc_realToCashNum").text());//实际转入数量
						$("#bizhong").val($("#htc_currencyCode").val()); // 结算币种
						$("#bizhongName").text($("#htc_currencyCodeName").val()); // 结算币种名称
						$("#qr_htc_rate").text(($("#htc_hid_exchangeRate").val()));//转换比率
						$("#qr_htc_toCashNum").text($("#htc_realToCashNum").text());//转入货币金额
						
						// div 显示隐藏控制
						$('#hsbToCash').addClass('none');
						$('#hsbToCash_affirm').removeClass('none');
					});

					// 互生币转货币返回修改页面
					$("#htc_return").click(function() {
						$('input').tooltip().tooltip('destroy');
						$('#htc_dealPwd').val("");
						
						// div 显示隐藏控制
						$('#hsbToCash').removeClass('none');
						$('#hsbToCash_affirm').addClass('none');
					});
					
					// 确认提交点击事件
					$('#htc_appComit').click(function() {
						// 交易密码数据验证
						var valid = hsbToCash.validateDealPwd();
						if (!valid.form()) {
							return;
						}
						//获取随机token
						hsbToCash.getRandomToken();
						
						// 确认要申请互生币转货币吗？
						comm.i_confirm(comm.lang("hsbAccount").affireHsbToCash,function() {
							
								//密码根据随机token加密
								var randomToken = $("#qr_htc_randomToken").val();	//获取随机token
								var dealPwd = $.trim($("#htc_dealPwd").val());		//获取交易密码
								var word = comm.tradePwdEncrypt(dealPwd,randomToken);		//加密								
	
								//传递参数
								var jsonData = {	
									randomToken     :   randomToken,								//随机token
									tradePwd		:	word,										//交易密码
									fromHsbAmt		: 	$('#cashAmount').val(),						//转出的互生币数量
								};
								
								//执行互生币转货币方法	
								dataModule.hsbTransferCurrency(jsonData,function(response) {
									$("#htc_dialog").dialog({
										dialogClass : "no-dialog-close",
										title : comm.lang("hsbToCash").hsbToCashSuccess,
										width : "260",
										modal : true
									});
									$("#htc_dealPwd").val('');
								});
							}
						);
												
					});
					
					// 点击dialog窗的确定按钮
					$('#htc_cg_sure').click(function() {
								$("#htc_dialog").dialog("destroy");
								$('#ul_myhs_right_tab li a[data-id="2"]').trigger('click');
					});
				},
				
				/**
				 * 计算货币转换费
				 */
				calculateRatefee : function(){
					var v = $("#htc_toCashHsbNum").val();
					if (v && !isNaN(v)) {
						
						var ratefee = $("#htc_hid_currencyFee").val();	//货币转换费

						var num = v * ratefee;
						//num = Math.round(num*100)/100;
						
						$("#hb_cost").text(comm.formatMoneyNumber(num));
						$("#htc_realToCashNum").text(comm.formatMoneyNumber(v-num));
						
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
												digits2 : true,
												maxlength : 10,
												greater : "#htc_hid_hsbAccTotal",
												less :"#htc_hid_minHsbNum"
											}
										},
										messages : {
											htc_toCashHsbNum : {
												greater : comm.lang("hsbAccount")[30010],
												required : comm.lang("hsbAccount")[30003],
												digits2 : comm.lang("hsbAccount")[30013],
												maxlength : comm.lang("hsbAccount").hsbBuy.maxlength,
												less : comm.lang("hsbAccount")[30013]
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
					return $("#htc_appform_qr").validate(
									{
										rules : {
											htc_dealPwd : {
												required : true,
												minlength : 8
											}
										},
										messages : {
											htc_dealPwd : {
												required : comm.lang("hsbAccount")[30002],
												minlength : comm.lang("hsbAccount")[30012]
											}
										},
										errorPlacement : function(error,element) {
											$(element).attr("title",$(error).text()).tooltip(
															{
																tooltipClass : "ui-tooltip-error",
																position : {
																	my : "left+200 top+5",
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
				}
			};
			return hsbToCash;
		});
