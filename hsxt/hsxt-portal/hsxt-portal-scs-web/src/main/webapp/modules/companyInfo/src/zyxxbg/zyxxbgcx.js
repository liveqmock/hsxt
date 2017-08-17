define(['text!companyInfoTpl/zyxxbg/zyxxbgcx.html',
        'companyInfoDat/zyxxbg/zyxxbg',
		'companyInfoLan'],function(zyxxbgcxTpl, dataModoule){
	return {
		showPage : function(obj){
			if(!obj){
				obj = {
					startDate:'',
					endDate:'',
					status:''
				}
			}
			this.initForm(obj);
			//this.initData();
		},
		/**
		 * 初始化界面
		 */
		initForm : function(obj){
			var self = this;
			$('#contentWidth_3').show();
			$('#contentWidth_3_detail').hide();
			$('#contentWidth_3').html(_.template(zyxxbgcxTpl));
			comm.initSelect('#quickDate', comm.lang("common").quickDateEnum);
			comm.initSelect('#search_status', comm.lang("companyInfo").queryStatusEnum, null, obj.status, {name:'全部', value:''});
			
			//时间区间查询
			comm.initBeginEndTime("#search_startDate", "#search_endDate");
			
			//快捷选择日期事件
			$("#quickDate").bind("change", function () {
				var method = {
					'today' : 'getTodaySE',
					'week' : 'getWeekSE',
					'month' : 'getMonthSE'
				}[$(this).attr('optionValue')];
				var arr = method ? comm[method]() : ['', ''];
				$("#search_startDate").val(arr[0]);
				$("#search_endDate").val(arr[1]);
			});
			
			$("#search_startDate").val(obj.startDate);
			$("#search_endDate").val(obj.endDate);
			
			$('#btnQuery').click(function(){
				self.initData();
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
			var params = {
				search_startDate:$("#search_startDate").val(),
				search_endDate:$("#search_endDate").val(),
				search_status:$("#search_status").attr('optionValue')
			};
			dataModoule.findEntChangeList(params, this.detail);
		},
		/**
		 * 查看详情
		 */
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex == 2){
				return comm.getNameByEnumId(record['status'], comm.lang("companyInfo").spStatusEnum2);
			}else if(colIndex == 3){
				if(record['status']== 0||record['status']== 1){
					return '';
				}
				return record.apprDate
			}else if(colIndex == 4){
				return $('<a data-type="'+ record['applyId']+'" >查看</a>').click(function(e) {	 
					$('#applyId').val(record['applyId']);
					$("#searchstartDate").val($("#search_startDate").val());
					$("#searchendDate").val($("#search_endDate").val());
					$("#searchstatus").val($("#search_status").attr('optionValue'));
					$('#zyxxbg_ck').click();
				});
			}
		}
	}
}); 

 