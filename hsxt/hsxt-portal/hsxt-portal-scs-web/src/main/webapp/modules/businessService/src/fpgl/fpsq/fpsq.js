define(['text!businessServiceTpl/fpgl/fpsq/fpsq.html',
	 'businessServiceDat/businessService',
	 'businessServiceLan'
		], function(fpsqTpl,dataModoule){
	var fpsqFun = {
		json_xz : null,
		invoiceAmount:null,
		invoiceCode:null,
		invoiceNo:null,
		zt:null,
		amount: 0,
		altogetherAmount:0,
		showPage : function(allAmount){
			
			//列表界面带过来的值，当点击菜单刷新后发票金额丢失保存到缓存中
			if(comm.isNotEmpty(allAmount)){
				comm.setCache("invoice","allAmount",allAmount);
			}else{
				allAmount = comm.getCache("invoice","allAmount");
			}
			
			fpsqFun.altogetherAmount = allAmount;
			
			$('#busibox').html(fpsqTpl) ;
				
				//切换配送方式
				$('#fpgl_ksps,#fpgl_zyps').click(function(e){
					var radioId = $(e.currentTarget).attr('id');
					if (radioId == 'fpgl_ksps' ){
						$('#fpsq_cygs').removeClass('none');
						$('#fpsq_kdgs').removeClass('none');
						$('#fpsp_ydh').removeClass('none');
					} else {
						$('#fpsq_cygs').addClass('none');
						$('#fpsq_kdgs').addClass('none');
						$('#fpsp_ydh').addClass('none');
					}					
				});
				
				json_xz=[];
				invoiceAmount=new Array();
				invoiceCode=new Array();
				invoiceNo=new Array();
				zt=new Array();
				cacheUtil.findCacheSystemInfo(function(res){
					var resNo=res.platResNo;
					dataModoule.findMainInfoDefaultBankByResNo({companyResNo:resNo},function(res){	
							 var obj=res.data.mainInfo;
							 obj.allAmount=allAmount;
							 var bankInfo=res.data.bankInfo;
							 if(!bankInfo){
									comm.error_alert("该企业无默认银行账户", 400);
									obj.bankBranchName = "";
									obj.bankNo = "";
									return false;
							  }else{
								  obj.bankBranchName=bankInfo.bankName;
								  obj.bankNo=bankInfo.bankAccNo;
							  }
							  obj.openContent="服务收益";
							  $('#busibox').html(_.template(fpsqTpl,obj));
							  fpsqFun.fpxz_init();
					});
				});								
			},
			fpxz_init : function(){
				comm.initSelect('#fpsq_invoiceType', comm.lang("businessService").invoice_type,150,0);
				/*新增发票事件*/
				$('#fpsq_xzfp').click(function(){	
					if(!fpsqFun.xzfpValid()){return;}
				
					var param = $('#fpsq_xzfpform').serializeJson(),
						obj = {};
					
					obj.invoiceCode = param.invoiceCode;
					obj.invoiceNo = param.invoiceNo;
					obj.invoiceAmount = comm.formatMoneyNumber(param.invoiceAmount);
					obj.zt = '1';
					
					//记录开具发票金额
					try{
						fpsqFun.amount = fpsqFun.amount + parseFloat(param.invoiceAmount);
					}catch(e){}
					
					
					json_xz.push(obj);						
					fpsqFun.fpjl_talbe_show(json_xz);	
					invoiceAmount.push(param.invoiceAmount);
					invoiceCode.push(param.invoiceCode);
					invoiceNo.push(param.invoiceNo);
					zt.push(obj.zt);
					
					$('#invoiceCode, #invoiceNo, #invoiceAmount').val('');
							
				});
				/*end*/
				$('#qykfp_qx').triggerWith('#fpgl_cxfpsqd');
				$('#qykfp_qr').click(function(){
					
					//验证发票类型
					var $invoiceType = $("#fpsq_invoiceType").attr("optionvalue");
					if(!$invoiceType || $invoiceType==""){
						comm.warn_alert(comm.lang("businessService").select_invoice_type);
						return false;
					}
					
					if(zt.length == 0){
						comm.warn_alert("需开发票总金额为"+comm.formatMoneyNumber(fpsqFun.altogetherAmount)+"，您尚未添加发票", 435);
						return false;
					}
					
					if(fpsqFun.amount <fpsqFun.altogetherAmount){
						comm.warn_alert("需开发票总金额为"+comm.formatMoneyNumber(fpsqFun.altogetherAmount)+"，您需一次性开具所需发票",415);
						return false;
					}
					
					var params=$("#fpsq_xzfpform").serializeJson();
					$.extend(params,{"invoiceNos":invoiceNo.join(',')});
					$.extend(params,{"invoiceCodes":invoiceCode.join(',')});
					$.extend(params,{"invoiceAmounts":invoiceAmount.join(',')});
					$.extend(params,{"zts":zt.join(',')});
					$.extend(params,{"invoiceType":$invoiceType});
					$.extend(params,{"allAmount":fpsqFun.altogetherAmount});
					dataModoule.custOpenInvoice(params,function(res){
						comm.yes_alert("企业开具发票成功",400);
						$('#fpgl_cxfpsqd').click(); 
					});
				});/*end*/
			},
			fpjl_talbe_show : function(json){
					gridObj_xz = $.fn.bsgrid.init('fpsq_table', {				 
					pageSizeSelect: true ,
					pageSize: 10 ,
					stripeRows: true,  //行色彩分隔 
					displayBlankRows: false ,   //显示空白行
					localData:json,
					operate : {	
						detail : function(record, rowIndex, colIndex, options){
							var link1 = null;
							
//							if(record.zt == '0'){
//								link1 = '<span class="gray">已删除</span>';
//							}
//							else{
								link1 = $('<a>删除</a>').click(function(e) {								
									fpsqFun.shanChu(rowIndex);		
								});	
//							}							
							return link1;							
						}
					}					
				});			
			},
	shanChu : function(row){
		comm.confirm({
			imgFlag : true,
			imgClass : 'tips_ques',
			content : '确认删除此发票？',
			callOk : function(){
//				json_xz[row].zt = '0';
//				zt[row]='0';
				json_xz.splice(row,1);
				zt.splice(row,1);
				invoiceAmount.splice(row,1);
				invoiceCode.splice(row,1);
				invoiceNo.splice(row,1);
				if(json_xz.length == 0){
					$('#fpgl_fpsq').click();
				}else{
					fpsqFun.fpjl_talbe_show(json_xz);
				}
			}
		});	
	},
	xzfpValid : function(){
		return comm.valid({
			left:100,
			top:20 ,
			formID:'#fpsq_xzfpform',
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
					required : comm.lang("businessService")[35124]
				},
				invoiceNo : {
					required : comm.lang("businessService")[35126]
				},
				invoiceAmount : {
					required : comm.lang("businessService")[35125]
				}
			}
 
		});		
	}
	}
	return fpsqFun;
});