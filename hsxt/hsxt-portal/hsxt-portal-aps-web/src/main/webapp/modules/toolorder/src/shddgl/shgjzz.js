define(['text!toolorderTpl/shddgl/shgjzz.html',
		'text!toolorderTpl/shddgl/shgjzz_ck_dialog.html',
		'text!toolorderTpl/shddgl/shgjzz_cl_dialog.html',
		'text!toolorderTpl/shddgl/shgjzz_cxgl_dialog.html',
		'toolorderDat/toolorder',
		'toolorderLan'
		], function(shgjzzTpl, shgjzz_ck_dialogTpl, shgjzz_cl_dialogTpl, shgjzz_cxgl_dialogTpl,dataModule){
	var self = {
		searchTable	: null,
		showPage : function(){
			$('#busibox').html(_.template(shgjzzTpl));

			/*表格数据模拟*/

			$("#qry_gjzz_btn").click(function(){
				var params = {
					search_companyResNo	:$('#entResNo').val(),
					search_companyCustName:$('#custName').val()
				};
				searchTable = comm.getCommBsGrid("searchTable","queryToolMakeAfterPaid",params,comm.lang("toolorder"),self.detail);
			});

			/*end*/

		},
		detail : function(record, rowIndex, colIndex, options){
			var obj = searchTable.getRecord(rowIndex);
			obj.categoryName = comm.lang("toolorder").categoryCode[record.categoryCode];
			if(colIndex==3){
				return comm.lang("toolorder").categoryCode[record.categoryCode];
			}
			if(colIndex==5){
				return comm.lang("toolorder").processing;
			}
			if(colIndex==6){
				var	link1 = null;
				if(searchTable.getColumnValue(rowIndex, 'categoryCode') == 'POINT_MCR' || searchTable.getColumnValue(rowIndex, 'categoryCode') == 'CONSUME_MCR'){
					link1 = $('<a>处理</a>').click(function(e) {
						self.chuLi(obj);
					});
				}
				//POS机、平板保持关联处理
				else if(searchTable.getColumnValue(rowIndex, 'categoryCode') == 'P_POS' || searchTable.getColumnValue(rowIndex, 'categoryCode') == 'TABLET'){
					link1 = $('<a>处理</a>').click(function(e) {
						self.chaKan(obj);
					});
				}
				return link1;
			}

		},
		chaKan : function(obj){
			$('#dialogBox').html(_.template(shgjzz_ck_dialogTpl, obj));

			/*弹出框*/
			$( "#dialogBox" ).dialog({
				title:"售后订单详情",
				width:"900",
				closeIcon : true,
				modal:true,
				buttons:{
					"关闭":function(){
						$( this ).dialog( "destroy" );
					}
				}
			});
			/*end*/

			/*表格数据模拟*/
			var chakanParam = {
				confNo	:	obj.confNo
			};
			dataModule.queryAfterConfigDetail( chakanParam,function(res){
				if(res.retCode==22000){
					uploadGridObj = comm.getEasyBsGrid("searchTable_1",res.data,function(record, rowIndex, colIndex, options){
						if(colIndex==2){
							return comm.isNotEmpty(record.newDeviceSeqNo)?record.newDeviceSeqNo:record.deviceSeqNo;
						}
						if(colIndex==3){
							return comm.lang("toolorder").isConfigStatus[record.isConfig];
						}else if(colIndex==4){
							if(uploadGridObj.getColumnValue(rowIndex, 'disposeType') == '1'&& !record.isConfig){
								var obj = uploadGridObj.getRecord(rowIndex),
									link1 = $('<a>保持关联</a>').click(function(){
										self.bcgl(obj, $('#dialogBox'));
									});
								return link1;
							}
						}
					});
				}
			});

			$('#searchTable_1_pt').addClass('td_nobody_r_b');
			/*end*/
		},

		chuLi : function(obj){
			$('#dialogBox_cl').html(_.template(shgjzz_cl_dialogTpl,obj));

			$('#dialogBox_cl').dialog({
				title : '售后订单处理详情',
				width : '900',
				closeIcon : true,
				modal : true,
				buttons : {
					'确定' : function(){
						$(this).dialog('destroy');
					}
				}

			});

			/*表格数据模拟*/
			var chakanParam = {
				confNo	:	obj.confNo
			};
			dataModule.queryAfterConfigDetail( chakanParam,function(res){
				if(res.retCode==22000){
					uploadGridObj = comm.getEasyBsGrid("shgjzz_cl_table",res.data,function(record, rowIndex, colIndex, options){
							if(colIndex==1){
								return comm.isNotEmpty(record.newDeviceSeqNo)?record.newDeviceSeqNo:record.deviceSeqNo;
							}
							if(colIndex==2){
								return comm.lang("toolorder").disposeTypeEnum[record.disposeType];
							}
							if(colIndex==3){
								return comm.lang("toolorder").isConfigStatus[record.isConfig];
							}

							if(colIndex==4){
								if(uploadGridObj.getColumnValue(rowIndex, 'disposeType') != '0' && uploadGridObj.getColumnValue(rowIndex, 'disposeType') != '3'&& !record.isConfig){
									var obj = uploadGridObj.getRecord(rowIndex),
										link1 = $('<a>保持关联</a>').click(function(){
											self.bcgl(obj, $('#dialogBox_cl'));
										});
									return link1;
								}
							}

						},
						function(record, rowIndex, colIndex, options){
							if(colIndex==4){
								if(uploadGridObj.getColumnValue(rowIndex, 'disposeType') == '3' && !record.isConfig){
									var obj = uploadGridObj.getRecord(rowIndex),
										link1 = $('<a>重新关联</a>').click(function(){
											self.cxgl(obj);
										});
									return link1;
								}
							}
						});
				}
			});

			$('#shgjzz_cl_table_pt').addClass('td_nobody_r_b');
			/*end*/

		},
		bcgl : function(obj, dialog){

			var reqParam = {
				custIdEnt		:	$('#custIdEnt').val(),
				afterOrderNo	:	obj.afterOrderNo,
				confNo			:	obj.confNo,
				deviceSeqNo		:	obj.deviceSeqNo,
				operNo			:	$.cookie('custName')
			};
			dataModule.keepAssociation(reqParam,function(res){
				comm.alert({content:comm.lang("toolorder")[22000], callOk:function(){
					dialog.dialog('destroy');
					$('#qry_gjzz_btn').click();
				}});

			});
		},
		cxgl : function(obj){
			$('#dialogBox_cxgl').html(shgjzz_cxgl_dialogTpl).dialog({
				title : '重新关联序列号',
				width : '400',
				closeIcon : true,
				modal : true,
				buttons : {
					'确定' : function(){
						if($('#newDeviceSeqNo').val().trim()==""){
							return;
						}
						var reassociationParam = {
							custIdEnt		:	$('#custIdEnt').val(),
							afterOrderNo	:	obj.afterOrderNo,
							confNo			:	obj.confNo,
							deviceSeqNo		:	obj.deviceSeqNo,
							terminalNo		:	obj.terminalNo,
							newDeviceSeqNo	:	$('#newDeviceSeqNo').val().trim(),
							operNo			:	$.cookie('custName')
						};

						dataModule.reassociation(reassociationParam,function(res){
							comm.alert({content:comm.lang("toolorder")[22000], callOk:function(){
								$("#dialogBox_cxgl").dialog( "destroy" );
								$('#qry_gjzz_btn').click();
							}});

						});
					},
					'取消' : function(){
						$(this).dialog('destroy');
					}
				}
			});
		}
	};
	return self;
});