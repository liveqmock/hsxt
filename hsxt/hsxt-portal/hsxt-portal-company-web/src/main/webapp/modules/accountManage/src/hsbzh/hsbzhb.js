define(['text!accountManageTpl/hsbzh/hsbzhb/hsbzhb.html',
		'text!accountManageTpl/hsbzh/hsbzhb/hsbzhb_qr.html',
        'accountManageDat/accountManage',
        'systemBusinessDat/systemBusiness',
        'accountManageLan'], function(tpl, qrTpl,accountManage,systemBusiness){
	return hsbzhb = {
		restrictData : null, 	//业务限制数据
		showPage : function(){
				
				$('#busibox').html(_.template(tpl));	
				$('#hsbPoint_affirm').html(_.template(qrTpl));
				
				//加载数据
				accountManage.init_hsb_transfer_currency({},function(response){
					var useHsb = Number(response.data.circulationHsb) - Number(response.data.hsbMinimum);
					var availableHsb= useHsb>0 ? useHsb : 0;
					
					$("#htc_hid_hsbAccTotal").val(availableHsb); 	// 流通币余额
					$("#htc_hid_minHsbNum").val(response.data.hsbMin);				//转出最小互生币数整倍数
					$("#htc_hid_currencyFee").val(response.data.currencyFee);		//货币转费用
					$("#htc_hid_exchangeRate").val(response.data.exchangeRate);	//转换比率
					$("#htc_currencyCode").val(response.data.currencyCode);		//当前平台币种
					$("#htc_txt_minHsbNum,#htc_minHsbNum").text(response.data.hsbMin);	 			//转出最小互生币数
					$("#htc_currencyFee,#htc_txt_currencyFee").text((response.data.currencyFee * 100));	//货币转换费
					
					$("#acpoint_cpointTotalNum").text(comm.formatMoneyNumber(availableHsb) || '0'); 		// 流通币余额
					$("#hsb_appNum").val('');
					$("#hsbMinimum").text(response.data.hsbMinimum);
					
					hsbzhb.restrictData = response.data.restrict;	//获取业务限制数据
				});
				//转出数量文本框-按下键盘事件
				$("#hsb_appNum").bind("keyup", function () {
					
					//计算转货币费用
					hsbzhb.transferCurrency();
				});
				//申请提交按钮事件
				$('#hsbQrBtn').click(function(){
					//重新计算转货币费用
					hsbzhb.transferCurrency();
					
					try{
						//判断业务限制
						var restrictValue = hsbzhb.restrictData.restrictValue;
						if("1" == restrictValue){
							var restrictRemark = hsbzhb.restrictData.restrictRemark;
							var msg = "互生币转货币业务暂时不能受理！原因：" + restrictRemark;
							comm.warn_alert(msg);
							return;
						}
					}catch(ex){}
					
					if (!hsbzhb.validateData()) {
						return;
					}
					$("#qr_htc_toCashHsbNum").text(comm.formatMoneyNumber($("#hsb_appNum").val())); // 转出数量
					//隐藏表单
					$("#qr_htc_toCashHsbNum_hidden").val($("#hsb_appNum").val()); // 转出数量
					
					$("#htc_fee_cost").text($("#acpoint_feeNum").text());//货币转换费
					$("#htc_fee_cost_rate").text(($("#htc_hid_currencyFee").val()* 100)); // 货币转换比
					$("#qr_htc_realToCashNum").text($("#hsb_realMoney").text());//实际转入数量
					$("#bizhong").text($("#htc_currencyCode").val()); // 结算币种
					$("#qr_htc_rate").text($("#htc_hid_exchangeRate").val());//转换比率
					var num = $("#hsb_realMoney").text();
					$("#htc_hid_exchangeRate").val();
					$("#qr_hsb_dealPwd").val("");
					
					try{
						$("#qr_htc_toCashNum").text(num);//转入货币金额
					}catch(ex){
						$("#qr_htc_toCashNum").text("0.00");//转入货币金额
					}
					
					//隐藏申请页面
					$('#hsbPoint').addClass('none');
					//显示确认页面
					$('#hsbPoint_affirm').removeClass('none');
					
					//页面返回修改
					$('#hsb_return').click(function(){
						//隐藏页面
						$('#hsbPoint_affirm').addClass('none');
						//显示申请页面
						$('#hsbPoint').removeClass('none');
						$('#qr_hsb_dealPwd').tooltip().tooltip("destroy");
					});
					//提交按钮事件
					$('#hsb_appSubmit').click(function(){
						if (!hsbzhb.validatePwdData()) {
							return;
						}
						
						//获取随机token
						hsbzhb.getRandomToken();
						
						comm.i_confirm("确认要申请互生币转货币吗？", function () {
							//密码根据随机token加密
							var randomToken = $("#qr_pi_randomToken").val();	//获取随机token
							var dealPwd = $.trim($("#qr_hsb_dealPwd").val());	//获取交易密码
							var word = comm.tradePwdEncrypt(dealPwd,randomToken);		//加密
							
							
							//lyh修改
							var qr_htc_toCashHsbNum = $.trim($("#qr_htc_toCashHsbNum_hidden").val());
							if(qr_htc_toCashHsbNum!=""){
								if(qr_htc_toCashHsbNum.indexOf(".")!=-1){
									var sxindex=qr_htc_toCashHsbNum.indexOf(".");
									var laststr=qr_htc_toCashHsbNum.split(".");
									qr_htc_toCashHsbNum=qr_htc_toCashHsbNum.substring(0,sxindex);
								}
							}
							
							var jsonDate = {
									fromHsbAmt :qr_htc_toCashHsbNum ,	//转出的互生币数量
									randomToken :randomToken,			//获取随机token
									tradePwd : word                    //加密密码
							}
							accountManage.hsb_transfer_currency(jsonDate,function(response){
								comm.alert({
									content: '互生币转货币成功！',
									callOk: function(){
										//跳转至明细查询页面
										$("#hsbzh_hsbzhb").trigger('click');
									}
								});
							});					
						});
					});
				});
				
		},
		validateData : function(){
			return comm.valid({
				formID : '#hsb_appForm',
				rules : {
					hsb_appNum : {
						required : true,
						digits2 : true,
						maxlength :10,
						greater : "#htc_hid_hsbAccTotal",
						less :"#htc_hid_minHsbNum"
					}
				},
				messages : {
					hsb_appNum : {
						required : comm.lang("accountManage")[30018],
						digits2 : comm.lang("accountManage")[30011],
						maxlength : comm.lang("accountManage")[30012],
						greater : comm.lang("accountManage")[30010],
						less : comm.lang("accountManage")[30013]
					}
				}
			});
		},
		validatePwdData : function(){
			return comm.valid({
				formID : '#hsb_appForm_qr',
				rules : {
					qr_hsb_dealPwd:{
						required : true,
						rangelength : [8,8]
					}
				},
				messages : {
					qr_hsb_dealPwd:{
						required : comm.lang("accountManage").jfzhb_tras_pass,
						rangelength : comm.lang("accountManage").jfzhb_tras_pass8
					}
				}
			});
		},
		getRandomToken : function (){
			//获取随机token
			comm.getRandomToken(function(response){
				//非空数据验证
				if(response.data)
				{
					$("#qr_pi_randomToken").val(response.data);
				}
				else
				{
					comm.warn_alert(comm.lang("accountManage").randomTokenInvalid);
				}
			});
		},
		/**
		 * 转货币计算
		 */
		transferCurrency :function(){
			var $cashHsbNum = $("#hsb_appNum").val();
			var r = 0.01;
			if (!isNaN($cashHsbNum)) {
				var $ratefee = $("#htc_hid_currencyFee").val();	//货币转换费
				
				var num = $cashHsbNum * $ratefee;
				//num = Math.round(num*100)/100;
				
				$("#hsb_realMoney").text(comm.formatMoneyNumber($cashHsbNum-num));
				$("#acpoint_feeNum").text(num.toFixed(2));
			}
			
		}
	};
});