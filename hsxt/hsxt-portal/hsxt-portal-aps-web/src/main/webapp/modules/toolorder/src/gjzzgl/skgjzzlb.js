define(['text!toolorderTpl/gjzzgl/skgjzzlb.html',
		'text!toolorderTpl/gjzzgl/skgjzz_dialog.html',
		'text!toolorderTpl/gjzzgl/skgjzz_ck.html',
		'toolorderDat/toolorder',
		"commDat/common",
		], function(skgjzzlbTpl,skgjzz_dialogTpl,skgjzz_ckTpl,toolorder,commonAjax){
	var glxlh_detail_list=null;
	var deviceSeqNo_list=[];
	var self = {
		searchTable : null,
		
		showPage : function(){
			$('#busibox').html(skgjzzlbTpl);
			//时间控件
			comm.initBeginEndTime('#search_startDate','#search_endDate');
			/*end*/

			/**下拉列表  订单类型*/
			comm.initSelect("#orderType",comm.lang("toolorder").swingCardTool);
			/** 下拉列表 配置状态/制作状态 **/
			comm.initSelect("#makeState",comm.lang("toolorder").confStatus);


			$("#skgjzzQueryBtn").click(function(){
				if(!comm.queryDateVaild("search_form").form()){
					return;
				}
				var params = {
					search_startDate : $("#search_startDate").val(), //配置开始日期
					search_endDate : $("#search_endDate").val(),		//配置结束日期
					search_hsResNo : $("#hsResNo").val().trim(),			//互生号
					search_hsCustName : $("#hsCustName").val().trim(),		//客户名称
					search_type : $("#orderType").attr("optionvalue"),		//订单类型
					search_makeState : $("#makeState").attr("optionvalue")	//制作状态
				};
				comm.getCommBsGrid("searchTable","queryServiceToolConfig_list",params,comm.lang("toolorder"),self.detail);

			});
		},
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex == 3){
				if(record.orderType=='109'){
					return "申报申购";
				}else {
					return  "新增申购";
				}
			}else if(colIndex == 4){
				return comm.lang("toolorder").swingCardTool[record.categoryCode];
			}else if(colIndex == 6){
				return comm.lang("toolorder").confStatus[record.confStatus];
			}else if(colIndex == 7){
				if(record.categoryCode == 'POINT_MCR' || record.categoryCode == 'CONSUME_MCR'){
					link1 = $('<a title="'+comm.lang("toolorder").chakanlb+'">'+comm.lang("toolorder").chakanlb+'</a>').click(function(e) {
						self.ckxlh(record);
					});
					return link1;
				}else if(record.categoryCode == 'P_POS' || record.categoryCode == 'TABLET'){
					link1 = $('<a title="'+comm.lang("toolorder").ckzdbh+'">'+comm.lang("toolorder").ckzdbh+'</a>').click(function(e) {
						self.ckzdbh(record);
					});
				    return link1;
				}
			}
		},
		//
		ckxlh : function(obj){
			obj.flag = '3';
			obj.type = comm.lang("toolorder").orderType[obj.orderType];
			$('#dialogBox').html(_.template(skgjzz_dialogTpl, obj));

			//设置联系人、联系电话
			self.initEntInfo(obj.entCustId);

			var params = {
				confNo : obj.confNo
			};
			searchTable_1  = comm.getCommBsGrid("searchTable_3","queryDeviceRelevanceDetail",params,comm.lang("toolorder"),self.inityglsl);
			/*弹出框*/
			$( "#dialogBox" ).dialog({
				title:comm.lang("toolorder").gljqmpz,
				width:"1000",
				modal:true,
				buttons:{
					"关闭":function(){
						$("#skgjzzQueryBtn").click();
						$( this ).dialog( "destroy" );
					}
				}
			});

			/*end*/

		},
		//
		ckzdbh : function(obj){
			obj.flag = '1';
			$('#dialogBox').html(_.template(skgjzz_dialogTpl, obj));

			var params = {
				confNo : obj.confNo
			};
			searchTable_1 = comm.getCommBsGrid("searchTable_1","terminal_list",params,comm.lang("toolorder"));

			$('#searchTable_1_pt').addClass('td_nobody_r_b');
			/*end*/

			/*弹出框*/
			$( "#dialogBox" ).dialog({
				title:comm.lang("toolorder").zdbhxq,
				width:"1000",
				modal:true,
				buttons:{
					"关闭":function(){
						$( this ).dialog( "destroy" );
					}
				}
			});
			/*end*/
		},
		
		chakan : function(orderNo){
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
				$('#orderDialogBox').html(_.template(skgjzz_ckTpl, obj));
				/*弹出框*/
				$( "#orderDialogBox" ).dialog({
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
	    //设置已关联数量 = 记录条数
		inityglsl : function(record, rowIndex, colIndex, options){
			$("#yglsl").text(searchTable_1.options.totalRows);
			return record.mobile;
		},
		//设置企业基本信息(联系人、联系电话)
		initEntInfo : function(custId){
			var param = {
				companyCustId : custId
			};
			commonAjax.findCompanyMainInfo(param,function(res){
				//联系电话
				$("#contactPhone").text(res.data.contactPhone);

				//联系人
				$("#contactPerson").text(res.data.contactPerson);
			});
		}
	};
	return self;
});