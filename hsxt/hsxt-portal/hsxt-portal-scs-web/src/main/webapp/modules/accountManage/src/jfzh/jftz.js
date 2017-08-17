define(['text!accountManageTpl/jfzh/jftz.html',
        'accountManageDat/jfzh/jfzh',
		'accountManageDat/jfzh/jfzh','accountManageLan'],function( jftzTpl ,dataModule){
	return pointInvest = {	 	
		restrictData : null, 	//业务限制数据
		showPage : function(){
			$('#busibox').html(_.template(jftzTpl)) ;
			
			//获取积分转现初始化数据
			dataModule.initIntegralInvestment(null,function (response) {
				//设置可用积分数
				$("#pi_pointAccUsable").text(comm.formatMoneyNumber(response.data.pointBlance) || '0');
				//设置转出积分出倍数
				$("#pi_integrationInvIntMult").text(response.data.integrationInvIntMult || '0');
				$("#pointAccUsable").val(response.data.pointBlance|| '0');
				$("#pi_minPointInvest").val(response.data.integrationInvIntMult);
				
				pointInvest.restrictData = response.data.restrict;	//获取业务限制数据
				
				
			});
			
			//提交
 			$('#jftz_tj').click(function(){
 				try{
 					var restrictValue = pointInvest.restrictData.restrictValue;
 					if("1" == restrictValue){
 						var restrictRemark = pointInvest.restrictData.restrictRemark;
 						var msg = "积分投资业务暂时不能受理！原因：" + restrictRemark;
 						comm.warn_alert(msg);
 						return;
 					}
 				}catch(ex){}
				
 				//转出积分数数据验证
				var valid = pointInvest.validateData();
				if (!valid.form()) {
					return;
				}
				
				//转出的积分数
				$("#qr_pi_pointInvestNum").text(comm.formatMoneyNumber($("#pi_pointInvestNum").val()|| '0'));
				
				//div显示隐藏控制
				$('#div_jftz_tj').addClass('none');
				$('#div_jftz_qr').removeClass('none');
 					
 			});
 			
 			//修改
 			$('#jftz_xg').click(function(){ 				 
 					$('#div_jftz_tj').removeClass('none');
 					$('#div_jftz_qr').addClass('none');	
 			});
 			
 			//确认
 			$('#jftz_qr').click(function(){ 				 
 					
 				//交易密码数据验证
				var valid = pointInvest.validateDealPwd();
				if (!valid.form()) {
					return;
				}
				
				//获取随机token
				pointInvest.getRandomToken();
				
				//确认提交询问框
				comm.i_confirm(comm.lang("accountManage").confirmIntegralInvestment, function () {
					
					//密码根据随机token加密
					var randomToken = $("#pi_randomToken").val();	//获取随机token
					var dealPwd = $.trim($("#qr_pi_dealPwd").val());	//获取交易密码
					var word = comm.tradePwdEncrypt(dealPwd,randomToken);		//加密
					var investAmount=$.trim($("#qr_pi_pointInvestNum").text());
					investAmount = investAmount.replace(',','').replace('.00','');
					//传递参数
					var jsonData = {	
						randomToken     :   randomToken,								//随机token
						tradePwd		:	word,										//交易密码
						investAmount 	: 	investAmount	//申请投资积分数
					};
					
					//积分投资提交
					dataModule.integralInvestmentInfo(jsonData, function (response) {
						//确认
						comm.alert({content:comm.lang("accountManage").integralInvestmentSuccessfully});
						pointInvest.showPage();
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
					$("#pi_randomToken").val(response.data);
				}
				else
				{
					comm.warn_alert(comm.lang("accountManage").randomTokenInvalid);
				}
				
			});
		},
		/**
		 * 转出积分验证规则
		 */
		validateData : function () {
			
			//转出积分数数据验证
			return $("#pi_appform").validate({
				rules : {
					pi_pointInvestNum : {
						required : true,
						digits : true,
						maxlength : 10,
						isNumTimes : true,
						greater : "#pointAccUsable",
						less : "#pi_minPointInvest"
					}
				},
				messages : {
					pi_pointInvestNum : {
						required : comm.lang("accountManage")[30001],
						digits : comm.lang("accountManage")[30154],
						isNumTimes : comm.lang("accountManage")[30008],
						maxlength : comm.lang("accountManage").pointToCashMaxlength,
						greater : comm.lang("accountManage")[30005],
						less : comm.lang("accountManage")[30008]
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
		 * 交易密码验证规则
		 */
		validateDealPwd : function() {
			return $("#pi_qr_appform").validate({
				rules : {
					qr_pi_dealPwd : {
						required : true,
						maxlength : 8,
						minlength : 8
					}
				},
				messages : {
					qr_pi_dealPwd : {
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
	return pointInvest;
});
