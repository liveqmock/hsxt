define(['text!systemBusinessTpl/toolManage/swingTerminal/terminalList.html',
		'text!systemBusinessTpl/toolManage/swingTerminal/searchPointRatio.html',
		'text!systemBusinessTpl/toolManage/swingTerminal/modifyDeviceStatus.html',
		'systemBusinessDat/systemBusiness',
], function(terminalListTpl, searchPointRatioTpl, modifyDeviceStatusTpl,systemBusinessAjax){	
	//表格对象
	var gridObj = null;
	var self = {
		showPage : function(){
			$('#busibox').html(terminalListTpl);			
			//查询单击事件
			$('#terminal_searchBtn').click(function(){
				self.loadGrid();
			});
			/*下拉列表*/
			comm.initSelect('#deviceType',comm.lang("systemBusiness").toolManage_deviceType);
		},
		//加载表格
		loadGrid : function(){
			var search_deviceType = $("#deviceType").attr("optionvalue");
			if(search_deviceType == 0){
				search_deviceType =null;
			}
			var params = {
					search_deviceType : comm.removeNull($("#deviceType").attr("optionvalue")),//设备类型
					search_terminalNo : comm.removeNull($("#terminalNo").val())//终端编号
			};
			gridObj = comm.getCommBsGrid("terminal_table","tool_terminal_list",params,this.showDetail,this.modifyStatus);
		},
		//修改状态
		modifyStatus : function(record, rowIndex, colIndex, options){
			if(colIndex == 4){	
				var link = $('<a>'+comm.lang("systemBusiness").toolManage_queryDeviceOpt['modifyStatus']+'</a>').click(function(e) {
					//显示头部菜单
					comm.liActive_add($('#gjgl_xgsx'));
					$('#busibox').html(_.template(modifyDeviceStatusTpl, record));
					/*下拉列表*/
					comm.initSelect('#deviceStatus',comm.lang("systemBusiness").toolManage_deviceOptStatus,null,record.status);
					//取消按钮
					$('#btn_xgsx_cx').triggerWith('#skzdgl');
					//确定按钮
					$('#btn_xgsx_qd').click(function(){
						systemBusinessAjax.updateDeviceStatus({deviceType:record.deviceType,deviceNo:record.deviceNO,deviceStatus:$("#deviceStatus").attr("optionvalue")},function(resp){
							if(resp){
								comm.yes_alert(comm.lang("systemBusiness").toolManage_modifyDeviceStatusSucc,null,function(){
									$('#skzdgl').trigger('click');
									$('#terminal_searchBtn').trigger('click');
								});
							}
						});
					});
				}.bind(this));
				return link;
			}
		},
		//查询积分比例
		showDetail : function(record, rowIndex, colIndex, options){
			if(colIndex == 1){
				return comm.lang("systemBusiness").toolManage_deviceType[record.deviceType];
			}else if(colIndex == 2){
				return record.deviceNO;
			}else if(colIndex == 3){
				return comm.lang("systemBusiness").toolManage_deviceStatus[record.status];
			}else if(colIndex == 4 && record.status =='1'){
				var link = $('<a>'+comm.lang("systemBusiness").toolManage_queryDeviceOpt['detail']+'</a>').click(function(e) {
					if(comm.isNotEmpty(record.pointRate)){
						$('#devicePointRatio-dialog > p').html(_.template(searchPointRatioTpl,record));
						$("#devicePointRatio-dialog" ).dialog({
							title:"积分比例详情",
							width:"400",
							modal:true,
							closeIcon : true,
							buttons:{ 
								"取消":function(){
									 $( this ).dialog( "destroy" );
								}
							}
						});	
					}else{
						comm.yes_alert(comm.lang("systemBusiness").toolManage_deviceNotSetPointRatio);
					}
				}.bind(this));
				return link;
			}
		}
	};
	return self;
});