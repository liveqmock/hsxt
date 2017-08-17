define(['text!receivablesmanageTpl/wyskdz/webzfwyskdz.html',
        'text!receivablesmanageTpl/wyskdz/webzfwyskdz_ck.html',
        'text!receivablesmanageTpl/wyskdz/webzfwyskdz_dailog.html',
        "receivablesmanageDat/receivablesmanage",
        "receivablesmanageLan"], 
        function(webzfwyskdzTpl, webzfwyskdz_ckTpl, webzfwyskdz_dailog, receivablesmanage){
	return {
		webzfwyskdz_self : this,
		webzfwyskdz_table : null,
		showPage : function(){
			webzfwyskdz_self = this;
			this.initForm();
		},
		/**
		 * 初始化界面
		 */
		initForm : function(){
			$('#busibox').html(_.template(webzfwyskdzTpl));
			
			//时间控件		    
		    comm.initBeginEndTime('#search_startDate','#search_endDate');
			
			/** 订单类型 */
			comm.initSelect("#webTransaction_type",comm.lang("receivablesmanage").OrderType, null, null, {name:'全部', value:''});
			
			/** 查询事件 */
			$("#sjdz_qry_btn").click(function(){
				webzfwyskdz_self.initData();
			});
			
			$('#sjdz_btn').click(function(){
				var records = webzfwyskdz_table.getCheckedRowsRecords();
				if(records.length == 0){
					comm.warn_alert("请至少选择一条数据进行对账.");
					return;
				}
				//获取需要对账订单号
				var orders = new Array();
				for(var key in records){
					if(comm.isNotEmpty(records[key].orderNo)){
						orders.push(records[key].orderNo);
					}
				}
				//开始对账
				receivablesmanage.dataReconciliation({orderNos:JSON.stringify(orders)}, function(res){
					var obj = {count:0, records:null};
					if(res.data){
						var successRecords = res.data;
						for(var key in successRecords){
							//更改对账成功的数据值
							if(key<successRecords.length){
								/*successRecords[key].orderType = comm.getNameByEnumId(successRecords[key]['orderType'], comm.lang("receivablesmanage").OrderType);
								successRecords[key].payStatusText = comm.getNameByEnumId(successRecords[key]['payStatus'], comm.lang("receivablesmanage").paymentStatus);*/
								successRecords[key].payStatus=1;
							}
							
							/** 添加对账失败的记录 */
							if(successRecords.length>0){
								for(var r=0; r<records.length; r++){
									if(records[r].orderNo==successRecords[key].orderNo){
										 records.splice(r,1)
										 break;
									 }
								}
							}
							/** 添加对账失败的记录 */
						}
						
						obj.count=successRecords.length;
						var newRecord=$.merge(successRecords,records);
						obj.records=newRecord;
					}
					$('#sjdz_content').html(_.template(webzfwyskdz_dailog, obj));
					$("#sjdz_content").dialog({
						title:"数据对账",
						modal:true,
						width:"1160",
						buttons:{ 
							"返回":function(){
								$("#sjdz_content").dialog("destroy");
								$("#sjdz_qry_btn").click();
							}/*,
							"取消":function(){
								$("#sjdz_content").dialog("destroy");
							}*/
						}
					  });
				});
			});
		},
		/**
		 * 初始化数据
		 */
		initData : function(){
			if(!comm.queryDateVaild("search_form").form()){
				return;
			}
			var queryParam = {
					"search_hsResNo":$("#txtHsResNo").val(),
					"search_startDate":$("#search_startDate").val(),
					"search_endDate":$("#search_endDate").val(),
					"search_orderType":$("#webTransaction_type").attr("optionvalue"),
					"search_payChannels":"100,102,103",
					"search_orderChannel":1,
					"search_unPayStatus":"0,1",
					"search_custNameCondition":$("#txtCustName").val()
				};
			webzfwyskdz_table = receivablesmanage.findPaymentOrderList(queryParam, webzfwyskdz_self.detail);
		},
		/**
		 * 详情
		 */
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex == 3){
                return comm.navNull(record['custName']);
            }
			if(colIndex == 6){
                return comm.lang("receivablesmanage").OrderType[record.orderType];
            }else if(colIndex == 7){
                if(record.payChannel == 200){
                	return comm.formatMoneyNumber(record.orderHsbAmount);
                }else {
                	return comm.formatMoneyNumber(record.orderCashAmount);
                }
            }
			return $('<a>查看</a>').click(function(e) {
            	webzfwyskdz_self.chaKan(record);
			}.bind(this));
		},
		/**
		 * 查看动作
		 */
		chaKan : function(record){
			receivablesmanage.findPaymentOrderInfo({orderNo:record.orderNo}, function(res){
				cacheUtil.findCurrencyByCode(comm.removeNull(record.currencyCode), function(sysRes){
					var obj = res.data;
					if(obj.payChannel == 200){
						obj.orderCashAmount = comm.formatMoneyNumber(obj.orderHsbAmount);
					}else {
						obj.orderCashAmount = comm.formatMoneyNumber(obj.orderCashAmount);
					}
					if(sysRes){
						obj.currencyTextName = sysRes.currencyNameCn;
					}
					obj.orderTypeName = comm.getNameByEnumId(obj['orderType'], comm.lang("receivablesmanage").OrderType);
					obj.payChannelText = comm.getNameByEnumId(obj['payChannel'], comm.lang("receivablesmanage").PayChannel);
					obj.payStatusText = comm.getNameByEnumId(obj['payStatus'], comm.lang("receivablesmanage").paymentStatus);
					$('#busibox_ck').html(_.template(webzfwyskdz_ckTpl, obj));		
					$('#webzfwyskdz_ckTpl').removeClass('none');
					$('#busibox').addClass('none');
					comm.liActive_add($('#cknr'));
					/*$('#webzfwyskdzTpl').addClass('none');
					$('#webzfwyskdz_ckTpl_div').removeClass('none');			
					$('#back_webzfwyskdz').triggerWith('#webzfwyskdz');*/
					//返回按钮
					$('#back_webzfwyskdz').click(function(){
						//隐藏头部菜单
						$('#webzfwyskdz_ckTpl').addClass('none');
						$('#busibox').removeClass('none');
						$('#cknr').addClass("tabNone").find('a').removeClass('active');
						$('#webzfwyskdz').find('a').addClass('active');
					});
					//end
				});
			});
		}
	}	
});