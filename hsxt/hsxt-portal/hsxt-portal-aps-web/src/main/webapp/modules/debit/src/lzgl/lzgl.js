define(["text!debitTpl/lzgl/lzgl.html",
		"text!debitTpl/lzgl/ck_dialog.html",
		"text!debitTpl/lzgl/lzgl_gl.html",
		"text!debitTpl/lzgl/qrgl_dialog.html",
		"text!debitTpl/lzgl/cklzxd_dialog.html",
		'debitDat/debit',
		'debitLan'],function(lzglTpl, ck_dialogTpl, lzgl_glTpl, qrgl_dialogTpl, cklzxd_dialogTpl,dataModoule){
	var lzglFun = {
		cognateDebitGrid:null,	//关联临帐列表
		showPage : function(){
			$('#busibox').html(_.template(lzglTpl));//.append(lzgl_glTpl);
			
			//初始化下拉选择    
		    comm.initBeginEndTime('#search_startDate','#search_endDate');
			comm.initSelect('#orderType', comm.lang("debit").orderType);
			
			//临战关联分页查询
			$("#lzglquery").click(function(){
				if(!comm.queryDateVaild("search_form").form()){
					return;
				}
				var params={
						search_startDate:$("#search_startDate").val(),
						search_endDate:$("#search_endDate").val(),
						search_orderType:$("#orderType").attr("optionvalue"),
						search_hsResNo:$("#hsResNo").val(),
						search_companyName:$("#companyName").val()
				};
				dataModoule.listNopayOrder("searchTable",params,lzglFun.grid,lzglFun.edit);
			});				
		},
		grid : function(record, rowIndex, colIndex, options){
			if(colIndex == 4){
				return comm.lang("debit").orderType[record.orderType];
			}else if(colIndex == 5){
				return comm.formatMoneyNumber(record.orderOriginalAmount-record.orderDerateAmount);
			}else if(colIndex == 6){
				return comm.formatMoneyNumber(record.orderCashAmount);
			}else if(colIndex == 8){
				var link1 = $('<a>查看</a>').click(function(e) {
						dataModoule.invoiceGetOrderByOrderId({orderNo:record.orderNo},function(res){
							lzglFun.chaKan(res.data);
						});					
				    });
				return link1;
			}
			
		},		
		edit : function(record, rowIndex, colIndex, options){
			 if(colIndex == 8){
				var link2 = $('<a>关联</a>').click(function(e) {
					lzglFun.guanLian(record);
				}) ;
				return link2;
			 }
		},
		chaKan : function(data){
			var obj=data.order;
			obj.orderTypeName=comm.lang("debit").orderType[obj.orderType];
			obj.orderStatusName=comm.lang("debit").orderStatus[obj.orderStatus];
			obj.orderAmount=obj.orderOriginalAmount-obj.orderDerateAmount;
			obj.orderChannelName=comm.lang("debit").orderChannel[obj.orderChannel];
			obj.payChannelName=comm.lang("debit").payChannel[obj.payChannel];
			areaYear=comm.getNextYear(obj.orderTime);
			obj.area=areaYear[0]+' ~ '+areaYear[1];
			
			//金额格式化
			obj.orderAmount = comm.formatMoneyNumber(obj.orderAmount) || '0.00';
			obj.orderCashAmount = comm.formatMoneyNumber(obj.orderCashAmount) || '0.00';
			obj.orderHsbAmount = comm.formatMoneyNumber(obj.orderHsbAmount) || '0.00';
			obj.orderOriginalAmount = comm.formatMoneyNumber(obj.orderOriginalAmount) || '0.00';
			
			
			/*comm.groupOperInfo(obj.orderOperator,function(res){
				obj.operNo=res;
			});*/
			var deliveryInfor = data.deliverInfo;
			if(deliveryInfor!=null){
				obj.linkman=deliveryInfor.linkman;
				obj.fullAddr=deliveryInfor.fullAddr;
				obj.mobile=deliveryInfor.mobile;
			}
			$('#dialogBox').html(_.template(ck_dialogTpl, obj));	
			var json_ck = data.confs;
			if(json_ck!=null && json_ck.length!=0){
				var gridObj_ck = $.fn.bsgrid.init('confs_table', {				 
					pageSizeSelect: true ,
					pageSize: 10 ,
					stripeRows: true,  //行色彩分隔 
					displayBlankRows: false ,   //显示空白行
					localData:json_ck,
					operate : {	
						detail : function(record, rowIndex, colIndex, options){
							if(colIndex == 0){
								return comm.lang("debit").categoryCode[record.categoryCode];	
							}
							else if (colIndex == 1)
							{//金额格式
								return comm.formatMoneyNumber(record.price) || '0.00';
							}
													
						}
					}
				});
			}
			/*弹出框*/
			$( "#dialogBox" ).dialog({
				title:"查看详情",
				width:"850",
				modal:true,
				buttons:{ 
					"确定":function(){
						$( this ).dialog( "destroy" );
					},
					"取消":function(){
						 $( this ).dialog( "destroy" );
					}
				}
			});
			/*end*/	
		},
		guanLian : function(obj){
			$('#lzgl_operation_tpl').html(_.template(lzgl_glTpl));
			$("#lzglTpl").addClass('none');
			$("#lzgl_operation_tpl").removeClass('none');	  
			
			comm.liActive_add($('#gl'));
			$('#qrglDiv').removeClass('none');
			
			//返回
			$('#gl_back').click(function(){
				$("#lzgl_operation_tpl").addClass('none').html("");
				$("#lzglTpl").removeClass('none') ;	
				$("#gl").addClass('tabNone');
			});

			obj.orderTypeName=comm.lang("debit").orderType[obj.orderType];
			obj.orderStatusName=comm.lang("debit").orderStatus[obj.orderStatus];
			
			/*表格数据模拟*/
			var json_1 = [obj];	
			$.fn.bsgrid.init('searchTable_1', {				 
				pageSizeSelect: true ,
				pageSize: 10 ,
				stripeRows: true,  //行色彩分隔 
				displayBlankRows: false ,   //显示空白行
				localData:json_1,
				operate : {	
					detail : function(record, rowIndex, colIndex, options){	
						if(colIndex==5){
						  return comm.formatMoneyNumberAps(record.orderHsbAmount);	
						}else if(colIndex==6){
							return comm.formatMoneyNumberAps(record.orderCashAmount);	
						}
					}
				}
			});
			
			var params={
					search_debitStatus:"1",
					search_useEntCustName : obj.hsResNo
			}
             //显示待关联的临账信息
			lzglFun.cognateDebitGrid=dataModoule.queryDebitListByQuery("searchTable_2",params, function(record, rowIndex, colIndex, options){
				if(colIndex == 2){
					return comm.formatMoneyNumberAps(record.payAmount);
				}else if(colIndex == 4){
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
				}else if(colIndex==6){
					return comm.formatMoneyNumberAps(record.unlinkAmount);
				}else if(colIndex == 7){
					  var link1 = $('<input type="text" id="linkAmount'+rowIndex+'" name="linkAmount" value="0" class="h28_middle w100 input-txt"/>').blur(function(e) {		
					            	//临帐金额统计
					            	lzglFun.cognateMoneyCount(rowIndex,obj.orderCashAmount);
				                }.bind(this));			
				     return link1;		
				}else if(colIndex == 8){
					var link1 = $('<a>查看临账详单</a>').click(function(e) {
						lzglFun.cklzxd(record);	
					}) ;				
					return link1;			
				}						
			});	
			
			//清除错误提示样式
	/*		$("input[name='linkAmount']").live("focus",function(){
				$(this).tooltip("destroy");
			});
			  */
			
			//确认关联			
			$('#qrgl_btn').unbind().bind("click",function(){
				var debitIds=new Array();
				var useEntCustNames=new Array();
				var linkAmount=new Array();
				var totalLinkAmount=0;
				var tempval=$("input[name='linkAmount']");
				
				//多笔临帐关联订单时，只允许有一笔临帐有剩余
				var unlinkAmountCount=0;//未关联金额大于0的条数
				$.each(tempval,function(i,val){
					var record = lzglFun.cognateDebitGrid.getRecord(i);
					if(record){
						var unlinkAmount=record.unlinkAmount;
						if(unlinkAmount==null){
							unlinkAmount=0;
						}
						
						//有效金额验证
						var amountReg = new RegExp("^[0-9]+\.{0,1}[0-9]{0,2}$"); 
						if(!amountReg.test(val.value)){
							 comm.error_alert("请输入正确的金额格式",400);
							 return;
						}
						if(parseFloat(val.value)>parseFloat(unlinkAmount)){
							 comm.error_alert("关联金额必须小于未关联金额",400);
							 return;
						}
						
						if(parseFloat(val.value)>0){
							if(parseFloat(unlinkAmount)-parseFloat(val.value)>0){
								unlinkAmountCount++;
							}
							linkAmount.push(val.value);
						    debitIds.push(record.debitId);
						    useEntCustNames.push(record.useEntCustName);
						    totalLinkAmount = parseFloat(totalLinkAmount)+parseFloat(val.value);
						}
					}
				  });
				 if(linkAmount==null || totalLinkAmount==null){
					 comm.error_alert("请至少选择一个临账进行关联",400);
					 return;
				 }else  if(obj.orderCashAmount!=totalLinkAmount){
					 comm.error_alert("关联金额不对，请重新填写",400);
					 return;
				 }else if(unlinkAmountCount>1){
					 comm.error_alert("多笔临帐关联订单时，只允许有一笔临帐有剩余",400);
					 return;
				 }
				 obj.totalLinkAmount=totalLinkAmount;
				 obj.useEntCustNames=useEntCustNames;
				 obj.debitIds=debitIds.join(',');		
			     obj.linkAmount=linkAmount;
				$('#dialogBox_cr').html(_.template(qrgl_dialogTpl,obj));
				/*弹出框*/
				$( "#dialogBox_cr" ).dialog({
					title:"确认关联",
					width:"630",
					height:'290',
					modal:true,
					buttons:{ 
						"确定":function(){	
							if(!lzglFun.validateDataLink()){
								return;
							}
							var params=$("#lzgl_glform"). serializeJson();
							$( this ).dialog( "destroy" );
							dataModoule.createDebitLink(params,function(res){
								 comm.yes_alert("临账关联申请成功",400);
								$('#gl_back').click();
							});		
						},
						"取消":function(){
							 $( this ).dialog( "destroy" );
						}
					}
				});
			});
		},
		/**
		 * 关联金额计算
		 */
		cognateMoneyCount:function(rowNum,orderAmount){
			var amnoutSum=0;	//临帐金额综合
			var debitNum=0;		//关联临帐数量
			var debitAmount;	//临帐金额
			
			//获取关联临帐金额
			var $linkAmount = $("input[name='linkAmount']");
			
			//清除错误样式
			$.each($linkAmount,function(i,v){
				$("#linkAmount"+i).tooltip();
				$("#linkAmount"+i).tooltip("destroy");
			});
			
			$.each($linkAmount,function(i,v){
				var inputAmount=v.value;
				
				//为金额时候，进行累加
				var amountReg = new RegExp("^[0-9]+\.{0,1}[0-9]{0,2}$"); 
				if(amountReg.test(inputAmount)){
					amnoutSum+=parseFloat(inputAmount);
				}else{
					 v.value="0";
					 $("#linkAmount"+i).attr("title","请输入正确的金额格式").tooltip({position:{ my:"left+100 top+5", at:"left top"}}).tooltip("open");
					 $(".ui-tooltip").css("max-width","230px");
					 return;
				}
				
				//关联金额与临帐金额比较
				debitAmount=lzglFun.cognateDebitGrid.getRecord(i).unlinkAmount;
				
				//临帐剩余金额=临帐金额-关联金额
				var debitSurplus=debitAmount-inputAmount;	
				if(debitSurplus>0 && v.value!="0"){
					debitNum++;
				}else if(debitSurplus<0){
					 $("#linkAmount"+i).attr("title","关联金额必须小于未关联金额").tooltip({position:{ my:"left+100 top+5", at:"left top"}}).tooltip("open");
					 $(".ui-tooltip").css("max-width","230px");
					 return;
				}
			});
			
			//关联金额大于订单金额
			if(amnoutSum>orderAmount){
				 $("#linkAmount"+rowNum).attr("title","关联金额不能大于订单金额，请重新填写").tooltip({position:{ my:"left+100 top+5", at:"left top"}}).tooltip("open");
				 $(".ui-tooltip").css("max-width","230px");
				 return;
			 }
			
			//多笔临帐关联订单时，只允许有一笔临帐有剩余
			if(debitNum>1){
				 $("#linkAmount"+rowNum).attr("title","多笔临帐关联订单时，只允许有一笔临帐有剩余").tooltip({position:{ my:"left+100 top+5", at:"left top"}}).tooltip("open");
				 $(".ui-tooltip").css("max-width","230px");
				 return;
			}
			 //展示关联总金额
			$("#totalLinkAmount").text(amnoutSum);
		},
		cklzxd : function(obj){
			if(!obj.payUse){
				obj.payUserString="";
			}else{
				var payUses=obj.payUse.split(',');
				var payUseString=new Array();
				for(var i=0;i<payUses.length;i++)
				{
					payUseString.push(comm.lang("debit").payUse[payUses[i]]);
				}
				obj.payUserString=payUseString.join("/");
			}
			obj.payTypeName=comm.lang("debit").payType[obj.payType];
			
			$('#dialogBox_ck').html(_.template(cklzxd_dialogTpl, obj));
			if(obj.fileId){
				$("#img").attr("src", comm.getFsServerUrl(obj.fileId));
				comm.initPicPreview("#img", obj.fileId, "");
			}
			
			/*弹出框*/
			$( "#dialogBox_ck" ).dialog({
				title:"查看明细(临时款项)",
				width:"820",
				modal:true,
				buttons:{ 
					"确定":function(){
						$( this ).dialog( "destroy" );
					},
					"取消":function(){
						 $( this ).dialog( "destroy" );
					}
				}
			});
			/*end*/	
		},
		/**
		 * 临账关联-表单校验
		 */
		validateDataLink : function(){
			return comm.valid({
				formID : '#lzgl_glform',
				rules : {
					debitIds : {
						required : true
					},
					reqRemark : {
						required : true
					},
					orderNo : {
						required : true
					},
					cashAmount : {
						required : true
					},
					totalLinkAmount : {
						required : true
					}
				},
				messages : {
					debitIds : {
						required : comm.lang("debit")[35020]
					},
					reqRemark : {
						required : comm.lang("debit")[35027]
					},
					orderNo : {
						required :  comm.lang("debit")[35024]
					},
					cashAmount : {
						required :  comm.lang("debit")[35026]
					},
					totalLinkAmount : {
						required :  comm.lang("debit")[35025]
					}
				}
			});
		},
		/**
		 * 待关联临账关联-表单校验
		 */
		validateDataLinkInfo : function(){
			return comm.valid({
				formID : '#gllz_form',
				rules : {
					debitIds : {
						required : true
					}
				},
				messages : {
					debitIds : {
						required : comm.lang("debit")[35020]
					}
				}
			});
		}
	} 
	
	return lzglFun;
});