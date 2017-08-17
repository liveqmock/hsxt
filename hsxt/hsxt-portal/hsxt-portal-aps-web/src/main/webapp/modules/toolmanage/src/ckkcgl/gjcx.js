define(['text!toolmanageTpl/ckkcgl/gjcx.html',
		'toolmanageDat/ckkcgl/gjcx',
        'toolmanageLan'
		], function(gjcxTpl, dataModoule){
	return {
		showPage : function(){
			this.initForm();
		},
		/**
		 * 初始化界面
		 */
		initForm : function(){
			var self = this;
			$('#busibox').html(_.template(gjcxTpl));
			comm.initSelect('#search_useStatus', comm.lang("toolmanage").useStatusEnum, null, null);
			$('#queryBtn').click(function(){
				self.initData();
			});
		},
		/**
		 * 初始化数据
		 */
		initData : function(){
			var params = {};
			params.search_deviceSeqNo = $("#search_deviceSeqNo").val().trim();
			params.search_batchNo = $("#search_batchNo").val().trim();
			params.search_useStatus = comm.removeNull($("#search_useStatus").attr('optionValue'));
			dataModoule.findToolDeviceUseList(params, this.detail);
		},
		/**
		 * 查看动作
		 */
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex == 0){
				return comm.getNameByEnumId(record['categoryCode'], comm.lang("toolmanage").categoryTypes);
			} 
			return comm.getNameByEnumId(record['useStatus'], comm.lang("toolmanage").useStatusEnum);
		}
	}	
});
