define(['text!coDeclarationTpl/qysbcx/ckxq/blztxx.html',
        'text!coDeclarationTpl/qysbcx/ckxq/blztxx_dialog.html',
        'coDeclarationDat/qysbcx/ckxq/blztxx',
		'coDeclarationLan'],function(blztxxTpl, blztxx_dialogTpl,dataModoule){
	return {	 	
		showPage : function(){
			this.initForm();
			this.query();
		},
		/**
		 * 初始化页面
		 */
		initForm : function(){
			$('#ckxq_view').html(_.template(blztxxTpl));
			$('#ckxq_xg').css('display','none'); 
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
			}
			if(colIndex == 1){
				return comm.getNameByEnumId(record['bizStatus'], comm.lang("coDeclaration").bizStatusEnum);
			}
			if(colIndex == 4){
				return record.optName?record.optName:"-";
			}
			if(colIndex == 5){
				if(!record.optRemark){
					return "-";
				}
				return $('<a data-sn="'+ record['bizStatus'] +'" >查看</a>').click(function(e) {
					
					
					//
					$('#dialogBox').html(_.template(blztxx_dialogTpl));	
					$( "#bz_info").html(record['optRemark']);
					/*弹出框*/	
					$( "#dialogBox").dialog({
						title:"查看备注",
						modal:true,
						width:"380",
						height:'300',
						buttons:{ 
							"确定":function(){
								 $( this ).dialog( "destroy" );
							}
						}
					
					});
					
					//
					//comm.alert({imgClass:'tips_i',title:'查看备注',content: record['optRemark']});
				});
			}
		}
	}
}); 
