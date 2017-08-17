define(['text!accountManageTpl/jfzh/jftz/jftz.html',
		'text!accountManageTpl/jfzh/jftz/jftz_qr.html',
		'accountManageDat/accountManage',
		'systemBusinessDat/systemBusiness'
		],function(tpl, qrTpl,accountManage,systemBusiness){
	return jftzInfo = {
		restrictData : null, 	//业务限制数据
		showPage : function(){
			
				$('#busibox').html(_.template(tpl)) ;
				
				$('#pointToInvest_affirm').html(_.template(qrTpl));
				
				//加载数据
				accountManage.init_integral_investment({},function(res){
					jftzInfo.restrictData = res.data.restrict;	//获取业务限制数据
					$("#minconvert").val(res.data.integrationInvIntMult);
					$("#pointAccUsable").val(res.data.pointBlance||0);
					$("#pi_pointAccUsable").text(comm.formatMoneyNumber(res.data.pointBlance));
					$("#pi_toInvestPointNum").val('');
					
				});
				//申请提交按钮事件
				$('#piQrBtn').click(function(){
					try{
						var restrictValue = jftzInfo.restrictData.restrictValue;
						if("1" == restrictValue){
							var restrictRemark = jftzInfo.restrictData.restrictRemark;
							var msg = "积分投资业务暂时不能受理！原因：" + restrictRemark;
							comm.warn_alert(msg);
							return;
						}
					}catch(ex){}
					
					//表单数据验证
					if (!jftzInfo.validateData()) {
						return;
					}
					var pointNum = $("#pi_toInvestPointNum").val();
					//
					$('#qr_pi_toInvestPointNum').text(comm.formatMoneyNumber(pointNum));
					//隐藏表单
					$('#qr_pi_toInvestPointNum_hidden').val(pointNum);
					//隐藏申请页面
					$('#pointToInvest').addClass('none');
					//显示确认页面
					$('#pointToInvest_affirm').removeClass('none');
					$("#qr_pi_dealPwd").val('');
					//页面返回修改
					$('#pi_return').click(function(){
						//隐藏页面
						$('#pointToInvest_affirm').addClass('none');
						//显示申请页面
						$('#pointToInvest').removeClass('none');
					});
					//提交按钮事件
					$('#pi_appComit').click(function(){
						if (!jftzInfo.validatePwdData()) {
							return;
						}
						//获取随机token
						jftzInfo.getRandomToken();
						
						
						comm.confirm({
							content: "确认要申请积分投资吗？",
							imgFlag: 1,
							callOk: function () {
								//密码根据随机token加密
								var randomToken = $("#qr_pi_randomToken").val();	//获取随机token
								var dealPwd = $.trim($("#qr_pi_dealPwd").val());	//获取交易密码
								var word = comm.tradePwdEncrypt(dealPwd,randomToken);		//加密
								var investAmount=$("#qr_pi_toInvestPointNum_hidden").val();//投资积分数
								if(investAmount!=""){
									if(investAmount.indexOf(".")!=-1){
										var sxindex=investAmount.indexOf(".");
										var laststr=investAmount.split(".");
										investAmount=investAmount.substring(0,sxindex);
									}
								}
								var jsonDate = {
										investAmount : investAmount,	
										randomToken :randomToken,			//获取随机token
										tradePwd : word,                    //加密密码
										custName:unescape($.cookie('custEntName'))
								}
								accountManage.commitJftz(jsonDate,function(response){
									comm.alert({
										content: "积分投资成功！",
										callOk: function(){
											//清空数据
											$("#qr_pi_toInvestPointNum").text("");
											$("#qr_pi_dealPwd").val("");
											//跳转至原界面
											$('#jfzh_jftz').click();
										}
									});
								})
								
							}
						});
					});
				});
		},
		validateData : function(){
			return comm.valid({
				formID : '#pi_appform',
				rules : {
					pi_toInvestPointNum : {
						required : true,
						number : true,
						greater : "#pointAccUsable",
						isNumTimes :true,
						less : "#minconvert"
					}
				},
				messages : {
					pi_toInvestPointNum : {
						required : comm.lang("accountManage")[30017],
						number : comm.lang("accountManage")[30154],
						greater : comm.lang("accountManage").pointBlanceGreaterTips,
						isNumTimes : comm.lang("accountManage")[30008],
						less : comm.lang("accountManage")[30008]
					}
				}
			});
		},
		validatePwdData : function(){
			return comm.valid({
				formID : '#qr_pi_appform',
				rules : {
					qr_pi_dealPwd:{
						required : true,
						rangelength : [8,8]
					}
				},
				messages : {
					qr_pi_dealPwd:{
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
	}
}); 

 