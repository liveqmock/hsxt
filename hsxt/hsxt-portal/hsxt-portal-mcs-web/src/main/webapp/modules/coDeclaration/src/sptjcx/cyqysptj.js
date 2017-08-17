define(['text!coDeclarationTpl/sptjcx/cyqysptj.html',
        'coDeclarationDat/sptjcx/cyqysptj',
        'coDeclarationLan'], function(cyqysptjTpl, dataModoule){
	return {
		showPage : function(){
			var self = this;
			$('#cx_content_list').empty().html(_.template(cyqysptjTpl));
			$('#cx_content_list').removeClass('none');
		    $('#cx_content_detail').addClass('none'); 	 
			
			//时间控件		    
		    comm.initBeginEndTime('#search_startDate','#search_endDate');

			comm.initSelect("#quickDate",comm.lang("common").quickDateEnum);
			$("#quickDate").change(function(){
				comm.quickDateChange($(this).attr("optionvalue"),'#search_startDate', '#search_endDate');
			});
			
			//初始化下拉框
			comm.initSelect('#search_status', comm.lang("coDeclaration").declarationStatusEnum, 150, null, {name:'全部', value:''});

			//绑定查询事件
			$('#queryBtn').click(function(){
				if(!comm.queryDateVaild('cx_search_form').form()){
					return;
				}
				self.query();
			});
		},
		/**
		 * 查询动作
		 */
		query : function(){
			var jsonParam = {
					search_resNo:$("#search_resNo").val(),
					search_entName:$("#search_entName").val(),
					search_startDate:$("#search_startDate").val(),
					search_endDate:$("#search_endDate").val(),
					search_status:$("#search_status").attr('optionValue'),
					search_custType:2,
				};
			dataModoule.findDeclareStatisticsList(jsonParam, this.detail);
		},
		/**
		 * 查看动作
		 */
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex == 5){
				return comm.getNameByEnumId(record['status'], comm.lang("coDeclaration").declarationStatusEnum);
			}else if(colIndex == 6 ){
				return record.apprDate;
			}else if(colIndex == 7){
				
				return  $('<a id="'+ record['applyId'] +'" >查看</a>').click(function(e) {
					$("#applyId").val(record['applyId']);
					$("#custType").val("2");
					$('#cx_content_list').addClass('none');
					$('#cx_content_detail').removeClass('none');							
					$('#ckxq_sbxx').click(); 
				}.bind(this));
			}
		}
	}	
});