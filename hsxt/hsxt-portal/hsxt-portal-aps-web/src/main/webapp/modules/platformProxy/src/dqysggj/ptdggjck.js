define(['text!platformProxyTpl/dqysggj/ptdggjck.html',
		'text!platformProxyTpl/dqysggj/ptdggjfh_ck_dialog.html',
		'platformProxyDat/platformProxy',
		'platformProxyLan'
		], function(ptdggjckTpl, ptdggjfh_ck_dialogTpl,dataMoudle){
	var gridObj = null;
	var self = {
		showPage : function(){
			$('#busibox').html(ptdggjckTpl);
			
			//时间控件		    
		    comm.initBeginEndTime('#search_startDate','#search_endDate');
			
			comm.initSelect('#orderType', comm.lang("platformProxy").proxyOrderType);
			comm.initSelect('#status', comm.lang("platformProxy").status);
			
			$("#seach_ptdggjck_btn").click(function(){
				if(!comm.queryDateVaild("search_form").form()){
					return;
				}
				var params={
						search_startDate : $("#search_startDate").val(), //开始时间
						search_endDate : $("#search_endDate").val(), 	//结束时间
						search_status : comm.navNull($("#status").attr("optionvalue")),//状态
						search_companyResNo:$("#companyResNo").val(),//互生号
						search_orderType : comm.navNull($("#orderType").attr("optionvalue"))//订单类型
				};
				gridObj = comm.getCommBsGrid("searchTable","find_plat_proxy_order_record_page",params,comm.lang("platformProxy"),self.detail);
			});
		},			
		detail : function(record, rowIndex, colIndex, options){
			     if(colIndex==3){
			    	 return "新增申购";
			     }else if(colIndex==4){
			    	 return comm.lang("platformProxy").proxyOrderType[record.orderType];
			     }else if(colIndex==5){
			    	 return comm.formatMoneyNumberAps(record.orderAmount);
			     } else if(colIndex==6){
			    	 return comm.lang("platformProxy").status[record.status];
			     }else if(colIndex==7){
					var link1 = $('<a>查看</a>').click(function(e) {
							dataMoudle.orderDetail({orderNo:record.proxyOrderNo},function(res){
								self.chaKan(res.data);
							});
					}) ;
				    return link1;
		         }
		},
		chaKan : function(obj){
			obj.statusName=comm.lang("platformProxy").status[obj.status];
			obj.orderTypeName=comm.lang("platformProxy").proxyOrderType[obj.orderType];
			$('#dialogBox_ck').html(_.template(ptdggjfh_ck_dialogTpl, self.buyCard(obj)));
			$( "#dialogBox_ck" ).dialog({
				title:"平台代购工具订单详情",
				width:"800",
				modal:true,
				buttons:{ 
					"确定":function(){
						$( this ).dialog( "destroy" );
					}
				}
			});
		},
		//买卡
		buyCard : function (obj) {
			if(obj.orderType=='110'){
				var detail = obj.detail[0];
				var count = (detail.quantity%1000==0) ? detail.quantity/1000 : (parseInt(detail.quantity/1000) + 1 );
				detail = {
					productName : '消费者系统资源段',
					price : '20000',
					quantity : count,
					totalAmount : 20000 * count
				};
				obj.detail[0] = detail;
			}
			return obj;
		}
	};
	return self;
});