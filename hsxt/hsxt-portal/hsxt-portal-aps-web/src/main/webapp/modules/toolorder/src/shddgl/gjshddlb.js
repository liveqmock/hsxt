define(['text!toolorderTpl/shddgl/gjshddlb.html',
		'text!toolorderTpl/shddgl/gjshddlb_ck_dialog.html',
		'toolorderDat/toolorder'
		], function(gjshddlbTpl, gjshddlb_ck_dialogTpl,toolorder){	
	var self = {
		gridObj : null,
		showPage : function(){
			$('#busibox').html(_.template(gjshddlbTpl));
		    comm.initBeginEndTime('#search_startDate','#search_endDate');
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
						search_status : $("#orderState").attr("optionvalue") //订单状态
				};				
				gridObj = comm.getCommBsGrid("searchTable","find_after_order_plat_page",params,comm.lang("toolorder"),self.detail,self.add,self.edit);
			});
		},
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex == 2){
				return '售后服务维修订单';
			}else if(colIndex == 3){
				return comm.lang("toolorder").orderState[record.orderStatus];
			}else if(colIndex == 5){
				return comm.formatMoneyNumber(record.orderTotal);
			}else if(colIndex == 6){
				return comm.lang("toolorder").payChannel2[record.payChannel];
			}else if(colIndex == 7){				
				return $('<a>'+comm.lang("toolorder").chakan+'</a>').click(function(e) {
					
					self.chaKan(record.orderNo);
				}.bind(this));
			}
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
				$('#dialogBox').html(_.template(gjshddlb_ck_dialogTpl, obj));
				/*弹出框*/
				$( "#dialogBox" ).dialog({
					title:"售后服务订单详情",
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
		}					
	};	
	return self;	
});