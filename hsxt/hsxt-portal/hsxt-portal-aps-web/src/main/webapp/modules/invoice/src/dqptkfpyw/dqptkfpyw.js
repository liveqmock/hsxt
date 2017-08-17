define(['text!invoiceTpl/dqptkfpyw/dqptkfpyw.html',
		'text!invoiceTpl/dqptkfpyw/dqptkfpyw_ck.html',
		'text!invoiceTpl/dqptkfpyw/dqptkfpyw_kp.html',
		'invoiceSrc/qyxx/qyxx_tab',
		'invoiceDat/invoice',
		'invoiceLan'],function(dqptkfpywlTpl, dqptkfpyw_ckTpl, dqptkfpyw_kpTpl, qyxx_tab,dataModoule){
	return dqptkfpywServices = {
		json_xz : null,
		invoiceAmount:null,
		invoiceCode:null,
		invoiceNo:null,
		zt:null,
		showPage : function(){
			$('#busibox').html(_.template(dqptkfpywlTpl));
			
			comm.initSelect('#search_custType', comm.lang("invoice").custType);
			comm.initSelect('#search_bizType', comm.lang("invoice").bizType, 185);
			/*end*/
			$("#kpfpool_query").click(function(){
				var params=$("#kpfpool_form").serializeJson();
				$.extend(params,{"search_custType":$("#search_custType").attr("optionvalue")});
				$.extend(params,{"search_bizType":$("#search_bizType").attr("optionvalue")});
				dataModoule.invoicePoolList("searchTable",params,dqptkfpywServices.edit);
			});	
		},
		edit : function(record, rowIndex, colIndex, options){
			if(colIndex == 5){
				var link1 = '';
				if (record.remainAmount > 0) {
					if(record.bizType == 'PC005'&&record.remainAmount<1000) {
						return link1;
					}
					link1 = $('<a>开票</a>').click(function (e) {
						//小于1000 进行提示消息
						if (record.remainAmount <= 0) {
							comm.error_alert("您开发票金额必须大于0", 435);
							return false;
						} else {
							//查询客户资料
							dataModoule.findMainInfoDefaultBankByResNo({companyResNo: record.resNo}, function (res) {
								var isExist = false;
								var obj = res.data.mainInfo;
								obj.openContent = comm.lang("invoice").bizType[record.bizType];
								obj.entCustId = record.custId;
								obj.allAmount = record.remainAmount;
								obj.bizType = record.bizType;
								var bankInfo = res.data.bankInfo;
								if (bankInfo) {
									isExist = true;
									obj.bankBranchName = bankInfo.bankName;
									obj.bankNo = bankInfo.bankAccNo;
								}
								dqptkfpywServices.kaiPiao(obj, isExist);
							});
						}
					});
				}
				return link1;
			}else if(colIndex == 0){
				var link2 = $('<a>' + record.resNo + '</a>').click(function(e) {
					dqptkfpywServices.ck_qyxx(record.custId);
				});	
				return link2;
			}else if(colIndex == 2){
				 return comm.lang("invoice").custType[record.custType];
			}else if(colIndex == 3){
				 return comm.lang("invoice").bizType[record.bizType];
			}else if(colIndex == 4){
				return comm.formatMoneyNumber(record.remainAmount);
			}		
		},		
		ck_qyxx : function(custId){
			var that = this;
			comm.liActive_add($('#ckqyxx'));
			this.showTpl($('#ckqyxxTpl'));			
			qyxx_tab.showPage(custId);
			
			$('#back_btn').click(function(){
				comm.liActive($('#dqptkfpyw'), '#lrfp, #ckqyxx, #ckfpxx');
				that.showTpl($('#dqptkfpywTpl'));
			});
			
			
		},
		kaiPiao : function(obj,isExistBank){
			$('#dqptkfpyw_kpTpl').html(_.template(dqptkfpyw_kpTpl, obj));
			comm.liActive_add($('#lrfp'));	
			dqptkfpywServices.showTpl($('#dqptkfpyw_kpTpl'));
			
			comm.initSelect('#fpsq_invoiceType', comm.lang("invoice").invoice_type, 185, null);
			dqptkfpywServices.json_xz=[];
			dqptkfpywServices.invoiceAmount=new Array();
			dqptkfpywServices.invoiceCode=new Array();
			dqptkfpywServices.invoiceNo=new Array();
			dqptkfpywServices.zt=new Array();
			
			/*新增发票事件*/
			$('#xzfp_btn').click(function(){	
				if(!dqptkfpywServices.xzfpValid()){return;}
				var param = $('#invoice_addform').serializeJson(),
					obj = {};
				obj.th_1 = param.invoiceCode;
				obj.th_2 = param.invoiceNo;
				obj.th_3 = comm.formatMoneyNumber(param.invoiceAmount);
				obj.zt = '1';
				
				dqptkfpywServices.json_xz.push(obj);		
				dqptkfpywServices.fpjl_talbe_show(dqptkfpywServices.json_xz);
				dqptkfpywServices.invoiceAmount.push(param.invoiceAmount);
				dqptkfpywServices.invoiceCode.push(param.invoiceCode);
				dqptkfpywServices.invoiceNo.push(param.invoiceNo);
				dqptkfpywServices.zt.push(obj.zt);
				
				$('#invoiceCode, #invoiceNo, #invoiceAmount').val('');
						
			});
			
			$('#back_dqptkfpyw').click(function(){
				comm.liActive($('#dqptkfpyw'), '#lrfp, #ckqyxx, #ckfpxx');
				dqptkfpywServices.showTpl($('#dqptkfpywTpl'));	
				$('#invoiceCode').tooltip().tooltip("destroy");
				$('#invoiceNo').tooltip().tooltip("destroy");
				$('#invoiceAmount').tooltip().tooltip("destroy");
			});
			$('#invoice_submit').click(function(){
				var totalAmount = 0;
				for(var i=0; i<dqptkfpywServices.zt.length; i++){
					totalAmount += parseFloat(dqptkfpywServices.invoiceAmount[i]);
				}
				
				totalAmount=totalAmount.toFixed(2);
				
				//发票类型
				var invoiceType = comm.removeNull($("#fpsq_invoiceType").attr("optionvalue"));
				if(invoiceType == ""){
					comm.error_alert("请选择发票类型", 400);
					return;
				}
				
				if(obj.bizType == 'PC005'){//消费积分可以比发票金额小
					if(parseFloat(totalAmount) > parseFloat(obj.allAmount)){
						comm.error_alert("需开发票金额为"+comm.formatMoneyNumber(obj.allAmount)+"，您开发票金额超过了所需金额", 435);
						return false;
					}
					if(parseInt(totalAmount) < 1000){
						comm.error_alert("您开发票金额不能低于1000", 435);
						return false;
					}
				}else{
					if(parseFloat(totalAmount) != parseFloat(obj.allAmount)){
						comm.error_alert("开发票金额为"+comm.formatMoneyNumber(obj.allAmount)+"，您需一次性开具所需发票", 435);
						return false;
					}
				}
				var params=$("#invoice_addform").serializeJson();
				$.extend(params,{"invoiceType":invoiceType});
				$.extend(params,{"invoiceNos":dqptkfpywServices.invoiceNo.join(',')});
				$.extend(params,{"invoiceCodes":dqptkfpywServices.invoiceCode.join(',')});
				$.extend(params,{"invoiceAmounts":dqptkfpywServices.invoiceAmount.join(',')});
				$.extend(params,{"zts":dqptkfpywServices.zt.join(',')});
				dataModoule.platCreateInvoice(params,function(res){
					comm.yes_alert("开发票成功",400);
					$('#back_dqptkfpyw').click();
					$("#kpfpool_query").click();	
				});
			});
		},
		fpjl_talbe_show : function(json){
			comm.getEasyBsGrid('searchTable_xz', dqptkfpywServices.json_xz, function(record, rowIndex, colIndex, options){
				return $('<a>删除</a>').click(function(e) {
					var row = (options.curPage-1)*10+rowIndex;
					dqptkfpywServices.shanChu(row);
				}.bind(this));
			});			
		},
		shanChu : function(row){
			comm.confirm({
				imgFlag : true,
				imgClass : 'tips_ques',
				content : '确认删除此发票？',
				callOk : function(){
					comm.arrayRemove(dqptkfpywServices.invoiceAmount, row);
					comm.arrayRemove(dqptkfpywServices.invoiceCode, row);
					comm.arrayRemove(dqptkfpywServices.invoiceNo, row);
					comm.arrayRemove(dqptkfpywServices.zt, row);
					comm.arrayRemove(dqptkfpywServices.json_xz, row);
					dqptkfpywServices.fpjl_talbe_show(dqptkfpywServices.json_xz);
				}
			});	
		},
		showTpl : function(tplObj){
			$('#dqptkfpywTpl, #dqptkfpyw_ckTpl, #dqptkfpyw_kpTpl, #ckqyxxTpl').addClass('none');
			tplObj.removeClass('none');	
		},
		xzfpValid : function(){
			return comm.valid({
				left:100,
				top:20 ,
				formID:'#invoice_addform',
				rules : {
					invoiceCode : {
						required : true
					},
					invoiceNo : {
						required : true	
					},
					invoiceAmount : {
						required : true,
						huobi :true
					}
		
				},
				messages : {
					invoiceCode : {
						required : comm.lang("invoice")[35124]
					},
					invoiceNo : {
						required : comm.lang("invoice")[35126]
					},
					invoiceAmount : {
						required : comm.lang("invoice")[35125]
					}
				}
	 
			});		
		}
	} 
});