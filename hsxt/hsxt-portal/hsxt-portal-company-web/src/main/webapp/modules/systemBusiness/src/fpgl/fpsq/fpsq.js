define(['text!systemBusinessTpl/fpgl/fpsq/fpsq.html',
        'systemBusinessDat/systemBusiness', 
        'systemBusinessLan'
		], function(fpsqTpl,dataModoule){
	return fpsq={
		json_xz : null,
		invoiceAmount:null,
		invoiceCode:null,
		invoiceNo:null,
		zt:null,
		showPage : function(allAmount){
			$('#busibox').html(fpsqTpl) ;
				//绑定按钮
				$('#qykfp_qx').triggerWith('#fpgl_cxfpsqd');
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
				cacheUtil.findCacheLocalInfo(function(res){
					var resNo=res.platResNo;
						dataModoule.findMainInfoDefaultBankByResNo({companyResNo:resNo},function(res){
							 var obj=res.data.mainInfo;
							 obj.allAmount=allAmount;
							 fpsq.allAmount = allAmount;
							 if(res.data.baseInfo){
						    	 obj.officeTel = res.data.baseInfo.officeTel;
						     }else{
						    	 obj.officeTel = "";
						     }
							 var bankInfo=res.data.bankInfo;
							 if(!bankInfo){
											comm.error_alert("该企业无默认银行账户",400)
											return false;
							  }
								obj.bankBranchName=bankInfo.bankName;
								obj.bankNo=bankInfo.bankAccNo;	
								obj.openContent="服务收益";
								$('#busibox').html(_.template(fpsqTpl,obj));
								fpsq.fpxz_init();
						});
				});								
			},
			fpxz_init : function(){
				comm.initSelect('#fpsq_invoiceType', comm.lang("systemBusiness").invoice_type,150,0);
				/*新增发票事件*/
				$('#fpsq_xzfp').click(function(){
				
					if(!fpsq.xzfpValid()){return;}
				
					var param = $('#fpsq_xzfpform').serializeJson(),
						obj = {};
					
					obj.invoiceCode = param.invoiceCode;
					obj.invoiceNo = param.invoiceNo;
					obj.invoiceAmount = comm.formatMoneyNumber(param.invoiceAmount);
					obj.zt = '1';
					
					json_xz.push(obj);						
					fpsq.fpjl_talbe_show(json_xz);	
					invoiceAmount.push(param.invoiceAmount);
					invoiceCode.push(param.invoiceCode);
					invoiceNo.push(param.invoiceNo);
					zt.push(obj.zt);
					
					$('#invoiceCode, #invoiceNo, #invoiceAmount').val('');
							
				});
				/*end*/
				
				$('#qykfp_qr').click(function(){
					var invoiceType = $("#fpsq_invoiceType").attr("optionvalue");
					var totalAmount = 0;
					if(zt.length == 0){
						comm.warn_alert("需开发票总金额为"+comm.formatMoneyNumber(fpsq.allAmount)+"，您尚未添加发票", 435);
						return false;
					}
					for(var i=0; i<zt.length; i++){
						if(zt[i] == '1'){
							totalAmount += parseInt(invoiceAmount[i]);
						}
					}
					if(totalAmount < parseFloat(fpsq.allAmount)){
						comm.warn_alert("需开发票总金额为"+comm.formatMoneyNumber(fpsq.allAmount)+"，您需一次性开具所需发票", 435);
						return false;
					}
		 			if(invoiceType == ""){
		 				comm.warn_alert("请选择选择发票类型.", 400);
		 				return;
		 			}
					var params=$("#fpsq_xzfpform").serializeJson();
					$.extend(params,{"invoiceNos":invoiceNo.join(',')});
					$.extend(params,{"invoiceCodes":invoiceCode.join(',')});
					$.extend(params,{"invoiceAmounts":invoiceAmount.join(',')});
					$.extend(params,{"zts":zt.join(',')});
					$.extend(params,{"invoiceType":$("#fpsq_invoiceType").attr("optionvalue")});
					$.extend(params,{"allAmount":fpsq.allAmount});
					dataModoule.custOpenInvoice(params,function(res){
						comm.yes_alert("企业开具发票成功",400);
						$('#fpgl_cxfpsqd').click(); 
					});
				});/*end*/
				$('#qykfp_qx').triggerWith('#fpgl_cxfpsqd');
			},
			fpjl_talbe_show : function(json){
				comm.getEasyBsGrid('fpsq_table', json, function(record, rowIndex, colIndex, options){
					return $('<a>删除</a>').click(function(e) {								
						fpsq.shanChu(rowIndex);		
					});
				});
			},
			shanChu : function(row){
				
				comm.confirm({
					imgFlag : true,
					imgClass : 'tips_ques',
					content : '确认删除此发票？',
					callOk : function(){
//						json_xz[row].zt = '0';
//						zt[row]='0';
						json_xz.splice(row,1);
						zt.splice(row,1);
						invoiceAmount.splice(row,1);
						invoiceCode.splice(row,1);
						invoiceNo.splice(row,1);
						if(json_xz.length == 0){
							fpsq.fpjl_talbe_show(json_xz);
						}else{
							fpsq.fpjl_talbe_show(json_xz);
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
							required : comm.lang("systemBusiness")[35124]
						},
						invoiceNo : {
							required : comm.lang("systemBusiness")[35126]
						},
						invoiceAmount : {
							required : comm.lang("systemBusiness")[35125],
						}
					}
				});		
			}
	};
});