define(['text!toolorderTpl/gjzzgl/gjddlb.html',
		'text!toolorderTpl/gjzzgl/gjddlb_ck_dialog.html',
		'toolorderDat/toolorder'
		], function(gjddlbTpl, gjddlb_ck_dialogTpl,toolorder){
	var self = {
		searchTable : null,
		gridObj : null,
		showPage : function(){
			$('#busibox').html(_.template(gjddlbTpl));

			//时间控件
			comm.initBeginEndTime('#search_startDate','#search_endDate');
			/*end*/
			
			/*下拉列表*/
			comm.initSelect("#orderState",comm.lang("toolorder").orderState);

			$("#gjddlbQueryBtn").click(function(){
				if(!comm.queryDateVaild("search_form").form()){
					return;
				}
				var params = {
					search_startDate : $("#search_startDate").val(), //配置开始日期
					search_endDate : $("#search_endDate").val(),		//配置结束日期
					search_orderNo : $("#orderNo").val().trim(),	//订单号
					search_hsResNo : $("#hsResNo").val().trim(),	//系统资源号（互生号）
					search_orderState : $("#orderState").attr("optionvalue") //订单状态
				};

				gridObj = comm.getCommBsGrid("searchTable","tool_order_list",params,comm.lang("toolorder"),self.detail,self.add,self.edit);
			});
		},
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex == 2){
				return comm.lang("toolorder").orderType3[record.orderType];
			}else if(colIndex == 3){
				return comm.lang("toolorder").orderState[record.orderStatus];
			}else if(colIndex == 5){
				return record.orderType=='109'?'------':comm.formatMoneyNumber(record.orderTotal);
			}else if(colIndex == 6){
				return comm.lang("toolorder").payChannel2[record.payChannel];
			}else if(colIndex == 7){

				link1 = $('<a>'+comm.lang("toolorder").chakan+'</a>').click(function(e) {

					self.chaKan(record.orderNo);
				});

				return link1;
			}
		},
		add : function(record, rowIndex, colIndex, options){

			if(colIndex == 7 && record.orderStatus == '6'&& record.orderType != '101'){

				link1 = $('<a>'+comm.lang("toolorder").confirOrder+'</a>').click(function(e) {

					self.qrdd(record);

				});

				return link1;
			}
		},
		edit : function(record, rowIndex, colIndex, options){
			if(colIndex == 7 && (record.orderStatus == '6' || record.orderStatus == '1')&& record.orderType != '101'){

				link1 = $('<a>'+comm.lang("toolorder").orderClose[record.orderStatus]+'</a>').click(function(e) {

					self.gbdd(record);

				});

				return link1;
			}
		},
		//确认订单
		qrdd : function(obj){
			var confirmObj = {
				imgFlag : true,
				imgClass : 'tips_ques' ,
				width : '400',
				height: '170',
				content : '<span class="red">'+comm.lang("toolorder").confirOrder1+'</span><br><br>'+comm.lang("toolorder").confirOrder2,
				callOk :function(){
					toolorder.confirmOrder({orderNo:obj.orderNo},function(res){
						comm.yes_alert(comm.lang("toolorder").toolOrderConfirmSucc,null,function(){
							if(gridObj)
							{
								gridObj.refreshPage();
							}
						});
					});
				}
			};
			comm.confirm(confirmObj);
		},
		chaKan : function(orderNo){
			toolorder.seachToolOrderDetail({orderNo:orderNo},function(res){
				var obj = res.data;
				//支付方式
				obj.pay = comm.lang("toolorder").payChannel2[res.data.order.payChannel];
				//订单状态
				obj.status = comm.lang("toolorder").orderState[res.data.order.orderStatus];
				//订单类型
				obj.type = comm.lang("toolorder").orderType3[res.data.order.orderType];
				//订单总价
				obj.orderTotal = res.data.order.orderType == '109' ? '------':comm.formatMoneyNumber(res.data.order.orderHsbAmount);
				$('#dialogBox').html(_.template(gjddlb_ck_dialogTpl, self.buyCard(obj)));
				/*弹出框*/
				$( "#dialogBox" ).dialog({
					title:"工具订单详情",
					width:"900",
					modal:true,
					closeIcon:true,
					buttons:{
						"关闭":function(){
							$( this ).dialog( "destroy" );
						},
					}
				});

			})
		},
		//买卡
		buyCard : function (obj) {
			if(obj.order.orderType=='110'){
				var conf = obj.confs[0];
				var count = (conf.quantity%1000==0) ? conf.quantity/1000 : (parseInt(conf.quantity/1000) + 1 );
				conf = {
					productName : '消费者系统资源段',
					price : '20000',
					quantity : count,
					totalAmount : 20000 * count
				};
				obj.confs[0] = conf;
			}
			return obj;
		},
		gbdd : function(obj){
			comm.confirm({
				width:600,
				imgFlag : true,
				imgClass : 'tips_ques',
				content : '您确定要'+comm.lang("toolorder").orderClose[obj.orderStatus]+'订单号为：'+ obj.orderNo + '的订单？',
				callOk :function(){
					var param = comm.getRequestParams()
					toolorder.closeOrder({orderNo:obj.orderNo},function(){
						var yesTip =  comm.strFormat(comm.lang("toolorder").toolOrderOptSucc,[comm.lang("toolorder").orderClose[obj.orderStatus]]);
						comm.yes_alert(yesTip,null,function(){
							if(gridObj)
							{
								gridObj.refreshPage();
							}
						});
					});
				}
			});
		},
		ddxq : function(){}
	};
	return self;
});