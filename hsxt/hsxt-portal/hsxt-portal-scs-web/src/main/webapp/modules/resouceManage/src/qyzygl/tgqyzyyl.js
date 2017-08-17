define(['text!resouceManageTpl/qyzygl/tgqyzyyl.html',
        "resouceManageDat/resouceManage",
        "resouceManageLan"],function(tgqyzyylTpl, dataModoule){
	var tgqyzyylPage = {
		showPage : function(){
			tgqyzyylPage.initForm();
		},
		/** 快捷日期控制 */
		quickDateChange:function(type){
			// 默认近一日
			var qd = ['',''];
			if(type == "1"){
				qd = comm.getTodaySE();
			}
			else if (type == "2") {
				// 近一周
				qd = comm.getWeekSE();
			}else if (type == "3") {
				// 近一月
				qd = comm.getMonthSE();
			}

			$("#search_startDate").val(qd[0]);
			$("#search_endDate").val(qd[1]);
		}, 
		/**
		 * 初始化界面
		 */
		initForm : function(){
			$('#busibox').html(_.template( tgqyzyylTpl));
			//时间区间查询
			comm.initBeginEndTime("#search_startDate", "#search_endDate");
			/*快捷日期*/
			comm.initSelect("#quickDate",comm.lang("resouceManage").quickDateEnum);
			$("#quickDate").change(function(){
				tgqyzyylPage.quickDateChange($(this).attr("optionvalue"));
			});
			
			
			//查询按钮
			$("#btnQuery").click(function(){
				tgqyzyylPage.initData();
			});
			//查询按钮
			$("#qyzygl_fh").click(function(){
				$('#busibox').removeClass('none');
				$('#cx_content_detail').addClass('none');
			});
			
			
		},
		
		
		/**
		 * 初始化数据
		 */
		initData : function(){
			var valid = comm.queryDateVaild("search_form");
			if(!valid.form()){
				return false;
			}
			var params = {};
			params.search_custType = 3;
			params.search_startDate = $("#search_startDate").val();
			params.search_endDate = $("#search_endDate").val();
			params.search_entResNo = $("#search_entResNo").val();
			params.search_entName = $("#search_entName").val();
			dataModoule.findQualMainList("tableDetail",params, this.detail);
		},
		
		/**
		 * 自定义函数
		 */
		detail : function(record, rowIndex, colIndex, options){
			//列：企业认证状态
			if(colIndex == 6){
				return comm.getNameByEnumId(record['realnameAuth'], comm.lang("resouceManage").realNameAuthSatus);
			}
			//列：参与积分状态
			if(colIndex == 7){ 
				return comm.getNameByEnumId(record['participationScore'], comm.lang("resouceManage").member_status);
			}
			return  $('<a id="'+ record['custId'] +'" >查看</a>').click(function(e) {
				$("#resEntCustId").val(record['custId']);
				$('#busibox').addClass('none');
				$('#cx_content_detail').removeClass('none');
				comm.delCache("resouceManage", "entAllInfo");
				$('#ckxq_qyxtzcxx').click();
			}.bind(this));
		}
	};
	return tgqyzyylPage
}); 
