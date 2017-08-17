define(['text!coDeclarationTpl/kqxtyw/blztxx.html',
        'coDeclarationDat/kqxtyw/blztxx',
		'coDeclarationLan'], function(blztxxTpl, dataModoule){
	return{
		showPage : function(){
			this.initForm();
			this.initData();
		},
		/**
		 * 初始化页面
		 */
		initForm : function(){
			$('#infobox').html(blztxxTpl);
			/*按钮事件*/
			$('#blztxx_cancel').triggerWith('#'+$("#menuName").val());
		},
		/**
		 * 查询列表
		 */
		initData : function(){
			var params = {};
			params.search_applyId = $("#applyId").val();
			dataModoule.findOperationHisList(params, this.detail);
		},
		/**
		 * 查看备注
		 */
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex == 0){
				return comm.getNameByEnumId(record['bizAction'], comm.lang("coDeclaration").bizResultEnum);
			}
			if(colIndex == 1){
				return comm.getNameByEnumId(record['bizStatus'], comm.lang("coDeclaration").bizStatusEnum);
			}
			if(colIndex == 4){
				return record.optName?record.optName:"-";
			}
			if(colIndex == 5){
				if(record['optRemark'] == null){
					return "-";
				}
				return $('<a data-sn="'+ record['bizStatus'] +'" >查看</a>').click(function(e) {
					comm.alert({imgClass:'tips_i',title:'查看备注',content: record['optRemark']});
				});
			}
		}
	}
});