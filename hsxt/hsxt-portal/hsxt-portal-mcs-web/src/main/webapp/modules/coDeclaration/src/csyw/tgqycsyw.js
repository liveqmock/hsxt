define(['text!coDeclarationTpl/csyw/tgqycsyw.html',
        'coDeclarationDat/csyw/tgqycsyw',
        'commDat/common',
        'coDeclarationLan'],function(tgqycsywTpl, dataModoule,commAjax){
	var mcs_tgqycsyw = {
		bgGrid:null,
		showPage: function () {
			$('#cx_content_list').empty().html(_.template(tgqycsywTpl));
			$('#cx_content_list').removeClass('none');
			$('#cx_content_detail').addClass('none');

			//时间控件		    
			comm.initBeginEndTime('#search_startDate', '#search_endDate');

			comm.initSelect("#quickDate",comm.lang("common").quickDateEnum);
			$("#quickDate").change(function(){
				comm.quickDateChange($(this).attr("optionvalue"),'#search_startDate', '#search_endDate');
			});

			$('#queryBtn').click(function () {
				if (!comm.queryDateVaild('tg_search_form').form()) {
					return;
				}
				mcs_tgqycsyw.query();
			});
		},
		/**
		 * 查询动作
		 */
		query: function () {
			var jsonParam = {
				search_resNo: $("#search_resNo").val(),
				search_entName: $("#search_entName").val(),
				search_startDate: $("#search_startDate").val(),
				search_endDate: $("#search_endDate").val(),
				search_custType: 3
			};
			mcs_tgqycsyw.bgGrid = dataModoule.findDeclareTrialList(jsonParam, this.detail, this.orderOpt);
		},
		/**
		 * 查看动作
		 */
		detail: function (record, rowIndex, colIndex, options) {
			if (colIndex == 6) {
				return comm.getNameByEnumId(record['status'], comm.lang("coDeclaration").declarationStatusEnum);
			} else if (colIndex == 7) {
				return $('<a id="' + record['applyId'] + '" >审批</a>').click(function (e) {
					$("#applyId").val(record['applyId']);
					$("#custType").val("3");
					$('#cx_content_list').addClass('none');
					$('#cx_content_detail').removeClass('none');
					$('#ckxq_sbxx').click();
				}.bind(this));
			} else if (colIndex == 8) {
				return $('<a>' + comm.lang("coDeclaration").refuseAccept + '</a>').click(function (e) {
					comm.i_confirm(comm.lang("coDeclaration").firstTRefuseAccept, function () {
						commAjax.workTaskRefuseAccept({bizNo: record.applyId}, function (resp) {
							mcs_tgqycsyw.query();
							comm.yes_alert(comm.lang("coDeclaration")[22000]);
						});
					});
				});
			}
		},
		/**
		 * 工单操作
		 */
		orderOpt: function (record, rowIndex, colIndex, options) {
			if (colIndex == 8) {
				return comm.workflow_operate(comm.lang("coDeclaration").hangUp, comm.lang("coDeclaration").firstTHangUp, function(){
					require(["workoptSrc/gdgq"],function(gdgq){
						gdgq.guaQi(record.applyId,mcs_tgqycsyw.bgGrid);
					});
				});
			}
		}
	};
	return mcs_tgqycsyw;
}); 
