define(['text!companyInfoTpl/zyxxbg/zyxxbgcx.html',
        'companyInfoDat/zyxxbg/zyxxbg',
		'companyInfoLan'],function(zyxxbgcxTpl, dataModoule){
	return {
		showPage : function(){								
			this.initForm();	
		},
		/**
		 * 初始化界面
		 */
		initForm : function(){
			var self = this;	
			$('#contentWidth_3').show();
			$('#contentWidth_3_detail').hide();
			$('#contentWidth_3').html(_.template(zyxxbgcxTpl));
			comm.initSelect('#quickDate', comm.lang("companyInfo").quickDateEnum);
			comm.initSelect('#search_status', comm.lang("companyInfo").approveStatusEnum, null, null, {name:'全部', value:''});
			
			//时间控件
			comm.initBeginEndTime("#search_beginDate","#search_endDate");
	
			//快捷选择日期事件
			$("#quickDate").bind("change", function () {
				var method = {
					'today' : 'getTodaySE',
					'week' : 'getWeekSE',
					'month' : 'getMonthSE'
				}[$(this).attr('optionValue')];
				var arr = method ? comm[method]() : ['', ''];
				$("#search_beginDate").val(arr[0]);
				$("#search_endDate").val(arr[1]);
			});
			
			$('#btnQuery').click(function(){
				self.initData();
			});
		},
		/**
		 * 初始化数据
		 */
		initData : function(){
			if(!comm.queryDateVaild('info_form').form()){
				return;
			}
			var params = {
				search_beginDate:$("#search_beginDate").val(),
				search_endDate:$("#search_endDate").val(),
				search_status:$("#search_status").attr('optionvalue')
			};
			dataModoule.findEntChangeList(params, this.detail);
		},
		/**
		 * 查看详情
		 */
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex == 2){
				return comm.getNameByEnumId(record['status'], comm.lang("companyInfo").spStatusEnum2);
			}
			if(colIndex == 3){
				//待初审、待复核 不显示状态日期
				if(1==record['status']||0==record['status']){
					return '';
				}
				return record['apprDate'];
			}
			return $('<a data-type="'+ record['applyId']+'" >查看</a>').click(function(e) {	 
				$('#applyId').val(record['applyId']);
				$('#zyxxbg_ck').click();
			});
		}
	}
}); 

 