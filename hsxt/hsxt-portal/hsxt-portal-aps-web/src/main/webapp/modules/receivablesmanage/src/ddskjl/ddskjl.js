define(["text!receivablesmanageTpl/ddskjl/ddskjl.html",
		"text!receivablesmanageTpl/ddskjl/ddskjl_ck.html",
		"receivablesmanageDat/receivablesmanage",
		"receivablesmanageLan"
		],function(ddskjlTpl, ddskjl_ckTpl,receivablesmanage){
	var ddskjlFun = {
		showPage : function(){
			$('#busibox').html(_.template(ddskjlTpl));	
			    
		    comm.initBeginEndTime('#search_payStartTime','#search_payEndTime');					// 时间控件		
			comm.initSelect("#transaction_type",comm.lang("receivablesmanage").OrderType);	// 订单类型
			comm.initSelect("#payment_method",comm.lang("receivablesmanage").PayChannel);	// 支付方式
			
			// 导出
			$('#export_btn').click(function(e){
				if(!ddskjlFun.queryDateVaild().form()){return;}
				var parm=ddskjlFun.pageQueryParam();
				window.open(receivablesmanage.exportBusinessOrder(parm),'_blank') 
			});
		
			// 查询事件
			$("#btnQuery").click(function(){
				if(!ddskjlFun.queryDateVaild().form()){
					return;
				}
				ddskjlFun.pageQuery();
			});
		},
		chaKan : function(orderNo){
			//查找订单详情
			var getParam={"orderNo":orderNo};
			receivablesmanage.getOrderDetail(getParam,function(rsp){
				var orderDetail = rsp.data;
				if(comm.isEmpty(orderDetail.exchangeRate)&&orderDetail.currencyCode=='CNY'){
					orderDetail.exchangeRate= 1 ;
				}
				$('#ddskjl_detail').html(_.template(ddskjl_ckTpl,orderDetail));	
				$('#ddskjl_detail').removeClass('none');
				$('#ddskjl_ckTpl').removeClass('none');
				$('#ddskjlTpl').addClass('none');
				$('#busibox').addClass('none');
				comm.liActive_add($('#cknr'));
				
				//支付信息 
				ddskjlFun.payInfo(rsp.data);
				//返回按钮
				$('#back_ddskjl').click(function(){
					//隐藏头部菜单
					$('#ddskjl_detail').addClass('none');
					$('#ddskjl_ckTpl').addClass('none');
					$('#ddskjlTpl').removeClass('none');
					$('#busibox').removeClass('none');
					$('#cknr').addClass("tabNone").find('a').removeClass('active');
					$('#ddskjl').find('a').addClass('active');
				});
			});
		},
		/*chaKan_2 : function(orderRemark){
			comm.alert(orderRemark);
		},*/
		/** 分页查询参数 */
		pageQueryParam:function(){
			return {
					"search_hsResNo":$("#txtHsResNo").val(),"search_payStartTime":$("#search_payStartTime").val(),
					"search_payEndTime":$("#search_payEndTime").val(),"search_orderType":$("#transaction_type").attr("optionvalue"),
					"search_payChannel":$("#payment_method").attr("optionvalue"),"search_payStatus":2,
					"search_custNameCondition":$("#txtCustName").val()
					};
		},
		/** 分页查询 */
		pageQuery:function(){
			//查询参数
			var queryParam=ddskjlFun.pageQueryParam();
			var gridObj= receivablesmanage.businessOrderPage("searchTable",queryParam,function(record, rowIndex, colIndex, options){
				
				//交易类型
				if(colIndex==4){
                    return comm.lang("receivablesmanage").OrderType[gridObj.getColumnValue(rowIndex,"orderType")];
                }
				
				//订单金额，根据订单支付方式判断
				if(colIndex==5){
                    if(record.payChannel==200){
                    	return comm.formatMoneyNumber(record.orderHsbAmount);
                    }else {
                    	return comm.formatMoneyNumber(record.orderCashAmount);
                    }
                }
				
				//折合应付本币(人民币)
				if(colIndex==6){
					if(comm.isEmpty(record.exchangeRate)&&record.currencyCode=='CNY'){
						record.exchangeRate= 1 ;
					}
                    if(record.payChannel==200){
                    	return comm.formatMoneyNumber(record.orderHsbAmount);
                    }else {
                    	return comm.formatMoneyNumber(record.orderCashAmount);
                    }
                }
				
				//订单支付方式
				if(colIndex==8){
                    return comm.lang("receivablesmanage").PayChannel[gridObj.getColumnValue(rowIndex,"payChannel")];
                }
				
				//查看
				if(colIndex==10){
					var link1 = $('<a>查看</a>').click(function(e) {
						ddskjlFun.chaKan(gridObj.getColumnValue(rowIndex,"orderNo"));
					});
					return link1;
				}
			});
		},
		/** 支付信息记录 */
		payInfo:function(obj){
			var payInfo= [];	
			
			//转账汇款
			if(obj.payChannel==400){
				//获取转账汇款信息
				receivablesmanage.getTempAcctPayInfo({"orderNo":obj.orderNo},function(rsp){
					var order=rsp.data;
					
					if(order!=null && order.linkDebits!=null){
						$.each(order.linkDebits,function(k,v){
							payInfo.push({
								"payChannel":comm.lang("receivablesmanage").PayChannel[obj.payChannel],
								"bankName":v.debit.payBankName,
								"payAmount":comm.formatMoneyNumber(v.linkAmount),
								"bankTransNo":"-",
								"payTime":v.debit.accountReceiveTime,
								"payStatus":comm.lang("receivablesmanage").PayStatus[order.payStatus]/*,
								"remark":v.remark*/
							});	
						});
					}
					ddskjlFun.showPayInfo(payInfo);
				});
			}else{
				var payAmount;//订单金额
				if(obj.payChannel==200){
					payAmount=comm.formatMoneyNumber(obj.orderHsbAmount);
				}else {
					payAmount=comm.formatMoneyNumber(obj.orderCashAmount);
				}
				
				/*表格数据模拟*/
				payInfo.push({
					"payChannel":comm.lang("receivablesmanage").PayChannel[obj.payChannel],
					"bankName":"-",
					"payAmount":payAmount,
					"bankTransNo":obj.bankTransNo,
					"payTime":obj.payTime,
					"payStatus":comm.lang("receivablesmanage").PayStatus[obj.payStatus]/*,
					"remark":obj.orderRemark*/
				});	
				
				ddskjlFun.showPayInfo(payInfo);
			}
		},
		//展示支付信息
		showPayInfo:function(payDat){
			var isAutoLoad=false;
			if(payDat.length>0){
				isAutoLoad=true;
			}
			
			$.fn.bsgrid.init('searchTable_2', {				 
				pageSizeSelect: true ,
				pageSize: 10 ,
				stripeRows: true,  //行色彩分隔 
				displayBlankRows: false ,   //显示空白行
				localData:payDat ,
				autoLoad:isAutoLoad,
				operate : {	
					detail : function(record, rowIndex, colIndex, options){
						if(colIndex==4){
							return comm.formatDate(record.payTime,'yyyy-MM-dd');
						}
						/*if(colIndex==6){
							if(comm.isEmpty(record.remark)){
								return '';
							}
							var link1 = $('<a>查看</a>').click(function(e) {
								self.chaKan_2(record.remark);
							}.bind(self)) ;
							return link1 ;
						}*/
					}
				}
			});
		},
		/**
		 * 验证日期的查询(开始日期、结束日期)
		 */
		queryDateVaild : function(){
			return $("#search_form").validate({
				rules : {
					search_payStartTime : {
						required : true,
						endDate : "#search_payStartTime",
						oneyear : "#search_payEndTime"
					},
					search_payEndTime : {
						required : true
					}
				},
				messages : {
					search_payStartTime : {
						required : comm.lang("common")[10001],
						endDate : comm.lang("common")[10003],
						oneyear : comm.lang("common")[10004]
					},
					search_payEndTime : {
						required : comm.lang("common")[10002]
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
		}
	} 
	
	return ddskjlFun;
});