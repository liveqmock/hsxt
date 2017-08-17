define(['text!posUpgradeManageTpl/sub/posgjcx/posgjcx.html',
        'text!posUpgradeManageTpl/sub/posgjcx/posgjxg.html',
        'posUpgradeManageLan'],function(posgjcxTpl,posgjxgTpl){
	var gridObj;
	return posgjcx = {
		showPage : function(tabid){
			var self = this;
			$('#main-content > div[data-contentid="'+tabid+'"]').html(_.template(posgjcxTpl)) ;
			/*日期控件*/
			comm.initBeginEndTime("#search_beginDate","#search_endDate");
			comm.initSelect($("#isUpgrade"),comm.lang("posUpgradeManage").updateType,null);
			// 查询条件
//		    $("#searchBtn").live('click',function(){
//		    	posgjcx.queryPage();
//		    });
			$("#searchBtn").click(function(){
				posgjcx.queryPage();
			});
		},
		// 查询
		queryPage : function(){
			var param = {};
		    param.posDeviceType = comm.removeNull($("#posType").val());
		    param.isUpgrade = comm.removeNull($("#isUpgrade").attr('optionValue'));
		    param.beginDate = comm.removeNull($("#search_beginDate").val());
		    param.endDate = comm.removeNull($("#search_endDate").val());
		    gridObj = comm.getCommBsGrid('tableDetail', "searchPosUpPage", param,null,posgjcx.detail);
		},
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex == 2){
				return comm.lang("posUpgradeManage").updateType[record.isUpgrade];
			}
			if(colIndex == 3){
				return comm.lang("posUpgradeManage").mustUpType[record.isForceUpgrade];
			}
			if(colIndex == 4){
				return opt_btn = $('<a>修改</a>').click(function(e) {
					posgjcx.edit(record);
				});
			}
		},
		edit : function(obj){
				$('#posgjxgDlg').html(_.template(posgjxgTpl,obj));
				/*弹出框*/
				$("#posgjxgDlg").dialog({
					title:"修改",
					width:"600",
					height:"380",
					modal:true,
					closeIcon : false,
					buttons:{ 
						"保存":function(){
							var param = {};
							param.posId = obj.posId;
						    param.posDeviceType = obj.posDeviceType;
						    param.isUpgrade = comm.removeNull($("#posgjxg_upType").attr('optionValue'));
						    param.isForceUpgrade = comm.removeNull($("#posgjxg_mustUpType").attr('optionValue'));
							comm.requestFun("updatePosUpInfo",param,function(obj){
								comm.yes_alert_info("",comm.lang("posUpgradeManage").success);
								$("#posgjxgDlg").dialog( "destroy" );
								$("#tableDetail_pt_refreshPage").click();
							},comm.lang("posUpgradeManage"));
						},
						"取消":function(){
							 $(this).dialog( "destroy" );
						}
					}
				});
				comm.initSelect($("#posgjxg_upType"),comm.lang("posUpgradeManage").updateType,200,obj.isUpgrade);
				comm.initSelect($("#posgjxg_mustUpType"),comm.lang("posUpgradeManage").mustUpType,200,obj.isForceUpgrade);
		}
	}
}); 
