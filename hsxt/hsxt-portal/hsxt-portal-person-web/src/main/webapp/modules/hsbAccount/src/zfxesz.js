define(['text!hsbAccountTpl/zfxesz/zfxesz.html',
		'text!hsbAccountTpl/zfxesz/zfxesz_edit.html',
		'hsbAccountDat/hsbAccount'
		], function(zfxeszTpl, zfxesz_editTpl, hsbAccount){
	var zfxeszFun = {
		tempPayLimitSet:null,	//临时支付限额配置
		show : function(){
			$('#myhs_zhgl_box').html(zfxeszTpl + zfxesz_editTpl);
			
			var self = this,isOver = false;
			
			//获取支付限额配置
			hsbAccount.payLimitSetting({},function (rsp) {
				zfxeszFun.tempPayLimitSet=rsp.data;
				if(rsp.data.dbPaymentMax){//单笔支付限额（系统阀值）
					zfxeszFun.dbPaymentMax = rsp.data.dbPaymentMax;
				}
				if(rsp.data.mrPaymentMax){//每日支付限额（系统阀值）
					zfxeszFun.mrPaymentMax = rsp.data.mrPaymentMax;
				}
				var singleQuota = rsp.data.aounmtMinByTime;		//单笔支付限额
				var dayQuota = rsp.data.amountMaxByDay;		//每日支付限额
				
				$('#dbhsbzfxe').val(singleQuota.singleQuotaSwitch=="Y" ? comm.formatMoneyNumber(singleQuota.singleQuota) : "-");
				$('#dbhsbzfxe_btn').text(singleQuota.singleQuotaSwitch=="Y" ? "启用":"停用");
				
				$('#mrhsbzfxe').val(dayQuota.dayQuotaSwitch=="Y" ? comm.formatMoneyNumber(dayQuota.dayQuota) : "-");
				$('#mrhsbzfxe_btn').text(dayQuota.dayQuotaSwitch=="Y" ? "启用":"停用");
			});
				
			
			
			function prompt_close(obj){
				if(!isOver){
					obj.addClass('none');
				}
			}  
		  
			function _prompt_close(obj){  
				return function(){  
					prompt_close(obj);  
				}  
			}  
			
			/*单笔互生币支付限额说明*/
			$('.dbxe_btn').bind('mouseover', function(){
				$('.dbxe_prompt').removeClass('none');
			}).bind('mouseout', function(){
				$('.dbxe_prompt').bind('mouseover', function(){
					isOver = true;
					$(this).removeClass('none');	
				}).bind('mouseout', function(){
					isOver = false;
					$(this).addClass('none');		
				});
				
				setTimeout(_prompt_close($('.dbxe_prompt')), 500);
					
			});
			
			//每日互生币支付限额说明
			$('.mrxe_btn').bind('mouseover', function(){
				$('.mrxe_prompt').removeClass('none');
			}).bind('mouseout', function(){
				
				$('.mrxe_prompt').bind('mouseover', function(){
					isOver = true;
					$(this).removeClass('none');	
				}).bind('mouseout', function(){
					isOver = false;
					$(this).addClass('none');	
				});
				
				setTimeout(_prompt_close($('.mrxe_prompt')), 500);
			});
			
			//点击进入修改支付限额页
			$('#zfxesz_xg_btn').click(function(){
				$('#dbhsbzfxe').val(zfxeszFun.tempPayLimitSet.aounmtMinByTime.singleQuotaSwitch=="Y" ? zfxeszFun.tempPayLimitSet.aounmtMinByTime.singleQuota : "-");
				$('#mrhsbzfxe').val(zfxeszFun.tempPayLimitSet.amountMaxByDay.dayQuotaSwitch=="Y" ? zfxeszFun.tempPayLimitSet.amountMaxByDay.dayQuota : "-");
				
				//支付限额类型
				var codeType="payQuotaType";
				
				//绑定验证码
				comm.getSecuritCode("#txtValidateCode","#imgValidateCode",["#imgValidateCode","#aValidateCodeRefresh"],codeType);
				
				/*初始化表单*/
				$('#txtSingleQuota').val($('#dbhsbzfxe').val());
				if($('#dbhsbzfxe_btn').text() == '启用'){
					$('#dbhsbzfxe_btn_edit').find('i').addClass('zfxesz-btn-on');
					$('#dbhsbzfxe_li').removeClass('bg_EFEFEF');	
					$('#txtSingleQuota').removeClass('bg_EFEFEF').removeAttr("readonly");
				}
				else{
					$('#dbhsbzfxe_btn_edit').find('i').addClass('zfxesz-btn-off');
					$('#dbhsbzfxe_li').addClass('bg_EFEFEF');
					$('#txtSingleQuota').addClass('bg_EFEFEF').attr('readonly' ,'readonly');		
				}
				
				$('#txtDayQuota').val($('#mrhsbzfxe').val());
				if($('#mrhsbzfxe_btn').text() == '启用'){
					$('#mrhsbzfxe_btn_edit').find('i').addClass('zfxesz-btn-on');
					$('#mrhsbzfxe_li').removeClass('bg_EFEFEF');
					$('#txtDayQuota').removeClass('bg_EFEFEF').removeAttr("readonly");	
				}
				else{
					$('#mrhsbzfxe_btn_edit').find('i').addClass('zfxesz-btn-off');	
					$('#mrhsbzfxe_li').addClass('bg_EFEFEF');
					$('#txtDayQuota').addClass('bg_EFEFEF').attr('readonly' ,'readonly');	
				}

				self.showTpl($('#zfxesz_editTpl'));
				
				// 限额提交
				$('#zfxesz_tj_btn').click(function(){
					var valid = self.validatePwdForm();
					if (!valid.form()) {
						return;
					}
					
					var $singleQuotaReSwitch = self.getQuotaSwitch('#txtSingleQuota');			//单笔限额开关
					var $singleQuotaRe = comm.navNull($('#txtSingleQuota').val()); 				//限额值
					var $dayQuotaReSwitch = self.getQuotaSwitch($('#txtDayQuota'));				//每日限额开关
					var $dayQuotaRe = comm.navNull($('#txtDayQuota').val());					//限额值
					var $validateCode = $("#txtValidateCode").val();							//验证码
					var $tradePwd = $("#txtDealPwd").val();										//交易密码
					
					/** 修改支付限额  start */
					//获取token
					comm.getToken({},function(resp){
							var token = resp.data;
							
							var jsonParam = {
									singleQuotaReSwitch : ($singleQuotaReSwitch ? "Y":"N"),
									singleQuotaRe : ($singleQuotaReSwitch ? $singleQuotaRe : null),
									dayQuotaReSwitch :($dayQuotaReSwitch ? "Y":"N") ,
									dayQuota :  ($dayQuotaReSwitch ? $dayQuotaRe : null),
									tradePwd:comm.tradePwdEncrypt($tradePwd,token),
									randomToken:token,
									smsCode : $validateCode,
									codesType : codeType
							};
							
							//支付限额修改
							hsbAccount.payLimitUpdate(jsonParam,function (response) {
								comm.alert({
									imgFlag : true,
									imgClass : 'tips_yes',
									content : '互生币支付限额设置成功！',
									callOk : function(){
										$('#ul_myhs_right_tab li a[data-id="5"]').click();	
									}	
								});
								$("#txtDealPwd").val('');	
								
							});
					});
					/** 修改支付限额  end */
					
				});	
				
				//取消设置
				$('#zfxesz_qx_btn').click(function(){
					//self.showTpl($('#zfxeszTpl'));	
					$("#ul_myhs_right_tab a[data-id='5']").click();
				});
			});
			
			//开启或关闭单笔限额
			$('#dbhsbzfxe_btn_edit').find('i').click(function(){
				var className_tmp = $(this).attr('class'),
					className = className_tmp.replace('zfxesz-btn ', '');
				if(className == 'zfxesz-btn-on'){
					$(this).removeClass('zfxesz-btn-on').addClass('zfxesz-btn-off');
					$('#dbhsbzfxe_li').addClass('bg_EFEFEF');
					$('#txtSingleQuota').addClass('bg_EFEFEF').attr('readonly' ,'readonly').val('-');	
				}
				else{
					$(this).removeClass('zfxesz-btn-off').addClass('zfxesz-btn-on');
					$('#dbhsbzfxe_li').removeClass('bg_EFEFEF');	
					$('#txtSingleQuota').removeClass('bg_EFEFEF').removeAttr("readonly");	
					if($('#txtSingleQuota').val()=="-"){$('#txtSingleQuota').val("");}
				}	
			});
			
			//开启或关闭每日限额
			$('#mrhsbzfxe_btn_edit').find('i').click(function(){
				var className_tmp = $(this).attr('class'),
					className = className_tmp.replace('zfxesz-btn ', '');
				if(className == 'zfxesz-btn-on'){
					$(this).removeClass('zfxesz-btn-on').addClass('zfxesz-btn-off');	
					$('#mrhsbzfxe_li').addClass('bg_EFEFEF');
					$('#txtDayQuota').addClass('bg_EFEFEF').attr('readonly' ,'readonly').val('-');	
				}
				else{
					$(this).removeClass('zfxesz-btn-off').addClass('zfxesz-btn-on');
					$('#mrhsbzfxe_li').removeClass('bg_EFEFEF');
					$('#txtDayQuota').removeClass('bg_EFEFEF').removeAttr("readonly");	
					if($('#txtDayQuota').val()=="-"){$('#txtDayQuota').val("");}
				}	
			});
		},
		showTpl : function(tplObj){
			$('#zfxeszTpl, #zfxesz_editTpl').addClass('none');
			tplObj.removeClass('none');	
		},
		/** 获取限额开关 false:关，ture:开*/
		getQuotaSwitch : function(id){
			//获取元素可读性
			var $quotaReSwitch = comm.navNull($(id).attr("readonly")); 
			//只读时，为关闭状态，否则为开启状态
			if($quotaReSwitch=="readonly"){
				return false;
			}
			return true;
		} ,
		/** 表单数据验证 */
		validatePwdForm : function(){	
		   var self=this;
		   var valid = $("#zfxesz_editForm").validate({
			rules : {
				txtDealPwd : { 
					required : true 
				},
				txtValidateCode : { 
					required : true
				}
			},
			messages : {
				txtDealPwd : {
					required : comm.lang("hsbAccount").zfxesz_pwd.required
				},
				txtValidateCode : {
					required : comm.lang("hsbAccount").zfxesz_code.required
				}
			},
			errorPlacement : function (error, element) {
				$(element).attr("title", $(error).text()).tooltip({
					tooltipClass: "ui-tooltip-error",
					position : {
						my : "left-5 top+30",
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
		   
		var $singleQuotaReSwitch = self.getQuotaSwitch('#txtSingleQuota');			//单笔限额开关
		var $singleQuotaRe = comm.navNull($('#txtSingleQuota').val()); 				//限额值
		var $dayQuotaReSwitch = self.getQuotaSwitch($('#txtDayQuota'));				//每日限额开关
		var $dayQuotaRe = comm.navNull($('#txtDayQuota').val());					//限额值
		var minAmount=1;															//最小金额设置
		
		//单笔限额开启验证
		if($singleQuotaReSwitch){
			valid.settings.rules.txtSingleQuota = {required : true , digits2 : true};
			valid.settings.messages.txtSingleQuota = {required : comm.lang("hsbAccount").hsbBuy.singleAmount , digits : comm.lang("hsbAccount").hsbBuy.digits};
			//校验是否超过平台阀值
			if(zfxeszFun.dbPaymentMax){
				valid.settings.rules.txtSingleQuota.max = zfxeszFun.dbPaymentMax*1;
				valid.settings.messages.txtSingleQuota.max = "单笔支付最大可设置限额"+comm.formatMoneyNumber(zfxeszFun.dbPaymentMax)+"，您输入的金额已超限。";
			}
		}else{
			valid.settings.rules.txtSingleQuota = {};
		}
		
		//每日限额开启验证
		if($dayQuotaReSwitch){
			valid.settings.rules.txtDayQuota = {required : true  , digits2 : true , min : minAmount};
			valid.settings.messages.txtDayQuota = {required : comm.lang("hsbAccount").hsbBuy.dayAmount , digits: comm.lang("hsbAccount").hsbBuy.digits , min : comm.lang("hsbAccount").hsbBuy.dayMinAmount};
			//校验是否超过平台阀值
			if(zfxeszFun.mrPaymentMax){
				valid.settings.rules.txtDayQuota.max = zfxeszFun.mrPaymentMax*1;
				valid.settings.messages.txtDayQuota.max = "每日支付最大可设置限额"+comm.formatMoneyNumber(zfxeszFun.mrPaymentMax)+"，您输入的金额已超限。";
			}
			//最小值
			if($singleQuotaReSwitch){
				if($singleQuotaReSwitch){
					minAmount=$('#txtSingleQuota').val();
					valid.settings.rules.txtDayQuota.min = minAmount;
				}
			}else{
				valid.settings.messages.txtDayQuota.min=comm.lang("hsbAccount").hsbBuy.minAmount;
			}
			
		}else {
			valid.settings.rules.txtDayQuota = {};
		}
		   
		return valid;
	  }
	}	
	return zfxeszFun;
});