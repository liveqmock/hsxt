define(['text!coDeclarationTpl/qysbfk/cyqysbfk.html',
		   'text!coDeclarationTpl/qysbfk/paidan.html',
		   'coDeclarationDat/qysbfk/cyqysbfk',
		   'coDeclarationLan'],function(cyqysbfkTpl ,paidanTpl, dataModoule){
	return {	 	
		showPage : function(){
			var self = this;
			$('#cx_content_list').html(_.template(cyqysbfkTpl)) ;
			$('#cx_content_list').append( paidanTpl );
		 	$('#cx_content_list').removeClass('none');
		    $('#cx_content_detail').addClass('none'); 	  
		 	$('#queryBtn').click(function(){
				self.query();
			});
		 	this.initForm();
			//this.query();
		},
		/**
		 * 初始化查询参数
		 */
		initForm : function(){
			$("#search_startDate, #search_endDate").datepicker({//jquery UI 日期选择插件
				changeMonth : true,
				changeYear : true,
				dateFormat : "yy-mm-dd",
				maxDate : comm.getCurrDate()
			});
			var dateArray = comm.getMonthSE();
			//$("#search_startDate").val(dateArray[0]);
			//$("#search_endDate").val(dateArray[1]);
		},
		/*	validateData : function(){
			return $("#search_form").validate({
				rules : {
					search_startDate : {
						required : true,
						date : true,
						endDate : "#search_endDate"
					},
					search_endDate : {
						required : true,
						date : true
					}
				},
				messages : {
					search_startDate : {
						required : comm.lang("coDeclaration")[22035],
						date : comm.lang("coDeclaration")[22012],
						endDate : comm.lang("coDeclaration")[22014]
					},
					search_endDate : {
						required : comm.lang("coDeclaration")[22011],
						date : comm.lang("coDeclaration")[22013],
					}
				},
				errorPlacement : function (error, element) {
					$(element).attr("title", $(error).text()).tooltip({
						tooltipClass: "ui-tooltip-error",
						destroyFlag : true,
						destroyTime : 3000,
						position : {
							my : "left+2 top+30",
							at : "left top"
						}
					}).tooltip("open");
					$(".ui-tooltip").css("max-width", "230px");
				},
				success : function (element) {
					$(element).tooltip();
					$(element).tooltip("destroy");
				}
			});
		},*/
		query : function(){
			var valid = comm.queryDateVaild("search_form");
			if(!valid.form()){
				return false;
			}
			/*if(!this.validateData().form()){
				return;
			}*/
			var jsonParam = {
                    search_entType:"2",
                    search_startDate:$("#search_startDate").val(),
                    search_endDate:$("#search_endDate").val(),
                    search_entResNo:$("#search_entResNo").val(),
                    search_entName:$("#search_entName").val(),
			};
			dataModoule.findEntDeclareReviewList(jsonParam, this.detail);
		},
		/**
		 * 详细信息
		 */
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex == 5){
				return comm.getNameByEnumId(record['status'], comm.lang("coDeclaration").declarationStatusEnum);;
			}
			var link1 = $('<a data-sn="'+ record['entResNo'] +'" >审批</a>').click(function(e) {
				var data_sn=$(this).attr('data-sn');				
				$('#cx_content_list').addClass('none');
				$('#cx_content_detail').removeClass('none');
				$("#custType").val("2");
				$("#editApplyId").val(record['applyId']);
				$('#ckxq_sbxx').click(); 
			});
			return link1;
		}
	}
}); 
