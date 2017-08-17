define(['text!accountManageTpl/jfzh/jfzhsb.html',
        'accountManageDat/jfzh/jfzh','accountManageLan'],function( jfzhsbTpl ,dataModule){
	return pointToHsb = {
		restrictData : null, 	//业务限制数据
		showPage : function(){
			
			$('#busibox').html(_.template(jfzhsbTpl)) ;
		 	 
			//获取积分转现初始化数据
			dataModule.initIntegralTransferHsb(null,function (response) {
				
					//非空验证
					if (response) 
					{
						//设置可用积分数
						$("#ptointAccUsable").val(response.data.pointBlance || '0');
						$("#pth_pointAccUsable").text(comm.formatMoneyNumber(response.data.pointBlance) || '0');	
						//设置转出最小积分数量
						$("#integrationMin").text(response.data.integrationMin || '0');
						$("#pth_minPointNum").val(response.data.integrationMin || '0');
						//设置转货币转换比率 
						$("#pth_exchangeRate").val(response.data.exchangeRate|| '0');
						$("#pth_currencyCode").val(response.data.exchangeRate|| '0');
						//设置币种
						//$("#pth_currencyCode").val(response.data.currencyCode|| '0');
						
						pointToHsb.restrictData = response.data.restrict;	//获取业务限制数据
						
					}
				}
			);
			
 			//提交
 			$('#jfzhsb_tj').click(function(){ 
 				try{
 					var restrictValue = pointToHsb.restrictData.restrictValue;
 					if("1" == restrictValue){
 						var restrictRemark = pointToHsb.restrictData.restrictRemark;
 						var msg = "积分转互生币业务暂时不能受理！原因：" + restrictRemark;
 						comm.warn_alert(msg);
 						return;
 					}
 				}catch(ex){}
				
 				//转出积分数数据验证
				var valid = pointToHsb.validateData();
				if (!valid.form()) {
					return;
				}
				
				var toHsbPointNum =$("#pth_toHsbPointNum").val();
				$("#qr_pth_currency").text($("#pth_currencyCode").val());	//币种
				$("#qr_pth_toHsbPointNum").text(comm.formatMoneyNumber(toHsbPointNum|| '0'));	//转出积分数数量
				
				//计算转换的互生币的手续费
				var realHsbNum=Math.round(toHsbPointNum* $("#pth_exchangeRate").val());
				realHsbNum=comm.formatMoneyNumber(realHsbNum|| '0');
				$("#qr_pth_realHsbNum").text(realHsbNum);
		
				//隐藏申请页面
				$('#div_jfzhsb_tj').addClass('none');
				//显示确认页面
 				$('#div_jfzhsb_qr').removeClass('none');
 					
 					
 			});
 			
 			//修改
 			$('#jfzhsb_xg').click(function(){
 				$('#qr_pth_dealPwd').val("");
				$('#div_jfzhsb_tj').removeClass('none');
				$('#div_jfzhsb_qr').addClass('none');
 					
 					
 					
 			});
 			
 			//确认
 			$('#jfzhsb_qr').click(function(){ 				 
 				//交易密码数据验证
				var valid = pointToHsb.validateDealPwd();
				if (!valid.form()) {
					return;
				}
				
				//获取随机token
				pointToHsb.getRandomToken();
				
				comm.i_confirm(comm.lang("accountManage").confirmIntegralHSCurrency, function () {
					//密码根据随机token加密
					var randomToken = $("#qr_pth_randomToken").val();	//获取随机token
					var dealPwd = $.trim($("#qr_pth_dealPwd").val());	//获取交易密码
					var word = comm.tradePwdEncrypt(dealPwd,randomToken);		//加密
					var amount = $("#pth_toHsbPointNum").val();					//转出积分数
					//$.trim($("#qr_pth_toHsbPointNum").text());
					//amount = amount.replace(',','').replace('.00','');
					
					//传递参数
					var jsonData = {
						tradePwd 	: 	word,											//交易密码
						amount		: 	amount,		//转出积分数
						randomToken :   randomToken,									//随机token（防止CSRF攻击）
					};
					
					//积分转互生币提交
					dataModule.integralTransferHsb(jsonData, function (response) {
						//确认
						comm.alert({content:comm.lang("accountManage").integralHSBSuccessfully});
						pointToHsb.showPage();
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
					$("#qr_pth_randomToken").val(response.data);
				}
				else
				{
					comm.warn_alert(comm.lang("accountManage").randomTokenInvalid);
				}
				
			});
		},

		/**
		 * 积分数量规则验证
		 */
		validateData : function () {
			
			//转出积分数数据验证
			return $("#pth_appform").validate({
				rules : {
					pth_toHsbPointNum : {
						required : true,
						digits : true,
						maxlength : 10,
						greater : "#ptointAccUsable",
						less : "#pth_minPointNum"
							
					}
				},
				messages : {
					pth_toHsbPointNum : {
						required : comm.lang("accountManage")[30001],
						digits : comm.lang("accountManage")[30154],
						maxlength : comm.lang("accountManage").pointToCashMaxlength,
						greater : comm.lang("accountManage")[30005],
						less : comm.lang("accountManage")[30006]
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
		 * 交易密码验证
		 */
		validateDealPwd : function() {
			return $("#pth_confirm_appform").validate({
				rules : {
					qr_pth_dealPwd : {
						required : true,
						maxlength : 8,
						minlength : 8
					}
				},
				messages : {
					qr_pth_dealPwd : {
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
	return pointToHsb;
}); 

 