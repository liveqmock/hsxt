define(['text!accountCompanyTpl/tzzh/tzfhmxcx.html',
		'accountCompanyDat/accountCompany',
		'accountCompanyLan'
		], function(tpl, accountCompany){
	return companyTzfhMxcx={
		showPage : function(){
			$('#busibox').html(tpl);
			
			comm.initSelect("#beginYear",comm.lang("accountCompany").years);
			comm.initSelect("#endYear",comm.lang("accountCompany").years);
			
			//查询单击事件
			$('#scpoint_searchBtn').click(function(){
				if (companyTzfhMxcx.queryValida().form()){
					companyTzfhMxcx.loadGrid();
				}
			});
		},
		/**
		 * 查询条件验证
		 */
		queryValida:function(){
			var valida = $("#scpoint_form").validate({ 
				rules : {
					beginYear : {
						required : true
					},
					endYear : {
						required : true,
						less : "#beginYear"
					}
				},
				messages : {
					beginYear : {
						required :  "请选择开始年份"
					},
					endYear : {
						required :  "请选择结束年份",
						less : "结束年份不能小于开始年份"
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
			
			return valida;
		},
		loadGrid:function(){
			var params = {
				search_custType : comm.lang("accountCompany").ent_type,
				search_beginDate : comm.removeNull($("#beginYear").val()),
				search_endDate : comm.removeNull($("#endYear").val()),
				search_hsResNo : comm.removeNull($("#hsResNo").val()),
				search_enterpriseName : comm.removeNull($("#enterpriseName").val()),
			};
			accountCompany.tzfh_detailed_page("scpoint_result_table",params,companyTzfhMxcx.detail); 
		},
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex == 3){
				return comm.formatMoneyNumber(record.amount);
			}
		}
	};
});