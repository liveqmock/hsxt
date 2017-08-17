define(['text!coDeclarationTpl/qysbfk/ckxq/blztxx.html',
        'coDeclarationDat/qysbfk/ckxq/blztxx',
		'coDeclarationLan'],function(blztxxTpl, dataModoule){
	return {	 	
		showPage : function(){
			this.initForm();
			this.query();
		},
		/**
		 * 初始化页面
		 */
		initForm : function(){
			var self = this;
			$('#ckxq_view').html(blztxxTpl);
			$('#ckxq_xg').hide();
			$("#skqr_tj").show();
			//取消
		 	$('#ckxq_qx').click(function(){
		 		self.gotoList();
		 	});
		},
		/**
		 * 查询列表
		 */
		query : function(){
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
			}else if(colIndex == 1){
				return comm.getNameByEnumId(record['bizStatus'], comm.lang("coDeclaration").bizStatusEnum);
			}else if(colIndex == 4){
				return record.optName?record.optName:"-";
			}else if(colIndex == 5){
				if(record['optRemark'] == null){
					return "-";
				}
				return $('<a data-sn="'+ record['bizStatus'] +'" >查看</a>').click(function(e) {
					comm.alert({imgClass:'tips_i',title:'查看备注',content: record['optRemark']});
				});
			}
		},
		/**
		 * 返回至列表
		 */
		gotoList : function(){
			var custType = $("#custType").val();
			if(custType == "4"){
				$('#qysbfk_fwgssbfk').click();
			}else if(custType == "2"){
				$('#qysbfk_cyqysbfk').click();
			}else{
				$('#qysbfk_tgqysbfk').click();
			}
		}
	}
}); 
