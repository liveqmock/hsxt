define(['text!coDeclarationTpl/yxkhcx/yxkhcx.html' ,
			'text!coDeclarationTpl/yxkhcx/yxhkcx_ck_dialog.html' ,
 			'text!coDeclarationTpl/yxkhcx/yxhkcx_cl_dialog.html' ,
 			'coDeclarationDat/yxkhcx/yxkhcx',
 			'coDeclarationLan'
		],function( yxkhcxTpl , yxhkcx_ck_dialogTpl   ,  yxhkcx_cl_dialogTpl, dataModoule){
	return {
		showPage : function(){
			$('#contentWidth_2').html(_.template(yxkhcxTpl));
			var self = this;
			comm.initSelect('#search_appType', comm.lang("coDeclaration").appTypeEnum, null, null, {name:'全部', value:''});
			comm.initSelect('#search_status', comm.lang("coDeclaration").dealStatusEnum, null, null, {name:'全部', value:''});
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
			/*var monthSE=comm.getMonthSE();
			$("#search_startDate").val(monthSE[0]);
			$("#search_endDate").val(monthSE[1]);*/
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
			var jsonParam = {
                    search_startDate:$("#search_startDate").val(),
                    search_endDate:$("#search_endDate").val(),
                    search_appType:$("#search_appType").attr('optionValue'),
                    search_entCustName:$("#search_entCustName").val(),
                    search_status:$("#search_status").attr('optionValue'),
			};
			cacheUtil.findProvCity();
			dataModoule.findIntentionCustList(jsonParam, this.detail, null);
		},
		detail : function (record, rowIndex, colIndex, options){
			var self = this;
			if(colIndex == 4){
				return comm.getRegionByCode(record['countryNo'], record['provinceNo'], record['cityNo']);
			}
			if(colIndex == 5){
				return comm.getNameByEnumId(record['appType'], comm.lang("coDeclaration").appTypeEnum);
			}
			if(colIndex == 6){
				return comm.getNameByEnumId(record['status'], comm.lang("coDeclaration").dealStatusEnum);
			}
			var operation = comm.getNameByEnumId(record['status'], comm.lang("coDeclaration").dealStatusEnum);
			
			var link = null;
			var status = record['status'];
			if(status == "4"){
				link=  $('<a>查看</a>').click(function(e) {
					var params = {applyId:record['applyId']};
					dataModoule.findIntentCustById(params, function(res){
						$('#yxkhcx_dialog>p').html(_.template(yxhkcx_ck_dialogTpl, res.data));
						$("#region").html(comm.getRegionByCode(res.data['countryNo'], res.data['provinceNo'], res.data['cityNo']));
						$("#appType").html(comm.getNameByEnumId(res.data.appType, comm.lang("coDeclaration").appTypeEnum));
						$("#entType").html(comm.getNameByEnumId(res.data.entType, comm.lang("coDeclaration").entTypeEnum));
						$("#status").html(comm.getNameByEnumId(res.data.status, comm.lang("coDeclaration").dealStatusEnum));
			 			$('#yxkhcx_dialog').dialog({width:800,
			 				buttons:{
			 					'确定':function(){
			 						$(this).dialog('destroy');
			 					}
			 				}
			 			}); 
					});
				});
			}else{
				link=  $('<a>处理</a>').click(function(e) {
					var params = {applyId:record['applyId']};
					dataModoule.findIntentCustById(params, function(res){
						$('#yxkhcx_dialog>p').html(_.template(yxhkcx_cl_dialogTpl, res.data));
						$("#region").html(comm.getRegionByCode(res.data['countryNo'], res.data['provinceNo'], res.data['cityNo']));
						$("#appType").html(comm.getNameByEnumId(res.data.appType, comm.lang("coDeclaration").appTypeEnum));
						$("#entType").html(comm.getNameByEnumId(res.data.entType, comm.lang("coDeclaration").entTypeEnum));
						if(status == "3"){
							$("#a3").attr("checked", true);
						}else if(status == "1" || status == "2"){
							$("#a2").attr("checked", true);
						}
			 			$('#yxkhcx_dialog').dialog({width:800,
			 				buttons:{
			 					'确定':function(){
			 						var params_ = {
											applyId:record['applyId'],
											status:$('input[name="status"]:checked').val(),
											apprRemark:$("#apprRemark").val()
										};
			 						dataModoule.dealIntentCustById(params_, function(res_){
			 							comm.alert({content:comm.lang("coDeclaration")[res_.retCode], callOk:function(){
			 								$('#yxkhcx_dialog').dialog('destroy');
			 								$('#queryBtn').click();
										}});
			 						});
			 					},
			 					'取消':function(){
			 						$(this).dialog('destroy');
			 					}
			 				}
			 			});
					});
				});
			}
			return link;
		}
	}
}); 
