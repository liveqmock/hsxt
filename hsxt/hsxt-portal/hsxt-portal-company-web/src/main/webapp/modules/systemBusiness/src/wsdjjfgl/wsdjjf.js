define(['text!systemBusinessTpl/wsdjjfgl/wsdjjf/wsdjjf.html',
		'text!systemBusinessTpl/wsdjjfgl/wsdjjf/qr.html',
		'text!systemBusinessTpl/wsdjjfgl/wsdjjf/tsxx.html',
		'systemBusinessDat/systemBusiness',
		'accountManageDat/accountManage'
		],function(tpl, qrTpl, tsxxTpl,systemBusiness,accountManage){
	var wsdjjf={
		voucherRate:null,
		voucherAmount:null,
		showPage : function(){
			var currencyRate;
			var currencyNameCn;
			var sourceCurrencyCode;
			cacheUtil.findCacheLocalInfo(function(res){
				currencyRate=comm.formatTransRate(res.exchangeRate);
				currencyNameCn=res.currencyNameCn;
				sourceCurrencyCode=res.currencyNo;
			});
			systemBusiness.searchEntStatusInfo({},function(res){
				var status=res.data.info.baseStatus;
				var obj = {
					status : status
				};
				$('#busibox').html(_.template(tpl,obj));
				if(status && status!='5' && status!='6' && status!='7' && status!='8'){
					//下拉框列表
					systemBusiness.query_pointrateMenu(null,function(res){
						var jfbl=res.data.rateList;
						var voucherList=res.data.voucherList;
						if(jfbl && jfbl.length>0){
							$("#wsdjjf_jfbl").selectList({
								width: 190,
								optionWidth: 190,
								options:jfbl
							});
						}else{
							comm.yes_alert(comm.lang("systemBusiness").wsjfdj_pointNumSet,400,function(){
								$('#wsdjjfgl_wsdjjfblsz').click();
							});
						}
						 voucherAmount=res.data.voucherAmount;//抵扣券每张面额
						 voucherRate=res.data.voucherRate;//抵扣券金额占消费金额比率
						$("#wsdjjf_dkj").selectList({
							width: 190,
							optionWidth: 190,
							options:voucherList
						}).change(function(e){
							var dkj_val = $(this).attr('optionValue');
							var consume_fee = $('#consume_fee').val();
							if(!dkj_val){
								dkj_val=0;
							}
							if (!isNaN(consume_fee)) {
								var sfje_val = (consume_fee - dkj_val*voucherAmount).toFixed(2);	
								$("#wsdjjf_sfje").val(sfje_val);
								if (!consume_fee) {
									$("#wsdjjf_sfje").val("");
								}
							}
						});
					});
				}
				
				//消费金额文本框-按下键盘、失去焦点事件
				$("#consume_fee").bind("keyup blur change", function () {
					var consume_fee = $(this).val();
					var dkj_val = $('#wsdjjf_dkj').attr('optionValue');
					if(!dkj_val){
						dkj_val=0;
					}
					if (!isNaN(consume_fee)) {
						var sfje_val = (consume_fee - dkj_val*voucherAmount).toFixed(2);	
						$("#wsdjjf_sfje").val(sfje_val);
						if (!consume_fee) {
							$("#wsdjjf_sfje").val("");
						}
					}
				});
				//确认按钮
				$('#btn_wsdjjf_tj').click(function(){
						if (!wsdjjf.validateData()) {
							return;
						}
						var hscard_num = $("#hscard_num").val();
					    var consume_fee = comm.formatMoneyNumber2($("#consume_fee").val());
					    var sfje=$('#wsdjjf_sfje').val();
					    var deductionVoucher= $('#wsdjjf_dkj').attr('optionValue');
					    if(!deductionVoucher){
					    	deductionVoucher=0;
					    }
						//抵扣金额不超过消费金额的50%
					    if(consume_fee*(1-voucherRate)>sfje){
					    	comm.error_alert(comm.lang("systemBusiness")[11007],400);
							return false;
					    }
					    var wsdjjf_jfbl = comm.formatTransRate($("#wsdjjf_jfbl").val());
					    systemBusiness.pointRegister({consume_fee:consume_fee,wsdjjf_jfbl:wsdjjf_jfbl},function(res){//计算扣除计分数
				            var resDate=res.data;
				            var yzfjfs = comm.formatMoneyNumber(resDate);
				       
						var zhkchsb = comm.formatMoneyNumber(currencyRate*resDate);
						var zhkchsb_num = currencyRate*resDate;
						
						var resDate = {
								hscard_num : hscard_num,
								consume_fee : consume_fee,
								wsdjjf_jfbl : wsdjjf_jfbl,
								currencyRate : currencyRate,
								yzfjfs : yzfjfs,
								zhkchsb : zhkchsb,
								sfje : sfje,
								deductionVoucher:deductionVoucher
						};
						$('#wsdjjf_1').addClass('none');
						$('#wsdjjf_2').removeClass('none').html(_.template(qrTpl,resDate));
						
						//修改按钮
						$('#btn_wsdjjf_xg').click(function(){
							$('#wsdjjf_1').removeClass('none');
							$('#wsdjjf_2').addClass('none').html('')
						});
						//确认按钮
						$('#btn_wsdjjf_qr').click(function(){
							if(zhkchsb_num<0.1){
								comm.error_alert(comm.lang("systemBusiness")[11005],400);
								return false;
							}
							var sourceTransDt=comm.getCurrDateYMDHMS();
							var params={
									yfhsb    : zhkchsb_num,
									perResNo : hscard_num,
									transType: 'A23000',
									channelType: '1',
									sourceCurrencyCode:sourceCurrencyCode,
									deductionVoucher:deductionVoucher,
									sourceTransAmount :sfje,
									orderAmount:consume_fee,
									pointRate:wsdjjf_jfbl,
									sourceTransDate:sourceTransDt,
									currencyRate:currencyRate
							};
							systemBusiness.getSequence(params,function(res){//获取流水号
								            var resDate=res.data;
								            resDate.currencyNameCn=currencyNameCn;
											$('#wsdjjf-dialog > p').html(_.template(tsxxTpl, resDate));
											$('#wsdjjf-dialog').dialog({
												title: '提示',
												width: 400,
												closeIcon:true,
												buttons: {
													"确定": function(){	
															//跳转到网上登记积分查询页面									
															$('#wsdjjf-dialog').dialog("destroy");
															$('#wsdjjfgl_wsdjjfcx').trigger('click');							
													}
												}
											});
						    });
					});
			});
			});
			});
		},
		validateData : function(){
			return comm.valid({
				formID : '#wsdjjf_form',
				rules : {
					hscard_num : {
						required : true,
						hsCardNo:true
					},
					consume_fee : {
						required : true,
						number:true
					},
					wsdjjf_jfbl : {
						required : true
					}
				},
				messages : {
					hscard_num : {
						required : comm.lang("systemBusiness").wsjfdj_resNoNotNull,
						hsCardNo: comm.lang("systemBusiness").resNoP
					},
					consume_fee : {
						required : comm.lang("systemBusiness").wsjfdj_consumFeeNotNull,
						number: comm.lang("systemBusiness").wsjfdj_consumMustNum
					},
					wsdjjf_jfbl : {
						required : comm.lang("systemBusiness").wsjfdj_pointNumSelect
					}
				}
			});
		}
	}
	return wsdjjf;
});