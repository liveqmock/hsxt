define(['text!toolorderTpl/gjzzgl/skgjzz.html',
		'text!toolorderTpl/gjzzgl/skgjzz_dialog.html',
		'text!toolorderTpl/gjzzgl/skgjzz_ck.html',
		'toolorderDat/toolorder',
		"commDat/common",
		], function(skgjzzTpl, skgjzz_dialogTpl,skgjzz_ckTpl,toolorder,commonAjax){
	var glxlh_detail_list=null;
	var deviceSeqNo_list=[];
	var self = {
		searchTable : null,
		
		showPage : function(){
			$('#busibox').html(skgjzzTpl);
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
				comm.getCommBsGrid("searchTable","swipecard_list",params,comm.lang("toolorder"),self.detail,self.add);

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
				link1 = $('<a>'+comm.lang("toolorder").chakan+'</a>').click(function(e) {
					self.chakan(record.orderNo);
				});
				return link1;
			}
		},
		add : function(record, rowIndex, colIndex, options){
			if(colIndex == 7){
				if(record.categoryCode == 'POINT_MCR' || record.categoryCode == 'CONSUME_MCR'){

					link1 = $('<a title="'+comm.lang("toolorder").glxlh+'">'+comm.lang("toolorder").glxlh+'</a>').click(function(e) {
						self.glxlh(record);
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
		glxlh : function(obj){
			obj.flag = '2';
			obj.type = comm.lang("toolorder").orderType[obj.orderType];
			$('#dialogBox').html(_.template(skgjzz_dialogTpl, obj));

			//设置联系人、联系电话
			self.initEntInfo(obj.entCustId);

			var params = {
				confNo : obj.confNo
			};
		//	searchTable_1  = comm.getCommBsGrid("searchTable_2","terminal_list",params,comm.lang("toolorder"),self.inityglsl);
			self.refreshData('searchTable_2',null);
			deviceSeqNo_list=[];
			glxlh_detail_list=null;
			//$('#searchTable_2_pt').addClass('td_nobody_r_b');
			/*弹出框*/
			$( "#dialogBox" ).dialog({
				title:comm.lang("toolorder").gljqmpz,
				width:"1000",
				modal:true,
				buttons:{
					"确定":function(){
						
						var deviceSeqNoLists=$("#deviceSeqNoHidden").val();
						if(deviceSeqNoLists==""||deviceSeqNoLists.length==0||deviceSeqNoLists=="[]"){
							comm.alert(comm.lang("toolorder").isNoDeviceSeq);
							return false;
						}
						
						var batchparams = {
								confNo : obj.confNo,
								operNo : $.cookie('custId'),
								apsCustId : obj.entCustId,				    //客户
								deviceSeqNo:$("#deviceSeqNoHidden").val()
							};
						//批量添加关联
						toolorder.addBatchRelation(batchparams,function(res){
							if(res)
							{
								comm.alert({
									 content: comm.lang("toolorder").addBatchRelationSuccess,
									 callOk: function(){
										 $("#skgjzzQueryBtn").click();
										 $("#dialogBox").dialog( "destroy" );
									 }
								 });
							}
						});
						
					},
					"取消":function(){
						$("#skgjzzQueryBtn").click();
						$( this ).dialog( "destroy" );
					}
				}
			});


			$("#addRelationBtn").click(function(){
				var deviceSeqNo = $("#deviceSeqNo").val();
				if(comm.isEmpty(deviceSeqNo)){
					comm.error_alert(comm.lang("toolorder")[34504]);
					return;
				}
				comm.i_confirm(comm.strFormat(comm.lang("toolorder").isConfirmRelationMcr,[deviceSeqNo]),function(){
					self.addRelation(obj.confNo,obj.entCustId);
				},500);
			});
			/*end*/

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
		},
		
		refreshData :function(tableId,glxlh_detail_lists){
			var autoLoad=true;
			if(glxlh_detail_lists==null || glxlh_detail_lists.length == 0){
				autoLoad=false;
			}
		  $.fn.bsgrid.init(tableId, {				 
					pageSize: 10 ,
					autoLoad :autoLoad,
					stripeRows: true,  //行色彩分隔 
					displayBlankRows: false ,   //显示空白行
					localData:glxlh_detail_lists ,
					operate : {	
						detail : function(record, rowIndex, colIndex, options){
							if(colIndex ==1){
								 return "<span title='"+record['batchNo']+"'>"+record['batchNo']+"</span>";
							}
							if(colIndex==5){
								var link1 = $('<a>删除</a>').click(function(e) {
									comm.confirm({
										imgFlag : true,
										imgClass : 'tips_ques',
										content : comm.lang("toolorder").confirmDel,
										callOk :function(){
											if(rowIndex==0){
												glxlh_detail_lists.shift();
												deviceSeqNo_list.shift();
											}else{
												glxlh_detail_lists.splice(rowIndex,1); 
												deviceSeqNo_list.splice(rowIndex,1); 
											}
											$("#detailListHidden").val(JSON.stringify(glxlh_detail_lists));
											$("#deviceSeqNoHidden").val(JSON.stringify(deviceSeqNo_list));
											self.refreshData('searchTable_2',glxlh_detail_lists);
										}
									});
								}) ;
								return  link1;
							}
						}
					}
			 });
		},
		
		addRelation : function(confNo,entCustId){
			var postData = {
				deviceSeqNo : $("#deviceSeqNo").val(),  //设备序列号
				confNo : confNo,						//配置单号
				apsCustId : entCustId,				    //客户
				operNo : $.cookie('custId')				//操作员
			};
			toolorder.addRelation(postData,function(res){
				if(res)
				{
					var data = res.data;
					if(data){
						var detailListHidden=$("#detailListHidden").val();
						if(detailListHidden){
							glxlh_detail_list=JSON.parse($("#detailListHidden").val());
							deviceSeqNo_list=JSON.parse($("#deviceSeqNoHidden").val());
							glxlh_detail_list=glxlh_detail_list.concat(data);
							var tempIndex= jQuery.inArray(data[0].deviceSeqNo, deviceSeqNo_list);
							if(tempIndex!=-1){
								comm.error_alert(comm.lang("toolorder").isexistDeviceSeq);
								return false;
							}else{
								deviceSeqNo_list=deviceSeqNo_list.concat(data[0].deviceSeqNo)
							}
						}else{
							glxlh_detail_list=data;
							deviceSeqNo_list[0]=data[0].deviceSeqNo;
						}
						$("#detailListHidden").val(JSON.stringify(glxlh_detail_list));
						$("#deviceSeqNoHidden").val(JSON.stringify(deviceSeqNo_list));
					    self.refreshData('searchTable_2',glxlh_detail_list);
					}
				}
			});
		}
	};
	return self;
});