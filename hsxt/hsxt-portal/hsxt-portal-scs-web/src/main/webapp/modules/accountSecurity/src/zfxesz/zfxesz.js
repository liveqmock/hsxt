define(['text!accountSecurityTpl/zfxesz/zfxesz.html',
		'text!accountSecurityTpl/zfxesz/zfxesz_edit.html',
		'text!accountSecurityTpl/zfxesz/zfxesz_add.html',
		'text!accountSecurityTpl/zfxesz/del_dialog.html'
		], function(zfxeszTpl, zfxesz_editTpl, zfxesz_addTpl, del_dialogTpl){
	return {
		showPage : function(){
			$('#busibox').html(_.template(zfxeszTpl)).append(_.template(zfxesz_addTpl));
			
			var self = this;
			var aounmtMinByTime;
			var amountMaxByDay;
			//var timesMaxByDay;
			
			//加载数据
			var jsonParam ={}
			
			comm.requestFun("payLimitSetting",jsonParam,function (response) {
				
					aounmtMinByTime = response.data.aounmtMinByTime;
					amountMaxByDay = response.data.amountMaxByDay;
					//timesMaxByDay = response.data.timesMaxByDay;
					//加载数据
					$("#aounmtMinByTime").text($.trim(aounmtMinByTime)!=""?comm.formatMoneyNumber(aounmtMinByTime):"");	
					$("#amountMaxByDay").text($.trim(amountMaxByDay)!=""?comm.formatMoneyNumber(amountMaxByDay):"");	
					//$("#timesMaxByDay").text(timesMaxByDay); 
					
				},comm.lang("accountManage"));
			
			
			$('#modify_btn').click(function(){
				$('#busibox').html(_.template(zfxesz_editTpl));
				
				$("#dbhsbzfxe").val(aounmtMinByTime);
				$("#mrhsbzfxe").val(amountMaxByDay);
				//$("#mrhsbzfxzcs").val(timesMaxByDay);
				
				self.getVCode();
				
				$('#cancel_btn').triggerWith('#zfxesz');
				
				
				
				$('#save_edit_btn').click(function(){
					if(!self.validateEditForm()){
						return;	
					}
					
					comm.getToken(function(resp){
						if(resp){
							var aounmtMin =  $("#dbhsbzfxe").val();
							var amountMax = $("#mrhsbzfxe").val();
							
							//var timesMax = $("#mrhsbzfxzcs").val();
							
							var code = $("#code").val();
							var jsonParam = {
									aounmtMinByTime : aounmtMin,
									amountMaxByDay : amountMax,
									//timesMaxByDay : timesMax,
									tradePwd:comm.tradePwdEncrypt($('#zfxesz_pwd').val(),resp.data),
									randomToken:resp.data,
									code : code
							};
							
							//支付限额修改
							comm.requestFun("payLimitUpdate",jsonParam,function (response) {
								comm.alert({
									imgFlag : true,
									imgClass : 'tips_yes',
									content : '互生币支付限额设置成功！',
									callOk : function(){
										$('#zfxesz').trigger('click');	
									}	
								});
							},comm.lang("accountManage"));
						}
					});
					
				});
				
				
				//三个说明 输入单笔互生币支付限额   ,  输入每日互生币支付限额    ,输入每日互生币支付限制次数
				$('#zfxesz_dbxe,#zfxesz_mrxe,#zfxesz_xzcs').mouseover(function(){
					$(this).next('div').css({'left':'0px','top':'25px'});
			 		$(this).next('div').removeClass('none');					 
				});
				$('#zfxesz_dbxe,#zfxesz_mrxe,#zfxesz_xzcs').mouseout(function(){
			 		$(this).next('div').addClass('none');					 
				});
				
				 
				/* 获取验证码 */
				$('#img_smrz_yzm').click(function(){
					self.getVCode();
				});
				/* 获取验证码 */
				$('#img_smrz_hyz').click(function(){
					self.getVCode();
				});
				
			});
			
			$('#delete_btn').click(function(){
				$('#del_dialog').html(_.template(del_dialogTpl));
				/*弹出框*/
				$( "#del_dialog" ).dialog({
					title:"输入交易密码",
					width:"400",
					modal:true,
					buttons:{ 
						"确定":function(){
							if(!self.validatePwdForm()){return;}
							$( this ).dialog( "destroy" );
							self.showTpl($('#zfxesz_addTpl'));
						},
						"取消":function(){
							 $( this ).dialog( "destroy" );
							 $('#pwd_del').tooltip().tooltip("destroy");
						}
					}
				});
				/*end*/	

			});
			
			$('#save_add_btn').click(function(){
				if(!self.validateAddForm()){return;}	
				comm.alert({
					imgFlag : true,
					imgClass : 'tips_yes',
					content : '互生币支付限额设置成功！',
					callOk : function(){
						$('#zfxesz').trigger('click');	
					}	
				});
			});
			
		},
		showTpl : function(tplObj){
			$('#zfxeszTpl, #zfxesz_editTpl, #zfxesz_addTpl').addClass('none');
			tplObj.removeClass('none');	
		},
		//验证码获取
		getVCode:function(){
			var param=comm.getRequestParams();
			var url = comm.domainList['scsWeb']+comm.UrlList["generateSecuritCode"]+"?custId="+param.custId+"&type=payLimist&"+(new Date()).valueOf();
			$("#img_smrz_yzm").attr("src",url);
		},
		/** 计算支付限额 */
		payLimit:function(){
			var $dayPayAmount=$.trim($("#mrhsbzfxe").val()); //每日支付限额
			var $singlePayAmount=$.trim($("#dbhsbzfxe").val());//单笔支付限额
			
			//空值时默认赋值为0
			if($dayPayAmount==""){$dayPayAmount="0"}
			if($singlePayAmount==""){$singlePayAmount="0"}
			
			//每日支付限额为0时，不限制
			if($dayPayAmount=="0"){
				return 0;
			}else {
				if($singlePayAmount=="0"){$singlePayAmount="0.01"}
				return $singlePayAmount;
			}
		},
		validateEditForm : function(){
			var self=this;
			return comm.valid({
				formID : '#zfxesz_editForm',
				rules : {
					dbhsbzfxe:{
						required : true,
						min:function(){
							var $dayPayAmount=$.trim($("#mrhsbzfxe").val()); //每日支付限额
							
							if($dayPayAmount!="" && $dayPayAmount!="0"){
								return 0.01;
							}
							return 0;
						}
					},
					mrhsbzfxe:{
						required : true,	
						min:self.payLimit
					},
					zfxesz_pwd : {
						required : true,
						minlength : 8
					},
					code : {
						required : true,
						minlength : 4
					}	
				},
				messages : {
					dbhsbzfxe:{
						required : comm.lang("accountManage").inputSingleLimit,
						min:comm.lang("accountManage").minSingleLimit
					},
					mrhsbzfxe:{
						required : comm.lang("accountManage").inputTotalLimit,
						min:comm.lang("accountManage").minTotalLimit
					},
					zfxesz_pwd : {
						required : comm.lang("accountManage").inputTradePwd,
						minlength : comm.lang("accountManage").dealPwdLength
					},
					code : {
						required : comm.lang("accountManage").inputVaildCode,	
						minlength : comm.lang("accountManage").vaildCodeLength	
					}	
				}	
			});	
		},
		validatePwdForm : function(){
			return comm.valid({
				formID : '#zfxesz_pwdForm',
				rules : {
					pwd_del : {
						required : true	,
						minlength : 8
					}	
				},
				messages : {
					pwd_del : {
						required : comm.lang("accountManage").inputTradePwd,
						minlength : comm.lang("accountManage").dealPwdLength
					}	
				}	
			});	
		},
		validateAddForm : function(){
			return comm.valid({
				formID : '#zfxesz_addForm',
				rules : {
					dbhsbzfxe_add:{
						required : true	
					},
					mrhsbzfxe_add:{
						required : true	
					},
					pwd_add : {
						required : true	,
						minlength : 8
					},
					code_add : {
						required : true	,
						minlength : 4
					}	
				},
				messages : {
					dbhsbzfxe_add:{
						required : comm.lang("accountManage").inputSingleLimit
					},
					mrhsbzfxe_add:{
						required : comm.lang("accountManage").inputTotalLimit
					},
					pwd_add : {
						required : comm.lang("accountManage").inputTradePwd,
						minlength : comm.lang("accountManage").dealPwdLength
					},
					code_add : {
						required : comm.lang("accountManage").inputVaildCode,
						minlength : comm.lang("accountManage").vaildCodeLength
					}	
				}	
			});	
		}
	}	
});