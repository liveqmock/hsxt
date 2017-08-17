define(['text!coDeclarationTpl/qysbcx/fwgssbtj.html',
        'coDeclarationSrc/qysbxz/tab',
        'coDeclarationDat/qysbcx/fwgssbtj',
        'coDeclarationLan'],function(fwgssbtjTpl, qysbxzTab, dataModoule){
	return {	 	
		showPage : function(){
			var self = this;
			$('#cx_content_list').html(_.template(fwgssbtjTpl)) ;
			comm.initSelect('#search_status', comm.lang("coDeclaration").declarationStatusQueryEnum, null, null, {name:'全部', value:''});
		 	$('#queryBtn').click(function(){
				self.query();
			});
		 	self.initData();
			//self.query();
		},
		initData : function(){
			$("#search_startDate, #search_endDate").datepicker({//jquery UI 日期选择插件
				changeMonth : true,
				changeYear : true,
				dateFormat : "yy-mm-dd",
				maxDate : comm.getCurrDate()
			});
			//$("#search_startDate").val(comm.getMonthSE()[0]);
			//$("#search_endDate").val(comm.getMonthSE()[1]);
		},
		/*validateData : function(){
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
			/**处理复合状态*/
			var search_status = $("#search_status").attr('optionValue');
			if(search_status == "2"){
				search_status = "23";
			}else if(search_status == "5"){
				search_status = "56";
			}
			var jsonParam = {
                    search_entType:"4",
                    search_startDate:$("#search_startDate").val(),
                    search_endDate:$("#search_endDate").val(),
                    search_entResNo:$("#search_entResNo").val(),
                    search_entName:$("#search_entName").val(),
                    search_status:comm.removeNull(search_status)
			};
			dataModoule.findEntDeclareQueryList(jsonParam, this.detail, this.del, this.edit);
		},
		/**
		 * 查看动作
		 */
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex == 5){
				return comm.getNameByEnumId(record['status'], comm.lang("coDeclaration").declarationStatusEnum);
			}
			if(record['status'] != 0){
			 	return  $('<a data-sn="'+record['entResNo']+'" >查看</a>').click(function(e) {
	 		        /*
				    *  子标签切换 ,隐藏修改按钮
				    */ 
					$('#ckxq_xg').addClass('tabNone');
					//显示详情	
					var data_sn = $(this).attr('data-sn');
					$('#fwgssbtj_cx').addClass('none');
					//$('#cx_content_list').addClass('none');
					$('#cx_content_detail').removeClass('none');
					$("#custType").val("4");
					$("#applyId").val(record['applyId']);
					$("#sbStatus").val(record['status']);
					$('#ckxq_sbxx').click();
				});
			}
			return null;
		},
		/**
		 * 编辑动作
		 */
		edit : function(record, rowIndex, colIndex, options){
			if(colIndex == 5){
				return null;
			}
			var status = record['status'];
			if(status == 0){
				return $('<a data-sn="'+record['entResNo']+'">编辑</a>').click(function(e) {
					dataModoule.findDeclareStep({applyId:record['applyId']}, function(res){
						qysbxzTab.showPage(record['applyId'], res.data, 4);
					});
				});
			}
			return null;
		},
		/**
		 * 删除动作
		 */
		del : function(record, rowIndex, colIndex, options){
			if(colIndex == 5){
				return null;
			}
			if(record['status'] == 0){
				return $('<a data-sn="'+record['entResNo']+'" >删除</a>').click(function(e) {
					comm.i_confirm(comm.lang("coDeclaration").confirmDel, function(){
						dataModoule.delUnSubmitDeclare({applyId:record['applyId']}, function(res){
							comm.alert({content:comm.lang("coDeclaration").deleteOk, callOk:function(){
								$('#queryBtn').click();
							}});
						});
					}, 400);
				});
			}
			return null;
		}
	}
}); 
