define(['text!toolmanageTpl/ckkcgl/kcyj.html',
		'toolmanageDat/ckkcgl/kcyj',
        'toolmanageLan'
		], function(kcyjTpl, dataModoule){
	return {
		showPage : function(){
			this.initForm();
		},
		/**
		 * 初始化界面
		 */
		initForm : function(){
			var self = this;
			$('#busibox').html(_.template(kcyjTpl));
			comm.initSelect('#search_warningState', comm.lang("toolmanage").warningState, null, null);
			comm.initSelect('#search_categoryCode', comm.lang("toolmanage").categoryTypes, null, null);
			$('#queryBtn').click(function(){
				self.initData();
			});
		},
		/**
		 * 初始化数据
		 */
		initData : function(){
			var params = {};
			params.search_warningState = comm.removeNull($("#search_warningState").attr('optionValue'));
			params.search_productName = $("#search_productName").val().trim();
			params.search_categoryCode = comm.removeNull($("#search_categoryCode").attr('optionValue'));
			dataModoule.findToolEnterStockWarningList(params, this.detail);
		},
		/**
		 * 查看动作
		 */
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex == 2){
				return comm.getNameByEnumId(record['categoryCode'], comm.lang("toolmanage").categoryTypes);
			}
			if(colIndex == 3){
				return comm.getNameByEnumId(record['warningStatus'], comm.lang("toolmanage").warningState);
			}
		}	
	}	
});
