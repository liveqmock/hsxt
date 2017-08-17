define(['text!systemBusinessTpl/toolManage/queryToolOrder/queryToolOrder.html',
		'text!systemBusinessTpl/toolManage/queryToolOrder/toolOrderDetail.html',
		'text!systemBusinessTpl/toolManage/queryToolOrder/uploadConfirm.html',
		'text!systemBusinessTpl/toolManage/serviceToolBuy/payment.html',
		'systemBusinessDat/systemBusiness',
], function(queryToolOrderTpl, toolOrderDetailTpl,uploadConfirmTpl, paymentTpl,systemBusinessAjax){
//表格对象
var gridObj = null;
var self = {
	showPage : function(){
		$('#busibox').html(_.template(queryToolOrderTpl)).append(paymentTpl);	
		/*日期控件*/
		comm.initBeginEndTime("#search_beginDate",'#search_endDate');
		//查询
		$("#btn_search").click(function(){
			if(!comm.queryDateVaild('tool_order_form').form()){
				return;
			}
			self.loadGrid();
		});
		
		/*下拉列表*/
		comm.initSelect('#channel',comm.lang("systemBusiness").toolManage_payChannel);
		comm.initSelect('#orderStaus',comm.lang("systemBusiness").toolManage_orderStatus);	
	},
	//加载表格
	loadGrid:function(){
		var params = {
				search_beginDate : $("#search_beginDate").val(), //开始日期
				search_endDate : $("#search_endDate").val(),//结束日期
				search_orderNo : comm.removeNull($("#orderNo").val()),//订单号
				search_channel : comm.removeNull($("#channel").attr("optionvalue")),//支付方式
				search_status : comm.removeNull($("#orderStaus").attr("optionvalue"))//订单状态
		};
		gridObj = comm.getCommBsGrid("queryToolOrder_table","tool_order_list",params,this.detail,this.cancelOrder,this.toPay,this.uploadConfirm);
	},
	//撤单
	cancelOrder : function(record, rowIndex, colIndex, options){
		if(colIndex == 6 && (record.orderStatus==6||record.orderStatus==1) && record.orderType != '104'){
			var title = '';
			var msg = '';
			if(record.orderStatus==6){
				title = comm.lang("systemBusiness").toolManage_queryOrderOpt['cancelOrder'];
				msg = comm.lang("systemBusiness").toolManage_toolOrderCancelSucc;
			}else{
				title = comm.lang("systemBusiness").toolManage_queryOrderOpt['closeOrder'];
				msg = comm.lang("systemBusiness").toolManage_toolOrderCloseSucc;
			}
			var link = $('<a>'+title+'</a>').click(function(e) {
				comm.i_confirm('确定'+title+'号为：' + record.orderNo + '的订单？',function(){
					systemBusinessAjax.toolOrderCancel({orderNo:record.orderNo},function(resp){
						if(resp){
							comm.yes_alert(msg,null,function(){
								if(gridObj){
									gridObj.refreshPage();
								}
							});
						}
					});
				},'550');
			}.bind(this));
			return link;
		}
	},
	//查看订单详情
	detail : function(record, rowIndex, colIndex, options){
		if(colIndex == 2){
			return comm.lang("systemBusiness").toolManage_orderType[record.orderType];
		}else if(colIndex == 3){
			if(record.orderType=='109'){
				return '----';
			}
			return comm.formatMoneyNumber(record.orderAmount);
		}else if(colIndex == 4){
			return comm.lang("systemBusiness").toolManage_payChannel[record.payChannel];
		}else if(colIndex == 5){
			return record.orderStatus==8?comm.lang("systemBusiness").toolManage_waitPay:comm.lang("systemBusiness").toolManage_orderStatus[record.orderStatus];
		}else if(colIndex == 6){				
			var link = $('<a>'+comm.lang("systemBusiness").toolManage_queryOrderOpt['detail']+'</a>').click(function(e) {
				systemBusinessAjax.toolOrderDetail({orderNo:record.orderNo},function(resp){
					if(resp){
						var order = resp.data.order;
						var deliver = resp.data.deliverInfo;
						var confs = resp.data.confs;
						var amount = order.payChannel==200?order.orderHsbAmount:order.orderCashAmount;
						amount = (order.orderStatus=='1'||order.orderStatus=='4'||order.orderStatus=='5')?null:amount;
						var obj = {
							confs:resp.data.confs,
							orderNo:comm.navNull(order.orderNo),
							orderTime:comm.navNull(order.orderTime),
							orderTypeName:comm.navNull(comm.lang("systemBusiness").toolManage_orderType[order.orderType]),
							orderType:order.orderType,
							applyType:order.orderType=='109'?'申报申购':'新增申购',
							amount:order.orderType=='109'?'----':(comm.isNotEmpty(amount)?comm.formatMoneyNumber(amount):amount),
							currencyCode:comm.navNull(order.currencyCode),
							exchangeRate:order.orderType=='109'?'----':comm.formatMoneyNumber(comm.navNull(order.exchangeRate))+'00',
							orderCashAmount:order.orderType=='109'?'----':comm.formatMoneyNumber(comm.navNull(order.orderCashAmount)),
							payChannel:comm.navNull(comm.lang("systemBusiness").toolManage_payChannel[order.payChannel]),
							orderStatus:comm.navNull(comm.lang("systemBusiness").toolManage_orderStatus[order.orderStatus]),
							orderOperator:comm.navNull(order.orderOperator),
							orderChannel:comm.navNull(comm.lang("systemBusiness").toolManage_orderChannel[order.orderChannel]),
							remark:comm.navNull(order.orderRemark),
							linkman:comm.isNotEmpty(deliver)?comm.valToLongShow(comm.navNull(deliver.linkman),45):'',
							fullAddr:comm.isNotEmpty(deliver)?comm.valToLongShow(comm.navNull(deliver.fullAddr),45):''
						};
						$('#toolOrderDetail-dialog').html(_.template(toolOrderDetailTpl,{obj:obj}));
						$( "#toolOrderDetail-dialog" ).dialog({
							title:"订单详情",
							width:"850",
							modal:true,
							closeIcon : true,
							buttons:{ 
								"取消":function(){
									 $( this ).dialog( "destroy" );
								}
							}
						});
						//系统申报申购
						if(order.orderType=='109'){
							self.loadApply(confs,order.custType);
						}
						//新增订单
						if(order.orderType=='103'){
							self.loadAdd(confs);
						}
						//系统资源申购
						if(order.orderType=='110'){
							self.systemResApply(confs);
						}
						//售后
						if(order.orderType=='104'){
							self.loadAfter(resp.data.afterDetail);
						}
					}
				});
			}.bind(this)) ;
			return link;
		}									
	},
	//加载申报申购
	loadApply : function(confs,custType){
		if(comm.isNotEmpty(confs)){
			$('#toolDetails').removeClass('none');
			for(var i = 0 ; i < confs.length ; i++){
				var quantity = confs[i].quantity;
				if(confs[i].categoryCode == 'P_CARD'){
					quantity = quantity%1000==0 ? (quantity/1000) : (parseInt(quantity/1000) + 1);
				}
				var tr = $('<tr>'+
						'<td style="text-align: center;" class="lineNoWrap">'+comm.lang("systemBusiness").toolManage_categoryCode[confs[i].categoryCode]+'</td>'+
						'<td style="text-align: center;" class="lineNoWrap">----</td>'+
						'<td style="text-align: center;" class="lineNoWrap">'+quantity+'</td>'+
						'<tr/>');
				$('#confs_table_service').append(tr);
			}
			$('#confs_table_service').removeClass('none');
		}
	},
	//加载新增申购
	loadAdd : function(confs){
		if(comm.isNotEmpty(confs)){
			$('#toolDetails').removeClass('none');
			if(confs.length > 0){
				for(var i = 0 ; i < confs.length ; i++){
					var tr = $('<tr>'+
							'<td style="text-align: center;" class="lineNoWrap">'+comm.lang("systemBusiness").toolManage_categoryCode[confs[i].categoryCode]+'</td>'+
							'<td style="text-align: center;" class="lineNoWrap">'+comm.formatMoneyNumber(confs[i].price)+'</td>'+
							'<td style="text-align: center;" class="lineNoWrap">'+confs[i].quantity+'</td>'+
							'<tr/>');
					$('#confs_table_service').append(tr);
				}
				$('#confs_table_service').removeClass('none');
			}
		}
	},
	//加载售后清单
	loadAfter : function(afters){
		if(comm.isNotEmpty(afters)&&afters.length > 0){
			$('#toolDetails').removeClass('none');
			$('#confs_table_after').removeClass('none');
			comm.getEasyBsGrid('confs_table_after',afters,self.afgerDetail);
		}
	},
	//售后列表详情
	afgerDetail : function(record, rowIndex, colIndex, options){
		if(colIndex == 1){
			return comm.lang("systemBusiness").toolManage_categoryCode[record.categoryCode];
		}else if(colIndex == 3){
			return comm.lang("systemBusiness").toolManage_disposeStatus[record.disposeStatus];
		}else if(colIndex == 4){
			return comm.formatMoneyNumber(record.disposeAmount);
		}
	},
	//加载系统资源申购
	systemResApply : function(confs){
		if(comm.isNotEmpty(confs)){
			$('#toolDetails').removeClass('none');
			var conf=confs[0];
			var count = (conf.quantity%1000==0) ? conf.quantity/1000 : (parseInt(conf.quantity/1000) + 1 );
			for(var i = 0 ; i < count; i++){
				var tr = $('<tr>'+
						'<td style="text-align: center;" class="lineNoWrap">消费者系统资源段</td>'+
						'<td style="text-align: center;" class="lineNoWrap">20000.00</td>'+
						'<td style="text-align: center;" class="lineNoWrap">1</td>'+
				'<tr/>');
				$('#confs_table_card').append(tr);
			}
			$('#confs_table_card').removeClass('none');
		}
	},
	//上传确认文件
	uploadConfirm : function(record, rowIndex, colIndex, options){
		if(colIndex == 6 && $.cookie('entResType') == '3' && (record.orderType=='110'||record.orderType=='109') && record.orderStatus==2){
			var link = $('<a>'+comm.lang("systemBusiness").toolManage_queryOrderOpt['uploadConfirm']+'</a>').click(function(e) {
				systemBusinessAjax.getToolConfigByNo({orderNo:record.orderNo},function(resp){					
					$('#confirmFile-dialog').html(_.template(uploadConfirmTpl));
					$( "#confirmFile-dialog" ).dialog({
						title:"互生卡制作卡样确认文件",
						width:"500",
						modal:true,
						closeIcon : true,
						buttons:{ 
							"取消":function(){
								 $( this ).dialog( "destroy" );
							}
						}
					});
					var conf = resp.data;
					if(comm.isEmpty(conf.confirmFile)){
						$('#confirmFileUpLoad').removeClass('none');
					}else{
						$('#confirmFileDownDiv').removeClass('none');
						comm.initDownload("#confirmFileDown", {1000:{fileId:conf.confirmFile, docName:''}}, 1000);
					}
					$("#confirmFileSubmit").click(function(){
						self.confirmFildSubmit(conf.confNo);
					});
				});				
			}.bind(this));
			return link;
		}
	},
	//确认文件提交
	confirmFildSubmit : function(confNo){
		var confirmFile = $('#confirmFile').val();
		if(comm.isEmpty(confirmFile)){
			comm.error_alert(comm.lang("systemBusiness").toolManage_uploadConfirmFile);
			return;
		}
		self.uploadFile(function(resp){
			if(resp){
				var param = {
					confNo:confNo,
					confirmFile:resp.confirmFile
				};
				$('#confirmFileHid').val(resp.confirmFil);
				systemBusinessAjax.uploadCardMarkConfirmFile(param,function(resp){
					if(resp){
						comm.yes_alert(comm.lang("systemBusiness").toolManage_uploadConfirmFileSucc,400,function(){
							$('#confirmFileHid').val();
							$('#confirmFile-dialog').dialog( "destroy" );
						});
					}
				});
			}
		});
	},
	//去付款
	toPay : function(record, rowIndex, colIndex, options){
		if(colIndex == 6 && record.orderStatus==1){				
			var link = $('<a>'+comm.lang("systemBusiness").toolManage_queryOrderOpt['toPay']+'</a>').click(function(e){
				systemBusinessAjax.toolOrderDetail({orderNo:record.orderNo},function(resp){
					var order = resp.data.order;
					var obj = {
						paymentMethod : 'hsbPay',
						isFormat:'format',
						isFrist:'true',
						orderType:record.orderType,
						orderNo:comm.navNull(order.orderNo),
						payOverTime:comm.navNull(order.payOvertime),
						cashAmount:comm.navNull(order.orderCashAmount),
						hsbAmount:comm.navNull(order.orderHsbAmount),
						exchangeRate:comm.navNull(order.exchangeRate),
						currencyNameCn:comm.navNull(order.currencyCode)
					};	
					$('#queryToolOrder').addClass('none');
					$('#pay_div').removeClass('none');
					require(['systemBusinessSrc/toolManage/pay/pay'], function(pay){
						pay.showPage(obj);	
					});
				});
			}.bind(this)) ;
			return link;
		}	
	},
	//上传文件
	uploadFile : function(callBack){
		var confirmFileHid = $('#confirmFileHid').val();
		if(comm.isNotEmpty(confirmFileHid)){
			var resp = {
				confirmFile : confirmFileHid
			};
			callBack(resp);
		}else{
			var ids = ['confirmFile'];
			comm.uploadFile(null, ids, function(resp){
				if(resp){
					callBack(resp);
				}
			},function(err){});
		}
	}
};
return self;	
});
