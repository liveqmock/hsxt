define(["text!pointAccountTpl/pointInvest.html", "text!pointAccountTpl/pointInvest_affirm.html","pointAccountLan"], function (tpl, tplAffirm) {
	var restrictData = null;
	var pointInvest = {
		show : function(dataModule){
			//加载页面模板
			$("#myhs_zhgl_box").html(tpl + tplAffirm);
			
			
			//获取积分转现初始化数据
			dataModule.initIntegralInvestment(null,function (response) {
				restrictData = response.data;
				//设置可用积分数
				$("#pi_pointAccUsable").text(comm.formatMoneyNumber(response.data.pointBlance) || '0');
				
				$("#pi_pointAccUsable_hidden").val(response.data.pointBlance || '0');
				//设置转出积分出倍数
				$("#pi_integrationInvIntMult").text(response.data.integrationInvIntMult || '0');
				
			});

			//提交申请点击事件
			$('#piQrBtn').click(function(){
				var restrictValue = restrictData.restrictValue;
				if("1" == restrictValue){
					var restrictRemark = restrictData.restrictRemark;
					var msg = "积分投资业务暂时不能受理！<br />原因：" + restrictRemark;
					comm.warn_alert(msg);
					return;
				}
				//转出积分数数据验证
				var valid = pointInvest.validateData();
				if (!valid.form()) {
					return;
				}
				
				//转出的积分数
				$("#qr_pi_pointInvestNum").text(comm.formatMoneyNumber($("#pi_pointInvestNum").val()));
				$("#qr_pi_pointInvestNum_hidden").val($("#pi_pointInvestNum").val());
				//div显示隐藏控制
				$('#pointInvest').addClass('none');				
				$('#pointInvest_affirm').removeClass('none');	
			});
			
			//积分转互生币返回修改页面
			$("#pi_return").click(function () {
				$('input').tooltip().tooltip('destroy');
				$('#qr_pi_dealPwd').val("");
				
				//div显示隐藏控制
				$('#pointInvest').removeClass('none');		
				$('#pointInvest_affirm').addClass('none');	
			});
			
			//确认提交点击事件
			$('#pi_appComit').click(function () {
				//交易密码数据验证
				var valid = pointInvest.validateDealPwd();
				if (!valid.form()) {
					return;
				}
				
				//获取随机token
				pointInvest.getRandomToken();
				
				//确认提交询问框
				comm.i_confirm(comm.lang("pointAccount").confirmIntegralInvestment, function () {
					//密码根据随机token加密
					var randomToken = $("#pi_randomToken").val();	//获取随机token
					var dealPwd = $.trim($("#qr_pi_dealPwd").val());	//获取交易密码
					var word = comm.tradePwdEncrypt(dealPwd,randomToken);		//加密
					
			
					//隐藏表单	//lyh修改
					var qr_pi_pointInvestNum_hidden=$("#qr_pi_pointInvestNum_hidden").val();

					if(qr_pi_pointInvestNum_hidden!=""){
						if(qr_pi_pointInvestNum_hidden.indexOf(".")!=-1){
							var sxindex=qr_pi_pointInvestNum_hidden.indexOf(".");
							qr_pi_pointInvestNum_hidden=qr_pi_pointInvestNum_hidden.substring(0,sxindex);
						}
					}
					
					//传递参数
					var jsonData = {	
						randomToken     :   randomToken,								//随机token
						tradePwd		:	word,										//交易密码
						investAmount 	: qr_pi_pointInvestNum_hidden	,	//申请投资积分数
					};
					
					//积分投资提交
					dataModule.integralInvestmentInfo(jsonData, function (response) {
						$("#pi_dialog").dialog({
							title : comm.lang("pointAccount").integralInvestmentSuccessfully,
							dialogClass : "no-dialog-close",
							width : "260",
							modal : true
						});
						//清空数据
					//	$("#qr_pi_pointInvestNum").text("");
						$("#qr_pi_dealPwd").val("");
					});
				});
				
			});
			
			//点击dialog窗的确定按钮
			$('#pi_cg_sure').click(function(){
				$("#pi_dialog").dialog("destroy");
				$('#ul_myhs_right_tab li a[data-id="3"]').trigger('click');
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
					$("#pi_randomToken").val(response.data);
				}
				else
				{
					comm.warn_alert(comm.lang("pointAccount").randomTokenInvalid);
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
						greater : "#pi_pointAccUsable_hidden",
						less : "#pi_minPointInvest",
						min : restrictData.integrationInvIntMult
					}
				},
				messages : {
					pi_pointInvestNum : {
						required : comm.lang("pointAccount")[30001],
						digits : comm.lang("pointAccount")[30154],
						isNumTimes : comm.lang("pointAccount")[30007],
						maxlength : comm.lang("pointAccount").pointToCashMaxlength,
						greater : comm.lang("pointAccount")[30005],
						less : comm.lang("pointAccount")[30007],
						min : comm.lang("pointAccount")[30007]
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
			return $("#qr_pi_appform").validate({
				rules : {
					qr_pi_dealPwd : {
						required : true,
						maxlength : 8,
						minlength : 8
					}
				},
				messages : {
					qr_pi_dealPwd : {
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
	return pointInvest;
});
