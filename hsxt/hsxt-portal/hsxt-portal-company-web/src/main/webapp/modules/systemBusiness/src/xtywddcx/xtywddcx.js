define(['text!systemBusinessTpl/xtywddcx/xtywddcx.html',
		'text!systemBusinessTpl/xtywddcx/xq_jnxtsynf.html',
		'text!systemBusinessTpl/xtywddcx/xq_yjjfyuk.html',
		'systemBusinessDat/systemBusiness'
], function(tpl,jnxtsynfTpl, yjjfyukTpl,systemBusinessAjax){
	var systemOrder = {
		showPage : function(){
			$('#busibox').html(tpl);
			//时间控件
			comm.initBeginEndTime("#search_beginDate",'#search_endDate');
			/** 下拉列表 **/
			//从缓存中获取企业类型
			var entResType = comm.getCookie("entResType");
			//成员企业没有 缴纳系统使用年费
			if(entResType == '2'){
				comm.initSelect("#orderType",comm.lang("systemBusiness").systemOrderType2);
			}else{
				comm.initSelect("#orderType",comm.lang("systemBusiness").systemOrderType);
			}
			//支付方式
			comm.initSelect("#payChannel",comm.lang("systemBusiness").toolManage_payChannel);
			
			//查询单击事件
			$('#btn_serch').click(function(){
				if(!comm.queryDateVaild('xtywddcx_form').form()){
					return;
				}
				systemOrder.loadGrid();
			});
		},
		//加载表格
		loadGrid:function(){
			var params = {
					search_beginDate : $("#search_beginDate").val(), //开始日期
					search_endDate : $("#search_endDate").val(),//结束日期
					search_orderType : $("#orderType").attr("optionvalue"),//订单类型
					search_payChannel : $("#payChannel").attr("optionvalue"),//支付方式
			};
			comm.getCommBsGrid("systemOrder_table","query_system_order_list",params,systemOrder.detail);
		},
		//详单
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex == 2){
				return comm.lang("systemBusiness").systemOrderType[record.orderType];
			}else if(colIndex == 3){
				return comm.formatMoneyNumber(record.orderHsbAmount);
			}else if(colIndex == 4){
				return comm.lang("systemBusiness").toolManage_payChannel[record.payChannel];
			}else if(colIndex == 5){
				return comm.lang("systemBusiness").payStatus[record.payStatus];
			}else if(colIndex == 6){				
				var link = $('<a>'+comm.lang("systemBusiness").toolManage_queryOrderOpt['detail']+'</a>').click(function(e) {
					switch (record.orderType) {
						case '100'://缴纳系统使用年费
							systemBusinessAjax.getAnnualfeeOrderDetail({orderNo:record.orderNo},function(resp){
								if(resp){
									var obj = resp.data;
									if(obj){
										record.annualfeeInterval = comm.removeNull(obj.areaStartDate) + '~' + comm.removeNull(obj.areaEndDate);
										systemOrder.commDetiledShow(jnxtsynfTpl,record);
									}
								}
							});
							break;
						case '108'://预缴积分预付款
							systemOrder.commDetiledShow(yjjfyukTpl,record);
							break;
						default:
							break;
					}
				}.bind(this)) ;
				return link;
			}									
		},	
		//显示
		commDetiledShow : function(html,record){
			$('#xtywddcx-dialog > p').html(_.template(html,record));
			$('#xtywddcx-dialog').dialog({width:480,title:comm.lang("systemBusiness").detailedTitle[record.orderType] ,closeIcon:true}); 
		}
	};
	return systemOrder;
});