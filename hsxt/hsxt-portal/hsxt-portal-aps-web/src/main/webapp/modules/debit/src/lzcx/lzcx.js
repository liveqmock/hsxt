define(["text!debitTpl/lzcx/lzcx.html",
        "text!debitTpl/lzcx/lzcx_ck.html",
       /* "text!debitTpl/lzcx/lzcx_ck0.html",
		"text!debitTpl/lzcx/lzcx_ck123.html",
		"text!debitTpl/lzcx/lzcx_ck567.html",*/
		"text!debitTpl/lzcx/fh_dialog.html",
		"text!debitTpl/lzcx/lzcx_xg.html",
		"text!debitTpl/lzcx/lzcx_xg2.html",
		"text!debitTpl/lzcx/fkyt_dialog.html",
		"text!debitTpl/lzcx/lzcx_tk.html",
		"text!debitTpl/lzcx/sqtk_dialog.html",
		"text!debitTpl/lzcx/lzglsp.html",
		'debitDat/debit',
		'debitLan'],function(lzcxTpl, lzcx_ckTpl/*lzcx_ck0Tpl,lzcx_ck123Tpl, lzcx_ck567Tpl*/, fh_dialogTpl, 
				lzcx_xgTpl,lzcx_xg2Tpl, fkyt_dialogTpl, lzcx_tkTpl, sqtk_dialogTpl,lzglspTpl,dataModoule){
	return lzcx={
		lzcxBsGrid:null,
		showPage : function(){
            $('#busibox').html(_.template(lzcxTpl));
			
			//时间控件		    
		    comm.initBeginEndTime('#search_startDate','#search_endDate');
			comm.initSelect('#search_debitStatus', comm.lang("debit").debitStatus);
			
			//临帐分页查询
			$("#querylz").click(function(){
				if(!comm.queryDateVaild("search_form").form()){
					return;
				}
				var params={
						search_startTime:$("#search_startDate").val(),
						search_endTime:$("#search_endDate").val(),
						search_payEntCustName:$("#search_payEntCustName").val(),
						search_accountName:$("#search_accountName").val(),
						search_debitStatus:$("#search_debitStatus").attr("optionvalue")
				};
				lzcx.lzcxBsGrid=dataModoule.listDebit("searchTable",params,lzcx.detail,lzcx.detail2,lzcx.detail3);
			});
		},
		detail : function(record, rowIndex, colIndex, options){		
			if(colIndex == 3){
				if(!record.payUse){
					return "";
				}else{
					var payUses=record.payUse.split(',');
					var payUseString=new Array();
					for(var i=0;i<payUses.length;i++)
					{
						payUseString.push(comm.lang("debit").payUse[payUses[i]]);
					}
					return '<span title='+payUseString.join("/")+'>'+payUseString.join("/")+'</span>';
				}
			}else if(colIndex == 5){	
				return comm.formatMoneyNumber(record.payAmount);
			}else if(colIndex == 6){
				return comm.formatMoneyNumber(record.linkAmount);
			}else if(colIndex == 7){
				return comm.lang("debit").debitStatus[record.debitStatus];
			}else if(colIndex == 8){				
				var link1 = $('<a>查看</a>').click(function(e) {	
					lzcx.newChaKan(record);
					/*if(record.debitStatus == '0' || record.debitStatus == '4'){
						lzcx.chaKan_fuhe(record);
					}else if(record.debitStatus == '5' ||record.debitStatus == '6' ||record.debitStatus == '7'){
						lzcx.chaKan_tuikuan(record);	
					}else {
						lzcx.chaKan(record);	
					}*/
				});				
				return link1;
			}
		},
		detail2 : function(record, rowIndex, colIndex, options){//待关联状态不可以编辑
			if(colIndex == 8 && (record.debitStatus == '0' || record.debitStatus == '4'|| record.debitStatus == '1')){					
				var link1 = $('<a>修改</a>').click(function(e) {
					lzcx.xiuGai(record);
				}) ;				
				return link1;
			}
		},
		detail3 : function(record, rowIndex, colIndex, options){
			if(colIndex == 8 && record.debitStatus == '1'){					
				var link1 = $('<a>退款</a>').click(function(e) {					
					lzcx.tuiKuan(record);					
				}) ;				
				return link1;
			}
		},
		chaKan_guanlian_detail : function(record, rowIndex, colIndex, options){
			if(colIndex == 4){					
				return comm.lang("debit").orderType[record.orderType];
			}else if(colIndex == 5){					
				return comm.formatMoneyNumber(record.orderOriginalAmount-record.orderDerateAmount);
			}
			else if(colIndex == 6){					
				return comm.formatMoneyNumber(record.orderCashAmount);
			}
			
		},
		lzglsp:function(debitId){
			dataModoule.queryTempAcctLinkListByDebitId({debitId:debitId},function(res){
				var obj=res.data;
				$('#lzcx_operation_tpl').html(_.template(lzglspTpl, obj));
				$('#lzglsp_back').click(function(e){
					 $( this ).dialog( "destroy" );
				});
			});
		},
		xiuGai : function(obj){
			var query = $.extend(obj,{}); //复制对象
			
			$("#lzcxTpl").addClass('none');
			$("#lzcx_operation_tpl").removeClass('none');
			comm.liActive_add($('#lzxg'));	
			if(!query.payUse){
				query.payUseString="";
			}else{
				var payUses=query.payUse.split(',');
				var payUseString=new Array();
				for(var i=0;i<payUses.length;i++)
				{
					payUseString.push(comm.lang("debit").payUse[payUses[i]]);
				}
				query.payUseString=payUseString.join("/");
			}
			query.payTypeString=comm.lang("debit").payType[query.payType];
			if(query.debitStatus == '1'){
				 $('#lzcx_operation_tpl').html(_.template(lzcx_xg2Tpl, query));
			}else{
				 $('#lzcx_operation_tpl').html(_.template(lzcx_xgTpl, query));
				/* 日期控件 */
				$( "#accountReceiveTime" ).datepicker({dateFormat:'yy-mm-dd'});
				$( "#payTime" ).datepicker({dateFormat:'yy-mm-dd'});
			  /*下拉列表*/
				dataModoule.listAccountingMemu(null, function(res_) {
					$("#accountName").selectList({
						width : 500,
						optionWidth : 505,
						options : res_.data
					}).change(function(e) {
						
						lzcx.getAccouting($(this).attr("optionvalue"));
					});
				});
				comm.initSelect('#payType', comm.lang("debit").payType);
		    }
			//初始化小图片
			if(query.fileId){
				$("#img").attr("src", comm.getFsServerUrl(query.fileId));
				comm.initPicPreview("#img", query.fileId, "");
			}
			//修改提交
			$("#btn_bg").click(function(){
				lzcx.aptitudeUpload();
		    });
			
			//返回到查询页
			$("#xg_back").click(function(){
				lzcx.returnPageQuery();
			});
			//$('#xg_back').triggerWith('#lzcx');
			$('#select_btn').click(function(){
				$('#dialogBox').html(fkyt_dialogTpl);
				var payUse=$("#payUse").val();
				$("[name='diaolog_payuse']").each(function(){
					var temp=$(this).val();
					if(payUse.indexOf(temp)!=-1){
						 $(this).attr("checked",'true'); 
					}				       				         
				});   
				/*弹出框*/
				$( "#dialogBox" ).dialog({
					title:"选择付款用途",
					width:"300",
					modal:true,
					buttons:{ 
						"确定":function(){
							var payUse= new Array();
							var payUseString=new Array();
							$('input[name="diaolog_payuse"]:checked').each(function(){   			       
								payUse.push($(this).val());
								payUseString.push(comm.lang("debit").payUse[$(this).val()]);
						    });    
							$("#payUse").val(payUse.join(','));
							$("#payUseString").val(payUseString.join("/"));
							$(this).dialog("destroy");
						},
						"取消":function(){
							 $( this ).dialog( "destroy" );
						}
					}
				});
				/*end*/	
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
				lzcx.update();
			}else{
				comm.uploadFile(null, ids, function(data){
					if(data.filename){
						$("#fileId").val(data.filename);
					}else{
						//重置默认文件
						lzcx.picPreview();
						$("#bimg").removeAttr("data-imgsrc");
						$("#bimg img").attr("src","resources/img/noImg.gif").show();
					}
					lzcx.update();
				}, function(err){
					comm.error_alert(comm.lang("common").comm.upload_erro);
					
					//重置默认文件
					lzcx.picPreview();
					$("#bimg").removeAttr("data-imgsrc");
					$("#bimg img").attr("src","resources/img/noImg.gif").show();
				}, {delFileIds:delFileIds});
			}
		},
		update : function(){
			if(!lzcx.validateData().form()){
				return;
			}
			var params = $("#lzxg_form"). serializeJson();
			$.extend(params,{"accountInfoId":$("#accountName").attr("optionvalue")});
			$.extend(params,{"payType":$("#payType").attr("optionvalue")});
			dataModoule.updateDebit(params,function(res){
				 comm.alert({imgClass: 'tips_yes' ,content: "修改临账成功",callOk:function(){
					 $('#xg_back').click();
				 }});
			});	
		},
		getAccouting : function(receiveAccountInfoId){
			dataModoule.queryAccountingInfo({receiveAccountInfoId : receiveAccountInfoId},function(res){
				var accountingdata = res.data;
				$('#receiveAccountNo').val(accountingdata.receiveAccountNo);		
				$('#bankName').val(accountingdata.bankName);
				});			
		},
		tuiKuan : function(obj){
			var refund = $.extend(obj , {}); //复制对象
			
			$("#lzcxTpl").addClass('none');
			$("#lzcx_operation_tpl").removeClass('none');
			comm.liActive_add($('#lztksq'));
			
			if(!refund.payUse){
				refund.payUseString="";
			}else{
				var payUses=refund.payUse.split(',');
				var payUseString=new Array();
				for(var i=0;i<payUses.length;i++)
				{
					payUseString.push(comm.lang("debit").payUse[payUses[i]]);
				}
				refund.payUseString=payUseString.join("/");
			}
			refund.payType=comm.lang("debit").payType[refund.payType];
			$('#lzcx_operation_tpl').html(_.template(lzcx_tkTpl, refund));	
			//初始化小图片
			if(refund.fileId){
				$("#img").attr("src", comm.getFsServerUrl(refund.fileId));
				comm.initPicPreview("#img", refund.fileId, "");
			}
			
			$('#tkDiv').removeClass('none');
			
			//返回到查询页面
			$('#tk_back').click(function(){
				lzcx.returnPageQuery();
			});
			
			$('#tkDiv').click(function(){
				$('#dialogBox').html(_.template(sqtk_dialogTpl, refund));
				
				/*弹出框*/
				$( "#dialogBox" ).dialog({
					title:"申请退款",
					width:"400",
					height:"220",
					modal:true,
					buttons:{ 
						"确定":function(){
							if(!lzcx.validateDataRefund()){
								return;
							}
							var params = $("#lzcx_tk_form"). serializeJson();
							$( this ).dialog( "destroy" );
							dataModoule.createRefundDebit(params,function(res){	
								 comm.alert({imgClass: 'tips_yes' ,content: "临账退款申请成功",callOk:function(){
									 $('#tk_back').click();
								 }});
							});		
						},
						"取消":function(){
							 $( this ).dialog( "destroy" );
						}
					}
				});
				/*end*/	
					
			});
		},
		/**
		 * 临账修改-表单校验
		 */
		validateData : function(){
			var valida = $("#lzxg_form").validate({ 
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
					}/*,
					useEntCustName : {
						rangelength:[11,11]
					}*/
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
					}/*,
					useEntCustName : {
						rangelength :  comm.lang("debit")[35038]
					}*/
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
		 * 退款-表单校验
		 */
		validateDataRefund : function(){
			return comm.valid({
				formID : '#lzcx_tk_form',
				rules : {
					debitId : {
						required : true
					},
					refundAmounts : {
						required : true
					},
					reqRemark : {
						required : true
					}
				},
				messages : {
					debitId : {
						required : comm.lang("debit")[35020]
					},
					refundAmounts : {
						required : comm.lang("debit")[35029]
					},
					reqRemark : {
						required :  comm.lang("debit")[35027]
					}
				}
			});
		},
		/** 返回分页查询页 */
		returnPageQuery : function (){
			$("#lzcx_operation_tpl").html("");
			$("#lzcx_operation_tpl").addClass('none');
			$("#lzcxTpl").removeClass('none') ;	
			$("#lzcx").siblings().addClass('tabNone');
			$("#fhxxDiv,#tkDiv").addClass('none');
			
			//重新加载数据
			lzcx.lzcxBsGrid.refreshPage();
		},
		/**
		 * 查看新版
		 */
		newChaKan : function(obj){
			$("#lzcxTpl").addClass('none');
			$("#lzcx_operation_tpl").removeClass('none');
			comm.liActive_add($('#ckxq'));	
			
			//临帐详情
			var query = $.extend(obj,{});
			if(!query.payUse){
				query.payUseString="";
			}else{
				var payUses=query.payUse.split(',');
				var payUseString=new Array();
				for(var i=0;i<payUses.length;i++)
				{
					payUseString.push(comm.lang("debit").payUse[payUses[i]]);
				}
				query.payUseString=payUseString.join("/");
			}
			query.payType=comm.lang("debit").payType[query.payType];
			
			$('#lzcx_operation_tpl').html(_.template(lzcx_ckTpl, query));
			
			$("#lr_spjg_txt").html(comm.getNameByEnumId(query.debitStatus, comm.lang("debit").debitStatus));
			
			//初始化小图片
			if(query.fileId){
				$("#img").attr("src", comm.getFsServerUrl(query.fileId));
				comm.initPicPreview("#img", query.fileId, "");
			}
			
			//一次性查询临帐关联信息 
			dataModoule.queryDebitLinkInfo({"debitId":query.debitId},function(rsp){
				var info = rsp.data;
				
				//关联订单
				comm.getEasyBsGrid("tab_cognateOrder", info.cognateOrder, lzcx.chaKan_guanlian_detail);
				
				//关联订单审批信息
				comm.getEasyBsGrid("tab_orderApprInfo", info.apprInfo, 
						function(record, rowIndex, colIndex, options){
							if(colIndex==1){return comm.formatMoneyNumber(record["linkAmount"])}
							if(colIndex==2){return comm.lang("debit").apprStatus[record["status"]]}
							if(colIndex==6){return comm.formatMoneyNumber(record["orderCashAmount"])}
						});
				
				//待关联订单
				comm.getEasyBsGrid("tab_pendingOrder", info.pendingOrder,lzcx.chaKan_guanlian_detail);
				
			});
			
			if(query.debitStatus == 4 || query.debitStatus == 0){
				$('#lzsyqk_div, #tksqxx_div, #glddxx_div, #zbdkxx_div, #lzglsp_div, #tksqsp_div,#glddspxx_div,#dspglddxx_div,#dglckxq_div').addClass('none');
				$('#lzlrsp_div')[query.debitStatus == 4 ? 'removeClass' : 'addClass']('none');
			}
			else{
				if(query.debitStatus == 2){
					$('#dspglddxx_div').removeClass('none');
				}
			}
			
			//返回到查询页面
			$('#ck1_back').click(function(){
				lzcx.returnPageQuery();
			});

		}
	} 
});