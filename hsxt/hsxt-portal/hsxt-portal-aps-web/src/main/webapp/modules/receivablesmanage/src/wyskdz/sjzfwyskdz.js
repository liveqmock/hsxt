define(['text!receivablesmanageTpl/wyskdz/sjzfwyskdz.html',
        'text!receivablesmanageTpl/wyskdz/sjzfwyskdz_ck.html',
        'text!receivablesmanageTpl/wyskdz/sjzfwyskdz_dailog.html',
        "receivablesmanageDat/receivablesmanage",
        "receivablesmanageLan"],
		function(sjzfwyskdzTpl, sjzfwyskdz_ckTpl, sjzfwyskdz_dailog, receivablesmanage){
	return {
		sjzfwyskdz_self : this,
		sjzfwyskdz_table : null,
		showPage : function(){
			sjzfwyskdz_self = this;
			this.initForm();
		},
		/**
		 * 初始化界面
		 */
		initForm : function(){
			$('#busibox').html(_.template(sjzfwyskdzTpl));
			
			//时间控件		    
		    comm.initBeginEndTime('#search_startDate','#search_endDate');
			
			/** 订单类型 */
			comm.initSelect("#sjTransaction_type",comm.lang("receivablesmanage").OrderType, null, null, {name:'全部', value:''});
			
			/** 查询事件 */
			$("#sjdz_qry_btn").click(function(){
				sjzfwyskdz_self.initData();
			});
			
			/**数据对账*/
			$('#sjdz_btn').click(function(){
				var records = sjzfwyskdz_table.getCheckedRowsRecords();
				if(records.length == 0){
					comm.warn_alert("请至少选择一条数据进行对账.");
					return;
				}
				var orders = new Array();
				for(var key in records){
					if(comm.isNotEmpty(records[key].orderNo)){
						orders.push(records[key].orderNo);
					}
				}
				receivablesmanage.dataReconciliation({orderNos:JSON.stringify(orders)}, function(res){
					var obj = {count:0, records:null};
					if(res.data){
						var successRecords = res.data;
						for(var key in records){
							
							//更改对账成功的数据值
							if(key<successRecords.length){
								successRecords[key].payStatus=2;
								/*successRecords[key].orderType = comm.getNameByEnumId(successRecords[key]['orderType'], comm.lang("receivablesmanage").OrderType);*/
								//successRecords[key].payStatusText = comm.getNameByEnumId(successRecords[key]['payStatus'], comm.lang("receivablesmanage").paymentStatus);
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
					$('#sjdz_content').html(_.template(sjzfwyskdz_dailog, obj));
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
			if(!comm.queryDateVaild("search_dz_form").form()){
				return;
			}
			var queryParam = {
					"search_hsResNo":$("#txtHsResNo").val(),
					"search_startDate":$("#search_startDate").val(),
					"search_endDate":$("#search_endDate").val(),
					"search_orderType":$("#sjTransaction_type").attr("optionvalue"),
					/*"search_payChannel":"101",*/
					"search_unPayStatus":"0,1",
					"search_payChannels":"100,101,102,103",
					"search_orderChannel":4,
					"search_custNameCondition":$("#txtCustName").val()
				};
			sjzfwyskdz_table = receivablesmanage.findPaymentOrderList(queryParam, sjzfwyskdz_self.detail);
		},
		/**
		 * 详情
		 */
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex == 3){
                var result = comm.navNull(record['custName']);
                return "<span title='"+result+"'>" + result + "</span>";
            }
			if(colIndex == 6){
                return comm.lang("receivablesmanage").OrderType[record.orderType];
            }else if(colIndex==7){
                if(record.payChannel == 200){
                	return comm.formatMoneyNumber(record.orderHsbAmount);
                }else {
                	return comm.formatMoneyNumber(record.orderCashAmount);
                }
            }
			return $('<a>查看</a>').click(function(e) {
            	sjzfwyskdz_self.chaKan(record.orderNo);
			}.bind(this));
		},
		/**
		 * 查看动作
		 */
		chaKan : function(orderNo){
			receivablesmanage.findPaymentOrderInfo({orderNo:orderNo}, function(res){
				var obj = res.data;
				if(obj.payChannel == 200){
					obj.orderCashAmount = comm.formatMoneyNumber(obj.orderHsbAmount);
				}else {
					obj.orderCashAmount = comm.formatMoneyNumber(obj.orderCashAmount);
				}
				obj.orderType = comm.getNameByEnumId(obj['orderType'], comm.lang("receivablesmanage").OrderType);
				obj.payChannelText = comm.getNameByEnumId(obj['payChannel'], comm.lang("receivablesmanage").PayChannel);
				obj.payStatusText = comm.getNameByEnumId(obj['payStatus'], comm.lang("receivablesmanage").paymentStatus);
				$('#busibox_ck').html(_.template(sjzfwyskdz_ckTpl, obj));

				$('#sjzfwyskdz_ckTpl').removeClass('none');
				$('#busibox').addClass('none');
				comm.liActive_add($('#cknr'));
				
				//返回按钮
				$('#back_sjzfwyskdz').click(function(){
					//隐藏头部菜单
					$('#sjzfwyskdz_ckTpl').addClass('none');
					$('#busibox').removeClass('none');
					$('#cknr').addClass("tabNone").find('a').removeClass('active');
					$('#sjzfwyskdz').find('a').addClass('active');
				});
			});
		}
	}	
});