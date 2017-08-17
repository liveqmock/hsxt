define(['text!toolmanageTpl/ckkcgl/gjckls.html',
		'toolmanageDat/ckkcgl/gjckls',
        'toolmanageLan'
		], function(gjcklsTpl, dataModoule){
	return {
		showPage : function(){
			this.initForm();
		},
		/**
		 * 初始化界面
		 */
		initForm : function(){
			var self = this;
			$('#infobox').html(_.template(gjcklsTpl));
			comm.initSelect('#search_categoryCode', comm.lang("toolmanage").categoryTypeEnum, null, null);
			//时间控件		    
		    comm.initBeginEndTime('#search_startDate','#search_endDate');
			
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
			
			$('#queryBtn').click(function(){
				self.initData();
			});
		},
		/**
		 * 初始化数据
		 */
		initData : function(){
			if(!comm.queryDateVaild("search_form").form()){
				return;
			}
			var params = {};
			params.search_startDate = $("#search_startDate").val();
			params.search_endDate = $("#search_endDate").val();
			params.search_categoryCode = comm.removeNull($("#search_categoryCode").attr('optionValue'));
			dataModoule.findToolOutSerialList(params, this.detail);
		},
		/**
		 * 详情
		 */
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex == 0){
				return comm.formatDate(record['operDate'],'yyyy-MM-dd');
			} 
			if(colIndex == 1){
				return  comm.getNameByEnumId(record['categoryCode'], comm.lang("toolmanage").categoryTypeEnum);
			} 
			
		}		
	}	
});
