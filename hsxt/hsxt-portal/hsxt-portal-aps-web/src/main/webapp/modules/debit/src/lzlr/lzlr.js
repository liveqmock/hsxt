define([ "text!debitTpl/lzlr/lzlr.html", 
         "text!debitTpl/lzlr/fkyt_dialog.html",
		'debitDat/debit',
		'debitLan' ], function(lzlrTpl, fkyt_dialogTpl,
		dataModoule) {
	var lzlrFun = {
		showPage : function() {
			$('#busibox').html(_.template(lzlrTpl));

			// 下拉列表
			dataModoule.listAccountingMemu(null, function(res_) {
				$("#accountInfoId").selectList({
					width : 500,
					optionWidth : 505,
					options : res_.data
				}).change(function(e) {
					lzlrFun.getAccouting($(this).attr("optionvalue"));
				});
			});
			
			comm.initSelect('#payType', comm.lang("debit").payType, null, "", {name:'', value:''});

			// 日期控件
			$( "#accountReceiveTime" ).datepicker({dateFormat:'yy-mm-dd'});
			$( "#payTime" ).datepicker({dateFormat:'yy-mm-dd'});
		
			 //保存
			 $("#btn_bg").click(function(){
				 lzlrFun.aptitudeUpload();
			 });
			 
			 //加载付款用途列表
			$('#select_btn').click(function() {
				$('#dialogBox').html(fkyt_dialogTpl);
				var payUse=$("#payUse").val();
				$("[name='diaolog_payuse']").each(function(){
					var temp=$(this).val();
					if(payUse.indexOf(temp)!=-1){
						 $(this).attr("checked",'true'); 
					}				       				         
				});   
				/* 弹出框 */
				$("#dialogBox").dialog({
					title : "选择付款用途",
					width : "300",
					modal : true,
					buttons : {
						"确定" : function() {
							var payUse= new Array();
							var payUseString=new Array();
							$('input[name="diaolog_payuse"]:checked').each(function(){   			       
								payUse.push($(this).val());
								payUseString.push(comm.lang("debit").payUse[$(this).val()]);
						    });    
							$("#payUse").val(payUse.join(','));
							$("#payUseString").val(payUseString);
							$(this).dialog("destroy");
						},
						"取消" : function() {
							$(this).dialog("destroy");
						}
					}
				});
				/* end */
			
			});			
			this.picPreview();
		},
		/**
		 * 绑定图片预览
		 */
		picPreview : function(){
			$("body").on("change", "", function(){
				comm.initTmpPicPreview('#bimg', $('#bimg').children().first().attr('src'));
			});
			comm.initUploadBtn(['#filename'], ['#bimg'], 81, 52);
		},
		getAccouting : function(receiveAccountInfoId){
			dataModoule.queryAccountingInfo({receiveAccountInfoId : receiveAccountInfoId},function(res){
				var accountingdata = res.data;
				$('#receiveAccountNo').val(accountingdata.receiveAccountNo);		
				$('#bankName').val(accountingdata.bankName);
				});			
		},
		save : function(){
			if(!lzlrFun.validateData().form()){
				return;
			}
			var params = $("#lzlrTpl_form"). serializeJson();
			$.extend(params,{"accountInfoId":$("#accountInfoId").attr("optionvalue")});
			$.extend(params,{"payType":$("#payType").attr("optionvalue")});
			dataModoule.createDebit(params,function(res){
				comm.yes_alert("录入成功",400);
				lzlrFun.showPage();
			});		
		},
		/**
		 * 表单校验
		 */
		validateData : function(){
			var valida = $("#lzlrTpl_form").validate({ 
				rules : {
					accountInfoId : {
						required : true
					},
					accountReceiveTime : {
						required : true
						
					},
					receiveAccountNo : {
						required : true
					},
					payTime : {
						required : true,
						endDate : "#accountReceiveTime"
					},
					/*payBankName : {
						required : true
					},*/
					payAmount : {
						required : true,
						huobi:true
					},
					payType : {
						required : true
					},
					payEntCustName : {
						required : true
					},
					useEntCustName : {
						rangelength:[11,11]
					}
				},
				messages : {
					accountInfoId : {
						required : comm.lang("debit")[35003]
					},
					accountReceiveTime : {
						required : comm.lang("debit")[35017]
						
					},
					receiveAccountNo : {
						required :  comm.lang("debit")[35007]
					},
					payTime : {
						required :  comm.lang("debit")[35019],
					    endDate: comm.lang("debit")[35037]
					},
					/*payBankName : {
						required :  comm.lang("debit")[35014]
					},*/
					payAmount : {
						required :  comm.lang("debit")[35010],
						huobi: comm.lang("debit")[35039]
					},
					payType : {
						required :  comm.lang("debit")[35011]
					},
					payEntCustName : {
						required :  comm.lang("debit")[35013]
					},
					useEntCustName : {
						rangelength :  comm.lang("debit")[35038]
					}
				},
				errorPlacement : function (error, element) {
					$(element).attr("title", $(error).text()).tooltip({
						tooltipClass: "ui-tooltip-error",
						destroyFlag : true,
						destroyTime : 3000,
						position : {
							my : "left+2 top+30",
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
			
			//银行卡号
			var $payBankNo = comm.navNull($("#payBankNo").val());
			if($payBankNo!=""){
				valida.settings.rules.payBankNo = {bankNo : true};
				valida.settings.messages.payBankNo = {bankNo : comm.lang("debit").bankNo_format_error};
			}else{
				valida.settings.rules.payBankNo = {bankNo : false};
			}
			return valida;
		},
		/**
		 * 上传图片信息
		 */
		aptitudeUpload : function(){
			var ids = [];
			var delFileIds = [];
			if($("#filename").val() != ""){
				if($("#fileId").val() != ""){
					delFileIds[delFileIds.length] = $("#fileId").val();
				}
				ids[ids.length] = "filename";
			}
			if(ids.length == 0){
				lzlrFun.save();
			}else{
				comm.uploadFile(null, ids, function(data){
					if(data.filename){
						$("#fileId").val(data.filename);
						lzlrFun.save();
					}else{
						//重置默认上传文件
						$("#bimg").removeAttr("data-imgsrc");
						$("#bimg img").attr("src","resources/img/noImg.gif").show();
						
						comm.error_alert(comm.lang("common").comm.upload_erro);
					}
					lzlrFun.picPreview();
					
					
				}, function(err){
					//上传发生错误，重置上传文件控件
					$("#bimg").removeAttr("data-imgsrc");
					$("#bimg img").attr("src","resources/img/noImg.gif").show();
					lzlrFun.picPreview();
					
					comm.error_alert(comm.lang("common").comm.upload_erro);
					
				
					
					
				}, {delFileIds:delFileIds});
			}
		}
	}
	return lzlrFun;
});