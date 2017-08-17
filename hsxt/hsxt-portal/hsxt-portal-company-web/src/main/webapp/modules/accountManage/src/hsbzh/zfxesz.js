define(['text!accountManageTpl/hsbzh/zfxesz/zfxesz.html',
		'text!accountManageTpl/hsbzh/zfxesz/zfxesz_edit.html',
		'accountManageDat/accountManage'
		/*'text!accountManageTpl/hsbzh/zfxesz/zfxesz_add.html',
		'text!accountManageTpl/hsbzh/zfxesz/del_dialog.html'*/
		], function(zfxeszTpl, zfxesz_editTpl, accountManageAjax){
	return zfxesz = {
		showPage : function(){
			$('#busibox').html(_.template(zfxeszTpl))/*.append(_.template(zfxesz_addTpl))*/;
			
			var aounmtMinByTime;
			var amountMaxByDay;
			var timesMaxByDay;
			
			//加载数据
			var jsonParam ={}
			accountManageAjax.payLimitSetting(jsonParam,function (response) {
				if (response) {
					aounmtMinByTime = response.data.aounmtMinByTime;
					amountMaxByDay = response.data.amountMaxByDay;
					timesMaxByDay = response.data.timesMaxByDay;
					//加载数据
					$("#aounmtMinByTime").text(comm.formatMoneyNumber(aounmtMinByTime));	
					$("#amountMaxByDay").text(comm.formatMoneyNumber(amountMaxByDay));	
					$("#timesMaxByDay").text(timesMaxByDay); 
				}
			});
			
			
			$('#modify_btn').click(function(){
				$('#busibox').html(_.template(zfxesz_editTpl));
				
				$("#dbhsbzfxe").val(aounmtMinByTime);
				$("#mrhsbzfxe").val(amountMaxByDay);
				$("#mrhsbzfxzcs").val(timesMaxByDay);
				
				zfxesz.getVCode();
				
				$('#cancel_btn').triggerWith('#hsbzh_zfxesz');
				
				//保留两位小数点
				$("#dbhsbzfxe,#mrhsbzfxe").blur(function(){
					var $money=$.trim($(this).val()); //输入金额
					
					//判断是否为金额
					if(!isNaN($money)){
						$(this).val(comm.formatMoneyRound($money));
					}
				});
				
				$('#save_edit_btn').click(function(){
					if(!zfxesz.validateEditForm()){
						return;	
					}
					comm.getToken(function(resp){
						if(resp){
							var aounmtMin =  $("#dbhsbzfxe").val();
							var amountMax = $("#mrhsbzfxe").val();
							var timesMax = $("#mrhsbzfxzcs").val();
							var code = $("#code").val();
							
							var jsonParam = {
									aounmtMinByTime : aounmtMin,
									amountMaxByDay : amountMax,
									timesMaxByDay : timesMax,
									tradePwd:comm.tradePwdEncrypt($('#zfxesz_pwd').val(),resp.data),
									randomToken:resp.data,
									code : code
							};
							
							//支付限额修改
							accountManageAjax.payLimitUpdate(jsonParam,function (response) {
									comm.alert({
										imgFlag : true,
										imgClass : 'tips_yes',
										content : '互生币支付限额设置成功！',
										callOk : function(){
											$('#hsbzh_zfxesz').trigger('click');	
										}	
									});
							});
						}
					});
					
				});
				
				var isOver = false;
								
				function prompt_close(obj)  
				{
					if(!isOver){
						obj.addClass('none');
					}
				}  
			  
				function _prompt_close(obj)  
				{  
					return function()  
					{  
						prompt_close(obj);  
					}  
				}  
				
				/*单笔互生币支付限额说明*/
				$('#dbxe_btn').bind('mouseover', function(){

					$('#dbxe_prompt').removeClass('none');
					
				}).bind('mouseout', function(){
					
					$('#dbxe_prompt').bind('mouseover', function(){
						isOver = true;
						$(this).removeClass('none');	
					}).bind('mouseout', function(){
						isOver = false;
						$(this).addClass('none');		
					});
					
					setTimeout(_prompt_close($('#dbxe_prompt')), 500);
						
				});
				/*end*/
				
				/*每日互生币支付限额说明*/
				$('#mrxe_btn').bind('mouseover', function(){
				
					$('#mrxe_prompt').removeClass('none');
					
				}).bind('mouseout', function(){
					
					$('#mrxe_prompt').bind('mouseover', function(){
						isOver = true;
						$(this).removeClass('none');	
					}).bind('mouseout', function(){
						isOver = false;
						$(this).addClass('none');	
					});
					
					setTimeout(_prompt_close($('#mrxe_prompt')), 500);
				});
				/*end*/
				
				/*每日互生币支付限制次数说明*/
				$('#xzcs_btn').bind('mouseover', function(){
				
					$('#xzcs_prompt').removeClass('none');
					
				}).bind('mouseout', function(){
					
					$('#xzcs_prompt').bind('mouseover', function(){
						isOver = true;
						$(this).removeClass('none');	
					}).bind('mouseout', function(){
						isOver = false;
						$(this).addClass('none');	
					});
					
					setTimeout(_prompt_close($('#xzcs_prompt')), 500);
				});
				/*end*/
				
				/* 获取验证码 */
				$('#img_smrz_yzm').click(function(){
					zfxesz.getVCode();
				});
				/* 获取验证码 */
				$('#img_smrz_hyz').click(function(){
					zfxesz.getVCode();
				});
				
			});
				
		},
		
	
		//验证码获取
		getVCode:function(){
			$("#img_smrz_yzm").attr("src",accountManageAjax.getCodeUrl());
		},
		validateEditForm : function(){
			
			jQuery.validator.addMethod("compare", function(value, element) {
				
				var returnVal = false;  
				var val2 = $("#mrhsbzfxe").val();
				var val1 = $("#dbhsbzfxe").val();
				
				if(val1==""){
					val1=0;
					$("#dbhsbzfxe").val(val1);
				}
				
				if(val2==""){
					val2=0;
					$("#mrhsbzfxe").val(val2)
				}
				
				if(parseFloat(val1) <=parseFloat(val2)){  
                    returnVal = true;  
                }
				return returnVal;   
				
			}, $.validator.format("单笔限额不得大于每日限额"));
			
			return comm.valid({
				formID : '#zfxesz_editForm',
				rules : {
					dbhsbzfxe : {
						number : true,
						min:0
					},
					mrhsbzfxe : {
						number : true,
						min:0,
						compare : true
					},
					zfxesz_pwd : {
						required : true,
						rangelength : [8,8]
					},
					code : {
						required : true	
					}	
				},
				messages : {
					dbhsbzfxe : {
						number : comm.lang("accountManage").number,
						min:comm.lang("accountManage").comm_min
					},
					mrhsbzfxe : {
						number : comm.lang("accountManage").number,
						min:comm.lang("accountManage").comm_min,
						compare : comm.lang("accountManage").singleLessDay
					},
					zfxesz_pwd : {
						required : comm.lang("accountManage").hsbcomm_jymm,
						rangelength:comm.lang("accountManage").hsbcomm_jymmlength
					},
					code : {
						required : comm.lang("accountManage").vaildCode	
					}	
				}	
			});	
		}
		
	}	
});