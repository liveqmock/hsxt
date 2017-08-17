define(['text!accountManageTpl/jfzh/jfzhsb/jfzhsb.html',
		'text!accountManageTpl/jfzh/jfzhsb/jfzhsb_qr.html',
		'accountManageDat/accountManage',
		'systemBusinessDat/systemBusiness'
		],function(tpl, qrTpl,accountManage,systemBusiness){
	return jfzhsb ={		
		restrictData : null, 	//业务限制数据
		showPage : function(){
			//查询企业状态信息  当状态为注销中时 不能操作该项
			systemBusiness.searchEntStatusInfo({},function(res){
				
				var reponse = {
						status : res.data.info.baseStatus
				};
				
				$('#busibox').html(_.template(tpl,reponse)) ;
				
				$('#pointToHSB_affirm').html(_.template(qrTpl));
				
				
				//加载数据
				accountManage.init_integral_transfer_Hsb({},function(res){
					jfzhsb.restrictData = res.data.restrict;
					$("#pth_pointAccUsable").text(comm.formatMoneyNumber(res.data.pointBlance));
					$("#pointAccUsable").val(res.data.pointBlance||0);
					$("#minconvert").text(res.data.integrationMin);
					$("#pth_toHSBPointNum").val('');
					
					
				});
				//申请提交按钮事件
				$('#pthQrBtn').click(function(){
					try{
						var restrictValue = jfzhsb.restrictData.restrictValue;
						if("1" == restrictValue){
							var restrictRemark = jfzhsb.restrictData.restrictRemark;
							var msg = "积分转互生币业务暂时不能受理！原因：" + restrictRemark;
							comm.warn_alert(msg);
							return;
						}
					}catch(ex){}
					
					//表单数据验证
					if (!jfzhsb.validateData()) {
						return;
					}
					
					var pointNum = $("#pth_toHSBPointNum").val();
					$('#qr_pth_toHSBPointNum').text(comm.formatMoneyNumber(pointNum));
					//隐藏表单 
					$('#qr_pth_toHSBPointNum_hidden').val(pointNum);
					$('#qr_pth_realMoneyNum').text(comm.formatMoneyNumber(pointNum));
					$("#qr_pth_dealPwd").val("");
					//隐藏申请页面
					$('#pointToHSB').addClass('none');
					//显示确认页面
					$('#pointToHSB_affirm').removeClass('none');
					
					//页面返回修改
					$('#pth_return').click(function(){
						//隐藏页面
						$('#pointToHSB_affirm').addClass('none');
						//显示申请页面
						$('#pointToHSB').removeClass('none');
					});
					//提交按钮事件
					$('#pth_appComit').click(function(){
						if (!jfzhsb.validatePwdData()) {
							return false;
						}
						//获取随机token
						jfzhsb.getRandomToken();
						comm.i_confirm("确认要申请积分转互生币吗？", function () {
							
							//密码根据随机token加密
							var randomToken = $("#qr_pth_randomToken").val();	//获取随机token
							var dealPwd = $.trim($("#qr_pth_dealPwd").val());	//获取交易密码
							var word = comm.tradePwdEncrypt(dealPwd,randomToken);		//加密
							//lyh修改
							var qr_pth_toHSBPointNum = $.trim($("#qr_pth_toHSBPointNum_hidden").val());
							//传递参数
							var jsonData = {
									tradePwd 	: 	word,											//交易密码
									amount		: 	qr_pth_toHSBPointNum,		//转出积分数
									randomToken :   randomToken,									//随机token（防止CSRF攻击）
									custName :unescape($.cookie('custEntName'))
							};
							
							//积分转互生币提交
							accountManage.commitJfzhsb(jsonData, function (response) {
								comm.alert({
									content: "积分转互生币成功！",//积分转互生币成功！
									callOk: function(){
										//清空数据
										$("#qr_pth_toHsbPointNum").text("");
										$("#qr_pth_dealPwd").val("");
										//跳转至原界面
										$('#jfzh_jfzhsb').click();
									}
								});
							});
						});
					});
				});
				
			});
			
		},
		getRandomToken : function (){
			//获取随机token
			comm.getRandomToken(function(response){
				//非空数据验证
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
		validateData : function(){
			return comm.valid({
				formID : '#pth_appform',
				rules : {
					pth_toHSBPointNum : {
						required : true,
						digits : true,
						maxlength : 10,
						greater : "#pointAccUsable",
						less : "#minconvert"
					}
				},
				messages : {
					pth_toHSBPointNum : {
						required 	: comm.lang("accountManage")[30001],
						digits 		: comm.lang("accountManage")[30154],
						maxlength : comm.lang("accountManage").pointToCashMaxlength,
						greater 	: comm.lang("accountManage")[30005],
						less 		: comm.lang("accountManage")[30006]
					}
				}
			});
		},
		validatePwdData : function(){
			return comm.valid({
				formID : '#qr_pth_appform',
				rules : {
					qr_pth_dealPwd : {
						required : true,
						rangelength : [8,8]
					}
				},
				messages : {
					qr_pth_dealPwd : {
						required : comm.lang("accountManage").jfzhb_tras_pass,
						rangelength : comm.lang("accountManage").jfzhb_tras_pass8
					}
				}
			});
		}
	};
}); 

 